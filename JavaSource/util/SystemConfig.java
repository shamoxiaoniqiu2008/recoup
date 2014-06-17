package util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.system.PensionSystemConfigMapper;
import domain.system.PensionSystemConfig;
import domain.system.PensionSystemConfigExample;

@Service
public class SystemConfig {

	@Autowired
	PensionSystemConfigMapper pensionSystemConfigMapper;
	
	/**
	 * 获取系统参数
	 * @param configName
	 * @return
	 * @throws PmsException
	 */
	public String selectProperty(String configName) throws PmsException {
		
		PensionSystemConfigExample example = new PensionSystemConfigExample();
		example.or()
		.andConfigNameEqualTo(configName);
		List<PensionSystemConfig> pensionSystemConfigs = pensionSystemConfigMapper.selectByExample(example);
		if(pensionSystemConfigs == null || pensionSystemConfigs.size() == 0) {
			throw new PmsException("系统参数不存在");
		}
		
		if(pensionSystemConfigs.size() > 1) {
			throw new PmsException("重复设置系统参数");
		}
		
		return pensionSystemConfigs.get(0).getConfigValue();
		
	}
}
