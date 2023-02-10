package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.autopassj.common.util.FileUtils;
import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.pageobjects.bvd.*;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
public class DashboardParamterization{
    private Config cfg;
    private Map<String, String> result = ResultStore.getResultObj();
    private String EXT_BVD_URL;
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
    BvdWsClient client;
    private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;
    private List<String> tags = new ArrayList<>();
    private List<String> channelTags = new ArrayList<>();
    private List<String> dBFields = new ArrayList<>();
    String queryType="DataQuery";
    String querytypeParam="ParameterQuery";
    private String sSourceDashboard = "DashboardParamterization";
    private String sDashboard = sSourceDashboard + "_" + BROWSER;
    private String sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
    private EditDashboardPage editDashboardPage;
    private String widget = "group1";
    private String dataChannel = "machinesdata";
    private List<String> lDataFields = Arrays.asList("CPU", "ram");
    private String dataChannel1 = "machinesdataupdate";
    private List<String> sDataFields1 = Arrays.asList("100", "70");
    private List<String> sDataFields2 = Arrays.asList("600", "500");
    private List<String> sDataFields3 = Arrays.asList("1", "2","3","4","5","6","7");
    private List<String> sDataFields4 = Arrays.asList("0", "0");
    private List<String> dataPoint=Arrays.asList("g>rect[width^='198']",
            "g>rect[width^='198']");
    private String sWidget = "group1";


