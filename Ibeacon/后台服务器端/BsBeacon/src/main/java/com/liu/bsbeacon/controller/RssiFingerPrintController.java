package com.liu.bsbeacon.controller;

import com.liu.bsbeacon.entity.CoordinateEntity;
import com.liu.bsbeacon.entity.RssiFingerPrintEntity;
import com.liu.bsbeacon.entity.RssiFingerprintRawEntity;
import com.liu.bsbeacon.service.RssiFingerPrintRawService;
import com.liu.bsbeacon.service.RssiFingerPrintService;
import com.liu.bsbeacon.service.CoordinateService;
import com.liu.bsbeacon.utils.AverageFilter;
import com.liu.bsbeacon.utils.Kalman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/add")
public class RssiFingerPrintController {
    @Autowired
    private RssiFingerPrintService rssiFingerPrintService;

    @Autowired
    private CoordinateService coordinateService;

    @Autowired
    private RssiFingerPrintRawService rssiFingerPrintRawService;

    @PostMapping("/rssi/fingerprint")
    public void addRssiFingerPrint(@RequestBody List<Map<String,String>> BeaconArrayList) {
        System.out.println(BeaconArrayList);
        RssiFingerPrintEntity rssiFingerPrintEntity = new RssiFingerPrintEntity();
        List<Map<String, Integer>> rssiList = new ArrayList<>();
        //BeaconArrayList 第一个元素为坐标、区域等信息，截取掉
        List<Map<String,String>> subBeaconArrayList= BeaconArrayList.subList(1,BeaconArrayList.size());
        Map<String, String> fingerPrinterInfo = BeaconArrayList.get(0);
        Map<String, String> beaconUUID = BeaconArrayList.get(1);
        for(Map<String, String> beaconRssiInfo : subBeaconArrayList) {
            RssiFingerprintRawEntity rssiFingerprintRaw = new RssiFingerprintRawEntity();
            Map<String, Integer> rssiMap = new HashMap<>();
            rssiMap.put("beaconOneRssi",Integer.parseInt(beaconRssiInfo.get("rssiOne")));
            rssiMap.put("beaconTwoRssi",Integer.parseInt(beaconRssiInfo.get("rssiTwo")));
            rssiMap.put("beaconThreeRssi",Integer.parseInt(beaconRssiInfo.get("rssiThree")));
            rssiList.add(rssiMap);

            rssiFingerprintRaw.setAreaNum(fingerPrinterInfo.get("areaNum"));
            rssiFingerprintRaw.setCoordinateX(Integer.parseInt(fingerPrinterInfo.get("coordinateX")));
            rssiFingerprintRaw.setCoordinateY(Integer.parseInt(fingerPrinterInfo.get("coordinateY")));
            rssiFingerprintRaw.setBeaconName(beaconUUID.get("BeaconName"));
            rssiFingerprintRaw.setBeaconOneRssi(Integer.parseInt(beaconRssiInfo.get("rssiOne")));
            rssiFingerprintRaw.setBeaconTwoRssi(Integer.parseInt(beaconRssiInfo.get("rssiTwo")));
            rssiFingerprintRaw.setBeaconThreeRssi(Integer.parseInt(beaconRssiInfo.get("rssiThree")));
            rssiFingerPrintRawService.addRssiRaw(rssiFingerprintRaw);
        }
        AverageFilter averageFilter = new AverageFilter();
        Map<String, Integer> fingerPrinterRssi = averageFilter.averageFilter(rssiList);



        rssiFingerPrintEntity.setAreaNum(fingerPrinterInfo.get("areaNum"));
        rssiFingerPrintEntity.setBeaconOneRssi(fingerPrinterRssi.get("beaconOneRssi"));
        rssiFingerPrintEntity.setBeaconTwoRssi(fingerPrinterRssi.get("beaconTwoRssi"));
        rssiFingerPrintEntity.setBeaconThreeRssi(fingerPrinterRssi.get("beaconThreeRssi"));
        rssiFingerPrintEntity.setBeaconName(beaconUUID.get("BeaconName"));


        CoordinateEntity coordinate = coordinateService.selectByCoorXAndCoorY(fingerPrinterInfo.get("coordinateX"),
                                                                              fingerPrinterInfo.get("coordinateY"));
        if (coordinate == null) {
            CoordinateEntity coordinateEntity = new CoordinateEntity();
            coordinateEntity.setCoorX(Integer.parseInt(fingerPrinterInfo.get("coordinateX")));
            coordinateEntity.setCoorY(Integer.parseInt(fingerPrinterInfo.get("coordinateY")));
            int Id = coordinateService.addCoordinate(coordinateEntity);
            rssiFingerPrintEntity.setCoordinateId(coordinateEntity.getId());
            rssiFingerPrintService.addRssiFingerPrint(rssiFingerPrintEntity);
        }else {
            rssiFingerPrintEntity.setCoordinateId(coordinate.getId());
            rssiFingerPrintService.addRssiFingerPrint(rssiFingerPrintEntity);
        }


    }

