package com.liu.bsbeacon.utils;

import com.liu.bsbeacon.entity.RssiFingerPrintEntity;

import java.util.*;

/**
 * @author liuhe
 *
 * @discription 欧式距离算法，用来计算实时RSSI数组与指纹间的欧式距离
 */
public class EuclideanDistance {
    /**
     * @discription 计算实时rssi值与指纹库的欧式距离
     * @param rssiFingerPrinterList
     * @param actualTimeRssiMap
     * @return
     */
    public List<Map<String,Object>> getDistance(List<RssiFingerPrintEntity> rssiFingerPrinterList,
                           Map<String, String> actualTimeRssiMap) {
        List<Map<String,Object>> distanceList = new ArrayList<>();

        for (RssiFingerPrintEntity rssiFingerPrinter : rssiFingerPrinterList) {
            Map<String,Object> distanceMap = new HashMap<>();
            double distance = Math.sqrt(Math.pow(rssiFingerPrinter.getBeaconOneRssi()-Integer.parseInt(actualTimeRssiMap.get("rssiOne")),2)+
                                     Math.pow(rssiFingerPrinter.getBeaconTwoRssi()-Integer.parseInt(actualTimeRssiMap.get("rssiTwo")),2)+
                                     Math.pow(rssiFingerPrinter.getBeaconThreeRssi()-Integer.parseInt(actualTimeRssiMap.get("rssiThree")),2));
            distanceMap.put("distance",distance);
            distanceMap.put("areaNum",rssiFingerPrinter.getAreaNum());
            distanceList.add(distanceMap);
        }
        return distanceList;
    }
}
