package com.hp.opr.qa.framework.pageobjects.bvd;

import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;


public class HelpPage {

    final WebDriver driver;
    final WebDriverWait wait;

    // BVD Menu Color Your Data
    @FindBy(how = How.XPATH, using = "//a[text()='Color Your Data']")
    static WebElement a_ColorYourData;

    // BVD Header Color Your Data
    @FindBy(how = How.XPATH, using = "//h1[text()='Color Your Data']")
    static WebElement h1_ColorYourData;

    @FindBy(how = How.XPATH, using = "//a[text()='Get Started']")
    static WebElement a_GetStarted;

    @FindBy(how = How.XPATH, using = "//h1[text()='Get Started']")
    static WebElement h1_GetStarted;

    @FindBy(how = How.XPATH, using = "//a[text()='OMi Integration']")
    static WebElement a_OMiIntegration;

    @FindBy(how = How.XPATH, using = "//h1[text()='OMi Integration']")
    static WebElement h1_OMiIntegration;

    @FindBy(how = How.XPATH, using = "//a[text()='Forward OMi Event Status']")
    static WebElement a_ForwardOMiEventStatus;

    @FindBy(how = How.XPATH, using = "//h1[text()='Forward OMi Event Status']")
    static WebElement h1_ForwardOMiEventStatus;

    @FindBy(how = How.XPATH, using = "//a[text()='Forward OMi KPI Status']")
    static WebElement a_ForwardOMiKPIStatus;

    @FindBy(how = How.XPATH, using = "//h1[text()='Forward OMi KPI Status']")
    static WebElement h1_ForwardOMiKPIStatus;

    @FindBy(how = How.XPATH, using = "//a[text()='Administration']")
    static WebElement a_Administration;

    @FindBy(how = How.XPATH, using = "//h1[text()='Administration']")
    static WebElement h1_Administration;

    @FindBy(how = How.XPATH, using = "//a[text()='System Settings']")
    static WebElement a_SystemSettings;

    @FindBy(how = How.XPATH, using = "//h1[text()='System Settings']")
    static WebElement h1_SystemSettings;

    @FindBy(how = How.XPATH, using = "//a[text()='Manage Dashboards']")
    static WebElement a_ManageDashboards;

    @FindBy(how = How.XPATH, using = "//h1[text()='Manage Dashboard']")
    static WebElement h1_ManageDashboards;

    @FindBy(how = How.XPATH, using = "//a[text()='User Management']")
    static WebElement a_UserManagement;

    @FindBy(how = How.XPATH, using = "//h1[text()='User Management']")
    static WebElement h1_UserManagement;

    @FindBy(how = How.XPATH, using = "//a[text()='Knowledge Base']")
    static WebElement a_KnowledgeBase;

    @FindBy(how = How.XPATH, using = "//h1[text()='Knowledge Base']")
    static WebElement h1_KnowledgeBase;

    @FindBy(how = How.XPATH, using = "//a[text()='Widgets']")
    static WebElement a_Widgets;

    @FindBy(how = How.XPATH, using = "//h1[text()='Widgets']")
    static WebElement h1_Widgets;

    @FindBy(how = How.XPATH, using = "//a[text()='Area and Multiple Area Chart Widgets']")
    static WebElement a_AreaAndMultipleAreaChartWidgets;

    @FindBy(how = How.XPATH, using = "//h1[text()='Area and Multiple Area Chart Widgets']")
    static WebElement h1_AreaAndMultipleAreaChartWidgets;

    @FindBy(how = How.XPATH, using = "//a[text()='Bar Charts Widgets']")
    static WebElement a_BarChartWidgets;

    @FindBy(how = How.XPATH, using = "//h1[text()='Bar Charts Widgets']")
    static WebElement h1_BarChartWidgets;

    @FindBy(how = How.XPATH, using = "//a[text()='Donut Charts Widgets']")
    static WebElement a_DonutChartWidgets;

    @FindBy(how = How.XPATH, using = "//h1[text()='Donut Charts Widgets']")
    static WebElement h1_DonutChartWidgets;

