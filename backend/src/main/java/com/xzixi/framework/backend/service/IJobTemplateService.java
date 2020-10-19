package com.xzixi.framework.backend.service;

import com.xzixi.framework.backend.model.po.JobTemplate;
import com.xzixi.framework.backend.model.po.JobTemplateParameter;
import com.xzixi.framework.backend.model.vo.JobTemplateVO;
import com.xzixi.framework.boot.webmvc.service.IBaseService;
import com.xzixi.framework.boot.webmvc.service.IVoService;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IJobTemplateService extends IBaseService<JobTemplate>,
        IVoService<JobTemplate, JobTemplateVO, JobTemplateVO.BuildOption> {

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
}
