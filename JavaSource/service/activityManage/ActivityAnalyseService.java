/**  
 * @Title: ActivityAnalyseService.java 
 * @Package service.activityManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-13 下午1:23:51 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package service.activityManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import controller.activityManage.ExtendForActivityAnalyse;
import domain.activityManage.PensionActivity;
import domain.activityManage.PensionAttendolder;
import domain.activityManage.PensionAttendolderExample;

import persistence.activityManage.PensionActivityMapper;
import persistence.activityManage.PensionAttendolderMapper;
import persistence.olderManage.PensionOlderMapper;

/**
 * @ClassName: ActivityAnalyseService
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-13 下午1:23:51
 * @version V1.0
 */
@Service
public class ActivityAnalyseService {

	@Autowired
	private PensionAttendolderMapper pensionAttendolderMapper;
	@Autowired
	private PensionActivityMapper pensionActivityMapper;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;

	/**
	 * 
	 * @Title: getExtendForActivityAnalyseList
	 * @Description: TODO
	 * @param @return
	 * @return List<ExtendForActivityAnalyse>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 下午3:43:34
	 * @version V1.0
	 */
	public List<ExtendForActivityAnalyse> getExtendForActivityAnalyseList() {
		return pensionAttendolderMapper.selectSumQty();
	}

	/**
	 * 
	 * @Title: searchDetailList
	 * @Description: TODO
	 * @param @param selectedExtend
	 * @param @return
	 * @return List<PensionAttendolder>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 下午3:43:38
	 * @version V1.0
	 */
	public List<PensionAttendolder> searchDetailList(
			ExtendForActivityAnalyse selectedExtend) {
		List<PensionAttendolder> tempList = new ArrayList<PensionAttendolder>();
		PensionAttendolderExample pae = new PensionAttendolderExample();
		pae.or().andActivityIdEqualTo(selectedExtend.getActivityId());
		tempList = pensionAttendolderMapper.selectByExample(pae);

		if (tempList.size() > 0) {
			for (PensionAttendolder pa : tempList) {
				pa.setItemName(pensionActivityMapper.selectByPrimaryKey(
						pa.getActivityId()).getItemname());
				pa.setOlderName(pensionOlderMapper.selectByPrimaryKey(
						pa.getOlderId()).getName());
				if (pa.getIsattend() == null) {
					pa.setAddCanOrNotName("未知");
				} else if (pa.getIsattend() == 1) {
					pa.setAddCanOrNotName("能");
				} else {
					pa.setAddCanOrNotName("否");
				}

				if (pa.getAttended() == null) {
					pa.setAddIsOrNotName("未知");
				} else if (pa.getAttended() == 1) {
					pa.setAddIsOrNotName("是");
				} else {
					pa.setAddIsOrNotName("否");
				}
				if(pa.getAppraise() == null){
					pa.setLikeStr("未评价");
				}
				else if(pa.getAppraise() >= 3){
					pa.setLikeStr("满意");
				}
				else{
					pa.setLikeStr("不满意");
				}
				
				if (pa.getCheckresult() == null) {
					pa.setCheckResultName("未评价");
				} else if (pa.getCheckresult() == 1) {
					pa.setCheckResultName("满意");
				} else {
					pa.setCheckResultName("不满意");
				}
				pa.setStartDate(pensionActivityMapper.selectByPrimaryKey(pa.getActivityId()).getStarttime());
				pa.setItemplace(pensionActivityMapper.selectByPrimaryKey(pa.getActivityId()).getItemplace());
			}
		}
		return tempList;
	}

	/**
	 * 
	 * @Title: getExtendForActivityAnalyseListByCondition
	 * @Description: TODO
	 * @param @param itemId
	 * @param @param startDate
	 * @param @param endDate
	 * @param @param attendCanOrNotFlag
	 * @param @param attendIsOrNotFlag
	 * @param @return
	 * @return List<ExtendForActivityAnalyse>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 下午4:52:43
	 * @version V1.0
	 */
	public List<ExtendForActivityAnalyse> getExtendForActivityAnalyseListByCondition(
			Long itemId, Date startDate, Date endDate,
			Integer appraise) {
		List<ExtendForActivityAnalyse> tempExtendForActivityAnalyseList = null;
		tempExtendForActivityAnalyseList = pensionAttendolderMapper
				.selectSumQtyByCondition(itemId, startDate, endDate,
						appraise);
		return tempExtendForActivityAnalyseList;
	}

	/**
	 * 根据评价信息更新康娱项目
	 * @param activity
	 */
	public void saveActivityAnalyse(PensionActivity activity) {
		pensionActivityMapper.updateByPrimaryKeySelective(activity);
		
	}

}
