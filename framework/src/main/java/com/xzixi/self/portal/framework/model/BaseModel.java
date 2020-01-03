package com.xzixi.self.portal.framework.model;

import com.xzixi.self.portal.framework.exception.ProjectException;

import java.io.*;

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
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);
            return (BaseModel) ois.readObject();
        } catch (Exception e) {
            throw new ProjectException("克隆对象失败！");
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
