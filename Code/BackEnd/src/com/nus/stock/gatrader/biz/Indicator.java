package com.nus.stock.gatrader.biz;

import java.util.Random;


public enum Indicator {
	INDICATOR_SMA5 ("SP>SMA5", 0),
	INDICATOR_SMA7 ("SP>SMA7", 1),
	INDICATOR_SMA14 ("SP>SMA14", 2),
	INDICATOR_SMA5_SMA14 ("SMA5>SMA14", 3),
	INDICATOR_EMA5("SP>EMA5",4),
	INDICATOR_EMA7("SP>EMA7",5),
	INDICATOR_EMA9("SP>EMA9",6),
	INDICATOR_EMA13("SP>EMA13",7),
	INDICATOR_EMA20("SP>EMA20",8),
	INDICATOR_EMA9_EMA20("EMA9>EMA20",9),
	INDICATOR_MACD("MACD>MACD_SIGNAL",10),
	INDICATOR_RSI_OVERSOLD("RSI<30",11),
	INDICATOR_RSI_OVERBOUGHT("RSI>70",12),
	INDICATOR_UBB("SP>UBB",13),
	INDICATOR_LBB("SP<LBB",14),
	INDICATOR_MBB("SP>MBB",15),
	INDICATOR_AVG_VOL10("VOL>AVG_VOL10",16),
	INDICATOR_INC_VOL("INC_VOL",17);
    
    private final String name;
    private final int index;

    public String getName() {
        return name;
    }
    
    public int getIndex(){
        return index;
    }
    
    private Indicator(String name, int index) {
        this.name = name;
        this.index = index;
    }
    
    public static Indicator getRandomIndicator() {
        Indicator[] indicators = Indicator.values();
        return indicators[new Random().nextInt(indicators.length)];
    }
}
