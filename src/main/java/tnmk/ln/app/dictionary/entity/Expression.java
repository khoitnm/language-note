package tnmk.ln.app.dictionary.entity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import tnmk.ln.app.common.entity.BaseMongoEntity;
import tnmk.ln.app.common.entity.Cleanable;
import tnmk.ln.app.common.entity.Possession;
import tnmk.ln.app.dictionary.LexicalEntryUtils;
import tnmk.ln.app.digitalasset.entity.DigitalAsset;
import tnmk.ln.infrastructure.data.neo4j.annotation.DetailLoading;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.List;

/**
 * @author khoi.tran on 2/18/17.
 */
//@NodeEntity(label = "Expression")
@Document(collection = "Expression")
public class Expression extends BaseMongoEntity implements Possession, Cleanable {
    public static final String HAS_LEXICAL_ENTRIES = "HAS_LEXICAL_ENTRIES";
    public static final String HAS_SENSE_GROUPS = "HAS_SENSE_GROUPS";
    public static final String HAS_MAIN_AUDIO = "HAS_MAIN_AUDIO";

    public static final String HAS_MAIN_IMAGE = "HAS_MAIN_IMAGE";

    public static final String HAS_IMAGES = "HAS_IMAGES";
    public static final String IS_SYNONYMOUS_WITH = "IS_SYNONYMOUS_WITH";
    public static final String IS_ANTONYMOUS_WITH = "IS_ANTONYMOUS_WITH";
    public static final String FAMILY_WITH = "FAMILY_WITH";
    public static final String OWN_EXPRESSION = "OWN_EXPRESSION";
    public static final String EXPRESSION_IN_LOCALE = "EXPRESSION_IN_LOCALE";

    /**
     * This field is not unique because many users can contribute differently to our system.
     */
    @Index
    @NotBlank
    private String text;
    private ExpressionType expressionType;

    private int favourite;

    // COMPOSITION RELATIONSHIPS //////////////////////////////////////////////////////////////
    @DetailLoading
    @Relationship(type = EXPRESSION_IN_LOCALE, direction = Relationship.OUTGOING)
    private Locale locale = Locale.DEFAULT;
    /**
     * The list of lexical entries which helps to form the expression's text.
     * If the lexicalEntries is not empty, the text will be created from lexicalEntries
     */
    @DetailLoading
    @Relationship(type = HAS_LEXICAL_ENTRIES, direction = Relationship.OUTGOING)
    private List<LexicalEntry> lexicalEntries;

    @DetailLoading
    @Relationship(type = HAS_SENSE_GROUPS, direction = Relationship.OUTGOING)
    private List<SenseGroup> senseGroups;

    @DetailLoading
    @Relationship(type = HAS_MAIN_AUDIO, direction = Relationship.OUTGOING)
    private DigitalAsset audio;

//    @DetailLoading
//    @CascadeRelationship
//    @Relationship(type = HAS_MAIN_IMAGE, direction = Relationship.OUTGOING)
//    private DigitalAsset image;
//
//    @DetailLoading
//    @CascadeRelationship
//    @Relationship(type = HAS_IMAGES, direction = Relationship.OUTGOING)
//    private List<DigitalAsset> images;

    // RELATIONSHIPS //////////////////////////////////////////////////////////////
//    @DetailLoading
    /**
     * In related expressions, they only contains text and id. That's it!
     */
    @Relationship(type = IS_SYNONYMOUS_WITH, direction = Relationship.UNDIRECTED)
    private List<Expression> synonyms;

    //    @DetailLoading
    @Relationship(type = IS_ANTONYMOUS_WITH, direction = Relationship.UNDIRECTED)
    private List<Expression> antonyms;

    @DetailLoading
    @Relationship(type = FAMILY_WITH, direction = Relationship.UNDIRECTED)
    private List<Expression> family;

    // PARENT RELATIONSHIPS //////////////////////////////////////////////////////////////
    /**
     * I use OUTGOING relationship here because I don't want the {@link User} object is concerned with Expression.
     * The {@link User} should be concerned with Security matters only.
     */
    @DetailLoading
    @Relationship(type = OWN_EXPRESSION, direction = Relationship.INCOMING)
    private User owner;

    //Just for convenient query
//    @Relationship(type = ExpressionPracticeResult.RESULT_OF_EXPRESSION, direction = Relationship.INCOMING)
//    private ExpressionPracticeResult expressionPracticeResult;
    @Transient
    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(this.text);
    }

    public void setLexicalEntries(List<LexicalEntry> lexicalEntries) {
        this.lexicalEntries = lexicalEntries;
        String text = LexicalEntryUtils.toText(lexicalEntries);
        if (StringUtils.isNotBlank(text)) {
            this.text = text;
        }
    }

    public Locale getLocaleOrDefault() {
        Locale locale = this.getLocale();
        if (locale == null) {
            locale = Locale.DEFAULT;
        }
        return locale;
    }

    @Override
    public String toString() {
        return String.format("Expression{%s, %s}", super.getId(), text);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ExpressionType getExpressionType() {
        return expressionType;
    }

    public void setExpressionType(ExpressionType expressionType) {
        this.expressionType = expressionType;
    }

    public List<LexicalEntry> getLexicalEntries() {
        return lexicalEntries;
    }

    public List<Expression> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<Expression> synonyms) {
        this.synonyms = synonyms;
    }

    public List<Expression> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<Expression> antonyms) {
        this.antonyms = antonyms;
    }

    public List<Expression> getFamily() {
        return family;
    }

    public void setFamily(List<Expression> family) {
        this.family = family;
    }

    public DigitalAsset getAudio() {
        return audio;
    }

    public void setAudio(DigitalAsset audio) {
        this.audio = audio;
    }
//
//    public List<DigitalAsset> getImages() {
//        return images;
//    }
//
//    public void setImages(List<DigitalAsset> images) {
//        this.images = images;
//    }
//
//    public DigitalAsset getImage() {
//        return image;
//    }
//
//    public void setImage(DigitalAsset image) {
//        this.image = image;
//    }

    @Override
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<SenseGroup> getSenseGroups() {
        return senseGroups;
    }

    public void setSenseGroups(List<SenseGroup> senseGroups) {
        this.senseGroups = senseGroups;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

//    public ExpressionPracticeResult getExpressionPracticeResult() {
//        return expressionPracticeResult;
//    }
//
//    public void setExpressionPracticeResult(ExpressionPracticeResult expressionPracticeResult) {
//        this.expressionPracticeResult = expressionPracticeResult;
//    }
}
