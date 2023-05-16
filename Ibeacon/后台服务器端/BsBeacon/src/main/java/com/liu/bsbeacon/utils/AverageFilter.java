package com.liu.bsbeacon.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuhe
 *
 * @discription 均值滤波法
 */
public class AverageFilter {

    public Map<String, Integer> averageFilter(List<Map<String, Integer>> rssiList) {
        Map<String, Integer> resultMap = new HashMap<>();
        int beaconRssiOne = 0;
        int beaconRssiTwo = 0;
        int beaconRssiThree = 0;
        for (Map<String, Integer> rssi : rssiList) {
            beaconRssiOne += rssi.get("beaconOneRssi");
            beaconRssiTwo += rssi.get("beaconTwoRssi");
            beaconRssiThree += rssi.get("beaconThreeRssi");
        }
//        System.out.println("家和之后beaconRssiOne："+beaconRssiOne);
        System.out.println("除数为："+rssiList.size());
        beaconRssiOne = beaconRssiOne / rssiList.size();
        beaconRssiTwo = beaconRssiTwo / rssiList.size();
        beaconRssiThree = beaconRssiThree / rssiList.size();

        resultMap.put("beaconOneRssi",beaconRssiOne);
        resultMap.put("beaconTwoRssi",beaconRssiTwo);
        resultMap.put("beaconThreeRssi",beaconRssiThree);

        System.out.println("均值过滤后："+resultMap);
        return resultMap;
    }
}
