package service.dictionary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.dictionary.PensionDicEventMapper;

import domain.dictionary.PensionDicEvent;
import domain.dictionary.PensionDicEventExample;

@Service
public class DicEventService {

	@Autowired
	private PensionDicEventMapper dicEventMapper;

	/**
	 * 获取事件
	 * 
	 * @param
	 * @return
	 */
	public List<PensionDicEvent> selectEventList(List<Long> empIDs,
			List<Long> deptIDs, Integer eventType) {
		PensionDicEventExample example = new PensionDicEventExample();
		if (empIDs != null && empIDs.size() > 0) {
			example.or().andEmpIdIn(empIDs).andEventtypeEqualTo(eventType);
		} else {
			example.or().andDeptIdIn(deptIDs).andEventtypeEqualTo(eventType);
		}
		return dicEventMapper.selectByExample(example);
	}

}
