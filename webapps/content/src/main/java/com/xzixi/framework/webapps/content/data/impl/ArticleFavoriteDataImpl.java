package com.xzixi.framework.webapps.content.data.impl;

import com.xzixi.framework.webapps.common.model.po.ArticleFavorite;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapps.content.data.IArticleFavoriteData;
import com.xzixi.framework.webapps.content.mapper.ArticleFavoriteMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "articleFavorite:base", casualCacheName = "articleFavorite:casual")
public class ArticleFavoriteDataImpl extends MybatisPlusDataImpl<ArticleFavoriteMapper, ArticleFavorite> implements IArticleFavoriteData {
}