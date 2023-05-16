package com.liu.bsbeacon.dao;

import com.liu.bsbeacon.entity.CoordinateEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CoordinateDao {
    CoordinateEntity selectByCoorXAndCoorY(@Param("coorX") String coordinateX, @Param("coorY") String coordinateY);

    Integer addCoordinate(CoordinateEntity coordinateEntity);
}
