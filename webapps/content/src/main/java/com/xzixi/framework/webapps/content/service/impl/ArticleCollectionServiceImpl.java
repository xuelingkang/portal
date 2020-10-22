package com.xzixi.framework.webapps.content.service.impl;

import com.xzixi.framework.webapps.common.model.po.ArticleCollection;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.webapps.content.data.IArticleCollectionData;
import com.xzixi.framework.webapps.content.service.IArticleCollectionService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleCollectionServiceImpl extends BaseServiceImpl<IArticleCollectionData, ArticleCollection> implements IArticleCollectionService {
}
