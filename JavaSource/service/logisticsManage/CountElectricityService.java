package service.logisticsManage;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.configureManage.PensionItempurseMapper;
import persistence.financeManage.PensionTemppaymentMapper;
import persistence.financeManage.PensionTemppaymentdetailMapper;
import persistence.logisticsManage.PensionOlderElectricityMapper;
import util.PmsException;
import util.SystemConfig;
import domain.configureManage.PensionItempurse;
import domain.financeManage.PensionTemppayment;
import domain.financeManage.PensionTemppaymentdetail;
import domain.logisticsManage.PensionOlderElectricity;

@Service
public class CountElectricityService {
	@Autowired
	private transient SystemConfig systemConfig;
	@Autowired
	private PensionItempurseMapper pensionItempurseMapper;
	@Autowired
	private PensionOlderElectricityMapper pensionOlderElectricityMapper;
	@Autowired
	private PensionTemppaymentMapper pensionTemppaymentMapper;
	@Autowired
	private PensionTemppaymentdetailMapper pensionTemppaymentdetailMapper;

	private static final Integer YES_FLAG = 1;
	private static final Integer NO_FLAG = 2;

	public List<PensionOlderElectricity> search(Long olderId, Date startDate,
			Date endDate) {
		return pensionOlderElectricityMapper.searchOlderElectricity(olderId,
				startDate, endDate);

	}

	public List<PensionOlderElectricity> searchFreeOlder(Date startDate,
			Date endDate) {
		return pensionOlderElectricityMapper
				.searchFreeOlder(startDate, endDate);

	}

	/**
	 * 向数据库中插入免费用电额度记录
	 * 
	 * @param freeElectricity
	 */
	public void insertOlderElectricity(PensionOlderElectricity freeElectricity) {
		pensionOlderElectricityMapper.insertSelective(freeElectricity);
	}

	/**
	 * 收电费
	 * 
	 * @param olderElectricitys
	 *            要设置为“已缴费”的老人电费记录
	 * @param itemPurseId
	 *            电费价表主键
	 * @param itemPurseName
	 *            电费价表名称
	 * @param itemPurse
	 *            电费价格
	 * @param amount
	 *            总度数
	 * @param empId
	 *            操作人编号
	 * @param empName
	 *            操作人姓名
	 */
	public void payElectricity(List<PensionOlderElectricity> olderElectricitys,
			Long itemPurseId, String itemPurseName, Float amount, Long empId,
			String empName) {
		// 将老人电费记录设置为 已缴费
		this.updateOlderElectricitys(olderElectricitys);
		// 向临时收费表中插入相应记录
		this.insertNormalpayment(olderElectricitys.get(0).getOlderId(),
				itemPurseId, itemPurseName, olderElectricitys, amount, empId,
				empName);

	}

	/**
	 * 向临时收费主记录表和明细表中插入电费缴费记录
	 * 
	 * @param olderId
	 *            老人编号哦啊
	 * @param itemPurseId
	 *            电费价表主键
	 * @param itemPurseName
	 *            电费价表名称
	 * @param itemPurse
	 *            电费价格
	 * @param amount
	 *            总度数
	 * @param empId
	 *            操作人编号
	 * @param empName
	 *            操作人姓名
	 */
	private void insertNormalpayment(Long olderId, Long itemPurseId,
			String itemPurseName,
			List<PensionOlderElectricity> olderElectricitys, Float amount,
			Long empId, String empName) {
		Date curDate = new Date();
		PensionTemppayment tempPayment = new PensionTemppayment();
		tempPayment.setBeginDate(curDate);
		tempPayment.setCleared(NO_FLAG);
		tempPayment.setEndtime(curDate);
		tempPayment.setGeneratetime(curDate);
		tempPayment.setGeneratorId(empId);
		tempPayment.setGeneratorName(empName);
		tempPayment.setIsclosed(NO_FLAG);
		tempPayment.setIspay(NO_FLAG);
		tempPayment.setOlderId(olderId);
		tempPayment.setStarttime(curDate);
		tempPayment.setTotalfees(amount);
		pensionTemppaymentMapper.insertSelective(tempPayment);
		for (PensionOlderElectricity olderElectricity : olderElectricitys) {
			PensionTemppaymentdetail temppaymentdetail = new PensionTemppaymentdetail();
			temppaymentdetail.setCleared(NO_FLAG);
			temppaymentdetail.setEndtime(curDate);
			temppaymentdetail.setGeneratetime(curDate);
			temppaymentdetail.setIspay(NO_FLAG);
			temppaymentdetail.setItemlfees(olderElectricity.getUnitPrice());
			temppaymentdetail.setItemName(itemPurseName);
			temppaymentdetail.setItemsId(itemPurseId);
			temppaymentdetail
					.setNumber(olderElectricity.getElectricityAmount());
			temppaymentdetail.setOlderId(olderId);
			temppaymentdetail.setPaymentId(tempPayment.getId());
			temppaymentdetail.setRecordId(empId);
			temppaymentdetail.setRecordName(empName);
			temppaymentdetail.setRecordtime(curDate);
			temppaymentdetail.setSource(YES_FLAG);// 价表中的临时缴费项目
			temppaymentdetail.setStarttime(curDate);
			temppaymentdetail.setTotalfees(olderElectricity.getUnitPrice()
					* olderElectricity.getElectricityAmount());
			pensionTemppaymentdetailMapper.insertSelective(temppaymentdetail);
		}
	}

	/**
	 * 更新老人用电记录，置为已收费
	 * @param olderElectricitys
	 */
	public void updateOlderElectricitys(
			List<PensionOlderElectricity> olderElectricitys) {
		for (PensionOlderElectricity olderElectricity : olderElectricitys) {
			PensionOlderElectricity tempElectricity = new PensionOlderElectricity();
			tempElectricity.setId(olderElectricity.getId());
			tempElectricity.setChargeFlag(YES_FLAG);
			pensionOlderElectricityMapper
					.updateByPrimaryKeySelective(tempElectricity);
		}
	}

	/**
	 * 从系统参数中获取电表价表
	 * @return
	 * @throws PmsException
	 */
	public PensionItempurse selectElectricityPurse() throws PmsException {
		try {
			String valueStr = systemConfig.selectProperty("ELECTRICITY_PURSE");
			Long id = Long.valueOf(valueStr);
			return pensionItempurseMapper.selectByPrimaryKey(id);
		} catch (NumberFormatException e) {
			throw new PmsException("请正确设置电费价表主键系统参数！");
		} catch (PmsException e) {
			throw new PmsException("请正确设置电费价表主键系统参数！");
		}
	}

}