    @PostMapping("/rssi/fingerprints")
    public void addRssiFingerPrints(@RequestBody List<Map<String,String>> BeaconArrayList) {
        System.out.println(BeaconArrayList);
        RssiFingerPrintEntity rssiFingerPrintEntity = new RssiFingerPrintEntity();
        List<Map<String, Integer>> rssiList = new ArrayList<>();
        //BeaconArrayList 第一个元素为坐标、区域等信息，截取掉
        List<Map<String,String>> subBeaconArrayList= BeaconArrayList.subList(1,BeaconArrayList.size());
        for(Map<String, String> beaconRssiInfo : subBeaconArrayList) {
            Map<String, Integer> rssiMap = new HashMap<>();
            rssiMap.put("OneRssi",Integer.parseInt(beaconRssiInfo.get("rssiOne")));
            rssiMap.put("TwoRssi",Integer.parseInt(beaconRssiInfo.get("rssiTwo")));
            rssiMap.put("ThreeRssi",Integer.parseInt(beaconRssiInfo.get("rssiThree")));
            rssiList.add(rssiMap);
        }

        Kalman kalman = new Kalman();
        kalman.initial();
        List<Map<String, Integer>> rssiListNew = kalman.KalmanFilter(rssiList);
        List<Integer> listOld = new ArrayList<>();
        List<Integer> listNew = new ArrayList<>();
        for (Map<String,Integer> map1 : rssiList) {
            listOld.add(map1.get("OneRssi"));
        }

        for (Map<String, Integer> map2 : rssiListNew) {
            listNew.add(map2.get("beaconRssiOne"));
        }

        System.out.println("过滤前rssiOne: "+listOld);
        System.out.println("过滤后的rssiOne: "+listNew);






//        AverageFilter averageFilter = new AverageFilter();
//        Map<String, Integer> fingerPrinterRssi = averageFilter.averageFilter(rssiList);
//
//        Map<String, String> fingerPrinterInfo = BeaconArrayList.get(0);
//        Map<String, String> beaconUUID = BeaconArrayList.get(1);
//
//        rssiFingerPrintEntity.setAreaNum(fingerPrinterInfo.get("areaNum"));
//        rssiFingerPrintEntity.setBeaconOneRssi(fingerPrinterRssi.get("beaconOneRssi"));
//        rssiFingerPrintEntity.setBeaconTwoRssi(fingerPrinterRssi.get("beaconTwoRssi"));
//        rssiFingerPrintEntity.setBeaconThreeRssi(fingerPrinterRssi.get("beaconThreeRssi"));
//        rssiFingerPrintEntity.setBeaconName(beaconUUID.get("BeaconName"));
//
//
//        CoordinateEntity coordinate = coordinateService.selectByCoorXAndCoorY(fingerPrinterInfo.get("coordinateX"),
//                fingerPrinterInfo.get("coordinateY"));
//        if (coordinate == null) {
//            CoordinateEntity coordinateEntity = new CoordinateEntity();
//            coordinateEntity.setCoordinateX(Integer.parseInt(fingerPrinterInfo.get("coordinateX")));
//            coordinateEntity.setCoordinateY(Integer.parseInt(fingerPrinterInfo.get("coordinateY")));
//            int Id = coordinateService.addCoordinate(coordinateEntity);
//            rssiFingerPrintEntity.setCoordinateId(coordinateEntity.getId());
//            rssiFingerPrintService.addRssiFingerPrint(rssiFingerPrintEntity);
//        }else {
//            rssiFingerPrintEntity.setCoordinateId(coordinate.getId());
//            rssiFingerPrintService.addRssiFingerPrint(rssiFingerPrintEntity);
//        }


    }
}
