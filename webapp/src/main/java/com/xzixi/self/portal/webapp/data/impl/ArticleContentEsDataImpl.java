package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.framework.data.impl.ElasticsearchDataImpl;
import com.xzixi.self.portal.webapp.data.IArticleContentEsData;
import com.xzixi.self.portal.webapp.model.po.ArticleContent;
import org.springframework.stereotype.Service;

/**
 * @author xuelingkang
 * @date 2020-09-26
 */
@Service
public class ArticleContentEsDataImpl extends ElasticsearchDataImpl<ArticleContent> implements IArticleContentEsData {
}
