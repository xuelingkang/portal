package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.framework.data.impl.BaseDataImpl;
import com.xzixi.self.portal.webapp.data.IArticleTagData;
import com.xzixi.self.portal.webapp.mapper.ArticleTagMapper;
import com.xzixi.self.portal.webapp.model.po.ArticleTag;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "articleTag:base", casualCacheName = "articleTag:casual")
public class ArticleTagDataImpl extends BaseDataImpl<ArticleTagMapper, ArticleTag> implements IArticleTagData {
}
