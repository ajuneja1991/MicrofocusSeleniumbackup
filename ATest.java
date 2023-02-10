package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.pageobjects.bvd.*;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Created by Eva Sokolyanskaya on 07/06/2016.
 * Automated test based on BVD - Licensing - Licenses
 */
public class ATest
{
  Map<String, String> result;
  LoginPage loginPage;
  MainPage mainPage;
  ManageDashboardsPage manageDashboardsPage;
  EditDashboardPage editDashboardPage;
  TemplatePage templatePage;
  AboutPage aboutPage;
  String sourceDashboard = "";
  String sDashboard = "";
  String sDashboardPath = "";
  WebDriver driver;
  BvdWsClient client;
  String key;
  String json;
  String dims = "loc";
  String tags = "template";
  String type;
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
    
  private void initPages()
  {
    loginPage = PageFactory.initElements(driver, LoginPage.class);
    mainPage = PageFactory.initElements(driver, MainPage.class);
    manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
    editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
    templatePage = PageFactory.initElements(driver, TemplatePage.class);
    aboutPage = PageFactory.initElements(driver, AboutPage.class);
  }

  @BeforeClass
  public void setup()
  {
    cfg = new TestConfig().getConfig();
    BROWSER=cfg.getString("application.bvd.browser.name");
    BVD_USR = cfg.getString("application.bvd.user.adminusr");
    BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
    URL=cfg.getString("application.bvd.url.external");
    sourceDashboard = "template_50_instances";
    sDashboard = sourceDashboard + "_BVD_Licenses_" + BROWSER;
    sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
    driver = UiTestWebDriver.getWebDriver();
    driver.get(URL);
    //super.setup(true);
    //driver = getWebDriver();
    //driver.get(EXT_BVD_URL);

    initPages();
    client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
  }
  
  @Test(priority = 10)
  public void getTempDir()  {
    result = ResultStore.success("TestNG Value = " + System.getProperty("java.io.tmpdir"));
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 20)
  public void stepToBeFailed()  {
    result = loginPage.login(BVD_USR , BVD_PWD);
    Result.rcEquals(result, "1");
  }
  
  @Test(priority = 50)
  public void setTempDir()  {
    System.setProperty("java.io.tmpdir", "C:\\TEMPORATOR");
  
    result = ResultStore.success("New TestNG Value = " + System.getProperty
          ("java.io.tmpdir"));
    Result.rcEquals(result, "0");
    
  }
  
  @Test(priority = 60)
  public void stepToBeFailedToCheck()  {
    result = loginPage.login(BVD_USR , BVD_PWD);
    Result.rcEquals(result, "1");
  }
  
  @AfterClass
  public void cleanup()
  {
    driver.quit();
  }
}