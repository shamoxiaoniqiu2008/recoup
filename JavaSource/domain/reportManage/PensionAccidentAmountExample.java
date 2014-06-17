package domain.reportManage;

import java.util.ArrayList;
import java.util.List;

public class PensionAccidentAmountExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pension_accident_amount
     *
     * @mbggenerated Sat Nov 16 11:23:35 CST 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pension_accident_amount
     *
     * @mbggenerated Sat Nov 16 11:23:35 CST 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pension_accident_amount
     *
     * @mbggenerated Sat Nov 16 11:23:35 CST 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_accident_amount
     *
     * @mbggenerated Sat Nov 16 11:23:35 CST 2013
     */
    public PensionAccidentAmountExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_accident_amount
     *
     * @mbggenerated Sat Nov 16 11:23:35 CST 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_accident_amount
     *
     * @mbggenerated Sat Nov 16 11:23:35 CST 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_accident_amount
     *
     * @mbggenerated Sat Nov 16 11:23:35 CST 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_accident_amount
     *
     * @mbggenerated Sat Nov 16 11:23:35 CST 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_accident_amount
     *
     * @mbggenerated Sat Nov 16 11:23:35 CST 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_accident_amount
     *
     * @mbggenerated Sat Nov 16 11:23:35 CST 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_accident_amount
     *
     * @mbggenerated Sat Nov 16 11:23:35 CST 2013
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_accident_amount
     *
     * @mbggenerated Sat Nov 16 11:23:35 CST 2013
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_accident_amount
     *
     * @mbggenerated Sat Nov 16 11:23:35 CST 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_accident_amount
     *
     * @mbggenerated Sat Nov 16 11:23:35 CST 2013
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table pension_accident_amount
     *
     * @mbggenerated Sat Nov 16 11:23:35 CST 2013
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null || value.toString().equals("") ) {
                 addCriterion("1=1"); 
                 return; 
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null || value1.toString().equals("")|| value2.toString().equals("") ) {
                 addCriterion("1=1"); 
                 return; 
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andReportIdIsNull() {
            addCriterion("report_id is null");
            return (Criteria) this;
        }

        public Criteria andReportIdIsNotNull() {
            addCriterion("report_id is not null");
            return (Criteria) this;
        }

        public Criteria andReportIdEqualTo(Long value) {
            addCriterion("report_id =", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdNotEqualTo(Long value) {
            addCriterion("report_id <>", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdGreaterThan(Long value) {
            addCriterion("report_id >", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdGreaterThanOrEqualTo(Long value) {
            addCriterion("report_id >=", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdLessThan(Long value) {
            addCriterion("report_id <", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdLessThanOrEqualTo(Long value) {
            addCriterion("report_id <=", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdIn(List<Long> values) {
            addCriterion("report_id in", values, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdNotIn(List<Long> values) {
            addCriterion("report_id not in", values, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdBetween(Long value1, Long value2) {
            addCriterion("report_id between", value1, value2, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdNotBetween(Long value1, Long value2) {
            addCriterion("report_id not between", value1, value2, "reportId");
            return (Criteria) this;
        }

        public Criteria andAccidentIdIsNull() {
            addCriterion("accident_id is null");
            return (Criteria) this;
        }

        public Criteria andAccidentIdIsNotNull() {
            addCriterion("accident_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccidentIdEqualTo(Long value) {
            addCriterion("accident_id =", value, "accidentId");
            return (Criteria) this;
        }

        public Criteria andAccidentIdNotEqualTo(Long value) {
            addCriterion("accident_id <>", value, "accidentId");
            return (Criteria) this;
        }

        public Criteria andAccidentIdGreaterThan(Long value) {
            addCriterion("accident_id >", value, "accidentId");
            return (Criteria) this;
        }

        public Criteria andAccidentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("accident_id >=", value, "accidentId");
            return (Criteria) this;
        }

        public Criteria andAccidentIdLessThan(Long value) {
            addCriterion("accident_id <", value, "accidentId");
            return (Criteria) this;
        }

        public Criteria andAccidentIdLessThanOrEqualTo(Long value) {
            addCriterion("accident_id <=", value, "accidentId");
            return (Criteria) this;
        }

        public Criteria andAccidentIdIn(List<Long> values) {
            addCriterion("accident_id in", values, "accidentId");
            return (Criteria) this;
        }

        public Criteria andAccidentIdNotIn(List<Long> values) {
            addCriterion("accident_id not in", values, "accidentId");
            return (Criteria) this;
        }

        public Criteria andAccidentIdBetween(Long value1, Long value2) {
            addCriterion("accident_id between", value1, value2, "accidentId");
            return (Criteria) this;
        }

        public Criteria andAccidentIdNotBetween(Long value1, Long value2) {
            addCriterion("accident_id not between", value1, value2, "accidentId");
            return (Criteria) this;
        }

        public Criteria andAccidentNameIsNull() {
            addCriterion("accident_name is null");
            return (Criteria) this;
        }

        public Criteria andAccidentNameIsNotNull() {
            addCriterion("accident_name is not null");
            return (Criteria) this;
        }

        public Criteria andAccidentNameEqualTo(String value) {
            addCriterion("accident_name =", value, "accidentName");
            return (Criteria) this;
        }

        public Criteria andAccidentNameNotEqualTo(String value) {
            addCriterion("accident_name <>", value, "accidentName");
            return (Criteria) this;
        }

        public Criteria andAccidentNameGreaterThan(String value) {
            addCriterion("accident_name >", value, "accidentName");
            return (Criteria) this;
        }

        public Criteria andAccidentNameGreaterThanOrEqualTo(String value) {
            addCriterion("accident_name >=", value, "accidentName");
            return (Criteria) this;
        }

        public Criteria andAccidentNameLessThan(String value) {
            addCriterion("accident_name <", value, "accidentName");
            return (Criteria) this;
        }

        public Criteria andAccidentNameLessThanOrEqualTo(String value) {
            addCriterion("accident_name <=", value, "accidentName");
            return (Criteria) this;
        }

        public Criteria andAccidentNameLike(String value) {
            addCriterion("accident_name like", value, "accidentName");
            return (Criteria) this;
        }

        public Criteria andAccidentNameNotLike(String value) {
            addCriterion("accident_name not like", value, "accidentName");
            return (Criteria) this;
        }

        public Criteria andAccidentNameIn(List<String> values) {
            addCriterion("accident_name in", values, "accidentName");
            return (Criteria) this;
        }

        public Criteria andAccidentNameNotIn(List<String> values) {
            addCriterion("accident_name not in", values, "accidentName");
            return (Criteria) this;
        }

        public Criteria andAccidentNameBetween(String value1, String value2) {
            addCriterion("accident_name between", value1, value2, "accidentName");
            return (Criteria) this;
        }

        public Criteria andAccidentNameNotBetween(String value1, String value2) {
            addCriterion("accident_name not between", value1, value2, "accidentName");
            return (Criteria) this;
        }

        public Criteria andOlderNumIsNull() {
            addCriterion("older_num is null");
            return (Criteria) this;
        }

        public Criteria andOlderNumIsNotNull() {
            addCriterion("older_num is not null");
            return (Criteria) this;
        }

        public Criteria andOlderNumEqualTo(Integer value) {
            addCriterion("older_num =", value, "olderNum");
            return (Criteria) this;
        }

        public Criteria andOlderNumNotEqualTo(Integer value) {
            addCriterion("older_num <>", value, "olderNum");
            return (Criteria) this;
        }

        public Criteria andOlderNumGreaterThan(Integer value) {
            addCriterion("older_num >", value, "olderNum");
            return (Criteria) this;
        }

        public Criteria andOlderNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("older_num >=", value, "olderNum");
            return (Criteria) this;
        }

        public Criteria andOlderNumLessThan(Integer value) {
            addCriterion("older_num <", value, "olderNum");
            return (Criteria) this;
        }

        public Criteria andOlderNumLessThanOrEqualTo(Integer value) {
            addCriterion("older_num <=", value, "olderNum");
            return (Criteria) this;
        }

        public Criteria andOlderNumIn(List<Integer> values) {
            addCriterion("older_num in", values, "olderNum");
            return (Criteria) this;
        }

        public Criteria andOlderNumNotIn(List<Integer> values) {
            addCriterion("older_num not in", values, "olderNum");
            return (Criteria) this;
        }

        public Criteria andOlderNumBetween(Integer value1, Integer value2) {
            addCriterion("older_num between", value1, value2, "olderNum");
            return (Criteria) this;
        }

        public Criteria andOlderNumNotBetween(Integer value1, Integer value2) {
            addCriterion("older_num not between", value1, value2, "olderNum");
            return (Criteria) this;
        }

        public Criteria andClearedIsNull() {
            addCriterion("cleared is null");
            return (Criteria) this;
        }

        public Criteria andClearedIsNotNull() {
            addCriterion("cleared is not null");
            return (Criteria) this;
        }

        public Criteria andClearedEqualTo(Integer value) {
            addCriterion("cleared =", value, "cleared");
            return (Criteria) this;
        }

        public Criteria andClearedNotEqualTo(Integer value) {
            addCriterion("cleared <>", value, "cleared");
            return (Criteria) this;
        }

        public Criteria andClearedGreaterThan(Integer value) {
            addCriterion("cleared >", value, "cleared");
            return (Criteria) this;
        }

        public Criteria andClearedGreaterThanOrEqualTo(Integer value) {
            addCriterion("cleared >=", value, "cleared");
            return (Criteria) this;
        }

        public Criteria andClearedLessThan(Integer value) {
            addCriterion("cleared <", value, "cleared");
            return (Criteria) this;
        }

        public Criteria andClearedLessThanOrEqualTo(Integer value) {
            addCriterion("cleared <=", value, "cleared");
            return (Criteria) this;
        }

        public Criteria andClearedIn(List<Integer> values) {
            addCriterion("cleared in", values, "cleared");
            return (Criteria) this;
        }

        public Criteria andClearedNotIn(List<Integer> values) {
            addCriterion("cleared not in", values, "cleared");
            return (Criteria) this;
        }

        public Criteria andClearedBetween(Integer value1, Integer value2) {
            addCriterion("cleared between", value1, value2, "cleared");
            return (Criteria) this;
        }

        public Criteria andClearedNotBetween(Integer value1, Integer value2) {
            addCriterion("cleared not between", value1, value2, "cleared");
            return (Criteria) this;
        }

        public Criteria andNoteIsNull() {
            addCriterion("note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            addCriterion("note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            addCriterion("note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            addCriterion("note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            addCriterion("note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            addCriterion("note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            addCriterion("note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            addCriterion("note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            addCriterion("note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            addCriterion("note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            addCriterion("note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            addCriterion("note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            addCriterion("note not between", value1, value2, "note");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table pension_accident_amount
     *
     * @mbggenerated do_not_delete_during_merge Sat Nov 16 11:23:35 CST 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table pension_accident_amount
     *
     * @mbggenerated Sat Nov 16 11:23:35 CST 2013
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}