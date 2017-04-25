package tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.aggregation.practice.model.QuestionComposite;
import tnmk.ln.app.dictionary.ExpressionUtils;
import tnmk.ln.app.dictionary.entity.Example;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.app.dictionary.entity.Sense;
import tnmk.ln.app.practice.entity.question.Question;
import tnmk.ln.app.practice.entity.question.QuestionExpressionRecall;
import tnmk.ln.app.practice.entity.question.QuestionFillBlank;
import tnmk.ln.app.practice.entity.question.QuestionPart;
import tnmk.ln.app.practice.entity.question.QuestionType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 3/4/17.
 */
@Service
public class QuestionGenerationService {
    public static final int MAX_FILL_BLANK_QUESTIONS_PER_SENSE = 5;

    @Autowired
    QuestionCompositeRepository questionCompositeRepository;

    @Autowired
    QuestionLoadingRepository questionLoadingRepository;

    @Autowired
    QuestionFillBlankGenerator questionFillBlankGenerator;

    @Transactional
    public List<Question> createQuestionsIfNotExist(Expression expression) {
        List<Question> questions = constructQuestionsIfNotExist(expression);
        questionCompositeRepository.save(questions);
        return questions;
    }

    @Transactional
    public List<Question> createQuestions(Expression expression) {
        List<Question> questions = constructQuestionsIfNotExist(expression);
        if (!questions.isEmpty()) {
            questionCompositeRepository.save(questions);
        }
        return questions;
    }

    public List<Question> constructQuestionsIfNotExist(Expression expression) {
        List<Question> questions = new ArrayList<>();
        questions.addAll(constructExpressionRecallQuestionsIfNotExist(expression));
        questions.addAll(constructFillBlankQuestionsIfNotExist(expression));
        return questions;
    }

    private List<Question> constructExpressionRecallQuestionsIfNotExist(Expression expression) {
        List<Sense> senses = ExpressionUtils.getSenses(expression);
        List<Question> questions = senses.stream().map(sense -> constructExpressionRecallQuestionIfNotExist(expression, sense)).collect(Collectors.toList());
        return questions;
    }

    private Question constructExpressionRecallQuestionIfNotExist(Expression expression, Sense sense) {
        Question question = questionLoadingRepository.findOneByQuestionTypeAndFromExpressionIdAndFromSenseId(QuestionType.EXPRESSION_RECALL, expression.getId(), sense.getId());
        if (question == null) {
            question = new QuestionExpressionRecall();
            question.setFromExpressionId(expression.getId());
            question.setFromSenseId(sense.getId());
        }
        return question;
    }

    private List<Question> constructFillBlankQuestionsIfNotExist(Expression expression) {
        List<Question> questions = new ArrayList<>();
        List<Sense> senses = ExpressionUtils.getSenses(expression);
        for (Sense sense : senses) {
            int exampleIndex = 0;
            for (Example example : sense.getExamples()) {
                if (exampleIndex >= MAX_FILL_BLANK_QUESTIONS_PER_SENSE) {
                    break;
                }
                questions.add(constructFillBlankQuestionIfNotExist(expression, sense, example));
                exampleIndex++;
            }
        }
        return questions;
    }

    private Question constructFillBlankQuestionIfNotExist(Expression expression, Sense sense, Example example) {
        Question question = questionLoadingRepository.findOneByQuestionTypeAndFromExpressionIdAndFromSenseIdAndFromExampleId(QuestionType.FILL_BLANK, expression.getId(), sense.getId(), example.getId());
        if (question == null) {
            question = new QuestionFillBlank();
            question.setText(example.getText());
            question.setFromExpressionId(expression.getId());
            question.setFromSenseId(sense.getId());
            question.setFromExampleId(example.getId());

            String findingExpression = expression.getText();
            List<QuestionPart> questionParts = questionFillBlankGenerator.analyzeToQuestionParts(expression.getLocaleOrDefault().getLanguage(), findingExpression, question.getText());
            question.setQuestionParts(questionParts);
        }
        return question;
    }

}
