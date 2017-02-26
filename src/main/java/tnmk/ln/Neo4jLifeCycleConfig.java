package tnmk.ln;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import tnmk.ln.app.common.entity.BaseEntity;

import java.util.Date;

/**
 * https://github.com/arebya/simple-neo4j/blob/master/src/main/java/org/simple/neo4j/config/ApplicationConfig.java
 *
 * @author khoi.tran on 2/19/17.
 */
@Configuration
@EnableNeo4jRepositories
public class Neo4jLifeCycleConfig {
    //        extends Neo4jConfiguration {
    @EventListener
    public void handleBeforeSaveEvent(BeforeSaveEvent event) {
//        Instant now = Instant.now();
        Date now = new Date();
        Object source = event.getSource();
        if (source instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) source;
//            if (entity.getCreatedDateTime() == null) {
//                entity.setCreatedDateTime(now);
//            }
//            entity.setUpdatedDateTime(now);
        }
    }

//    @Bean
//    ApplicationListener<BeforeSaveEvent> beforeSaveEventApplicationListener() {
//        return new ApplicationListener<BeforeSaveEvent>() {
//            @Override
//            public void onApplicationEvent(BeforeSaveEvent event) {
//                BaseEntity entity = (BaseEntity) event.getEntity();
//                entity.setCreatedDateTime(Instant.now());
//            }
//        };
//    }

//    @Bean
//    ApplicationListener<AfterSaveEvent> afterSaveEventApplicationListener() {
//        return new ApplicationListener<AfterSaveEvent>() {
//            @Override
//            public void onApplicationEvent(AfterSaveEvent event) {
//                AcmeEntity entity = (AcmeEntity) event.getEntity();
//                auditLog.onEventSaved(entity);
//            }
//        };
//    }
//
//    @Bean
//    ApplicationListener<BeforeDeleteEvent> beforeDeleteEventApplicationListener() {
//        return new ApplicationListener<BeforeDeleteEvent>() {
//            @Override
//            public void onApplicationEvent(BeforeDeleteEvent event) {
//                AcmeEntity entity = (AcmeEntity) event.getEntity();
//                auditLog.onEventDeleted(entity);
//            }
//        };
//    }

//    @Override
//    public SessionFactory getSessionFactory() {
//
//    }
}