    private void initPages()
    {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
        dataCollectorPage = PageFactory.initElements(driver, DataCollectorPage.class);
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
        EXT_BVD_URL = cfg.getString("application.bvd.url.external");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD = cfg.getString("application.bvd.user.adminpwd");
        BROWSER = cfg.getString("environment.browser.name");
        PROXY_HOST = cfg.getString("environment.proxy.host");
        PROXY_PORT = cfg.getInt("environment.proxy.port");
        driver = UiTestWebDriver.getWebDriver();
        driver.get(EXT_BVD_URL);
        tags.add("AutomationTag1");
        tags.add("AutomationTag2");
        tags.add("AutomationTag3");
        channelTags.add("machinesdata");
        channelTags.add("AutomationTag1");
        channelTags.add("AutomationTag2");
        channelTags.add("AutomationTag3");
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

    @Test(priority = 2)
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

    @Test(priority = 8,enabled = false)
    public void setupDBConnection()
    {
        if (!dataCollectorPage.isDBSet())
        {
            result = dataCollectorPage.clickDBconfiguraton();
            Result.rcEquals(result, "0");

            result = dataCollectorPage.fillDBSettingsFields(dBFields);
            Result.rcEquals(result, "0");

            result = dataCollectorPage.testDBConnection();
            Result.rcEquals(result, "0");

            result = dataCollectorPage.saveDBSettings();
            Result.rcEquals(result, "0");
        }

    }

    @Test(priority = 10)
    public void newQueryCreation()
    {
        result = dataCollectorPage.clickNewQuery(queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryName("machinesdata",queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryDescription("Fetching Machine Data",queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.createTag(tags.get(0));
        Result.rcEquals(result, "0");

        result = dataCollectorPage.createTag(tags.get(1));
        Result.rcEquals(result, "0");

        result = dataCollectorPage.createTag(tags.get(2));
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchFormat("Use in widget group");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQuery("select * from Machines1 where ${CPU > ${cpuparameter}}");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResultforMultiSeries("HOSTNAME", "CPU", "RAM", "ID");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");

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

        result = dataCollectorPage.readSQLField("select * from Machines1 where ${CPU > ${cpuparameter}}");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResultforMultiSeries("HOSTNAME", "CPU", "RAM", "ID");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30, dataProvider = "MultipleValue")
    public void newParameterQueryCreation(String queryName, String paramVariable, String Desc, String Value, String label, String def)
    {
        result = dataCollectorPage.clickNewQuery(querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryName(queryName, querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryParamVariable(paramVariable);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryDescription(Desc,querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchData("Database query");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchTab();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchTab();
        Result.rcEquals(result,"0");

        result = dataCollectorPage.typeTextIntoQuery("select * from Machines1");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResultforMultiSeries("HOSTNAME", "CPU", "RAM", "ID");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.selectValueColumn(Value);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.selectLabelColumn(label);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchDefault("None","");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");

    }

    @DataProvider(name="MultipleValue")
    public Object[][] getDataFromDataprovider(){
        return new Object[][]
                {
                        { "fetchingcpudata", "cpuparameter", "Fetching CPU Data", "CPU", "CPU", "None" }
                };

    }


    @Test(priority = 40, dataProvider = "MultipleValue")
    public void queryReadSavedDataParam(String queryName, String paramVariable, String Desc, String Value, String label, String def)
    {

        result = dataCollectorPage.clickQueryInList(queryName + " ("+paramVariable+")");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickEditQuery(querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readQueryName(queryName,querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readVariableName(paramVariable);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readQueryDescription(Desc);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readSQLField("select * from Machines1");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResultforMultiSeries("HOSTNAME", "CPU", "RAM", "ID");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readValues(Value);
        Result.rcEquals(result,"0");

        result = dataCollectorPage.readValues(label);
        Result.rcEquals(result,"0");

        result = dataCollectorPage.readDefault(def);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickCancelQuery();
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

    @Test(priority = 70)
    public void clickSelectedWidget() {
        result = editDashboardPage.selectWidget(widget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void setDataChannel() {
        result = editDashboardPage.setDataChannel(dataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90)
    public void setDataField() {
        result = editDashboardPage.SetDataFieldIgnoreExisting(lDataFields);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void saveUploadedDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void viewDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void verifyingValueAfterUploadingDashboard() {
        result = mainPage.verifyingValue(sDataFields1);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 140)
    public void checkData() {
        result = mainPage.checkdataPoints(sWidget,dataPoint);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150)
    public void clickOnLeftBarButton() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 160)
    public void clickOnLeftBarParameterValue() {
        result = mainPage.assignvaluetosingleParam("500","cpuparameter");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void clickOnLeftBarParameterApplyValue() {
        result = mainPage.leftBarParameterApplyValue();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void verifyingValue() {
        result = mainPage.verifyingValue(sDataFields2);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 185)
    public void checkDataPoints() {
        result = mainPage.checkdataPoints(sWidget,dataPoint);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void openDataCollectorApplication1()
    {
        TestUtils.sleep(2);
        result = mainPage.openDataCollectors();
        Result.rcEquals(result, "0");
        TestUtils.sleep(4);
    }

    @Test(priority = 200)
    public void queryUpdateData()
    {

        TestUtils.sleep(3);

        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@name='iframer']"))); // switching to iframe

        TestUtils.sleep(4);

        result = dataCollectorPage.selectQueryUsingActions("machinesdata","Fetching Machine Data");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickEditQuery(queryType);
        Result.rcEquals(result, "0");

        TestUtils.sleep(2);

        result = dataCollectorPage.typeTextIntoQueryName("machinesdataupdate", queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryDescription("Fetching Updated Machine Data", queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.deleteTag(tags.get(2));
        Result.rcEquals(result, "0");

        TestUtils.sleep(2);
        result = dataCollectorPage.switchFormat("Default");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQuery("select * from Machines1 where ${CPU < ${cpuparam}}");
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

    @Test(priority = 210)
    public void queryReadUpdatedData()
    {
        result = dataCollectorPage.clickEditQuery(queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readQueryName("machinesdataupdate",queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readQueryDescription("Fetching Updated Machine Data");
        Result.rcEquals(result, "0");

        tags.remove(2);
        TestUtils.sleep(2);
        result = dataCollectorPage.readTags(tags);
        Result.rcEquals(result, "0");

        channelTags.remove(3);
        TestUtils.sleep(2);
        channelTags.set(0, "machinesdataupdate");
        result = dataCollectorPage.readChannel(channelTags);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readFormat("Default");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readSQLField("select * from Machines1 where ${CPU < ${cpuparam}}");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickCancelQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.selectQueryUsingActions("machinesdataupdate","Fetching Updated Machine Data");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readQueryInfo(tags, channelTags, "Default");
        Result.rcEquals(result, "0");
    }

    @DataProvider(name="MultipleValueUpdate")
    public Object[][] getDataFromDataproviderUpdate(){
        return new Object[][]
                {
                        { "fetchingcpudata","fetchingupdatedcpudata","cpuparameter", "cpuparam", "Fetching CPU Data", "CPU", "CPU", "Value" }
                };

    }

    @Test(priority = 220, dataProvider = "MultipleValueUpdate")
    public void queryUpdateDataParam(String prevQue, String QueryName, String prevParam,String paramValue, String Desc, String Value, String Label, String data)
    {

        result = dataCollectorPage.clickQueryInList(prevQue+ " (" + prevParam +")");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickEditQuery(querytypeParam);
        Result.rcEquals(result, "0");

        TestUtils.sleep(5);

        result = dataCollectorPage.typeTextIntoQueryName(QueryName, querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryParamVariable(paramValue);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryDescription(Desc, querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQuery("select * from Machines1");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResultforMultiSeries("HOSTNAME", "CPU", "RAM", "ID");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.selectValueColumn(Value);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.selectLabelColumn(Label);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchDefault(data,"100");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");

    }

    @DataProvider(name="MultipleValueUpdateRead")
    public Object[][] getDataFromDataproviderUpdateRead(){
        return new Object[][]
                {
                        { "fetchingupdatedcpudata", "cpuparam", "Fetching CPU Data", "CPU", "CPU", "Value" }
                };

    }

    @Test(priority = 230, dataProvider = "MultipleValueUpdateRead")
    public void queryReadUpdatedDataParam(String queryName, String param, String Desc, String value, String label, String data)
    {

        result = dataCollectorPage.clickQueryInList(queryName + " ("+param+")");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickEditQuery(querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readQueryName(queryName,querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readVariableName(param);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readQueryDescription(Desc);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readSQLField("select * from Machines1");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResultforMultiSeries("HOSTNAME", "CPU", "RAM", "ID");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readValues(value);
        Result.rcEquals(result,"0");

        result = dataCollectorPage.readValues(label);
        Result.rcEquals(result,"0");

        result = dataCollectorPage.readDefault(data);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickCancelQuery();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 240)
    public void openDashboardApplicationAg()
    {

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 250)
    public void deleteDashboardAfterQueryUpdate() {
        result = client.dashboardWS.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 260)
    public void uploadDashboardtoCheckQueryUpdate() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 270, enabled = false)
    public void uploadDashboardAg() {
        result = manageDashboardsPage.replaceDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 280)
    public void clickSelectedWidgetAg() {
        result = editDashboardPage.selectWidget(widget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 290)
    public void setDataChannelAg() {
        result = editDashboardPage.setDataChannel(dataChannel1);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 300)
    public void setDataFieldAg() {
        result = editDashboardPage.SetDataFieldIgnoreExisting(lDataFields);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 310)
    public void saveUploadedDashboardAg() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 320)
    public void viewDashboardAg() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 330)
    public void verifyingValueAfterUpdatingDashboard() {
        result = mainPage.verifyingValue(sDataFields4);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 340)
    public void clickOnLeftBarButtonAg() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 350)
    public void updatingParameterValueAg() {
        result = mainPage.assignvaluetosingleParam("200","cpuparam");
        Result.rcEquals(result, "0");
    }


    @Test(priority = 360)
    public void updatingParameterValueApplyAg() {
        result = mainPage.leftBarParameterApplyValue();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 370)
    public void verifyingValueAfterApplyingParameter() {
        result = mainPage.verifyingValue(sDataFields1);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 375)
    public void checkDataPointsAfterUpdate() {
        result = mainPage.checkdataPoints(sWidget,dataPoint);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 380)
    public void openDataCollectorApplication2()
    {
        TestUtils.sleep(2);
        result = mainPage.openDataCollectors();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 390)
    public void queryOptions()
    {
        TestUtils.sleep(3);

        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@name='iframer']"))); // switching to iframe

        TestUtils.sleep(4);

        result = dataCollectorPage.selectQueryUsingActions("machinesdataupdate","Fetching Updated Machine Data");
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

    @Test(priority = 400)
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

    @Test(priority = 410)
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

    @DataProvider(name="forQueryOptions")
    public Object[][] getDataFromDataprovider1(){
        return new Object[][]
                {
                        { "fetchingupdatedcpudata (cpuparam)"}
                };

    }


    @Test(priority = 420, dataProvider = "forQueryOptions")
    public void queryOptionsParam(String Query)
    {
        result = dataCollectorPage.clickQueryInList(Query);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.duplicateQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickQueryInList("copy of " + Query);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.duplicateQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickQueryInList("copy of copy of " + Query);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.duplicateQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickQueryInList("copy of " + Query);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickEditQueryInList();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickCancelQuery();
        Result.rcEquals(result, "0");

        TestUtils.sleep(2);

    }

    @DataProvider(name="forDeleteOptions")
    public Object[][] getDataFromDataprovider2(){
        return new Object[][]
                {
                        { "fetchingupdatedcpudata (cpuparam)"}
                };

    }

    @Test(priority = 430, dataProvider = "forDeleteOptions")
    public void queryDeleteParam(String Query)
    {

        result = dataCollectorPage.multipleQuerySelect("copy of copy of "+Query, "copy of copy of copy of "+Query);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickDeleteQueryTopOfList();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickQueryInList("copy of copy of "+Query);
        Result.rcEquals(result, "1");

        result = dataCollectorPage.clickQueryInList("copy of copy of copy of "+Query);
        Result.rcEquals(result, "1");

        result = dataCollectorPage.clickQueryInList(Query);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickDeleteQueryInList();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickQueryInList(Query);
        Result.rcEquals(result, "1");

        result = dataCollectorPage.clickQueryInList("copy of "+Query);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickDeleteQueryTopOfList();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickQueryInList(Query);
        Result.rcEquals(result, "1");


    }

    @Test(priority = 440)
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

