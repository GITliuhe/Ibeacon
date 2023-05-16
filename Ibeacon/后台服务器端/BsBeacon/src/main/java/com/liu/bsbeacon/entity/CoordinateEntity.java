package com.liu.bsbeacon.entity;

/**
 *
 * @author LiuHe
 * @discription 位置坐标实体
 *
 * @date 2022/04/12
 */
public class CoordinateEntity {

    /**
     * 主键 id 自增
     */
    private Integer id;

    /**
     * X坐标 coordinateX
     */
    private Integer coorX;

    /**
     * Y坐标 coordinateY
     */
    private Integer coorY;

    /**
     * 车位id spaceId
     */
    private Integer spaceId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCoorX() {
        return coorX;
    }

    public void setCoorX(Integer coorX) {
        this.coorX = coorX;
    }

    public Integer getCoorY() {
        return coorY;
    }

    public void setCoorY(Integer coorY) {
        this.coorY = coorY;
    }

    public Integer getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }
}
