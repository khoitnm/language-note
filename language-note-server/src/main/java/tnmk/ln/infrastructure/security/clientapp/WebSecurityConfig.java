/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tnmk.ln.infrastructure.security.clientapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tnmk.ln.infrastructure.security.clientapp.service.UserAuthenticationService;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
//@EnableOAuth2Client
//@EnableAuthorizationServer
@Order(6)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserAuthenticationService userAuthenticationService;

//    @Autowired
//    OAuth2ClientContext oauth2ClientContext;

    @RequestMapping({ "/user", "/me" })
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", principal.getName());
        return map;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off

        //All requests are protected by default
        http.csrf().disable()
//                .httpBasic().disable()
                .antMatcher("/**").authorizeRequests()
                //Static resources are explicitly excluded
                .antMatchers(
                        "/**/*.css",
                        "/**/*.css.map",
                        "/**/*.js",
                        "/**/*.png",
                        "/**/*.svg",
                        "/**/*.jpg", "/**/fonts/**").permitAll()

                //The home page and login endpoints are explicitly excluded
                .antMatchers("/project-info**", "/swagger-ui.html**", "/web/login**", "/login**", "/web/register**", "/webjars/**", "/api/users").permitAll()

                //All other endpoints require an authenticated user
                .anyRequest().authenticated()

                //Unauthenticated users are re-directed to the home page
                .and().exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/web/login"))

                .and().formLogin().defaultSuccessUrl("/web/main", false).loginPage("/web/login").permitAll()

                .and().logout().logoutSuccessUrl("/web/login").permitAll()

//                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
        ;
        // @formatter:on
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthenticationService).passwordEncoder(passwordEncoder());
//        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }
//
//    @Configuration
//    @EnableResourceServer
//    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            // @formatter:off
//            http.antMatcher("/me").authorizeRequests().anyRequest().authenticated();
//            // @formatter:on
//        }
//    }

//    public static void main(String[] args) {
//        SpringApplication.run(SocialApplication.class, args);
//    }
//
//    @Bean
//    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(filter);
//        registration.setOrder(-100);
//        return registration;
//    }
//
//    @Bean
//    @ConfigurationProperties("github")
//    public ClientResources github() {
//        return new ClientResources();
//    }
//
//    @Bean
//    @ConfigurationProperties("facebook")
//    public ClientResources facebook() {
//        return new ClientResources();
//    }
//
//    private Filter ssoFilter() {
//        CompositeFilter filter = new CompositeFilter();
//        List<Filter> filters = new ArrayList<>();
//        filters.add(ssoFilter(facebook(), "/login/facebook"));
//        filters.add(ssoFilter(github(), "/login/github"));
//        filter.setFilters(filters);
//        return filter;
//    }
//
//    private Filter ssoFilter(ClientResources client, String path) {
//        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
//        OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
//        filter.setRestTemplate(template);
//        UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
//        tokenServices.setRestTemplate(template);
//        filter.setTokenServices(tokenServices);
//        return filter;
//    }
//
//    public static class ClientResources {
//
//        @NestedConfigurationProperty
//        private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();
//
//        @NestedConfigurationProperty
//        private ResourceServerProperties resource = new ResourceServerProperties();
//
//        public AuthorizationCodeResourceDetails getClient() {
//            return client;
//        }
//
//        public ResourceServerProperties getResource() {
//            return resource;
//        }
//    }

}

