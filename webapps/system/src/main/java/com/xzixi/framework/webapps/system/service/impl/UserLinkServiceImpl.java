package com.xzixi.framework.webapps.system.service.impl;

import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.webapps.common.model.po.UserLink;
import com.xzixi.framework.webapps.system.data.IUserLinkData;
import com.xzixi.framework.webapps.system.service.IUserLinkService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class UserLinkServiceImpl extends BaseServiceImpl<IUserLinkData, UserLink> implements IUserLinkService {
}
