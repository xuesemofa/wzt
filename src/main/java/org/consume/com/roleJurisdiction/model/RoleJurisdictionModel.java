package org.consume.com.roleJurisdiction.model;

import java.io.Serializable;

/*
* 角色权限关联关系
*
* */
public class RoleJurisdictionModel implements Serializable {
    //主键
    private String uuid;
    //角色主键
    private String roleId;
    //权限主键
    private String jurisdictionId;

    public RoleJurisdictionModel(String uuid, String roleId, String jurisdictionId) {
        this.uuid = uuid;
        this.roleId = roleId;
        this.jurisdictionId = jurisdictionId;
    }

    public RoleJurisdictionModel() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getJurisdictionId() {
        return jurisdictionId;
    }

    public void setJurisdictionId(String jurisdictionId) {
        this.jurisdictionId = jurisdictionId;
    }

    @Override
    public String toString() {
        return "RoleJurisdictionModel{" +
                "uuid='" + uuid + '\'' +
                ", roleId='" + roleId + '\'' +
                ", jurisdictionId='" + jurisdictionId + '\'' +
                '}';
    }
}
