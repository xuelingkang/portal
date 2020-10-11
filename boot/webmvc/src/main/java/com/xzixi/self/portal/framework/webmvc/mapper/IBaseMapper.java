package com.xzixi.self.portal.framework.webmvc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzixi.self.portal.framework.webmvc.model.BaseModel;

/**
 * 为方便扩展留一个接口
 *
 * @author 薛凌康
 */
public interface IBaseMapper<T extends BaseModel> extends BaseMapper<T> {
}
