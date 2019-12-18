package com.xzixi.self.portal.webapp.framework.model;

import com.xzixi.self.portal.webapp.framework.exception.ProjectException;
import com.xzixi.self.portal.webapp.framework.util.FieldUtil;
import com.xzixi.self.portal.webapp.framework.util.SerializeUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;

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
     * 忽略属性
     *
     * @param ignoreProperties 忽略的属性名称
     * @return BaseModel
     */
    public BaseModel ignoreProperties(String... ignoreProperties) {
        if (ArrayUtils.isEmpty(ignoreProperties)) {
            return this;
        }
        Class<? extends BaseModel> cls = this.getClass();
        BaseModel model;
        try {
            model = cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ProjectException(String.format("使用无参构造器创建[%s]实例失败！", cls.getName()));
        }
        BeanUtils.copyProperties(this, model, ignoreProperties);
        return model;
    }

    /**
     * 按照字段名称取值
     *
     * @param fieldName 字段名称
     * @return 字段值
     */
    public Object value(String fieldName) {
        return FieldUtil.get(this, fieldName);
    }

    /**
     * 按照字段名称赋值
     *
     * @param fieldName 字段名称
     * @param value 字段值
     */
    public void value(String fieldName, Object value) {
        FieldUtil.set(this, fieldName, value);
    }

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
