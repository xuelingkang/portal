package com.xzixi.self.portal.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.webapp.data.IJobTemplateData;
import com.xzixi.self.portal.webapp.framework.service.impl.BaseServiceImpl;
import com.xzixi.self.portal.webapp.model.po.JobTemplate;
import com.xzixi.self.portal.webapp.model.po.JobTemplateParameter;
import com.xzixi.self.portal.webapp.model.vo.JobTemplateVO;
import com.xzixi.self.portal.webapp.service.IJobTemplateParameterService;
import com.xzixi.self.portal.webapp.service.IJobTemplateService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@Service
public class JobTemplateServiceImpl extends BaseServiceImpl<IJobTemplateData, JobTemplate> implements IJobTemplateService {

    @Autowired
    private IJobTemplateParameterService jobTemplateParameterService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveJobTemplate(JobTemplateVO jobTemplateVO) {
        if (!save(jobTemplateVO)) {
            return false;
        }
        List<JobTemplateParameter> parameters = jobTemplateVO.getParameters();
        if (CollectionUtils.isNotEmpty(parameters)) {
            if (!jobTemplateParameterService.saveBatch(parameters)) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateJobTemplateById(JobTemplateVO jobTemplateVO) {
        if (!updateById(jobTemplateVO)) {
            return false;
        }
        List<JobTemplateParameter> parameters = jobTemplateVO.getParameters();
        List<JobTemplateParameter> oldParameters = jobTemplateParameterService
                .list(new QueryWrapper<>(new JobTemplateParameter().setJobTemplateId(jobTemplateVO.getId())));
        return jobTemplateParameterService.merge(parameters, oldParameters, ((sources, target) ->
                sources.stream().filter(source -> source.getId().equals(target.getId())).findFirst().orElse(null)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeJobTemplatesByIds(Collection<Integer> ids) {
        if (!removeByIds(ids)) {
            return false;
        }

        List<JobTemplateParameter> parameters = jobTemplateParameterService.listByJobTemplateIds(ids);
        if (CollectionUtils.isNotEmpty(parameters)) {
            List<Integer> parameterIds = parameters.stream().map(JobTemplateParameter::getId).collect(Collectors.toList());
            if (!jobTemplateParameterService.removeByIds(parameterIds)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public JobTemplateVO buildJobTemplateVO(Integer id) {
        JobTemplate jobTemplate = getById(id);
        return buildJobTemplateVO(jobTemplate);
    }

    @Override
    public JobTemplateVO buildJobTemplateVO(JobTemplate jobTemplate) {
        List<JobTemplateParameter> parameters = jobTemplateParameterService
                .list(new QueryWrapper<>(new JobTemplateParameter().setJobTemplateId(jobTemplate.getId())));
        JobTemplateVO jobTemplateVO = new JobTemplateVO(jobTemplate);
        jobTemplateVO.setParameters(parameters);
        return jobTemplateVO;
    }
}
