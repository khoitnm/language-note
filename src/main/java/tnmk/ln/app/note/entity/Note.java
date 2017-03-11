package tnmk.ln.app.note.entity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.dictionary.entity.Locale;
import tnmk.ln.infrastructure.data.neo4j.annotation.DetailLoading;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.Set;

/**
 * @author khoi.tran on 2/25/17.
 */
@NodeEntity(label = "Note")
public class Note extends BaseNeo4jEntity {
    public static final String HAS_EXPRESSION = "HAS_EXPRESSION";
    public static final String RELATE_TO_TOPIC = "RELATE_TO_TOPIC";
    public static final String NOTE_IN_LOCALE = "NOTE_IN_LOCALE";

    /**
     * Don't name the relationship as "HAS_NOTE", I would like to distinguish it with other composition relationships (HAS_A, HAS_B...)
     */
    public static final String OWN_NOTE = "OWN_NOTE";
    public static final String TITLE_DEFAULT = "untitled";

    private String title = TITLE_DEFAULT;
    @DetailLoading
    @Relationship(type = NOTE_IN_LOCALE, direction = Relationship.OUTGOING)
    private Locale locale;
    @DetailLoading
    @Relationship(type = HAS_EXPRESSION, direction = Relationship.OUTGOING)
    private Set<Expression> expressions;

    @DetailLoading
    @Relationship(type = RELATE_TO_TOPIC, direction = Relationship.OUTGOING)
    private Set<Topic> topics;

    @Relationship(type = OWN_NOTE, direction = Relationship.INCOMING)
    private User owner;

    public Locale getLocaleOrDefault() {
        Locale locale = this.getLocale();
        if (locale == null) {
            locale = tnmk.ln.app.dictionary.entity.Locale.DEFAULT;
        }
        return locale;
    }

    @Override
    public String toString() {
        return String.format("Note{%s, %s}", super.getId(), title);
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
