package service.employee;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.employee.PensionEmployeeMapper;
import persistence.system.PensionDeptMapper;
import persistence.system.PensionSysUserMapper;
import domain.employee.PensionEmployee;
import domain.employee.PensionEmployeeExample;
import domain.system.PensionDept;
import domain.system.PensionDeptExample;
import domain.system.PensionSysUser;
import domain.system.PensionSysUserExample;

/**
 * 员工管理
 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class EmployeeManageService {

	@Autowired
	private PensionEmployeeMapper pensionEmployeeMapper;
	@Autowired
	private PensionSysUserMapper pensionSysUserMapper;
	@Autowired
	private PensionDeptMapper pensionDeptMapper;
	
	private final static Integer YES_FLAG=1;//否
	private final static Integer NO_FLAG=2;//否

	public List<PensionEmployee> search(Long empId) {
		List<PensionEmployee> employees=new ArrayList<PensionEmployee>();
		if(empId == null){
			PensionEmployeeExample example=new PensionEmployeeExample();
			example.or().andClearedEqualTo(NO_FLAG);
			employees=pensionEmployeeMapper.selectByExample(example);
		}else{
			PensionEmployee employee=pensionEmployeeMapper.selectByPrimaryKey(empId);
			employees.add(employee);
		}
		return employees;
	}

	/**
	 * 保存一条员工记录
	 * @param employee
	 * @return 
	 */
	public Long saveEmployee(PensionEmployee employee) {
		pensionEmployeeMapper.insertSelective(employee);
		return employee.getId();
		
	}

	/**
	 * 更新一条员工记录
	 * @param employee
	 */
	public void updateEmployee(PensionEmployee employee) {
		pensionEmployeeMapper.updateByPrimaryKeySelective(employee);
		
	}

	/**
	 * 逻辑删除指定的员工记录
	 * @param empId
	 */
	public void deleteEmployee(Long empId) {
		//将员工表pension_employee表中该用户逻辑删除
		PensionEmployee tempEmp=new PensionEmployee();
		tempEmp.setId(empId);
		tempEmp.setCleared(YES_FLAG);
		//将该员工对应的帐号锁定
		pensionEmployeeMapper.updateByPrimaryKeySelective(tempEmp);
		PensionSysUserExample example=new PensionSysUserExample();
		example.or().andEmployeeIdEqualTo(empId);
		PensionSysUser sysUser=new PensionSysUser();
		sysUser.setLocked(YES_FLAG);
		pensionSysUserMapper.updateByExampleSelective(sysUser, example);
	}

	public String selectDeptName(Long deptId) {
		PensionDeptExample example=new PensionDeptExample();
		example.or().andIdEqualTo(deptId)
			.andClearedEqualTo(NO_FLAG);
		List<PensionDept> depts=pensionDeptMapper.selectByExample(example);
		if(depts.size()>0){
			return depts.get(0).getName();
		}else{
			return "";
		}
	}
	
	public List<PensionEmployee> selectById(List<Long> values) {
		PensionEmployeeExample example = new PensionEmployeeExample();
		example.or().andIdIn(values)
		.andClearedEqualTo(NO_FLAG);
		return pensionEmployeeMapper.selectByExample(example);
	}
	
	
}
