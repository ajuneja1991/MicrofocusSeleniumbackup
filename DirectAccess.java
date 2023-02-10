package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdUserWS;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.usermgmt.UserEditor;
import com.hp.opr.qa.framework.pageobjects.usermgmt.UsersGroupsAndRolesHomePage;
import com.hp.opr.qa.framework.pageobjects.usermgmt.UsersList;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;

import java.util.Map;

public class DirectAccess
{
  private LoginPage loginPage;
  private WebDriver driver;
  private MainPage mainPage;
  private UsersGroupsAndRolesHomePage usersGroupsAndRolesHomePage;
  private UserEditor userEditor;
  private UsersList userList;
  private BvdWsClient client;
  public BvdUserWS userWS;
  private Map<String, String> result;
  private Config cfg;
  private String userName = "TesterfordashboardCRUD";
  private String userLogin;
  private String userPwd = "Test@123";
  private String roleName = "testfordashboardcrud";
  private String desc = "For tester";
  private String category = "All";
  private String accessType = "full-control";
  private String directLogindirectaccess;
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
  String roleId;
  private void initPages()
  {

    loginPage = PageFactory.initElements(driver, LoginPage.class);
    mainPage = PageFactory.initElements(driver, MainPage.class);
    usersGroupsAndRolesHomePage = PageFactory.initElements(driver, UsersGroupsAndRolesHomePage.class);
    userEditor = PageFactory.initElements(driver, UserEditor.class);
    userList = PageFactory.initElements(driver, UsersList.class);
  }

  @BeforeClass
  public void setup()
  {
    cfg = new TestConfig().getConfig();
    BVD_USR = cfg.getString("application.bvd.user.adminusr");
    BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
    URL=cfg.getString("application.bvd.url.external");
    BROWSER=cfg.getString("environment.browser.name");
    //userName = "bvd_tester_" + BROWSER + "_" + "SA";
    directLogindirectaccess=URL + "/login/" + userName + "/" + userPwd;
    driver = UiTestWebDriver.getWebDriver();
    driver.get(URL);
    initPages();
    client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
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

  @Test(priority = 32)
  public void createRole() {
    result = client.userWS.createRole(roleName, desc, category,accessType);
    Result.rcEquals(result, "0");
    roleId = result.get("out");
  }


  /*@Test(priority = 20)
  public void openUserManagement() {
    result = mainPage.openUserManagement();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 30)
  public void openCreateNewUser() {
    result = usersGroupsAndRolesHomePage.openCreateNewUser();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 40, enabled = true)
  public void createNewUser() {
    result = userEditor.createUser(sUserName, sUserLogin, sUserPwd, sUserEmail, bUserSuperAdmin);
    Result.rcEquals(result, "0");
  }*/

  @Test(priority=50)
  public void logoutBVDUser(){
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 60)
  public void directAccess()
  {
    driver.get(directLogindirectaccess);
    loginPage = PageFactory.initElements(driver, LoginPage.class);
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");

  }
  /*@Test(priority = 70)
  public void directacceslogin_check()
  {
    loginPage = PageFactory.initElements(driver, LoginPage.class);

    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
  }*/

  @Test(priority=80)
  public void logoutCreatedUser(){
    mainPage = PageFactory.initElements(driver, MainPage.class);

    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 90)
  public void idmloginrecheck() {
    loginPage = PageFactory.initElements(driver, LoginPage.class);
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }
  @Test(priority = 100)
  public void login_checkagain()
  {
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
  }
  @Test(priority = 130)
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