    @FindBy(how = How.XPATH, using = "//a[text()='Feed Widgets']")
    static WebElement a_FeedWidgets;

    @FindBy(how = How.XPATH, using = "//h1[text()='Feed Widgets']")
    static WebElement h1_FeedWidgets;

    @FindBy(how = How.XPATH, using = "//a[text()='Frame Widgets']")
    static WebElement a_FrameWidgets;

    @FindBy(how = How.XPATH, using = "//h1[text()='Frame Widgets']")
    static WebElement h1_FrameWidgets;

    @FindBy(how = How.XPATH, using = "//a[text()='Line Chart and Sparkline Widgets']")
    static WebElement a_LineChartAndSparklineWidgets;

    @FindBy(how = How.XPATH, using = "//h1[text()='Line Chart and Sparkline Widgets']")
    static WebElement h1_LineChartAndSparklineWidgets;

    @FindBy(how = How.XPATH, using = "//a[text()='Status Color Group']")
    static WebElement a_StatusColorGroup;

    @FindBy(how = How.XPATH, using = "//h1[text()='Status Color Group']")
    static WebElement h1_StatusColorGroup;

    @FindBy(how = How.XPATH, using = "//a[text()='Status Image Widgets']")
    static WebElement a_StatusImageWidget;

    @FindBy(how = How.XPATH, using = "//h1[text()='Status Image Widgets']")
    static WebElement h1_StatusImageWidget;

    @FindBy(how = How.XPATH, using = "//a[text()='Status Visible Group']")
    static WebElement a_StatusVisibleGroup;

    @FindBy(how = How.XPATH, using = "//h1[text()='Status Visible Group']")
    static WebElement h1_StatusVisibleGroup;

    @FindBy(how = How.XPATH, using = "//a[text()='Text Value Widgets']")
    static WebElement a_TextValueWidgets;

    @FindBy(how = How.XPATH, using = "//h1[text()='Text Value Widgets']")
    static WebElement h1_TextValueWidgets;

    @FindBy(how = How.XPATH, using = "//a[text()='Web Page Widgets']")
    static WebElement a_WebPageWidgets;

    @FindBy(how = How.XPATH, using = "//h1[text()='Web Page Widgets']")
    static WebElement h1_WebPageWidgets;

    @FindBy(how = How.XPATH, using = "//a[text()='Widget Properties']")
    static WebElement a_WidgetProperties;

    @FindBy(how = How.XPATH, using = "//h1[text()='Widget Properties']")
    static WebElement h1_WidgetProperties;

    @FindBy(how = How.XPATH, using = "//a[text()='Chart Auto Scale']")
    static WebElement a_ChartAutoScale;

    @FindBy(how = How.XPATH, using = "//h1[text()='Chart Auto Scale']")
    static WebElement h1_ChartAutoScale;

    @FindBy(how = How.XPATH, using = "//a[text()='Chart Colors']")
    static WebElement a_ChartColors;

    @FindBy(how = How.XPATH, using = "//h1[text()='Chart Colors']")
    static WebElement h1_ChartColors;

    @FindBy(how = How.XPATH, using = "//a[text()='Chart Period']")
    static WebElement a_ChartPeriod;

    @FindBy(how = How.XPATH, using = "//h1[text()='Chart Period']")
    static WebElement h1_ChartPeriod;

    @FindBy(how = How.XPATH, using = "//a[text()='Coloring Rule']")
    static WebElement a_ColoringRule;

    @FindBy(how = How.XPATH, using = "//h1[text()='Coloring Rule']")
    static WebElement h1_ColoringRule;

    @FindBy(how = How.XPATH, using = "//a[text()='Data Channel']")
    static WebElement a_DataChannel;

    @FindBy(how = How.XPATH, using = "//h1[text()='Data Channel']")
    static WebElement h1_DataChannel;

    @FindBy(how = How.XPATH, using = "//a[text()='Data Field']")
    static WebElement a_DataField;

