package org.tnmk.common.util.testmodel;

import org.junit.Ignore;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author khoi.tran on 6/5/17.
 */
@Ignore
public final class PersonFactory {
    private PersonFactory() {
    }

    public static Person createJasonBourne() {
        final Person testingPerson = new Person();
        testingPerson.setAge(30.5f);
        testingPerson.setName("Jason Bourne");
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