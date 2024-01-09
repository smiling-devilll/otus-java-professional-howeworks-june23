package ru.otus.jdbc.mapper;

import ru.otus.jdbc.mapper.annotations.Id;
import ru.otus.jdbc.mapper.exceptions.NoDefaultConstructor;
import ru.otus.jdbc.mapper.exceptions.NoIdFieldException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;
    private final List<Field> fields;
    private final Constructor<T> constructor;

    private final Field idField;

    public EntityClassMetaDataImpl(Class<T> clazz) throws NoSuchMethodException {
        this.clazz = clazz;
        this.fields = Arrays.stream(this.clazz.getDeclaredFields()).collect(Collectors.toList());
        this.constructor = this.clazz.getDeclaredConstructor(fields.stream().map(Field::getGenericType).toList().toArray(new Class[]{}));
        this.idField = fields.stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new NoIdFieldException(this.getName()));
    }

    @Override
    public String getName() {
        return this.clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return getAllFields().stream()
                .filter(field -> !field.equals(idField))
                .collect(Collectors.toList());
    }
}
