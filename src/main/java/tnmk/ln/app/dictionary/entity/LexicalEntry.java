package tnmk.ln.app.dictionary.entity;

import org.neo4j.ogm.annotation.NodeEntity;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;

/**
 * @author khoi.tran on 2/18/17.
 */
@NodeEntity(label = "LexicalEntry")
public class LexicalEntry extends BaseNeo4jEntity {
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
