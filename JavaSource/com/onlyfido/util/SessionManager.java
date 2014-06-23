package com.onlyfido.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import domain.employee.PensionEmployee;
import domain.system.PensionSysUser;

public class SessionManager {

	public static final String USER = "user";

	public static final String USER_NAME = "username";

	
	public static final String CATEGORIER = "categories";
	

	public static final String EMPLOYEE = "employee";
	
	public static final String LOCK = "lock";
	
	/**
	 * 定时器session_id
	 */
	public static final String TIMER_WEB_SERVICE="timerWebService";
	
	public static Object getSessionAttribute(String key) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext == null) {
			return null;
		}
		
		//当没有session时，会跳转出去
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(false);

		if (session != null) {
			Object object = session.getAttribute(key);

			return object;
		}
		return null;

	}

	public static String fectchLock() {
		return (String) getSessionAttribute(LOCK);
	}
	/**
	 * 取得当前staff
	 * 
	 * @return
	 */
	public static PensionSysUser getCurUser() {
		return (PensionSysUser) getSessionAttribute(USER);
	}

	/**
	 * 获取当前员工
	 * @return
	 */
	public static PensionEmployee getCurEmployee() {
		return (PensionEmployee) getSessionAttribute(EMPLOYEE);
	}

	/**
	 * 设置session中某个key键的值
	 * 
	 * @return
	 * @author Ade
	 */
	public static void setSessionAttribute(String key, Object obj) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext != null) {
			HttpSession session = (HttpSession) facesContext
					.getExternalContext().getSession(true);
			if (session != null) {
				session.setAttribute(key, obj);
			}
		}

	}
}
