package com.xzixi.framework.content.data.impl;

import com.xzixi.framework.common.model.po.ArticleContent;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.content.data.IArticleContentData;
import com.xzixi.framework.content.mapper.ArticleContentMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class ArticleContentDataImpl extends MybatisPlusDataImpl<ArticleContentMapper, ArticleContent> implements IArticleContentData {
}
