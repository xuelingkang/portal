/**
 * 配合enhance模块的CacheEnhance注解，扩展了模糊删除缓存的功能
 *
 * 2020-01-09，删除了listByIds的缓存，在listByIds方法中循环调用getById方法，
 * 提高缓存的利用率，如果这种方式效率不行的话，就将这个包下的keyGenerator
 * 和RedisConstant以及enhance模块下的CacheEnhanceProcessor还原到2020-01-09之前的版本
 *
 * @author 薛凌康
 */
package com.xzixi.self.portal.webapp.config.redis;
