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

package com.xzixi.framework.boot.webmvc.service.impl;

import com.xzixi.framework.boot.core.exception.ClientException;
import com.xzixi.framework.boot.core.exception.ProjectException;
import com.xzixi.framework.boot.core.util.TypeUtils;
import com.xzixi.framework.boot.webmvc.service.ISignService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * MD5实现签名
 *
 * @author xuelingkang
 * @date 2020-10-27
 */
public class Md5SignServiceImpl implements ISignService {

    @Override
    public String genSign(Map<String, Object> params, String secret) {
        if (StringUtils.isBlank(secret)) {
            throw new ProjectException("密钥不能为空！");
        }
        Object timestampObj = params.get(TIMESTAMP_NAME);
        if (timestampObj == null || !Long.class.isAssignableFrom(timestampObj.getClass())) {
            throw new ProjectException("必须设置long时间戳！");
        }
        // TreeMap默认按照key的字典升序排序
        TreeMap<String, Object> treeMap = new TreeMap<>(params);
        // 拼接键值对
        String pairs = treeMap.entrySet().stream().map(entry -> {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                return String.format(PAIR_FORMAT, key, "");
            }
            if (TypeUtils.isSimpleValueType(value.getClass())) {
                return String.format(PAIR_FORMAT, key, value);
            }
            return String.format(PAIR_FORMAT, key, value.hashCode());
        }).collect(Collectors.joining(DELIMITER));
        // md5签名
        return DigestUtils.md5Hex((pairs + DELIMITER + String.format(PAIR_FORMAT, SECRET_NAME, secret)).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean check(Map<String, Object> params, String sign, String secret, long timeout) {
        long now = System.currentTimeMillis();
        if (StringUtils.isBlank(sign)) {
            throw new ClientException(400, "签名不能为空！");
        }
        if (StringUtils.isBlank(secret)) {
            throw new ClientException(400, "密钥不能为空！");
        }
        Object timestampObj = params.get(TIMESTAMP_NAME);
        if (timestampObj == null || !Long.class.isAssignableFrom(timestampObj.getClass())) {
            throw new ClientException(400, "必须设置long时间戳！");
        }
        long timestamp = (long) timestampObj;
        if (Math.abs(now - timestamp) > timeout) {
            throw new ClientException(400, "请求已过期，请重试！");
        }
        return sign.equals(genSign(params, secret));
    }
}
