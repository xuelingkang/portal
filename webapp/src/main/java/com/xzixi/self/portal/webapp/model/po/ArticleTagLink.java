package com.xzixi.self.portal.webapp.model.po;

import com.xzixi.self.portal.framework.webmvc.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章标签关联
 *
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
public class ArticleTagLink extends BaseModel {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /** 文章id */
    private Integer articleId;

    /** 标签id */
    private Integer tagId;

    /** 显示顺序 */
    private Integer seq;

    public ArticleTagLink(Integer articleId, Integer tagId, Integer seq) {
        this.articleId = articleId;
        this.tagId = tagId;
        this.seq = seq;
    }
}
