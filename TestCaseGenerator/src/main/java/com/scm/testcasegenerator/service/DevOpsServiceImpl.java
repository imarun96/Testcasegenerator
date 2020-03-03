package com.scm.testcasegenerator.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

@Service
public class DevOpsServiceImpl implements DevOpsService {
	@Override
	public void getLink(int chromeVersion) {
		System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\" + chromeVersion + "\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://www.javatpoint.com/");
	}

	@Override
	public void getFiles() {
		File file = new File("D:\\ChromeDriver\\invoice.ods");
		Sheet sheet = null;
		try {
			sheet = SpreadSheet.createFromFile(file).getSheet(0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println(sheet.getSpreadSheet().getSheetCount());
		System.out.println(sheet.getCellAt("A1").getValue());
		File outputFile = new File("invoice.ods");
		try {
			sheet.getSpreadSheet().saveAs(outputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}