package com.liu.bsbeacon.dao;

import com.liu.bsbeacon.entity.RssiFingerprintRawEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RssiFingerPrintRawDao {
    void addRssiRaw(RssiFingerprintRawEntity rssiFingerprintRaw);
}
