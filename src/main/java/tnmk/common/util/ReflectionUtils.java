package tnmk.common.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author khoi.tran on 2/28/17.
 */
public class ReflectionUtils {

    public static <A extends Annotation> Field findFieldByAnnotationType(Class<?> clazz, Class<A> annotationClass) {
        org.springframework.data.util.ReflectionUtils.AnnotationFieldFilter annotationFieldFilter = new org.springframework.data.util.ReflectionUtils.AnnotationFieldFilter(annotationClass);
        return org.springframework.data.util.ReflectionUtils.findField(clazz, annotationFieldFilter);
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
}
