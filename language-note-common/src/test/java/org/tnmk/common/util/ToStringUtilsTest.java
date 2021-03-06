package org.tnmk.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.testingmodel.Person;
import org.tnmk.common.testingmodel.PersonFactory;
import org.junit.Assert;
import org.junit.Test;
import org.tnmk.common.utils.ToStringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author khoi.tran on 6/5/17.
 */
public class ToStringUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(ToStringUtilsTest.class);

    @Test
    public void success_toString_null() {
        final String str = ToStringUtils.toString(null);
        Assert.assertNull(str);
    }

    @Test
    public void success_toString_emptyString() {
        final String str = ToStringUtils.toString("");
        Assert.assertEquals("", str);
    }

    @Test
    public void success_toString_String() {
        final String input = "hello world";
        final String str = ToStringUtils.toString(input);
        Assert.assertEquals(input, str);
    }

    @Test
    public void success_toString_Number() {
        final Float input = 5.3f;
        final String str = ToStringUtils.toString(input);
        Assert.assertEquals("5.3", str);
    }

    @Test
    public void success_toString_Object_multipleLines() {
        final Person input = PersonFactory.createJasonBourne();
        final String str = ToStringUtils.toStringMultiLine(input);
        Assert.assertNotNull(str);
    }

    @Test
    public void success_toString_multipleLines_list() {
        final List input = Arrays.asList(5.1f, "XYZ", PersonFactory.createJasonBourne());
        final String str = ToStringUtils.toStringMultiLineForEachElement(input);
        LOGGER.debug(str);
        Assert.assertNotNull(str);
    }

    @Test
    public void success_toString_multipleLines_null() {
        final String str = ToStringUtils.toStringMultiLineForEachElement(null);
        Assert.assertNull(str);
    }

    @Test
    public void success_toString_simple_objects() {
        String str = ToStringUtils.toSimpleString(new String[]{"a", "b", null, "c"});
        LOGGER.debug(str);
        Assert.assertNotNull(str);

        str = ToStringUtils.toSimpleString(new Integer[]{1, null, 2});
        LOGGER.debug(str);
        Assert.assertNotNull(str);

        str = ToStringUtils.toSimpleString(
            new Object[]{
                1,
                null,
                "2",
                new Integer[]{3, 4},
                new Object[] {
                    new String[]{"5", "6"},
                    new Integer[] {7,8},
                    "9",
                    10,
                    PersonFactory.createPerson("11")
                },
                Arrays.asList("12", PersonFactory.createPerson("13"))
            }
        );
        LOGGER.debug(str);
        Assert.assertNotNull(str);

        str = ToStringUtils.toSimpleString("2");
        LOGGER.debug(str);
        Assert.assertNotNull(str);

        str = ToStringUtils.toSimpleString(new Person());
        LOGGER.debug(str);
        Assert.assertNotNull(str);

        //Print Map
        str = ToStringUtils.toSimpleString(PersonFactory.createJasonBourne().getProperties());
        LOGGER.debug(str);
        Assert.assertNotNull(str);

        //Print List
        str = ToStringUtils.toSimpleString(Arrays.asList(new Person(), 1, "2"));
        LOGGER.debug(str);
        Assert.assertNotNull(str);

        str = ToStringUtils.toSimpleString(null);
        LOGGER.debug(str);
        Assert.assertNull(str);
    }
}
