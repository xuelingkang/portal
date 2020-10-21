package com.xzixi.framework.backend;

import com.xzixi.framework.common.model.po.Article;
import com.xzixi.framework.common.model.po.ArticleContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author 薛凌康
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void deleteArticleIndex() {
        boolean deleteResult = elasticsearchTemplate.deleteIndex(Article.class);
        System.out.println(deleteResult);
    }

    @Test
    public void testCreateArticleIndex() {
        boolean createResult = elasticsearchTemplate.createIndex(Article.class);
        boolean putResult = elasticsearchTemplate.putMapping(Article.class);
        System.out.println(createResult);
        System.out.println(putResult);
    }

    @Test
    public void deleteArticleContentIndex() {
        boolean deleteResult = elasticsearchTemplate.deleteIndex(ArticleContent.class);
        System.out.println(deleteResult);
    }

    @Test
    public void testCreateArticleContentIndex() {
        boolean createResult = elasticsearchTemplate.createIndex(ArticleContent.class);
        boolean putResult = elasticsearchTemplate.putMapping(ArticleContent.class);
        System.out.println(createResult);
        System.out.println(putResult);
    }

    @Test
    public void testGetMapping() {
        Map<?, ?> map = elasticsearchTemplate.getMapping(Article.class);
        System.out.println(map);
    }
}
