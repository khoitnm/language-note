package org.tnmk.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.tnmk.common.util.testmodel.Person;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.utils.io.IOUtils;
import org.tnmk.common.utils.json.JsonUtils;

import java.io.InputStream;

/**
 * @author khoi.tran on 6/7/17.
 */
public class JsonUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(JsonUtilsTest.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final String personJson = IOUtils.loadTextFileInClassPath("/testdata/person-jason-bourne.json");

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
    public void success_toObject() {
        final Person person = (Person) JsonUtils.toObject(OBJECT_MAPPER, personJson, Person.class);
        Assert.assertNotNull(person);
        Assert.assertEquals(30.5f, person.getAge(), 0.01);
    }

    @Test
    public void success_toInputStream() {
        final InputStream inputStream = JsonUtils.toJsonInputStream(OBJECT_MAPPER, personJson);
        Assert.assertNotNull(inputStream);
    }

    @Test
    public void success_toInputStream_null() {
        final String personJson = null;
        final InputStream inputStream = JsonUtils.toJsonInputStream(OBJECT_MAPPER, personJson);
        Assert.assertNull(inputStream);
    }
}
