package org.tnmk.ln.test.common.utils;

import org.junit.Test;
import org.tnmk.common.utils.json.JsonUtils;
import org.tnmk.ln.app.topic.entity.Topic;
import org.tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;
import org.tnmk.ln.test.PureTest;
import org.tnmk.ln.infrastructure.data.neo4j.repository.DetailLoadingRelationship;

/**
 * @author khoi.tran on 3/8/17.
 */
public class Neo4jRepositoryTest extends PureTest {
    @Test
    public void test() {
        DetailLoadingRelationship result = Neo4jRepository.getRelationshipTypesWithDetailLoadingMultiLevels(Topic.class);
        LOGGER.info(JsonUtils.toStringMultiLine(result));
    }
}
