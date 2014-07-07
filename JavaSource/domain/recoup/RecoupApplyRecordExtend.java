  
	/**  
	* @Title: RecoupApplyRecordExtend.java 
	* @Package domain.recoup 
	* @Description: TODO
	* @author Justin.Su
	* @date 2014-6-30 上午11:07:26 
	* @version V1.0
	* @Copyright: Copyright (c) Centling Co.Ltd. 2014
	* ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
	*/ 
    
package domain.recoup;

import java.util.Date;

  
	/** 
 * @ClassName: RecoupApplyRecordExtend 
 * @Description: TODO
 * @author Justin.Su
 * @date 2014-6-30 上午11:07:26
 * @version V1.0 
 */

public class RecoupApplyRecordExtend extends  RecoupApplyRecord{

		  
			/** 
			* @Fields serialVersionUID : TODO
			* @version V1.0
			*/   
	
		    
		private static final long serialVersionUID = 1000000001L;
		
		private String userName;
		private Date applyDateTime;
		
		/**
		 * @return the userName
		 */
		
		public String getUserName() {
			return userName;
		}

		/**
		 * @param userName the userName to set
		 */
		
		public void setUserName(String userName) {
			this.userName = userName;
		}

		  
		    /**
			 * @param applyDateTime the applyDateTime to set
			 */  
		    
		public void setApplyDateTime(Date applyDateTime) {
			this.applyDateTime = applyDateTime;
		}

		  
			/**
			 * @return the applyDateTime
			 */  
		    
		public Date getApplyDateTime() {
			return applyDateTime;
		}
		

}
