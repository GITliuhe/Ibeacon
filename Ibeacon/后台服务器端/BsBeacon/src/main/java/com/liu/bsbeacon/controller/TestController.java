package com.liu.bsbeacon.controller;

import com.liu.bsbeacon.entity.CoordinateEntity;
import com.liu.bsbeacon.entity.TestEntity;
import com.liu.bsbeacon.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    public TestEntity test(@PathVariable Integer id){
        System.out.println("id:" + id);
        return testService.getById(id);
    }


//        for(Map<String,String> item : BeaconArrayList){
//            if(item.containsKey("uuid")){
//                if(!item.get("uuid").isEmpty()){
////                    System.out.println(item.get("uuid"));
//                    if(item.get("uuid").equals("AC:23:3F:72:36:74")) {
//                        rssiFingerPrintEntity.setBeaconOneRssi(item.get("rssi"));
//                        rssiFingerPrintEntity.setBeaconOneUuid(item.get("uuid"));
//                    }else if(item.get("uuid").equals("AC:23:3F:72:37:FF")){
//                        rssiFingerPrintEntity.setBeaconTwoRssi(item.get("rssi"));
//                        rssiFingerPrintEntity.setBeaconTwoUuid(item.get("uuid"));
//                    }else if(item.get("uuid").equals("AC:23:3F:72:38:05")){
//                        rssiFingerPrintEntity.setBeaconThreeRssi(item.get("rssi"));
//                        rssiFingerPrintEntity.setBeaconThreeUuid(item.get("uuid"));
//                    }
//                }
//            }else if(item.containsKey("parkingLotName")){
//                if(!item.get("parkingLotName").isEmpty()){
//                    rssiFingerPrintEntity.setParkingLotName(item.get("parkingLotName"));
//                    rssiFingerPrintEntity.setAreaNum(item.get("areaNum"));
//                }
//            }
//
//        }
//        System.out.println(rssiFingerPrintEntity.getParkingLotName());
//        System.out.println(rssiFingerPrintEntity.getAreaNum());
//        System.out.println(rssiFingerPrintEntity.getBeaconOneRssi());
//        System.out.println(rssiFingerPrintEntity.getBeaconTwoRssi());
//        System.out.println(rssiFingerPrintEntity.getBeaconThreeRssi());

}
