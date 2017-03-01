package tnmk.common.infrastructure.data.neo4j;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import tnmk.common.exception.UnexpectedException;
import tnmk.common.infrastructure.data.neo4j.repository.RelationshipDirection;
import tnmk.common.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
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
        //Note:
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

    public static String[] findAllRelationships(Object entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        List<String> relationshipsList = new ArrayList<>();
        for (Field field : fields) {
            Relationship relationshipAnno = field.getAnnotation(Relationship.class);
            String relationshipName;
            if (relationshipAnno == null) {
                relationshipName = field.getName();
            } else {
                relationshipName = relationshipAnno.type();
            }
            relationshipsList.add(relationshipName);
        }
        return relationshipsList.toArray(new String[] {});
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
