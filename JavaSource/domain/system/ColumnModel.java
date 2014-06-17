package domain.system;

import java.io.Serializable;
/**
 * 
 * <p>Description:公共数据字典维护的ColumnModel.</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company:Centling</p>
 * @author: Ade Wang
 * @version: 1.0
 * @Date:2011-9-19 下午01:56:47
 * @see: com.centling.his.service.sysmanage
 */
public class ColumnModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	 private String tableName;//数据库表名字
	 private String columnName;//列名字
	 private String comments;//
	 private String columnValue;//列的值
	 private String isNull; //此列是否允许空，只有两个值Y/N
	 
	 
	public ColumnModel() {
	}
	
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getColumnValue() {
		return columnValue;
	}
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}
	


	public String getIsNull() {
		return isNull;
	}



	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result
				+ ((columnValue == null) ? 0 : columnValue.hashCode());
		result = prime * result
				+ ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((isNull == null) ? 0 : isNull.hashCode());
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColumnModel other = (ColumnModel) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		if (columnValue == null) {
			if (other.columnValue != null)
				return false;
		} else if (!columnValue.equals(other.columnValue))
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (isNull == null) {
			if (other.isNull != null)
				return false;
		} else if (!isNull.equals(other.isNull))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}



	


	



	 
	 
	 
	 
}
