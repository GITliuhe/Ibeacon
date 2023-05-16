package com.liu.bsbeacon.service;


import com.liu.bsbeacon.dao.ParkingLotDao;
import com.liu.bsbeacon.entity.ParkingLotEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotDao parkingLotDao;
    public List<ParkingLotEntity> getParkingLot() {
        return parkingLotDao.getParkingLot();
    }

    public List<ParkingLotEntity> findParkingLotByName(String searchWOrd) {
        return parkingLotDao.findParkingLotByName(searchWOrd);
    }

    public void addParkingLotByName(ParkingLotEntity parkingLot) {
        parkingLotDao.addParkingLotByName(parkingLot);
    }

    public void deleteParkingLotByid(Integer id) {
        parkingLotDao.deleteParkingLotByid(id);
    }
}
