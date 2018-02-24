package org.tnmk.common.util.collections;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.util.testmodel.Person;
import org.tnmk.common.util.testmodel.PersonFactory;
import org.tnmk.common.utils.collections.SetUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 6/7/17.
 */
public class SetUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(SetUtilsTest.class);

    @Test
    public void success_distinct() {
        final List<Person> persons = PersonFactory.createPersons("Aa","Bb","Aa", "Cc");
        List<Person> distinctPersons = SetUtils.distinctListByProperty(persons, Person::getName).collect(Collectors.toList());
        Assert.assertEquals(3,distinctPersons.size());
    }


    @Test
    public void success_getTop() {
        Set<String> set = SetUtils.constructSet("Aaa","Bbb","Aaa","Ccc");
        Assert.assertEquals(3, set.size());

        Set<String> topSet = SetUtils.getTop(set, 2);
        Assert.assertEquals(2,topSet.size());
    }
}
