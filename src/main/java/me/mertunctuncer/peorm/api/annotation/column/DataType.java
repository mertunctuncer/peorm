package me.mertunctuncer.peorm.api.annotation.column;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DataType {
    int size() default -1;
    Types type() default Types.REFLECT;

    enum Types {
        REFLECT,
        CHAR,
        VARCHAR,
        BOOLEAN,
        INTEGER,
        FLOAT,
        DOUBLE,
        DATE,
        TIME,
        TIMESTAMP,
    }
}
