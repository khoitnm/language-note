package org.tnmk.common.utils.reflection;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.util.Assert;
import org.tnmk.common.exception.UnexpectedException;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author khoi.tran on 2/28/17.
 */
public final class ReflectionUtils {
    private ReflectionUtils() {
    }

    public static <A extends Annotation> Field findFieldByAnnotationType(Class<?> clazz, Class<A> annotationClass) {
        org.springframework.data.util.ReflectionUtils.AnnotationFieldFilter annotationFieldFilter = new org.springframework.data.util.ReflectionUtils.AnnotationFieldFilter(annotationClass);
        return org.springframework.data.util.ReflectionUtils.findField(clazz, annotationFieldFilter);
    }

    public static <A extends Annotation> List<Field> findFieldsByAnnotationType(Class<?> clazz, Class<A> annotationClass) {
        List<Field> fieldsWithAnnotation = new ArrayList<>();
        List<Field> fields = getDeclaredFieldsIncludeSuperClasses(clazz);
        for (Field field : fields) {
            A annotation = field.getAnnotation(annotationClass);
            if (annotation != null) {
                fieldsWithAnnotation.add(field);
            }
        }
        return fieldsWithAnnotation;
    }

    public static Field findFieldByName(Class<?> clazz, String fieldName){
        return org.springframework.util.ReflectionUtils.findField(clazz, fieldName);
    }

    /**
     * @param clazz
     * @param fieldName
     * @return It will also find PropertyDescriptor in super classes.
     */
    public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String fieldName) {
        PropertyDescriptor propertyDescriptor;
        try {
            propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, fieldName);
        } catch (BeansException ex) {
            String msg = String.format("Cannot find property '%s' in class %s: %s", fieldName, clazz, ex.getMessage());
            throw new UnexpectedException(msg, ex);
        }
        return propertyDescriptor;
    }

    /**
     * @param clazz
     * @param annotationClass
     * @param <A>
     * @return also look up in super classes.
     */
    public static <A extends Annotation> List<PropertyDescriptor> findPropertyDescriptorsByAnnotationType(Class<?> clazz, Class<A> annotationClass) {
        List<PropertyDescriptor> result = new ArrayList<>();
        List<Field> fields = getDeclaredFieldsIncludeSuperClasses(clazz);
        for (Field field : fields) {
            A annotation = field.getAnnotation(annotationClass);
            if (annotation != null) {
                PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, field.getName());
                if (propertyDescriptor == null) {
                    String msg = String.format("The class %s has field %s, so its descriptor must be not null", clazz, field);
                    throw new UnexpectedException(msg);
                }
                result.add(propertyDescriptor);
            }
        }
        return result;
    }



    /**
     * This method doesn't check Map, Array or Collection types
     *
     * @param type
     * @return
     */
    public static boolean isSimpleType(Class<?> type) {
        boolean result = BeanUtils.isSimpleValueType(type) || isDateTimeType(type);
        return result;
    }

    public static boolean isDateTimeType(Class<?> type) {
        return Temporal.class.isAssignableFrom(type) || Date.class.isAssignableFrom(type);
    }

    public static Object readProperty(Object object, String propertyName) {
        if (object == null) {
            return null;
        }
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(object.getClass(), propertyName);
        return readProperty(object, propertyDescriptor);
    }

    public static void writeProperty(Object object, String propertyName, Object propertyValue) {
        if (object == null) {
            return;
        }
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(object.getClass(), propertyName);
        if (propertyDescriptor == null){
            String msg = String.format("Cannot set value into the property '%s' of the object %s: the field doesn't exist.", propertyName, object);
            throw new UnexpectedException(msg);
        }
        writeProperty(object, propertyDescriptor, propertyValue);
    }

    public static Object readProperty(Object object, PropertyDescriptor propertyDescriptor) {
        Method method = propertyDescriptor.getReadMethod();
        Assert.notNull(method, "The field " + propertyDescriptor.getName() + " of object " + object + " doesn't have getter");
        try {
            return method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new UnexpectedException(e.getMessage(), e);
        }
    }

    public static void writeProperty(Object object, PropertyDescriptor propertyDescriptor, Object propertyValue) {
        Method method = propertyDescriptor.getWriteMethod();
        Assert.notNull(method, "The field " + propertyDescriptor.getName() + " of object " + object + " doesn't have setter");
        try {
            method.invoke(object, propertyValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new UnexpectedException(e.getMessage(), e);
        }
    }

    public static boolean isWrapperClass(Class<?> entityClass) {
        boolean result = (isIterable(entityClass)
            || isMap(entityClass)
            || isArray(entityClass));
        return result;
    }

    public static boolean isArray(Class<?> entityClass) {
        return entityClass != null && entityClass.isArray();
    }

    public static boolean isMap(Class<?> entityClass) {
        return Map.class.isAssignableFrom(entityClass);
    }

    public static boolean isIterable(Class<?> entityClass) {
        return Iterable.class.isAssignableFrom(entityClass);
    }

    public static List<Class<?>> getParameterClasses(Field field) {
        Type[] types = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
        List<Class<?>> classes = new ArrayList<>();
        classes.addAll(getParameterClasses(types));
        return classes;
    }

    private static List<Class<?>> getParameterClasses(Type[] types) {
        List<Class<?>> classes = new ArrayList<>();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType iparameterizedType = (ParameterizedType) type;
                classes.addAll(getParameterClasses(iparameterizedType));
            } else if (type instanceof Class<?>) {
                classes.add((Class<?>) type);
            }
        }
        return classes;
    }

    private static List<Class<?>> getParameterClasses(ParameterizedType parameterizedType) {
        List<Class<?>> classes = new ArrayList<>();
        Type[] types = parameterizedType.getActualTypeArguments();
        classes.addAll(getParameterClasses(types));
        return classes;
    }

    public static List<Field> getDeclaredFieldsIncludeSuperClasses(Class<?> type) {
        List<Field> result = new ArrayList<>();

        Class<?> i = type;
        while (i != null && i != Object.class) {
            Collections.addAll(result, i.getDeclaredFields());
            i = i.getSuperclass();
        }

        return result;
    }

    public static boolean isStatic(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new UnexpectedException("Cannot create instance of class " + clazz + ": " + e.getMessage(), e);
        }
    }
}