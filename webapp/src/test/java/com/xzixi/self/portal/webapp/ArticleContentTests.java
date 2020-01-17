package com.xzixi.self.portal.webapp;

import com.xzixi.self.portal.framework.data.ISearchEngine;
import com.xzixi.self.portal.webapp.data.IArticleContentData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 薛凌康
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleContentTests {

    @Autowired
    private IArticleContentData articleContentData;

    @Test
    public void testSync() {
        ((ISearchEngine) articleContentData).sync();
    }

    @Test
    public void testInit() {
        ((ISearchEngine) articleContentData).init();
    }
}
