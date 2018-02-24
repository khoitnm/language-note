package org.tnmk.ln.infrastructure.basemodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.tnmk.common.exception.UnexpectedException;
import org.tnmk.common.utils.datatype.NumberUtils;
import org.tnmk.common.utils.json.JsonUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <E> the type of Entity
 * @param <M> the type fo Model
 */
public abstract class BaseConverter<E, M> {
    public static final Logger LOGGER = LoggerFactory.getLogger(BaseConverter.class);
    private final Class<E> entityClass;
    private final Class<M> modelClass;
    private final PropertyDescriptor modelIdPropertyDescriptor;

    @SuppressWarnings("unchecked")
    public BaseConverter() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        entityClass = (Class<E>) types[0];
        modelClass = (Class<M>) types[1];
        modelIdPropertyDescriptor = getIdPropertyDescriptor(modelClass);
    }

    private PropertyDescriptor getIdPropertyDescriptor(Class<?> clazz) {
        PropertyDescriptor propertyDescriptor;
        try {
            propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, "id");
        } catch (BeansException ex) {
            propertyDescriptor = null;
        }
        return propertyDescriptor;
    }

    public void copyPropertiesWithoutAuditableTime(M source, E target) {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime");
    }

    public void toModel(E entity, M model) {
        BeanUtils.copyProperties(entity, model);
    }

    public List<Long> toLongIds(List<? extends M> models) {
        List<Long> result = new ArrayList<>();
        for (M model : models) {
            Long id = toLongId(model);
            if (id != null) {
                result.add(id);
            }
        }
        return result;
    }

    public Object toId(M model) {
        return toId(model, modelIdPropertyDescriptor);
    }

    public Object toId(Object object, PropertyDescriptor idPropertyDescriptor) {
        if (object == null || idPropertyDescriptor == null) {
            return null;
        }
        Object result;
        try {
            result = idPropertyDescriptor.getReadMethod().invoke(object);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            LOGGER.error("Cannot get id from object " + JsonUtils.toStringMultiLine(object), e);
            result = null;
        }
        return result;
    }

    public <T> List<Long> toLongIdsFromObjects(List<T> objects) {
        List<Long> result = new ArrayList<>();
        for (T model : objects) {
            result.add(toLongIdFromObject(model));
        }
        return result;
    }

    public <T> List<Integer> toIntegerIdsFromObjects(List<T> objects) {
        List<Integer> result = new ArrayList<>();
        for (T model : objects) {
            result.add(toIntegerIdFromObject(model));
        }
        return result;
    }

    public Integer toIntegerIdFromObject(Object object) {
        if (object == null) {
            return null;
        }
        PropertyDescriptor idPropertyDescriptor = getIdPropertyDescriptor(object.getClass());
        if (idPropertyDescriptor == null) {
            return null;
        }
        Object idValue = toId(object, idPropertyDescriptor);
        return NumberUtils.toIntegerIfPossible(idValue);
    }

    public Long toLongIdFromObject(Object object) {
        if (object == null) {
            return null;
        }
        PropertyDescriptor idPropertyDescriptor = getIdPropertyDescriptor(object.getClass());
        if (idPropertyDescriptor == null) {
            return null;
        }
        Object idValue = toId(object, idPropertyDescriptor);
        return NumberUtils.toLongIfPossible(idValue);
    }

    public Long toLongId(M model) {
        Object idValue = toId(model);
        return NumberUtils.toLongIfPossible(idValue);
    }

    public M toModel(E source) {
        if (source == null) {
            return null;
        }
        try {
            M target = modelClass.newInstance();
            toModel(source, target);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new UnexpectedException(
                    "Error when convert entity to model, cannot create new instance of class<"
                            + modelClass.getSimpleName() + ">, please check the default constructor of that class.", e);
        }
    }

    public E toEntity(M source) {
        if (source == null) {
            return null;
        }
        try {
            E target = entityClass.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new UnexpectedException(
                    "Error when convert model to entity, cannot create new instance of class<"
                            + entityClass.getSimpleName() + ">, please check the default constructor of that class.", e);
        }

    }

    public List<M> toModels(Iterable<E> entities) {
        if (entities == null) {
            return null;
        }
        List<M> result = new ArrayList<>();
        for (E entity : entities) {
            M model = toModel(entity);
            result.add(model);
        }
        return result;
    }

    public List<E> toEntities(List<M> models) {
        if (models == null) {
            return null;
        }
        List<E> result = new ArrayList<>();
        for (M model : models) {
            E entity = toEntity(model);
            result.add(entity);
        }
        return result;
    }

    public List<E> toEntities(M[] models) {
        if (models == null) {
            return null;
        }
        List<E> result = new ArrayList<>();
        for (M model : models) {
            E entity = toEntity(model);
            result.add(entity);
        }
        return result;
    }

    /**
     * It will copy value from model to entity except the fields like "id", "createTime".
     *
     * @param model  the model object.
     * @param entity the entity object.
     */
    public void mergeToEntity(M model, E entity) {
        BeanUtils.copyProperties(model, entity, "id", "createTime");
    }

    public Map<Long, M> toMapByLongId(List<? extends M> models) {
        Map<Long, M> result = new HashMap<>();
        for (M model : models) {
            if (model != null) {
                Long id = toLongId(model);
                result.put(id, model);
            }
        }
        return result;
    }

}
