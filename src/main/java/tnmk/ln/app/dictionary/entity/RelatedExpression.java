package tnmk.ln.app.dictionary.entity;

/**
 * @author khoi.tran on 2/18/17.
 */
//@NodeEntity(label = "Expression")
public class RelatedExpression implements BaseExpression {
    private String id;
    private String text;

    @Override
    public String toString() {
        return String.format("Expression{%s, %s}", this.getId(), text);
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
