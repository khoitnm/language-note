package tnmk.ln.app.vocabulary.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tnmk.ln.app.common.entity.UriPrefixConstants;
import tnmk.ln.app.vocabulary.entity.Topic;
import tnmk.ln.app.vocabulary.service.TopicService;

import java.util.List;

@RestController
public class TopicResource {

    @Autowired
    private TopicService topicService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics/initiation", method = RequestMethod.GET)
    public Topic init() {
        return new Topic();
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/topics", method = RequestMethod.GET)
    public List<Topic> findAll() {
        return topicService.findAll();
    }
}