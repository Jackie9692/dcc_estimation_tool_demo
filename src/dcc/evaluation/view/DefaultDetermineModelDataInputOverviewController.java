package dcc.evaluation.view;

import java.io.FileNotFoundException;

import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import dcc.evaluation.view.model.DefectAmount;
import dcc.evaluation.view.model.FillTableActions;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class DefaultDetermineModelDataInputOverviewController extends FillTableActions{
			//测试阶段软件可靠性评估-自动选择模型功能失效数据源地址输入框
			@FXML
			private TextField tfTestFileAddress;
			
			// 测试阶段软件可靠性评估-自动选择模型功能失效数据表格
			@FXML
			private TableView<DefectAmount> testFailureTable;
			@FXML
			private TableColumn<DefectAmount, String> testMounthColumn;
			@FXML
			private TableColumn<DefectAmount, String> testFailureAmountColumn;
			
			//
			@FXML
			private void developmentImportFileWindow() throws FileNotFoundException, FileFormatException {
				
				openImportFileWindow(tfTestFileAddress,testFailureTable,testMounthColumn,testFailureAmountColumn);
		
			}
		
		
			/**
			 * 在“请输入缺陷数据”的文本框中输入数据，并将其添加至表格中
			 */
			@FXML
			private TextField tfTestInputFailureData;
		
		
			@FXML
			private void developmentInputDefectData() {		
				
				inputData(tfTestInputFailureData,testFailureTable,testMounthColumn,testFailureAmountColumn);
		
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
				
				deleteOne(testFailureTable);
		
			}
				
		
}
