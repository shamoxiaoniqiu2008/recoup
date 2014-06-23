package service.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.onlyfido.util.SessionManager;

import domain.system.PensionSysUser;

import util.JSONHelper;
import util.JavaConfig;
import util.PmsException;
import util.URLConn;



@Service
public class TimerService {
	
	/**
	 * 登录，成功返回true 
	 * @param sessionIdStr
	 * @param timerSessionId
	 * @return
	 * @throws PmsException
	 */
	public boolean timerLogin(StringBuffer sessionIdStr, String timerSessionId) throws PmsException {
		String longinUrl = JavaConfig.fetchProperty("timerWebSevice.loginInUrl");
		PensionSysUser sysUser = (PensionSysUser) SessionManager.getCurUser();
		String longparam  = "[ {\"userName\": \""+sysUser.getUsername()+"\",\"passWord\": \""+sysUser.getPassword()+"\" }]";
		 // sessionIdStr = new StringBuffer("");
		String str  = URLConn.sendPost(longinUrl, longparam, sessionIdStr);
		if(str == null || str.isEmpty()) {
			 throw new PmsException("远端出错");
		}
		String backStr = sessionIdStr.toString();
		if(!backStr.equals(timerSessionId)){
			SessionManager.setSessionAttribute(SessionManager.TIMER_WEB_SERVICE, backStr);
		}
		List<Map<String, Object>> jsonList = JSONHelper.jsonObjList(str);
		Integer status = (Integer) jsonList.get(0).get("status");
		if(!(status != null && status.equals(1))) {
			return false;
		} 
		return true;
	}
	
	/**
	 * 获取所有定时任务
	 * @return
	 * @throws PmsException
	 */
	public List<TimerProperties> fetchTimerType() throws PmsException {
		String url = JavaConfig.fetchProperty("timerWebSevice.timerTypeUrl"); 
		String param  = "{}";
		Object sessionValObj = SessionManager.getSessionAttribute(SessionManager.TIMER_WEB_SERVICE);
		String timerSessionId = "";
		if(sessionValObj != null) {
			timerSessionId = (String) sessionValObj;
		}
		
		StringBuffer sessionIdStr = new StringBuffer(timerSessionId);
		String str = URLConn.sendGet(url, param, sessionIdStr);
		
		if(str == null || str.isEmpty()) {
			 throw new PmsException("远端出错");
		}
		
		String backStr = sessionIdStr.toString();
		if(!backStr.equals(timerSessionId)){
			SessionManager.setSessionAttribute(SessionManager.TIMER_WEB_SERVICE, backStr);
		}
		List<TimerProperties> timerPropertiesList = null;
		
		
		List<Map<String, Object>> jsonList = JSONHelper.jsonObjList(str);
		if (jsonList.size() > 0) {
			Integer  status = (Integer) jsonList.get(0).get("status");
			String message = (String) jsonList.get(0).get("message");
			//需要重新登录
			if(message != null && message.equals("login")) {
				boolean loginStatus = timerLogin(sessionIdStr, timerSessionId);				
				if(!loginStatus) {
					throw new PmsException("无权访问");
				} else {
					str = URLConn.sendGet(url, param, sessionIdStr);
					jsonList = JSONHelper.jsonObjList(str);
				}
				
			}
			timerPropertiesList = new ArrayList<TimerProperties>();
			if (jsonList.size() > 0) {
				 status = (Integer) jsonList.get(0).get("status");
				 if(status != null && status.equals(1)) {
					Object st = jsonList.get(0).get("result");
					//System.out.println(st);
					HashMap<String, HashMap<String,String>> propertyHtable= (HashMap<String,  HashMap<String,String>>) st;
					for(Iterator itr = propertyHtable.keySet().iterator(); itr.hasNext();){ 
						TimerProperties timerProperties = new TimerProperties();
			    		String key = (String) itr.next(); 
			    		HashMap<String,String> mapTimerProperties = (HashMap<String,String>) propertyHtable.get(key); 
			    		timerProperties.setKey(key);
			    		timerProperties.setShowName(mapTimerProperties.get("showName"));
			    		timerProperties.setOpen(mapTimerProperties.get("open"));
			    		timerPropertiesList.add(timerProperties);
			    	}

					System.out.println(timerPropertiesList);
				 } else {
					 throw new PmsException("当前无定时任务");
				 }
			}
			
		} else {
			throw new PmsException("访问失败");
		}
		
		return timerPropertiesList;
	}
	
	public void putUpTimerType(String key, String val) throws PmsException {
		String url = JavaConfig.fetchProperty("timerWebSevice.timerTypeUrl"); 
		String param  = "[{\"key\": \"" + key + "\",\"val\": \"" + val + "\" }]";
		
		Object sessionValObj = SessionManager.getSessionAttribute(SessionManager.TIMER_WEB_SERVICE);
		String timerSessionId = "";
		if(sessionValObj != null) {
			timerSessionId = (String) sessionValObj;
		}
		StringBuffer sessionIdStr = new StringBuffer(timerSessionId);
		String str = URLConn.sendPost(url, param, sessionIdStr);

		if(str == null || str.isEmpty()) {
			 throw new PmsException("远端出错");
		}
		
		String backStr = sessionIdStr.toString();
		if(!backStr.equals(timerSessionId)){
			SessionManager.setSessionAttribute(SessionManager.TIMER_WEB_SERVICE, backStr);
		}
		
		List<Map<String, Object>> jsonList = JSONHelper.jsonObjList(str);
		if (jsonList.size() > 0) {
			Integer  status = (Integer) jsonList.get(0).get("status");
			String message = (String) jsonList.get(0).get("message");
			//需要重新登录
			if(message != null && message.equals("login")) {
				boolean loginStatus = timerLogin(sessionIdStr, timerSessionId);				
				if(!loginStatus) {
					throw new PmsException("无权访问");
				} else {
					str = URLConn.sendPost(url, param, sessionIdStr);
					jsonList = JSONHelper.jsonObjList(str);
				}
			}
			if (jsonList.size() > 0) {
				 status = (Integer) jsonList.get(0).get("status");
				 if(status != null && status.equals(1)) {
					System.out.println("设置成功");
				 } else {
					 throw new PmsException("设置失败");
				 }
			}
			
		} else {
			throw new PmsException("访问失败");
		}
		
		
	}
}
