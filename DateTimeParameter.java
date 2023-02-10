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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DateTimeParameter {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private DataCollectorPage dataCollectorPage;
    private WebDriver driver;
    private Map<String, String> result;
    private String sWidget = "group271";
    private String key;
    private String dims = "browser";
    private String tags = "DateTimeParam";
    private BvdWsClient client;
    private String json;
    String queryType="DataQuery";
    private String sSourceDashboard ;
    private String sDashboard ;
    private String sDashboardPath;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    String queryName="paramquery_datetime";
    String paramVariable="parameter_dateTime";
    private List<String> sDataFields = Arrays.asList("Data1");
    private String sDataChannel;
    private String userName = "TesterDateTimeParam";
    private String userLogin;
    String querytypeParam="ParameterQuery";
    private String userPwd = "Test@123";
    private String roleName = "testdatetimeparam";
    private String desc = "For datetimeparam";
    private String category = "All";
    private String accessType = "full-control";
    private String Descparam="DateTimeparameter";

    String roleId;
    String fromdate="2017-01-27 08:00:00";
    List<String> fromdatelist=Arrays.asList("2017","Jan 2017","Jan 27, 2017","8","00","AM");
    List<String> todatelist=Arrays.asList("2017","Jan 2017","Jan 30, 2017","8","00","AM");
    List<String> fromdatelistIndefault=Arrays.asList("2017","Jan 2017","Jan 28, 2017","8","00","AM");
    List<String> todatelistIndefault=Arrays.asList("2017","Jan 2017","Jan 30, 2017","8","00","AM");

    String todate="2017-01-30 08:00:00";
    String beforefromdate="2016-01-23 09:00:00";
    String invalidateErrorMessage="The end date cannot be before the start date";

    private void initPages() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
        dataCollectorPage=PageFactory.initElements(driver, DataCollectorPage.class);
    }


    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        sDataChannel = tags.replace(",", " ") + " " + BROWSER;
        sSourceDashboard = "DateTimeParameter";
        sDashboard = sSourceDashboard + "_" + BROWSER;
        sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        initPages();
        try {
            client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 0)
    public void idmlogin() {
        result = loginPage.login(BVD_USR, BVD_PWD);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 10)
    public void login_check()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 20)
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30)
    public void getDashboardNames() {
        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40)
    public void getApiKeyWS() {
        result = client.userWS.getApiKey();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 45)
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }


    @Test(priority = 50)
    public void createData()  {

        result=mainPage.openDataCollectors();
        Result.rcEquals(result,"0");

        result = dataCollectorPage.clickNewQuery("DataQuery");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryName(sSourceDashboard+" "+BROWSER,queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryDescription(sSourceDashboard+" "+BROWSER+" Description",queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchFormat("Default");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQuery("select * from datetimeparam_table where ${timestamp > ${parameter_dateTime:start}} and ${timestamp < ${parameter_dateTime:end}} order by timestamp");

        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResultforMultiSeries("ID", "TIMESTAMP","DATA1","DATA2");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");


    }

    @Test(priority = 55)
    public void ParameterQueryCreation()
    {
        result = dataCollectorPage.clickNewQuery(querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryName(queryName, querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryParamVariable(paramVariable);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryDescription(Descparam,querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchData("Date");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchTab();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchTab();
        Result.rcEquals(result,"0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");

    }

    @Test(priority = 60)
    public void openManageDashboards() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70)
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
    }


    @Test(priority = 100)
    public void apply() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void setDataField() {
        result = editDashboardPage.setDataFieldMultiSelectTypeText(sDataFields);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 120)
    public void setTimeSpan() {
        result = editDashboardPage.setTimeSpanInMinutes("0");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void saveUploadDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void viewDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void validateDataforDefaultValue() {
        result = mainPage.barCount(sWidget,5);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 190)
    public void clickOnLeftBarButton() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void applyDateRangecorrect() {
        result = mainPage.selectdateparmsfromslideout(fromdatelist,todatelist);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 210)
    public void clickOnLeftBarParameterApplyValue() {
        result = mainPage.leftBarParameterApplyValue();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void validatedataforappliedValue() {
        result = mainPage.barCount(sWidget,2);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 240)
    public void editqueryToAssignDefaultDateValue() {

        result=mainPage.openDataCollectors();
        Result.rcEquals(result,"0");

        TestUtils.sleep(3);
        driver.switchTo().defaultContent();
        driver.switchTo().frame("contentFrame");

        result = dataCollectorPage.clickQueryInList("paramquery_datetime (parameter_dateTime)");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickEditQuery(querytypeParam);
        Result.rcEquals(result, "0");

        driver.switchTo().defaultContent();
        driver.switchTo().frame("contentFrame");


        result= dataCollectorPage.provideDefaultValueForDate(fromdatelistIndefault,todatelistIndefault);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");

    }

    @Test(priority=350)
    public void logOutfromAdmin()
    {
        driver.switchTo().defaultContent();
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 360)
    public void testLoginnonadmin()
    {
        //driver.get(URL);
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 370)
    public void login_checkfornonadmin()
    {
        result = loginPage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 380)
    public void viewDashboardforDateDefaultValue() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 390)
    public void validateDataforDateDefaultValue() {
        result = mainPage.barCount(sWidget,1);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 400)
    public void logOutNonAdmin() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");

    }

    @Test(priority = 630)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 640)
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


