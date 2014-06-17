/**  
 * @Title: ProjectMaintainService.java 
 * @Package service.activityManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-13 上午10:21:08 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package service.activityManage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.activityManage.PensionActivityMapper;
import persistence.activityManage.PensionAttendolderMapper;
import persistence.dictionary.PensionDicClassMapper;
import persistence.dictionary.PensionDicSuperClassMapper;
import domain.activityManage.PensionActivity;
import domain.activityManage.PensionActivityExample;
import domain.activityManage.PensionAttendolder;
import domain.activityManage.PensionAttendolderExample;
import domain.dictionary.PensionDicClass;
import domain.dictionary.PensionDicSuperClass;

/**
 * @ClassName: ProjectMaintainService
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-13 上午10:21:08
 * @version V1.0
 */

@Service
public class ProjectMaintainService {

	@Autowired
	private PensionDicSuperClassMapper pensionDicSuperClassMapper;
	@Autowired
	private PensionDicClassMapper pensionDicClassMapper;
	@Autowired
	private PensionActivityMapper pensionActivityMapper;
	@Autowired
	private PensionAttendolderMapper pensionAttendolderMapper;

	/**
	 * 
	 * @Title: getAllActivitis
	 * @Description: TODO
	 * @param @return
	 * @return List<PensionActivity>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-11 下午1:55:36
	 * @version V1.0
	 */
	public List<PensionActivity> getAllActivitis() {
		List<PensionActivity> tempList = null;
		PensionActivityExample pae = new PensionActivityExample();
		pae.or().andClearedEqualTo(2);
		tempList = pensionActivityMapper.selectByExample(pae);
		for (PensionActivity pa : tempList) {
			pa.setSuperClassName(pensionDicSuperClassMapper.selectByPrimaryKey(
					pa.getSuperClassId()).getSuperClassName());
			pa.setClassName(pensionDicClassMapper.selectByPrimaryKey(
					pa.getClassId()).getClassName());
		}
		return tempList;
	}

	/**
	 * 
	 * @Title: getAllSuperClass
	 * @Description: TODO
	 * @param @return
	 * @return List<PensionDicSuperClass>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-11 下午2:23:55
	 * @version V1.0
	 */
	public List<PensionDicSuperClass> getAllSuperClass() {
		return pensionDicSuperClassMapper.selectByExample(null);
	}

	/**
	 * 
	 * @Title: getAllClass
	 * @Description: TODO
	 * @param @return
	 * @return List<PensionDicClass>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-11 下午2:23:59
	 * @version V1.0
	 */
	public List<PensionDicClass> getAllClass() {
		return pensionDicClassMapper.selectByExample(null);
	}

	/**
	 * 
	 * @Title: getAllActivitisByCondition
	 * @Description: TODO
	 * @param @param itemId
	 * @param @param superClassId
	 * @param @param classId
	 * @param @return
	 * @return List<PensionActivity>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-11 下午3:59:49
	 * @version V1.0
	 */
	public List<PensionActivity> getAllActivitisByCondition(Long itemId,
			Long superClassId, Long classId) {
		List<PensionActivity> tempList = null;
		tempList = pensionActivityMapper.selectByExampleExtend(itemId,
				superClassId, classId);
		if (tempList.size() > 0) {
			for (PensionActivity pa : tempList) {
				pa.setSuperClassName(pensionDicSuperClassMapper
						.selectByPrimaryKey(pa.getSuperClassId())
						.getSuperClassName());
				pa.setClassName(pensionDicClassMapper.selectByPrimaryKey(
						pa.getClassId()).getClassName());
			}
		}
		return tempList;
	}

	/**
	 * 
	 * @Title: getAllActivityName
	 * @Description: TODO
	 * @param @return
	 * @return List<String>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-11 下午5:49:25
	 * @version V1.0
	 */
	public List<String> getAllActivityName() {
		List<PensionActivity> allList = null;
		List<String> menuNameList = new ArrayList<String>();
		PensionActivityExample ex = new PensionActivityExample();
		ex.or().andClearedEqualTo(2);
		allList = pensionActivityMapper.selectByExample(ex);
		for (PensionActivity pf : allList) {
			menuNameList.add(pf.getItemname());
		}
		return menuNameList;
	}

	/**
	 * 
	 * @Title: getOtherActivityName
	 * @Description: TODO
	 * @param @param pa
	 * @param @return
	 * @return List<String>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-11 下午5:47:40
	 * @version V1.0
	 */
	public List<String> getOtherActivityName(PensionActivity pa) {
		List<PensionActivity> allList = null;
		List<String> menuNameList = new ArrayList<String>();
		PensionActivityExample pfe = new PensionActivityExample();
		pfe.or().andIdNotEqualTo(pa.getId()).andClearedEqualTo(2);
		allList = pensionActivityMapper.selectByExample(pfe);
		for (PensionActivity pay : allList) {
			menuNameList.add(pay.getItemname());
		}
		return menuNameList;
	}

	/**
	 * 
	 * @Title: insertActivityBySelective
	 * @Description: TODO
	 * @param @param pensionActivity
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-12 上午9:32:31
	 * @version V1.0
	 */
	public void insertActivityBySelective(PensionActivity pensionActivity) {
		pensionActivityMapper.insertSelective(pensionActivity);
	}

