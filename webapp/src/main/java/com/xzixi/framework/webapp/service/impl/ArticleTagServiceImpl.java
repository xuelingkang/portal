package com.xzixi.framework.webapp.service.impl;

import com.xzixi.framework.webapp.model.po.ArticleTag;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.webapp.data.IArticleTagData;
import com.xzixi.framework.webapp.service.IArticleTagService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleTagServiceImpl extends BaseServiceImpl<IArticleTagData, ArticleTag> implements IArticleTagService {
}
