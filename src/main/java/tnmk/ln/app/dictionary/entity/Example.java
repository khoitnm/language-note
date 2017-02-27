package tnmk.ln.app.dictionary.entity;

import tnmk.ln.app.common.entity.BaseEntity;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;

/**
 * @author khoi.tran on 2/18/17.
 */
//@NodeEntity
public class Example extends BaseNeo4jEntity {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
