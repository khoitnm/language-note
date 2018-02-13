package org.tnmk.ln.app.practice.entity.result;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author khoi.tran on 3/11/17.
 */
@NodeEntity(label = "ExpressionPracticeResult")
public class ExpressionPracticeResult extends BasePracticeResult {
    private String expressionId;

    public String getExpressionId() {
        return expressionId;
    }

    public void setExpressionId(String expressionId) {
        this.expressionId = expressionId;
    }

}
