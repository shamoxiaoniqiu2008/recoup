package service.system;

import java.io.Serializable;
import java.util.List;

import domain.system.PensionMenu;

public class PensionMenus  implements Serializable{
	
	/** 
	* @Fields serialVersionUID : TODO
	* @version V1.0
	*/ 
	
	private static final long serialVersionUID = -8196156844243034821L;
	List<PensionMenu> pensionMenusList;
	PensionMenu pensionMenuFirst;
	public List<PensionMenu> getPensionMenusList() {
		return pensionMenusList;
	}
	public void setPensionMenusList(List<PensionMenu> pensionMenusList) {
		this.pensionMenusList = pensionMenusList;
	}
	public PensionMenu getPensionMenuFirst() {
		return pensionMenuFirst;
	}
	public void setPensionMenuFirst(PensionMenu pensionMenuFirst) {
		this.pensionMenuFirst = pensionMenuFirst;
	}
	
	
}
