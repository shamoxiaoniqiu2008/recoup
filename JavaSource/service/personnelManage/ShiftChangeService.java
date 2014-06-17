package service.personnelManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.hrManage.PensionShiftchangeRecordMapper;
import persistence.system.PensionDeptMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;
import domain.hrManage.PensionShiftchangeRecord;
import domain.hrManage.PensionShiftchangeRecordExample;
import domain.system.PensionDept;

/**
 * 日常缴费
 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class ShiftChangeService {

	
	@Autowired
	private PensionShiftchangeRecordMapper pensionShiftchangeRecordMapper; 
	@Autowired
	private PensionDeptMapper pensionDeptMapper;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private MessageMessage messageMessage;
	
	private final static Integer NO_FLAG=2;//否
	private final static Integer YES_FLAG=1;//是
	
	//读取系统参数中的 可以查看财务结帐的角色
	public List<Long> selectShiftChangeRoles(String shiftChangeCheckRoleId) throws PmsException {
		List<Long> roles=new ArrayList<Long>();
		try {
			String rolesStr=systemConfig.selectProperty(shiftChangeCheckRoleId);
			  if(rolesStr != null && !rolesStr.isEmpty()) {
			    	 String[]  rolesArr=rolesStr.split(",");
			    	 for(int i = 0; i < rolesArr.length; i++) {
			    		 roles.add(Long.valueOf(rolesArr[i]));
				     }
			     }
		} catch (NumberFormatException e) {
			throw new PmsException("系统参数设置可以查看交班记录的角色有误！",new Throwable("SHIFT_CHANGE_CHECK_ROLE_ID"));
		} catch (PmsException e) {
			throw new PmsException("系统参数还没有设置可以查看交班记录的角色！",new Throwable("SHIFT_CHANGE_CHECK_ROLE_ID"));
		}
		return roles;
	}


	/**
	 * 根据起止时间查询 本人是交班人或者接班人的交接班记录
	 * @param startDate
	 * @param endDate
	 * @param empId
	 * @param shiftFlag 交接班标识 1：交班；2 接班
	 * @param recordId 消息进入，记录主键
	 * @return
	 * @throws PmsException 
	 */
	public List<PensionShiftchangeRecord> search(Date startDate, Date endDate, Long empId, Integer shiftFlag) {
		
		return pensionShiftchangeRecordMapper.selectShiftchangeRecords(startDate,endDate,empId,shiftFlag);
		
	}
	
	public String selectDeptName(Long deptId) {
		PensionDept dept=pensionDeptMapper.selectByPrimaryKey(deptId);
		if(dept!=null){
			return dept.getName();
		}else{
			return "";
		}
	}
	
	/**
	 * 消息处理后关闭
	 */
	public void updateMessageProcessor(Long recordId, Long employeeId,Long deptId) {
		PensionShiftchangeRecord record = new PensionShiftchangeRecord();
		record.setId(recordId);
		record.setAffirmed(YES_FLAG);
		pensionShiftchangeRecordMapper.updateByPrimaryKeySelective(record);
		//消息类别
	     String typeIdStr = JavaConfig.fetchProperty("ShiftChangeService.messagetypeId");
	     Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(employeeId, messagetypeId, "pension_shiftchange_record", recordId, deptId);
		
	}


	public PensionShiftchangeRecord insertShiftChangeRecord(PensionShiftchangeRecord selectShiftChangeRecord) {
		pensionShiftchangeRecordMapper.insertSelective(selectShiftChangeRecord);
		return selectShiftChangeRecord;
		
	}


	public void delete(Long id) {
		PensionShiftchangeRecord record=new PensionShiftchangeRecord();
		record.setId(id);
		record.setCleared(YES_FLAG);
		pensionShiftchangeRecordMapper.updateByPrimaryKeySelective(record);
	}


	public void updateShiftChangeRecord(PensionShiftchangeRecord selectShiftChangeRecord) {
		pensionShiftchangeRecordMapper.updateByPrimaryKeySelective(selectShiftChangeRecord);
	}

	/**
	 * 提交
	 * @param recordId
	 * @param empName
	 * @param successorId
	 * @throws PmsException
	 */
	public void send(Long recordId, String empName, Long successorId) throws PmsException{
		PensionShiftchangeRecord record = new PensionShiftchangeRecord();
		record.setId(recordId);
		record.setSended(YES_FLAG);
		pensionShiftchangeRecordMapper.updateByPrimaryKeySelective(record);
		this.sendMessage(recordId, empName, successorId);
	}

	/**
	 * 提交交接班记录，发送消息
	 * @param recordId
	 * @param empName
	 * @param successorId 
	 * @throws PmsException 
	 */
	public void sendMessage(Long recordId, String empName, Long successorId) throws PmsException {
		String messageContent="员工 "+empName+" 提交了一条交接班记录！";
	     //消息类别
	     String typeIdStr = JavaConfig.fetchProperty("ShiftChangeService.messagetypeId");
	     Long messagetypeId = Long.valueOf(typeIdStr);
	     
	     String url;

		 try {
			url = messageMessage.selectMessageTypeUrl(messagetypeId);
			url = url+"?recordId="+recordId;
			
			List<Long> dptIdList = new ArrayList<Long>();
			List<Long> userIdList = new ArrayList<Long>();
			userIdList.add(successorId);
			
			String messagename = "【"+empName+"】交接班记录";
			
			
			messageMessage.sendMessage(messagetypeId, dptIdList, userIdList, messagename, messageContent, url,"pension_shiftchange_record",recordId);
		} catch (PmsException e) {
			throw new PmsException("请设置交接班记录消息类型！");
		}
		
	}


	/**
	 * 根据记录主键查询
	 * @param recordId
	 * @return
	 */
	public PensionShiftchangeRecord selectShiftChangeRecord(Long recordId) {
		return pensionShiftchangeRecordMapper.selectByPrimaryKey(recordId);
	}

}
