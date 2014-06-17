/**  
 * @Title: ActivitySignService.java 
 * @Package service.activityManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-13 下午1:17:16 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package service.activityManage;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.activityManage.PensionActivityMapper;
import persistence.activityManage.PensionAttendolderMapper;
import persistence.olderManage.PensionOlderMapper;
import util.PmsException;
import domain.activityManage.PensionActivity;
import domain.activityManage.PensionAttendolder;
import domain.activityManage.PensionAttendolderExample;

/**
 * @ClassName: ActivitySignService
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-13 下午1:17:16
 * @version V1.0
 */
@Service
public class ActivitySignService {

	@Autowired
	private PensionAttendolderMapper pensionAttendolderMapper;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;
	@Autowired
	private PensionActivityMapper pensionActivityMapper;

	/**
	 * 
	 * @Title: selectAllPensionAttendolderList
	 * @Description: TODO
	 * @param @return
	 * @return List<PensionAttendolder>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 上午10:05:45
	 * @version V1.0
	 */
	public List<PensionAttendolder> selectAllPensionAttendolderList() {
		List<PensionAttendolder> tempPensionAttendolderList = null;
		tempPensionAttendolderList = pensionAttendolderMapper
				.selectByExample(null);
		if (tempPensionAttendolderList.size() > 0) {
			for (PensionAttendolder pa : tempPensionAttendolderList) {
				pa.setItemName(pensionActivityMapper.selectByPrimaryKey(
						pa.getActivityId()).getItemname());
				pa.setOlderName(pensionOlderMapper.selectByPrimaryKey(
						pa.getOlderId()).getName());
				if (pa.getIsattend() == null) {
					pa.setAddCanOrNotName("");
				} else if (pa.getIsattend() == 1) {
					pa.setAddCanOrNotName("能");
				} else {
					pa.setAddCanOrNotName("否");
				}

				if (pa.getAttended() == null) {
					pa.setAddIsOrNotName("");
				} else if (pa.getAttended() == 1) {
					pa.setAddIsOrNotName("是");
				} else {
					pa.setAddIsOrNotName("否");
				}

				if (pa.getCheckresult() == null) {
					pa.setCheckResultName("");
				} else if (pa.getCheckresult() == 1) {
					pa.setCheckResultName("满意");
				} else {
					pa.setCheckResultName("不满意");
				}
				pa.setStartDate(pensionActivityMapper.selectByPrimaryKey(
						pa.getActivityId()).getStarttime());
				pa.setItemplace(pensionActivityMapper.selectByPrimaryKey(
						pa.getActivityId()).getItemplace());
			}
		}
		return tempPensionAttendolderList;
	}

