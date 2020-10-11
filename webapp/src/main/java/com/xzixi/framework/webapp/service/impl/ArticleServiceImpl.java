package com.xzixi.framework.webapp.service.impl;

import com.xzixi.framework.webapp.model.po.Article;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.webapp.data.IArticleData;
import com.xzixi.framework.webapp.service.IArticleService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<IArticleData, Article> implements IArticleService {
}
