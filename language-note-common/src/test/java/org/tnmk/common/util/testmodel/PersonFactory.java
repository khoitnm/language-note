package org.tnmk.common.util.testmodel;

import org.junit.Ignore;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 6/5/17.
 */
@Ignore
public final class PersonFactory {
    public static final Person BATMAN = PersonFactory.createPerson("Batman");
    public static final Person SUPERMAN = PersonFactory.createPerson("Superman");
    public static final Person WONDER_WOMAN = PersonFactory.createPerson("WonderWoman");
    public static final Person CAT_GIRL = PersonFactory.createPerson("CatGirl");
    public static final List<Person> SUPER_HEROES = Collections.unmodifiableList(Arrays.asList(
        BATMAN, SUPERMAN, WONDER_WOMAN, CAT_GIRL
    ));

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