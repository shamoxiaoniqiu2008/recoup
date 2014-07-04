/**
 * 
 */
package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.recoup.SysDeDatarangeitem;
import persistence.recoup.SysDeDatarangeMapper;
import persistence.recoup.SysDeDatarangeitemMapper;

/**
 * @author veny
 *
 */
@Service
public class DataRangeService {

	@Autowired
	private SysDeDatarangeMapper datarangeMapper;
	@Autowired
	private SysDeDatarangeitemMapper datarangeitemMapper;
	
	/**
	 * 根据数据类型获取值域数据
	 * @param type
	 * @return 
	 */
	public static List<SysDeDatarangeitem> getDataRangeItem(String type) {
		
		return null;
	}

	/**
	 * 根据数据类型和父值域代码获取数据
	 * @param type 值域类型
	 * @param parentCode 父代码
	 * @return
	 */
	public static List<SysDeDatarangeitem> getDataRangeItem(String type,
			String parentCode) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
