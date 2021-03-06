package domain.employee;

import java.io.Serializable;
import java.util.Date;

public class PensionEmployee implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.id
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.rest_id
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String restId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.name
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String name;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.inputCode
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String inputcode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.age
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private Integer age;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.sex
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String sex;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.birthday
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private Date birthday;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.job
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String job;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.health
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String health;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.married
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String married;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.IDcard
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String idcard;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.phone
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String phone;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.knowledge
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String knowledge;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.school
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String school;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.speciality
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String speciality;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.technology
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String technology;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.language
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String language;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.computer
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String computer;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.certName
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String certname;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.dept_id
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private Long deptId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.accessDate
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private Date accessdate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.nativePLace
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String nativeplace;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.address
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String address;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.wage
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private Integer wage;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.notes
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private String notes;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_employee.cleared
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private Integer cleared;
	
	private String bankName;
	
	private String bankCard;
	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankCard
	 */
	public String getBankCard() {
		return bankCard;
	}

	/**
	 * @param bankCard the bankCard to set
	 */
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_employee
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.id
	 * @return  the value of pension_employee.id
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.id
	 * @param id  the value for pension_employee.id
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.rest_id
	 * @return  the value of pension_employee.rest_id
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getRestId() {
		return restId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.rest_id
	 * @param restId  the value for pension_employee.rest_id
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setRestId(String restId) {
		this.restId = restId == null ? null : restId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.name
	 * @return  the value of pension_employee.name
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.name
	 * @param name  the value for pension_employee.name
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.inputCode
	 * @return  the value of pension_employee.inputCode
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getInputcode() {
		return inputcode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.inputCode
	 * @param inputcode  the value for pension_employee.inputCode
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setInputcode(String inputcode) {
		this.inputcode = inputcode == null ? null : inputcode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.age
	 * @return  the value of pension_employee.age
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.age
	 * @param age  the value for pension_employee.age
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.sex
	 * @return  the value of pension_employee.sex
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.sex
	 * @param sex  the value for pension_employee.sex
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setSex(String sex) {
		this.sex = sex == null ? null : sex.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.birthday
	 * @return  the value of pension_employee.birthday
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.birthday
	 * @param birthday  the value for pension_employee.birthday
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.job
	 * @return  the value of pension_employee.job
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getJob() {
		return job;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.job
	 * @param job  the value for pension_employee.job
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setJob(String job) {
		this.job = job == null ? null : job.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.health
	 * @return  the value of pension_employee.health
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getHealth() {
		return health;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.health
	 * @param health  the value for pension_employee.health
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setHealth(String health) {
		this.health = health == null ? null : health.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.married
	 * @return  the value of pension_employee.married
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getMarried() {
		return married;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.married
	 * @param married  the value for pension_employee.married
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setMarried(String married) {
		this.married = married == null ? null : married.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.IDcard
	 * @return  the value of pension_employee.IDcard
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getIdcard() {
		return idcard;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.IDcard
	 * @param idcard  the value for pension_employee.IDcard
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setIdcard(String idcard) {
		this.idcard = idcard == null ? null : idcard.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.phone
	 * @return  the value of pension_employee.phone
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.phone
	 * @param phone  the value for pension_employee.phone
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.knowledge
	 * @return  the value of pension_employee.knowledge
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getKnowledge() {
		return knowledge;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.knowledge
	 * @param knowledge  the value for pension_employee.knowledge
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setKnowledge(String knowledge) {
		this.knowledge = knowledge == null ? null : knowledge.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.school
	 * @return  the value of pension_employee.school
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.school
	 * @param school  the value for pension_employee.school
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setSchool(String school) {
		this.school = school == null ? null : school.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.speciality
	 * @return  the value of pension_employee.speciality
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getSpeciality() {
		return speciality;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.speciality
	 * @param speciality  the value for pension_employee.speciality
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setSpeciality(String speciality) {
		this.speciality = speciality == null ? null : speciality.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.technology
	 * @return  the value of pension_employee.technology
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getTechnology() {
		return technology;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.technology
	 * @param technology  the value for pension_employee.technology
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setTechnology(String technology) {
		this.technology = technology == null ? null : technology.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.language
	 * @return  the value of pension_employee.language
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.language
	 * @param language  the value for pension_employee.language
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setLanguage(String language) {
		this.language = language == null ? null : language.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.computer
	 * @return  the value of pension_employee.computer
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getComputer() {
		return computer;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.computer
	 * @param computer  the value for pension_employee.computer
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setComputer(String computer) {
		this.computer = computer == null ? null : computer.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.certName
	 * @return  the value of pension_employee.certName
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getCertname() {
		return certname;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.certName
	 * @param certname  the value for pension_employee.certName
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setCertname(String certname) {
		this.certname = certname == null ? null : certname.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.dept_id
	 * @return  the value of pension_employee.dept_id
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public Long getDeptId() {
		return deptId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.dept_id
	 * @param deptId  the value for pension_employee.dept_id
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.accessDate
	 * @return  the value of pension_employee.accessDate
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public Date getAccessdate() {
		return accessdate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.accessDate
	 * @param accessdate  the value for pension_employee.accessDate
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setAccessdate(Date accessdate) {
		this.accessdate = accessdate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.nativePLace
	 * @return  the value of pension_employee.nativePLace
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getNativeplace() {
		return nativeplace;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.nativePLace
	 * @param nativeplace  the value for pension_employee.nativePLace
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace == null ? null : nativeplace.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.address
	 * @return  the value of pension_employee.address
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.address
	 * @param address  the value for pension_employee.address
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.wage
	 * @return  the value of pension_employee.wage
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public Integer getWage() {
		return wage;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.wage
	 * @param wage  the value for pension_employee.wage
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setWage(Integer wage) {
		this.wage = wage;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.notes
	 * @return  the value of pension_employee.notes
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.notes
	 * @param notes  the value for pension_employee.notes
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setNotes(String notes) {
		this.notes = notes == null ? null : notes.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_employee.cleared
	 * @return  the value of pension_employee.cleared
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public Integer getCleared() {
		return cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_employee.cleared
	 * @param cleared  the value for pension_employee.cleared
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	public void setCleared(Integer cleared) {
		this.cleared = cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_employee
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
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
		PensionEmployee other = (PensionEmployee) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getRestId() == null ? other.getRestId() == null : this
						.getRestId().equals(other.getRestId()))
				&& (this.getName() == null ? other.getName() == null : this
						.getName().equals(other.getName()))
				&& (this.getInputcode() == null ? other.getInputcode() == null
						: this.getInputcode().equals(other.getInputcode()))
				&& (this.getAge() == null ? other.getAge() == null : this
						.getAge().equals(other.getAge()))
				&& (this.getSex() == null ? other.getSex() == null : this
						.getSex().equals(other.getSex()))
				&& (this.getBirthday() == null ? other.getBirthday() == null
						: this.getBirthday().equals(other.getBirthday()))
				&& (this.getJob() == null ? other.getJob() == null : this
						.getJob().equals(other.getJob()))
				&& (this.getHealth() == null ? other.getHealth() == null : this
						.getHealth().equals(other.getHealth()))
				&& (this.getMarried() == null ? other.getMarried() == null
						: this.getMarried().equals(other.getMarried()))
				&& (this.getIdcard() == null ? other.getIdcard() == null : this
						.getIdcard().equals(other.getIdcard()))
				&& (this.getPhone() == null ? other.getPhone() == null : this
						.getPhone().equals(other.getPhone()))
				&& (this.getKnowledge() == null ? other.getKnowledge() == null
						: this.getKnowledge().equals(other.getKnowledge()))
				&& (this.getSchool() == null ? other.getSchool() == null : this
						.getSchool().equals(other.getSchool()))
				&& (this.getSpeciality() == null ? other.getSpeciality() == null
						: this.getSpeciality().equals(other.getSpeciality()))
				&& (this.getTechnology() == null ? other.getTechnology() == null
						: this.getTechnology().equals(other.getTechnology()))
				&& (this.getLanguage() == null ? other.getLanguage() == null
						: this.getLanguage().equals(other.getLanguage()))
				&& (this.getComputer() == null ? other.getComputer() == null
						: this.getComputer().equals(other.getComputer()))
				&& (this.getCertname() == null ? other.getCertname() == null
						: this.getCertname().equals(other.getCertname()))
				&& (this.getDeptId() == null ? other.getDeptId() == null : this
						.getDeptId().equals(other.getDeptId()))
				&& (this.getAccessdate() == null ? other.getAccessdate() == null
						: this.getAccessdate().equals(other.getAccessdate()))
				&& (this.getNativeplace() == null ? other.getNativeplace() == null
						: this.getNativeplace().equals(other.getNativeplace()))
				&& (this.getAddress() == null ? other.getAddress() == null
						: this.getAddress().equals(other.getAddress()))
				&& (this.getWage() == null ? other.getWage() == null : this
						.getWage().equals(other.getWage()))
				&& (this.getNotes() == null ? other.getNotes() == null : this
						.getNotes().equals(other.getNotes()))
				&& (this.getCleared() == null ? other.getCleared() == null
						: this.getCleared().equals(other.getCleared()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_employee
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getRestId() == null) ? 0 : getRestId().hashCode());
		result = prime * result
				+ ((getName() == null) ? 0 : getName().hashCode());
		result = prime * result
				+ ((getInputcode() == null) ? 0 : getInputcode().hashCode());
		result = prime * result
				+ ((getAge() == null) ? 0 : getAge().hashCode());
		result = prime * result
				+ ((getSex() == null) ? 0 : getSex().hashCode());
		result = prime * result
				+ ((getBirthday() == null) ? 0 : getBirthday().hashCode());
		result = prime * result
				+ ((getJob() == null) ? 0 : getJob().hashCode());
		result = prime * result
				+ ((getHealth() == null) ? 0 : getHealth().hashCode());
		result = prime * result
				+ ((getMarried() == null) ? 0 : getMarried().hashCode());
		result = prime * result
				+ ((getIdcard() == null) ? 0 : getIdcard().hashCode());
		result = prime * result
				+ ((getPhone() == null) ? 0 : getPhone().hashCode());
		result = prime * result
				+ ((getKnowledge() == null) ? 0 : getKnowledge().hashCode());
		result = prime * result
				+ ((getSchool() == null) ? 0 : getSchool().hashCode());
		result = prime * result
				+ ((getSpeciality() == null) ? 0 : getSpeciality().hashCode());
		result = prime * result
				+ ((getTechnology() == null) ? 0 : getTechnology().hashCode());
		result = prime * result
				+ ((getLanguage() == null) ? 0 : getLanguage().hashCode());
		result = prime * result
				+ ((getComputer() == null) ? 0 : getComputer().hashCode());
		result = prime * result
				+ ((getCertname() == null) ? 0 : getCertname().hashCode());
		result = prime * result
				+ ((getDeptId() == null) ? 0 : getDeptId().hashCode());
		result = prime * result
				+ ((getAccessdate() == null) ? 0 : getAccessdate().hashCode());
		result = prime
				* result
				+ ((getNativeplace() == null) ? 0 : getNativeplace().hashCode());
		result = prime * result
				+ ((getAddress() == null) ? 0 : getAddress().hashCode());
		result = prime * result
				+ ((getWage() == null) ? 0 : getWage().hashCode());
		result = prime * result
				+ ((getNotes() == null) ? 0 : getNotes().hashCode());
		result = prime * result
				+ ((getCleared() == null) ? 0 : getCleared().hashCode());
		return result;
	}
}