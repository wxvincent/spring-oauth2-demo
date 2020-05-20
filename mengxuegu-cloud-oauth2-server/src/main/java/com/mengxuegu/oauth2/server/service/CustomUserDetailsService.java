package com.mengxuegu.oauth2.server.service;


import com.mengxuegu.oauth2.web.entities.SysUser;
import com.mengxuegu.oauth2.web.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 查询数据库中的用户信息
 * @Auther: 梦学谷 www.mengxuegu.com
 */
@Component("customUserDetailsService")
public class CustomUserDetailsService extends AbstractUserDetailsService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;

    @Override
    SysUser findSysUser(String usernameOrMobile){
        logger.info("请求认证的用户名：" + usernameOrMobile);
        return sysUserService.findByUsername(usernameOrMobile);
    }

}