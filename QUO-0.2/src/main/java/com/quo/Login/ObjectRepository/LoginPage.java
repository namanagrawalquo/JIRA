package com.quo.Login.ObjectRepository;

import org.apache.log4j.Logger;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.quo.elements.Buttons;
import com.quo.elements.Hyperlinks;
import com.quo.elements.Labels;
import com.quo.elements.TextBoxes;

public class LoginPage {
	
	Logger logger = Logger.getLogger("devpinoyLogger");
	WebDriver wdr;
	
	@FindBy(id = Buttons.googleloginButtonId)
	public WebElement googleloginButton;
	
	@FindBy(id = TextBoxes.emailTextboxId)
	public WebElement emailTextbox;
	
	@FindBy(xpath = TextBoxes.passwordTextboxXpath)
	public WebElement passwordTextbox;
	
	@FindBy(id = Hyperlinks.dashboardLogoId)
	public WebElement dashboardLogo;
	
	@FindBy(xpath = Hyperlinks.reportsLinkXpath)
	public WebElement reportsLink;

	@FindBy(xpath = Hyperlinks.sprintReportLinkXpath)
	public WebElement sprintReportLink;
	
	@FindBy(css = Labels.sprintReportHeaderCSS)
	public WebElement sprintReportHeader;
	
	public LoginPage(WebDriver any_wdr) 
	{
		wdr = any_wdr;
		PageFactory.initElements(any_wdr, this);
	}
	
	public void clickOnGoogleSignIn()
	{
		try
		{
			if(isElementVisible(googleloginButton))
			{
				googleloginButton.click();
				logger.info("Click on Google Sign-In button");
			}
			else
			{
				throw new ElementNotVisibleException("Google Sign-In button is not found");
			}
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
		}
	}
	
	public void enterEmail(String email)
	{
		try
		{
			if(isElementVisible(emailTextbox))
			{
				emailTextbox.sendKeys(email, Keys.ENTER);
				logger.info("Enter email: "+email);
			}
			else
			{
				throw new ElementNotVisibleException("Email textbox is not found");
			}
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
		}
	}
	
	public void enterPassword(String password)
	{
		try
		{
			if(isElementVisible(passwordTextbox))
			{
				passwordTextbox.sendKeys(password, Keys.ENTER);
				logger.info("Enter password: **********");
			}
			else
			{
				throw new ElementNotVisibleException("Password textbox is not found");
			}
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
		}
	}
	
	public boolean isDashboardLogoDisplayed()
	{
		try
		{
			return isElementVisible(dashboardLogo);
		}
		catch(Exception ex)
		{
			logger.info("Exception Occurred: "+ex);
			return false;
		}
	}
	
	public boolean isElementVisible(WebElement element)
	{
		boolean isVisible = false;
		try
		{
			WebDriverWait wait = new WebDriverWait(wdr, 20);
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
	
	