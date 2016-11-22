package dcc.evaluation.view.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TotalDefectAmount {
	private final StringProperty mounth;
	private final StringProperty defectAmount;
	private final StringProperty totalDefectAmount;

	/**
	 * 无参构造
	 */
	public TotalDefectAmount() {
		this(null, null,null);
	}
	

	/**
	 * 带参构造
	 * 
	 * @param mounth
	 * @param defectAmount
	 * @param totalDefectAmount
	 */


	// 三个参数
	public TotalDefectAmount(String mounth, String defectAmount, String totalDefectAmount) {
		this.mounth = new SimpleStringProperty(mounth);
		this.defectAmount = new SimpleStringProperty(defectAmount);
		this.totalDefectAmount = new SimpleStringProperty(totalDefectAmount);
		
	}

	public String getMounth() {
		return mounth.get();
	}

	public void setMounth(String mounth) {
		this.mounth.set(mounth);
	}

	public StringProperty mounthProperty() {
		return mounth;
	}

	public String getDefectAmount() {
		return defectAmount.get();
	}

	public void setDefectAmount(String defectAmount) {
		this.defectAmount.set(defectAmount);
	}

	public StringProperty defectAmountProperty() {
		return defectAmount;
	}
	
	public String getTotalDefectAmount() {
		return totalDefectAmount.get();
	}

	public void setTotalDefectAmount(String defectAmount) {
		this.totalDefectAmount.set(defectAmount);
	}

	public StringProperty totalDefectAmountProperty() {
		return totalDefectAmount;
	}

}
