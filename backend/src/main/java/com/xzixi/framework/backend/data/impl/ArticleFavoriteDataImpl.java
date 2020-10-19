package com.xzixi.framework.backend.data.impl;

import com.xzixi.framework.backend.model.po.ArticleFavorite;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.backend.data.IArticleFavoriteData;
import com.xzixi.framework.backend.mapper.ArticleFavoriteMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "articleFavorite:base", casualCacheName = "articleFavorite:casual")
public class ArticleFavoriteDataImpl extends MybatisPlusDataImpl<ArticleFavoriteMapper, ArticleFavorite> implements IArticleFavoriteData {
}
