package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

public class MultipleFeeds
{

  private LoginPage loginPage;
  private MainPage mainPage;
  private ManageDashboardsPage manageDashboardsPage;
  private EditDashboardPage editDashboardPage;
  private WebDriver driver;
  private Map<String, String> result;
  private String dims = "browser";
  private String dims2 = "browser";
  private String tags = "FirstMultiFeed";
  private String tags2 = "SecondMultiFeed";
  private BvdWsClient client;
  private String json;
  private String feed1Text = "Feed1";
  private String feed2Text = "Feed2";
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
  private String sSourceDashboard = "multiFeeds";
  private String sDashboard;
  private String sDashboardPath;
  private String sDataChannel1;
  private String sDataChannel2;
  //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;
  private String[] feed1Widgets = {"shape1", "shape3", "shape4", "shape5", "shape6" };
  private String[] feed2Widgets = {"shape8", "shape9", "shape10", "shape11", "shape13" };
  private String userName = "Testerformultiplefeed";
  private String userLogin;
  private String userPwd = "Test@123";
  private String roleName = "testmultiplefeed";
  private String desc = "For mutiple feed tester";
  private String category = "All";
  private String accessType = "full-control";
  String roleId;



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
    sDataChannel1 = tags + " " + BROWSER;
    sDataChannel2 = tags2 + " " + BROWSER;
    driver = UiTestWebDriver.getWebDriver();
    driver.get(URL);
    initPages();
    try {
      client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test(priority = 0)
  public void idmlogin() {
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }
  @Test(priority = 1)
  public void login_check()
  {
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
  }


  @Test(priority = 2)
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
  public void sendDataFeed1() throws JSONException, UnrecoverableKeyException,
          NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{\"time\":1437633749317," +
            "\"browser\":\"" + BROWSER + "\"," +
            "\"title\":\"Feed1\"," +
            "\"link\":\"http://rss.cnn.com/~r/rss/cnn_latest/~3/HgufPus_pOs/index.html\"}";

    result = client.receiverWS.bvdSendData(get_key(), dims, tags, json);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 50)
  public void sendDataFeed2() throws JSONException, UnrecoverableKeyException,
          NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{\"time\":1437633749317," +
            "\"browser\":\"" + BROWSER + "\"," +
            "\"title\":\"Feed2\"," +
            "\"link\":\"http://rss.cnn.com/~r/rss/cnn_latest/~3/HgufPus_pOs/index.html\"}";

    result = client.receiverWS.bvdSendData(get_key(), dims2, tags2, json);
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

  @Test(priority = 75)
  public void assignDataFeed1() {
    for (int i = 0; i < feed1Widgets.length ; i++ ){
      editDashboardPage.selectWidget(feed1Widgets[i]);
      result =  editDashboardPage.setDataChannel(sDataChannel1);
      Result.rcEquals(result, "0");
    }
  }

  @Test(priority = 80)
  public void assignDataFeed2() {
    for (int i = 0; i < feed2Widgets.length ; i++ ){
      editDashboardPage.selectWidget(feed2Widgets[i]);
      result =  editDashboardPage.setDataChannel(sDataChannel2);
      Result.rcEquals(result, "0");
    }
  }

  @Test(priority = 90)
  public void saveDashboard() {
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }


  @Test(priority = 110)
  public void viewDashboard() {
    result = mainPage.viewDashboardNew(sDashboard);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 120)
  public void checkFeed() {
    for (int i = 0; i < feed1Widgets.length ; i++ ){
      result = mainPage.getFeed(feed1Widgets[i]);
      Result.outContains(result, feed1Text);
    }

  }

  @Test(priority = 130)
  public void checkFeed2() {
    for (int i = 0; i < feed2Widgets.length ; i++ ){
      result = mainPage.getFeed(feed2Widgets[i]);
      Result.outContains(result, feed2Text);
    }

  }

  @Test(priority = 140)
  public void openManageDashboardsToDelete() {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 150)
  public void deleteDashboard() {
    result = manageDashboardsPage.deleteDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 170)
  public void createRole() {
    result = client.userWS.createRole(roleName, desc, category,accessType);
    Result.rcEquals(result, "0");
    roleId = result.get("out");
  }

  @Test(priority = 180)
  public void logOutFromAdmin()
  {
    result = mainPage.logout();
  }


  @Test(priority = 190)
  public void loginAsNewUser() {
    driver.get(URL);
    result = loginPage.login(userName, userPwd);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 200)
  public void checkLogin()
  {
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 210)
  public void openManageDashboardsForNonAdmin() {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 220)
  public void uploadDashboardForNonAdmin() {
    result = manageDashboardsPage.uploadDashboard(sDashboardPath);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 230)
  public void assignDataFeed1ForNonAdmin() {
    for (int i = 0; i < feed1Widgets.length ; i++ ){
      editDashboardPage.selectWidget(feed1Widgets[i]);
      result =  editDashboardPage.setDataChannel(sDataChannel1);
      Result.rcEquals(result, "0");
    }
  }

  @Test(priority = 240)
  public void assignDataFeed2ForNonAdmin() {
    for (int i = 0; i < feed2Widgets.length ; i++ ){
      editDashboardPage.selectWidget(feed2Widgets[i]);
      result =  editDashboardPage.setDataChannel(sDataChannel2);
      Result.rcEquals(result, "0");
    }
  }

  @Test(priority = 250)
  public void saveDashboardForNonAdmin() {
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }


  @Test(priority = 260)
  public void viewDashboardForNonAdmin() {
    result = mainPage.viewDashboardNew(sDashboard);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 270)
  public void checkFeedForNonAdmin() {
    for (int i = 0; i < feed1Widgets.length ; i++ ){
      result = mainPage.getFeed(feed1Widgets[i]);
      Result.outContains(result, feed1Text);
    }

  }

  @Test(priority = 280)
  public void checkFeed2ForNonAdmin() {
    for (int i = 0; i < feed2Widgets.length ; i++ ){
      result = mainPage.getFeed(feed2Widgets[i]);
      Result.outContains(result, feed2Text);
    }

  }

  @Test(priority = 290)
  public void openManageDashboardsToDeleteForNonAdmin() {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 300)
  public void deleteDashboardForNonAdmin() {
    result = manageDashboardsPage.deleteDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 310)
  public void logOutFromUser()
  {
    result = mainPage.logout();
  }

  @Test(priority = 320)
  public void loginIntoAdmin() {
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }
  @Test(priority = 330)
  public void loginCheck()
  {
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 340)
  public void deleteRole()
  {
    result = client.userWS.deleteRole(roleId);
    Result.rcEquals(result,"0");
  }

  @AfterClass
  public void cleanup() {
    driver.quit();
  }

}
