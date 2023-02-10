package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ChannelsPurge
{
  private LoginPage loginPage;
  private MainPage mainPage;
  private ManageDashboardsPage manageDashboardsPage;
  private EditDashboardPage editDashboardPage;
  private WebDriver driver;
  private BvdWsClient client;
  private Map<String, String> result;
  //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;
  
  
  private String json;
  private String sDataChannel ;
  private String sDataChannel2;
  private String sDataChannel3;
  private String sSourceDashboard;
  private String sDashboard ;
  private String sDashboardPath;
  private String sWidget = "shape917";
  private String sWidget2 = "shape919";
  private String sWidget3 = "shape921";
  private String sWidget4 = "group792";
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
  private String userName = "TesterChannelsPurge";
  private String userLogin;
  private String userPwd = "Test@123";
  private String roleName = "testChannelsPurge";
  private String desc = "For tester";
  private String category = "All";
  private String accessType = "full-control";
  String roleId;
  private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");
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
    sDataChannel = "1DataChannel_assigned " + BROWSER;
    sDataChannel2 = "1DataChannel_not_assigned " + BROWSER;
    sDataChannel3 = "1DataChannel_not_assigned2 " + BROWSER;
    sSourceDashboard = "sample_dashboard_test";
    sDashboard = sSourceDashboard + "_BVDDataChannel_" + BROWSER;
    sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
    driver = UiTestWebDriver.getWebDriver();
    driver.get(URL);
    initPages();
    try
    {
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
  public void copyTestFiles()
  {
    result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 30)
  public void sendChannel1() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{\"id\": 1,\n" +
          " \"data\":50,\n" +
          " \"text\":channel\n" +
          " }";
    result = client.receiverWS.bvdSendDataTagsOnly(get_key(), sDataChannel2, json);
    Result.rcEquals(result, "0");
    
  }
  
  @Test(priority = 40)
  public void sendChannel2() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{\"id\": 1,\n" +
          " \"data\":50,\n" +
          " \"text\":channel2\n" +
          " }";
    result = client.receiverWS.bvdSendDataTagsOnly(get_key(), sDataChannel3, json);
    Result.rcEquals(result, "0");
    
  }
  
  @Test(priority = 45)
  public void sendChannel3() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{\"id\": 1,\n" +
          " \"data\":50,\n" +
          " \"text\":channel3\n" +
          " }";
    result = client.receiverWS.bvdSendDataTagsOnly(get_key(), sDataChannel, json);
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
  
  }
  
  @Test(priority = 80)
  public void setChannelsForPurge()
  {
    result = editDashboardPage.selectWidget(sWidget);
    Result.rcEquals(result, "0");
    result = editDashboardPage.setDataChannel(sDataChannel);
    Result.rcEquals(result, "0");
    result = editDashboardPage.setDataField("text");
    Result.rcEquals(result, "0");
    
    result = editDashboardPage.selectWidget(sWidget2);
    Result.rcEquals(result, "0");
    result = editDashboardPage.setDataChannel(sDataChannel2);
    Result.rcEquals(result, "0");
    result = editDashboardPage.setDataField("text");
    Result.rcEquals(result, "0");
    
    result = editDashboardPage.selectWidget(sWidget3);
    Result.rcEquals(result, "0");
    result = editDashboardPage.setDataChannel(sDataChannel3);
    Result.rcEquals(result, "0");
    result = editDashboardPage.setDataField("text");
    Result.rcEquals(result, "0");
    
    
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
    
  }


  @Test(priority = 83)
  public void testLogout() {
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }


  @Test(priority = 85)
  public void deleteRole()
  {
    result = client.userWS.deleteRole(roleId);
    Result.rcEquals(result,"0");
  }

  @Test(priority = 87)
  public void idmloginadmin() {
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }
  @Test(priority = 89)
  public void login_checkadmin()
  {
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
  }


  @Test(priority = 90)
  public void purgeChannels()
  {
    
    result = mainPage.openSystemSettings();
    Result.rcEquals(result, "0");
    
    result = mainPage.clickPurgeAndClosePanel();
    Result.rcEquals(result, "0");
    
  }
  
  
  @Test(priority = 100)
  public void checkAssignedChannelsWereNotRemoved()
  {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
    
    result = manageDashboardsPage.openEditDashboard(sDashboard);
    Result.rcEquals(result, "0");
    
    result = editDashboardPage.selectWidget(sWidget4);
    Result.rcEquals(result, "0");
    
    result = editDashboardPage.getDataChannels();
    Result.outContains(result, sDataChannel);
    Result.outContains(result, sDataChannel2);
    Result.outContains(result, sDataChannel3);
  }
  
  @Test(priority = 110)
  public void removeChannelsFromFewWidgets()
  {
    result = editDashboardPage.selectWidget(sWidget2);
    Result.rcEquals(result, "0");
    result = editDashboardPage.deleteChannel();
    Result.rcEquals(result, "0");
    
    result = editDashboardPage.selectWidget(sWidget3);
    Result.rcEquals(result, "0");
    result = editDashboardPage.deleteChannel();
    Result.rcEquals(result, "0");
    
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
    
  }
  
  
  @Test(priority = 120)
  public void purgeUnusedChannels()
  {
    result = mainPage.openSystemSettings();
    Result.rcEquals(result, "0");
    
    result = mainPage.clickPurgeAndClosePanel();
    Result.rcEquals(result, "0");
    
  }
  
  @Test(priority = 130)
  public void checkChannelsWerePurged()
  {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
    
    result = manageDashboardsPage.openEditDashboard(sDashboard);
    Result.rcEquals(result, "0");
    
    result = editDashboardPage.selectWidget(sWidget);
    Result.rcEquals(result, "0");
    
    result = editDashboardPage.getDataChannels();
    Result.outContains(result, sDataChannel);
    
    
    result = editDashboardPage.selectWidget(sWidget4);
    Result.rcEquals(result, "0");
    
    result = editDashboardPage.getDataChannels();
    Result.outContains(result, sDataChannel);
    Result.outContainsNot(result, sDataChannel2);
    Result.outContainsNot(result, sDataChannel3);
    
  }
  
  @Test(priority = 140)
  public void deleteDashboard()
  {
    result = editDashboardPage.cancelAndDiscardChanges();
    Result.rcEquals(result, "0");
    
    result = manageDashboardsPage.deleteDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }




  @AfterClass
  public void cleanup()
  {
    driver.quit();
  }
  
}