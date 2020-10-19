package com.xzixi.framework.backend.data.impl;

import com.xzixi.framework.backend.model.po.JobTemplate;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.backend.data.IJobTemplateData;
import com.xzixi.framework.backend.mapper.JobTemplateMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "jobTemplate:base", casualCacheName = "jobTemplate:casual")
public class JobTemplateDataImpl extends MybatisPlusDataImpl<JobTemplateMapper, JobTemplate> implements IJobTemplateData {
}