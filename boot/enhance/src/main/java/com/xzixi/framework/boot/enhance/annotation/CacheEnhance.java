package com.xzixi.framework.boot.enhance.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主要用于自动维护数据库和缓存的一致性，直接用在数据层的实现类上
 *
 * @author 薛凌康
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface CacheEnhance {

    String baseCacheName();
    String casualCacheName();
    boolean getById() default true;
    boolean listByIds() default true;
    boolean getOne() default true;
    boolean list() default true;
    boolean page() default true;
    boolean count() default true;
    boolean updateById() default true;
    boolean updateBatchById() default true;
    boolean save() default true;
    boolean saveBatch() default true;
    boolean saveOrUpdate() default true;
    boolean saveOrUpdateBatch() default true;
    boolean removeById() default true;
    boolean removeByIds() default true;
}
