package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.framework.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.self.portal.webapp.data.IArticleContentData;
import com.xzixi.self.portal.webapp.mapper.ArticleContentMapper;
import com.xzixi.self.portal.webapp.model.po.ArticleContent;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleContentDataImpl extends MybatisPlusDataImpl<ArticleContentMapper, ArticleContent> implements IArticleContentData {
}
