package com.quo.SprintReport.ObjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.quo.elements.Div;
import com.quo.elements.Dropdowns;
import com.quo.elements.Hyperlinks;
import com.quo.elements.Labels;
import com.quo.elements.TextBoxes;

public class SprintReportPage {
	
	Logger logger = Logger.getLogger("devpinoyLogger");
	ResourceBundle dynamicElements = ResourceBundle.getBundle("dynamicElements");
	WebDriver wdr;
	
	ArrayList<String> completedIssueWindow;
	ArrayList<String> issueNotCompletedWindow;
	ArrayList<String> issueCompletedOutsideWindow;
	ArrayList<String> issueRemovedWindow;
	
	@FindBy(xpath = Div.navigationResizeButtonXpath)
	public WebElement navigationResizeButton;
	
	@FindBy(xpath = Div.navigationPanelXpath)
	public WebElement navigationPanel;
	
	@FindBy(xpath = Hyperlinks.reportsLinkXpath)
	public WebElement reportsLink;

	@FindBy(xpath = Hyperlinks.sprintReportLinkXpath)
	public WebElement sprintReportLink;
	
	@FindBy(css = Labels.sprintReportHeaderCSS)
	public WebElement sprintReportHeader;
	
	@FindBy(id = Dropdowns.sprintDropdownId)
	public WebElement sprintDropdown;
	
	@FindBy(id = Dropdowns.sprintDropdownSelectId)
	public WebElement sprintDropdownSelect;
	
	@FindBy(xpath = TextBoxes.sprintDropDownInputXpath)
	public WebElement sprintDropDownInput;
	
	@FindBy(xpath = Labels.sprintReportIssueCompletedXpath)
	public WebElement sprintReportIssueCompleted;
	
	@FindBy(xpath = Labels.sprintReportIssueNotCompletedXpath)
	public WebElement sprintReportIssueNotCompleted;
	
	@FindBy(xpath = Labels.sprintReportIssueCompletedOutsideXpath)
	public WebElement sprintReportIssueCompletedOutside;
	
	@FindBy(xpath = Labels.sprintReportIssueRemovedXpath)
	public WebElement sprintReportIssueRemoved;
	
	@FindBy(xpath = Hyperlinks.sprintReportIssueCompletedNavigatorXpath)
	public WebElement sprintReportIssueCompletedNavigator;
	
	@FindBy(xpath = Hyperlinks.sprintReportIssueNotCompletedNavigatorXpath)
	public WebElement sprintReportIssueNotCompletedNavigator;
	
	@FindBy(xpath = Hyperlinks.sprintReportIssueCompletedOutsideNavigatorXpath)
	public WebElement sprintReportIssueCompletedOutsideNavigator;
	
	@FindBy(xpath = Hyperlinks.sprintReportIssueRemovedNavigatorXpath)
	public WebElement sprintReportIssueRemovedNavigator;
	
	@FindBy(id = Div.issueTableId)
	public WebElement issueTable;
	
	@FindBy(xpath = Div.headerCountXpath)
	public List<WebElement> tableHeaderCount;
	
	@FindBy(xpath = Div.rowCountXpath)
	public List<WebElement> tableRowCount;
	
	public SprintReportPage(WebDriver any_wdr) 
	{
		wdr = any_wdr;
		PageFactory.initElements(any_wdr, this);
	}
	
	public String getNavigationButtonClass()
	{
		String classText = "";
		try
		{
			classText = navigationResizeButton.getAttribute("aria-expanded");
			logger.info("Navigation panel is displayed: "+classText);
			return classText;
		}
		catch(Exception ex)
		{
			// logger.info("Exception Occurred: "+ex);
			return classText;
		}
	}
	
	public void openNavigationPanel()
	{
		try
		{
			if(isElementVisible(navigationResizeButton))
			{
				navigationResizeButton.click();
				logger.info("Open left navigation panel.");
			}
			else
			{
				throw new ElementNotVisibleException("Navigation button is not found");
			}
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
		}
	}
	
	public void clickOnReports()
	{
		try
		{
			if(isElementVisible(reportsLink))
			{
				reportsLink.click();
				logger.info("Click on Reports link in left navigation.");
			}
			else
			{
				throw new ElementNotVisibleException("Reports link is not found");
			}
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
		}
	}
	
	public void clickOnSprintReport()
	{
		try
		{
			if(isElementVisible(sprintReportLink))
			{
				sprintReportLink.click();
				logger.info("Click on Sprint Report.");
			}
			else
			{
				throw new ElementNotVisibleException("Reports link is not found");
			}
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
		}
	}
	
