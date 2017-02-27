package tnmk.ln.app.vocabulary.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import tnmk.common.infrastructure.repositoriesfilter.MongoRepoScanInclude;
import tnmk.ln.app.vocabulary.entity.ExpressionItem;
import tnmk.ln.app.vocabulary.model.ExpressionFilter;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@MongoRepoScanInclude
@Repository
public class ExpressionItemFilterRepository {
    @Autowired
    private MongoOperations mongoOperations;

    public List<ExpressionItem> filter(String userId, ExpressionFilter expressionFilter, boolean hasFavourite, boolean hasLatestAnswers, Pageable pageable) {
        Query query = createQuery(userId, expressionFilter, hasFavourite, hasLatestAnswers);
        limitFields(userId, query);
        List<ExpressionItem> result = executeQuery(query, ExpressionItem.class, pageable);
        return result;
    }

    private <T> List<T> executeQuery(Query query, Class<T> elementClass, Pageable pageable) {
        List<T> result = mongoOperations.find(query.with(pageable), elementClass, elementClass.getAnnotation(Document.class).collection());
        return result;
    }

    private void limitFields(String userId, Query query) {
        query.fields()
                .include("expression")
                .include("type")
                .include("expression")
                .include("meanings")
                .include("userPoints." + userId)
        ;
    }

    private Query createQuery(String userId, ExpressionFilter expressionFilter, boolean hasFavourite, boolean hasLatestAnswers) {
        Query query = new Query();
        if (!expressionFilter.isSelectAllBooks()) {
            if (expressionFilter.getSelectedBookIds() != null && !expressionFilter.getSelectedBookIds().isEmpty()) {
                query.addCriteria(where("bookIds").in(expressionFilter.getSelectedBookIds()));
            }
        }
        if (!expressionFilter.isSelectAllLessons()) {
            if (expressionFilter.getSelectedLessonIds() != null && !expressionFilter.getSelectedLessonIds().isEmpty()) {
                query.addCriteria(where("lessonIds").in(expressionFilter.getSelectedLessonIds()));
            }
        }
        if (!expressionFilter.isSelectAllTopics()) {
            if (expressionFilter.getSelectedTopicIds() != null && !expressionFilter.getSelectedTopicIds().isEmpty()) {
                query.addCriteria(where("topicIds").in(expressionFilter.getSelectedTopicIds()));
            }
        }
        Criteria favouriteCriteria;
        if (hasFavourite) {
            favouriteCriteria = where("userPoints." + userId + ".favourite").is(-1);
        } else {
            favouriteCriteria = where("").orOperator(
                    where("userPoints." + userId + ".favourite").is(0)
                    , where("userPoints." + userId + ".favourite").exists(false)
            );
        }

        query.addCriteria(favouriteCriteria);
        query.addCriteria(where("userPoints." + userId + ".latestAnswers").exists(hasLatestAnswers));
        return query;
    }
}
