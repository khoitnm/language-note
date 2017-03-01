package tnmk.common.infrastructure.data.neo4j.repository;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @param <E> the type of entity. It must have an id property (with annotation {@link GraphId}).
 * @author khoi.tran on 2/28/17.
 */
public class OgmRepository<E> {
    public static final Logger LOGGER = LoggerFactory.getLogger(OgmRepository.class);

    @Autowired
    protected Session session;

    private final Class<E> entityClass;

    private OgmRepositoryHandler ogmRepositoryHandler;

    public OgmRepository() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        entityClass = (Class<E>) types[0];
        ogmRepositoryHandler = OgmRepositoryHandlerFactory.getOgmRepositoryHandler(entityClass);
    }

    @Transactional
    public void setAllPropertiesAndRelationships(E entity) {
        ogmRepositoryHandler.setPropertiesAndRelationships(session, entity);
    }

    /**
     * @param entity
     * @param relationshipTypes the name of relationships, not the name of properties
     */
    @Transactional
    public void setPropertiesAndRelationshipsByTypes(E entity, String... relationshipTypes) {
        ogmRepositoryHandler.setPropertiesAndRelationshipsByTypes(session, entity, relationshipTypes);
    }

    @Transactional
    public void setPropertiesAndRelationshipsExcludeIncoming(E entity) {
        ogmRepositoryHandler.setPropertiesAndRelationshipsByDirection(session, entity, RelationshipDirection.OUTGOING, RelationshipDirection.UNDIRECTED);
    }

    @Transactional
    public void removeAllRelationships(E entity) {
        ogmRepositoryHandler.removeAllRelationships(session, entity);
    }

    @Transactional
    public void removeRelationships(E entity, String... relationships) {
        ogmRepositoryHandler.removeRelationshipsByTypes(session, entity, relationships);
    }

    @Transactional
    public void setPropertiesAndRelationshipsCascade(E entity) {
        ogmRepositoryHandler.setPropertiesAndRelationshipsCascade(session, entity);
    }

    @Transactional
    public void setPropertiesAndRelationshipsCascade(E entity, int limitDepth) {
        ogmRepositoryHandler.setPropertiesAndRelationshipsCascade(session, entity, limitDepth);
    }
}
