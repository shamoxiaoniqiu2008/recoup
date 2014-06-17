package service.logisticsManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.employeeManage.PensionEmployeeMapper;
import persistence.logisticsManage.PensionCheckApproveMapper;
import persistence.logisticsManage.PensionCheckMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

import com.centling.his.util.DateUtil;

import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionCheck;
import domain.logisticsManage.PensionCheckApprove;
import domain.logisticsManage.PensionCheckApproveExtend;
import domain.logisticsManage.PensionCheckExtend;

@Service
public class InvestigateService {
	@Autowired
	private PensionCheckMapper pensionCheckMapper;
	@Autowired
	private PensionCheckApproveMapper pensionCheckApproveMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private PensionEmployeeMapper pensionEmployeeMapper;
	
	/**
	 * 查询维修申请记录
	 * @param startDate
	 * @param endDate
	 * @param olderId
	 * @param repairerId
	 * @param sendFlag
	 * @param handleFlag
	 * @param checkType 
	 * @param checkId 
	 * @return
	 */
	public List<PensionCheckExtend> selectInvestigatationRecords(Date startDate,
			Date endDate, Long checkerId,Long roomId,Integer applysendFlag,
			Integer needrepairFlag, Integer checkType, Long checkId) {
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if(endDate != null){
			map.put("endDate", DateUtil.getNextDay(endDate));
		}else{
			map.put("endDate", null);
		}		
	//	map.put("checkerId", checkerId);
		map.put("roomId", roomId);
		map.put("applysendFlag", applysendFlag);
		map.put("needrepairFlag", needrepairFlag);
		map.put("checkType", checkType);
		map.put("checkId", checkId);
		
		return pensionCheckMapper.selectInvestigatationRecords(map);
	}
	/**
	 * 删除维修申请记录
	 * @param selectedRow
	 */
	public void deleteInvestigatationRecord(PensionCheck selectedRow) {
		selectedRow.setCleared(1);
		pensionCheckMapper.updateByPrimaryKeySelective(selectedRow);
	}
	/**
	 * 录入维修申请记录
	 * @param insertedRow
	 */
	public void insertInvestigatationRecord(PensionCheck insertedRow) {
		insertedRow.setCleared(2);
		insertedRow.setCheckTime(new Date());
		pensionCheckMapper.insertSelective(insertedRow);
	}
	/**
	 * 修改维修申请记录
	 * @param updatedRow
	 */
	public void updateInvestigatationRecord(PensionCheck updatedRow) {
		pensionCheckMapper.updateByPrimaryKeySelective(updatedRow);
	}
	/**
	 * 提交维修申请记录
	 * @param selectedRow
	 * @param curEmployee
	 * @throws PmsException 
	 */
	public void submitInvestigatationRecord(PensionCheckExtend selectedRow,
			PensionEmployee curEmployee) throws PmsException {
		 String messageContent="【"+selectedRow.getRoomName()+"】 提交了一条排查维修申请！";
	     //消息类别
	     String typeIdStr = JavaConfig.fetchProperty("investigateService.messagetypeId");
	     Long messagetypeId = Long.valueOf(typeIdStr);
	     
	     String url;
	
		 url = messageMessage.selectMessageTypeUrl(messagetypeId);
		 url = url+"?checkId="+selectedRow.getId();
		 
	     
		 
		 //String vacation_dpt_id = systemConfig.selectProperty("REPAIR_DPT_ID");
		 //审批是否需要维修的或部门
	     String repair_approve_emp_id = systemConfig.selectProperty("REPAIR_APPROVE_EMP_ID");
	     String repair_approve_dept_id = systemConfig.selectProperty("REPAIR_APPROVE_DEPT_ID");
	     List<Long> repairEmpList = new ArrayList<Long>();
	     List<Long> repairDeptList = new ArrayList<Long>();
	     if(repair_approve_emp_id != null && !repair_approve_emp_id.isEmpty()) {
	    	 String[] empId = repair_approve_emp_id.split(",");
	    	 for(int i = 0; i < empId.length; i++) {
	    		 repairEmpList.add(Long.valueOf(empId[i]));
		     }
	     }else if(repair_approve_dept_id != null && !repair_approve_dept_id.isEmpty()){
	    	 String[] dptId=repair_approve_dept_id.split(",");
	    	 for(int i = 0; i < dptId.length; i++) {
	    		 repairDeptList.add(Long.valueOf(dptId[i]));
		     }
	     }else{
	    	 throw new PmsException("请设置申请发送维修审批到的部门或人员");
	     }
		 //审批维修内容的人员或部门
	     String detail_approve_emp_id = systemConfig.selectProperty("DETAIL_APPROVE_EMP_ID");
	     String detail_approve_dept_id = systemConfig.selectProperty("DETAIL_APPROVE_DEPT_ID");
	     List<Long> detailEmpList = new ArrayList<Long>();
	     List<Long> detailDeptList = new ArrayList<Long>();
	     if(detail_approve_emp_id != null && !detail_approve_emp_id.isEmpty()) {
	    	 String[] empId = detail_approve_emp_id.split(",");
	    	 for(int i = 0; i < empId.length; i++) {
	    		 detailEmpList.add(Long.valueOf(empId[i]));
		     }
	     }else if(detail_approve_dept_id != null && !detail_approve_dept_id.isEmpty()){
	    	 String[] dptId=detail_approve_dept_id.split(",");
	    	 for(int i = 0; i < dptId.length; i++) {
	    		 detailDeptList.add(Long.valueOf(dptId[i]));
		     }
	     }else{
	    	 throw new PmsException("请设置申请发送维修内容审批到的部门或人员");
	     }
	     
	     	 
	     String messagename = "【"+selectedRow.getRoomName()+"】排查维修申请";
	     //发给审批是否需要维修的人或部门
	     messageMessage.sendMessage(messagetypeId, repairDeptList, repairEmpList, messagename, messageContent, url,"pension_check",selectedRow.getId());
	     //发给审批维修内容的人或部门
	     messageMessage.sendMessage(messagetypeId, detailDeptList, detailEmpList, messagename, messageContent, url,"pension_check",selectedRow.getId());
	     selectedRow.setApplysendFlag(1);//将发送标志 表示为1已发送
		 pensionCheckMapper.updateByPrimaryKeySelective(selectedRow);
		 //剩下的代码是为了往pension_check_approve表里面插入数据  
		 if(repair_approve_emp_id != null && !repair_approve_emp_id.isEmpty()){
			 for(Long  repairId:repairEmpList){
				 PensionCheckApprove needRepairApprove = new PensionCheckApprove();
				 needRepairApprove.setCheckId(selectedRow.getId());
				 needRepairApprove.setDeptId(getDeptId(repairId));
				 
				 //needRepairApprove.setApproverId(repairId);
				 needRepairApprove.setApproveType(1);
				 pensionCheckApproveMapper.insertSelective(needRepairApprove);
				 
			 }
		 }else if(repair_approve_dept_id != null && !repair_approve_dept_id.isEmpty()){
			 for(Long  repairDeptId:repairDeptList){
				 PensionCheckApprove needRepairApprove = new PensionCheckApprove();
				 needRepairApprove.setCheckId(selectedRow.getId());
				 needRepairApprove.setDeptId(repairDeptId);
				 //needRepairApprove.setApproverId(repairId);
				 needRepairApprove.setApproveType(1);
				 pensionCheckApproveMapper.insertSelective(needRepairApprove);
				 
			 }
		 }
		  if(detail_approve_emp_id != null && !detail_approve_emp_id.isEmpty()){
			  for(Long  detailId:detailEmpList){
					 PensionCheckApprove repairDetailApprove = new PensionCheckApprove();
					 repairDetailApprove.setCheckId(selectedRow.getId());
					 repairDetailApprove.setDeptId(getDeptId(detailId));
					 //repairDetailApprove.setApproverId(detailId);
					 repairDetailApprove.setApproveType(2);
					 pensionCheckApproveMapper.insertSelective(repairDetailApprove);
					 
				 }
		  }else if(detail_approve_dept_id != null && !detail_approve_dept_id.isEmpty()){
			  for(Long  detailId:detailDeptList){
					 PensionCheckApprove repairDetailApprove = new PensionCheckApprove();
					 repairDetailApprove.setCheckId(selectedRow.getId());
					 repairDetailApprove.setDeptId(detailId);
					 //repairDetailApprove.setApproverId(detailId);
					 repairDetailApprove.setApproveType(2);
					 pensionCheckApproveMapper.insertSelective(repairDetailApprove);
					 
				 }
		  }
		
	}
	/**
	 * 根据排查记录主键 查询出对应的审批结果
	 * @param checkId
	 * @return
	 */
	public List<PensionCheckApproveExtend> selectApproveResults(Long checkId){
		return pensionCheckApproveMapper.selectApproveResults(checkId);
	}
	
	
	/**
	 * 根据老人ID返回相应的大厦号，房间号等等
	 * @param olderId
	 * @return
	 */
	public List<PensionCheckExtend> fillField(Long roomId) {
		return pensionCheckMapper.fillField(roomId);
	}
	
	/**
	 * 获取员工的部门ID
	 * @param empId
	 * @return
	 */
	public Long getDeptId(Long empId){
		 return pensionEmployeeMapper.selectByPrimaryKey(empId).getDeptId();
		
	}
	public void setMessageMessage(MessageMessage messageMessage) {
		this.messageMessage = messageMessage;
	}

	public MessageMessage getMessageMessage() {
		return messageMessage;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}
	public void setPensionCheckMapper(PensionCheckMapper pensionCheckMapper) {
		this.pensionCheckMapper = pensionCheckMapper;
	}
	public PensionCheckMapper getPensionCheckMapper() {
		return pensionCheckMapper;
	}
	public PensionCheckApproveMapper getPensionCheckApproveMapper() {
		return pensionCheckApproveMapper;
	}
	public void setPensionCheckApproveMapper(
			PensionCheckApproveMapper pensionCheckApproveMapper) {
		this.pensionCheckApproveMapper = pensionCheckApproveMapper;
	}
}
