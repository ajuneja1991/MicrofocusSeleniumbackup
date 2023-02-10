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
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.*;

public class ImportExportTool
{

  private LoginPage loginPage;
  private MainPage mainPage;
  private ManageDashboardsPage manageDashboardsPage;
  private EditDashboardPage editDashboardPage;
  private WebDriver driver;
  private Map<String, String> result;
  private Map<String, String> secondResult;
  private String sWidget = "group17";
  private String sWidgetBarsChart = "group456";
  private List<String> lChartNumbersDefault = Arrays.asList("71", "45","45","34");
  private String tempPath = "C:\\tmp\\";
  private String zipName = "bvd-cli.zip";
  private String windowsTool = "bvd-cli-win.exe";
  private String linuxTool = "bvd-cli-linux";
  private String macOsTool = "bvd-cli-macos";
  private String exportShortArguments = "short.bvd";
  private String exportLongArguments = "long.bvd";
  private String exportUseConfig = "config.bvd";
  private String configExport = "export.conf";
  private String batchRun = "exportConf.bat";
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BVD_PORT="";
  private String BVD_HOSTNAME="";
  private String BROWSER="";
  private int PROXY_PORT;
  private String OS ;
  private String ImportDashboard="ImportDashboard.bat";
  private String ExportDashboard="ExportDashboard.bat";
  private String inputText="input.txt";
  private String userName = "Testerimportexporttool";
  private String userLogin;
  private String userPwd = "Test@123";
  private String roleName = "testimportexporttool";
  private String desc = "For ImportExportTool";
  private String category = "All";
  private String accessType = "full-control";
  private String sLinkToDashboard="Welcome";
  private String hyperlinkurl="";
  private String hyperlinkdashboard="";
  private String BaseurlofExpImpToolTest="";

  String roleId;
  private List<String> textElemsInWidgetwoTitlesolditem = new ArrayList<>();
  private List<String> textElemsInWidgetwotitlelocation = new ArrayList<>();
  private ArrayList<String> allFiles = new ArrayList<>();
  private BvdWsClient client;
  private String sSourceDashboard = "ExpImpToolDashAuto";
  private String sDashboard ;
  private String sDashboardPath;


  private String[] listofcateg={"alpha","alph%a","alph:a","alph,a","alph;a"};

  private String dashboardExportFile;
  private Set<String> categorylist=new HashSet<String>(Arrays.asList(listofcateg));

  private List<String> toolFiles = Arrays.asList(zipName, windowsTool, linuxTool, macOsTool);
  private List<String> testFiles = Arrays.asList(exportShortArguments, exportLongArguments, exportUseConfig, configExport, batchRun, dashboardExportFile,ImportDashboard,ExportDashboard,inputText);

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

