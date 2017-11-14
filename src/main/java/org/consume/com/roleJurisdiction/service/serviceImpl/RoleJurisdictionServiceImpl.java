package org.consume.com.roleJurisdiction.service.serviceImpl;

import org.consume.com.roleJurisdiction.mapper.RoleJurisdictionMapper;
import org.consume.com.roleJurisdiction.model.RoleJurisdictionModel;
import org.consume.com.roleJurisdiction.service.RoleJurisdictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleJurisdictionServiceImpl implements RoleJurisdictionService {
    @Autowired
    private RoleJurisdictionMapper mapper;

    @Override
    public int add(RoleJurisdictionModel roju) {
        return mapper.add(roju);
    }

    @Override
    public int del(String uuid) {
        return mapper.del(uuid);
    }
}
