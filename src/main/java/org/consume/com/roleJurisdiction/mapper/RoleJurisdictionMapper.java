package org.consume.com.roleJurisdiction.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.consume.com.roleJurisdiction.model.RoleJurisdictionModel;

public interface RoleJurisdictionMapper {
    //添加角色与权限间的对应关系
    @Insert({"INSERT INTO rolejurisdiction_table VALUES (#{roju.uuid},#{roju.roleId},#{roju.jurisdictionId})"})
    int add(@Param("roju") RoleJurisdictionModel roju);

    //删除角色与权限间的对应关系
    @Delete({"DELETE FROM rolejurisdiction_table WHERE uuid = #{uuid}"})
    int del(@Param("uuid") String uuid);
}
