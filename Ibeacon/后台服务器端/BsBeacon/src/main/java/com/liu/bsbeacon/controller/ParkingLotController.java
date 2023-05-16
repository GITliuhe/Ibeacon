package com.liu.bsbeacon.controller;


import com.liu.bsbeacon.entity.ParkingLotEntity;
import com.liu.bsbeacon.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @RequestMapping(value = "/parkingLot", method = RequestMethod.GET)
    public List<ParkingLotEntity> getParkingLot () {

        List<ParkingLotEntity> parkingLotList = parkingLotService.getParkingLot();

        return parkingLotList;

    }

    @RequestMapping(value = "/findParkingLot", method = RequestMethod.POST)
    public List<ParkingLotEntity> findParkingLot (@RequestBody Map<String,String> info) {

        System.out.println(info);
        List<ParkingLotEntity> parkingLotList = parkingLotService.findParkingLotByName(info.get("Word"));

        return parkingLotList;

    }

    @RequestMapping(value = "/addParkingLot", method = RequestMethod.POST)
    public void addParkingLot (@RequestBody Map<String,String> parkingLotInfo) {

        System.out.println(parkingLotInfo);
        ParkingLotEntity parkingLot = new ParkingLotEntity();
        parkingLot.setParkingLotName(parkingLotInfo.get("parkingLotName"));
        parkingLot.setParkingLotAddress(parkingLotInfo.get("address"));
        parkingLot.setBeaconName(parkingLotInfo.get("beaconName"));

        parkingLotService.addParkingLotByName(parkingLot);



    }

    @RequestMapping(value = "/deleteParkingLot", method = RequestMethod.POST)
    public void deleteParkingLot (@RequestBody Integer id) {

        System.out.println(id);

        parkingLotService.deleteParkingLotByid(id);



    }
}
