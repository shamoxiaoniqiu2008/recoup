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
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.onlyfido.util.SessionManager;

import domain.system.PensionSysUser;

import service.system.UserPasswordService;
import util.PmsException;


/**
 * Created by JBoss Tools
 */
@ViewScoped
public class UserPasswordController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private transient UserPasswordService userPasswordService;
	
	private PensionSysUser pensionSysUser;

	private String userPassworda;
	private String userPasswordR;	
	
	

	@PostConstruct
	public void  init() {
		//allUser = userManageService.findAllUser();
		System.out.println("加载成功");
		pensionSysUser = SessionManager.getCurUser();
		//testStr ="8888888888888888888888888888888888888888888888888888888888888888888888";
	}


	public void saveSysUser() {
		if(userPassworda == null || userPasswordR == null || userPassworda.isEmpty() || userPasswordR.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"密码不能为空" , ""));
			return ;
		}
		if(!userPassworda.equals(userPasswordR)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"两次输入密码不一致" , ""));
			return ;
		}
		try {
			userPasswordService.updateSysUser(pensionSysUser.getId(), userPassworda);
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"修改成功" , ""));
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getLocalizedMessage() , ""));
			e.printStackTrace();
		}
		//System.out.println("修改密码");
	}

	public UserPasswordService getUserPasswordService() {
		return userPasswordService;
	}



	public void setUserPasswordService(UserPasswordService userPasswordService) {
		this.userPasswordService = userPasswordService;
	}



	public PensionSysUser getPensionSysUser() {
		return pensionSysUser;
	}



	public void setPensionSysUser(PensionSysUser pensionSysUser) {
		this.pensionSysUser = pensionSysUser;
	}



	public String getUserPassworda() {
		return userPassworda;
	}


	public void setUserPassworda(String userPassworda) {
		this.userPassworda = userPassworda;
	}


	public String getUserPasswordR() {
		return userPasswordR;
	}



	public void setUserPasswordR(String userPasswordR) {
		this.userPasswordR = userPasswordR;
	}




}