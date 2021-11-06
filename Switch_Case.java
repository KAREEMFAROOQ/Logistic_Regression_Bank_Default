package com.maven.testng;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class Switch_Case {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver = getDriver("edge");
		driver.get("https://opensource-demo.orangehrmlive.com/");
		System.out.println("Title of the page is :" + driver.getTitle());
		driver.navigate().to("https://facebook.com");
		System.out.println("Title of the FB page is :" + driver.getTitle());

	}

	public static WebDriver getDriver(String brName) {
		WebDriver driver;
		switch (brName) {
		case "chrome":

			System.setProperty("webdriver.chrome.driver", "F:\\eclipse\\Resources\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
			break;

		case "edge":
			System.setProperty("webdriver.edge.driver", "F:\\eclipse\\Resources\\edgedriver_win64\\msedgedriver.exe");
			driver = new EdgeDriver();
			break;
		default:
			System.out.println("please enter a valid browser from the list 1.chrome,2.firefox,3.ie,4.edge");
			driver = null;
			break;

		}
		return driver;
	}

}
