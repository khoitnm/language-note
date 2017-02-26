package tnmk.ln.app.dictionary.entity;

/**
 * @author khoi.tran on 2/18/17.
 */
public class LexicalEntry {
    private String text;
    private LexicalType type;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LexicalType getType() {
        return type;
    }

    public void setType(LexicalType type) {
        this.type = type;
    }
}
