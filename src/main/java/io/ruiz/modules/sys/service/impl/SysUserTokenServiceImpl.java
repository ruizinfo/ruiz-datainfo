/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.ruiz.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.ruiz.common.utils.R;
import io.ruiz.modules.sys.config.UserConfig;
import io.ruiz.modules.sys.dao.SysUserTokenDao;
import io.ruiz.modules.sys.entity.SysUserTokenEntity;
import io.ruiz.modules.sys.oauth2.TokenGenerator;
import io.ruiz.modules.sys.service.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
    //12小时后过期
    private final static int EXPIRE = 3600 * 24 * 6;

    @Autowired
    private UserConfig userConfig;

    @Override
    public R createToken(long userId) {
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        SysUserTokenEntity userToken = this.getById(userId);

        if (userConfig.getLoginMode().equals(2)) {
            // 多点登录
            if (userToken == null || userToken.getExpireTime().before(now)) {
                if (userToken == null) {
                    userToken = new SysUserTokenEntity();
                    userToken.setUserId(userId);
                }
                userToken.setToken(TokenGenerator.generateValue());
                userToken.setUpdateTime(now);
                userToken.setExpireTime(expireTime);
            }
        } else {
            // 单点登录
            if (userToken == null) {
                userToken = new SysUserTokenEntity();
                userToken.setUserId(userId);
            }
            userToken.setToken(TokenGenerator.generateValue());
            userToken.setUpdateTime(now);
            userToken.setExpireTime(expireTime);
        }
        this.saveOrUpdate(userToken);

        R r = R.ok().put("token", userToken).put("expire", EXPIRE);

        return r;
    }

    @Override
    public void logout(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //修改token
        SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        this.updateById(tokenEntity);
    }
}
