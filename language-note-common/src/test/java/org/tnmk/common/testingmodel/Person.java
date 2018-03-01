package org.tnmk.common.testingmodel;

import org.hibernate.validator.constraints.NotBlank;
import org.junit.Ignore;
import org.tnmk.common.testingmodel.constants.CreatureKind;
import org.tnmk.common.util.json.JsonUtilsTest;
import org.tnmk.common.util.reflection.ReflectionUtilsTest;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

/**
 * This class is used for testing converting to Map, Json...
 * Note: don't change the extension to Creature class. It's used for testing in {@link ReflectionUtilsTest#findPropertyDescriptorsByAnnotationType()}
 * @author khoi.tran on 6/5/17.
 */
@Ignore
public class Person extends Creature{
    private float age;
    @NotBlank
    @NotNull
    private String name;
    private LocalDate dob;
    private Instant initiatedAt;
    /**
     * Demo for map
     */
    private Map<Object, Object> properties;

    public Instant getInitiatedAt() {
        return initiatedAt;
    }

    public void setInitiatedAt(final Instant initiatedAt) {
        this.initiatedAt = initiatedAt;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(final LocalDate dob) {
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