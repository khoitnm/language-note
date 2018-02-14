package org.tnmk.ln.app.topic.entity;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import org.tnmk.ln.app.common.entity.Cleanable;
import org.tnmk.ln.app.common.entity.Possession;
import org.tnmk.ln.infrastructure.data.neo4j.annotation.DetailLoading;
import org.tnmk.ln.app.common.entity.BaseNeo4jEntity;
import org.tnmk.ln.app.dictionary.entity.Locale;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import java.util.List;
import java.util.Set;

/**
 * @author khoi.tran on 2/25/17.
 */
@NodeEntity(label = "Topic")
public class Topic extends BaseNeo4jEntity implements Possession, Cleanable {
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

    private List<String> expressionIds;

    @DetailLoading
    @Relationship(type = RELATE_TO_CATEGORY, direction = Relationship.OUTGOING)
    private Set<Category> categories;

    @Relationship(type = OWN_TOPIC, direction = Relationship.INCOMING)
    private User owner;

    public Locale getLocaleOrDefault() {
        Locale locale = this.getLocale();
        if (locale == null) {
            locale = Locale.DEFAULT;
        }
        return locale;
    }

    @Override
    public String toString() {
        return String.format("Topic{%s, %s}", super.getId(), title);
    }

    @Transient
    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(this.title);
    }

    @Override
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public List<String> getExpressionIds() {
        return expressionIds;
    }

    public void setExpressionIds(List<String> expressionIds) {
        this.expressionIds = expressionIds;
    }
}
