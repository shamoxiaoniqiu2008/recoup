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

import service.olderManage.RingService;
import service.system.MessageDoman;
import service.system.MessageMessage;

import com.centling.his.util.SessionManager;
import com.centling.his.util.logger.HisLogger;
import com.google.gson.Gson;

import domain.employeeManage.PensionEmployee;


@WebServlet("/RingServlet.Servlet")
public class RingServlet extends HttpServlet implements Serializable {
	/**
	 * HisLogger.
	 */
	private static HisLogger<?> logger = HisLogger.getLogger(RingServlet.class);
	/**
	 * 版本号.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * sevice.
	 */
	private transient RingService ringService;
	/**
	 * bean 工厂.
	 */
	private WebApplicationContext factory;


	private Integer lastCount = 0;
	public RingServlet() {
		super();
	}

	public RingServlet(RingService ringService) {
		super();
		this.ringService = ringService;
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
		ringService = (RingService) factory.getBean("ringService");
		HttpSession session = request.getSession(false);  
		if (session != null) {  
		
			Integer ringCount = ringService.readRingCount();
			out.print("{\"ringCount\":\""+ringCount+"\",\"lastCount\":\""+lastCount+"\"}");
			lastCount = ringCount;
//			if(ringCount>0)
//				try {
//					ringService.sendNotifyToTop();
//				} catch (PmsException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
		}  else {
			out.print("{\"messageCount\":\"0\"}");
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
