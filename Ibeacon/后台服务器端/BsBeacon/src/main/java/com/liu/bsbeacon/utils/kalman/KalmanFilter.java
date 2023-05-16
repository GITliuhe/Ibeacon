package com.liu.bsbeacon.utils.kalman;

import com.liu.bsbeacon.entity.CoordinateEntity;
import com.liu.bsbeacon.utils.kalman.jama.Matrix;
import com.liu.bsbeacon.utils.kalman.jkalman.JKalman;
import org.apache.ibatis.logging.Log;

import java.awt.*;

public class KalmanFilter {
    private JKalman mFilter;
    private Matrix mPredictValue;
    private Matrix mCorrectedValue;
    private Matrix mMeasurementValue;
    private final String TAG = "KalmanFilter";
    public void KalmanFilter(){}
    public void initial(){
        try {
            mFilter = new JKalman(4, 2);
            int x = 0;
            int y = 0;
            // init
            mPredictValue = new Matrix(4, 1); // predict state [x, y, dx, dy, dxy]
            mCorrectedValue = new Matrix(4, 1); // corrected state [x, y, dx, dy, dxy]

            mMeasurementValue = new Matrix(2, 1); // measurement [x]
            mMeasurementValue.set(0, 0, x);
            mMeasurementValue.set(1, 0, y);
            // transitions for x, y, dx, dy
            double[][] tr = { {1, 0, 1, 0},
                    {0, 1, 0, 1},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1} };
            mFilter.setTransition_matrix(new Matrix(tr));
            // 1s somewhere?
            mFilter.setError_cov_post(mFilter.getError_cov_post().identity());
        } catch (Exception ex) {
        }
    }
    public void filter(CoordinateEntity oldValue, CoordinateEntity newValue) {
        mPredictValue = mFilter.Predict();
        mMeasurementValue.set(0, 0, oldValue.getCoorX());
        mMeasurementValue.set(1, 0, oldValue.getCoorY());
        mCorrectedValue = mFilter.Correct(mMeasurementValue);
        newValue.setCoorX((int) mPredictValue.get(0,0));
        newValue.setCoorY((int) mPredictValue.get(1,0));
    }
}
