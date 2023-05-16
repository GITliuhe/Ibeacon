package com.liu.bsbeacon.dao;

import com.liu.bsbeacon.entity.TestEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestDao {

    TestEntity getById(Integer id);
}
