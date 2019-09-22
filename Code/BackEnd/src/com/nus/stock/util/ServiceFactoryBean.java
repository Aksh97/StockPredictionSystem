package com.nus.stock.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nus.stock.service.LoginService;
import com.nus.stock.service.StockSymbolService;

public class ServiceFactoryBean
{
    private static ApplicationContext ctx = new ClassPathXmlApplicationContext("basic-config.xml");
    
  
    public static Object getService(String beanName)
    {
        return ctx.getBean(beanName);
    }
    
   
    public static LoginService getLoginService()
    {
        return (LoginService) getService("loginService");
    }
    
    
    public static StockSymbolService getStockSymbolService()
    {
        return (StockSymbolService) getService("stockSymbolService");
    }
    
    
}
