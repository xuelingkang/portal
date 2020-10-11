package com.xzixi.framework.webapp.model.po;

import com.xzixi.framework.boot.webmvc.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章收藏
 *
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
public class ArticleFavorite extends BaseModel {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /** 收藏夹id */
    private Integer collectionId;

    /** 文章id */
    private Integer articleId;

    /** 收藏时间 */
    private Long favoriteTime;

    public ArticleFavorite(Integer collectionId, Integer articleId) {
        this.collectionId = collectionId;
        this.articleId = articleId;
    }
}
