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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import domain.olderManage.PensionRing;

import service.system.RingManageService;

/**
 * Created by JBoss Tools
 */

public class RingManageController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient RingManageService ringManageService;

	private List<PensionRing> pensionRingList = new ArrayList<PensionRing>();
	private PensionRing pensionRing = new PensionRing();
	private PensionRing pensionRingNow = new PensionRing();

	/**
	 * 设置按钮的可用性
	 */
	private boolean disabledFlag;
	/**
	 * 操作标记 1 为新增 2 为修改
	 */
	private Short operationId;

	private String nurseName;
	private Long nurseID;
	private String nurseSQL;
	private String roomNumber;
	private Long roomID;
	private String roomSQL;

	@PostConstruct
	public void init() {
		initParameter();
		selectRingRecords();
	}

	public void initParameter() {
		disabledFlag = true;
		nurseName = null;
		nurseID = null;
		roomNumber = null;
		roomID = null;
		nurseSQL = "select e.id,e.name,e.inputCode from pension_employee e where e.cleared = 2";
		roomSQL = "select * from pension_room where cleared = 2";
	}

	public void clearSearchForm() {
		nurseName = null;
		nurseID = null;
		roomNumber = null;
		roomID = null;
	}

	public void selectRingRecords() {
		pensionRingList = ringManageService.selectRing(roomID, nurseID);
		pensionRingNow = null;
		disabledFlag = true;
	}

	/**
	 * 选中一条记录，设置按钮可用状态
	 */
	public void setEnableFlag(SelectEvent event) {
		// 修改、删除按钮状态
		disabledFlag = false;
	}

	/**
	 * 未选中一条记录，设置按钮可用状态
	 */
	public void setUnableFlag(UnselectEvent event) {
		disabledFlag = true;
	}

	/**
	 * 新增触发事件
	 */
	public void showAddForm() {
		operationId = 1;
		clearAddForm();
	}

	/**
	 * 修改触发事件
	 */
	public void showEditForm() {
		operationId = 2;
	}

	/**
	 * 清空新增对话框
	 */
	public void clearAddForm() {
		pensionRingNow = new PensionRing();
	}

	/**
	 * 逻辑删除呼叫器记录
	 */
	public void deleteRingRecord() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request = RequestContext.getCurrentInstance();
		pensionRingNow.setCleared(1);
		ringManageService.updateRing(pensionRingNow);

		request.addCallbackParam("sendMessage", true);
		facesContext.addMessage("", new FacesMessage(
				FacesMessage.SEVERITY_INFO, pensionRingNow.getHardaddress()
						+ "删除成功", ""));
		selectRingRecords();
	}

	/**
	 * 保存呼叫器
	 */
	public void insertRingRecord() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request = RequestContext.getCurrentInstance();

		if (operationId == (short) 1) {
			pensionRingNow.setCleared(2);
			ringManageService.addRing(pensionRingNow);
			request.addCallbackParam("sendMessage", true);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, pensionRingNow.getHardaddress()
							+ "新增成功", ""));
		} else {

			ringManageService.updateRing(pensionRingNow);

			request.addCallbackParam("sendMessage", true);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, pensionRingNow.getHardaddress()
							+ "更新成功", ""));

		}
		pensionRingList = ringManageService.selectRing(roomID, nurseID);

	}

	public void setRingManageService(RingManageService ringManageService) {
		this.ringManageService = ringManageService;
	}

	public RingManageService getRingManageService() {
		return ringManageService;
	}

	public void setPensionRing(PensionRing pensionRing) {
		this.pensionRing = pensionRing;
	}

	public PensionRing getPensionRing() {
		return pensionRing;
	}

	public void setPensionRingList(List<PensionRing> pensionRingList) {
		this.pensionRingList = pensionRingList;
	}

	public List<PensionRing> getPensionRingList() {
		return pensionRingList;
	}

	public void setPensionRingNow(PensionRing pensionRingNow) {
		this.pensionRingNow = pensionRingNow;
	}

	public PensionRing getPensionRingNow() {
		return pensionRingNow;
	}

	public void setDisabledFlag(boolean disabledFlag) {
		this.disabledFlag = disabledFlag;
	}

	public boolean isDisabledFlag() {
		return disabledFlag;
	}

	public void setOperationId(Short operationId) {
		this.operationId = operationId;
	}

	public Short getOperationId() {
		return operationId;
	}

	public void setNurseName(String nurseName) {
		this.nurseName = nurseName;
	}

	public String getNurseName() {
		return nurseName;
	}

	public void setNurseSQL(String nurseSQL) {
		this.nurseSQL = nurseSQL;
	}

	public String getNurseSQL() {
		return nurseSQL;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomSQL(String roomSQL) {
		this.roomSQL = roomSQL;
	}

	public String getRoomSQL() {
		return roomSQL;
	}

	public Long getNurseID() {
		return nurseID;
	}

	public void setNurseID(Long nurseID) {
		this.nurseID = nurseID;
	}

	public Long getRoomID() {
		return roomID;
	}

	public void setRoomID(Long roomID) {
		this.roomID = roomID;
	}

}