package tnmk.ln.app.practice.entity.question;

//import org.neo4j.ogm.annotation.NodeEntity; import tnmk.ln.app.common.entity.BaseEntity;

import org.neo4j.ogm.annotation.NodeEntity;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.Set;

/**
 * @author khoi.tran on 2/26/17.
 */
@NodeEntity
public class QuestionSet extends BaseNeo4jEntity {
    private User owner;
    private String topicAsHtml;
    private Set<Question> questions;

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
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
