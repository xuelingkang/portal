package com.xzixi.framework.backend.data.impl;

import com.xzixi.framework.backend.model.po.JobParameter;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.backend.data.IJobParameterData;
import com.xzixi.framework.backend.mapper.JobParameterMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "jobParameter:base", casualCacheName = "jobParameter:casual")
public class JobParameterDataImpl extends MybatisPlusDataImpl<JobParameterMapper, JobParameter> implements IJobParameterData {
}
