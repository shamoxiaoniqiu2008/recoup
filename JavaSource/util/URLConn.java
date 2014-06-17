package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class URLConn {
	public static String sendGet(String url, String param, StringBuffer sessionIdStr) throws PmsException {
	    String result = "";
	    BufferedReader in =null;
	    String urlName = url;// + "?" + param;
	    URL realUrl;
	    try {
	        realUrl = new URL(urlName);
	        try {
	            //打开和URL之间的连接
	            URLConnection conn;
	            conn = realUrl.openConnection();
	            /*conn.setConnectTimeout(30000);  
	            conn.setReadTimeout(30000);  */ 
	            //设置通用的请求属性
	            //设置通用的请求属性
		        conn.setRequestProperty("accept", "*/*");
		        conn.setRequestProperty("connection", "Keep-Alive");
		       // conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
	            
	            
	           if(sessionIdStr != null && sessionIdStr.length() > 0){
		        	conn.setRequestProperty("Cookie",sessionIdStr.toString());
		       }
	
		        try{
		           in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));  
		        } catch(Exception e) {
		        	   throw new PmsException(e.getMessage());
		           }
			
			   String line;
		       while ((line = in.readLine()) != null) {
		            result += "\n" + line;
		       }
		       
		       String session_value=conn.getHeaderField("Set-Cookie");
		       if(session_value != null && !session_value.isEmpty()) {
			        String[] sessionId = session_value.split(";");
			        if(sessionId.length > 0) {
						//保存session信息
				        sessionIdStr.setLength(0);  
				        sessionIdStr.append(sessionId[0]);
			        }
		       }
	        } catch(IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    } catch(MalformedURLException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return result;
	}
	public static String sendPost(String url, String param, StringBuffer sessionIdStr)  throws PmsException {
	    PrintWriter out = null;
	    BufferedReader in =null;
	    String result = "";
	    try {
	        URL realUrl = new URL(url);
	        //打开和URL之间的连接
	        URLConnection conn = realUrl.openConnection();
	       /* conn.setConnectTimeout(30000);  
            conn.setReadTimeout(30000);*/  
	        if(sessionIdStr != null && sessionIdStr.length() > 0){
	        	conn.setRequestProperty("Cookie",sessionIdStr.toString());
	        }    
	        //设置通用的请求属性
	        conn.setRequestProperty("accept", "*/*");
	        conn.setRequestProperty("connection", "Keep-Alive");
	      //  conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
	      
	        //发送POST请求必须设置如下两行
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        //获取URLConnection对象对应的输出流
	       // out = new PrintWriter(conn.getOutputStream());
	        
	        out =new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));
	        
	       // System.out.println(" 测试编码： " + URLEncoder.encode(param, "GBK"));
	        //发送请求参数
	        out.print(param);
	        
	 /*       Object args;
			out.format(param, args);*/
	        //flush输出流的缓冲
	        out.flush();
		   try{
		        //定义BufferedReader输入流来读取URL的响应
		        in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));  
		    } catch(Exception e) {
	     	   throw new PmsException(e.getMessage());
	        }
	        //  in = reader;
	        String line;
	        while ((line = in.readLine()) != null) {
	            result += "\n" + line;
	        }
	        
	       String session_value=conn.getHeaderField("Set-Cookie");
	       if(session_value != null && !session_value.isEmpty()) {
		        String[] sessionId = session_value.split(";");
		        if(sessionId.length > 0) {
					//保存session信息
			        sessionIdStr.setLength(0);  
			        sessionIdStr.append(sessionId[0]);
		        }
	       }
	        
	    } catch(Exception e) {
	        System.out.println("发送POST请求出现异常！" + e);
	        e.printStackTrace();
	    }
	    //使用finally块来关闭输出流、输入流
	    finally {
	        try {
	            if (out != null) {
	                out.close();
	            }
	            if ( in !=null) { in .close();
	            }
	        } catch(IOException ex) {
	            ex.printStackTrace();
	        }
	    }
	    return result;
	}
		
}
