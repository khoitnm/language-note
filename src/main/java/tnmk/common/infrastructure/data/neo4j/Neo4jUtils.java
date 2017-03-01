package tnmk.common.infrastructure.data.neo4j;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author khoi.tran on 2/28/17.
 */
public class Neo4jUtils {

    public static String getLabel(Object entity) {
        //Note:
        // the entity can be a subclass of <E>, so we should work based on entity.getClass()
        // should not work on this.entityClass
        Class<?> entityClass = entity.getClass();
        NodeEntity nodeEntity = entityClass.getAnnotation(NodeEntity.class);
        String label = nodeEntity.label();
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
}
