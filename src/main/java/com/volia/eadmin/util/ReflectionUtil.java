package com.volia.eadmin.util;

import com.volia.eadmin.domain.AbstractEntity;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class ReflectionUtil {
    private ReflectionUtil(){}

    public static Class loadClass(ClassLoader classLoader, String className){
        try{
            return classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Field getDeclaredField(Class targetClass, String fieldName){
        Field field = null;
        try{
            field = targetClass.getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException: " + e.getMessage());
        }
        return field;
    }

    public static Object getDeclaredFieldValue(Object targetClass, String columnName){
        try {
            if(targetClass != null){
                Field field = "id".equals(columnName)
                        ? getDeclaredField(targetClass.getClass().getSuperclass(), columnName)
                        : getDeclaredField(targetClass.getClass(), columnName);
                field.setAccessible(true);
                return field.get(targetClass);
            }
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException: " + e.getMessage());
        }
        return null;
    }

    public static boolean isEntity(Type genericType){
        return AbstractEntity.class.isAssignableFrom((Class)(((ParameterizedType) genericType).getActualTypeArguments()[0]));
    }

    public static String getSimpleNameByType(Type genericType){
        return ((Class) genericType).getSimpleName();
    }

    public static String getGenericSimpleNameOfList(Type genericType){
        ParameterizedType stringListType = (ParameterizedType) genericType;
        Class<?> genericClass = (Class<?>) stringListType.getActualTypeArguments()[0];
        return genericClass.getSimpleName();
    }

    public static Class getObjectByGenericSuperclass(Class targetClass){
        ParameterizedType pt = (ParameterizedType)targetClass.getGenericSuperclass();
        return (Class)pt.getActualTypeArguments()[0];
    }

    public static String getNameOfEntityByGenericSuperclass(Class targetClass){
        return (getObjectByGenericSuperclass(targetClass)).getSimpleName();
    }

    public static String getObjectNameByFieldName(Object target, String fieldName){
        return getFieldByName(target, fieldName).getType().getSimpleName();
    }

    public static Field getFieldByName(Object target, String fieldName){
        try{
            return target.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException: " + e.getMessage());
        }
        return null;
    }

    public static String getNameOfGenericEntity(Object target, String fieldName){
        return ((Class)((ParameterizedType) getFieldByName(target, fieldName).getGenericType()).getActualTypeArguments()[0]).getSimpleName();
    }

    public static Object createEntity(Class targetClass){
        try {
            return getObjectByGenericSuperclass(targetClass).newInstance();
        } catch (InstantiationException e) {
            System.out.println("InstantiationException: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException: " + e.getMessage());
        }
        return null;
    }
}
