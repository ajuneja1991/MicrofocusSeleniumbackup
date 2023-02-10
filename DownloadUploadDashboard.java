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


public class DownloadUploadDashboard
{
  private LoginPage loginPage;
  private MainPage mainPage;
  private ManageDashboardsPage manageDashboardsPage;
  private EditDashboardPage editDashboardPage;
  private WebDriver driver;
  private Map<String, String> result;
  //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;
  private String sSourceDashboard = "sample_dashboard_test_791";
  private String sDashboard ;
  private String sDashboardPath;
  private BvdWsClient client;
  private String dims = "browser";
  private String tags = "DataChannel";
  private String sWidget = "group791";
  private String sDataChannel ;
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
  private String OS ;
  private String userName = "TesterDownloadUploadDash";
  private String userLogin;
  private String userPwd = "Test@123";
  private String roleName = "testDownloadUploadDash";
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
  public void setup()
  {
    cfg = new TestConfig().getConfig();
    BROWSER=cfg.getString("application.bvd.browser.name");
    BVD_USR = cfg.getString("application.bvd.user.adminusr");
    BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
    URL=cfg.getString("application.bvd.url.external");
    sDashboard = sSourceDashboard + "_" + BROWSER + "_download_upload_test";
    sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
    sDataChannel = "DataChannel " + BROWSER;
    OS=System.getProperty("os.name").toLowerCase();
    driver = UiTestWebDriver.getWebDriver();
    driver.get(URL);
    client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
    initPages();
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

  @Test(priority = 18)
  public void copyTestFiles() {
    result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
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
    String json = "[{\"browser\":\"" + BROWSER + "\"," +
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
    
    result = client.receiverWS.bvdSendDataArray(get_key(), dims, tags, json);
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
  
  @Test(priority = 200)
  public void saveDashboard()
  {
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 210)
  public void deleteChangedDashboardFile() throws IOException
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
  
  @Test(priority = 220)
  public void downloadDashboard() {
    result = manageDashboardsPage.downloadDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 230)
  public void deleteDashboard() {
    result = manageDashboardsPage.deleteDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 240)
  public void uploadDownloadedDashboard()
  {
    result = manageDashboardsPage.uploadDashboard(sDashboardPath);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 250)
  public void changeDashboardTitle()
  {
    result = editDashboardPage.changeTitle("New Dashboard Title");
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 260)
  public void saveDashboardWithNewTitle()
  {
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 270)
  public void checkDashboardIsNotTemplate()
  {
    result = manageDashboardsPage.checkDashboardIsNotTemplate(sDashboard);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 280)
  public void deleteNewDashboard() {
    result = manageDashboardsPage.deleteDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 290)
  public void deleteDashboardFile() throws IOException
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

  @Test(priority = 300)
  public void testLogout() {
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }


  @Test(priority = 310)
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
