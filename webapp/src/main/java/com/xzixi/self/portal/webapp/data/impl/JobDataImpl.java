package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.framework.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.framework.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.self.portal.webapp.data.IJobData;
import com.xzixi.self.portal.webapp.mapper.JobMapper;
import com.xzixi.self.portal.webapp.model.po.Job;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "job:base", casualCacheName = "job:casual")
public class JobDataImpl extends MybatisPlusDataImpl<JobMapper, Job> implements IJobData {
}
