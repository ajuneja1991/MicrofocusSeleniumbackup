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
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
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

public class DataCollectorDataOnWidgets
{
  private LoginPage loginPage;
  private MainPage mainPage;
  private ManageDashboardsPage manageDashboardsPage;
  private EditDashboardPage editDashboardPage;
  private WebDriver driver;
  private Map<String, String> result;
  private BvdWsClient client;
  private List<String> defaultWidgetDataFields = Arrays.asList("Data1", "Data2", "Data3", "Data4");
  private List<String> defaultWidgetnumbers = Arrays.asList("10", "20", "30", "40");
  private List<String> groupWidgetnumbers = Arrays.asList("20", "30", "40", "50");
  private List<String> textElemsInWidget = new ArrayList<>();
  private List<String> textElemsInWidgetwoTitle = new ArrayList<>();
  private List<String> textElemsInWidgetwoTitlesolditem = new ArrayList<>();
  private List<String> textElemsInWidgetwotitlelocation = new ArrayList<>();
  private String autoDefaultWidgetQuery = "AutoDefaultWidgetQuery";
  private String autoGroupWidgetQuery = "AutoGroupWidgetQuery";
  String sSourceDashboard = "autoDataCollector";
  String sDashboard ;
  String sDashboardPath;
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
    manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
    editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
  }
  
  
  private String defaultWidgetJson()
  {
    return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"unchanged\"," +
          "\"tags\":[\"DefaultWidgetQuery\"],\"dims\":[],\"queryText\":\"SELECT * FROM public" +
          ".bvd_automation2 where id = 1\",\"availableColumns\":[\"Data1\",\"Data2\",\"Data3\"," +
          "\"Data4\",\"id\"],\"sampleQueryResult\":{\"Data1\":10,\"Data2\":20,\"Data3\":30," +
          "\"Data4\":40,\"id\":1}},\"name\":\"AutoDefaultWidgetQuery\"}}";
  }
  
  
  private String defaultWidgetJsonUpdate()
  {
    return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"unchanged\"," +
          "\"tags\":[\"DefaultWidgetQuery\"],\"dims\":[],\"queryText\":\"SELECT * FROM public" +
          ".bvd_automation2 where id = 2\",\"availableColumns\":[\"Data1\",\"Data2\",\"Data3\"," +
          "\"Data4\",\"id\"],\"sampleQueryResult\":{\"Data1\":20,\"Data2\":30,\"Data3\":40," +
          "\"Data4\":50,\"id\":2}},\"name\":\"AutoDefaultWidgetQuery\"}}";
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
  
  
  private String groupWidgetJsonUpdate()
  {
    return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"groupwidget\"," +
          "\"tags\":[\"GroupWidgetQuery\"],\"dims\":[],\"queryText\":\"select location as ID, " +
          "location, sum(sold_item) as sold_items from bvd_lwr_demo group by location order by " +
          "sold_items desc limit 4\",\"availableColumns\":[\"ID\",\"location\",\"sold_items\"]," +
          "\"sampleQueryResult\":{\"ID\":\"Stuttgart\",\"location\":\"Stuttgart\"," +
          "\"sold_items\":141}},\"name\":\"AutoGroupWidgetQuery\"}}";
  }
  
  
  @BeforeClass
  public void setup()
  {
    cfg = new TestConfig().getConfig();
    BROWSER=cfg.getString("application.bvd.browser.name");
    BVD_USR = cfg.getString("application.bvd.user.adminusr");
    BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
    URL=cfg.getString("application.bvd.url.external");
    sDashboard = sSourceDashboard + "_Dashboard_" + BROWSER;
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
  public void login_check() throws UnrecoverableKeyException, NoSuchAlgorithmException,
        KeyStoreException, JSONException, KeyManagementException
  {
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
    client.collectorWS.deleteAllExistQueries();
  }
  
  
  @Test(priority = 30)
  public void createInitialQueryDefault() throws UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException
  {
    result = client.collectorWS.bvdDataCollectorCreateQuery(defaultWidgetJson());
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 40)
  public void createInitialQueryGroup() throws UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException
  {
    result = client.collectorWS.bvdDataCollectorCreateQuery(groupWidgetJson());
    Result.rcEquals(result, "0");
  }
  
  
  @Test(priority = 50)
  public void copyTestFiles()
  {
    result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 60)
  public void uploadDashboard()
  {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
    
    result = manageDashboardsPage.uploadDashboard(sDashboardPath);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 70)
  public void selectWidget()
  {
    result = editDashboardPage.selectWidget("group1");
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 80)
  public void setDataChannel()
  {
    result = editDashboardPage.setDataChannel(autoDefaultWidgetQuery);
    Result.rcEquals(result, "0");
  }
  
  
  @Test(priority = 90)
  public void setDataFields()
  {
    result = editDashboardPage.setDataFieldMultiSelect(defaultWidgetDataFields);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 100)
  public void selectGroupWidget()
  {
    result = editDashboardPage.selectWidget("group30");
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 110)
  public void setDataChannelGroupWidget()
  {
    result = editDashboardPage.setDataChannel(autoGroupWidgetQuery);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 120)
  public void selectTextWidgetLocation()
  {
    result = editDashboardPage.selectWidget("shape27");
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 130)
  public void setDataFieldsTextInGroup()
  {
    result = editDashboardPage.setDataField("location");
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 140)
  public void selectTextWidgetCount()
  {
    result = editDashboardPage.selectWidget("shape28");
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 150)
  public void setDataFieldsTextInGroup2()
  {
    result = editDashboardPage.setDataField("sold_items");
    Result.rcEquals(result, "0");
  }
  
  
  @Test(priority = 160)
  public void saveDashboard()
  {
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }
  
  
  @Test(priority = 170)
  public void openDashboard()
  {
    result = mainPage.viewDashboardNew(sDashboard);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 180)
  public void checkChartNumber()
  {
    result = mainPage.getChartNumbers("group1");
    Result.outEquals(result, defaultWidgetnumbers.toString());
    
  }
  
  
  @Test(priority = 190)
  public void checkTextWidgetsInGroup()
  {
    
    textElemsInWidget.add("Stuttgart 141");
    textElemsInWidget.add("Jakarta 139");
    Assert.assertEquals(mainPage.getGroupWidgetCount(), 2);
    
    result = mainPage.compareTextElementsInTextWidget(textElemsInWidget);
    Result.rcEquals(result, "0");
  }


  @Test(priority = 195)
  public void checkTextWidgetsInGroupWithOutTitle()
  {

    textElemsInWidgetwotitlelocation.add("Stuttgart");
    textElemsInWidgetwotitlelocation.add("Jakarta");

    textElemsInWidgetwoTitlesolditem.add("141");
    textElemsInWidgetwoTitlesolditem.add("139");
    Assert.assertEquals(mainPage.getGroupWidgetCount(), 2);

    result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwotitlelocation,"shape27");
    Result.rcEquals(result, "0");

    result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwoTitlesolditem,"shape28");
    Result.rcEquals(result, "0");
  }

  
  @Test(priority = 200)
  public void updateQueryDefault() throws UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException
  {
    result = client.collectorWS.bvdDataCollectorUpdateQuery(autoDefaultWidgetQuery,
          defaultWidgetJsonUpdate());
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 210)
  public void updateQueryGroup() throws UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException
  {
    result = client.collectorWS.bvdDataCollectorUpdateQuery(autoGroupWidgetQuery,
          groupWidgetJsonUpdate());
    Result.rcEquals(result, "0");
  }
  
  
  @Test(priority = 220)
  public void checkUpdatedQueriesData()
  {
    result = mainPage.openHome();
    Result.rcEquals(result, "0");
    
    result = mainPage.viewDashboardNew(sDashboard);
    Result.rcEquals(result, "0");
    
    textElemsInWidget.add("Boeblingen 133");
    textElemsInWidget.add("Munich 116");
    
    result = mainPage.getChartNumbers("group1");
    Result.outEquals(result, groupWidgetnumbers.toString());
    
    Assert.assertEquals(mainPage.getGroupWidgetCount(), 4);
    
    result = mainPage.compareTextElementsInTextWidget(textElemsInWidget);
    Result.rcEquals(result, "0");
    
  }


  @Test(priority = 225)
  public void checkUpdatedQueriesDataWithOutTitle()
  {

    textElemsInWidgetwotitlelocation.add("Boeblingen");
    textElemsInWidgetwotitlelocation.add("Munich");


    textElemsInWidgetwoTitlesolditem.add("133");
    textElemsInWidgetwoTitlesolditem.add("116");
    Assert.assertEquals(mainPage.getGroupWidgetCount(), 4);

    result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwotitlelocation,"shape27");
    Result.rcEquals(result, "0");

    result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwoTitlesolditem,"shape28");
    Result.rcEquals(result, "0");
  }



  @Test(priority = 230)
  public void deleteDashboard() throws UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException
  {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
    
    
    result = manageDashboardsPage.deleteDashboard(sDashboard);
    Result.rcEquals(result, "0");
    
  }
  
  
  @Test(priority = 240)
  public void deleteQueries() throws UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException
  {
    result = client.collectorWS.bvdDataCollectorDeleteQuery(autoDefaultWidgetQuery);
    Result.rcEquals(result, "0");
    
    result = client.collectorWS.bvdDataCollectorDeleteQuery(autoGroupWidgetQuery);
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
