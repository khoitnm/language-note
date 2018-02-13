package tnmk.ln.app.practice.entity.question;

//import org.neo4j.ogm.annotation.NodeEntity; import tnmk.ln.app.common.entity.BaseEntity;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Transient;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.app.common.entity.Cleanable;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.List;

/**
 * @author khoi.tran on 2/26/17.
 */
@NodeEntity
public class QuestionSet extends BaseNeo4jEntity implements Cleanable {
    private User owner;
    private String topicAsHtml;
    private List<Question> questions;

    @Transient
    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(this.topicAsHtml);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTopicAsHtml() {
        return topicAsHtml;
    }

    public void setTopicAsHtml(String topicAsHtml) {
        this.topicAsHtml = topicAsHtml;
    }
}
