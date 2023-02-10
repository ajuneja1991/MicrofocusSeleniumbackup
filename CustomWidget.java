package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CustomWidget
{
  
  
  private LoginPage loginPage;
  private MainPage mainPage;
  private ManageDashboardsPage manageDashboardsPage;
  private EditDashboardPage editDashboardPage;
  private WebDriver driver;
  private Map<String, String> result;
  private String dims = "browser";

  private String tags = "SoloCircle";
  private String tags2 = "WidgetGroupCircle";
  private BvdWsClient client;
  private String json;
  private String soloCircleWidget = "shape2";
  private String circleGroupWidget = "group3";
  private String circleInGroup = "shape8";

  
  private String sSourceDashboard = "PumpCircleSoloAndWG";
  private String sDashboard ;
  private String sDashboardPath;
  //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;
  
  private String soloCircleSize ;
  private String widgetGroupCircleSize ;
  private String soloChangedCircleSize;
  private String widgetGroupChangedCircleSize;
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
  private String OS ;
  private String userName = "TesterCustomWidget";
  private String userLogin;
  private String userPwd = "Test@123";
  private String roleName = "testCustomWidget";
  private String desc = "For tester";
  private String category = "All";
  private String accessType = "full-control";
  String roleId;
  private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");
  private void initPages() {
    loginPage = PageFactory.initElements(driver, LoginPage.class);
    mainPage = PageFactory.initElements(driver, MainPage.class);
    manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
    editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
  }
  
  private String get_key()
  {
    result = client.userWS.getApiKey();
    return result.get("out");
  }
  
  @BeforeClass
  public void setup() {
    cfg = new TestConfig().getConfig();
    BROWSER=cfg.getString("application.bvd.browser.name");
    BVD_USR = cfg.getString("application.bvd.user.adminusr");
    BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
    URL=cfg.getString("application.bvd.url.external");
    sDashboard = sSourceDashboard + BROWSER;
    sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
    OS=System.getProperty("os.name").toLowerCase();
    driver = UiTestWebDriver.getWebDriver();
    driver.get(URL);
    initPages();
    try {
      client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
    } catch (Exception e) {
      e.printStackTrace();
    }
  
    if (BROWSER.equalsIgnoreCase("chrome")){
    soloCircleSize = "28.347551345825195";
    widgetGroupCircleSize = "35.99995040893555";
    soloChangedCircleSize = "15.87462875366211";
    widgetGroupChangedCircleSize = "60.479916687011716";
    }
    else if (BROWSER.equalsIgnoreCase("firefox")) {
      soloCircleSize = "28.34765625";
      widgetGroupCircleSize = "35.99995040893555";
      soloChangedCircleSize = "15.874687500000002";
      widgetGroupChangedCircleSize = "60.479916687011716";
      
    }
    else
    {
      soloCircleSize = "28.3475";
      widgetGroupCircleSize = "36";
      soloChangedCircleSize = "15.8746";
      widgetGroupChangedCircleSize = "60.4799";
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
  public void deleteDashboardFileIfExist() throws IOException
  {
    
    File file;
    
    if (OS.contains("win")) {
      file = new File("C:\\tmp\\" + sDashboard + ".svg");
    } else {
      file = new File("/tmp/" + sDashboard + ".svg");
    }
    
    try {
      file.delete();
      result = ResultStore.success("File successfully deleted");
      
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to delete Dashboard " + file.toString() + ". error: " + Misc.getStacktraceAsString(exc));
    }
    
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 25)
  public void copyTesFiles() {
    result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 30)
  public void getApiKeyWS() {
    result = client.userWS.getApiKey();
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 40)
  public void sendDataSoloCircle() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{\"browser\":\"" + BROWSER + "\"," +
           "\"radius\":50}";
    
    result = client.receiverWS.bvdSendData(get_key(), dims, tags, json);
    Result.rcEquals(result, "0");
    
  }
  
  @Test(priority = 50)
  public void sendDataWGCircle() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{ \n" +
          " \"items\" : [{  \n" +
          " \"id\": 1,\n" +
          " \"radius\":50,\n" +
          " \"text\":50\n" +
          " }]\n" +
          " }";
    
    result = client.receiverWS.bvdSendDataTagsOnly(get_key(), tags2, json);
    Result.rcEquals(result, "0");
    
  }
  
  @Test(priority = 60)
  public void openManageDashboards() {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 70)
  public void uploadDashboard() {
    result = manageDashboardsPage.uploadDashboard(sDashboardPath);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 80)
  public void setupSoloCircle()
  {
    result = editDashboardPage.selectWidget(soloCircleWidget);
    Result.rcEquals(result, "0");

    result = editDashboardPage.setDataChannel(tags);
    Result.rcEquals(result, "0");

    result = editDashboardPage.selectDataFieldFromDropdown("radius");
    Result.rcEquals(result, "0");
  
    result = editDashboardPage.setColoringRule("#7FFF00:radius<30;#ffff26:radius<60;#f40000");
    Result.rcEquals(result, "0");
  
    result = editDashboardPage.setVisibilityRule("radius>=10");
    Result.rcEquals(result, "0");
  
//    result = editDashboardPage.setHyperlinkToAnotherDashboard("omi_sample");
//    Result.rcEquals(result, "0");
    
  }
  
  
  @Test(priority = 90)
  public void setupCircleInWg()
  {
    result = editDashboardPage.selectWidgetFromList(circleGroupWidget);
    Result.rcEquals(result, "0");
    
    result = editDashboardPage.setDataChannel(tags2);
    Result.rcEquals(result, "0");

    result = editDashboardPage.selectWidgetFromList(circleInGroup);
    Result.rcEquals(result, "0");

    result = editDashboardPage.selectDataFieldFromDropdown("radius");
    Result.rcEquals(result, "0");
  
    result = editDashboardPage.setColoringRule("#7FFF00:radius<30;#ffff26:radius<60;#f40000");
    Result.rcEquals(result, "0");
  
    result = editDashboardPage.setVisibilityRule("radius>=10");
    Result.rcEquals(result, "0");
    
  }
  

  @Test(priority = 100)
  public void saveDashboard()
  {
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 110)
  public void viewDashboard() {
    result = mainPage.viewDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }


  @Test(priority = 120)
  public void checkSoloCircle() {
    result = mainPage.checkSoloCircleSize(soloCircleWidget, soloCircleSize);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 130)
  public void checkWGCircle() {
    TestUtils.sleepAndLog(1000);
    result = mainPage.checkWGCircleSize(circleInGroup, widgetGroupCircleSize);
    Result.rcEquals(result, "0");
  }


  @Test(priority = 140)
  public void changeSoloCircle() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{\"browser\":\"" + BROWSER + "\"," +
          "\"radius\":28}";

    result = client.receiverWS.bvdSendData(get_key(), dims, tags, json);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 150)
  public void changeWGCircle() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{ \n" +
          " \"items\" : [{  \n" +
          " \"id\": 1,\n" +
          " \"radius\":84,\n" +
          " \"text\":84\n" +
          " }]\n" +
          " }";

    result = client.receiverWS.bvdSendDataTagsOnly(get_key(), tags2, json);
    Result.rcEquals(result, "0");

  }


  @Test(priority = 160)
  public void reCheckSoloCircle() {
    TestUtils.sleepAndLog(1000);
    result = mainPage.checkSoloCircleSize(soloCircleWidget, soloChangedCircleSize);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 170)
  public void reCheckWGCircle() {
    result = mainPage.checkWGCircleSize(circleInGroup, widgetGroupChangedCircleSize);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 180)
  public void changeSoloCircleWidgetNotAvailable() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{\"browser\":\"" + BROWSER + "\"," +
          "\"radius\":9}";

    result = client.receiverWS.bvdSendData(get_key(), dims, tags, json);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 190)
  public void changeWGCircleWidgetNotAvailable() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{ \n" +
          " \"items\" : [{  \n" +
          " \"id\": 1,\n" +
          " \"radius\":9,\n" +
          " \"text\":84\n" +
          " }]\n" +
          " }";

    result = client.receiverWS.bvdSendDataTagsOnly(get_key(), tags2, json);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 200)
  public void reCheckSoloCircleIsDisappeared() {
    TestUtils.sleepAndLog(1000);
    result = mainPage.checkSoloCircleSize(soloCircleWidget, soloChangedCircleSize);
    Result.rcEquals(result, "1");
  }

  @Test(priority = 210)
  public void reCheckWGCircleIsDisappeared() {
    result = mainPage.checkWGCircleSize(circleInGroup, widgetGroupChangedCircleSize);
    Result.rcEquals(result, "1");
  }


  @Test(priority = 220)
  public void changeSoloCircleWidgetSetToGreen() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{\"browser\":\"" + BROWSER + "\"," +
          "\"radius\":20}";

    result = client.receiverWS.bvdSendData(get_key(), dims, tags, json);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 230)
  public void changeWGCircleWidgetSetToGreen() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{ \n" +
          " \"items\" : [{  \n" +
          " \"id\": 1,\n" +
          " \"radius\":20,\n" +
          " \"text\":84\n" +
          " }]\n" +
          " }";

    result = client.receiverWS.bvdSendDataTagsOnly(get_key(), tags2, json);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 240)
  public void reCheckSoloCircleIsGreen() {
    TestUtils.sleepAndLog(1000);
    result = mainPage.checkSoloCircleColor(soloCircleWidget);
    Result.outEquals(result, "#7fff00");
  }

  @Test(priority = 250)
  public void reCheckWGCircleIsGreen() {
    result = mainPage.checkWGCircleColor(circleInGroup);
    Result.outEquals(result, "#7fff00");
  }


  @Test(priority = 260)
  public void changeSoloCircleWidgetSetToYellow() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{\"browser\":\"" + BROWSER + "\"," +
          "\"radius\":45}";

    result = client.receiverWS.bvdSendData(get_key(), dims, tags, json);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 270)
  public void changeWGCircleWidgetSetToYellow() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{ \n" +
          " \"items\" : [{  \n" +
          " \"id\": 1,\n" +
          " \"radius\":45,\n" +
          " \"text\":84\n" +
          " }]\n" +
          " }";

    result = client.receiverWS.bvdSendDataTagsOnly(get_key(), tags2, json);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 280)
  public void reCheckSoloCircleIsYellow() {
    TestUtils.sleepAndLog(1000);
    result = mainPage.checkSoloCircleColor(soloCircleWidget);
    Result.outEquals(result, "#ffff26");
  }

  @Test(priority = 290)
  public void reCheckWGCircleIsYellow() {
    result = mainPage.checkWGCircleColor(circleInGroup);
    Result.outEquals(result, "#ffff26");
  }

  @Test(priority = 300)
  public void changeSoloCircleWidgetSetToRed() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{\"browser\":\"" + BROWSER + "\"," +
          "\"radius\":86}";

    result = client.receiverWS.bvdSendData(get_key(), dims, tags, json);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 310)
  public void changeWGCircleWidgetSetToRed() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{ \n" +
          " \"items\" : [{  \n" +
          " \"id\": 1,\n" +
          " \"radius\":86,\n" +
          " \"text\":84\n" +
          " }]\n" +
          " }";

    result = client.receiverWS.bvdSendDataTagsOnly(get_key(), tags2, json);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 320)
  public void reCheckSoloCircleIsRed() {
    TestUtils.sleepAndLog(1000);
    result = mainPage.checkSoloCircleColor(soloCircleWidget);
    Result.outEquals(result, "#f40000");
  }

  @Test(priority = 330)
  public void reCheckWGCircleIsRed() {
    result = mainPage.checkWGCircleColor(circleInGroup);
    Result.outEquals(result, "#f40000");
  }

  @Test(priority = 580)
  public void reOpenManageDashboards() {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
  }
  
    @Test(priority = 590)
  public void changeValuesForExportImport() {
    result = manageDashboardsPage.openEditDashboard(sDashboard);
    Result.rcEquals(result, "0");
  
    result = editDashboardPage.selectWidget(soloCircleWidget);
    Result.rcEquals(result, "0");
  
    result = editDashboardPage.setCircleRange("55");
    Result.rcEquals(result, "0");
  
    result = editDashboardPage.setCalculationRule("radius + 5", 50);
    Result.rcEquals(result, "0");
  
    result = editDashboardPage.selectWidget(circleInGroup);
    Result.rcEquals(result, "0");
  
    result = editDashboardPage.setCircleRange("65");
    Result.rcEquals(result, "0");
  
      result = editDashboardPage.setCalculationRule("radius + 6", 50);
      Result.rcEquals(result, "0");
  
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
    
  }
  
  @Test(priority = 595)
  public void deleteInitialDashboardFile() throws IOException
  {
    
    File file;
    
    if (OS.contains("win")) {
      file = new File("C:\\tmp\\" + sDashboard + ".svg");
    } else {
      file = new File("/tmp/" + sDashboard + ".svg");
    }
    
    try {
      file.delete();
      result = ResultStore.success("File successfully deleted");
      
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to delete Dashboard " + file.toString() + ". error: " + Misc.getStacktraceAsString(exc));
    }
    
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 600)
  public void exportImportDashboard() {
    result = manageDashboardsPage.downloadDashboard(sDashboard);
    Result.rcEquals(result, "0");
  
    result = manageDashboardsPage.deleteDashboard(sDashboard);
    Result.rcEquals(result, "0");
  
    result = manageDashboardsPage.uploadDashboard(sDashboardPath);
    Result.rcEquals(result, "0");

  }
  
  
  @Test(priority = 610)
  public void checkUploadedValuesSoloCircle() {
    result = editDashboardPage.selectWidget(soloCircleWidget);
    Result.rcEquals(result, "0");
  
    result = editDashboardPage.getCircleRange();
    Result.assertEquals(result.get("out"), "55");
  
    result = editDashboardPage.getColoringRule();
    Result.assertEquals(result.get("out"), "#7FFF00:radius<30;#ffff26:radius<60;#f40000");
  
    result = editDashboardPage.getVisibilityRule();
    Result.assertEquals(result.get("out"), "radius>=10");
  
    result = editDashboardPage.getCalculationRule();
    Result.assertEquals(result.get("out"), "radius + 5");
  }
  
  
  @Test(priority = 620)
  public void checkUploadedValuesWGCircle() {
    result = editDashboardPage.selectWidget(circleInGroup);
    Result.rcEquals(result, "0");
    
    result = editDashboardPage.getCircleRange();
    Result.assertEquals(result.get("out"), "65");
    
    result = editDashboardPage.getColoringRule();
    Result.assertEquals(result.get("out"), "#7FFF00:radius<30;#ffff26:radius<60;#f40000");
    
    result = editDashboardPage.getVisibilityRule();
    Result.assertEquals(result.get("out"), "radius>=10");
    
    result = editDashboardPage.getCalculationRule();
    Result.assertEquals(result.get("out"), "radius + 6");
  
    result = editDashboardPage.cancelAndDiscardChanges();
    Result.rcEquals(result, "0");
    
  }



  @Test(priority = 630)
  public void testLogout() {
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }


  @Test(priority = 640)
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
