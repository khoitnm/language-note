package org.tnmk.common.util;


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
        Assert.assertNotNull(str);
    }

    @Test
    public void success_toString_multipleLines_null() {
        final String str = ToStringUtils.toStringMultiLineForEachElement(null);
        Assert.assertNull(str);
    }
}
