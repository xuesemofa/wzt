package org.consume.com.jurisdiction.service.serviceImpl;

import org.consume.com.jurisdiction.mapper.JurisdictionMapper;
import org.consume.com.jurisdiction.model.JurisdictionModel;
import org.consume.com.jurisdiction.service.JurisdictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JurisdictionServiceImpl implements JurisdictionService {
    @Autowired
    private JurisdictionMapper mapper;

    @Override
    public int add(JurisdictionModel ju) {
        return mapper.add(ju);
    }

    @Override
    public int del(String uuid) {
        return mapper.del(uuid);
    }

    @Override
    public List<JurisdictionModel> findAll() {
        return mapper.findAll();
    }
}
