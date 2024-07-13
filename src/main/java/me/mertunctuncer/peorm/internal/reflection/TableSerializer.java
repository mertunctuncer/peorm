package me.mertunctuncer.peorm.internal.reflection;

import me.mertunctuncer.peorm.api.annotation.Column;
import me.mertunctuncer.peorm.internal.reflection.model.ClassData;
import me.mertunctuncer.peorm.internal.reflection.model.FieldData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TableSerializer<T> {

    ClassData<T> readClass(Class<T> clazz) {
        List<FieldData> fieldDataList = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if(column == null) continue;

        }
    }
}
