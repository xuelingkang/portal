package com.xzixi.framework.webapp.data.impl;

import com.xzixi.framework.webapp.model.po.ArticleTag;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapp.data.IArticleTagData;
import com.xzixi.framework.webapp.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "articleTag:base", casualCacheName = "articleTag:casual")
public class ArticleTagDataImpl extends MybatisPlusDataImpl<ArticleTagMapper, ArticleTag> implements IArticleTagData {
}
