package tnmk.ln.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import tnmk.common.util.ObjectMapperUtil;
import tnmk.ln.app.vocabulary.entity.ExpressionItem;
import tnmk.ln.app.vocabulary.entity.ExpressionType;

/**
 * @author khoi.tran on 2/8/17.
 */
public class JsonServiceTest {
    @Test
    public void parseJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"type\":\"phrasal verb\"}";
        ExpressionItem expressionItem = ObjectMapperUtil.toObject(objectMapper, json, ExpressionItem.class);
        Assert.assertEquals(ExpressionType.PHRASAL_VERB, expressionItem.getType());
        String jsonString = ObjectMapperUtil.toJson(objectMapper, expressionItem);
        Assert.assertTrue(jsonString.contains("\"type\":\"phrasal verb\""));
    }
}
