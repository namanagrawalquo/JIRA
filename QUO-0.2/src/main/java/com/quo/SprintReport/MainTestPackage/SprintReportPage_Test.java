package com.quo.SprintReport.MainTestPackage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.testng.Assert;

import com.quo.utility.Email;
import com.quo.utility.ReadXLS;
import com.quo.SprintReport.ObjectRepository.SprintReportPage;
import com.quo.core.CreateDriver;

public class SprintReportPage_Test extends CreateDriver {
	
	ResourceBundle config = ResourceBundle.getBundle("Config"); // To get Application URL and other settings.
	
	Logger logger = Logger.getLogger("devpinoyLogger"); // To generate the log file
	
	String rootDirectory = System.getProperty("user.dir");
	String configFilePath = rootDirectory + config.getString("configDataFilePath");
	String sprintReportFilePath = rootDirectory + config.getString("sprintReportFolderPath");
	
	boolean completedIssuesDisplayed;
	boolean issueNotCompletedDisplayed;
	boolean issueCompletedOutsideDisplayed;
	boolean issueRemovedDisplayed;
	
	String createdWorkbookPath;
	
	ReadXLS xlsOperations = new ReadXLS();
	Email email = new Email();
	
	SprintReportPage sprintReportPage;
	
	@Test (priority = 2, dependsOnGroups = {"prerequisites"})
	public void Navigate_To_Sprint_Report()
	{
		try
		{
			logger.info("Test Case Started: Navigate_To_Sprint_Report");
			driver.navigate().to(xlsOperations.readConfigData(configFilePath, 0, 0, 1) + "browse/"
					+ xlsOperations.readConfigData(configFilePath, 0, 1, 1));
			SoftAssert softAssertion = new SoftAssert();
			sprintReportPage = new SprintReportPage(driver);
			
			String navigationPanel = sprintReportPage.getNavigationButtonClass();
			if (navigationPanel.equals("false"))
			{
				sprintReportPage.openNavigationPanel();
			}
			
			sprintReportPage.clickOnReports();
			sprintReportPage.clickOnSprintReport();
			
			softAssertion.assertEquals(sprintReportPage.getSprintReportHeaderText(), "Sprint Report", "Header text is not \"Sprint Report\"");
			softAssertion.assertAll();
			logger.info("Test Case Completed: Navigate_To_Sprint_Report");
		}
		catch(Exception ex)
		{
			logger.error("Exception Occurred: ", ex);
			Assert.fail("Exception Occurred: " +ex);
		}	
	}
	
	@Test (priority = 3, dependsOnMethods = {"Navigate_To_Sprint_Report"})
	public void Select_Sprint_In_Drop_Down()
	{
		try
		{
			logger.info("Test Case Started: Select_Sprint_In_Drop_Down");
			SoftAssert softAssertion = new SoftAssert();
			sprintReportPage = new SprintReportPage(driver);
			
			String sprintNumber = xlsOperations.readConfigData(configFilePath, 0, 4, 1);
			String selectedSprintNumber = sprintReportPage.getCurrentSprintNumber();
			//String firstSplit = selectedSprintNumber.substring(0,selectedSprintNumber.lastIndexOf(" ") + 1).trim();
			// String secondSplit = selectedSprintNumber.substring(selectedSprintNumber.lastIndexOf(" ") + 1).trim();
			
			String landingSprintReportURL = driver.getCurrentUrl();
			
			if(!selectedSprintNumber.equals(sprintNumber))
			{
				sprintReportPage.clickOnSprintDropdown();
				// sprintReportPage.selectSprintInDropdown(firstSplit + " " + sprintNumber);
				sprintReportPage.selectSprintInDropdown(sprintNumber);
				String currentSprintReportURL = driver.getCurrentUrl();
				softAssertion.assertNotEquals(currentSprintReportURL, landingSprintReportURL, "Sprint report URL is not updated.");
			}
			else
			{
				String actualSprintDropdownText = selectedSprintNumber;
				// String expectedSprintDropdownText = firstSplit + " " + sprintNumber;
				String expectedSprintDropdownText = sprintNumber;
				softAssertion.assertEquals(actualSprintDropdownText, expectedSprintDropdownText, "Sprint report dropdown text do not match with the provided value.");
			}
			
			softAssertion.assertAll();
			logger.info("Test Case Completed: Select_Sprint_In_Drop_Down");
		}
		catch(Exception ex)
		{
			logger.error("Exception Occurred: ", ex);
			Assert.fail("Exception Occurred: " +ex);
		}	
	}
	
