package com.xzixi.self.portal.webapp.framework.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xzixi.self.portal.webapp.framework.exception.ProjectException;

/**
 * @author 薛凌康
 */
public class SerializeUtil {

    private static final byte[] EMPTY_ARRAY = new byte[0];

    public static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    /** 序列化对象 */
    public static byte[] serialize(Object obj) {
        if (obj==null) {
            return EMPTY_ARRAY;
        }
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (Exception e) {
            throw new ProjectException("序列化对象出错", e);
        }
    }

    /** 反序列化对象 */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] bytes) {
        if (bytes==null || bytes.length==0) {
            return null;
        }
        try {
            return (T) objectMapper.readValue(bytes, 0, bytes.length, Object.class);
        } catch (Exception e) {
            throw new ProjectException("反序列化对象出错", e);
        }
    }

}
