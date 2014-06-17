package util;
/**
 * 
 * <p>Description:系统管理模块:公共数据字典维护.</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company:Centling</p>
 * @author: Ade Wang
 * @version: 1.0
 * @Date:2011-9-16 下午02:01:59
 * @see: com.centling.his.util
 */
public class CmmDictMaintainNameUtil {
	
	private CmmDictMaintainNameUtil() {
	}
	//class级别的字典，如人员属性，医疗工作
	public static final String CLASS_DICT = "classDict";
	//leaf级别的字典,如诊疗项目字典，临床科室属性字典
	public static final String LEAF_DICT = "leafDict";
	
	//表名称,与ManualColumnMapper.xml中名称对应
	public static final String TABLE_NAME = "tableName"; 
	
	//列名称与ManualColumnMapper.xml中名称对应
	public static final String COLUMN_NAMES = "columnNames"; 
	
	
	//增加一条数据时，传入的值
	public static final String VALUES = "values";
	
	//查询表的seq
	public static final String TABLE_SEQ = "tableSeq";
	
	//追加的字母为了不全sql语句，如table_name_seq.nextval
	public static final String APPEND_SEQ = "_SEQ.NEXTVAL";
	
	//某一列的名字，与ManualColumnMapper.xml名称对应
	public static final String COLUMN_NAME = "columnName";
	
	//参数名字，与ManualColumnMapper.xml对应
	public static final String PARAMETER = "parameter";
	
	//查询的条件，与ManualColumnMapper.xml对应
	public static final String SEARCH_CONDITION = "searchCondition";
	
	//将要set的列及其值组成字符串，如（column1=1,column=2,column=3）
	public static final String COLUMN_AND_VALUES = "columnAndValues";
	
	//主键为1
	public static final String PRIMARYKEY_EQUALS_ONE="1";
	
	//被排序的列
	public static final String ORDERBY_COLUMNAME="orderColumnName";
}
