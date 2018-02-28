package org.tnmk.common.testingmodel;

import org.junit.Ignore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author khoi.tran on 6/5/17.
 */
@Ignore
public final class PetFactory {
    public static final String DEFAULT_NAME = "Husky";
    public static final float DEFAULT_AGE = 3.5f;
    public static final String PROP_KEY_DEFAULT_WEAPONS = "weapons";
    public static final String PROP_VAL_DEFAULT_WEAPON = "craw";
    public static final String PROP_KEY_DEFAULT_FOOD = "food";
    public static final String PROP_VAL_DEFAULT_FOOD = "meat";


    private PetFactory() {
    }

    public static Pet createDog() {
        final Pet pet = new Pet();
        pet.setAge(DEFAULT_AGE);
        pet.setName(DEFAULT_NAME);
        pet.setDob(new Date());
        final Map<Object, Object> properties = new HashMap<>();
        properties.put(PROP_KEY_DEFAULT_WEAPONS, new Object[] {PROP_VAL_DEFAULT_WEAPON});
        properties.put(PROP_KEY_DEFAULT_FOOD, PROP_VAL_DEFAULT_FOOD);
        pet.setProperties(properties);
        return pet;
    }

}