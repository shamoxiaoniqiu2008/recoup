/**
 * 
 */
package service.recoup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.recoup.RecoupDicProject;
import domain.recoup.RecoupDicProjectExample;
import persistence.recoup.RecoupDicProjectMapper;

/**
 * @author justin
 *
 */

@Service
public class RecoupApplyService {

	@Autowired
	private RecoupDicProjectMapper recoupDicProjectMapper;
	
	/**
	 * 
	 * @return
	 */
	public List<RecoupDicProject> selectAllProjects(){
		RecoupDicProjectExample example = new RecoupDicProjectExample();
		example.or().andClearedEqualTo(2);
		return recoupDicProjectMapper.selectByExample(example);
	}
}
