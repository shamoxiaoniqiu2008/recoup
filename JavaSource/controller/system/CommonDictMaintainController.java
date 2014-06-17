package controller.system;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;

import service.system.CommonDictMaintainService;
import util.CmmDictMaintainNameUtil;
import util.Spell;


import domain.system.ColumnModel;
import domain.system.DictClassInfoForCommonDict;
import domain.system.PensionDictInfo;

/**
 * <p>
 * Description:公共数据字典维护页面的controller.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * // * Company:Centling
 * </p>
 * 
 * @author: Tim Li 
 * @version: 1.0
 * @Date:2013年11月28日9:40:09
 * @see: controller.system
 */
public class CommonDictMaintainController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient CommonDictMaintainService commonDictMaintainService;
	private List<DictClassInfoForCommonDict> dictClassInfoForCommonDictList;
	private TreeNode root;
	private TreeNode selectedTreeNode;
	/*********************************************************************************************
	 * 查询的条件
	 * *********************************************************************************************/
	private String coluNameValue; // 要搜寻列的值
	private String selectedColumnName;// 选择的列名

	private List<HashMap<String, Object>> tableDataList;
	private HashMap<String, Object> selectedData; // 选择的数据
	private boolean visibleFlag;
	private List<ColumnModel> columnModelList; // 去掉主键的key-map,key-map,用于修改
	private List<ColumnModel> searchColumnModelList; //用于查询
	private List<ColumnModel> addingColumnModelList; // 有主键的key-map,key-map,用于增加
	private List<ColumnModel> displayModelList;//用于datatable的显示
	private ColumnModel selectedColumnModel; // 选择的ColumnModel
	private String columnNames = ""; // 列名拼成的字符串，如column1,column2
	private String tableName; // 表名
	private Map<String, String> tableMap = new HashMap<String, String>();
	private String pkColuName; // PK Column 的name
	private Object primaryKey; // 表中的主键值，根绝firstColuName得到
	private HashMap<String, Object> newSelectedData; // 选择的数据
	
	private boolean findDefaultSeeDataDictIdFlag ;//是否可以看见主键的系统参数
	
	@PostConstruct
	public void init() {
		
		//将前端的树进行初始化
		dictClassInfoForCommonDictList = commonDictMaintainService
				.findAllDictClassInfoForCommDict();
		root = new DefaultTreeNode("公共字典", null);
		for (DictClassInfoForCommonDict dictClassInfoForCommonDict : dictClassInfoForCommonDictList) {
			//暂时还没有分类
			//final DictClassInfo dictClassInfo = dictClassInfoForCommonDict.getDictClassInfo();
			final List<PensionDictInfo> dictInfoList = dictClassInfoForCommonDict.getDictInfoList();
			
			for (PensionDictInfo dictInfo : dictInfoList) {
				new DefaultTreeNode(CmmDictMaintainNameUtil.LEAF_DICT,
						dictInfo, root);
			}
			
		}
		
		if(root.getChildCount()>0)
		{
			selectedTreeNode = root.getChildren().get(0);
			onNodeSelect(null);
		}

	}
	
	/**
	 * 
	 * @param event
	 *            选中叶子节点时，更新右侧form
	 * @version: 1.0
	 * @author: Ade Wang
	 */
	public void onNodeSelect(NodeSelectEvent event) {
		TreeNode treeNode = null;
		if(event == null)
		{
			treeNode = selectedTreeNode;
		}
		else
		{
			treeNode = event.getTreeNode();
		}
		// 是否是叶子结点
		
		boolean flag = treeNode.isLeaf();
		if (flag && (treeNode.getData() instanceof PensionDictInfo)) {
			final PensionDictInfo dictInfo = (PensionDictInfo) treeNode.getData();
			tableName = dictInfo.getDictDatatableName();
			// 将表名字中的空格过滤掉
			tableName = commonDictMaintainService.trimWhiteSpace(tableName);
			// 将表名转化为大写
			tableName = tableName.toUpperCase();

			tableMap = new HashMap<String, String>();
			
			tableMap.put(CmmDictMaintainNameUtil.TABLE_NAME, tableName);
			
			columnNames = commonDictMaintainService
					.findColumnNamesByTabelNameNotCleanEditable(tableName);//查询全部列名
			
			tableMap.put(CmmDictMaintainNameUtil.COLUMN_NAMES, columnNames);
			
			pkColuName = commonDictMaintainService.selectPkColumnName(tableName);//查询主键的列名
			tableMap.put(CmmDictMaintainNameUtil.COLUMN_NAME, pkColuName);
			
			tableMap.put(CmmDictMaintainNameUtil.ORDERBY_COLUMNAME,pkColuName);//根据主键排序
			
			try {
				
				tableDataList = commonDictMaintainService
						.findDataOfTable(tableMap);
				
			} catch (DataAccessException e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"查询数据时，出错！", "查询数据时，出错！"));
			}
			
			// 将卫生标准编码排到第2列,index为1的,第一列应该是主键
			addingColumnModelList = commonDictMaintainService
					.findColumnModelByTableName(tableName,pkColuName);

			// 去掉了主键的那一列,并将卫生标准编码排到第1列,index为0的
			columnModelList = commonDictMaintainService.removePkColumn(addingColumnModelList,pkColuName);
			
			if(findDefaultSeeDataDictIdFlag){//能看见主键
				displayModelList = new ArrayList<ColumnModel>(addingColumnModelList);
				searchColumnModelList = new ArrayList<ColumnModel>(addingColumnModelList);
			}else{//不能看见主键
				displayModelList = new ArrayList<ColumnModel>(columnModelList);//用于datatable显示
				searchColumnModelList = new ArrayList<ColumnModel>(columnModelList);//用于查询
				
			}
			// 右边fieldset是可见的
			visibleFlag = true;
		}
		// 使没有行被选中
		selectedData = null;
		// 使查询条件为空
		this.coluNameValue = "";

	}
	
	/**
	 * 
	 * @Title: clearSearchCondition
	 * @Description: 清空查询条件
	 * @version: 1.0
	 */
	public void clearSearchCondition() {
		coluNameValue = null;
	}

	/**
	 * @param: args 根据列数据，查询数据
	 * @return: CommonDictMaintainService
	 * @version: 1.0
	 * @author: Ade Wang
	 */
	public void search() {
		// where coluName like '%'||'str'||'%' or columnName like '%'||'str(upper)'||'%'
		String condition = null;
		if(coluNameValue == null || "".equals(coluNameValue.replaceAll(" ", ""))){
			/*
			 * 解决掉,对于可能有三条数据，只有1条有数据，另外没有数据，然后查询条件无值，则只能查询有值的记录的问题
			 */
			condition = selectedColumnName + " LIKE '%' " + " or "+selectedColumnName+" IS NULL";
		}else{
			condition = selectedColumnName + " LIKE '%"+ coluNameValue.toLowerCase() + "%' or "+selectedColumnName+" LIKE '%"+coluNameValue.toUpperCase()+"%'";
		}
		
		// 构造查询要传入的参数
		tableMap = new HashMap<String, String>();
		tableMap.put(CmmDictMaintainNameUtil.TABLE_NAME, tableName);
		tableMap.put(CmmDictMaintainNameUtil.SEARCH_CONDITION, condition);
		tableMap.put(CmmDictMaintainNameUtil.ORDERBY_COLUMNAME, pkColuName);//根据主键排序
		tableDataList = commonDictMaintainService.findDataOfTableByColumnName(tableMap);
		
		// 将选择的数据置为null
		selectedData = null;
	}

	/**
	 * 
	 * @Title: clearAddDataDig
	 * @Description: 清空增加数据的dialog
	 * @version: 1.0
	 */
	public void clearAddDataDig() {
		
		RequestContext request = RequestContext.getCurrentInstance();
		boolean isNull = true;
		if(addingColumnModelList==null){//有主键的List
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请先选中节点","请先选中节点");
			FacesContext.getCurrentInstance().addMessage(null, message);
			isNull = true;
		}else{
			// 增加一条数据后将，增加的dialog清空
			for (ColumnModel columnModel : addingColumnModelList) {
				columnModel.setColumnValue(null);
			}
			isNull = false;
		}
		request.addCallbackParam("isNull", isNull);
	}

	/**
	 * 
	 * @Title: addData
	 * @Description: 增加一条数据
	 * @version: 1.0
	 */

	public void addData() {
		RequestContext request = RequestContext.getCurrentInstance();
		boolean flag = true;
		
		StringBuffer columnNameBuffer = new StringBuffer();//表列的串,如column1,column2
		StringBuffer valueBuffer = new StringBuffer();//表列的串,如column1,column2
		for(ColumnModel e : addingColumnModelList){
			if(e.getColumnName().equals(pkColuName)){
				
			}else{
				columnNameBuffer.append(e.getColumnName()+",");
				valueBuffer.append("'"+e.getColumnValue()+"',");
			}
		}
		
		
		// 删除最后一个逗号
		final String columnNameStr = columnNameBuffer.deleteCharAt(
				columnNameBuffer.length() - 1).toString();
		
		// 删除最后一个逗号
		final String valueStr = valueBuffer.deleteCharAt(
				valueBuffer.length() - 1).toString();

		// 构造出增加数据时，要传入的参数
		tableMap = new HashMap<String, String>();
		tableMap.put(CmmDictMaintainNameUtil.TABLE_NAME, tableName);
		columnNames = commonDictMaintainService
				.findColumnNamesByTabelNameNotCleanEditable(tableName);
		tableMap.put(CmmDictMaintainNameUtil.COLUMN_NAMES, columnNameStr);
		tableMap.put(CmmDictMaintainNameUtil.VALUES, valueStr);
		

		final FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			commonDictMaintainService.addRecord(tableMap);
		} 
		catch (TypeMismatchDataAccessException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "您增加的数据与要求的数据类型不匹配！","您增加的数据与要求的数据类型不匹配！"));
			flag = false;
		}
		catch(UncategorizedSQLException e ){
			//值太长
			if(e.getMessage().contains("ORA-12899")){
				for(ColumnModel c : addingColumnModelList ){
					//异常信息中包含列名
					if(e.getMessage().contains(c.getColumnName().toUpperCase())){
						facesContext.addMessage(null, new FacesMessage(
								FacesMessage.SEVERITY_ERROR, c.getComments()+"过长",  c.getComments()+"过长"));
						flag = false;
						
					}
				}
				
			}else{
				facesContext.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "增加数据时，发生错误！", "增加数据时，发生错误！"));
				flag = false;
			}
			
		}
		catch(DuplicateKeyException e ){
			
				facesContext.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "有重复数据！", "有重复数据！"));
				flag = false;
		}
		catch (DataAccessException e) {
			
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "增加数据时，发生错误！", "增加数据时，发生错误！"));
			flag = false;
		}
		if (flag) {
			
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "成功增加数据！", "成功增加数据！"));
			
			search();//重查一遍
			
			clearAddDataDig();// 清空表单
		}

		// 返回参数定义
		request.addCallbackParam("flag", flag);
	}

	/**
	 * 
	 * @Title: refresh
	 * @Description: 查数据库刷新列表
	 * @version: 1.0
	 * @param tableName
	 * @param tableMap
	 */
	public void refresh(String tableName, Map<String, String> tableMap) {
		String columnNames = commonDictMaintainService
				.findColumnNamesByTabelNameNotCleanEditable(tableName);
		tableMap.put(CmmDictMaintainNameUtil.COLUMN_NAMES, columnNames);
		tableDataList = commonDictMaintainService.findDataOfTable(tableMap);
	}

	public void setInputCode(String columnName) {
		RequestContext request = RequestContext.getCurrentInstance();
		
		String inputCodeColumnValue=null;//输入码的那一列的列值
		String inputCodeColumnName=null;//输入码的那一列列名
		
		boolean isNameColumn = false;
		//是否是name那一列
		if(columnName.toUpperCase().contains("NAME")){
			
			isNameColumn = true;//是name列
			
			//求出输入码
			for (ColumnModel e : addingColumnModelList) {
				
				if(e.getColumnName().equals(columnName)){
					String nameCloumn = e.getColumnValue();
					inputCodeColumnValue = Spell.getFirstSpell(nameCloumn);
					break;
				}
			}
			
			
			//将输入码设置进去
			for (ColumnModel e : addingColumnModelList) {
				
				if(e.getColumnName().toUpperCase().contains("INPUT_CODE")){
					e.setColumnValue(inputCodeColumnValue);
					inputCodeColumnName = e.getColumnName();
					break;
				}
			}
			
			
			
		}else{
			isNameColumn = false;//不是name列
			
		}
		
		request.addCallbackParam("isNameColumn", isNameColumn);//是否是name列
		
		request.addCallbackParam("inputCodeColumnName", inputCodeColumnName);//input_code 列的名字
		
		request.addCallbackParam("inputCodeColumnValue", inputCodeColumnValue);//input_code 列值
		
	}

	/**
	 * 
	 * @Title: showDeleteDig
	 * @Description: 弹出删除窗口
	 * @version: 1.0
	 */
	public void showDeleteDig() {
		RequestContext request = RequestContext.getCurrentInstance();
		boolean flag = true;
		if (selectedData == null) {
			flag = false;
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "请先选择数据",
							"请先选择数据"));
		}else {
			flag = true;
		}
		request.addCallbackParam("flag", flag);
	}

	/**
	 * 
	 * @Title: deleteData
	 * @Description: 删除一条数据
	 * @version: 1.0
	 */
	public void deleteData() {
		 
		final RequestContext context = RequestContext.getCurrentInstance();
		primaryKey = (Object) selectedData.get(pkColuName);
		// 构造删除数据要传入的参数
		tableMap = new HashMap<String, String>();
		tableMap.put(CmmDictMaintainNameUtil.TABLE_NAME, tableName);
		tableMap.put(CmmDictMaintainNameUtil.COLUMN_NAME, pkColuName);
		tableMap.put(CmmDictMaintainNameUtil.PARAMETER, primaryKey.toString());
		boolean flag = true;
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			commonDictMaintainService.deleteData(tableMap);

		} catch (DataIntegrityViolationException e) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "此条数据已被关联，不能删除！",
					"此条数据已被关联，不能删除！"));
			flag = false;
		} catch (DataAccessException e) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "删除数据时，发生错误！", "删除数据时，发生错误！"));
			flag = false;
		}

		if (flag) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "成功删除数据！", "成功删除数据！"));
			
			/*// 将旧数据移除,再将新数据添加
			int index = findIndex(tableDataList, pkColuName,primaryKey.toString());
			tableDataList.remove(index);

			context.addPartialUpdateTarget("rightForm");// 成功时，在update form
			 */
			search();//重查
			// 将选择的数据置为null
			selectedData = null;
		}

		context.addCallbackParam("issuccess", flag);

	}

	/**
	 * 将选择的数据重新new一个这样子改都不会修改元数据
	 */
	public void newSelectDataOfModify() {
		RequestContext request = RequestContext.getCurrentInstance();
		boolean flag = true;
		if (selectedData == null) {
			flag = false;
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "请先选择数据",
							"请先选择数据"));
		} 
		else {
			newSelectedData = new HashMap<String, Object>(selectedData);
			flag = true;
		}
		request.addCallbackParam("flag", flag);
	}

	// 保存修改的数据

	public void modifyData() {
		final RequestContext context = RequestContext.getCurrentInstance();

		final Set<String> keySet = newSelectedData.keySet();
		final Iterator<String> iterator = keySet.iterator();
		final StringBuffer strBuffer = new StringBuffer();
		while (iterator.hasNext()) {
			final String key = iterator.next();
			final Object obj = newSelectedData.get(key);
			strBuffer.append(key + "='" + obj + "',");
		}
		final String parameterBuffer = strBuffer.deleteCharAt(
				strBuffer.length() - 1).toString();
		// 构造保存数据，要传入的参数
		tableMap = new HashMap<String, String>();
		tableMap.put(CmmDictMaintainNameUtil.TABLE_NAME, tableName);
		tableMap.put(CmmDictMaintainNameUtil.COLUMN_AND_VALUES, parameterBuffer);
		tableMap.put(CmmDictMaintainNameUtil.COLUMN_NAME, pkColuName);
		primaryKey = (Object) newSelectedData.get(pkColuName);
		tableMap.put(CmmDictMaintainNameUtil.PARAMETER, primaryKey.toString());
		FacesContext facesContext = FacesContext.getCurrentInstance();
		boolean flag = true;
		try {
			commonDictMaintainService.updateData(tableMap);
		} catch (TypeMismatchDataAccessException e) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "您所修改后的数据类型不符合要求！",
					"您所修改后的数据类型不符合要求！"));
			flag = false;
		} 
		
		catch(UncategorizedSQLException e ){
			//值太长
			if(e.getMessage().contains("ORA-12899")){
				for(ColumnModel c : addingColumnModelList ){
					//异常信息中包含列名
					if(e.getMessage().contains(c.getColumnName().toUpperCase())){
						facesContext.addMessage(null, new FacesMessage(
								FacesMessage.SEVERITY_ERROR, c.getComments()+"过长",  c.getComments()+"过长"));
						flag = false;
						
					}
				}
				
			}else{
				facesContext.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "修改数据时，发生错误！", "修改数据时，发生错误！"));
				flag = false;
			}
			
		}
		catch(DuplicateKeyException e ){
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "有重复数据！", "有重复数据！"));
			flag = false;
		}
		catch (DataAccessException e) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "修改数据时，发生错误！", "修改数据时，发生错误！"));
			flag = false;
		}

		if (flag) {
			
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "成功修改数据！", "成功修改数据"));
			// 将旧数据移除,再将新数据添加
			int index = findIndex(tableDataList, pkColuName,
					primaryKey.toString());
			tableDataList.remove(index);
			tableDataList.add(newSelectedData);

			// 将选中的数据置为null
			selectedData = null;

		}

		context.addCallbackParam("issuccess", flag);

	}

	/**
	 * 
	 * @Title: findIndex
	 * @Description: 找到某在对象在集合中的index
	 * @version: 1.0
	 * @param list
	 * @param key
	 * @param pk
	 * @return
	 */
	public int findIndex(List<HashMap<String, Object>> list, String key,
			String pk) {
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> e = list.get(i);
			if (e.get(key).toString().equals(pk)) {
				return i;
			}
		}
		return -1;
	}

	public CommonDictMaintainService getCommonDictMaintainService() {
		return commonDictMaintainService;
	}

	public void setCommonDictMaintainService(
			CommonDictMaintainService commonDictMaintainService) {
		this.commonDictMaintainService = commonDictMaintainService;
	}

	public List<DictClassInfoForCommonDict> getDictClassInfoForCommonDictList() {
		return dictClassInfoForCommonDictList;
	}

	public void setDictClassInfoForCommonDictList(
			List<DictClassInfoForCommonDict> dictClassInfoForCommonDictList) {
		this.dictClassInfoForCommonDictList = dictClassInfoForCommonDictList;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getSelectedTreeNode() {
		return selectedTreeNode;
	}

	public void setSelectedTreeNode(TreeNode selectedTreeNode) {
		this.selectedTreeNode = selectedTreeNode;
	}

	public String getColuNameValue() {
		return coluNameValue;
	}

	public void setColuNameValue(String coluNameValue) {
		this.coluNameValue = coluNameValue;
	}

	public List<HashMap<String, Object>> getTableDataList() {
		return tableDataList;
	}

	public void setTableDataList(List<HashMap<String, Object>> tableDataList) {
		this.tableDataList = tableDataList;
	}

	public boolean isVisibleFlag() {
		return visibleFlag;
	}

	public void setVisibleFlag(boolean visibleFlag) {
		this.visibleFlag = visibleFlag;
	}

	public List<ColumnModel> getColumnModelList() {
		return columnModelList;
	}

	public void setColumnModelList(List<ColumnModel> columnModelList) {
		this.columnModelList = columnModelList;
	}

	public ColumnModel getSelectedColumnModel() {
		return selectedColumnModel;
	}

	public void setSelectedColumnModel(ColumnModel selectedColumnModel) {
		this.selectedColumnModel = selectedColumnModel;
	}

	public String getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public HashMap<String, Object> getSelectedData() {
		return selectedData;
	}

	public void setSelectedData(HashMap<String, Object> selectedData) {
		this.selectedData = selectedData;
	}

	public Map<String, String> getTableMap() {
		return tableMap;
	}

	public void setTableMap(Map<String, String> tableMap) {
		this.tableMap = tableMap;
	}

	public String getPkColuName() {
		return pkColuName;
	}
	
	public void setPkColuName(String pkColuName) {
		this.pkColuName = pkColuName;
	}

	public Object getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Object primaryKey) {
		this.primaryKey = primaryKey;
	}

	public List<ColumnModel> getAddingColumnModelList() {
		return addingColumnModelList;
	}

	public void setAddingColumnModelList(List<ColumnModel> addingColumnModelList) {
		this.addingColumnModelList = addingColumnModelList;
	}

	public HashMap<String, Object> getNewSelectedData() {
		return newSelectedData;
	}

	public void setNewSelectedData(HashMap<String, Object> newSelectedData) {
		this.newSelectedData = newSelectedData;
	}

	public String getSelectedColumnName() {
		return selectedColumnName;
	}

	public void setSelectedColumnName(String selectedColumnName) {
		this.selectedColumnName = selectedColumnName;
	}

	public boolean isFindDefaultSeeDataDictIdFlag() {
		return findDefaultSeeDataDictIdFlag;
	}
	
	public void setFindDefaultSeeDataDictIdFlag(boolean findDefaultSeeDataDictIdFlag) {
		this.findDefaultSeeDataDictIdFlag = findDefaultSeeDataDictIdFlag;
	}
	
	public List<ColumnModel> getDisplayModelList() {
		return displayModelList;
	}
	
	
	public void setDisplayModelList(List<ColumnModel> displayModelList) {
		this.displayModelList = displayModelList;
	}
	
	
	public List<ColumnModel> getSearchColumnModelList() {
		return searchColumnModelList;
	}
	
	
	public void setSearchColumnModelList(List<ColumnModel> searchColumnModelList) {
		this.searchColumnModelList = searchColumnModelList;
	}

}
