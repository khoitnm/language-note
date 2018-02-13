package tnmk.ln.app.vocabulary.entity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import tnmk.ln.app.common.entity.BaseMongoEntity;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 1/25/17.
 */
@Document(collection = "ExpressionItem")
public class ExpressionItem extends BaseMongoEntity {
    /**
     * full expression
     */
    @Indexed(unique = true)
    @NotBlank
    private String expression;
    /**
     * If the expression is a phrasal verb, then this value is not null. Otherwise, this value is null.
     */
    private PhrasalVerb phrasalVerbDetail;

    private ExpressionType type;

    @NotNull
    private UserPoints userPoints;

    //REFERENCES ////////////////////////////////////
    @NotEmpty
    private List<Meaning> meanings;
    private Set<String> bookIds;
    private Set<String> lessonIds;
    private Set<String> topicIds;

    //RELATION WORDS ////////////////////////////////
    private Set<String> synonymExpressionIds;

    private Set<String> oppositeExpressionIds;

    private Set<String> irregularVerbs;

    @Indexed
    private Set<String> pluralNouns;

    public ExpressionItem() {
        //Every expressionItem must have at least one meaning.
        meanings = new ArrayList<>();
        meanings.add(new Meaning());
        userPoints = new UserPoints();
    }

    public void addLessonId(String lessonId) {
        if (this.lessonIds == null) {
            lessonIds = new HashSet<>();
        }
        if (StringUtils.isNotBlank(lessonId)) {
            lessonIds.add(lessonId);
        }
    }

    public void addBookId(String bookId) {
        if (this.bookIds == null) {
            bookIds = new HashSet<>();
        }
        if (StringUtils.isNotBlank(bookId)) {
            bookIds.add(bookId);
        }
    }

    public void addTopicId(String topicId) {
        if (this.topicIds == null) {
            this.topicIds = new HashSet<>();
        }
        if (StringUtils.isNotBlank(topicId)) {
            this.topicIds.add(topicId);
        }
    }

    public void addTopicIds(Set<String> topicIds) {
        if (this.topicIds == null) {
            this.topicIds = new HashSet<>();
        }
        Set<String> notBlankTopicIds = topicIds.stream().filter(topicId -> StringUtils.isNotBlank(topicId)).collect(Collectors.toSet());
        this.topicIds.addAll(notBlankTopicIds);
    }

    public String toString() {
        return this.expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }

    public ExpressionType getType() {
        return type;
    }

    public void setType(ExpressionType type) {
        this.type = type;
    }

    public Set<String> getBookIds() {
        return bookIds;
    }

    public void setBookIds(Set<String> bookIds) {
        this.bookIds = bookIds;
    }

    public Set<String> getLessonIds() {
        return lessonIds;
    }

    public void setLessonIds(Set<String> lessonIds) {
        this.lessonIds = lessonIds;
    }

    public Set<String> getTopicIds() {
        return topicIds;
    }

    public void setTopicIds(Set<String> topicIds) {
        this.topicIds = topicIds;
    }

    public Set<String> getSynonymExpressionIds() {
        return synonymExpressionIds;
    }

    public void setSynonymExpressionIds(Set<String> synonymExpressionIds) {
        this.synonymExpressionIds = synonymExpressionIds;
    }

    public Set<String> getOppositeExpressionIds() {
        return oppositeExpressionIds;
    }

    public void setOppositeExpressionIds(Set<String> oppositeExpressionIds) {
        this.oppositeExpressionIds = oppositeExpressionIds;
    }

    public Set<String> getIrregularVerbs() {
        return irregularVerbs;
    }

    public void setIrregularVerbs(Set<String> irregularVerbs) {
        this.irregularVerbs = irregularVerbs;
    }

    public Set<String> getPluralNouns() {
        return pluralNouns;
    }

    public void setPluralNouns(Set<String> pluralNouns) {
        this.pluralNouns = pluralNouns;
    }

    public UserPoints getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(UserPoints userPoints) {
        this.userPoints = userPoints;
    }

    public PhrasalVerb getPhrasalVerbDetail() {
        return phrasalVerbDetail;
    }

    public void setPhrasalVerbDetail(PhrasalVerb phrasalVerbDetail) {
        this.phrasalVerbDetail = phrasalVerbDetail;
    }

}
