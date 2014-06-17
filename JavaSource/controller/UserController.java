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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import service.UserManageService;
import domain.UserInfo;

/**
 * Created by JBoss Tools
 */
@ManagedBean(name="userController")
@ViewScoped
public class UserController implements Serializable{
	
	private String name;

	
	//@ManagedProperty(value="#{userManageService}")
	private UserManageService userManageService;
	
	private  List<UserInfo> allUser;
	
	

	@PostConstruct
	public void  init() {
		allUser = userManageService.findAllUser();
	}

	
	/**
	 * add user
	 * @throws Exception 
	 */
	public void addUser() throws Exception{
		
		UserInfo u = new UserInfo();
		u.setUserName(name);
		userManageService.addUser(u);
		
		//重新查询数据库
		allUser = userManageService.findAllUser();
		
		name = null;
		
		/*
		 * 故意在server端，让线程沉睡2s
		 */
		try {
	        Thread.sleep(2000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    
//		throw new Exception("出错了");
	}
	
	
	/**
	 * delete user
	 */
	public void deleteUser(UserInfo u){
		
		
		userManageService.deleteUser(u);
		
		//重新查询数据库
		allUser = userManageService.findAllUser();
	}
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String sayHello() {
		return "greeting";
	}

	public UserManageService getUserManageService() {
		return userManageService;
	}


	public void setUserManageService(UserManageService userManageService) {
		this.userManageService = userManageService;
	}


	public List<UserInfo> getAllUser() {
		return allUser;
	}


	public void setAllUser(List<UserInfo> allUser) {
		this.allUser = allUser;
	}

	
}