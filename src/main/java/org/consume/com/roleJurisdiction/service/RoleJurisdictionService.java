package org.consume.com.roleJurisdiction.service;

import org.consume.com.roleJurisdiction.model.RoleJurisdictionModel;

public interface RoleJurisdictionService {
    //添加角色与权限间的对应关系
    int add(RoleJurisdictionModel roju);

    //删除角色与权限间的对应关系
    int del(String uuid);
}
