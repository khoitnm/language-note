package org.tnmk.common.util.collections;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.util.testmodel.Person;
import org.tnmk.common.util.testmodel.PersonFactory;
import org.tnmk.common.utils.collections.ListUtils;
import org.tnmk.common.utils.reflection.expression.ComparatorByFields;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 6/7/17.
 */
public class ListUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(ListUtilsTest.class);

    @Test
    public void sortByField() {
        final List<Person> persons = PersonFactory.createPersons("Aa","Bb","aA","1", "Cc");
        ListUtils.sortByFields(persons,"name");
        Assert.assertEquals("1", persons.get(0).getName());
        Assert.assertEquals("Aa", persons.get(1).getName());
        Assert.assertEquals("Bb", persons.get(2).getName());
        Assert.assertEquals("Cc", persons.get(3).getName());
        Assert.assertEquals("aA", persons.get(4).getName());
    }

    @Test
    public void remove_hasRemainItems() {
        final List<Person> originalList = cloneTestList();
        int originalSize = originalList.size();
        List<Person> removingList = Arrays.asList(originalList.get(1), originalList.get(2), PersonFactory.createJasonBourne());

        List<Person> result = ListUtils.removeAll(originalList, removingList, new ComparatorByFields<>(new String[] {"name"}));
        Assert.assertEquals(originalSize, originalList.size());
        Assert.assertEquals(originalSize - 2, result.size());
        Assert.assertFalse(result.contains(removingList.get(0)));
        Assert.assertFalse(result.contains(removingList.get(1)));
    }

    @Test
    public void remove_hasNoMoreItem() {
        final List<Person> originalList = Arrays.asList(PersonFactory.BATMAN, PersonFactory.SUPERMAN);
        List<Person> removingList = Arrays.asList(PersonFactory.CAT_WOMAN,PersonFactory.SUPERMAN, PersonFactory.BATMAN);

        List<Person> result = ListUtils.removeAll(originalList, removingList, new ComparatorByFields<>(new String[] {"name"}));
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void addWithMaxSize_underCurrentSize() {
        final List<Person> originalList = Arrays.asList(PersonFactory.CAT_WOMAN,PersonFactory.SUPERMAN, PersonFactory.BATMAN);
        List<Person> addedListWith2Items = originalList.stream().collect(Collectors.toList());
        ListUtils.addToListWithMaxSize(addedListWith2Items, PersonFactory.WONDER_WOMAN, 2);
        Assert.assertEquals(2, addedListWith2Items.size());
        Assert.assertEquals(PersonFactory.BATMAN, addedListWith2Items.get(0));
        Assert.assertEquals(PersonFactory.WONDER_WOMAN, addedListWith2Items.get(1));
    }
    @Test
    public void addWithMaxSize_overCurrentSize() {
        final List<Person> originalList = Arrays.asList(PersonFactory.CAT_WOMAN,PersonFactory.SUPERMAN, PersonFactory.BATMAN);
        List<Person> addedListWith2Items = originalList.stream().collect(Collectors.toList());
        ListUtils.addToListWithMaxSize(addedListWith2Items, PersonFactory.WONDER_WOMAN, 10);
        Assert.assertEquals(4, addedListWith2Items.size());
        Assert.assertEquals(PersonFactory.CAT_WOMAN, addedListWith2Items.get(0));
        Assert.assertEquals(PersonFactory.SUPERMAN, addedListWith2Items.get(1));
        Assert.assertEquals(PersonFactory.BATMAN, addedListWith2Items.get(2));
        Assert.assertEquals(PersonFactory.WONDER_WOMAN, addedListWith2Items.get(3));
    }

    @Test
    public void getTop_underCurrentSize() {
        final List<Person> originalList = Arrays.asList(PersonFactory.CAT_WOMAN,PersonFactory.SUPERMAN, PersonFactory.BATMAN);
        List<Person> result = ListUtils.getTop(originalList, 2);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(PersonFactory.CAT_WOMAN, result.get(0));
        Assert.assertEquals(PersonFactory.SUPERMAN, result.get(1));
    }

    @Test
    public void getTop_overCurrentSize() {
        final List<Person> originalList = Arrays.asList(PersonFactory.CAT_WOMAN,PersonFactory.SUPERMAN, PersonFactory.BATMAN);
        List<Person> result = ListUtils.getTop(originalList, 10);
        Assert.assertEquals(originalList.size(), result.size());
    }
    private List<Person> cloneTestList(){
        List<Person> personList = new ArrayList<>();
        personList.addAll(PersonFactory.SUPER_HEROES);
        return personList;
    }
}
