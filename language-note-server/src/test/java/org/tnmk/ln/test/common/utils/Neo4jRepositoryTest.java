package org.tnmk.ln.test.common.utils;

import org.junit.Test;
import org.tnmk.common.utils.ToStringUtils;
import org.tnmk.ln.app.topic.entity.Topic;
import org.tnmk.ln.infrastructure.data.neo4j.repository.DetailLoadingRelationship;
import org.tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;
import org.tnmk.ln.test.UnitBaseTest;

/**
 * @author khoi.tran on 3/8/17.
 */
public class Neo4jRepositoryTest extends UnitBaseTest {
    @Test
    public void test() {
        DetailLoadingRelationship result = Neo4jRepository.getRelationshipTypesWithDetailLoadingMultiLevels(Topic.class);
        LOGGER.info(ToStringUtils.toStringMultiLine(result));
    }
}
