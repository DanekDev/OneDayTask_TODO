package com.danielkarpov.todo.persistence;

import com.danielkarpov.todo.exception.TaskNotFoundException;
import com.danielkarpov.todo.model.Dao.tables.records.TaskRecord;
import com.danielkarpov.todo.model.Dto.TaskDto;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.danielkarpov.todo.model.Dao.Tables.TASK;

@Service
public class TaskRepository {
    String userName = "todo_user";
    String password = "password";
    String url = "jdbc:postgresql://localhost:5432/todo";
    DSLContext dslContext;
    Connection connection;

    public TaskRepository() throws SQLException {
        connection = DriverManager.getConnection(url, userName, password);
        dslContext = DSL.using(connection, SQLDialect.POSTGRES);
    }

    public Optional<List<TaskDto>> getAllTasks(){
        Optional<List<TaskDto>> optionalTasks = null;
        List<TaskDto> tasks = new ArrayList<>();

        Result<Record> result = dslContext.select().from(TASK).fetch();

        for (Record r : result) {
            tasks.add(new TaskDto(r.getValue(TASK.NAME), r.get(TASK.STATUS)));
        }
        optionalTasks = Optional.of(tasks);

        return optionalTasks;
    }

    public TaskDto add(TaskDto taskDto) {

        TaskRecord persisted = dslContext.insertInto(TASK)
                .set(createRecord(taskDto.getName(), true))
                .returning()
                .fetchOne();

        return new TaskDto(persisted.getName(), persisted.getStatus());
    }

    private TaskRecord createRecord(String name, boolean status) {

        TaskRecord record = new TaskRecord();
        record.setName(name);
        record.setStatus(status);
        return record;
    }

    public void safelyDeleteTask(String name) {
        if (taskExists(name))
            forcedDeleteTask(name);
    }

    private void forcedDeleteTask(String name){
         dslContext.delete(TASK)
                .where(TASK.NAME.eq(name))
                .execute();
    }

    public Optional<TaskDto> updateTask(TaskDto taskDto){
        if(taskExists(taskDto.getName())) {
            forcedDeleteTask(taskDto.getName());
            TaskRecord persisted = dslContext.insertInto(TASK)
                    .set(createRecord(taskDto.getName(), taskDto.isStatus()))
                    .returning()
                    .fetchOne();
            return Optional.of(new TaskDto(persisted.getName(), persisted.getStatus()));
        }
        return Optional.empty();
    }

    public boolean taskExists(String name) {
        TaskRecord queryResult = dslContext.selectFrom(TASK)
                .where(TASK.NAME.equal(name))
                .fetchOne();

        if (queryResult == null) {
            TaskNotFoundException.causedByTaskNonExistence(name).printStackTrace();
            return false;
        }
        return true;
    }
}
