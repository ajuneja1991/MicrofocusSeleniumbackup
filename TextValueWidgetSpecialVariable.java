package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.*;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TextValueWidgetSpecialVariable {

    private Config cfg;
    private String BVD_URL;
    private String BVD_USR;
    private String BVD_PWD;
    private String PROXY_HOST;
    private int PROXY_PORT;
    private String BROWSER;
    private LoginPage loginPage;
    private MainPage mainPage;
    ManageDashboardsPage manageDashboardsPage;
    private DataCollectorPage dataCollectorPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private Map<String, String> result;
    BvdWsClient client;
    private List<String> tags = new ArrayList<>();
    private List<String> channelTags = new ArrayList<>();
    private List<String> dBFields = new ArrayList<>();
    String queryType="DataQuery";
    String querytypeParam="ParameterQuery";
    private String sSourceDashboard = "TextValueWidgetSpecialVariable";
    private String sDashboard = sSourceDashboard + "_" + BROWSER;
    private String sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
    private String dataChannel = "TextWidgetForSpecialVariable";
    private String url ;
    private String widget1 = "group7";
    private String textWidget1 = "shape5";
    private String textWidget2 = "shape6";
    private String textWidget3 = "shape9";
    private String id = "background";
    private String appendValue1 = "&page=1";
    private String appendValue2 = "&page=2";
    private String appendValue3 = "&page=0";
    private String appendValue4 = "&page=-1";
    private String appendValue5 = "&page=30";
    private String appendValue6 = "&page=32";
    private String currentPage = "0";
    private String currentPage1 = "1";
    private String currentPage2 = "2";
    private String currentPage3 = "30";
    private String totalPages = "30";
    private String firstTotalPage = "0";
    private String specialVariableText = "${bvd_currentuser} ${bvd_currentdate} ${bvd_currenttime} ${bvd_currentpage} ${bvd_totalpages}";
    DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("M/d/yyyy");
    LocalDateTime now = LocalDateTime.now();
    private String date = dtf1.format(now);
    DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("a");
    private String AmPm = dtf2.format(now);
    private List<String> sTextFields1 = Arrays.asList("", "", specialVariableText);
    private List<String> sTextFieldsFirst = Arrays.asList("admin",date,AmPm,"${bvd_currentpage}","${bvd_totalpages}");
    private List<String> sTextFields2 = Arrays.asList("admin",date,AmPm,currentPage1,totalPages);
    private List<String> sTextFields3 = Arrays.asList("admin",date,AmPm,currentPage2,totalPages);
    private List<String> sTextFields4 = Arrays.asList("admin",date,AmPm,currentPage3,totalPages);
    private List<String> sTextFields5 = Arrays.asList("admin",date,AmPm);

    private void initPages(){
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        dataCollectorPage = PageFactory.initElements(driver, DataCollectorPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
    }

    @BeforeClass
    public void setup(){

        cfg = new TestConfig().getConfig();
        BVD_URL = cfg.getString("application.bvd.url.external");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD = cfg.getString("application.bvd.user.adminpwd");
        BROWSER = cfg.getString("environment.browser.name");
        PROXY_HOST = cfg.getString("environment.proxy.host");
        PROXY_PORT = cfg.getInt("environment.proxy.port");
        url=BVD_URL+"/#/show/"+ sDashboard+"?params=none";
        driver = UiTestWebDriver.getWebDriver();
        driver.get(BVD_URL);
        tags.add("AutomationTag1");
        channelTags.add("TextWidgetForSpecialVariable");
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

        result = dataCollectorPage.typeTextIntoQueryName("TextWidgetForSpecialVariable",queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryDescription("Text Value Widgets For Special Variable",queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.createTag(tags.get(0));
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchFormat("Use in widget group");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQuery("select * from bvd_lwr_demo where ${ID = ${id}} and ${sold_item = ${solditem}}");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResultforMultiSeries("ID", "location", "timestamp", "sold_item");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickQueryInList("TextWidgetForSpecialVariable");
        Result.rcEquals(result, "0");

    }

    @Test(priority = 20)
    public void queryReadSavedData()
    {

        result = dataCollectorPage.clickEditQuery(queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readQueryName("TextWidgetForSpecialVariable",queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readQueryDescription("Text Value Widgets For Special Variable");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readTags(tags);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readChannel(channelTags);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readFormat("Use in Widget Group");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readSQLField("select * from bvd_lwr_demo where ${ID = ${id}} and ${sold_item = ${solditem}}");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResultforMultiSeries("ID", "location", "timestamp", "sold_item");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30,dataProvider = "MultipleValue")
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

        result = dataCollectorPage.typeTextIntoQuery("select * from bvd_lwr_demo");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResultforMultiSeries("ID", "location", "timestamp", "sold_item");
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
                        { "idparam", "id", "Text Value Widgets for special variable", "ID", "ID", "None" },
                        { "solditemparam", "solditem", "Text Value Widgets for special varibale", "sold_item", "sold_item", "None" }
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

        result = dataCollectorPage.readSQLField("select * from bvd_lwr_demo");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResultforMultiSeries("ID", "location", "timestamp", "sold_item");
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
    public void clickOnGroupWidget() {
        result = editDashboardPage.selectWidget(widget1);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void setDataChannelForGroupWidget() {
        result = editDashboardPage.setDataChannel(dataChannel);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 90)
    public void clickOnFirstTextWidget() {
        result = editDashboardPage.selectWidget(textWidget1);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void setDataFieldForFirstTextWidget() {
        result = editDashboardPage.setDataField("ID");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void clickOnSecondTextWidget() {
        result = editDashboardPage.selectWidget(textWidget2);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void setDataFieldForSecondTextWidget() {
        result = editDashboardPage.setDataField("sold_item");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void saveUploadedDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 140)
    public void viewDashboard() {
        result = mainPage.viewDashboardNew(sDashboard);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 150)
    public void verifyingText() {
        result = mainPage.verifyingTextfornontruncatevalues(id,sTextFields1);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 160)
    public void openDashboardApplicationForApplyingDatachannelToSpecialVariable()
    {

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void openEditDashboardoForApplyingDatachannelToSpecialVariable() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        com.mf.opr.qa.framework.runner.Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void clickOnSpecialVariableTextWidget() {
        result = editDashboardPage.selectWidget(textWidget3);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void setDataChannelForSpecialvariable() {
        result = editDashboardPage.setDataChannel(dataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void saveUploadedDashboardAfterAppylingDatachannel() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 210)
    public void viewDashboardAfterApplyingDatachannel() {
        result = mainPage.viewDashboardNew(sDashboard);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 220)
    public void verifyingTextAfterApplyingDatachannel() {
        result = mainPage.verifyingTextForSpecialvariable(id,sTextFieldsFirst);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 230)
    public void AppendingUrlAndRedirectingCase1(){

        result = mainPage.gettingCurrentUrlAndAppending(url,appendValue1);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 240)
    public void verifyingTextAfterAppendinngUrlCase1() {
        result = mainPage.verifyingTextForSpecialvariable(id,sTextFields2);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 250)
    public void AppendingUrlAndRedirectingCase2(){

        result = mainPage.gettingCurrentUrlAndAppending(url,appendValue2);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 260)
    public void verifyingTextAfterAppendinngUrlCase2() {
        result = mainPage.verifyingTextForSpecialvariable(id,sTextFields3);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 270)
    public void AppendingUrlAndRedirectingCase3(){

        result = mainPage.gettingCurrentUrlAndAppending(url,appendValue3);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 280)
    public void verifyingTextAfterAppendinngUrlCase3() {
        result = mainPage.verifyingTextForSpecialvariable(id,sTextFieldsFirst);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 290)
    public void AppendingUrlAndRedirectingCase4(){

        result = mainPage.gettingCurrentUrlAndAppending(url,appendValue4);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 300)
    public void verifyingTextAfterAppendinngUrlCase4() {
        result = mainPage.verifyingTextForSpecialvariable(id,sTextFieldsFirst);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 310)
    public void AppendingUrlAndRedirectingCase5(){

        result = mainPage.gettingCurrentUrlAndAppending(url,appendValue5);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 320)
    public void verifyingTextAfterAppendinngUrlCase5() {
        result = mainPage.verifyingTextForSpecialvariable(id,sTextFields4);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 330)
    public void AppendingUrlAndRedirectingCase6(){

        result = mainPage.gettingCurrentUrlAndAppending(url,appendValue6);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 340)
    public void verifyingTextAfterAppendinngUrlCase6() {
        result = mainPage.verifyingTextForSpecialvariable(id,sTextFieldsFirst);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 350)
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
