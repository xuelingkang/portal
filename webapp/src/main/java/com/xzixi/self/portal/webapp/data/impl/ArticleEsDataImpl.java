package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.framework.data.impl.ElasticsearchDataImpl;
import com.xzixi.self.portal.webapp.data.IArticleEsData;
import com.xzixi.self.portal.webapp.model.po.Article;
import org.springframework.stereotype.Service;

/**
 * @author xuelingkang
 * @date 2020-09-26
 */
@Service
public class ArticleEsDataImpl extends ElasticsearchDataImpl<Article> implements IArticleEsData {
}
