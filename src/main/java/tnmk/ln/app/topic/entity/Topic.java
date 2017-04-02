package tnmk.ln.app.topic.entity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.app.common.entity.Possession;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.dictionary.entity.Locale;
import tnmk.ln.infrastructure.data.neo4j.annotation.DetailLoading;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.Set;

/**
 * @author khoi.tran on 2/25/17.
 */
@NodeEntity(label = "Topic")
public class Topic extends BaseNeo4jEntity implements Possession {
    public static final String HAS_EXPRESSION = "HAS_EXPRESSION";
    public static final String RELATE_TO_CATEGORY = "RELATE_TO_CATEGORY";
    public static final String TOPIC_IN_LOCALE = "TOPIC_IN_LOCALE";

    /**
     * Don't name the relationship as "HAS_TOPIC", I would like to distinguish it with other composition relationships (HAS_A, HAS_B...)
     */
    public static final String OWN_TOPIC = "OWN_TOPIC";
    public static final String TITLE_DEFAULT = "untitled";

    private String title = TITLE_DEFAULT;
    private String noteAsHtml;

    @DetailLoading
    @Relationship(type = TOPIC_IN_LOCALE, direction = Relationship.OUTGOING)
    private Locale locale;
    @DetailLoading
    @Relationship(type = HAS_EXPRESSION, direction = Relationship.OUTGOING)
    private Set<Expression> expressions;

    @DetailLoading
    @Relationship(type = RELATE_TO_CATEGORY, direction = Relationship.OUTGOING)
    private Set<Category> categories;

    @Relationship(type = OWN_TOPIC, direction = Relationship.INCOMING)
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
        return String.format("Topic{%s, %s}", super.getId(), title);
    }

    @Override
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
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

    public String getNoteAsHtml() {
        return noteAsHtml;
    }

    public void setNoteAsHtml(String noteAsHtml) {
        this.noteAsHtml = noteAsHtml;
    }
}
