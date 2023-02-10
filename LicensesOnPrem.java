package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.TestCase;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.*;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import org.apache.commons.io.IOUtils;
import org.ini4j.Ini;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eva Sokolyanskaya on 07/06/2016.
 * Automated test based on BVD - Licensing - Licenses
 */
public class LicensesOnPrem extends TestCase
{
  private Map<String, String> result;
  private LoginPage loginPage;
  private MainPage mainPage;
  private ManageDashboardsPage manageDashboardsPage;
  private EditDashboardPage editDashboardPage;
  private AboutPage aboutPage;
  
  private String dashboardFile = "sample_dashboard_test";
  private String dashboard = dashboardFile + "_BVD_License_" + BROWSER;
  private String dashboardPath = "C:\\tmp\\" + dashboard + ".svg";
  
  private WebDriver driver;
  private BvdWsClient client;
  private String type;
  
  private String configFile = "bvdconfig_example.conf";
  private int license_number_of_uploaded_dashboards;
  private int real_dashboard_number;
  
  private int instanceOn_allowed_number_of_dashboards;
  private int instantOnCapacity = 50;
  private String instantOnTemplateFile = "template_50";
  private String instantOnTemplateName = "InstantOn_" + instantOnTemplateFile + "_" + BROWSER;
  private String instantOnTemplatePath = "C:\\tmp\\" + instantOnTemplateName + ".svg";
  
  private int ultimate_allowed_number_of_dashboards;
  private String ultimateLicenseFile = "Ultimate50.dat";
  private String ultimateTemplateFile = "template_50";
  private String ultimateTemplateName = "Ultimate_" + ultimateTemplateFile + "_" + BROWSER;
  private String ultimateTemplatePath = "C:\\tmp\\" + ultimateTemplateName + ".svg";
  private int ultimateCapacity = 50;
  
  private int addon_allowed_number_of_dashboards;
//  private String addOnFile = "AddOn60.dat";
  private String addOnFile = "AddOn50.dat";
  private String addOnTemplateFile = "template_60";
  private String addOnTemplateName = "AddOn_" + addOnTemplateFile + "_" + BROWSER;
  private String addOnTemplatePath = "C:\\tmp\\" + addOnTemplateName + ".svg";
  private int addOnCapacity = 50;
  
  private String expiredLicenseFile = "Expired.dat";
  
  
  private void initPages()
  {
    loginPage = PageFactory.initElements(driver, LoginPage.class);
    mainPage = PageFactory.initElements(driver, MainPage.class);
    manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
    editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
    aboutPage = PageFactory.initElements(driver, AboutPage.class);
  }
  
  @BeforeClass
  public void setup(){
    super.setup(true);
    driver = getWebDriver();
    driver.get(EXT_BVD_URL);
    initPages();
    client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
  }
  
  @Test(priority = 10)
  public void testLogin()
  {
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 20)
  public void instantOn_getInfoFromAboutPage(){
    //This step is added to stabilize goToAboutPage
//    result = mainPage.openManageDashboards();
//    Result.rcEquals(result, "0");

    result = mainPage.goToAboutPage();
    Result.rcEquals(result, "0");

    result = aboutPage.getAllowedNumberOfDashboards();
    Result.rcEquals(result, "0");
    instanceOn_allowed_number_of_dashboards = Integer.parseInt(result.get("out"));
    Result.assertEquals(instanceOn_allowed_number_of_dashboards, instantOnCapacity);

    result = aboutPage.getNumberOfUploadedDashboards();
    Result.rcEquals(result, "0");
    license_number_of_uploaded_dashboards = Integer.parseInt(result.get("out"));

    result = aboutPage.getLicenseType();
    Result.rcEquals(result, "0");
    type = result.get("out");
    String expected = "Type: INSTANT ON";
    Result.assertEquals(type, expected);

    //TODO: remove from final version of test
    System.out.println(aboutPage.getLicenseType());
    System.out.println("instanceOn_allowed_number_of_dashboards:" + instanceOn_allowed_number_of_dashboards);
    System.out.println("license_number_of_uploaded_dashboards:" + license_number_of_uploaded_dashboards);
    System.out.println(aboutPage.getServerInfo());
  }

