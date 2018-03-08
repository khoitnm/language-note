package org.tnmk.common.util.reflection;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.tnmk.common.exception.UnexpectedException;
import org.tnmk.common.testingmodel.Person;
import org.tnmk.common.testingmodel.PersonFactory;
import org.tnmk.common.testingmodel.Pet;
import org.tnmk.common.utils.collections.IterableUtils;
import org.tnmk.common.utils.collections.ListUtils;
import org.tnmk.common.utils.reflection.ReflectionTraverseUtils;
import org.tnmk.common.utils.reflection.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 6/7/17.
 */
public class ReflectionUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtilsTest.class);

    @Test
    public void getDeclaredFieldsIncludeSuperClasses() {
        List<Field> fields = ReflectionUtils.getDeclaredFieldsIncludeSuperClasses(Person.class);
        assertContainsField(fields, "creatureId", true);
    }

    @Test
    public void findPropertyDescriptorsByAnnotationType() {
        List<PropertyDescriptor> propertyDescriptors = ReflectionUtils.findPropertyDescriptorsByAnnotationType(Person.class, NotBlank.class);
        assertContainsPropertyDescriptor(propertyDescriptors, "name", true);
        assertContainsPropertyDescriptor(propertyDescriptors, "creatureId", true);

        List<Field> fields =  ReflectionUtils.findFieldsByAnnotationType(Person.class, NotBlank.class);
        assertContainsField(fields, "name", true);
        assertContainsField(fields, "creatureId", true);
    }

    @Test
    public void writeProperty() {
        Person person = PersonFactory.createPerson("Werewolf");
        ReflectionUtils.writeProperty(person,"age", 0.5f);
        Assert.assertEquals(0.5f, person.getAge(), 0.00001);
    }
    @Test(expected = IllegalArgumentException.class)
    public void writeProperty_error_IllegalArgumentException() {
        Person person = PersonFactory.createPerson("Werewolf");
        ReflectionUtils.writeProperty(person,"age", "xxx");
    }

    @Test(expected = UnexpectedException.class)
    public void writeProperty_error_UnexpectedException() {
        Person person = PersonFactory.createPerson("Werewolf");
        ReflectionUtils.writeProperty(person,"unknownProperty"+System.currentTimeMillis(), "xxx");
    }


    @Test
    public void findFieldByAnnotationType(){
        Field field = ReflectionUtils.findFieldByAnnotationType(Person.class, Id.class);
        Assert.assertEquals("creatureId",field.getName());
    }

    @Test
    public void isWrapperClass(){
        Assert.assertTrue(ReflectionUtils.isWrapperClass(Collection.class));
        Assert.assertTrue(ReflectionUtils.isWrapperClass(List.class));
        Assert.assertTrue(ReflectionUtils.isWrapperClass(Set.class));
        Assert.assertTrue(ReflectionUtils.isWrapperClass(Map.class));
        Assert.assertTrue(ReflectionUtils.isWrapperClass(new Object[] {}.getClass()));

        Assert.assertFalse(ReflectionUtils.isWrapperClass(Array.class));

        Assert.assertFalse(ReflectionUtils.isWrapperClass(String.class));
        Assert.assertFalse(ReflectionUtils.isWrapperClass(Person.class));
    }

    @Test
    public void isSimpleType(){
        Assert.assertTrue(ReflectionUtils.isSimpleType(Date.class));
        Assert.assertTrue(ReflectionUtils.isSimpleType(String.class));
        Assert.assertTrue(ReflectionUtils.isSimpleType(Integer.class));
        Assert.assertTrue(ReflectionUtils.isSimpleType(BigDecimal.class));

        Assert.assertFalse(ReflectionUtils.isSimpleType(Person.class));
        Assert.assertFalse(ReflectionUtils.isSimpleType(Collection.class));
    }

    @Test
    public void getParameterClasses() throws NoSuchFieldException {
        Field petsField = ReflectionUtils.findFieldByName(Person.class, "pets");
        List<Class<?>> parameterClasses = ReflectionUtils.getParameterClasses(petsField);
        Assert.assertEquals(Pet.class,parameterClasses.get(0));
        Assert.assertEquals(1,parameterClasses.size());
    }

    private void assertContainsPropertyDescriptor(List<PropertyDescriptor> propertyDescriptors, String propertyName, boolean expectResult){
        Assert.assertEquals(expectResult,propertyDescriptors.stream().filter(propertyDescriptor -> propertyDescriptor.getName().equals(propertyName)).count() > 0);
    }

    private void assertContainsField(List<Field> fields, String fieldName, boolean expectResult) {
        Assert.assertEquals(expectResult,fields.stream().filter(field -> field.getName().equals(fieldName)).count() > 0);
    }
}
