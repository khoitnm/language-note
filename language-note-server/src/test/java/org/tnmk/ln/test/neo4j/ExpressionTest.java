package org.tnmk.ln.test.neo4j;

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
import org.tnmk.common.utils.ToStringUtils;
import org.tnmk.ln.app.dictionary.ExpressionService;
import org.tnmk.ln.app.dictionary.entity.Example;
import org.tnmk.ln.app.dictionary.entity.Expression;
import org.tnmk.ln.app.dictionary.entity.LexicalType;
import org.tnmk.ln.app.dictionary.entity.Sense;
import org.tnmk.ln.app.dictionary.entity.SenseGroup;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.UserRepository;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.Contributor;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;
import org.tnmk.ln.test.BaseTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private UserRepository accountRepository;

    @Autowired
    private ExpressionService expressionService;

    public static final String MAIN_EXPRESSION_ID = "" + 221l;

    @After
    public void finish() {
//        session.purgeDatabase();
    }

    @Test
    public void testCreate() {
        User account = new Contributor();
        account.setUsername("test01");
        Expression expression = new Expression();
        expression.setOwner(account);
        expression.setText("test_" + System.currentTimeMillis());
        expression = expressionService.createExpression(account, expression);
        LOGGER.info(ToStringUtils.toStringMultiLine(expression));
    }

    @Test
    public void testUdate() {
//        User account = accountRepository.findOne(220l);
//        account.setUsername("contributor1");
//
//        Expression antonym1 = expressionService.findById("" + 192);
//        antonym1.setText("antonym1");
//
//        Expression synonym2 = expressionService.findById("" + 224);
//        synonym2.setText("synonym2");
//        synonym2.setAntonyms(Arrays.asList(antonym1));
//        Expression synonym3 = expressionService.findById("" + 191);
//        synonym3.setText("synonym3");
//        synonym3.setSenseGroups(Arrays.asList(constructSensesGroup("" + 201l), constructSensesGroup("" + 197l)));
//
//        antonym1.setAntonyms(Arrays.asList(synonym3));
//
//        Expression expression = expressionService.findById(MAIN_EXPRESSION_ID);
//        expression.setText("main");
//        expression.setSynonyms(Arrays.asList(synonym2, synonym3));
//        expression.setAntonyms(Arrays.asList(antonym1));
//        expression.setOwner(account);
//        expressionService.save(expression);
//
//        expression = expressionService.findById(MAIN_EXPRESSION_ID);
//        LOGGER.info(ObjectMapperUtils.toStringMultiLine(expression));
    }

    private SenseGroup constructSensesGroup(String id) {
        SenseGroup sensesGroup = new SenseGroup();
        sensesGroup.setId(id);
        sensesGroup.setLexicalType(LexicalType.NOUN);
        List<Sense> senseSet = new ArrayList<>();
        senseSet.add(constructSense("" + 199l));
        senseSet.add(constructSense("" + 196l));
        sensesGroup.setSenses(senseSet);
        return sensesGroup;
    }

    private Sense constructSense(String id) {
        Sense sense = new Sense();
        sense.setId(id);
        sense.setExplanation("explanation2");
        sense.setExamples(Arrays.asList(constructExample("" + 198l)));
        return sense;
    }

    private Example constructExample(String id) {
        Example example = new Example();
        example.setId(id);
        example.setText("example2.1");
        return example;
    }

    @Test
    public void select() {
        Expression expression = expressionService.findById(MAIN_EXPRESSION_ID);
        LOGGER.info(ToStringUtils.toStringMultiLine(expression));
    }

//    @Test
//    public void remove_synonyms() {
//        Expression expression = expressionService.findById(MAIN_EXPRESSION_ID);
//        expression.setSynonyms(null);
//        expression.setOwner(null);
//        expressionService.updateExpressionDefinition(expression);
//
//        expression = expressionService.findById(MAIN_EXPRESSION_ID);
//        LOGGER.info(ObjectMapperUtils.toStringMultiLine(expression));
//    }

    @Test
    public void deleteById() {
        expressionService.deleteById("" + 213l);
    }
}
