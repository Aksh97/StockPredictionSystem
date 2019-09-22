package com.nus.stock.gatrader.biz;

import java.util.Arrays;


public class Candle {
    private String datetime;
    private double open;
    private double close;
    private double high;
    private double low;
    private int volume;
    public boolean [] indicatorList;

    public String getDatetime() {
        return datetime;
    }

    public Candle setDatetime(String datetime) {
        this.datetime = datetime;
        return this;
    }

    public double getOpen() {
        return open;
    }

    public Candle setOpen(double open) {
        this.open = open;
        return this;
    }

    public double getClose() {
        return close;
    }

    public Candle setClose(double close) {
        this.close = close;
        return this;
    }

    public double getHigh() {
        return high;
    }

    public Candle setHigh(double high) {
        this.high = high;
        return this;
    }

    public double getLow() {
        return low;
    }

    public Candle setLow(double low) {
        this.low = low;
        return this;
    }

    public double getVolume() {
        return volume;
    }

    public Candle setVolume(int volume) {
        this.volume = volume;
        return this;
    }

	@Override
	public String toString() {
		return "Candle [datetime=" + datetime + ", open=" + open + ", close="
				+ close + ", high=" + high + ", low=" + low + ", volume="
				+ volume + ", indicatorList=" + Arrays.toString(indicatorList)
				+ "]";
	}
      
}
