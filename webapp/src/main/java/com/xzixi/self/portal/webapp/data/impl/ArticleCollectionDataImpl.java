package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.framework.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.framework.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.self.portal.webapp.data.IArticleCollectionData;
import com.xzixi.self.portal.webapp.mapper.ArticleCollectionMapper;
import com.xzixi.self.portal.webapp.model.po.ArticleCollection;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "articleCollection:base", casualCacheName = "articleCollection:casual")
public class ArticleCollectionDataImpl extends MybatisPlusDataImpl<ArticleCollectionMapper, ArticleCollection> implements IArticleCollectionData {
}
