package tnmk.ln.app.aggregation.practice.model;

import org.springframework.stereotype.Component;
import tnmk.ln.app.practice.entity.question.Question;
import tnmk.ln.infrastructure.basemodel.BaseConverter;

/**
 * @author khoi.tran on 4/19/17.
 */
@Component
public class QuestionConverter extends BaseConverter<Question, QuestionComposite> {
}
