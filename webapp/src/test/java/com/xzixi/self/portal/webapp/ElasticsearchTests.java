package com.xzixi.self.portal.webapp;

import com.xzixi.self.portal.webapp.model.po.Article;
import com.xzixi.self.portal.webapp.model.po.ArticleContent;
import com.xzixi.self.portal.webapp.service.IArticleContentService;
import com.xzixi.self.portal.webapp.service.IArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 薛凌康
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IArticleContentService articleContentService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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
    public void testSaveArticleContent() {
        ArticleContent content = new ArticleContent();
        content.setArticleId(1);
        content.setContent("作用在成员变量，标记为文档的字段，并指定字段映射属性");
        articleContentService.save(content);
    }

    @Test
    public void testRemove() {
        redisTemplate.opsForList().remove("123", 1, 1);
    }

    @Test
    public void testValue() {
        redisTemplate.opsForValue().set("123::456", 2, 1800, TimeUnit.SECONDS);
    }

    @Test
    public void testGetMapping() {
        Map<?, ?> map = elasticsearchTemplate.getMapping(Article.class);
        System.out.println(map);
    }
}
