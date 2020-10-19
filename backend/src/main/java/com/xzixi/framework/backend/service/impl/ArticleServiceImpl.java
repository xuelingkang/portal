package com.xzixi.framework.backend.service.impl;

import com.xzixi.framework.backend.model.po.Article;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.backend.data.IArticleData;
import com.xzixi.framework.backend.service.IArticleService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<IArticleData, Article> implements IArticleService {
}
