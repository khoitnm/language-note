package org.tnmk.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.tnmk.common.util.testmodel.Person;
import org.tnmk.common.util.testmodel.PersonFactory;
import org.tnmk.common.util.testmodel.SimplePerson;
import org.tnmk.common.util.testmodel.SimplePersonFactory;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.utils.collections.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author khoi.tran on 6/7/17.
 */
public class MapUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(MapUtilsTest.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void success_toMap() {
        final Person object = PersonFactory.createJasonBourne();
        final Map<String, Object> map = MapUtils.toMap(OBJECT_MAPPER, object);
        Assert.assertEquals(30.5f, (Float) map.get("age"), 0.01);
    }

    @Test
    public void success_putAllIfAbsent() {
        final Map<String, Object> map = new HashMap<>();
        map.put("fieldA", "a");
        map.put("fieldB", "b");

        final Map<String, Object> map2 = new HashMap<>();
        map2.put("fieldC", "c");
        map2.put("fieldB", "b");

        MapUtils.putAllIfAbsent(map, map2);
        Assert.assertEquals(map2.get("fieldA"), "a");
    }

    @Test
    public void success_flatternMap() {
        final Person object = PersonFactory.createJasonBourne();
        final SimplePerson simplePerson = SimplePersonFactory.createJasonBourne();
        object.getProperties().put("child", simplePerson);
        Map<String, String> flatMap = MapUtils.toFlatMap(OBJECT_MAPPER, object);
        Assert.assertEquals(flatMap.get("properties.drug"), "Efferalgan");

        final Map<String, Object> map = MapUtils.toMap(OBJECT_MAPPER, object);
        flatMap = MapUtils.toFlatMap(OBJECT_MAPPER, map);
        Assert.assertEquals(flatMap.get("properties.child.age"), "30.5");
    }

}
