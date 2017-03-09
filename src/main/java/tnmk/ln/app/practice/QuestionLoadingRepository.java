package tnmk.ln.app.practice;

//import org.springframework.data.neo4j.repository.GraphRepository; import tnmk.ln.app.practice.entity.Question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tnmk.common.util.IterableUtil;
import tnmk.ln.app.practice.entity.Question;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.List;

/**
 * @author khoi.tran on 2/26/17.
 */
@Repository
public class QuestionLoadingRepository {
    @Autowired
    private Neo4jRepository neo4jRepository;

//    public List<Question> loadQuestionsByNote(User user, Long noteId) {
//        String queryString = "";
//        return IterableUtil.toList(neo4jRepository.queryList(Question.class, queryString, user.getId(), noteId));
//    }

    public List<Question> loadQuestionsByNotes(User user, Long... noteIds) {
        String queryString = "";
        return IterableUtil.toList(neo4jRepository.queryList(Question.class, queryString, user.getId(), noteIds));
    }

    public List<Question> loadQuestionsByTopics(User user, Long... topicIds) {
        String queryString = "";
        return IterableUtil.toList(neo4jRepository.queryList(Question.class, queryString, user.getId(), topicIds));
    }
}