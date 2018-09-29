package com.hywy.publichzt.entity;

/**
 * Created by Superman on 2018/6/26.
 */

public class WaterClassify {
    public WaterClassify(String name, double value, int color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }

    public WaterClassify(String name, double value) {
        this.name = name;
        this.value = value;
    }

    private String name;
    private double value;
    private int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    //总磷类别
    public static int getTPtype(double d) {
        if (d <= 0.02) {
            return 1;
        } else if (d > 0.02 && d <= 0.1) {
            return 2;
        } else if (d > 0.1 && d <= 0.2) {
            return 3;
        } else if (d > 0.2 && d <= 0.3) {
            return 4;
        } else if (d > 0.3 && d <= 0.4) {
            return 5;
        }
        return 5;
    }

    //氟化物类别
    public static int getFtype(double d) {
        if (d <= 1.0) {
            return 1;
        } else if (d > 1.0 && d <= 1.5) {
            return 4;
        }
        return 5;
    }

    //溶解氧类别
    public static int getRjytype(double d) {
        if (d >= 7.5) {
            return 1;
        } else if (d >= 6.0 && d < 7.5) {
            return 2;
        } else if (d >= 5.0 && d < 6.0) {
            return 3;
        } else if (d >= 3.0 && d < 5.0) {
            return 4;
        } else if (d >= 2.0 && d < 3.0) {
            return 5;
        }
        return 5;
    }

    //高锰酸盐类别
    public static int getGmsytype(double d) {
        if (d <= 2.0) {
            return 1;
        } else if (d > 2.0 && d <= 4.0) {
            return 2;
        } else if (d > 4.0 && d <= 6.0) {
            return 3;
        } else if (d > 6.0 && d <= 10.0) {
            return 4;
        } else if (d > 10.0 && d <= 15.0) {
            return 5;
        }
        return 5;
    }
}
