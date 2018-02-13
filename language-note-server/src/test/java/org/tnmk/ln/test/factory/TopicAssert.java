package org.tnmk.ln.test.factory;

import org.junit.Assert;
import org.tnmk.ln.app.topic.entity.Category;
import org.tnmk.ln.app.topic.entity.Topic;
import org.tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.Optional;
import java.util.Set;

/**
 * @author khoi.tran on 3/6/17.
 */
public class TopicAssert {
    public static void assertExistCategory(Topic topic, User owner, int numCategory) {
        Set<Category> categorySet = topic.getCategories();
        Assert.assertEquals(numCategory, categorySet.size());
        for (Category category : categorySet) {
            Assert.assertNotNull(category.getId());
            Assert.assertNotNull(category.getText());
            Assert.assertNotNull(category.getCreatedDateTime());
//            Assert.assertEquals(owner, category.getOwner());
        }
    }

//    public static void assertExpressions(Topic topic, User owner, int numExpressions) {
//        Set<Expression> expressions = topic.getExpressions();
//        Assert.assertNotNull(expressions);
//        Assert.assertEquals(numExpressions, expressions.size());
//        for (Expression expression : expressions) {
//            Assert.assertNotNull(expression.getText());
//            List<SenseGroup> senseGroupList = expression.getSenseGroups();
//            Assert.assertTrue(senseGroupList.size() > 0);
//            for (SenseGroup senseGroup : senseGroupList) {
//                Assert.assertNotNull(senseGroup.getLexicalType());
//                Assert.assertTrue(senseGroup.getSenses().size() > 0);
//                for (Sense sense : senseGroup.getSenses()) {
//                    Assert.assertTrue(StringUtils.isNotBlank(sense.getExplanation()));
//                    Assert.assertTrue(sense.getExamples().size() > 0);
//                    for (Example example : sense.getExamples()) {
//                        Assert.assertTrue(StringUtils.isNotBlank(example.getText()));
//                    }
//                }
//            }
//        }
//    }

    public static void assertExistCategory(Topic topic, String categoryText, boolean exist) {
        Set<Category> categorys = topic.getCategories();
        Optional<Category> categoryOptional = categorys.stream().filter(category -> category.getText().equals(categoryText)).findAny();
        Assert.assertEquals(exist, categoryOptional.isPresent());
    }
}