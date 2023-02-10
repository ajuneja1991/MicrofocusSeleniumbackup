package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.*;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.testng.annotations.DataProvider;
import org.openqa.selenium.interactions.Actions;
import java.awt.*;
public class DataCollectorCRUD
{

  private LoginPage loginPage;
  private MainPage mainPage;
  ManageDashboardsPage manageDashboardsPage;
  private DataCollectorPage dataCollectorPage;
  private WebDriver driver;
  private Map<String, String> result;
  BvdWsClient client;
  private List<String> tags = new ArrayList<>();
  private List<String> channelTags = new ArrayList<>();
  private List<String> dBFields = new ArrayList<>();
  String queryType="DataQuery";
  private List<String> lDataFields = Arrays.asList("CPU", "ram");
  String querytypeParam="ParameterQuery";
  private String sSourceDashboard = "DataCollectorCRUD";
  private String sDashboard ;
  private String sDashboardPath ;
  private EditDashboardPage editDashboardPage;
  private String widget = "group1";
  private String dataChannel = "machinesdata";
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
  private String userName = "Tester@microfocus.com";
  private String userLogin;
  private String userPwd = "Test@123";
  private String roleName = "tester";
  private String desc = "For Tester";
  private String category = "All";
  private String accessType = "full-control";
  String roleId;
  private List<String> sDataFields1 = Arrays.asList("100", "70");
  private String sWidget = "group1";
  private List<String> dataPoint=Arrays.asList("g>rect[width^='198']",
          "g>rect[width^='198']");

  private void initPages()
  {
    loginPage = PageFactory.initElements(driver, LoginPage.class);
    mainPage = PageFactory.initElements(driver, MainPage.class);
    manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
    dataCollectorPage = PageFactory.initElements(driver, DataCollectorPage.class);
    editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
  }

  private String defaultWidgetJson()
  {
    return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"unchanged\"," +
            "\"tags\":[\"DefaultWidgetQuery\"],\"dims\":[],\"queryText\":\"SELECT * FROM public" +
            ".bvd_automation2 where id = 1\",\"availableColumns\":[\"Data1\",\"Data2\",\"Data3\"," +
            "\"Data4\",\"id\"],\"sampleQueryResult\":{\"Data1\":10,\"Data2\":20,\"Data3\":30," +
            "\"Data4\":40,\"id\":1}},\"name\":\"Query For Search\"}}";
  }


