package dcc.evaluation.view.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.Callback;

public class ImportFileActions {
	//private TextField tfFileAddress;
//	private TableView<DefectAmount> defectTable;
//	private TableColumn<DefectAmount, String> mounthColumn;
//	private TableColumn<DefectAmount, String> defectAmountColumn;
	
	public void openImportFileWindow(TextField tfFileAddress,TableView defectTable,TableColumn mounthColumn,TableColumn defectAmountColumn) throws FileNotFoundException, FileFormatException{
		
		
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("选择导入文件");
		File file = fileChooser.showOpenDialog(null);
		deleteAll(tfFileAddress);
		if (file != null) {
			// FXMLLoader loader = new FXMLLoader();
			// NewTaskOverviewController ntoc = new NewTaskOverviewController();
			// loader.getController();
			String fileAddress = file.getAbsolutePath();
			tfFileAddress.setText(fileAddress);
			readExcel(fileAddress);
		}
		fillTable(defectData,defectTable,mounthColumn,defectAmountColumn);
	}
	
	
	private static final String EXTENSION_XLS = "xls";
	private static final String EXTENSION_XLSX = "xlsx";

	/*
	 * 将导入的软件缺陷早期预测失效数据存储在集合中
	 */
	public  static ObservableList defectData = FXCollections.observableArrayList();
	public static ArrayList<String> al = new ArrayList<String>();

	int x = 1;

	
	private void makeDefectData(ArrayList<String> al) {
		// int x = 1;
		for (String s : al) {
			//defectData.add(new DefectAmount(Integer.toString(al.indexOf(s)+1), s));
			defectData.add(new DefectAmount(Integer.toString(x), s));
			x++;
		}

		
	}

	/***
	 * <pre>
	 * 取得Workbook对象(xls和xlsx对象不同,不过都是Workbook的实现类)
	 *   xls:HSSFWorkbook
	 *   xlsx：XSSFWorkbook
	 * &#64;param filePath
	 * &#64;return
	 * &#64;throws IOException
	 * </pre>
	 */
	private Workbook getWorkbook(String filePath) throws IOException {
		Workbook workbook = null;
		InputStream is = new FileInputStream(filePath);
		if (filePath.endsWith(EXTENSION_XLS)) {
			workbook = new HSSFWorkbook(is);
		} else if (filePath.endsWith(EXTENSION_XLSX)) {
			workbook = new XSSFWorkbook(is);
		}
		return workbook;
	}

	/**
	 * 文件检查
	 * 
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws FileFormatException
	 */
	private void preReadCheck(String filePath) throws FileNotFoundException, FileFormatException {
		// 常规检查
		File file = new File(filePath);
		if (!file.exists()) {
			throw new FileNotFoundException("传入的文件不存在：" + filePath);
		}

		if (!(filePath.endsWith(EXTENSION_XLS) || filePath.endsWith(EXTENSION_XLSX))) {
			throw new FileFormatException("传入的文件不是excel");
		}
	}

