package tnmk.ln.app.note.entity;

import tnmk.ln.app.common.entity.BaseEntity;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.infrastructure.security.entity.User;

import java.util.Set;

/**
 * @author khoi.tran on 2/25/17.
 */
//@NodeEntity
public class Note extends BaseEntity {
    private User owner;
    private Set<Expression> expressions;
    private Set<Topic> topics;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Expression> getExpressions() {
        return expressions;
    }

    public void setExpressions(Set<Expression> expressions) {
        this.expressions = expressions;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }
}
