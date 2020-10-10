package com.xzixi.self.portal.webapp.service.impl;

import com.xzixi.self.portal.framework.webmvc.exception.ServerException;
import com.xzixi.self.portal.framework.webmvc.model.search.QueryParams;
import com.xzixi.self.portal.framework.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.self.portal.webapp.data.IJobTemplateData;
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
import java.util.Objects;
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
    public void saveJobTemplate(JobTemplate jobTemplate, Collection<JobTemplateParameter> parameters) {
        if (!save(jobTemplate)) {
            throw new ServerException(jobTemplate, "保存定时任务模板失败！");
        }
        parameters.forEach(parameter -> parameter.setJobTemplateId(jobTemplate.getId()));
        if (CollectionUtils.isNotEmpty(parameters)) {
            if (!jobTemplateParameterService.defaultSaveBatch(parameters)) {
                throw new ServerException(parameters, "保存定时任务模板参数失败！");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJobTemplateById(JobTemplate jobTemplate, Collection<JobTemplateParameter> parameters) {
        if (!updateById(jobTemplate)) {
            throw new ServerException(jobTemplate, "更新定时任务模板失败！");
        }
        parameters.forEach(parameter -> parameter.setJobTemplateId(jobTemplate.getId()));
        List<JobTemplateParameter> oldParameters = jobTemplateParameterService
                .list(new QueryParams<>(new JobTemplateParameter().setJobTemplateId(jobTemplate.getId())));
        boolean mergeResult = jobTemplateParameterService.merge(parameters, oldParameters, ((sources, target) ->
                sources.stream().filter(source -> Objects.equals(source.getId(), target.getId())).findFirst().orElse(null)));
        if (!mergeResult) {
            throw new ServerException(parameters, "更新定时任务模板参数失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeJobTemplatesByIds(Collection<Integer> ids) {
        if (!removeByIds(ids)) {
            throw new ServerException(ids, "删除定时任务模板失败！");
        }

        List<JobTemplateParameter> parameters = jobTemplateParameterService.listByJobTemplateIds(ids);
        if (CollectionUtils.isNotEmpty(parameters)) {
            List<Integer> parameterIds = parameters.stream().map(JobTemplateParameter::getId).collect(Collectors.toList());
            if (!jobTemplateParameterService.removeByIds(parameterIds)) {
                throw new ServerException(parameters, "删除定时任务模板参数失败！");
            }
        }
    }

    @Override
    public JobTemplateVO buildVO(JobTemplate jobTemplate, JobTemplateVO.BuildOption option) {
        JobTemplateVO jobTemplateVO = new JobTemplateVO(jobTemplate);
        if (option.isParameters()) {
            List<JobTemplateParameter> parameters = jobTemplateParameterService
                    .list(new QueryParams<>(new JobTemplateParameter().setJobTemplateId(jobTemplate.getId())));
            if (CollectionUtils.isNotEmpty(parameters)) {
                jobTemplateVO.setParameters(parameters);
            }
        }
        return jobTemplateVO;
    }

    @Override
    public List<JobTemplateVO> buildVO(Collection<JobTemplate> jobTemplates, JobTemplateVO.BuildOption option) {
        List<JobTemplateVO> jobTemplateVOList = jobTemplates.stream().map(JobTemplateVO::new).collect(Collectors.toList());
        if (option.isParameters()) {
            List<JobTemplateParameter> parameters = jobTemplateParameterService
                    .listByJobTemplateIds(jobTemplates.stream().map(JobTemplate::getId).collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(parameters)) {
                jobTemplateVOList.forEach(jobTemplateVO -> {
                    List<JobTemplateParameter> params = parameters.stream()
                            .filter(parameter -> Objects.equals(jobTemplateVO.getId(), parameter.getJobTemplateId()))
                            .collect(Collectors.toList());
                    jobTemplateVO.setParameters(params);
                });
            }
        }
        return jobTemplateVOList;
    }
}
