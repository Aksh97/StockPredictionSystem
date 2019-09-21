package com.nus.stock.jersey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;

import com.nus.stock.bean.Login;
import com.nus.stock.bean.StockSymbol;
import com.nus.stock.service.LoginService;
import com.nus.stock.service.StockSymbolService;
import com.nus.stock.util.JacksonUtils;
import com.nus.stock.util.Logger4j;
import com.nus.stock.util.ServiceFactoryBean;

@Path("/")
public class LoginRestService {

	private static final Logger log = Logger4j
			.getLogger(LoginRestService.class);

	private static LoginService loginService = ServiceFactoryBean
			.getLoginService();
	private static StockSymbolService stockSymbolService = ServiceFactoryBean
			.getStockSymbolService();
	public static String curRootPathString;
	
	@POST
	@Path("login")
	public String login(@Context HttpServletRequest request,
			@FormParam("userName") String userName,
			@FormParam("password") String password) {
		String path=request.getSession().getServletContext().getRealPath("");
    	com.nus.stock.util.Path.PRJ_BASE_PATH = path + "/../";
		String a = request.getSession().getServletContext().getRealPath("/");
		a = a.substring(0, a.length()-"/WebContent".length());
		curRootPathString = a;
		System.out.println("mwz-a:"+a);
		System.out.println("@mwz:  userName:" + userName + "  password:"
				+ password);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (null == userName || null == password) {
				resultMap.put("ack", "failed");
				resultMap.put("cause", "user name or password can't be empty！");
				return JacksonUtils.getJsonString(resultMap);
			}
			Login logInfo = loginService.findLoginByUserName(userName);
			if (null == logInfo) {
				resultMap.put("ack", "failed");
				resultMap.put("cause", "username is wrong！");
				return JacksonUtils.getJsonString(resultMap);
			}
			if (!logInfo.getPassword().equals(password)) {
				resultMap.put("ack", "failed");
				resultMap.put("cause", "password is wrong！");
				return JacksonUtils.getJsonString(resultMap);
			}
			System.out.println("mwz ok");

			List<StockSymbol> ss = stockSymbolService.findAllStock();

			resultMap.put("ack", "success");
			resultMap.put("symbol", ss);

			String ret = JacksonUtils.getJsonString(resultMap);
			System.out.println("mwz--" + ret);
			return ret;

		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("ack", "failed");
		}

		return JacksonUtils.getJsonString(resultMap);
	}

}
