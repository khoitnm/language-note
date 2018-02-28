package org.tnmk.common.util.reflection;

import org.hibernate.validator.constraints.NotBlank;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.tnmk.common.testingmodel.Person;
import org.tnmk.common.testingmodel.PersonFactory;
import org.tnmk.common.utils.reflection.ReflectionTraverseUtils;
import org.tnmk.common.utils.reflection.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author khoi.tran on 6/7/17.
 */
public class ReflectionTraverseUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(ReflectionTraverseUtilsTest.class);

    @Test
    public void travers(){
        //Setup data
        //---------------
        //HappyGuy:Person
        //|--children
        //      |--[0]:Person
        //      |   |--friends (List)
        //      |       |--[0]:Person
        //      |--[1]:Person
        Person happyGuy = PersonFactory.createPerson("Happy Guy","happy");
        List<Person> children = PersonFactory.createPersons("child1", "child2");
        happyGuy.getProperties().put("children", children);
        List<Person> friendsOfFirstChild = PersonFactory.createPersons("friendOfFirstChild");
        children.get(0).getProperties().put("friends",friendsOfFirstChild);

        //Set new value to properties of each persons in the HappyGuy
        ReflectionTraverseUtils.traverseEntity(
            happyGuy
            , (ReflectionTraverseUtils.ActionStatus actionStatus) -> {
                Object objectValue = actionStatus.getObjectValue();
                if (objectValue == null) return false;
                if (objectValue instanceof Person) {
                    Person person = (Person) objectValue;
                    person.getProperties().put("future","unknown");
                }
                return true;
            }
        );

        Assert.assertEquals("unknown", happyGuy.getProperties().get("future"));
        Assert.assertEquals("unknown", children.get(0).getProperties().get("future"));
        Assert.assertEquals("unknown", children.get(1).getProperties().get("future"));
        Assert.assertEquals("unknown", friendsOfFirstChild.get(0).getProperties().get("future"));

    }
}
