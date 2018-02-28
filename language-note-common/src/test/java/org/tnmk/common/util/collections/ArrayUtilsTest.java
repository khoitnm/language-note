package org.tnmk.common.util.collections;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.testingmodel.Person;
import org.tnmk.common.testingmodel.PersonFactory;
import org.tnmk.common.utils.collections.ArrayUtils;

/**
 * @author khoi.tran on 6/7/17.
 */
public class ArrayUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(ArrayUtilsTest.class);

    @Test
    public void isEmpty_null() {
        Person[] people = null;
        Assert.assertTrue(ArrayUtils.isEmpty(people));
    }

    @Test
    public void isEmpty_empty() {
        Person[] people = new Person[0];
        Assert.assertTrue(ArrayUtils.isEmpty(people));
    }


    @Test
    public void isEmpty_notEmpty() {
        Person[] people = new Person[] {PersonFactory.createJasonBourne()};
        Assert.assertFalse(ArrayUtils.isEmpty(people));
    }
}
