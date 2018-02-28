package org.tnmk.common.util.reflection;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.testingmodel.Person;
import org.tnmk.common.testingmodel.PersonFactory;
import org.tnmk.common.utils.collections.IterableUtils;
import org.tnmk.common.utils.collections.ListUtils;
import org.tnmk.common.utils.reflection.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 6/7/17.
 */
public class ReflectionUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtilsTest.class);

    @Test
    public void getDeclaredFieldsIncludeSuperClasses() {
        List<Field> fields = ReflectionUtils.getDeclaredFieldsIncludeSuperClasses(Person.class);
        long findCreatureIdField = fields.stream().filter(field -> field.getName().equals("creatureId")).count();
        Assert.assertEquals(1, findCreatureIdField);

        Assert.assertNotNull(ReflectionUtils.getPropertyDescriptor(Person.class,"creatureId"));
    }

}
