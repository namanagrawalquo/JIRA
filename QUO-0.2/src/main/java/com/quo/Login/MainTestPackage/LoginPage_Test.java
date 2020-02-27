package com.quo.Login.MainTestPackage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.BeforeClass;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.testng.Assert;

import com.quo.utility.ReadXLS;
import com.quo.Login.ObjectRepository.LoginPage;
import com.quo.core.CreateDriver;

public class LoginPage_Test extends CreateDriver {
	
	ResourceBundle config = ResourceBundle.getBundle("Config"); // To get Application URL and other settings.
	
	Logger logger = Logger.getLogger("devpinoyLogger"); // To generate the log file
	
	String rootDirectory = System.getProperty("user.dir");
	String configFilePath = rootDirectory + config.getString("configDataFilePath");
	
	ReadXLS xlsOperations = new ReadXLS();
	
	LoginPage loginPage;
	
	@BeforeClass
	public void beforeTest()
	{
		driver.get(xlsOperations.readConfigData(configFilePath, 0, 0, 1));
	}
	
	@Test (priority = 1, groups = {"prerequisites"})
	public void Check_Login_Is_Successful()
	{
		try
		{
			logger.info("Test Case Started: Check_Login_Is_Successful");
			SoftAssert softAssertion = new SoftAssert();
			loginPage = new LoginPage(driver);
			
			loginPage.clickOnGoogleSignIn();
			loginPage.enterEmail(xlsOperations.readConfigData(configFilePath,0,2,1));
			loginPage.enterPassword(xlsOperations.readConfigData(configFilePath,0,3,1));
			
			softAssertion.assertEquals(loginPage.isDashboardLogoDisplayed(), true, "Dashboard logo is not visible.");
			softAssertion.assertAll();
			logger.info("Test Case Completed: Check_Login_Is_Successful");
		}
		catch(Exception ex)
		{
			logger.error("Exception Occurred: ", ex);
			Assert.fail("Exception Occurred: " +ex);
		}
	}
}

