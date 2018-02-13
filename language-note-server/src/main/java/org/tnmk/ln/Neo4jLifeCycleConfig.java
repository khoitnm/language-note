package org.tnmk.ln;

import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.session.event.Event;
import org.neo4j.ogm.session.event.EventListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.tnmk.ln.app.common.entity.BaseNeo4jEntity;

import java.util.Date;

//import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 * http://stackoverflow.com/questions/38742337/spring-data-neo4-beforesaveevent-deprecated
 *
 * @author khoi.tran on 2/19/17.
 */
@Configuration
@EnableTransactionManagement
public class Neo4jLifeCycleConfig {
    @Autowired
    private SessionFactory sessionFactory;

    @Bean
    public Neo4jTransactionManager transactionManager() throws Exception {
        sessionFactory.register(new PreSaveListener());
        return new Neo4jTransactionManager(sessionFactory);
    }

    public static class PreSaveListener extends EventListenerAdapter {
        @Override
        public void onPreSave(Event event) {
            Object eventObject = event.getObject();
            if (eventObject instanceof BaseNeo4jEntity) {
                BaseNeo4jEntity baseEntity = (BaseNeo4jEntity) eventObject;
                if (baseEntity.getCreatedDateTime() == null) {
                    baseEntity.setCreatedDateTime(new Date());
                } else {
                    baseEntity.setUpdatedDateTime(new Date());
                }
            }
        }
    }
}
