package service.reportManage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.configureManage.PensionBuildingMapper;
import persistence.dictionary.PensionDicAccidenttypeMapper;
import persistence.dictionary.PensionDicOldertypeMapper;
import persistence.dictionary.PensionDicReporttypeMapper;
import persistence.employeeManage.PensionEmployeeMapper;
import persistence.olderManage.PensionAccidentRecordMapper;
import persistence.olderManage.PensionDepartregisterMapper;
import persistence.olderManage.PensionLivingrecordMapper;
import persistence.olderManage.PensionOlderMapper;
import persistence.receptionManage.PensionLivingapplyMapper;
import persistence.reportManage.PensionAccidentAmountMapper;
import persistence.reportManage.PensionBuildingStaffamountMapper;
import persistence.reportManage.PensionOldertypeAmountMapper;
import persistence.reportManage.PensionWeeklyReportMapper;
import persistence.system.PensionDeptMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;
import domain.configureManage.PensionBuilding;
import domain.configureManage.PensionBuildingExample;
import domain.dictionary.PensionDicAccidenttype;
import domain.dictionary.PensionDicAccidenttypeExample;
import domain.dictionary.PensionDicOldertype;
import domain.dictionary.PensionDicOldertypeExample;
import domain.dictionary.PensionDicReporttype;
import domain.dictionary.PensionDicReporttypeExample;
import domain.employeeManage.PensionEmployeeExample;
import domain.olderManage.PensionDepartregisterExample;
import domain.olderManage.PensionDosage;
import domain.olderManage.PensionLivingrecordExample;
import domain.olderManage.PensionOlderExample;
import domain.reportManage.PensionAccidentAmount;
import domain.reportManage.PensionAccidentAmountExample;
import domain.reportManage.PensionBuildingStaffamount;
import domain.reportManage.PensionBuildingStaffamountExample;
import domain.reportManage.PensionOldertypeAmount;
import domain.reportManage.PensionOldertypeAmountExample;
import domain.reportManage.PensionWeeklyReport;
import domain.reportManage.PensionWeeklyReportExample;
import domain.system.PensionDept;
import domain.system.PensionRole;

