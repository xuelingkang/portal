package com.xzixi.self.portal.webapp.framework.constant;

/**
 * @author 薛凌康
 */
public interface RedisConstant {

    /** 正则key前缀 */
    String REGEX_KEY_PREFIX = "regex=";
    /** 多个key之间的分隔符 */
    String KEYS_SEPARATOR = ",";
    /** key层次分隔符 */
    String KEY_SEPARATOR = ":";
    /** getById方法名 */
    String GET_BY_ID_METHOD_NAME = "getById";
    /** listByIds方法名 */
    String LIST_BY_IDS_METHOD_NAME = "listByIds";
    /** 无参方法key */
    String EMPTY_PARAM = "emptyParam";
    /** 参数分隔符 */
    String PARAM_SEPARATOR = "_";
    /** 正则key通配符 */
    String WILDCARD = "*";
}