	public String getSprintReportHeaderText()
	{
		String headerText = "";
		try
		{
			if(isElementVisible(sprintReportHeader))
			{
				headerText = sprintReportHeader.getText();
				logger.info("Sprint header text is: "+headerText);
				return headerText;
			}
			else
			{
				throw new ElementNotVisibleException("Sprint header text is not found");
			}
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
			return headerText;
		}
	}
	
	public String getCurrentSprintNumber()
	{
		String sprintNumber = "";
		try
		{
			if(isElementVisible(sprintDropdown))
			{
				sprintNumber = sprintDropdown.getText();
				logger.info("Current sprint dropdown text is: "+sprintNumber);
				return sprintNumber;
			}
			else
			{
				throw new ElementNotVisibleException("Sprint dropdown is not found");
			}
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
			return sprintNumber;
		}
	}
	
	public void clickOnSprintDropdown()
	{
		try
		{
			if(isElementVisible(sprintDropdown))
			{
				sprintDropdown.click();
				logger.info("Click on Sprint Dropdown.");
			}
			else
			{
				throw new ElementNotVisibleException("Sprint dropdown is not found");
			}
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
		}
	}
	
	public void selectSprintInDropdown(String selectText)
	{
		try
		{
			if(isElementVisible(sprintDropDownInput))
			{
				sprintDropDownInput.sendKeys(selectText, Keys.ENTER);
				logger.info("Selected Sprint in dropdown: "+selectText);
			}
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
		}
	}
	
	public boolean completedIssuesAvailable()
	{
		boolean isVisible = false;
		try
		{
			isVisible = isElementVisible(sprintReportIssueCompleted);
			logger.info("Completed Issue block available: "+isVisible);
			return isVisible;
		}
		catch(Exception ex)
		{
			logger.info("Completed Issue block available: "+isVisible);
			return isVisible;
		}
	}
	
	public boolean issueNotCompletedAvailable()
	{
		boolean isVisible = false;
		try
		{
			isVisible = isElementVisible(sprintReportIssueNotCompleted);
			logger.info("Issue Not Completed block available: "+isVisible);
			return isVisible;
		}
		catch(Exception ex)
		{
			logger.info("Issue Not Completed block available: "+isVisible);
			return isVisible;
		}
	}
	
	public boolean issueCompletedOutsideAvailable()
	{
		boolean isVisible = false;
		try
		{
			
			isVisible = isElementVisible(sprintReportIssueCompletedOutside);
			logger.info("Issue Completed Outside block available: "+isVisible);
			return isVisible;
		}
		catch(Exception ex)
		{
			logger.info("Issue Completed Outside block available: "+isVisible);
			return isVisible;
		}
	}
	
	public boolean issueRemovedAvailable()
	{
		boolean isVisible = false;
		try
		{
			isVisible = isElementVisible(sprintReportIssueRemoved);
			logger.info("Issue Removed block available: "+isVisible);
			return isVisible;
		}
		catch(Exception ex)
		{
			logger.info("Issue Removed block available: "+isVisible);
			return isVisible;
		}
	}
	
	public void clickOnIssueNavigatorForCompletedIssue()
	{
		try
		{
			if(isElementVisible(sprintReportIssueCompletedNavigator))
			{
				sprintReportIssueCompletedNavigator.sendKeys(Keys.CONTROL, Keys.RETURN);

				completedIssueWindow = new ArrayList<String>(wdr.getWindowHandles());
			    wdr.switchTo().window(completedIssueWindow.get(1));
			    logger.info("Click on Issue Navigator for Completed Issue.");
			}
			else
			{
				throw new ElementNotVisibleException("Issue Navigator for Completed Issue link is not found");
			}
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
		}
	}
	
	public void closeCompletedIssueWindow()
	{
		try
		{
			wdr.close();
			wdr.switchTo().window(completedIssueWindow.get(0));
			logger.info("Closed Completed Issue Window.");
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
		}
	}
	
	public void clickOnIssueNotCompletedNavigator()
	{
		try
		{
			if(isElementVisible(sprintReportIssueNotCompletedNavigator))
			{
				sprintReportIssueNotCompletedNavigator.sendKeys(Keys.CONTROL, Keys.RETURN);
				
				issueNotCompletedWindow = new ArrayList<String>(wdr.getWindowHandles());
			    wdr.switchTo().window(issueNotCompletedWindow.get(1));
				logger.info("Click on Navigator for Issue Not Completed.");
			}
			else
			{
				throw new ElementNotVisibleException("Issue Navigator for Completed Issue link is not found");
			}
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
		}
	}
	
