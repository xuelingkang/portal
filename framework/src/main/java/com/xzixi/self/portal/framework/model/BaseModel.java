package com.xzixi.self.portal.framework.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;

/**
 * 所有实体类的父类
 *
 * @author 薛凌康
 */
public abstract class BaseModel implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    private final static ParserConfig PARSER_CONFIG = new ParserConfig();

    static {
        PARSER_CONFIG.setAutoTypeSupport(true);
    }

    /**
     * 获取id
     *
     * @return id
     */
    public abstract Integer getId();

    /**
     * 设置id
     *
     * @param id id
     * @return 当前对象
     */
    public abstract BaseModel setId(Integer id);

    /**
     * 深度复制
     *
     * @return BaseModel
     */
    @Override
    public BaseModel clone() {
        return JSON.parseObject(JSON.toJSONString(this, SerializerFeature.WriteClassName), Object.class, PARSER_CONFIG);
    }
}
