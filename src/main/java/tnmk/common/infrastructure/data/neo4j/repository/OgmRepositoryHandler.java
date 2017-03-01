package tnmk.common.infrastructure.data.neo4j.repository;

import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.exception.UnexpectedException;
import tnmk.common.infrastructure.data.neo4j.Neo4jUtils;
import tnmk.common.util.StringUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author khoi.tran on 2/28/17.
 */
public class OgmRepositoryHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(OgmRepositoryHandler.class);

//    @Autowired
//    protected Session session;

    //    private final Class<?> entityClass;
    private final PropertyDescriptor idPropertyDescription;
    private final Method idGetter;

    protected OgmRepositoryHandler(Class<?> entityClass) {
//        this.entityClass = entityClass;
        this.idPropertyDescription = Neo4jUtils.getIdPropertyDescriptor(entityClass);
        this.idGetter = idPropertyDescription.getReadMethod();
    }

    @Transactional
    public void setPropertiesAndRelationships(Session session, Object entity) {
        if (entity == null) {
            return;
        }
        removeAllRelationships(session, entity);
        session.save(entity, 1);
    }

    @Transactional
    public void setPropertiesAndRelationshipsByTypes(Session session, Object entity, String... relationshipTypes) {
        removeRelationshipsByTypes(session, entity, relationshipTypes);
        session.save(entity, 1);
    }

    @Transactional
    public void setPropertiesAndRelationshipsByDirection(Session session, Object entity, RelationshipDirection... relationshipDirections) {
        removeRelationshipsByDirection(session, entity, relationshipDirections);
        session.save(entity, 1);
    }

    @Transactional
    public void removeAllRelationships(Session session, Object entity) {
        if (entity == null) {
            return;
        }
        String label = Neo4jUtils.getLabel(entity);
        String queryString = String.join("",
                "MATCH (a:", label, ")-[rel]-(b) WHERE id(a)={id} DELETE rel");
        LOGGER.debug(queryString);
        Map<String, Object> params = new HashMap<>();
        params.put("id", getId(entity));
        Result result = session.query(queryString, params);
        result.queryStatistics().getRelationshipsDeleted();
    }

    @Transactional
    public void removeRelationshipsByDirection(Session session, Object entity, RelationshipDirection... relationshipDirections) {
        String[] relationshipTypes = Neo4jUtils.findAllRelationshipsByDirection(entity, relationshipDirections);
        removeRelationshipsByTypes(session, entity, relationshipTypes);
    }

    /**
     * @param session
     * @param entity
     * @param relationshipTypes
     */
    @Transactional
    public void removeRelationshipsByTypes(Session session, Object entity, String... relationshipTypes) {
        if (entity == null) {
            return;
        }
        String label = Neo4jUtils.getLabel(entity);
        String relationshipsQuery = StringUtil.joinNotBlankStringsWithElementWrapper(" | ", ":`", "`", relationshipTypes);
        String queryString = String.join("",
                "MATCH (a:", label, ")-[rel", relationshipsQuery, "]-(b) WHERE id(a)={id} DELETE rel");
        LOGGER.debug(queryString);
        Map<String, Object> params = new HashMap<>();
        params.put("id", getId(entity));
        Result result = session.query(queryString, params);
        result.queryStatistics().getRelationshipsDeleted();
    }

    private Object getId(Object entity) {
        try {
            Object idObject = idGetter.invoke(entity);
            return idObject;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new UnexpectedException("Cannot get Id value from object " + entity);
        }
    }

}
