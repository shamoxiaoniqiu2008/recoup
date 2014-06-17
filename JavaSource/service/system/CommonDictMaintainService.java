package service.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.system.ColumnModel;
import domain.system.Columns;
import domain.system.ColumnsExample;
import domain.system.DictClassInfoForCommonDict;
import domain.system.PensionDictInfo;

import persistence.system.ColumnsMapper;
import persistence.system.ManualColumnMapper;
import persistence.system.PensionDictInfoMapper;
import util.PmsException;
import util.SystemConfig;

@Service
public class CommonDictMaintainService {

	@Autowired
	private PensionDictInfoMapper dictInfoMapper;
	
	@Autowired
	private ColumnsMapper columnsMapper;

	@Autowired
	private ManualColumnMapper manualColumnMapper;
	
	@Autowired
	private SystemConfig systemConfig;
	/**
	 * @param: args @return
	 * @return: List<DictInfoForCommonDict>
	 * @author: Ade Wang 显示有公共字典树
	 */
	public List<DictClassInfoForCommonDict> findAllDictClassInfoForCommDict() {

		final List<DictClassInfoForCommonDict> dictClassInfoForCommDictList = new ArrayList<DictClassInfoForCommonDict>();
		//因为现在还没数据字典还没有涉及到类别 所以先把代码关于类别的代码注释掉 2013-11-22
		// 先查出所有字典分类表
		//final List<DictClassInfo> dictClassInfoList = dictClassInfoMapper.selectByExample(null);
		//for (DictClassInfo dictClassInfo : dictClassInfoList) {
		//final DictInfoExample dictInfoExample = new DictInfoExample();
		//dictInfoExample.or().andDictSuperiorClassIdEqualTo(dictClassInfo.getDictClassId());
		// 查出每个字典分类关联的子字典表
		final List<PensionDictInfo> dictInfoList = dictInfoMapper.selectByExample(null);
		final DictClassInfoForCommonDict dictClassInfoForCommonDict = new DictClassInfoForCommonDict(dictInfoList);
		dictClassInfoForCommDictList.add(dictClassInfoForCommonDict);
		
		return dictClassInfoForCommDictList;

	}

	/**
	 * 
	* @Title : findColumnModelByTableName
	 * @Description : 包装过的UserColComments，用户新增，修改删除
	 * @param : @param tableName
	 * @return : List<ColumnModel>
	 */
	public List<ColumnModel> findColumnModelByTableName(String tableName,String pkColuName) {
		
		List<ColumnModel> columnModelList = new ArrayList<ColumnModel>();
		
		final List<Columns> userColCommeList = findColumnByTableNameNotCleanEditable(tableName);
		
		for (Columns userColComments : userColCommeList) {
			
			final ColumnModel columnModel = new ColumnModel();
			columnModel.setColumnName(userColComments.getColumnName());
			columnModel.setComments(spiltString(userColComments.getColumnComment()));
			columnModel.setTableName(userColComments.getTableName());
			columnModel.setIsNull(userColComments.getIsNullable());
			columnModelList.add(columnModel);
		}
		
		//将主键放在index为0的位置
		columnModelList = sortPkColumnAndHealthCol(columnModelList,pkColuName);
		
		return columnModelList;
	}
	
	/**
	 * 
	* @Title : findColumnNamesByTabelName.
	 * @Description : 通过tableName将此表的所有列组成一个字符串，如column2,column2，不剔除Editable列
	 * @param : @param tableName
	 * @param : @return
	 * @return : String
	 */
	public String findColumnNamesByTabelNameNotCleanEditable(String tableName) {
		final StringBuffer columnNames = new StringBuffer();
		final List<Columns> list = findColumnByTableNameNotCleanEditable(tableName);
		for (Columns userColComments : list) {
			columnNames.append(userColComments.getColumnName() + ",");
		}
		final int index = columnNames.lastIndexOf(",");
		
		if(index != -1) {
			columnNames.deleteCharAt(index);
		}
		
		return columnNames.toString();
	}
	
	/**
	 * 
	 * @Title: findColumnByTableNameNotCleanEditable
	 * @Description: 通过tableName查询此表的所有columnName，并且不提出Editable列
	 * @version: 1.0
	 * @param tableName
	 * @return
	 */
	public List<Columns> findColumnByTableNameNotCleanEditable(String tableName){
		
		final ColumnsExample example = new ColumnsExample();
		example.or().andTableNameEqualTo(tableName);
		final List<Columns> list = columnsMapper.selectByExample(example);
		return list;
	}
	
	/**
	 * 
	 * @Title: trimWhiteSpace
	 * @Description: 将表名的空格全部去掉
	 * @version: 1.0
	 * @param tableName
	 * @return
	 */
	public String trimWhiteSpace(String tableName) {
		tableName = tableName.trim();
		tableName = tableName.replaceAll(" ", "");
		return tableName;
	}
	
	
	/**
	 * 
	* @Title : findDataOfTable
	 * @Description : 根据表名查出此表的全部数据
	 * @param : @param tableMap
	 * @param : @return
	 * @return : HashMap
	 */
	public List<HashMap<String, Object>> findDataOfTable(
			Map<String, String> tableMap) {
		 List<HashMap<String, Object>> list =  manualColumnMapper.selectDataOfTable(tableMap);
		 return list;
	}
	
