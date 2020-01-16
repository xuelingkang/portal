package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.framework.data.impl.ElasticsearchDataImpl;
import com.xzixi.self.portal.framework.lock.ILock;
import com.xzixi.self.portal.framework.lock.impl.RedisLock;
import com.xzixi.self.portal.webapp.data.IArticleData;
import com.xzixi.self.portal.webapp.mapper.ArticleMapper;
import com.xzixi.self.portal.webapp.model.po.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.xzixi.self.portal.webapp.constant.RedisConstant.LOCK_PREFIX;

/**
 * @author 薛凌康
 */
@Service
public class ArticleDataImpl extends ElasticsearchDataImpl<ArticleMapper, Article> implements IArticleData {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected ILock getRemoveLock() {
        return new RedisLock(LOCK_PREFIX + "article:remove", redisTemplate);
    }

    @Override
    protected ILock getInitLock() {
        return new RedisLock(LOCK_PREFIX + "article:init", redisTemplate);
    }

    @Override
    protected ILock getSyncLock() {
        return new RedisLock(LOCK_PREFIX + "article:sync", redisTemplate);
    }
}
