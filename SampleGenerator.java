package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdJSONFileEdit.EditJSONFile;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.Cmd;
import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.common.utils.Rmi2HttpClient;
import com.hp.opr.qa.framework.githubPages.Git_ColorYourDataMainPage;
import com.hp.opr.qa.framework.githubPages.Git_NodeGeneratorPage;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.apache.commons.io.FileUtils;
import org.hornetq.utils.json.JSONException;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SampleGenerator
{

  private LoginPage loginPage;
  private MainPage mainPage;
  private ManageDashboardsPage manageDashboardsPage;
  private EditDashboardPage editDashboardPage;
  private Git_NodeGeneratorPage nodegeneratePage;
  private Git_ColorYourDataMainPage coloryourdatamainPage;
  private WebDriver driver;
  private Map<String, String> result;
  private Map<String, String> secondResult;
  private boolean notEqual = false;
  private String zipName = "ColorYourData-master.zip";
  private String sWidgetBarsChart = "group3";

  private String tempPath = "C:\\tmp\\";
  private String colurYourMasterFolder="ColorYourData-master";
  private String masterzip="ColorYourData-master.zip";
  private String sSourceDashboard = "BaseDashboard";
  private String sDashboard ;
  private String sDashboardPath;
  String key="";
  private List<String> files = Arrays.asList(masterzip);
  private BvdWsClient client;
  List<String> lDataFields = Arrays.asList("numberOfCritical", "numberOfMajor", "numberOfMinor",
          "numberOfNormal");
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
  private String ReceiverUrl;
  private String hostname="";
  private String protocol="";
  private String userName = "TesterSampleGen";
  private String userLogin;
  private String userPwd = "Test@123";
  private String roleName = "testSampleGen";
  private String desc = "For testersamplegen";
  private String category = "All";
  private String accessType = "full-control";
  String roleId;
  private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");
  Map<String, String> envVars = new HashMap();
  private Rmi2HttpClient master1;
  private String namespace;
  private void initPages()
  {
    loginPage = PageFactory.initElements(driver, LoginPage.class);
    mainPage = PageFactory.initElements(driver, MainPage.class);
    manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
    editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
    nodegeneratePage=PageFactory.initElements(driver,Git_NodeGeneratorPage.class);
    coloryourdatamainPage=PageFactory.initElements(driver,Git_ColorYourDataMainPage.class);
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
    protocol=cfg.getString("application.bvd.network.protocol");
    hostname=cfg.getString("application.bvd.network.external_access_host");
    URL=cfg.getString("application.bvd.url.external");
    ReceiverUrl=cfg.getString("application.bvd.url.externalReceiverUrl");
    sDashboard = sSourceDashboard + "_BVDBarChart_" + BROWSER;
    sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
    this.master1 = new Rmi2HttpClient(this.cfg.getString("environment.suite.rmihost.master1"));
    this.namespace = this.cfg.getString("application.suite.configuration.namespace");
    this.envVars.put("HOME", "/root");
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
  private void runSampleGenerator() throws IOException
  {
    TestUtils.sleepAndLog(2000);
    Process p = Runtime.getRuntime().exec("cmd.exe /c cd \"C:\\tmp\\ColorYourData-master\\generators\\node\\dist\" & start cmd.exe /k \"basicGenerator-win.exe -f \"C:\\tmp\\ColorYourData-master\\generators\\node\\obmConfig.json\"");
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
  public void deleteSampleGenZip() throws IOException
  {
    try
    {
      for (String targetFile : files)
      {
        File fileToDelete;
        fileToDelete = new File(tempPath + targetFile);
        if(fileToDelete.exists()){
          fileToDelete.delete();
        }

      }
      if(FileUtils.getFile(tempPath+colurYourMasterFolder).exists()) {
        FileUtils.deleteDirectory(new File(tempPath + colurYourMasterFolder));
      }
      result = ResultStore.success("Files successfully deleted");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to delete file  error: " + Misc
              .getStacktraceAsString(exc));
    }

    Result.rcEquals(result, "0");
  }

  @Test(priority = 30)
  public void downloadSampleGenTool()
  {
    result = mainPage.openResources();
    Result.rcEquals(result, "0");

    result = mainPage.clickDataGenerateLink();
    Result.rcEquals(result, "0");

    result = mainPage.switchToTab("ColorYourData/generators/node at master · MicroFocus/ColorYourData · GitHub");
    Result.rcEquals(result, "0");

    result=nodegeneratePage.clickColorYourData();
    Result.rcEquals(result, "0");

    result=coloryourdatamainPage.downloadSampleGenzipfile();
    Result.rcEquals(result,"0");

    java.io.File downloadedZip;
    downloadedZip = new java.io.File(tempPath + zipName);

    for(int i=1; i<300; i++){
      if (downloadedZip.exists()){
        System.out.println("BREAK");
        break;
      }
      else {
        System.out.println("SLEEP # " + i);
        TestUtils.sleepAndLog(2000);
      }
    }

  }


  @Test(priority = 40)
  public void copyTestFiles()
  {
    result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
    Result.rcEquals(result, "0");

  }


  @Test(priority = 50)
  public void unzipGen() throws IOException
  {

    Runtime.getRuntime().exec("\"c:\\Program Files\\7-Zip\\7z.exe\" x " + tempPath + zipName + " " +
            " " +
            "-oc:\\tmp\\");

  }


  @Test(priority = 60)
  public void getApiKeyWS() {
    TestUtils.sleep(10);
    result = client.userWS.getApiKey();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 70)
  public void sendData() throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException ,IOException, org.json.JSONException {
    key = result.get("out");
    EditJSONFile.editAPIKeyinJSON(tempPath+"ColorYourData-master\\generators\\node\\obmConfig.json","host",hostname);
    EditJSONFile.editAPIKeyinJSON(tempPath+"ColorYourData-master\\generators\\node\\obmConfig.json","port","19443");
    EditJSONFile.editAPIKeyinJSON(tempPath+"ColorYourData-master\\generators\\node\\obmConfig.json","protocol",protocol);
    TestUtils.sleep(2);
    EditJSONFile.editAPIKeyinJSON(tempPath+"ColorYourData-master\\generators\\node\\obmConfig.json","apiKey",key);
    TestUtils.sleep(2);
  }


  @Test(priority = 80)
  public void startGenerator() throws IOException
  {
    runSampleGenerator();
    TestUtils.sleep(10);

  }


  @Test(priority = 90)
  public void uploadDashboard()
  {

    result=coloryourdatamainPage.closeActiveTab();
    Result.rcEquals(result, "0");

    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");

    result = manageDashboardsPage.uploadDashboard(sDashboardPath);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 100)
  public void setDataToWidget()
  {

    result = editDashboardPage.selectWidget(sWidgetBarsChart);
    Result.rcEquals(result, "0");

    result = editDashboardPage.setDataChannel("omi mdb OBM Health Status Overall PIE");
    Result.rcEquals(result, "0");

    result = editDashboardPage.setDataFieldMultiSelect(lDataFields);
    Result.rcEquals(result, "0");

    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 110)
  public void openDashboard()
  {
    result = mainPage.viewDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 120)
  public void compareGeneratedValues()
  {
    result = mainPage.getChartNumbers(sWidgetBarsChart);
    Result.rcEquals(result, "0");
    String initialValues = result.get("out");

    for (int i = 1; i < 30; i++)
    {
      secondResult = mainPage.getChartNumbers(sWidgetBarsChart);
      Result.rcEquals(secondResult, "0");
      String changedValues = secondResult.get("out");

      if (!initialValues.equals(changedValues))
      {
        System.out.println("BREAK " + "Initial Values: " + initialValues);
        System.out.println("BREAK " + "Second Values: " + changedValues);
        notEqual = true;
        break;
      }
      else
      {
        System.out.println("SLEEP # " + i);
        TestUtils.sleepAndLog(2000);
      }
    }
    Assert.assertTrue(notEqual);
  }


  @Test(priority = 130)
  public void testLogout() {
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }


  @Test(priority = 140)
  public void deleteRole()
  {
    result = client.userWS.deleteRole(roleId);
    Result.rcEquals(result,"0");
  }



  @Test(priority = 150)
  public void deleteDashboard()
  {
    result = client.dashboardWS.deleteDashboard(sDashboard);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 160)
  public void killCMD() throws IOException
  {
    TestUtils.sleep(2);
    Runtime.getRuntime().exec("c:\\Windows\\System32\\taskkill /im basicGenerator-win.exe /f");
    Runtime.getRuntime().exec("c:\\Windows\\System32\\taskkill /F /IM cmd.exe");
  }

  @Test(priority=170)
  public void cleanChannels(){

  }


  /*@Test(priority=190)
  public void login_Linux(){
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 210)
  public void loginlinux_check()
  {
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 220)
  public void linux_purgedata()
  {
    result = mainPage.openSystemSettings();
    Result.rcEquals(result, "0");

    result = mainPage.clickPurgeAndClosePanel();
    Result.rcEquals(result,"0");

  }

  @Test(priority = 230)
  public void makedirinLinux() {
    try
    {
      String[] cmd = {"mkdir", "/tmp/ColourYourData-master"};
      Map<String, String> envVars = new HashMap<>();
      result = master1.proxy(Cmd.class).exec(cmd, envVars);
      Result.rcEquals(result, "0");
      result=ResultStore.success("ColourYourData-master dir is created");

    }
    catch (RemoteException e)
    {
      result = ResultStore.failure("ColourYourData-master dir not created\n" + Misc.getExceptionInfo(e));
    }

  }


  @Test(priority=240)
  public void sendColourYourDatatoLinux() throws IOException{

    Process p = Runtime.getRuntime().exec("cmd.exe /c cd \"C:\\tmp\" & start cmd.exe /K \"scp -r -P 42201 /cygdrive/c/tmp/ColorYourData-master/* root@tcav043.bbntc.swinfra.net:/tmp/ColourYourData-master\"");
    TestUtils.sleep(20);

  }

  @Test(priority =250)
  public void setFileExecutables()  {
    try {
      String[] cmd = {"chmod", "777", "/tmp/ColourYourData-master/generators/node/dist/basicGenerator-linux"};
      Map<String, String> envVars = new HashMap<>();
      result = master1.proxy(Cmd.class).exec(cmd, envVars);
      Result.rcEquals(result, "0");
      result = ResultStore.success("basicGenerator-linux is executable");
    }
    catch (RemoteException e)
    {
      result = ResultStore.failure("basicGenerator-linux is not executable\n" + Misc.getExceptionInfo(e));
    }
  }


  @Test(priority=260)
  public void runSampleGen_Linux() throws IOException{



    try
    {

      Process p = Runtime.getRuntime().exec("cmd.exe /c cd \"C:\\tmp\" & start cmd.exe /K \"ssh -p 42201 root@tcav043.bbntc.swinfra.net \"/tmp/ColourYourData-master/generators/node/dist/basicGenerator-linux  -f /tmp/ColourYourData-master/generators/node/obmConfig.json\"\"");
      TestUtils.sleep(10);


    }
    catch (Exception e)
    {
      result = ResultStore.failure("ColourYourData-master dir not created\n" + Misc.getExceptionInfo(e));
    }

  }

  @Test(priority = 270)
  public void uploadDashboard_linux()
  {

    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");

    result = manageDashboardsPage.uploadDashboard(sDashboardPath);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 280)
  public void setDataToWidget_linux()
  {

    result = editDashboardPage.selectWidget(sWidgetBarsChart);
    Result.rcEquals(result, "0");

    result = editDashboardPage.setDataChannel("omi mdb OBM Health Status Overall PIE");
    Result.rcEquals(result, "0");

    result = editDashboardPage.setDataFieldMultiSelect(lDataFields);
    Result.rcEquals(result, "0");

    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 290)
  public void openDashboard_linux()
  {
    result = mainPage.viewDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 300)
  public void compareGeneratedValues_linux()
  {
    result = mainPage.getChartNumbers(sWidgetBarsChart);
    Result.rcEquals(result, "0");
    String initialValues = result.get("out");

    for (int i = 1; i < 30; i++)
    {
      secondResult = mainPage.getChartNumbers(sWidgetBarsChart);
      Result.rcEquals(secondResult, "0");
      String changedValues = secondResult.get("out");

      if (!initialValues.equals(changedValues))
      {
        System.out.println("BREAK " + "Initial Values: " + initialValues);
        System.out.println("BREAK " + "Second Values: " + changedValues);
        notEqual = true;
        break;
      }
      else
      {
        System.out.println("SLEEP # " + i);
        TestUtils.sleepAndLog(2000);
      }
    }
    Assert.assertTrue(notEqual);
  }


  @Test(priority = 305)
  public void killCMDlinux() throws IOException
  {
    TestUtils.sleep(2);
    Runtime.getRuntime().exec("c:\\Windows\\System32\\taskkill /F /IM ssh.exe");
    Runtime.getRuntime().exec("c:\\Windows\\System32\\taskkill /F /IM cmd.exe");
  }


  @Test(priority = 310)
  public void testLogout_linux() {
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }
  @Test(priority = 320)
  public void deleteDashboardlinux()
  {
    result = client.dashboardWS.deleteDashboard(sDashboard);
    Result.rcEquals(result, "0");

  }



  @Test(priority = 330)
  public void deletedirinLinux() {
    try
    {
      String[] cmd = {"rm","-rf", "/tmp/ColourYourData-master"};
      Map<String, String> envVars = new HashMap<>();
      result = master1.proxy(Cmd.class).exec(cmd, envVars);
      Result.rcEquals(result, "0");
      result=ResultStore.success("ColourYourData-master dir is deleted");

    }
    catch (RemoteException e)
    {
      result = ResultStore.failure("ColourYourData-master dir not created\n" + Misc.getExceptionInfo(e));
    }

  }*/




  @AfterClass
  public void cleanup()
  {
    driver.quit();
  }

}
