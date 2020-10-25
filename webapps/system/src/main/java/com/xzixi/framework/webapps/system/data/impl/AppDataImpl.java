package com.xzixi.framework.webapps.system.data.impl;

import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapps.system.data.IAppData;
import com.xzixi.framework.webapps.system.mapper.AppMapper;
import com.xzixi.framework.webapps.common.model.po.App;
import org.springframework.stereotype.Service;

/**
 * 应用 数据实现类
 *
 * @author xuelingkang
 * @date 2020-10-25
 */
@Service
public class AppDataImpl extends MybatisPlusDataImpl<AppMapper, App> implements IAppData {

}
