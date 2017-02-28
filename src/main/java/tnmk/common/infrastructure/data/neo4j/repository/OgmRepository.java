package tnmk.common.infrastructure.data.neo4j.repository;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.exception.UnexpectedException;
import tnmk.common.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <E> the type of entity. It must have an id property (with annotation {@link GraphId}).
 * @author khoi.tran on 2/28/17.
 */
public class OgmRepository<E> {
    public static final Logger LOGGER = LoggerFactory.getLogger(OgmRepository.class);

    @Autowired
    protected Session session;

    private final Class<E> entityClass;
    private final PropertyDescriptor idPropertyDescription;
    private final Method idGetter;

    public OgmRepository() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        entityClass = (Class<E>) types[0];
        idPropertyDescription = getIdPropertyDescriptor(entityClass);
        idGetter = idPropertyDescription.getReadMethod();
    }

    @Transactional
    public void updateWithRelationshipsReplacement(E entity) {
        if (entity == null) {
            return;
        }
        removeAllRelationships(entity);
        session.save(entity, 1);
    }

    /**
     * @param entity
     * @param relationships the name of relationships, not the name of properties
     */
    @Transactional
    public void updateWithRelationshipsReplacement(E entity, String... relationships) {
        removeRelationships(entity, relationships);
        session.save(entity, 1);
    }

    @Transactional
    public void removeAllRelationships(E entity) {
        if (entity == null) {
            return;
        }
        Class<E> entityClass = (Class<E>) entity.getClass();
        NodeEntity nodeEntity = entityClass.getAnnotation(NodeEntity.class);
        String label = nodeEntity.label();
        if (StringUtils.isBlank(label)) {
            label = entityClass.getSimpleName();
        }
        String queryString = String.join("",
                "MATCH (a:", label, ")-[rel]-(b) WHERE id(a)={id} DELETE rel");
        LOGGER.debug(queryString);
        Map<String, Object> params = new HashMap<>();
        params.put("id", getId(entity));
        Result result = session.query(queryString, params);
        result.queryStatistics().getRelationshipsDeleted();
    }

    @Transactional
    public void removeRelationships(E entity, String... relationships) {
        if (entity == null) {
            return;
        }
        Class<E> entityClass = (Class<E>) entity.getClass();
        NodeEntity nodeEntity = entityClass.getAnnotation(NodeEntity.class);
        String label = nodeEntity.label();
        if (StringUtils.isBlank(label)) {
            label = entityClass.getSimpleName();
        }

        String relationshipsQuery = String.join(" | :", relationships);
        String queryString = String.join("",
                "MATCH (a:", label, ")-[rel:", relationshipsQuery, "]-(b) WHERE id(a)={id} DELETE rel");
        LOGGER.debug(queryString);
        Map<String, Object> params = new HashMap<>();
        params.put("id", getId(entity));
        Result result = session.query(queryString, params);
        result.queryStatistics().getRelationshipsDeleted();
    }

    private Object getId(E entity) {
        try {
            Object idObject = idGetter.invoke(entity);
            return idObject;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new UnexpectedException("Cannot get Id value from object " + entity);
        }
    }

    private PropertyDescriptor getIdPropertyDescriptor(Class<?> clazz) {
        Field idField = ReflectionUtils.findFieldByAnnotationType(clazz, GraphId.class);
        if (idField == null) {
            throw new UnexpectedException("Not found any field with annotation " + GraphId.class.getName() + " in class " + clazz.getName());
        }
        return ReflectionUtils.getPropertyDescriptor(clazz, idField.getName());
    }

    private String[] findAllRelationships(E entity) {
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
