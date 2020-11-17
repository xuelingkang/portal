/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
