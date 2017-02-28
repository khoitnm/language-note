package tnmk.ln.app.social.entity;

import org.neo4j.ogm.annotation.NodeEntity;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.app.user.entity.Account;

import java.util.List;

/**
 * @author khoi.tran on 2/28/17.
 */
@NodeEntity
public class Comment extends BaseNeo4jEntity {
    private Account owner;
    private String comment;
    private List<Comment> replies;

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }
}
