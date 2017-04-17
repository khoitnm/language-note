package tnmk.ln.app.dictionary.entity;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import tnmk.ln.app.common.entity.BaseMongoEntity;
import tnmk.ln.app.common.entity.Cleanable;

/**
 * @author khoi.tran on 2/18/17.
 */
//@NodeEntity(label = "LexicalEntry")
@Document(collection = "LexicalEntry")
public class LexicalEntry extends BaseMongoEntity implements Cleanable {
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
