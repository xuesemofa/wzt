package org.consume.com.heatattribute.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.consume.com.heatattribute.model.AttributeModel;

import java.util.List;

/**
 * @name 换热站属性集合
 */
public interface AttributeMapper {
    /**
     * add
     *
     * @param model HeatModel
     * @return int
     */
    @Insert({
            "insert into heat_attribute_table set uuid = #{model.uuid},names = #{model.names}"
    })
    int add(@Param("model") AttributeModel model);

    /**
     * del
     *
     * @param id String
     * @return int
     */
    @Delete({
            "delete from heat_attribute_table where uuid = #{id}"
    })
    int del(@Param("id") String id);

    /**
     * update
     *
     * @param model HeatModel
     * @return int
     */
    @Update({
            "update heat_attribute_table set names = #{model.names} where uuid = #{model.uuid}"
    })
    int update(@Param("model") AttributeModel model);

    /**
     * findAllPage
     *
     * @return Page<HeatModel>
     */
    @Select({
            "select * from heat_attribute_table order by names"
    })
    Page<AttributeModel> findAllPage();

    @Select({
            "select * from heat_attribute_table order by names"
    })
    List<AttributeModel> findAll();

    @Select({
            "select * from heat_attribute_table where names = #{names}"
    })
    AttributeModel getByNames(@Param("names") String names);

    @Select({
            "select * from heat_attribute_table where uuid = #{id}"
    })
    AttributeModel getById(@Param("id") String id);
}
