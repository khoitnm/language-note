package tnmk.ln.test.neo4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.datastructure.NodeWithProps;
import tnmk.ln.app.datastructure.NodeWithPropsRepositories;
import tnmk.ln.test.BaseTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author khoi.tran on 2/26/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class NodeWithPropsTest extends BaseTest {
    public static Logger LOGGER = LoggerFactory.getLogger(NodeWithPropsTest.class);

    @Autowired
    Session session;

    @Autowired
    private NodeWithPropsRepositories nodeWithPropsRepositories;

    @Test
    public void test() {
        NodeWithProps nodeWithProps = new NodeWithProps();
        List<Float> points = new ArrayList<>();
        points.add(0.5f);
        points.add(1.333f);
        nodeWithProps.setPoints(points);
        nodeWithPropsRepositories.save(nodeWithProps);

        Iterable<NodeWithProps> nodeWithPropses = nodeWithPropsRepositories.findAll();
        LOGGER.debug("" + nodeWithPropses);
    }
}
