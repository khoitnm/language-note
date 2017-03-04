package tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.common.util.StringUtil;
import tnmk.ln.app.dictionary.ExpressionUtils;
import tnmk.ln.app.dictionary.entity.Example;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.dictionary.entity.Sense;
import tnmk.ln.app.practice.entity.Question;
import tnmk.ln.app.practice.entity.QuestionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 3/4/17.
 */
@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    @Transactional
    public List<Question> createQuestionsForExpression(Expression expression) {
        List<Question> questions = constructQuestionsForExpression(expression);
        questionRepository.save(questions);
        return questions;
    }

    public List<Question> constructQuestionsForExpression(Expression expression) {
        List<Question> questions = new ArrayList<>();
        questions.addAll(constructExpressionRecallQuestions(expression));
        questions.addAll(constructFillBlankQuestions(expression));
        return questions;
    }

    private List<Question> constructExpressionRecallQuestions(Expression expression) {
        Set<Sense> senses = ExpressionUtils.getSenses(expression);
        List<Question> questions = senses.stream().map(sense -> constructExpressionRecallQuestion(expression, sense)).collect(Collectors.toList());
        return questions;
    }

    private Question constructExpressionRecallQuestion(Expression expression, Sense sense) {
        Question question = new Question();
        question.setQuestionType(QuestionType.EXPRESSION_RECALL);
        question.setFromExpression(expression);
        question.setFromSense(sense);
        return question;
    }

    private List<Question> constructFillBlankQuestions(Expression expression) {
        List<Question> questions = new ArrayList<>();
        Set<Sense> senses = ExpressionUtils.getSenses(expression);
        for (Sense sense : senses) {
            for (Example example : sense.getExamples()) {
                questions.add(constructFillBlankQuestion(expression, sense, example));
            }
        }
        return questions;
    }

    private Question constructFillBlankQuestion(Expression expression, Sense sense, Example example) {
        Question question = new Question();
        question.setQuestionType(QuestionType.FILL_BLANK);
        question.setFromExpression(expression);
        question.setFromSense(sense);
        question.setFromExample(example);

        //TODO question.setQuestionParts();
        return question;
    }

    private String[] toStemmingWords(String text) {
        String[] originalWords = StringUtil.toWords(text);
        //TODO
        List<String> stemmedWords = Collections.EMPTY_LIST;
//        String stemmedSentence = stemmedWords.stream().collect(Collectors.joining());
        return null;
    }
}
