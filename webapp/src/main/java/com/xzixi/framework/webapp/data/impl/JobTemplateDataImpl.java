package com.xzixi.framework.webapp.data.impl;

import com.xzixi.framework.webapp.model.po.JobTemplate;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapp.data.IJobTemplateData;
import com.xzixi.framework.webapp.mapper.JobTemplateMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "jobTemplate:base", casualCacheName = "jobTemplate:casual")
public class JobTemplateDataImpl extends MybatisPlusDataImpl<JobTemplateMapper, JobTemplate> implements IJobTemplateData {
}
