package org.tnmk.ln.app.aggregation.practice.model;

import org.springframework.stereotype.Component;
import org.tnmk.ln.infrastructure.basemodel.BaseConverter;
import org.tnmk.ln.app.practice.entity.question.Question;

/**
 * @author khoi.tran on 4/19/17.
 */
@Component
public class QuestionConverter extends BaseConverter<Question, QuestionComposite> {
}
