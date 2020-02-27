package com.quo.elements;

public class Hyperlinks {
	
	//Dashboard Page
	public static final String dashboardLogoId = "productLogoGlobalItem";
	
	//Home Page
	public static final String reportsLinkXpath = "//div[text()='Reports']";
	
	//All Reports
	public static final String sprintReportLinkXpath = "//a[text()='Sprint Report']";
	
	//Sprint Reports
	public static final String sprintReportIssueCompletedNavigatorXpath = "//*[text()='Completed Issues']//parent::div//following::div[1]/a";
	public static final String sprintReportIssueNotCompletedNavigatorXpath = "//*[text()='Issues Not Completed']//parent::div//following::div[1]/a";
	public static final String sprintReportIssueCompletedOutsideNavigatorXpath = "//*[text()='Issues completed outside of this sprint']//parent::div//following::div[1]/a";
	public static final String sprintReportIssueRemovedNavigatorXpath = "//*[text()='Issues Removed From Sprint']//parent::div//following::div[1]/a";
}
