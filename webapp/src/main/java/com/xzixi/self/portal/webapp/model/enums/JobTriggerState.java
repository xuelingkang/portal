package com.xzixi.self.portal.webapp.model.enums;

import com.xzixi.self.portal.framework.webmvc.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 定时任务触发器状态
 *
 * @author 薛凌康
 * @see org.terracotta.quartz.wrappers.TriggerWrapper.TriggerState
 */
@Getter
@AllArgsConstructor
public enum JobTriggerState implements IBaseEnum {

    /** 等待中 */
    WAITING("等待中"),
    /** 准备中 */
    ACQUIRED("准备中"),
    /** 完成 */
    COMPLETE("完成"),
    /** 暂停 */
    PAUSED("暂停"),
    /** 阻塞 */
    BLOCKED("阻塞"),
    /** 阻塞暂停 */
    PAUSED_BLOCKED("阻塞暂停"),
    /** 错误 */
    ERROR("错误");

    private final String value;
}
