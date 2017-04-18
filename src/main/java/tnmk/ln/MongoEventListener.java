package tnmk.ln;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import tnmk.common.util.ReflectionUtils;
import tnmk.ln.app.common.entity.BaseMongoEntity;

import java.time.Instant;
import java.util.Date;

/**
 * @author khoi.tran on 4/17/17.
 * @view http://www.baeldung.com/cascading-with-dbref-and-lifecycle-events-in-spring-data-mongodb
 */
public class MongoEventListener extends AbstractMongoEventListener<Object> {
    public static final Logger LOGGER = LoggerFactory.getLogger(MongoEventListener.class);

    /**
     * @param event
     * @explanation use {@link #onBeforeConvert(BeforeConvertEvent)}, don't use {@link #onBeforeSave(BeforeSaveEvent)} because all the setter into the entity will be useless at this phrases (I have not investigate to understand why yet)
     */
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        ReflectionUtils.traverseEntity(
                source
                , (ReflectionUtils.ActionStatus actionStatus) -> {
                    Object objectValue = actionStatus.getObjectValue();
                    if (objectValue == null) return false;
                    if (objectValue instanceof BaseMongoEntity) {
                        BaseMongoEntity baseMongoEntity = (BaseMongoEntity) objectValue;
                        if (baseMongoEntity.getCreatedDateTime() == null) {
                            baseMongoEntity.setCreatedDateTime(Instant.now());
                        } else {
                            baseMongoEntity.setUpdatedDateTime(Instant.now());
                        }
                        ObjectId id = new ObjectId(new Date());
                        baseMongoEntity.setId(id.toString());
                    }
                    return true;
                }
        );
    }

}
