package org.tnmk.common.utils.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.exception.JsonConverterException;
import org.tnmk.common.utils.ToStringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Provides functionality for reading and writing object to Json, String.
 * The converting to json will be helpful when working with messaging (e.g. SQS, SNS).
 */
public final class JsonUtils {
    public static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);
    /**
     * This is the default object mapper, you may want to use another objectMapper based on your Spring context.
     */
    public static final ObjectMapper OBJECT_MAPPER = configureDefaultObjectMapper();
    private JsonUtils() {
        //Utils
    }

    public static ObjectMapper configureDefaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        DefaultSerializerProvider.Impl sp = new DefaultSerializerProvider.Impl();
        mapper.setSerializerProvider(sp);
        return mapper;
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
            String msg = "Cannot convert object to json: " + ToStringUtils.toString(object);
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
            String msg = "Cannot convert object to json: " + ToStringUtils.toString(object);
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
}
