package org.tnmk.common.util.collections;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.tnmk.common.testingmodel.Person;
import org.tnmk.common.testingmodel.PersonFactory;
import org.tnmk.common.utils.collections.IterableUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author khoi.tran on 6/7/17.
 */
public class IterableUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(IterableUtilsTest.class);

    @Test
    public void count_on_a_collection() {
        final List<Person> persons = PersonFactory.createPersons("Aa", "Bb", "aA", "1", "Cc");
        Assert.assertEquals(new Integer(5), IterableUtils.count(persons));
        Assert.assertEquals(new Integer(0), IterableUtils.count(Arrays.asList()));
        Assert.assertNull(IterableUtils.count(null));
    }

    @Test
    public void count_on_a_none_collection_type() {
        Iterable<Person> people = constructIterableWith5Elements();
        Assert.assertEquals(new Integer(5), IterableUtils.count(people));
    }

    @Test
    public void findByField() {
        final List<Person> persons = PersonFactory.list5SuperHeroes();
        Person shouldFoundPerson = persons.get(2);
        Person someGuyWithSameName = PersonFactory.createPerson(shouldFoundPerson.getName());
        someGuyWithSameName.setAge(3000);
        Assert.assertTrue(IterableUtils.containsByField(persons, someGuyWithSameName, "name"));
        Assert.assertFalse(IterableUtils.containsByField(persons, someGuyWithSameName, "age"));
    }


    @Test
    public void findItemsNotInFirstListByField() {
        final List<Person> personsA = PersonFactory.createPersons("d", "b", null, "a", "c");
        final List<Person> personsB = PersonFactory.createPersons("b", "c", "e", null);

        List<Person> notInListA = IterableUtils.findItemsNotInFirstListByField(personsA, personsB, "name");
        Assert.assertTrue(notInListA.contains(personsB.get(2)));//"e"
        Assert.assertTrue(notInListA.contains(null));//The null in listB is different from the null in listA
        Assert.assertEquals(2, notInListA.size());


        List<Person> notInListB = IterableUtils.findItemsNotInFirstListByField(personsB, personsA, "name");
        Assert.assertTrue(notInListB.contains(personsA.get(0)));//"d"
        Assert.assertTrue(notInListB.contains(personsA.get(3)));//"a"
        Assert.assertTrue(notInListB.contains(null));//The null in listB is different from the null in listA
        Assert.assertEquals(3, notInListB.size());

    }

    @Test
    public void removeByField() {
        final List<Person> persons = PersonFactory.list5SuperHeroes();
        Person shouldFoundPerson = persons.get(2);
        Person someGuyWithSameName = PersonFactory.createPerson(shouldFoundPerson.getName());
        someGuyWithSameName.setAge(3000);

        IterableUtils.removeItemsByFieldValue(persons, someGuyWithSameName, "name");
        Assert.assertFalse(persons.contains(someGuyWithSameName));
        Assert.assertEquals(4,persons.size());
    }
    @Test
    public void removeFirstItems() {
        Iterable<Person> persons = constructIterableWith5Elements();
        IterableUtils.removeFirstItems(persons, 4);
        Assert.assertEquals(1, IterableUtils.count(persons).intValue());
        Assert.assertEquals("Cc",IterableUtils.getFirst(persons).getName());

        persons = PersonFactory.createPersons("Aa", "Bb", "aA", "1", "Cc");
        IterableUtils.removeFirstItems(persons, 10);
        Assert.assertEquals(0, IterableUtils.count(persons).intValue());
    }

    @Test
    public void toListAndSet() {
        Iterable<Person> iterable = constructIterableWith5Elements();
        Assert.assertEquals(5, IterableUtils.toList(iterable).size());
        Assert.assertEquals(5, IterableUtils.toSet(iterable).size());
    }

    private Iterable<Person> constructIterableWith5Elements(){
        List<Person> persons = PersonFactory.createPersons("Aa", "Bb", "aA", "1", "Cc");
        return new PageImpl<>(persons);
    }
}
