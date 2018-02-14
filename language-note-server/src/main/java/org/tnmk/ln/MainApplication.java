package org.tnmk.ln;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.tnmk.common.infrastructure.data.mongo.repository.MongoRepoScanInclude;
import org.tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;

@SpringBootApplication
/**
 * I have to add proxyTargetClass=true, otherwise, it cause problem with AOP and proxy on DefaultTokenServices:
 * <pre>
 *     The bean 'defaultTokenServices' could not be injected as a 'org.springframework.security.oauth2.provider.token.DefaultTokenServices' because it is a JDK dynamic proxy that implements:
 * </pre>
 */
@EnableAsync(proxyTargetClass = true)
@EnableCaching(proxyTargetClass = true)
//@EnableScheduling
@ComponentScan({"org.tnmk.ln", "org.tnmk.common", "org.tnmk.ln.infrastructure"})
//Have to add this annotation because the repositories in 'infrastructure' package are not in the inside the package of MainApplication
@EnableMongoRepositories(basePackages = {"org.tnmk.ln", "org.tnmk.common", "org.tnmk.ln.infrastructure"}, includeFilters = @ComponentScan.Filter(MongoRepoScanInclude.class))
@EnableNeo4jRepositories(basePackages = {"org.tnmk.ln", "org.tnmk.common", "org.tnmk.ln.infrastructure"}, includeFilters = @ComponentScan.Filter(Neo4jRepoScanInclude.class))
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
