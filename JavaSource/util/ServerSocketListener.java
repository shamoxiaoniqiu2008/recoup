package util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServerSocketListener implements ServletContextListener {

	private EchoServerThread echoServerThread;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		echoServerThread.stopThread();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
//      String port = event.getServletContext().getInitParameter("socketPort");
//      event.getServletContext().log("++++++++++++++++++++++++++++++++");
//      event.getServletContext().log("++++ Socket服务随web启动而启动 ++++");        
        echoServerThread = new EchoServerThread(9888);        
        echoServerThread.start();
//      event.getServletContext().log("++++ Socket服务已经启动完毕，端口：" + port + " ++++");
	}

}
