package domain.recoup;

import java.io.Serializable;

public class RecoupDicCostclass1 implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column recoup_dic_costclass1.id
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column recoup_dic_costclass1.class1_code
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    private String class1Code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column recoup_dic_costclass1.class1_name
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    private String class1Name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column recoup_dic_costclass1.input_code
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    private String inputCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column recoup_dic_costclass1.note
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    private String note;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column recoup_dic_costclass1.cleared
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    private Integer cleared;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table recoup_dic_costclass1
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recoup_dic_costclass1.id
     *
     * @return the value of recoup_dic_costclass1.id
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recoup_dic_costclass1.id
     *
     * @param id the value for recoup_dic_costclass1.id
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recoup_dic_costclass1.class1_code
     *
     * @return the value of recoup_dic_costclass1.class1_code
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    public String getClass1Code() {
        return class1Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recoup_dic_costclass1.class1_code
     *
     * @param class1Code the value for recoup_dic_costclass1.class1_code
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    public void setClass1Code(String class1Code) {
        this.class1Code = class1Code == null ? null : class1Code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recoup_dic_costclass1.class1_name
     *
     * @return the value of recoup_dic_costclass1.class1_name
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    public String getClass1Name() {
        return class1Name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recoup_dic_costclass1.class1_name
     *
     * @param class1Name the value for recoup_dic_costclass1.class1_name
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    public void setClass1Name(String class1Name) {
        this.class1Name = class1Name == null ? null : class1Name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recoup_dic_costclass1.input_code
     *
     * @return the value of recoup_dic_costclass1.input_code
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    public String getInputCode() {
        return inputCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recoup_dic_costclass1.input_code
     *
     * @param inputCode the value for recoup_dic_costclass1.input_code
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    public void setInputCode(String inputCode) {
        this.inputCode = inputCode == null ? null : inputCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recoup_dic_costclass1.note
     *
     * @return the value of recoup_dic_costclass1.note
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    public String getNote() {
        return note;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recoup_dic_costclass1.note
     *
     * @param note the value for recoup_dic_costclass1.note
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recoup_dic_costclass1.cleared
     *
     * @return the value of recoup_dic_costclass1.cleared
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    public Integer getCleared() {
        return cleared;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recoup_dic_costclass1.cleared
     *
     * @param cleared the value for recoup_dic_costclass1.cleared
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    public void setCleared(Integer cleared) {
        this.cleared = cleared;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_dic_costclass1
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
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
        RecoupDicCostclass1 other = (RecoupDicCostclass1) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getClass1Code() == null ? other.getClass1Code() == null : this.getClass1Code().equals(other.getClass1Code()))
            && (this.getClass1Name() == null ? other.getClass1Name() == null : this.getClass1Name().equals(other.getClass1Name()))
            && (this.getInputCode() == null ? other.getInputCode() == null : this.getInputCode().equals(other.getInputCode()))
            && (this.getNote() == null ? other.getNote() == null : this.getNote().equals(other.getNote()))
            && (this.getCleared() == null ? other.getCleared() == null : this.getCleared().equals(other.getCleared()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_dic_costclass1
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getClass1Code() == null) ? 0 : getClass1Code().hashCode());
        result = prime * result + ((getClass1Name() == null) ? 0 : getClass1Name().hashCode());
        result = prime * result + ((getInputCode() == null) ? 0 : getInputCode().hashCode());
        result = prime * result + ((getNote() == null) ? 0 : getNote().hashCode());
        result = prime * result + ((getCleared() == null) ? 0 : getCleared().hashCode());
        return result;
    }
}