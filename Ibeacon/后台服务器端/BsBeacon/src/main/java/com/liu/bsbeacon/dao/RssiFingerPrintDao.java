package com.liu.bsbeacon.dao;

import com.liu.bsbeacon.entity.RssiFingerPrintEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RssiFingerPrintDao {

    void addRssiFingerPrint(RssiFingerPrintEntity rssiFingerPrintEntity);

    List<RssiFingerPrintEntity> selectFingerPrinter();
}
