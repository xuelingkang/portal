package com.xzixi.framework.webapps.common.component;

import com.xzixi.framework.webapps.common.service.ISignService;
import com.xzixi.framework.webapps.common.service.impl.Md5SignServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author xuelingkang
 * @date 2020-10-26
 */
@Configuration
public class Components {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ISignService md5SignService() {
        return new Md5SignServiceImpl();
    }
}