	@Test (priority = 4, dependsOnMethods = {"Select_Sprint_In_Drop_Down"})
	public void Generate_XLSX_Template_For_Sprint_Report()
	{
		try
		{
			logger.info("Test Case Started: Generate_XLSX_Template_For_Sprint_Report");
			SoftAssert softAssertion = new SoftAssert();
			sprintReportPage = new SprintReportPage(driver);
					
			completedIssuesDisplayed = sprintReportPage.completedIssuesAvailable();
			issueNotCompletedDisplayed = sprintReportPage.issueNotCompletedAvailable();
			issueCompletedOutsideDisplayed = sprintReportPage.issueCompletedOutsideAvailable();
			issueRemovedDisplayed = sprintReportPage.issueRemovedAvailable();
			
			if (!completedIssuesDisplayed && !issueNotCompletedDisplayed && !issueCompletedOutsideDisplayed && !issueRemovedDisplayed)
			{
				softAssertion.fail("No block is available on the page");
			}
			else
			{
				createdWorkbookPath = xlsOperations.createWorkbook(sprintReportFilePath, completedIssuesDisplayed, issueNotCompletedDisplayed,
						issueCompletedOutsideDisplayed, issueRemovedDisplayed);
				softAssertion.assertNotEquals(createdWorkbookPath, "Not Found", "Workbook is not created.");
			}
			softAssertion.assertAll();
			logger.info("Test Case Completed: Generate_XLSX_Template_For_Sprint_Report");
		}
		catch(Exception ex)
		{
			logger.error("Exception Occurred: ", ex);
			Assert.fail("Exception Occurred: " +ex);
		}
	}
	
