/*******************************************************************************
 * Copyright (c) 2010 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package controller.system;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;

import com.onlyfido.util.SessionManager;

import domain.system.PensionSysUser;

import service.system.MainService;
import service.system.PensionMenus;
import util.JavaConfig;

import java.io.InputStream;  
import javax.servlet.ServletContext;  
  
import org.primefaces.model.DefaultStreamedContent;  
/**
 * Created by JBoss Tools
 */
@ViewScoped
public class MainController implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String testStr;
	//private  List<UserInfo> allUser;
	private transient MainService mainService;
	
	//List<List<PensionMenu>> pensionMenusList;
	List<PensionMenus> pensionMenusList;
	
	private String passWord;

	//设置最多显示的页面
	private String maxCount = "99999999";
	
	private StreamedContent helpFile;  
	
	@PostConstruct
	public void  init() {
		//allUser = userManageService.findAllUser();
		System.out.println("加载成功");
		testStr = mainService.testFun();
		pensionMenusList = mainService.selectMenus();
		//String sss = SessionManager.fectchLock();
		//System.out.println(sss);
	}

	public void initFileStream()
	{
        InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/doc/养老信息管理系统说明书.doc");  
        try {
			String fileName = java.net.URLEncoder.encode("养老信息管理系统说明书.doc", "UTF-8");
			helpFile = new DefaultStreamedContent(stream, "*", fileName,"UTF-8"); 
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public String getTestStr() {
		return testStr;
	}

	public void setAutoClose()
	{
		if(maxCount == "99999999")
		{
			maxCount = JavaConfig.fetchProperty("MainController.maxCount");
		}
		else
		{
			maxCount = "99999999";
		}
	}
	
	public void leaveSystem() {
		final HttpSession session = (HttpSession) FacesContext
		.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
	}

	//上锁
	public void lock() {
		SessionManager.setSessionAttribute(SessionManager.LOCK, true);
	}
	
	//解锁
	public void unLock() {
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(passWord == null || passWord.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"密码为空" , ""));
			context.addCallbackParam("login", "fail");
		} else {
			
			List<PensionSysUser> pensionSysUsers = mainService.unlock(passWord);
			if(pensionSysUsers != null && pensionSysUsers.size() > 0 ) {
				context.addCallbackParam("login", "success");
				SessionManager.setSessionAttribute(SessionManager.LOCK, "false");
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"密码错误" , ""));
				context.addCallbackParam("login", "fail");
			}
		}
		
	//	System.out.println(passWord);
	}
	public void setTestStr(String testStr) {
		this.testStr = testStr;
	}


	public MainService getMainService() {
		return mainService;
	}


	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	public List<PensionMenus> getPensionMenusList() {
		return pensionMenusList;
	}

	public void setPensionMenusList(List<PensionMenus> pensionMenusList) {
		this.pensionMenusList = pensionMenusList;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setMaxCount(String maxCount) {
		this.maxCount = maxCount;
	}

	public String getMaxCount() {
		return maxCount;
	}

	public void setHelpFile(StreamedContent helpFile) {
		this.helpFile = helpFile;
	}

	public StreamedContent getHelpFile() {
		return helpFile;
	}


}