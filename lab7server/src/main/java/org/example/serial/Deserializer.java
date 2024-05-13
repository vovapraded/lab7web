package org.example.serial;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class Deserializer {

    public static <T> T deserialize(byte[] data,Class<T> clazz ) throws DeserializeException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        try {
            // Создание объектного потока ввода для десериализации объекта
            ObjectInputStream objectStream = new ObjectInputStream(byteStream);

// Десериализация объекта из потока и приведение его к нужному типу
            Object deserializedObject = objectStream.readObject();
            return  clazz.cast(deserializedObject);
        }
        catch (Exception e){
            throw new DeserializeException("Ошибка десереализации объекта "+clazz);
        }


    }
}