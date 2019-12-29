package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.webapp.data.IJobTemplateParameterData;
import com.xzixi.self.portal.webapp.framework.data.impl.BaseDataImpl;
import com.xzixi.self.portal.webapp.mapper.JobTemplateParameterMapper;
import com.xzixi.self.portal.webapp.model.po.JobTemplateParameter;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance
public class JobTemplateParameterDataImpl extends BaseDataImpl<JobTemplateParameterMapper, JobTemplateParameter> implements IJobTemplateParameterData {
}
