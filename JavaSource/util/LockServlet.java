package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import service.system.MessageDoman;
import service.system.MessageMessage;

import com.google.gson.Gson;
import com.onlyfido.util.SessionManager;
import com.onlyfido.util.logger.HisLogger;

import domain.employee.PensionEmployee;
import domain.system.PensionMessages;
import domain.system.PensionSysUser;


@WebServlet("/LockServlet.Servlet")
public class LockServlet extends HttpServlet implements Serializable {
	/**
	 * HisLogger.
	 */
	private static HisLogger<?> logger = HisLogger.getLogger(LockServlet.class);
	/**
	 * 版本号.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * bean 工厂.
	 */
	private WebApplicationContext factory;


	public LockServlet() {
		super();
	}

	
	
	public @ResponseBody
	String selectUser(@RequestParam("ethnicityName") String ethnicityName ){
		
		return ethnicityName;
		
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * @overrides HttpServlet#doGet(HttpServletRequest request,
	 *            HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println("执行doGet");
		String getLock = request.getParameter("getLock");
		
		HttpSession session = request.getSession(false);  
		final PrintWriter out = response.getWriter();
		if(getLock != null && !getLock.isEmpty()) {
			if (session != null) {  
				String lock = (String) session.getAttribute(SessionManager.LOCK);
				out.print(lock);
			} 
		} else {
			if (session != null) {  
				session.setAttribute(SessionManager.LOCK, "true");//(SessionManager.LOCK, "true");
			} 
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/* 初始化方法.
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		// -----------BeanFactory IoC容器---------------------//
		final ServletContext application = getServletContext();
		factory = WebApplicationContextUtils
				.getWebApplicationContext(application); // 获取spring的context
	}

}
