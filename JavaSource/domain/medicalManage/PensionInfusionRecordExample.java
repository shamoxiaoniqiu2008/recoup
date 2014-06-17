package domain.medicalManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PensionInfusionRecordExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_infusion_record
	 * @mbggenerated  Thu Dec 26 10:16:19 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_infusion_record
	 * @mbggenerated  Thu Dec 26 10:16:19 CST 2013
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_infusion_record
	 * @mbggenerated  Thu Dec 26 10:16:19 CST 2013
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_infusion_record
	 * @mbggenerated  Thu Dec 26 10:16:19 CST 2013
	 */
	public PensionInfusionRecordExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_infusion_record
	 * @mbggenerated  Thu Dec 26 10:16:19 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_infusion_record
	 * @mbggenerated  Thu Dec 26 10:16:19 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_infusion_record
	 * @mbggenerated  Thu Dec 26 10:16:19 CST 2013
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_infusion_record
	 * @mbggenerated  Thu Dec 26 10:16:19 CST 2013
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_infusion_record
	 * @mbggenerated  Thu Dec 26 10:16:19 CST 2013
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_infusion_record
	 * @mbggenerated  Thu Dec 26 10:16:19 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_infusion_record
	 * @mbggenerated  Thu Dec 26 10:16:19 CST 2013
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_infusion_record
	 * @mbggenerated  Thu Dec 26 10:16:19 CST 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_infusion_record
	 * @mbggenerated  Thu Dec 26 10:16:19 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_infusion_record
	 * @mbggenerated  Thu Dec 26 10:16:19 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table pension_infusion_record
	 * @mbggenerated  Thu Dec 26 10:16:19 CST 2013
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

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null || value.toString().equals("")) {
				addCriterion("1=1");
				return;
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null
					|| value1.toString().equals("")
					|| value2.toString().equals("")) {
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

		public Criteria andApplyIdIsNull() {
			addCriterion("apply_id is null");
			return (Criteria) this;
		}

		public Criteria andApplyIdIsNotNull() {
			addCriterion("apply_id is not null");
			return (Criteria) this;
		}

		public Criteria andApplyIdEqualTo(Long value) {
			addCriterion("apply_id =", value, "applyId");
			return (Criteria) this;
		}

		public Criteria andApplyIdNotEqualTo(Long value) {
			addCriterion("apply_id <>", value, "applyId");
			return (Criteria) this;
		}

		public Criteria andApplyIdGreaterThan(Long value) {
			addCriterion("apply_id >", value, "applyId");
			return (Criteria) this;
		}

		public Criteria andApplyIdGreaterThanOrEqualTo(Long value) {
			addCriterion("apply_id >=", value, "applyId");
			return (Criteria) this;
		}

		public Criteria andApplyIdLessThan(Long value) {
			addCriterion("apply_id <", value, "applyId");
			return (Criteria) this;
		}

		public Criteria andApplyIdLessThanOrEqualTo(Long value) {
			addCriterion("apply_id <=", value, "applyId");
			return (Criteria) this;
		}

		public Criteria andApplyIdIn(List<Long> values) {
			addCriterion("apply_id in", values, "applyId");
			return (Criteria) this;
		}

		public Criteria andApplyIdNotIn(List<Long> values) {
			addCriterion("apply_id not in", values, "applyId");
			return (Criteria) this;
		}

		public Criteria andApplyIdBetween(Long value1, Long value2) {
			addCriterion("apply_id between", value1, value2, "applyId");
			return (Criteria) this;
		}

		public Criteria andApplyIdNotBetween(Long value1, Long value2) {
			addCriterion("apply_id not between", value1, value2, "applyId");
			return (Criteria) this;
		}

		public Criteria andInfusionTimeIsNull() {
			addCriterion("infusion_time is null");
			return (Criteria) this;
		}

		public Criteria andInfusionTimeIsNotNull() {
			addCriterion("infusion_time is not null");
			return (Criteria) this;
		}

		public Criteria andInfusionTimeEqualTo(Date value) {
			addCriterion("infusion_time =", value, "infusionTime");
			return (Criteria) this;
		}

		public Criteria andInfusionTimeNotEqualTo(Date value) {
			addCriterion("infusion_time <>", value, "infusionTime");
			return (Criteria) this;
		}

		public Criteria andInfusionTimeGreaterThan(Date value) {
			addCriterion("infusion_time >", value, "infusionTime");
			return (Criteria) this;
		}

		public Criteria andInfusionTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("infusion_time >=", value, "infusionTime");
			return (Criteria) this;
		}

		public Criteria andInfusionTimeLessThan(Date value) {
			addCriterion("infusion_time <", value, "infusionTime");
			return (Criteria) this;
		}

		public Criteria andInfusionTimeLessThanOrEqualTo(Date value) {
			addCriterion("infusion_time <=", value, "infusionTime");
			return (Criteria) this;
		}

		public Criteria andInfusionTimeIn(List<Date> values) {
			addCriterion("infusion_time in", values, "infusionTime");
			return (Criteria) this;
		}

		public Criteria andInfusionTimeNotIn(List<Date> values) {
			addCriterion("infusion_time not in", values, "infusionTime");
			return (Criteria) this;
		}

		public Criteria andInfusionTimeBetween(Date value1, Date value2) {
			addCriterion("infusion_time between", value1, value2,
					"infusionTime");
			return (Criteria) this;
		}

		public Criteria andInfusionTimeNotBetween(Date value1, Date value2) {
			addCriterion("infusion_time not between", value1, value2,
					"infusionTime");
			return (Criteria) this;
		}

		public Criteria andExecutorIdIsNull() {
			addCriterion("executor_id is null");
			return (Criteria) this;
		}

		public Criteria andExecutorIdIsNotNull() {
			addCriterion("executor_id is not null");
			return (Criteria) this;
		}

		public Criteria andExecutorIdEqualTo(Long value) {
			addCriterion("executor_id =", value, "executorId");
			return (Criteria) this;
		}

		public Criteria andExecutorIdNotEqualTo(Long value) {
			addCriterion("executor_id <>", value, "executorId");
			return (Criteria) this;
		}

		public Criteria andExecutorIdGreaterThan(Long value) {
			addCriterion("executor_id >", value, "executorId");
			return (Criteria) this;
		}

		public Criteria andExecutorIdGreaterThanOrEqualTo(Long value) {
			addCriterion("executor_id >=", value, "executorId");
			return (Criteria) this;
		}

		public Criteria andExecutorIdLessThan(Long value) {
			addCriterion("executor_id <", value, "executorId");
			return (Criteria) this;
		}

		public Criteria andExecutorIdLessThanOrEqualTo(Long value) {
			addCriterion("executor_id <=", value, "executorId");
			return (Criteria) this;
		}

		public Criteria andExecutorIdIn(List<Long> values) {
			addCriterion("executor_id in", values, "executorId");
			return (Criteria) this;
		}

		public Criteria andExecutorIdNotIn(List<Long> values) {
			addCriterion("executor_id not in", values, "executorId");
			return (Criteria) this;
		}

		public Criteria andExecutorIdBetween(Long value1, Long value2) {
			addCriterion("executor_id between", value1, value2, "executorId");
			return (Criteria) this;
		}

		public Criteria andExecutorIdNotBetween(Long value1, Long value2) {
			addCriterion("executor_id not between", value1, value2,
					"executorId");
			return (Criteria) this;
		}

		public Criteria andExecutorNameIsNull() {
			addCriterion("executor_name is null");
			return (Criteria) this;
		}

		public Criteria andExecutorNameIsNotNull() {
			addCriterion("executor_name is not null");
			return (Criteria) this;
		}

		public Criteria andExecutorNameEqualTo(String value) {
			addCriterion("executor_name =", value, "executorName");
			return (Criteria) this;
		}

		public Criteria andExecutorNameNotEqualTo(String value) {
			addCriterion("executor_name <>", value, "executorName");
			return (Criteria) this;
		}

		public Criteria andExecutorNameGreaterThan(String value) {
			addCriterion("executor_name >", value, "executorName");
			return (Criteria) this;
		}

		public Criteria andExecutorNameGreaterThanOrEqualTo(String value) {
			addCriterion("executor_name >=", value, "executorName");
			return (Criteria) this;
		}

		public Criteria andExecutorNameLessThan(String value) {
			addCriterion("executor_name <", value, "executorName");
			return (Criteria) this;
		}

		public Criteria andExecutorNameLessThanOrEqualTo(String value) {
			addCriterion("executor_name <=", value, "executorName");
			return (Criteria) this;
		}

		public Criteria andExecutorNameLike(String value) {
			addCriterion("executor_name like", value, "executorName");
			return (Criteria) this;
		}

		public Criteria andExecutorNameNotLike(String value) {
			addCriterion("executor_name not like", value, "executorName");
			return (Criteria) this;
		}

		public Criteria andExecutorNameIn(List<String> values) {
			addCriterion("executor_name in", values, "executorName");
			return (Criteria) this;
		}

		public Criteria andExecutorNameNotIn(List<String> values) {
			addCriterion("executor_name not in", values, "executorName");
			return (Criteria) this;
		}

		public Criteria andExecutorNameBetween(String value1, String value2) {
			addCriterion("executor_name between", value1, value2,
					"executorName");
			return (Criteria) this;
		}

		public Criteria andExecutorNameNotBetween(String value1, String value2) {
			addCriterion("executor_name not between", value1, value2,
					"executorName");
			return (Criteria) this;
		}

		public Criteria andExecuteTimeIsNull() {
			addCriterion("execute_time is null");
			return (Criteria) this;
		}

		public Criteria andExecuteTimeIsNotNull() {
			addCriterion("execute_time is not null");
			return (Criteria) this;
		}

		public Criteria andExecuteTimeEqualTo(Date value) {
			addCriterion("execute_time =", value, "executeTime");
			return (Criteria) this;
		}

		public Criteria andExecuteTimeNotEqualTo(Date value) {
			addCriterion("execute_time <>", value, "executeTime");
			return (Criteria) this;
		}

		public Criteria andExecuteTimeGreaterThan(Date value) {
			addCriterion("execute_time >", value, "executeTime");
			return (Criteria) this;
		}

		public Criteria andExecuteTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("execute_time >=", value, "executeTime");
			return (Criteria) this;
		}

		public Criteria andExecuteTimeLessThan(Date value) {
			addCriterion("execute_time <", value, "executeTime");
			return (Criteria) this;
		}

		public Criteria andExecuteTimeLessThanOrEqualTo(Date value) {
			addCriterion("execute_time <=", value, "executeTime");
			return (Criteria) this;
		}

		public Criteria andExecuteTimeIn(List<Date> values) {
			addCriterion("execute_time in", values, "executeTime");
			return (Criteria) this;
		}

		public Criteria andExecuteTimeNotIn(List<Date> values) {
			addCriterion("execute_time not in", values, "executeTime");
			return (Criteria) this;
		}

		public Criteria andExecuteTimeBetween(Date value1, Date value2) {
			addCriterion("execute_time between", value1, value2, "executeTime");
			return (Criteria) this;
		}

		public Criteria andExecuteTimeNotBetween(Date value1, Date value2) {
			addCriterion("execute_time not between", value1, value2,
					"executeTime");
			return (Criteria) this;
		}

		public Criteria andFinishFlagIsNull() {
			addCriterion("finish_flag is null");
			return (Criteria) this;
		}

		public Criteria andFinishFlagIsNotNull() {
			addCriterion("finish_flag is not null");
			return (Criteria) this;
		}

		public Criteria andFinishFlagEqualTo(Integer value) {
			addCriterion("finish_flag =", value, "finishFlag");
			return (Criteria) this;
		}

		public Criteria andFinishFlagNotEqualTo(Integer value) {
			addCriterion("finish_flag <>", value, "finishFlag");
			return (Criteria) this;
		}

		public Criteria andFinishFlagGreaterThan(Integer value) {
			addCriterion("finish_flag >", value, "finishFlag");
			return (Criteria) this;
		}

		public Criteria andFinishFlagGreaterThanOrEqualTo(Integer value) {
			addCriterion("finish_flag >=", value, "finishFlag");
			return (Criteria) this;
		}

		public Criteria andFinishFlagLessThan(Integer value) {
			addCriterion("finish_flag <", value, "finishFlag");
			return (Criteria) this;
		}

		public Criteria andFinishFlagLessThanOrEqualTo(Integer value) {
			addCriterion("finish_flag <=", value, "finishFlag");
			return (Criteria) this;
		}

		public Criteria andFinishFlagIn(List<Integer> values) {
			addCriterion("finish_flag in", values, "finishFlag");
			return (Criteria) this;
		}

		public Criteria andFinishFlagNotIn(List<Integer> values) {
			addCriterion("finish_flag not in", values, "finishFlag");
			return (Criteria) this;
		}

		public Criteria andFinishFlagBetween(Integer value1, Integer value2) {
			addCriterion("finish_flag between", value1, value2, "finishFlag");
			return (Criteria) this;
		}

		public Criteria andFinishFlagNotBetween(Integer value1, Integer value2) {
			addCriterion("finish_flag not between", value1, value2,
					"finishFlag");
			return (Criteria) this;
		}

		public Criteria andEvaluationIsNull() {
			addCriterion("evaluation is null");
			return (Criteria) this;
		}

		public Criteria andEvaluationIsNotNull() {
			addCriterion("evaluation is not null");
			return (Criteria) this;
		}

		public Criteria andEvaluationEqualTo(Integer value) {
			addCriterion("evaluation =", value, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationNotEqualTo(Integer value) {
			addCriterion("evaluation <>", value, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationGreaterThan(Integer value) {
			addCriterion("evaluation >", value, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationGreaterThanOrEqualTo(Integer value) {
			addCriterion("evaluation >=", value, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationLessThan(Integer value) {
			addCriterion("evaluation <", value, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationLessThanOrEqualTo(Integer value) {
			addCriterion("evaluation <=", value, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationIn(List<Integer> values) {
			addCriterion("evaluation in", values, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationNotIn(List<Integer> values) {
			addCriterion("evaluation not in", values, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationBetween(Integer value1, Integer value2) {
			addCriterion("evaluation between", value1, value2, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationNotBetween(Integer value1, Integer value2) {
			addCriterion("evaluation not between", value1, value2, "evaluation");
			return (Criteria) this;
		}

		public Criteria andPayFlagIsNull() {
			addCriterion("pay_flag is null");
			return (Criteria) this;
		}

		public Criteria andPayFlagIsNotNull() {
			addCriterion("pay_flag is not null");
			return (Criteria) this;
		}

		public Criteria andPayFlagEqualTo(Integer value) {
			addCriterion("pay_flag =", value, "payFlag");
			return (Criteria) this;
		}

		public Criteria andPayFlagNotEqualTo(Integer value) {
			addCriterion("pay_flag <>", value, "payFlag");
			return (Criteria) this;
		}

		public Criteria andPayFlagGreaterThan(Integer value) {
			addCriterion("pay_flag >", value, "payFlag");
			return (Criteria) this;
		}

		public Criteria andPayFlagGreaterThanOrEqualTo(Integer value) {
			addCriterion("pay_flag >=", value, "payFlag");
			return (Criteria) this;
		}

		public Criteria andPayFlagLessThan(Integer value) {
			addCriterion("pay_flag <", value, "payFlag");
			return (Criteria) this;
		}

		public Criteria andPayFlagLessThanOrEqualTo(Integer value) {
			addCriterion("pay_flag <=", value, "payFlag");
			return (Criteria) this;
		}

		public Criteria andPayFlagIn(List<Integer> values) {
			addCriterion("pay_flag in", values, "payFlag");
			return (Criteria) this;
		}

		public Criteria andPayFlagNotIn(List<Integer> values) {
			addCriterion("pay_flag not in", values, "payFlag");
			return (Criteria) this;
		}

		public Criteria andPayFlagBetween(Integer value1, Integer value2) {
			addCriterion("pay_flag between", value1, value2, "payFlag");
			return (Criteria) this;
		}

		public Criteria andPayFlagNotBetween(Integer value1, Integer value2) {
			addCriterion("pay_flag not between", value1, value2, "payFlag");
			return (Criteria) this;
		}

		public Criteria andPayerIdIsNull() {
			addCriterion("payer_id is null");
			return (Criteria) this;
		}

		public Criteria andPayerIdIsNotNull() {
			addCriterion("payer_id is not null");
			return (Criteria) this;
		}

		public Criteria andPayerIdEqualTo(Long value) {
			addCriterion("payer_id =", value, "payerId");
			return (Criteria) this;
		}

		public Criteria andPayerIdNotEqualTo(Long value) {
			addCriterion("payer_id <>", value, "payerId");
			return (Criteria) this;
		}

		public Criteria andPayerIdGreaterThan(Long value) {
			addCriterion("payer_id >", value, "payerId");
			return (Criteria) this;
		}

		public Criteria andPayerIdGreaterThanOrEqualTo(Long value) {
			addCriterion("payer_id >=", value, "payerId");
			return (Criteria) this;
		}

		public Criteria andPayerIdLessThan(Long value) {
			addCriterion("payer_id <", value, "payerId");
			return (Criteria) this;
		}

		public Criteria andPayerIdLessThanOrEqualTo(Long value) {
			addCriterion("payer_id <=", value, "payerId");
			return (Criteria) this;
		}

		public Criteria andPayerIdIn(List<Long> values) {
			addCriterion("payer_id in", values, "payerId");
			return (Criteria) this;
		}

		public Criteria andPayerIdNotIn(List<Long> values) {
			addCriterion("payer_id not in", values, "payerId");
			return (Criteria) this;
		}

		public Criteria andPayerIdBetween(Long value1, Long value2) {
			addCriterion("payer_id between", value1, value2, "payerId");
			return (Criteria) this;
		}

		public Criteria andPayerIdNotBetween(Long value1, Long value2) {
			addCriterion("payer_id not between", value1, value2, "payerId");
			return (Criteria) this;
		}

		public Criteria andPayerNameIsNull() {
			addCriterion("payer_name is null");
			return (Criteria) this;
		}

		public Criteria andPayerNameIsNotNull() {
			addCriterion("payer_name is not null");
			return (Criteria) this;
		}

		public Criteria andPayerNameEqualTo(String value) {
			addCriterion("payer_name =", value, "payerName");
			return (Criteria) this;
		}

		public Criteria andPayerNameNotEqualTo(String value) {
			addCriterion("payer_name <>", value, "payerName");
			return (Criteria) this;
		}

		public Criteria andPayerNameGreaterThan(String value) {
			addCriterion("payer_name >", value, "payerName");
			return (Criteria) this;
		}

		public Criteria andPayerNameGreaterThanOrEqualTo(String value) {
			addCriterion("payer_name >=", value, "payerName");
			return (Criteria) this;
		}

		public Criteria andPayerNameLessThan(String value) {
			addCriterion("payer_name <", value, "payerName");
			return (Criteria) this;
		}

		public Criteria andPayerNameLessThanOrEqualTo(String value) {
			addCriterion("payer_name <=", value, "payerName");
			return (Criteria) this;
		}

		public Criteria andPayerNameLike(String value) {
			addCriterion("payer_name like", value, "payerName");
			return (Criteria) this;
		}

		public Criteria andPayerNameNotLike(String value) {
			addCriterion("payer_name not like", value, "payerName");
			return (Criteria) this;
		}

		public Criteria andPayerNameIn(List<String> values) {
			addCriterion("payer_name in", values, "payerName");
			return (Criteria) this;
		}

		public Criteria andPayerNameNotIn(List<String> values) {
			addCriterion("payer_name not in", values, "payerName");
			return (Criteria) this;
		}

		public Criteria andPayerNameBetween(String value1, String value2) {
			addCriterion("payer_name between", value1, value2, "payerName");
			return (Criteria) this;
		}

		public Criteria andPayerNameNotBetween(String value1, String value2) {
			addCriterion("payer_name not between", value1, value2, "payerName");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table pension_infusion_record
	 * @mbggenerated  Thu Dec 26 10:16:19 CST 2013
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

		protected Criterion(String condition, Object value, Object secondValue,
				String typeHandler) {
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

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table pension_infusion_record
     *
     * @mbggenerated do_not_delete_during_merge Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}