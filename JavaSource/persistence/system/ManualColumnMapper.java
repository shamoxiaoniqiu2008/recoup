package persistence.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <p>Description:系统管理模块：公共数据字典维护.</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company:Centling</p>
 * @author: Ade Wang
 * @version: 1.0
 * @Date:2011-9-16 上午10:36:16
 * @see: com.centling.his.persistence.dictionary.sys
 */
public interface ManualColumnMapper {
	/**
	* @Title : selectDataOfTable
	 * @Description : 查询某个表的全部数据
	 * @param : @param tabMap
	 * @param : @return
	 * @return : List<Hashtable<String,Object>>
	 */
	List<HashMap<String, Object>> selectDataOfTable(
			Map<String, String> tabMap);
	
	
	/**
	 * 
	* @Title : selectDataOfTabelByColumnName
	 * @Description : 根据某个列查询这个表的数据
	 * @param : @param tabMap
	 * @param : @return
	 * @return : List<Hashtable<String,Object>>
	 */
	List<HashMap<String, Object>> selectDataOfTabelByColumnName(
			Map<String, String> tabMap);
	
	/**
	* @Title : insertRecord
	 * @Description : 增加一条数据
	 * @param : @param tableMap
	 * @return : void
	 */
	void insertRecord(Map<String, String> tableMap); 
	/**
	 * 
	* @Title : deleteData
	 * @Description : 删除数据库中的一条数据
	 * @param : @param tableMap
	 * @return : void
	 */
	void deleteData(Map<String, String> tableMap);
	
	/**
	 * 
	* @Title : updateData
	 * @Description : 修改数据库中的一条数据
	 * @param : @param tableMap
	 * @return : void
	 */
	void updateData(Map<String, String> tableMap);
	
	/**
	 * 
	* 
	* @Title: selectPKEqOne
	* @Description: 
	* @return:HashMap
	* @version: 1.0
	 */
	HashMap<String, Object> selectPKEqOne(Map<String, String> tableMap);
	
	/**
	 * 
	* 
	* @Title: selectPkColumnName
	* @Description: 查询某个表的主键名称
	* @return:String
	* @version: 1.0
	 */
	String selectPkColumnName(Map<String,String> map);
}
