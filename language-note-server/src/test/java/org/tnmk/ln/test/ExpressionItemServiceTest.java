package org.tnmk.ln.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.tnmk.ln.MainApplication;
import org.tnmk.ln.app.vocabulary.entity.ExpressionItem;
import org.tnmk.ln.app.vocabulary.entity.Lesson;
import org.tnmk.ln.app.vocabulary.entity.Meaning;
import org.tnmk.ln.app.vocabulary.model.ExpressionFilter;
import org.tnmk.ln.app.vocabulary.service.ExpressionItemFilterService;
import org.tnmk.ln.app.vocabulary.service.LessonService;

import java.util.Arrays;
import java.util.List;

/**
 * @author khoi.tran on 1/31/17.
 */
public class ExpressionItemServiceTest extends BaseTest{

    @Autowired
    LessonService lessonService;
    @Autowired
    ExpressionItemFilterService expressionItemService;

    String userId = "test_user_1";

    //    @Test
    public void test_create_lesson() {

        Lesson lesson = new Lesson();//lessonService.findByName("test_lesson_1");
        lesson.setName("test_lesson_2");
        List<ExpressionItem> expressionItems = lesson.getExpressionItems();

        ExpressionItem expressionItem1 = constructExpressionItem(lesson.getName() + "_expression_1");
        expressionItems.add(expressionItem1);

        expressionItem1.getUserPoints().putUserPoint("test_user_1", Arrays.asList(2, 0, 3, 1, 5, 6));
        expressionItem1.getUserPoints().putUserPoint("test_user_2", Arrays.asList(2, 0, 0));
        expressionItem1.getUserPoints().putUserPoint("test_user_3", Arrays.asList(3, 2, 1));
        expressionItem1.getUserPoints().putUserPoint("test_user_4", Arrays.asList(0));

        ExpressionItem expressionItem2 = constructExpressionItem(lesson.getName() + "_expression_2");
        expressionItems.add(expressionItem2);
        expressionItem2.getUserPoints().putUserPoint("test_user_1", Arrays.asList(2, 3));

        lessonService.saveLesson(lesson);

//        expressionItemService.
    }

    public static ExpressionItem constructExpressionItem(String expression) {
        ExpressionItem expressionItem = new ExpressionItem();
        expressionItem.setExpression(expression);

        Meaning meaning = new Meaning();
        meaning.setExplanation(expression + "_meaning");
        expressionItem.setMeanings(Arrays.asList(meaning));
        return expressionItem;
    }

    @Test
    public void test_load_expression_by_user() {
        ExpressionFilter expressionFilter = new ExpressionFilter();
        List<ExpressionItem> expressionItems = expressionItemService.filter(userId, expressionFilter);
        System.out.println(expressionItems);
    }
}
