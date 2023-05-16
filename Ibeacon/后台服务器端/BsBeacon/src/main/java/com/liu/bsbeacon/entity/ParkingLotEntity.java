package com.liu.bsbeacon.entity;

/**
 *
 * @author LiuHe
 * @discription 停车场实体
 *
 * @date 2022/04/12
 */
public class ParkingLotEntity {

    /**
     * 主键 id 自增
     */
    private Integer id;

    /**
     * 停车场名称 parkingLotName
     */
    private String parkingLotName;

    /**
     * 停车场地址 parkingLotAddress
     */
    private String parkingLotAddress;

    /**
     * 布点信标localName beaconName
     */
    private String beaconName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public String getParkingLotAddress() {
        return parkingLotAddress;
    }

    public void setParkingLotAddress(String parkingLotAddress) {
        this.parkingLotAddress = parkingLotAddress;
    }

    public String getBeaconName() {
        return beaconName;
    }

    public void setBeaconName(String beaconName) {
        this.beaconName = beaconName;
    }


}
