package tnmk.ln.app.vocabulary.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import tnmk.ln.app.common.entity.BaseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author khoi.tran on 1/25/17.
 */
@Document(collection = "Lesson")
public class Lesson extends BaseEntity {
    @Indexed
    @NotBlank
    private String name;

    private String noteAsHtml;
    /**
     * This is only the default expressionType, each expression in this lesson might has different types.
     */
    private ExpressionType defaultExpressionType = ExpressionType.WORD;
    @Indexed
    @DBRef
    private Set<Topic> topics = new HashSet<>();

    @NotEmpty
    @Indexed
    @DBRef
    private List<ExpressionItem> expressionItems;

    @Indexed
    private String bookId;

    public Lesson() {
        //This list must contains at least one item.
        expressionItems = new ArrayList<>();
        expressionItems.add(new ExpressionItem());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExpressionItem> getExpressionItems() {
        return expressionItems;
    }

    public void setExpressionItems(List<ExpressionItem> expressionItems) {
        this.expressionItems = expressionItems;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    public String getNoteAsHtml() {
        return noteAsHtml;
    }

    public void setNoteAsHtml(String noteAsHtml) {
        this.noteAsHtml = noteAsHtml;
    }

    public ExpressionType getDefaultExpressionType() {
        return defaultExpressionType;
    }

    public void setDefaultExpressionType(ExpressionType defaultExpressionType) {
        this.defaultExpressionType = defaultExpressionType;
    }
}
