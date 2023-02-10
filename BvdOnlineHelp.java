package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.BvdOnlineHelpPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.utils.TestUtils;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by vmyasnikov on 5/24/2017.
 * Modified by Swamil on 1/29/2019 for BVD Online Help 2019.02
 * Modified by Swamil on 12/15/2020 for BVD Online Help 2020.01
 */
public class BvdOnlineHelp
{
  private LoginPage loginPage;
  private WebDriver driver;
  private BvdOnlineHelpPage onlineHelpPage;
  private Map<String, String> result;
  private MainPage mainPage;
  private String getStartedText = "Business Value Dashboard (BVD) provides a business";
  private String gettingstartedtext="This page guides you through the process of creating dashboard in BVD";
  private String releaseNotesText="This release of BVD introduces new features and enhancements, in addition to fixing issues.";
  private String accessLogsText = "You can find BVD log files in the directory";
  private String installText="You can install BVD as part of your suite deployment in container "+
          "based mode";
  private String upgradeText="You can migrate BVD databases as part of your suite upgrade in container based";
  private String loginToBvdText = "You can access BVD using a supported web browser from any " +
          "computer";
  private String  EnhancementsText= "";
  private String FixedIssuesText= "";
  private String microfocusSupportPageURL="https://softwaresupport.softwaregrp.com/";
  private String KnownIssuesText = "";
  private String DepreciationText = "A deprecated item comes under a component/feature/parameter for "+
          "which there will be no further enhancement";
  private String ReleaseLogText = "The Business Value Dashboard release log is a cumulative " +
          "list of features introduced in each release";
  private String translateDashboardText = "You can now translate the dashboard created in Visio using BVD Stencil";
  private String infoPortalPageTitle = "Deploy Stakeholder dashboards on premises";
  private String manageRolesDocTitle = "Manage roles";
  private String gitColourYourDataPageTitle="BVD Community";
  private String downsamplingofDataText="BVD provides downsampling of data input for";
  private String timezoneSettingsText ="This section gives you the steps to change your display name and timezone.";
  private String createFirstDashboardText = "Step 1: Before You Begin";
  private String administrationText = "This section describes the administrator tasks that you " +
          "can perform in BVD .";
  private String widgetsInUseSectionText = "BVD provides an extensive set of resources";
  private String UseWidgetsDashboardsText = "In BVD you can create and monitor dashboards";
  private String visioSupportText = "BVD provides the new and enhanced Visio stencil and guide template to design your BVD dashboards";
  private String screenReaderSupportText = "You can use BVD’s screen reader compatibility to read out the values";
  private String dashboardManagementText = "The Dashboards page lists all available dashboards, and enables you " +
          "to upload, delete, download, edit, or show or hide dashboards in the dashboards menu.";
  private String userManagmeentText = "BVD enables you to manage access by assigning permissions to roles inside BVD.";
  private String downloadToolsText = "BVD provides an extensive set of resources to help you develop dashboards and integrate data";
  private String Textside="";
  private String multicategoryWidgetText="Use the Multi Category Series charts to plot multiple sets of data over a selected category";
  private String dataIntegrationText = "BVD provides out-of-the-box integrations with software products such as Operations Bridge Manager ( OBM )";
  private String createReportsInBVDText="This topic provides information on creating your own reports using BVD";
  private String userRoleForBVDText= "Administrator has full access to administer the suite";
  private String reportingText= "Reports enable you to visualize data collected from various data sources";
  private String externalOracleText = "To migrate data:";
  private String externalPostgresText = "To migrate data:";
  private String postUpgradeText = "After the upgrade, set up a database connection for BVD  to ensure that the dashboard can display all the data elements";
  private String omiIntegrationText = "You can configure OBM to send Event Status, KPI Status and Performance Dashboard data to BVD";
  private String connectorIntegrationText = "Prerequisite";
  private String multiTimeSeriesWidgetText="This topic describes the Multi Time Series widget and the corresponding widget properties";
  private String customIntegrationsText = "BVD expects to receive your data as HTTP post requests in JavaScript Object Notation (JSON) format";
  private String sendingJSONtoBVDText="In this example, Data Center East sends two sets of JSON data to the BVD receiver";
  private String widgetsText = "BVD provides an extensive set of resources to help you develop dashboards and integrate data";
  private String tipsAndTricksText = "For BVD to be able to render the text as designed in Visio, " +
          "you must make the fonts used in Visio available to the web browser where you view the dashboards";
  private String localizedDashboardText="The dashboard designer uses the special Visio shapes to mark";
  private String parametrizedDashboardText="If a dashboard has channels with parameter queries";
  private String translatedDashboardText="You can now translate the dashboard created in Visio using BVD Stencil.";
  private String scalingdashboardText="Scaling feature allows you to view dashboard either in";
  private String sidebarScalingText="Fit-to-Window: Dashboard fits in to the screen";
  private String ehancedVisioSupportText="BVD provides the new and enhanced Visio stencil and guide template to design your BVD dashboards";
  private String glossaryText = "Bar charts are useful when you want to compare many values from the same data channel";
  private String StakeHolderDashboardText = "Monitoring Dashboards give you with an overview of the events from your monitored environment";
  private String  modifyVisioShapeText="Custom widgets require a Visio shape that represents their appearance in the dashboard";
  private String defineCustomWidgetCodeText="To code how the custom widget behaves, a Custom Widget API is provided";
  private String templateManagerText = "The Template Manager enables you to edit, download, and " +
          "delete dashboard templates.";
  private String Use_DashboardsText = "This section includes:";
  private String jsonText = "In this example, Data Center East sends two sets of JSON data to the" +
          " BVD receiver";
  private String commandLineText="The import and export command line enables you to import and export the dashboards";
  private String bvdMonitoringText="Prometheus is an open source, metrics-based monitoring system that records real-time metrics";
  private String areaText = "Use Area charts to display quantitative data. They're based on line charts.";
  private String shapesText="Visio stencil provided by BVD has the following shapes";
  private String blanksvgfilesText="When editing a dashboard in Visio, the dashboard is blank or shapes are missing after it is exported as SVG file.";
  private String dashboardloadingText="When sending a huge amount of data to a data channel in a high frequency,";
  private String UpgradeText="When you upgrade from 2019.08 to 2019.11, all BVD pods appear to be in Init state";
  private String acesslogsText="You can find BVD log files in the directory";
  private String verifymetricsForwardingText="Metrics Forwarding from Performance Graphing in OMi 9.2x and 10.0x:";
  private String testConnectionTLStext="The DB test connection for Vertica TLS fails with any one of the following error message";
  private String sidebarDateTypeusngTZText="Date type parameter is using UTC time zone instead of server time zone";
  private String sidebarBVDpodsWRONPASSText="Whenever the bvd-www, bvd-explore, or bvd-receiver PODs aren't running, the logfiles contain the following error";
  private String sidebarredBannerText="When viewing a dashboard,";
  private String sidebarConnTimeOutText="BVD reports failed to load with connection timeout error";
  private String notificationMessagesText="BVD displays notification messages if there are issues with the created dashboards";
  private String  exchangecertificateText="When you set up a Vertica database connection in the Data Collector,";
  private String  colorchangesText="Color changes happen based on the following:";
  private String barChartText = "Use the Bar Chart widget to display a bar chart. Bar charts are " +
          "useful when you want to";
  private String donutText = "Use the Donut/Pie widget to display a donut or pie chart. Each " +
          "slice in a ";
  private String feedText = "The Feed widget enables you to display information feeds, similar to RSS feeds";
  private String frameText = "BVD offers the following Frame shapes:";
  private String gaugeText = "Use the Gauge widget to display a gauge chart. By default, gauge " +
          "widgets";
  private String  LabelWidgetText="Use the Label widget to display the localized text "+
          "in the dashboards";
  private String lineChartText = "Use the Line Chart widget to show a line chart with axes and coordinates";
  private String statusColorText = "Group the  Status Color Group  widgets in Visio to make them change color";
  private String statusImageText = "Use the Status Image widget to display an image depending on the value received";
  private String statusVisibleText = "Group the invisible Status Visible Group Visio shape with other shapes";
  private String textValueText = "Use the Text Value widget to display values or to color text.";
  private String webPageText = "Web Page widgets enable you to show web pages in a dashboard. For" +
          " example";
  private String hyperlinkgroupwidgettext = "Group the Hyperlink Group shape together with any Visio shape";
  private String widgetgroupText="Use the Widget Group to display a dynamic list of individual widgets within one widget container";
  private String widgetpropertiesText="You can use widget properties to render the data as per your configuration";
  private String useText = "This section provides details about how to use BVD";
  private String troubleshootText = "This section provides the information to troubleshoot problems that you may encounter " +
          "while installing and using Business Value Dashboard";
  private String troubleshootDashboardsAndTemplatesText = "This section provides the following troubleshooting topics:";
  private String uploadingDashboardText = "On Internet Explorer 11 browser, the upload of SVG files locks the files in the file system";
  private String blankSVGFilesText = "When editing a dashboard in Visio, the dashboard is blank or shapes are missing " +
          "after it is exported as SVG file";
  private String dashboardLoadingCrashesText = "When sending a huge amount of data to a data channel in a high frequency";
  private String developText = "You can create custom widgets to visualize your data in ways";
  private String modifyVisioText="Custom widgets require a Visio shape that represents their appearance in the dashboard";
  private String defineCustomWidgetText = "To code how the custom widget behaves, a Custom Widget API is provided";
  private String modifySettingsText = "The System Settings side panel allows you to change the default timezone and configure aging";

