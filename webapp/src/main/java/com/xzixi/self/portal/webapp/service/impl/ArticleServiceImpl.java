package com.xzixi.self.portal.webapp.service.impl;

import com.xzixi.self.portal.framework.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.self.portal.webapp.data.IArticleData;
import com.xzixi.self.portal.webapp.model.po.Article;
import com.xzixi.self.portal.webapp.service.IArticleService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<IArticleData, Article> implements IArticleService {
}
