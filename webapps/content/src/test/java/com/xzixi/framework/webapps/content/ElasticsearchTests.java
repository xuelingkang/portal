/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.content;

import com.xzixi.framework.webapps.common.model.po.Article;
import com.xzixi.framework.webapps.common.model.po.ArticleContent;
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
