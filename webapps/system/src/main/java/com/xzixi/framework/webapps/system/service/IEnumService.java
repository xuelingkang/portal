package com.xzixi.framework.webapps.system.service;

import com.xzixi.framework.webapps.common.model.vo.EnumVO;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author 薛凌康
 */
public interface IEnumService {

    String ENUM_SCAN = "classpath:com/xzixi/framework/webapps/common/model/enums/**.class";
    Set<EnumVO> ENUMS = new HashSet<>();

    /**
     * 查询所有枚举项
     *
     * @return 所有枚举项
     */
    default Set<EnumVO> listAll() {
        return ENUMS;
    }

    /**
     * 根据枚举类名查询枚举项
     *
     * @param name 枚举类名
     * @return 枚举项集合
     */
    default EnumVO listByName(String name) {
        return ENUMS.stream().filter(enumVO -> Objects.equals(name, enumVO.getName())).findFirst().orElse(null);
    }
}
