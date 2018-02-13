package org.tnmk.ln.infrastructure.data.neo4j.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author khoi.tran on 2/28/17.
 */
public class OgmRepositoryHandlerFactory {
    public static final Logger LOGGER = LoggerFactory.getLogger(OgmRepositoryHandlerFactory.class);
    private static final Map<Class<?>, OgmRepositoryHandler> OGM_REPOSITORY_HANDLER_MAP = Collections.synchronizedMap(new HashMap<>());

    public static OgmRepositoryHandler getOgmRepositoryHandler(Class<?> entityClass) {
        OgmRepositoryHandler result = OGM_REPOSITORY_HANDLER_MAP.get(entityClass);
        if (result == null) {
            result = new OgmRepositoryHandler(entityClass);
            OGM_REPOSITORY_HANDLER_MAP.putIfAbsent(entityClass, result);
        }
        return result;
    }

}
