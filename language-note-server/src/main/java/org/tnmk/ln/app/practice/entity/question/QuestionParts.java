package org.tnmk.ln.app.practice.entity.question;

import org.springframework.data.mongodb.core.mapping.Document;
import org.tnmk.ln.app.common.entity.BaseMongoEntity;

import java.util.List;

/**
 * @author khoi.tran on 4/25/17.
 */
@Document(collection = "QuestionParts")
public class QuestionParts extends BaseMongoEntity {
    private List<QuestionPart> items;

    public List<QuestionPart> getItems() {
        return items;
    }

    public void setItems(List<QuestionPart> items) {
        this.items = items;
    }
}