/**
 * 总体护理情况周报
 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class TotalNurseWeeklyService {
	
	
	
	@Autowired
	private PensionOlderMapper pensionOlderMapper;
	@Autowired
	private PensionDicOldertypeMapper pensionDicOldertypeMapper;
	@Autowired
	private PensionOldertypeAmountMapper pensionOldertypeAmountMapper;
	@Autowired
	private PensionEmployeeMapper pensionEmployeeMapper;
	@Autowired
	private PensionBuildingMapper pensionBuildingMapper;
	@Autowired
	private PensionBuildingStaffamountMapper pensionBuildingStaffamountMapper;
	@Autowired
	private PensionAccidentRecordMapper pensionAccidentRecordMapper;
	@Autowired
	private PensionDicAccidenttypeMapper pensionDicAccidenttypeMapper;
	@Autowired
	private PensionDepartregisterMapper pensionDepartregisterMapper;
	@Autowired
	private PensionLivingapplyMapper pensionLivingapplyMapper;
	@Autowired
	private PensionDicReporttypeMapper pensionDicReporttypeMapper;
	@Autowired
	private PensionLivingrecordMapper pensionLivingrecordMapper;
	@Autowired
	private PensionDeptMapper pensionDeptMapper;
	@Autowired
	private PensionWeeklyReportMapper pensionWeeklyReportMapper;
	@Autowired
	private PensionAccidentAmountMapper pensionAccidentAmountMapper;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private MessageMessage messageMessage;
	
	private static final Integer OLDER_STATUSES_INHOSP = 3;//pension_older 状态 在院
	private static final Integer OLDER_STATUSES_LEAVE = 4;//pension_older 状态 请假
	
	private static final Integer YES_FLAG=1;//是
	private static final Integer NO_FLAG=2;//否
	
	private static final Integer DEPART_REGISTER_LEFT = 6;//pension_departregister 已离院

	/**
	 * 读取系统参数中的 系统的开始年份
	 * @param startYearStr
	 * @return
	 * @throws PmsException
	 */
	public Integer selectstartYear(String startYearStr) throws PmsException {
		Integer startYear=null;
		try {
			String yearStr = systemConfig.selectProperty(startYearStr);
			startYear=new Integer(yearStr);
		} catch (NumberFormatException e) {
			throw new PmsException("系统参数设置系统起始年份有误！");
		} catch (PmsException e) {
			throw new PmsException("系统参数还没有设置系统起始年份！");
		}
		return startYear;
	}
	
	/**
	 * 读取系统参数中的 系统的开始年份
	 * @param reportType
	 * @return
	 * @throws PmsException
	 */
	public Long selectRepertType(String reportType) throws PmsException {
		Long reportTypeId=null;
		try {
			String reportTypeStr = systemConfig.selectProperty(reportType);
			reportTypeId=new Long(reportTypeStr);
		} catch (NumberFormatException e) {
			throw new PmsException("系统参数设置默认报表类型有误！");
		} catch (PmsException e) {
			throw new PmsException("系统参数还没有设置默认报表类型！");
		}
		return reportTypeId;
	}


	/**
	 * 判断指定年份和指定周数的周报是否存在	
	 * @param selectYear
	 * @param selectWeek
	 * @return
	 */
	public boolean checkExisted(Integer selectYear, Integer selectWeek) {
		PensionWeeklyReportExample example=new PensionWeeklyReportExample();
		example.or().andYearNumberEqualTo(selectYear)
			.andWeekNumberEqualTo(selectWeek);
		List<PensionWeeklyReport> weeklyReports=pensionWeeklyReportMapper.selectByExample(example);
		if(weeklyReports.size()>0){
			return true;//已存在该周的周报
		}else{
			return false;//还不存在该周的周报
		}
	}

	/**
	 * 根据报表编号查询
	 * @param id
	 * @return
	 */
	private List<PensionBuilding> selectBuildings(Long id) {
		List<PensionBuilding> buildings=new ArrayList<PensionBuilding>();
		PensionBuildingStaffamountExample example=new PensionBuildingStaffamountExample();
		example.or().andClearedEqualTo(NO_FLAG).andReportIdEqualTo(id);
		List<PensionBuildingStaffamount> buildingStaffAmounts=pensionBuildingStaffamountMapper.selectByExample(example);
		for(PensionBuildingStaffamount buildingStaffAmount: buildingStaffAmounts){
			PensionBuilding building=new PensionBuilding();
			building.setId(buildingStaffAmount.getBuildingId());
			building.setName(buildingStaffAmount.getBuildingname());
			building.setOldernumber(buildingStaffAmount.getOlderAmount());
			buildings.add(building);
		}
		return buildings;
	}

	private List<PensionDicAccidenttype> selectAccidentTypes(Long id) {
		List<PensionDicAccidenttype> accidentTypes=new ArrayList<PensionDicAccidenttype>();
		PensionAccidentAmountExample example=new PensionAccidentAmountExample();
		example.or().andClearedEqualTo(NO_FLAG).andReportIdEqualTo(id);
		List<PensionAccidentAmount> accidentAmounts=pensionAccidentAmountMapper.selectByExample(example);
		for(PensionAccidentAmount accidentAmount: accidentAmounts){
			PensionDicAccidenttype accidentType=new PensionDicAccidenttype();
			accidentType.setId(accidentAmount.getAccidentId());
			accidentType.setAccidenttypeName(accidentAmount.getAccidentName());
			accidentType.setNum(accidentAmount.getOlderNum());
			accidentTypes.add(accidentType);
		}
		return accidentTypes;
	}

	private List<PensionDicOldertype> selectOlderTypes(Long id) {
		List<PensionDicOldertype> olderTypes=new ArrayList<PensionDicOldertype>();
		PensionOldertypeAmountExample example=new PensionOldertypeAmountExample();
		example.or().andClearedEqualTo(NO_FLAG).andReportIdEqualTo(id);
		List<PensionOldertypeAmount> olderTypeAmounts=pensionOldertypeAmountMapper.selectByExample(example);
		for(PensionOldertypeAmount olderTypeAmount: olderTypeAmounts){
			PensionDicOldertype olderType=new PensionDicOldertype();
			olderType.setId(olderTypeAmount.getOldertypeId());
			olderType.setOldertypeName(olderTypeAmount.getOldertypeName());
			olderType.setNum(olderTypeAmount.getOlderAmount());
			olderTypes.add(olderType);
		}
		return olderTypes;
	}

	public Map<String,Object> createWeeklyReport(Date startDate, Date endDate) throws PmsException {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(endDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 0);
		endDate=calendar.getTime();
		calendar.setTime(startDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		startDate=calendar.getTime();
		//查找结束时间点的在院的老人数
		/*PensionOlderExample olderExample=new PensionOlderExample();
		List<Integer> olderStatuses=new ArrayList<Integer>();
		olderStatuses.add(OLDER_STATUSES_INHOSP);
		olderStatuses.add(OLDER_STATUSES_LEAVE);
		olderExample.or().andStatusesIn(olderStatuses).andClearedEqualTo(NO_FLAG);*/
		//开始时间点的在院老人总数
		Integer startOlderNum=new Integer(0);
		List<PensionBuilding> startBuildings=pensionBuildingMapper.selectOlderLiquidityByDate(startDate);
		for(PensionBuilding startBuilding: startBuildings){
			startOlderNum+=startBuilding.getOldernumber();
		}
		//结束时间点的在院老人总数
		Integer endOlderNum=new Integer(0);
		List<PensionBuilding> endBuildings=pensionBuildingMapper.selectOlderLiquidityByDate(endDate);
		for(PensionBuilding endBuilding: endBuildings){
			endOlderNum+=endBuilding.getOldernumber();
		}
		//该周内新入住老人数
		Integer newInNum=pensionLivingrecordMapper.selectNewInNum(startDate,endDate);
		//该周内新出院老人数
		Integer newOutNum=pensionDepartregisterMapper.selectNewOutNum(startDate,endDate);
		/*//查找在起止时间内入院的老人数
		PensionLivingrecordExample livingRecordExample=new PensionLivingrecordExample();
		livingRecordExample.or().andVisittimeGreaterThanOrEqualTo(startDate)
			.andVisittimeLessThanOrEqualTo(endDate)
			.andClearedEqualTo(NO_FLAG);
		Integer inOlderNum=pensionLivingrecordMapper.countByExample(livingRecordExample);
		//查找在起止时间内离院的老人数
		PensionDepartregisterExample departRegisterExample=new PensionDepartregisterExample();
		departRegisterExample.or().andClearedEqualTo(NO_FLAG)
			.andLeavetimeGreaterThanOrEqualTo(startDate)
			.andLeavetimeLessThanOrEqualTo(endDate)
			.andIsagreeEqualTo(DEPART_REGISTER_LEFT);
		Integer outOlderNum=pensionDepartregisterMapper.countByExample(departRegisterExample);
		//起始时间点处的在院老人数
		Integer startOlderNum=endOlderNum-inOlderNum+outOlderNum;*/
		//护理部员工总数
		List<Long> nurseDeptIds=this.selectNurseDeptIdReport("NURSE_DEPT_ID_REPORT");
		PensionEmployeeExample employeeExample=new PensionEmployeeExample();
		employeeExample.or().andDeptIdIn(nurseDeptIds)
			.andClearedEqualTo(NO_FLAG);
		Integer nurseNum=pensionEmployeeMapper.countByExample(employeeExample);
		//定岗护理员数目
		Integer fixedNurseNum=pensionLivingrecordMapper.countByDistinctNurseId();
		//每种老人类型的数量
		List<PensionDicOldertype> olderTypes=pensionDicOldertypeMapper.selectDicOlderTypes(startDate,endDate);
		//每种事故类型的老人数量
		List<PensionDicAccidenttype> accidentTypes=pensionDicAccidenttypeMapper.selectDicAccidentTypes(startDate,endDate);
		//每座大厦中的老人数
		List<PensionBuilding> buildings=pensionBuildingMapper.selectInOlderNumInPeriod(startDate,endDate);
		Map<String,Object> totalNurseWeeklyMap=new HashMap<String,Object>();
		totalNurseWeeklyMap.put("StartOlderNum",startOlderNum );
		totalNurseWeeklyMap.put("EndOlderNum",endOlderNum );
		totalNurseWeeklyMap.put("NewInNum",newInNum);
		totalNurseWeeklyMap.put("NewOutNum",newOutNum);
		totalNurseWeeklyMap.put("NurseNum",nurseNum );
		totalNurseWeeklyMap.put("FixedNurseNum",fixedNurseNum );
		totalNurseWeeklyMap.put("OlderTypes",olderTypes );
		totalNurseWeeklyMap.put("AccidentTypes",accidentTypes );
		totalNurseWeeklyMap.put("Buildings",buildings );
		return totalNurseWeeklyMap;
	}
	
	//读取系统参数中的 护理部编号
		public List<Long> selectNurseDeptIdReport(String nurseDeptIdReport) throws PmsException {
			List<Long> nurseDeptIds=new ArrayList<Long>();
			try {
				String nurseDeptIdsStr=systemConfig.selectProperty(nurseDeptIdReport);
				  if(nurseDeptIdsStr!= null && !nurseDeptIdsStr.isEmpty()) {
				    	 String[]  nurseDeptIdsArr=nurseDeptIdsStr.split(",");
				    	 for(int i = 0; i < nurseDeptIdsArr.length; i++) {
				    		 nurseDeptIds.add(Long.valueOf(nurseDeptIdsArr[i]));
					     }
				     }
			} catch (NumberFormatException e) {
				throw new PmsException("系统参数设置护理部编号有误！",new Throwable("SHIFT_CHANGE_CHECK_ROLE_ID"));
			} catch (PmsException e) {
				throw new PmsException("系统参数还没有设置护理部编号！",new Throwable("SHIFT_CHANGE_CHECK_ROLE_ID"));
			}
			return nurseDeptIds;
		}	
		
		public String selectDeptName(Long deptId) {
			PensionDept dept=pensionDeptMapper.selectByPrimaryKey(deptId);
			if(dept!=null){
				return dept.getName();
			}else{
				return "";
			}
		}

		public List<PensionDicReporttype> selectReportTypes() {
			PensionDicReporttypeExample example=new PensionDicReporttypeExample();
			example.or().andClearedEqualTo(NO_FLAG);
			return pensionDicReporttypeMapper.selectByExample(example);
		}
		
		public String selectReportTypeName(Long id) {
			PensionDicReporttype reportType=pensionDicReporttypeMapper.selectByPrimaryKey(id);
			if(reportType!=null){
				return reportType.getReporttypeName();
			}else{
				return "";
			}
		}

		@Transactional
		public Long saveWeeklyReport(PensionWeeklyReport weeklyReport,List<PensionDicOldertype> olderTypes,List<PensionDicAccidenttype> accidentTypes,List<PensionBuilding> buildings) {
			pensionWeeklyReportMapper.insertSelective(weeklyReport);
			this.insertOlderTypes(weeklyReport.getId(),olderTypes);
			this.insertAccidenttypes(weeklyReport.getId(),accidentTypes);
			this.insertBuildings(weeklyReport.getId(),buildings);
			return weeklyReport.getId();
		}           

		@Transactional
		private void insertBuildings(Long id, List<PensionBuilding> buildings) {
			for(PensionBuilding building: buildings){
				PensionBuildingStaffamount buildingStaffamount=new PensionBuildingStaffamount();
				buildingStaffamount.setBuildingId(building.getId());
				buildingStaffamount.setBuildingname(building.getName());
				buildingStaffamount.setOlderAmount(building.getOldernumber());
				buildingStaffamount.setReportId(id);
				pensionBuildingStaffamountMapper.insertSelective(buildingStaffamount);
			}
			
		}

		@Transactional
		private void insertAccidenttypes(Long id,List<PensionDicAccidenttype> accidentTypes) {
			for(PensionDicAccidenttype accidentType: accidentTypes){
				PensionAccidentAmount pensionAccidentAmount=new PensionAccidentAmount();
				pensionAccidentAmount.setAccidentId(accidentType.getId());
				pensionAccidentAmount.setAccidentName(accidentType.getAccidenttypeName());
				pensionAccidentAmount.setReportId(id);
				pensionAccidentAmount.setOlderNum(accidentType.getNum());
				pensionAccidentAmountMapper.insertSelective(pensionAccidentAmount);
			}			
		}

		@Transactional
		private void insertOlderTypes(Long id,List<PensionDicOldertype> olderTypes) {
			for(PensionDicOldertype olderType: olderTypes){
				PensionOldertypeAmount olderTypeAmount=new PensionOldertypeAmount();
				olderTypeAmount.setOlderAmount(olderType.getNum());
				olderTypeAmount.setOldertypeId(olderType.getId());
				olderTypeAmount.setOldertypeName(olderType.getOldertypeName());
				olderTypeAmount.setReportId(id);
				pensionOldertypeAmountMapper.insertSelective(olderTypeAmount);
			}
			
		}



		public Map<String,Object> selectWeeklyReport(Integer selectYear, Integer selectWeek, Long reportId) throws PmsException {
			PensionWeeklyReport weeklyReport=new PensionWeeklyReport();
			Map<String,Object> weeklyReportMap=new HashMap<String,Object>();
			//消息定位 按主键查询
			if(reportId!=null){
				weeklyReport=pensionWeeklyReportMapper.selectByPrimaryKey(reportId);
			}else{//按 指定年数 指定周次 查询
				PensionWeeklyReportExample example=new PensionWeeklyReportExample();
				example.or().andYearNumberEqualTo(selectYear)
				.andWeekNumberEqualTo(selectWeek);
				List<PensionWeeklyReport> weeklyReports=pensionWeeklyReportMapper.selectByExample(example);
				if(weeklyReports.size()>0){
					weeklyReport=weeklyReports.get(0);
				}else{
					throw new PmsException("您查询的周报不存在！");
				}
			}
			List<PensionDicOldertype> olderTypes=this.selectOlderTypes(weeklyReport.getId());
			List<PensionDicAccidenttype> accidentTypes=this.selectAccidentTypes(weeklyReport.getId());
			List<PensionBuilding> buildings=this.selectBuildings(weeklyReport.getId());
			weeklyReportMap.put("WEEKLYREPORT",weeklyReport );
			weeklyReportMap.put("OlderTypes",olderTypes );
			weeklyReportMap.put("AccidentTypes",accidentTypes );
			weeklyReportMap.put("Buildings",buildings );
			return weeklyReportMap;
			
		}
		
		public List<PensionDicOldertype> selectDicOlderTypes(){
			PensionDicOldertypeExample example=new PensionDicOldertypeExample();
			example.or().andClearedEqualTo(NO_FLAG);
			return pensionDicOldertypeMapper.selectByExample(example);
		}
		
		public List<PensionDicAccidenttype> selectDicAccidenttypes(){
			PensionDicAccidenttypeExample example=new PensionDicAccidenttypeExample();
			example.or().andClearedEqualTo(NO_FLAG);
			return pensionDicAccidenttypeMapper.selectByExample(example);
		}
		
		public List<PensionBuilding> selectBuildings(){
			PensionBuildingExample example=new PensionBuildingExample();
			example.or().andClearedEqualTo(NO_FLAG);
			return pensionBuildingMapper.selectByExample(example);
		}

		/**
		 * 发送消息
		 * @param id
		 * @param name
		 * @throws PmsException
		 */
		public void sendMessages(Long id, String name) throws PmsException {
			 String messageContent="员工 "+name+" 提交了一条总体护理情况周报！";
		     //消息类别
		     String typeIdStr = JavaConfig.fetchProperty("TotalNurseWeeklyService.sendWeeklyReport");
		     Long messagetypeId = Long.valueOf(typeIdStr);
		     
		     String url;

			 url = messageMessage.selectMessageTypeUrl(messagetypeId);
			 url = url+"?reportId="+id;
			 
		     String nurse_dpt_id = systemConfig.selectProperty("NURSE_WEEKLY_REPORT_DEPT_ID");
		     String nurse_emp_id = systemConfig.selectProperty("NURSE_WEEKLY_REPORT_EMP_ID");
		     List<Long> dptIdList = new ArrayList<Long>();
		     List<Long> userIdList = new ArrayList<Long>();
		     if(nurse_emp_id != null && !nurse_emp_id.isEmpty()) {
		    	 String[]  nurseEmpId=nurse_emp_id.split(",");
		    	 for(int i = 0; i < nurseEmpId.length; i++) {
		    		 userIdList.add(Long.valueOf(nurseEmpId[i]));
			     }
		     }else if(nurse_dpt_id != null && !nurse_dpt_id.isEmpty()){
		    	 String[] nurseDptId=nurse_dpt_id.split(",");
		    	 for(int i = 0; i < nurseDptId.length; i++) {
		    		 dptIdList.add(Long.valueOf(nurseDptId[i]));
			     }
		     }else{
		    	 throw new PmsException("请设置申请发送到的部门或人员");
		     }
		    	 
		     String messagename = "【"+name+"】总体护理情况周报";
		
		     
		     messageMessage.sendMessage(messagetypeId, dptIdList, userIdList, messagename, messageContent, url,"pension_weekly_report",id);
			
		     this.updateWeeklyReport(id);
		}
		
		/**
		 * 将该条周报设置为 已发送
		 * @param id
		 */
		public void updateWeeklyReport(Long id) {
			PensionWeeklyReport weeklyReport=new PensionWeeklyReport();
			weeklyReport.setId(id);
			weeklyReport.setSendFlag(YES_FLAG);
			pensionWeeklyReportMapper.updateByPrimaryKeySelective(weeklyReport);
			
		}

		public List<Long> selectRoles(String nurseWeeklyReportRoleId) throws PmsException {
			List<Long> nurseWeeklyReportRoleIds=new ArrayList<Long>();
			try {
				String nurseWeeklyReportRoleIdStr=systemConfig.selectProperty(nurseWeeklyReportRoleId);
				  if(nurseWeeklyReportRoleIdStr!= null && !nurseWeeklyReportRoleIdStr.isEmpty()) {
				    	 String[]  nurseWeeklyReportRoleIdArr=nurseWeeklyReportRoleIdStr.split(",");
				    	 for(int i = 0; i < nurseWeeklyReportRoleIdArr.length; i++) {
				    		 nurseWeeklyReportRoleIds.add(Long.valueOf(nurseWeeklyReportRoleIdArr[i]));
					     }
				     }
			} catch (NumberFormatException e) {
				throw new PmsException("系统参数设置有权生成护理周报的角色有误！");
			} catch (PmsException e) {
				throw new PmsException("系统参数还没有设置有权生成护理周报的角色！");
			}
			return nurseWeeklyReportRoleIds;
		}

		public void updateWeeklyReport(PensionWeeklyReport weeklyReport) {
			pensionWeeklyReportMapper.updateByPrimaryKeySelective(weeklyReport);
			
		}

}
