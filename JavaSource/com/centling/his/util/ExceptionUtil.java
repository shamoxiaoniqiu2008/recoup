/**
 * 
 */
package com.centling.his.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 处理异常时的
 * 
 * @author:Damon Shen
 * @version:1.0
 * @Date:2011-11-2 13:42:25
 */
public class ExceptionUtil {

	private static StringWriter stringWriter;
	private static PrintWriter writer;

	static{
		stringWriter = new StringWriter();
		writer = new PrintWriter(stringWriter);
	}
	// 通过异常返回该异常的堆栈信息
	public static String exceptionStackTrace(Throwable t)  {
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		try {
			stringWriter.close();
			writer.close();
		} catch (IOException e) {
			//todo 
		}
		return buffer.toString();
	}
}
