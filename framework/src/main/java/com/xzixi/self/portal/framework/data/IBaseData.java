package com.xzixi.self.portal.framework.data;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzixi.self.portal.framework.model.BaseModel;

/**
 * @author 薛凌康
 */
public interface IBaseData<T extends BaseModel> extends IService<T> {
}
