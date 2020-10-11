package com.xzixi.framework.webapp.service.impl;

import com.xzixi.framework.webapp.model.po.UserLink;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.webapp.data.IUserLinkData;
import com.xzixi.framework.webapp.service.IUserLinkService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class UserLinkServiceImpl extends BaseServiceImpl<IUserLinkData, UserLink> implements IUserLinkService {
}
