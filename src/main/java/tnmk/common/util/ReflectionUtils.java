package tnmk.common.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.util.Assert;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import tnmk.common.exception.UnexpectedException;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author khoi.tran on 2/28/17.
 */
public class ReflectionUtils {

    public static <A extends Annotation> Field findFieldByAnnotationType(Class<?> clazz, Class<A> annotationClass) {
        org.springframework.data.util.ReflectionUtils.AnnotationFieldFilter annotationFieldFilter = new org.springframework.data.util.ReflectionUtils.AnnotationFieldFilter(annotationClass);
        return org.springframework.data.util.ReflectionUtils.findField(clazz, annotationFieldFilter);
    }

//    public PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String fieldName) {
//        return BeanUtils.getPropertyDescriptor(clazz, fieldName);
//    }

    public static <A extends Annotation> List<PropertyDescriptor> findPropertyDescriptorsByAnnotationType(Class<?> clazz, Class<A> annotationClass) {
        List<PropertyDescriptor> result = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
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

    public static <A extends Annotation> List<Field> findFieldsByAnnotationType(Class<?> clazz, Class<A> annotationClass) {
        List<Field> fieldsWithAnnotation = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            A annotation = field.getAnnotation(annotationClass);
            if (annotation != null) {
                fieldsWithAnnotation.add(field);
            }
        }
        return fieldsWithAnnotation;
    }

    public static <A extends Annotation> List<A> findAnnotationsByAnnotationType(Class<?> clazz, Class<A> annotationClass) {
        List<A> fieldsWithAnnotation = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            A annotation = field.getAnnotation(annotationClass);
            if (annotation != null) {
                fieldsWithAnnotation.add(annotation);
            }
        }
        return fieldsWithAnnotation;
    }

    public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String fieldName) {
        PropertyDescriptor propertyDescriptor;
        try {
            propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, fieldName);
        } catch (BeansException ex) {
            propertyDescriptor = null;
        }
        return propertyDescriptor;
    }

    /**
     * This method doesn't check Map, Array or Collection types
     *
     * @param type
     * @return
     */
    public static boolean isSimpleType(Class<?> type) {
        if (BeanUtils.isSimpleValueType(type)) return true;
        if (isDateTimeType(type)) return true;
        return false;
    }

    public static boolean isDateTimeType(Class<?> type) {
        return Temporal.class.isAssignableFrom(type) || Date.class.isAssignableFrom(type);
    }

    public static Object readProperty(Object object, String propertyName) {
        if (object == null) return null;
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(object.getClass(), propertyName);
        return readProperty(object, propertyDescriptor);
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

    public static boolean isWrapperClass(Class<?> entityClass) {
        boolean result = (isIterable(entityClass)
                || isMap(entityClass)
                || isArray(entityClass));
        return result;
    }

    public static boolean isArray(Class<?> entityClass) {
        return Array.class.isAssignableFrom(entityClass);
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
            if (type instanceof ParameterizedTypeImpl) {
                ParameterizedType iparameterizedType = (ParameterizedTypeImpl) type;
                iparameterizedType.getActualTypeArguments();
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

    //TODO not finished yet.
    public void traverseEntity(Object entity, Function<FieldDeclaration, Boolean> acceptFilter, Function action) {
        if (entity == null) return;
        Class<?> clazz = entity.getClass();
        if (isArray(clazz)) {

        } else if (isIterable(clazz)) {

        } else if (isMap(clazz)) {

        } else {
            List<Field> fields = getDeclaredFieldsIncludeSuperClasses(clazz);
            for (Field field : fields) {
                Object fieldValue = readProperty(entity, field.getName());
                if (!acceptFilter.apply(new FieldDeclaration(entity, field, fieldValue))) continue;

            }
        }
    }

    public static class FieldDeclaration {
        private Field field;
        private Object fieldValue;
        private Object parent;

        public FieldDeclaration(Object parent, Field field, Object fieldValue) {
            this.field = field;
            this.fieldValue = fieldValue;
            this.parent = parent;
        }
    }
}