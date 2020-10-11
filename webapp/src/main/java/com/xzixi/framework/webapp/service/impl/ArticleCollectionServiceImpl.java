package com.xzixi.framework.webapp.service.impl;

import com.xzixi.framework.webapp.model.po.ArticleCollection;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.webapp.data.IArticleCollectionData;
import com.xzixi.framework.webapp.service.IArticleCollectionService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleCollectionServiceImpl extends BaseServiceImpl<IArticleCollectionData, ArticleCollection> implements IArticleCollectionService {
}
