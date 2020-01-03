package com.xzixi.self.portal.webapp.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @author 薛凌康
 */
public class FileUtil {

    /**
     * 获取文件的扩展名，包含"."
     *
     * @param filename 文件名
     * @return .扩展名
     */
    public static String getExp(String filename) {
        if (StringUtils.isNotBlank(filename)) {
            int lastPointIndex = filename.lastIndexOf(".");
            if (lastPointIndex != -1) {
                return filename.substring(lastPointIndex);
            }
        }
        return "";
    }

    public static String getRandomName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
