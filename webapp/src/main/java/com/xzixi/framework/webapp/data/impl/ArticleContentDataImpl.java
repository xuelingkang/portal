package com.xzixi.framework.webapp.data.impl;

import com.xzixi.framework.webapp.model.po.ArticleContent;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapp.data.IArticleContentData;
import com.xzixi.framework.webapp.mapper.ArticleContentMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleContentDataImpl extends MybatisPlusDataImpl<ArticleContentMapper, ArticleContent> implements IArticleContentData {
}
