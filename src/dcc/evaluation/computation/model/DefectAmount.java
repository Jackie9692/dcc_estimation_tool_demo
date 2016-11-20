package dcc.evaluation.computation.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 软件缺陷早期预测失效数据类
 * 
 * @author rsy
 *
 */

public class DefectAmount {

	private final StringProperty mounth;
	private final StringProperty defectAmount;

	/**
	 * 无参构造
	 */
	public DefectAmount(){
		this(null,null);
	}
	
	/**
	 * 带参构造
	 * @param mounth
	 * @param defectAmount
	 */
	public DefectAmount(String mounth, String defectAmount) {
		this.mounth = new SimpleStringProperty(mounth);
		this.defectAmount = new SimpleStringProperty(defectAmount);

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
	
}
