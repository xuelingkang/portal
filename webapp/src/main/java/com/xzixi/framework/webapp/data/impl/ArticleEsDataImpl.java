package com.xzixi.framework.webapp.data.impl;

import com.xzixi.framework.webapp.data.IArticleEsData;
import com.xzixi.framework.webapp.model.po.Article;
import com.xzixi.framework.boot.webmvc.data.impl.ElasticsearchDataImpl;
import org.springframework.stereotype.Service;

/**
 * @author xuelingkang
 * @date 2020-09-26
 */
@Service
public class ArticleEsDataImpl extends ElasticsearchDataImpl<Article> implements IArticleEsData {
}
