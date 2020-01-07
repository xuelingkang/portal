package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.framework.data.impl.BaseDataImpl;
import com.xzixi.self.portal.webapp.data.IArticleTagLinkData;
import com.xzixi.self.portal.webapp.mapper.ArticleTagLinkMapper;
import com.xzixi.self.portal.webapp.model.po.ArticleTagLink;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "articleTagLink:base", casualCacheName = "articleTagLink:casual")
public class ArticleTagLinkDataImpl extends BaseDataImpl<ArticleTagLinkMapper, ArticleTagLink> implements IArticleTagLinkData {
}
