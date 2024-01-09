package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaData<T> entityClassMetaDataClient;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaDataClient) {
        this.entityClassMetaDataClient = entityClassMetaDataClient;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select %s from %s",
                entityClassMetaDataClient.getAllFields().stream().map(Field::getName).collect(Collectors.joining(",")),
                entityClassMetaDataClient.getName()
        );
    }

    @Override
    public String getSelectByIdSql() {
        return String.format(
                "select %s from %s where %s = ?",
                entityClassMetaDataClient.getAllFields().stream().map(Field::getName).collect(Collectors.joining(",")),
                entityClassMetaDataClient.getName(),
                entityClassMetaDataClient.getIdField().getName()
        );
    }

    @Override
    public String getInsertSql() {
        return String.format(
                "insert into %s (%s) values (%s)",
                entityClassMetaDataClient.getName(),
                entityClassMetaDataClient.getFieldsWithoutId().stream().map(Field::getName).collect(Collectors.joining(",")),
                entityClassMetaDataClient.getFieldsWithoutId().stream().map(f -> "?").collect(Collectors.joining(","))
        );
    }

    @Override
    public String getUpdateSql() {
        return String.format(
                "update %s set %s where %s=?",
                entityClassMetaDataClient.getName(),
                entityClassMetaDataClient.getFieldsWithoutId().stream().map(f -> f.getName() + "=?").collect(Collectors.joining(",")),
                entityClassMetaDataClient.getIdField().getName()
        );
    }
}
