package tnmk.ln.app.dictionary.entity;

/**
 * @author khoi.tran on 2/18/17.
 */
public class LexicalEntry {
    private String value;
    private LexicalType type;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LexicalType getType() {
        return type;
    }

    public void setType(LexicalType type) {
        this.type = type;
    }
}