  @Test(priority = 30)
  public void instantOn_checkNumberOfDashboards(){
    result = mainPage.getDashboardNames();
    String[] dashboards = result.get("out").split(" ");
    real_dashboard_number = dashboards.length;
    Result.assertEquals(real_dashboard_number, license_number_of_uploaded_dashboards);
  }
   
  @Test(priority = 40)
  public void instantOn_uploadTemplate(){
    result = TestUtils.downloadResource(this.getClass(), instantOnTemplateFile + ".svg",
          instantOnTemplatePath);
    Result.rcEquals(result, "0");
    
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");

    result = manageDashboardsPage.uploadTemplate(instantOnTemplatePath);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 50)
  public void instantOn_Logout1(){
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 60)
  public void instantOn_LoginGetAlert(){
    result = loginPage.loginGetAlert1(BVD_USR, BVD_PWD);
//    result = LoginPage.login(BVD_USR , BVD_PWD);
    String expected = "Alert was present: You've exceeded the number of dashboards allowed by your BVD license! Please check the about page for details.";
    Result.outContains(result, expected);
  }

  @Test(priority = 70)
  public void instantOn_openAboutPage(){
    result = mainPage.goToAboutPage();
//    result = MainPage.openAboutPageUI();
    Result.rcEquals(result, "0");

    result = aboutPage.getNumberOfUploadedDashboards();
    Result.rcEquals(result, "0");
    license_number_of_uploaded_dashboards = Integer.parseInt(result.get("out"));
  }

  @Test(priority = 80)
  public void instantOn_checkNumberOfDashboardsAndInstances(){
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");

    result = mainPage.getDashboardNames();
    Result.rcEquals(result, "0");

    String[] dashboards = result.get("out").split(",");
    real_dashboard_number = dashboards.length;
    Result.assertEquals(real_dashboard_number, license_number_of_uploaded_dashboards);
  }

  @Test(priority = 90)
  public void instantOn_tryToUploadDashboard(){
    result = TestUtils.downloadResource(this.getClass(), dashboardFile + ".svg",
          dashboardPath);
    Result.rcEquals(result, "0");

    result = manageDashboardsPage.uploadDashboard(dashboardPath);
    Result.rcEquals(result, "0");

    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }

//  @Test(priority = 100)
//  public void instanceOn_enableTestAutomationMode()
//  {
//    result = templatePage.insertTestAutomation();
//    Result.rcEquals(result, "0");
//  }

  @Test(priority = 110)
  public void instantOn_deleteUploadedDashboard(){
    result = client.dashboardWS.deleteDashboard(dashboard);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 120)
  public void instantOn_deleteTemplate(){
    result = client.templateWS.deleteTemplate(instantOnTemplateName);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 130)
  public void instantOn_Logout2(){
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 140)
  public void instantOn_Login2(){
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 150)
  public void ultimate_uploadLicense() throws IOException{
    String bvdDataDir = BVD_BVD_CMD.getBvdDataDir();
    URL license = this.getClass().getResource(ultimateLicenseFile);
    result = BVD_FILE_TRANSFER.upload(bvdDataDir + "config/license.dat", IOUtils.toByteArray
        (license));
    Result.rcEquals(result, "0");
  }

