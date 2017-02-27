package tnmk.ln.app.dictionary.entity;

import tnmk.ln.app.common.entity.BaseNeo4jEntity;

import java.util.Set;

/**
 * @author khoi.tran on 2/27/17.
 */
public class SensesGroup extends BaseNeo4jEntity {
    private LexicalType lexicalType;
    private Set<Sense> senses;

    public LexicalType getLexicalType() {
        return lexicalType;
    }

    public void setLexicalType(LexicalType lexicalType) {
        this.lexicalType = lexicalType;
    }

    public Set<Sense> getSenses() {
        return senses;
    }

    public void setSenses(Set<Sense> senses) {
        this.senses = senses;
    }
}
