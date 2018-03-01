package org.tnmk.common.testingmodel;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Ignore;
import org.tnmk.common.testingmodel.constants.CreatureKind;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public static final String PROP_KEY_CHARACTERISTIC = "characteristic";
    public static final String PROP_VAL_CHARACTERISTIC_DEFAULT = "unknown";
    public static final String PROP_KEY_DRUG = "drug";
    public static final String PROP_VAL_DRUG_DEFAULT = "Efferalgan";


    public static final Person BATMAN = PersonFactory.createPerson("Batman","intelligent");
    public static final Person SUPERMAN = PersonFactory.createPerson("Superman","kindhearted");
    public static final Person WONDER_WOMAN = PersonFactory.createPerson("WonderWoman","fearlessness");
    public static final Person CATWOMAN = PersonFactory.createPerson("Catwoman","alluring");
    public static final Person FLASH = PersonFactory.createPerson("Flash","fast");

    private static final List<Person> LIST_5_SUPER_HEROES = Collections.unmodifiableList(Arrays.asList(
        BATMAN, SUPERMAN, WONDER_WOMAN, CATWOMAN,FLASH
    ));

    private PersonFactory() {
    }

    /**
     * <bold>Note:</bold> If name element is null, the Person result is null
     * @param names
     * @return
     */
    public static List<Person> createPersons(String... names) {
        return Arrays.stream(names).map(name -> createPerson(name)).collect(Collectors.toList());
    }
    public static List<Person> list5SuperHeroes(){
        return new ArrayList<>(LIST_5_SUPER_HEROES);
    }
    public static Person createJasonBourne() {
        return createPerson("Jason Bourne", "tough");
    }
    public static Person createPerson(String name) {
        return createPerson(name, PROP_VAL_CHARACTERISTIC_DEFAULT);
    }
    public static Person createPerson(String name, String characteristic) {
        if (name == null) return null;
        final Person testingPerson = new Person();
        testingPerson.setAge(RandomUtils.nextFloat(0.0f, 100.0f));
        testingPerson.setName(name);
        testingPerson.setDob(LocalDate.of(1971, 4, 15));
        testingPerson.setCreatureKind(CreatureKind.MUGGLE);
        testingPerson.setInitiatedAt(Instant.now());
        final Map<Object, Object> properties = new HashMap<>();
        final Person.InstinctWeapon instinctWeapon = new Person.InstinctWeapon();
        instinctWeapon.setValue("hand");
        properties.put("weapons", new Object[] { instinctWeapon, "pencil", "knife", "gun" });
        properties.put(PROP_KEY_DRUG, PROP_VAL_DRUG_DEFAULT);
        properties.put(PROP_KEY_CHARACTERISTIC, characteristic);
        testingPerson.setProperties(properties);
        return testingPerson;
    }
    public static Person createPersonWithNullName() {
        final Person testingPerson = new Person();
        testingPerson.setAge(RandomUtils.nextFloat(0.0f, 100.0f));
        testingPerson.setName(null);
        testingPerson.setDob(LocalDate.of(1971, 4, 15));
        testingPerson.setInitiatedAt(Instant.now());
        final Map<Object, Object> properties = new HashMap<>();
        final Person.InstinctWeapon instinctWeapon = new Person.InstinctWeapon();
        instinctWeapon.setValue("hand");
        properties.put("weapons", new Object[] { instinctWeapon, "pencil", "knife", "gun" });
        properties.put(PROP_KEY_DRUG, PROP_VAL_DRUG_DEFAULT);
        properties.put(PROP_KEY_CHARACTERISTIC, "null");
        testingPerson.setProperties(properties);
        return testingPerson;
    }
}