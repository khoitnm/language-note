package tnmk.ln.app.example;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.DateLong;
import tnmk.ln.app.dictionary.entity.Expression;

import java.util.Date;

/**
 * @author khoi.tran on 2/27/17.
 */
@NodeEntity
public class Example {
    @GraphId
    private Long id;

    private String text;

    private Child child;
    private Expression expression;

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public static class Child {
        @GraphId
        private Long id;

        private String text;

        private Long num = 10L;
        @DateLong
        private Date time = new Date();

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

        public Long getNum() {
            return num;
        }

        public void setNum(Long num) {
            this.num = num;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
