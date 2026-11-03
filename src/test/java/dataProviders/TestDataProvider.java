package dataProviders;

import org.testng.annotations.DataProvider;
import utilities.ExcelReaderUtility;

import java.io.IOException;

public class TestDataProvider {

    @DataProvider(name = "loginData")
    public static Object[][] loginData() throws IOException {
        return ExcelReaderUtility.readExcelData("credentials","Sheet1");
    }
}
