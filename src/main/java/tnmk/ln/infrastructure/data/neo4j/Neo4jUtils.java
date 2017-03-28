package tnmk.ln.infrastructure.data.neo4j;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.beans.BeanUtils;
import tnmk.common.exception.UnexpectedException;
import tnmk.common.util.NumberUtil;
import tnmk.common.util.ReflectionUtils;
import tnmk.ln.infrastructure.data.neo4j.repository.RelationshipDirection;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author khoi.tran on 2/28/17.
 */
public class Neo4jUtils {
    public static PropertyDescriptor getIdPropertyDescriptor(Class<?> clazz) {
        Field idField = ReflectionUtils.findFieldByAnnotationType(clazz, GraphId.class);
        if (idField == null) {
            throw new UnexpectedException("Not found any field with annotation " + GraphId.class.getName() + " in class " + clazz.getName());
        }
        return ReflectionUtils.getPropertyDescriptor(clazz, idField.getName());
    }

    public static String getLabel(Object entity) {
        //Topic:
        // the entity can be a subclass of <E>, so we should work based on entity.getClass()
        // should not work on this.entityClass
        Class<?> entityClass = entity.getClass();
        NodeEntity nodeEntity = entityClass.getAnnotation(NodeEntity.class);
        String label = null;
        if (nodeEntity != null) {
            label = nodeEntity.label();
        }
        if (StringUtils.isBlank(label)) {
            label = entityClass.getSimpleName();
        }
        return label;
    }

    public static Long getId(Object entity) {
        Field idField = ReflectionUtils.findFieldByAnnotationType(entity.getClass(), GraphId.class);
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(entity.getClass(), idField.getName());
        Object id = ReflectionUtils.readProperty(entity, propertyDescriptor);
        return NumberUtil.toLongIfPossible(id);
    }

    public static String[] findAllRelationshipsOnAnnotatedFields(Class<?> entityClass, Class<? extends Annotation> annotationClass) {
        return findRelationships(entityClass, field -> field.getAnnotationsByType(annotationClass) != null);
    }

    public static String[] findAllRelationships(Class<?> entityClass) {
        return findRelationships(entityClass, null);
    }

    public static String[] findRelationships(Class<?> entityClass, Predicate<Field> predicate) {
        Field[] fields = entityClass.getDeclaredFields();
        List<String> relationshipsList = new ArrayList<>();
        for (Field field : fields) {
            if (predicate != null && !predicate.evaluate(field)) continue;
            relationshipsList.add(findRelationshipType(field));
        }
        return relationshipsList.toArray(new String[] {});
    }

    public static String findRelationshipType(Field field) {
        Relationship relationshipAnno = field.getAnnotation(Relationship.class);
        String relationshipType;
        if (relationshipAnno == null) {
            relationshipType = field.getName();
        } else {
            relationshipType = relationshipAnno.type();
        }
        return relationshipType;
    }

    public static List<Field> findRelationshipFields(Class<?> entityClass, Predicate<Field> predicate) {
        Field[] fields = entityClass.getDeclaredFields();
        List<Field> relationshipsList = new ArrayList<>();
        for (Field field : fields) {
            if (predicate != null && !predicate.evaluate(field)) continue;
            Relationship relationshipAnno = field.getAnnotation(Relationship.class);
            if (relationshipAnno != null) {
                relationshipsList.add(field);
            }
        }
        return relationshipsList;
    }

    /**
     * @param entity
     * @param includeRelationshipDirections
     * @return relationship types
     */
    public static String[] findAllRelationshipsByDirection(Object entity, RelationshipDirection... includeRelationshipDirections) {
        Field[] fields = entity.getClass().getDeclaredFields();
        List<String> relationshipsList = new ArrayList<>();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;//Ignore the static field.
            }
            if (ReflectionUtils.isSimpleType(field.getType())) {
                continue;
            }
            Relationship relationshipAnno = field.getAnnotation(Relationship.class);
            String relationshipDirection;
            String relationshipName;
            if (relationshipAnno == null) {
                relationshipName = field.getName();
                relationshipDirection = RelationshipDirection.OUTGOING.name();//Default relationship of Spring Neo4j
            } else {
                relationshipName = relationshipAnno.type();
                relationshipDirection = relationshipAnno.direction();
            }
            if (containsDirection(relationshipDirection, includeRelationshipDirections)) {
                relationshipsList.add(relationshipName);
            }
        }
        return relationshipsList.toArray(new String[] {});
    }

    public static boolean containsDirection(String findingDirection, RelationshipDirection... relationshipDirections) {
        for (RelationshipDirection relationshipDirection : relationshipDirections) {
            if (relationshipDirection.name().equals(findingDirection)) {
                return true;
            }
        }
        return false;
    }
}
