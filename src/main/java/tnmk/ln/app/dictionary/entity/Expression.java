package tnmk.ln.app.dictionary.entity;

import tnmk.ln.app.common.entity.BaseEntity;
import tnmk.ln.infrastructure.filestorage.entity.FileItem;

import java.util.List;

/**
 * @author khoi.tran on 2/18/17.
 */
//@NodeEntity
public class Expression extends BaseEntity {
    private String text;
    private ExpressionType expressionType;
    /**
     * The list of lexical entries which helps to form the expression's text.
     */
    private List<LexicalEntry> lexicalEntries;
    private List<Sense> senses;
    private List<Expression> synonyms;
    private List<Expression> antonyms;
    private List<Expression> family;
    private FileItem audio;
    private List<FileItem> images;

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

    public void setLexicalEntries(List<LexicalEntry> lexicalEntries) {
        this.lexicalEntries = lexicalEntries;
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

    public FileItem getAudio() {
        return audio;
    }

    public void setAudio(FileItem audio) {
        this.audio = audio;
    }

    public List<FileItem> getImages() {
        return images;
    }

    public void setImages(List<FileItem> images) {
        this.images = images;
    }
}
