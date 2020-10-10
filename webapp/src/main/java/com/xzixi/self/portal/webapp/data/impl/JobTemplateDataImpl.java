package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.framework.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.framework.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.self.portal.webapp.data.IJobTemplateData;
import com.xzixi.self.portal.webapp.mapper.JobTemplateMapper;
import com.xzixi.self.portal.webapp.model.po.JobTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "jobTemplate:base", casualCacheName = "jobTemplate:casual")
public class JobTemplateDataImpl extends MybatisPlusDataImpl<JobTemplateMapper, JobTemplate> implements IJobTemplateData {
}
