package com.xzixi.self.portal.framework.util;

import java.util.regex.Pattern;

/**
 * @author 薛凌康
 */
public class OrderUtil {

    private static final Pattern ASC_REG = Pattern.compile("\\s*[Aa][Ss][Cc]\\s*");
    private static final String SPACE_REG = "\\s+";
    private static final String ASC = "asc";
    private static final String DESC = "desc";

    public static String[] parse(String order) {
        String[] arr = order.split(SPACE_REG);
        if (arr.length == 2) {
            if (ASC_REG.matcher(arr[1]).matches()) {
                return new String[]{arr[0], ASC};
            } else {
                return new String[]{arr[0], DESC};
            }
        }
        return null;
    }

    public static boolean isAsc(String order) {
        return ASC_REG.matcher(order).matches();
    }
}
