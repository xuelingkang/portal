package com.xzixi.framework.content.service.impl;

import com.xzixi.framework.common.model.po.Article;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.content.data.IArticleData;
import com.xzixi.framework.content.service.IArticleService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<IArticleData, Article> implements IArticleService {
}
