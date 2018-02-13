package org.tnmk.ln.app.vocabulary.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.tnmk.ln.app.common.entity.BaseMongoEntity;

/**
 * @author khoi.tran on 1/25/17.
 */
@Document(collection = "Topic")
public class Topic extends BaseMongoEntity {
    @Indexed(unique = true)
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
