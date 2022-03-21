package com.p.appconfiguration;



import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;

public class Log
{
  public static Logger notice = Logger.getLogger("NOTICE");

  static {
	  FileAppender appender1 = null;
	
	  String log_path = PropertiesFile.getLogPath();
	  System.out.println(log_path);
	
	    try
	    {
	      appender1 = new DailyRollingFileAppender(new PatternLayout("%d{dd-MM-yyyy HH:mm:ss} %C %L %-5p: %m%n"), log_path, "'.'dd-MM-yyyy");
	    }
	    catch (IOException e)
	    {
	    	e.printStackTrace();
	    }
	    notice.setLevel(Level.DEBUG);
	    notice.addAppender(appender1);
  	}
}
