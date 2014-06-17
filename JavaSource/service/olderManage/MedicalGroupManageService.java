package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centling.his.util.DateUtil;
import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;

import persistence.olderManage.PensionHospitalizegroupMapper;
import service.olderManage.PensionHospitalizegroupExtend;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

@Service
public class MedicalGroupManageService {
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
	public List<PensionHospitalizegroupExtend> selectGroupRecords(Date startDate, Date endDate, Long managerId,Long groupId) {
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if(endDate != null){
			map.put("endDate", DateUtil.getNextDay(endDate));
		}else{
			map.put("endDate", null);
		}		
		map.put("managerId", managerId);
		map.put("groupId", groupId);
		
		return pensionHospitalizegroupMapper.selectGroupRecords(map);
	
	}
	/**
	 * 添加分组
	 * @param insertedRow
	 */
	public void insertGroupRecord(PensionHospitalizegroupExtend insertedRow) {
		
		pensionHospitalizegroupMapper.insertSelective(insertedRow);
		
	}
	/**
	 * 删除选定的分组
	 * @param selectedRow
	 */
	public void deleteGroupRecord(PensionHospitalizegroupExtend selectedRow) {

		selectedRow.setCleared(1);
		pensionHospitalizegroupMapper.updateByPrimaryKeySelective(selectedRow);
	}
	/**
	 * 修改选定的分组
	 * @param updatedRow
	 */
	public void updateGroupRecord(PensionHospitalizegroupExtend updatedRow) {
		
		pensionHospitalizegroupMapper.updateByPrimaryKeySelective(updatedRow);
		
	}
	
	/**
	 * 发送消息
	 * @param viewedRow
	 * @param itemRecords
	 * @param curEmployee
	 * @throws PmsException
	 */
	public void sendItemCountPaper(PensionHospitalizegroupExtend viewedRow) throws PmsException {
		
		 PensionEmployee curEmployee = SessionManager.getCurEmployee();
		 
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
	

	public void setPensionHospitalizegroupMapper(
			PensionHospitalizegroupMapper pensionHospitalizegroupMapper) {
		this.pensionHospitalizegroupMapper = pensionHospitalizegroupMapper;
	}

	public PensionHospitalizegroupMapper getPensionHospitalizegroupMapper() {
		return pensionHospitalizegroupMapper;
	}

	
	
	
	
	
}
