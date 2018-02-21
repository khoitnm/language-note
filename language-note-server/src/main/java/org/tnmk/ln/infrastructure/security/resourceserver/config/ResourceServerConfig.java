package org.tnmk.ln.infrastructure.security.resourceserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.tnmk.ln.infrastructure.security.resourceserver.error.CustomOAuth2AccessDeniedHandler;


@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    /**
     * Reuse the same bean of TokenService which was defined in {@link org.tnmk.ln.infrastructure.security.authserver.config.AuthorizationServerConfig}
     */
    @Autowired
    @Qualifier("tokenServices")
    private DefaultTokenServices tokenServices;

    /**
     * Reuse the same bean TokenService which was defined in AuthorizationServer
     */
    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
            .resourceId(resourceId)
            .tokenServices(tokenServices)
            .tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
//            .requestMatcher(new OAuthRequestedMatcher())
//                .anonymous().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            .antMatchers("/project-info").permitAll()

            //This is the API for looking up vocabularies meaning, so we don't need an authenticated user permission.
            .antMatchers("/api/expression-in-page:memorize**").permitAll()

            //FIXME At this moment, on JavaScript (client app) I have not found the solution for getting Audio(url) with Authorization header, so I temporary put media link to the whitelist.
            .antMatchers("/api/tts**").permitAll()
            //For images/sounds... we allow all!
            .antMatchers("/api/files/**").permitAll()

            .antMatchers("/api/me").hasAnyRole("USER", "ADMIN")
            .antMatchers("/api/register").hasAuthority("ROLE_REGISTER")
            .anyRequest().authenticated()


            //Guideline in http://www.baeldung.com/spring-security-custom-access-denied-page
            .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        ;
    }
    //Guideline in http://www.baeldung.com/spring-security-custom-access-denied-page
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomOAuth2AccessDeniedHandler();
    }

    /**
     * Explanation why we use it: http://sgdev-blog.blogspot.ca/2016/04/spring-oauth2-with-jwt-sample.html
     */
//    private static class OAuthRequestedMatcher implements RequestMatcher {
//        public boolean matches(HttpServletRequest request) {
//            String auth = request.getHeader("Authorization");
//            // Determine if the client request contained an OAuth Authorization
//            boolean haveOauth2Token = (auth != null) && auth.startsWith("Bearer");
//            boolean haveAccessToken = request.getParameter("access_token")!=null;
//            return haveOauth2Token || haveAccessToken;
//        }
//    }


}
