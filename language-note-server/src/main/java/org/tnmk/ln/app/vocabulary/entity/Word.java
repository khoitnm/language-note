package org.tnmk.ln.app.vocabulary.entity;

/**
 * @author khoi.tran on 2/4/17.
 */
public class Word {
    private int index;
    private String value;
    private String type;

    public Word() {
    }

    public Word(int index, String type, String value) {
        this.index = index;
        this.value = value;
        this.type = type;
    }

    public String toString() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value != null ? value.trim() : null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
