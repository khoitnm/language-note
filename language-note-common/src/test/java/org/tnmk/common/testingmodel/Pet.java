package org.tnmk.common.testingmodel;

import org.junit.Ignore;

import java.util.Date;
import java.util.Map;

/**
 * This class is used for testing converting to Map, Json...
 *
 * @author khoi.tran on 6/5/17.
 */
@Ignore
public class Pet extends Creature{
    private float age;
    private String name;
    private Date dob;
    /**
     * Demo for map
     */
    private Map<Object, Object> properties;

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Demo static class
     */
    public static class InstinctWeapon {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(final String value) {
            this.value = value;
        }
    }

    public float getAge() {
        return age;
    }

    public void setAge(final float age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Map<Object, Object> getProperties() {
        return properties;
    }

    public void setProperties(final Map<Object, Object> properties) {
        this.properties = properties;
    }

}