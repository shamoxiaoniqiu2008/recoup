/**
 * 本文件应该是我们所有的logger的入口，方便自定义内容加入。
 * Author：Kevin Li
 */
package com.onlyfido.util.logger;

import org.apache.log4j.Logger;

import com.onlyfido.util.ExceptionUtil;

public class HisLogger<T> {

	private Logger logger;

	private HisLogger(Class<T> classType) {
		logger = Logger.getLogger(classType);
	}

	@SuppressWarnings("unchecked")
	// I know what I am doing
	public static HisLogger<?> getLogger(Class<?> classType) {
		HisLogger<?> hisLogger = new HisLogger(classType);
		return hisLogger;
	}

	public void debug(Object message) {
		try {

			logger.debug(addUserInfo(message));
		} catch (Exception ex) {
			logger.debug(ExceptionUtil.exceptionStackTrace(ex));
		}
	}

	/**
	 * @param message
	 * @return
	 */
	private String addUserInfo(Object message) {
		// modified by Damon 2011-11-4 19:25:47 at julien's client
		/*if (SessionManager.getCurStaff() != null)
			return "USER:" + SessionManager.getCurStaff().getName() + ":"
					+ message;
		else*/
			return message.toString();
	}

	public void info(Object message) {
		try {
			logger.info(addUserInfo(message));
		} catch (Exception ex) {
			logger.info(ExceptionUtil.exceptionStackTrace(ex));
		}
	}

	public void warn(Object message) {
		try {
			logger.warn(addUserInfo(message));
		} catch (Exception ex) {
			logger.warn(ExceptionUtil.exceptionStackTrace(ex));
		}
	}

	public void error(Object message) {
		try {
			logger.error(addUserInfo(message));
		} catch (Exception ex) {
			logger.error(ExceptionUtil.exceptionStackTrace(ex));
		}
	}
	
	public void warn(Object message, Throwable t){
		logger.warn(message, t);
	}
}