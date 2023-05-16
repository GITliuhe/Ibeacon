package com.liu.bsbeacon.dao;


import com.liu.bsbeacon.entity.ParkingLotEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ParkingLotDao {
    List<ParkingLotEntity> getParkingLot();

    List<ParkingLotEntity> findParkingLotByName(String searchWOrd);

    void addParkingLotByName(ParkingLotEntity parkingLot);

    void deleteParkingLotByid(Integer id);
}
