package com.xzixi.framework.content.service.impl;

import com.xzixi.framework.content.data.IArticleContentData;
import com.xzixi.framework.common.model.po.ArticleContent;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.content.service.IArticleContentService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleContentServiceImpl extends BaseServiceImpl<IArticleContentData, ArticleContent> implements IArticleContentService {
}
