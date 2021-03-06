package org.tnmk.ln.infrastructure.security.authserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.tnmk.common.exception.UnexpectedException;
import org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter.EnhancedJwtTokenConverter;
import org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter.SecretKeyProvider;
import org.tnmk.ln.infrastructure.security.authserver.config.userdetails.UserDetailsAuthenticationService;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    @Value("${access_token.validity_period}")
    private int accessTokenValiditySeconds;

    @Value("${refresh_token.validity_period}")
    private int refreshTokenValiditySeconds;

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private UserDetailsService userDetailsService;

    /**
     * Note: The UserDetailsService bean was declare in {@link UserDetailsAuthenticationService}
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .authenticationManager(this.authenticationManager)
            //See the method comment to know where's the userDetailsService comes from.
            //Actually, we don't even need this, this injection of userDetailsService just let you know that we declared it somewhere else.
            .userDetailsService(userDetailsService)
            .tokenServices(tokenServices())
            .tokenStore(tokenStore())
            .accessTokenConverter(accessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
            .tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')")
            .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.
        clients.inMemory()
            .withClient("normal-app")
            .authorizedGrantTypes("authorization_code", "implicit")
            .authorities("ROLE_CLIENT")
            .scopes("read", "write")
            .resourceIds(resourceId)
            .accessTokenValiditySeconds(accessTokenValiditySeconds)
            .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
            .and()
            .withClient("trusted-app")
            .authorizedGrantTypes("client_credentials", "password", "refresh_token")
            .authorities("ROLE_TRUSTED_CLIENT")
            .scopes("read", "write")
            .resourceIds(resourceId)
            .accessTokenValiditySeconds(accessTokenValiditySeconds)
            .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
            .secret("secret")
            .and()
            .withClient("register-app")
            .authorizedGrantTypes("client_credentials")
            .authorities("ROLE_REGISTER")
            .scopes("read")
            .resourceIds(resourceId)
            .secret("secret")
            .and()
            .withClient("my-client-with-registered-redirect")
            .authorizedGrantTypes("authorization_code")
            .authorities("ROLE_CLIENT")
            .scopes("read", "trust")
            .resourceIds("oauth2-resource")
            .redirectUris("http://anywhere?key=value");
    }

    @Bean
    public TokenStore tokenStore() {
//        return new JwkTokenStore()
        return new JwtTokenStore(accessTokenConverter());
    }

    //    @Autowired
    private SecretKeyProvider keyProvider = new SecretKeyProvider();

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new EnhancedJwtTokenConverter();
        try {
            converter.setSigningKey(keyProvider.getKey());
        } catch (URISyntaxException | KeyStoreException | NoSuchAlgorithmException | IOException | UnrecoverableKeyException | CertificateException e) {
            throw new UnexpectedException(e.getMessage(), e);
        }
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenEnhancer(accessTokenConverter());//Maybe not need
        return defaultTokenServices;
    }

    //CORS CONFIG: BEGIN ////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * TODO Should only allow our application.
     * @return
     */
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = constructUrlBasedCorsConfigurationSource();
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);//It must be
        return bean;
    }
    
    private UrlBasedCorsConfigurationSource constructUrlBasedCorsConfigurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", constructAllowAllCorsConfiguration());
        return source;
    }

    private CorsConfiguration constructAllowAllCorsConfiguration(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        return config;
    }
    //CORS CONFIG: END ////////////////////////////////////////////////////////////////////////////////////////////
}
