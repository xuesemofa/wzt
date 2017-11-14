package org.consume.com.jurisdiction.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.consume.com.jurisdiction.model.JurisdictionModel;

import java.util.List;

public interface JurisdictionMapper {
    //增加权限
    @Insert({"INSERT INTO jurisdiction_table VALUES (#{ju.uuid},#{ju.name},#{ju.url},#{ju.tye},#{ju.parent})"})
    int add(@Param("ju") JurisdictionModel ju);

    //删除权限
    @Delete({"DELETE FROM jurisdiction_table WHERE uuid = #{uuid}"})
    int del(@Param("uuid") String uuid);

    //查询所有
    @Select({"SELECT * FROM jurisdiction_table ORDER BY parent"})
    List<JurisdictionModel> findAll();
}