  @Test(priority = 160, enabled = true)
  public void ultimate_prepareSilentConfigFile() throws IOException{
    String bvdDir = BVD_BVD_CMD.getBvdDir();
    result = BVD_FILE_EDIT.readAllLines(bvdDir + configFile);
    Result.rcEquals(result, "0");

    String ini = updateSilentConfigIni(result.get("out"), "create");
    result = BVD_FILE_EDIT.writeLine(bvdDir + configFile, ini);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 170)
  public void ultimate_Logout1(){
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 180, enabled = true, timeOut = 4000000)
  public void ultimate_runSilentConfiguration() throws RemoteException{
    String bvdBinDir = BVD_BVD_CMD.getBvdBinDir();
    String bvdDir = BVD_BVD_CMD.getBvdDir();
    String scriptSuffix = BVD_BVD_CMD.getBvdScriptSuffix();

    if (BVD_SYS_INFO.isWindows())
    {
      Map<String, String> envVars = new HashMap<>();
      String path = BVD_SYS_INFO.getEnvironmentVariable("PATH").get("out");
      envVars.put("PATH", "C:\\Program Files\\HP\\HP BTO Software\\bin;C:\\Program Files\\HP\\HP BTO Software\\bin\\win64;" + path);
      envVars.put("BVD_HOME", "C:\\Program Files\\Hewlett Packard Enterprise\\BVD\\bin\\\\..");
      envVars.put("OvInstallDir", "C:\\Program Files\\HP\\HP BTO Software\\");
      String[] args = {bvdBinDir + "configure" + scriptSuffix, "--configuration", bvdDir + "bvdconfig_example.conf"};
      result = BVD_CMD.exec(args, envVars);
    }
    else
    {
      String[] args = {bvdBinDir + "configure" + scriptSuffix, "--configuration", bvdDir + "bvdconfig_example.conf"};
      result = BVD_CMD.exec(args);
      
      String rc = result.get("rc");
      if (!rc.equalsIgnoreCase("0")) result = BVD_CMD.exec(args);
    }
    Result.rcEquals(result, "0");
  }

  @Test(priority = 190)
  public void ultimate_Login1(){
    TestCase.sleepAndLog(60000);
    driver.get(EXT_BVD_URL);
    TestCase.sleepAndLog(60000);
    
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 200)
  public void ultimate_getInfoFromAboutPage(){
    //This step is added to stabilise goToAboutPage
//    result = mainPage.openManageDashboards();
//    Result.rcEquals(result, "0");
    
    result = mainPage.goToAboutPage();
    Result.rcEquals(result, "0");
    
    result = aboutPage.getAllowedNumberOfDashboards();
    Result.rcEquals(result, "0");
    ultimate_allowed_number_of_dashboards = Integer.parseInt(result.get("out"));
    Result.assertEquals(ultimate_allowed_number_of_dashboards, ultimateCapacity);
    
    result = aboutPage.getNumberOfUploadedDashboards();
    Result.rcEquals(result, "0");
    license_number_of_uploaded_dashboards = Integer.parseInt(result.get("out"));
    
    result = aboutPage.getLicenseType();
    Result.rcEquals(result, "0");
    type = result.get("out");
    String expected = "Type: ULTIMATE";
    Result.assertEquals(type, expected);
    
    //TODO: remove from final version of test
    System.out.println(aboutPage.getLicenseType());
    System.out.println("ultimate_allowed_number_of_dashboards:" +
          ultimate_allowed_number_of_dashboards);
    System.out.println("license_number_of_uploaded_dashboards:" + license_number_of_uploaded_dashboards);
    System.out.println(aboutPage.getServerInfo());
  }
  
  @Test(priority = 210)
  public void ultimate_checkNumberOfDashboards(){
    result = mainPage.getDashboardNames();
    Result.rcEquals(result, "0");
    
    String[] dashboards = result.get("out").split(",");
    real_dashboard_number = dashboards.length;
    Result.assertEquals(real_dashboard_number, license_number_of_uploaded_dashboards);
  }
  
  @Test(priority = 220)
  public void ultimate_uploadTemplate(){
    result = TestUtils.downloadResource(this.getClass(), ultimateTemplateFile + ".svg",
          ultimateTemplatePath);
    Result.rcEquals(result, "0");
    
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
    
    result = manageDashboardsPage.uploadTemplate(ultimateTemplatePath);
    Result.rcEquals(result, "0");
  }
  
  
  @Test(priority = 230)
  public void ultimate_Logout2(){
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 240)
  public void ultimate_LoginGetAlert()
  {
    result = loginPage.loginGetAlert1(BVD_USR, BVD_PWD);
//    result = LoginPage.login(BVD_USR , BVD_PWD);
    String expected = "Alert was present: You've exceeded the number of dashboards allowed by your BVD license! Please check the about page for details.";
    Result.outContains(result, expected);
  }
  
  @Test(priority = 250)
  public void ultimate_openAboutPage(){
    result = mainPage.goToAboutPage();
//    result = MainPage.openAboutPageUI();
    Result.rcEquals(result, "0");
    
    result = aboutPage.getNumberOfUploadedDashboards();
    Result.rcEquals(result, "0");
    license_number_of_uploaded_dashboards = Integer.parseInt(result.get("out"));
  }
  
