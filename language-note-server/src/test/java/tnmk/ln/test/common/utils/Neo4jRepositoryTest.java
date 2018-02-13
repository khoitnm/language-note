package tnmk.ln.test.common.utils;

import org.junit.Test;
import tnmk.common.util.ObjectMapperUtil;
import tnmk.ln.app.topic.entity.Topic;
import tnmk.ln.infrastructure.data.neo4j.repository.DetailLoadingRelationship;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;
import tnmk.ln.test.PureTest;

/**
 * @author khoi.tran on 3/8/17.
 */
public class Neo4jRepositoryTest extends PureTest {
    @Test
    public void test() {
        DetailLoadingRelationship result = Neo4jRepository.getRelationshipTypesWithDetailLoadingMultiLevels(Topic.class);
        LOGGER.info(ObjectMapperUtil.toStringMultiLine(result));
    }
}
