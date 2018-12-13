
package com.utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public abstract class BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
	private static final String BREAK_LINE = "\n";// "</br>";
	public static ExtentTest test;
	public static ExtentReports extent;
	
	protected String username ="testertested100@gmail.com";
	protected String password = "Qwerty@123";
	private String browserType;
	protected WebDriver driver;
	private String applicationUrl;

	public enum DriverType {
		Firefox, IE, Chrome
	}

	@BeforeSuite
	public void before() {
		extent = new ExtentReports("target/surefire-reports/CustomReport.html", true);
	}

	@BeforeClass
	public void setUp() throws Exception {
		WebDriverManager.chromedriver().setup();
		WebDriverManager.firefoxdriver().setup();
		WebDriverManager.iedriver().setup();
		browserType = Configuration.readApplicationFile("Browser");
		username = Configuration.readApplicationFile("Username");
		password = Configuration.readApplicationFile("Password");
		this.applicationUrl = Configuration.readApplicationFile("URL");

		if (DriverType.Firefox.toString().toLowerCase().equals(browserType.toLowerCase())) {
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);
			driver = new FirefoxDriver();
		} else if (DriverType.IE.toString().toLowerCase().equals(browserType.toLowerCase())) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new InternetExplorerDriver(capabilities);
		} else if (DriverType.Chrome.toString().toLowerCase().equals(browserType.toLowerCase())) {
			driver = new ChromeDriver();
		} else {
			throw new Exception("Please pass a valid browser type value");
		}

		/**
		 * Maximize window
		 */
		driver.manage().window().maximize();

		/**
		 * Delete cookies
		 */
		driver.manage().deleteAllCookies();

		/**
		 * Open application URL
		 */
		getWebDriver().navigate().to(applicationUrl);
		
	}

	@AfterMethod
	public void afterMainMethod(ITestResult result) throws IOException, InterruptedException {
		if (result.getStatus() == ITestResult.FAILURE) {
			captureScreenshot(result);
		}		
		extent.endTest(test);
	}

	@AfterClass
	public void closeBrowser(){
		driver.quit();
	}
	
	@BeforeMethod
	public void setTest(Method method) {
		test = extent.startTest(method.getName(), this.getClass().getName());
		test.assignAuthor("Gaurav");
		test.assignCategory(this.getClass().getSimpleName());
	}

	@AfterSuite
	public void tearDownSuite() {
		extent.flush();
		extent.close();
	}

	public WebDriver getWebDriver() {
		return driver;
	}

	/**
	 * Capturing screenshot once script is failed
	 */
	public void captureScreenshot(ITestResult result) {
		try {
			String screenshotName = getFileName(result.getName());
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String path = getPath();
			String screen = path + "/screenshots/" + screenshotName + ".png";
			File screenshotLocation = new File(screen);
			FileUtils.copyFile(screenshot, screenshotLocation);
			Thread.sleep(2000);
			InputStream is = new FileInputStream(screenshotLocation);
			byte[] imageBytes = IOUtils.toByteArray(is);
			Thread.sleep(2000);
			String base64 = Base64.getEncoder().encodeToString(imageBytes);
			test.log(LogStatus.FAIL, result.getThrowable() + " \n Snapshot below: " + test.addBase64ScreenShot("data:image/png;base64," + base64));
			Reporter.log("<a href= '" + screen + "'target='_blank' ><img src='" + screen + "'>" + screenshotName + "</a>");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}

	public static String getFileName(String file) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		Calendar cal = Calendar.getInstance();
		String fileName = file + dateFormat.format(cal.getTime());
		return fileName;
	}

	/**
	 * Get absolute path
	 */
	public static String getPath() {
		String path = "";
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		path = absolutePathOfFirstFile.replaceAll("\\\\+", "/");
		return path;
	}

	/**
	 * Report logs
	 *
	 * @param :
	 *            message
	 */
	public void reportLog(String message) {
		test.log(LogStatus.PASS, message);
		message = BREAK_LINE + message;
		logger.info("Message: " + message);
		Reporter.log(message);
	}
}
