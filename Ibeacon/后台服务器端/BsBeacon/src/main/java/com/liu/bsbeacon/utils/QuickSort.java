package com.liu.bsbeacon.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author LiuHe
 * @discription 快速排序
 */
public class QuickSort {
    /**
     * 对求出的欧式距离进行排序，并同时修改每个距离所对应的区域编号的顺序
     * @param areaNumList
     * @param distanceList
     * @param low
     * @param high
     */
    public void sort(List<String> areaNumList, List<Double> distanceList,int low,int high) {
        int i,j;
        if (low > high) {
            return ;
        }
        double tempDistance = distanceList.get(low);
        String tempAreaNum = areaNumList.get(low);
        i = low;
        j = high;
        while (i < j) {
            while (tempDistance <= distanceList.get(j) && i<j ) {
                j--;
            }

            while (tempDistance >= distanceList.get(i) && i<j) {
                i++;
            }

            if (i<j) {
                String areaNumLeft = areaNumList.get(i);
                String areaNumRight = areaNumList.get(j);
                double distanceLeft = distanceList.get(i);
                double distanceRight = distanceList.get(j);
                areaNumList.set(i,areaNumRight);
                areaNumList.set(j,areaNumLeft);
                distanceList.set(i,distanceRight);
                distanceList.set(j,distanceLeft);
            }
        }
        areaNumList.set(low,areaNumList.get(i));
        areaNumList.set(i,tempAreaNum);
        distanceList.set(low,distanceList.get(i));
        distanceList.set(i,tempDistance);
        sort(areaNumList,distanceList,low,j-1);
        sort(areaNumList,distanceList,j+1,high);
    }
}
