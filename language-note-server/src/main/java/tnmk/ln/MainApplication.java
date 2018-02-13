package tnmk.ln;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tnmk.common.infrastructure.data.mongo.repository.MongoRepoScanInclude;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepoScanInclude;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@ComponentScan({ "tnmk.ln", "tnmk.common", "tnmk.ln.infrastructure" })
//Have to add this annotation because the repositories in 'infrastructure' package are not in the inside the package of MainApplication
@EnableMongoRepositories(basePackages = { "tnmk.ln", "tnmk.common", "tnmk.ln.infrastructure" }, includeFilters = @ComponentScan.Filter(MongoRepoScanInclude.class))
@EnableNeo4jRepositories(basePackages = { "tnmk.ln", "tnmk.common", "tnmk.ln.infrastructure" }, includeFilters = @ComponentScan.Filter(Neo4jRepoScanInclude.class))
@EnableCaching
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
