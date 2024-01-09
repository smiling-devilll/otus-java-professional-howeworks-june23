package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(),
                List.of(id),
                this::getNextEntry
        );
    }

    @Override
    public List<T> findAll(Connection connection) {
        var res = dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(),
                Collections.emptyList(),
                this::getListOfEntries
        );
        return res.orElse(Collections.emptyList());
    }

    @Override
    public long insert(Connection connection, T client) {
        var fields = entityClassMetaData.getFieldsWithoutId();
        List<Object> params = new ArrayList<>(getObjectFieldValues(client, fields));

        return dbExecutor.executeStatement(
                connection,
                entitySQLMetaData.getInsertSql(),
                params
        );
    }

    @Override
    public void update(Connection connection, T client) {
        var fields = entityClassMetaData.getAllFields();
        List<Object> params = new ArrayList<>(getObjectFieldValues(client, fields));
        dbExecutor.executeStatement(
                connection,
                entitySQLMetaData.getUpdateSql(),
                params
        );
    }

    private List<Object> getObjectFieldValues(T object, List<Field> fields) {
        return fields.stream().map(field -> {
            try {
                field.setAccessible(true);
                var value = field.get(object);
                field.setAccessible(false);
                return value;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(String.format("Cannot access field %s", field.getName()), e);
            }
        }).collect(Collectors.toList());
    }

    private T convertToObject(ResultSet rs) {

        var fields = entityClassMetaData.getAllFields();
        var fieldValues = fields.stream()
                .map(field -> {
                    try {
                        return rs.getObject(field.getName());
                    } catch (SQLException e) {
                        throw new RuntimeException(String.format("Cannot find field %s in resultSet", field.getName()), e);
                    }
                }).toList();

        var constructor = entityClassMetaData.getConstructor();
        try {
            return constructor.newInstance(fieldValues.toArray());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(String.format("Cannot create instance of class %s", entityClassMetaData.getName()), e);
        }

    }

    private T getNextEntry(ResultSet rs) {
        try {
            if (rs.next()) {
                return convertToObject(rs);
            } else {
                throw new RuntimeException("No data in rs");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot invoke rs.next", e);
        }
    }
    private List<T> getListOfEntries(ResultSet rs) {
        var list = new ArrayList<T>();
        try {
            while (rs.next()) {
                list.add(convertToObject(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot invoke rs.next", e);
        }
        return list;
    }
}
