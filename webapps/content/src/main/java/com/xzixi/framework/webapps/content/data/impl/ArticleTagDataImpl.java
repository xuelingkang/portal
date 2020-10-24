package com.xzixi.framework.webapps.content.data.impl;

import com.xzixi.framework.webapps.common.model.po.ArticleTag;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapps.content.data.IArticleTagData;
import com.xzixi.framework.webapps.content.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "articleTag:base", casualCacheName = "articleTag:casual")
public class ArticleTagDataImpl extends MybatisPlusDataImpl<ArticleTagMapper, ArticleTag> implements IArticleTagData {
}