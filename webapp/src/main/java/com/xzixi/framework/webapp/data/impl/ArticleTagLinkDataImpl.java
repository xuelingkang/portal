package com.xzixi.framework.webapp.data.impl;

import com.xzixi.framework.webapp.model.po.ArticleTagLink;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapp.data.IArticleTagLinkData;
import com.xzixi.framework.webapp.mapper.ArticleTagLinkMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "articleTagLink:base", casualCacheName = "articleTagLink:casual")
public class ArticleTagLinkDataImpl extends MybatisPlusDataImpl<ArticleTagLinkMapper, ArticleTagLink> implements IArticleTagLinkData {
}
