package service.receptionManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.dictionary.PensionDicNationMapper;
import persistence.dictionary.PensionDicPoliticsMapper;
import persistence.dictionary.PensionDicRelationshipMapper;
import persistence.employeeManage.PensionEmployeeMapper;
import persistence.olderManage.PensionOlderMapper;
import persistence.receptionManage.PensionApplyevaluateMapper;
import persistence.receptionManage.PensionLivingapplyMapper;
import persistence.system.PensionDeptMapper;
import persistence.system.PensionPadUserMapper;
import service.system.MessageMessage;
import util.DateUtil;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

import com.centling.his.util.MD5Util;

import domain.dictionary.PensionDicNation;
import domain.dictionary.PensionDicNationExample;
import domain.dictionary.PensionDicPolitics;
import domain.dictionary.PensionDicPoliticsExample;
import domain.dictionary.PensionDicRelationship;
import domain.dictionary.PensionDicRelationshipExample;
import domain.olderManage.PensionOlder;
import domain.olderManage.PensionOlderExample;
import domain.receptionManage.PensionApplyevaluate;
import domain.receptionManage.PensionLivingapply;
import domain.receptionManage.PensionLivingapplyExample;
import domain.system.PensionDept;
import domain.system.PensionPadUser;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-29 上午10:16:44
 */
@Service
public class LivingApplyService {
	@Autowired
	private PensionOlderMapper pensitonOlderMapper;
	@Autowired
	private PensionDicNationMapper nationMapper;
	@Autowired
	private PensionDicPoliticsMapper politicMapper;
	@Autowired
	private PensionDicRelationshipMapper relationShipMapper;
	@Autowired
	private PensionLivingapplyMapper applyMapper;
	@Autowired
	private PensionDeptMapper deptMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private PensionApplyevaluateMapper pensionApplyevaluateMapper;
	@Autowired
	private PensionPadUserMapper pensionPadUserMapper;
	@Autowired
	private PensionEmployeeMapper pensionEmployeeMapper;

	public String testFun() {
		return "test-Return";
	}

	/**
	 * 查询老人入住申请记录列表
	 * 
	 * @return
	 */
	public List<PensionOlderDomain> selectApplications(String personState,
			String applier, Long olderId, String types, Date StartDate,
			Date endDate) {
		List<PensionOlderDomain> applicationList = new ArrayList<PensionOlderDomain>();
		Long deptId = null;
		Long emptId = selectLeadEmpId();
		if (emptId == null) {
			deptId = selectLeadDeptId();
			applicationList = pensitonOlderMapper.selectApplications(
					personState, applier, olderId, types, StartDate, endDate,
					null, deptId);
		} else {
			applicationList = pensitonOlderMapper.selectApplications(
					personState, applier, olderId, types, StartDate, endDate,
					emptId, null);
		}
		for (PensionOlderDomain temp : applicationList) {
			convertOlderTemp(temp);
		}
		return applicationList;
	}

	/**
	 * 老人申请记录转换
	 */
	public PensionOlderDomain convertOlderTemp(PensionOlderDomain temp) {
		// 申请人 性别转换
		if (temp.getApplySex() != null) {
			if (temp.getApplySex() == 1) {
				temp.setApplySexStr("男");
			} else {
				temp.setApplySexStr("女");
			}
		} else {
			temp.setApplySexStr("");
		}
		// 老人性别转换
		if (temp.getSex() == 1) {
			temp.setOldSex("男");
		} else {
			temp.setOldSex("女");
		}

		// 老人类型转换
		if (temp.getTypes() == 1) {
			temp.setOldType("自理");
		} else if (temp.getTypes() == 2) {
			temp.setOldType("半自理");
		} else {
			temp.setOldType("不能自理");
		}
		// 老人状态转换
		switch (temp.getStatuses()) {
		case 1:
			temp.setOlderState("待评估");
			break;
		case 2:
			temp.setOlderState("已评估");
			break;
		case 3:
			temp.setOlderState("入住");
			break;
		case 4:
			temp.setOlderState("请假");
			break;
		default:
			temp.setOlderState("退住");
			break;
		}
		return temp;
	}

	/**
	 * 查询民族列表
	 * 
	 * @return
	 */
	public List<PensionDicNation> selectNationList() {
		List<PensionDicNation> nationList = new ArrayList<PensionDicNation>();
		PensionDicNationExample example = new PensionDicNationExample();
		example.or();
		example.setOrderByClause("Id");
		nationList = nationMapper.selectByExample(example);
		return nationList;
	}

	/**
	 * 查询政治面貌列表
	 * 
	 * @return
	 */
	public List<PensionDicPolitics> selectPoliticList() {
		List<PensionDicPolitics> politicList = new ArrayList<PensionDicPolitics>();
		PensionDicPoliticsExample example = new PensionDicPoliticsExample();
		example.or();
		example.setOrderByClause("Id");
		politicList = politicMapper.selectByExample(example);
		return politicList;
	}

