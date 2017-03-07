package tnmk.ln.test.factory;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import tnmk.ln.app.dictionary.entity.Example;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.dictionary.entity.Sense;
import tnmk.ln.app.dictionary.entity.SenseGroup;
import tnmk.ln.app.note.entity.Note;
import tnmk.ln.app.note.entity.Topic;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author khoi.tran on 3/6/17.
 */
public class NoteAssert {
    public static void assertExistTopic(Note note, User owner, int numTopic) {
        Set<Topic> topicSet = note.getTopics();
        Assert.assertEquals(numTopic, topicSet.size());
        for (Topic topic : topicSet) {
            Assert.assertNotNull(topic.getId());
            Assert.assertNotNull(topic.getText());
            Assert.assertNotNull(topic.getCreatedDateTime());
//            Assert.assertEquals(owner, topic.getOwner());
        }
    }

    public static void assertExpressions(Note note, User owner, int numExpressions) {
        Set<Expression> expressions = note.getExpressions();
        Assert.assertEquals(numExpressions, expressions.size());
        for (Expression expression : expressions) {
//            Assert.assertEquals(owner, expression.getOwner());
            Assert.assertNotNull(expression.getText());
            List<SenseGroup> senseGroupList = expression.getSensesGroups();
            Assert.assertTrue(senseGroupList.size() > 0);
            for (SenseGroup senseGroup : senseGroupList) {
                Assert.assertNotNull(senseGroup.getLexicalType());
                Assert.assertTrue(senseGroup.getSenses().size() > 0);
                for (Sense sense : senseGroup.getSenses()) {
                    Assert.assertTrue(StringUtils.isNotBlank(sense.getExplanation()));
                    Assert.assertTrue(sense.getExamples().size() > 0);
                    for (Example example : sense.getExamples()) {
                        Assert.assertTrue(StringUtils.isNotBlank(example.getText()));
                    }
                }
            }
        }
    }

    public static void assertExistTopic(Note note, String topicText, boolean exist) {
        Set<Topic> topics = note.getTopics();
        Optional<Topic> topicOptional = topics.stream().filter(topic -> topic.getText().equals(topicText)).findAny();
        Assert.assertEquals(exist, topicOptional.isPresent());
    }
}