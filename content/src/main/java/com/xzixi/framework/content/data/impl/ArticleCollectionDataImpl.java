package com.xzixi.framework.content.data.impl;

import com.xzixi.framework.common.model.po.ArticleCollection;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.content.data.IArticleCollectionData;
import com.xzixi.framework.content.mapper.ArticleCollectionMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "articleCollection:base", casualCacheName = "articleCollection:casual")
public class ArticleCollectionDataImpl extends MybatisPlusDataImpl<ArticleCollectionMapper, ArticleCollection> implements IArticleCollectionData {
}
