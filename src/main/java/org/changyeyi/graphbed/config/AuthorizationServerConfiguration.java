package org.changyeyi.graphbed.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.util.Map;

/**
 * @author :    zj.wu
 * @date :      2019/09/02 10:49
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }

    @Bean
    public ClientDetailsService clientDetailsService(){
        BaseClientDetails insert = new BaseClientDetails("graph-bed", null, "insert", "password,refresh_token", null);
        insert.setClientSecret("graph-bed");
        InMemoryClientDetailsService inMemoryClientDetailsService = new InMemoryClientDetailsService();
        inMemoryClientDetailsService.setClientDetailsStore(Map.of("graph-bed",insert));
        return inMemoryClientDetailsService;
    }

    @Bean
    @Primary
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setClientDetailsService(clientDetailsService());
        defaultTokenServices.setAccessTokenValiditySeconds(60 * 60 * 12 * 2 * 30);
        defaultTokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
        return defaultTokenServices;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);
        endpoints.reuseRefreshTokens(true);
        endpoints.tokenServices(defaultTokenServices());
    }
}
