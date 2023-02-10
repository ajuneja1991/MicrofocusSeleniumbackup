package com.hp.opr.qa.framework.pageobjects.bvd;
import com.google.common.base.Function;
import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.utils.TestUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.SECONDS;
/**
 * Created by vmyasnikov on 5/24/2017.
 */
public class BvdOnlineHelpPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(how = How.CSS, using = "[data-cy='help-button']")
    static WebElement menuHelpDropdown;

    // BVD Menu Dropdown Documentation
    @FindBy(how = How.CSS, using = "[data-cy='documentation']")
    static WebElement menuHelpDocumentation;

    // BVD Menu Dropdown Getting Started
    @FindBy(how = How.ID, using = "nav_get_started")
    static WebElement menuHelpGettingStarted;

    @FindBy(how = How.LINK_TEXT, linkText = "Getting started")
    public WebElement gettingStartedsidebar;

    @FindBy(how = How.LINK_TEXT, linkText = "Home")
    public WebElement homeTab;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/ReleaseSummary']")
    public WebElement homeReleaseNotes;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/BVDIntro']")
    public WebElement homegetStarted;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/Install']")
    public WebElement homeInstall;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/Update']")
    public WebElement homeUpgrade;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/IntegratingDataIntegr']")
    public WebElement homeIntegrate;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/AdminOverview']")
    public WebElement homeAdminister;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/KnowledgeBase']")
    public WebElement homeUse;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/Troubleshoot']")
    public WebElement homeTroubleshoot;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsStencilRefCustom']")
    public WebElement homeDevelop;

    @FindBy(how = How.XPATH, using = "//a[text()='Get started']/ancestor::div/div[@class='caret-icon']")
    public WebElement getStartedDropdownside;

    @FindBy(how = How.XPATH, using = "//a[text()='Reporting']/ancestor::div/div[@class='caret-icon']")
    public WebElement Reportingsidebarbtn;

    @FindBy(how = How.XPATH, using = "//a[text()='Stakeholder dashboard']")
    public WebElement StakeholderDashboardLink;

    @FindBy(how = How.XPATH, using = "//a[text()='Stakeholder dashboard']/ancestor::div/div[@class='caret-icon']")
    public WebElement StakeholderDashboardsidebarbtn;

    @FindBy(how = How.XPATH, using = "//a[text()='Release notes']/ancestor::div/div[@class='caret-icon']")
    public WebElement releaseNotesDropdownside;

    @FindBy(how = How.XPATH, using = "//a[text()='Administer']/ancestor::div/div[@class='caret-icon']")
    public WebElement AdministrationDropdownside;

    @FindBy(how = How.XPATH, using = "//a[text()='Use']/ancestor::div/div[@class='caret-icon']")
    public WebElement UseDropdownside;

    @FindBy(how = How.XPATH, using = "//a[text()='Develop']/ancestor::div/div[@class='caret-icon']")
    public WebElement DevlopDropdownside;

    @FindBy(how = How.XPATH, using = "//a[text()='Widgets']/ancestor::div/div[@class='caret-icon']")
    public WebElement WidgetDropdownside;

    @FindBy(how = How.XPATH, using = "//a[text()='Widgets']")
    public WebElement WidgetsInUseSection;

    @FindBy(how = How.XPATH, using = "//a[text()='Use BVD']/ancestor::div/div[@class='caret-icon']")
    public WebElement UseBVDDropdownside;

    @FindBy(how = How.XPATH, using = "//a[text()='BVD widgets and dashboards']/ancestor::div/div[@class='caret-icon']")
    public WebElement UseWidgetsDashboardsdropdownside;

    @FindBy(how = How.XPATH, using = "//a[text()='BVD widgets and dashboards']")
    public WebElement UseWidgetsDashboards;

    @FindBy(how = How.XPATH, using = "//a[text()='Dashboards']/ancestor::div/div[@class='caret-icon']")
    public WebElement Use_Dashboardsdropdownside;

    @FindBy(how = How.XPATH, using = "//a[text()='Dashboards']")
    public WebElement Use_Dashboards;

    @FindBy(how = How.XPATH, using = "//a[text()='Troubleshoot Dashboards and Templates']/ancestor::div/div[@class='caret-icon']")
    public WebElement TroubleshootDashboardDropside;

    @FindBy (how = How.LINK_TEXT, linkText = "Troubleshoot Dashboards and Templates")
    public WebElement troubleshootDashboardsAndTemplates;

    @FindBy (how = How.LINK_TEXT, linkText = "Widgets")
    public WebElement widgetDropdown;

    @FindBy (how = How.LINK_TEXT, linkText = "Widget Properties")
    public WebElement widgetproperties;

    @FindBy (how = How.LINK_TEXT, linkText = "Release notes")
    public WebElement ReleaseNotes;

    @FindBy(how=How.CSS,using="div.json-container")
    public WebElement verifyPage;

    @FindBy(how=How.XPATH, using = "//a[text()='Upgrade']")
    public WebElement Upgrade;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/UserManagement']")
    public WebElement manageRolesLink;

    @FindBy (how = How.LINK_TEXT, linkText = "Install")
    public WebElement Install;

    @FindBy(how = How.LINK_TEXT, linkText ="Get started")
    public WebElement getStartedDropdown;

    @FindBy(how = How.LINK_TEXT, linkText ="Getting Started ")
    public WebElement gettingStartedDropdown;

    @FindBy(how = How.LINK_TEXT, linkText ="Administer")
    public WebElement administrationDropdownDocker;

    @FindBy(how = How.LINK_TEXT, linkText = "Integrate")
    public WebElement dataIntegrationDropdownDocker;

    @FindBy(how = How.LINK_TEXT, linkText="Use")
    public WebElement useDropdownDocker;

    @FindBy(how = How.LINK_TEXT, linkText="Dashboards")
    public WebElement dashboardDropdown;

    @FindBy(how = How.LINK_TEXT, linkText = "Troubleshoot")
    public WebElement troubleshootDropDocker;

    @FindBy(how = How.LINK_TEXT, linkText="Develop")
    public WebElement developDropDocker;

    @FindBy(how = How.XPATH, using = "//a[text()='Troubleshoot']/ancestor::div/div[@class='caret-icon']")
    public WebElement sidebartroubleshootDropdownside;

    @FindBy(how = How.XPATH, using = "//a[text()='Upgrade']/ancestor::div/div[@class='caret-icon']")
    public WebElement UpgradeDropdownside;

    @FindBy(how = How.XPATH, using = "//a[text()='Integrate']/ancestor::div/div[@class='caret-icon']")
    public WebElement DataIntegrationDropdownside;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsStencilRefCharts']")
    public WebElement areaandMultipleAreaWidgets;


    @FindBy(how = How.PARTIAL_LINK_TEXT, using = "Area and Multiple Area Chart Widgets")
    public WebElement areaandMultipleAreaWidgetslink;


    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsStencilRefBar']")
    public WebElement barChartWidgets;


    @FindBy(how = How.PARTIAL_LINK_TEXT, using = "Bar Chart Widgets")
    public WebElement barChartWidgetslink;


    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsCategoryChart']")
    public WebElement categoryWidgets;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsStencilDonutPie']")
    public WebElement DonutPieWidgets;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsStencilRefRSS']")
    public WebElement FeedWidgets;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsStencilRefFrames']")
    public WebElement FrameWidgets;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsStencilRefGauge']")
    public WebElement GaugeWidgets;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsStencilRefSparkline']")
    public WebElement LineChartandSparklineWidgets;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsCategoryChart']")
    public WebElement multicategoryWidget;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsMultiLine']")
    public WebElement multiTimeSeries;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsStencilColorGroup']")
    public WebElement StatusColorGroupWidgets;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsStencilStatusImage']")
    public WebElement StatusImageWidgets;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsStencilVisibleGroup']")
    public WebElement StatusVisibleGroupWidgets;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsStencilTextValue']")
    public WebElement TextValueWidgets;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsStencilRefWeb']")
    public WebElement WebPageWidgets;

    @FindBy(how = How.PARTIAL_LINK_TEXT, using = "Hyperlink Group Widget")
    public WebElement hyperlinkGroupWidgetlink;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsStencilRefGroup']")
    public WebElement WidgetGroups;


    //@FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard_2020.11/Localizedashboard']")
    @FindBy(how = How.PARTIAL_LINK_TEXT,using="Localize a dashboard")
    public WebElement localizeDashboardLink;

    //@FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard_2020.11/viewparamdashboards']")
    @FindBy(how = How.PARTIAL_LINK_TEXT, using = "View parameterized dashboards")
    public WebElement viewParameterizedDashboardLink;


    @FindBy(how = How.PARTIAL_LINK_TEXT, using = "Translate dashboards")
    public WebElement translateDashboardLink;

    @FindBy(how = How.PARTIAL_LINK_TEXT, using = "Scaling dashboard view")
    public WebElement scalingdashboardLink;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/TroubleshootUploadingDashboard']")
    public WebElement uploadingDashboard;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/TroubleshootMissingFiles']")
    public WebElement blankSVGFiles;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/TroubleshootDashboardLoading']")
    public WebElement dashboardLoadingCrashes;

    @FindBy(how = How.XPATH, using = "//a[text()='Chrome crashed when dragging a image composed by data URI']")
    public WebElement chromeCrashed;

    // BVD Documentation Log into BVD Dropdown
    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/Login']")
    public WebElement logIntoBvd;

    @FindBy(how = How.PARTIAL_LINK_TEXT, using = "Screen reader support")
    public WebElement screenReaderSupport;

    @FindBy(how = How.XPATH, using = "//*[@class='external text']/a[text()='support']")
    public WebElement microfocusSupport;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/KnownIssues']")
    public WebElement knownIssues;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/ReleaseSummary']")
    public WebElement releaseNotes;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/AccessLogs']")
    public WebElement accessLogs;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsStencilRefVisio']")
    public WebElement modifyVisio;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsStencilRefAPI']")
    public WebElement defineCustomWidget;

    @FindBy(linkText = "microfocus.github.io/ColorYourData")
    public WebElement gitColourYourData;

    @FindBy(how = How.CSS, using = "a[title='Business Value Dashboard:2019.02/ReleaseSummary']")
    public WebElement ReleaseSummary1;
    @FindBy(how = How.CSS, using = "a[title='Business Value Dashboard:2019.02/ReleaseSummary']")
    public WebElement ReleaseSummary2;
    @FindBy(how = How.CSS, using = "a[title='Business Value Dashboard:2019.02/ReleaseNotes']")
    public WebElement ReleaseSummary3;

    @FindBy(how = How.XPATH, using = "//a[text()='Data Collectors']")
    public WebElement dataCollectorLink;

    @FindBy(how = How.XPATH, using = "//a[text()='Text Value Widgets']")
    public WebElement textValueWidgetsLink;

    @FindBy(how = How.XPATH, using = "//a[text()='Widget Properties']")
    public WebElement widgetPropertiesLink;

    @FindBy(how = How.XPATH, using = "//a[text()='Hyperlink group widget']")
    public WebElement hyperlinkGroupWidgetLink;

    //@FindBy(how = How.XPATH, using = "//a[@href='https://docs.microfocus.com/']")
    //public WebElement swInfoPortal;

    @FindBy(how=How.LINK_TEXT, using = "Software Information Portal")
    public WebElement swInfoPortal;

    @FindBy(how = How.CSS, using = "a[class='external text']")
    public WebElement softwareInfoPortal;

    @FindBy(how = How.XPATH, using = "//a[text()='Manage Users']")
    public WebElement userMngmtDoc;

    @FindBy(how = How.XPATH, using = "//a[text()='Log into BVD']")
    public WebElement sidebarLogIntoBvd;

    @FindBy(how = How.XPATH, using = "//a[text()='Enhancements']")
    public WebElement sidebarEnhancements;

    @FindBy(how = How.XPATH, using = "//a[text()='Fixed issues']")
    public WebElement sidebarFixedIssues;

    @FindBy(how = How.XPATH, using = "//a[text()='Known issues']")
    public WebElement sidebarKnownIssues;

    @FindBy(how = How.XPATH, using = "//a[text()='Deprecation and obsolescence']")
    public WebElement sidebarDepreciationAndObsolescence;

    @FindBy(how = How.XPATH, using = "//a[text()='Release logs']")
    public WebElement sidebarReleaseLog;

    // BVD Documentation Change Your Name Or Password Dropdown
    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/MyAccount']")
    public WebElement changeYourNameAndPassword;

    @FindBy(how = How.XPATH, using = "//a[text()='Change your name']")
    public WebElement sidebarChangeYourNameAndPassword;

    @FindBy(how = How.XPATH, using = "//a[text()='Change display name and timezone']")
    public WebElement sidebartimezonesettings;

    // BVD Documentation Create Your First Dashboard Dropdown
    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/GetStarted']")
    public WebElement createFirstDashboard;

    @FindBy(how = How.XPATH, using = "//a[text()='Create your first dashboard']")
    public WebElement sidebarCreateFirstDashboard;

    @FindBy(how = How.XPATH, using = "//a[text()='Downsampling of data']")
    public WebElement sidebardownSampling;

    // BVD Documentation Dashboard Management Dropdown
    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/DashboardManagement']")
    public WebElement dashboardManagement;

    @FindBy(how = How.LINK_TEXT, linkText = "Manage dashboards")
    public WebElement sidebarDashboardManagement;

    @FindBy(how = How.LINK_TEXT, linkText = "Manage templates")
    public WebElement sidebarTemplateManager;

    // BVD Documentation usermanagement Dropdown
    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/UserManagement']")
    public WebElement userManagement;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/SystemSettings']")
    public WebElement modifySettings;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/APIKey']")
    public WebElement apiKey;

    @FindBy(how = How.CSS, using =  "a[title='Business_Value_Dashboard:11.10/DataCollector']")
    public WebElement dataCollector;

    @FindBy(how = How.LINK_TEXT, linkText = "Modify the settings")
    public WebElement sidebarModifySettings;

    @FindBy(how = How.PARTIAL_LINK_TEXT, partialLinkText = "View and update the API")
    public WebElement sidebarApiKey;

    @FindBy(how = How.LINK_TEXT, linkText = "Data Collectors")
    public WebElement sidebarDataCollector;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/TemplateManager']")
    public WebElement templateManagementDocker;

    @FindBy(how = How.LINK_TEXT, linkText = "Manage roles")
    public WebElement sidebarUserManagement;

    // BVD Documentation download Tools Dropdown
    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/Download']")
    public WebElement downloadTools;

    @FindBy(how = How.LINK_TEXT, linkText = "Resources")
    public WebElement sidebarDownloadTools;

    @FindBy(how = How.XPATH, using = "//a[text()='View parameterized dashboards']")
    public WebElement sidebarparametrizedDashboard;

    @FindBy(how = How.XPATH, using = "//a[text()='Scaling - Dashboard view']")
    public WebElement sidebarScaling;

    @FindBy(how = How.XPATH, using = "//a[text()='Enhanced Visio support']")
    public WebElement sidebarVisioSupport;

    @FindBy(how = How.LINK_TEXT, linkText = "Command Line Interface to Export and Import Dashboards")
    public WebElement sidebarCommandLineInterface;

    @FindBy(how = How.LINK_TEXT, linkText = "BVD monitoring")
    public WebElement bvdMonitoring;

    // BVD Documentation OMi integration Dropdown
    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/IntegratingOBM']")
    public WebElement omiIntegration;

    @FindBy(how = How.LINK_TEXT, linkText = "Create reports in BVD")
    public WebElement sidebarcreateReportsInBVD;

    @FindBy(how = How.LINK_TEXT, linkText = "User roles for BVD")
    public WebElement sidebarUserRolesForBVDLink;

    @FindBy(how = How.LINK_TEXT, linkText = "Glossary")
    public WebElement sidebarGlossaryLink;

    @FindBy(how = How.LINK_TEXT, linkText = "Reporting")
    public WebElement sidebarReporting;

    @FindBy(how = How.LINK_TEXT, linkText = "Migrating external Oracle database")
    public WebElement sidebarExternalOracle;

    @FindBy(how = How.LINK_TEXT, linkText = "Migrating external Postgres")
    public WebElement sidebarExternalPostgres;

    @FindBy(how = How.LINK_TEXT, linkText = "Set up database connection")
    public WebElement sidebarsetUpDBconnection;

    @FindBy(how = How.LINK_TEXT, linkText = "Integrate with OBM")
    public WebElement sidebarOmiIntegration;

    @FindBy(how = How.LINK_TEXT, linkText = "Example: Sending JSON Data to BVD")
    public WebElement sidebarsendingJSONDatatoBVD;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/IntegratingBSMC']")
    public WebElement connectorIntegration;

    @FindBy(how = How.LINK_TEXT, linkText = "Integrate with OpsCx")
    public WebElement sidebarConnectorIntegration;

    @FindBy(how = How.LINK_TEXT, linkText = "Create custom integrations")
    public WebElement customIntegrations;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/IntegratingJSONHTTPPost']" )
    public WebElement  customIntegrationsLink;

    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/IntegratingPostExample']")
    public WebElement sendingJSONDatatoBVD;

    //@FindBy(how = How.XPATH, using = "//a[text()='Widgets']")
    @FindBy(how = How.CSS, using = "a[title='Business_Value_Dashboard:11.10/WidgetsStencil']")
    public WebElement widgets;

    @FindBy(how = How.PARTIAL_LINK_TEXT, using = "Tips and tricks")
    public WebElement tipsAndTricksDocker;

    @FindBy(how = How.PARTIAL_LINK_TEXT, using = "Enhanced Visio support")
    public WebElement enhancedVisiosupport;

    @FindBy(how = How.XPATH, using = "//a[text()='Enhanced Visio support']")
    public WebElement sidebarScreenReader;

    @FindBy(how = How.XPATH, using = "//a[text()='Screen reader support']")
    public WebElement sidebarScreenReadersupport;

    @FindBy(how = How.LINK_TEXT, linkText = "Tips and tricks")
    public WebElement sidebarTipsAndTricksDocker;

    @FindBy(how = How.XPATH, using = "//a[text()='Modify the Visio shape']")
    public WebElement modifyVisioShapeDocker;

    @FindBy(how = How.XPATH, using = "//a[text()='Define the custom widget code']")
    public WebElement defineCustomWidgetCodeDocker;

    @FindBy(how = How.XPATH, using = "//a[text()='Area and Multiple Area Chart Widgets']")
    public WebElement sidebarArea;

    @FindBy(how = How.XPATH, using = "//a[text()='Shapes']")
    public WebElement sidebarShapes;

    @FindBy(how = How.XPATH, using = "//a[text()='Blank SVG files or shapes']")
    public WebElement sidebarblankSvgFileorShapes;

    @FindBy(how = How.XPATH, using = "//a[text()='Dashboard loading crashes or blocks browser']")
    public WebElement sidebardashboardloadingCrashes;

    @FindBy(how = How.XPATH, using = "//a[text()='Upgrade from 2019.08 to 2019.11 shows all BVD pods in Init state']")
    public WebElement sidebarUpgrade;

    @FindBy(how = How.XPATH, using = "//a[text()='Access logs']")
    public WebElement sidebaracesslogs;

    @FindBy(how = How.XPATH, using = "//a[text()='Verifying metrics forwarding from OBM to BVD']")
    public WebElement sidebarverifymetrics;

    @FindBy(how = How.XPATH, using = "//a[text()='DB test connection for Vertica TLS fails']")
    public WebElement sidebardbTestConnVerticaTLS;

    @FindBy(how = How.XPATH, using = "//a[text()='Vertica DB connection fails with self-signed certificate']")
    public WebElement sidebarConnFailsSELFSignedCert;

    @FindBy(how = How.XPATH, using = "//a[text()='Date type parameter using UTC time zone']")
    public WebElement sidebarDateTypeusngTimeZone;

    @FindBy(how = How.XPATH, using = "//a[text()='BVD pods failing with error WRONGPASS invalid username-password pair']")
    public WebElement sidebarBVDpodsWRONPASS;

    @FindBy(how = How.XPATH, using = "//a[text()='BVD reports failed to load with a red banner without any error']")
    public WebElement sidebarredBanner;

    @FindBy(how = How.XPATH, using = "//a[text()='Connection timed out']")
    public WebElement sidebarConnTimeOut;

    @FindBy(how = How.XPATH, using = "//a[text()='Notification Messages']")
    public WebElement sidebarnotificationMessages;

    @FindBy(how = How.XPATH, using = "//a[text()='Exchange Certificate in Vertica']")
    public WebElement sidebarexchangeCertificates;

    @FindBy(how = How.XPATH, using = "//a[text()='Color changes']")
    public WebElement sidebarcolorChanges;

    @FindBy(how = How.XPATH, using = "//a[text()='Bar Chart Widgets']")
    public WebElement sidebarBarChart;

    @FindBy(how = How.XPATH, using = "//a[text()='Donut/Pie Widgets']")
    public WebElement sidebarDonut;

    @FindBy(how = How.XPATH, using = "//a[text()='Feed Widgets']")
    public WebElement sidebarFeed;

    @FindBy (how = How.XPATH, using = "//a[text()='Frame Widgets']")
    public WebElement sidebarFrame;

    @FindBy (how = How.XPATH, using = "//a[text()='Gauge Widgets']")
    public WebElement sidebarGauge;

    @FindBy (how = How.XPATH, using = "//a[text()='Label Widget']")
    public WebElement sidebarlabelWidget;

    @FindBy (how = How.XPATH, using = "//a[text()='Line chart and sparkline widgets']")
    public WebElement sidebarLineChart;

    @FindBy (how = How.XPATH, using = "//a[text()='Multi Category Series Widgets']")
    public WebElement   sidebarmultiCategoryWidget;

    @FindBy (how = How.XPATH, using = "//a[text()='Multi Time Series Widgets']")
    public WebElement sidebarmultiTimeseriesWidget;

    @FindBy (how = How.XPATH, using = "//a[text()='Status Color Group Widgets']")
    public WebElement sidebarStatusColor;

    @FindBy (how = How.XPATH, using = "//a[text()='Status Image Widgets']")
    public WebElement sidebarStatusImage;

    @FindBy (how = How.XPATH, using = "//a[text()='Status Visible Group Widgets']")
    public WebElement sidebarStatusVisible;

    @FindBy (how = How.XPATH, using = "//a[text()='Text Value Widgets']")
    public WebElement sidebarTextValue;

    @FindBy (how = How.XPATH, using = "//a[text()='Web Page Widgets']")
    public WebElement sidebarWebPage;

    @FindBy (how = How.XPATH, using = "//a[text()='Hyperlink group widget']")
    public WebElement sidebarHyperlinkgroupwidget;

    @FindBy(how = How.PARTIAL_LINK_TEXT, partialLinkText = "Widget Groups")
    public WebElement sidebarwidgetGroup;

    @FindBy (how = How.XPATH, using = "//a[text()='Widget Properties']")
    public WebElement sidebarwidgetProperties;

    @FindBy(how = How.LINK_TEXT, linkText = "Localize a dashboard")
    public WebElement sidebarLocalizedDashboard;

    @FindBy(how = How.LINK_TEXT, linkText = "Translate dashboards")
    public WebElement sidebarTranslateDashboard;

    public BvdOnlineHelpPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 25);
    }
    String textElementFirst = "#mw-content-text";
    String textElementSecond = "#mw-content-text";
    String textElementJson = "#mw-content-text";
    String textElementOrdinary = "#mw-content-text";
    public Map<String, String> pageScrollView(WebElement element)
    {
        Map<String, String> result;
        try
        {
            JavascriptExecutor scroll = (JavascriptExecutor)driver;
            scroll.executeScript("arguments[0].scrollIntoView();", element);
            result = ResultStore.success("Successfully scrolled Element to View" );
        } catch (Exception exc)
        {
            result = ResultStore.failure("Failed to scroll: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }
    public Map<String, String> navigateToCheckPageTitleAndBack(WebElement rootMenu, WebElement element,
                                                               String title_expected)
    {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(5000))
                .ignoring(NoSuchElementException.class);
        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return rootMenu;
            }
        });
        Actions builder = new Actions(driver);
        pageScrollView(element);
        String home =driver.getCurrentUrl();
        TestUtils.sleep(2);
        builder.moveToElement(rootMenu).click(rootMenu).build().perform();
        clickElementAndReturnResultDynamicWait(element, element + " menu clicked",
                element + " menu not clicked");
        try
        {
            TestUtils.sleep(3);
            String actualTitle = driver.getTitle();
            if (actualTitle.equals(title_expected))
            {
                driver.get(home);
                TestUtils.sleep(3);
                return ResultStore.success(element + " Page validated successfully");
            }
            driver.navigate().back();
            TestUtils.sleep(3);
            return ResultStore.failure(element + " Page not validated successfully");
        } catch (Exception e)
        {
            return ResultStore.failure(e.getMessage());
        }
    }
    public Map<String, String> navigateToCheckPageTitleAndClose(WebElement rootMenu, WebElement element,
                                                                String title_expected)
    {
        Map<String,String> result;
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(5000))
                .ignoring(NoSuchElementException.class);
        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return rootMenu;
            }
        });
        Actions builder = new Actions(driver);
        pageScrollView(element);
        builder.moveToElement(rootMenu).click(rootMenu).build().perform();
        clickElementAndReturnResultDynamicWait(element, element + " menu clicked",
                element + " menu not clicked");
        try
        {
            TestUtils.sleepAndLog(8000);
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(2));
            TestUtils.sleepAndLog(8000);
            String actualTitle = driver.getTitle();
            if (actualTitle.equals(title_expected)) {
                result = ResultStore.success(element + " Page validated successfully");
                driver.close();
                driver.switchTo().window(tabs.get(1));
                return result;
            }
            driver.close();
            driver.switchTo().window(tabs.get(1));
            result = ResultStore.failure(element + " Page not validated successfully");
        }
        catch (Exception exc)
        {
            result =  ResultStore.failure(exc.getMessage());
        }
        return result;
    }
    public Map<String, String> navigateToCheckPageURLAndClose(WebElement rootMenu, WebElement element,
                                                              String url_expected)
    {
        Map<String,String> result;
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(5000))
                .ignoring(NoSuchElementException.class);
        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return rootMenu;
            }
        });
        Actions builder = new Actions(driver);
        pageScrollView(element);
        builder.moveToElement(rootMenu).click(rootMenu).build().perform();
        clickElementAndReturnResultDynamicWait(element, element + " menu clicked",
                element + " menu not clicked");
        try
        {
            TestUtils.sleepAndLog(1000);
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(2));
            TestUtils.sleepAndLog(5000);
            String actualURL = driver.getCurrentUrl();
            if (actualURL.equals(url_expected)) {
                result = ResultStore.success(element + " Page validated successfully");
                driver.close();
                driver.switchTo().window(tabs.get(1));
                return result;
            }
            driver.close();
            driver.switchTo().window(tabs.get(1));
            result = ResultStore.failure(element + " Page not validated successfully");
        }
        catch (Exception exc)
        {
            result =  ResultStore.failure(exc.getMessage());
        }
        return result;
    }
    public Map<String, String> switchToTab(String pageTitle) {
        String subWindowHandler = null;
        TestUtils.sleep(4);
        Set<String> handles = driver.getWindowHandles();
        Iterator<String> iterator = handles.iterator();
        while (iterator.hasNext()) {
            subWindowHandler = iterator.next();
        }
        driver.switchTo().window(subWindowHandler);
        if(pageTitle=="Home"){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#firstHeading"))).equals("Home");
        }else{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#firstHeading"))).equals("Getting started");
        }

        String actualTitle = driver.getTitle();
        try {
            if (actualTitle.equals(pageTitle)) {
                return ResultStore.success("Tab Switched");
            }
            return ResultStore.failure("Tab not Switched");
        } catch (Exception e) {
            return ResultStore.failure(e.getMessage());
        }
    }
    private Map<String, String> clickElementAndReturnResultDynamicWait(WebElement element,
                                                                       String successMessage,
                                                                       String failureMessage) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofMillis(5000))
                    .ignoring(NoSuchElementException.class);
            WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return element;
                }
            });
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            Actions builderelement = new Actions(driver);
            builderelement.moveToElement(element).click(element).build().perform();
            return ResultStore.success(successMessage);
        } catch (Exception e) {
            return ResultStore.failure(failureMessage);
        }
    }
    public Map<String, String> openHelpDropdownMenu() {
        try {
            clickElementAndReturnResultDynamicWait(menuHelpDropdown, "Help menu clicked",
                    "Help menu clicked");
            return ResultStore.success("Help menu clicked");
        } catch (Exception e) {
            return ResultStore.failure("Help menu clicked");
        }
    }
    public Map<String, String> openDocumentation() {
        try {
            clickElementAndReturnResultDynamicWait(menuHelpDocumentation, "Documentation menu clicked",
                    "Documentation menu not clicked");
            return ResultStore.success("Documentation menu clicked");
        } catch (Exception e) {
            return ResultStore.failure("Documentation menu clicked");
        }
    }
    public Map<String, String> clickGettingStarted() {
        try {
            clickElementAndReturnResultDynamicWait(menuHelpGettingStarted, "GettingStarted menu clicked",
                    "GettingStarted menu not clicked");
            return ResultStore.success("GettingStarted menu clicked");
        } catch (Exception e) {
            return ResultStore.failure("GettingStarted menu not clicked");
        }
    }
    public Map<String, String> openGettingStartedAndCheckText(String expected_text) {
        try {
            String helpTextFirstDashboard = driver.findElement(By.cssSelector
                    (textElementSecond)).getText();
            System.out.println(helpTextFirstDashboard);
            if (helpTextFirstDashboard.contains(expected_text)) {
                return ResultStore.success("text exist");
            }
            return ResultStore.failure("text doesn't exist");
        } catch (Exception e) {
            return ResultStore.failure("Documentation menu not clicked");
        }
    }
    public Map<String, String> clickHorizontalDropdownMenuside(WebElement rootMenu, WebElement element,
                                                               String
                                                                       text_expected) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(5000))
                .ignoring(NoSuchElementException.class);
        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return rootMenu;
            }
        });
        Actions builder = new Actions(driver);
        builder.moveToElement(rootMenu).click(rootMenu).build().perform();
        if(!rootMenu.equals(element)) {
            clickElementAndReturnResultDynamicWait(element, element + " menu clicked",
                    element + " menu not clicked");
        }
        try
        {
            wait.until(ExpectedConditions.visibilityOf(verifyPage));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(textElementOrdinary)));
            WebElement foundText = driver.findElement(By.cssSelector(textElementOrdinary));
            if (foundText.getText().contains(text_expected))
            {
                return ResultStore.success(element + " text exist");
            }
            return ResultStore.failure(element + " text doesn't exist");
        } catch (Exception e)
        {
            return ResultStore.failure(e.getMessage());
        }
    }
    public Map<String, String> clickHorizontalDropdownMenu(WebElement rootMenu, WebElement element,
                                                           String
                                                                   text_expected)
    {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(5000))
                .ignoring(NoSuchElementException.class);
        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return rootMenu;
            }
        });
        Actions builder = new Actions(driver);
        builder.moveToElement(rootMenu).click(rootMenu).build().perform();
        clickElementAndReturnResultDynamicWait(element, element + " menu clicked",
                element + " menu not clicked");
        try
        {
            wait.until(ExpectedConditions.visibilityOf(verifyPage));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(textElementOrdinary)));
            WebElement foundText = driver.findElement(By.cssSelector(textElementOrdinary));

            if (foundText.getText().contains(text_expected))
            {
                return ResultStore.success(element + " text exist");
            }
            return ResultStore.failure(element + " text doesn't exist");
        } catch (Exception e)
        {
            return ResultStore.failure(e.getMessage());
        }
    }
    public Map<String, String> clickHorizontalDropdownMenuclick(WebElement element,
                                                                String
                                                                        text_expected)
    {
        pageScrollView(element);
        clickElementAndReturnResultDynamicWait(element, element + " menu clicked",
                element + " menu not clicked");
        try
        {
            wait.until(ExpectedConditions.visibilityOf(verifyPage));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(textElementOrdinary)));
            WebElement foundText = driver.findElement(By.cssSelector(textElementOrdinary));
            if (foundText.getText().contains(text_expected))
            {
                return ResultStore.success(element + " text exist");
            }
            return ResultStore.failure(element + " text doesn't exist");
        } catch (Exception e)
        {
            return ResultStore.failure(e.getMessage());
        }
    }
    public Map<String, String> checkMainTabLinks(WebElement rootMenu, WebElement element,
                                                 String
                                                         text_expected)
    {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(5000))
                .ignoring(NoSuchElementException.class);
        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return rootMenu;
            }
        });
        Actions builder = new Actions(driver);
        builder.moveToElement(rootMenu).click(rootMenu).build().perform();
        clickElementAndReturnResultDynamicWait(element, element + " menu clicked",
                element + " menu not clicked");
        try
        {
            wait.until(ExpectedConditions.visibilityOf(verifyPage));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(textElementOrdinary)));
            WebElement foundText = driver.findElement(By.cssSelector(textElementOrdinary));
            System.out.println(foundText.getText());
            if (foundText.getText().contains(text_expected))
            {
                return ResultStore.success(element + " text exist");
            }
            return ResultStore.failure(element + " text doesn't exist");
        } catch (Exception e)
        {
            return ResultStore.failure(e.getMessage());
        }
    }
    public Map<String, String> clickSidebar(WebElement element,
                                            String
                                                    text_expected)
    {
        pageScrollView(element);
        clickElementAndReturnResultDynamicWait(element, element + " menu clicked",
                element + " menu not clicked");
        driver.manage().timeouts().pageLoadTimeout(20, SECONDS);
        try
        {
            wait.until(ExpectedConditions.visibilityOf(verifyPage));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(textElementOrdinary)));
            WebElement foundText = driver.findElement(By.cssSelector(textElementOrdinary));
            System.out.println(foundText.getText());
            if (foundText.getText().contains(text_expected))
            {
                return ResultStore.success(element + " text exist");
            }
            return ResultStore.failure(element + " text doesn't exist");
        } catch (Exception e)
        {
            return ResultStore.failure(e.getMessage());
        }
    }
    public Map<String, String> closeActiveTab()
    {
        try
        {
            ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
            driver.switchTo().window(tabs2.get(1));
            driver.close();
            driver.switchTo().window(tabs2.get(0));
            return ResultStore.success("Tab closed");
        } catch (Exception e)
        {
            return ResultStore.failure("Tab not Closed");
        }
    }
}