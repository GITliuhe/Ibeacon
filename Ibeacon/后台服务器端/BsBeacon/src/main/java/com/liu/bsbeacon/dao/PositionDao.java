package com.liu.bsbeacon.dao;

import com.liu.bsbeacon.entity.CoordinateEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PositionDao {
    CoordinateEntity getCoorDinateByAreaNum(String areaNum);
}
