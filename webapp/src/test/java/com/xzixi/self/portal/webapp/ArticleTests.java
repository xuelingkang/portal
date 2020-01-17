package com.xzixi.self.portal.webapp;

import com.xzixi.self.portal.framework.data.ISearchEngine;
import com.xzixi.self.portal.framework.model.search.Pagination;
import com.xzixi.self.portal.framework.model.search.QueryParams;
import com.xzixi.self.portal.webapp.data.IArticleData;
import com.xzixi.self.portal.webapp.model.enums.ArticleAccess;
import com.xzixi.self.portal.webapp.model.po.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 薛凌康
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleTests {

    @Autowired
    private IArticleData articleData;

    @Test
    public void testSync() {
        ((ISearchEngine) articleData).sync();
    }

    @Test
    public void testInit() {
        ((ISearchEngine) articleData).init();
    }

    @Test
    public void testList() {
        QueryParams<Article> params = new QueryParams<>(new Article().setAccess(ArticleAccess.PUBLIC));
        List<Article> list = articleData.list(params);
        System.out.println(list);
    }

    @Test
    public void testPage() {
        Pagination<Article> pagination = new Pagination<>(2, 10);
        QueryParams<Article> params = new QueryParams<>(new Article());
        Pagination<Article> page = articleData.page(pagination, params);
        System.out.println(page);
    }
}
