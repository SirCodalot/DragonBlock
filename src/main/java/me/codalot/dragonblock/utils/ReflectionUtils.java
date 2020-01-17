package me.codalot.dragonblock.utils;

import java.lang.reflect.Field;

public class ReflectionUtils {

//    public Object getField(Object object, String name) {
//
//    }
//
    public static <T> T getField(Object object, String name) {
        try {
            Class clazz = object.getClass();

            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);

            return (T) field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setField(Class clazz, Object object, String name, Object value) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);

            field.set(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
