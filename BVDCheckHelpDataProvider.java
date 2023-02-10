package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.pageobjects.bvd.HelpPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class BVDCheckHelpDataProvider{
  private LoginPage loginPage;
  private MainPage mainPage;
  private HelpPage helpPage;
  private WebDriver driver;
  private Map<String, String> result;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;

    private void initPages() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        helpPage = PageFactory.initElements(driver, HelpPage.class);
    }

    @BeforeClass
    public void setup() {
        //super.setup(true);
        //driver = getWebDriver();
        //driver.get(EXT_BVD_URL + "/#/login");
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL+ "/#/login");
        initPages();
    }

    @Test(priority = 10)
    public void testLogin() {
        result = loginPage.login("admin", "da$hb0ard!");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 20)
    public void testOpenDocumenation() {
        result = mainPage.openDocumentation();
        Result.rcEquals(result, "0");
    }

    @DataProvider(name = "getDocuStructure")
    public Object[][] getDocuStructure() {
        Object[][] oDocuStructure = helpPage.getMenuStructure();
        return oDocuStructure;
    }

    @Test(priority = 30, dataProvider = "getDocuStructure")
    public void checkDocu(String[] sMenuEntry) {
        result = helpPage.openGetStartedDocu();
        Result.rcEquals(result, "0");
    }

    /*
          @Test(priority = 50)
          public void checkOMiIntegrationDocu(){
            result = HelpPage.openOMiIntegrationDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 60)
          public void checkForwardOMiEventStatusDocu(){
            result = HelpPage.openForwardOMiEventStatusDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 70)
          public void checkForwardOMiKPIStatusDocu(){
            result = HelpPage.openForwardOMiKPIStatusDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 80)
          public void checkAdministrationDocu(){
            result = HelpPage.openAdministrationDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 90)
          public void checkSystemSettingsDocu(){
            result = HelpPage.openSystemSettingsDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 100)
          public void checkManageDashboardsDocu(){
            result = HelpPage.openManageDashboardsDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 110)
          public void checkUserManagementDocu(){
            result = HelpPage.openUserManagementDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 120)
          public void checkKnowledgeBaseDocu(){
            result = HelpPage.openKnowledgeBaseDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 130)
          public void checkWidgetsDocu(){
            result = HelpPage.openWidgetsDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 140)
          public void checkAreaAndMultipleAreaChartWidgetsDocu(){
            result = HelpPage.openAreaAndMultipleAreaChartWidgetsDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 150)
          public void barChartWidgetsDocu(){
            result = HelpPage.openBarChartWidgetsDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 160)
          public void donutChartWidgetsDocu(){
            result = HelpPage.openDonutChartWidgetsDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 170)
          public void feedWidgetsDocu(){
            result = HelpPage.openFeedWidgetsDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 180)
          public void frameWidgetsDocu(){
            result = HelpPage.openFrameWidgetsDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 190)
          public void lineChartAndSparklineWidgetsDocu(){
            result = HelpPage.openLineChartAndSparklineWidgetsDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 200)
          public void statusColorGroupDocu(){
            result = HelpPage.openStatusColorGroupDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 210)
          public void statusImageWidgetsDocu(){
            result = HelpPage.openStatusImageWidgetDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 220)
          public void statusVisibleGroupDocu(){
            result = HelpPage.openStatusVisibleGroupDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 230)
          public void textValueWidgetsDocu(){
            result = HelpPage.openTextValueWidgetsDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 240)
          public void webPageWidgetsDocu(){
            result = HelpPage.openWebPageWidgetsDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 250)
          public void widgetPropertiesDocu(){
            result = HelpPage.openWidgetPropertiesDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 260)
          public void chartAutoScaleDocu(){
            result = HelpPage.openChartAutoScaleDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 270)
          public void chartColorsDocu(){
            result = HelpPage.openChartColorsDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 280)
          public void chartPeriodDocu(){
            result = HelpPage.openChartPeriodDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 290)
          public void coloringRuleDocu(){
            result = HelpPage.openColoringRuleDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 300)
          public void dataChannelDocu(){
            result = HelpPage.openDataChannelDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 310)
          public void dataFieldDocu(){
            result = HelpPage.openDataFieldDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 320)
          public void defaultValueDocu(){
            result = HelpPage.openDefaultValueDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 330)
          public void feedMaxItemsDocu(){
            result = HelpPage.openFeedMaxItemsDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 340)
          public void hyperlinkDocu(){
            result = HelpPage.openHyperlinkDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 350)
          public void imageSelectionRuleDocu(){
            result = HelpPage.openImageSelectionRuleDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 360)
          public void maxValueDocu(){
            result = HelpPage.openMaxValueDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 370)
          public void minValueDocu(){
            result = HelpPage.openMinValueDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 380)
          public void mouseOverDocu(){
            result = HelpPage.openMouseOverDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 390)
          public void showChartNumbersDocu(){
            result = HelpPage.openShowChartNumbersDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 400)
          public void httpPostFormatDocu(){
            result = HelpPage.openHttpPostFormatDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 410)
          public void exampleConnectingJsonDataToWidgetsDocu(){
            result = HelpPage.openExampleConnectingJsonDataToWidgetsDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 420)
          public void tipsTricksAndTroubleshootingDocu(){
            result = HelpPage.openTipsTricksAndTroubleshootingDocu();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 430)
          public void closeHelp()
          {
          driver.switchTo();

          }

          @Test(priority = 440)
          public void checkGettingStarted()
          {
            result = MainPage.openGettingStarted();
            Result.rcEquals(result, "0");
          }

          @Test(priority = 6)
          public void testLogout(){

            // logout
            result = MainPage.logout();
            Result.rcEquals(result, "0");
          }
        */

    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}
