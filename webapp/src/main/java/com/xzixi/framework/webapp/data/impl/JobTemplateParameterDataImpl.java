package com.xzixi.framework.webapp.data.impl;

import com.xzixi.framework.webapp.model.po.JobTemplateParameter;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapp.data.IJobTemplateParameterData;
import com.xzixi.framework.webapp.mapper.JobTemplateParameterMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "jobTemplateParameter:base", casualCacheName = "jobTemplateParameter:casual")
public class JobTemplateParameterDataImpl extends MybatisPlusDataImpl<JobTemplateParameterMapper, JobTemplateParameter> implements IJobTemplateParameterData {
}
