package com.xzixi.framework.backend.typehandler;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@MappedTypes({List.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class IntegerListTypeHandler implements TypeHandler<List<Integer>> {

    private static final String SEPARATOR = ",";

    @Override
    public void setParameter(PreparedStatement ps, int i, List<Integer> parameter, JdbcType jdbcType) throws SQLException {
        if (CollectionUtils.isEmpty(parameter)) {
            ps.setNull(i, Types.VARCHAR);
        } else {
            ps.setString(i, StringUtils.join(parameter, SEPARATOR));
        }
    }

    @Override
    public List<Integer> getResult(ResultSet rs, String columnName) throws SQLException {
        return parse(rs.getString(columnName));
    }

    @Override
    public List<Integer> getResult(ResultSet rs, int columnIndex) throws SQLException {
        return parse(rs.getString(columnIndex));
    }

    @Override
    public List<Integer> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parse(cs.getString(columnIndex));
    }

    private List<Integer> parse(String value) {
        if (StringUtils.isEmpty(value)) {
            return new ArrayList<>();
        }
        String[] arr = value.split(SEPARATOR);
        return Arrays.stream(arr).map(Integer::valueOf).collect(Collectors.toList());
    }
}
