package com.xzixi.framework.backend.service.impl;

import com.xzixi.framework.backend.data.IArticleContentData;
import com.xzixi.framework.backend.model.po.ArticleContent;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.backend.service.IArticleContentService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleContentServiceImpl extends BaseServiceImpl<IArticleContentData, ArticleContent> implements IArticleContentService {
}
