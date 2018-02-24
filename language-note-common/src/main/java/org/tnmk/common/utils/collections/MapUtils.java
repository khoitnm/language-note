package org.tnmk.common.utils.collections;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import org.tnmk.common.exception.UnexpectedException;
import org.tnmk.common.utils.json.JsonUtils;
import org.tnmk.common.utils.reflection.ReflectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author khoi.tran on 8/17/16.
 */
public class MapUtils {

    /**
     * If field value is null, it will not be input into map
     *
     * @param list
     * @param fieldName
     * @param <T>
     * @return
     */
    public static <T> Map<Object, T> mapListByFieldName(List<T> list, String fieldName) {
        Map<Object, T> map = new HashMap<>();
        for (T item : list) {
            Object key = ReflectionUtils.readProperty(item, fieldName);
            if (key != null) {
                map.put(key, item);
            }
        }
        return map;
    }


    /**
     * This method is usually used by clients of content-service. We need to pass entity's data as Map objects.
     *
     * @param object the object need to convert
     * @return the converted map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(final ObjectMapper mapper, final Object object) {
        if (object == null) {
            return null;
        }
        return (Map<String, Object>) mapper.convertValue(object, Map.class);
    }

    /**
     * Put all entries from source to target map if the target doesn't have those fields.
     * If fields of sources already exists in target, those corresponding fields in target won't be overridden.
     *
     * @param source
     * @param target
     * @param <K>
     * @param <V>
     */
    public static <K, V> void putAllIfAbsent(final Map<K, V> source, final Map<K, V> target) {
        source.forEach(target::putIfAbsent);
    }

    /**
     * https://stackoverflow.com/questions/20355261/how-to-deserialize-json-into-flat-map-like-structure
     * <pre>
     * For example, convert following map:
     * Map<String, Object> map = {
     *      "field0":"val0",
     *      "subMap": {
     *          "field1": "val1",
     *          "field2": "val2"
     *      }
     * }
     * to Map<String,Object> newMap = {
     *      "field0": "val0"
     *      "subMap.field1":"val1",
     *      "subMap.field2":"val2",
     * }
     *
     * </pre>
     *
     * @param objectMapper
     * @param bean
     * @return
     */
    public static Map<String, String> toFlatMap(final ObjectMapper objectMapper, final Object bean) {
        final String json = JsonUtils.toJson(objectMapper, bean);
        try {
            final Map<String, String> map = new HashMap<>();
            setPropertiesToMap("", objectMapper.readTree(json), map);
            return map;
        } catch (final IOException e) {
            throw new UnexpectedException("Cannot convert bean " + bean + " to flat map: " + e.getMessage(), e);
        }
    }

    private static void setPropertiesToMap(final String currentPath, final JsonNode jsonNode,
                                           final Map<String, String> map) {
        if (jsonNode.isObject()) {
            final ObjectNode objectNode = (ObjectNode) jsonNode;
            final Iterator<Map.Entry<String, JsonNode>> iter = objectNode.fields();
            final String pathPrefix = currentPath.isEmpty() ? "" : currentPath + ".";

            while (iter.hasNext()) {
                final Map.Entry<String, JsonNode> entry = iter.next();
                setPropertiesToMap(pathPrefix + entry.getKey(), entry.getValue(), map);
            }
        } else if (jsonNode.isArray()) {
            final ArrayNode arrayNode = (ArrayNode) jsonNode;
            for (int i = 0; i < arrayNode.size(); i++) {
                setPropertiesToMap(currentPath + "[" + i + "]", arrayNode.get(i), map);
            }
        } else if (jsonNode.isValueNode()) {
            final ValueNode valueNode = (ValueNode) jsonNode;
            map.put(currentPath, valueNode.asText());
        }
    }
}
