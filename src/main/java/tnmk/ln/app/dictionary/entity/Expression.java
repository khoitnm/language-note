package tnmk.ln.app.dictionary.entity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.app.dictionary.LexicalEntryUtils;
import tnmk.ln.app.digitalasset.entity.DigitalAsset;
import tnmk.ln.app.user.entity.Account;

import java.util.List;

/**
 * @author khoi.tran on 2/18/17.
 */
@NodeEntity(label = "Expression")
public class Expression extends BaseNeo4jEntity {
    public static final String HAS_LEXICAL_ENTRIES = "HAS_LEXICAL_ENTRIES";
    public static final String HAS_SENSES = "HAS_SENSES";
    public static final String HAS_MAIN_AUDIO = "HAS_MAIN_AUDIO";

    public static final String HAS_MAIN_IMAGE = "HAS_MAIN_IMAGE";

    public static final String HAS_IMAGES = "HAS_IMAGES";
    public static final String IS_SYNONYMOUS_WITH = "IS_SYNONYMOUS_WITH";
    public static final String IS_ANTONYMOUS_WITH = "IS_ANTONYMOUS_WITH";
    public static final String RELATE_TO = "RELATE_TO";
    public static final String CREATED_EXPRESSION = "CREATED_EXPRESSION";

    /**
     * This field is not unique because many users can contribute differently to our system.
     */
    @Index
    @NotBlank
    private String text;
    private ExpressionType expressionType;

    // COMPOSITION RELATIONSHIPS //////////////////////////////////////////////////////////////
    /**
     * The list of lexical entries which helps to form the expression's text.
     * If the lexicalEntries is not empty, the text will be created from lexicalEntries
     */
    @Relationship(type = HAS_LEXICAL_ENTRIES, direction = Relationship.OUTGOING)
    private List<LexicalEntry> lexicalEntries;
    @Relationship(type = HAS_SENSES, direction = Relationship.OUTGOING)
    private List<Sense> senses;
    @Relationship(type = HAS_MAIN_AUDIO, direction = Relationship.OUTGOING)
    private DigitalAsset audio;
    @Relationship(type = HAS_MAIN_IMAGE, direction = Relationship.OUTGOING)
    private DigitalAsset image;
    @Relationship(type = HAS_IMAGES, direction = Relationship.OUTGOING)
    private List<DigitalAsset> images;

    // RELATIONSHIPS //////////////////////////////////////////////////////////////
    @Relationship(type = IS_SYNONYMOUS_WITH, direction = Relationship.UNDIRECTED)
    private List<Expression> synonyms;
    @Relationship(type = IS_ANTONYMOUS_WITH, direction = Relationship.UNDIRECTED)
    private List<Expression> antonyms;
    @Relationship(type = RELATE_TO, direction = Relationship.UNDIRECTED)
    private List<Expression> family;

    // PARENT RELATIONSHIPS //////////////////////////////////////////////////////////////
    @Relationship(type = CREATED_EXPRESSION, direction = Relationship.INCOMING)
    private Account owner;

    public void setLexicalEntries(List<LexicalEntry> lexicalEntries) {
        this.lexicalEntries = lexicalEntries;
        String text = LexicalEntryUtils.toText(lexicalEntries);
        if (StringUtils.isNotBlank(text)) {
            this.text = text;
        }
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

    public List<Sense> getSenses() {
        return senses;
    }

    public void setSenses(List<Sense> senses) {
        this.senses = senses;
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

    public List<DigitalAsset> getImages() {
        return images;
    }

    public void setImages(List<DigitalAsset> images) {
        this.images = images;
    }

    public DigitalAsset getImage() {
        return image;
    }

    public void setImage(DigitalAsset image) {
        this.image = image;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }
}
