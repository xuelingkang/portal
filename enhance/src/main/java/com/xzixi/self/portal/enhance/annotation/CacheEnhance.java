package com.xzixi.self.portal.enhance.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 薛凌康
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface CacheEnhance {

    boolean getById() default true;
    boolean listByIds() default true;
    boolean getOne() default true;
    boolean list() default true;
    boolean listByMap() default true;
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
