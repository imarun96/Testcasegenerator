package com.scm.testcasegenerator.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.scm.testcasegenerator.service.DevOpsService;

@Controller
class TestCaseGeneratorController {
	Logger log = LoggerFactory.getLogger(TestCaseGeneratorController.class);

	@Autowired
	DevOpsService devOpsService;
	private static final String EXTERNAL_FILE_PATH = "D:/ChromeDriver/";

	@RequestMapping(value = { "/dashboard" }, method = RequestMethod.GET)
	public ModelAndView dashboardPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("dashboard");
		return modelAndView;
	}

	@GetMapping("/get-chrome-version/{chromeVersion}")
	public void eGet(@PathVariable("chromeVersion") String weekNumberString, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		weekNumberString = weekNumberString.substring(14, weekNumberString.length());
		devOpsService.getLink(Integer.parseInt(weekNumberString));
		System.out.println(weekNumberString);
	}

	@RequestMapping("/download")
	public void downloadCSV(HttpServletResponse response) throws IOException {
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
		String formattedDate = myDateObj.format(myFormatObj);
		File newFolder = new File("D:\\ChromeDriver\\" + formattedDate);
		newFolder.mkdir();
		System.out.println(newFolder.getName());
			File file = new File(EXTERNAL_FILE_PATH + "invoice.ods");
			if (file.exists()) {
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				response.setHeader("Content-Disposition",
						String.format("attachment; filename=\"" + file.getName() + "\""));
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
}