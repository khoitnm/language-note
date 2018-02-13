package org.tnmk.ln;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 * http://stackoverflow.com/questions/38742337/spring-data-neo4-beforesaveevent-deprecated
 *
 * @author khoi.tran on 2/19/17.
 */
@Configuration
@EnableTransactionManagement
public class MongoDBLifeCycleConfig {

    @Bean
    public MongoEventListener mongoEventListener() {
        return new MongoEventListener();
    }
}
