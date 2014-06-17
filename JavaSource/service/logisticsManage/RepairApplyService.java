package service.logisticsManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centling.his.util.DateUtil;

import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionCheckExtend;

import persistence.logisticsManage.PensionCheckMapper;
import persistence.logisticsManage.PensionRepairMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;
@Service
public class RepairApplyService {
	@Autowired
	private PensionRepairMapper pensionRepairMapper;
	@Autowired
	private PensionCheckMapper pensionCheckMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	/**
	 * 查询维修申请记录
	 * @param startDate
	 * @param endDate
	 * @param olderId
	 * @param repairerId
	 * @param sendFlag
	 * @param handleFlag
	 * @return
	 */
	public List<PensionRepairExtend> selectRepairApplicationRecords(Date startDate,
			Date endDate, Long olderId, Long managerId, Integer sendFlag,
			Integer handleFlag) {
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if(endDate != null){
			map.put("endDate", DateUtil.getNextDay(endDate));
		}else{
			map.put("endDate", null);
		}		
		map.put("olderId", olderId);
		map.put("managerId", managerId);
		map.put("sendFlag", sendFlag);
		map.put("handleFlag", handleFlag);
		return pensionRepairMapper.selectRepairApplicationRecords(map);
	}
	/**
	 * 删除维修申请记录
	 * @param selectedRow
	 */
	public void deleteRepairApplicationRecord(PensionRepairExtend selectedRow) {
		selectedRow.setCleared(1);
		pensionRepairMapper.updateByPrimaryKeySelective(selectedRow);
	}
	/**
	 * 录入维修申请记录
	 * @param insertedRow
	 */
	public void insertRepairApplicationRecord(PensionRepairExtend insertedRow) {
		insertedRow.setCleared(2);
		pensionRepairMapper.insertSelective(insertedRow);
	}
	/**
	 * 修改维修申请记录
	 * @param updatedRow
	 */
	public void updateRepairApplicationRecord(PensionRepairExtend updatedRow) {
		pensionRepairMapper.updateByPrimaryKeySelective(updatedRow);
	}
	/**
	 * 提交维修申请记录
	 * @param selectedRow
	 * @param curEmployee
	 * @throws PmsException 
	 */
	public void submitRepairApplicationRecord(PensionRepairExtend selectedRow,
			PensionEmployee curEmployee) throws PmsException {
		String messageContent;
		String messagename;
		if(selectedRow.getRepairType()==1){
			 messageContent="老人"+selectedRow.getOlderName()+" 提交了一条维修申请！";
			 messagename = "【"+selectedRow.getOlderName()+"】维修申请";
		}else{
			 messageContent="房间"+selectedRow.getRoomName()+" 提交了一条维修申请！";
			 messagename = "【"+selectedRow.getRoomName()+"】维修申请";
		}
		
		 //消息类别
	     String typeIdStr = JavaConfig.fetchProperty("repairApplyService.messagetypeId");
	     Long messagetypeId = Long.valueOf(typeIdStr);
	     
	     String url;
	
		 url = messageMessage.selectMessageTypeUrl(messagetypeId);
		 url = url+"?repairId="+selectedRow.getId();
		 
	     String dpt_id = systemConfig.selectProperty("REPAIR_DPT_ID");
	     String emp_id = systemConfig.selectProperty("REPAIR_EMP_ID");
	     List<Long> dptIdList = new ArrayList<Long>();
	     List<Long> empIdList = new ArrayList<Long>();
	     if(emp_id != null && !emp_id.isEmpty()) {
	    	 String[] empId = emp_id.split(",");
	    	 for(int i = 0; i < empId.length; i++) {
	    		 empIdList.add(Long.valueOf(empId[i]));
		     }
	     }else if(dpt_id != null && !dpt_id.isEmpty()){
	    	 String[] dptId=dpt_id.split(",");
	    	 for(int i = 0; i < dptId.length; i++) {
	    		 dptIdList.add(Long.valueOf(dptId[i]));
		     }
	     }else{
	    	 throw new PmsException("请设置申请发送到的部门或人员");
	     }
	     
	     messageMessage.sendMessage(messagetypeId, dptIdList, empIdList, messagename, messageContent, url,"pension_repair",selectedRow.getId());
	     selectedRow.setApplyTime(new Date());
	     selectedRow.setSendFlag(1);//将发送标志 表示为1已发送
		 pensionRepairMapper.updateByPrimaryKeySelective(selectedRow);
	}
	
	public List<PensionCheckExtend> fillField(Long roomId) {
		return pensionCheckMapper.fillField(roomId);
	}
	
	public PensionCheckMapper getPensionCheckMapper() {
		return pensionCheckMapper;
	}
	public void setPensionCheckMapper(PensionCheckMapper pensionCheckMapper) {
		this.pensionCheckMapper = pensionCheckMapper;
	}
	public void setPensionRepairMapper(PensionRepairMapper pensionRepairMapper) {
		this.pensionRepairMapper = pensionRepairMapper;
	}

	public PensionRepairMapper getPensionRepairMapper() {
		return pensionRepairMapper;
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

}
