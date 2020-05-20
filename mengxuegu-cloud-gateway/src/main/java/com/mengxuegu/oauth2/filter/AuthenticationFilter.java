package com.mengxuegu.oauth2.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class AuthenticationFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //从security上下文中获取认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //如果JWT令牌有效，则会解析用户信息封装到OAuth2Authentication对象中
        if (!(authentication instanceof OAuth2Authentication)) {
            return null;
        }

        //只有用户名，用户表中手机号，邮箱等都没有
        Object principal = authentication.getPrincipal();
        //此用户拥有的权限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> authoritySet = AuthorityUtils.authorityListToSet(authorities);
        //请求详情
        Object details = authentication.getDetails();

        //封装传输的数据
        Map<String, Object> result = new HashMap<>();
        result.put("principal", principal);
        result.put("details", details);
        result.put("authorities", authoritySet);

        try {
            RequestContext context = RequestContext.getCurrentContext();
            String base64 = Base64Utils.encodeToString(JSON.toJSONString(result).getBytes("UTF-8"));
            context.addZuulRequestHeader("auth-token", base64);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
