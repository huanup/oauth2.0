package com.mine.serve.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @Author : zhanghuan
 * @Date: 2021-11-13 23:54
 */
@Order(10)
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许表单提交
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()")
                .tokenKeyAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("c1")
                .secret(passwordEncoder.encode("c1"))
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .resourceIds("c1")
                .scopes("all")
                .redirectUris("http://www.baidu.com")
        .and()
                .withClient("c2")
                .secret(passwordEncoder.encode("c2"))
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .resourceIds("c2")
                .scopes("all")
                .redirectUris("http://www.baidu.com", "http://localhost:8083/login");

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //密码模式需要设置此认证管理器
//        endpoints.authenticationManager(authenticationManager);
        // 刷新令牌获取新令牌时需要
//        endpoints.userDetailsService(customUserDetailsService);
        //设置token存储策略
        endpoints.tokenStore(tokenStore).accessTokenConverter(jwtAccessTokenConverter);
        //授权码管理策略，针对授权码模式有效，会将授权码放到 auth_code 表，授权后就会删除它
//        endpoints.authorizationCodeServices(authorizationCodeServices());
    }
}
