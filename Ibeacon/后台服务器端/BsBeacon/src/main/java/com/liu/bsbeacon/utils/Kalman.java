package com.liu.bsbeacon.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiuHe
 */
public class Kalman {

    private Integer predict;
    private Integer current;
    private Integer estimate;
    private Integer rssiOne;
    private Integer rssiTwo;
    private Integer rssiThree;
    private double pdelt;
    private double mdelt;
    private double Gauss;
    private double kalmanGain;
    private final static double Q = 0.00001;
    private final static double R = 0.1;
    public void initial(){
        pdelt = 12;    //系统测量误差
        mdelt = 3;
        rssiOne = -69;
        rssiTwo = -63;
        rssiThree = -68;
    }
    public Integer kalman(Integer oldValue,Integer value){
        //(1)第一个估计值
        predict = oldValue;
        current = value;
        //(2)高斯噪声方差
        Gauss = Math.sqrt(pdelt * pdelt + mdelt * mdelt) + Q;
        //(3)估计方差
        kalmanGain = Math.sqrt((Gauss * Gauss)/(Gauss * Gauss + pdelt * pdelt)) + R;
        //(4)估计值
        estimate = (int) (kalmanGain * (current - predict) + predict);
        //(5)新的估计方差
        mdelt = Math.sqrt((1-kalmanGain) * Gauss * Gauss);
        return estimate;
    }

    public List<Map<String,Integer>> KalmanFilter (List<Map<String, Integer>> rssiList) {
        AverageFilter averageFilter = new AverageFilter();
        Map<String, Integer> fingerPrinterRssi = averageFilter.averageFilter(rssiList);
        int rssiOne = fingerPrinterRssi.get("beaconOneRssi");
        int rssiTwo = fingerPrinterRssi.get("beaconTwoRssi");
        int rssiThree = fingerPrinterRssi.get("beaconThreeRssi");
        List<Map<String, Integer>> rssiListNew = new ArrayList<>();
        for (Map<String, Integer> rssi : rssiList) {
            Map<String, Integer> rssiMap = new HashMap<>();
            rssiMap.put("beaconRssiOne",kalman(rssiOne,rssi.get("OneRssi")));
            rssiMap.put("beaconRssiTwo",kalman(rssiTwo,rssi.get("TwoRssi")));
            rssiMap.put("beaconRssiThree",kalman(rssiThree,rssi.get("ThreeRssi")));
            rssiListNew.add(rssiMap);
            rssiOne = kalman(rssiOne,rssi.get("OneRssi"));
            rssiTwo = kalman(rssiTwo,rssi.get("TwoRssi"));
            rssiThree = kalman(rssiThree,rssi.get("ThreeRssi"));


        }
        return rssiListNew;
    }

    public  Map<String,String> actualTimeRssiFilter (Map<String,String> actualTimeRssi) {


        Map<String,String> filterRssi = new HashMap<>();
        rssiOne = kalman(rssiOne, Integer.parseInt(actualTimeRssi.get("rssiOne")));
        rssiTwo = kalman(rssiTwo, Integer.parseInt(actualTimeRssi.get("rssiTwo")));
        rssiThree = kalman(rssiThree, Integer.parseInt(actualTimeRssi.get("rssiTwo")));

        filterRssi.put("rssiOne",rssiOne.toString());
        filterRssi.put("rssiTwo",rssiTwo.toString());
        filterRssi.put("rssiThree",rssiThree.toString());

        return filterRssi;

    }
}