  @Test(priority = 260)
  public void ultimate_checkNumberOfDashboardsAndInstances(){
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
    
    result = mainPage.getDashboardNames();
    Result.rcEquals(result, "0");
    
    String[] dashboards = result.get("out").split(",");
    real_dashboard_number = dashboards.length;
    Result.assertEquals(real_dashboard_number, license_number_of_uploaded_dashboards);
  }
  
  @Test(priority = 270)
  public void ultimate_tryToUploadDashboard(){
    result = TestUtils.downloadResource(this.getClass(), dashboardFile + ".svg",
          dashboardPath);
    Result.rcEquals(result, "0");
  
    result = manageDashboardsPage.uploadDashboard(dashboardPath);
    Result.rcEquals(result, "0");
  
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }
  
//  @Test(priority = 280)
//  public void ultimate_enableTestAutomationMode()
//  {
//    result = templatePage.insertTestAutomation();
//    Result.rcEquals(result, "0");
//  }
  
  @Test(priority = 290)
  public void ultimate_deleteUploadedDashboard(){
//    result = manageDashboardsPage.deleteDashboard(dashboard);
    result = client.dashboardWS.deleteDashboard(dashboard);
    Result.rcEquals(result, "0");
  }
  
//  @Test(priority = 300, enabled = false)
//  public void ultimate_deleteTemplate()
//  {
//    result = client.templateWS.deleteTemplate(ultimateTemplateName);
//    Result.rcEquals(result, "0");
//  }
  
  @Test(priority = 310, enabled = false)
  public void ultimate_Logout3(){
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 320, enabled = false)
  public void ultimate__Login2(){
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }
  
  
  @Test(priority = 330)
  public void addon_uploadLicense() throws IOException{
    String bvdDataDir = BVD_BVD_CMD.getBvdDataDir();
    URL license = this.getClass().getResource(addOnFile);
    result = BVD_FILE_TRANSFER.upload(bvdDataDir + "config/license.dat", IOUtils.toByteArray
          (license));
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 340)
  public void addon_Logout1(){
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 350, enabled = true)
  public void addon_prepareSilentConfigFile() throws IOException{
    String bvdDir = BVD_BVD_CMD.getBvdDir();
    result = BVD_FILE_EDIT.readAllLines(bvdDir + configFile);
    Result.rcEquals(result, "0");
    
    String ini = updateSilentConfigIni(result.get("out"), "create");
    result = BVD_FILE_EDIT.writeLine(bvdDir + configFile, ini);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 360, enabled = true, timeOut = 4000000)
  public void addon_runSilentConfiguration() throws RemoteException{
    String bvdBinDir = BVD_BVD_CMD.getBvdBinDir();
    String bvdDir = BVD_BVD_CMD.getBvdDir();
    String scriptSuffix = BVD_BVD_CMD.getBvdScriptSuffix();
    
    if (BVD_SYS_INFO.isWindows())
    {
      Map<String, String> envVars = new HashMap<>();
      String path = BVD_SYS_INFO.getEnvironmentVariable("PATH").get("out");
      envVars.put("PATH", "C:\\Program Files\\HP\\HP BTO Software\\bin;C:\\Program Files\\HP\\HP BTO Software\\bin\\win64;" + path);
      envVars.put("BVD_HOME", "C:\\Program Files\\Hewlett Packard Enterprise\\BVD\\bin\\\\..");
      envVars.put("OvInstallDir", "C:\\Program Files\\HP\\HP BTO Software\\");
      String[] args = {bvdBinDir + "configure" + scriptSuffix, "--configuration", bvdDir + "bvdconfig_example.conf"};
      result = BVD_CMD.exec(args, envVars);
    }
    else
    {
      String[] args = {bvdBinDir + "configure" + scriptSuffix, "--configuration", bvdDir + "bvdconfig_example.conf"};
      result = BVD_CMD.exec(args);
    }
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 370)
  public void addon_Login1(){
    TestCase.sleepAndLog(60000);
    driver.get(EXT_BVD_URL);
    TestCase.sleepAndLog(30000);
    
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 380)
  public void addon_getInfoFromAboutPage(){
    //This step is added to stabilise goToAboutPage
//    result = mainPage.openManageDashboards();
//    Result.rcEquals(result, "0");
    
    result = mainPage.goToAboutPage();
    Result.rcEquals(result, "0");
    
    result = aboutPage.getAllowedNumberOfDashboards();
    Result.rcEquals(result, "0");
    addon_allowed_number_of_dashboards = Integer.parseInt(result.get("out"));
    Result.assertEquals(addon_allowed_number_of_dashboards, addOnCapacity + ultimateCapacity);
    
    result = aboutPage.getNumberOfUploadedDashboards();
    Result.rcEquals(result, "0");
    license_number_of_uploaded_dashboards = Integer.parseInt(result.get("out"));
    
    result = aboutPage.getLicenseType();
    Result.rcEquals(result, "0");
    type = result.get("out");
    String expected = "Type: ULTIMATE";
    Result.assertEquals(type, expected);
    
    //TODO: remove from final version of test
    System.out.println(aboutPage.getLicenseType());
    System.out.println("addon_allowed_number_of_dashboards:" +
          addon_allowed_number_of_dashboards);
    System.out.println("addon_number_of_uploaded_dashboards:" +
          license_number_of_uploaded_dashboards);
    System.out.println(aboutPage.getServerInfo());
  }
  
