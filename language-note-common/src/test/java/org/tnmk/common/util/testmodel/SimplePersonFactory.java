package org.tnmk.common.util.testmodel;

import org.junit.Ignore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author khoi.tran on 6/5/17.
 */
@Ignore
public final class SimplePersonFactory {
    private SimplePersonFactory() {
    }

    public static SimplePerson createJasonBourne() {
        final SimplePerson testingPerson = new SimplePerson();
        testingPerson.setAge(30.5f);
        testingPerson.setName("Jason Bourne");
        testingPerson.setDob(new Date());
        final Map<Object, Object> properties = new HashMap<>();
        final Person.InstinctWeapon instinctWeapon = new Person.InstinctWeapon();
        instinctWeapon.setValue("hand");
        properties.put("weapons", new Object[] { instinctWeapon });
        properties.put("drug", "Efferalgan");
        testingPerson.setProperties(properties);
        return testingPerson;
    }

}