package org.tnmk.common.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.BeanUtils;
import org.tnmk.common.utils.datatype.EnumUtils;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author khoi.tran on 2/9/17.
 */
public class DeserializerEnumByField<T extends Enum<T>> extends StdDeserializer<T> {
    /**
     * The default field contains enum value.
     */
    public static final String DEFAULT_ENUM_VAL_FIELD = "stringValue";
    private final Class<T> enumClass;
    private final Method fieldGetter;

    /**
     * @param enumClass
     */
    public DeserializerEnumByField(Class<T> enumClass) {
        this(enumClass, DEFAULT_ENUM_VAL_FIELD);
    }

    /**
     * @param enumClass
     * @param fieldName e.g. "stringValue"
     */
    public DeserializerEnumByField(Class<T> enumClass, String fieldName) {
        super(enumClass);
        this.enumClass = enumClass;
        this.fieldGetter = BeanUtils.getPropertyDescriptor(enumClass, fieldName).getReadMethod();
    }

    @Override
    public T deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        return EnumUtils.deserializeEnumValue(this.enumClass, this.fieldGetter, jp.getValueAsString());
    }

}