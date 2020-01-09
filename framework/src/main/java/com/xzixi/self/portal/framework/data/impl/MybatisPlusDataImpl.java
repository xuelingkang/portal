package com.xzixi.self.portal.framework.data.impl;

import com.xzixi.self.portal.framework.data.IBaseData;
import com.xzixi.self.portal.framework.mapper.IBaseMapper;
import com.xzixi.self.portal.framework.model.BaseModel;

/**
 * 扩展mybatis-plus
 *
 * @author 薛凌康
 */
public class MybatisPlusDataImpl<M extends IBaseMapper<T>, T extends BaseModel> extends AbstractDataImpl<M, T> implements IBaseData<T> {
}
