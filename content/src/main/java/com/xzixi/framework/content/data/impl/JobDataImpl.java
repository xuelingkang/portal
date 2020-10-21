package com.xzixi.framework.content.data.impl;

import com.xzixi.framework.common.model.po.Job;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.content.data.IJobData;
import com.xzixi.framework.content.mapper.JobMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "job:base", casualCacheName = "job:casual")
public class JobDataImpl extends MybatisPlusDataImpl<JobMapper, Job> implements IJobData {
}
