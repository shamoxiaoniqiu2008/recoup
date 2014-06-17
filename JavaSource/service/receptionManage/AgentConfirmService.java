package service.receptionManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.olderManage.PensionOlderMapper;
import persistence.receptionManage.PensionAgentApplyMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;
import domain.employeeManage.PensionEmployee;
import domain.receptionManage.PensionAgentApply;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-1 下午1:26:44
 */
@Service
public class AgentConfirmService {
	@Autowired
	private PensionAgentApplyMapper pensionAgentApplyMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;
	@Autowired
	private SystemConfig systemConfig;

	/**
	 * 查询当前用户的代办服务列表
	 * 
	 * @return
	 */
	public List<PensionAgentApply> selectAgentList(Long olderId,
			Date startDate, Date endDate, Long userId, Long applyId) {
		List<PensionAgentApply> agentList = new ArrayList<PensionAgentApply>();
		agentList = pensionAgentApplyMapper.selectAgentConfirmList(olderId,
				startDate, endDate, userId, applyId);
		return agentList;
	}

	/**
	 * 更新一条代办申请
	 * 
	 * @param domain
	 */
	@Transactional
	public void updateAgentApply(PensionAgentApply apply,
			PensionEmployee curUser) {
		try {
			pensionAgentApplyMapper.updateByPrimaryKeySelective(apply);
			// 消息类别
			String typeIdStr = JavaConfig
					.fetchProperty("AgentApplyService.empMessagetypeId");
			Long messagetypeId = Long.valueOf(typeIdStr);
			messageMessage.updateMessageProcessor(curUser.getId(),
					messagetypeId, "pension_agent_apply", apply.getId(),
					curUser.getDeptId());
		} catch (Exception ex) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"数据更新异常", ""));
		}
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

	// 读取系统参数
	public Long selectItemPurseTypeId() throws PmsException {
		try {
			String rolesStr = systemConfig
					.selectProperty("AGENT_ITEM_PURSE_TYPE_ID");
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
