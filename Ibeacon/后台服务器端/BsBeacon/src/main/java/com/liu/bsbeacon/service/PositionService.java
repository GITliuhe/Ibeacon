package com.liu.bsbeacon.service;

import com.liu.bsbeacon.dao.PositionDao;
import com.liu.bsbeacon.entity.CoordinateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {
    @Autowired
    private PositionDao positionDao;



    public CoordinateEntity getCoorDinateByAreaNum(String areaNum) {
        return positionDao.getCoorDinateByAreaNum(areaNum);
    }
}
