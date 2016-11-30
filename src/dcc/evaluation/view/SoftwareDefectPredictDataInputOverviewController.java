package dcc.evaluation.view;

import java.io.FileNotFoundException;

import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import dcc.evaluation.view.model.DefectAmount;
import dcc.evaluation.view.model.FillTableActions;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SoftwareDefectPredictDataInputOverviewController extends FillTableActions{
	 	//软件缺陷早期预测地址输入框
		@FXML
		private TextField tfDevelopmentFileAddress;
		
		// 软件缺陷早期预测中缺陷数据表格
		@FXML
		private TableView<DefectAmount> developmentDefectTable;
		@FXML
		private TableColumn<DefectAmount, String> developmentMounthColumn;
		@FXML
		private TableColumn<DefectAmount, String> developmentDefectAmountColumn;
		
		//
		@FXML
		private void developmentImportFileWindow() throws FileNotFoundException, FileFormatException {
			
			openImportFileWindow(tfDevelopmentFileAddress,developmentDefectTable,developmentMounthColumn,developmentDefectAmountColumn);
	
		}
	
	
		/**
		 * 在“请输入缺陷数据”的文本框中输入数据，并将其添加至表格中
		 */
		@FXML
		private TextField tfDevelopmentInputDefectData;
	
	
		@FXML
		private void developmentInputDefectData() {		
			
			inputData(tfDevelopmentInputDefectData,developmentDefectTable,developmentMounthColumn,developmentDefectAmountColumn);
	
		}
	
		/**
		 * 点击清除所有数据按钮，清除所有数据
		 */
	
		@FXML
		private void developmentClearAllData() {
	
			deleteAll(tfDevelopmentFileAddress);
	
		}
	
		/**
		 * 点击删除该行按钮，删除该行数据
		 */
	
		@FXML
		private void developmentHandleDeleteDefect() {
			
			deleteOne(developmentDefectTable);
	
		}
	
}
