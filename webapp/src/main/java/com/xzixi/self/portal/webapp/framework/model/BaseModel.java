package com.xzixi.self.portal.webapp.framework.model;

import com.xzixi.self.portal.webapp.framework.util.SerializeUtil;

import java.io.Serializable;

/**
 * 所有实体类的父类
 *
 * @author 薛凌康
 */
public abstract class BaseModel implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

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
        return SerializeUtil.deserialize(SerializeUtil.serialize(this));
    }
}
