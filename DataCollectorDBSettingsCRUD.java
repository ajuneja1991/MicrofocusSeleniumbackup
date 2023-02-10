package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.DataCollectorPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataCollectorDBSettingsCRUD
{
  
  
  
  LoginPage loginPage;
  MainPage mainPage;
  ManageDashboardsPage manageDashboardsPage;
  private DataCollectorPage dataCollectorPage;
  private WebDriver driver;
  private Map<String, String> result;
  BvdWsClient client;
  private String URL="";
  private String BVD_USR="";
  private String BVD_PWD="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
  private String directLogin ="";
  private List<String> dBFields = new ArrayList<>();
  
  
  private void initPages()
  {
    loginPage = PageFactory.initElements(driver, LoginPage.class);
    mainPage = PageFactory.initElements(driver, MainPage.class);
    manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
    dataCollectorPage = PageFactory.initElements(driver, DataCollectorPage.class);
  }
  
  
  @BeforeClass
  public void setup()
  {
    cfg = new TestConfig().getConfig();
    BVD_USR = cfg.getString("application.bvd.user.adminusr");
    BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
    URL=cfg.getString("application.bvd.url.external");
    BROWSER=cfg.getString("environment.browser.name");
    directLogin=URL + "/login/" + BVD_USR + "/" + BVD_PWD;
    //super.setup(true);
    driver = UiTestWebDriver.getWebDriver();
    //driver.get(URL);
    //driver = getWebDriver();
    driver.get(directLogin);
    dBFields.add("someserver.com");
    dBFields.add("5432");
    dBFields.add("SomeDBname");
    dBFields.add("admin");
    dBFields.add("P@ssw0rd");
    dBFields.add("P@ssw0rd");
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
  public void login_check()
  {
    driver.get("https://dashboard-test.coloryourdata.io/#/dataCollector");
  }
  
  
  
  @Test(priority = 10)
  public void newDBSettingsCreation()
  {
    result = dataCollectorPage.clickDBconfiguraton();
    Result.rcEquals(result, "0");
  
    result = dataCollectorPage.fillDBSettingsFields(dBFields);
    Result.rcEquals(result, "0");
  
    result = dataCollectorPage.clickDBcancel();
    Result.rcEquals(result, "0");
    
    
  }
  
  
  @Test(priority = 20)
  public void DBSettingsRead()
  {
    result = dataCollectorPage.clickDBconfiguraton();
    Result.rcEquals(result, "0");
    
    result = dataCollectorPage.readDBSettingsFields(dBFields);
    Result.rcEquals(result, "0");
  
    result = dataCollectorPage.checkSSLselected();
    Result.rcEquals(result, "1");
    
  }
  
  
  @Test(priority = 30)
  public void DBSettingsUpdate()
  {
    dBFields.set(0, "update.com");
    dBFields.set(1, "5466");
    dBFields.set(2, "NewDbName");
    dBFields.set(3, "superadmin");
    dBFields.set(4, "111");
    dBFields.set(5, "111");
  
    result = dataCollectorPage.fillDBSettingsFields(dBFields);
    Result.rcEquals(result, "0");
  
    result = dataCollectorPage.clickDBssl();
    Result.rcEquals(result, "0");
  
    result = dataCollectorPage.clickDBcancel();
    Result.rcEquals(result, "0");
  
  }
  
  @Test(priority = 40)
  public void DBSettingsCheckUpdate()
  {
    result = dataCollectorPage.clickDBconfiguraton();
    Result.rcEquals(result, "0");
  
    result = dataCollectorPage.readDBSettingsFields(dBFields);
    Result.rcEquals(result, "0");
  
    result = dataCollectorPage.checkSSLselected();
    Result.rcEquals(result, "0");
    
    result = dataCollectorPage.clickDBcancel();
    Result.rcEquals(result, "0");
    
  }
  
}