	/**
	 * 查询申请人与老人关系列表
	 * 
	 * @return
	 */
	public List<PensionDicRelationship> selectRelationshipList() {
		List<PensionDicRelationship> RelationshipList = new ArrayList<PensionDicRelationship>();
		PensionDicRelationshipExample example = new PensionDicRelationshipExample();
		example.or();
		example.setOrderByClause("Id");
		RelationshipList = relationShipMapper.selectByExample(example);
		return RelationshipList;
	}

	/**
	 * 插入一条老人基本信息
	 * 
	 * @param olderTemp
	 */
	@Transactional
	public void insertPensionOlder(PensionOlder olderTemp,
			PensionLivingapply application) {
		// add by justin.su 2014-03-12 解决日历控件不选择日期，手动输入，老人年龄不能算出，后台报错的问题
		olderTemp.setAge(DateUtil.selectAge(olderTemp.getBirthday()));
		// 保存老人基本信息
		pensitonOlderMapper.insertSelective(olderTemp);
		// 保存老人申请信息
		Long olderId = olderTemp.getId();
		// 同时插入pension_pad_user表 add by justin.su 2103-12-16
		PensionOlder po = pensitonOlderMapper.selectByPrimaryKey(olderId);
		PensionPadUser pensionPadUser = new PensionPadUser();
		pensionPadUser.setOlderId(po.getId());
		pensionPadUser.setUserName(po.getName());
		pensionPadUser.setRoleId((long) 1);
		pensionPadUser.setPassword(MD5Util.encodeStr("1234"));
		pensionPadUserMapper.insertSelective(pensionPadUser);
		application.setOlderId(olderId);
		insertApplication(application);
		// 插入入住评估信息
		long applyId = selectApplyId(olderId);
		List<Long> deptIdList = selectDeptIdList();
		List<Long> empIdList = selectEmptIdList();
		if (empIdList != null && empIdList.size() != 0) {
			insertApplyevaluate(applyId, empIdList, null);
		} else {
			insertApplyevaluate(applyId, null, deptIdList);
		}
	}

	/**
	 * 更新一条老人基本信息
	 * 
	 * @param olderTemp
	 */
	@Transactional
	public void updatePensionOlder(PensionOlder olderTemp) {
		// add by justin.su 2014-03-12 解决日历控件不选择日期，手动输入，老人年龄不能算出，后台报错的问题
		olderTemp.setAge(DateUtil.selectAge(olderTemp.getBirthday()));
		pensitonOlderMapper.updateByPrimaryKeySelective(olderTemp);
	}

	/**
	 * 更新一条老人基本信息
	 * 
	 * @param olderTemp
	 */
	@Transactional
	public void updateApplication(PensionLivingapply apply) {
		applyMapper.updateByPrimaryKeySelective(apply);
	}

	/**
	 * 修改申请记录清除标记,只修改申请表，不更改老人信息表
	 */
	public void deleteApplication(PensionLivingapply apply) {
		apply.setCleared(1);
		applyMapper.updateByPrimaryKeySelective(apply);
	}

	/**
	 * 根据系统参数分离部门Id
	 * 
	 * @return
	 */
	public Long selectLeadDeptId() {
		List<Long> deptIdList = selectDeptIdList();
		if (deptIdList != null && deptIdList.size() != 0) {
			return deptIdList.get(deptIdList.size() - 1);
		}
		return null;
	}

	/**
	 * 根据系统参数分离审批人员Id
	 * 
	 * @return
	 */
	public Long selectLeadEmpId() {
		List<Long> empIdList = selectEmptIdList();
		if (empIdList != null && empIdList.size() != 0) {
			return empIdList.get(empIdList.size() - 1);
		}
		return null;
	}

	/**
	 * 查询所有的老人入住申请记录列表
	 * 
	 * @return
	 */
	public List<PensionOlderDomain> selectAllApplications(Long olderId) {
		List<PensionOlderDomain> applicationList = new ArrayList<PensionOlderDomain>();
		Long deptId = null;
		Long emptId = selectLeadEmpId();
		if (emptId == null) {
			deptId = selectLeadDeptId();
			applicationList = pensitonOlderMapper.getAllApplications(null,
					deptId, olderId);
		} else {
			applicationList = pensitonOlderMapper.getAllApplications(emptId,
					null, olderId);
		}
		for (PensionOlderDomain temp : applicationList) {
			convertOlderTemp(temp);
		}
		return applicationList;
	}

	/**
	 * 插入一条入住申请记录
	 */
	@Transactional
	public void insertApplication(PensionLivingapply apply) {
		applyMapper.insertSelective(apply);
	}

