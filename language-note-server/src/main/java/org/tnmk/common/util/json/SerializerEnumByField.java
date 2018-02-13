package org.tnmk.common.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.beans.BeanUtils;
import org.tnmk.common.exception.UnexpectedException;
import org.tnmk.common.util.StringUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author khoi.tran on 2/9/17.
 */
public class SerializerEnumByField<T extends Enum<T>> extends StdSerializer<T> {
    private final Class<T> enumClass;
    private final Method fieldGetter;

    /**
     * @param enumClass
     */
    public SerializerEnumByField(Class<T> enumClass) {
        this(enumClass, "stringValue");
    }

    /**
     * @param enumClass
     * @param fieldName e.g. "stringValue"
     */
    public SerializerEnumByField(Class<T> enumClass, String fieldName) {
        super(enumClass);
        this.enumClass = enumClass;
        this.fieldGetter = BeanUtils.getPropertyDescriptor(enumClass, fieldName).getReadMethod();
    }

    @Override
    public void serialize(T expressionType, JsonGenerator jgen, SerializerProvider swe) throws IOException {
        try {
            if (expressionType != null) {
                Object fieldValue = fieldGetter.invoke(expressionType);
                jgen.writeString(StringUtil.toString(fieldValue));
            } else {
                jgen.writeNull();
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new UnexpectedException(e.getMessage(), e);
        }
    }
}