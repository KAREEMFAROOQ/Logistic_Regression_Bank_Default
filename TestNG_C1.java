package com.maven.testng;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class TestNG_C1 {
	ExtentReports extent;
	WebDriver driver;

	@Test()
	public void login() throws InterruptedException {

		ExtentTest login = extent.createTest("login", "verify the admin can add employee using ohrm");
		login.assignAuthor("suresh");
		login.assignCategory("mandatory");
		login.assignDevice("chrome");

		ExtentTest username = login.createNode("enter username", "user enters valid username");
		driver.findElement(By.id("txtUsername")).sendKeys("Admin");
		// login.pass("username entered successfully");
		username.log(Status.PASS, "username entered");
		username.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(getAppScreenshot(driver)).build());
		getAppScreenshot(driver);

		ExtentTest psw = login.createNode("enter pwd", "user enters valid psw");
		driver.findElement(By.name("txtPassword")).sendKeys("admin123");
		psw.warning("user entered password");

		ExtentTest log = login.createNode("click login");
		driver.findElement(By.id("btnLogin")).click();
		log.skip("login button");
		String title = driver.getTitle();
		if (title.equalsIgnoreCase("OrangeHRM")) {
			login.pass("user logged in succssfully");
		} else {
			login.fail("fails to login");
		}
	}

	@Test(priority = 1)
	public void navigatetopim() throws InterruptedException {
		ExtentTest navigate = extent.createTest("navigate to addemployee",
				"verify the admin can add employee using ohrm");

		driver.findElement(By.linkText("PIM")).click();
		driver.findElement(By.partialLinkText("Add Emp")).click();
		navigate.fail("unable to navigate to add emp");

		Thread.sleep(3000);

	}

	@Test(priority = 2)
	public void AddEmp() throws InterruptedException {
		ExtentTest testcase = extent.createTest("ohrm_addemployee", "verify the admin can add employee using ohrm");
		driver.findElement(By.id("firstName")).sendKeys("Vincere semantics");
		driver.findElement(By.name("lastName")).sendKeys("consultancy services");
		driver.findElement(By.id("btnSave")).click();
		testcase.pass("employy added successfully");
	}

	@BeforeClass
	public void openApplication() {
		this.driver = Switch_Case.getDriver("chrome");
		driver.get("https://opensource-demo.orangehrmlive.com");
		driver.manage().window().maximize();

		extent = new ExtentReports();
		ExtentSparkReporter sprkrep = new ExtentSparkReporter("F:\\eclipse\\Maven2\\ExtentReport_" + getuniqueID() + ".html");

		// sprkrep.config(ExtentSparkReporterConfig.builder().theme(Theme.DARK).documentTitle("MyReport").build());
		sprkrep.config().setTheme(Theme.DARK);
		sprkrep.config().setReportName("sample report for demo");
		sprkrep.config().setDocumentTitle("sample report for demo");

		extent.attachReporter(sprkrep);
		String title = driver.getTitle();
		if (title.equalsIgnoreCase("OrangeHRM")) {
			System.out.println("Application opened successfully");
		} else {
			System.out.println("Application not opened ");
		}
	}

	@AfterClass
	public void closeApplication() {
		driver.quit();
		extent.flush();
	}

	public static String getuniqueID() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddhhmms");
		return sd.format(new Date());
	}

	public static String getAppScreenshot(WebDriver driver) {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcFl = ts.getScreenshotAs(OutputType.FILE);
		String dstFlpath = "F:\\eclipse\\Maven2\\src_" + getuniqueID() + ".png";
		File dstFl = new File(dstFlpath);
		try {
			FileUtils.copyFile(srcFl, dstFl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dstFl.getAbsolutePath();

	}
}		

