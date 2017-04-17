package tnmk.ln.app.dictionary.entity;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import tnmk.ln.app.common.entity.BaseMongoEntity;
import tnmk.ln.app.common.entity.Cleanable;

/**
 * @author khoi.tran on 2/18/17.
 */
//@NodeEntity(label = "Example")
@Document(collection = "Example")
public class Example extends BaseMongoEntity implements Cleanable {
    private String text;

    @Transient
    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(this.text);
    }

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
