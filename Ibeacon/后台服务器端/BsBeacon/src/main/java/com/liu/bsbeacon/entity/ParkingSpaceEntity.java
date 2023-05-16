package com.liu.bsbeacon.entity;

/**
 *
 * @author LiuHe
 * @discription 停车位实体
 *
 * @date 2022/04/12
 */
public class ParkingSpaceEntity {

    /**
     * 主键 id 自增
     */
    private Integer id;

    /**
     * 停车场id parkingLotId
     */
    private Integer parkingLotId;

    /**
     * 坐标id coordinateId
     */
    private Integer coordinateId;

    /**
     * 车位编号 spaceNum
     */
    private String spaceNum;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Integer parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public Integer getCoordinateId() {
        return coordinateId;
    }

    public void setCoordinateId(Integer coordinateId) {
        this.coordinateId = coordinateId;
    }

    public String getSpaceNum() {
        return spaceNum;
    }

    public void setSpaceNum(String spaceNum) {
        this.spaceNum = spaceNum;
    }
}
