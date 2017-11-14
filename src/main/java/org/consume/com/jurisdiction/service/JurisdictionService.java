package org.consume.com.jurisdiction.service;

import org.consume.com.jurisdiction.model.JurisdictionModel;

import java.util.List;

public interface JurisdictionService {
    //增加权限
    int add(JurisdictionModel ju);

    //删除权限
    int del(String uuid);

    //查询所有
    List<JurisdictionModel> findAll();
}
