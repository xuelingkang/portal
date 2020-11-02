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

package com.xzixi.framework.boot.webmvc.util;

import java.util.regex.Pattern;

/**
 * @author 薛凌康
 */
public class OrderUtils {

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
