package service.financeManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.employeeManage.PensionEmployeeMapper;
import persistence.financeManage.PensionApprovalbudgetMapper;
import persistence.financeManage.PensionDictBudgettypeMapper;
import persistence.financeManage.PensionFinancialbudgetMapper;
import persistence.system.PensionDeptMapper;
import persistence.system.PensionMessagesMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;
import domain.employeeManage.PensionEmployee;
import domain.employeeManage.PensionEmployeeExample;
import domain.financeManage.PensionApprovalbudget;
import domain.financeManage.PensionApprovalbudgetExample;
import domain.financeManage.PensionDictBudgettype;
import domain.financeManage.PensionDictBudgettypeExample;
import domain.financeManage.PensionFinancialbudget;
import domain.financeManage.PensionFinancialbudgetExample;
import domain.system.PensionDept;
import domain.system.PensionDeptExample;
import domain.system.PensionMessages;
import domain.system.PensionMessagesExample;

/**
 * 日常缴费
 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class BudgetService {

	@Autowired
	private PensionFinancialbudgetMapper pensionFinancialbudgetMapper;
	@Autowired
	private PensionDictBudgettypeMapper pensionDictBudgettypeMapper;
	@Autowired
	private PensionApprovalbudgetMapper pensionApprovalbudgetMapper;
	@Autowired
	private PensionDeptMapper pensionDeptMapper;
	@Autowired
	private PensionEmployeeMapper pensionEmployeeMapper;
	@Autowired
	private PensionMessagesMapper pensionMessagesMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	
	private final static Integer YES=1;
	
	private final static Integer NO=2;
	
	/**
	 * 根据条件查询
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @param approvalResult 审批结果
	 * @param employeeId 申请人
	 * @param budgetId 申请记录主键
	 * @return
	 */
	public List<PensionFinancialbudget> selectMyBudgets(Date startDate,Date endDate, Integer approvalResult, Long employeeId,Long budgetId) {
		List<PensionFinancialbudget> financialbudgets=new ArrayList<PensionFinancialbudget>();
		//根据财务申请记录主键查询---消息进入
		if(budgetId!=null&&!new Long(0).equals(budgetId)){
			PensionFinancialbudget financialbudget=pensionFinancialbudgetMapper.selectByPrimaryKey(budgetId);
			financialbudgets.add(financialbudget);
			budgetId=null;
		}else{
			/*PensionFinancialbudgetExample example=new PensionFinancialbudgetExample();
			if(startDate!=null&&endDate!=null&&approvalResult!=null){
				example.or().andApplytimeBetween(startDate, endDate)
				.andApproveresultEqualTo(approvalResult)
				.andApplyerIdEqualTo(employeeId.intValue())
				.andClearedEqualTo(NO);
			}else if(startDate!=null&&endDate!=null&&approvalResult==null){
				example.or().andApplytimeBetween(startDate, endDate)
				.andApplyerIdEqualTo(employeeId.intValue())
				.andClearedEqualTo(NO);
			}else if(startDate==null&&endDate!=null&&approvalResult!=null){
				example.or().andApplytimeLessThanOrEqualTo(endDate)
				.andApproveresultEqualTo(approvalResult)
				.andApplyerIdEqualTo(employeeId.intValue())
				.andClearedEqualTo(NO);
			}else if(startDate==null&&endDate!=null&&approvalResult==null){
				example.or().andApplytimeLessThanOrEqualTo(endDate)
				.andApplyerIdEqualTo(employeeId.intValue())
				.andClearedEqualTo(NO);
			}else if(startDate!=null&&endDate==null&&approvalResult!=null){
				example.or().andApplytimeGreaterThanOrEqualTo(startDate)
				.andApproveresultEqualTo(approvalResult)
				.andApplyerIdEqualTo(employeeId.intValue())
				.andClearedEqualTo(NO);
			}else if(startDate!=null&&endDate==null&&approvalResult==null){
				example.or().andApplytimeGreaterThanOrEqualTo(startDate)
				.andApplyerIdEqualTo(employeeId.intValue())
				.andClearedEqualTo(NO);
			}else if(startDate==null&&endDate==null&&approvalResult!=null){
				example.or().andApproveresultEqualTo(approvalResult)
				.andApplyerIdEqualTo(employeeId.intValue())
				.andClearedEqualTo(NO);
			}else if(startDate==null&&endDate==null&&approvalResult==null){
				example.or().andApplyerIdEqualTo(employeeId.intValue())
				.andClearedEqualTo(NO);
			}
			example.setOrderByClause("ID DESC");
			financialbudgets= pensionFinancialbudgetMapper.selectByExample(example);*/
			//根据查询条件搜索
			financialbudgets= pensionFinancialbudgetMapper.selectMyApprovalBudgets(startDate, endDate, approvalResult, employeeId);
		}
		return financialbudgets;
	}
	
	/**
	 * 查找符合条件的预算申请
	 */
	public List<PensionFinancialbudget> selectApprovalBudgets(Date startDate,Date endDate, Integer approvalResult, Long employeeId,Long deptId,Long budgetId) throws PmsException {
		List<PensionFinancialbudget> financialbudgets=new ArrayList<PensionFinancialbudget>();
		//从消息进入时，查询该条预算申请
		if(budgetId!=null&&!new Long(0).equals(budgetId)){
			PensionFinancialbudgetExample example=new PensionFinancialbudgetExample();
			example.or().andIdEqualTo(budgetId).andClearedEqualTo(NO);
			PensionFinancialbudget financialbudget=pensionFinancialbudgetMapper.selectApprovalBudget(budgetId);
			if(financialbudget==null||financialbudget.getId()==null){
				throw new PmsException("该记录已删除");
			}else{
				financialbudgets.add(financialbudget);
			}
		}else{
			financialbudgets= pensionFinancialbudgetMapper.selectApprovalBudgets(startDate,endDate,approvalResult,employeeId,deptId);
		}
		return financialbudgets;
	}

	/**
	 * 从字典表中读取预算申请类型列表
	 */
	public List<PensionDictBudgettype> selectBudgetTypes() {
		PensionDictBudgettypeExample example=new PensionDictBudgettypeExample();
		example.or().andInvalidedEqualTo(YES);
		return pensionDictBudgettypeMapper.selectByExample(example);
	}
	/**
	 * 从系统参数中读取预算申请发向的部门
	 */
	public List<PensionDept> selectApproveDepts() throws PmsException {
		String budget_dpt_id = systemConfig.selectProperty("BUDGET_DPT_ID");
		List<Long> dptIdList = new ArrayList<Long>();
		if (budget_dpt_id != null && !budget_dpt_id.isEmpty()) {
			String[] budgetDptId = budget_dpt_id.split(",");
			for (int i = 0; i < budgetDptId.length; i++) {
				dptIdList.add(Long.valueOf(budgetDptId[i]));
			}
		}
		if(dptIdList.size()<1){
			return new ArrayList<PensionDept>();
		}else{
			PensionDeptExample example =new PensionDeptExample();
			example.or().andIdIn(dptIdList);
			return pensionDeptMapper.selectByExample(example);
		}
	}

	/**
	 * 从系统参数中读取预算申请发向的员工
	 */
	public List<PensionEmployee> selectApproveEmployees() throws PmsException {
	     String budget_emp_id = systemConfig.selectProperty("BUDGET_EMP_ID");
	     List<Long> userIdList = new ArrayList<Long>();
	     if(budget_emp_id != null && !budget_emp_id.isEmpty()) {
	    	 String[]  budgetEmpId=budget_emp_id.split(",");
	    	 for(int i = 0; i < budgetEmpId.length; i++) {
	    		 userIdList.add(Long.valueOf(budgetEmpId[i]));
		     }
	     }
	     if(userIdList.size()<1){
	    	 return new ArrayList<PensionEmployee>();
	     }else{
	    	 PensionEmployeeExample example=new PensionEmployeeExample ();
	    	 example.or().andIdIn(userIdList);
	    	 return pensionEmployeeMapper.selectByExample(example);
	     }
	}

	
	/**
	 * 新增一条我的预算申请
	 */
	@Transactional
	public Long insertMyFinancialBudget(PensionFinancialbudget financialBudget,List<PensionDept> approveDepts,List<PensionEmployee> approveEmployees) throws PmsException {
		pensionFinancialbudgetMapper.insertSelective(financialBudget);
		Long id=financialBudget.getId();
		this.insertApproveBudgets(id,approveDepts,approveEmployees);
		return id;
	}

	/**
	 * 插入预算审批记录
	 */
	public void insertApproveBudgets(Long id, List<PensionDept> approveDepts,List<PensionEmployee> approveEmployees) {
		//如果预算审批没有指定审批人，就设置发送到部门
		if(approveEmployees.size()==0){
			for(PensionDept approveDept: approveDepts){
				PensionApprovalbudget approvalBudget=new PensionApprovalbudget();
				approvalBudget.setDeptId(approveDept.getId().intValue());
				approvalBudget.setDeptName(approveDept.getName());
				approvalBudget.setBudgetId(id.intValue());
				approvalBudget.setCleared(NO);
				pensionApprovalbudgetMapper.insertSelective(approvalBudget);
			}
		}else{//否则就直接指定发送到人员
			for(PensionEmployee approveEmployee:approveEmployees){
				PensionApprovalbudget approvalBudget=new PensionApprovalbudget();
				approvalBudget.setApprovalId(approveEmployee.getId().intValue());
				approvalBudget.setApprovalName(approveEmployee.getName());
				approvalBudget.setBudgetId(id.intValue());
				approvalBudget.setCleared(NO);
				approvalBudget.setDeptId(approveEmployee.getDeptId().intValue());
				approvalBudget.setDeptName(pensionDeptMapper.selectByPrimaryKey(approveEmployee.getDeptId()).getName());
				pensionApprovalbudgetMapper.insertSelective(approvalBudget);
			}
		}
		
	}

	/**
	 * 更新我的财务预算记录
	 */
	public void updateMyFinancialBudget(PensionFinancialbudget financialBudget) {
		pensionFinancialbudgetMapper.updateByPrimaryKeySelective(financialBudget);
	}

	/**
	 * 删除财务预算，需要删除财务申请记录和审批记录
	 * @param id
	 */
	@Transactional
	public void deleteFinancialBudget(Long id) {
		//删除财务申请记录
		PensionFinancialbudget temp=new PensionFinancialbudget();
		temp.setId(id);
		temp.setCleared(YES);
		pensionFinancialbudgetMapper.updateByPrimaryKeySelective(temp);
		//删除财务申请审批记录
		this.deleteApprovalBudget(id);
		
	}

	/**
	 * 删除财务申请审批记录
	 * @param id
	 */
	public void deleteApprovalBudget(Long id) {
		PensionApprovalbudgetExample example=new PensionApprovalbudgetExample(); 
		example.or().andBudgetIdEqualTo(id.intValue());
		PensionApprovalbudget temp=new PensionApprovalbudget();
		temp.setCleared(YES);
		pensionApprovalbudgetMapper.updateByExampleSelective(temp, example);
	}


	/**
	 * 发送消息
	 */
	public void sendMessage(Long id, String applyername) throws PmsException {
	     String messageContent="员工 "+applyername+" 提交了一条财务预算申请！";
	     //消息类别
	     String typeIdStr = JavaConfig.fetchProperty("BudgetService.applyBudget");
	     Long messagetypeId = Long.valueOf(typeIdStr);
	     
	     String url;

		 url = messageMessage.selectMessageTypeUrl(messagetypeId);
		 url = url+"?budgetId="+id;
		 
	     String budget_dpt_id = systemConfig.selectProperty("BUDGET_DPT_ID");
	     String budget_emp_id = systemConfig.selectProperty("BUDGET_EMP_ID");
	     List<Long> dptIdList = new ArrayList<Long>();
	     List<Long> userIdList = new ArrayList<Long>();
	     if(budget_emp_id != null && !budget_emp_id.isEmpty()) {
	    	 String[]  budgetEmpId=budget_emp_id.split(",");
	    	 for(int i = 0; i < budgetEmpId.length; i++) {
	    		 userIdList.add(Long.valueOf(budgetEmpId[i]));
		     }
	     }else if(budget_dpt_id != null && !budget_dpt_id.isEmpty()){
	    	 String[] budgetDptId=budget_dpt_id.split(",");
	    	 for(int i = 0; i < budgetDptId.length; i++) {
	    		 dptIdList.add(Long.valueOf(budgetDptId[i]));
		     }
	     }else{
	    	 throw new PmsException("请设置申请发送到的部门或人员");
	     }
	    	 
	     String messagename = "【"+applyername+"】财务预算申请";
	
	     
	     messageMessage.sendMessage(messagetypeId, dptIdList, userIdList, messagename, messageContent, url,"pension_financialbudget",id);
	}

	/**
	 * select某条财务预算的审批记录
	 */
	public PensionApprovalbudget selectApprovalBudget(Long budgetId, Long userId,Long deptId) throws PmsException {
		PensionApprovalbudgetExample example1=new PensionApprovalbudgetExample(); 
		example1.or().andBudgetIdEqualTo(budgetId.intValue())
			.andApprovalIdEqualTo(userId.intValue());
		List<PensionApprovalbudget> list1=pensionApprovalbudgetMapper.selectByExample(example1);
		if(list1.size()>0){
			return list1.get(0);
		}else{
			PensionApprovalbudgetExample example2=new PensionApprovalbudgetExample(); 
			example2.or().andBudgetIdEqualTo(budgetId.intValue())
				.andDeptIdEqualTo(deptId.intValue());
			List<PensionApprovalbudget> list2=pensionApprovalbudgetMapper.selectByExample(example2);
			if(list2.size()>0){
				return list2.get(0);
			}else{
				throw new PmsException("获取审批信息错误");
			}
		}
	}
	
	/**
	 * 更新财务审批记录
	 */
	public PensionFinancialbudget updateApprovalBudget(PensionApprovalbudget approvalBudget, Long employeeId, Long deptId) throws PmsException {
		pensionApprovalbudgetMapper.updateByPrimaryKeySelective(approvalBudget);
		this.updateMessageProcessor(approvalBudget.getBudgetId(),employeeId,deptId);
		return this.updateFinancialBudget(approvalBudget.getBudgetId(),employeeId,deptId);
	}


	/**
	 * 更新财务申请主记录的审批结果。只有当所有的审批结果都是同意时，主记录结果才是同意
	 */
	public PensionFinancialbudget updateFinancialBudget(Integer budgetId, Long employeeId, Long deptId) throws PmsException {
		PensionApprovalbudgetExample example=new PensionApprovalbudgetExample();
		example.or().andBudgetIdEqualTo(budgetId);
		List<PensionApprovalbudget> approvalBudgets=pensionApprovalbudgetMapper.selectByExample(example);
		boolean flag=true;
		Integer result=YES;//将审批结果的初始状态置为 同意
		String approveNote="";
		for(PensionApprovalbudget approvalBudget:approvalBudgets){
			//只要有一条审批结果为不同意，结果就是不同意
			if(NO.equals(approvalBudget.getResult())){
				result=NO;
			}
			//如果有一条审批记录没有审批，就不设置主记录的审批结果
			if(approvalBudget.getResult()==null){
				flag=false;
			}else{
				//设置审批意见：将所有的审批意见全部显示
				if(approvalBudget.getNote()!=null && !"".equals(approvalBudget.getNote().trim())){
					if(approvalBudget.getApprovalName()!=null){
						approveNote+=approvalBudget.getApprovalName();
					}else{
						approveNote+=approvalBudget.getDeptName();
					}
					approveNote+=" : "+approvalBudget.getNote()+" ; ";
				}
			}
		}
		if(flag){
			PensionFinancialbudget financialbudget=new PensionFinancialbudget(); 
			financialbudget.setId(budgetId.longValue());
			financialbudget.setApproveresult(result);
			financialbudget.setApprovenote(approveNote);
			financialbudget.setApprovetime(new Date());
			pensionFinancialbudgetMapper.updateByPrimaryKeySelective(financialbudget);
			return pensionFinancialbudgetMapper.selectApprovalBudget(budgetId.longValue());
		}else{
			throw new PmsException("在其他用户提交之后，您将看到最终的审批结果！");
		}
	}

	/**
	 * 消息处理后关闭
	 */
	public void updateMessageProcessor(Integer budgetId, Long employeeId,Long deptId) {
		//消息类别
	     String typeIdStr = JavaConfig.fetchProperty("BudgetService.applyBudget");
	     Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(employeeId, messagetypeId, "pension_financialbudget", budgetId.longValue(), deptId);
		
	}

	/**
	 * check 要删除的财务申请记录
	 * 如果申请记录已经有人审批，就不能再删除
	 */
	public boolean checkDeleteData(Long id) {
		PensionApprovalbudgetExample example=new PensionApprovalbudgetExample();
		example.or().andBudgetIdEqualTo(id.intValue());
		List<PensionApprovalbudget> approvalBudgets=pensionApprovalbudgetMapper.selectByExample(example);
		boolean flag=false;
		for(PensionApprovalbudget approvalBudget: approvalBudgets){
			if(approvalBudget.getResult()!=null){
				flag=true;
			}
		}
		return flag;
	}

	/**
	 * 发放预算
	 */
	public void grantBudget(Long budgetId,Long userId, String username, Date givetime) {
		PensionFinancialbudget financialBudget=new PensionFinancialbudget();
		financialBudget.setId(budgetId);
		financialBudget.setGiverId(userId.intValue());
		financialBudget.setGivername(username);
		financialBudget.setGivetime(givetime);
		pensionFinancialbudgetMapper.updateByPrimaryKeySelective(financialBudget);
	}

	/**
	 * 判断该消息是否已发送过
	 * @param id
	 * @return
	 */
	public boolean checkSendMessages(Long id) {
		PensionMessagesExample example=new PensionMessagesExample();
		example.or().andTableNameEqualTo("pension_financialbudget")
			.andTableKeyIdEqualTo(id)
			.andClearedEqualTo(NO);
		List<PensionMessages> pensionMessages=pensionMessagesMapper.selectByExample(example);
		if(pensionMessages.size()>0){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 根据财务申请记录主键，查询其审批记录
	 * @param id
	 * @return
	 */
	public List<PensionApprovalbudget> selectApprovalBudgets(Long id) {
		PensionApprovalbudgetExample example=new PensionApprovalbudgetExample();
		example.or().andBudgetIdEqualTo(id.intValue());
		List<PensionApprovalbudget> approvalbudgets=pensionApprovalbudgetMapper.selectByExample(example);
		for(PensionApprovalbudget approvalbudget: approvalbudgets){
			if(approvalbudget.getDeptName()==null||approvalbudget.getDeptName().equals("")){
				approvalbudget.setDeptName(this.selectDeptName(approvalbudget.getApprovalId()));
			}
		}
		return approvalbudgets;
	}

	/**
	 * 根据部门编号，查询部门名称
	 * @param approvalId
	 * @return
	 */
	public String selectDeptName(Integer approvalId) {
		PensionEmployee emplyee=pensionEmployeeMapper.selectByPrimaryKey(approvalId.longValue());
		PensionDept dept=pensionDeptMapper.selectByPrimaryKey(emplyee.getDeptId());
		if(dept!=null){
			return dept.getName();
		}else{
			return "";
		}
	}

	/**
	 * 根据申请类型查询类型名称
	 * @param budgettypeId
	 * @return
	 */
	public String selectBudgetTypeName(Long budgettypeId) {
		return pensionDictBudgettypeMapper.selectByPrimaryKey(budgettypeId).getType();
	}

	public boolean checkSelectDate(Long budgetId, Long empId, Long deptId) throws PmsException {
		PensionApprovalbudgetExample example=new PensionApprovalbudgetExample();
		example.or().andBudgetIdEqualTo(budgetId.intValue()).andDeptIdEqualTo(deptId.intValue());
		List<PensionApprovalbudget> approvalbudgets=pensionApprovalbudgetMapper.selectByExample(example);
		if(approvalbudgets.size()>0){
			if(approvalbudgets.get(0).getApprovalId()!=null&&empId.intValue()!=approvalbudgets.get(0).getApprovalId()){
				return true;
			}else{
				return false;
			}
		}else{
			throw new PmsException("获取预算申请的审批信息时出错！");
		}
	}

	
}