    @FindBy(how = How.XPATH, using = "//h1[text()='Data Field']")
    static WebElement h1_DataField;

    @FindBy(how = How.XPATH, using = "//a[text()='Default Value']")
    static WebElement a_DefaultValue;

    @FindBy(how = How.XPATH, using = "//h1[text()='Default Value']")
    static WebElement h1_DefaultValue;

    @FindBy(how = How.XPATH, using = "//a[text()='Feed Max Items']")
    static WebElement a_FeedMaxItems;

    @FindBy(how = How.XPATH, using = "//h1[text()='Feed Max Items']")
    static WebElement h1_FeedMaxItems;

    @FindBy(how = How.XPATH, using = "//a[text()='Hyperlink']")
    static WebElement a_Hyperlink;

    @FindBy(how = How.XPATH, using = "//h1[text()='Hyperlink']")
    static WebElement h1_Hyperlink;

    @FindBy(how = How.XPATH, using = "//a[text()='Image Selection Rule']")
    static WebElement a_ImageSelectionRule;

    @FindBy(how = How.XPATH, using = "//h1[text()='Image Selection Rule']")
    static WebElement h1_ImageSelectionRule;

    @FindBy(how = How.XPATH, using = "//a[text()='Max Value']")
    static WebElement a_MaxValue;

    @FindBy(how = How.XPATH, using = "//h1[text()='Max Value']")
    static WebElement h1_MaxValue;

    @FindBy(how = How.XPATH, using = "//a[text()='Min Value']")
    static WebElement a_MinValue;

    @FindBy(how = How.XPATH, using = "//h1[text()='Min Value']")
    static WebElement h1_MinValue;

    @FindBy(how = How.XPATH, using = "//a[text()='Mouse Over']")
    static WebElement a_MouseOver;

    @FindBy(how = How.XPATH, using = "//h1[text()='Mouse Over']")
    static WebElement h1_MouseOver;

    @FindBy(how = How.XPATH, using = "//a[text()='Show Chart Numbers']")
    static WebElement a_ShowChartNumbers;

    @FindBy(how = How.XPATH, using = "//h1[text()='Show Chart Numbers']")
    static WebElement h1_ShowChartNumbers;

    @FindBy(how = How.XPATH, using = "//a[text()='Status Field']")
    static WebElement a_StatusField;

    @FindBy(how = How.XPATH, using = "//h1[text()='Status Field']")
    static WebElement h1_StatusField;

    @FindBy(how = How.XPATH, using = "//a[text()='Text Format']")
    static WebElement a_TextFormat;

    @FindBy(how = How.XPATH, using = "//h1[text()='Text Format']")
    static WebElement h1_TextFormat;

    @FindBy(how = How.XPATH, using = "//a[text()='URL']")
    static WebElement a_URL;

    @FindBy(how = How.XPATH, using = "//h1[text()='URL']")
    static WebElement h1_URL;

    @FindBy(how = How.XPATH, using = "//a[text()='Visibility Rule']")
    static WebElement a_VisibilityRule;

    @FindBy(how = How.XPATH, using = "//h1[text()='Visibility Rule']")
    static WebElement h1_VisibilityRule;

    @FindBy(how = How.XPATH, using = "//a[text()='HTTP Post Format']")
    static WebElement a_HttpPostFormat;

    @FindBy(how = How.XPATH, using = "//h1[text()='HTTP Post Format']")
    static WebElement h1_HttpPostFormat;

    @FindBy(how = How.XPATH, using = "//a[text()='Example: Connecting JSON Data to Widgets']")
    static WebElement a_ExampleConnectingJsonDataToWidgets;

    @FindBy(how = How.XPATH, using = "//h1[text()='Example: Connecting JSON Data to Widgets']")
    static WebElement h1_ExampleConnectingJsonDataToWidgets;

    @FindBy(how = How.XPATH, using = "//a[text()='Tips, Tricks, and Troubleshooting']")
    static WebElement a_TipsTricksAndTroubleshooting;

