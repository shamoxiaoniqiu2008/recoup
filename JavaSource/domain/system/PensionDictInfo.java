package domain.system;

import java.io.Serializable;

public class PensionDictInfo implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_dict_info.DICT_ID
	 * @mbggenerated  Thu Nov 28 09:11:22 CST 2013
	 */
	private Long dictId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_dict_info.DICT_NAME
	 * @mbggenerated  Thu Nov 28 09:11:22 CST 2013
	 */
	private String dictName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_dict_info.DICT_DATATABLE_NAME
	 * @mbggenerated  Thu Nov 28 09:11:22 CST 2013
	 */
	private String dictDatatableName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_dict_info
	 * @mbggenerated  Thu Nov 28 09:11:22 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_dict_info.DICT_ID
	 * @return  the value of pension_dict_info.DICT_ID
	 * @mbggenerated  Thu Nov 28 09:11:22 CST 2013
	 */
	public Long getDictId() {
		return dictId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_dict_info.DICT_ID
	 * @param dictId  the value for pension_dict_info.DICT_ID
	 * @mbggenerated  Thu Nov 28 09:11:22 CST 2013
	 */
	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_dict_info.DICT_NAME
	 * @return  the value of pension_dict_info.DICT_NAME
	 * @mbggenerated  Thu Nov 28 09:11:22 CST 2013
	 */
	public String getDictName() {
		return dictName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_dict_info.DICT_NAME
	 * @param dictName  the value for pension_dict_info.DICT_NAME
	 * @mbggenerated  Thu Nov 28 09:11:22 CST 2013
	 */
	public void setDictName(String dictName) {
		this.dictName = dictName == null ? null : dictName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_dict_info.DICT_DATATABLE_NAME
	 * @return  the value of pension_dict_info.DICT_DATATABLE_NAME
	 * @mbggenerated  Thu Nov 28 09:11:22 CST 2013
	 */
	public String getDictDatatableName() {
		return dictDatatableName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_dict_info.DICT_DATATABLE_NAME
	 * @param dictDatatableName  the value for pension_dict_info.DICT_DATATABLE_NAME
	 * @mbggenerated  Thu Nov 28 09:11:22 CST 2013
	 */
	public void setDictDatatableName(String dictDatatableName) {
		this.dictDatatableName = dictDatatableName == null ? null
				: dictDatatableName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dict_info
	 * @mbggenerated  Thu Nov 28 09:11:22 CST 2013
	 */
	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		PensionDictInfo other = (PensionDictInfo) that;
		return (this.getDictId() == null ? other.getDictId() == null : this
				.getDictId().equals(other.getDictId()))
				&& (this.getDictName() == null ? other.getDictName() == null
						: this.getDictName().equals(other.getDictName()))
				&& (this.getDictDatatableName() == null ? other
						.getDictDatatableName() == null : this
						.getDictDatatableName().equals(
								other.getDictDatatableName()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dict_info
	 * @mbggenerated  Thu Nov 28 09:11:22 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getDictId() == null) ? 0 : getDictId().hashCode());
		result = prime * result
				+ ((getDictName() == null) ? 0 : getDictName().hashCode());
		result = prime
				* result
				+ ((getDictDatatableName() == null) ? 0
						: getDictDatatableName().hashCode());
		return result;
	}
}