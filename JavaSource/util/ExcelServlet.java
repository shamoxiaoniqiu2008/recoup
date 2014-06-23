package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import service.system.MessageDoman;
import service.system.MessageMessage;

import com.google.gson.Gson;
import com.onlyfido.util.SessionManager;
import com.onlyfido.util.logger.HisLogger;

import domain.employeeManage.PensionEmployee;


@WebServlet("/ExcelServlet.Servlet")
public class ExcelServlet extends HttpServlet implements Serializable {
	/**
	 * HisLogger.
	 */
	private static HisLogger<?> logger = HisLogger.getLogger(ExcelServlet.class);
	/**
	 * 版本号.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * sevice.
	 */
	private transient MessageMessage messageMessage;
	/**
	 * bean 工厂.
	 */
	private WebApplicationContext factory;


	public ExcelServlet() {
		super();
	}

	public ExcelServlet(MessageMessage messageMessage) {
		super();
		this.messageMessage = messageMessage;
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
		/*final String birthday = request.getParameter("birthday");
		final String ethnicityName = request.getParameter("ethnicityName");*/
	System.out.println("执行doGet");
		final PrintWriter out = response.getWriter();
		// 获取受管bean
		messageMessage = (MessageMessage) factory.getBean("messageMessage");
		HttpSession session = request.getSession(false);  
		if (session != null) {  
			//PensionSysUser sysUser = (PensionSysUser) session.getAttribute(SessionManager.USER);
			PensionEmployee employee = (PensionEmployee) session.getAttribute(SessionManager.EMPLOYEE);
			Long userId = employee.getId();
			Long dptId= employee.getDeptId();  
		
			Integer messageCount = messageMessage.readMessageCount(dptId, userId);
			out.print("{\"messageCount\":\""+messageCount+"\",\"curUser\":\""+employee.getName()+"\"}");
		}  else {
			out.print("{\"messageCount\":\"0\",\"curUser\":\"noUser\"}");
		}
	    
		//Long dptId = ((PensionSysUser) SessionManager.getSessionAttribute(SessionManager.USER) ).getId();
		//Long userId = ((PensionEmployee)SessionManager.getSessionAttribute(SessionManager.EMPLOYEE)).getDeptId();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String excelName = request.getParameter("excelName");
		response.setHeader("Content-Disposition","attachment; filename="+excelName+".xls");
		String asdf = request.getParameter("contentExcel");
		final PrintWriter out = response.getWriter();
		out.print(asdf);

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
