package com.liu.bsbeacon.service;

import com.liu.bsbeacon.dao.CoordinateDao;
import com.liu.bsbeacon.entity.CoordinateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoordinateService {
    @Autowired
    private CoordinateDao coordinateDao;
    public CoordinateEntity selectByCoorXAndCoorY(String coordinateX, String coordinateY) {
        return coordinateDao.selectByCoorXAndCoorY(coordinateX, coordinateY);
    }

    public Integer addCoordinate(CoordinateEntity coordinateEntity) {
        return coordinateDao.addCoordinate(coordinateEntity);
    }
}
