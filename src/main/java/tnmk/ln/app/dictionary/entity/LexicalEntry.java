package tnmk.ln.app.dictionary.entity;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Transient;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.app.common.entity.Cleanable;

/**
 * @author khoi.tran on 2/18/17.
 */
@NodeEntity(label = "LexicalEntry")
public class LexicalEntry extends BaseNeo4jEntity implements Cleanable {
    private String text;
    private LexicalType type;

    @Transient
    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(this.text);
    }

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
