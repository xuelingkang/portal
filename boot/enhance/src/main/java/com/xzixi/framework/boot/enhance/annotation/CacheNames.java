package com.xzixi.framework.boot.enhance.annotation;

/**
 * @author xuelingkang
 * @date 2020-11-10
 */
public class CacheNames {

    private String baseCacheName;
    private String casualCacheName;

    public CacheNames() {
    }

    public CacheNames(String baseCacheName, String casualCacheName) {
        this.baseCacheName = baseCacheName;
        this.casualCacheName = casualCacheName;
    }

    public String getBaseCacheName() {
        return baseCacheName;
    }

    public void setBaseCacheName(String baseCacheName) {
        this.baseCacheName = baseCacheName;
    }

    public String getCasualCacheName() {
        return casualCacheName;
    }

    public void setCasualCacheName(String casualCacheName) {
        this.casualCacheName = casualCacheName;
    }
}
