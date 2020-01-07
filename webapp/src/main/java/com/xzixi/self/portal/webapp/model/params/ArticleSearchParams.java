package com.xzixi.self.portal.webapp.model.params;

import com.xzixi.self.portal.framework.model.search.BaseSearchParams;
import com.xzixi.self.portal.webapp.model.po.Article;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "文章查询参数")
public class ArticleSearchParams extends BaseSearchParams<Article> {
}