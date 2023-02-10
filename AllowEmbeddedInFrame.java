package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.*;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class AllowEmbeddedInFrame
{
  private WebDriver driver;
  String html;
  String path = "C:\\tmp\\TestBVDinFrame.html";
  private LoginPage loginPage;
  private Map<String, String> result;
  private MainPage mainPage;
  private MyAccountPage myAccountPage;
  private BvdWsClient client;
  private DataCollectorPage dataCollectorPage;
  private UserManagementPage usermanagementPage;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
  //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;
  
  
  private void initPages()
  {
    loginPage = PageFactory.initElements(driver, LoginPage.class);
    mainPage = PageFactory.initElements(driver, MainPage.class);
    myAccountPage = PageFactory.initElements(driver, MyAccountPage.class);
    dataCollectorPage=PageFactory.initElements(driver, DataCollectorPage.class);
    usermanagementPage=PageFactory.initElements(driver, UserManagementPage.class);
    client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
  }
  
  
  @BeforeClass
  public void setup() {
      cfg = new TestConfig().getConfig();
      BROWSER=cfg.getString("application.bvd.browser.name");
      BVD_USR = cfg.getString("application.bvd.user.adminusr");
      BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
      URL=cfg.getString("application.bvd.url.external");
      driver = UiTestWebDriver.getWebDriver();
      driver.get(URL);
    initPages();
  }

  @Test(priority = 0)
  public void idmlogin() {
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }
  @Test(priority = 5)
  public void login_check()
  {
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
  }
    @Test(priority = 10)
    public void createTestHtml() throws IOException
    {
        html = "<iframe id=\"bvd\" src=\"" + URL + "\" width=\"90%\"height=\"90%\"></iframe>";
        if (Files.exists(Paths.get(path))) Files.delete(Paths.get(path));
        TestUtils.writeToFile(html.getBytes(), path);
    }

    @Test(priority = 15)
    public void openSystemSettingsNegative() {
        result = mainPage.openSystemSettingsforEmbedded();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 20)
    public void checkEmbeddeinFrameNegative() {
        result = mainPage.denyEmbedded();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 25)
    public void loadHtmlnegativeTest() {
        driver.navigate().refresh();
        driver.get(path);
        result = mainPage.openSystemSettings();
        Result.rcEquals(result, "1");

    }

  @Test(priority = 30)
  public void openSystemSettings() {
      driver.navigate().refresh();
      driver.get(URL);
    result = mainPage.openSystemSettingsforEmbedded();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 40)
  public void checkEmbedded() {
    result = mainPage.setAllowEmbedded();
    Result.rcEquals(result, "0");
  }



  @Test(priority = 50)
  public void loadHtml() {
    driver.navigate().refresh();
    driver.get(path);
  }

  @Test(priority = 60)
  public void checkBVDinFrame() {
    result = loginPage.checkFrame();
    Result.rcEquals(result, "0");

  }

    @Test(priority = 70)
    public void datacollectorinFrame() {
        result = mainPage.openDataCollectors();
        Result.rcEquals(result, "0");
        result=dataCollectorPage.checkDataCollectorInFrame();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 100)
    public void logout() {
        result = mainPage.logoutinFrame();
        Result.rcEquals(result, "1");
    }

//
//  @Test(priority = 40)
//  public void re_login_check() {
//    result = loginPage.bvd_Direct_Access_LoggedIn_Check();
//    Result.rcEquals(result, "0");
//  }
//
//
//  @Test(priority = 50)
//  public void reOpenSystemSettings() {
//    result = mainPage.openSystemSettings();
//    Result.rcEquals(result, "0");
//  }
//
//  @Test(priority = 5)
//  public void checkEmbeddedDeny() {
//    result = mainPage.setAllowEmbedded();
//    Result.rcEquals(result, "0");
//  }
//
  
  @AfterClass
  public void cleanup() throws IOException
  {
    driver.quit();
    TestUtils.sleepAndLog(3000);
    Files.delete(Paths.get(path));
  }
}
