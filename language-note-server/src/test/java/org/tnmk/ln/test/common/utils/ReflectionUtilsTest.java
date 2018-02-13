package org.tnmk.ln.test.common.utils;

import org.junit.Assert;
import org.junit.Test;
import org.tnmk.common.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author khoi.tran on 3/7/17.
 */
public class ReflectionUtilsTest {

    List<Set<Map<Child, List<Group<Boy>>>>> entity = new ArrayList<>();

    public static class Child {}

    public static class Boy {}

    public static class Group<T> {}

    @Test
    public void test() throws NoSuchFieldException {
        Field entityField = ReflectionUtilsTest.class.getDeclaredField("entity");
        List<Class<?>> classes = ReflectionUtils.getParameterClasses(entityField);
        System.out.println(classes);
        Assert.assertTrue(classes.contains(Child.class));
        Assert.assertTrue(classes.contains(Boy.class));

    }
}
