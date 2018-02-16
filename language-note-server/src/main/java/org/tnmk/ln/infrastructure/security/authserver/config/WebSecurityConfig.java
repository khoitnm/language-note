package org.tnmk.ln.infrastructure.security.authserver.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity(debug = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * <strong>Note:</strong>
     * If you want to configure whitelist for request to ResourceServer, please put it in the {@link org.tnmk.ln.infrastructure.security.resourceserver.config.ResourceServerConfig}, not here.
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .anyRequest().authenticated()
            //Should check whether the whitelist request is here or on ResourceServer.
            //...
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            .and().httpBasic().and().csrf().disable();
    }
}
