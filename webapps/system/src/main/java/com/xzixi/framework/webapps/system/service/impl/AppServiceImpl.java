package com.xzixi.framework.webapps.system.service.impl;

import com.xzixi.framework.boot.webmvc.model.search.QueryParams;
import com.xzixi.framework.webapps.common.model.po.App;
import com.xzixi.framework.webapps.system.data.IAppData;
import com.xzixi.framework.webapps.system.service.IAppService;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 应用 服务实现类
 *
 * @author xuelingkang
 * @date 2020-10-25
 */
@Service
public class AppServiceImpl extends BaseServiceImpl<IAppData, App> implements IAppService {

    @Override
    public App getByUid(String uid) {
        return getOne(new QueryParams<>(new App().setUid(uid)));
    }
}
