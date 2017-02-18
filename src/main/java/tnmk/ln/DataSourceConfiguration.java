package tnmk.ln;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource.el")
    public DataSource elDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "elTemplate")
    public JdbcTemplate elTemplate() {
        return new JdbcTemplate(elDataSource());
    }

}
