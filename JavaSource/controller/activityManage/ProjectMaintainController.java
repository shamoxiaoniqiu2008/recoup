/**  
 * @Title: ProjectMaintainController.java 
 * @Package controller.activityManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-13 上午10:21:31 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package controller.activityManage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;

import service.activityManage.ProjectMaintainService;
import util.DateUtil;
import util.FileUtil;
import util.Spell;

import domain.activityManage.PensionActivity;
import domain.dictionary.PensionDicClass;
import domain.dictionary.PensionDicSuperClass;

/**
 * @ClassName: ProjectMaintainController
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-13 上午10:21:31
 * @version V1.0
 */
public class ProjectMaintainController implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @version V1.0
	 */

	private static final long serialVersionUID = -1504535075635020436L;

	private transient ProjectMaintainService projectMaintainService;

	// 定义项目名称输入法变量
	private String itemToSql;
	private String fitcolItem;
	private String displaycolItem;

	// 定义修改和删除按钮可用性Flag
	private boolean modifyEnableFlag = false;
	private boolean deleteEnableFlag = false;
	// 康娱项目List
	private List<PensionActivity> pensionActivityList = new ArrayList<PensionActivity>();
	// 项目大类
	private List<PensionDicSuperClass> pensionDicSuperClassList = new ArrayList<PensionDicSuperClass>();
	// 项目亚类
	private List<PensionDicClass> pensionDicClassList = new ArrayList<PensionDicClass>();
	// 项目大类ID
	private Long superClassId;
	// 项目亚类ID
	private Long classId;
	// 项目名称
	private String itemName;
	// 项目ID
	private Long itemId;
	// 康娱项目对象
	private PensionActivity pensionActivity;
	// 选中的康娱项目对象
	private PensionActivity selectedPensionActivity;
	private String photoPath;// 用来存储photo的相对路径
	private boolean addFlag = false;// 新增标记
	private boolean copyFlag = false;// 复制标记
	private Date hodeTime;
	private String hodePlace;

	/**
	 * 
	 * @Title: init
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-11 下午1:56:55
	 * @version V1.0
	 */
	@PostConstruct
	public void init() {
		modifyEnableFlag = true;
		deleteEnableFlag = true;
		pensionActivityList = projectMaintainService.getAllActivitis();
		pensionDicSuperClassList = projectMaintainService.getAllSuperClass();
		pensionDicClassList = projectMaintainService.getAllClass();
		initItemSql();
	}

	/**
	 * 
	 * @Title: initItemSql
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-11 下午2:55:49
	 * @version V1.0
	 */
	public void initItemSql() {
		itemToSql = " SELECT" + " pa.`id` AS itemId"
				+ " ,pa.`itemName` AS itemName"
				+ " ,pa.`inputCode` AS inputCode" + " FROM"
				+ " pension_activity pa" + " WHERE" + " pa.`cleared`=2";
		fitcolItem = "inputCode";
		displaycolItem = "编号:0:hide,名称:1,输入码:2";
	}

	/**
	 * 
	 * @Title: searchByCondition
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-11 下午4:56:15
	 * @version V1.0
	 */
	public void searchByCondition() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (itemName == null || "".equals(itemName.trim())) {
			itemId = null;
		}
		pensionActivityList = projectMaintainService
				.getAllActivitisByCondition(itemId, superClassId, classId);
		// context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
		// "查询完毕！", ""));
	}

	/**
	 * 
	 * @Title: initParam
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-11 下午4:26:41
	 * @version V1.0
	 */
	public void initParam() {
		itemId = null;
		itemName = "";
		superClassId = 0L;
		classId = 0L;
	}

	/**
	 * 
	 * @Title: setSelectedFlag
	 * @Description: TODO
	 * @param @param event
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-11 下午5:29:55
	 * @version V1.0
	 */
	public void setSelectedFlag(SelectEvent event) {
		modifyEnableFlag = false;
		deleteEnableFlag = false;
	}

	/**
	 * 
	 * @Title: setUnselectedFlag
	 * @Description: TODO
	 * @param @param event
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-11 下午5:29:59
	 * @version V1.0
	 */
	public void setUnselectedFlag(UnselectEvent event) {
		modifyEnableFlag = true;
		deleteEnableFlag = true;
	}

	/**
	 * 查看活动详情
	 * 
	 * @param activityId
	 */
	public void view(Long activityId) {
		pensionActivity = projectMaintainService
				.selectPensionActivity(activityId);
	}

	/**
	 * 
	 * @Title: handleFileUpload
	 * @Description: TODO
	 * @param @param event
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-12 上午9:26:53
	 * @version V1.0
	 */
	public void handleFileUpload(FileUploadEvent event) {
		photoPath = null;
		// 图片上传到Tomcat发布目录下的uploaded目录
		UploadedFile file = event.getFile();
		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		String fileName = file.getFileName();
		String upFileName = DateUtil.getFormatDateTime(new Date()) + ".jpg";
		String sourceFileName = servletContext.getRealPath("") + File.separator
				+ "uploaded" + File.separator + fileName;
		String targetFileName = servletContext.getRealPath("") + File.separator
				+ "images" + File.separator + "activityManage" + File.separator
				+ upFileName;

		photoPath = File.separator + "images" + File.separator
				+ "activityManage" + File.separator + upFileName;
		photoPath = photoPath.replace("\\", "/");
		if (addFlag) {
			pensionActivity.setImageurl(photoPath);
		} else {
			selectedPensionActivity.setImageurl(photoPath);
		}
		try {
			FileOutputStream fos = new FileOutputStream(
					new File(sourceFileName));
			InputStream is = file.getInputstream();
			int BUFFER_SIZE = 8192;
			byte[] buffer = new byte[BUFFER_SIZE];
			int a;
			while (true) {
				a = is.read(buffer);
				if (a < 0)
					break;
				fos.write(buffer, 0, a);
				fos.flush();
			}
			fos.close();
			is.close();
			FacesMessage msg = new FacesMessage("图片上传成功！", event.getFile()
					.getFileName() + null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 图片拷贝到images目录对应的文件夹
		File sourceFile = new File(sourceFileName);
		File targetFile = new File(targetFileName);
		try {
			FileUtil.copyFile(sourceFile, targetFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 删除Tomcat发布目录下的uploaded目录下的文件
		try {
			FileUtil.deleteFile(sourceFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: addActivity
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-12 上午9:26:48
	 * @version V1.0
	 */
	public void addActivity() {
		RequestContext request = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		if (pensionActivity.getImageurl() == null
				|| "".equals(pensionActivity.getImageurl().trim())) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请先上传图片！", ""));
			request.addCallbackParam("validate", false);
			return;
		}
		if (pensionActivity.getSuperClassId() == 0L) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请先选择大类！", ""));
			request.addCallbackParam("validate", false);
			return;
		}
		if (pensionActivity.getClassId() == 0L) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请先选择亚类！", ""));
			request.addCallbackParam("validate", false);
			return;
		}

		List<String> nameList = projectMaintainService.getAllActivityName();
		String activityName = pensionActivity.getItemname();
		if (nameList.contains(activityName)) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "项目名称设定重复 ！", ""));
			request.addCallbackParam("validate", false);
			return;
		}
		pensionActivity.setCleared(2);
		if (addFlag) {
			projectMaintainService.insertActivityBySelective(pensionActivity);
			pensionActivity = new PensionActivity();
			pensionActivity.setSuperClassId(0L);
			pensionActivity.setClassId(0L);
			request.addCallbackParam("validate", false);
		} else if (copyFlag) {
			pensionActivity.setId(null);
			pensionActivity.setEnlistnumber(null);
			pensionActivity.setRealnumber(null);
			pensionActivity.setSummary(null);
			pensionActivity.setSummaryerId(null);
			pensionActivity.setSummaryername(null);
			pensionActivity.setSummarytime(null);
			pensionActivity.setRecorderId(null);
			pensionActivity.setRecordername(null);
			pensionActivity.setRecordtime(null);
			projectMaintainService.insertActivityBySelective(pensionActivity);
			request.addCallbackParam("validate", true);
		}
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"康娱项目" + activityName + "新增成功！", ""));
		if (itemName == null || "".equals(itemName.trim())) {
			itemId = null;
		}
		pensionActivityList = projectMaintainService
				.getAllActivitisByCondition(itemId, superClassId, classId);
	}

	/**
	 * 
	 * @Title: saveActivity
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-12 上午10:34:06
	 * @version V1.0
	 */
	public void saveActivity() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedPensionActivity.getImageurl() == null
				|| "".equals(selectedPensionActivity.getImageurl().trim())) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请先上传图片！", ""));
			return;
		}
		if (selectedPensionActivity.getSuperClassId() == 0L) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请先选择大类！", ""));
			return;
		}
		if (selectedPensionActivity.getClassId() == 0L) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请先选择亚类！", ""));
			return;
		}

		List<String> nameList = projectMaintainService
				.getOtherActivityName(selectedPensionActivity);
		if (nameList.contains(selectedPensionActivity.getItemname())) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "项目名称设定重复 ！", ""));
			return;
		}
		// 如果康娱项目的举办时间或举办地点有改动，则需通知老人，否则就不需要通知老人
		if (!hodeTime.equals(selectedPensionActivity.getStarttime())
				|| !hodePlace.equals(selectedPensionActivity.getItemplace())) {
			projectMaintainService.updateActivityBySelective(
					selectedPensionActivity, true);
		} else {
			projectMaintainService.updateActivityBySelective(
					selectedPensionActivity, false);
		}

		if (itemName == null || "".equals(itemName.trim())) {
			itemId = null;
		}
		pensionActivityList = projectMaintainService
				.getAllActivitisByCondition(itemId, superClassId, classId);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"康娱项目" + selectedPensionActivity.getItemname() + "修改成功！", ""));

	}

	/**
	 * 
	 * @Title: setInputCodeAdd
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-12 上午9:27:05
	 * @version V1.0
	 */
	public void setInputCodeAdd() {
		pensionActivity.setInputcode(Spell.getFirstSpell(pensionActivity
				.getItemname()));
	}

	/**
	 * 
	 * @Title: setInputCodeEdit
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-12 上午10:34:20
	 * @version V1.0
	 */
	public void setInputCodeEdit() {
		selectedPensionActivity.setInputcode(Spell
				.getFirstSpell(selectedPensionActivity.getItemname()));
	}

	/**
	 * 
	 * @Title: initActivity
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-12 上午9:27:10
	 * @version V1.0
	 */
	public void initActivity() {
		addFlag = true;
		pensionActivity = new PensionActivity();
	}

	/**
	 * 
	 * @Title: editButtonPara
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-12 上午9:29:24
	 * @version V1.0
	 */
	public void editButtonPara() {
		addFlag = false;
		hodeTime = selectedPensionActivity.getStarttime();
		hodePlace = selectedPensionActivity.getItemplace();
	}

	/**
	 * 
	 * @Title: deleteActivity
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-12 上午10:43:01
	 * @version V1.0
	 */
	public void deleteActivity() {
		FacesContext context = FacesContext.getCurrentInstance();
		projectMaintainService.deleteActivity(selectedPensionActivity);
		if (itemName == null || "".equals(itemName.trim())) {
			itemId = null;
		}
		pensionActivityList = projectMaintainService
				.getAllActivitisByCondition(itemId, superClassId, classId);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"项目" + selectedPensionActivity.getItemname() + "删除成功！", ""));
	}

	/**
	 * 取消康娱项目
	 */
	public void cancelActivity() {
		FacesContext context = FacesContext.getCurrentInstance();
		projectMaintainService.cancelActivity(selectedPensionActivity.getId(),
				selectedPensionActivity.getItemname());
		selectedPensionActivity.setCanceled(1);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"项目" + selectedPensionActivity.getItemname() + "取消成功！", ""));
	}

	/**
	 * check要删除的项目下，是否已经有人报名，有，不能删除，无，可以删除 add by mary.liu 2014-02-27
	 */
	public void checkDeleteProject() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (projectMaintainService.checkActivity(selectedPensionActivity
				.getId())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"该项目已有人报名，不能删除！", ""));
			request.addCallbackParam("validate", false);
		} else {
			request.addCallbackParam("validate", true);
		}
	}

	/**
	 * check要删除的项目下，是否已经有人报名，有，不能删除，无，可以删除 add by mary.liu 2014-02-27
	 */
	public void checkCancelProject() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedPensionActivity.getCanceled() == 1) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"该项目已取消，不能重复操作！", ""));
			request.addCallbackParam("validate", false);
		} else {
			request.addCallbackParam("validate", true);
		}
	}

	/**
	 * 复制康娱项目 add by mary.liu 2014-02-27
	 */
	public void copyProject() {
		copyFlag = true;
		pensionActivity = selectedPensionActivity;
		pensionActivity.setId(null);
		pensionActivity.setItemname(null);
	}

	public String getItemToSql() {
		return itemToSql;
	}

	public void setItemToSql(String itemToSql) {
		this.itemToSql = itemToSql;
	}

	public String getFitcolItem() {
		return fitcolItem;
	}

	public void setFitcolItem(String fitcolItem) {
		this.fitcolItem = fitcolItem;
	}

	public String getDisplaycolItem() {
		return displaycolItem;
	}

	public void setDisplaycolItem(String displaycolItem) {
		this.displaycolItem = displaycolItem;
	}

	public boolean isModifyEnableFlag() {
		return modifyEnableFlag;
	}

	public void setModifyEnableFlag(boolean modifyEnableFlag) {
		this.modifyEnableFlag = modifyEnableFlag;
	}

	public boolean isDeleteEnableFlag() {
		return deleteEnableFlag;
	}

	public void setDeleteEnableFlag(boolean deleteEnableFlag) {
		this.deleteEnableFlag = deleteEnableFlag;
	}

	/**
	 * @return the pensionActivityList
	 */
	public List<PensionActivity> getPensionActivityList() {
		return pensionActivityList;
	}

	/**
	 * @param pensionActivityList
	 *            the pensionActivityList to set
	 */
	public void setPensionActivityList(List<PensionActivity> pensionActivityList) {
		this.pensionActivityList = pensionActivityList;
	}

	/**
	 * @return the projectMaintainService
	 */
	public ProjectMaintainService getProjectMaintainService() {
		return projectMaintainService;
	}

	/**
	 * @param projectMaintainService
	 *            the projectMaintainService to set
	 */
	public void setProjectMaintainService(
			ProjectMaintainService projectMaintainService) {
		this.projectMaintainService = projectMaintainService;
	}

	/**
	 * @return the pensionDicClassList
	 */
	public List<PensionDicClass> getPensionDicClassList() {
		return pensionDicClassList;
	}

	/**
	 * @param pensionDicClassList
	 *            the pensionDicClassList to set
	 */
	public void setPensionDicClassList(List<PensionDicClass> pensionDicClassList) {
		this.pensionDicClassList = pensionDicClassList;
	}

	/**
	 * @return the pensionDicSuperClassList
	 */
	public List<PensionDicSuperClass> getPensionDicSuperClassList() {
		return pensionDicSuperClassList;
	}

	/**
	 * @param pensionDicSuperClassList
	 *            the pensionDicSuperClassList to set
	 */
	public void setPensionDicSuperClassList(
			List<PensionDicSuperClass> pensionDicSuperClassList) {
		this.pensionDicSuperClassList = pensionDicSuperClassList;
	}

	/**
	 * @return the superClassId
	 */
	public Long getSuperClassId() {
		return superClassId;
	}

	/**
	 * @param superClassId
	 *            the superClassId to set
	 */
	public void setSuperClassId(Long superClassId) {
		this.superClassId = superClassId;
	}

	/**
	 * @return the classId
	 */
	public Long getClassId() {
		return classId;
	}

	/**
	 * @param classId
	 *            the classId to set
	 */
	public void setClassId(Long classId) {
		this.classId = classId;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName
	 *            the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the itemId
	 */
	public Long getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the pensionActivity
	 */
	public PensionActivity getPensionActivity() {
		return pensionActivity;
	}

	/**
	 * @param pensionActivity
	 *            the pensionActivity to set
	 */
	public void setPensionActivity(PensionActivity pensionActivity) {
		this.pensionActivity = pensionActivity;
	}

	/**
	 * @return the selectedPensionActivity
	 */
	public PensionActivity getSelectedPensionActivity() {
		return selectedPensionActivity;
	}

	/**
	 * @param selectedPensionActivity
	 *            the selectedPensionActivity to set
	 */
	public void setSelectedPensionActivity(
			PensionActivity selectedPensionActivity) {
		this.selectedPensionActivity = selectedPensionActivity;
	}

	/**
	 * @return the addFlag
	 */
	public boolean isAddFlag() {
		return addFlag;
	}

	/**
	 * @param addFlag
	 *            the addFlag to set
	 */
	public void setAddFlag(boolean addFlag) {
		this.addFlag = addFlag;
	}

	/**
	 * @return the photoPath
	 */
	public String getPhotoPath() {
		return photoPath;
	}

	/**
	 * @param photoPath
	 *            the photoPath to set
	 */
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public boolean isCopyFlag() {
		return copyFlag;
	}

	public void setCopyFlag(boolean copyFlag) {
		this.copyFlag = copyFlag;
	}

}
