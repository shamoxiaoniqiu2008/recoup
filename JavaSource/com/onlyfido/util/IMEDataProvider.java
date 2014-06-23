package com.onlyfido.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.el.ValueExpression;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * 输入法候选数据服务程序
 * 
 * @author centling
 * 
 */
public class IMEDataProvider implements PhaseListener {

	public UIComponent searchComponuet(String id, UIComponent cur) {

		String clientid = cur.getClientId();

		//System.out.println("curId ="+ clientid);
		//System.out.println("id ="+ id);
		// System.out.println("ID:"+ String.valueOf(clientid) +" class: " +
		// cur.getClass().toString());
		if (clientid.equals(id))
			return cur;

		for (int i = 0; i < cur.getChildCount(); i++) {

			UIComponent ret = searchComponuet(id, cur.getChildren().get(i));
			
			//System.out.println("cur.getChildren().get(i) ="+ cur.getChildren().get(i));
			//System.out.println("id ="+ id);
			
			if (ret != null) {
				return ret;
			}
		}

		return null;
	}

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void walkThroughComponets(UIComponent cur){
		
		//System.out.println(cur.getClientId());

		if (cur instanceof EditableValueHolder) {
			EditableValueHolder editableValueHolder = (EditableValueHolder) cur;
			ValueExpression valueExpression =  cur.getValueExpression("value");
			//--------------------你这样先试试-----------------------
			if(valueExpression != null)
			{
				if (editableValueHolder.getSubmittedValue() != null
				 || editableValueHolder.getLocalValue() != null	) {
							//System.out.println(cur.getClass()+ " ---  -resetvalude");
					editableValueHolder.resetValue();
				}
			}

		}
		
		
		for (int i = 0; i < cur.getChildCount(); i++) {
			walkThroughComponets( cur.getChildren().get(i));
		}
	}
	
	

	
	
	
	public void dispatchAjax(PhaseEvent arg0){
		
		
		// System.out.println("afterPhase" + arg0.getPhaseId().toString());
		FacesContext context = arg0.getFacesContext();
		Map requestMap = context.getExternalContext().getRequestParameterMap();
		if (requestMap.containsKey("ajaxreq")) {

			if (requestMap.containsKey("etype")) {

			} else {
				String idString = (String) requestMap.get("id");
				idString = idString.trim();
				UIViewRoot viewRoot = context.getViewRoot();
				//System.out.println("=========================id ="+ idString);
				if (viewRoot != null) {
					AjaxInterface ajaxComponent = null;
					try {
						//ajaxComponent = (AjaxInterface) viewRoot
						//		.findComponent(idString);
						ajaxComponent = (AjaxInterface)searchComponuet(idString,viewRoot);
						
					} catch (ClassCastException cce) {
						throw new IllegalArgumentException(
								"Component found under Ajax key was not of expected type.");
					}
					if (ajaxComponent != null) {
						ajaxComponent.handleAjaxRequest(context);
					} else {
						throw new IllegalArgumentException("findComponent 失败.[" + idString+"]");
					}
				}
				


			}
		}
		
		
	}
	
	
	@Override
	public void afterPhase(PhaseEvent arg0) {
		if (arg0.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
			dispatchAjax(arg0);
		}else if (arg0.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
			resetComponetState(arg0);
		}
	}

	
//	private boolean redirectStamp(PhaseEvent arg0){
//		
//
//		
//		FacesContext context = arg0.getFacesContext();
//	    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
//		HttpServletResponse response = (HttpServletResponse) context
//		.getExternalContext().getResponse();
//		
//		Map<String, String> maptempp = context.getExternalContext().getRequestHeaderMap();
//		String refferUrlString = maptempp.get("X-Requested-With");
//		if (refferUrlString != null) {
//			return false;
//		}
//		
//		
//	    //请求地址
//	    StringBuffer stringBuffer =  request.getRequestURL();
//
//
//	    
//	    
//		if (stringBuffer.indexOf("login.jsf") > -1) {
//			//登陆页
//			return false;
//		}else if(stringBuffer.indexOf("error.jsf")>-1){//错误页
//			return false;
//		}else if(stringBuffer.indexOf("sessionTimeOut.jsf") > -1){
//			return false;
//		}
//		String timeStamp = NavigatorController.getRequestPara("timeStamp");
//		if (timeStamp == null) {
//			//请求中不包含时间戳。。。。重定向生成。
//			try {
//				//response.sendRedirect(request.getContextPath() + "/sessionTimeOut.jsf");
//			
//				String finalStr = request.getRequestURI()+"?";
//				Map<String, String> map = context.getExternalContext().getRequestParameterMap();
//				
//				
//			    Set<Map.Entry<String, String>> entryseSet=map.entrySet();
//				for (Map.Entry<String, String> entry:entryseSet) {
//					
//					finalStr += entry.getKey()+ "=" + entry.getValue() ;
//					finalStr += "&";
//				}
//				
//				HttpSession httpSession =(HttpSession) context.getExternalContext().getSession(false);
//				String curtimeStamp =NavigatorController.getRefferPara("timeStamp");
//				if (curtimeStamp == null) {
//					curtimeStamp = httpSession.getAttribute("timeStamp").toString();
//				}
//				
//				finalStr += "timeStamp=";
//				finalStr += curtimeStamp ;
//				response.sendRedirect(finalStr);
//			
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			context.responseComplete();
//			
//			return true;
//		}
//		return false;
//	}
		
	
	
	
//	/**
//	 * 用页面标记的当前用户与session中存储的当前用户进行比较，不相同则该页面为过期页面。
//	 * @Description:
//	 * @param arg0
//	 * @return
//	 * @throws:
//	 * @author:Patrick.Guo
//	 * @Date:2012-9-5下午02:08:43
//	 * @version:1.0
//	 * @Copyright: Copyright (c) 2012 Company:Centling
//	 */
//	private boolean invalidUsr(PhaseEvent arg0){
//		
//		FacesContext context = arg0.getFacesContext();
//	    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
//	    StringBuffer stringBuffer =  request.getRequestURL();
//		HttpSession httpSession =(HttpSession) context.getExternalContext().getSession(false);
//	    
//		
//		boolean sessionTimeOut = false;
//		Map<String, String> maptempp = context.getExternalContext().getRequestHeaderMap();
//		String refferUrlString = maptempp.get("X-Requested-With");
//
//		if (stringBuffer.indexOf("login.jsf") > -1) {
//			//登陆页
//			return sessionTimeOut;
//		}else if(stringBuffer.indexOf("error.jsf")>-1){//错误页
//			return sessionTimeOut;
//		}else if(stringBuffer.indexOf("sessionTimeOut.jsf") > -1){
//			return sessionTimeOut;
//		}
//				
//		if(httpSession != null){
//			StaffInfo staffInfo = (StaffInfo) httpSession.getAttribute(SessionManager.STAFF);
//			if (staffInfo == null) {
//				//session不为空但无法取得session中的当前用户。。。
//				sessionTimeOut = true;
//			}
//		}else {
//			//session为空。。
//			sessionTimeOut = true;
//		}
//		
//		if (!sessionTimeOut) {
//			//用户校验
//			String staffid = NavigatorController.getRequestPara("usrId");
//			if (staffid == null) {
//				staffid = NavigatorController.getRefferPara("usrId");
//			}
//			
//			if (staffid != null) {
//				if (!staffid.equals(SessionManager.getCurStaff().getStaffId().toString())) {
//					//页面记录的id与当前session中不符。。。
//					sessionTimeOut = true;
//				}
//			}
//		}
//		
//		//校验session时间戳
//		if (!sessionTimeOut) {
//			
//			String timeStamp = NavigatorController.getRequestPara("timeStamp");
//			if (timeStamp == null) {
//				timeStamp = NavigatorController.getRefferPara("timeStamp");
//			}
//			
//			if (timeStamp != null) {
//				if (!timeStamp.equals(httpSession.getAttribute("timeStamp"))) {
//					//页面记录的时间戳与当前session中不符。。。
//					sessionTimeOut = true;
//				}
//			}else {
//				sessionTimeOut = true;
//			}
//		}
//
//		if (sessionTimeOut) {
//			
//			HttpServletResponse response = (HttpServletResponse) context
//			.getExternalContext().getResponse();
//			
//			if (sessionTimeOut) {
//
//				if (refferUrlString == null
//				|| !refferUrlString.equals("XMLHttpRequest")) {
//					//非ajax请求
//
//					try {
//						response.sendRedirect(request.getContextPath() + "/sessionTimeOut.jsf");
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					context.responseComplete();
//					
//				} else {
//					//ajax请求
//					FacesContext facesContext = FacesContext.getCurrentInstance();
//					ExternalContext externalContext = facesContext.getExternalContext();
//				
//					String strContextPathString = externalContext.getRequestContextPath();
//					String temp = stringBuffer.substring(0,stringBuffer.indexOf(strContextPathString));
//					strContextPathString += "/index.html";
//					strContextPathString = temp + strContextPathString;
//					
//					response.setContentType("text/html");
//					response.setHeader("Cache-Control", "no-cache");
//					response.setCharacterEncoding("UTF-8");
//					try {
//						response.getWriter().write(
//								"userTimeOut\\" + strContextPathString);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					context.responseComplete();
//				}
//
//			}
//		}
//		
//		return sessionTimeOut;
//	}
	
	
//	private boolean sessionTimeOut(PhaseEvent arg0){
//		
//		boolean sessionTimeOut = false;
//		
//		FacesContext context = arg0.getFacesContext();
//	    HttpServletRequest request =
//                              (HttpServletRequest) context.getExternalContext().getRequest();
//		StringBuffer stringBuffer =  request.getRequestURL();
//		if (!arg0.getFacesContext().getPartialViewContext().isAjaxRequest()) {
//			return sessionTimeOut;
//		}
//	
//		if (stringBuffer.indexOf("login") > -1) {
//			//登陆页
//			return sessionTimeOut;
//		}else if(stringBuffer.indexOf("error")>-1){//错误页
//			return sessionTimeOut;
//		}
//
//
//
//			HttpSession httpSession =(HttpSession) context
//			.getExternalContext()
//			.getSession(false);
//			
//			if(httpSession != null){
//				StaffInfo staffInfo = (StaffInfo) httpSession.getAttribute(SessionManager.STAFF);
//				if (staffInfo == null) {
//					sessionTimeOut = true;
//				}
//			}else {
//				sessionTimeOut = true;
//			}
//			
//		
//		
//		HttpServletResponse response = (HttpServletResponse) context
//		.getExternalContext().getResponse();
//		
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		ExternalContext externalContext = facesContext.getExternalContext();
//	
//		String strContextPathString = externalContext.getRequestContextPath();
//		String temp = stringBuffer.substring(0,stringBuffer.indexOf(strContextPathString));
//		strContextPathString += "/index.html";
//		strContextPathString = temp + strContextPathString;
//
//		if (sessionTimeOut) {
//			response.setContentType("text/html");
//			response.setHeader("Cache-Control", "no-cache");
//			response.setCharacterEncoding("UTF-8");
//			try {
//				response.getWriter().write("sessionTimeOut\\" + strContextPathString);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			context.responseComplete();
//		}
//		return sessionTimeOut;
//	}

	
	private void resetComponetState(PhaseEvent arg0){
		FacesContext context = arg0.getFacesContext();
		UIViewRoot viewRoot = context.getViewRoot();
		if (viewRoot != null) {
			walkThroughComponets(viewRoot);
		}

	}
	
	
	@Override
	public void beforePhase(PhaseEvent arg0) {
		if (arg0.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
			
//			if (redirectStamp(arg0)) {
//				return;
//			}
//			
//			
//			if (invalidUsr(arg0)) {
//				return;
//			}
//			
//			
//			sessionTimeOut(arg0);
		}else if (arg0.getPhaseId().equals(PhaseId.RENDER_RESPONSE)) {
			FacesContext context = arg0.getFacesContext();
			
		    HttpServletRequest request =
                (HttpServletRequest) context.getExternalContext().getRequest();
		    StringBuffer stringBuffer =  request.getRequestURL();
			
			if (stringBuffer.indexOf("login") > -1) {
				//登陆页
				context
				.getExternalContext()
				.getSession(true);
				
				
			}

		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
