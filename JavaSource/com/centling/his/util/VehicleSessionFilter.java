package com.centling.his.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Encryption;
import util.JavaSN;

/**
 * Description:
 * @author Ade Wang
 * Date:2013-8-5 下午08:19:58
 */

public class VehicleSessionFilter implements Filter {

	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		
		//设置请求数据的编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession(false);//如果session为null，则返回null
		
		boolean loggedIn = (session != null) && session.getAttribute(SessionManager.USER_NAME) != null && !session.getAttribute(SessionManager.USER_NAME).equals("");//已经登录的标志
		String requestURI = request.getRequestURI();
		String projectName = request.getContextPath();
		boolean loginRequest = requestURI.startsWith(projectName+"/pages/login.xhtml") || requestURI.startsWith(projectName+"/pages/login.jsf");
		/*
		 * 此处必须区分出是否是资源请求，如果是资源请求则写入到客户端，否则是资源请求但是直接redirect会导致css、js样式等不正常
		 */
		boolean resourceRequest = requestURI.startsWith(projectName+ResourceHandler.RESOURCE_IDENTIFIER);//identifier: /javax.faces.resource
		String redirectURL = projectName+"/pages/login.jsf";
		
		
		
		
		/****************************************/
		boolean err = false;
		try {
			String cpuId = JavaSN.fetchProperty("ID", VehicleServletContextListener.snFullPath);
			//cpuid = DES.JM(cpuid);
			cpuId = cpuId.substring(cpuId.length() -  5) + cpuId.substring(0, cpuId.length() -  5) ;
			
			StringBuffer b  = new StringBuffer(cpuId);
			cpuId = b.reverse().toString();
			
			String key = JavaSN.fetchProperty("SN", VehicleServletContextListener.snFullPath);
			String toEndDate = Encryption.readDate(key);
			//String sn = Encryption.readKey(key);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date deadLine = sdf.parse(toEndDate);
				if(deadLine.before(new Date())) {
					err = true;
				}
			
			
			String countSn = Encryption.countKey(toEndDate, cpuId);
			if(!countSn.equals(key)) {
				err = true;
			}
		} catch (Exception e) {
			err = true;
		}
	
			String redirectsURL = request.getContextPath()+"/pages/err.jsf";
			//String requestURIs = request.getRequestURI();
			/*if(!requestURIs.startsWith(redirectsURL)) {
				response.sendRedirect(redirectsURL);
			}*/
	
		boolean isErrPage  = requestURI.startsWith(redirectsURL);
		/****************************************/
			
		err = false;
		
		if(err) {
			redirectURL = redirectsURL;
			loggedIn = false;
			loginRequest = false;
			resourceRequest = false;
		}
			
		if((loggedIn || loginRequest || resourceRequest || isErrPage) ){
			/*
			 * 如果是资源请求，则cache缓存
			 */
			if(!resourceRequest){
				/*
				 * cache-control适应http1.1
				 */
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
				
				/*
				 * pragma适应http1.0
				 */
	            response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	            response.setDateHeader("Expires", 0); // Proxies.
			}
			filterChain.doFilter(arg0, arg1);  
		}else{
			
			boolean ajaxFlag = isAjaxRequest(request);//是否是ajax请求
			if(ajaxFlag){
				
				/*
				 * 是ajax请求，客户端无法重定向到指定页面，
				 * 则需要写回到client端，通过common.js文件中的jquery.success,jquery.error进行重定向
				 */
				response.getWriter().write("sessionTimeOut\\"+redirectURL);
				
			}else{
			
				response.sendRedirect(redirectURL);
			}
			
		}
		
		
//		if(checkRequestURIInNotCheckURI(request)){//此uri不需要check
//			
//			filterChain.doFilter(arg0, arg1);  
//			
//		}else{//此uri需要check
//			
//			if(session.getAttribute(SessionManager.USER_NAME) == null || session.getAttribute(SessionManager.USER_NAME) == ""){
//				
//				
//				String contextPaht = request.getContextPath();//是“/projectName”比如"/texstVehicle"的
//				String redirectURL = contextPaht+redirectPagePath;//最终重定向的页面路径
//				
//				boolean ajaxFlag = isAjaxRequest(request);//是否是ajax请求
//				
//				if(ajaxFlag){
//					
//					/*
//					 * 是ajax请求，客户端无法重定向到指定页面，
//					 * 则需要写回到client端，通过common.js文件中的jquery.success,jquery.error进行重定向
//					 */
//					
//					response.getWriter().write("sessionTimeOut\\"+redirectURL);
//					
//				}else{
//				
//					response.sendRedirect(redirectURL);
//				}
//				
//				/*
//				 * 到这里response已经生成，所以需要调responseComplete来结束jsf生命周期
//				 * 这里会有空指针异常，不知道为什么
//				 */
//				FacesContext.getCurrentInstance().responseComplete();
//				
//			}else{
//				filterChain.doFilter(arg0, arg1);  
//			}
//			
//		}
		
	}

	/* 初始化过滤器
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		
	}

	
	
	/**
	 * 判断是否为Ajax请求
	 * @param request   HttpServletRequest
	 * @return  是true, 否false
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
	    String requestType = request.getHeader("X-Requested-With");
	    if (requestType != null && requestType.equals("XMLHttpRequest")) {
	        return true;
	    } else {
	        return false;
	    }
	}
}
