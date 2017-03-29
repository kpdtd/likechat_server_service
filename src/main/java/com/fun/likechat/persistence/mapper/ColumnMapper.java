package com.fun.likechat.persistence.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.PersistenceException;

import com.fun.likechat.persistence.po.Column;

/**
 * @version
 * @since JDK 1.7
 */
public interface ColumnMapper extends BaseMapper<Column> {

    // 根据频道标识查询出频道对象
    public Column getColumnByIdentifying(@Param("identifying") String identifying)
            throws PersistenceException;

    // 根据频道标识串得到该频道的所有子频道
    public List<Column> getSonColumnByIdentifying(@Param("identifying") String identifying)
            throws PersistenceException;

}
