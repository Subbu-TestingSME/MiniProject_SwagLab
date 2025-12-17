package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;

public class ExcelUtils {
	public static String getCellData(String filePath, String sheetName, int row, int col) {
		try (FileInputStream fis = new FileInputStream(filePath); Workbook wb = new XSSFWorkbook(fis)) {
			return wb.getSheet(sheetName).getRow(row).getCell(col).getStringCellValue();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
