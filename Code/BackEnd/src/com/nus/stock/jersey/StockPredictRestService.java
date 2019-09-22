package com.nus.stock.jersey;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.nus.stock.gatrader.biz.Trader;
import com.nus.stock.gatrader.biz.Trader.Decision2;
import com.nus.stock.util.JacksonUtils;
import com.nus.stock.util.Json2Obj;
import com.nus.stock.util.Logger4j;

@Path("/stock")
public class StockPredictRestService {
    private static final Logger log = Logger4j.getLogger(StockPredictRestService.class);
    private static final Trader trader = new Trader();
    
    private final class StockHistory {
    	private String symbol;
    	private String start;
    	private String end;
    	
		
		public String getSymbol() {
			return symbol;
		}
		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}
		public String getStart() {
			return start;
		}
		public void setStart(String start) {
			this.start = start;
		}
		public String getEnd() {
			return end;
		}
		public void setEnd(String end) {
			this.end = end;
		}
		@Override
		public String toString() {
			return "History [symbol=" + symbol + ", start=" + start + ", end="
					+ end + "]";
		}
    	
    }
    
    @POST
    @Path("predict")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String stockPredict(@Context HttpServletRequest request, String jstr)
    {
    	String path=request.getSession().getServletContext().getRealPath("");
    	com.nus.stock.util.Path.PRJ_BASE_PATH = path + "/../";
    	System.out.println("mwzjstr:"+jstr);
    	String symbol = "";
		try {
			JSONObject obj = new JSONObject(jstr);
	        symbol = obj.getJSONObject("data").getString("symbol");
	        System.out.println("mwzsymbol "+symbol);
		} catch (JSONException e) {
			e.printStackTrace();
		}
         
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	Decision2 decision = trader.predictStockTrend(symbol);
    	if (decision != null) {
    		String trendString = decision.getTrend();//(decision==Decision.BUY) ? "up" : "down";
        	double confidence = decision.getConfidence();
        	resultMap.put("trend", trendString);
        	resultMap.put("confidence", confidence);
    	} else {
    		resultMap.put("trend", null);
        	resultMap.put("confidence", 0);
    	}
    	
    	String ret =  JacksonUtils.getJsonString(resultMap);
        System.out.println("mwz--"+ret);
        return ret;
    }
   
    @GET
    @Path("history")
    @Produces(MediaType.APPLICATION_JSON)
    public String stockHistory(@Context HttpServletRequest request,String jstr)
    {
    	System.out.println("mwzjstr:"+jstr);
    	StockHistory s = (StockHistory) Json2Obj.getObj(jstr, StockHistory.class);
    	System.out.println(s.toString());
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	trader.getHistoryData("");
    	String ret =  JacksonUtils.getJsonString(resultMap);
        System.out.println("mwz--"+ret);
        return ret;
    }

}
