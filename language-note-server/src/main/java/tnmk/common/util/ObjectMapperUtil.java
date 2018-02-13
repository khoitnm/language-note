package tnmk.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tnmk.common.exception.JsonConverterException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Provides functionality for reading and writing object to Json, String.
 * The converting to json will be helpful when working with messaging (e.g. SQS, SNS).
 */
public final class ObjectMapperUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ObjectMapperUtil.class);

    private ObjectMapperUtil() {
    }

    /**
     * @param objectMapper
     * @param object
     * @return this method wrap objectMapper toJson to throw detail error in case there are something wrong
     */
    public static String toJson(ObjectMapper objectMapper, Object object) {
        try {
            if (object == null) {
                return null;
            }
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            String msg = "Cannot convert object to json: " + toString(object);
            throw new JsonConverterException(msg, object, e);
        }
    }

    public static InputStream toJsonInputStream(ObjectMapper objectMapper, Object object) {
        try {
            if (object == null) {
                return null;
            }
            byte[] bytes = objectMapper.writeValueAsBytes(object);
            return new ByteArrayInputStream(bytes);
        } catch (JsonProcessingException e) {
            String msg = "Cannot convert object to json: " + toString(object);
            throw new JsonConverterException(msg, object, e);
        }
    }

    public static <T> T toObject(ObjectMapper objectMapper, String jsonString, Type type) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructType(type);
            return objectMapper.readValue(jsonString, javaType);
        } catch (Exception e) {
            String msg = String.format("Cannot json to object:\n\tType:%s\n\tJsonString:\t\n%s", type.getTypeName(), jsonString);
            throw new JsonConverterException(msg, jsonString, e);
        }
    }

    public static String toString(Object object) {
        if (object == null) {
            return null;
        }
        return ReflectionToStringBuilder.toString(object, ToStringStyle.DEFAULT_STYLE);
    }

    /**
     * This method is usually for logging only.
     *
     * @param object
     * @return
     */
    public static String toStringMultiLine(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return (String) object;
        }
        return ReflectionToStringBuilder.toString(object, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * This method is usually for logging only.
     *
     * @param list
     * @return
     */
    public static String toStringMultiLineForEachElement(List<?> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder("[\n");
        for (Object element : list) {
            result.append(toStringMultiLine(element));
            result.append(", \n");
        }
        result.append("\n]");
        return result.toString();
    }
}
