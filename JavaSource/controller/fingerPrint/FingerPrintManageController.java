package controller.fingerPrint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.fingerPrint.FingerPrintDomain;
import service.fingerPrint.FingerPrintManageService;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-10-30 上午08:16:44
 */

public class FingerPrintManageController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient FingerPrintManageService fingerPrintManageService;

	/**
	 * 房间ID(查询)
	 */
	private Long roomId;
	/**
	 * 选中的指纹机
	 */
	private FingerPrintDomain selectedRow;
	/**
	 * 指纹机列表
	 */
	private List<FingerPrintDomain> fingerPrintList = new ArrayList<FingerPrintDomain>();

	/**
	 * 编辑指纹机对象
	 */
	private FingerPrintDomain editFingerPrint = new FingerPrintDomain();

	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void init() {
		searchFingerPrints();
	}

	/**
	 * 查询指纹机设备列表
	 */
	public void searchFingerPrints() {
		fingerPrintList = fingerPrintManageService.selectFingerPrints(roomId);
		selectedRow = null;
	}

	/**
	 * 点击修改按钮触发
	 */
	public void showEditForm() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
			return;
		}
		editFingerPrint = selectedRow;
		requestContext.addCallbackParam("edit", true);
	}

	/**
	 * 修改一条指纹机记录
	 */
	public void updateFingerPrint() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		fingerPrintManageService.updateFingerPrint(editFingerPrint);
		fingerPrintList = fingerPrintManageService.selectFingerPrints(roomId);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"修改成功", ""));
		requestContext.addCallbackParam("editSuccess", true);
	}

	/**
	 * 选中一行时触发
	 * 
	 * @return
	 */
	public void setEnableFlag(SelectEvent e) {

	}

	/**
	 * 未选中一行时触发
	 * 
	 * @return
	 */
	public void setUnableFlag(UnselectEvent e) {

	}

	public FingerPrintDomain getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(FingerPrintDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public void setFingerPrintManageService(
			FingerPrintManageService fingerPrintManageService) {
		this.fingerPrintManageService = fingerPrintManageService;
	}

	public FingerPrintManageService getFingerPrintManageService() {
		return fingerPrintManageService;
	}

	public void setFingerPrintList(List<FingerPrintDomain> fingerPrintList) {
		this.fingerPrintList = fingerPrintList;
	}

	public List<FingerPrintDomain> getFingerPrintList() {
		return fingerPrintList;
	}

	public void setEditFingerPrint(FingerPrintDomain editFingerPrint) {
		this.editFingerPrint = editFingerPrint;
	}

	public FingerPrintDomain getEditFingerPrint() {
		return editFingerPrint;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getRoomId() {
		return roomId;
	}

}
