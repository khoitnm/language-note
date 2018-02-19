package org.tnmk.common.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.BeanUtils;
import org.tnmk.common.exception.UnexpectedException;
import org.tnmk.common.utils.datatype.StringUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author khoi.tran on 2/9/17.
 */
public class DeserializerEnumByField<T extends Enum<T>> extends StdDeserializer<T> {
    private final Class<T> enumClass;
    private final Method fieldGetter;

    /**
     * @param enumClass
     */
    public DeserializerEnumByField(Class<T> enumClass) {
        this(enumClass, "stringValue");
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
        return deserialize(this.enumClass, this.fieldGetter, jp.getValueAsString());
    }

    public static <T extends Enum<T>> T deserialize(Class<T> enumClass, String enumFieldName, String enumFieldValue) {
        Method fieldGetter = BeanUtils.getPropertyDescriptor(enumClass, enumFieldName).getReadMethod();
        return deserialize(enumClass, fieldGetter, enumFieldValue);
    }

    public static <T extends Enum<T>> T deserialize(Class<T> enumClass, Method fieldGetter, String enumFieldValue) {
        if (enumFieldValue == null) {
            return null;
        }
        try {
            for (T ienum : enumClass.getEnumConstants()) {
                Object fieldValue = fieldGetter.invoke(ienum);
                String fieldStringValue = StringUtils.toString(fieldValue);
                if (enumFieldValue.trim().equalsIgnoreCase(fieldStringValue)) {
                    return ienum;
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new UnexpectedException("Cannot get value from " + enumClass + "." + fieldGetter.getName() + "()", e);
        }
        return null;
    }
}