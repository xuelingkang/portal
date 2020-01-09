package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.framework.data.impl.MybatisPlusDataImpl;
import com.xzixi.self.portal.webapp.data.IArticleFavoriteData;
import com.xzixi.self.portal.webapp.mapper.ArticleFavoriteMapper;
import com.xzixi.self.portal.webapp.model.po.ArticleFavorite;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "articleFavorite:base", casualCacheName = "articleFavorite:casual")
public class ArticleFavoriteDataImpl extends MybatisPlusDataImpl<ArticleFavoriteMapper, ArticleFavorite> implements IArticleFavoriteData {
}
