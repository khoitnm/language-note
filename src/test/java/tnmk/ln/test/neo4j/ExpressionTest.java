package tnmk.ln.test.neo4j;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.util.ObjectMapperUtil;
import tnmk.ln.app.dictionary.ExpressionRepository;
import tnmk.ln.app.dictionary.ExpressionService;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.user.entity.Account;
import tnmk.ln.app.user.entity.Contributor;
import tnmk.ln.test.BaseTest;

import java.util.Arrays;

/**
 * @author khoi.tran on 2/26/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@EnableTransactionManagement
/**
 * http://stackoverflow.com/questions/24829823/interrogation-about-springjunit4classrunners-default-rollback-behavior-for-inte
 * If we don't use this, the Testing context will rollback the transaction.
 */
@Transactional(propagation = Propagation.SUPPORTS)
public class ExpressionTest extends BaseTest {
    public static Logger LOGGER = LoggerFactory.getLogger(ExpressionTest.class);

    @Autowired
    Session session;

    @Autowired
    private ExpressionRepository expressionRepository;

    @Autowired
    private ExpressionService expressionService;

    public static final long MAIN_EXPRESSION_ID = 221l;

    @After
    public void finish() {
//        session.purgeDatabase();
    }

    @Test
    public void testCreate() {
        Account account = new Contributor();
        account.setUsername("test01");
        Expression expression = new Expression();
        expression.setOwner(account);
        expression.setText("test_" + System.currentTimeMillis());
        expression = expressionService.createExpression(account, expression);
        LOGGER.info(ObjectMapperUtil.toStringMultiLine(expression));
    }

    @Test
    public void testUdate() {
        Expression antonym1 = new Expression();
        antonym1.setText("antonym1");

        Expression synonym1 = expressionService.findById(223);
        synonym1.setText("synonym01");
        Expression synonym2 = expressionService.findById(224);
        synonym2.setAntonyms(Arrays.asList(antonym1));

        Expression expression = expressionService.findById(MAIN_EXPRESSION_ID);
        expression.setText("main expression 2");
        expression.setSynonyms(Arrays.asList(synonym1, synonym2));
        expression.setOwner(null);
//        expressionService.detachExpressionDefinition(expression);
        expressionService.updateExpressionDefinition(expression);
//        expressionService.save(expression);

        expression = expressionService.findById(MAIN_EXPRESSION_ID);
        LOGGER.info(ObjectMapperUtil.toStringMultiLine(expression));
    }

    @Test
    public void select() {
        Expression expression = expressionService.findById(MAIN_EXPRESSION_ID);
        LOGGER.info(ObjectMapperUtil.toStringMultiLine(expression));
    }

    @Test
    public void remove_synonyms() {
        Expression expression = expressionService.findById(MAIN_EXPRESSION_ID);
        expression.setSynonyms(null);
        expression.setOwner(null);
        expressionService.updateExpressionDefinition(expression);

        expression = expressionService.findById(MAIN_EXPRESSION_ID);
        LOGGER.info(ObjectMapperUtil.toStringMultiLine(expression));
    }

    @Test
    public void deleteById() {
        expressionService.deleteById(213l);
    }
}
