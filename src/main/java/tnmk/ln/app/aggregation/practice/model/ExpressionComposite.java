package tnmk.ln.app.aggregation.practice.model;

import tnmk.ln.app.dictionary.entity.Expression;

/**
 * @author khoi.tran on 4/30/17.
 */
public class ExpressionComposite extends Expression {
    /**
     * From this value, it will create a new PracticeFavourite for the owner of this expression.
     * It only useful when favourite for a new creating Expression (not saved yet).
     * <p>
     * If a user favourite an Existing Expression, the favourite value will be update in the different way.
     */
    private int favourite;

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }
}
