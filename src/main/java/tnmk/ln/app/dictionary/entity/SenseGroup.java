package tnmk.ln.app.dictionary.entity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import tnmk.ln.infrastructure.data.neo4j.annotation.CascadeRelationship;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;

import java.util.Set;

/**
 * The group of senses which have the common lexical type.
 *
 * @author khoi.tran on 2/27/17.
 */
@NodeEntity(label = "SenseGroup")
public class SenseGroup extends BaseNeo4jEntity {
    public static final String HAS_SENSES = "HAS_SENSES";

    private LexicalType lexicalType;

    @CascadeRelationship
    @Relationship(type = HAS_SENSES, direction = Relationship.OUTGOING)
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