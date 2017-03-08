package tnmk.ln.test.common.utils;

import org.junit.Assert;
import org.junit.Test;
import tnmk.common.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author khoi.tran on 3/7/17.
 */
public class ReflectionUtilsTest {

    List<Set<Map<String, List<Integer>>>> entity = new ArrayList<>();

    @Test
    public void test() throws NoSuchFieldException {
        Field entityField = ReflectionUtilsTest.class.getDeclaredField("entity");
        List<Class<?>> classes = ReflectionUtils.getParameterClasses(entityField);
        System.out.println(classes);
        Assert.assertTrue(classes.contains(String.class));
        Assert.assertTrue(classes.contains(Integer.class));

    }
}
