package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.webapp.framework.service.IBaseService;
import com.xzixi.self.portal.webapp.model.po.JobTemplate;
import com.xzixi.self.portal.webapp.model.vo.JobTemplateVO;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IJobTemplateService extends IBaseService<JobTemplate> {

    /**
     * 保存任务模板和参数
     *
     * @param jobTemplateVO 任务模板对象
     * @return {@code true} 保存成功 {@code false} 保存失败
     */
    boolean saveJobTemplate(JobTemplateVO jobTemplateVO);

    /**
     * 更新任务模板和参数
     *
     * @param jobTemplateVO 任务模板对象
     * @return {@code true} 更新成功 {@code false} 更新失败
     */
    boolean updateJobTemplateById(JobTemplateVO jobTemplateVO);

    /**
     * 根据id集合删除任务模板
     *
     * @param ids 模板id集合
     * @return {@code true} 删除成功 {@code false} 删除失败
     */
    boolean removeJobTemplatesByIds(Collection<Integer> ids);

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
