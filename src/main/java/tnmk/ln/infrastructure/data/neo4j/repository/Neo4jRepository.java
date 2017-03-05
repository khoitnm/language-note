package tnmk.ln.infrastructure.data.neo4j.repository;

import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author khoi.tran on 2/28/17.
 */
@Component
public class Neo4jRepository {
    public static final Logger LOGGER = LoggerFactory.getLogger(Neo4jRepository.class);

    @Autowired
    protected Session session;

    @Transactional
    public void removeRelationship(Long idA, Long idB, String relationshipType, RelationshipDirection relationshipDirection) {
        String queryString = String.join("",
                "MATCH (a)-[rel:", relationshipType, "]-(b) WHERE id(a)={idA} and id(b)={idB} DELETE rel");
        LOGGER.debug(queryString);
        Map<String, Object> params = new HashMap<>();
        params.put("idA", idA);
        params.put("idB", idB);
        Result result = session.query(queryString, params);
        result.queryStatistics().getRelationshipsDeleted();
    }

    public Long count(String queryString, Object... paramValues) {
        Map<String, Object> params = constructParams(paramValues);
        Long count = session.queryForObject(Long.class, queryString, params);
        if (count == null) {
            count = 0l;
        }
        return count;
    }

    public <T> T queryForObject(Class<T> resultClass, String queryString, Object... paramValues) {
        Map<String, Object> params = constructParams(paramValues);
        return session.queryForObject(resultClass, queryString, params);
    }

    private Map<String, Object> constructParams(Object... paramValues) {
        Map<String, Object> params = new HashMap<>();
        int i = 0;
        for (Object paramValue : paramValues) {
            params.put("p" + i, paramValue);
            i++;
        }
        return params;
    }
}
