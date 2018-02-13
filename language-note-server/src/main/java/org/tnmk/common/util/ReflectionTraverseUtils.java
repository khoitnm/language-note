package org.tnmk.common.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author khoi.tran on 2/28/17.
 */
public final class ReflectionTraverseUtils {
    private ReflectionTraverseUtils() {
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
        if (currentEntity == null) {
            return;
        }
        boolean runActionResult = action.apply(currentStatus);
        if (!runActionResult) {
            return;
        }
        if (ReflectionUtils.isSimpleType(currentEntity.getClass())) {
            return;
        }

        Class<?> clazz = currentEntity.getClass();
        if (ReflectionUtils.isArray(clazz)) {
            Object[] children = (Object[]) currentEntity;
            traverseArray(children, currentEntity, action, currentStatus);
        } else if (ReflectionUtils.isIterable(clazz)) {
            Iterable<Object> children = (Iterable<Object>) currentEntity;
            traverseIterable(children, currentEntity, action, currentStatus);
        } else if (ReflectionUtils.isMap(clazz)) {
            Map<Object, Object> map = (Map<Object, Object>) currentEntity;
            traverseMap(map, currentEntity, action, currentStatus);
        } else {
            List<Field> childFields = ReflectionUtils.getDeclaredFieldsIncludeSuperClasses(clazz);
            traverseChildrenFields(childFields, currentEntity, action, currentStatus);
        }
    }

    private static void traverseMap(Map<Object, Object> map, Object currentEntity, Function<ActionStatus, Boolean> action, ActionStatus currentStatus) {
        for (Map.Entry<Object, Object> childEntry : map.entrySet()) {
            ActionStatus childActionStatus = new ActionStatus(childEntry.getValue(), null, null, childEntry.getKey(), currentEntity, currentStatus);
            traverseEntity(action, childActionStatus);
        }
    }

    private static void traverseIterable(Iterable<Object> children, Object currentEntity, Function<ActionStatus, Boolean> action, ActionStatus currentStatus) {
        int i = 0;
        for (Object child : children) {
            traverseChildElementInCollection(i, child, currentEntity, action, currentStatus);
            i++;
        }
    }

    private static void traverseArray(Object[] children, Object currentEntity, Function<ActionStatus, Boolean> action, ActionStatus currentStatus) {
        int i = 0;
        for (Object child : children) {
            traverseChildElementInCollection(i, child, currentEntity, action, currentStatus);
            i++;
        }
    }

    private static void traverseChildElementInCollection(int i, Object child, Object currentEntity, Function<ActionStatus, Boolean> action, ActionStatus currentStatus) {
        ActionStatus childActionStatus = new ActionStatus(child, null, i, null, currentEntity, currentStatus);
        traverseEntity(action, childActionStatus);
    }

    private static void traverseChildrenFields(List<Field> childFields, Object currentEntity, Function<ActionStatus, Boolean> action, ActionStatus currentStatus) {
        for (Field childField : childFields) {
            if (ReflectionUtils.isStatic(childField)) {
                continue;
            }
            Object childFieldValue = ReflectionUtils.readProperty(currentEntity, childField.getName());
            ActionStatus childActionStatus = new ActionStatus(childFieldValue, childField, null, null, currentEntity, currentStatus);
            if (action.apply(childActionStatus)) {
                traverseEntity(action, childActionStatus);
            }
        }
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