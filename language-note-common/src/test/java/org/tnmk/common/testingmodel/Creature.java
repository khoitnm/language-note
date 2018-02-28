package org.tnmk.common.testingmodel;

import org.apache.commons.lang3.RandomUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.tnmk.common.util.reflection.ReflectionUtilsTest;

public class Creature {

    /**
     * Don't rename this field because it's used in test {@link ReflectionUtilsTest#getDeclaredFieldsIncludeSuperClasses()}
     */
    @NotBlank
    @Id
    private String creatureId = "id"+System.currentTimeMillis();

    public String getCreatureId() {
        return creatureId;
    }

    public void setCreatureId(String creatureId) {
        this.creatureId = creatureId;
    }
}
