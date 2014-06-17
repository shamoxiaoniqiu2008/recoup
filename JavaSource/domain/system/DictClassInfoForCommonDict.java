package domain.system;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * <p>Description:数据字典的分类信息.</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company:Centling</p>
 * @author: Ade Wang
 * @version: 1.0
 * @Date:2011-9-13 下午08:22:25
 * @see: com.centling.his.service.sysmanage
 */
public class DictClassInfoForCommonDict implements Serializable { 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private DictClassInfo dictClassInfo; //数据字典分类信息
	
	private List<PensionDictInfo> dictInfoList; //字典信息
	

	public DictClassInfoForCommonDict() {
		
	}
	
	public DictClassInfoForCommonDict(List<PensionDictInfo> dictInfoList) {
		
		this.dictInfoList = dictInfoList;
	}

	public List<PensionDictInfo> getDictInfoList() {
		return dictInfoList;
	}

	public void setDictInfoList(List<PensionDictInfo> dictInfoList) {
		this.dictInfoList = dictInfoList;
	}
}
