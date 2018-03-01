package org.tnmk.common.testingmodel.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.junit.Ignore;
import org.tnmk.common.util.json.JsonUtilsTest;
import org.tnmk.common.util.reflection.ReflectionUtilsTest;
import org.tnmk.common.utils.json.DeserializerEnumByField;
import org.tnmk.common.utils.json.SerializerEnumByField;

/**
 * This class is used for testing converting to Map, Json...
 * Note: don't change the extension to Creature class. It's used for testing in {@link ReflectionUtilsTest#findPropertyDescriptorsByAnnotationType()}
 * @author khoi.tran on 6/5/17.
 */
@Ignore
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = CreatureKind.KindSerializer.class)
@JsonDeserialize(using = CreatureKind.KindDeserializer.class)
public enum CreatureKind {
    /**
     * Don't change this value, it's used in {@link JsonUtilsTest#success_toJson_Object()}.
     */
    MUGGLE("Muggle"),
    SUPER_HERO("Super Hero"),
    WITCH("Witch");

    /**
     * Don't change this property name, it's used in {@link JsonUtilsTest#success_toJson_Object()}.
     */
    private final String displayName;

    public static class KindDeserializer extends DeserializerEnumByField<CreatureKind> {
        public KindDeserializer() {
            super(CreatureKind.class,"displayName");
        }
    }

    public static class KindSerializer extends SerializerEnumByField<CreatureKind> {
        protected KindSerializer() {
            super(CreatureKind.class,"displayName");
        }
    }

    CreatureKind(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}