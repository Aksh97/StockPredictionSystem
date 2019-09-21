package com.nus.stock.util;
import java.io.File;
import java.lang.Exception;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;

public class Logger4j
{
    protected Logger4j() throws Exception
    {
        throw new Exception();
    }
    
    public static Logger getLogger(Class clazz)
    {
        return getLog4jLogger(clazz);
    }
    
    private static Logger getLog4jLogger(Class clazz)
    {
        Logger logger = Logger.getLogger(clazz);
        logger.addAppender(getAppender(clazz.getName()));
        return logger;
    }
    
    public static RollingFileAppender getAppender(String className)
    {
        RollingFileAppender appender = null;
        try
        {
            appender = new RollingFileAppender(null, getPath(className));
            appender.setMaxFileSize("5MB");
            appender.setMaxBackupIndex(5);
        }
        catch(Exception e)
        {
            new Exception(e);
        }
        return appender;
    }
    
    public static String getPath(String className)
    {
        StringBuffer path = new StringBuffer();
        path.append("logs");
        path.append("/" + className);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        path.append(format.format(new Date()) + ".log");
        return path.toString();
    }
    
    public static void checkPathExists(StringBuffer path)
    {
        String thePath = path.toString();
        File file = new File(thePath);
        if(!file.exists())
            file.mkdir();
    }
}
