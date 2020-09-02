package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.framework.data.impl.ElasticsearchDataImpl;
import com.xzixi.self.portal.webapp.data.IArticleContentData;
import com.xzixi.self.portal.webapp.model.po.ArticleContent;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleContentDataImpl extends ElasticsearchDataImpl<ArticleContent> implements IArticleContentData {
}
