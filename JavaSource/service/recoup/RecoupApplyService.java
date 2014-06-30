/**
 * 
 */
package service.recoup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.recoup.RecoupDicCostclass1;
import domain.recoup.RecoupDicCostclass1Example;
import domain.recoup.RecoupDicCostclass2;
import domain.recoup.RecoupDicCostclass2Example;
import domain.recoup.RecoupDicPayclass;
import domain.recoup.RecoupDicPayclassExample;
import domain.recoup.RecoupDicProject;
import domain.recoup.RecoupDicProjectExample;
import persistence.recoup.RecoupDicCostclass1Mapper;
import persistence.recoup.RecoupDicCostclass2Mapper;
import persistence.recoup.RecoupDicPayclassMapper;
import persistence.recoup.RecoupDicProjectMapper;

/**
 * @author justin
 *
 */

@Service
public class RecoupApplyService {

	@Autowired
	private RecoupDicProjectMapper recoupDicProjectMapper;
	@Autowired
	private RecoupDicPayclassMapper recoupDicPayclassMapper;
	@Autowired
	private RecoupDicCostclass1Mapper recoupDicCostclass1Mapper;
	@Autowired
	private RecoupDicCostclass2Mapper recoupDicCostclass2Mapper;
	
	/**
	 * 
	 * @return
	 */
	public List<RecoupDicProject> selectAllProjects(){
		RecoupDicProjectExample example = new RecoupDicProjectExample();
		example.or().andClearedEqualTo(2);
		return recoupDicProjectMapper.selectByExample(example);
	}
	
	
	public List<RecoupDicPayclass> selectAllPayclasses(){
		RecoupDicPayclassExample example = new RecoupDicPayclassExample();
		example.or().andClearedEqualTo(2);
		example.setOrderByClause("sort_by asc");
		return recoupDicPayclassMapper.selectByExample(example);
	}
	
	public List<RecoupDicCostclass1> selectAllCostclasses1(){
		RecoupDicCostclass1Example example = new RecoupDicCostclass1Example();
		example.or().andClearedEqualTo(2);
		return recoupDicCostclass1Mapper.selectByExample(example);
	}
	
	
	
	public List<RecoupDicCostclass2> selectAllCostclasses2(){
		RecoupDicCostclass2Example example = new RecoupDicCostclass2Example();
		example.or().andClearedEqualTo(2);
		return recoupDicCostclass2Mapper.selectByExample(example);
	}
}
