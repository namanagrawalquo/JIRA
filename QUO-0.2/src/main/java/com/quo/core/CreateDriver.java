package com.quo.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.quo.utility.Email;
import com.quo.utility.ExtentManager;

public class CreateDriver {
	
	public static WebDriver driver;
	public static ExtentReports extent;
	public static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	public ExtentHtmlReporter htmlReporter;

	ResourceBundle resourceBundle = ResourceBundle.getBundle("Config"); //To get element from Elements.properties
	Logger logger = Logger.getLogger("devpinoyLogger");//To perform logging operations

	Email email = new Email();

	String rootDirectory = System.getProperty("user.dir");
	String failureDirectory = rootDirectory + resourceBundle.getString("screenFailurePath");
	String successDirctory = rootDirectory + resourceBundle.getString("screenSuccessPath");
	String successBackupPath = rootDirectory + resourceBundle.getString("screenSuccessBackupPath");
	String failureBackupPath = rootDirectory + resourceBundle.getString("screenFailureBackupPath");
	String reportFilePath = rootDirectory + resourceBundle.getString("reportPath");
	String userProfile = System.getProperty("user.name");
	
	@BeforeSuite
	public void beforeSuite() 
	{
		try 
		{
			logger.info("Script is run by: " + userProfile);
			extent = ExtentManager.createInstance("extent.html");
			htmlReporter = new ExtentHtmlReporter("extent.html");
			extent.attachReporter(htmlReporter);
		} 
		catch (Exception ex) 
		{
			logger.info("Exception Occurred: " + ex);
		}
	}

	@BeforeClass
	public void beforeClass() 
	{
		try 
		{
			ExtentTest parent = extent.createTest(getClass().getName());
			parentTest.set(parent);
		} 
		catch (Exception ex)
		{
			logger.info("Exception Occurred: " + ex);
		}
	}

	@BeforeMethod
	public void beforeMethod(Method method) 
	{
		try 
		{
			ExtentTest child = (parentTest.get()).createNode(method.getName());
			test.set(child);
		}
		catch (Exception ex)
		{
			logger.info("Exception Occurred1: " + ex);
		}
	}

	@AfterMethod
	public void afterMethod(ITestResult result) 
	{
		try 
		{
			if (result.getStatus() == ITestResult.FAILURE) 
			{
				((ExtentTest) test.get()).fail(result.getThrowable());
				captureScreenShot(result, "fail");
			}
			else if (result.getStatus() == ITestResult.SKIP)
			{
				((ExtentTest) test.get()).skip(result.getThrowable());
			}
			else 
			{
				((ExtentTest) test.get()).pass("Test Passed");
				captureScreenShot(result, "pass");
			}
			extent.flush();
		} 
		catch (Exception ex) 
		{
			logger.info("Exception Occurred: " + ex);
		}
	}

	@Parameters({"browser"})
	@BeforeTest
	public void startTest(String browser) {
		try 
		{
			// Clean screenshot backup directory
			FileUtils.cleanDirectory(new File(successBackupPath));
			FileUtils.cleanDirectory(new File(failureBackupPath));
			
			// Move screenshots to backup folder
			FileUtils.copyDirectory(new File(successDirctory), new File(successBackupPath));
			logger.info("Moved the success screenshots to Backup Directory");
			FileUtils.copyDirectory(new File(failureDirectory), new File(failureBackupPath));
			logger.info("Moved the failure screenshots to Backup Directory");
			
			// Clean failure and success folder
			FileUtils.cleanDirectory(new File(failureDirectory));
			logger.info("Cleared the ScreenShots > Failure directory.");
			FileUtils.cleanDirectory(new File(successDirctory));
			logger.info("Cleared the ScreenShots > Success directory.");
			
			String filePath = rootDirectory + resourceBundle.getString("mainDriverPath");
			String chromeDriverPath = filePath + "\\chromedriver.exe";
			
			 if (browser.equalsIgnoreCase("chrome")) 
			{
				// Open Chrome browser
				System.setProperty("webdriver.chrome.driver", chromeDriverPath);
				
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				
				ChromeOptions options = new ChromeOptions();
				HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
				
				// options.addArguments("--test-type");
				// options.addArguments("--disable-extensions"); //to disable browser extension popup
				// options.addArguments("--disable-infobars"); // To disable info bar
				
				options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				options.setExperimentalOption("useAutomationExtension", false);
				options.setExperimentalOption("prefs", chromePrefs);
				
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				cap.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver(cap);
				driver.manage().window().maximize();
				logger.info("Chrome browser started: "+ driver.getWindowHandle());
				
			}
		} 
		catch (Exception ex) 
		{
			logger.info("Exception Occurred: " + ex);
		}
	}

	@AfterTest
	public void endTest() 
	{
		try 
		{
			driver.close();
			logger.info("Browser closed.");
			/*if (resourceBundle.getString("sendReport").equalsIgnoreCase("true")) 
			{
				email.sendEmail(reportFilePath);
			}*/
		} 
		catch (Exception ex) 
		{
			logger.info("Exception Occurred: " + ex);
		}
	}

	public void captureScreenShot(ITestResult result, String status) 
	{
		try {
			String destDir = "";
			String passfailMethod = result.getMethod().getRealClass().getSimpleName() + "."
					+ result.getMethod().getMethodName();
			
			// To capture screenshot.
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			DateFormat dateFormat = new SimpleDateFormat(resourceBundle.getString("dateFormatForScreenshot"));
			
			// If status = fail then set folder name "screenshots/Failures"
			if (status.equalsIgnoreCase("fail")) 
			{
				// destDir = "ScreenShots/Failures";
				destDir = failureDirectory;
			}
			// If status = pass then set folder name "screenshots/Success"
			else if (status.equalsIgnoreCase("pass")) 
			{
				destDir = successDirctory;
			}

			// To create folder to store screenshots
			new File(destDir).mkdirs();
			// Set file name with combination of test class name + date time.
			String destFile = passfailMethod + " - " + dateFormat.format(new Date()) 
				+ resourceBundle.getString("screenshotFileExtension");

			// Store file at destination folder location
			FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