	/**
	 * 
	 * @Title: selectPensionAttendolderListByCondition
	 * @Description: TODO
	 * @param @param olderId
	 * @param @param itemId
	 * @param @param attendCanOrNotFlag
	 * @param @param attendIsOrNotFlag
	 * @param @param startDate
	 * @param @param endDate
	 * @param @return
	 * @return List<PensionAttendolder>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 上午10:38:25
	 * @version V1.0
	 */
	public List<PensionAttendolder> selectPensionAttendolderListByCondition(
			Long olderId, Long itemId, Long attendIsOrNotFlag, Date startDate,
			Date endDate) {
		List<PensionAttendolder> returnList = null;
		PensionAttendolderExample pael = new PensionAttendolderExample();
		// if(attendCanOrNotFlag == 3 && attendIsOrNotFlag == 3){
		if (attendIsOrNotFlag == 3) {
			if (startDate == null && endDate == null) {
				if (olderId == null && itemId == null) {

				} else if (olderId != null && itemId == null) {
					pael.or().andOlderIdEqualTo(olderId);
				} else if (olderId == null && itemId != null) {
					pael.or().andActivityIdEqualTo(itemId);
				} else {
					pael.or().andActivityIdEqualTo(itemId)
							.andOlderIdEqualTo(olderId);
				}

			} else if (startDate != null && endDate == null) {
				if (olderId == null && itemId == null) {
					pael.or().andEnlisttimeGreaterThanOrEqualTo(startDate);
				} else if (olderId != null && itemId == null) {
					pael.or().andOlderIdEqualTo(olderId)
							.andEnlisttimeGreaterThanOrEqualTo(startDate);
				} else if (olderId == null && itemId != null) {
					pael.or().andActivityIdEqualTo(itemId)
							.andEnlisttimeGreaterThanOrEqualTo(startDate);
				} else {
					pael.or().andActivityIdEqualTo(itemId)
							.andOlderIdEqualTo(olderId)
							.andEnlisttimeGreaterThanOrEqualTo(startDate);
				}
			} else if (startDate == null && endDate != null) {
				if (olderId == null && itemId == null) {
					pael.or().andEnlisttimeLessThanOrEqualTo(endDate);
				} else if (olderId != null && itemId == null) {
					pael.or().andOlderIdEqualTo(olderId)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				} else if (olderId == null && itemId != null) {
					pael.or().andActivityIdEqualTo(itemId)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				} else {
					pael.or().andActivityIdEqualTo(itemId)
							.andOlderIdEqualTo(olderId)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				}
			} else {
				if (olderId == null && itemId == null) {
					pael.or().andEnlisttimeGreaterThanOrEqualTo(startDate)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				} else if (olderId != null && itemId == null) {
					pael.or().andOlderIdEqualTo(olderId)
							.andEnlisttimeGreaterThanOrEqualTo(startDate)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				} else if (olderId == null && itemId != null) {
					pael.or().andActivityIdEqualTo(itemId)
							.andEnlisttimeGreaterThanOrEqualTo(startDate)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				} else {
					pael.or().andActivityIdEqualTo(itemId)
							.andOlderIdEqualTo(olderId)
							.andEnlisttimeGreaterThanOrEqualTo(startDate)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				}
			}
		}
		// else if(attendCanOrNotFlag == 3 && attendIsOrNotFlag == 2){
		else if (attendIsOrNotFlag == 2) {
			if (startDate == null && endDate == null) {
				if (olderId == null && itemId == null) {
					pael.or().andAttendedEqualTo(2);
				} else if (olderId != null && itemId == null) {
					pael.or().andOlderIdEqualTo(olderId).andAttendedEqualTo(2);
				} else if (olderId == null && itemId != null) {
					pael.or().andActivityIdEqualTo(itemId)
							.andAttendedEqualTo(2);
				} else {
					pael.or().andActivityIdEqualTo(itemId)
							.andOlderIdEqualTo(olderId).andAttendedEqualTo(2);
				}

			} else if (startDate != null && endDate == null) {
				if (olderId == null && itemId == null) {
					pael.or().andAttendedEqualTo(2)
							.andEnlisttimeGreaterThanOrEqualTo(startDate);
				} else if (olderId != null && itemId == null) {
					pael.or().andOlderIdEqualTo(olderId).andAttendedEqualTo(2)
							.andEnlisttimeGreaterThanOrEqualTo(startDate);
				} else if (olderId == null && itemId != null) {
					pael.or().andActivityIdEqualTo(itemId)
							.andAttendedEqualTo(2)
							.andEnlisttimeGreaterThanOrEqualTo(startDate);
				} else {
					pael.or().andActivityIdEqualTo(itemId)
							.andOlderIdEqualTo(olderId).andAttendedEqualTo(2)
							.andEnlisttimeGreaterThanOrEqualTo(startDate);
				}

			} else if (startDate == null && endDate != null) {
				if (olderId == null && itemId == null) {
					pael.or().andAttendedEqualTo(2)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				} else if (olderId != null && itemId == null) {
					pael.or().andOlderIdEqualTo(olderId).andAttendedEqualTo(2)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				} else if (olderId == null && itemId != null) {
					pael.or().andActivityIdEqualTo(itemId)
							.andAttendedEqualTo(2)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				} else {
					pael.or().andActivityIdEqualTo(itemId)
							.andOlderIdEqualTo(olderId).andAttendedEqualTo(2)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				}
			} else {
				if (olderId == null && itemId == null) {
					pael.or().andAttendedEqualTo(2)
							.andEnlisttimeGreaterThanOrEqualTo(startDate)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				} else if (olderId != null && itemId == null) {
					pael.or().andOlderIdEqualTo(olderId).andAttendedEqualTo(2)
							.andEnlisttimeGreaterThanOrEqualTo(startDate)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				} else if (olderId == null && itemId != null) {
					pael.or().andActivityIdEqualTo(itemId)
							.andAttendedEqualTo(2)
							.andEnlisttimeGreaterThanOrEqualTo(startDate)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				} else {
					pael.or().andActivityIdEqualTo(itemId)
							.andOlderIdEqualTo(olderId).andAttendedEqualTo(2)
							.andEnlisttimeGreaterThanOrEqualTo(startDate)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				}
			}
		} else if (attendIsOrNotFlag == 1) {
			// else if(attendCanOrNotFlag == 3 && attendIsOrNotFlag == 1){
			if (startDate == null && endDate == null) {
				if (olderId == null && itemId == null) {
					pael.or().andAttendedEqualTo(1);
				} else if (olderId != null && itemId == null) {
					pael.or().andOlderIdEqualTo(olderId).andAttendedEqualTo(1);
				} else if (olderId == null && itemId != null) {
					pael.or().andActivityIdEqualTo(itemId)
							.andAttendedEqualTo(1);
				} else {
					pael.or().andActivityIdEqualTo(itemId)
							.andOlderIdEqualTo(olderId).andAttendedEqualTo(1);
				}
			} else if (startDate != null && endDate == null) {
				if (olderId == null && itemId == null) {
					pael.or().andAttendedEqualTo(1)
							.andEnlisttimeGreaterThanOrEqualTo(startDate);
				} else if (olderId != null && itemId == null) {
					pael.or().andOlderIdEqualTo(olderId).andAttendedEqualTo(1)
							.andEnlisttimeGreaterThanOrEqualTo(startDate);
				} else if (olderId == null && itemId != null) {
					pael.or().andActivityIdEqualTo(itemId)
							.andAttendedEqualTo(1)
							.andEnlisttimeGreaterThanOrEqualTo(startDate);
				} else {
					pael.or().andActivityIdEqualTo(itemId)
							.andOlderIdEqualTo(olderId).andAttendedEqualTo(1)
							.andEnlisttimeGreaterThanOrEqualTo(startDate);
				}
			} else if (startDate == null && endDate != null) {
				if (olderId == null && itemId == null) {
					pael.or().andAttendedEqualTo(1)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				} else if (olderId != null && itemId == null) {
					pael.or().andOlderIdEqualTo(olderId).andAttendedEqualTo(1)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				} else if (olderId == null && itemId != null) {
					pael.or().andActivityIdEqualTo(itemId)
							.andAttendedEqualTo(1)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				} else {
					pael.or().andActivityIdEqualTo(itemId)
							.andOlderIdEqualTo(olderId).andAttendedEqualTo(1)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				}
			} else {
				if (olderId == null && itemId == null) {
					pael.or().andAttendedEqualTo(1)
							.andEnlisttimeGreaterThanOrEqualTo(startDate)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				} else if (olderId != null && itemId == null) {
					pael.or().andOlderIdEqualTo(olderId).andAttendedEqualTo(1)
							.andEnlisttimeGreaterThanOrEqualTo(startDate)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				} else if (olderId == null && itemId != null) {
					pael.or().andActivityIdEqualTo(itemId)
							.andAttendedEqualTo(1)
							.andEnlisttimeGreaterThanOrEqualTo(startDate)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				} else {
					pael.or().andActivityIdEqualTo(itemId)
							.andOlderIdEqualTo(olderId).andAttendedEqualTo(1)
							.andEnlisttimeGreaterThanOrEqualTo(startDate)
							.andEnlisttimeLessThanOrEqualTo(endDate);
				}
			}
		}
		// else if(attendCanOrNotFlag == 2 && attendIsOrNotFlag == 3){
		// if(startDate == null && endDate == null){
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(2);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(2);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(2);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(2);
		// }
		// }else if(startDate != null && endDate == null){
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(2).andEnlisttimeGreaterThan(startDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andEnlisttimeGreaterThan(startDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(2).andEnlisttimeGreaterThan(startDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andEnlisttimeGreaterThan(startDate);
		// }
		// }else if(startDate == null && endDate != null){
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(2).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(2).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andEnlisttimeLessThanOrEqualTo(endDate);
		// }
		// }else{
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }
		// }
		//
		// }
		// else if(attendCanOrNotFlag == 2 && attendIsOrNotFlag == 2){
		// if(startDate == null && endDate == null){
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(2).andAttendedEqualTo(2);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andAttendedEqualTo(2);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(2).andAttendedEqualTo(2);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andAttendedEqualTo(2);
		// }
		// }else if(startDate != null && endDate == null){
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(2).andAttendedEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andAttendedEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(2).andAttendedEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andAttendedEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }
		// }else if(startDate == null && endDate != null){
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(2).andAttendedEqualTo(2).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andAttendedEqualTo(2).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(2).andAttendedEqualTo(2).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andAttendedEqualTo(2).andEnlisttimeLessThanOrEqualTo(endDate);
		// }
		// }else{
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(2).andAttendedEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andAttendedEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(2).andAttendedEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andAttendedEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }
		// }
		// }
		// else if(attendCanOrNotFlag == 2 && attendIsOrNotFlag == 1){
		// if(startDate == null && endDate == null){
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(2).andAttendedEqualTo(1);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andAttendedEqualTo(1);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(2).andAttendedEqualTo(1);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andAttendedEqualTo(1);
		// }
		// }else if(startDate != null && endDate == null){
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(2).andAttendedEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andAttendedEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(2).andAttendedEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andAttendedEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }
		// }else if(startDate == null && endDate != null){
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(2).andAttendedEqualTo(1).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andAttendedEqualTo(1).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(2).andAttendedEqualTo(1).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andAttendedEqualTo(1).andEnlisttimeLessThanOrEqualTo(endDate);
		// }
		// }else{
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(2).andAttendedEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andAttendedEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(2).andAttendedEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(2).andAttendedEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }
		// }
		// }else if(attendCanOrNotFlag == 1 && attendIsOrNotFlag == 3){
		// if(startDate == null && endDate == null){
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(1);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(1);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(1);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(1);
		// }
		// }else if(startDate != null && endDate == null){
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }
		// }else if(startDate == null && endDate != null){
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(1).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(1).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andEnlisttimeLessThanOrEqualTo(endDate);
		// }
		// }else{
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }
		// }
		// }else if(attendCanOrNotFlag == 1 && attendIsOrNotFlag == 2){
		// if(startDate == null && endDate == null){
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(1).andAttendedEqualTo(2);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andAttendedEqualTo(2);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(1).andAttendedEqualTo(2);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andAttendedEqualTo(2);
		// }
		// }else if(startDate != null && endDate == null){
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(1).andAttendedEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andAttendedEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(1).andAttendedEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andAttendedEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }
		// }else if(startDate == null && endDate != null){
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(1).andAttendedEqualTo(2).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andAttendedEqualTo(2).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(1).andAttendedEqualTo(2).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andAttendedEqualTo(2).andEnlisttimeLessThanOrEqualTo(endDate);
		// }
		// }else{
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(1).andAttendedEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andAttendedEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(1).andAttendedEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andAttendedEqualTo(2).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }
		// }
		// }else if(attendCanOrNotFlag == 1 && attendIsOrNotFlag == 1){
		// if(startDate == null && endDate == null){
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(1).andAttendedEqualTo(1);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andAttendedEqualTo(1);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(1).andAttendedEqualTo(1);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andAttendedEqualTo(1);
		// }
		// }else if(startDate != null && endDate == null){
		//
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(1).andAttendedEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andAttendedEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(1).andAttendedEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andAttendedEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate);
		// }
		// }else if(startDate == null && endDate != null){
		//
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(1).andAttendedEqualTo(1).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andAttendedEqualTo(1).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(1).andAttendedEqualTo(1).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andAttendedEqualTo(1).andEnlisttimeLessThanOrEqualTo(endDate);
		// }
		// }else{
		//
		// if(olderId == null && itemId == null){
		// pael.or().andIsattendEqualTo(1).andAttendedEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId != null && itemId == null){
		// pael.or().andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andAttendedEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else if(olderId == null && itemId != null){
		// pael.or().andActivityIdEqualTo(itemId).andIsattendEqualTo(1).andAttendedEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }else{
		// pael.or().andActivityIdEqualTo(itemId).andOlderIdEqualTo(olderId).andIsattendEqualTo(1).andAttendedEqualTo(1).andEnlisttimeGreaterThanOrEqualTo(startDate).andEnlisttimeLessThanOrEqualTo(endDate);
		// }
		// }
		// }
		returnList = pensionAttendolderMapper.selectByCondition(pael);
		if (returnList.size() > 0) {
			for (PensionAttendolder pa : returnList) {
				pa.setItemName(pensionActivityMapper.selectByPrimaryKey(
						pa.getActivityId()).getItemname());
				pa.setOlderName(pensionOlderMapper.selectByPrimaryKey(
						pa.getOlderId()).getName());
				if (pa.getIsattend() == null) {
					pa.setAddCanOrNotName("");
				} else if (pa.getIsattend() == 1) {
					pa.setAddCanOrNotName("能");
				} else {
					pa.setAddCanOrNotName("否");
				}

				if (pa.getAttended() == null) {
					pa.setAddIsOrNotName("");
				} else if (pa.getAttended() == 1) {
					pa.setAddIsOrNotName("是");
				} else {
					pa.setAddIsOrNotName("否");
				}

				if (pa.getCheckresult() == null) {
					pa.setCheckResultName("");
				} else if (pa.getCheckresult() == 1) {
					pa.setCheckResultName("满意");
				} else {
					pa.setCheckResultName("不满意");
				}
				pa.setStartDate(pensionActivityMapper.selectByPrimaryKey(
						pa.getActivityId()).getStarttime());
				pa.setItemplace(pensionActivityMapper.selectByPrimaryKey(
						pa.getActivityId()).getItemplace());
			}
		}
		return returnList;
	}

	/**
	 * 
	 * @Title: saveOlderAttend
	 * @Description: TODO
	 * @param @param pensionAttendolder
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 上午10:05:50
	 * @version V1.0
	 * @throws PmsException
	 */
	@Transactional
	public void saveOlderAttend(PensionAttendolder pensionAttendolder)
			throws PmsException {
		if (this.checkAttendOlder(pensionAttendolder.getActivityId(),
				pensionAttendolder.getOlderId())) {
			pensionAttendolder.setIsattend(1);
			pensionAttendolder.setAttended(2);
			// 设置报名时间为当前时间
			pensionAttendolder.setEnlisttime(new Date());
			pensionAttendolderMapper.insertSelective(pensionAttendolder);
		} else {
			throw new PmsException("该老人已报名，不能重复报名！");
		}
	}

	/**
	 * check 是否该老人已报名该项目 已报名，返回false 未报名，返回true
	 * 
	 * @param activityId
	 *            康娱活动编号
	 * @param olderId
	 *            老人编号
	 * @return add by mary.liu
	 */
	public boolean checkAttendOlder(Long activityId, Long olderId) {
		PensionAttendolderExample example = new PensionAttendolderExample();
		example.or().andActivityIdEqualTo(activityId)
				.andOlderIdEqualTo(olderId).andClearedEqualTo(2);
		List<PensionAttendolder> olderList = pensionAttendolderMapper
				.selectByExample(example);
		if (olderList.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @Title: getItemName
	 * @Description: TODO
	 * @param @param pensionAttendolder
	 * @param @return
	 * @return String
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 上午10:05:55
	 * @version V1.0
	 */
	public String getItemName(PensionAttendolder pensionAttendolder) {

		return pensionActivityMapper.selectByPrimaryKey(
				pensionAttendolder.getActivityId()).getItemname();
	}

	/**
	 * 
	 * @Title: getOlderName
	 * @Description: TODO
	 * @param @param pensionAttendolder
	 * @param @return
	 * @return String
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 上午10:05:59
	 * @version V1.0
	 */
	public String getOlderName(PensionAttendolder pensionAttendolder) {
		return pensionOlderMapper.selectByPrimaryKey(
				pensionAttendolder.getOlderId()).getName();
	}

	/**
	 * 
	 * @Title: updatePensionAttendolder
	 * @Description: TODO
	 * @param @param selectedpensionAttendolder
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 上午10:06:03
	 * @version V1.0
	 */
	@Transactional
	public void updatePensionAttendolder(
			PensionAttendolder editpensionAttendolder) {
		PensionAttendolderExample pae = new PensionAttendolderExample();
		pae.or().andIdEqualTo(editpensionAttendolder.getId());
		pensionAttendolderMapper.updateByExampleSelective(
				editpensionAttendolder, pae);
	}

	/**
	 * 
	 * @Title: deletePensionAttendolder
	 * @Description: TODO
	 * @param @param selectedpensionAttendolder
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 上午10:06:07
	 * @version V1.0
	 */
	@Transactional
	public void deletePensionAttendolder(
			PensionAttendolder selectedpensionAttendolder) {
		pensionAttendolderMapper.deleteByPrimaryKey(selectedpensionAttendolder
				.getId());
	}

	/**
	 * 
	 * @Title: getPensionActivity
	 * @Description: 获取康娱项目对象
	 * @param @param id
	 * @param @return
	 * @return PensionActivity
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-22 上午10:30:49
	 * @version V1.0
	 */
	public PensionActivity getPensionActivity(Long id) {
		return pensionActivityMapper.selectByPrimaryKey(id);
	}

	/**
	 * 
	 * @Title: updateActivityRecord
	 * @Description: TODO
	 * @param @param pensionAttendolder
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-10-10 下午5:09:18
	 * @version V1.0
	 */
	@Transactional
	public void updateActivityRecord(PensionAttendolder pensionAttendolder) {
		pensionAttendolderMapper.updateAttendolderRecord(pensionAttendolder
				.getActivityId());
	}

	/**
	 * 查找该老人是否报名了该活动
	 * 
	 * @param activityId
	 * @param olderId
	 * @return
	 */
	public List<PensionAttendolder> checkDuality(Long activityId, Long olderId) {
		PensionAttendolderExample example = new PensionAttendolderExample();
		example.or().andActivityIdEqualTo(activityId)
				.andOlderIdEqualTo(olderId).andClearedEqualTo(2);
		return pensionAttendolderMapper.selectByExample(example);
	}
}
