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
import java.lang.reflect.Modifier;
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

    public static void traverseEntity(Object entity, Function<ActionStatus, Boolean> action) {
        traverseEntity(action, new ActionStatus(entity, null, null, null, null, null));
    }

    /**
     * @param action        return result whether to continue with children elements or not.
     * @param currentStatus
     */
    public static void traverseEntity(Function<ActionStatus, Boolean> action, ActionStatus currentStatus) {
        Object currentEntity = currentStatus.objectValue;
        if (currentEntity == null) return;
        boolean runActionResult = action.apply(currentStatus);
        if (!runActionResult) return;
        if (ReflectionUtils.isSimpleType(currentEntity.getClass())) return;

        Class<?> clazz = currentEntity.getClass();
        if (isArray(clazz)) {
            Object[] children = (Object[]) currentEntity;
            int i = 0;
            for (Object child : children) {
                ActionStatus childActionStatus = new ActionStatus(child, null, i, null, currentEntity, currentStatus);
                traverseEntity(action, childActionStatus);
                i++;
            }
        } else if (isIterable(clazz)) {
            Iterable<Object> children = (Iterable<Object>) currentEntity;
            int i = 0;
            for (Object child : children) {
                ActionStatus childActionStatus = new ActionStatus(child, null, i, null, currentEntity, currentStatus);
                traverseEntity(action, childActionStatus);
                i++;
            }
        } else if (isMap(clazz)) {
            Map<Object, Object> map = (Map<Object, Object>) currentEntity;
            for (Map.Entry<Object, Object> childEntry : map.entrySet()) {
                ActionStatus childActionStatus = new ActionStatus(childEntry.getValue(), null, null, childEntry.getKey(), currentEntity, currentStatus);
                traverseEntity(action, childActionStatus);
            }
        } else {
            List<Field> childFields = getDeclaredFieldsIncludeSuperClasses(clazz);
            for (Field childField : childFields) {
                if (ReflectionUtils.isStatic(childField)) {
                    continue;
                }
                Object childFieldValue = readProperty(currentEntity, childField.getName());
                ActionStatus childActionStatus = new ActionStatus(childFieldValue, childField, null, null, currentEntity, currentStatus);
                if (action.apply(childActionStatus)) {
                    traverseEntity(action, childActionStatus);
                }
            }
        }
    }

    private static boolean isStatic(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    public static class ActionStatus {
        /**
         * Only use when the parent is an object (not Array / Collection / Map)
         */
        private final Field objectField;
        /**
         * Only used when the container is an Array, or a Collection
         */
        private final Integer objectIndex;
        /**
         * Only used when the container is a Map
         */
        private final Object objectKey;
        private final Object objectValue;
        private final Object parent;
        private final ActionStatus parentActionStatus;

        private Object result;

        public ActionStatus(Object objectValue, Field objectField, Integer objectIndex, Object objectKey, Object parent, ActionStatus parentActionStatus) {
            this.objectField = objectField;
            this.objectValue = objectValue;
            this.objectIndex = objectIndex;
            this.objectKey = objectKey;
            this.parent = parent;
            this.parentActionStatus = parentActionStatus;
        }

        public Field getObjectField() {
            return objectField;
        }

        public Object getObjectValue() {
            return objectValue;
        }

        public Object getParent() {
            return parent;
        }

        public ActionStatus getParentActionStatus() {
            return parentActionStatus;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }

        public Integer getObjectIndex() {
            return objectIndex;
        }

        public Object getObjectKey() {
            return objectKey;
        }
    }
}