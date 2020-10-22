package com.xzixi.framework.content.service.impl;

import com.xzixi.framework.common.model.po.ArticleTag;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.content.data.IArticleTagData;
import com.xzixi.framework.content.service.IArticleTagService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleTagServiceImpl extends BaseServiceImpl<IArticleTagData, ArticleTag> implements IArticleTagService {
}