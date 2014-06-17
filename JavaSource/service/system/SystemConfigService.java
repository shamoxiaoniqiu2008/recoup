package service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.system.PensionSystemConfig;
import domain.system.PensionSystemConfigExample;

import persistence.system.PensionSystemConfigMapper;

@Service
public class SystemConfigService {
	
	@Autowired
	private PensionSystemConfigMapper pensionSystemConfigMapper;
	/**
	 * 根据查询条件 查询出对应的系统参数记录
	 * @param configId
	 * @return
	 */
	public List<PensionSystemConfig> selectSystemConfigRecords(Long configId,Integer type) {
		PensionSystemConfigExample example = new PensionSystemConfigExample();
		example.or().andIdEqualTo(configId).andTypeEqualTo(type);
		return pensionSystemConfigMapper.selectByExample(example);
	}
	/**
	 * 修改系统参数的值
	 * @param updatedRow
	 */
	public void updateSystemConfigRecord(PensionSystemConfig updatedRow) {
		pensionSystemConfigMapper.updateByPrimaryKeySelective(updatedRow);
	}
	
	public void setPensionSystemConfigMapper(PensionSystemConfigMapper pensionSystemConfigMapper) {
		this.pensionSystemConfigMapper = pensionSystemConfigMapper;
	}

	public PensionSystemConfigMapper getPensionSystemConfigMapper() {
		return pensionSystemConfigMapper;
	}

	
	
}
