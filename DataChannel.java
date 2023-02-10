package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.SystemInfo;
import com.hp.opr.qa.framework.common.utils.Rmi2HttpClient;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.WebDriver;
import com.hp.opr.qa.framework.UiTestWebDriver;
import com.mf.opr.qa.framework.TestConfig;
import com.typesafe.config.Config;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.rmi.RemoteException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.*;

public class DataChannel
{
  private LoginPage loginPage;
  private MainPage mainPage;
  private ManageDashboardsPage manageDashboardsPage;
  private EditDashboardPage editDashboardPage;
  private WebDriver driver;
  private Map<String, String> result;
  private String sWidget = "group791";
  private String sWidgetBars = "group792";
  private String sDataChannel = "";
  private String sSourceDashboard = "sample_dashboard_test";
  private String sDashboard ="";
  private String sDashboardPath="";
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
  private String userName = "TesterforDatachannel";
  private String userLogin;
  private String userPwd = "Test@123";
  private String roleName = "testfordatachannel";
  private String desc = "For tester";
  private String category = "All";
  private String accessType = "full-control";
  String roleId;
  private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");
  private String key;
  private String dims = "browser";
  private String tags = "DataChannel";
  private BvdWsClient client;
  private String json;
  private String tag_slash = "before/after";
  private String dim_slash = "devant/apres";
  private List<String> dimsList = Arrays.asList("dim1", "dim2", "dim3", "dim4");
  private List<String> lChartNumber = Collections.singletonList("2, 4, 6, 16");
  private String slashDataChannel = "before/after " + "with slash";
  private static String OS="";

  
  private void initPages()
  {
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
  public void setup()
  {
    cfg = new TestConfig().getConfig();
    BROWSER=cfg.getString("application.bvd.browser.name");
    BVD_USR = cfg.getString("application.bvd.user.adminusr");
    BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
    URL=cfg.getString("application.bvd.url.external");
    sDataChannel="DataChannel " + BROWSER;
    sDashboard = sSourceDashboard + "_BVDDataChannel_" + BROWSER;
    sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
    OS = System.getProperty("os.name").toLowerCase();
    driver = UiTestWebDriver.getWebDriver();
    driver.get(URL);
    initPages();
    if(OS.contains("win")){
      sDashboardPath = new String("C:\\tmp\\" + sDashboard + ".svg");
    } else {
      sDashboardPath = new String("/tmp/" + sDashboard + ".svg");
    }
    try
    {
      //client = new BvdRestClient(PROXY_HOST, PROXY_PORT);
      client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
    } catch (Exception e)
    {
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
  public void copyTestFiles() {
    result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg",  sDashboardPath);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 30)
  public void getApiKeyWS()
  {
    result = client.userWS.getApiKey();
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 40)
  public void sendArrayData() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    key = result.get("out");
    json = "[{\"browser\":\"" + BROWSER + "\"," +
          "\"omiSystem\":\"tcpd123.deu.hp.com\"," +
          "\"viewName\":\"OprSample\"," +
          "\"ciId\":\"c51a039f38fe4b1db16d23181ce869c2\"," +
          "\"ciName\":\"Gold ESS\"," +
          "\"ciType\":\"BusinessService\"," +
          "\"kpiName\":\"System Performance\"," +
          "\"kpiId\":1002," +
          "\"status\":20}," +
          "{\"browser\":\"" + BROWSER + "\"," +
          "\"omiSystem\":\"tcpd123.deu.hp.com\"," +
          "\"viewName\":\"OprSample\"," +
          "\"ciId\":\"c51a039f38fe4b1db16d23181ce869c2\"," +
          "\"ciName\":\"Gold ESS\"," +
          "\"ciType\":\"BusinessService\"," +
          "\"kpiName\":\"System Performance\"," +
          "\"kpiId\":1002," +
          "\"status\":20}]";

    result = client.receiverWS.bvdSendDataArray(key, dims, tags, json);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 50)
  public void openManageDashboards()
  {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 60)
  public void uploadDashboard()
  {
    result = manageDashboardsPage.uploadDashboard(sDashboardPath);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 70)
  public void selectWidget()
  {
    result = editDashboardPage.selectWidget(sWidget);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 80)
  public void checkDataChannel()
  {
    result = editDashboardPage.getDataChannels();
    Result.outContains(result, sDataChannel);
  }

  @Test(priority = 90)
  public void cancelUploadDashboard()
  {
    result = editDashboardPage.cancelAndDiscardChanges();
    Result.rcEquals(result, "0");
  }


  @Test(priority = 145)
  public void sendArrayDataSlashTest() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "[{\"loc\":\"loc1\"," +
          "\"site\":\"site1\"," +
          "\"devant/apres\":\"with slash\", " +
          "\"dim1\":\"1\"," +
          "\"dim2\":\"2\"," +
          " \"dim3\":\"3\"," +
          " \"dim4\":\"8\"}]";

    result = client.receiverWS.bvdSendDataArrayQuery(get_key(), dim_slash,
          tag_slash,
          json);
    Result.rcEquals(result, "0");
    
  }

  @Test(priority = 150)
  public void openManageDashboards2()
  {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 160)
  public void uploadDashboard2()
  {
    result = manageDashboardsPage.uploadDashboard(sDashboardPath);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 170)
  public void selectWidget2()
  {
    result = editDashboardPage.selectWidget(sWidgetBars);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 185)
  public void setSlashDataChannel()
  {
    result = editDashboardPage.setDataChannel(slashDataChannel);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 190)
  public void setDataFields()
  {
    result = editDashboardPage.setDataFieldMultiSelectTypeText(dimsList);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 200)
  public void saveDashboard()
  {
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 210)
  public void sendArrayDataSlashTestUpdate() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    key = result.get("out");
    json = "[{\"loc\":\"loc1\"," +
          "\"site\":\"site1\"," +
          "\"devant/apres\":\"with slash\", " +
          "\"dim1\":\"2\"," +
          "\"dim2\":\"4\"," +
          " \"dim3\":\"6\"," +
          " \"dim4\":\"16\"}]";

    result = client.receiverWS.bvdSendDataArrayQuery(get_key(), dim_slash,
          tag_slash,
          json);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 220)
  public void viewDashboard()
  {
    result = mainPage.viewDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 230)
  public void checkChartNumber()
  {
    result = mainPage.getChartNumbers(sWidgetBars);
    Result.outEquals(result, lChartNumber.toString());
  }

  @Test(priority = 240)
  public void reOpenManageDashboards()
  {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 250)
  public void editDashboards()
  {
    result = manageDashboardsPage.openEditDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 260)
  public void selectWidgetForChannelDeletionCheck()
  {
    result = editDashboardPage.selectWidget(sWidgetBars);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 280)
  public void deleteChannel()
  {
    result = editDashboardPage.deleteChannel();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 290)
  public void saveDashboardAfterChannelDeletion()
  {
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 300)
  public void reOpenSavedDashboard()
  {
    result = manageDashboardsPage.openEditDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 310)
  public void selectWidgetWithDeletedChannel()
  {
    result = editDashboardPage.selectWidget(sWidgetBars);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 320)
  public void checkChannelWasDeleted()
  {
    result = editDashboardPage.getChannelsVisibility();
    Result.rcEquals(result, "0");

  }


  @Test(priority = 321)
  public void logOutFromUser() {
    result = mainPage.logout();
  }

  @Test(priority = 323)
  public void loginIntoAdmin() {
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 325)
  public void loginCheck()
  {
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 330)
  public void deleteRole()
  {
    result = client.userWS.deleteRole(roleId);
    Result.rcEquals(result,"0");
  }

  @Test(priority = 340)
  public void deleteDashboard() {
    result = client.dashboardWS.deleteDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }

  
  @AfterClass
  public void cleanup()
  {
    driver.quit();
  }
  
}