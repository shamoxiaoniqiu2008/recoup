package domain;

import java.io.Serializable;

public class Testtable implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column testtable.id
	 * @mbggenerated  Tue Aug 20 19:11:24 CST 2013
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column testtable.name
	 * @mbggenerated  Tue Aug 20 19:11:24 CST 2013
	 */
	private String name;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table testtable
	 * @mbggenerated  Tue Aug 20 19:11:24 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column testtable.id
	 * @return  the value of testtable.id
	 * @mbggenerated  Tue Aug 20 19:11:24 CST 2013
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column testtable.id
	 * @param id  the value for testtable.id
	 * @mbggenerated  Tue Aug 20 19:11:24 CST 2013
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column testtable.name
	 * @return  the value of testtable.name
	 * @mbggenerated  Tue Aug 20 19:11:24 CST 2013
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column testtable.name
	 * @param name  the value for testtable.name
	 * @mbggenerated  Tue Aug 20 19:11:24 CST 2013
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table testtable
	 * @mbggenerated  Tue Aug 20 19:11:24 CST 2013
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
		Testtable other = (Testtable) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getName() == null ? other.getName() == null : this
						.getName().equals(other.getName()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table testtable
	 * @mbggenerated  Tue Aug 20 19:11:24 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getName() == null) ? 0 : getName().hashCode());
		return result;
	}
}