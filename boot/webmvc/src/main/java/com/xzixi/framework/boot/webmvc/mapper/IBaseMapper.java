package com.xzixi.framework.boot.webmvc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzixi.framework.boot.webmvc.model.BaseModel;

/**
 * 为方便扩展留一个接口
 *
 * @author 薛凌康
 */
public interface IBaseMapper<T extends BaseModel> extends BaseMapper<T> {
}
