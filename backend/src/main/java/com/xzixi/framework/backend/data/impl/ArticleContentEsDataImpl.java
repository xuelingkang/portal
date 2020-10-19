package com.xzixi.framework.backend.data.impl;

import com.xzixi.framework.backend.data.IArticleContentEsData;
import com.xzixi.framework.backend.model.po.ArticleContent;
import com.xzixi.framework.boot.webmvc.data.impl.ElasticsearchDataImpl;
import org.springframework.stereotype.Service;

/**
 * @author xuelingkang
 * @date 2020-09-26
 */
@Service
public class ArticleContentEsDataImpl extends ElasticsearchDataImpl<ArticleContent> implements IArticleContentEsData {
}
