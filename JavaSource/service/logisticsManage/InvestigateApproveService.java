package service.logisticsManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centling.his.util.DateUtil;

import persistence.logisticsManage.PensionCheckApproveMapper;
import persistence.logisticsManage.PensionCheckMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionCheckApproveExtend;
import domain.logisticsManage.PensionCheckExtend;
@Service
public class InvestigateApproveService {
	@Autowired
	private PensionCheckMapper pensionCheckMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired 
	private PensionCheckApproveMapper pensionCheckApproveMapper;
	/**
	 * 查看被选中行的审批结果
	 * @param selectedRow
	 * @return 
	 */
	public List<PensionCheckApproveExtend> selectApprovalResults(PensionCheckExtend selectedRow) {
		
		Long checkId = selectedRow.getId();
		return pensionCheckApproveMapper.selectApproveResults(checkId);
	}
	/**
	 * 拒绝该申请
	 * @param selectedRow
	 * @param employeeId
	 * @param deptId
	 * @param refuseReason
	 * @param approves 
	 * @throws PmsException 
	 */
	public void refuse(PensionCheckExtend selectedRow, PensionEmployee employee, String refuseReason, List<PensionCheckApproveExtend> approves) throws PmsException {
		//在前台更新
		selectedRow.setApproveResult(2);
		Long checkId = selectedRow.getId();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("checkId",checkId);
		map.put("deptId", employee.getDeptId());
		map.put("approverId", employee.getId());
		map.put("approveResult", 2);
		map.put("currentTime", new Date());
		map.put("refuseReason", refuseReason);
		pensionCheckApproveMapper.check(map);
		updateApprove(checkId,employee.getId(),employee.getDeptId());
		if(isFinishedApprove(approves)){
			sendApproveResults(selectedRow, employee);
		}
		
	}
	/**
	 * 同意该申请
	 * @param selectedRow
	 * @param employeeId
	 * @param deptId
	 * @param approves 
	 * @throws PmsException 
	 */
	public void approve(PensionCheckExtend selectedRow, PensionEmployee employee, List<PensionCheckApproveExtend> approves) throws PmsException {
		//在前台更新
		selectedRow.setApproveResult(1);
		Long checkId = selectedRow.getId();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("checkId",checkId);
		map.put("deptId", employee.getDeptId());
		map.put("approverId", employee.getId());
		map.put("approveResult", 1);
		map.put("currentTime", new Date());
		pensionCheckApproveMapper.check(map);
		updateApprove(checkId,employee.getId(),employee.getDeptId());
		if(isFinishedApprove(approves)){
			sendApproveResults(selectedRow, employee);
		}
		
	}
	/**
	 * 查询排查记录
	 * @param startDate
	 * @param endDate
	 * @param ensureFlag
	 * @param curEmployee
	 * @param checkId
	 * @return
	 */
	public List<PensionCheckExtend> selectInvestigatationRecords(
			Date startDate, Date endDate, Integer ensureFlag,
			PensionEmployee curEmployee, Long checkId) {
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if(endDate != null){
			map.put("endDate", DateUtil.getNextDay(endDate));
		}else{
			map.put("endDate", null);
		}		
		map.put("ensureFlag",ensureFlag);
		map.put("checkId", checkId);
		map.put("deptId", curEmployee.getDeptId());
		
		return pensionCheckApproveMapper.selectInvestigatationRecords(map);
	}
	
	/**
	 * 更新一条评估记录
	 */
	@Transactional
	public void updateApprove(Long checkId,Long employeeId, Long deptId) {
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("investigateService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(employeeId, messagetypeId,
				"pension_check", checkId, deptId);
	}
	
	/**
	 * 通知审批结果
	 * @param selectedRow
	 * @param curEmployee
	 * @throws PmsException 
	 */
	public void sendApproveResults(PensionCheckExtend selectedRow,
			PensionEmployee curEmployee) throws PmsException {
		 String messageContent="【"+curEmployee.getName()+"】 提交了一条排查维修审批结果通知！";
	     //消息类别
	     String typeIdStr = JavaConfig.fetchProperty("investigateApproveService.messagetypeId");
	     Long messagetypeId = Long.valueOf(typeIdStr);
	     
	     String url;
	
		 url = messageMessage.selectMessageTypeUrl(messagetypeId);
		 url = url+"?checkId="+selectedRow.getId();
		 
	     //提出排查维修申请的部门
	     String dept_id = systemConfig.selectProperty("CHECK_APPROVE_DEPT_ID");
		 //提出排查维修申请的人员
	     String emp_id = systemConfig.selectProperty("CHECK_APPROVE_EMP_ID");
	     List<Long> dptIdList = new ArrayList<Long>();
	     List<Long> empIdList = new ArrayList<Long>();
	     if(emp_id != null && !emp_id.isEmpty()){
	    	 String[] empIds=emp_id.split(",");
	    	 for(int i = 0; i < empIds.length; i++) {
	    		 empIdList.add(Long.valueOf(empIds[i]));
		     }
	     }else if(dept_id != null && !dept_id.isEmpty()){
	    	 String[] dptIds=dept_id.split(",");
	    	 for(int i = 0; i < dptIds.length; i++) {
	    		 dptIdList.add(Long.valueOf(dptIds[i]));
		     }
	     }else{
	    	 throw new PmsException("请设置申请发送到的部门或人员");
	     }
	     	 
	     String messagename = "【"+curEmployee.getName()+"】排查维修审批结果通知";
	     //发给审批是否需要维修的人
	     messageMessage.sendMessage(messagetypeId, dptIdList, empIdList, messagename, messageContent, url,"pension_check",selectedRow.getId());
	
	}
	/**
	 * 判断是否 应进行判断的部门都已经完成判断
	 * @param approves
	 * @return
	 */
	public boolean isFinishedApprove(List<PensionCheckApproveExtend> approves){
		int i = 0;
		for(PensionCheckApproveExtend approve:approves){
			if(approve.getApproveResult()!=null){
				i++;
			}
		}
		return i==approves.size()-1;
	}
	
	
	public PensionCheckMapper getPensionCheckMapper() {
		return pensionCheckMapper;
	}

	public void setPensionCheckMapper(PensionCheckMapper pensionCheckMapper) {
		this.pensionCheckMapper = pensionCheckMapper;
	}

	public MessageMessage getMessageMessage() {
		return messageMessage;
	}

	public void setMessageMessage(MessageMessage messageMessage) {
		this.messageMessage = messageMessage;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}

	public PensionCheckApproveMapper getPensionCheckApproveMapper() {
		return pensionCheckApproveMapper;
	}

	public void setPensionCheckApproveMapper(
			PensionCheckApproveMapper pensionCheckApproveMapper) {
		this.pensionCheckApproveMapper = pensionCheckApproveMapper;
	}
}
