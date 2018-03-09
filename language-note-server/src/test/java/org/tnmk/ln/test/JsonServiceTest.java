package org.tnmk.ln.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.tnmk.common.utils.json.JsonUtils;
import org.tnmk.ln.app.vocabulary.entity.ExpressionItem;
import org.tnmk.ln.app.vocabulary.entity.ExpressionType;

/**
 * @author khoi.tran on 2/8/17.
 */
public class JsonServiceTest extends UnitBaseTest {
    @Test
    public void parseJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"type\":\"phrasal verb\"}";
        ExpressionItem expressionItem = JsonUtils.toObject(objectMapper, json, ExpressionItem.class);
        Assert.assertEquals(ExpressionType.PHRASAL_VERB, expressionItem.getType());
        String jsonString = JsonUtils.toJson(objectMapper, expressionItem);
        Assert.assertTrue(jsonString.contains("\"type\":\"phrasal verb\""));
    }
}
