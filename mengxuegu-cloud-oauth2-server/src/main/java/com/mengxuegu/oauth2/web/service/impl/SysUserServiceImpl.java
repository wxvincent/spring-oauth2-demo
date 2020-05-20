package com.mengxuegu.oauth2.web.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.oauth2.web.entities.SysUser;
import com.mengxuegu.oauth2.web.mapper.SysUserMapper;
import com.mengxuegu.oauth2.web.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * ServiceImpl<M extends BaseMapper<T>, T> 是对 IService接口中方法的实现
 *     第1个泛型 M 指定继承了 BaseMapper接口的子接口
 *     第2个泛型 T 指定实体类
 * @Auther: 梦学谷 www.mengxuegu.com
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser findByUsername(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        // baseMapper 对应的是 SysUserMapper 实例
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public SysUser findByMobile(String mobile) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        // baseMapper 对应的是 SysUserMapper 实例
        return baseMapper.selectOne(queryWrapper);
    }

}
