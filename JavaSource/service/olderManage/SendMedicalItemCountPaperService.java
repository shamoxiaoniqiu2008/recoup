package service.olderManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.employeeManage.PensionEmployee;

import persistence.olderManage.PensionHospitalizegroupMapper;
import persistence.olderManage.PensionItemcountMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

@Service
public class SendMedicalItemCountPaperService {

	@Autowired
	private PensionItemcountMapper pensionItemcountMapper;
	@Autowired
	private PensionHospitalizegroupMapper pensionHospitalizegroupMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	/**
	 * 查看分组记录
	 * @param startDate
	 * @param endDate
	 * @param managerId
	 * @return
	 */
	public List<PensionHospitalizegroupExtend> selectGroupRecords(Long groupId) {
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		map.put("generated",1 );
		return pensionHospitalizegroupMapper.selectGroupRecords(map);
	
	}
	
	public void sendItemCountPaper(PensionHospitalizegroupExtend viewedRow,
			List<PensionItemcountExtend> itemRecords, PensionEmployee curEmployee) throws PmsException {
		
		 String messageContent="员工"+curEmployee.getName()+"发送了一条就医项目统计单！";
	     //消息类别
	     String typeIdStr = JavaConfig.fetchProperty("sendMedicalItemCountPaperService.messagetypeId");
	     Long messagetypeId = Long.valueOf(typeIdStr);
	     
	     String url;
	
		 url = messageMessage.selectMessageTypeUrl(messagetypeId);
		 url = url+"?groupId="+viewedRow.getId();
		 
	     String vacation_dpt_id = systemConfig.selectProperty("MEDICAL_DPT_ID");
	    // String vacation_emp_id = systemConfig.selectProperty("MEDICAL_EMP_ID");
	     List<Long> dptIdList = new ArrayList<Long>();
	     List<Long> empIdList = new ArrayList<Long>();
	    
	     /*if(vacation_emp_id != null && !vacation_emp_id.isEmpty()) {
	    	 String[]  vacationEmpId=vacation_emp_id.split(",");
	    	 for(int i = 0; i < vacationEmpId.length; i++) {
	    		 empIdList.add(Long.valueOf(vacationEmpId[i]));
		     }
	     }else 
	    */	 
    	 if(vacation_dpt_id != null && !vacation_dpt_id.isEmpty()){
	    	 String[] vacationDptIds=vacation_dpt_id.split(",");
	    	 for(int i = 0; i < vacationDptIds.length; i++) {
	    		 dptIdList.add(Long.valueOf(vacationDptIds[i]));
		     }
	     }else{
	    	 throw new PmsException("请设置申请发送到的部门或人员");
	     }
	     	 
	     String messagename = "【"+curEmployee.getName()+"】就医项目统计单发送";
	     
	     messageMessage.sendMessage(messagetypeId, dptIdList, empIdList, messagename, messageContent, url,"pension_hospitalizegroup",viewedRow.getId());
	     viewedRow.setSended(1);//将发送标志 表示为1已发送
	     pensionHospitalizegroupMapper.updateByPrimaryKeySelective(viewedRow);
	}
	public PensionItemcountMapper getPensionItemcountMapper() {
		return pensionItemcountMapper;
	}

	public void setPensionItemcountMapper(
			PensionItemcountMapper pensionItemcountMapper) {
		this.pensionItemcountMapper = pensionItemcountMapper;
	}

	public PensionHospitalizegroupMapper getPensionHospitalizegroupMapper() {
		return pensionHospitalizegroupMapper;
	}

	public void setPensionHospitalizegroupMapper(
			PensionHospitalizegroupMapper pensionHospitalizegroupMapper) {
		this.pensionHospitalizegroupMapper = pensionHospitalizegroupMapper;
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

}
