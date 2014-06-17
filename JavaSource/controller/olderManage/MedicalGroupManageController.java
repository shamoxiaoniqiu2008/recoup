package controller.olderManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.dao.DataAccessException;

import service.olderManage.MedicalGroupManageService;
import service.olderManage.PensionHospitalizegroupExtend;

public class MedicalGroupManageController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionHospitalizegroupExtend> records = new ArrayList<PensionHospitalizegroupExtend>();
	/**
	 * 被选中的记录
	 */
	private PensionHospitalizegroupExtend[] selectedRows;
	/**
	 * 被录入的记录
	 */
	private PensionHospitalizegroupExtend insertedRow = new PensionHospitalizegroupExtend();
	/**
	 * 被修改的记录
	 */
	private PensionHospitalizegroupExtend updatedRow = new PensionHospitalizegroupExtend();
	/**
	 * 起始时间 用作查询条件
	 */
	private Date startDate;
	/**
	 * 截止时间 用作查询条件
	 */
	private Date endDate;
	/**
	 * 负责人主键用于查询条件
	 */
	private Long managerId;
	/**
	 * 分组Id
	 */
	private Long groupId;
	/**
	 * 绑定所有负责人姓名的输入法
	 */
	private String managerNameSql;
	/**
	 * 帮顶所有的员工姓名的输入法
	 */
	private String employeeNameSql;
	/**
	 * 修改按钮是否可用
	 */
	private boolean disUpdateButton = true;
	/**
	 * 删除和分组按钮是否可用
	 */
	private boolean disDeleteButton = true;
	/**
	 * 注入业务
	 */
	private transient MedicalGroupManageService medicalGroupManageService;

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
		selectGroupRecords();
	}

	/**
	 * 初始化sql语句
	 */
	public void initSql() {
		employeeNameSql = "select pe.name as managerName,"
				+ "pe.inputCode as inputCode, " + "pd.name	as deptName,"
				+ "pe.id as managerId " + "from pension_employee pe "
				+ "join pension_dept pd " + "on pe.dept_id = pd.id "
				+ "where pe.cleared = 2";
		managerNameSql = "select distinct pe.name as managerName,"
				+ "pe.inputCode as inputCode, " + "pd.name	as deptName,"
				+ "pe.id as managerId " + "from pension_hospitalizegroup phg "
				+ "join pension_employee pe " + "on phg.manager_id = pe.id "
				+ "join pension_dept pd " + "on pe.dept_id = pd.id "
				+ "where phg.cleared = 2 " + "and pe.cleared = 2 ";
	}

	/**
	 * 查询分组记录
	 */
	public void selectGroupRecords() {
		disDeleteButton = true;
		disUpdateButton = true;
		selectedRows = null;
		records = medicalGroupManageService.selectGroupRecords(startDate,
				endDate, managerId, null);

	}

	/**
	 * 删除分组记录
	 */
	public void deleteGroupRecord() {

		FacesContext context = FacesContext.getCurrentInstance();
		for (PensionHospitalizegroupExtend selectedRow : selectedRows) {

			medicalGroupManageService.deleteGroupRecord(selectedRow);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"删除成功！", "删除成功！");
			context.addMessage(null, message);

		}
		disDeleteButton = true;
		selectGroupRecords();
	}

	/**
	 * 录入分组记录
	 */
	public void insertGroupRecord() {

		FacesContext context = FacesContext.getCurrentInstance();
		String info = "添加成功";
		try {
			medicalGroupManageService.insertGroupRecord(insertedRow);
			selectGroupRecords();
		} catch (DataAccessException e) {

			info = "添加操作写入数据库出现异常！";

		} catch (Exception e) {

			info = "出现未知异常，请联系系统管理员！";

		}
		clearInsertForm();
		if (info.equals("添加成功")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					info, info);
			context.addMessage(null, message);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					info, info);
			context.addMessage(null, message);
		}

	}

	/**
	 * 修改分组记录
	 */
	public void updateGroupRecord() {
		FacesContext context = FacesContext.getCurrentInstance();
		String info = "修改成功";
		medicalGroupManageService.updateGroupRecord(updatedRow);
		selectGroupRecords();
		if (info.equals("修改成功")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					info, info);
			context.addMessage(null, message);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					info, info);
			context.addMessage(null, message);
		}

	}

	/**
	 * 
	 * 清空insertFrom
	 */
	public void clearInsertForm() {
		insertedRow = new PensionHospitalizegroupExtend();
	}

	public void clearSelectForm() {
		startDate = null;
		endDate = null;
		managerId = null;
		selectGroupRecords();
	}

	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {
		setDisDeleteButton(false);
		if (selectedRows.length == 1) {
			setDisUpdateButton(false);
		} else {
			setDisUpdateButton(true);
		}
	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
		if (selectedRows.length == 1) {
			setDisUpdateButton(false);
		}
		if (selectedRows.length == 0) {
			setDisUpdateButton(true);
			setDisDeleteButton(true);
		}
	}

	/**
	 * 讲选中的值赋值给要更新的行
	 */
	public void copyRecordUpdatedRow() {
		// CloneUtil.deepCopy();
		// 浅拷贝
		updatedRow = selectedRows[0];
	}

	public void setRecords(List<PensionHospitalizegroupExtend> records) {
		this.records = records;
	}

	public List<PensionHospitalizegroupExtend> getRecords() {
		return records;
	}

	public void setUpdatedRow(PensionHospitalizegroupExtend updatedRow) {
		this.updatedRow = updatedRow;
	}

	public PensionHospitalizegroupExtend getUpdatedRow() {
		return updatedRow;
	}

	public void setSelectedRows(PensionHospitalizegroupExtend[] selectedRows) {
		this.selectedRows = selectedRows;
	}

	public PensionHospitalizegroupExtend[] getSelectedRows() {
		return selectedRows;
	}

	public void setInsertedRow(PensionHospitalizegroupExtend insertedRow) {
		this.insertedRow = insertedRow;
	}

	public PensionHospitalizegroupExtend getInsertedRow() {
		return insertedRow;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setManagerNameSql(String managerNameSql) {
		this.managerNameSql = managerNameSql;
	}

	public String getManagerNameSql() {
		return managerNameSql;
	}

	public void setDisUpdateButton(boolean disUpdateButton) {
		this.disUpdateButton = disUpdateButton;
	}

	public boolean isDisUpdateButton() {
		return disUpdateButton;
	}

	public void setDisDeleteButton(boolean disDeleteButton) {
		this.disDeleteButton = disDeleteButton;
	}

	public boolean isDisDeleteButton() {
		return disDeleteButton;
	}

	public void setEmployeeNameSql(String employeeNameSql) {
		this.employeeNameSql = employeeNameSql;
	}

	public String getEmployeeNameSql() {
		return employeeNameSql;
	}

	public void setMedicalGroupManageService(
			MedicalGroupManageService medicalGroupManageService) {
		this.medicalGroupManageService = medicalGroupManageService;
	}

	public MedicalGroupManageService getMedicalGroupManageService() {
		return medicalGroupManageService;
	}
}
