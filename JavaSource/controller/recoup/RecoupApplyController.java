/**
 * 
 */
package controller.recoup;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;

import service.recoup.RecoupApplyService;
import util.DateUtil;

import com.onlyfido.util.SessionManager;

import domain.recoup.RecoupApplyDetailExtend;
import domain.recoup.RecoupApplyRecordExtend;
import domain.recoup.SysDeDatarangeitem;

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
	
	//项目列表
	private List<SysDeDatarangeitem> projects =new ArrayList<SysDeDatarangeitem>();
	//支付方式
	private List<SysDeDatarangeitem> payclasses = new ArrayList<SysDeDatarangeitem>();
	//费用大类
	private List<SysDeDatarangeitem> costclasses1 = new ArrayList<SysDeDatarangeitem>();
	//费用亚类
	private List<SysDeDatarangeitem> costclasses2 = new ArrayList<SysDeDatarangeitem>();
	
	private boolean addFlag = false;

	/**
	 * 说明：
	 * 	Controller尽量不要在这层放业务逻辑操作。
	 * 	将业务逻辑挪到Service层进行处理，对应的值域加载也别放在此处。
	 * 将值域的获取尽量统一封装到一个方法里面，通过参数调用。另外就是能够合在一起最好
	 * 目前优先实现功能，后面重构吧。
	 */
	
	@PostConstruct
	public void init() {
		payclasses = new ArrayList<SysDeDatarangeitem>();
		projects = new ArrayList<SysDeDatarangeitem>();
		costclasses1 = new ArrayList<SysDeDatarangeitem>();
		costclasses2 = new ArrayList<SysDeDatarangeitem>();
		projects = recoupApplyService.selectAllItem("RC003");
		payclasses = recoupApplyService.selectAllItem("RC002");
		costclasses1 = recoupApplyService.selectAllpayItem("RC001",(long)1);
		costclasses2 = recoupApplyService.selectAllpayItem("RC001",(long)2);
	}

	/**
	 * 
		* @Title: getDetailDefaultValue 
		* @Description: TODO
		* @param 
		* @return void
		* @throws 
		* @author Justin.Su
		* @date 2014-7-2 下午2:12:12
		* @version V1.0
	 */
	public void getDetailDefaultValue() {
		detailForAdd = new RecoupApplyDetailExtend();
		detailForAdd.setFeeDate(new Date());
		detailForAdd.setAmount(BigDecimal.ZERO);
		addFlag = true;
	}

	/**
	 * 
		* @Title: getRecordDefaultValue 
		* @Description: TODO
		* @param 
		* @return void
		* @throws 
		* @author Justin.Su
		* @date 2014-7-2 下午2:12:15
		* @version V1.0
	 */
	public void getRecordDefaultValue() {
		recordForAdd = new RecoupApplyRecordExtend();
		recordForAdd.setUserName(SessionManager.getCurUser().getUsername());
		recordForAdd.setUserId(SessionManager.getCurUser().getId());
		recordForAdd.setApplyDateTime(DateUtil.parseDate(DateUtil.getDateyyyyMMdd(new Date()), "yyyyMMdd"));
	}

	/**
	 * 
		* @Title: addDetailList 
		* @Description: TODO
		* @param 
		* @return void
		* @throws 
		* @author Justin.Su
		* @date 2014-7-2 下午2:12:21
		* @version V1.0
	 */
	public void addDetailList() {
		if (detailForAdd.equals(null) || detailForAdd.equals("")) {
			return;
		} else {
			detailListForAdd.add(detailForAdd);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "明细添加成功！",
							"明细添加成功！"));
		}
		if(detailListForAdd.size() > 0){
			BigDecimal tempAmount = BigDecimal.ZERO;
			recordForAdd.setMoney(BigDecimal.ZERO);
			for(RecoupApplyDetailExtend detail : detailListForAdd){
				tempAmount = tempAmount.add(detail.getPrice().multiply(new BigDecimal(detail.getQty())));
				detail.setFeeDatetime(DateUtil.getDateyyyyMMdd(detail.getFeeDate()));
			}
			recordForAdd.setMoney(tempAmount);
		}
		detailForAdd = new RecoupApplyDetailExtend();
	}
	
	/**
	 * 
		* @Title: calcAmount 
		* @Description: TODO
		* @param 
		* @return void
		* @throws 
		* @author Justin.Su
		* @date 2014-7-2 下午09:25:47
		* @version V1.0
	 */
	public void calcAmount(){
		detailForAdd.setAmount(recoupApplyService.getAmount(detailForAdd));
	}
	
	/**
	 * 保存报销申请
	 */
	public void saveRecoup(){
		System.out.println("调用保存！");
		recoupApplyService.insertApplyRecordAndDetail(recordForAdd, detailListForAdd,1);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("保存报销申请成功", "保存报销申请成功！"));
	}
	
	public void commitRecoup(){
		System.out.println("调用提交！");
		recoupApplyService.insertApplyRecordAndDetail(recordForAdd, detailListForAdd,2);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("提交报销申请成功", "提交报销申请成功！"));
	}
	
	
	/**
	 * 文件上传业务逻辑操作
	 * @param event
	 */
	public void handleFileUpload(FileUploadEvent event) {
		// 图片上传到Tomcat发布目录下的uploaded目录
		UploadedFile file = event.getFile();

		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		String currentPath = servletContext.getRealPath("");
		
		String photoPath = recoupApplyService.fileUpload(currentPath,file);
		//此flag的作用是啥？不是很清楚。
		
		if (addFlag) {
			detailForAdd.setImageUrl(photoPath);
		} else {
			selectedDetail.setImageUrl(photoPath);
		}
	}
	
	
	public void onDetailUnselect(UnselectEvent e) {

	}

	public void onDetailSelect(SelectEvent e) {

	}
	
	
	public void getType2List(){
		if(recordForAdd.getExpTypeCodeP().equals("0")){
			costclasses2 = recoupApplyService.selectAllpayItem("RC001",(long)2);
		}else{
			costclasses2 = recoupApplyService.selectAllpayItemBy(recordForAdd.getExpTypeCodeP());
		}
	}
	
	
	public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edited", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "编辑完成", "编辑完成");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if(detailListForAdd.size() > 0){
			BigDecimal tempAmount = BigDecimal.ZERO;
			recordForAdd.setMoney(BigDecimal.ZERO);
			for(RecoupApplyDetailExtend detail : detailListForAdd){
				detail.setAmount(detail.getPrice().multiply(new BigDecimal(detail.getQty())));
				tempAmount = tempAmount.add(detail.getPrice().multiply(new BigDecimal(detail.getQty())));
			}
			recordForAdd.setMoney(tempAmount);
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
		 * @param projects the projects to set
		 */  
	    
	public void setProjects(List<SysDeDatarangeitem> projects) {
		this.projects = projects;
	}

	  
		/**
		 * @return the projects
		 */  
	    
	public List<SysDeDatarangeitem> getProjects() {
		return projects;
	}

		/**
		 * @return the payclasses
		 */
		
		public List<SysDeDatarangeitem> getPayclasses() {
			return payclasses;
		}

		/**
		 * @param payclasses the payclasses to set
		 */
		
		public void setPayclasses(List<SysDeDatarangeitem> payclasses) {
			this.payclasses = payclasses;
		}

		/**
		 * @return the costclasses1
		 */
		
		public List<SysDeDatarangeitem> getCostclasses1() {
			return costclasses1;
		}

		/**
		 * @param costclasses1 the costclasses1 to set
		 */
		
		public void setCostclasses1(List<SysDeDatarangeitem> costclasses1) {
			this.costclasses1 = costclasses1;
		}

		/**
		 * @return the costclasses2
		 */
		
		public List<SysDeDatarangeitem> getCostclasses2() {
			return costclasses2;
		}

		/**
		 * @param costclasses2 the costclasses2 to set
		 */
		
		public void setCostclasses2(List<SysDeDatarangeitem> costclasses2) {
			this.costclasses2 = costclasses2;
		}

		/**
		 * @return the addFlag
		 */
		
		public boolean isAddFlag() {
			return addFlag;
		}

		/**
		 * @param addFlag the addFlag to set
		 */
		
		public void setAddFlag(boolean addFlag) {
			this.addFlag = addFlag;
		}


	
}
