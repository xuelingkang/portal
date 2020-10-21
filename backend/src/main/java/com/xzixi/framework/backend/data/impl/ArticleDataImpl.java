package com.xzixi.framework.backend.data.impl;

import com.xzixi.framework.common.model.po.Article;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.backend.data.IArticleData;
import com.xzixi.framework.backend.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleDataImpl extends MybatisPlusDataImpl<ArticleMapper, Article> implements IArticleData {
}
