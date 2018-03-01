package org.tnmk.common.util.datatype;

import org.junit.Assert;
import org.junit.Test;
import org.tnmk.common.testingmodel.Person;
import org.tnmk.common.testingmodel.PersonFactory;
import org.tnmk.common.utils.datatype.NumberUtils;
import org.tnmk.common.utils.datatype.StringUtils;

import java.util.Arrays;

/**
 * @author khoi.tran on 6/7/17.
 */
public class NumUtilsTest {

    @Test
    public void toNumber() {
        Assert.assertEquals((short)5, NumberUtils.toShort("5.99"));
        Assert.assertEquals(new Integer(5), NumberUtils.toIntegerOrNull(5));
        Assert.assertEquals(new Integer(5), NumberUtils.toIntegerOrNull(5.95));
        Assert.assertEquals(new Integer(5), NumberUtils.toIntegerOrNull("5.95f"));
        Assert.assertEquals(new Long(5), NumberUtils.toLongOrNull(new Long(5)));
        Assert.assertEquals(new Long(5), NumberUtils.toLongOrNull("5.95f"));
        Assert.assertEquals(new Long(5), NumberUtils.toLongOrNull(5.95f));
        Assert.assertEquals(5.00d, NumberUtils.toDoubleOrNull(5),0.0001);
        Assert.assertEquals(new Double(5.95), NumberUtils.toDoubleOrNull(5.95d));
        Assert.assertEquals(new Double(5.95), NumberUtils.toDoubleOrNull("5.95f"));

        Assert.assertNull(NumberUtils.toIntegerOrNull("aaa.95f"));
        Assert.assertNull(NumberUtils.toLongOrNull("aaa.95f"));
        Assert.assertNull(NumberUtils.toDoubleOrNull("aaa.95f"));

        Assert.assertNull(NumberUtils.toIntegerOrNull(null));
        Assert.assertNull(NumberUtils.toLongOrNull(null));
        Assert.assertNull(NumberUtils.toDoubleOrNull(null));
    }

    @Test(expected = NumberFormatException.class)
    public void toNumber_fail() {
        NumberUtils.toInteger(new Person());
    }
}
