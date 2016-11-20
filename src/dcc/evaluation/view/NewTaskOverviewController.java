package dcc.evaluation.view;

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

import dcc.evaluation.MainApp;
import dcc.evaluation.computation.model.DefectAmount;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.FileChooser;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class NewTaskOverviewController {

	public NewTaskOverviewController() {
		super();
	}

	// 需求分析阶段的按钮
	@FXML
	private RadioButton rbSystemType1;
	@FXML
	private RadioButton rbSystemType2;
	@FXML
	private RadioButton rbSystemType3;
	@FXML
	private RadioButton rbSystemType4;
	@FXML
	private RadioButton rbSystemType5;
	@FXML
	private RadioButton rbSystemType6;
	@FXML
	private RadioButton rbDevelopmentEnvironment1;
	@FXML
	private RadioButton rbDevelopmentEnvironment2;
	@FXML
	private RadioButton rbDevelopmentEnvironment3;

	// 设计阶段的按钮
	// 质量评估的按钮
	@FXML
	private CheckBox cbQualityEvaluation1;
	@FXML
	private CheckBox cbQualityEvaluation2;
	@FXML
	private CheckBox cbQualityEvaluation3;
	@FXML
	private CheckBox cbQualityEvaluation4;
	@FXML
	private CheckBox cbQualityEvaluation5;
	@FXML
	private CheckBox cbQualityEvaluation6;
	@FXML
	private CheckBox cbQualityEvaluation7;
	@FXML
	private CheckBox cbQualityEvaluation8;
	@FXML
	private CheckBox cbQualityEvaluation9;
	@FXML
	private CheckBox cbQualityEvaluation10;
	@FXML
	private CheckBox cbQualityEvaluation11;
	@FXML
	private CheckBox cbQualityEvaluation12;
	@FXML
	private CheckBox cbQualityEvaluation13;
	// 异常管理评估
	@FXML
	private CheckBox cbExceptionManagement1;
	@FXML
	private CheckBox cbExceptionManagement2;
	@FXML
	private CheckBox cbExceptionManagement3;
	@FXML
	private CheckBox cbExceptionManagement4;
	@FXML
	private CheckBox cbExceptionManagement5;
	@FXML
	private CheckBox cbExceptionManagement6;
	@FXML
	private CheckBox cbExceptionManagement7;
	@FXML
	private CheckBox cbExceptionManagement8;
	@FXML
	private CheckBox cbExceptionManagement9;
	@FXML
	private CheckBox cbExceptionManagement10;
	// 可塑性评估
	@FXML
	private CheckBox cbLimbernessEvaluation1;
	@FXML
	private CheckBox cbLimbernessEvaluation2;
	@FXML
	private CheckBox cbLimbernessEvaluation3;
	@FXML
	private CheckBox cbLimbernessEvaluation4;

	// 开发过程的软件可靠性预测与分析-开发过程可靠性分析-需求分析阶段
	// 定义平均错误密度A
	double a;
	// 定义开发环境决定的修正因子D
	double d;
	// 定义需求阶段的错误密度
	double requirementAnalysisResult;
	// 计算需求阶段的错误密度

	public double requirementAnalysis() {

		// 确定平均错误密度A(错误/行)
		if (rbSystemType1.isSelected()) {
			a = 0.0128;
		} else if (rbSystemType2.isSelected()) {
			a = 0.0092;
		} else if (rbSystemType3.isSelected()) {
			a = 0.0078;
		} else if (rbSystemType4.isSelected()) {
			a = 0.0018;
		} else if (rbSystemType5.isSelected()) {
			a = 0.0085;
		} else if (rbSystemType6.isSelected()) {
			a = 0.0123;
		}

		// 开发环境决定的修正因子D
		if (rbDevelopmentEnvironment1.isSelected()) {
			d = 0.76;
		} else if (rbDevelopmentEnvironment2.isSelected()) {
			d = 1.0;
		} else if (rbDevelopmentEnvironment3.isSelected()) {
			d = 1.3;
		}

		// 返回结果：需求分析阶段的错误密度
		return requirementAnalysisResult = a * d;

	}

	// 开发过程的软件可靠性预测与分析-开发过程可靠性分析-设计阶段
	// 定义质量评估多选框中被选中的选项个数
	int qe = 0;
	// 定义异常管理评估多选框中被选中的选项个数
	int em = 0;
	// 定义可塑性评估多选框中被选中的选项个数
	int le = 0;
	// 初始化质量评估SQ的值ֵ
	double SQ = 1.0;
	// 初始化修正因子SA的值ֵ
	double SA = 0.9;
	// 初始化可塑性评估ST的值ֵ
	double ST = 1.1;
	// 定义设计阶段对软件失效率预测的修正因子 D
	double D;
	// 定义软件设计阶段的错误密度
	double designPhaseResult;

	// 计算设计阶段的错误密度
	public double designPhase() {

		ArrayList<CheckBox> qualityEvaluation = new ArrayList<CheckBox>();
		qualityEvaluation.add(cbQualityEvaluation1);
		qualityEvaluation.add(cbQualityEvaluation2);
		qualityEvaluation.add(cbQualityEvaluation3);
		qualityEvaluation.add(cbQualityEvaluation4);
		qualityEvaluation.add(cbQualityEvaluation5);
		qualityEvaluation.add(cbQualityEvaluation6);
		qualityEvaluation.add(cbQualityEvaluation7);
		qualityEvaluation.add(cbQualityEvaluation8);
		qualityEvaluation.add(cbQualityEvaluation9);
		qualityEvaluation.add(cbQualityEvaluation10);
		qualityEvaluation.add(cbQualityEvaluation11);
		qualityEvaluation.add(cbQualityEvaluation12);
		qualityEvaluation.add(cbQualityEvaluation13);
		for (CheckBox cb : qualityEvaluation) {
			if (cb.isSelected()) {
				qe++;
			}
		}

		if (qe < 6) {
			SQ = 1.1;
		}

		ArrayList<CheckBox> exceptionManagement = new ArrayList<CheckBox>();
		exceptionManagement.add(cbExceptionManagement1);
		exceptionManagement.add(cbExceptionManagement2);
		exceptionManagement.add(cbExceptionManagement3);
		exceptionManagement.add(cbExceptionManagement4);
		exceptionManagement.add(cbExceptionManagement5);
		exceptionManagement.add(cbExceptionManagement6);
		exceptionManagement.add(cbExceptionManagement7);
		exceptionManagement.add(cbExceptionManagement8);
		exceptionManagement.add(cbExceptionManagement9);
		exceptionManagement.add(cbExceptionManagement10);
		for (CheckBox cb : exceptionManagement) {
			if (cb.isSelected()) {
				em++;
			}
		}

		if (em == 3) {
			SA = 1;
		} else if (em > 3) {
			SA = 1.1;
		}

		ArrayList<CheckBox> limbernessEvaluation = new ArrayList<CheckBox>();
		limbernessEvaluation.add(cbLimbernessEvaluation1);
		limbernessEvaluation.add(cbLimbernessEvaluation2);
		limbernessEvaluation.add(cbLimbernessEvaluation3);
		limbernessEvaluation.add(cbLimbernessEvaluation4);
		for (CheckBox cb : limbernessEvaluation) {
			if (cb.isSelected()) {
				le++;
			}
		}

		if (le == 4) {
			ST = 1.0;
		}

		// 软件设计阶段对软件失效率预测的修正因子 D
		D = SA * ST * SQ;

		// 返回结果：软件设计阶段的错误密度

		return designPhaseResult = D * requirementAnalysisResult;

	}

	@FXML
	private void importFileWindow() throws FileNotFoundException, FileFormatException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("选择导入文件");
		File file = fileChooser.showOpenDialog(null);
		if(file != null){
			//FXMLLoader loader = new FXMLLoader();
			//NewTaskOverviewController ntoc = new NewTaskOverviewController();
					//loader.getController();
			readExcel(file.getAbsolutePath());
		}

	}

	// 软件缺陷早期预测中缺陷数据表格

	@FXML
	private TableView<DefectAmount> defectTable ;

	@FXML
	private TableColumn<DefectAmount, String> mounthColumn ;
	@FXML
	private TableColumn<DefectAmount, String> defectAmountColumn ;

	public void fillTable(ObservableList<DefectAmount> defectData) {
		for (DefectAmount defectAmount : defectData) {
			System.out.println(defectAmount.getMounth() + "---" + defectAmount.getDefectAmount());
		}
		System.out.println(defectTable);
		defectTable.setItems(defectData);
		System.out.println(defectTable);
		mounthColumn
				.setCellValueFactory(new Callback<CellDataFeatures<DefectAmount, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<DefectAmount, String> p) {
						// p.getValue() returns the Person instance for a
						// particular TableView row
						return p.getValue().mounthProperty();
					}
				});

		defectAmountColumn
				.setCellValueFactory(new Callback<CellDataFeatures<DefectAmount, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<DefectAmount, String> p) {
						// p.getValue() returns the Person instance for a
						// particular TableView row
						return p.getValue().defectAmountProperty();
					}
				});

		// mounthColumn.setCellValueFactory(cellData ->
		// cellData.getValue().mounthProperty());
		// defectAmountColumn.setCellValueFactory(cellData ->
		// cellData.getValue().defectAmountProperty());
	}
	
	private static final String EXTENSION_XLS = "xls";
	private static final String EXTENSION_XLSX = "xlsx";

	//NewTaskOverviewController ntoc =new NewTaskOverviewController();

	/*
	 * 将导入的软件缺陷早期预测失效数据存储在集合中
	 */
	private ObservableList<DefectAmount> defectData = FXCollections.observableArrayList();

	// public static void main(String[] args) throws FileNotFoundException,
	// FileFormatException {
	// ReadExcelTest ret = new ReadExcelTest();
	// ret.readExcel("C:\\Users\\admin\\Downloads\\project\\test.xlsx");
	// }

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

				// 读取首行，即表头
				Row firstRow = sheet.getRow(firstRowIndex);
				for (int i = firstRow.getFirstCellNum(); i <= firstRow.getLastCellNum(); i++) {
					Cell cell = firstRow.getCell(i);
					String cellValue = this.getCellValue(cell, true);
					// System.out.print(" " + cellValue + "\t");
				}
				System.out.println("");

				// 读取数据行
				ArrayList<String> al = new ArrayList<String>();
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

						// defectData.add(new
						// DefectAmount(Integer.toString(x),currentCellValue));
						// for (DefectAmount da : defectData) {
						// System.out.println(da.getMounth()+"---"+da.getDefectAmount());
						// }

					}
					// System.out.println("");
				}
				int x = 1;
				for (String s : al) {
					defectData.add(new DefectAmount(Integer.toString(x), s));
					x++;
				}

//				for (DefectAmount da : defectData) {
//					System.out.println(da.getMounth() + "---" + da.getDefectAmount());
//				}
				
				fillTable(defectData);
				//NewTaskOverviewController.fillTable(defectData);
				// System.out.println("======================================================");
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


	@FXML
	private void evaluateIsClicked() {
		System.out.println(requirementAnalysis());
		System.out.println(designPhase());
	}

}
