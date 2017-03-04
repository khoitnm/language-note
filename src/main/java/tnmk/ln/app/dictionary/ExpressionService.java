package tnmk.ln.app.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.dictionary.entity.SenseGroup;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.Arrays;

/**
 * @author khoi.tran on 2/28/17.
 */
@Service
public class ExpressionService {
    @Autowired
    private ExpressionRepository expressionRepository;

    @Autowired
    private ExpressionUpdateRepository expressionUpdateRepository;



    //    @Transactional
    public Expression createExpression(User owner, Expression expression) {
        expression.setOwner(owner);
        return expressionRepository.save(expression);
    }

    @Transactional
    public void save(Expression expression) {
        expressionRepository.save(expression);
//        return expressionRepository.save(expression);
    }

    @Transactional
    public void updateExpressionDefinition(Expression expression) {
        //TODO should remove old lexical entries which are not related to this expression anymore.
        expressionUpdateRepository.setPropertiesAndRelationshipsExcludeIncoming(expression);
//        return expressionRepository.save(expression);
    }

    @Transactional
    public void updateCascade(Expression expression) {
        //TODO should remove old lexical entries which are not related to this expression anymore.
        expressionUpdateRepository.setPropertiesAndRelationshipsCascade(expression);
//        return expressionRepository.save(expression);
    }

    @Transactional
    public void detachExpressionDefinition(Expression expression) {
        expressionUpdateRepository.removeRelationships(expression);
    }

    public Expression findById(long expressionId) {
        return expressionRepository.findOne(expressionId);
    }

    public void deleteById(long expressionId) {
        expressionRepository.delete(expressionId);
    }
}
