/**
 * 
 */
package controller.recoup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import service.recoup.RecoupApplyService;

import domain.recoup.RecoupApplyDetailExtend;
import domain.recoup.RecoupApplyRecordExtend;

/**
 * @author justin
 *
 */

public class RecoupApplyController implements Serializable{
	
	
	  
		/** 
		* @Fields serialVersionUID : TODO
		* @version V1.0
		*/   
	    
	private static final long serialVersionUID = 100000000000001L;
	
	private transient RecoupApplyService recoupApplyService;

	private RecoupApplyRecordExtend recordForAdd = new RecoupApplyRecordExtend();

	private List<RecoupApplyDetailExtend> detailListForAdd = new ArrayList<RecoupApplyDetailExtend>();
	
	private RecoupApplyDetailExtend detailForAdd = new RecoupApplyDetailExtend();
	
	
	
	public void addDetailList(){
		if(detailForAdd.equals(null) || detailForAdd.equals("")){
			
		}else{
			RecoupApplyDetailExtend detail = new RecoupApplyDetailExtend();
			detail = detailForAdd;
			detailListForAdd.add(detail);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"明细添加成功！" , "明细添加成功！"));
		}
		
	}

	  
		/**
		 * @return the recordForAdd
		 */  
	    
	public RecoupApplyRecordExtend getRecordForAdd() {
		return recordForAdd;
	}



	  
	    /**
		 * @param recordForAdd the recordForAdd to set
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
			 * @param detailListForAdd the detailListForAdd to set
			 */  
		    
		public void setDetailListForAdd(List<RecoupApplyDetailExtend> detailListForAdd) {
			this.detailListForAdd = detailListForAdd;
		}




			  
				/**
				 * @return the detailForAdd
				 */  
			    
			public RecoupApplyDetailExtend getDetailForAdd() {
				return detailForAdd;
			}




			  
			    /**
				 * @param detailForAdd the detailForAdd to set
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
					 * @param recoupApplyService the recoupApplyService to set
					 */  
				    
				public void setRecoupApplyService(RecoupApplyService recoupApplyService) {
					this.recoupApplyService = recoupApplyService;
				}




}