  @BeforeClass
  public void setup()
  {
    cfg = new TestConfig().getConfig();
    BROWSER=cfg.getString("application.bvd.browser.name");
    BVD_USR = cfg.getString("application.bvd.user.adminusr");
    BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
    URL=cfg.getString("application.bvd.url.external");
    sDashboard = sSourceDashboard + "_" + BROWSER;
    sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
    tags.add("AutomationTag1");
    tags.add("AutomationTag2");
    tags.add("AutomationTag3");
    channelTags.add("machinesdata");
    //channelTags.add("Automation Test Query");
    channelTags.add("AutomationTag1");
    channelTags.add("AutomationTag2");
    channelTags.add("AutomationTag3");
    dBFields.add("vdb.mambo.net");
    dBFields.add("5433");
    dBFields.add("opsadb");
    dBFields.add("dbadmin");
    dBFields.add("installed");
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

  @Test(priority = 2,enabled=false)
  public void copyTestFiles() {
    result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
    Result.rcEquals(result, "0");
  }
  @Test(priority = 3)
  public void deleteAllCreatedQueriesIfExist() throws UnrecoverableKeyException,
          NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException
  {
    client.collectorWS.deleteAllExistQueries();
    result = client.collectorWS.bvdDataCollectorCreateQuery(defaultWidgetJson());
    Result.rcEquals(result, "0");
  }

  @Test(priority = 5)
  public void openDataCollectorApplication()
  {
    result = mainPage.openDataCollectors();
    Result.rcEquals(result, "0");
  }



  @Test(priority = 10)
  public void createRole() {
    result = client.userWS.createRole(roleName, desc, category,accessType);
    Result.rcEquals(result, "0");
    roleId = result.get("out");
  }

  @Test(priority = 11)
  public void newQueryCreation()
  {
    result = dataCollectorPage.clickNewQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.typeTextIntoQueryName("machinesdata",queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.typeTextIntoQueryDescription("Fetching Machine Data",queryType);

    result = dataCollectorPage.createTag(tags.get(0));
    Result.rcEquals(result, "0");

    result = dataCollectorPage.createTag(tags.get(1));
    Result.rcEquals(result, "0");

    result = dataCollectorPage.createTag(tags.get(2));
    Result.rcEquals(result, "0");

    result = dataCollectorPage.switchFormat("Use in widget group");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.typeTextIntoQuery("select * from Machines1 where CPU <= 500 and ram < 500");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickExecuteQuery();
    Result.rcEquals(result, "0");

    /*result = dataCollectorPage.checkQueryResult("crud_query_check", "test_passed");
    Result.rcEquals(result, "0");*/
    result = dataCollectorPage.checkQueryResultforMultiSeries("HOSTNAME", "CPU", "RAM", "ID");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickSaveQuery();
    Result.rcEquals(result, "0");

    /*result = dataCollectorPage.clickQueryInList("Automation Test Query");
    Result.rcEquals(result, "0");*/

    result = dataCollectorPage.clickQueryInList("machinesdata");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryInfo(tags, channelTags, "Use in Widget Group");
    Result.rcEquals(result, "0");

  }


  @Test(priority = 20)
  public void queryReadSavedData()
  {

    result = dataCollectorPage.clickEditQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryName("machinesdata",queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryDescription("Fetching Machine Data");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readTags(tags);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readChannel(channelTags);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readFormat("Use in Widget Group");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readSQLField("select * from Machines1 where CPU <= 500 and ram < 500");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.checkQueryResultforMultiSeries("HOSTNAME", "CPU", "RAM", "ID");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickSaveQuery();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 30)
  public void openDashboardApplicationForOverflowTest()
  {

    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 50)
  public void logOutFromAdmin()
  {
    result = mainPage.logout();
  }

  @Test(priority = 60)
  public void loginAsNewUser() {
    driver.get(URL);
    result = loginPage.login(userName, userPwd);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 70)
  public void checkLogin()
  {
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 80)
  public void openDashboardApplication()
  {

    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
  }


  @Test(priority = 90)
  public void uploadDashboard() {
    result = manageDashboardsPage.uploadDashboard(sDashboardPath);
    Result.rcEquals(result, "0");
  }


  @Test(priority = 100)
  public void clickSelectedWidget() {
    result = editDashboardPage.selectWidget(widget);
    Result.rcEquals(result, "0");
  }

  //@Test(priority = 80, enabled = false)
  @Test(priority = 110)
  public void setDataChannel() {
    result = editDashboardPage.setDataChannel(dataChannel);
    Result.rcEquals(result, "0");
  }

  //@Test(priority = 90, enabled = false)
  @Test(priority = 120)
  public void setDataField() {
    result = editDashboardPage.SetDataFieldIgnoreExisting(lDataFields);
    //result = editDashboardPage.typeDataFieldNew("ram");
    Result.rcEquals(result, "0");
  }

  //@Test(priority = 110, enabled = false)
  @Test(priority = 130)
  public void saveUploadedDashboard() {
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }

  //@Test(priority = 120, enabled = false)
  @Test(priority = 140)
  public void viewDashboard() {
    result = mainPage.viewDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 150)
  public void verifyingValueAfterUploadingDashboard() {
    result = mainPage.verifyingValue(sDataFields1);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 160)
  public void checkData() {
    result = mainPage.checkdataPoints(sWidget,dataPoint);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 170)
  public void logOutFromUser()
  {
    result = mainPage.logout();
  }

  @Test(priority = 180)
  public void loginIntoAdmin() {
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 190)
  public void loginCheck()
  {
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 200)
  public void deleteRole()
  {
    result = client.userWS.deleteRole(roleId);
    Result.rcEquals(result,"0");
  }

  //@Test(priority = 160, enabled = false)
  @Test(priority = 210)
  public void openDataCollectorApplication1()
  {
    TestUtils.sleep(2);
    result = mainPage.openDataCollectors();
    Result.rcEquals(result, "0");
  }

  //@Test(priority = 170, enabled = false)
  @Test(priority = 220)
  public void queryUpdateData()
  {

    Actions actions = new Actions(driver);
    try {
      Robot robot = new Robot();

      robot.mouseMove(400,350);
    } catch (AWTException e) {
      e.printStackTrace();
    }

    actions.click().build().perform();

    com.hp.opr.qa.framework.utils.TestUtils.sleep(3);

    driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@name='iframer']"))); // switching to iframe

    TestUtils.sleep(2);

    result = dataCollectorPage.clickQueryInList("machinesdata");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickEditQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.typeTextIntoQueryName("machinesdataupdate", queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.typeTextIntoQueryDescription("Fetching Updated Machine Data", queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.deleteTag(tags.get(2));
    Result.rcEquals(result, "0");

    result = dataCollectorPage.switchFormat("Default");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.typeTextIntoQuery("select * from Machines1 where CPU >= 500 and ram > 500");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickExecuteQuery();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.checkQueryResultforMultiSeries("HOSTNAME", "CPU", "RAM", "ID");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickSaveQuery();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickQueryInList("machinesdataupdate");
    Result.rcEquals(result, "0");


  }


  @Test(priority = 230)
  public void queryReadUpdatedData()
  {

    result = dataCollectorPage.clickEditQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryName("machinesdataupdate",queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryDescription("Fetching Updated Machine Data");
    Result.rcEquals(result, "0");

    tags.remove(2);
    result = dataCollectorPage.readTags(tags);
    Result.rcEquals(result, "0");

    channelTags.remove(3);
    channelTags.set(0, "machinesdataupdate");
    result = dataCollectorPage.readChannel(channelTags);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readFormat("Default");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readSQLField("select * from Machines1 where CPU >= 500 and ram > 500");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickCancelQuery();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickQueryInList("machinesdataupdate");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryInfo(tags, channelTags, "Default");
    Result.rcEquals(result, "0");
  }


  @Test(priority = 240)
  public void queryOptions()
  {

    result = dataCollectorPage.clickQueryInList("machinesdataupdate");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.duplicateQuery();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickQueryInList("copy of machinesdataupdate");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.duplicateQuery();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickQueryInList("copy of copy of machinesdataupdate");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.duplicateQuery();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickQueryInList("copy of machinesdataupdate");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickEditQueryInList();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryNameforqueryOptions("copy of machinesdataupdate", queryType);
    Result.rcEquals(result, "0");

    channelTags.set(0, "copy of machinesdataupdate");
    result = dataCollectorPage.readChannelforDelete(channelTags);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickCancelQuery();
    Result.rcEquals(result, "0");

  }

  @Test(priority = 250)
  //@Test(priority = 55)
  public void querySearch()
  {
    result = dataCollectorPage.searchQuery("Query For Search");
    Result.rcEquals(result, "0");


    result = dataCollectorPage.clickQueryInList("Query For Search");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickDeleteQueryInList();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clearSearchField();
    Result.rcEquals(result, "0");

  }

  @Test(priority = 260)
  //@Test(priority = 350)
  public void queryDelete()
  {

    result = dataCollectorPage.multipleQuerySelect("copy of copy of machinesdataupdate", "copy of copy of copy of machinesdataupdate");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickDeleteQueryTopOfList();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickQueryInList("copy of copy of machinesdataupdate");
    Result.rcEquals(result, "1");

    result = dataCollectorPage.clickQueryInList("copy of copy of copy of machinesdataupdate");
    Result.rcEquals(result, "1");


    result = dataCollectorPage.clickQueryInList("machinesdataupdate");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickDeleteQueryInList();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickQueryInList("machinesdataupdate");
    Result.rcEquals(result, "1");

    result = dataCollectorPage.clickQueryInList("copy of machinesdataupdate");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickDeleteQueryTopOfList();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickQueryInList("copy of machinesdataupdate");
    Result.rcEquals(result, "1");

  }


  //@Test(priority = 390, enabled = false)
  @Test(priority = 270)
  public void deleteDashboard() {
    result = client.dashboardWS.deleteDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }

  @AfterClass
  public void cleanup() throws UnrecoverableKeyException, NoSuchAlgorithmException,
          KeyStoreException, JSONException, KeyManagementException
  {
    client.collectorWS.deleteAllExistQueries();
    driver.quit();
  }


}
