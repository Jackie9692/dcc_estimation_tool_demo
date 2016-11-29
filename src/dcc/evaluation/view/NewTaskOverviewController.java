package dcc.evaluation.view;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import dcc.evaluation.computation.model.JMModel;
import dcc.evaluation.computation.model.ModelTest;
import dcc.evaluation.view.model.DefectAmount;
import dcc.evaluation.view.model.FillTableActions;
import dcc.evaluation.view.model.TotalDefectAmount;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class NewTaskOverviewController extends FillTableActions {

	public NewTaskOverviewController() {
		super();
	}

//	// 需求分析阶段的按钮
//	@FXML
//	private RadioButton rbSystemType1;
//	@FXML
//	private RadioButton rbSystemType2;
//	@FXML
//	private RadioButton rbSystemType3;
//	@FXML
//	private RadioButton rbSystemType4;
//	@FXML
//	private RadioButton rbSystemType5;
//	@FXML
//	private RadioButton rbSystemType6;
//	@FXML
//	private RadioButton rbDevelopmentEnvironment1;
//	@FXML
//	private RadioButton rbDevelopmentEnvironment2;
//	@FXML
//	private RadioButton rbDevelopmentEnvironment3;
//
//	/**
//	 * 开发过程的软件可靠性预测与分析-开发过程可靠性分析-需求分析阶段
//	 */
//	// 定义平均错误密度A
//	double a;
//	// 定义开发环境决定的修正因子D
//	double d;
//	// 定义需求阶段的错误密度
//	double requirementAnalysisResult;
//	// 计算需求阶段的错误密度
//
//	public double requirementAnalysis() {
//
//		// 确定平均错误密度A(错误/行)
//		if (rbSystemType1.isSelected()) {
//			a = 0.0128;
//		} else if (rbSystemType2.isSelected()) {
//			a = 0.0092;
//		} else if (rbSystemType3.isSelected()) {
//			a = 0.0078;
//		} else if (rbSystemType4.isSelected()) {
//			a = 0.0018;
//		} else if (rbSystemType5.isSelected()) {
//			a = 0.0085;
//		} else if (rbSystemType6.isSelected()) {
//			a = 0.0123;
//		}
//
//		// 开发环境决定的修正因子D
//		if (rbDevelopmentEnvironment1.isSelected()) {
//			d = 0.76;
//		} else if (rbDevelopmentEnvironment2.isSelected()) {
//			d = 1.0;
//		} else if (rbDevelopmentEnvironment3.isSelected()) {
//			d = 1.3;
//		}
//
//		// 返回结果：需求分析阶段的错误密度
//		return requirementAnalysisResult = a * d;
//
//	}
//
//	// 设计阶段的按钮
//	// 质量评估的按钮
//	@FXML
//	private CheckBox cbQualityEvaluation1;
//	@FXML
//	private CheckBox cbQualityEvaluation2;
//	@FXML
//	private CheckBox cbQualityEvaluation3;
//	@FXML
//	private CheckBox cbQualityEvaluation4;
//	@FXML
//	private CheckBox cbQualityEvaluation5;
//	@FXML
//	private CheckBox cbQualityEvaluation6;
//	@FXML
//	private CheckBox cbQualityEvaluation7;
//	@FXML
//	private CheckBox cbQualityEvaluation8;
//	@FXML
//	private CheckBox cbQualityEvaluation9;
//	@FXML
//	private CheckBox cbQualityEvaluation10;
//	@FXML
//	private CheckBox cbQualityEvaluation11;
//	@FXML
//	private CheckBox cbQualityEvaluation12;
//	@FXML
//	private CheckBox cbQualityEvaluation13;
//	// 异常管理评估
//	@FXML
//	private CheckBox cbExceptionManagement1;
//	@FXML
//	private CheckBox cbExceptionManagement2;
//	@FXML
//	private CheckBox cbExceptionManagement3;
//	@FXML
//	private CheckBox cbExceptionManagement4;
//	@FXML
//	private CheckBox cbExceptionManagement5;
//	@FXML
//	private CheckBox cbExceptionManagement6;
//	@FXML
//	private CheckBox cbExceptionManagement7;
//	@FXML
//	private CheckBox cbExceptionManagement8;
//	@FXML
//	private CheckBox cbExceptionManagement9;
//	@FXML
//	private CheckBox cbExceptionManagement10;
//	// 可塑性评估
//	@FXML
//	private CheckBox cbLimbernessEvaluation1;
//	@FXML
//	private CheckBox cbLimbernessEvaluation2;
//	@FXML
//	private CheckBox cbLimbernessEvaluation3;
//	@FXML
//	private CheckBox cbLimbernessEvaluation4;
//
//	@FXML
//	private TextField requirementAnalysisResultField;
//	@FXML
//	private Label lRequirementAnalysisResult;
//
//	/**
//	 * 开发过程的软件可靠性预测与分析-开发过程可靠性分析-设计阶段
//	 */
//
//	// 定义质量评估多选框中被选中的选项个数
//	int qe = 0;
//	// 定义异常管理评估多选框中被选中的选项个数
//	int em = 0;
//	// 定义可塑性评估多选框中被选中的选项个数
//	int le = 0;
//	// 初始化质量评估SQ的值ֵ
//	double SQ = 1.0;
//	// 初始化修正因子SA的值ֵ
//	double SA = 0.9;
//	// 初始化可塑性评估ST的值ֵ
//	double ST = 1.1;
//	// 定义设计阶段对软件失效率预测的修正因子 D
//	double D;
//	// 定义软件设计阶段的错误密度
//	double designPhaseResult;
//
//	// 计算设计阶段的错误密度
//	public double designPhase() {
//		// if ((rbSystemType1.isSelected() || rbSystemType2.isSelected() ||
//		// rbSystemType3.isSelected()
//		// || rbSystemType4.isSelected() || rbSystemType5.isSelected() ||
//		// rbSystemType6.isSelected())
//		// && (rbDevelopmentEnvironment1.isSelected() ||
//		// rbDevelopmentEnvironment2.isSelected()
//		// || rbDevelopmentEnvironment3.isSelected())) {
//		// requirementAnalysisResultField.setEditable(false);
//		// }
//
//		ArrayList<CheckBox> qualityEvaluation = new ArrayList<CheckBox>();
//		qualityEvaluation.add(cbQualityEvaluation1);
//		qualityEvaluation.add(cbQualityEvaluation2);
//		qualityEvaluation.add(cbQualityEvaluation3);
//		qualityEvaluation.add(cbQualityEvaluation4);
//		qualityEvaluation.add(cbQualityEvaluation5);
//		qualityEvaluation.add(cbQualityEvaluation6);
//		qualityEvaluation.add(cbQualityEvaluation7);
//		qualityEvaluation.add(cbQualityEvaluation8);
//		qualityEvaluation.add(cbQualityEvaluation9);
//		qualityEvaluation.add(cbQualityEvaluation10);
//		qualityEvaluation.add(cbQualityEvaluation11);
//		qualityEvaluation.add(cbQualityEvaluation12);
//		qualityEvaluation.add(cbQualityEvaluation13);
//		for (CheckBox cb : qualityEvaluation) {
//			if (cb.isSelected()) {
//				qe++;
//			}
//		}
//
//		if (qe < 6) {
//			SQ = 1.1;
//		}
//
//		ArrayList<CheckBox> exceptionManagement = new ArrayList<CheckBox>();
//		exceptionManagement.add(cbExceptionManagement1);
//		exceptionManagement.add(cbExceptionManagement2);
//		exceptionManagement.add(cbExceptionManagement3);
//		exceptionManagement.add(cbExceptionManagement4);
//		exceptionManagement.add(cbExceptionManagement5);
//		exceptionManagement.add(cbExceptionManagement6);
//		exceptionManagement.add(cbExceptionManagement7);
//		exceptionManagement.add(cbExceptionManagement8);
//		exceptionManagement.add(cbExceptionManagement9);
//		exceptionManagement.add(cbExceptionManagement10);
//		for (CheckBox cb : exceptionManagement) {
//			if (cb.isSelected()) {
//				em++;
//			}
//		}
//
//		if (em == 3) {
//			SA = 1;
//		} else if (em > 3) {
//			SA = 1.1;
//		}
//
//		ArrayList<CheckBox> limbernessEvaluation = new ArrayList<CheckBox>();
//		limbernessEvaluation.add(cbLimbernessEvaluation1);
//		limbernessEvaluation.add(cbLimbernessEvaluation2);
//		limbernessEvaluation.add(cbLimbernessEvaluation3);
//		limbernessEvaluation.add(cbLimbernessEvaluation4);
//		for (CheckBox cb : limbernessEvaluation) {
//			if (cb.isSelected()) {
//				le++;
//			}
//		}
//
//		if (le == 4) {
//			ST = 1.0;
//		}
//
//		// 软件设计阶段对软件失效率预测的修正因子 D
//		D = SA * ST * SQ;
//
//		// 返回结果：软件设计阶段的错误密度
//		if (requirementAnalysisResultField.getText().trim().length() != 0
//				&& requirementAnalysisResultField.getText().trim() != null) {
//			requirementAnalysisResult = Double.parseDouble(requirementAnalysisResultField.getText());
//		}
//
//		return designPhaseResult = D * requirementAnalysisResult;
//
//	}
//
//	// 编码阶段的输入框
//
//	@FXML
//	private TextField tfCodeAmount;
//	@FXML
//	private TextField tfDevelopMounth;
//	@FXML
//	private TextField tfAfterInstallMounth;
//	@FXML
//	private TextField designPhaseResultField;
//	@FXML
//	private Label lDesignPhaseResult;
//
//	/**
//	 * 开发过程的软件可靠性预测与分析-开发过程可靠性分析-编码阶段
//	 */
//	public double codingPhase() {
//		double codeAmount = Double.parseDouble(tfCodeAmount.getText());
//		double developMounth = Double.parseDouble(tfDevelopMounth.getText());
//		double afterInstallMounth = Double.parseDouble(tfAfterInstallMounth.getText());
//		
//		
//
//		return 0;
//	}
//	
//	
//
//	/**
//	 * 开发过程的软件可靠性预测与分析-开发过程可靠性预测
//	 */
//
//	// 开发过程可靠性预测输入框
//
//	@FXML
//	private TextField tfModuleCodeAmount;
//	@FXML
//	private TextField tfDevelopCMMLevel;
//	@FXML
//	private TextField tfMatureCMMLevel;
//
//	public double developReliabilityPrediction() {
//		int moduleCodeAmount = Integer.parseInt(tfModuleCodeAmount.getText());
//		int developCMMLevel = Integer.parseInt(tfDevelopCMMLevel.getText());
//		int matureCMMLevel = Integer.parseInt(tfMatureCMMLevel.getText());
//
//		return 0;
//
//	}
//
//	//
//
//	/**
//	 * 开发过程的软件可靠性预测与分析-软件缺陷早期预测
//	 */
//
//	// 软件缺陷早期预测地址输入框
//	@FXML
//	private TextField tfDevelopmentFileAddress;
//	
//	// 软件缺陷早期预测中缺陷数据表格
//	@FXML
//	private TableView<DefectAmount> developmentDefectTable;
//	@FXML
//	private TableColumn<DefectAmount, String> developmentMounthColumn;
//	@FXML
//	private TableColumn<DefectAmount, String> developmentDefectAmountColumn;
//	
//	//
//	@FXML
//	private void developmentImportFileWindow() throws FileNotFoundException, FileFormatException {
//		
//		openImportFileWindow(tfDevelopmentFileAddress,developmentDefectTable,developmentMounthColumn,developmentDefectAmountColumn);
//
//	}
//
//
//	/**
//	 * 在“请输入缺陷数据”的文本框中输入数据，并将其添加至表格中
//	 */
//	@FXML
//	private TextField tfDevelopmentInputDefectData;
//
//
//	@FXML
//	private void developmentInputDefectData() {		
//		
//		inputData(tfDevelopmentInputDefectData,developmentDefectTable,developmentMounthColumn,developmentDefectAmountColumn);
//
//	}
//
//	/**
//	 * 点击清除所有数据按钮，清除所有数据
//	 */
//
//	@FXML
//	private void developmentClearAllData() {
//
//		deleteAll(tfDevelopmentFileAddress);
//
//	}
//
//	/**
//	 * 点击删除该行按钮，删除该行数据
//	 */
//
//	@FXML
//	private void developmentHandleDeleteDefect() {
//		
//		deleteOne(developmentDefectTable);
//
//	}
//
//	/**
//	 * 点击输入完毕按钮，计算出截止当月发现的累积缺陷数，并输出到缺陷发现历史表格中
//	 */
//
//	/*
//	 * 通过确认输入完毕后的缺陷数据计算出截止当前周期发现的累积缺陷数，并将其存储在集合中
//	 */
//	private ObservableList<TotalDefectAmount> totalDefectData = FXCollections.observableArrayList();
//
//	@FXML
//	private TableView<TotalDefectAmount> historicDefectTable;
//	@FXML
//	private TableColumn<TotalDefectAmount, String> periodColumn;
//	@FXML
//	private TableColumn<TotalDefectAmount, String> periodDefectAmountColumn;
//	@FXML
//	private TableColumn<TotalDefectAmount, String> totalDefectAmountColumn;
//
//	@FXML
//	private void inputCompleteIsClicked() {
//		totalDefectData.clear();
//		int x = 0;
//		for (DefectAmount defectAmount : defectData) {
//			// System.out.println(defectAmount.getMounth() + "---" +
//			// defectAmount.getDefectAmount());
//			x += Integer.parseInt(defectAmount.getDefectAmount());
//			totalDefectData.add(new TotalDefectAmount(defectAmount.getMounth(), defectAmount.getDefectAmount(),
//					Integer.toString(x)));
//		}
//
//		historicDefectTable.setItems(totalDefectData);
//		periodColumn.setCellValueFactory(
//				new Callback<CellDataFeatures<TotalDefectAmount, String>, ObservableValue<String>>() {
//					public ObservableValue<String> call(CellDataFeatures<TotalDefectAmount, String> p) {
//						// p.getValue() returns the Person instance for a
//						// particular TableView row
//						return p.getValue().mounthProperty();
//					}
//				});
//		periodDefectAmountColumn.setCellValueFactory(
//				new Callback<CellDataFeatures<TotalDefectAmount, String>, ObservableValue<String>>() {
//					public ObservableValue<String> call(CellDataFeatures<TotalDefectAmount, String> p) {
//						// p.getValue() returns the Person instance for a
//						// particular TableView row
//						return p.getValue().defectAmountProperty();
//					}
//				});
//		totalDefectAmountColumn.setCellValueFactory(
//				new Callback<CellDataFeatures<TotalDefectAmount, String>, ObservableValue<String>>() {
//					public ObservableValue<String> call(CellDataFeatures<TotalDefectAmount, String> p) {
//						// p.getValue() returns the Person instance for a
//						// particular TableView row
//						return p.getValue().totalDefectAmountProperty();
//					}
//				});
//
//	}
//	
//	
//	
//	/**
//	 * 测试阶段的软件可靠性评估
//	 */
//	
//	@FXML
//	private TextField tfTestFileAddress;
//	
//	// 失效数据表格
//	@FXML
//	private TableView<DefectAmount> testDefectTable;
//	@FXML
//	private TableColumn<DefectAmount, String> testMounthColumn;
//	@FXML
//	private TableColumn<DefectAmount, String> testDefectAmountColumn;
//	
//	
//	@FXML
//	private void testImportFileWindow() throws FileNotFoundException, FileFormatException {
//		
//		openImportFileWindow(tfTestFileAddress,testDefectTable,testMounthColumn,testDefectAmountColumn);
//
//	}
//
//
//	/**
//	 * 在“请输入缺陷数据”的文本框中输入数据，并将其添加至表格中
//	 */
//	@FXML
//	private TextField tfTestInputDefectData;
//
//
//	@FXML
//	private void testInputDefectData() {		
//		
//		inputData(tfTestInputDefectData,testDefectTable,testMounthColumn,testDefectAmountColumn);
//
//	}
//	
//	/**
//	 * 点击删除该行按钮，删除该行数据
//	 */
//
//	@FXML
//	private void testHandleDeleteDefect() {
//		
//		deleteOne(testDefectTable);
//
//	}
//	
//	/**
//	 * 点击清除所有数据按钮，清除所有数据
//	 */
//
//	@FXML
//	private void testClearAllData() {
//
//		deleteAll(tfTestFileAddress);
//
//	}
//	
//	
//	
//
//	@FXML
//	private void designInitialize() {
//		if ((rbSystemType1.isSelected() || rbSystemType2.isSelected() || rbSystemType3.isSelected()
//				|| rbSystemType4.isSelected() || rbSystemType5.isSelected() || rbSystemType6.isSelected())
//				&& (rbDevelopmentEnvironment1.isSelected() || rbDevelopmentEnvironment2.isSelected()
//						|| rbDevelopmentEnvironment3.isSelected())) {
//			lRequirementAnalysisResult.setVisible(false);
//			requirementAnalysisResultField.setVisible(false);
//
//		}else{
//			lRequirementAnalysisResult.setVisible(true);
//			requirementAnalysisResultField.setVisible(true);
//		}
//	}
//
//	@FXML
//	private void codingInitialize() {
//		if ((cbQualityEvaluation1.isSelected() || cbQualityEvaluation2.isSelected() || cbQualityEvaluation3.isSelected()
//				|| cbQualityEvaluation4.isSelected() || cbQualityEvaluation9.isSelected()
//				|| cbQualityEvaluation5.isSelected() || cbQualityEvaluation10.isSelected()
//				|| cbQualityEvaluation6.isSelected() || cbQualityEvaluation11.isSelected()
//				|| cbQualityEvaluation7.isSelected() || cbQualityEvaluation12.isSelected()
//				|| cbQualityEvaluation8.isSelected() || cbQualityEvaluation13.isSelected())
//				&& (cbLimbernessEvaluation1.isSelected() || cbLimbernessEvaluation2.isSelected()
//						|| cbLimbernessEvaluation3.isSelected() || cbLimbernessEvaluation4.isSelected())
//				&& (cbExceptionManagement1.isSelected() || cbExceptionManagement2.isSelected()
//						|| cbExceptionManagement3.isSelected() || cbExceptionManagement4.isSelected()
//						|| cbExceptionManagement7.isSelected() || cbExceptionManagement9.isSelected()
//						|| cbExceptionManagement5.isSelected() || cbExceptionManagement8.isSelected()
//						|| cbExceptionManagement10.isSelected() || cbExceptionManagement6.isSelected())) {
//			lDesignPhaseResult.setVisible(false);
//			designPhaseResultField.setVisible(false);
//
//		}else{
//			lDesignPhaseResult.setVisible(true);
//			designPhaseResultField.setVisible(true);
//		}
//	}
//	
//	
//	
//	@FXML
//	private void developmentAnalysisEvaluateIsClicked() {
//		System.out.println(requirementAnalysis());
//		System.out.println(designPhase());
//	}
//	
//	
//	@FXML
//	private void testEvaluationIsClicked(){
//		double[] time = new double[al.size()];
//		for(int x = 0 ; x<al.size();x++){
//			time[x] = Double.parseDouble(al.get(x));
//		}
//		//double[] time1 = new double[] { 9, 12, 11, 4, 7, 2, 5, 8, 5, 7, 1, 6, 1, 9, 4, 1, 3, 3, 6, 1, 11, 33, 7, 91, 2, 1 };//NTDS，完全失效数据
//		JMModel jmModel = new JMModel(time);
//		jmModel.calculate(0.000001);//设置计算阈值为0.000001
//		jmModel.printResult();//输出计算结果到控制台
//	}
//	
	@FXML
	VBox developmentPeriodTasksBox;
	@FXML
	VBox modelChooseBox;
	@FXML
	VBox selfDetermineModelTypeBox;
	
	
	
	@FXML
	private void developmentPeriodChoosed(){
		developmentPeriodTasksBox.setDisable(false);
	}
	@FXML
	private void testPeriodChoosed(){
		modelChooseBox.setDisable(false);
	}
	@FXML
	private void selfDetermineModelChoosed(){
		selfDetermineModelTypeBox.setVisible(true);
	}
	
	
	
}
