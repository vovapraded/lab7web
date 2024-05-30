package org.example.utility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnumValidator {

    public static <T extends Enum<T>> boolean isValidEnum(String value, Class<T> clazz) {
        try {
            // Получаем метод valueOf, который принимает String
            Method valueOfMethod = clazz.getMethod("valueOf", String.class);
            // Вызываем этот метод, передавая ему значение в качестве аргумента
            valueOfMethod.invoke(null, value.toUpperCase().replace("-", "_"));
            return true; // Если метод вызван без исключений, значит значение допустимо
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return false; // Если возникли исключения, значит значение недопустимо
        }
    }
}