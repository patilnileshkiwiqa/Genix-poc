package com.genix.init;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.genix.Login.LoginIndexPage;
import com.genix.Login.LoginVerification;
import com.genix.reports.ExtentLogger;
import com.genix.reports.ExtentManager;
import com.genix.utility.TestData;

public class SeleniumInit {
	public String suiteName = "";
	public String testName = "";
	public static String PayertestURL = "";
	public static String testUrl;
	public static String seleniumHub; // Selenium hub IP
	public static String seleniumHubPort; // Selenium hub port
	protected String targetBrowser; // Target browser
	protected static String test_data_folder_path = null;
	public static String currentWindowHandle = "";// Get Current Window handle
	public static String browserName = "Chrome";
	public static String osName = "";
	public String HomeDir = "";
	protected String testDataFolderPath;
	protected String screenshotFolderPath = null;
	public static String browserVersion = "";
	public static String browseruse = "";
	public static String Url = "";
	public static String AuthorName;
	public static String ModuleName;
	public static String Version = "";
	/* public static String header=""; */
	public static int col = 0;

	protected static String screenshot_folder_path = null;
	public static String currentTest; // current running test
	protected static Logger logger = Logger.getLogger("testing");
	protected static WebDriver driver;
	protected static String configFile = "config.properties";
	// Object Creation
	protected LoginIndexPage loginIndexPage;
	protected LoginVerification loginVerification;


	 @Parameters({ "browser", "url" })
	@BeforeSuite(alwaysRun = true)
	public void fetchSuite(@Optional String browser,@Optional String url,ITestContext testContext) throws IOException {
		try {
			TestData.deleteDirectory(new File(System.getProperty("user.dir") + "\\test-output\\screenshots"));
			TestData.deleteDirectory(new File(System.getProperty("user.dir") + "\\images"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String execution=testContext.getCurrentXmlTest().getParameter("ExecutionByXML");
		 
		 if(execution.equalsIgnoreCase("true"))
			{
				testUrl=testContext.getCurrentXmlTest().getParameter("URL");
				browserName =testContext.getCurrentXmlTest().getParameter("Browser");
			}else
			{
//				testUrl=TestData.getValueFromConfig("config.properties","URL");
//				browserName =TestData.getValueFromConfig("config.properties","Browser");
				
				browserName=browser;
				testUrl=url;
			}
		 
//		testUrl = TestData.getValueFromConfig(configFile, "URL");
		final String SCREENSHOT_FOLDER_NAME = "screenshots";
		final String TESTDATA_FOLDER_NAME = "test_data";
		testDataFolderPath = new File(TESTDATA_FOLDER_NAME).getAbsolutePath();
		screenshotFolderPath = new File(SCREENSHOT_FOLDER_NAME).getAbsolutePath();
		String scrFolder = "images/img_" + Common.getCurrentTimeStampString();
		new File(scrFolder).mkdir();
		System.setProperty("scr.folder", scrFolder);
		try {
			final String downloadDir = "DownloadData";
			final String headless = "Headless";

			if (StringUtils.containsIgnoreCase(browserName, "Chrome")) {
				System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
				final ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("--start-maximized");
				chromeOptions.addArguments("--disable-popup-blocking");
				if (StringUtils.containsIgnoreCase(browserName, headless)) {
					chromeOptions.addArguments(headless);
					chromeOptions.addArguments("window-size=1920,1080");
				}
				chromeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
				chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
				Map<String, Object> prefs = new HashMap<>();
				prefs.put("download.default_directory", new File(downloadDir).getAbsolutePath());
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				chromeOptions.setExperimentalOption("prefs", prefs);
				driver = new ChromeDriver(chromeOptions);
			}
//			Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
//			browserVersion = cap.getBrowserVersion();
			// suiteName = testContext.getSuite().getName();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.get(testUrl);
			/* Fix for script to run on Chrome 98 */
			String originalHandle = driver.getWindowHandle();
			for (String handle : driver.getWindowHandles()) {
				if (!handle.equals(originalHandle)) {
					driver.switchTo().window(handle);
					driver.close();
				}
			}
			driver.switchTo().window(originalHandle);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeTest(alwaysRun = true)
	public void fetchSuiteConfiguration(ITestContext testContext) throws IOException {
		/*
		 * seleniumHub = testContext.getCurrentXmlTest().getParameter("selenium.host");
		 * seleniumHubPort =
		 * testContext.getCurrentXmlTest().getParameter("selenium.port");
		 */
		// testUrl=TestData.getValueFromConfig("config.properties","URL");
	}

	/**
	 * WebDriver initialization
	 * 
	 * @return WebDriver object
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method, ITestContext testContext, ITestResult testResult)
			throws IOException, InterruptedException {

		driver.get(testUrl);

		suiteName = testContext.getSuite().getName();

		// Login
		loginIndexPage = new LoginIndexPage(driver);
		loginVerification = new LoginVerification(driver);

	}

	/**
	 * After Method
	 * 
	 * @param testResult
	 */
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult testResult, ITestContext testContext) {
		String screenshotName = "";
		testName = testContext.getName();
		try {
			Reporter.setCurrentTestResult(testResult);
			if (!testResult.isSuccess()) {
				System.out.println();
				System.out.println("TEST FAILED - " + testName);
				System.out.println();
				System.out.println("ERROR MESSAGE: " + testResult.getThrowable());
				System.out.println("\n");
				if (testResult.getStatus() == ITestResult.FAILURE) {
					System.out.println("1 message from tear down");
					screenshotName = "finalScreenshot_" + Common.getCurrentTimeStampString();
					Common.makeScreenshot(driver, screenshotName);
					slog("<Strong><b>" + testName + " is failed.</b></font></strong>");
				}
			} else if (testResult.getStatus() == ITestResult.SUCCESS) {
				System.out.println("1 message from tear down");
				slog("<Strong><b>" + testName + " is passed.</b></font></strong>");
			}
//			driver.manage().deleteAllCookies();
//			driver.close();
//			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Log given message to Reporter output.
	 * 
	 * @param msg Message/Log to be reported.
	 */
	@AfterSuite(alwaysRun = true)
	public void postConfigue() {
		driver.manage().deleteAllCookies();
		driver.close();
	}

	protected void log(String msg) {
		ExtentLogger.log(msg);
		slog(msg);
	}

	protected static void slog(String msg) {
		Reporter.log(msg + "</br>");
		System.out.println(msg);
	}

	protected void logStatus(ITestStatus testStatus, String msg) {
		switch (testStatus) {
		case PASSED:
			ExtentLogger.pass(msg);
			slog(msg + " <Strong><font color=#32cd32><b> &#10004 Pass</b></font></strong>");
			Common.captureScreenshot(driver);
			break;
		case FAILED:
			String screenshotName = Common.getCurrentTimeStampString();
			ExtentLogger.fail(msg);
			ExtentManager.getExtentTest().fail(
					MediaEntityBuilder.createScreenCaptureFromBase64String(Common.getBase64Image(driver)).build());
			slog(msg + " <Strong><font color=#dc3545><b> &#10008 Fail</b></font></strong>");
			Common.makeScreenshot(driver, screenshotName);
			break;
		case SKIPPED:
			log(msg);
			break;
		default:
			break;
		}
	}
}