package util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class EchoServerThread extends Thread {

	private int port;
	DatagramSocket socket;
	public EchoServerThread(int port)
	{
		this.setPort(port);
	}
	
	public void run() 
	{ 
		System.out.println("多线程测试！---------------------------------------------\n"); 
		//启动socket监听
		try {
			// 构建DatagramSocket实例，指定本地端口。   
			DatagramSocket socket = new DatagramSocket(this.port);
			int recvMsgSize = 1024;
			byte []receiveBuf = new byte[recvMsgSize];
			// 构建需要收发的DatagramPacket报文   
			DatagramPacket packet = new DatagramPacket(receiveBuf, recvMsgSize);  
			
			while(true)   
			{   
			    // 收报文   
			    socket.receive(packet);   
			    System.out.println("Handling client at " + packet.getAddress().getHostAddress() + " on port " + packet.getPort());   
			    //事件处理
			    /*
				1 指纹登记，记录到指纹登记表中
				2 后台开启socket监听，有指纹记录上传，
				就和指纹登记表中指纹字符匹配，匹配到就记录到指纹记录表
				3 指纹记录表每记录一条，如果type为员工，则轮询探访记录表，
				如果新记录dept_id和探访记录表中的匹配，且探访时间或离开时间为空，
					if 探访时间为空，则把该条记录的记录时间记为探访时间
					else 不为空，则 查看该条记录时间是否在
					（探访时间+预订两次最少间隔，预订两次最长间隔）之间，
					是则将该条记录时间计入探访记录的离开时间，
					并生成探访持续时间
			     */
			    //发送通知，在message中插入一条消息通知
			    // 发报文   
			    socket.send(packet);   
			    packet.setLength(recvMsgSize);   
			}    

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   

	}

	public void stopThread() {

		//关闭socket
		if(socket!=null)
		{
			socket.close();
		}
	}
	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	} 
}
