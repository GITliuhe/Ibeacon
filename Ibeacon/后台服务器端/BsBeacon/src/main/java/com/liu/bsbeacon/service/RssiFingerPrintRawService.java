package com.liu.bsbeacon.service;

import com.liu.bsbeacon.dao.RssiFingerPrintRawDao;
import com.liu.bsbeacon.entity.RssiFingerprintRawEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RssiFingerPrintRawService {
    @Autowired
    private RssiFingerPrintRawDao rssiFingerPrintRawDao;

    public void addRssiRaw(RssiFingerprintRawEntity rssiFingerprintRaw) {
        rssiFingerPrintRawDao.addRssiRaw(rssiFingerprintRaw);
    }
}
