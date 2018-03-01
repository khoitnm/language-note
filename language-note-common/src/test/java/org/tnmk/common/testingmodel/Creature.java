package org.tnmk.common.testingmodel;

import org.apache.commons.lang3.RandomUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.tnmk.common.testingmodel.constants.CreatureKind;
import org.tnmk.common.util.json.JsonUtilsTest;
import org.tnmk.common.util.reflection.ReflectionUtilsTest;

public class Creature {

    /**
     * Don't rename this field because it's used in test {@link ReflectionUtilsTest#getDeclaredFieldsIncludeSuperClasses()}
     */
    @NotBlank
    @Id
    private String creatureId = "id"+System.currentTimeMillis();
    /**
     * Don't change this field name, it's used in {@link JsonUtilsTest#success_toJson_Object()}.
     */
    private CreatureKind creatureKind;

    public String getCreatureId() {
        return creatureId;
    }

    public void setCreatureId(String creatureId) {
        this.creatureId = creatureId;
    }

    public CreatureKind getCreatureKind() {
        return creatureKind;
    }

    public void setCreatureKind(CreatureKind creatureKind) {
        this.creatureKind = creatureKind;
    }
}
