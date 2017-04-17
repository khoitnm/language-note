package tnmk.ln.app.dictionary.entity;

import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.mongodb.core.mapping.Document;
import tnmk.ln.app.common.entity.BaseMongoEntity;
import tnmk.ln.app.common.entity.Cleanable;
import tnmk.ln.infrastructure.data.neo4j.annotation.DetailLoading;

import java.util.Set;

/**
 * The group of senses which have the common lexical type.
 *
 * @author khoi.tran on 2/27/17.
 */
//@NodeEntity(label = "SenseGroup")
@Document(collection = "SenseGroup")
public class SenseGroup extends BaseMongoEntity implements Cleanable {
    public static final String HAS_SENSES = "HAS_SENSES";

    private LexicalType lexicalType;

    @DetailLoading
    @Relationship(type = HAS_SENSES, direction = Relationship.OUTGOING)
    private Set<Sense> senses;

    @Override
    public boolean isEmpty() {
        return lexicalType == null;
    }

    @Override
    public String toString() {
        return String.format("SenseGroup{%s, %s}", super.getId(), lexicalType);
    }

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
