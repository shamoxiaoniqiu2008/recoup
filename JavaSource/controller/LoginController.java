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
package controller;


import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.onlyfido.util.SessionManager;

import service.LoginService;

import util.JavaConfig;
import util.Spell;
import domain.employeeManage.PensionEmployee;
import domain.system.PensionSysUser;


public class LoginController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String passWord;
	
	
	private transient LoginService loginService;
	
	private String testStr;
	//private  List<UserInfo> allUser;
	
	

	@PostConstruct
	public void  init() {
		//allUser = userManageService.findAllUser();
		System.out.println("加载成功");
		String asd = JavaConfig.fetchProperty("login.sucess");
		System.out.println(asd);
		String hzString = "音乐";  
		System.out.println(Spell.getFirstSpell(hzString));
		testStr ="8888888888888888888888888888888888888888888888888888888888888888888888";
	}

	public void fetchUser() {
		RequestContext context = RequestContext.getCurrentInstance();
		if(userName == null || userName.isEmpty() || passWord == null || passWord.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"用户或密码不能为空" , ""));
			return;
		}
		
		List<PensionSysUser> pensionSysUsers = loginService.userLogin(userName, passWord);
		if(pensionSysUsers != null && pensionSysUsers.size() > 0 ) {
			System.out.println("登录成功");
			//FacesContext facesContext = FacesContext.getCurrentInstance();
			//HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			
			/*HttpServletResponse response = ;*/
			//	response.sendRedirect("user.jsf");
			
			PensionEmployee pensionEmployee = loginService.selectPensionEmployee(pensionSysUsers.get(0).getEmployeeId());
			if(pensionEmployee != null && pensionEmployee.getId() != null) {
				SessionManager.setSessionAttribute(SessionManager.USER, pensionSysUsers.get(0)) ;
				SessionManager.setSessionAttribute(SessionManager.USER_NAME, pensionSysUsers.get(0).getLoginname()) ;
				SessionManager.setSessionAttribute(SessionManager.EMPLOYEE, pensionEmployee);
				SessionManager.setSessionAttribute(SessionManager.LOCK, "false");
				context.addCallbackParam("login", "success");
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"请将用户与员工绑定后再登录" , ""));
				 //"fail";
				context.addCallbackParam("login", "fail");
			}
			
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"用户名或密码错误" , ""));
			 //"fail";
			context.addCallbackParam("login", "fail");
		}
	}
	

	
	


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public String sayHello() {
		return "greeting";
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setTestStr(String testStr) {
		this.testStr = testStr;
	}

	public String getTestStr() {
		return testStr;
	}

}