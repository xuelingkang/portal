package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.framework.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.self.portal.webapp.data.IArticleData;
import com.xzixi.self.portal.webapp.mapper.ArticleMapper;
import com.xzixi.self.portal.webapp.model.po.Article;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleDataImpl extends MybatisPlusDataImpl<ArticleMapper, Article> implements IArticleData {
}
