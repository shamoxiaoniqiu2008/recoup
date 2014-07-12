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

import org.primefaces.context.RequestContext;
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
import exception.ClassSelectException;
import exception.DetailAddException;
import exception.ItemSelectException;
import exception.PayWaySelectException;
import exception.SuperClassSelectException;

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

	private RecoupApplyRecordExtend selectedRecord = new RecoupApplyRecordExtend();
	
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
	//添加还是编辑Flag
	private boolean addFlag = false;
	//项目代码
	private String projectCode;
	//支付状态
	private Integer payState;
	//申请开始日期
	private Date applyDateStart;
	//申请截止日期
	private Date applyDateEnd;
	
	

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
		searchRecoupBy();
	}
	
	/**
	 * 
		* @Title: searchRecoupBy 
		* @Description: TODO
		* @param 
		* @return void
		* @throws 
		* @author Justin.Su
		* @date 2014-7-12 上午10:46:16
		* @version V1.0
	 */
	public void searchRecoupBy(){
		recordList = recoupApplyService.getRecoupBy(projectCode, payState, applyDateStart, applyDateEnd);
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
	 * 
		* @Title: saveRecoup 
		* @Description: TODO
		* @param 
		* @return void
		* @throws 
		* @author Justin.Su
		* @date 2014-7-12 上午09:02:12
		* @version V1.0
	 */
	public void saveRecoup(){
		System.out.println("调用保存！");
		try {
			if(recoupApplyService.checkValue(recordForAdd, detailListForAdd)){
				recoupApplyService.insertApplyRecordAndDetail(recordForAdd, detailListForAdd,1);
				//初始化
				getRecordDefaultValue();
				searchRecoupBy();
				detailListForAdd = new ArrayList<RecoupApplyDetailExtend>();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("保存报销申请成功", "保存报销申请成功！"));
			}
		} catch (ItemSelectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请选择项目！", "请选择项目！"));
		} catch (DetailAddException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请添加明细！", "请添加明细！"));
		} catch (SuperClassSelectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请选择一级费用类别！", "请选择一级费用类别！"));
		} catch (ClassSelectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请选择二级费用类别！", "请选择二级费用类别！"));
		} catch (PayWaySelectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请选择支付方式！", "请选择支付方式！"));
		}
		
	}
	
	/**
	 * 
		* @Title: commitRecoup 
		* @Description: TODO
		* @param 
		* @return void
		* @throws 
		* @author Justin.Su
		* @date 2014-7-12 上午09:02:20
		* @version V1.0
	 */
	public void commitRecoup(){
		System.out.println("调用提交！");
		try {
			if(recoupApplyService.checkValue(recordForAdd, detailListForAdd)){
				recoupApplyService.insertApplyRecordAndDetail(recordForAdd, detailListForAdd,2);
				//初始化
				getRecordDefaultValue();
				searchRecoupBy();
				detailListForAdd = new ArrayList<RecoupApplyDetailExtend>();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("提交报销申请成功", "提交报销申请成功！"));
			}
		}catch (ItemSelectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请选择项目！", "请选择项目！"));
		} catch (DetailAddException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请添加明细！", "请添加明细！"));
		} catch (SuperClassSelectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请选择一级费用类别！", "请选择一级费用类别！"));
		} catch (ClassSelectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请选择二级费用类别！", "请选择二级费用类别！"));
		} catch (PayWaySelectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请选择支付方式！", "请选择支付方式！"));
		}
		
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
	
	public void onRowSelect(SelectEvent e){
		
	}
	
	public void onRowUnSelect(UnselectEvent e){
		
	}
	
	/**
	 * 
		* @Title: getType2List 
		* @Description: TODO
		* @param 
		* @return void
		* @throws 
		* @author Justin.Su
		* @date 2014-7-12 上午09:02:32
		* @version V1.0
	 */
	public void getType2List(){
		if(recordForAdd.getExpTypeCodeP().equals("0")){
			costclasses2 = recoupApplyService.selectAllpayItem("RC001",(long)2);
		}else{
			costclasses2 = recoupApplyService.selectAllpayItemBy(recordForAdd.getExpTypeCodeP());
		}
	}
	
	/**
	 * 
		* @Title: onRowEdit 
		* @Description: TODO
		* @param @param event
		* @return void
		* @throws 
		* @author Justin.Su
		* @date 2014-7-12 上午09:02:36
		* @version V1.0
	 */
	public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edited", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
	/**
	 * 
		* @Title: onRowCancel 
		* @Description: TODO
		* @param @param event
		* @return void
		* @throws 
		* @author Justin.Su
		* @date 2014-7-12 上午09:02:40
		* @version V1.0
	 */
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    /**
     * 
    	* @Title: onCellEdit 
    	* @Description: TODO
    	* @param @param event
    	* @return void
    	* @throws 
    	* @author Justin.Su
    	* @date 2014-7-12 上午09:02:45
    	* @version V1.0
     */
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
     * 
    	* @Title: checkCancel 
    	* @Description: TODO
    	* @param 
    	* @return void
    	* @throws 
    	* @author Justin.Su
    	* @date 2014-7-12 下午04:57:09
    	* @version V1.0
     */
    public void checkCancel(){
    	RequestContext request = RequestContext.getCurrentInstance();
    	if(selectedRecord == null){
    		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "请选择一条记录！", "请选择一条记录！");
    		 FacesContext.getCurrentInstance().addMessage(null, msg);
    		 request.addCallbackParam("cancelFlag", false);
    	}else if(selectedRecord.getState() == 4){
    	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "审批已经完成，无法撤销！", "审批已经完成，无法撤销！");
   		 FacesContext.getCurrentInstance().addMessage(null, msg);
   		request.addCallbackParam("cancelFlag", false);
    	}
    	else{
    		request.addCallbackParam("cancelFlag", true);
    	}
    }
    
    /**
     * 
    	* @Title: checkSubmit 
    	* @Description: TODO
    	* @param 
    	* @return void
    	* @throws 
    	* @author Justin.Su
    	* @date 2014-7-12 下午04:57:13
    	* @version V1.0
     */
    public void checkSubmit(){
    	RequestContext request = RequestContext.getCurrentInstance();
    	if(selectedRecord == null){
    		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "请选择一条记录！", "请选择一条记录！");
    		 FacesContext.getCurrentInstance().addMessage(null, msg);
    		 request.addCallbackParam("submitFlag", false);
    	}else if(selectedRecord.getState() != 1){
    		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "非保存状态的记录，无法提交！", "非保存状态的记录，无法提交！！");
      		 FacesContext.getCurrentInstance().addMessage(null, msg);
   		request.addCallbackParam("submitFlag", false);
    	}
    	else{
    		request.addCallbackParam("submitFlag", true);
    	}
    }
    
    /**
     * 
    	* @Title: checkView 
    	* @Description: TODO
    	* @param 
    	* @return void
    	* @throws 
    	* @author Justin.Su
    	* @date 2014-7-12 下午04:58:55
    	* @version V1.0
     */
    public void checkView(){
    	RequestContext request = RequestContext.getCurrentInstance();
    	if(selectedRecord == null){
    		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "请选择一条记录！", "请选择一条记录！");
    		 FacesContext.getCurrentInstance().addMessage(null, msg);
    		 request.addCallbackParam("viewFlag", false);
    	}
    	else{
    		request.addCallbackParam("viewFlag", true);
    		recordForAdd = selectedRecord;
    		
    	}
    }
    
    /**
     * 
    	* @Title: cancelRecoup 
    	* @Description: TODO
    	* @param 
    	* @return void
    	* @throws 
    	* @author Justin.Su
    	* @date 2014-7-12 下午03:43:47
    	* @version V1.0
     */
    public void cancelRecoup(){
		recoupApplyService.deleteRecoupRecord(selectedRecord);
		searchRecoupBy();
    }
    
    /**
     * 
    	* @Title: submitRecoup 
    	* @Description: TODO
    	* @param 
    	* @return void
    	* @throws 
    	* @author Justin.Su
    	* @date 2014-7-12 下午04:08:39
    	* @version V1.0
     */
    public void submitRecoup(){
		recoupApplyService.updateRecoupRecord(selectedRecord);
		searchRecoupBy();
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

		  
		    /**
			 * @param projectCode the projectCode to set
			 */  
		    
		public void setProjectCode(String projectCode) {
			this.projectCode = projectCode;
		}

		  
			/**
			 * @return the projectCode
			 */  
		    
		public String getProjectCode() {
			return projectCode;
		}

		

				  
				    /**
					 * @param applyDateStart the applyDateStart to set
					 */  
				    
				public void setApplyDateStart(Date applyDateStart) {
					this.applyDateStart = applyDateStart;
				}

				  
					/**
					 * @return the applyDateStart
					 */  
				    
				public Date getApplyDateStart() {
					return applyDateStart;
				}

					  
					    /**
						 * @param applyDateEnd the applyDateEnd to set
						 */  
					    
					public void setApplyDateEnd(Date applyDateEnd) {
						this.applyDateEnd = applyDateEnd;
					}

					  
						/**
						 * @return the applyDateEnd
						 */  
					    
					public Date getApplyDateEnd() {
						return applyDateEnd;
					}

						  
						    /**
							 * @param payState the payState to set
							 */  
						    
						public void setPayState(Integer payState) {
							this.payState = payState;
						}

						  
							/**
							 * @return the payState
							 */  
						    
						public Integer getPayState() {
							return payState;
						}

							  
							    /**
								 * @param selectedRecord the selectedRecord to set
								 */  
							    
							public void setSelectedRecord(RecoupApplyRecordExtend selectedRecord) {
								this.selectedRecord = selectedRecord;
							}

							  
								/**
								 * @return the selectedRecord
								 */  
							    
							public RecoupApplyRecordExtend getSelectedRecord() {
								return selectedRecord;
							}


	
}
