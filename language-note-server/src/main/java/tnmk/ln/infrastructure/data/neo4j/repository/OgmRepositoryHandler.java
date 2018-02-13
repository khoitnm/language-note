package tnmk.ln.infrastructure.data.neo4j.repository;

import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.exception.UnexpectedException;
import tnmk.common.util.ReflectionUtils;
import tnmk.common.util.StringUtil;
import tnmk.ln.infrastructure.data.neo4j.Neo4jUtils;
import tnmk.ln.infrastructure.data.neo4j.annotation.CascadeRelationship;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author khoi.tran on 2/28/17.
 * @deprecated Still has bugs, not completed
 */
@Deprecated
public class OgmRepositoryHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(OgmRepositoryHandler.class);

//    @Autowired
//    protected Session session;

    private final PropertyDescriptor idPropertyDescription;
    private final Method idGetter;

    protected OgmRepositoryHandler(Class<?> entityClass) {
        if (!(Iterable.class.isAssignableFrom(entityClass))
                && !(Array.class.isAssignableFrom(entityClass))
                && !(Map.class.isAssignableFrom(entityClass))) {
            this.idPropertyDescription = Neo4jUtils.getIdPropertyDescriptor(entityClass);
            this.idGetter = idPropertyDescription.getReadMethod();
        } else {
            this.idPropertyDescription = null;
            this.idGetter = null;
        }

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
    public void setPropertiesAndRelationshipsCascade(Session session, Object entity) {
        setPropertiesAndRelationshipsCascade(session, entity, -1);
    }

    @Transactional
    public void setPropertiesAndRelationshipsCascade(Session session, Object entity, int limitDepth) {
        List<Object> trackedEntities = new ArrayList<>();
        int level = 0;
        setPropertiesAndRelationshipsCascade(session, trackedEntities, entity, level, limitDepth);
    }

    public void setPropertiesAndRelationshipsCascade(Session session, List<Object> trackedEntities, Object entity, int level, int limitDepth) {
        if (entity == null || (limitDepth >= 0 && level >= limitDepth)) return;

        LOGGER.info("Entity: {}. Level: {}", entity, level);
        if (entity instanceof Iterable) {
            Iterable<?> childElements = (Iterable<?>) entity;
            for (Object childElement : childElements) {
                setPropertiesAndRelationshipsCascadeForChild(session, trackedEntities, childElement, level, limitDepth);
            }
        } else if (entity instanceof Array) {
            Object[] childElements = (Object[]) entity;
            for (Object childElement : childElements) {
                setPropertiesAndRelationshipsCascadeForChild(session, trackedEntities, childElement, level, limitDepth);
            }
        } else if (entity instanceof Map) {
            Map<Object, Object> childMap = (Map<Object, Object>) entity;
            Set<Map.Entry<Object, Object>> childEntries = childMap.entrySet();
            for (Map.Entry entry : childEntries) {
                Object entryValue = entry.getValue();
                if (entryValue != null && !ReflectionUtils.isSimpleType(entryValue.getClass())) {
                    setPropertiesAndRelationshipsCascadeForChild(session, trackedEntities, entryValue, level, limitDepth);
                }
                Object entryKey = entry.getKey();
                if (entryKey != null && !ReflectionUtils.isSimpleType(entryKey.getClass())) {
                    setPropertiesAndRelationshipsCascadeForChild(session, trackedEntities, entryKey, level, limitDepth);
                }
            }
        } else {
            //TODO cannot detect 2 instance with same entity
            if (trackedEntities.contains(entity)) {
                return;
            }
            trackedEntities.add(entity);

            //Handle children node first
            List<PropertyDescriptor> cascadeChildren = tnmk.common.util.ReflectionUtils.findPropertyDescriptorsByAnnotationType(entity.getClass(), CascadeRelationship.class);
            int childLevel = level + 1;
            for (PropertyDescriptor cascadeChild : cascadeChildren) {
                LOGGER.info("Entity: {}. Child: {}. ChildLevel: {}", entity, cascadeChild.getName(), childLevel);
                Object childValue = ReflectionUtils.readProperty(entity, cascadeChild);
                if (childValue != null) {
                    if (!trackedEntities.contains(childValue)) {
                        setPropertiesAndRelationshipsCascadeForChild(session, trackedEntities, childValue, childLevel, limitDepth);
                    }
                }
            }
            //Handle itself later
            OgmRepositoryHandler childOmgHandler = OgmRepositoryHandlerFactory.getOgmRepositoryHandler(entity.getClass());
            childOmgHandler.setPropertiesAndRelationships(session, entity);
        }

    }

    private void setPropertiesAndRelationshipsCascadeForChild(Session session, List<Object> trackedEntities, Object childValue, int childLevel, int limitDepth) {
        OgmRepositoryHandler childOmgHandler = OgmRepositoryHandlerFactory.getOgmRepositoryHandler(childValue.getClass());
        childOmgHandler.setPropertiesAndRelationshipsCascade(session, trackedEntities, childValue, childLevel, limitDepth);
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