  private String apiKeyText = "The API Key side panel displays your API key and enables you to generate a new one.";

  private String dataCollectorText = "You can create on-demand reports from a Vertica database by defining queries";
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
  private String BVD_DOCKER_SETUP;
  private String userName = "TesterOnlineHelp";
  private String userLogin;
  private String userPwd = "Test@123";
  private String roleName = "testnewOnlineHelp";
  private String desc = "For tester";
  private String category = "All";
  private String accessType = "full-control";
  String roleId;
  BvdWsClient client;
  private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");

  private void initPages()
  {
    loginPage = PageFactory.initElements(driver, LoginPage.class);
    mainPage = PageFactory.initElements(driver, MainPage.class);
    onlineHelpPage = PageFactory.initElements(driver, BvdOnlineHelpPage.class);
  }

  @BeforeClass
  public void setup()
  {
    cfg = new TestConfig().getConfig();
    BROWSER=cfg.getString("application.bvd.browser.name");
    BVD_USR = cfg.getString("application.bvd.user.adminusr");
    BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
    URL=cfg.getString("application.bvd.url.external");
    BVD_DOCKER_SETUP=cfg.getString("application.suite.installation.setup-type");
    driver = UiTestWebDriver.getWebDriver();
    driver.get(URL);
    initPages();
    try {
      client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test(priority = 1)
  public void createRole() {
    result = client.userWS.createRole(roleName, desc, category,accessType);
    Result.rcEquals(result, "0");
    roleId = result.get("out");
  }
  @Test(priority = 5)
  public void idmlogin() {
    result = loginPage.login(userName, userPwd);
    Result.rcEquals(result, "0");
  }
  @Test(priority = 10)
  public void login_check()
  {
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 15)
  public void checknonadminOptionsdropdown() {
    result = mainPage.adminoptionsfornonadmin(admindropdownoptions);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 20)
  public void openDocumentation()
  {
    result = onlineHelpPage.openHelpDropdownMenu();
    Result.rcEquals(result, "0");
    result = onlineHelpPage.openDocumentation();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 25)
  public void tabSwitch()
  {

    result = onlineHelpPage.switchToTab("Home");
    Result.rcEquals(result, "0");
    TestUtils.sleep(2);

  }

  @Test(priority = 35)
  public void Home()
  {
    result = onlineHelpPage.checkMainTabLinks(onlineHelpPage.homeTab,
            onlineHelpPage.homeReleaseNotes,
            releaseNotesText);
    Result.rcEquals(result, "0");

    result = onlineHelpPage.checkMainTabLinks(onlineHelpPage.homeTab,
            onlineHelpPage.homegetStarted,
            getStartedText);
    Result.rcEquals(result, "0");

    result = onlineHelpPage.checkMainTabLinks(onlineHelpPage.homeTab,
            onlineHelpPage.homeInstall,
            installText);
    Result.rcEquals(result, "0");

    result = onlineHelpPage.checkMainTabLinks(onlineHelpPage.homeTab,
            onlineHelpPage.homeUpgrade,
            upgradeText);
    Result.rcEquals(result, "0");

    result = onlineHelpPage.checkMainTabLinks(onlineHelpPage.homeTab,
            onlineHelpPage.homeIntegrate,
            dataIntegrationText);
    Result.rcEquals(result, "0");

    result = onlineHelpPage.checkMainTabLinks(onlineHelpPage.homeTab,
            onlineHelpPage.homeAdminister,
            administrationText);
    Result.rcEquals(result, "0");

    result = onlineHelpPage.checkMainTabLinks(onlineHelpPage.homeTab,
            onlineHelpPage.homeUse,
            useText);
    Result.rcEquals(result, "0");

    result = onlineHelpPage.checkMainTabLinks(onlineHelpPage.homeTab,
            onlineHelpPage.homeTroubleshoot,
            troubleshootText);
    Result.rcEquals(result, "0");

    result = onlineHelpPage.checkMainTabLinks(onlineHelpPage.homeTab,
            onlineHelpPage.homeDevelop,
            developText);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 50)
  public void ReleaseNotes()
  {

    result = onlineHelpPage.clickHorizontalDropdownMenuclick(onlineHelpPage.ReleaseNotes,
            releaseNotesText);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 200)
  public void openGetStartedSidebarCheckText()
  {
    result = onlineHelpPage.clickHorizontalDropdownMenuside(onlineHelpPage.getStartedDropdownside,
            onlineHelpPage.getStartedDropdownside,
            Textside);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 300)
  public void closeTab()
  {
    result = onlineHelpPage.closeActiveTab();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 400)
  public void openGettingStarted()
  {
    result = onlineHelpPage.openHelpDropdownMenu();
    Result.rcEquals(result, "0");
    result = onlineHelpPage.clickGettingStarted();
    Result.rcEquals(result, "0");
    result = onlineHelpPage.switchToTab("Create stakeholder dashboard in BVD");
    Result.rcEquals(result, "0");
    result = onlineHelpPage.openGettingStartedAndCheckText(gettingstartedtext);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 500)
  public void closeTabstarted()
  {
    result = onlineHelpPage.closeActiveTab();
    Result.rcEquals(result, "0");
  }


  @Test(priority = 600)
  public void testLogout() {
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }


  @Test(priority = 700)
  public void deleteRole()
  {
    result = client.userWS.deleteRole(roleId);
    Result.rcEquals(result,"0");
  }

  @AfterClass
  public void cleanup()
  {
    driver.quit();
  }

}
