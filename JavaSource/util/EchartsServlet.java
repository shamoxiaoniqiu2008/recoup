package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import persistence.olderManage.PensionLivingrecordMapper;

import service.system.FeeEchartsDeman;
import service.system.MainService;
import service.system.MessageDoman;
import service.system.MessageEchartsDeman;
import service.system.MessageMessage;
import service.system.NurseEchartsDeman;

import com.centling.his.util.SessionManager;
import com.centling.his.util.logger.HisLogger;
import com.google.gson.Gson;

import domain.employeeManage.PensionEmployee;


@WebServlet("/EchartsServlet.Servlet")
public class EchartsServlet extends HttpServlet implements Serializable {
	/**
	 * HisLogger.
	 */
	private static HisLogger<?> logger = HisLogger.getLogger(EchartsServlet.class);
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


	public EchartsServlet() {
		super();
	}

	public EchartsServlet(MessageMessage messageMessage,MainService mainService) {
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
		
		final PrintWriter out = response.getWriter();
		// 获取受管bean
		messageMessage = (MessageMessage) factory.getBean("messageMessage");
		HttpSession session = request.getSession(false);  
		String olderIdStr = request.getParameter("olderId");
		String tabStr = request.getParameter("tab");
		if(olderIdStr != null){
			Long olderId = Long.parseLong(olderIdStr);
			List<FeeEchartsDeman> feeList = messageMessage.selectOlderTotalFee(olderId);
			Gson gson = new Gson();
			String feeListStr = gson.toJson(feeList);
			out.print(feeListStr);
			out.flush();
			out.close();
		}else if(tabStr != null){
			if (session != null) {
				PensionEmployee employee = (PensionEmployee) session.getAttribute(SessionManager.EMPLOYEE);
				if(employee != null && employee.getId() != null ) {
					Long userId = employee.getId();
					Long deptId = employee.getDeptId();
					List<MessageEchartsDeman> messageList = messageMessage.classifyMessages(deptId, userId);
					List<Integer> olderFlowList = messageMessage.selectOlderFlowList();
					Gson gson = new Gson();
					String messageListStr = gson.toJson(messageList);
					String olderFlowStr = gson.toJson(olderFlowList);
					String jsonStr = "[{\"messageListStr\":"+messageListStr+",\"olderFlowStr\":"+olderFlowStr+"}]"; 
					out.print(jsonStr);
					out.flush();
					out.close();
				}
			}
		}else{
			if (session != null) {
				PensionEmployee employee = (PensionEmployee) session.getAttribute(SessionManager.EMPLOYEE);
				if(employee != null && employee.getId() != null ) {
					Long userId = employee.getId();
					List<NurseEchartsDeman> nurseList = messageMessage.selectNurseList(userId);
					Gson gson = new Gson();
					String nurseListStr = gson.toJson(nurseList);
					String jsonStr = "[{\"nurseList\":"+nurseListStr+"}]"; 
					out.print(jsonStr);
					out.flush();
					out.close();
				}
			}
		} 
		
		
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