	/**
	 * 读取excel文件内容
	 * 
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws FileFormatException
	 */
	public void readExcel(String filePath) throws FileNotFoundException, FileFormatException {
		// 检查
		this.preReadCheck(filePath);
		// 获取workbook对象
		Workbook workbook = null;

		try {
			workbook = this.getWorkbook(filePath);
			// 读文件 一个sheet一个sheet地读取
			for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
				Sheet sheet = workbook.getSheetAt(numSheet);
				if (sheet == null) {
					continue;
				}
				// System.out.println("=======================" +
				// sheet.getSheetName() + "=========================");

				int firstRowIndex = sheet.getFirstRowNum();
				int lastRowIndex = sheet.getLastRowNum();

				// // 读取首行，即表头
				// Row firstRow = sheet.getRow(firstRowIndex);
				// for (int i = firstRow.getFirstCellNum(); i <=
				// firstRow.getLastCellNum(); i++) {
				// Cell cell = firstRow.getCell(i);
				// String cellValue = this.getCellValue(cell, true);
				// // System.out.print(" " + cellValue + "\t");
				// }
				// //System.out.println("");

				// 读取数据行
				// ArrayList<String> al = new ArrayList<String>();
				for (int rowIndex = firstRowIndex + 1; rowIndex <= lastRowIndex; rowIndex++) {
					Row currentRow = sheet.getRow(rowIndex);// 当前行
					int firstColumnIndex = currentRow.getFirstCellNum(); // 首列
					int lastColumnIndex = currentRow.getLastCellNum();// 最后一列
					for (int columnIndex = firstColumnIndex; columnIndex <= lastColumnIndex; columnIndex++) {
						Cell currentCell = currentRow.getCell(columnIndex);// 当前单元格
						String currentCellValue = this.getCellValue(currentCell, true);// 当前单元格的值ֵ
						// System.out.print(currentCellValue + "---");

						if (currentCellValue != null && currentCellValue.length() != 0) {
							al.add(currentCellValue);
						}

					}
					// System.out.println("");
				}
				// int x = 1;
				// for (String s : al) {
				// defectData.add(new DefectAmount(Integer.toString(x), s));
				// x++;
				// }
				//
				// fillTable(defectData);
//				for (String s : al) {
//					defectData.add(new DefectAmount(Integer.toString(x), s));
//					x++;
//				}
				makeDefectData(al);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 取单元格的值
	 * 
	 * @param cell
	 *            单元格对象
	 * @param treatAsStr
	 *            为true时，当做文本来取值 (取到的是文本，不会把“1”取成“1.0”)
	 * @return
	 */
	private String getCellValue(Cell cell, boolean treatAsStr) {
		if (cell == null) {
			return "";
		}

		if (treatAsStr) {
			// 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
			// 加上下面这句，临时把它当做文本来读取
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}

		if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		} else {
			return String.valueOf(cell.getStringCellValue());
		}
	}
	
	
	
	public void fillTable(ObservableList defectData,TableView defectTable,TableColumn mounthColumn,TableColumn defectAmountColumn) {
//		for (DefectAmount defectAmount : defectData) {
//			System.out.println(defectAmount.getMounth() + "---" + defectAmount.getDefectAmount());
//		}
		// System.out.println(defectTable);
		defectTable.setItems(defectData);
		// System.out.println(defectTable);

		// Callback<TableColumn<DefectAmount, String>,
		// TableCell<DefectAmount, String>> cellFactory
		// = (TableColumn<DefectAmount, String> p) -> new EditingCell();
		//

		// mounthColumn.setCellFactory(TextFieldTableCell.<DefectAmount>forTableColumn());
		mounthColumn
				.setCellValueFactory(new Callback<CellDataFeatures<DefectAmount, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<DefectAmount, String> p) {
						// p.getValue() returns the Person instance for a
						// particular TableView row
						return p.getValue().mounthProperty();
					}
				});
		defectAmountColumn.setCellFactory(TextFieldTableCell.<DefectAmount>forTableColumn());
		defectAmountColumn
				.setCellValueFactory(new Callback<CellDataFeatures<DefectAmount, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<DefectAmount, String> p) {
						// p.getValue() returns the Person instance for a
						// particular TableView row
						return p.getValue().defectAmountProperty();
					}
				});
		

	}
	
	
	
	public void inputData(TextField tfinputDefectData,TableView defectTable,TableColumn mounthColumn,TableColumn defectAmountColumn){
		String errorMessage = "";

		if (tfinputDefectData.getText() == null || tfinputDefectData.getText().length() == 0) {
			errorMessage += "请输入缺陷数据！\n";
		} else {
			// try to parse the 缺陷数据 into an int.
			try {
				Integer.parseInt(tfinputDefectData.getText());
			} catch (NumberFormatException e) {
				errorMessage += "无效的缺陷数据 (必须为数字)!\n";
			}
		}
		if (errorMessage.length() == 0) {
			al.add(tfinputDefectData.getText().trim());
			defectData.clear();
			x = 1;
			makeDefectData(al);
			fillTable(defectData,defectTable,mounthColumn,defectAmountColumn);
			// defectData.add(new DefectAmount(Integer.toString(x),
			// tfinputDefectData.getText().trim()));
			// fillTable(defectData);
		} else {
			// Show the error message.
			// Dialogs.create()
			// .title("Invalid Fields")
			// .masthead("Please correct invalid fields")
			// .message(errorMessage)
			// .showError();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("错误");
			alert.setHeaderText(null);
			alert.setContentText("\r\n" + errorMessage);

			alert.showAndWait();
		}
	}
	
	public void deleteAll(TextField tfFileAddress){
		x = 1;
		al.clear();
		defectData.clear();
		tfFileAddress.clear();
		
	}
	
	public void deleteOne(TableView defectTable){
		x = 1;
		int selectedIndex = defectTable.getSelectionModel().getSelectedIndex();
		System.out.println(selectedIndex);
		if (selectedIndex >= 0) {
			al.remove(selectedIndex);
			defectData.clear();
			makeDefectData(al);
			// defectTable.getItems().remove(selectedIndex);

		} else {
			// Nothing selected.

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("错误");
			alert.setHeaderText(null);
			alert.setContentText("\r\n请选择要删除的缺陷数据！");

			alert.showAndWait();
		}
	}
	
}
