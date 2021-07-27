/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2021  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.boot.redis.aspect;

import com.xzixi.framework.boot.core.exception.ClientException;
import com.xzixi.framework.boot.core.exception.ServerException;
import com.xzixi.framework.boot.redis.annotation.Limit;
import com.xzixi.framework.boot.redis.model.RedisLimit;
import com.xzixi.framework.boot.redis.service.RedisLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * LimitAspect
 * description
 *
 * @author xuelingkang
 * @version 1.0
 * @date 2021年07月27日
 */
@Aspect
public class RedisLimitAspect {

    private static final String UNKNOWN = "unknown";

    @Autowired
    private ApplicationContext applicationContext;

    private Map<Limit.Strategy, RedisLimiter> strategyLimiterMap;

    @PostConstruct
    public void init() {
        strategyLimiterMap = new HashMap<>(4);
        Map<String, RedisLimiter> limiterMap = applicationContext.getBeansOfType(RedisLimiter.class);
        limiterMap.forEach((key, limiter) -> strategyLimiterMap.put(limiter.strategy(), limiter));
    }

    @Around("execution(public * *(..)) && @annotation(com.xzixi.framework.boot.redis.annotation.Limit)")
    public Object checkLimit(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Limit limit = method.getAnnotation(Limit.class);
        Limit.Strategy strategy = limit.strategy();
        Limit.Type type = limit.type();

        String key;
        if (type == Limit.Type.IP) {
            key = getIpAddress();
        } else if (type == Limit.Type.KEY) {
            key = limit.key();
        } else {
            key = method.getName();
        }

        RedisLimit params = new RedisLimit(key, limit.period(), limit.rate(), limit.count(), limit.capacity(), limit.timeout());

        try {
            boolean result = strategyLimiterMap.get(strategy).check(params);
            if (result) {
                return pjp.proceed();
            } else {
                throw new ClientException(500, "服务繁忙！");
            }
        } catch (ClientException e) {
            throw e;
        } catch (Throwable e) {
            throw new ServerException(e);
        }
    }

    public String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
