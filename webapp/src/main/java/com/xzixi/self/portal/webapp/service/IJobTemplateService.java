package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.framework.service.IBaseService;
import com.xzixi.self.portal.webapp.model.po.JobTemplate;
import com.xzixi.self.portal.webapp.model.po.JobTemplateParameter;
import com.xzixi.self.portal.webapp.model.vo.JobTemplateVO;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IJobTemplateService extends IBaseService<JobTemplate> {

    /**
     * 保存任务模板和参数
     *
     * @param jobTemplate 任务模板对象
     * @param parameters 任务模板参数
     */
    void saveJobTemplate(JobTemplate jobTemplate, Collection<JobTemplateParameter> parameters);

    /**
     * 更新任务模板和参数
     *
     * @param jobTemplate 任务模板对象
     * @param parameters 任务模板参数
     */
    void updateJobTemplateById(JobTemplate jobTemplate, Collection<JobTemplateParameter> parameters);

    /**
     * 根据id集合删除任务模板
     *
     * @param ids 模板id集合
     */
    void removeJobTemplatesByIds(Collection<Integer> ids);

    /**
     * 构建JobTemplateVO
     *
     * @param id 任务模板对象id
     * @return JobTemplateVO
     */
    JobTemplateVO buildJobTemplateVO(Integer id);

    /**
     * 构建JobTemplateVO
     *
     * @param jobTemplate 任务模板对象
     * @return JobTemplateVO
     */
    JobTemplateVO buildJobTemplateVO(JobTemplate jobTemplate);
}
