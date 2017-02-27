package tnmk.ln.test.neo4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tnmk.ln.app.dictionary.SimpleRepository;
import tnmk.ln.app.dictionary.entity.Simple;
import tnmk.ln.test.BaseTest;

/**
 * @author khoi.tran on 2/26/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleTest extends BaseTest {
    public static Logger LOGGER = LoggerFactory.getLogger(SimpleTest.class);
//    @Autowired
//    private ExpressionRepository expressionRepository;
//
//    @Test
//    public void test() {
//        Expression expression = new Expression();
//        expression.setText("test_" + System.currentTimeMillis());
//        expression = expressionRepository.save(expression);
//        LOGGER.info(ObjectMapperUtil.toStringMultiLine(expression));
//    }

    @Autowired
    SimpleRepository simpleRepository;

    @Test
    public void testcreate() {
        Simple simple = new Simple();
        simple.setText("tex");
        simpleRepository.save(simple);
    }
}
