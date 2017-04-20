package tnmk.ln.app.aggregation.topic.model;

import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.topic.entity.Topic;

import java.util.List;

/**
 * @author khoi.tran on 4/13/17.
 */
public class TopicComposite extends Topic {
    private List<Expression> expressions;

    public List<Expression> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<Expression> expressions) {
        this.expressions = expressions;
    }
}
