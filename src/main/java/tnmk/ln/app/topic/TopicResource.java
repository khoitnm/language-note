package tnmk.ln.app.topic;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tnmk.ln.app.common.entity.UriPrefixConstants;
import tnmk.ln.app.topic.entity.Topic;

/**
 * @author khoi.tran on 3/4/17.
 */
@RestController
public class TopicResource {
    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/categorys/construct", method = RequestMethod.GET)
    public Topic construct() {
        return TopicFactory.constructSchema();
    }
}
