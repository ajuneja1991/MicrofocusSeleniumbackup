package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.typesafe.config.Config;

import java.util.Map;

public class Login
{
  private LoginPage loginPage;
  private MainPage mainPage;
  //private WebDriver driver;
  private Map<String, String> result;
  private Config cfg;
  private WebDriver driver;
  private String sUserName = "unknownUser";
  private String sPassword = "someWrongPassword";
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  BvdWsClient client;
  private String PROXY_HOST;
  private int PROXY_PORT;
  private String userName = "TesterforLogin";
  private String userLogin;
  private String userPwd = "Test@123";
  private String roleName = "testforlogin";
  private String desc = "For tester";
  private String category = "All";
  private String accessType = "full-control";
  String roleId;

  private void initPages()
  {
    loginPage = PageFactory.initElements(driver, LoginPage.class);
    mainPage = PageFactory.initElements(driver, MainPage.class);
  }

  @BeforeClass
  public void setup()  {

    cfg = new TestConfig().getConfig();
    BVD_USR = cfg.getString("application.bvd.user.adminusr");
    BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
    URL=cfg.getString("application.bvd.url.external");
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
  public void loginWithWrongCredentials()
  {
    result = loginPage.loginWithWrongCredentials(sUserName, sPassword);
    Result.rcEquals(result, "0");
  }


  @Test(priority = 2)
  public void testLogin()
  {
    driver.get(URL);
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 3)
  public void login_checkadmin()
  {
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 20)
  public void createRole() {
    result = client.userWS.createRole(roleName, desc, category,accessType);
    Result.rcEquals(result, "0");
    roleId = result.get("out");
  }

  @Test(priority=30)
  public void logoutcheckforadmin()
  {
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 40)
  public void testLoginnonadmin()
  {
    driver.get(URL);
    result = loginPage.login(userName, userPwd);
    Result.rcEquals(result, "0");
  }


  @Test(priority = 50)
  public void login_checkfornonadmin()
  {
    result = loginPage.idm_LoggedIn_Checkfornonadmin();
    Result.rcEquals(result, "0");
  }

  @Test(priority=60)
  public void logoutcheckfornonadmin()
  {
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }



  @Test(priority = 70)
  public void testreLoginadmin()
  {
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 80)
  public void relogin_checkadmin()
  {
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 90)
  public void deleteRole()
  {
    result = client.userWS.deleteRole(roleId);
    Result.rcEquals(result,"0");
  }

  @Test(priority=100)
  public void relogoutcheckforadmin()
  {
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }


  // change password removed to the MyAccount test

//  @Test(priority = 3)
//  public void testChangePassword()
//  {
//    result = mainPage.openMyAccount();
//    Result.rcEquals(result, "0");
//    List<WebElement> passButton = driver.findElements(By.cssSelector("#openPasswordChange.ng-hide"));
//    if (BVD_DOCKER_SETUP)
//      Result.assertTrue(passButton.size() > 0);
//    else
//      Result.assertTrue(passButton.size() == 0);
//  }

  @Test(priority =110)
  public void checkTempDir()  {
    result = ResultStore.success("TestNG Value = " + System.getProperty("java.io.tmpdir"));
    Result.rcEquals(result, "0");

    System.setProperty("java.io.tmpdir", "C:\\TEMPORATOR");

    result = ResultStore.success("New TestNG Value = " + System.getProperty
            ("java.io.tmpdir"));
    Result.rcEquals(result, "0");
  }

  @AfterClass
  public void cleanup()
  {
    driver.quit();
  }
}
