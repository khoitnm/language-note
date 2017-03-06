package tnmk.ln.test.expression;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.note.NoteService;
import tnmk.ln.app.note.TopicAndOwnerRepository;
import tnmk.ln.app.note.TopicRepository;
import tnmk.ln.app.note.TopicService;
import tnmk.ln.app.note.entity.Topic;
import tnmk.ln.infrastructure.security.neo4j.entity.User;
import tnmk.ln.test.BaseTest;
import tnmk.ln.test.factory.UserTestFactory;

/**
 * @author khoi.tran on 3/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class TopicServiceTest extends BaseTest {
    @Autowired
    NoteService noteService;

    @Autowired
    UserTestFactory userTestFactory;

    @Autowired
    TopicService topicService;
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    TopicAndOwnerRepository topicCountRepository;
    private User defaultUser;

    @Before
    public void initUser() {
        defaultUser = userTestFactory.initDefaultUser();
    }

    @Test
    public void saveTopicIfNotExist() {
        String text = "topic_test";
        User owner = defaultUser;

        Topic topic = new Topic();
        topic.setText(text);
        topic.setOwner(defaultUser);
        Topic topicSaved01 = topicService.saveIfNecessaryByTextAndOwner(defaultUser, topic);
        long topicsCountBefore = topicCountRepository.countByTextAndOwner(text, owner.getId());

        topic.setId(null);
        topic.setCreatedDateTime(null);
        Topic topicSaved02 = topicService.saveIfNecessaryByTextAndOwner(defaultUser, topic);

        long topicsCountAfter = topicCountRepository.countByTextAndOwner(text, owner.getId());
        LOGGER.info("Count: {} -> {}", topicsCountBefore, topicsCountAfter);

        Assert.assertEquals(topicSaved01.getId(), topicSaved02.getId());
        Assert.assertEquals(topicsCountBefore, topicsCountAfter);
    }

}
