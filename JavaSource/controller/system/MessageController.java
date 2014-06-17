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
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;

import service.system.MessageDoman;
import service.system.MessageMessage;



/**
 * Created by JBoss Tools
 */
@ViewScoped
public class MessageController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private transient MessageMessage messageMessage;
	
	List<MessageDoman> messageList = new ArrayList<MessageDoman>();
	
	private Integer type = 2;

	private Long messageTypeID = null;
	private String messageType = "";
	@PostConstruct
	public void  init() {
		selectByType();
	}

	public void clearSelectForm()
	{
		type = 2;
		messageTypeID = null;
		messageType = "";
	}
	public void selectByType()
	{
		PensionEmployee employee = (PensionEmployee) SessionManager.getSessionAttribute(SessionManager.EMPLOYEE);
		Long userId = employee.getId();
		Long dptId= employee.getDeptId(); 
		messageList = messageMessage.readMessages(dptId, userId, 2, type,messageTypeID);
	}
	public void setMessageMessage(MessageMessage messageMessage) {
		this.messageMessage = messageMessage;
	}


	public MessageMessage getMessageMessage() {
		return messageMessage;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setMessageTypeID(Long messageTypeID) {
		this.messageTypeID = messageTypeID;
	}

	public Long getMessageTypeID() {
		return messageTypeID;
	}

	public List<MessageDoman> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<MessageDoman> messageList) {
		this.messageList = messageList;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageType() {
		return messageType;
	}

}