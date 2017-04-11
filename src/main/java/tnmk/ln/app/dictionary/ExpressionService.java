package tnmk.ln.app.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.infrastructure.dictionary.oxford.OxfordService;
import tnmk.ln.infrastructure.dictionary.oxford.entity.OxfordWord;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

/**
 * @author khoi.tran on 2/28/17.
 */
@Service
public class ExpressionService {
    @Autowired
    private ExpressionRepository expressionRepository;

    @Autowired
    private ExpressionMapper expressionMapper;

    @Autowired
    private ExpressionDetailRepository expressionDetailRepository;

    @Autowired
    OxfordService oxfordService;

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
//        expressionUpdateRepository.setPropertiesAndRelationshipsExcludeIncoming(expression);
//        return expressionRepository.save(expression);
    }

    public Expression findById(long expressionId) {
        return expressionRepository.findOne(expressionId);
    }

    /**
     * It only delete the expression, not delete the children entities inside that expression.
     *
     * @param expressionId
     */
    public void deleteById(long expressionId) {
        expressionRepository.delete(expressionId);
    }

    public Expression findDetailById(long expressionId) {
        return expressionDetailRepository.findOneDetailById(expressionId);
    }

    public Expression findOneDetailByText(String text) {
        return expressionDetailRepository.findOneDetailByText(text);
    }

    public Expression findLookUpDetailByText(String sourceLanguage, String text) {
        String trimmedText = text.trim().toLowerCase();
        Expression expression = findOneDetailByText(trimmedText);
        if (expression == null) {
            OxfordWord oxfordWord = oxfordService.lookUpDefinition(sourceLanguage, trimmedText);
            expression = ExpressionMapper.toExpression(oxfordWord);
        }
        return expression;
    }

    public Expression findOneBriefByText(String text) {
        String trimmedText = text.trim().toLowerCase();
        return expressionDetailRepository.findOneBriefByText(trimmedText);
    }
//
//    public void deleteExpressionWithItsSensesAndExamples(Expression expression) {
//        for (SenseGroup senseGroup : expression.getSenseGroups()) {
//            for (Sense sense : senseGroup.getSenses()) {
//                for (Example example : sense.getExamples()) {
////                    List<Question> questions = que
//                    //TODO remove questions and answers
//                }
//            }
//        }
//
//    }
}
