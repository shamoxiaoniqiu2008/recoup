package service.reportManage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.caterManage.PensionFoodmenuMapper;
import persistence.configureManage.PensionBuildingMapper;
import persistence.configureManage.PensionItempurseMapper;
import persistence.dictionary.PensionDicPaywayMapper;
import persistence.dictionary.PensionDictRefundtypeMapper;
import domain.caterManage.PensionFoodmenu;
import domain.caterManage.PensionFoodmenuExample;
import domain.configureManage.PensionBuilding;
import domain.configureManage.PensionBuildingExample;
import domain.configureManage.PensionItempurse;
import domain.configureManage.PensionItempurseExample;
import domain.dictionary.PensionDicPayway;
import domain.dictionary.PensionDicPaywayExample;
import domain.dictionary.PensionDictRefundtype;
import domain.dictionary.PensionDictRefundtypeExample;
import domain.reportManage.CapitalFlowReport;

/**
 * 日常缴费
 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class CapitalFlowService {

	@Autowired
	private PensionBuildingMapper pensionBuildingMapper;
	@Autowired
	private PensionDictRefundtypeMapper pensionDictRefundtypeMapper;
	@Autowired
	private PensionItempurseMapper pensionItempurseMapper;
	@Autowired
	private PensionDicPaywayMapper pensionDicPaywayMapper;
	@Autowired
	private PensionFoodmenuMapper pensionFoodmenuMapper;

	private final static Integer YES_FLAG = 1;
	private final static Integer NO_FLAG = 2;

	public Map<String, List<CapitalFlowReport>> search(Date startDate,
			Date endDate) {
		Map<String, List<CapitalFlowReport>> capitalFlowMap = new HashMap<String, List<CapitalFlowReport>>();
		List<PensionDictRefundtype> refundTypes = this.selectRefundTypes();
		List<PensionItempurse> itemPurses = this.selectItemPurses();
		List<PensionFoodmenu> foodMenus = this.selectFoodMenu();
		List<PensionDicPayway> payways = this.selectPayWays();
		List<CapitalFlowReport> itemPurseReports = new ArrayList<CapitalFlowReport>();
		List<CapitalFlowReport> foodMenuReports = new ArrayList<CapitalFlowReport>();
		List<CapitalFlowReport> refundTypeReports = new ArrayList<CapitalFlowReport>();
		List<CapitalFlowReport> paywayReports = new ArrayList<CapitalFlowReport>();
		for (PensionItempurse itemPurse : itemPurses) {
			CapitalFlowReport itemPurseCapitalFlow = new CapitalFlowReport();
			itemPurseCapitalFlow.setItemName(itemPurse.getItemname());
			List<PensionBuilding> buildingTotalFees = pensionBuildingMapper
					.selectCapitalFlowByDetail(startDate, endDate,
							itemPurse.getId(),// 缴费项目编号
							null,// 缴费（而非退费）
							YES_FLAG,// 缴费（而非欠费）
							YES_FLAG);// 临时缴费来源 - 价表中的临时缴费项目
			itemPurseCapitalFlow.setBuildingTotals(buildingTotalFees);
			Float totalFees = new Float(0);
			for (PensionBuilding buildingTotalFee : buildingTotalFees) {
				totalFees += buildingTotalFee.getTotalfee();
			}
			itemPurseCapitalFlow.setTotalFees(totalFees);
			if (!itemPurseCapitalFlow.getTotalFees().equals(0.00f)) {
				itemPurseReports.add(itemPurseCapitalFlow);
			}
		}
		for (PensionFoodmenu foodMenu : foodMenus) {
			CapitalFlowReport itemPurseCapitalFlow = new CapitalFlowReport();
			itemPurseCapitalFlow.setItemName(foodMenu.getName());
			List<PensionBuilding> buildingTotalFees = pensionBuildingMapper
					.selectCapitalFlowByFoodMenu(startDate, endDate,
							foodMenu.getId(),// 缴费项目编号
							null,// 缴费（而非退费）
							YES_FLAG,// 缴费（而非欠费）
							NO_FLAG);// 临时缴费来源 - 菜品表
			itemPurseCapitalFlow.setBuildingTotals(buildingTotalFees);
			Float totalFees = new Float(0);
			for (PensionBuilding buildingTotalFee : buildingTotalFees) {
				totalFees += buildingTotalFee.getTotalfee();
			}
			itemPurseCapitalFlow.setTotalFees(totalFees);
			if (!itemPurseCapitalFlow.getTotalFees().equals(0.00f)) {
				foodMenuReports.add(itemPurseCapitalFlow);
			}
		}
		for (PensionDictRefundtype refundType : refundTypes) {
			CapitalFlowReport refundTypeCapitalFlow = new CapitalFlowReport();
			refundTypeCapitalFlow.setItemName(refundType.getRefundtypeName());
			List<PensionBuilding> normalTotalFees = pensionBuildingMapper
					.selectNormalCapitalFlowForRefund(startDate, endDate,
							refundType.getId());
			List<PensionBuilding> tempTotalFees = pensionBuildingMapper
					.selectTempCapitalFlowForRefund(startDate, endDate,
							refundType.getId());
			Float totalFees = new Float(0);
			for (int i = 0; i < normalTotalFees.size(); i++) {
				PensionBuilding normalTotalFee = normalTotalFees.get(i);
				PensionBuilding tempTotalFee = tempTotalFees.get(i);
				normalTotalFee.setTotalfee(normalTotalFee.getTotalfee()
						+ tempTotalFee.getTotalfee());
				totalFees += normalTotalFee.getTotalfee();
			}
			refundTypeCapitalFlow.setBuildingTotals(normalTotalFees);
			refundTypeCapitalFlow.setTotalFees(totalFees);
			refundTypeReports.add(refundTypeCapitalFlow);
		}
		for (PensionDicPayway payway : payways) {
			CapitalFlowReport paywayCapitalFlow = new CapitalFlowReport();
			paywayCapitalFlow.setItemName(payway.getPaywayName());
			List<PensionBuilding> normalTotalFees = pensionBuildingMapper
					.selectNormalCapitalFlowByPayway(startDate, endDate,
							payway.getId());// 支付方式编号
			List<PensionBuilding> TempTotalFees = pensionBuildingMapper
					.selectTempCapitalFlowByPayway(startDate, endDate,
							payway.getId());// 支付方式编号
			/*
			 * List<PensionBuilding> depositTotalFees=pensionBuildingMapper.
			 * selectDepositCapitalFlowByPayway(startDate,endDate,
			 * payway.getId());//支付方式编号
			 */Float totalFees = new Float(0);
			for (int i = 0; i < normalTotalFees.size(); i++) {
				PensionBuilding normalTotalFee = normalTotalFees.get(i);
				PensionBuilding TempTotalFee = TempTotalFees.get(i);
				// PensionBuilding depositTotalFee=depositTotalFees.get(i);
				normalTotalFee.setTotalfee(normalTotalFee.getTotalfee()
						+ TempTotalFee.getTotalfee());
				totalFees += normalTotalFee.getTotalfee();
			}
			paywayCapitalFlow.setBuildingTotals(normalTotalFees);
			paywayCapitalFlow.setTotalFees(totalFees);
			if (!paywayCapitalFlow.getTotalFees().equals(0.00f)) {
				paywayReports.add(paywayCapitalFlow);
			}
		}
		capitalFlowMap.put("ITEMPURSE", itemPurseReports);
		capitalFlowMap.put("FOODMENU", foodMenuReports);
		capitalFlowMap.put("REFUND", refundTypeReports);
		capitalFlowMap.put("PAYWAY", paywayReports);
		return capitalFlowMap;
	}

	private List<PensionFoodmenu> selectFoodMenu() {
		PensionFoodmenuExample example = new PensionFoodmenuExample();
		example.or().andClearedEqualTo(NO_FLAG);
		return pensionFoodmenuMapper.selectByExample(example);
	}

	public List<PensionDicPayway> selectPayWays() {
		PensionDicPaywayExample example = new PensionDicPaywayExample();
		example.or().andClearedEqualTo(NO_FLAG);
		return pensionDicPaywayMapper.selectByExample(example);
	}

	public List<PensionItempurse> selectItemPurses() {
		PensionItempurseExample example = new PensionItempurseExample();
		example.or().andClearedEqualTo(NO_FLAG);
		return pensionItempurseMapper.selectByExample(example);
	}

	public List<PensionDictRefundtype> selectRefundTypes() {
		PensionDictRefundtypeExample example = new PensionDictRefundtypeExample();
		example.or().andClearedEqualTo(NO_FLAG);
		return pensionDictRefundtypeMapper.selectByExample(example);
	}

	public List<PensionBuilding> selectBulidings() {
		PensionBuildingExample example = new PensionBuildingExample();
		example.or().andClearedEqualTo(NO_FLAG);
		return pensionBuildingMapper.selectByExample(example);
	}

	/**
	 * 获取最大值，并且可被4整除
	 * 
	 * @param series
	 * @return
	 */
	public Float getMax(List<ChartSeries> series) {
		Float max = new Float(0);
		for (ChartSeries serie : series) {
			Map<Object, Number> tempMap = serie.getData();
			for (Entry<Object, Number> entry : tempMap.entrySet()) {
				if (max.floatValue() < entry.getValue().floatValue()) {
					max = entry.getValue().floatValue();
				}
			}
		}
		max = (float) (max + Math.pow(10,
				new Float(max.toString().length() - 1)));
		for (int i = 0; i < 4; i++) {
			Float a = (float) (max / 4);
			Double b = Math.ceil(max / 4);
			if (a.toString().equals(b.toString())) {
				break;
			} else {
				max += 1;
			}
		}
		return max;
	}

	/**
	 * 将报表类的名称后加上所占比例
	 * 
	 * @param itemPurseReports
	 * @return
	 */
	public List<CapitalFlowReport> packItemPurseReports(
			List<CapitalFlowReport> itemPurseReports) {
		List<CapitalFlowReport> temp = new ArrayList<CapitalFlowReport>();
		Float totalFee = new Float(0);// 缴费项目总金额
		// 设置百分数格式
		NumberFormat percentFormat = new DecimalFormat("0%");
		// 求得缴费项目总金额
		for (CapitalFlowReport itemPurseReport : itemPurseReports) {
			totalFee += itemPurseReport.getTotalFees();
		}
		// 重置每条缴费项目的显示名--加了该项目所占百分数
		for (int i = 0; i < itemPurseReports.size(); i++) {
			CapitalFlowReport itemPurseReport = itemPurseReports.get(i);
			CapitalFlowReport tempReport = new CapitalFlowReport();
			tempReport.setItemName(itemPurseReport.getItemName()
					+ " "
					+ percentFormat.format(itemPurseReport.getTotalFees()
							/ totalFee));
			tempReport.setBuildingTotals(itemPurseReport.getBuildingTotals());
			tempReport.setTotalFees(itemPurseReport.getTotalFees());
			temp.add(tempReport);
		}
		return temp;
	}

}
