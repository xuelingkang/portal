package com.xzixi.framework.webapp.data.impl;

import com.xzixi.framework.webapp.model.po.Article;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapp.data.IArticleData;
import com.xzixi.framework.webapp.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleDataImpl extends MybatisPlusDataImpl<ArticleMapper, Article> implements IArticleData {
}
