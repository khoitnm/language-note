package tnmk.ln.app.aggregation.topic.model;

import tnmk.ln.app.aggregation.practice.model.ExpressionComposite;
import tnmk.ln.app.topic.entity.Topic;

import java.util.List;

/**
 * @author khoi.tran on 4/13/17.
 */
public class TopicComposite extends Topic {
    private List<ExpressionComposite> expressions;

    public List<ExpressionComposite> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<ExpressionComposite> expressions) {
        this.expressions = expressions;
    }
}
