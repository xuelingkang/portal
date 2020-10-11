package com.xzixi.framework.webapp.data.impl;

import com.xzixi.framework.webapp.model.po.ArticleFavorite;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapp.data.IArticleFavoriteData;
import com.xzixi.framework.webapp.mapper.ArticleFavoriteMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "articleFavorite:base", casualCacheName = "articleFavorite:casual")
public class ArticleFavoriteDataImpl extends MybatisPlusDataImpl<ArticleFavoriteMapper, ArticleFavorite> implements IArticleFavoriteData {
}
