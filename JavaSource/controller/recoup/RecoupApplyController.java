/**
 * 
 */
package controller.recoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.onlyfido.util.SessionManager;

import service.recoup.RecoupApplyService;
import util.DateUtil;
import util.FileUtil;
import domain.recoup.RecoupApplyDetailExtend;
import domain.recoup.RecoupApplyRecordExtend;
import domain.recoup.RecoupDicCostclass1;
import domain.recoup.RecoupDicCostclass2;
import domain.recoup.RecoupDicPayclass;
import domain.recoup.RecoupDicProject;

/**
 * @author justin
 * 
 */

public class RecoupApplyController implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @version V1.0
	 */

	private static final long serialVersionUID = 100000000000001L;

	private transient RecoupApplyService recoupApplyService;

	private List<RecoupApplyRecordExtend> recordList = new ArrayList<RecoupApplyRecordExtend>();

	private RecoupApplyRecordExtend recordForAdd = new RecoupApplyRecordExtend();

	private List<RecoupApplyDetailExtend> detailListForAdd = new ArrayList<RecoupApplyDetailExtend>();

	private RecoupApplyDetailExtend detailForAdd = new RecoupApplyDetailExtend();

	private RecoupApplyDetailExtend selectedDetail = new RecoupApplyDetailExtend();

	private List<RecoupDicProject> projects = new ArrayList<RecoupDicProject>();

	private List<RecoupDicPayclass> payclasses = new ArrayList<RecoupDicPayclass>();

	private List<RecoupDicCostclass1> costclasses1 = new ArrayList<RecoupDicCostclass1>();

	private List<RecoupDicCostclass2> costclasses2 = new ArrayList<RecoupDicCostclass2>();

	/**
	 * 说明：
	 * 	Controller尽量不要在这层放业务逻辑操作。
	 * 	将业务逻辑挪到Service层进行处理，对应的值域加载也别放在此处。
	 * 将值域的获取尽量统一封装到一个方法里面，通过参数调用。另外就是能够合在一起最好
	 * 目前优先实现功能，后面重构吧。
	 */
	
	@PostConstruct
	public void init() {
		payclasses = new ArrayList<RecoupDicPayclass>();
		projects = new ArrayList<RecoupDicProject>();
		costclasses1 = new ArrayList<RecoupDicCostclass1>();
		costclasses2 = new ArrayList<RecoupDicCostclass2>();
		projects = recoupApplyService.selectAllProjects();
		payclasses = recoupApplyService.selectAllPayclasses();
		costclasses1 = recoupApplyService.selectAllCostclasses1();
		costclasses2 = recoupApplyService.selectAllCostclasses2();
	}

	public void getDetailDefaultValue() {
		detailForAdd = new RecoupApplyDetailExtend();
		detailForAdd.setFeeDatetime(DateUtil.parseDate(
				DateUtil.getDate(new Date()), "yyyy-MM-dd"));
	}

	public void getRecordDefaultValue() {
		recordForAdd = new RecoupApplyRecordExtend();
		recordForAdd.setNeme(SessionManager.getCurEmployee().getName());
		recordForAdd.setApplyDate(DateUtil.parseDate(
				DateUtil.getDate(new Date()), "yyyy-MM-dd"));
	}

	public void addDetailList() {
		if (detailForAdd.equals(null) || detailForAdd.equals("")) {
			return;
		} else {
			RecoupApplyDetailExtend detail = new RecoupApplyDetailExtend();
			detail = detailForAdd;
			detailListForAdd.add(detail);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "明细添加成功！",
							"明细添加成功！"));
		}
	}
	/**
	 * 保存报销申请
	 */
	public void saveRecoup(){
		System.out.println("调用保存！");
		RequestContext request = RequestContext.getCurrentInstance();
		RecoupApplyService ras = new RecoupApplyService();
		
		
	}
	
	
	/**
	 * 文件上传业务逻辑操作
	 * @param event
	 */
	public void handleFileUpload(FileUploadEvent event) {
		RecoupApplyService ras = new RecoupApplyService();
		// 图片上传到Tomcat发布目录下的uploaded目录
		UploadedFile file = event.getFile();

		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		String currentPath = servletContext.getRealPath("");
		
		String photoPath = ras.fileUpload(currentPath,file);
		//此flag的作用是啥？不是很清楚。
		boolean addFlag = false;
		if (addFlag) {
			detailForAdd.setImageUrl(photoPath);
		} else {
			selectedDetail.setImageUrl(photoPath);
		}
	}

	/**
	 * @return the recordForAdd
	 */

	public RecoupApplyRecordExtend getRecordForAdd() {
		return recordForAdd;
	}

	/**
	 * @param recordForAdd
	 *            the recordForAdd to set
	 */

	public void setRecordForAdd(RecoupApplyRecordExtend recordForAdd) {
		this.recordForAdd = recordForAdd;
	}

	/**
	 * @return the detailListForAdd
	 */

	public List<RecoupApplyDetailExtend> getDetailListForAdd() {
		return detailListForAdd;
	}

	/**
	 * @param detailListForAdd
	 *            the detailListForAdd to set
	 */

	public void setDetailListForAdd(
			List<RecoupApplyDetailExtend> detailListForAdd) {
		this.detailListForAdd = detailListForAdd;
	}

	/**
	 * @return the detailForAdd
	 */

	public RecoupApplyDetailExtend getDetailForAdd() {
		return detailForAdd;
	}

	/**
	 * @param detailForAdd
	 *            the detailForAdd to set
	 */

	public void setDetailForAdd(RecoupApplyDetailExtend detailForAdd) {
		this.detailForAdd = detailForAdd;
	}

	/**
	 * @return the recoupApplyService
	 */

	public RecoupApplyService getRecoupApplyService() {
		return recoupApplyService;
	}

	/**
	 * @param recoupApplyService
	 *            the recoupApplyService to set
	 */

	public void setRecoupApplyService(RecoupApplyService recoupApplyService) {
		this.recoupApplyService = recoupApplyService;
	}

	/**
	 * @return the projects
	 */
	public List<RecoupDicProject> getProjects() {
		return projects;
	}

	/**
	 * @param projects
	 *            the projects to set
	 */
	public void setProjects(List<RecoupDicProject> projects) {
		this.projects = projects;
	}

	/**
	 * @return the selectedDetail
	 */
	public RecoupApplyDetailExtend getSelectedDetail() {
		return selectedDetail;
	}

	/**
	 * @param selectedDetail
	 *            the selectedDetail to set
	 */
	public void setSelectedDetail(RecoupApplyDetailExtend selectedDetail) {
		this.selectedDetail = selectedDetail;
	}

	/**
	 * @return the recordList
	 */
	public List<RecoupApplyRecordExtend> getRecordList() {
		return recordList;
	}

	/**
	 * @param recordList
	 *            the recordList to set
	 */
	public void setRecordList(List<RecoupApplyRecordExtend> recordList) {
		this.recordList = recordList;
	}

	/**
	 * @return the payclasses
	 */
	public List<RecoupDicPayclass> getPayclasses() {
		return payclasses;
	}

	/**
	 * @param payclasses
	 *            the payclasses to set
	 */
	public void setPayclasses(List<RecoupDicPayclass> payclasses) {
		this.payclasses = payclasses;
	}

	/**
	 * @return the costclasses1
	 */
	public List<RecoupDicCostclass1> getCostclasses1() {
		return costclasses1;
	}

	/**
	 * @param costclasses1
	 *            the costclasses1 to set
	 */
	public void setCostclasses1(List<RecoupDicCostclass1> costclasses1) {
		this.costclasses1 = costclasses1;
	}

	/**
	 * @return the costclasses2
	 */
	public List<RecoupDicCostclass2> getCostclasses2() {
		return costclasses2;
	}

	/**
	 * @param costclasses2
	 *            the costclasses2 to set
	 */
	public void setCostclasses2(List<RecoupDicCostclass2> costclasses2) {
		this.costclasses2 = costclasses2;
	}

}
