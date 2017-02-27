//package tnmk.ln.test.neo4j;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import tnmk.common.util.ObjectMapperUtil;
//import tnmk.ln.app.dictionary.ExpressionRepository;
//import tnmk.ln.app.dictionary.entity.Expression;
//import tnmk.ln.test.BaseTest;
//
///**
// * @author khoi.tran on 2/26/17.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//public class ExpressionTest extends BaseTest {
//    public static Logger LOGGER = LoggerFactory.getLogger(ExpressionTest.class);
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
//}
