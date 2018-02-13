package tnmk.ln.test.common.utils.infrastructure.data;

import org.junit.Test;
import tnmk.common.infrastructure.data.query.ClassPathQueryLoader;
import tnmk.ln.app.practice.entity.question.QuestionType;
import tnmk.ln.test.PureTest;

/**
 * @author khoi.tran on 3/13/17.
 */
public class ClassPathQueryLoaderTest extends PureTest {
    @Test
    public void test() {
        String queryString = ClassPathQueryLoader.loadQuery("/tnmk/ln/app/practice/query/load-questions-by-recommended-expressions.cql", QuestionType.EXPRESSION_RECALL.getLogicName());
        LOGGER.debug(queryString);
    }
}
