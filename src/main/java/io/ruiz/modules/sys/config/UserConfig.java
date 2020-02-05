package io.ruiz.modules.sys.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Steed
 * @description TODO
 * @time 2019/7/17 14:51
 **/
@Data
@Component
@ConfigurationProperties("ruiz.user")
public class UserConfig {

    // 登录模式 1. 单点登录 2.多点登录
    private Integer loginMode;
}
