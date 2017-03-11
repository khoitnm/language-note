package tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.dictionary.ExpressionUtils;
import tnmk.ln.app.dictionary.entity.Example;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.dictionary.entity.Sense;
import tnmk.ln.app.practice.entity.Question;
import tnmk.ln.app.practice.entity.QuestionExpressionRecall;
import tnmk.ln.app.practice.entity.QuestionFillBlank;
import tnmk.ln.app.practice.entity.QuestionPart;
import tnmk.ln.app.practice.entity.QuestionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 3/4/17.
 */
@Service
public class QuestionGenerationService {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionLoadingRepository questionLoadingRepository;

    @Autowired
    QuestionFillBlankGenerator questionFillBlankGenerator;

    @Transactional
    public List<Question> createQuestionsIfNotExist(Expression expression) {
        //TODO need to check if this expression has questions or not.
        List<Question> questions = constructQuestions(expression);
        questionRepository.save(questions);
        return questions;
    }

    @Transactional
    public List<Question> createQuestions(Expression expression) {
        List<Question> questions = constructQuestions(expression);
        if (!questions.isEmpty()) {
            questionRepository.save(questions);
        }
        return questions;
    }

    public List<Question> constructQuestions(Expression expression) {
        List<Question> questions = new ArrayList<>();
        questions.addAll(constructExpressionRecallQuestions(expression));
        questions.addAll(constructFillBlankQuestions(expression));
        return questions;
    }

    private List<Question> constructExpressionRecallQuestions(Expression expression) {
        Set<Sense> senses = ExpressionUtils.getSenses(expression);
        List<Question> questions = senses.stream().map(sense -> constructExpressionRecallQuestionIfNotExist(expression, sense)).collect(Collectors.toList());
        return questions;
    }

    private Question constructExpressionRecallQuestionIfNotExist(Expression expression, Sense sense) {
        Question question = questionLoadingRepository.findOneByQuestionTypeAndFromExpressionIdAndFromSenseId(QuestionType.EXPRESSION_RECALL, expression.getId(), sense.getId());
        if (question == null) {
            question = new QuestionExpressionRecall();
            question.setFromExpression(expression);
            question.setFromSense(sense);
        }
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
        Question question = questionLoadingRepository.findOneByQuestionTypeAndFromExpressionIdAndFromSenseIdAndFromExampleId(QuestionType.FILL_BLANK, expression.getId(), sense.getId(), example.getId());
        if (question == null) {
            question = new QuestionFillBlank();
            question.setText(example.getText());
            question.setFromExpression(expression);
            question.setFromSense(sense);
            question.setFromExample(example);

            String findingExpression = expression.getText();
            List<QuestionPart> questionParts = questionFillBlankGenerator.analyzeToQuestionParts(expression.getLocaleOrDefault().getLanguage(), findingExpression, question.getText());
            question.setQuestionParts(questionParts);
        }
        return question;
    }

}
