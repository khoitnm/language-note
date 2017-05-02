package tnmk.ln.app.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public static final Logger LOGGER = LoggerFactory.getLogger(QuestionGenerationService.class);

    public static final int MAX_FILL_BLANK_QUESTIONS_PER_SENSE = 5;

    @Autowired
    QuestionCompositeRepository questionCompositeRepository;

    @Autowired
    QuestionLoadingRepository questionLoadingRepository;

    @Autowired
    QuestionFillBlankGenerator questionFillBlankGenerator;

    @Async
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
            int examplesCount = 0;
            for (Example example : sense.getExamples()) {
                if (examplesCount >= MAX_FILL_BLANK_QUESTIONS_PER_SENSE) {
                    break;
                }
                QuestionFillBlank questionFillBlank = constructFillBlankQuestionIfNotExist(expression, sense, example);
                if (questionFillBlank.getQuestionParts().size() > 1) {
                    questions.add(questionFillBlank);
                    examplesCount++;
                }
            }
//            if (questions.isEmpty() && StringUtils.isNotBlank(sense.getExplanation())) {
//                constructFillBlankQuestionFromDefinitionIfNotExist(expression, sense);
//            }
        }
        return questions;
    }

//    private QuestionFillBlank constructFillBlankQuestionFromDefinitionIfNotExist(Expression expression, Sense sense) {
//        Question question = questionLoadingRepository.findOneByQuestionTypeAndFromExpressionIdAndFromSenseIdAndFromExampleId(QuestionType.FILL_BLANK, expression.getId(), sense.getId(), "explanation");
//
//    }

    private QuestionFillBlank constructFillBlankQuestionIfNotExist(Expression expression, Sense sense, Example example) {
        Question question = questionLoadingRepository.findOneByQuestionTypeAndFromExpressionIdAndFromSenseIdAndFromExampleId(QuestionType.FILL_BLANK, expression.getId(), sense.getId(), example.getId());
        QuestionFillBlank questionFillBlank = (QuestionFillBlank) question;
        if (questionFillBlank == null) {
            questionFillBlank = new QuestionFillBlank();
            questionFillBlank.setText(example.getText());
            questionFillBlank.setFromExpressionId(expression.getId());
            questionFillBlank.setFromSenseId(sense.getId());
            questionFillBlank.setFromExampleId(example.getId());

            String findingExpression = expression.getText();
            List<QuestionPart> questionParts = questionFillBlankGenerator.analyzeToQuestionParts(expression.getLocaleOrDefault().getLanguage(), findingExpression, questionFillBlank.getText());
            questionFillBlank.setQuestionParts(questionParts);
        }
        return questionFillBlank;
    }

}
