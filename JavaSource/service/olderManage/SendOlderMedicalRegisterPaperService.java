package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centling.his.util.DateUtil;

import domain.employeeManage.PensionEmployee;

import persistence.olderManage.PensionHospitalizeregisterMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

@Service
public class SendOlderMedicalRegisterPaperService {
	@Autowired
	private PensionHospitalizeregisterMapper pensionHospitalizeregisterMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	
	public List<PensionHospitalizeregisterExtend> selectOlderMedicalRegisterRecords(Date startDate, Date endDate, Long olderId, Long groupId) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if(endDate != null){
			map.put("endDate", DateUtil.getNextDay(endDate));
		}else{
			map.put("endDate", null);
		}	
		map.put("groupId", groupId);
		map.put("olderId", olderId);
		return pensionHospitalizeregisterMapper.selectMedicalRegisterRecords(map); 
	}
	
	
	public void sendOlderMedicalRegisterPaper(PensionHospitalizeregisterExtend selectedRow, PensionEmployee curEmployee) throws PmsException{
		
		 String messageContent="员工【"+curEmployee.getName()+"】发送了一条就医登记单！";
	     //消息类别
	     String typeIdStr = JavaConfig.fetchProperty("sendOlderMedicalRegisterPaperService.messagetypeId");
	     Long messagetypeId = Long.valueOf(typeIdStr);
	     
	     String url;
	
		 url = messageMessage.selectMessageTypeUrl(messagetypeId);
		 url = url+"?registerId="+selectedRow.getId();
		 
	     String vacation_dpt_id = systemConfig.selectProperty("MEDICAL_DPT_ID");
	     //String vacation_emp_id = systemConfig.selectProperty("MEDICAL_EMP_ID");
	     List<Long> dptIdList = new ArrayList<Long>();
	     List<Long> empIdList = new ArrayList<Long>();
	     /*if(vacation_emp_id != null && !vacation_emp_id.isEmpty()) {
	    	 String[]  vacationEmpId=vacation_emp_id.split(",");
	    	 for(int i = 0; i < vacationEmpId.length; i++) {
	    		 empIdList.add(Long.valueOf(vacationEmpId[i]));
		     }
	     }else*/ 
    	 if(vacation_dpt_id != null && !vacation_dpt_id.isEmpty()){
    		 String[] vacationDptIds=vacation_dpt_id.split(",");
	    	 for(int i = 0; i < vacationDptIds.length; i++) {
	    		 dptIdList.add(Long.valueOf(vacationDptIds[i]));
		     }
	     }else{
	    	 throw new PmsException("请设置申请发送到的部门或人员");
	     }
	     	 
	     String messagename = "【"+curEmployee.getName()+"】就医登记单发送";
	     
	     messageMessage.sendMessage(messagetypeId, dptIdList, empIdList, messagename, messageContent, url,"pension_hospitalizeregister",selectedRow.getId());
	     
	     selectedRow.setSended(1);//将发送标志 表示为1已发送
	     pensionHospitalizeregisterMapper.updateByPrimaryKeySelective(selectedRow);
	}
	
	public MessageMessage getMessageMessage() {
		return messageMessage;
	}
	public void setMessageMessage(MessageMessage messageMessage) {
		this.messageMessage = messageMessage;
	}
	public PensionHospitalizeregisterMapper getPensionHospitalizeregisterMapper() {
		return pensionHospitalizeregisterMapper;
	}
	public void setPensionHospitalizeregisterMapper(
			PensionHospitalizeregisterMapper pensionHospitalizeregisterMapper) {
		this.pensionHospitalizeregisterMapper = pensionHospitalizeregisterMapper;
	}
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}
	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}
}
