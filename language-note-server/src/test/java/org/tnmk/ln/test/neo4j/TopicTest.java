package org.tnmk.ln.test.neo4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tnmk.common.utils.json.ObjectMapperUtils;
import org.tnmk.ln.app.topic.TopicDetailRepository;
import org.tnmk.ln.app.topic.TopicRepository;
import org.tnmk.ln.app.topic.entity.Topic;
import org.tnmk.ln.test.BaseTest;

/**
 * @author khoi.tran on 3/8/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TopicTest extends BaseTest {
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    TopicDetailRepository topicAndOwnerRepository;

    @Test
    public void test() {
        Topic topic = topicAndOwnerRepository.findOneDetailById(150l);
        LOGGER.info(ObjectMapperUtils.toJson(new ObjectMapper(), topic));
    }
}