  @Test(priority = 390)
  public void addon_checkNumberOfDashboards(){
    result = mainPage.getDashboardNames();
    Result.rcEquals(result, "0");
    
    String[] dashboards = result.get("out").split(",");
    real_dashboard_number = dashboards.length;
    Result.assertEquals(real_dashboard_number, license_number_of_uploaded_dashboards);
  }
  
  @Test(priority = 400)
  public void addon_uploadTemplate(){
    result = TestUtils.downloadResource(this.getClass(), addOnTemplateFile + ".svg",
          addOnTemplatePath);
    Result.rcEquals(result, "0");
    
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
    
    result = manageDashboardsPage.uploadTemplate(addOnTemplatePath);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 410)
  public void addon_Logout2(){
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 420)
  public void addon_LoginGetAlert(){
    result = loginPage.loginGetAlert1(BVD_USR, BVD_PWD);
//    result = LoginPage.login(BVD_USR , BVD_PWD);
    String expected = "Alert was present: You've exceeded the number of dashboards allowed by your BVD license! Please check the about page for details.";
    Result.outContains(result, expected);
  }
  
  @Test(priority = 430)
  public void addon_openAboutPage(){
    result = mainPage.goToAboutPage();
//    result = MainPage.openAboutPageUI();
    Result.rcEquals(result, "0");
    
    result = aboutPage.getNumberOfUploadedDashboards();
    Result.rcEquals(result, "0");
    license_number_of_uploaded_dashboards = Integer.parseInt(result.get("out"));
  }
  
  @Test(priority = 440)
  public void addon_checkNumberOfDashboardsAndInstances(){
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
    
    result = mainPage.getDashboardNames();
    Result.rcEquals(result, "0");
    
    String[] dashboards = result.get("out").split(",");
    real_dashboard_number = dashboards.length;
    Result.assertEquals(real_dashboard_number, license_number_of_uploaded_dashboards);
  }
  
  @Test(priority = 450)
  public void addon_tryToUploadDashboard(){
    result = TestUtils.downloadResource(this.getClass(), dashboardFile + ".svg",
          dashboardPath);
    Result.rcEquals(result, "0");
  
    result = manageDashboardsPage.uploadDashboard(dashboardPath);
    Result.rcEquals(result, "0");
  
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }
  
//  @Test(priority = 460)
//  public void addon_enableTestAutomationMode()
//  {
//    result = templatePage.insertTestAutomation();
//    Result.rcEquals(result, "0");
//  }
  
