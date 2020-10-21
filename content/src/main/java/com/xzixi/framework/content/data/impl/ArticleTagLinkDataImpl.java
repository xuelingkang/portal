package com.xzixi.framework.content.data.impl;

import com.xzixi.framework.common.model.po.ArticleTagLink;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.content.data.IArticleTagLinkData;
import com.xzixi.framework.content.mapper.ArticleTagLinkMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "articleTagLink:base", casualCacheName = "articleTagLink:casual")
public class ArticleTagLinkDataImpl extends MybatisPlusDataImpl<ArticleTagLinkMapper, ArticleTagLink> implements IArticleTagLinkData {
}
