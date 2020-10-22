package com.xzixi.framework.webapps.content.service.impl;

import com.xzixi.framework.webapps.content.data.IArticleContentData;
import com.xzixi.framework.webapps.common.model.po.ArticleContent;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.webapps.content.service.IArticleContentService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleContentServiceImpl extends BaseServiceImpl<IArticleContentData, ArticleContent> implements IArticleContentService {
}
