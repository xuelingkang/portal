package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.framework.data.impl.BaseDataImpl;
import com.xzixi.self.portal.webapp.data.IArticleData;
import com.xzixi.self.portal.webapp.mapper.ArticleMapper;
import com.xzixi.self.portal.webapp.model.po.Article;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "article:base", casualCacheName = "article:casual")
public class ArticleDataImpl extends BaseDataImpl<ArticleMapper, Article> implements IArticleData {
}
