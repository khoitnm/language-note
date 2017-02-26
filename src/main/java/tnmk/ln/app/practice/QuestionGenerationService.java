package tnmk.ln.app.practice;

import org.springframework.data.neo4j.repository.GraphRepository;
import tnmk.ln.app.practice.entity.Question;

/**
 * @author khoi.tran on 2/26/17.
 */
public interface QuestionGenerationService extends GraphRepository<Question> {
}
