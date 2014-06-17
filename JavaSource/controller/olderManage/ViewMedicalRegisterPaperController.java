package controller.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.olderManage.PensionHospitalizeregisterExtend;
import service.olderManage.ViewMedicalRegisterPaperService;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;

public class ViewMedicalRegisterPaperController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionHospitalizeregisterExtend> records = new ArrayList<PensionHospitalizeregisterExtend>();
	/**
	 * 被选中的记录
	 */
	private PensionHospitalizeregisterExtend[] selectedRows;
	/**
	 * 起始时间 用作查询条件
	 */
	private Date startDate;
	/**
	 * 截止时间 用作查询条件
	 */
	private Date endDate;
	/**
	 * 分组Id
	 */
	private Long groupId;
	/**
	 * 老人Id
	 */
	private Long olderId;
	/**
	 * 登记表Id
	 */
	private Long registerId;
	
	/**
	 * 注入业务方法
	 */
	private transient ViewMedicalRegisterPaperService viewMedicalRegisterPaperService;

	/**
	 * 获取当前用户
	 */
	private PensionEmployee curEmployee = (PensionEmployee)SessionManager.getSessionAttribute(SessionManager.EMPLOYEE);
	/**
	 * @Description:初始化数据方法.
	 * @return: void
	 * @exception:
	 * @throws:
	 * @version: 1.0
	 * @author: Tim li
	 */
	@PostConstruct
	public void init() {
		initSql();
		//获取消息源发给本页面的参数
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
		.getExternalContext().getRequestParameterMap();
		String registerId = paramsMap.get("registerId");
		if(registerId != null) {
			this.registerId = Long.valueOf(registerId);
		} else {
			this.registerId = null;
		}
		//根据参数 和其余默认的查询条件查询出所有的请假申请
		selectOlderMedicalRegisterRecords();
		//之后 将其至空
		this.registerId = null;
		
		
	}
	/**
	 * 初始化sql语句
	 */
	public void initSql() {
	
	}
	public void clearSelectForm(){
		startDate = null;
		endDate = null;
		olderId = null;
		groupId = null;
	}

	/**
	 * 查询老人就医登记记录
	 */
	public void selectOlderMedicalRegisterRecords(){
		records = viewMedicalRegisterPaperService.selectOlderMedicalRegisterRecords(startDate,endDate,olderId,groupId,registerId,curEmployee);
	}
	
	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {

	}
	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {

	}
	public void setRecords(List<PensionHospitalizeregisterExtend> records) {
		this.records = records;
	}
	public List<PensionHospitalizeregisterExtend> getRecords() {
		return records;
	}
	public void setSelectedRows(PensionHospitalizeregisterExtend[] selectedRows) {
		this.selectedRows = selectedRows;
	}
	public PensionHospitalizeregisterExtend[] getSelectedRows() {
		return selectedRows;
	}
	public void setCurEmployee(PensionEmployee curEmployee) {
		this.curEmployee = curEmployee;
	}
	public PensionEmployee getCurEmployee() {
		return curEmployee;
	}
	public void setRegisterId(Long registerId) {
		this.registerId = registerId;
	}
	public Long getRegisterId() {
		return registerId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getOlderId() {
		return olderId;
	}
	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}
	public ViewMedicalRegisterPaperService getViewMedicalRegisterPaperService() {
		return viewMedicalRegisterPaperService;
	}
	public void setViewMedicalRegisterPaperService(
			ViewMedicalRegisterPaperService viewMedicalRegisterPaperService) {
		this.viewMedicalRegisterPaperService = viewMedicalRegisterPaperService;
	}
}
