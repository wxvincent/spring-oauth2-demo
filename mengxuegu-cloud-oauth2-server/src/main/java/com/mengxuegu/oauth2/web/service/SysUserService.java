package com.mengxuegu.oauth2.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mengxuegu.oauth2.web.entities.SysUser;

/**
 *  实现 IService<T> 接口，提供了常用更复杂的对 T 数据表的操作，
 *  比如：支持 Lambda 表达式，批量删除、自动新增或更新操作
 * @Auther: 梦学谷 www.mengxuegu.com
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 通过用户名查询
     * @param username 用户名
     * @return 用户信息
     */
    SysUser findByUsername(String username);

    /**
     * 通过手机号查询
     * @param mobile 手机号
     * @return 用户信息
     */
    SysUser findByMobile(String mobile);

}
