package controller.reportManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;


/**

 *
 *
 */

public abstract class ReportController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//饼状图
	private Map<String, Float> pieMap = new HashMap<String,Float>();
	//柱状图
	private List<HashMap<String, ArrayList<Float>>> columnList = new ArrayList<HashMap<String,ArrayList<Float>>>();
	
	@PostConstruct
	public void init(){
		
	}
	
	//饼形图
	public abstract  void createPieList();
	
	//柱状图
	public abstract  void createColumnList();

	
	
	
	
	
	
	
	
	
	
	
	
	

	public List<HashMap<String, ArrayList<Float>>> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<HashMap<String, ArrayList<Float>>> columnList) {
		this.columnList = columnList;
	}

	public Map<String, Float> getPieMap() {
		return pieMap;
	}

	public void setPieMap(Map<String, Float> pieMap) {
		this.pieMap = pieMap;
	}
	
	
	
	
	
	
}

