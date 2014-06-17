package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.medicalManage.PensionInfusionApplyMapper;
import persistence.medicalManage.PensionInfusionRecordMapper;
import util.PmsException;
import util.SystemConfig;
import domain.medicalManage.PensionInfusionApply;
import domain.medicalManage.PensionInfusionRecord;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-6 上午9:01:44
 */
@Service
public class InfusionConfirmService {
	@Autowired
	private PensionInfusionApplyMapper pensionInfusionApplyMapper;
	@Autowired
	private PensionInfusionRecordMapper pensionInfusionRecordMapper;
	@Autowired
	private SystemConfig systemConfig;

	/**
	 * 按条件查询输液计划
	 * 
	 * @param olderId
	 * @return
	 */
	public List<PensionInfusionApply> selectApplyList(Long olderId) {
		List<PensionInfusionApply> applyList = new ArrayList<PensionInfusionApply>();
		applyList = pensionInfusionApplyMapper.selectInfusionPlanList(olderId);
		return applyList;
	}

	/**
	 * 更新一条输液计划
	 * 
	 * @param apply
	 */
	@Transactional
	public void updateInfusionApply(PensionInfusionApply apply) {
		try {
			pensionInfusionApplyMapper.updateByPrimaryKeySelective(apply);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 查询一条输液计划生成的输液记录列表
	 * 
	 * @param applyId
	 * @return
	 */
	public List<InfusionRecordDomain> selectInfusionRecords(Date startDate,
			Date endDate) {
		List<InfusionRecordDomain> infusionDomainList = new ArrayList<InfusionRecordDomain>();
		infusionDomainList = pensionInfusionRecordMapper.selectInfusionRecords(
				startDate, endDate);
		return infusionDomainList;
	}

	/**
	 * 更新一条输液记录
	 * 
	 * @param record
	 */
	@Transactional
	public void updateInfusionRecord(PensionInfusionRecord record) {
		try {
			pensionInfusionRecordMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	// 读取系统参数
	public Long selectItemPurseTypeId() throws PmsException {
		try {
			String rolesStr = systemConfig
					.selectProperty("INFUSION_ITEM_PURSE_TYPE_ID");
			if (rolesStr != null && !rolesStr.isEmpty()) {
				return new Long(rolesStr);
			}
		} catch (NumberFormatException e) {
			throw new PmsException("系统参数设置代办服务价表类型有误！");
		} catch (PmsException e) {
			throw new PmsException("系统参数还没有设置代办服务价表类型！");
		}
		return null;
	}
}