	/**
	 * 查询入住申请Id
	 * 
	 * @return
	 */
	public Long selectApplyId(Long olderId) {
		PensionLivingapplyExample example = new PensionLivingapplyExample();
		example.or().andOlderIdEqualTo(olderId);
		Long applyId = applyMapper.selectByExample(example).get(0).getId();
		return applyId;
	}

	/**
	 * 插入一条申请评估记录
	 */
	@Transactional
	public void insertApplyevaluate(Long applyId, List<Long> emptList,
			List<Long> deptList) {
		PensionApplyevaluate applyEvaluate = new PensionApplyevaluate();
		applyEvaluate.setApplyId(applyId);
		applyEvaluate.setCleared(2);
		if (emptList != null) {
			for (Long emptId : emptList) {
				applyEvaluate.setEvaluatorId(emptId.intValue());
				Long deptId = pensionEmployeeMapper.selectByPrimaryKey(emptId)
						.getDeptId();
				applyEvaluate.setDeptId(deptId);
				applyEvaluate.setEvaluatorId(emptId.intValue());
				pensionApplyevaluateMapper.insert(applyEvaluate);
			}
		} else {
			for (Long deptId : deptList) {
				applyEvaluate.setDeptId(deptId);
				pensionApplyevaluateMapper.insert(applyEvaluate);
			}
		}
	}

	/**
	 * 查询接收入住申请评估通知的部门列表
	 * 
	 * @param deptIdList
	 * @return
	 * @throws PmsException
	 */
	public List<PensionDept> selectApplyDept() throws PmsException {
		List<Long> deptIdList = selectDeptIdList();
		List<PensionDept> deptList = new ArrayList<PensionDept>();
		PensionDept dept;
		for (Long deptId : deptIdList) {
			dept = deptMapper.selectByPrimaryKey(deptId);
			deptList.add(dept);
		}
		return deptList;
	}

	/**
	 * 根据系统参数分离部门Id
	 * 
	 * @return
	 * @throws PmsException
	 */
	public List<Long> selectDeptIdList() {
		List<Long> deptIdList = new ArrayList<Long>();
		String living_apply_dpt_id = null;
		try {
			living_apply_dpt_id = systemConfig
					.selectProperty("LIVING_APPLY_DPT_ID");
		} catch (PmsException e) {
			e.printStackTrace();
		}
		if (living_apply_dpt_id != null && !living_apply_dpt_id.isEmpty()) {
			String[] dpt_id_arr = living_apply_dpt_id.split(",");
			for (int i = 0; i < dpt_id_arr.length; i++) {
				deptIdList.add(Long.valueOf(dpt_id_arr[i]));
			}
		} else {
			deptIdList = null;
		}
		return deptIdList;
	}

	/**
	 * 根据系统参数分离部门Id
	 * 
	 * @return
	 * @throws PmsException
	 */
	public List<Long> selectEmptIdList() {
		List<Long> emptIdList = new ArrayList<Long>();
		String living_apply_emp_id = null;
		try {
			living_apply_emp_id = systemConfig
					.selectProperty("LIVING_APPLY_EMP_ID");
		} catch (PmsException e) {
			e.printStackTrace();
		}
		if (living_apply_emp_id != null && !living_apply_emp_id.isEmpty()) {
			String[] emp_id_arr = living_apply_emp_id.split(",");
			for (int i = 0; i < emp_id_arr.length; i++) {
				emptIdList.add(Long.valueOf(emp_id_arr[i]));
			}
		} else {
			emptIdList = null;
		}
		return emptIdList;
	}

	/**
	 * 查询老人ID
	 * 
	 * @return
	 */
	public Long selectOlderId(String name, String idCard) {
		PensionOlderExample example = new PensionOlderExample();
		example.or().andNameEqualTo(name).andIdcardnumEqualTo(idCard);
		example.setOrderByClause("id DESC");
		return pensitonOlderMapper.selectByExample(example).get(0).getId();
	}

	/**
	 * 发送消息
	 * 
	 * @param selectedRow
	 * @throws PmsException
	 */
	public void sentMessage(PensionOlderDomain selectedRow) throws PmsException {
		String pensionOlderName = selectedRow.getName();
		String messageContent = "老人" + pensionOlderName + "入住申请已提交！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("LivingApplyController.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?olderId=" + selectedRow.getId();

		List<Long> dptIdList = selectDeptIdList();
		List<Long> empIdList = selectEmptIdList();

		String messagename = "【" + pensionOlderName + "】入住审批";

		messageMessage.sendMessage(messagetypeId, dptIdList, empIdList,
				messagename, messageContent, url, "pension_older",
				selectedRow.getId());
	}

}