  @Test(priority = 470)
  public void addon_deleteUploadedDashboard(){
    result = client.dashboardWS.deleteDashboard(dashboard);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 480, enabled = true)
  public void addon_deleteTemplate(){
    result = client.templateWS.deleteTemplate(addOnTemplateName);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 490)
  public void addon_Logout3(){
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 500)
  public void addon__Login2(){
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 510)
  public void expired_uploadLicense() throws IOException{
    String bvdDataDir = BVD_BVD_CMD.getBvdDataDir();
    URL license = this.getClass().getResource(expiredLicenseFile);
    result = BVD_FILE_TRANSFER.upload(bvdDataDir + "config/license.dat", IOUtils.toByteArray
          (license));
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 520, enabled = true)
  public void expired_prepareSilentConfigFile() throws IOException{
    String bvdDir = BVD_BVD_CMD.getBvdDir();
    result = BVD_FILE_EDIT.readAllLines(bvdDir + configFile);
    Result.rcEquals(result, "0");
    
    String ini = updateSilentConfigIni(result.get("out"), "create");
    result = BVD_FILE_EDIT.writeLine(bvdDir + configFile, ini);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 530)
  public void expired_Logout(){
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 540, enabled = true, timeOut = 4000000)
  public void expired_runSilentConfiguration() throws RemoteException{
    String bvdBinDir = BVD_BVD_CMD.getBvdBinDir();
    String bvdDir = BVD_BVD_CMD.getBvdDir();
    String scriptSuffix = BVD_BVD_CMD.getBvdScriptSuffix();
    
    if (BVD_SYS_INFO.isWindows())
    {
      Map<String, String> envVars = new HashMap<>();
      String path = BVD_SYS_INFO.getEnvironmentVariable("PATH").get("out");
      envVars.put("PATH", "C:\\Program Files\\HP\\HP BTO Software\\bin;C:\\Program Files\\HP\\HP BTO Software\\bin\\win64;" + path);
      envVars.put("BVD_HOME", "C:\\Program Files\\Hewlett Packard Enterprise\\BVD\\bin\\\\..");
      envVars.put("OvInstallDir", "C:\\Program Files\\HP\\HP BTO Software\\");
      String[] args = {bvdBinDir + "configure" + scriptSuffix, "--configuration", bvdDir + "bvdconfig_example.conf"};
      result = BVD_CMD.exec(args, envVars);
    }
    else
    {
      String[] args = {bvdBinDir + "configure" + scriptSuffix, "--configuration", bvdDir + "bvdconfig_example.conf"};
      result = BVD_CMD.exec(args);
    }
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 550)
  public void expired_Login(){
    TestCase.sleepAndLog(60000);
    driver.get(EXT_BVD_URL);
    TestCase.sleepAndLog(30000);
    
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 560)
  public void expired_getInfoFromAboutPage(){
    //This step is added to stabilise goToAboutPage
//    result = mainPage.openManageDashboards();
//    Result.rcEquals(result, "0");
    
    result = mainPage.goToAboutPage();
    Result.rcEquals(result, "0");
    
    result = aboutPage.getExpiredLicenseData();
    Result.rcEquals(result, "0");
    String expected = "OpsBridge Manager Ultimate, number of dashboards: 20, expired at 02/06/2016";
    String expiredLicenseText = result.get("out");
    Result.assertEquals(expiredLicenseText, expected);
    
    //TODO: remove from final version of test
    System.out.println("Expired License Information:" + expiredLicenseText);
  }

  @Test(priority = 997, enabled = true)
  public void deleteTemplate(){
    result = client.templateWS.deleteTemplate(ultimateTemplateName);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 999)
  public void testLogout(){
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }
  
  
  @AfterClass
  public void cleanup(){
    driver.quit();
  }
  
  
  private String updateSilentConfigIni(String silentConfigIni, String operation) throws IOException
  {
    String ini = "";
    try
    {
      
      Ini exampleIni = new Ini(new StringReader(silentConfigIni));
      
      Ini.Section license = exampleIni.get("License");
      license.put("file", BVD_BVD_CMD.getBvdDataDir() + "config/license.dat");
//      license.put("file", "C:\\ProgramData\\Hewlett Packard Enterprise\\BVD\\config\\license.dat");
            
      StringWriter writer = new StringWriter();
      exampleIni.getConfig().setEscape(false);
      exampleIni.store(writer);
      ini = writer.getBuffer().toString();
      
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    return ini;
  }
}