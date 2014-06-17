package controller.olderManage;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.dao.DataAccessException;

import com.centling.his.util.DateUtil;

import service.olderManage.EntryVacationApplicationService;
import service.olderManage.MedicalRegisterService;
import service.olderManage.PensionHospitalizegroupExtend;
import service.olderManage.PensionHospitalizeregisterExtend;
import service.olderManage.PensionLeaveExtend;
import util.PmsException;

public class MedicalRegisterController implements Serializable {
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
	 * 被录入的记录
	 */
	private PensionHospitalizeregisterExtend insertedRow = new PensionHospitalizeregisterExtend();
	/**
	 * 被修改的记录
	 */
	private PensionHospitalizeregisterExtend updatedRow = new PensionHospitalizeregisterExtend();
	/**
	 * 起始时间 用作查询条件
	 */
	private Date startDate;
	/**
	 * 截止时间 用作查询条件
	 */
	private Date endDate;

	private Date startGroupDate;
	private Date endGroupDate;

	/**
	 * 老人主键用于查询条件
	 */
	private Integer olderId;
	/**
	 * 分组Id
	 */
	private Long groupId;
	/**
	 * 分组负责人姓名
	 */
	private String managerName;

	/**
	 * 绑定分组输入法的sql
	 */
	private String groupSql;
	/**
	 * 绑定老人输入法sql
	 */
	private String olderNameSql;

	private Long grouped;
	private Long managerId;

	/**
	 * 注入业务
	 */
	private transient MedicalRegisterService medicalRegisterService;
	private transient EntryVacationApplicationService entryVacationApplicationService;

	private PensionHospitalizegroupExtend insertedGroupRow = new PensionHospitalizegroupExtend();

