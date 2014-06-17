/**  
* @Title: ActivityCheckService.java 
* @Package service.activityManage 
* @Description: TODO
* @author Justin.Su
* @date 2013-11-12 下午2:55:36 
* @version V1.0
* @Copyright: Copyright (c) Centling Co.Ltd. 2013
* ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
*/ 
package service.activityManage;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centling.his.util.SessionManager;

import domain.activityManage.PensionAttendolder;
import domain.activityManage.PensionAttendolderExample;
import domain.employeeManage.PensionEmployee;

import persistence.activityManage.PensionAttendolderMapper;

/** 
 * @ClassName: ActivityCheckService 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午2:55:36
 * @version V1.0 
 */
@Service
public class ActivityCheckService {

	@Autowired
	private PensionAttendolderMapper pensionAttendolderMapper;
	
	/**
	 * 
	* @Title: searchAllAttendCheckInfo 
	* @Description: TODO
	* @param @return
	* @return List<PensionAttendolder>
	* @throws 
	* @author Justin.Su
	* @date 2013-11-14 下午1:47:25
	* @version V1.0
	 */
	public List<PensionAttendolder> searchAllAttendCheckInfo(){
		return pensionAttendolderMapper.selectAttendOlderCheckInfo(null, null, null, null);
	}
	
	/**
	 * 
	* @Title: searchAttendCheckInfo 
	* @Description: TODO
	* @param @param itemId
	* @param @param olderId
	* @param @param startDate
	* @param @param endDate
	* @param @return
	* @return List<PensionAttendolder>
	* @throws 
	* @author Justin.Su
	* @date 2013-11-14 下午1:55:50
	* @version V1.0
	 */
	public List<PensionAttendolder> searchAttendCheckInfo(Long itemId, Long olderId,Date startDate,Date endDate){
		return pensionAttendolderMapper.selectAttendOlderCheckInfo(itemId, olderId, startDate, endDate);
	}
	
	/**
	 * 
	* @Title: updatePensionAttendolderCheck 
	* @Description: TODO
	* @param @param pensionAttendolder
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-11-14 下午3:00:09
	* @version V1.0
	 */
	public void updatePensionAttendolderCheck(PensionAttendolder pensionAttendolder){
		PensionAttendolder record = new PensionAttendolder();
		PensionAttendolderExample example = new PensionAttendolderExample();
		record.setCheckresult(pensionAttendolder.getCheckresult());//质检结果
		record.setChecktime(new Date());//质检时间
		PensionEmployee pe = (PensionEmployee) SessionManager.getSessionAttribute(SessionManager.EMPLOYEE);
		record.setCheckerId(pe.getId());//质检人ID
		record.setCheckername(pe.getName());//modified by wensy
		record.setNotes(pensionAttendolder.getNotes());//备注
		example.or().andIdEqualTo(pensionAttendolder.getId());
		pensionAttendolderMapper.updateByExampleSelective(record, example);
	}
}