	/**
	 * 
	* @Title : findDataOfTableByColumnName
	 * @Description : 根据某个列查询这个表的数据
	 * @param : @param tableMap
	 * @param : @return
	 * @return : List<HashMap<String,Object>>
	 */
	public List<HashMap<String, Object>> findDataOfTableByColumnName(
			Map<String, String> tableMap) {
		 List<HashMap<String, Object>> list = manualColumnMapper.selectDataOfTabelByColumnName(tableMap);
		 return list;
	}
	
	/**
	 * 
	* @Title : addRecord
	 * @Description : 增加一条记录
	 * @param : @param tableMap
	 * @return : void
	 */
	@Transactional
	public void addRecord(Map<String, String> tableMap) {
		manualColumnMapper.insertRecord(tableMap);
	}
	
	/**
	 * 
	* @Title : deleteData
	 * @Description : 删除一条数据
	 * @param : @param tableMap
	 * @return : void
	 */
	@Transactional
	public void deleteData(Map<String, String> tableMap) {
		 manualColumnMapper.deleteData(tableMap);
	}
	
	/**
	 * 
	* @Title : updateData
	 * @Description : 修改数据库中的一条数据
	 * @param : @param tableMap
	 * @return : void
	 */
	@Transactional
	public void updateData(Map<String, String> tableMap) {
		 manualColumnMapper.updateData(tableMap);
	}
	
	/**
	 * 
	 * @Title: spiltString
	 * @Description: 截取冒号前的comments
	 * @version: 1.0
	 * @param str
	 * @return
	 */
	public String spiltString(String str) {
		if(str ==null || str.trim().equals("")){
			return str;
		}else{
			//将汉字的冒号替换为中文的冒号
			str =str.replaceAll("：", ":");
			String[] strs = str.split(":");
			return strs[0];
		}
		
	}

	/**
	 * 
	* 
	* @Title: selectPkColumnName
	* @Description: 查询某个表的主键
	* @return:String
	* @version: 1.0
	 */
	public String selectPkColumnName(String tableName){
		Map<String, String> map = new HashMap<String, String>();
		map.put("tableName", tableName);
		return manualColumnMapper.selectPkColumnName(map);
	}
	
	
	/**
	 * 
	* 
	* @Title: removePkColumn
	* @Description: 将传进来的包含全部列的List<ColumnModel>去掉主键的那个对象,卫生统计标准编码
	* @return:List<ColumnModel>
	* @version: 1.0
	 */
	public List<ColumnModel> removePkColumn(List<ColumnModel> listParam,String pkColumnName){
		
		List<ColumnModel> list = new ArrayList<ColumnModel>(listParam);
		
		//先移除主键的那一列
		for(int i = list.size()-1;i>=0;i--){
			ColumnModel columnModel = list.get(i);
			if(columnModel.getColumnName().equals(pkColumnName)){
				list.remove(i);
			}
		}
		return list;
	}
	
	
	
	
	/**
	 * 将主键列和健康code列，分别放在
	 * @param columnModelList
	 * @param healthStaticCode
	 * @param pkColumnName
	 * @return
	 */
	private  List<ColumnModel> sortPkColumnAndHealthCol(List<ColumnModel> columnModelList,String pkColumnName){

		
		ColumnModel pkColumnColumnModel = null;
		
		for(ColumnModel e : columnModelList){
			
			if(e.getColumnName().equals(pkColumnName)){
				pkColumnColumnModel = e;
			}
		}
		columnModelList.remove(pkColumnColumnModel);
		columnModelList.add(0, pkColumnColumnModel);
		
		return columnModelList;
	}
	
	 /**
     * 查询默认系统参数定义的是否显示主键
     * @return
	 * @throws PmsException 
     */
    public boolean findDefaultSeeDataDictIdFlag() throws PmsException{
    	
    	String defaultSeeSectionIdFlag = systemConfig.selectProperty("SYSTEM_MANAGE_SEE_DATA_DICT_ID_FLAG"); //系统参数定义的是否显示主键
    	if(null != defaultSeeSectionIdFlag && "1".equals(defaultSeeSectionIdFlag)){
    		return true;
    	}else{
    		return false;
    	}
    }

	public PensionDictInfoMapper getDictInfoMapper() {
		return dictInfoMapper;
	}

	public void setDictInfoMapper(PensionDictInfoMapper dictInfoMapper) {
		this.dictInfoMapper = dictInfoMapper;
	}

	public ColumnsMapper getColumnsMapper() {
		return columnsMapper;
	}

	public void setColumnsMapper(ColumnsMapper columnsMapper) {
		this.columnsMapper = columnsMapper;
	}

	public ManualColumnMapper getManualColumnMapper() {
		return manualColumnMapper;
	}

	public void setManualColumnMapper(ManualColumnMapper manualColumnMapper) {
		this.manualColumnMapper = manualColumnMapper;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}

}
