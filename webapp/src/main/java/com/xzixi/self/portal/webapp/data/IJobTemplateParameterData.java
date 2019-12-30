package com.xzixi.self.portal.webapp.data;

import com.xzixi.self.portal.webapp.framework.data.IBaseData;
import com.xzixi.self.portal.webapp.model.po.JobTemplateParameter;

/**
 * @author 薛凌康
 */
public interface IJobTemplateParameterData extends IBaseData<JobTemplateParameter> {

    String BASE_CACHE_NAME = "jobTemplateParameter:base";
    String CASUAL_CACHE_NAME = "jobTemplateParameter:casual";
}