	/**
	 * 
	 * @Title: updateActivityBySelective
	 * @Description: TODO
	 * @param @param selectedPensionActivity
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-12 上午10:39:38
	 * @version V1.0
	 * @param remindFlag
	 *            更新通知标识
	 */
	public void updateActivityBySelective(
			PensionActivity selectedPensionActivity, boolean remindFlag) {
		PensionActivity record = new PensionActivity();
		PensionActivityExample example = new PensionActivityExample();
		record.setItemname(selectedPensionActivity.getItemname());
		record.setStarttime(selectedPensionActivity.getStarttime());
		record.setInputcode(selectedPensionActivity.getInputcode());
		record.setItemdesc(selectedPensionActivity.getItemdesc());
		record.setItemplace(selectedPensionActivity.getItemplace());
		record.setItemprepare(selectedPensionActivity.getItemprepare());
		record.setSuperClassId(selectedPensionActivity.getSuperClassId());
		record.setClassId(selectedPensionActivity.getClassId());
		record.setMinnumber(selectedPensionActivity.getMinnumber());
		record.setMaxnumber(selectedPensionActivity.getMaxnumber());
		record.setNotes(selectedPensionActivity.getNotes());
		record.setImageurl(selectedPensionActivity.getImageurl());
		example.or().andIdEqualTo(selectedPensionActivity.getId());
		pensionActivityMapper.updateByExampleSelective(record, example);
		PensionAttendolderExample example1 = new PensionAttendolderExample();
		example1.or().andActivityIdEqualTo(selectedPensionActivity.getId())
				.andClearedEqualTo(2);
		PensionAttendolder record1 = new PensionAttendolder();
		record1.setModifyRemind(2);
		pensionAttendolderMapper.updateByExampleSelective(record1, example1);
		// 康娱项目举办的时间和地点变更，给老人以提醒
		if (remindFlag) {
			this.remindActivity(
					selectedPensionActivity.getId(),
					"活动 &nbsp;" + record.getItemname() + "&nbsp;变更为：&nbsp;"
							+ record.getStarttime() + "&nbsp;在&nbsp;"
							+ record.getItemplace() + "&nbsp;举办！");
		}
	}

	/**
	 * 取消或更改举办时间和地点的活动 向老人pad端发消息
	 * 
	 * @param activityId
	 * @param remindMsg
	 * @author mary.liu 2014-04-08
	 */
	public void remindActivity(Long activityId, String remindMsg) {
		PensionAttendolderExample example = new PensionAttendolderExample();
		example.or().andActivityIdEqualTo(activityId).andIsattendEqualTo(1)// 能参加
				.andClearedEqualTo(2);// 未清除
		List<PensionAttendolder> attendOlderList = pensionAttendolderMapper
				.selectByExample(example);
		// /////////向老人列表发消息
	}

	/**
	 * 
	 * @Title: deleteActivity
	 * @Description: TODO
	 * @param @param pa
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-12 上午10:41:36
	 * @version V1.0
	 */
	public void deleteActivity(PensionActivity pa) {
		PensionActivity record = new PensionActivity();
		PensionActivityExample example = new PensionActivityExample();
		record.setCleared(1);
		example.or().andIdEqualTo(pa.getId());
		pensionActivityMapper.updateByExampleSelective(record, example);
	}

	/**
	 * check康娱项目是否有人报名 有，true，无，false
	 * 
	 * @param selectedPensionActivity
	 * @return
	 */
	public boolean checkActivity(Long activityId) {
		PensionActivity activity = pensionActivityMapper
				.selectByPrimaryKey(activityId);
		if (activity.getEnlistnumber() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 取消康娱项目 将康娱项目取消，将参加老人的取消标记置为2 未发送 add by mary.liu 2014-02-27
	 * 
	 * @param activityId
	 * @param activityName
	 */
	public void cancelActivity(Long activityId, String activityName) {
		PensionActivity activity = new PensionActivity();
		activity.setId(activityId);
		activity.setCanceled(1);
		pensionActivityMapper.updateByPrimaryKeySelective(activity);
		PensionAttendolderExample example = new PensionAttendolderExample();
		example.or().andActivityIdEqualTo(activityId).andClearedEqualTo(2);
		PensionAttendolder record = new PensionAttendolder();
		record.setCancelRemind(2);
		pensionAttendolderMapper.updateByExampleSelective(record, example);
		// /////////////向老人pad端发送活动取消的消息
		this.remindActivity(activityId, "活动&nbsp;" + activityName + "&nbsp;取消！");

	}

	/**
	 * 按活动编号查询
	 * 
	 * @param activityId
	 * @return
	 */
	public PensionActivity selectPensionActivity(Long activityId) {
		PensionActivity record = pensionActivityMapper
				.selectByPrimaryKey(activityId);
		if (record != null) {
			record.setSuperClassName(pensionDicSuperClassMapper
					.selectByPrimaryKey(record.getSuperClassId())
					.getSuperClassName());
			record.setClassName(pensionDicClassMapper.selectByPrimaryKey(
					record.getClassId()).getClassName());
		}
		return record;
	}
}
