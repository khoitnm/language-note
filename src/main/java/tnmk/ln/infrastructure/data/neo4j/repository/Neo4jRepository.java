package tnmk.ln.infrastructure.data.neo4j.repository;

import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.util.IterableUtil;
import tnmk.common.util.ReflectionUtils;
import tnmk.ln.infrastructure.data.neo4j.Neo4jUtils;
import tnmk.ln.infrastructure.data.neo4j.annotation.DetailLoading;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public <T> T queryOneDetail(Class<T> resultClass, Long id) {
        DetailLoadingRelationship detailLoadingRelationship = getRelationshipTypesWithDetailLoadingMultiLevels(resultClass);
        String relTypes = detailLoadingRelationship.getRelationshipTypes().stream().collect(Collectors.joining(" | :"));
        int relDepth = detailLoadingRelationship.getDepth();
        String sb = String.join("",
                "MATCH (n) WHERE ID(n) = {p0} WITH n MATCH p=(n)-[:", relTypes, "*0..", "" + relDepth, "]-(m) RETURN p"
        );
        Iterable<T> iterable = session.query(resultClass, sb, constructParams(id));
        List<T> results = IterableUtil.toList(iterable);
        return results.get(0);
//        int i = 0;
//        for (T o : iterable) {
//            LOGGER.info(i + ": " + ObjectMapperUtil.toStringMultiLine(o));
//            i++;
//        }
//        return queryForObject(resultClass, sb, id);
    }

    public static DetailLoadingRelationship getRelationshipTypesWithDetailLoadingMultiLevels(Class<?> entityClass) {
        DetailLoadingRelationship result = new DetailLoadingRelationship();
        result.setRelationshipTypes(new HashSet<>());
        int currentDepth = 0;
        int depth = getRelationshipTypesWithDetailLoadingMultiLevels(result, Arrays.asList(entityClass), currentDepth, entityClass);
        result.setDepth(depth);
        return result;
    }

    private static int getRelationshipTypesWithDetailLoadingMultiLevels(DetailLoadingRelationship result, List<Class<?>> parentClasses, int parentDepth, Field field) {
        int depth = parentDepth + 1;
        List<Class<?>> childClasses;
        Class<?> fieldType = field.getType();
        if (ReflectionUtils.isWrapperClass(fieldType)) {
            childClasses = ReflectionUtils.getParameterClasses(field);
        } else {
            childClasses = Arrays.asList(fieldType);
        }
        result.getRelationshipTypes().add(Neo4jUtils.findRelationshipType(field));
        for (Class<?> childClass : childClasses) {
            if (parentClasses.contains(childClass)) continue;//This line is very important, it will prevent infinite loop.
            List<Class<?>> parentClassesOfThisChild = new ArrayList<>();
            parentClassesOfThisChild.addAll(parentClasses);
            int idepth = getRelationshipTypesWithDetailLoadingMultiLevels(result, parentClassesOfThisChild, parentDepth + 1, childClass);
            depth = Math.max(idepth, depth);
        }
        return depth;
    }

    private static int getRelationshipTypesWithDetailLoadingMultiLevels(DetailLoadingRelationship result, List<Class<?>> parentClasses, int currentDepth, Class<?> fieldClass) {
        int depth = currentDepth;
        List<Field> relationshipFields = Neo4jUtils.findRelationshipFields(fieldClass, field -> field.getAnnotationsByType(DetailLoading.class) != null);
        for (Field relationshipField : relationshipFields) {
            List<Class<?>> traveledClasses = new ArrayList<>();
            traveledClasses.addAll(parentClasses);
            traveledClasses.add(fieldClass);
            int idepth = getRelationshipTypesWithDetailLoadingMultiLevels(result, traveledClasses, currentDepth, relationshipField);
            depth = Math.max(depth, idepth);
        }
        return depth;
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