	private List<PensionHospitalizegroupExtend> grouRecords;
	private PensionHospitalizegroupExtend selectedGroup;
	private String managerNameSql;
	private boolean disDeleteButton;
	private short toDo = 1;// 1添加到新分组，2新建分组 3修改分组
	private List<PensionHospitalizeregisterExtend> oneGroupOlder;
	private String groupCollectionInfo;

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
		clearSelectForm();
		selectMedicalRegisterRecords();
		disDeleteButton = true;
	}

	/**
	 * 初始化sql语句
	 */
	public void initSql() {
		groupSql = "select distinct " + "pe.name        as managerName,"
				+ "phg.carNumber  as carNumber,"
				+ "DATE_FORMAT(phg.leaveTime, '%Y-%m-%d')  as leaveTime,"
				+ "DATE_FORMAT(phg.backTime, '%Y-%m-%d')  as backTime,"
				+ "phg.id         as groupId," + "phg.manager_id as managerId,"
				+ "pe.inputCode	as inputCode "
				+ "from pension_hospitalizegroup phg "
				+ "join pension_employee pe " + "on phg.manager_id = pe.id "
				+ "where phg.cleared = 2";

		olderNameSql = "select distinct po.name	as oldName,"
				+ "pbuilding.name	  as buildName," + "pf.name		  as floorName,"
				+ "pr.name		  as roomName," + "pb.name		  as bedName,"
				+ "po.id       		  as olderId,"
				+ "po.inputCode		  as inputCode,"
				+ "pf.id              as floorId,"
				+ "pr.id              as roomId,"
				+ "pb.id              as bedId,"
				+ "pbuilding.id       as buildId" + " from pension_older po"
				+ " join pension_livingRecord plr "
				+ "on po.id = plr.older_id " + "join pension_bed pb "
				+ "on plr.bed_id = pb.id " + "join pension_room pr "
				+ "on pb.room_id = pr.id " + "join pension_floor pf "
				+ "on pr.floor_id = pf.id "
				+ "join pension_building pbuilding "
				+ "on pf.build_id = pbuilding.id " + "where po.cleared = 2 "
				+ "and po.statuses = 3";

		managerNameSql = "select distinct pe.name as managerName,"
				+ "pe.inputCode as inputCode, " + "pd.name	as deptName,"
				+ "pe.id as managerId " + "from pension_hospitalizegroup phg "
				+ "join pension_employee pe " + "on phg.manager_id = pe.id "
				+ "join pension_dept pd " + "on pe.dept_id = pd.id "
				+ "where phg.cleared = 2 " + "and pe.cleared = 2 ";
	}

	/**
	 * 查询假期申请记录
	 */
	public void selectMedicalRegisterRecords() {
		selectedRows = null;
		records = medicalRegisterService.selectMedicalRegisterRecords(
				startDate, endDate, olderId, grouped);

	}

	/**
	 * 删除申请记录
	 */
	public void deleteMedicalRegisterRecord() {
		FacesContext context = FacesContext.getCurrentInstance();
		for (PensionHospitalizeregisterExtend selectedRow : selectedRows) {
			if (selectedRow.getIsback() != null && selectedRow.getIsback() == 1) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, "该老人已返院不能进行任何操作！",
						"该老人已返院不能进行任何操作！");
				context.addMessage(null, message);
			} else {
				medicalRegisterService.deleteMedicalRegisterRecord(selectedRow);
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "删除成功！", "删除成功！");
				context.addMessage(null, message);
			}
		}
		selectMedicalRegisterRecords();
	}

	/**
	 * 录入申请记录
	 */
	public void insertMedicalRegisterRecord() {

		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		Long hosTime = insertedRow.getHospitalizetime().getTime();
		Long curTime = DateUtil.integerDay(new Date()).getTime();
		boolean success = true;
		if (curTime > hosTime) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "就医时间不能小于当前时间！", "提示"));
			success = false;
			return;
		}
		String info = "添加成功";
		try {
			medicalRegisterService.insertMedicalRegisterRecord(insertedRow);
			selectMedicalRegisterRecords();
		} catch (DataAccessException e) {

			info = "添加操作写入数据库出现异常！";

		} catch (Exception e) {

			info = "出现未知异常，请联系系统管理员！";
			System.out.println(e.getStackTrace());

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
		request.addCallbackParam("success", success);

	}

	/**
	 * 修改申请记录
	 */
	public void updateMedicalRegisterRecord() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		Long hosTime = updatedRow.getHospitalizetime().getTime();
		Long curTime = DateUtil.integerDay(new Date()).getTime();
		boolean success = true;
		if (curTime > hosTime) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "就医时间不能小于当前时间！", "提示"));
			success = false;
			return;
		}
		medicalRegisterService.updateMedicalRegisterRecord(updatedRow);
		selectMedicalRegisterRecords();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"修改成功", "修改成功");
		context.addMessage(null, message);
		request.addCallbackParam("success", success);
	}

	/**
	 * 老人就医返院后的登记
	 * 
	 * @throws PmsException
	 */
	public void backedRegister() throws PmsException {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		medicalRegisterService.backedRegister(updatedRow);
		selectMedicalRegisterRecords();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"登记成功！", "登记成功！");
		context.addMessage(null, message);
		request.addCallbackParam("success", true);

	}

	/**
	 * 对就医登记的人进行分组
	 */
	public void groupingMedicalRegisterRecord() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		for (PensionHospitalizeregisterExtend selectedRow : selectedRows) {
			if (selectedRow.getIsback() != null && selectedRow.getIsback() == 1) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, "该老人已返院不能进行任何操作！",
						"该老人已返院不能进行任何操作！");
				context.addMessage(null, message);
			} else {
				medicalRegisterService.groupingMedicalRegisterRecord(
						selectedRow, groupId);
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "分组成功！", "分组成功！");
				context.addMessage(null, message);
			}
		}
		request.addCallbackParam("success", true);
		selectMedicalRegisterRecords();
		groupId = null;
		managerName = null;
	}

	/**
	 * 在后台对输入法填充的字段进行填充
	 */
	public void fillField() {
		PensionLeaveExtend extend = entryVacationApplicationService.fillField(
				insertedRow.getOlderId()).get(0);
		insertedRow.setBedId(extend.getBedId());
		insertedRow.setBedName(extend.getBedName());
		insertedRow.setBuildId(extend.getBuildId());
		insertedRow.setBuildName(extend.getBuildName());
		insertedRow.setFloorId(extend.getFloorId());
		insertedRow.setFloorName(extend.getFloorName());
		insertedRow.setRoomId(extend.getRoomId());
		insertedRow.setRoomName(extend.getRoomName());

	}

	/**
	 * 
	 * 清空insertFrom
	 */
	public void clearInsertForm() {
		insertedRow = new PensionHospitalizeregisterExtend();
	}

	public void clearSelectForm() {

		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		String datePart = df2.format(new Date());
		String dateTime = datePart + " " + "00:00:00";
		SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date newDateTime = null;
		try {
			newDateTime = df3.parse(dateTime);
		} catch (ParseException pe) {
			newDateTime = new Date();
		}
		startDate = newDateTime;
		endDate = newDateTime;
		grouped = null;
		olderId = null;
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

	/**
	 * 选中修改分组
	 * 
	 * @param e
	 */
	public void selectEditGroupRecord(PensionHospitalizegroupExtend record) {
		disDeleteButton = false;
		oneGroupOlder = medicalRegisterService.selectRegisterRecords(record
				.getId());
		groupCollectionInfo = medicalRegisterService
				.selectGroupCollection(record.getId());
	}

	/**
	 * 取消选中分组
	 * 
	 * @param e
	 */
	public void unSelectEditGroupRecord(UnselectEvent e) {
		disDeleteButton = true;
	}

	public void setEnableFLag(SelectEvent e) {
		disDeleteButton = false;
	}

	/**
	 * 讲选中的值赋值给要更新的行
	 */
	public void copyRecordUpdatedRow() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRows == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"请先选中一条记录！", "请先选中一条记录！");
			context.addMessage(null, message);
			return;
		}
		if (selectedRows.length != 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"不可同时修改多条记录！", "不可同时修改多条记录！");
			context.addMessage(null, message);
			return;
		}
		updatedRow = selectedRows[0];
		if (updatedRow.getIsback() != null && updatedRow.getIsback() == 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"该老人已返院不能进行任何操作！", "该老人已返院不能进行任何操作！");
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
		} else {
			request.addCallbackParam("success", true);
		}

	}

	public void regBackRecordUpdatedRow() {

		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRows == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"请先选中一条记录！", "请先选中一条记录！");
			context.addMessage(null, message);
			return;
		}
		if (selectedRows.length != 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"不可同时对多条记录进行返院登记！", "");
			context.addMessage(null, message);
			return;
		}
		updatedRow = selectedRows[0];
		if (updatedRow != null && updatedRow.getId() == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"先选择老人", "");
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
		} else {
			if (updatedRow.getBacktime() == null) {
				updatedRow.setBacktime(new Date());
			}
			request.addCallbackParam("success", true);
		}
	}

	/**
	 * 显示详细信息
	 */
	public void showDetailButton() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRows == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"请先选中一条记录！", "");
			context.addMessage(null, message);
			return;
		}
		if (selectedRows.length != 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"不可同时查看多条记录的详细信息！", "");
			context.addMessage(null, message);
			return;
		}
		updatedRow = selectedRows[0];
		if (updatedRow == null || updatedRow.getId() == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"清选择就医信息", "");
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
		} else {
			request.addCallbackParam("success", true);
		}
	}

	/**
	 * 点击加入新分组
	 */
	public void showAddToNewGroupDlg() {
		toDo = 1;
		insertedGroupRow = new PensionHospitalizegroupExtend();
	}

	public void selectCheck() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRows == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"请先选中一条记录！", "请先选中一条记录！");
			FacesContext.getCurrentInstance().addMessage(null, message);
			requestContext.addCallbackParam("show", false);
		} else {
			requestContext.addCallbackParam("show", true);
		}
	}

	/**
	 * 新建分组
	 */
	public void showAddNewGroupDlg() {
		toDo = 2;
		insertedGroupRow = new PensionHospitalizegroupExtend();
	}

	public void updatedGroupDlg() {
		toDo = 3;
		insertedGroupRow = selectedGroup;
	}

	/**
	 * 对分组进行操作
	 */
	public void addTONewGroupRecord() {

		FacesContext context = FacesContext.getCurrentInstance();
		String info = "添加成功";
		RequestContext request = RequestContext.getCurrentInstance();

		if (toDo == 1) {
			if (selectedRows.length <= 0) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "请先选择就者医信息", "");
				context.addMessage(null, message);
				request.addCallbackParam("success", false);
				return;
			}
		}
		if (insertedGroupRow.getManagerId() == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"必须输入负责人姓名", "");
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
			return;
		}
		if (insertedGroupRow.getLeavetime() == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"必须输入出发时间", "");
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
			return;
		}
		checkBackedRegister();
		try {
			if (toDo == 1) {
				medicalRegisterService.groupingMedicalRegisterRecordToNewGroup(
						selectedRows, insertedGroupRow);
				showAddToNewGroupDlg();
				selectMedicalRegisterRecords();
				info = "分组完成";
			}
			if (toDo == 2) {
				selectedRows = null;
				medicalRegisterService.groupingMedicalRegisterRecordToNewGroup(
						selectedRows, insertedGroupRow);
				showAddNewGroupDlg();
				searchGroupRecordsManagerId();
				info = "分组完成";
				toDo = 1;
			}

			if (toDo == 3) {
				selectedRows = null;
				if (insertedGroupRow.getId() != null) {
					medicalRegisterService.updateGroup(insertedGroupRow);
					searchGroupRecordsManagerId();
					info = "分组完成";
				} else {
					info = "请先选择分组";
				}
				toDo = 1;
			}

			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					info, info);
			context.addMessage(null, message);
			request.addCallbackParam("success", true);
		} catch (PmsException e1) {
			// TODO Auto-generated catch block
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					e1.getMessage(), "");
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
			e1.printStackTrace();
		}
	}

	/**
	 * 加入已有的分组
	 */
	public void addTOGroupRecord() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();

		if (selectedGroup == null || selectedGroup.getId() == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"请选择要加入的分组", "");
			context.addMessage(null, message);
			return;
		}
		if (selectedRows.length <= 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"请先选择就者医信息", "");
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
			return;
		}
		checkBackedRegister();
		try {
			for (PensionHospitalizeregisterExtend selectedRow : selectedRows) {
				String registDate = util.DateUtil.formatDate(selectedRow
						.getHospitalizetime());
				String checkDate = util.DateUtil.formatDate(selectedGroup
						.getLeavetime());
				if (!registDate.equals(checkDate)) {
					FacesMessage message = new FacesMessage(
							FacesMessage.SEVERITY_INFO, "老人"
									+ selectedRow.getOlderName()
									+ "就医时间与分组时间不符，请重新选择！", "");
					context.addMessage(null, message);
					return;
				}
			}
			medicalRegisterService.groupingMedicalRegisterRecordToGroup(
					selectedRows, selectedGroup.getId());
			selectMedicalRegisterRecords();
			selectedGroup = null;
			request.addCallbackParam("success", true);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"分组完成！", "分组完成！");
			context.addMessage(null, message);
		} catch (PmsException e) {
			request.addCallbackParam("success", false);
			// TODO Auto-generated catch block
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					e.getMessage(), "");
			context.addMessage(null, message);
			e.printStackTrace();
		}

	}

	public void sendMessage() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			medicalRegisterService.sendItemCountPaper(selectedGroup);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"发送成功", "");
			context.addMessage(null, message);
		} catch (PmsException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					e.getMessage(), "");
			context.addMessage(null, message);
			e.printStackTrace();
		}
	}

	public void selectToAddRecords(SelectEvent e) {
		addTOGroupRecord();
	}

	/**
	 * 获取分组信息
	 */
	public void selectGroupRecords() {
		selectedGroup = new PensionHospitalizegroupExtend();
		groupCollectionInfo = "";
		oneGroupOlder = new ArrayList<PensionHospitalizeregisterExtend>();
		startGroupDate = startDate;
		endGroupDate = endDate;
		searchGroupRecords();
	}

	public void checkGroup() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRows == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"请先选中一条记录！", "请先选中一条记录！");
			FacesContext.getCurrentInstance().addMessage(null, message);
			requestContext.addCallbackParam("show", false);
		} else {
			selectGroupRecords();
			requestContext.addCallbackParam("show", true);
		}
	}

	/**
	 * 按钮查询
	 */
	public void searchGroupRecords() {
		grouRecords = medicalRegisterService.selectGroupRecords(startGroupDate,
				endGroupDate);
	}

	/**
	 * 管理查询
	 */
	public void searchGroupRecordsManagerId() {
		grouRecords = medicalRegisterService.selectGroupRecordsAndManager(
				startGroupDate, endGroupDate, managerId);
	}

	/**
	 * 删除分组记录
	 */
	public void deleteGroupRecord() {

		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedGroup.getCounts() > 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"分组内存在就医人员，不能删除", "");
			context.addMessage(null, message);
			return;
		}
		try {
			medicalRegisterService.deleteGroupRecord(selectedGroup);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"删除成功！", "删除成功！");
			context.addMessage(null, message);
		} catch (PmsException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					e.getMessage(), "");
			context.addMessage(null, message);
			e.printStackTrace();
		}
		disDeleteButton = true;
		selectGroupRecords();
	}

	/**
	 * 判断被选中的数据中 各个行是否为已经返院登记 如果是则给予提示信息 并且从数据中移除 除此之外还判断被选中的数据 是否存在未返院的如果是
	 * 则返回true 否则返回false
	 */
	public void checkBackedRegister() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedRows == null) {
			return;
		}
		int length = selectedRows.length;
		// 该数组用来存储selectedRows中所有的未返院登记的数据
		PensionHospitalizeregisterExtend[] hospitalizeregisterExtends = new PensionHospitalizeregisterExtend[length];
		int i = 0;
		for (PensionHospitalizeregisterExtend selectedRow : selectedRows) {
			// 把未返院登记的数据填入新的数组中
			if (selectedRow.getIsback() == null) {
				hospitalizeregisterExtends[i++] = selectedRow;
				// FacesMessage message = new
				// FacesMessage(FacesMessage.SEVERITY_INFO,
				// "老人"+selectedRow.getOlderName()+"分组成功！",
				// "老人"+selectedRow.getOlderName()+"分组成功！");
				// context.addMessage(null, message);
			} else {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "老人"
								+ selectedRow.getOlderName()
								+ "已经完成返院登记，不能再加入分组！", "老人"
								+ selectedRow.getOlderName()
								+ "已经完成返院登记，不能再加入分组！");
				context.addMessage(null, message);
			}
		}
		// 先把selectRows中的数据全部置为null
		Arrays.fill(selectedRows, null);
		// 再把所有的数据全部转移到selectedRows中
		System.arraycopy(hospitalizeregisterExtends, 0, selectedRows, 0, i);
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

	public void setOlderId(Integer olderId) {
		this.olderId = olderId;
	}

	public Integer getOlderId() {
		return olderId;
	}

	public void setMedicalRegisterService(
			MedicalRegisterService medicalRegisterService) {
		this.medicalRegisterService = medicalRegisterService;
	}

	public MedicalRegisterService getMedicalRegisterService() {
		return medicalRegisterService;
	}

	public void setRecords(List<PensionHospitalizeregisterExtend> records) {
		this.records = records;
	}

	public List<PensionHospitalizeregisterExtend> getRecords() {
		return records;
	}

	public PensionHospitalizeregisterExtend getInsertedRow() {
		return insertedRow;
	}

	public void setInsertedRow(PensionHospitalizeregisterExtend insertedRow) {
		this.insertedRow = insertedRow;
	}

	public PensionHospitalizeregisterExtend getUpdatedRow() {
		return updatedRow;
	}

	public void setUpdatedRow(PensionHospitalizeregisterExtend updatedRow) {
		this.updatedRow = updatedRow;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public PensionHospitalizeregisterExtend[] getSelectedRows() {
		return selectedRows;
	}

	public void setSelectedRows(PensionHospitalizeregisterExtend[] selectedRows) {
		this.selectedRows = selectedRows;
	}

	public void setGroupSql(String groupSql) {
		this.groupSql = groupSql;
	}

	public String getGroupSql() {
		return groupSql;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setOlderNameSql(String olderNameSql) {
		this.olderNameSql = olderNameSql;
	}

	public String getOlderNameSql() {
		return olderNameSql;
	}

	public void setEntryVacationApplicationService(
			EntryVacationApplicationService entryVacationApplicationService) {
		this.entryVacationApplicationService = entryVacationApplicationService;
	}

	public EntryVacationApplicationService getEntryVacationApplicationService() {
		return entryVacationApplicationService;
	}

	public Long getGrouped() {
		return grouped;
	}

	public void setGrouped(Long grouped) {
		this.grouped = grouped;
	}

	public void setInsertedGroupRow(
			PensionHospitalizegroupExtend insertedGroupRow) {
		this.insertedGroupRow = insertedGroupRow;
	}

	public PensionHospitalizegroupExtend getInsertedGroupRow() {
		return insertedGroupRow;
	}

	public void setStartGroupDate(Date startGroupDate) {
		this.startGroupDate = startGroupDate;
	}

	public Date getStartGroupDate() {
		return startGroupDate;
	}

	public void setEndGroupDate(Date endGroupDate) {
		this.endGroupDate = endGroupDate;
	}

	public Date getEndGroupDate() {
		return endGroupDate;
	}

	public void setGrouRecords(List<PensionHospitalizegroupExtend> grouRecords) {
		this.grouRecords = grouRecords;
	}

	public List<PensionHospitalizegroupExtend> getGrouRecords() {
		return grouRecords;
	}

	public void setSelectedGroup(PensionHospitalizegroupExtend selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public PensionHospitalizegroupExtend getSelectedGroup() {
		return selectedGroup;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerNameSql(String managerNameSql) {
		this.managerNameSql = managerNameSql;
	}

	public String getManagerNameSql() {
		return managerNameSql;
	}

	public void setDisDeleteButton(boolean disDeleteButton) {
		this.disDeleteButton = disDeleteButton;
	}

	public boolean isDisDeleteButton() {
		return disDeleteButton;
	}

	public void setOneGroupOlder(
			List<PensionHospitalizeregisterExtend> oneGroupOlder) {
		this.oneGroupOlder = oneGroupOlder;
	}

	public List<PensionHospitalizeregisterExtend> getOneGroupOlder() {
		return oneGroupOlder;
	}

	public void setGroupCollectionInfo(String groupCollectionInfo) {
		this.groupCollectionInfo = groupCollectionInfo;
	}

	public String getGroupCollectionInfo() {
		return groupCollectionInfo;
	}
}
