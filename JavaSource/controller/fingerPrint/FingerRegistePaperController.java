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

import service.fingerPrint.FingerRegDomain;
import service.fingerPrint.FingerRegisterPaperService;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-10-30 上午08:16:44
 */

public class FingerRegistePaperController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient FingerRegisterPaperService fingerRegisterPaperService;

	/**
	 * 员工/老人ID(查询)
	 */
	private Long peopleId;
	/**
	 * 员工/老人姓名（查询）
	 */
	private String peopleName;
	/**
	 * 人员类型 1为员工，2为老人
	 */
	private Integer type;
	/**
	 * 选中的指纹登记记录
	 */
	private FingerRegDomain selectedRow;
	/**
	 * 指纹登记列表
	 */
	private List<FingerRegDomain> fingerRegList = new ArrayList<FingerRegDomain>();

	/**
	 * 新增指纹登记对象
	 */
	private FingerRegDomain addFingerReg = new FingerRegDomain();
	/**
	 * 用来控制输入框中的老人和员工label和input 是否显示 默认为显示员工   added by tim 2013-12-11
	 */
	private boolean insertedEmployeeAndOlderRenderedFlag = true;
	
	/**
	 * 用来控制查询框中的老人和员工label和input 是否显示 默认为显示员工   added by tim 2013-12-11
	 */
	private boolean selectedEmployeeAndOlderRenderedFlag = true;
	
	

	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void init() {
		type = 1;
		searchFingerRegRecords();
	}

	/**
	 * 查询指纹机登记列表
	 */
	public void searchFingerRegRecords() {
		fingerRegList = fingerRegisterPaperService
				.selectFingerRegRecords(peopleId,type);
		selectedRow = null;
	}

	/**
	 * 初始化新增对话框
	 */
	public void showAddForm() {
		addFingerReg = new FingerRegDomain();
		addFingerReg.setType(1);
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
		addFingerReg = selectedRow;
		requestContext.addCallbackParam("edit", true);
	}

	/**
	 * 保存指纹登记信息
	 */
	public void saveFingerReg() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		fingerRegisterPaperService.insertFingerReg(addFingerReg);
		searchFingerRegRecords();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "新增成功", ""));
		requestContext.addCallbackParam("addSuccess", true);
	}

	/**
	 * 修改一条指纹登记信息
	 */
	public void updateFingerReg() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		fingerRegisterPaperService.updateFingerReg(addFingerReg);
		fingerRegList = fingerRegisterPaperService
				.selectFingerRegRecords(peopleId,type);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "修改成功", ""));
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

	/**
	 * 删除指纹登记记录
	 */
	public void delFingerReg() {
		fingerRegisterPaperService.deleteFingerReg(selectedRow);
		searchFingerRegRecords();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "删除成功", ""));
	}
	
	/**
	 * 点击前台的维修类型 根据具体类型 来改变新增dialog的布局
	 */
	public void changeInsertDialog(){
		
		Integer type = addFingerReg.getType();
		//员工
		if(type!=null&&type==1){
			insertedEmployeeAndOlderRenderedFlag = true;
			addFingerReg.setType(1);
			addFingerReg.setPeopleId(null);
			addFingerReg.setEmployeeName(null);
		}else{
			insertedEmployeeAndOlderRenderedFlag = false;
			addFingerReg.setType(2);
			addFingerReg.setPeopleId(null);
			addFingerReg.setEmployeeName(null);
		}
		
	}
	/**
	 * 点击前台的维修类型 根据具体类型 来改变查询的布局
	 */
	public void changeSelectDialog(){
	
		//员工
		if(type!=null&&type==1){
			selectedEmployeeAndOlderRenderedFlag = true;
			peopleId = null;
			peopleName= null;
		}else{
			selectedEmployeeAndOlderRenderedFlag  = false;
			peopleId = null;
			peopleName = null;
		}
		
	}

	public FingerRegDomain getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(FingerRegDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public void setFingerRegisterPaperService(
			FingerRegisterPaperService fingerRegisterPaperService) {
		this.fingerRegisterPaperService = fingerRegisterPaperService;
	}

	public FingerRegisterPaperService getFingerRegisterPaperService() {
		return fingerRegisterPaperService;
	}

	public void setFingerRegList(List<FingerRegDomain> fingerRegList) {
		this.fingerRegList = fingerRegList;
	}

	public List<FingerRegDomain> getFingerRegList() {
		return fingerRegList;
	}

	public void setAddFingerReg(FingerRegDomain addFingerReg) {
		this.addFingerReg = addFingerReg;
	}

	public FingerRegDomain getAddFingerReg() {
		return addFingerReg;
	}

	public void setInsertedEmployeeAndOlderRenderedFlag(
			boolean insertedEmployeeAndOlderRenderedFlag) {
		this.insertedEmployeeAndOlderRenderedFlag = insertedEmployeeAndOlderRenderedFlag;
	}

	public boolean isInsertedEmployeeAndOlderRenderedFlag() {
		return insertedEmployeeAndOlderRenderedFlag;
	}

	public void setPeopleId(Long peopleId) {
		this.peopleId = peopleId;
	}

	public Long getPeopleId() {
		return peopleId;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setSelectedEmployeeAndOlderRenderedFlag(
			boolean selectedEmployeeAndOlderRenderedFlag) {
		this.selectedEmployeeAndOlderRenderedFlag = selectedEmployeeAndOlderRenderedFlag;
	}

	public boolean isSelectedEmployeeAndOlderRenderedFlag() {
		return selectedEmployeeAndOlderRenderedFlag;
	}

	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
	}

	public String getPeopleName() {
		return peopleName;
	}

}
