package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.pageobjects.bvd.DataCollectorPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class QueriesExportImport
{
  
  private LoginPage loginPage;
  private MainPage mainPage;
  private DataCollectorPage dataCollectorPage;
  private WebDriver driver;
  String queryType="DataQuery";
  private Map<String, String> result;
  BvdWsClient client;
  //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;
  private List<String> dBFields = new ArrayList<>();
  private List<String> queriesNames = Arrays.asList("dataCollectors", "dataCollectors (1)",
        "dataCollectors (2)", "dataCollectors (3)", "wrong_data", "queries");
  private List<String> tagsGroupWidget = new ArrayList<>();
  private List<String> tagsDefaultWidget = new ArrayList<>();
  private List<String> channelTagsDefault = new ArrayList<>();
  private List<String> channelTagsGroup = new ArrayList<>();
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
  private String OS ;
  
  private String filePath(String filename)
  {
    return "C:\\tmp\\" + filename + ".bvddc";
  }
  
  //dataCollectors - Export all without selection
  //dataCollectors (1) - Export Default widget query
  //dataCollectors (2) - Export Group widget query
  //dataCollectors (3) - Export multi-selected queries
  
  private void initPages()
  {
    loginPage = PageFactory.initElements(driver, LoginPage.class);
    mainPage = PageFactory.initElements(driver, MainPage.class);
    dataCollectorPage = PageFactory.initElements(driver, DataCollectorPage.class);
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
  
  private String queryForCheck()
  {
    return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"groupwidget\"," +
          "\"tags\":[\"GroupWidgetQuery\"],\"dims\":[],\"queryText\":\"select location as ID, " +
          "location, sum(sold_item) as sold_items from bvd_lwr_demo group by location order by " +
          "sold_items desc limit 2\",\"availableColumns\":[\"ID\",\"location\",\"sold_items\"]," +
          "\"sampleQueryResult\":{\"ID\":\"Stuttgart\",\"location\":\"Stuttgart\"," +
          "\"sold_items\":141}},\"name\":\"Old Query in List\"}}";
  }
  
  
  public void deleteQueryFile(String filename) throws IOException
  {
    // dashboard file path
    File file;
    
    if (OS.contains("win"))
    {
      file = new File("C:\\tmp\\" + filename + ".bvddc");
    }
    else
    {
      file = new File("/tmp/" + filename + ".bvddc");
    }
    
    // delete file
    try
    {
      file.delete();
      result = ResultStore.success("File successfully deleted");
      
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to delete Dashboard " + file.toString() + ". error: "
            + Misc.getStacktraceAsString(exc));
    }
    
    Result.rcEquals(result, "0");
  }
  
  @BeforeClass
  public void setup()
  {
    cfg = new TestConfig().getConfig();
    BROWSER=cfg.getString("application.bvd.browser.name");
    BVD_USR = cfg.getString("application.bvd.user.adminusr");
    BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
    URL=cfg.getString("application.bvd.url.external");
    OS=System.getProperty("os.name").toLowerCase();
    dBFields.add("vdb.mambo.net");
    dBFields.add("5433");
    dBFields.add("opsadb");
    dBFields.add("dbadmin");
    dBFields.add("installed");
    tagsGroupWidget.add("GroupWidgetQuery");
    tagsDefaultWidget.add("DefaultWidgetQuery");
    tagsDefaultWidget.add("NewTag1");
    tagsDefaultWidget.add("NewTag2");
    channelTagsDefault.add("AutoDefaultWidgetQuery");
    channelTagsDefault.add("DefaultWidgetQuery");
    channelTagsDefault.add("NewTag1");
    channelTagsDefault.add("NewTag2");
    channelTagsGroup.add("AutoGroupWidgetQuery");
    channelTagsGroup.add("GroupWidgetQuery");
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
        KeyStoreException, JSONException, KeyManagementException, IOException
  {
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
    client.collectorWS.deleteAllExistQueries();
    for (String queryName : queriesNames)
    {
      deleteQueryFile(queryName);
    }
  }
  
  
    @Test(priority = 10)
  public void copyTestFiles()
  {
    result = TestUtils.downloadResource(this.getClass(), "wrong_data.bvddc",
          filePath("wrong_data"));
    Result.rcEquals(result, "0");

    result = TestUtils.downloadResource(this.getClass(), "queries.bvddc",
          filePath("queries"));
    Result.rcEquals(result, "0");

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
  public void openDataCollectorApplication()
  {
    result = mainPage.openDataCollectors();
    Result.rcEquals(result, "0");
  }


  @Test(priority = 60)
  public void checkCreatedQueries()
  {

    result = dataCollectorPage.checkQueryInListfirsttime("AutoGroupWidgetQuery");
    Result.rcEquals(result, "0");
    result = dataCollectorPage.checkQueryInList("AutoDefaultWidgetQuery");
    Result.rcEquals(result, "0");
  }

  @Test(priority = 70)
  public void exportAllWithoutSelection()
  {
    result = dataCollectorPage.exportAllQueries();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 80)
  public void exportDefaultWidgetQuery()
  {
    result = dataCollectorPage.clickQueryInList("AutoDefaultWidgetQuery");
    Result.rcEquals(result, "0");
    result = dataCollectorPage.exportSelectedQuery();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 90)
  public void exportGroupWidgetQuery()
  {
    result = dataCollectorPage.clickQueryInList("AutoGroupWidgetQuery");
    Result.rcEquals(result, "0");
    result = dataCollectorPage.exportSelectedQuery();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 100)
  public void multiSelectionExport()
  {
    result = dataCollectorPage.multipleQuerySelect("AutoGroupWidgetQuery",
 "AutoDefaultWidgetQuery");
    Result.rcEquals(result, "0");
    result = dataCollectorPage.exportSelectedQuery();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 110)
  public void deleteAllQueries()
  {
    result = dataCollectorPage.clickDeleteQueryTopOfList();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 120)
  public void checkQueriesWereDeleted()
  {
    result = dataCollectorPage.checkQueryInList("AutoGroupWidgetQuery");
    Result.rcEquals(result, "1");
    result = dataCollectorPage.checkQueryInList("AutoDefaultWidgetQuery");
    Result.rcEquals(result, "1");
  }

    @Test(priority = 130)
  public void importExportedAllQueries()
  {
    result = dataCollectorPage.importQuery(filePath("dataCollectors"));
    Result.rcEquals(result, "0");

  }

  @Test(priority = 140)
  public void checkAllImportedQueries()
  {
    result = dataCollectorPage.clickQueryInList("AutoDefaultWidgetQuery");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickEditQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryName("AutoDefaultWidgetQuery", queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readFormat("Default");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readSQLField("SELECT * FROM public.bvd_automation2 where id = 1");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickCancelQuery();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickQueryInList("AutoGroupWidgetQuery");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickEditQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryName("AutoGroupWidgetQuery", queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readFormat("Use in Widget Group");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readSQLField("select location as ID, location, sum(sold_item) as " +
          "sold_items from bvd_lwr_demo group by location order by sold_items desc limit 2");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickCancelQuery();
    Result.rcEquals(result, "0");

  }

  @Test(priority = 150)
  public void deleteAllImportedQueries()
  {
    result = dataCollectorPage.multipleQuerySelect("AutoGroupWidgetQuery",
 "AutoDefaultWidgetQuery");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickDeleteQueryTopOfList();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.checkQueryInList("AutoGroupWidgetQuery");
    Result.rcEquals(result, "1");
    result = dataCollectorPage.checkQueryInList("AutoDefaultWidgetQuery");
    Result.rcEquals(result, "1");

  }

  @Test(priority = 160)
  public void importDefaultWidgetQuery()
  {
    result = dataCollectorPage.importQuery(filePath("dataCollectors (1)"));
    Result.rcEquals(result, "0");
  }

  @Test(priority = 180)
  public void checkImportedDefaultAndGroupQueries()
  {
    result = dataCollectorPage.clickQueryInList("AutoDefaultWidgetQuery");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickEditQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryName("AutoDefaultWidgetQuery", queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readFormat("Default");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readSQLField("SELECT * FROM public.bvd_automation2 where id = 1");
    Result.rcEquals(result, "0");

   }

  @Test(priority = 190)
  public void updateAndCheckGroupWidgetQuery()
  {
    result = dataCollectorPage.typeTextIntoQueryName("AutoDefaultWidgetQuery Update",queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.typeTextIntoQueryDescription("Automation Test Query Description " +
            "Update",queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.switchFormat("Default");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.typeTextIntoQuery("select location as ID, location, sum(sold_item) as sold_items from bvd_lwr_demo group by location order by sold_items desc limit 2");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickExecuteQuery();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickSaveQuery();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickQueryInList("AutoDefaultWidgetQuery Update");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickEditQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryName("AutoDefaultWidgetQuery Update", queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryDescription("Automation Test Query Description Update");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readSQLField("select location as ID, location, sum(sold_item) as " +
          "sold_items from bvd_lwr_demo group by location order by sold_items desc limit 2");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickCancelQuery();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickQueryInList("AutoDefaultWidgetQuery Update");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickDeleteQueryTopOfList();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickQueryInList("AutoGroupWidgetQuery Update");
    Result.rcEquals(result, "1");

  }

  @Test(priority = 200)
  public void importMultiSelectedQuery()
  {
    result = dataCollectorPage.importQuery(filePath("dataCollectors (3)"));
    Result.rcEquals(result, "0");
  }

  @Test(priority = 210)
  public void checkImportedMultiSelectedQuery()
  {
    result = dataCollectorPage.clickQueryInList("AutoDefaultWidgetQuery");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickEditQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryName("AutoDefaultWidgetQuery", queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readFormat("Default");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readSQLField("SELECT * FROM public.bvd_automation2 where id = 1");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickCancelQuery();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickQueryInList("AutoGroupWidgetQuery");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickEditQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryName("AutoGroupWidgetQuery", queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readFormat("Use in Widget Group");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readSQLField("select location as ID, location, sum(sold_item) as " +
          "sold_items from bvd_lwr_demo group by location order by sold_items desc limit 2");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickCancelQuery();
    Result.rcEquals(result, "0");

  }

  @Test(priority = 215)
  public void createQuery() throws UnrecoverableKeyException, NoSuchAlgorithmException,
        KeyStoreException, JSONException, KeyManagementException
  {
    result = client.collectorWS.bvdDataCollectorCreateQuery(queryForCheck());
    Result.rcEquals(result, "0");
  }

  @Test(priority = 220)
  public void importMultiSelectedAndSkip()
  {
    result = dataCollectorPage.importQuerySkip(filePath("queries"));
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickQueryInList("AutoDefaultWidgetQuery");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickEditQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readFormat("Default");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readSQLField("SELECT * FROM public.bvd_automation2 where id = 1");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickCancelQuery();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickQueryInList("AutoGroupWidgetQuery");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickEditQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readFormat("Use in Widget Group");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readSQLField("select location as ID, location, sum(sold_item) as " +
          "sold_items from bvd_lwr_demo group by location order by sold_items desc limit 2");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickCancelQuery();
    Result.rcEquals(result, "0");

  }

  @Test(priority = 225)
  public void checkNewQueryCreatedAfterSkip()
  {

    result = dataCollectorPage.clickQueryInList("New Query in List");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickEditQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryName("New Query in List", queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readFormat("Use in Widget Group");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readSQLField("select location as ID, location, sum(sold_item) as " +
          "sold_items from bvd_lwr_demo group by location order by sold_items desc limit 2");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickCancelQuery();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickQueryInList("New Query in List");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickDeleteQueryInList();
    Result.rcEquals(result, "0");

  }

  @Test(priority = 230)
  public void checkOldQueryNotChangedAfterSkip()
  {

    result = dataCollectorPage.clickQueryInList("Old Query in List");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickEditQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryName("Old Query in List",queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readFormat("Use in Widget Group");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readSQLField("select location as ID, location, sum(sold_item) as " +
          "sold_items from bvd_lwr_demo group by location order by sold_items desc limit 2");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickCancelQuery();
    Result.rcEquals(result, "0");

  }


  @Test(priority = 240)
  public void importAndOverwriteQueries()
  {
    result = dataCollectorPage.importQueryOverwrite(filePath("queries"));
    Result.rcEquals(result, "0");
  }


  @Test(priority = 250)
  public void checkOverwrittenQueries()
  {
    result = dataCollectorPage.clickQueryInList("AutoDefaultWidgetQuery");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickEditQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readFormat("Use in Widget Group");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readTags(tagsDefaultWidget);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readChannel(channelTagsDefault);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readSQLField("SELECT * FROM public.bvd_automation2 where id = 2");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickCancelQuery();
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickQueryInList("AutoGroupWidgetQuery");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickEditQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readFormat("Default");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readTags(tagsGroupWidget);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readChannel(channelTagsGroup);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readSQLField("select 1");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickCancelQuery();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 260)
  public void checkOldQueryNotChangedAfterOverwrite()
  {

    result = dataCollectorPage.clickQueryInList("Old Query in List");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickEditQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryName("Old Query in List",queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readFormat("Use in Widget Group");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readSQLField("select location as ID, location, sum(sold_item) as " +
          "sold_items from bvd_lwr_demo group by location order by sold_items desc limit 2");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickCancelQuery();
    Result.rcEquals(result, "0");

  }

  @Test(priority = 270)
  public void checkNewQueryCreatedAfterOverwrite()
  {

    result = dataCollectorPage.clickQueryInList("New Query in List");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickEditQuery(queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readQueryName("New Query in List",queryType);
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readFormat("Use in Widget Group");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.readSQLField("select location as ID, location, sum(sold_item) as " +
          "sold_items from bvd_lwr_demo group by location order by sold_items desc limit 2");
    Result.rcEquals(result, "0");

    result = dataCollectorPage.clickCancelQuery();
    Result.rcEquals(result, "0");

  }

  @Test(priority = 280)
  public void importWrongDataFile()
  {
    result = dataCollectorPage.importWrongData(filePath("wrong_data"));
    Result.rcEquals(result, "0");
  }


  @AfterClass
  public void cleanup() throws UnrecoverableKeyException, NoSuchAlgorithmException,
        KeyStoreException, JSONException, KeyManagementException, IOException
  {
    client.collectorWS.deleteAllExistQueries();
    for (String queryName : queriesNames)
    {
      deleteQueryFile(queryName);
    }
    driver.quit();
  }

}