	@Test (priority = 5, dependsOnMethods = {"Generate_XLSX_Template_For_Sprint_Report"})
	public void Get_Data_For_Available_Block()
	{
		try
		{
			logger.info("Test Case Started: Get_Data_For_Available_Block");
			SoftAssert softAssertion = new SoftAssert();
			sprintReportPage = new SprintReportPage(driver);
			
			if(completedIssuesDisplayed)
			{
				sprintReportPage.clickOnIssueNavigatorForCompletedIssue();
				
				int countOfIssueTableHeader = sprintReportPage.getNumberOfColumnOnIssueNavigator();
				int countOfIssueTableRow = sprintReportPage.getNumberOfRowOnIssueNavigator();
				int columnCountInConfigFile = xlsOperations.getNumberOfRows(configFilePath);
				
				if(countOfIssueTableHeader > 0 && countOfIssueTableRow > 0)
				{
					int columnValue = 0;
					for(int i = 0; i < columnCountInConfigFile; i++)
					{
						String columnTextToBeFind = xlsOperations.readConfigData(configFilePath, 1, i, 0);
						for(int j = 1; j <= countOfIssueTableHeader; j++)
						{
							String headerText = sprintReportPage.getTableHeaderText(j);
							if(headerText.equalsIgnoreCase(columnTextToBeFind))
							{
								xlsOperations.writeSprintReportData(createdWorkbookPath, "Completed Issues", 0, columnValue, headerText);
								for(int k = 1; k <= countOfIssueTableRow; k++)
								{
									String rowText = sprintReportPage.getTableRowText(k, j);
									xlsOperations.writeSprintReportData(createdWorkbookPath, "Completed Issues", k, columnValue, rowText);
								}
								columnValue++;
							}
						}
					}
					
				}
				else
				{
					softAssertion.fail("Table header count is "+countOfIssueTableHeader+" and row count is "+countOfIssueTableRow+" which indicates an error");
				}
				sprintReportPage.closeCompletedIssueWindow();
			}
			
			if(issueNotCompletedDisplayed)
			{
				sprintReportPage.clickOnIssueNotCompletedNavigator();
				
				int countOfIssueTableHeader = sprintReportPage.getNumberOfColumnOnIssueNavigator();
				int countOfIssueTableRow = sprintReportPage.getNumberOfRowOnIssueNavigator();
				int columnCountInConfigFile = xlsOperations.getNumberOfRows(configFilePath);
				
				if(countOfIssueTableHeader > 0 && countOfIssueTableRow > 0)
				{
					int columnValue = 0;
					for(int i = 0; i < columnCountInConfigFile; i++)
					{
						String columnTextToBeFind = xlsOperations.readConfigData(configFilePath, 1, i, 0);
						for(int j = 1; j <= countOfIssueTableHeader; j++)
						{
							String headerText = sprintReportPage.getTableHeaderText(j);
							if(headerText.equalsIgnoreCase(columnTextToBeFind))
							{
								xlsOperations.writeSprintReportData(createdWorkbookPath, "Issues Not Completed", 0, columnValue, headerText);
								for(int k = 1; k <= countOfIssueTableRow; k++)
								{
									String rowText = sprintReportPage.getTableRowText(k, j);
									xlsOperations.writeSprintReportData(createdWorkbookPath, "Issues Not Completed", k, columnValue, rowText);
								}
								columnValue++;
							}
						}
					}
					
				}
				else
				{
					softAssertion.fail("Table header count is "+countOfIssueTableHeader+" and row count is "+countOfIssueTableRow+" which indicates an error");
				}
				
				sprintReportPage.closeIssueNotCompletedWindow();
			}
			
			if(issueCompletedOutsideDisplayed)
			{
				sprintReportPage.clickOnIssueCompletedOutsideNavigator();
				
				int countOfIssueTableHeader = sprintReportPage.getNumberOfColumnOnIssueNavigator();
				int countOfIssueTableRow = sprintReportPage.getNumberOfRowOnIssueNavigator();
				int columnCountInConfigFile = xlsOperations.getNumberOfRows(configFilePath);
				
				if(countOfIssueTableHeader > 0 && countOfIssueTableRow > 0)
				{
					int columnValue = 0;
					for(int i = 0; i < columnCountInConfigFile; i++)
					{
						String columnTextToBeFind = xlsOperations.readConfigData(configFilePath, 1, i, 0);
						for(int j = 1; j <= countOfIssueTableHeader; j++)
						{
							String headerText = sprintReportPage.getTableHeaderText(j);
							if(headerText.equalsIgnoreCase(columnTextToBeFind))
							{
								xlsOperations.writeSprintReportData(createdWorkbookPath, "Issues completed outside", 0, columnValue, headerText);
								for(int k = 1; k <= countOfIssueTableRow; k++)
								{
									String rowText = sprintReportPage.getTableRowText(k, j);
									xlsOperations.writeSprintReportData(createdWorkbookPath, "Issues completed outside", k, columnValue, rowText);
								}
								columnValue++;
							}
						}
					}
					
				}
				else
				{
					softAssertion.fail("Table header count is "+countOfIssueTableHeader+" and row count is "+countOfIssueTableRow+" which indicates an error");
				}
				
				sprintReportPage.closeIssueCompletedOutsideWindow();
			}
			
			if(issueRemovedDisplayed)
			{
				sprintReportPage.clickOnIssueRemovedNavigator();
				
				int countOfIssueTableHeader = sprintReportPage.getNumberOfColumnOnIssueNavigator();
				int countOfIssueTableRow = sprintReportPage.getNumberOfRowOnIssueNavigator();
				int columnCountInConfigFile = xlsOperations.getNumberOfRows(configFilePath);
				
				if(countOfIssueTableHeader > 0 && countOfIssueTableRow > 0)
				{
					int columnValue = 0;
					for(int i = 0; i < columnCountInConfigFile; i++)
					{
						String columnTextToBeFind = xlsOperations.readConfigData(configFilePath, 1, i, 0);
						for(int j = 1; j <= countOfIssueTableHeader; j++)
						{
							String headerText = sprintReportPage.getTableHeaderText(j);
							if(headerText.equalsIgnoreCase(columnTextToBeFind))
							{
								xlsOperations.writeSprintReportData(createdWorkbookPath, "Issues Removed From Sprint", 0, columnValue, headerText);
								for(int k = 1; k <= countOfIssueTableRow; k++)
								{
									String rowText = sprintReportPage.getTableRowText(k, j);
									xlsOperations.writeSprintReportData(createdWorkbookPath, "Issues Removed From Sprint", k, columnValue, rowText);
								}
								columnValue++;
							}
						}
					}
					
				}
				else
				{
					softAssertion.fail("Table header count is "+countOfIssueTableHeader+" and row count is "+countOfIssueTableRow+" which indicates an error");
				}
				
				sprintReportPage.closeIssueRemovedWindow();
			}
			softAssertion.assertAll();
			logger.info("Test Case Completed: Get_Data_For_Available_Block");
			
		}
		catch(Exception ex)
		{
			logger.error("Exception Occurred: ", ex);
			Assert.fail("Exception Occurred: " +ex);
		}
	}
	
	@Test (priority = 6, dependsOnMethods = {"Get_Data_For_Available_Block"})
	public void Send_Report_To_Email()
	{
		try
		{
			SoftAssert softAssertion = new SoftAssert();
			
			String emailTo = xlsOperations.readConfigData(configFilePath, 0, 5, 1);
			String emailFrom = xlsOperations.readConfigData(configFilePath, 0, 6, 1);
			String emailFromPassword = xlsOperations.readConfigData(configFilePath, 0, 7, 1);
			boolean emailSentStatus = email.sendSprintReport(createdWorkbookPath, emailTo, emailFrom, emailFromPassword);
			
			softAssertion.assertEquals(emailSentStatus, true, "Email is not sent");
			softAssertion.assertAll();
		}
		catch(Exception ex)
		{
			logger.error("Exception Occurred: ", ex);
			Assert.fail("Exception Occurred: " +ex);
		}
		
	}
}

