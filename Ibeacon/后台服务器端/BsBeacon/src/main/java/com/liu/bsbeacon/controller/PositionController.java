package com.liu.bsbeacon.controller;

import com.liu.bsbeacon.entity.CoordinateEntity;
import com.liu.bsbeacon.entity.RssiFingerPrintEntity;
import com.liu.bsbeacon.service.PositionService;
import com.liu.bsbeacon.service.RssiFingerPrintService;
import com.liu.bsbeacon.utils.CoordinateFormat;
import com.liu.bsbeacon.utils.EuclideanDistance;
import com.liu.bsbeacon.utils.QuickSort;
import com.liu.bsbeacon.utils.kalman.KalmanFilter;
import com.liu.bsbeacon.vo.CoordinateFormatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private RssiFingerPrintService rssiFingerPrintService;

    @Autowired
    private PositionService positionService;

    private List<RssiFingerPrintEntity> fingerList = new ArrayList<>();

    KalmanFilter kalmanFilter = new KalmanFilter();

    @RequestMapping(value = "/get/fingerPrinter", method = RequestMethod.GET)
    public void getRssiFingerPrinter(){
        kalmanFilter.initial();
        fingerList = rssiFingerPrintService.selectFingerPrinter();
        System.out.println("rssiOne: "+fingerList.get(1).getBeaconOneRssi());
        System.out.println("指纹大小为："+fingerList.size());
    }

    @PostMapping(value = "/actual/time")
    public CoordinateFormatVO position(@RequestBody Map<String,String> actualTimeRssi){
        System.out.println("接收到实时的RSSI为："+actualTimeRssi);
        //对实时接收的信号值进行过滤，以去掉跳变值，这里暂时不用，在定位结果处进行跳变坐标的过滤
//        Kalman kalman = new Kalman();
//        kalman.initial();
//        Map<String,String> filterActualRssi = kalman.actualTimeRssiFilter(actualTimeRssi);

        EuclideanDistance euclideanDistance = new EuclideanDistance();
        List<Map<String,Object>> distanceMapList = euclideanDistance.getDistance(fingerList,actualTimeRssi);
        System.out.println("排序后的map: "+distanceMapList);
        List<String> areaNumList = new ArrayList<>();
        List<Double> distanceList = new ArrayList<>();

        for (Map<String, Object> distanceMap : distanceMapList) {
            areaNumList.add((String) distanceMap.get("areaNum"));
            distanceList.add((Double) distanceMap.get("distance"));
        }


        QuickSort quickSort = new QuickSort();
        int high = distanceList.size() - 1;
        System.out.println("high:" + high);
        quickSort.sort(areaNumList,distanceList,0,high);

        System.out.println("排序后的区域列表："+areaNumList);
        System.out.println("排序后的距离列表："+distanceList);

        String areaNum = areaNumList.get(0);
        System.out.println("定位区域为："+areaNum);
        //根据实时信号值的定位结果坐标
        CoordinateEntity coordinatePosition = positionService.getCoorDinateByAreaNum(areaNum);
        System.out.println("定位坐标id为："+coordinatePosition.getId());
        System.out.println("定位坐标为：（"+coordinatePosition.getCoorX()+" , "+coordinatePosition.getCoorY()+")");


        //用于装载过滤后的坐标值
        CoordinateEntity coordinateFilterResult = new CoordinateEntity();
        //对定位结果坐标进行卡尔曼过滤，去掉跳变值
        kalmanFilter.filter(coordinatePosition,coordinateFilterResult);
        System.out.println("过滤后的定位坐标为：("+coordinateFilterResult.getCoorX()+" , "+coordinateFilterResult.getCoorY()+")");

        //将过滤完的坐标（x,y）转换为屏幕位置（left, top）对应的值
        CoordinateFormatVO coordinateFormatVO = new CoordinateFormatVO();
        CoordinateFormat coordinateFormat = new CoordinateFormat();
        int left = coordinateFormat.xFormat(coordinateFilterResult.getCoorX());
        int top = coordinateFormat.yFormat(coordinateFilterResult.getCoorY());
        coordinateFormatVO.setLeft(left);
        coordinateFormatVO.setTop(top);

        return coordinateFormatVO;
    }
}
