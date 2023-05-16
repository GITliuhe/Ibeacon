package com.liu.bsbeacon.utils;

public class CoordinateFormat {

    public int xFormat (int X) {
        int left = 0;

        switch (X) {
            case 1:
                left = 15;
                break;
            case 2:
                left = 75;
                break;
            case 3:
                left = 135;
                break;
            case 4:
                left = 200;
                break;
            case 5:
                left = 260;
                break;
            case 6:
                left = 315;
                break;

        }

        return left;
    }

    public int yFormat (int Y) {

        int top = 0;

        switch (Y) {

            case 1:
                top = 650;
                break;
            case 2:
                top = 560;
                break;
            case 3:
                top = 470;
                break;
            case 4:
                top = 380;
                break;
            case 5:
                top = 290;
                break;
            case 6:
                top = 205;
                break;
            case 7:
                top = 115;
                break;
            case 8:
                top = 30;
                break;
        }

        return top;
    }
}
