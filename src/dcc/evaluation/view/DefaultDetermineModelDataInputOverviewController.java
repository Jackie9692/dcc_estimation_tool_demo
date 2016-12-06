package dcc.evaluation.view;

import java.io.FileNotFoundException;

import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import dcc.evaluation.MainApp;
import dcc.evaluation.computation.model.JMModel;
import dcc.evaluation.view.model.DefectAmount;
import dcc.evaluation.view.model.ImportFileActions;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class DefaultDetermineModelDataInputOverviewController extends ImportFileActions {

	// 测试阶段软件可靠性评估-自动选择模型-选择失效数据类型
	@FXML
	private ComboBox dataTypeBox;

	// 测试阶段软件可靠性评估-自动选择模型-失效数据源地址输入框
	@FXML
	private TextField tfTestFileAddress;

	// 测试阶段软件可靠性评估-自动选择模型-完全失效数据表格
	@FXML
	private TableView<DefectAmount> testCompleteFailureTable;
	@FXML
	private TableColumn<DefectAmount, String> testFailureTimesColumn;
	@FXML
	private TableColumn<DefectAmount, String> testTimeBetweenFailuresColumn;

	// 测试阶段软件可靠性评估-自动选择模型-不完全失效数据表格
	@FXML
	private TableView<DefectAmount> testIncompleteFailureTable;
	@FXML
	private TableColumn<DefectAmount, String> testIntervalTimeColumn;
	@FXML
	private TableColumn<DefectAmount, String> testFailureAmountColumn;

	//
	@FXML
	private void developmentImportFileWindow() throws FileNotFoundException, FileFormatException {
		if (testCompleteFailureTable.isVisible() && testIncompleteFailureTable.isDisable()) {
			openImportFileWindow(tfTestFileAddress, testCompleteFailureTable, testFailureTimesColumn,
					testTimeBetweenFailuresColumn);
		} else if (testIncompleteFailureTable.isVisible() && testCompleteFailureTable.isDisable()) {
			openImportFileWindow(tfTestFileAddress, testIncompleteFailureTable, testIntervalTimeColumn,
					testFailureAmountColumn);
		}

	}

	/**
	 * 在“请输入缺陷数据”的文本框中输入数据，并将其添加至表格中
	 */
	@FXML
	private TextField tfTestInputFailureData;

	@FXML
	private void developmentInputDefectData() {
		if (testCompleteFailureTable.isVisible() && testIncompleteFailureTable.isDisable()) {
			inputData(tfTestInputFailureData, testCompleteFailureTable, testFailureTimesColumn,
					testTimeBetweenFailuresColumn);
		} else if (testIncompleteFailureTable.isVisible() && testCompleteFailureTable.isDisable()) {
			inputData(tfTestInputFailureData, testIncompleteFailureTable, testIntervalTimeColumn, testFailureAmountColumn);
		}

	}

	/**
	 * 点击清除所有数据按钮，清除所有数据
	 */

	@FXML
	private void developmentClearAllData() {

		deleteAll(tfTestFileAddress);

	}

	/**
	 * 点击删除该行按钮，删除该行数据
	 */

	@FXML
	private void developmentHandleDeleteDefect() {
		if (testCompleteFailureTable.isVisible() && testIncompleteFailureTable.isDisable()) {
			deleteOne(testCompleteFailureTable);

		} else if (testIncompleteFailureTable.isVisible() && testCompleteFailureTable.isDisable()) {
			deleteOne(testIncompleteFailureTable);

		}

	}

	@FXML
	private void initialize() {
		dataTypeBox.getItems().addAll("完全失效数据", "不完全失效数据");
		dataTypeBox.setValue("完全失效数据");

		dataTypeBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals("不完全失效数据")) {
					testCompleteFailureTable.setDisable(true);
					testCompleteFailureTable.setVisible(false);
					testIncompleteFailureTable.setDisable(false);
					testIncompleteFailureTable.setVisible(true);
					deleteAll(tfTestFileAddress);
				} else if (newValue.equals("完全失效数据")) {
					testIncompleteFailureTable.setDisable(true);
					testIncompleteFailureTable.setVisible(false);
					testCompleteFailureTable.setDisable(false);
					testCompleteFailureTable.setVisible(true);
					deleteAll(tfTestFileAddress);
				}
			}

		});
	}
	@FXML
	private void testEvaluationIsClicked(){
		
		MainApp.showDefaultDetermineModelResultOverview();
		double[] time = new double[al.size()];
		for(int x = 0 ; x<al.size();x++){
			time[x] = Double.parseDouble(al.get(x));
		}
		
		JMModel jmModel = new JMModel(time);
		jmModel.calculate(0.000001);//设置计算阈值为0.000001
		jmModel.printResult();//输出计算结果到控制台
		
	}

}
