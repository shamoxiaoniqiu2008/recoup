  
	/**  
	* @Title: RecoupApplyDetailExtend.java 
	* @Package domain 
	* @Description: TODO
	* @author Justin.Su
	* @date 2014-6-30 上午11:08:18 
	* @version V1.0
	* @Copyright: Copyright (c) Centling Co.Ltd. 2014
	* ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
	*/ 
    
package domain.recoup;

import java.util.Date;

  
	/** 
 * @ClassName: RecoupApplyDetailExtend 
 * @Description: TODO
 * @author Justin.Su
 * @date 2014-6-30 上午11:08:18
 * @version V1.0 
 */

public class RecoupApplyDetailExtend extends RecoupApplyDetail{
			/** 
			* @Fields serialVersionUID : TODO
			* @version V1.0
			*/   
		    
		private static final long serialVersionUID = 10L;
		 
		private Date feeDate;

		  
		    /**
			 * @param feeDate the feeDate to set
			 */  
		    
		public void setFeeDate(Date feeDate) {
			this.feeDate = feeDate;
		}

		  
			/**
			 * @return the feeDate
			 */  
		    
		public Date getFeeDate() {
			return feeDate;
		}

}
