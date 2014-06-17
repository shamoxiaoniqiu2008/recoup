package domain.reportManage;

import java.io.Serializable;
import java.util.List;

import domain.configureManage.PensionBuilding;

public class CapitalFlowReport implements Serializable {
	
	private String itemName;
	
	private List<PensionBuilding> buildingTotals;
	
	private Float totalFees;

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public CapitalFlowReport(String string, int i) {
		this.setItemName(string);
		this.setTotalFees((float) i);
	}

	public CapitalFlowReport() {
		// TODO Auto-generated constructor stub
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public Float getTotalFees() {
		return totalFees;
	}

	public void setTotalFees(Float totalFees) {
		this.totalFees = totalFees;
	}

	public List<PensionBuilding> getBuildingTotals() {
		return buildingTotals;
	}

	public void setBuildingTotals(List<PensionBuilding> buildingTotals) {
		this.buildingTotals = buildingTotals;
	}
	
	
	
	
	
}