package com.xzixi.framework.content.data.impl;

import com.xzixi.framework.content.data.IArticleContentEsData;
import com.xzixi.framework.common.model.po.ArticleContent;
import com.xzixi.framework.boot.webmvc.data.impl.ElasticsearchDataImpl;
import org.springframework.stereotype.Service;

/**
 * @author xuelingkang
 * @date 2020-09-26
 */
@Service
public class ArticleContentEsDataImpl extends ElasticsearchDataImpl<ArticleContent> implements IArticleContentEsData {
}
