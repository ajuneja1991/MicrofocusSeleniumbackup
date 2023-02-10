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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class BackgroundColor
{
  private LoginPage loginPage;
  private MainPage mainPage;
  private ManageDashboardsPage manageDashboardsPage;
  private EditDashboardPage editDashboardPage;
  private Map<String, String> result;
  private String sDefaultBackgroundColor = "#44E80E";
  private String sBackgroundColor = "#614767";
  //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;
  private BvdWsClient client;
  private WebDriver driver;
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
  private String sSourceDashboard = "sample_dashboard_test_bg";
  private String sDashboard = "";
  private String sDashboardPath="";
  private String userName = "TesterforBackgroundColour";
  private String userLogin;
  private String userPwd = "Test@123";
  private String roleName = "testforbackground";
  private String desc = "For tester Backround";
  private String category = "All";
  private String accessType = "full-control";
  String roleId;

  private void initPages()
  {
    loginPage = PageFactory.initElements(driver, LoginPage.class);
    mainPage = PageFactory.initElements(driver, MainPage.class);
    manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
    editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
  }

  @BeforeClass
  public void setup()
  {
    cfg = new TestConfig().getConfig();
    BROWSER=cfg.getString("application.bvd.browser.name");
    BVD_USR = cfg.getString("application.bvd.user.adminusr");
    BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
    URL=cfg.getString("application.bvd.url.external");
    sDashboard=sSourceDashboard + "_BackgroundColor_" + BROWSER;
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
  public void copyTestFiles()
  {
    result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
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


  @Test(priority = 90)
  public void saveDashboard()
  {
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 110)
  public void viewDashboard()
  {
    result = mainPage.viewDashboardNew(sDashboard);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 120)
  public void checkDefaultBackgroundColor()
  {
    result = mainPage.getBackgroundColor();
    Result.outEquals(result, sDefaultBackgroundColor);
  }

  @Test(priority = 200)
  public void openManageDashboardsToReplaceDashboard()
  {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 210)
  public void openEditDashboardToReplaceDashboard()
  {
    result = manageDashboardsPage.openEditDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 230)
  public void replaceDashboard()
  {
    result = editDashboardPage.replaceDashboard(sDashboardPath);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 240)
  public void saveReplaceDashboard()
  {
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 250)
  public void viewDashboardReplaceDashboard()
  {
    result = mainPage.viewDashboardNew(sDashboard);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 260)
  public void checkBackgroundColorReplaceDashboard()
  {
    result = mainPage.getBackgroundColor();
    Result.outEquals(result, sDefaultBackgroundColor);
  }

  @Test(priority = 270)
  public void openManageDashboardsToDelete()
  {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 280)
  public void deleteDashboard()
  {
    result = client.dashboardWS.deleteDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 300)
  public void createRoleForBackgroundcolor() {
    result = client.userWS.createRole(roleName, desc, category,accessType);
    Result.rcEquals(result, "0");
    roleId = result.get("out");
  }

  @Test(priority = 310)
  public void logOutFromAdmin() {
    result = mainPage.logout();
  }


  @Test(priority = 320)
  public void loginAsNewUser() {
    driver.get(URL);
    result = loginPage.login(userName, userPwd);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 330)
  public void checkLogin()
  {
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 340)
  public void copyTestFilesForNonAdmin()
  {
    result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 350)
  public void openManageDashboardsForNonAdmin()
  {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 360)
  public void uploadDashboardForNonAdmin()
  {
    result = manageDashboardsPage.uploadDashboard(sDashboardPath);
    Result.rcEquals(result, "0");
  }


  @Test(priority = 370)
  public void saveDashboardForNonAdmin()
  {
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 380)
  public void viewDashboardForNonAdmin()
  {
    result = mainPage.viewDashboardNew(sDashboard);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 390)
  public void checkDefaultBackgroundColorForNonAdmin()
  {
    result = mainPage.getBackgroundColor();
    Result.outEquals(result, sDefaultBackgroundColor);
  }


  @Test(priority = 590)
  public void deleteDashboardForNonAdmin()
  {
    result = client.dashboardWS.deleteDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }


  @Test(priority = 595)
  public void testLogout() {
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }


  @Test(priority = 600)
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
