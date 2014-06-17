package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.dictionary.PensionDicAccidenttypeMapper;
import persistence.olderManage.PensionAccidentRecordMapper;
import persistence.olderManage.PensionOlderMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;
import domain.dictionary.PensionDicAccidenttype;
import domain.dictionary.PensionDicAccidenttypeExample;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-29 上午10:16:44
 */
@Service
public class PensionAccidentService {
	@Autowired
	private PensionAccidentRecordMapper pensionAccidentRecordMapper;
	@Autowired
	private PensionDicAccidenttypeMapper pensionDicAccidenttypeMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;

	/**
	 * 查询老人事故记录列表
	 * 
	 * @param olderId
	 * @param startDate
	 * @param endDate
	 * @param accidenttypeId
	 * @return
	 */
	public List<PensionAccidentDomain> selectAccidentList(Long olderId,
			Date startDate, Date endDate, Long accidenttypeId, Long accidentId) {
		List<PensionAccidentDomain> accidentList = new ArrayList<PensionAccidentDomain>();
		accidentList = pensionAccidentRecordMapper.selectAccidentRecord(
				olderId, startDate, endDate, accidenttypeId, accidentId);
		return accidentList;
	}

	/**
	 * 查询事故类型列表
	 * 
	 * @return
	 */
	public List<PensionDicAccidenttype> selectAccidentType() {
		PensionDicAccidenttypeExample example = new PensionDicAccidenttypeExample();
		example.or();
		return pensionDicAccidenttypeMapper.selectByExample(example);
	}

	/**
	 * 插入一条事故记录
	 * 
	 * @param accident
	 */
	public void insertAccident(PensionAccidentDomain accident) {
		try {
			pensionAccidentRecordMapper.insertSelective(accident);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param selectedRow
	 * @throws PmsException
	 */
	public void sentMessage(PensionAccidentDomain selectedRow)
			throws PmsException {
		String pensionOlderName = selectedRow.getOlderName();
		Long olderId = selectedRow.getOlderId();
		String messageContent = "老人" + pensionOlderName + "事故通知已提交！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("pensionAccidentService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?olderId=" + olderId + "&accidentId="
				+ selectedRow.getId();

		String accident_dpt_id = systemConfig.selectProperty("ACCIDENT_DPT_ID");
		String accident_emp_id = systemConfig.selectProperty("ACCIDENT_EMP_ID");

		List<Long> dptIdList = new ArrayList<Long>();
		List<Long> empIdList = new ArrayList<Long>();

		if (accident_dpt_id != null && !accident_dpt_id.isEmpty()) {
			String[] dpt_id_arr = accident_dpt_id.split(",");
			for (int i = 0; i < dpt_id_arr.length; i++) {
				dptIdList.add(Long.valueOf(dpt_id_arr[i]));
			}
		} else {
			dptIdList = null;
		}

		if (accident_emp_id != null && !accident_emp_id.isEmpty()) {
			String[] emp_id_arr = accident_emp_id.split(",");
			for (int i = 0; i < emp_id_arr.length; i++) {
				empIdList.add(Long.valueOf(emp_id_arr[i]));
			}
		} else {
			empIdList = null;
		}
		String messagename = "【" + pensionOlderName + "】事故上报通知";
		messageMessage.sendMessage(messagetypeId, dptIdList, empIdList,
				messagename, messageContent, url, "pension_accident_record",
				selectedRow.getId());
	}

	/**
	 * 更新一条事故记录
	 */
	public void updateAccident(PensionAccidentDomain accident) {
		try {
			pensionAccidentRecordMapper.updateByPrimaryKeySelective(accident);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 查询事故录入部门
	 * 
	 * @return
	 */
	public Long getAccidentDeptId() {
		Long deptId = 0L;
		try {
			deptId = Long.valueOf(systemConfig
					.selectProperty("ACCIDENT_DEPT_ID"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (PmsException e) {
			e.printStackTrace();
		}
		return deptId;
	}

	/**
	 * 查询老人姓名
	 * 
	 * @param olderId
	 * @return
	 */
	public String selectOlderName(Long olderId) {
		return pensionOlderMapper.selectByPrimaryKey(olderId).getName();
	}
}
