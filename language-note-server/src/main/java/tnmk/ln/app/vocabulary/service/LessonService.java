package tnmk.ln.app.vocabulary.service;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnmk.common.infrastructure.validator.BeanValidator;
import tnmk.ln.app.vocabulary.entity.ExpressionItem;
import tnmk.ln.app.vocabulary.entity.ExpressionType;
import tnmk.ln.app.vocabulary.entity.Lesson;
import tnmk.ln.app.vocabulary.entity.LessonIntroduction;
import tnmk.ln.app.vocabulary.entity.Topic;
import tnmk.ln.app.vocabulary.entity.Word;
import tnmk.ln.app.vocabulary.repository.ExpressionItemRemoveRepository;
import tnmk.ln.app.vocabulary.repository.ExpressionItemRepository;
import tnmk.ln.app.vocabulary.repository.LessonRepository;
import tnmk.ln.app.vocabulary.util.WordUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 1/26/17.
 */
@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ExpressionItemRepository expressionItemRepository;

    @Autowired
    private ExpressionItemRemoveRepository expressionItemRemoveRepository;
    @Autowired
    private TopicService topicService;

    @Autowired
    private BeanValidator beanValidator;

    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    /**
     * TODO there will be some garbage expression or topics???
     * TODO now I'm not sure about above comment anymore?! :D
     *
     * @param lesson
     * @return
     */
    public Lesson saveLesson(Lesson lesson) {
        lesson.setName(StringUtils.trim(lesson.getName()));

        saveRelatedTopics(lesson);

        Lesson savedLesson = presaveLessonCover(lesson);

        saveRelatedExpressionItems(savedLesson);

        //Save again to store related expressionItem (with generated ids) inside the lesson.
        return lessonRepository.save(savedLesson);
    }

    private void saveRelatedTopics(Lesson lesson) {
        Set<Topic> resultTopics = topicService.saveTopics(lesson.getTopics());
        lesson.setTopics(resultTopics);
    }

    /**
     * Save lesson first to generate the lessonId, so that we can set that id into expressionItems.
     *
     * @param lesson
     */
    private Lesson presaveLessonCover(Lesson lesson) {
        Lesson savedLesson = lesson;
        List<ExpressionItem> expressionItems = lesson.getExpressionItems();
        if (StringUtils.isBlank(lesson.getId())) {
            //Temporary remove expressions so that it able to save the lesson (because expressionItems don't have id yet).
            lesson.setExpressionItems(Collections.emptyList());
            savedLesson = lessonRepository.save(lesson);
        }
        savedLesson.setExpressionItems(expressionItems);
        return savedLesson;
    }

    private List<ExpressionItem> saveRelatedExpressionItems(Lesson savedLesson) {
        List<ExpressionItem> expressionItems = savedLesson.getExpressionItems();
        for (ExpressionItem expressionItem : expressionItems) {
            expressionItem.addBookId(savedLesson.getBookId());
            expressionItem.addLessonId(savedLesson.getId());
            Set<String> topicIds = savedLesson.getTopics().stream().map(topic -> topic.getId()).collect(Collectors.toSet());
            expressionItem.addTopicIds(topicIds);
            if (expressionItem.getType().equals(ExpressionType.PHRASAL_VERB)) {
                List<Word> words = expressionItem.getPhrasalVerbDetail().getWords();
                ListIterator<Word> listIterator = words.listIterator();
                while (listIterator.hasNext()) {
                    Word word = listIterator.next();
                    if (word == null || StringUtils.isBlank(word.getValue())) {
                        listIterator.remove();
                    }
                }
            }

        }
        expressionItems = cleanUpExpressionItems(expressionItems);
        savedLesson.setExpressionItems(expressionItems);
        return expressionItemRepository.save(expressionItems);
    }

    private List<ExpressionItem> cleanUpExpressionItems(List<ExpressionItem> expressionItems) {
        expressionItems.forEach(expressionItem -> convertPhrasalVerbToExpressionIfPossible(expressionItem));
        return expressionItems.stream()
                .filter(expressionItem -> !isBlank(expressionItem))
                .collect(Collectors.toList());
    }

    private void convertPhrasalVerbToExpressionIfPossible(ExpressionItem expressionItem) {
        if (expressionItem.getPhrasalVerbDetail() != null) {
            expressionItem.setExpression(WordUtils.toString(expressionItem.getPhrasalVerbDetail().getWords()));
        }
    }

    private boolean isBlank(ExpressionItem expressionItem) {
        boolean result;
        if (expressionItem.getType().equals(ExpressionType.PHRASAL_VERB)) {
            boolean isWordsEmpty = true;
            List<Word> words = expressionItem.getPhrasalVerbDetail().getWords();
            for (Word word : words) {
                if (StringUtils.isNotBlank(word.getValue())) {
                    isWordsEmpty = false;
                    break;
                }
            }
            result = isWordsEmpty;
        } else {
            result = StringUtils.isBlank(expressionItem.getExpression());
        }
        return result;
    }

    public Lesson findById(String lessonId) {
        return lessonRepository.findOne(lessonId);
    }

    public List<LessonIntroduction> findAllIntroductions() {
        List<Lesson> lessons = lessonRepository.findAllIntroductions();
        List<LessonIntroduction> result = new ArrayList<>();
        for (Lesson lesson : lessons) {
            LessonIntroduction lessonIntroduction = new LessonIntroduction();
            BeanUtils.copyProperties(lesson, lessonIntroduction);
            result.add(lessonIntroduction);
        }
        return result;
    }

    public Lesson findByName(String lessonName) {
        return lessonRepository.findOneByName(lessonName);
    }

    public int removeLesson(RemoveLessonRequest removeLessonRequest) {
        int relatedExpression = 0;
        beanValidator.validate(removeLessonRequest);
        if (removeLessonRequest.isIncludeExpressions()) {
            relatedExpression = expressionItemRemoveRepository.removeByLessonIds(removeLessonRequest.getLessonId());
        }
        lessonRepository.delete(removeLessonRequest.getLessonId());
        return relatedExpression;
    }

    public static class RemoveLessonRequest {
        @NotBlank
        private String lessonId;
        private boolean includeExpressions;

        public String getLessonId() {
            return lessonId;
        }

        public void setLessonId(String lessonId) {
            this.lessonId = lessonId;
        }

        public boolean isIncludeExpressions() {
            return includeExpressions;
        }

        public void setIncludeExpressions(boolean includeExpressions) {
            this.includeExpressions = includeExpressions;
        }
    }
}
