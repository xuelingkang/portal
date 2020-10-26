package com.xzixi.framework.webapps.common.service.impl;

import com.xzixi.framework.boot.webmvc.exception.ClientException;
import com.xzixi.framework.boot.webmvc.exception.ProjectException;
import com.xzixi.framework.boot.webmvc.util.TypeUtils;
import com.xzixi.framework.webapps.common.service.ISignService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.xzixi.framework.webapps.common.constant.SignConstant.*;

/**
 * @author xuelingkang
 * @date 2020-10-27
 */
public class Md5SignServiceImpl implements ISignService {

    @Override
    public String genSecret(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

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