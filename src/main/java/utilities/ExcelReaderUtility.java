package utilities;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class ExcelReaderUtility{

    public static Object[][] readExcelData(String xlFileName, String xlSheet) throws IOException {
        File excel = new File(System.getProperty("user.dir")+"\\testData\\"+xlFileName+".xlsx");
        FileInputStream file = new FileInputStream(excel);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet(xlSheet);
        int totalRows = sheet.getLastRowNum();
        int totalColumn = sheet.getRow(0).getLastCellNum();

        String[][] testData = new String[totalRows][totalColumn];

        for (int i = 1; i <= totalRows; i++) {
            for (int j = 0; j < totalColumn; j++) {
                testData[i-1][j]= sheet.getRow(i).getCell(j).toString();
            }
        }
        workbook.close();
        file.close();

        return testData;
    }
}