    @FindBy(how = How.XPATH, using = "//h1[text()='Tips, Tricks, and Troubleshooting']")
    static WebElement h1_TipsTricksAndTroubleshooting;


    public HelpPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
    }


    // OPEN COLOR YOUR DATA
    public Map<String, String> openColorYourDataDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            wait.until(ExpectedConditions.elementToBeClickable(a_ColorYourData));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_ColorYourData);
            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_ColorYourData));
            result = ResultStore.success("Color Your Data Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Color Your Data Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN GET STARTED
    public Map<String, String> openGetStartedDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_ColorYourData));
            actions.moveToElement(a_ColorYourData).moveToElement(a_GetStarted).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_GetStarted);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_GetStarted));
            result = ResultStore.success("Get Started Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Get Started Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN OMi INTEGRATION
    public Map<String, String> openOMiIntegrationDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_ColorYourData));
            actions.moveToElement(a_ColorYourData).moveToElement(a_OMiIntegration).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_OMiIntegration);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_OMiIntegration));
            result = ResultStore.success("OMi Integration Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open OMi Integration Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN FORWARD OMi EVENT STATUS
    public Map<String, String> openForwardOMiEventStatusDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_ColorYourData));
            actions.moveToElement(a_ColorYourData).moveToElement(a_ForwardOMiEventStatus).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_ForwardOMiEventStatus);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_ForwardOMiEventStatus));
            result = ResultStore.success("Forward OMi Event Status Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Forward OMi Event Status Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN FORWARD OMi KPI STATUS
    public Map<String, String> openForwardOMiKPIStatusDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_ColorYourData));
            actions.moveToElement(a_ColorYourData).moveToElement(a_ForwardOMiKPIStatus).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_ForwardOMiKPIStatus);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_ForwardOMiKPIStatus));
            result = ResultStore.success("Forward OMi KPI Status Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Forward OMi KPI Status Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN ADMINISTRATION
    public Map<String, String> openAdministrationDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            wait.until(ExpectedConditions.elementToBeClickable(a_Administration));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_Administration);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_Administration));
            result = ResultStore.success("Administration Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Administration Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN SYSTEM SETTINGS
    public Map<String, String> openSystemSettingsDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_Administration));
            actions.moveToElement(a_Administration).moveToElement(a_SystemSettings).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_SystemSettings);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_SystemSettings));
            result = ResultStore.success("System Settings Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open System Settings Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN MANAGE DASHBOARDS
    public Map<String, String> openManageDashboardsDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_Administration));
            actions.moveToElement(a_Administration).moveToElement(a_ManageDashboards).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_ManageDashboards);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_ManageDashboards));
            result = ResultStore.success("Manage Dashboards Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Manage Dashboards Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN USER MANAGEMENT
    public Map<String, String> openUserManagementDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_Administration));
            actions.moveToElement(a_Administration).moveToElement(a_UserManagement).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_UserManagement);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_UserManagement));
            result = ResultStore.success("User Management Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open User Management Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN KNOWLEDGE BASE
    public Map<String, String> openKnowledgeBaseDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_KnowledgeBase);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_KnowledgeBase));
            result = ResultStore.success("Knowledge Base Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Knowledge Base Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN WIDGETS
    public Map<String, String> openWidgetsDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_Widgets).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_Widgets);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_Widgets));
            result = ResultStore.success("Widgets Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Widgets Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN AREA AND MULTI AREA CHART WIDGETS
    public Map<String, String> openAreaAndMultipleAreaChartWidgetsDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_Widgets).moveToElement(a_AreaAndMultipleAreaChartWidgets).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_AreaAndMultipleAreaChartWidgets);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_AreaAndMultipleAreaChartWidgets));
            result = ResultStore.success("Area and Multiple Area Chart Widgets Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Area and Multiple Area Chart Widgets Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN BAR CHART WIDGETS
    public Map<String, String> openBarChartWidgetsDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_Widgets).moveToElement(a_BarChartWidgets).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_BarChartWidgets);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_BarChartWidgets));
            result = ResultStore.success("Bar Chart Widgets Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Bar Chart Widgets Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN DONUT CHART WIDGETS
    public Map<String, String> openDonutChartWidgetsDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_Widgets).moveToElement(a_DonutChartWidgets).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_DonutChartWidgets);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_DonutChartWidgets));
            result = ResultStore.success("Donut Chart Widgets Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Donut Chart Widgets Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN FEED WIDGETS
    public Map<String, String> openFeedWidgetsDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_Widgets).moveToElement(a_FeedWidgets).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_FeedWidgets);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_FeedWidgets));
            result = ResultStore.success("Feed Widgets Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Feed Widgets Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN FRAME WIDGETS
    public Map<String, String> openFrameWidgetsDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_Widgets).moveToElement(a_FrameWidgets).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_FrameWidgets);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_FrameWidgets));
            result = ResultStore.success("Frame Widgets Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Frame Widgets Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN LINE CHART AND SPARKLINE WIDGETS
    public Map<String, String> openLineChartAndSparklineWidgetsDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_Widgets).moveToElement(a_LineChartAndSparklineWidgets).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_LineChartAndSparklineWidgets);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_LineChartAndSparklineWidgets));
            result = ResultStore.success("Line and Sparkline Widgets Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Line and Sparkline Widgets Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN STATUS COLOR GROUP
    public Map<String, String> openStatusColorGroupDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_Widgets).moveToElement(a_StatusColorGroup).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_StatusColorGroup);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_StatusColorGroup));
            result = ResultStore.success("Status Color Group Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Status Color Group Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN STATUS IMAGE WIDGET
    public Map<String, String> openStatusImageWidgetDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_Widgets).moveToElement(a_StatusImageWidget).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_StatusImageWidget);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_StatusImageWidget));
            result = ResultStore.success("Status Image Widget Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Status Image Widget Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN STATUS VISIBLE GROUP DOCU
    public Map<String, String> openStatusVisibleGroupDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_Widgets).moveToElement(a_StatusVisibleGroup).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_StatusVisibleGroup);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_StatusVisibleGroup));
            result = ResultStore.success("Status Visible Group Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Status Visible Group Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN TEXT VALUE WIDGETS DOCU
    public Map<String, String> openTextValueWidgetsDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_Widgets).moveToElement(a_TextValueWidgets).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_TextValueWidgets);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_TextValueWidgets));
            result = ResultStore.success("Text Value Widgets Help successfully opened");
            } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Text Value Widgets Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN WEB PAGE WIDGETS DOCU
    public Map<String, String> openWebPageWidgetsDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_Widgets).moveToElement(a_WebPageWidgets).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_WebPageWidgets);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_WebPageWidgets));
            result = ResultStore.success("Web Page Widgets Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Web Page Widgets Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN WIDGET POPERTIES
    public Map<String, String> openWidgetPropertiesDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_WidgetProperties).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_WidgetProperties);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_WidgetProperties));
            result = ResultStore.success("Widget Properties Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Widget Properties Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN CHART AUTO SCALE DOCU
    public Map<String, String> openChartAutoScaleDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_WidgetProperties).moveToElement(a_ChartAutoScale).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_ChartAutoScale);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_ChartAutoScale));
            result = ResultStore.success("Chart Auto Scale Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Chart Auto Scale Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN CHART COLORS DOCU
    public Map<String, String> openChartColorsDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_WidgetProperties).moveToElement(a_ChartColors).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_ChartColors);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_ChartColors));
            result = ResultStore.success("Chart Colors Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Chart Colors Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN CHART PERIOD DOCU
    public Map<String, String> openChartPeriodDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_WidgetProperties).moveToElement(a_ChartPeriod).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_ChartPeriod);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_ChartPeriod));
            result = ResultStore.success("Chart Period Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Chart Period Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN COLORING RULE DOCU
    public Map<String, String> openColoringRuleDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_WidgetProperties).moveToElement(a_ColoringRule).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_ColoringRule);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_ColoringRule));
            result = ResultStore.success("Coloring Rule Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Coloring Rule Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN DATA CHANNEL DOCU
    public Map<String, String> openDataChannelDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_WidgetProperties).moveToElement(a_DataChannel).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_DataChannel);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_DataChannel));
            result = ResultStore.success("Data Channel Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Data Channel Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN DATA FIELD DOCU
    public Map<String, String> openDataFieldDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_WidgetProperties).moveToElement(a_DataField).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_DataField);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_DataField));
            result = ResultStore.success("Data Field Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Data Field Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN DEFAULT VALUE DOCU
    public Map<String, String> openDefaultValueDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_WidgetProperties).moveToElement(a_DefaultValue).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_DefaultValue);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_DefaultValue));
            result = ResultStore.success("Default Value Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Default Value Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN FEED MAX ITEMS DOCU
    public Map<String, String> openFeedMaxItemsDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_WidgetProperties).moveToElement(a_FeedMaxItems).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_FeedMaxItems);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_FeedMaxItems));
            result = ResultStore.success("Feed Max Items Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Feed Max Items Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN HYPERLINK DOCU
    public Map<String, String> openHyperlinkDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_WidgetProperties).moveToElement(a_Hyperlink).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_Hyperlink);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_Hyperlink));
            result = ResultStore.success("Hyperlink Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Hyperlink Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN IMAGE SELECTION RULE HELP
    public Map<String, String> openImageSelectionRuleDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_WidgetProperties).moveToElement(a_ImageSelectionRule).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_ImageSelectionRule);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_ImageSelectionRule));
            result = ResultStore.success("Image Selection Rule Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Image Selection Rule Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN MAX VALUE HELP
    public Map<String, String> openMaxValueDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_WidgetProperties).moveToElement(a_MaxValue).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_MaxValue);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_MaxValue));
            result = ResultStore.success("Max Value Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Max Value Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN MIN VALUE HELP
    public Map<String, String> openMinValueDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_WidgetProperties).moveToElement(a_MinValue).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_MinValue);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_MinValue));
            result = ResultStore.success("Min Value Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Min Value Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN MOUSE OVER HELP
    public Map<String, String> openMouseOverDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_WidgetProperties).moveToElement(a_MouseOver).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_MouseOver);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_MouseOver));
            result = ResultStore.success("Mouse Over Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Mouse Over Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN SHOW CHART NUMBERS HELP
    public Map<String, String> openShowChartNumbersDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_WidgetProperties).moveToElement(a_ShowChartNumbers).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_ShowChartNumbers);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_ShowChartNumbers));
            result = ResultStore.success("Show Chart Numbers Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Show Chart Numbers Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN HTTP POST FORMAT HELP
    public Map<String, String> openHttpPostFormatDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_HttpPostFormat).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_HttpPostFormat);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_HttpPostFormat));
            result = ResultStore.success("Http Post Format Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Http Post Format Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN EXAMPLE: CONNECTING JSON DATA TO WIDGETS HELP
    public Map<String, String> openExampleConnectingJsonDataToWidgetsDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_ExampleConnectingJsonDataToWidgets).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_ExampleConnectingJsonDataToWidgets);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_ExampleConnectingJsonDataToWidgets));
            result = ResultStore.success("Example Connecting JSON Data To Widgets Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Example Connecting JSON Data To Widgetst Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    // OPEN TIPS TRICKS AND TROUBLESHOOTING HELP
    public Map<String, String> openTipsTricksAndTroubleshootingDocu() {
        Map<String, String> result = ResultStore.getResultObj();

        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(a_KnowledgeBase));
            actions.moveToElement(a_KnowledgeBase).moveToElement(a_TipsTricksAndTroubleshooting).build().perform();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", a_TipsTricksAndTroubleshooting);

            // wait until header is there
            wait.until(ExpectedConditions.visibilityOf(h1_TipsTricksAndTroubleshooting));
            result = ResultStore.success("Tips Tricks And Troubleshooting Help successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Tips Tricks And Troubleshooting Help. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    public Map<String, String> checkDocu() {
        Map<String, String> result = ResultStore.getResultObj();
        // Docu Menu
        WebElement ul_NavigationClearfix = driver.findElement(By.xpath("//ul[@class='navigation clearfix']"));
        // Menu Entrie ( - Entry Points)
        List<WebElement> a_MenuEntries = ul_NavigationClearfix.findElements(By.xpath("./li/a[contains(@href,'')]"));
        //List<String> list = a_MenuEntries.stream().map(a -> a.getAttribute("text")).toArray();

        if (!a_MenuEntries.isEmpty()) {
            for (WebElement a_MenuEntry : a_MenuEntries) {
                WebElement a_MenuEntryParent = a_MenuEntry.findElement(By.xpath(".."));
                List<WebElement> a_SubMenuEntries = a_MenuEntryParent.findElements(By.xpath(".//a[contains(@href,'.htm')]"));
                a_SubMenuEntries.remove(0);
                if (!a_SubMenuEntries.isEmpty()) {
                    for (WebElement a_SubMenuEntry : a_SubMenuEntries) {
                        WebElement a_SubMenuEntryParent = a_SubMenuEntry.findElement(By.xpath(".."));
                        List<WebElement> a_SubSubMenuEntries = a_SubMenuEntryParent.findElements(By.xpath(".//a[contains(@href,'.htm')]"));
                        a_SubSubMenuEntries.remove(0);
                        if (!a_SubSubMenuEntries.isEmpty()) {
                            for (WebElement a_SubSubMenuEntry : a_SubSubMenuEntries) {
                                try {
                                    Actions actions = new Actions(driver);
                                    wait.until(ExpectedConditions.elementToBeClickable(a_MenuEntry));
                                    actions.moveToElement(a_MenuEntry).moveToElement(a_SubMenuEntry).moveToElement(a_SubSubMenuEntry).build().perform();

                                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                                    executor.executeScript("arguments[0].click();", a_SubSubMenuEntry);

                                    // wait until header is there
                                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='" + a_SubSubMenuEntry.getAttribute("text") + "']")));
                                    result = ResultStore.success(a_SubSubMenuEntry.getAttribute("text") + " Help successfully opened");
                                } catch (Exception exc) {
                                    result = ResultStore.failure("Failed to open " + a_SubSubMenuEntry.getAttribute("text") + " Help. error: " + Misc.getStacktraceAsString(exc));
                                }
                            }
                        }
                        try {
                            Actions actions = new Actions(driver);
                            wait.until(ExpectedConditions.elementToBeClickable(a_MenuEntry));
                            actions.moveToElement(a_MenuEntry).moveToElement(a_SubMenuEntry).build().perform();

                            JavascriptExecutor executor = (JavascriptExecutor) driver;
                            executor.executeScript("arguments[0].click();", a_SubMenuEntry);

                            // wait until header is there
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='" + a_SubMenuEntry.getAttribute("text") + "']")));
                            result = ResultStore.success(a_SubMenuEntry.getAttribute("text") + " Help successfully opened");
                        } catch (Exception exc) {
                            result = ResultStore.failure("Failed to open " + a_SubMenuEntry.getAttribute("text") + " Help. error: " + Misc.getStacktraceAsString(exc));
                        }
                    }

                }
                try {
                    Actions actions = new Actions(driver);
                    wait.until(ExpectedConditions.elementToBeClickable(a_MenuEntry));
                    actions.moveToElement(a_MenuEntry).build().perform();

                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", a_MenuEntry);

                    // wait until header is there
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='" + a_MenuEntry.getAttribute("text") + "']")));
                    result = ResultStore.success(a_MenuEntry.getAttribute("text") + " Help successfully opened");
                } catch (Exception exc) {
                    result = ResultStore.failure("Failed to open " + a_MenuEntry.getAttribute("text") + " Help. error: " + Misc.getStacktraceAsString(exc));
                }
            }
        } else {
            result = ResultStore.failure("Failed to find Menu Entries.");
        }
        return result;
    }

    public Map<String, String> checkDocu2() {

        List<WebElement> a_SubMenuEntries;
        Actions aAction = new Actions(driver);
        Map<String, String> result = ResultStore.getResultObj();

        // Entry Point
        WebElement div_NavigationWrapper = driver.findElement(By.xpath("//div[@class='navigation-wrapper']"));
        a_SubMenuEntries = div_NavigationWrapper.findElements(By.xpath("./ul/li/a[contains(@href,'')]"));

        if (!a_SubMenuEntries.isEmpty()) {
            for (WebElement a_SubMenuEntry : a_SubMenuEntries) {
                String sMenuEntry = a_SubMenuEntry.getAttribute("text");
                result = checkDocuHelp2(aAction, sMenuEntry);
            }
        }

        return result;
    }

    private Map<String, String> checkDocuHelp2(Actions aAction, String sMenuEntry) {

        List<WebElement> a_SubMenuEntries;
        Map<String, String> result = ResultStore.getResultObj();
        WebElement a_MenuEntry = driver.findElement(By.xpath("//a[text()='" + sMenuEntry + "']"));
        // Sub Menu Entries
        a_SubMenuEntries = a_MenuEntry.findElements(By.xpath("../ul/li/a[contains(@href,'')]"));

        if (!a_SubMenuEntries.isEmpty()) {
            for (WebElement a_SubMenuEntry : a_SubMenuEntries) {
                String sSubMenuEntry = a_SubMenuEntry.getAttribute("text");
                wait.until(ExpectedConditions.elementToBeClickable(a_MenuEntry));
                aAction = aAction.moveToElement(driver.findElement((By.xpath("//a[text()='" + sMenuEntry + "']"))));
                result = checkDocuHelp2(aAction, sSubMenuEntry);
            }
        } else {
            try {
                //wait.until(ExpectedConditions.elementToBeClickable(a_MenuEntry));
                aAction.moveToElement(a_MenuEntry).click().perform();//.build().perform();

                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", a_MenuEntry);

                // wait until header is there
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='" + sMenuEntry + "']")));
                result = ResultStore.success(sMenuEntry + " Help successfully opened");
            } catch (Exception exc) {
                result = ResultStore.failure("Failed to open " + sMenuEntry + " Help. error: " + Misc.getStacktraceAsString(exc));
            }
        }
        return result;
    }

    public Object[][] getMenuStructure() {

        List<WebElement> a_MenuEntries;
        Object[][] oMenuStructure = null;

        // Entry Point
        WebElement div_NavigationWrapper = driver.findElement(By.xpath("//div[@class='navigation-wrapper']"));
        a_MenuEntries = div_NavigationWrapper.findElements(By.xpath("./ul/li/a[contains(@href,'')]"));

        if (!a_MenuEntries.isEmpty()) {
            for (WebElement a_MenuEntry : a_MenuEntries) {
                String sMenuEntry = a_MenuEntry.getAttribute("text");
                oMenuStructure = ArrayUtils.addAll(oMenuStructure, checkDocuHelp3(new String[]{sMenuEntry}, sMenuEntry));
            }
        }

        return oMenuStructure;
    }

    private Object[][] checkDocuHelp3(String[] sMenu, String sMenuEntry) {

        List<WebElement> a_SubMenuEntries;
        Object[][] result = null;
        WebElement a_MenuEntry = driver.findElement(By.xpath("//a[text()='" + sMenuEntry + "']"));
        // Sub Menu Entries
        a_SubMenuEntries = a_MenuEntry.findElements(By.xpath("../ul/li/a[contains(@href,'')]"));

        if (!a_SubMenuEntries.isEmpty()) {
            for (WebElement a_SubMenuEntry : a_SubMenuEntries) {
                String sSubMenuEntry = a_SubMenuEntry.getAttribute("text");
                result = ArrayUtils.addAll(result, checkDocuHelp3(ArrayUtils.add(sMenu, sSubMenuEntry), sSubMenuEntry));
            }
        } else {
            result = new Object[][]{sMenu};
        }
        return result;
    }

}