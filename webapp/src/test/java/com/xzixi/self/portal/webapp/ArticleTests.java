package com.xzixi.self.portal.webapp;

import com.xzixi.self.portal.framework.data.ISearchEngine;
import com.xzixi.self.portal.webapp.data.IArticleData;
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
}
