package org.tnmk.common.util.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.tnmk.common.testingmodel.IgnoreUnknownJsonDragon;
import org.tnmk.common.testingmodel.Person;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.testingmodel.PersonFactory;
import org.tnmk.common.testingmodel.constants.CreatureKind;
import org.tnmk.common.utils.io.IOUtils;
import org.tnmk.common.utils.json.JsonUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author khoi.tran on 6/7/17.
 */
public class JsonUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(JsonUtilsTest.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final String personJson = IOUtils.loadTextFileInClassPath("/testdata/person-jason-bourne.json");
    private final String dragonJson = IOUtils.loadTextFileInClassPath("/testdata/creature-with-not-existing-fields.json");

    @Test
    public void success_toJson_Null() {
        final String jsonString = JsonUtils.toJson(OBJECT_MAPPER, null);
        Assert.assertNull(jsonString);
    }

    @Test
    public void success_toJson_Blank() {
        final String str = "";
        final String jsonString = JsonUtils.toJson(OBJECT_MAPPER, str);
        LOGGER.info(jsonString);
        Assert.assertEquals("\"\"", jsonString);
    }

    @Test
    public void success_toJson_String() {
        final String str = "This is some string";
        final String jsonString = JsonUtils.toJson(OBJECT_MAPPER, str);
        LOGGER.info(jsonString);
        Assert.assertEquals('"' + str + '"', jsonString);
    }

    @Test
    public void success_toJson_Object() throws IOException {
        Person person = PersonFactory.createJasonBourne();
        final String jsonString = JsonUtils.toJson(OBJECT_MAPPER, person);
        LOGGER.info(jsonString);
        Assert.assertNotNull(jsonString);
        JsonNode creatureKindValueJsonNode = OBJECT_MAPPER.readTree(jsonString).get("creatureKind");
        Assert.assertEquals(CreatureKind.MUGGLE.getDisplayName(),creatureKindValueJsonNode.asText());
    }

    @Test
    public void success_toObject() {
        final Person person = JsonUtils.toObject(OBJECT_MAPPER, personJson, Person.class);
        Assert.assertNotNull(person);
        Assert.assertEquals(CreatureKind.MUGGLE, person.getCreatureKind());
        Assert.assertEquals(30.5f, person.getAge(), 0.01);
    }

    @Test
    public void success_toObject_ignoreUnknownField() {
        final IgnoreUnknownJsonDragon ignoreUnknownJsonDragon = JsonUtils.toObject(OBJECT_MAPPER, dragonJson, IgnoreUnknownJsonDragon.class);
        Assert.assertNull(ignoreUnknownJsonDragon.getCreatureKind());
        Assert.assertNotNull(ignoreUnknownJsonDragon.getCreatureId());
    }

    @Test
    public void success_toInputStream() {
        final InputStream inputStream = JsonUtils.toJsonInputStream(OBJECT_MAPPER, personJson);
        Assert.assertNotNull(inputStream);
    }

    @Test
    public void success_toInputStream_null() {
        final InputStream inputStream = JsonUtils.toJsonInputStream(OBJECT_MAPPER, null);
        Assert.assertNull(inputStream);
    }
}
