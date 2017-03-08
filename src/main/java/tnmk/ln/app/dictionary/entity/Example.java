package tnmk.ln.app.dictionary.entity;

import org.neo4j.ogm.annotation.NodeEntity;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;

/**
 * @author khoi.tran on 2/18/17.
 */
@NodeEntity(label = "Example")
public class Example extends BaseNeo4jEntity {
    private String text;

    @Override
    public String toString() {
        return String.format("Example{%s, %s}", super.getId(), text);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
