/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.common.model.enums;

import com.xzixi.framework.boot.core.model.IBaseEnum;
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
