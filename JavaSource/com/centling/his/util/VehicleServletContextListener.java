package com.centling.his.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import util.DES;
import util.Encryption;
import util.JavaSN;

/**
 * Description:工程实现的servletcontext 监听器
 * @author Ade Wang
 * Date:2013-8-7 上午01:32:18
 */

public class VehicleServletContextListener implements ServletContextListener{
	
	public static final String propertyName = "javaConfig.properties";

	public static String curPropertyFullPath="";//当前配置文件的全路径，在初始化之后存在。。。
	public static String snFullPath="";
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		/*
		 * el表达式存在bug就是对于number的类型，如果不输入值，提交到后台就成了零
		 * 
		 * 英文注释：
		 *  you need to ensure that this is been set before JSF/EL ever get initialized. 
		 *  Best place would be a ServletContextListener.
		 *  
		 *  也可以在Tomcat中配置，-Dorg.apache.el.parser.COERCE_TO_ZERO=false，但是这样需要每个人都配置
		 *  所以就配置在这里
		 *  
		 * 
		 * 这个配置就是真个JVM，都不会成零
		 */
		
		try {
			InitProperty(arg0);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("初始化配置文件错误");
			throw new RuntimeException();
		}
		
		System.setProperty("org.apache.el.parser.COERCE_TO_ZERO", "false");
	}

	/**
	 * 工程路径
	 * @param arg0
	 * @throws Exception
	 */
	public void InitProperty(ServletContextEvent arg0) throws Exception {
		// 当前工程目录
		String appPathString = arg0.getServletContext().getRealPath("/");
		System.out.println("工程路径：" + appPathString);
		int index = appPathString.substring(0, appPathString.length() - 1).lastIndexOf("\\") + 1;
		if (index == 0 || index == -1) {
			index =  appPathString.substring(0, appPathString.length() - 1).lastIndexOf("/") + 1;
			if (index == 0 || index == -1) {
				throw new Exception();
			}
		}
		
		
		
		// 实际配置文件路径。。。
		String temp = appPathString + "";
		String newPropertyPathDir = temp + "WEB-INF/classes/";
		curPropertyFullPath = newPropertyPathDir + propertyName;
		
		String cpuId = Encryption.getCPUSerial();
		//cpuId = DES.KL(cpuId);
		snFullPath = newPropertyPathDir +"sn.properties";
		if(!cpuId.equals("err")){
			StringBuffer b  = new StringBuffer(cpuId);
			cpuId = b.reverse().toString();
			cpuId =cpuId.substring(5) + cpuId.substring(0, 5) ;

			
			JavaSN.applyProperties(snFullPath, "ID", cpuId);
		}
	}

}
