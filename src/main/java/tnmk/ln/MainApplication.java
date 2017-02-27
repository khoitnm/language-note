package tnmk.ln;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan({ "tnmk.ln", "tnmk.common", "tnmk.ln.infrastructure" })
//Have to add this annotation because the repositories in 'infrastructure' package are not in the inside the package of MainApplication
//@EnableMongoRepositories({ "tnmk.ln", "tnmk.common", "tnmk.ln.infrastructure" })
@EntityScan({ "tnmk.ln", "tnmk.common", "tnmk.ln.infrastructure" })
@EnableNeo4jRepositories({ "tnmk.ln", "tnmk.common", "tnmk.ln.infrastructure" })
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
