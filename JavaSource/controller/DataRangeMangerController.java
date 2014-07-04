/**
 * 
 */
package controller;

import java.util.ArrayList;
import java.util.List;

import service.DataRangeService;
import domain.recoup.SysDeDatarangeitem;

/**
 * @author veny
 *
 */
public class DataRangeMangerController {
	
	private List<SysDeDatarangeitem> projects = new ArrayList<SysDeDatarangeitem>();
	private List<SysDeDatarangeitem> payType = new ArrayList<SysDeDatarangeitem>();
	private List<SysDeDatarangeitem> expTypeCodeP= new ArrayList<SysDeDatarangeitem>();
	private List<SysDeDatarangeitem> expTypeCode = new ArrayList<SysDeDatarangeitem>();
	/**
	 * @return the projects
	 */
	public List<SysDeDatarangeitem> getProjects() {
		if(projects.isEmpty()){
			String type = "RC003";
			projects = DataRangeService.getDataRangeItem(type);
		}
		return projects;
	}

	/**
	 * @return the payType
	 */
	public List<SysDeDatarangeitem> getPayType() {
		if(payType.isEmpty()){
			String type = "RC002";
			payType = DataRangeService.getDataRangeItem(type);
		}
		return payType;
	}
	
	/**
	 * @return the expTypeCodeP
	 */
	public List<SysDeDatarangeitem> getExpTypeCodeP() {
		if(expTypeCodeP.isEmpty()){
			String type = "RC001";
			String parentCode = "";
			expTypeCodeP = DataRangeService.getDataRangeItem(type,parentCode);
		}
		return expTypeCodeP;
	}
	
	/**
	 * @return the expTypeCode
	 */
	public List<SysDeDatarangeitem> getExpTypeCode(String parentCode) {
		if(expTypeCode.isEmpty()){
			String type = "RC001";
			expTypeCode = DataRangeService.getDataRangeItem(type,parentCode);
		}
		return expTypeCode;
	}


	
}
