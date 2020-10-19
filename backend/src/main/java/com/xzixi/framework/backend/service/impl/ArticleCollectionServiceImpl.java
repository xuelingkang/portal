package com.xzixi.framework.backend.service.impl;

import com.xzixi.framework.backend.model.po.ArticleCollection;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.backend.data.IArticleCollectionData;
import com.xzixi.framework.backend.service.IArticleCollectionService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleCollectionServiceImpl extends BaseServiceImpl<IArticleCollectionData, ArticleCollection> implements IArticleCollectionService {
}
