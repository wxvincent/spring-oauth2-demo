package com.mengxuegu.oauth2.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

/**
 * 认证服务器配置类
 * @Auther: 梦学谷 www.mengxuegu.com
 */
@Configuration
@EnableAuthorizationServer // 开启了认证服务器
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;

    @Bean
    public ClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 配置被允许访问此认证服务器的客户端信息
     * 1.内存方式
     * 2. 数据库方式
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 内存方式管理客户端信息
        /*clients.inMemory()
                .withClient("mengxuegu-pc") // 客户端id
                .secret(passwordEncoder.encode("mengxuegu-secret")) // 加密，客户端密码
                .resourceIds("product-server") // 资源id，针对的是微服务名称，商品管理
                .authorizedGrantTypes("authorization_code", "password", "implicit", "client_credentials", "refresh_token")
                .scopes("all") // 授权范围标识，哪部分资源可访问（all只是标识，不是说所有资源）
                .autoApprove(false) // false 跳到一个授权页面手动点击授权，true不需要手动点授权，直接响应一个授权码
                .redirectUris("http://www.mengxuegu.com/")// 客户端回调地址
                .accessTokenValiditySeconds(60*60*8) //访问令牌有效时长 默认为12小时
                .refreshTokenValiditySeconds(60*60*24*60) // 刷新令牌有效时长,默认是30天
            ;*/
        // jdbc管理客户端
        clients.withClientDetails(jdbcClientDetailsService());

    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired // 刷新令牌
    private UserDetailsService customUserDetailsService;

    @Autowired // token管理方式，在TokenConfig类中已对添加到容器中了
    private TokenStore tokenStore;

//    @Autowired // 移到上面去
//    private DataSource dataSource;

    @Bean // 授权码管理策略
    public AuthorizationCodeServices jdbcAuthorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Autowired // jwt转换器
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    /**
     * 关于认证服务器端点配置
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // password 要这个 AuthenticationManager 实例
        endpoints.authenticationManager(authenticationManager);
        // 刷新令牌时需要使用
        endpoints.userDetailsService(customUserDetailsService);
        // 令牌的管理方式
        endpoints.tokenStore(tokenStore).accessTokenConverter(jwtAccessTokenConverter);
        // 授权码管理策略 会产生的授权码放到 oauth_code 表中，如果这个授权码已经使用了，则对应这个表中的数据就会被删除
        endpoints.authorizationCodeServices(jdbcAuthorizationCodeServices());
    }

    /**
     * 令牌端点的安全配置
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 认证后可访问 /oauth/token_key , 默认拒绝访问
        security.tokenKeyAccess("permitAll()");
        // 认证后可访问 /oauth/check_token , 默认拒绝访问
        security.checkTokenAccess("isAuthenticated()");
    }
}
