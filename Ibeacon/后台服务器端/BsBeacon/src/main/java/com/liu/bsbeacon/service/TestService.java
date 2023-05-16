package com.liu.bsbeacon.service;

import com.liu.bsbeacon.dao.TestDao;
import com.liu.bsbeacon.entity.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private TestDao testDao;

    public TestEntity getById(Integer id){
        return testDao.getById(id);
    }
}
