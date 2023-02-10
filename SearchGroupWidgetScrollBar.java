package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.*;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SearchGroupWidgetScrollBar{
    private Config cfg;
    private String BVD_URL;
    private String BVD_USR;
    private String BVD_PWD;
    private String BROWSER;
    private String PROXY_HOST;
    private int PROXY_PORT;
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
    private String sSourceDashboard = "dashboard_group_widget";
    private String sDashboard = "";
    private String sDashboardPath = "C:\\tmp\\" + sSourceDashboard + ".svg";
    private EditDashboardPage editDashboardPage;
    private String widget1 = "shape13";
    private String widget2 = "shape14";
    private String dataChannel = "GroupWidgetOptions";
    private List<String> sDataSearchFields = Arrays.asList("id", "lvl");
    private String id = "background";
    private String sDataChannel = "GroupWidgetOptions";
    private String sWidget = "group16";

    private void initPages()
    {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
        dataCollectorPage = PageFactory.initElements(driver, DataCollectorPage.class);
    }

    @BeforeClass
    public void setup()
    {
        cfg = new TestConfig().getConfig();
        BVD_URL = cfg.getString("application.bvd.url.external");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD = cfg.getString("application.bvd.user.adminpwd");
        BROWSER = cfg.getString("environment.browser.name");
        PROXY_HOST = cfg.getString("environment.proxy.host");
        PROXY_PORT = cfg.getInt("environment.proxy.port");
        driver = UiTestWebDriver.getWebDriver();
        driver.get(BVD_URL);
        tags.add("AutomationTag1");
        channelTags.add("GroupWidgetOptions");
        sDashboard = sSourceDashboard;
        channelTags.add("AutomationTag1");
        dBFields.add("vdb.mambo.net");
        dBFields.add("5433");
        dBFields.add("opsadb");
        dBFields.add("dbadmin");
        dBFields.add("installed");
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
    public void idmlogin() {
        result = loginPage.login(BVD_USR, BVD_PWD);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 2)
    public void login_check()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 3)
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 4)
    public void deleteAllCreatedQueriesIfExist() throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException
    {
        client.collectorWS.deleteAllExistQueries();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 5)
    public void openDataCollectorApplication()
    {
        result = mainPage.openDataCollectors();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 10)
    public void newQueryCreation()
    {
        result = dataCollectorPage.clickNewQuery(queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryName("GroupWidgetOptions",queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryDescription("Search Group widget options",queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.createTag(tags.get(0));
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchFormat("Use in widget group");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQuery("select * from resource_relation");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickQueryInList("GroupWidgetOptions");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readQueryInfo(tags, channelTags, "Use in Widget Group");
        Result.rcEquals(result, "0");

    }

    @Test(priority = 50)
    public void openDashboardApplication()
    {

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60)
    public void uploadDashboard() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void selectWidget() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 90)
    public void setDataChannel() {
        result = editDashboardPage.setDataChannel(sDataChannel);
        Result.rcEquals(result, "0");
        result = editDashboardPage.setsearchFields(sDataSearchFields);
        Result.rcEquals(result, "0");
    }

    @Test(priority=100)
    public void setWidget1() {

        result = editDashboardPage.selectWidget(widget1);
        Result.rcEquals(result, "0");
        result = editDashboardPage.setDataField("id");
        Result.rcEquals(result, "0");

    }

    @Test(priority=110)
    public void setWidget2() {

        result = editDashboardPage.selectWidget(widget2);
        Result.rcEquals(result, "0");
        result = editDashboardPage.setDataField("lvl");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 140)
    public void apply() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
        result = editDashboardPage.navigateToDashboardProperties();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150)
    public void viewDashboard() {
        result = mainPage.viewDashboardforGroupWidget(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 155)
    public void viewDatainDashboard() {
        result = mainPage.viewDatainDashboardforGroupWidget(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 160)
    public void typeTextinSearch() {
        result = mainPage.typeTextinSearchBoxScrollBar();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void SearchFunctionality() {
        result = mainPage.validateSearchFunctionalityScrollBar();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 520)
    public void openDataCollectorForQueryDelete()
    {
        TestUtils.sleep(2);
        result = mainPage.openDataCollectors();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 530)
    public void queryDelete()
    {
        Actions actions = new Actions(driver);
        try {
            Robot robot = new Robot();
            robot.mouseMove(400,350);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        actions.click().build().perform();

        TestUtils.sleep(3);

        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@name='iframer']")));

        TestUtils.sleep(2);

        result = dataCollectorPage.clickQueryInList("GroupWidgetOptions");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickDeleteQueryInList();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickQueryInList("GroupWidgetOptions");
        Result.rcEquals(result, "1");

    }

    @Test(priority = 540)
    public void deleteDashboard() {
        result = client.dashboardWS.deleteDashboard(sSourceDashboard);
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

