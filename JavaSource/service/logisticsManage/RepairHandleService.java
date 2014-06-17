package service.logisticsManage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.logisticsManage.PensionGoodsDetailMapper;
import persistence.logisticsManage.PensionRepairMapper;
import persistence.stockManage.PensionStorageMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

import com.centling.his.util.DateUtil;

import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionGoodsDetail;
import domain.logisticsManage.PensionGoodsDetailExample;
import domain.logisticsManage.PensionRepair;
import domain.stockManage.PensionStorage;
import domain.stockManage.PensionStorageExample;

@Service
public class RepairHandleService {
	@Autowired
	private PensionRepairMapper pensionRepairMapper;
	@Autowired
	private PensionGoodsDetailMapper pensionGoodsDetailMapper;
	@Autowired
	private PensionStorageMapper pensionStorageMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;

	public List<PensionRepairExtend> selectRepairApplicationRecords(
			Date startDate, Date endDate, Long olderId, Long repairerId,
			Integer handleFlag, Long repairId) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if (endDate != null) {
			map.put("endDate", DateUtil.getNextDay(endDate));
		} else {
			map.put("endDate", null);
		}
		map.put("olderId", olderId);
		map.put("repairId", repairId);
		map.put("handleFlag", handleFlag);
		// map.put("repairerId", repairerId);
		map.put("sendFlag", 1);
		return pensionRepairMapper.selectRepairApplicationRecords(map);
	}

	/**
	 * 处理维修申请记录
	 */
	public void handleRepairApplicationRecord(PensionRepairExtend handledRow,
			List<PensionGoodsDetail> details, PensionEmployee curEmployee) {

		Long repairId = handledRow.getId();
		handledRow.setHandleFlag(1);
		handledRow.setHandleTime(new Date());
		handledRow.setRepairerId(curEmployee.getId());
		pensionRepairMapper.updateByPrimaryKeySelective(handledRow);
		for (PensionGoodsDetail detail : details) {
			detail.setRepairId(repairId);
			pensionGoodsDetailMapper.insertSelective(detail);
		}
		updateApprove(handledRow.getId(),curEmployee.getId(),curEmployee.getDeptId());
	}

	/**
	 * 更新一条处理记录
	 */
	public void updateApprove(Long repairId,Long employeeId, Long deptId) {
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("repairApplyService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(employeeId, messagetypeId,
				"pension_repair", repairId, deptId);
	}
	
	
	
	/**
	 * 查询选定维修的材料明细
	 */
	public List<PensionGoodsDetail> selectGoodsDetail(
			PensionRepairExtend handledRow) {

		PensionGoodsDetailExample example = new PensionGoodsDetailExample();
		example.or().andRepairIdEqualTo(handledRow.getId());
		return pensionGoodsDetailMapper.selectByExample(example);

	}

	/**
	 * 获取当前项目的库存量
	 * 
	 * @param itemId
	 * @return
	 */
	public Float selectStorageQty(Long itemId) {

		PensionStorageExample example = new PensionStorageExample();
		example.or().andItemIdEqualTo(itemId);
		List<PensionStorage> storage = pensionStorageMapper
				.selectByExample(example);
		return storage.get(0).getStorageQty();
	}

	/**
	 * 【费用录入】，将收费标识 置为‘是’
	 */
	public void charge(Long id) {
		PensionRepair repair = new PensionRepair();
		repair.setId(id);
		repair.setPayFlag(1);
		pensionRepairMapper.updateByPrimaryKeySelective(repair);
	}

	/**
	 * 将被选中行置为已缴费
	 * @param selectedRow
	 */
	public void payMoveApplicationRecord(PensionRepairExtend selectedRow) {
		selectedRow.setPayFlag(1);
		pensionRepairMapper.updateByPrimaryKeySelective(selectedRow);
	}
	/**
	 * 获得价表类型
	 * @return
	 * @throws PmsException 
	 */
	public String getPriceTypeId() throws PmsException {
		
		return systemConfig.selectProperty("REPAIR_ITEM_PURSE_TYPE_ID");
		
		
	}
	
	public void setPensionRepairMapper(PensionRepairMapper pensionRepairMapper) {
		this.pensionRepairMapper = pensionRepairMapper;
	}

	public PensionRepairMapper getPensionRepairMapper() {
		return pensionRepairMapper;
	}

	public void setPensionGoodsDetailMapper(
			PensionGoodsDetailMapper pensionGoodsDetailMapper) {
		this.pensionGoodsDetailMapper = pensionGoodsDetailMapper;
	}

	public PensionGoodsDetailMapper getPensionGoodsDetailMapper() {
		return pensionGoodsDetailMapper;
	}

	public void setMessageMessage(MessageMessage messageMessage) {
		this.messageMessage = messageMessage;
	}

	public MessageMessage getMessageMessage() {
		return messageMessage;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}
}
