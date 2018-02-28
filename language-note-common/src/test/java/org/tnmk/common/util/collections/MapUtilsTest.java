package org.tnmk.common.util.collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.tnmk.common.testingmodel.Person;
import org.tnmk.common.testingmodel.PersonFactory;
import org.tnmk.common.testingmodel.Pet;
import org.tnmk.common.testingmodel.PetFactory;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.utils.collections.MapUtils;

import java.util.HashMap;
import java.util.List;
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
        Assert.assertEquals(object.getAge(), (Float) map.get("age"), 0.00001);
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
        Assert.assertEquals("a", map2.get("fieldA"));
    }

    @Test
    public void success_flattenMap() {
        final Person parentObject = PersonFactory.createJasonBourne();
        final Pet petObject = PetFactory.createDog();
        parentObject.getProperties().put("pet", petObject);
        //From object to flatMap
        Map<String, String> flatMap = MapUtils.toFlatMap(OBJECT_MAPPER, parentObject);
        Assert.assertEquals(PersonFactory.PROP_VAL_DRUG_DEFAULT, flatMap.get("properties."+PersonFactory.PROP_KEY_DRUG));
        Assert.assertEquals(PetFactory.PROP_VAL_DEFAULT_FOOD, flatMap.get("properties.pet.properties."+PetFactory.PROP_KEY_DEFAULT_FOOD));

        //To Map and to flatMap again
        final Map<String, Object> map = MapUtils.toMap(OBJECT_MAPPER, parentObject);
        flatMap = MapUtils.toFlatMap(OBJECT_MAPPER, map);
        Assert.assertEquals(PersonFactory.PROP_VAL_DRUG_DEFAULT, flatMap.get("properties."+PersonFactory.PROP_KEY_DRUG));
        Assert.assertEquals(PetFactory.PROP_VAL_DEFAULT_FOOD, flatMap.get("properties.pet.properties."+PetFactory.PROP_KEY_DEFAULT_FOOD));
    }


    @Test
    public void mapListByFieldName() {
        List<Person> personList = PersonFactory.list5SuperHeroes();
        Person personWithNullName = PersonFactory.createPersonWithNullName();
        personList.add(personWithNullName);
        Map<Object, Person> personMap = MapUtils.mapListByFieldName(personList, "name");
        Assert.assertEquals(5, personMap.size());
        Assert.assertEquals(PersonFactory.BATMAN, personMap.get(PersonFactory.BATMAN.getName()));
        Assert.assertEquals(PersonFactory.SUPERMAN, personMap.get(PersonFactory.SUPERMAN.getName()));
        Assert.assertEquals(PersonFactory.CATWOMAN, personMap.get(PersonFactory.CATWOMAN.getName()));
        Assert.assertEquals(PersonFactory.WONDER_WOMAN, personMap.get(PersonFactory.WONDER_WOMAN.getName()));
        Assert.assertEquals(PersonFactory.FLASH, personMap.get(PersonFactory.FLASH.getName()));
        Assert.assertFalse(personMap.values().contains(personWithNullName));
        Assert.assertNull(personMap.get(null));

    }

}