  private static String getFileContent(String filePath)
  {
    String content = "";
    try
    {
      content = new String(Files.readAllBytes(Paths.get(filePath)));
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    return content;
  }

  private String defaultWidgetJson()
  {
    return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"unchanged\"," +
            "\"tags\":[\"DefaultWidgetQuery\"],\"dims\":[],\"queryText\":\"SELECT * FROM public" +
            ".bvd_automation2 where id = 1\",\"availableColumns\":[\"Data1\",\"Data2\",\"Data3\"," +
            "\"Data4\",\"id\"],\"sampleQueryResult\":{\"Data1\":10,\"Data2\":20,\"Data3\":30," +
            "\"Data4\":40,\"id\":1}},\"name\":\"AutoDefaultWidgetQuery\"}}";
  }

  private String groupWidgetJson()
  {
    return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"groupwidget\"," +
            "\"tags\":[\"GroupWidgetQuery\"],\"dims\":[],\"queryText\":\"select location as ID, " +
            "location, sum(sold_item) as sold_items from bvd_lwr_demo group by location order by " +
            "sold_items desc limit 2\",\"availableColumns\":[\"ID\",\"location\",\"sold_items\"]," +
            "\"sampleQueryResult\":{\"ID\":\"Stuttgart\",\"location\":\"Stuttgart\"," +
            "\"sold_items\":141}},\"name\":\"AutoGroupWidgetQuery\"}}";
  }

  private void executeCommand(String[] commands) throws IOException
  {

    Runtime rt = Runtime.getRuntime();
    Process proc = rt.exec(commands);


    BufferedReader stdInput = new BufferedReader(new
            InputStreamReader(proc.getInputStream()));

    BufferedReader stdError = new BufferedReader(new
            InputStreamReader(proc.getErrorStream()));

    Reporter.log("Here is output for "+Arrays.toString(commands)+":\n");
    String s = null;
    while ((s = stdInput.readLine()) != null) {
      Reporter.log(s+"\n");
    }

    Reporter.log("Here is errors for "+Arrays.toString(commands)+":\n");
    while ((s = stdError.readLine()) != null) {
      Reporter.log(s+"\n");


    }
  }


  @BeforeClass
  public void setup()
  {
    cfg = new TestConfig().getConfig();
    BROWSER=cfg.getString("application.bvd.browser.name");
    BVD_USR = cfg.getString("application.bvd.user.adminusr");
    BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
    URL=cfg.getString("application.bvd.url.external");
    hyperlinkurl=URL+"/#/show/Welcome?params=none";
    hyperlinkdashboard=URL+"/#/show/Welcome?params=none";
    BaseurlofExpImpToolTest=URL+"/#/show/ExpImpToolDashAuto_chrome?params=none";
    BVD_PORT=cfg.getString("application.bvd.network.webserver_port");
    BVD_HOSTNAME=cfg.getString("environment.suite.hostname.master1");
    sDashboard = sSourceDashboard + "_" + BROWSER;
    sDashboardPath = tempPath + sDashboard + ".svg";
    dashboardExportFile = sDashboard+".bvd";
    driver = UiTestWebDriver.getWebDriver();
    driver.get(URL);
    initPages();
    allFiles.addAll(toolFiles);
    allFiles.addAll(testFiles);
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


  @Test(priority = 3)
  public void initialClean()
  {
    try
    {
      for (String targetFile : allFiles)
      {
        java.io.File fileToDelete;
        fileToDelete = new java.io.File(tempPath + targetFile);
        fileToDelete.delete();
      }
      result = ResultStore.success("File(s) successfully deleted");
      Result.rcEquals(result, "0");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to delete file  error: " + Misc.getStacktraceAsString(exc));
    }

    Result.rcEquals(result, "0");
  }




  @Test(priority = 5)
  public void copyTestFiles()
  {
    result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg",
            sDashboardPath);
    result = TestUtils.downloadResource(this.getClass(), ImportDashboard,
            tempPath+ImportDashboard);
    result = TestUtils.downloadResource(this.getClass(), ExportDashboard,
            tempPath+ExportDashboard);
    result = TestUtils.downloadResource(this.getClass(), inputText,
            tempPath+inputText);

    Result.rcEquals(result, "0");
  }

  @Test(priority = 10)
  public void downloadCliTool()
  {
    result = mainPage.openResources();
    Result.rcEquals(result, "0");

    result = mainPage.pageScrollView();
    Result.rcEquals(result, "0");

    result = mainPage.downloadResource(zipName);
    Result.rcEquals(result, "0");

    java.io.File downloadedZip;
    downloadedZip = new java.io.File(tempPath + zipName);

    for(int i=1; i<150; i++){
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



  @Test(priority = 20)
  public void unzipTool() throws IOException
  {
    Runtime.getRuntime().exec("\"c:\\Program Files\\7-Zip\\7z.exe\" x " + tempPath + zipName + " " +
            "-oc:\\tmp\\");
    TestUtils.sleepAndLog(4000);
  }


  @Test(priority = 22)
  public void createRole() {
    result = client.userWS.createRole(roleName, desc, category,accessType);
    Result.rcEquals(result, "0");
    roleId = result.get("out");
  }

  @Test(priority = 30)
  public void checkFilesExist() throws IOException
  {
    try
    {
      for (String targetFile : toolFiles)
      {
        java.io.File fileToCheck;
        fileToCheck = new java.io.File(tempPath + targetFile);
        if(!fileToCheck.exists()){
          result = ResultStore.failure("One of files doesn't exist: " + targetFile);
          Result.rcEquals(result, "0");
        }
      }
      result = ResultStore.success("File(s) exist");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to delete file  error: " + Misc
              .getStacktraceAsString(exc));
    }

    Result.rcEquals(result, "0");

  }

  @Test(priority = 32)
  public void sendData() throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    String key = get_key();
    String json = "{ \n" +
            "                \"items\" : [{  \n" +
            "                                \"id\": 1,\n" +
            "                                \"value_1\":71,\n" +
            "                                \"value_2\":45,\n" +
            "                                \"value_3\":45,\n" +
            "                                \"value_4\":34,\n" +
            "                                \"status\":\"red\"\n" +
            "                } ]        }";

    result = client.receiverWS.bvdSendDataTagsOnly(key, "Test3", json);
    Result.rcEquals(result, "0");
    result = client.receiverWS.bvdSendDataTagsOnly(key, "Test4", json);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 40)
  public void createInitialQueryDefault() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException
  {
    result = client.collectorWS.bvdDataCollectorCreateQuery(defaultWidgetJson());
    Result.rcEquals(result, "0");
  }

  @Test(priority = 50)
  public void createInitialQueryGroup() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException
  {
    result = client.collectorWS.bvdDataCollectorCreateQuery(groupWidgetJson());
    Result.rcEquals(result, "0");
  }


  @Test(priority = 60)
  public void uploadDashboard()
  {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");

    result = manageDashboardsPage.uploadDashboard(sDashboardPath);
    Result.rcEquals(result, "0");

    result=editDashboardPage.assignNewMenuCategory("test Menu");
    Result.rcEquals(result,"0");

    result = editDashboardPage.assignNewCategoryMultiple("alpha");
    Result.rcEquals(result, "0");

    result = editDashboardPage.assignNewCategoryMultiple("alph%a");
    Result.rcEquals(result, "0");

    result = editDashboardPage.assignNewCategoryMultiple("alph:a");
    Result.rcEquals(result, "0");

    result = editDashboardPage.assignNewCategoryMultiple("alph,a");
    Result.rcEquals(result, "0");

    result = editDashboardPage.assignNewCategoryMultiple("alph;a");
    Result.rcEquals(result, "0");

    result = editDashboardPage.selectWidget("group17");
    Result.rcEquals(result, "0");

    result = editDashboardPage.modifyDataChannelSetNewChannel("Test4");
    Result.rcEquals(result, "0");

    String[] dataFields = new String[] {"items/0/value_1", "items/0/value_2", "items/0/value_3", "items/0/value_4"};
    result = editDashboardPage.setDataFieldMultiSelect(Arrays.asList(dataFields));
    Result.rcEquals(result, "0");

    result = editDashboardPage.selectWidget("group55");
    Result.rcEquals(result, "0");

    result = editDashboardPage.selectWidget("shape50");
    Result.rcEquals(result, "0");

    result = editDashboardPage.setHyperlinkToAnotherDashboard_passparams(sLinkToDashboard,false);
    Result.rcEquals(result, "0");

    result = editDashboardPage.apply();
    Result.rcEquals(result, "0");

    result = editDashboardPage.selectWidget("shape51");
    Result.rcEquals(result, "0");

    result = editDashboardPage.setHyperlinkToUrl(hyperlinkurl);
    Result.rcEquals(result, "0");

    result = editDashboardPage.save();
    Result.rcEquals(result, "0");

  }

   @Test(priority = 70)
   public void viewDashboard() {
     result = mainPage.viewDashboardwithMenu("test Menu",sDashboard);
     Result.rcEquals(result, "0");
   }

  @Test(priority = 80)
  public void checkData() {
    result = mainPage.getChartNumbers(sWidget);
    Result.outEquals(result, lChartNumbersDefault.toString());

  }

  @Test(priority = 90)
  public void checkTextWidgetsInGroupWithOutTitle()
  {

    textElemsInWidgetwotitlelocation.add("Stuttgart");
    textElemsInWidgetwotitlelocation.add("Jakarta");

    textElemsInWidgetwoTitlesolditem.add("141");
    textElemsInWidgetwoTitlesolditem.add("139");
    Assert.assertEquals(mainPage.getGroupWidgetCount(), 2);

    result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwotitlelocation,"shape50");
    Result.rcEquals(result, "0");

    result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwoTitlesolditem,"shape51");
    Result.rcEquals(result, "0");
  }

  @Test(priority = 100)
  public void checkHyperlinktodashboardpresentonWidget() {

    result = mainPage.clickOnHyperLink("g[id$='Stuttgart-shape50'] text");
    Result.rcEquals(result, "0");

    result = mainPage.getDashboard();
    Result.rcEquals(result, "0");
    Result.outContains(result, sLinkToDashboard);
    // string compare

  }

  @Test(priority = 110)
  public void back() {

      result = mainPage.viewDashboardwithMenu("test Menu",sDashboard);
      Result.rcEquals(result, "0");

  }

  @Test(priority = 120)
  public void checkHyperlinktourl() {

      result = mainPage.clickOnHyperLink("g[id$='Stuttgart-shape51'] text");
      Result.rcEquals(result, "0");

    if (driver.getCurrentUrl().equals(hyperlinkurl)) {
      result.put("out", "Hyperlink to Url " + hyperlinkurl + " is working");
    } else {
      result = ResultStore.failure("Hyperlink to Url " + hyperlinkurl + " is not working, instead " + result.get("out") + " is shown");
    }

    }


  @Test(priority = 130)
  public void backafterclickingonurl() {
      result = mainPage.viewDashboardwithMenu("test Menu",sDashboard);
      Result.rcEquals(result, "0");
  }

  @Test(priority = 140)
  public void editdashboard() {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");

  }

  @Test(priority = 150)
  public void hidedashboardMethod()throws ParseException
  {
    result = manageDashboardsPage.hideInMenuDashboard(sDashboard);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 160)
  public void validatehidedashboard() {
    result = mainPage.checkDashboardHiddenfromViewDashboard(sDashboard);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 170)
  public void exportDashboards() throws IOException, InterruptedException
  {

    String[] exportCommand={tempPath+ ExportDashboard,BVD_USR,BVD_PWD,BVD_HOSTNAME,BVD_PORT};
    executeCommand(exportCommand);

  }


  @Test (priority = 180)
  public void modifyDashboard()
  {
    driver.navigate().refresh();

    result = manageDashboardsPage.showInMenuDashboard(sDashboard);
    Result.rcEquals(result, "0");

    TestUtils.sleep(3);

    manageDashboardsPage.checkDashboardShowbtnactive(sDashboard);
    Result.rcEquals(result, "0");

    result = manageDashboardsPage.openEditDashboard(sDashboard);
    Result.rcEquals(result, "0");

    result = editDashboardPage.removeMenuCategory("test Menu");
    Result.rcEquals(result, "0");

    result = editDashboardPage.removeCategoryAssignment("alph,a");
    Result.rcEquals(result, "0");

    result = editDashboardPage.removeCategoryAssignment("alpha");
    Result.rcEquals(result, "0");

    result = editDashboardPage.removeCategoryAssignment("alph%a");
    Result.rcEquals(result, "0");

    result = editDashboardPage.removeCategoryAssignment("alph;a");
    Result.rcEquals(result, "0");


    result = editDashboardPage.selectWidget("group17");
    Result.rcEquals(result, "0");

    result = editDashboardPage.modifyDataChannelSetNewChannel("Test3");
    Result.rcEquals(result, "0");

    String[] dataFields = new String[] {"items/0/value_1", "items/0/value_2", "items/0/value_3", "items/0/value_4"};
    result = editDashboardPage.setDataFieldMultiSelect(Arrays.asList(dataFields));
    Result.rcEquals(result, "0");

    result = editDashboardPage.save();
    Result.rcEquals(result, "0");

  }

  @Test(priority = 190)
  public void importDashboard() throws IOException
  {
    String[] importCommand={tempPath+ ImportDashboard,BVD_USR,BVD_PWD,BVD_HOSTNAME,BVD_PORT};
    executeCommand(importCommand);

    TestUtils.sleepAndLog(4000);
  }

  @Test(priority=200)
  public void logoutcheck()
  {
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 210)
  public void testLoginnonadmin()
  {
    driver.get(URL);
    result = loginPage.login(userName, userPwd);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 220)
  public void login_checkfornonadmin()
  {
    result = loginPage.idm_LoggedIn_Checkfornonadmin();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 230)
  public void checkDashboard()
  {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");

    result = manageDashboardsPage.openEditDashboard(sDashboard);
    Result.rcEquals(result, "0");

    result = editDashboardPage.getCategory();
    Result.rcEquals(result, "0");
    Assert.assertEquals(result.get("OUT"), null, "Category value isn't empty as expected");

    result = editDashboardPage.selectWidget("group17");
    Result.rcEquals(result, "0");

    result = editDashboardPage.getSelectedDataChannels();
    Result.rcEquals(result, "0");
    Assert.assertTrue(result.get("OUT").contains("Test4"), "data channel should be Test4 as just before export");

    result = editDashboardPage.cancel();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 240)
  public void checkDashboardMultipleProperty() {

    result=manageDashboardsPage.checkDashboardHiddenbtnactive(sDashboard);
    Result.rcEquals(result, "0");

    result = manageDashboardsPage.openEditDashboard(sDashboard);
    Result.rcEquals(result, "0");

    result = editDashboardPage.validateMenuCategoriesassigned("test Menu");
    Result.rcEquals(result, "0");

    result = editDashboardPage.removeMenuCategory("test Menu");
    Result.rcEquals(result, "0");

    result = editDashboardPage.validateCategoriesassignedDashboard(categorylist);
    Result.rcEquals(result, "0");

    result = editDashboardPage.removeCategoryAssignment("alph,a");
    Result.rcEquals(result, "0");

    result = editDashboardPage.removeCategoryAssignment("alpha");
    Result.rcEquals(result, "0");

    result = editDashboardPage.removeCategoryAssignment("alph%a");
    Result.rcEquals(result, "0");

    result = editDashboardPage.removeCategoryAssignment("alph:a");
    Result.rcEquals(result, "0");

    result = editDashboardPage.removeCategoryAssignment("alph;a");
    Result.rcEquals(result, "0");

    result = editDashboardPage.deleteMenuCategory("test Menu");
    Result.rcEquals(result, "0");
    TestUtils.sleep(4);

    result = editDashboardPage.deleteCategory("alph%a");
    Result.rcEquals(result, "0");
    TestUtils.sleep(4);

    result = editDashboardPage.deleteCategory("alpha");
    Result.rcEquals(result, "0");
    TestUtils.sleep(4);

    result = editDashboardPage.deleteCategory("alph,a");
    Result.rcEquals(result, "0");
    TestUtils.sleep(4);

    result = editDashboardPage.deleteCategory("alph:a");
    Result.rcEquals(result, "0");
    TestUtils.sleep(4);

    result = editDashboardPage.deleteCategory("alph;a");
    Result.rcEquals(result, "0");
    TestUtils.sleep(4);

  }

  @Test(priority = 250)
  public void editdashboardforshowinMenu() {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");

  }

  @Test(priority = 260)
  public void showdashboardMethod()throws ParseException
  {
    result = manageDashboardsPage.showInMenuDashboard(sDashboard);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 270)
  public void viewDashboardAfterImport() {
    result = mainPage.viewDashboardNew(sDashboard);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 280)
  public void checkDataAfterImport() {
    result = mainPage.getChartNumbers(sWidget);
    Result.outEquals(result, lChartNumbersDefault.toString());

  }

  @Test(priority = 290)
  public void checkTextWidgetsInGroupWithOutTitleAfterImport()
  {

    Assert.assertEquals(mainPage.getGroupWidgetCount(), 2);

    result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwotitlelocation,"shape50");
    Result.rcEquals(result, "0");

    result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwoTitlesolditem,"shape51");
    Result.rcEquals(result, "0");
  }


  @Test(priority = 300)
  public void checkHyperlinktodashboardpresentonWidgetafterImport() {

    result = mainPage.clickOnHyperLink("g[id$='Stuttgart-shape50'] text");
    Result.rcEquals(result, "0");

    result = mainPage.getDashboard();
    Result.rcEquals(result, "0");
    Result.outContains(result, sLinkToDashboard);

  }

  @Test(priority = 310)
  public void backafterImport() {
      result = mainPage.viewDashboardNew(sDashboard);
      Result.rcEquals(result, "0");
  }

  @Test(priority = 320)
  public void checkHyperlinktourlafterImport() {

    result = mainPage.clickOnHyperLink("g[id$='Stuttgart-shape51'] text");
    Result.rcEquals(result, "0");

    if (driver.getCurrentUrl().equals(hyperlinkurl)) {
      result.put("out", "Hyperlink to Url " + hyperlinkurl + " is working");
    } else {
      result = ResultStore.failure("Hyperlink to Url " + hyperlinkurl + " is not working, instead " + result.get("out") + " is shown");
    }

  }


  @Test(priority=340)
  public void logoutcheckfornonadmin()
  {
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }


  @Test(priority = 350)
  public void deleteRole()
  {
    result = client.userWS.deleteRole(roleId);
    Result.rcEquals(result,"0");
  }

  @Test(priority = 400, enabled = true)
  public void filesClean()
  {
    try
    {
      for (String targetFile : allFiles)
      {
        java.io.File fileToDelete;
        fileToDelete = new java.io.File(tempPath + targetFile);
        fileToDelete.delete();
      }
      result = ResultStore.success("File(s) successfully deleted");
      Result.rcEquals(result, "0");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to delete file  error: " + Misc.getStacktraceAsString(exc));
    }

    Result.rcEquals(result, "0");
  }

  @AfterClass
  public void cleanup() throws UnrecoverableKeyException, NoSuchAlgorithmException,
          KeyStoreException, JSONException, KeyManagementException
  {
    client.collectorWS.deleteAllExistQueries();
    client.dashboardWS.deleteDashboard(sDashboard);
    driver.quit();
  }
}
