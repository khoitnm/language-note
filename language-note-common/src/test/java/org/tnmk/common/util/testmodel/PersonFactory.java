package org.tnmk.common.util.testmodel;

import org.junit.Ignore;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 6/5/17.
 */
@Ignore
public final class PersonFactory {
    private PersonFactory() {
    }
    public static List<Person> createPersons(String... names) {
        return Arrays.stream(names).map(name -> createPerson(name)).collect(Collectors.toList());
    }

    public static Person createJasonBourne() {
        return createPerson("Jason Bourne");
    }
    public static Person createPerson(String name) {
        final Person testingPerson = new Person();
        testingPerson.setAge(30.5f);
        testingPerson.setName(name);
        testingPerson.setDob(LocalDate.of(1971, 4, 15));
        testingPerson.setInitiatedAt(Instant.now());
        final Map<Object, Object> properties = new HashMap<>();
        final Person.InstinctWeapon instinctWeapon = new Person.InstinctWeapon();
        instinctWeapon.setValue("hand");
//        properties.put("weapons", new Object[] { instinctWeapon, "pencil", "knife", "gun" });
        properties.put("drug", "Efferalgan");
        testingPerson.setProperties(properties);
        return testingPerson;
    }

}