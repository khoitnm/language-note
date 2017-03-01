package tnmk.common.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.util.Assert;
import tnmk.common.exception.UnexpectedException;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author khoi.tran on 2/28/17.
 */
public class ReflectionUtils {

    public static <A extends Annotation> Field findFieldByAnnotationType(Class<?> clazz, Class<A> annotationClass) {
        org.springframework.data.util.ReflectionUtils.AnnotationFieldFilter annotationFieldFilter = new org.springframework.data.util.ReflectionUtils.AnnotationFieldFilter(annotationClass);
        return org.springframework.data.util.ReflectionUtils.findField(clazz, annotationFieldFilter);
    }

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

    public static Object readProperty(Object object, PropertyDescriptor propertyDescriptor) {
        Method method = propertyDescriptor.getReadMethod();
        Assert.notNull(method, "The field " + propertyDescriptor.getName() + " of object " + object + " doesn't have getter");
        try {
            return method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new UnexpectedException(e.getMessage(), e);
        }
    }
}
