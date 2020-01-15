package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.framework.data.impl.ElasticsearchDataImpl;
import com.xzixi.self.portal.framework.lock.ILock;
import com.xzixi.self.portal.framework.lock.impl.RedisLock;
import com.xzixi.self.portal.webapp.data.IArticleContentData;
import com.xzixi.self.portal.webapp.mapper.ArticleContentMapper;
import com.xzixi.self.portal.webapp.model.po.ArticleContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.xzixi.self.portal.webapp.constant.RedisConstant.LOCK_PREFIX;

/**
 * @author 薛凌康
 */
@Service
public class ArticleContentDataImpl extends ElasticsearchDataImpl<ArticleContentMapper, ArticleContent> implements IArticleContentData {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    @Qualifier("taskExecutor")
    private TaskExecutor taskExecutor;

    @Override
    protected ILock getRemoveLock() {
        return new RedisLock(LOCK_PREFIX + "articleContent:remove:%s", redisTemplate, taskExecutor);
    }

    @Override
    protected ILock getInitLock() {
        return new RedisLock(LOCK_PREFIX + "articleContent:init:%s", redisTemplate, taskExecutor);
    }

    @Override
    protected ILock getSyncLock() {
        return new RedisLock(LOCK_PREFIX + "articleContent:sync:%s", redisTemplate, taskExecutor);
    }
}
