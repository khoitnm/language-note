package tnmk.ln.app.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.infrastructure.dictionary.oxford.OxfordService;
import tnmk.ln.infrastructure.dictionary.oxford.entity.OxfordWord;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.List;

/**
 * @author khoi.tran on 2/28/17.
 */
@Service
public class ExpressionService {
    @Autowired
    private ExpressionRepository expressionRepository;

    @Autowired
    private ExpressionMapper expressionMapper;

//    @Autowired
//    private ExpressionDetailNeo4jRepository expressionDetailNeo4jRepository;

    @Autowired
    OxfordService oxfordService;

    public Expression createExpression(User owner, Expression expression) {
        expression.setOwner(owner);
        return expressionRepository.save(expression);
    }

    @Transactional
    public void save(Expression expression) {
        expressionRepository.save(expression);
    }

    public Expression findById(String expressionId) {
        return expressionRepository.findOne(expressionId);
    }

    /**
     * It only delete the expression, not delete the children entities inside that expression.
     *
     * @param expressionId
     */
    public void deleteById(String expressionId) {
        expressionRepository.delete(expressionId);
    }

    public void deleteCompositeById(String expressionId) {
        expressionRepository.delete(expressionId);
    }

//    public Expression findDetailById(long expressionId) {
//        return expressionDetailNeo4jRepository.findOneDetailById(expressionId);
//    }

    /**
     * @param text
     * @return
     * @deprecated There are many expression has the same text but different locale
     */
    @Deprecated
    public Expression findOneDetailByText(String text) {
        return expressionRepository.findOneByText(text);
//        return expressionDetailNeo4jRepository.findOneDetailByText(text);
    }

    public Expression findLookUpDetailByText(String sourceLanguage, String text) {
        String trimmedText = text.trim().toLowerCase();
        Expression expression = findOneDetailByText(trimmedText);
        if (expression == null) {
            if (trimmedText.contains(" ")) return null;
            OxfordWord oxfordWord = oxfordService.lookUpDefinition(sourceLanguage, trimmedText);
            expression = ExpressionMapper.toExpression(oxfordWord);
        }
        return expression;
    }

    /**
     * @param text
     * @return
     * @deprecated There are many expression has the same text but different locale
     */
    @Deprecated
    public Expression findOneBriefByText(String text) {
        String trimmedText = text.trim().toLowerCase();
        return expressionRepository.findOneBriefByText(trimmedText);
    }

    public List<Expression> findByIds(List<String> expressionIds) {
        return expressionRepository.findByIdIn(expressionIds);
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
