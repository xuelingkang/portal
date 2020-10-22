package com.xzixi.framework.webapps.content.service.impl;

import com.xzixi.framework.webapps.common.model.po.Article;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.webapps.content.data.IArticleData;
import com.xzixi.framework.webapps.content.service.IArticleService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<IArticleData, Article> implements IArticleService {
}
