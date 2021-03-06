package domain.finance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PensionSettleRecordExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pension_settle_record
     *
     * @mbggenerated Tue Oct 29 15:06:28 GMT+08:00 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pension_settle_record
     *
     * @mbggenerated Tue Oct 29 15:06:28 GMT+08:00 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pension_settle_record
     *
     * @mbggenerated Tue Oct 29 15:06:28 GMT+08:00 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_settle_record
     *
     * @mbggenerated Tue Oct 29 15:06:28 GMT+08:00 2013
     */
    public PensionSettleRecordExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_settle_record
     *
     * @mbggenerated Tue Oct 29 15:06:28 GMT+08:00 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_settle_record
     *
     * @mbggenerated Tue Oct 29 15:06:28 GMT+08:00 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_settle_record
     *
     * @mbggenerated Tue Oct 29 15:06:28 GMT+08:00 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_settle_record
     *
     * @mbggenerated Tue Oct 29 15:06:28 GMT+08:00 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_settle_record
     *
     * @mbggenerated Tue Oct 29 15:06:28 GMT+08:00 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_settle_record
     *
     * @mbggenerated Tue Oct 29 15:06:28 GMT+08:00 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_settle_record
     *
     * @mbggenerated Tue Oct 29 15:06:28 GMT+08:00 2013
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_settle_record
     *
     * @mbggenerated Tue Oct 29 15:06:28 GMT+08:00 2013
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
     * This method corresponds to the database table pension_settle_record
     *
     * @mbggenerated Tue Oct 29 15:06:28 GMT+08:00 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_settle_record
     *
     * @mbggenerated Tue Oct 29 15:06:28 GMT+08:00 2013
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table pension_settle_record
     *
     * @mbggenerated Tue Oct 29 15:06:28 GMT+08:00 2013
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

        public Criteria andSettlerIdIsNull() {
            addCriterion("settler_id is null");
            return (Criteria) this;
        }

        public Criteria andSettlerIdIsNotNull() {
            addCriterion("settler_id is not null");
            return (Criteria) this;
        }

        public Criteria andSettlerIdEqualTo(Long value) {
            addCriterion("settler_id =", value, "settlerId");
            return (Criteria) this;
        }

        public Criteria andSettlerIdNotEqualTo(Long value) {
            addCriterion("settler_id <>", value, "settlerId");
            return (Criteria) this;
        }

        public Criteria andSettlerIdGreaterThan(Long value) {
            addCriterion("settler_id >", value, "settlerId");
            return (Criteria) this;
        }

        public Criteria andSettlerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("settler_id >=", value, "settlerId");
            return (Criteria) this;
        }

        public Criteria andSettlerIdLessThan(Long value) {
            addCriterion("settler_id <", value, "settlerId");
            return (Criteria) this;
        }

        public Criteria andSettlerIdLessThanOrEqualTo(Long value) {
            addCriterion("settler_id <=", value, "settlerId");
            return (Criteria) this;
        }

        public Criteria andSettlerIdIn(List<Long> values) {
            addCriterion("settler_id in", values, "settlerId");
            return (Criteria) this;
        }

        public Criteria andSettlerIdNotIn(List<Long> values) {
            addCriterion("settler_id not in", values, "settlerId");
            return (Criteria) this;
        }

        public Criteria andSettlerIdBetween(Long value1, Long value2) {
            addCriterion("settler_id between", value1, value2, "settlerId");
            return (Criteria) this;
        }

        public Criteria andSettlerIdNotBetween(Long value1, Long value2) {
            addCriterion("settler_id not between", value1, value2, "settlerId");
            return (Criteria) this;
        }

        public Criteria andSettlerNameIsNull() {
            addCriterion("settler_name is null");
            return (Criteria) this;
        }

        public Criteria andSettlerNameIsNotNull() {
            addCriterion("settler_name is not null");
            return (Criteria) this;
        }

        public Criteria andSettlerNameEqualTo(String value) {
            addCriterion("settler_name =", value, "settlerName");
            return (Criteria) this;
        }

        public Criteria andSettlerNameNotEqualTo(String value) {
            addCriterion("settler_name <>", value, "settlerName");
            return (Criteria) this;
        }

        public Criteria andSettlerNameGreaterThan(String value) {
            addCriterion("settler_name >", value, "settlerName");
            return (Criteria) this;
        }

        public Criteria andSettlerNameGreaterThanOrEqualTo(String value) {
            addCriterion("settler_name >=", value, "settlerName");
            return (Criteria) this;
        }

        public Criteria andSettlerNameLessThan(String value) {
            addCriterion("settler_name <", value, "settlerName");
            return (Criteria) this;
        }

        public Criteria andSettlerNameLessThanOrEqualTo(String value) {
            addCriterion("settler_name <=", value, "settlerName");
            return (Criteria) this;
        }

        public Criteria andSettlerNameLike(String value) {
            addCriterion("settler_name like", value, "settlerName");
            return (Criteria) this;
        }

        public Criteria andSettlerNameNotLike(String value) {
            addCriterion("settler_name not like", value, "settlerName");
            return (Criteria) this;
        }

        public Criteria andSettlerNameIn(List<String> values) {
            addCriterion("settler_name in", values, "settlerName");
            return (Criteria) this;
        }

        public Criteria andSettlerNameNotIn(List<String> values) {
            addCriterion("settler_name not in", values, "settlerName");
            return (Criteria) this;
        }

        public Criteria andSettlerNameBetween(String value1, String value2) {
            addCriterion("settler_name between", value1, value2, "settlerName");
            return (Criteria) this;
        }

        public Criteria andSettlerNameNotBetween(String value1, String value2) {
            addCriterion("settler_name not between", value1, value2, "settlerName");
            return (Criteria) this;
        }

        public Criteria andChangeTimeIsNull() {
            addCriterion("change_time is null");
            return (Criteria) this;
        }

        public Criteria andChangeTimeIsNotNull() {
            addCriterion("change_time is not null");
            return (Criteria) this;
        }

        public Criteria andChangeTimeEqualTo(Date value) {
            addCriterion("change_time =", value, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeNotEqualTo(Date value) {
            addCriterion("change_time <>", value, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeGreaterThan(Date value) {
            addCriterion("change_time >", value, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("change_time >=", value, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeLessThan(Date value) {
            addCriterion("change_time <", value, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeLessThanOrEqualTo(Date value) {
            addCriterion("change_time <=", value, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeIn(List<Date> values) {
            addCriterion("change_time in", values, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeNotIn(List<Date> values) {
            addCriterion("change_time not in", values, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeBetween(Date value1, Date value2) {
            addCriterion("change_time between", value1, value2, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeNotBetween(Date value1, Date value2) {
            addCriterion("change_time not between", value1, value2, "changeTime");
            return (Criteria) this;
        }

        public Criteria andTotalAmountIsNull() {
            addCriterion("total_amount is null");
            return (Criteria) this;
        }

        public Criteria andTotalAmountIsNotNull() {
            addCriterion("total_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalAmountEqualTo(Double value) {
            addCriterion("total_amount =", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotEqualTo(Double value) {
            addCriterion("total_amount <>", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountGreaterThan(Double value) {
            addCriterion("total_amount >", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("total_amount >=", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountLessThan(Double value) {
            addCriterion("total_amount <", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountLessThanOrEqualTo(Double value) {
            addCriterion("total_amount <=", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountIn(List<Double> values) {
            addCriterion("total_amount in", values, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotIn(List<Double> values) {
            addCriterion("total_amount not in", values, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountBetween(Double value1, Double value2) {
            addCriterion("total_amount between", value1, value2, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotBetween(Double value1, Double value2) {
            addCriterion("total_amount not between", value1, value2, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andIncomeAmountIsNull() {
            addCriterion("income_amount is null");
            return (Criteria) this;
        }

        public Criteria andIncomeAmountIsNotNull() {
            addCriterion("income_amount is not null");
            return (Criteria) this;
        }

        public Criteria andIncomeAmountEqualTo(Float value) {
            addCriterion("income_amount =", value, "incomeAmount");
            return (Criteria) this;
        }

        public Criteria andIncomeAmountNotEqualTo(Float value) {
            addCriterion("income_amount <>", value, "incomeAmount");
            return (Criteria) this;
        }

        public Criteria andIncomeAmountGreaterThan(Float value) {
            addCriterion("income_amount >", value, "incomeAmount");
            return (Criteria) this;
        }

        public Criteria andIncomeAmountGreaterThanOrEqualTo(Float value) {
            addCriterion("income_amount >=", value, "incomeAmount");
            return (Criteria) this;
        }

        public Criteria andIncomeAmountLessThan(Float value) {
            addCriterion("income_amount <", value, "incomeAmount");
            return (Criteria) this;
        }

        public Criteria andIncomeAmountLessThanOrEqualTo(Float value) {
            addCriterion("income_amount <=", value, "incomeAmount");
            return (Criteria) this;
        }

        public Criteria andIncomeAmountIn(List<Float> values) {
            addCriterion("income_amount in", values, "incomeAmount");
            return (Criteria) this;
        }

        public Criteria andIncomeAmountNotIn(List<Float> values) {
            addCriterion("income_amount not in", values, "incomeAmount");
            return (Criteria) this;
        }

        public Criteria andIncomeAmountBetween(Float value1, Float value2) {
            addCriterion("income_amount between", value1, value2, "incomeAmount");
            return (Criteria) this;
        }

        public Criteria andIncomeAmountNotBetween(Float value1, Float value2) {
            addCriterion("income_amount not between", value1, value2, "incomeAmount");
            return (Criteria) this;
        }

        public Criteria andExpensesAmountIsNull() {
            addCriterion("expenses_amount is null");
            return (Criteria) this;
        }

        public Criteria andExpensesAmountIsNotNull() {
            addCriterion("expenses_amount is not null");
            return (Criteria) this;
        }

        public Criteria andExpensesAmountEqualTo(Float value) {
            addCriterion("expenses_amount =", value, "expensesAmount");
            return (Criteria) this;
        }

        public Criteria andExpensesAmountNotEqualTo(Float value) {
            addCriterion("expenses_amount <>", value, "expensesAmount");
            return (Criteria) this;
        }

        public Criteria andExpensesAmountGreaterThan(Float value) {
            addCriterion("expenses_amount >", value, "expensesAmount");
            return (Criteria) this;
        }

        public Criteria andExpensesAmountGreaterThanOrEqualTo(Float value) {
            addCriterion("expenses_amount >=", value, "expensesAmount");
            return (Criteria) this;
        }

        public Criteria andExpensesAmountLessThan(Float value) {
            addCriterion("expenses_amount <", value, "expensesAmount");
            return (Criteria) this;
        }

        public Criteria andExpensesAmountLessThanOrEqualTo(Float value) {
            addCriterion("expenses_amount <=", value, "expensesAmount");
            return (Criteria) this;
        }

        public Criteria andExpensesAmountIn(List<Float> values) {
            addCriterion("expenses_amount in", values, "expensesAmount");
            return (Criteria) this;
        }

        public Criteria andExpensesAmountNotIn(List<Float> values) {
            addCriterion("expenses_amount not in", values, "expensesAmount");
            return (Criteria) this;
        }

        public Criteria andExpensesAmountBetween(Float value1, Float value2) {
            addCriterion("expenses_amount between", value1, value2, "expensesAmount");
            return (Criteria) this;
        }

        public Criteria andExpensesAmountNotBetween(Float value1, Float value2) {
            addCriterion("expenses_amount not between", value1, value2, "expensesAmount");
            return (Criteria) this;
        }

        public Criteria andSettletypeIdIsNull() {
            addCriterion("settletype_id is null");
            return (Criteria) this;
        }

        public Criteria andSettletypeIdIsNotNull() {
            addCriterion("settletype_id is not null");
            return (Criteria) this;
        }

        public Criteria andSettletypeIdEqualTo(Long value) {
            addCriterion("settletype_id =", value, "settletypeId");
            return (Criteria) this;
        }

        public Criteria andSettletypeIdNotEqualTo(Long value) {
            addCriterion("settletype_id <>", value, "settletypeId");
            return (Criteria) this;
        }

        public Criteria andSettletypeIdGreaterThan(Long value) {
            addCriterion("settletype_id >", value, "settletypeId");
            return (Criteria) this;
        }

        public Criteria andSettletypeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("settletype_id >=", value, "settletypeId");
            return (Criteria) this;
        }

        public Criteria andSettletypeIdLessThan(Long value) {
            addCriterion("settletype_id <", value, "settletypeId");
            return (Criteria) this;
        }

        public Criteria andSettletypeIdLessThanOrEqualTo(Long value) {
            addCriterion("settletype_id <=", value, "settletypeId");
            return (Criteria) this;
        }

        public Criteria andSettletypeIdIn(List<Long> values) {
            addCriterion("settletype_id in", values, "settletypeId");
            return (Criteria) this;
        }

        public Criteria andSettletypeIdNotIn(List<Long> values) {
            addCriterion("settletype_id not in", values, "settletypeId");
            return (Criteria) this;
        }

        public Criteria andSettletypeIdBetween(Long value1, Long value2) {
            addCriterion("settletype_id between", value1, value2, "settletypeId");
            return (Criteria) this;
        }

        public Criteria andSettletypeIdNotBetween(Long value1, Long value2) {
            addCriterion("settletype_id not between", value1, value2, "settletypeId");
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
     * This class corresponds to the database table pension_settle_record
     *
     * @mbggenerated do_not_delete_during_merge Tue Oct 29 15:06:28 GMT+08:00 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table pension_settle_record
     *
     * @mbggenerated Tue Oct 29 15:06:28 GMT+08:00 2013
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