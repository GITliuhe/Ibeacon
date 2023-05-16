package com.liu.bsbeacon.entity;

public class RssiFingerprintRawEntity {
    /**
     * 主键 id 自增
     */
    private Integer id;

    /**
     * 区域编号 areaNum
     */
    private String areaNum;

    /**
     * 坐标X coordinateX
     */
    private Integer coordinateX;

    /**
     * 坐标Y coordinateY
     */
    private Integer coordinateY;

    /**
     * 信标名称 beaconName
     */
    private String beaconName;

    /**
     * 蓝牙信标1 RSSI信号值 BeaconOneRssi
     */
    private Integer BeaconOneRssi;

    /**
     * 蓝牙信标2 RSSI信号值 BeaconTwoRssi
     */
    private Integer BeaconTwoRssi;

    /**
     * 蓝牙信标2 RSSI信号值 BeaconThreeRssi
     */
    private Integer BeaconThreeRssi;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaNum() {
        return areaNum;
    }

    public void setAreaNum(String areaNum) {
        this.areaNum = areaNum;
    }

    public Integer getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Integer coordinateX) {
        this.coordinateX = coordinateX;
    }

    public Integer getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Integer coordinateY) {
        this.coordinateY = coordinateY;
    }

    public String getBeaconName() {
        return beaconName;
    }

    public void setBeaconName(String beaconName) {
        this.beaconName = beaconName;
    }

    public Integer getBeaconOneRssi() {
        return BeaconOneRssi;
    }

    public void setBeaconOneRssi(Integer beaconOneRssi) {
        BeaconOneRssi = beaconOneRssi;
    }

    public Integer getBeaconTwoRssi() {
        return BeaconTwoRssi;
    }

    public void setBeaconTwoRssi(Integer beaconTwoRssi) {
        BeaconTwoRssi = beaconTwoRssi;
    }

    public Integer getBeaconThreeRssi() {
        return BeaconThreeRssi;
    }

    public void setBeaconThreeRssi(Integer beaconThreeRssi) {
        BeaconThreeRssi = beaconThreeRssi;
    }
}
