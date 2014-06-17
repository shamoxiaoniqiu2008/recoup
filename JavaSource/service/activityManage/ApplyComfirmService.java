/**  
* @Title: ApplyComfirmService.java 
* @Package service.activityManage 
* @Description: TODO
* @author Justin.Su
* @date 2013-11-12 下午2:54:36 
* @version V1.0
* @Copyright: Copyright (c) Centling Co.Ltd. 2013
* ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
*/ 
package service.activityManage;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.activityManage.PensionAttendolderMapper;

import domain.activityManage.PensionAttendolder;
import domain.activityManage.PensionAttendolderExample;

/** 
 * @ClassName: ApplyComfirmService 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午2:54:36
 * @version V1.0 
 */
@Service
public class ApplyComfirmService {

	@Autowired
	private PensionAttendolderMapper pensionAttendolderMapper;

	/**
	 * 
	* @Title: searchAllAttendInfo 
	* @Description: TODO
	* @param @return
	* @return List<PensionAttendolder>
	* @throws 
	* @author Justin.Su
	* @date 2013-11-13 下午2:22:28
	* @version V1.0
	 *//*
	public List<PensionAttendolder> searchAllAttendInfo(){
		return pensionAttendolderMapper.selectAttendOlderInfo(null, null, null, null);
	}*/
	
	/**
	 * 
	* @Title: searchAttendInfo 
	* @Description: TODO
	* @param @param itemId
	* @param @param olderId
	* @param @param startDate
	* @param @param endDate
	* @param @return
	* @return List<PensionAttendolder>
	* @throws 
	* @author Justin.Su
	* @date 2013-11-13 下午2:40:35
	* @version V1.0
	 * @param attended 是否签到
	 */
	public List<PensionAttendolder> searchAttendInfo(Long itemId, Long olderId,Date startDate,Date endDate, Integer attended ){
		return pensionAttendolderMapper.selectAttendOlderInfo(itemId, olderId, startDate, endDate,attended);
	}
	
	/**
	 * 
	* @Title: updatePensionAttendolder 
	* @Description: TODO
	* @param @param pensionAttendolder
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-11-14 下午1:25:41
	* @version V1.0
	 */
	public void updatePensionAttendolder(PensionAttendolder pensionAttendolder){
		PensionAttendolder record = new PensionAttendolder();
		PensionAttendolderExample example = new PensionAttendolderExample();
		record.setIsattend(pensionAttendolder.getIsattend());
		record.setReason(pensionAttendolder.getReason());
		record.setNotes(pensionAttendolder.getNotes());
		record.setAttended(pensionAttendolder.getAttended());
		example.or().andIdEqualTo(pensionAttendolder.getId());
		pensionAttendolderMapper.updateByExampleSelective(record, example);
	}
}
