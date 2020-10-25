package com.xzixi.framework.webapps.system.service;

import com.xzixi.framework.webapps.common.model.po.App;
import com.xzixi.framework.boot.webmvc.service.IBaseService;

/**
 * 应用 服务类
 *
 * @author xuelingkang
 * @date 2020-10-25
 */
public interface IAppService extends IBaseService<App> {

    App getByUid(String uid);
}
