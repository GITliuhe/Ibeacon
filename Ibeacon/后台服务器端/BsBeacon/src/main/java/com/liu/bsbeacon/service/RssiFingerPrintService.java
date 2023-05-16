package com.liu.bsbeacon.service;

import com.liu.bsbeacon.dao.RssiFingerPrintDao;
import com.liu.bsbeacon.entity.RssiFingerPrintEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RssiFingerPrintService {
    @Autowired
    private RssiFingerPrintDao rssiFingerPrintDao;
    public void addRssiFingerPrint(RssiFingerPrintEntity rssiFingerPrintEntity) {
        rssiFingerPrintDao.addRssiFingerPrint(rssiFingerPrintEntity);
    }

    public List<RssiFingerPrintEntity> selectFingerPrinter() {
        return rssiFingerPrintDao.selectFingerPrinter();
    }
}
