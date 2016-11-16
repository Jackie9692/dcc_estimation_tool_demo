package dcc.evaluation.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;



public class InputFileController {

	 	private static final String EXTENSION_XLS = "xls";
	    private static final String EXTENSION_XLSX = "xlsx";
//	    public static void main(String[] args) throws FileNotFoundException, FileFormatException {
//	    	ReadExcelTest ret = new ReadExcelTest();
//	    	ret.readExcel("C:\\Users\\admin\\Downloads\\project\\test.xlsx");
//		}

	    /***
	     * <pre>
	     * ȡ��Workbook����(xls��xlsx����ͬ,��������Workbook��ʵ����)
	     *   xls:HSSFWorkbook
	     *   xlsx��XSSFWorkbook
	     * @param filePath
	     * @return
	     * @throws IOException
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
	     * �ļ����
	     * @param filePath
	     * @throws FileNotFoundException
	     * @throws FileFormatException
	     */
	    private void preReadCheck(String filePath) throws FileNotFoundException, FileFormatException {
	        // ������
	        File file = new File(filePath);
	        if (!file.exists()) {
	            throw new FileNotFoundException("������ļ������ڣ�" + filePath);
	        }

	        if (!(filePath.endsWith(EXTENSION_XLS) || filePath.endsWith(EXTENSION_XLSX))) {
	            throw new FileFormatException("������ļ�����excel");
	        }
	    }

	    /**
	     * ��ȡexcel�ļ�����
	     * @param filePath
	     * @throws FileNotFoundException
	     * @throws FileFormatException
	     */
	    public void readExcel(String filePath) throws FileNotFoundException, FileFormatException {
	        // ���
	        this.preReadCheck(filePath);
	        // ��ȡworkbook����
	        Workbook workbook = null;

	        try {
	            workbook = this.getWorkbook(filePath);
	            // ���ļ� һ��sheetһ��sheet�ض�ȡ
	            for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
	                Sheet sheet = workbook.getSheetAt(numSheet);
	                if (sheet == null) {
	                    continue;
	                }
//	                System.out.println("=======================" + sheet.getSheetName() + "=========================");

	                int firstRowIndex = sheet.getFirstRowNum();
	                int lastRowIndex = sheet.getLastRowNum();

	                // ��ȡ���� ��,��ͷ
	                Row firstRow = sheet.getRow(firstRowIndex);
	                for (int i = firstRow.getFirstCellNum(); i <= firstRow.getLastCellNum(); i++) {
	                    Cell cell = firstRow.getCell(i);
	                    String cellValue = this.getCellValue(cell, true);
	                    System.out.print(" " + cellValue + "\t");
	                }
	                System.out.println("");

	                // ��ȡ������
	                for (int rowIndex = firstRowIndex + 1; rowIndex <= lastRowIndex; rowIndex++) {
	                    Row currentRow = sheet.getRow(rowIndex);// ��ǰ��
	                    int firstColumnIndex = currentRow.getFirstCellNum(); // ����
	                    int lastColumnIndex = currentRow.getLastCellNum();// ���һ��
	                    for (int columnIndex = firstColumnIndex; columnIndex <= lastColumnIndex; columnIndex++) {
	                        Cell currentCell = currentRow.getCell(columnIndex);// ��ǰ��Ԫ��
	                        String currentCellValue = this.getCellValue(currentCell, true);// ��ǰ��Ԫ���ֵ
	                        System.out.print(currentCellValue + "\t");
	                    }
	                    System.out.println("");
	                }
//	                System.out.println("======================================================");
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
	     * ȡ��Ԫ���ֵ
	     * @param cell ��Ԫ�����
	     * @param treatAsStr Ϊtrueʱ�������ı���ȡֵ (ȡ�������ı�������ѡ�1��ȡ�ɡ�1.0��)
	     * @return
	     */
	    private String getCellValue(Cell cell, boolean treatAsStr) {
	        if (cell == null) {
	            return "";
	        }

	        if (treatAsStr) {
	            // ��Ȼexcel�����õĶ����ı������������ı����������硰1��ȡ�ɡ�1.0��
	            // ����������䣬��ʱ���������ı�����ȡ
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



}

