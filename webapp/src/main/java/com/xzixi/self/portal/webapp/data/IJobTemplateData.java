package com.xzixi.self.portal.webapp.data;

import com.xzixi.self.portal.webapp.framework.data.IBaseData;
import com.xzixi.self.portal.webapp.model.po.JobTemplate;

/**
 * @author 薛凌康
 */
public interface IJobTemplateData extends IBaseData<JobTemplate> {

    String BASE_CACHE_NAME = "jobTemplate:base";
    String CASUAL_CACHE_NAME = "jobTemplate:casual";
}
