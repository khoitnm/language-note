package tnmk.ln.app.dictionary.entity;

import org.neo4j.ogm.annotation.NodeEntity;
import tnmk.ln.app.common.entity.BaseEntity;

/**
 * @author khoi.tran on 2/18/17.
 */
@NodeEntity
public class Example extends BaseEntity {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