	public void closeIssueNotCompletedWindow()
	{
		try
		{
			wdr.close();
			wdr.switchTo().window(issueNotCompletedWindow.get(0));
			logger.info("Closed Issue not Completed Window.");
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
		}
	}
	
	public void clickOnIssueCompletedOutsideNavigator()
	{
		try
		{
			if(isElementVisible(sprintReportIssueCompletedOutsideNavigator))
			{
				sprintReportIssueCompletedOutsideNavigator.sendKeys(Keys.CONTROL, Keys.RETURN);
				
				issueCompletedOutsideWindow = new ArrayList<String>(wdr.getWindowHandles());
			    wdr.switchTo().window(issueCompletedOutsideWindow.get(1));
				logger.info("Click on Navigator for Issue Completed Outside.");
			}
			else
			{
				throw new ElementNotVisibleException("Issue Navigator for Completed Outside link is not found");
			}
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
		}
	}
	
	public void closeIssueCompletedOutsideWindow()
	{
		try
		{
			wdr.close();
			wdr.switchTo().window(issueCompletedOutsideWindow.get(0));
			logger.info("Closed Issue Completed Outside Window.");
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
		}
	}
	
	public void clickOnIssueRemovedNavigator()
	{
		try
		{
			if(isElementVisible(sprintReportIssueRemovedNavigator))
			{
				sprintReportIssueRemovedNavigator.sendKeys(Keys.CONTROL, Keys.RETURN);
				
				issueRemovedWindow = new ArrayList<String>(wdr.getWindowHandles());
			    wdr.switchTo().window(issueRemovedWindow.get(1));
				logger.info("Click on Navigator for Issue Removed.");
			}
			else
			{
				throw new ElementNotVisibleException("Issue Navigator for Issue Removed link is not found");
			}
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
		}
	}
	
	public void closeIssueRemovedWindow()
	{
		try
		{
			wdr.close();
			wdr.switchTo().window(issueRemovedWindow.get(0));
			logger.info("Closed Issue Removed Window.");
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
		}
	}
	
	public int getNumberOfColumnOnIssueNavigator()
	{
		int columnCount = 0;
		try
		{
			if (isElementVisible(issueTable))
			{
				columnCount = tableHeaderCount.size();
				logger.info("Sprint Report table header count: "+columnCount);
			}
			return columnCount;
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
			columnCount = -1;
			return columnCount;
		}
	}
	
	public int getNumberOfRowOnIssueNavigator()
	{
		int rowCount = 0;
		try
		{
			if (isElementVisible(issueTable))
			{
				rowCount = tableRowCount.size();
				logger.info("Sprint Report table row count: "+rowCount);
			}
			return rowCount;
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
			rowCount = -1;
			return rowCount;
		}
	}
	
	public String getTableHeaderText(int headerIndex)
	{
		String headerText = "";
		try
		{
			String index = Integer.toString(headerIndex);
			String xpathExpression = dynamicElements.getString("sprintReportTableHeaderTextXpath1") + index 
					+ dynamicElements.getString("sprintReportTableHeaderTextXpath2");
			if(wdr.findElement(By.xpath(xpathExpression)).isDisplayed())
			{
				headerText = wdr.findElement(By.xpath(xpathExpression)).getText();
				logger.info("Sprint Report table header text on position "+index+ " is: "+headerText);
			}
			return headerText;
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
			headerText = "Not Found";
			return headerText;
		}
	}
	
	public String getTableRowText(int rowIndex, int headerIndex)
	{
		String rowText = "";
		try
		{
			String rIndex = Integer.toString(rowIndex);
			String hIndex = Integer.toString(headerIndex);
			String xpathExpression = dynamicElements.getString("sprintReportRowTextXpath1") + rIndex 
					+ dynamicElements.getString("sprintReportRowTextXpath2")+ hIndex + dynamicElements.getString("sprintReportRowTextXpath3");
			if(wdr.findElement(By.xpath(xpathExpression)).isDisplayed())
			{
				rowText = wdr.findElement(By.xpath(xpathExpression)).getText();
				logger.info("Sprint Report row "+rIndex+ " header "+hIndex+" position text is: "+rowText);
			}
			return rowText;
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
			rowText = "Not Found";
			return rowText;
		}
	}
	
	public boolean isElementVisible(WebElement element)
	{
		boolean isVisible = false;
		try
		{
			WebDriverWait wait = new WebDriverWait(wdr, 10);
			wait.until(ExpectedConditions.visibilityOf(element));
			if(element.isDisplayed())
			{
				isVisible = true;
			}
			return isVisible;
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
			return isVisible;
		}
	}
}