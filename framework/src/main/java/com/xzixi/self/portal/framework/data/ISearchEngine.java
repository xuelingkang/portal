package com.xzixi.self.portal.framework.data;

/**
 * 以一定的顺序从数据库中批量读取数据，导入搜索引擎或与搜索引擎进行比对，<br>
 * 如果同时有删除数据的操作，且删除的数据包含已经导入过的数据，
 * 那么下个批次查询数据库就会跳过一些数据，导致搜索引擎与数据库不同步；<br>
 * 如果同时有插入数据的操作，有可能导致下个批次查询到重复的数据，影响不大。<br>
 *
 * @author 薛凌康
 */
public interface ISearchEngine {

    /**
     * 初始化/重新初始化数据
     */
    void init();

    /**
     * 同步数据，以数据库为准
     */
    void sync();
}
