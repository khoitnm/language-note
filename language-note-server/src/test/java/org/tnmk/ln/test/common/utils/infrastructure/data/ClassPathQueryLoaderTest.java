package org.tnmk.ln.test.common.utils.infrastructure.data;

import org.junit.Test;
import org.tnmk.ln.app.practice.entity.question.QuestionType;
import org.tnmk.ln.test.PureTest;
import org.tnmk.common.infrastructure.data.query.ClassPathQueryLoader;

/**
 * @author khoi.tran on 3/13/17.
 */
public class ClassPathQueryLoaderTest extends PureTest {
    @Test
    public void test() {
        String queryString = ClassPathQueryLoader.loadQuery("/org/tnmk/ln/app/practice/query/load-questions-by-recommended-expressions.cql", QuestionType.EXPRESSION_RECALL.getLogicName());
        LOGGER.debug(queryString);
    }
}
