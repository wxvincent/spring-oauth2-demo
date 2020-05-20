package com.mengxuegu.oauth2.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mengxuegu.oauth2.web.entities.SysPermission;

import java.util.List;

/**
 * @Auther: 梦学谷 www.mengxuegu.com
 */
public interface SysPermissionService extends IService<SysPermission> {

    /**
     * @param userId 用户id
     * @return 用户所拥有的权限
     */
    List<SysPermission> findByUserId(Long userId);

}
