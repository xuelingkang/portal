package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.framework.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.framework.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.self.portal.webapp.data.IJobParameterData;
import com.xzixi.self.portal.webapp.mapper.JobParameterMapper;
import com.xzixi.self.portal.webapp.model.po.JobParameter;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "jobParameter:base", casualCacheName = "jobParameter:casual")
public class JobParameterDataImpl extends MybatisPlusDataImpl<JobParameterMapper, JobParameter> implements IJobParameterData {
}
