package com.fuqqqq.common.area.cn.data.mapper;

import com.fuqqqq.common.area.cn.data.mapper.base.BaseMapper;
import com.fuqqqq.common.area.cn.data.model.TAreaModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface TAreaMapper extends BaseMapper {
    @Select({
            "SELECT",
            "`_code`, `_name`, `_level`",
            "FROM `t_area`",
            "WHERE `_code` = #{code,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column = "_code", property = "code", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "_level", property = "level", jdbcType = JdbcType.INTEGER)
    })
    TAreaModel selectByPrimaryKey(String code);

    @Select({
            "SELECT",
            "`_code`, `_name`, `_level`",
            "FROM `t_area`",
            "ORDER BY `_code` ASC"
    })
    @Results({
            @Result(column = "_code", property = "code", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "_level", property = "level", jdbcType = JdbcType.INTEGER)
    })
    List<TAreaModel> selectAll();


    //----- Extend Methods -------------------------------------------------------------------------------


    @Insert({
            "<script>",
            "INSERT INTO `t_area` (`_code`, `_name`, ",
            "`_level`)",
            "VALUES",
            "<foreach collection='rows' item='row' open='' close='' separator=','>",
            "(#{row.code,jdbcType=VARCHAR}, #{row.name,jdbcType=VARCHAR}, ",
            "#{row.level,jdbcType=INTEGER})",
            "</foreach>",
            "ON DUPLICATE KEY UPDATE",
            "`_name` = VALUES(`_name`),",
            "`_level` = VALUES(`_level`)",
            "</script>"
    })
    int insertBatch(TAreaModel... rows);

    @Select({
            "<script>",
            "SELECT",
            "`_code`, `_name`, `_level`",
            "FROM `t_area`",
            "WHERE `_level` = #{level,jdbcType=INTEGER}",
            "<if test='codePrefix != null and codePrefix != \"\"'>",
            "AND `_code` LIKE CONCAT(#{codePrefix,jdbcType=VARCHAR}, '%')",
            "</if>",
            "ORDER BY `_code` ASC",
            "</script>"
    })
    @Results({
            @Result(column = "_code", property = "code", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "_level", property = "level", jdbcType = JdbcType.INTEGER)
    })
    List<TAreaModel> selectByLevel(@Param("level") Integer level, @Param("codePrefix") String codePrefix);

    @Update({
            "TRUNCATE TABLE `t_area`"
    })
    void truncateTable();
}