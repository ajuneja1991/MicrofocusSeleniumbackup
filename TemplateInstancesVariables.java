package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.*;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.hp.opr.qa.framework.utils.UiTestUtils;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
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
import java.util.List;
import java.util.Map;

/**
 * Created by Eva Sokolyanskaya on 02/08/2016.
 */
public class TemplateInstancesVariables  {
    private WebDriver driver;
    private Map<String, String> result;
    private BvdWsClient client;
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private TemplatePage templatePage;
    private String sourceDashboard ;
    private String dashboard ;
    private String dashboardPath;
    private String key;
    private String json;
    private String dims = "loc";
    private String tags = "instance_var";
    private String widget = "group792";
    private String widgetWrongParams = "group877";
    private String urlsWidget = "shape921";
    private String cityOneName = "Rome";
    private String cityTwoName = "Berlin";
    private String instanceOne = cityOneName + "_Instance";
    private String instanceTwo = cityTwoName + "_Instance";
    private String link = "https://www.google.co.in/${loc}/#{temp}";
    private String wrongParameter = "wrongParameter";
    private String wrongParamsLink = "https://www.google.co#q=${" + wrongParameter + "}";
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;

    List<String> dataFields;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;

    private String userName = "TestertemplateInstance";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testtemplateinstance";
    private String desc = "For templateinstance";
    private String category = "All";
    private String accessType = "full-control";
    String roleId;

    private void initPages() {

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
        templatePage = PageFactory.initElements(driver, TemplatePage.class);
    }

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        sourceDashboard = "sample_dashboard_test";
        dashboard = sourceDashboard + "_BVD_Instance_Variables_" + BROWSER;
        dashboardPath = "C:\\tmp\\" + dashboard + ".svg";
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        initPages();
        client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);

        dataFields = new ArrayList<>();
        dataFields.add("sales");
        dataFields.add("temp");
        dataFields.add("heads");
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
        result = TestUtils.downloadResource(this.getClass(), sourceDashboard + ".svg", dashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 20)
    public void getAPIKey() {
        result = client.userWS.getApiKey();
        Result.rcEquals(result, "0");

        key = result.get("out");
    }

    @Test(priority = 30)
    public void createTemplate_sendDataOne() throws UnrecoverableKeyException,
            NoSuchAlgorithmException,
            KeyStoreException, JSONException, KeyManagementException {
        json = "{\"loc\":\"" + cityOneName + "\"," +
                "\"sales\":\"1\"," +
                "\"temp\":\"11\"," +
                "\"heads\":\"111\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40)
    public void createTemplate_sendDataTwo() throws UnrecoverableKeyException,
            NoSuchAlgorithmException,
            KeyStoreException, JSONException, KeyManagementException {
        json = "{\"loc\":\"" + cityTwoName + "\"," +
                "\"sales\":\"2\"," +
                "\"temp\":\"22\"," +
                "\"heads\":\"222\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 42)
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }

    @Test(priority = 50)
    public void createTemplate_uploadDashboard() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.uploadDashboard(dashboardPath);
        Result.rcEquals(result, "0");

        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60)
    public void createTemplate_convertToTemplate() {
        result = manageDashboardsPage.openEditDashboard(dashboard);
        Result.rcEquals(result, "0");

        result = editDashboardPage.selectWidget(widget);
        Result.rcEquals(result, "0");

        String sDataChannel = tags + " " + cityOneName;
        result = editDashboardPage.setDataChannel(sDataChannel);
        Result.rcEquals(result, "0");

        result = editDashboardPage.convertToTemplate();
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataFieldMultiSelect(dataFields);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 65)
    public void createTemplate_setUrls() {
        result = editDashboardPage.selectWidget(urlsWidget);
        Result.rcEquals(result, "0");

        String sDataChannel = tags + " " + cityOneName;
        result = editDashboardPage.setDataChannel(sDataChannel);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataField("temp");
        Result.rcEquals(result, "0");

        //result = editDashboardPage.scrollToBottomDashboardProperties();
        //Result.rcEquals(result, "0");

        result = editDashboardPage.setHyperlinkToUrl(link);
        Result.rcEquals(result, "0");

        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");

        result = editDashboardPage.selectWidget(widgetWrongParams);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataChannel(sDataChannel);
        Result.rcEquals(result, "0");

        result = editDashboardPage.selectDataFieldFromDropdown("temp");
        Result.rcEquals(result, "0");

        //result = editDashboardPage.scrollToBottomDashboardProperties();
        //Result.rcEquals(result, "0");

        result = editDashboardPage.setHyperlinkToUrl(wrongParamsLink);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70)
    public void createTemplate_apply() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }


    @Test(priority=71)
    public void logoutcheck()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }



    @Test(priority = 72)
    public void testLoginnonadmin()
    {
        driver.get(URL);
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 75)
    public void login_checkfornonadmin()
    {
        result = loginPage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");
    }



    @Test(priority = 80)
    public void createTemplate_checkTemplateIsCreated() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.checkDashboardIsTemplate(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90)
    public void createInstances_enableAutoTest() {
        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void createInstances_createFirstInstance() {
        result = manageDashboardsPage.openTemplatePage(dashboard);
        Result.rcEquals(result, "0");

        result = templatePage.createInstance(instanceOne);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void createInstances_createSecondInstance() {
        result = templatePage.createInstance(instanceTwo);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void createInstances_checkInstancesAreCreated() {
        String[] names = {instanceOne, instanceTwo};
        result = mainPage.getDashboardNames();
        Result.outContains(result, names);
    }

    @Test(priority = 130)
    public void setFirstInstance_setFirstValue() {
        result = templatePage.selectInstance(instanceOne);
        Result.rcEquals(result, "0");

        result = templatePage.setVariableValue("loc", cityOneName);
        Result.rcEquals(result, "0");

        result = templatePage.getVariableValue("loc");
        Result.assertEquals(result.get("out"), cityOneName);
    }

    @Test(priority = 140)
    public void setSecondInstance_setFirstValue() {
        result = templatePage.selectInstance(instanceTwo);
        Result.rcEquals(result, "0");

        result = templatePage.setVariableValue("loc", cityTwoName);
        Result.rcEquals(result, "0");

        result = templatePage.getVariableValue("loc");
        Result.assertEquals(result.get("out"), cityTwoName);
    }

    @Test(priority = 150)
    public void getFirstDonutData_goToCityOne() {
        result = mainPage.viewDashboardforHyperlinkfortemplate(instanceOne);
        Result.rcEquals(result, "0");

        UiTestUtils.waitDocumentReadyState(driver, 1000);
    }

    @Test(priority = 160)
    public void getFirstDonutData_checkUrl() {
        result = mainPage.clickWidget(urlsWidget);
        Result.rcEquals(result, "0");

        result = mainPage.checkNewTabLink("https://www.google.co.in/Rome/11");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void getFirstDonutData_checkWrongParamsUrl() {
        result = mainPage.clickWidget(widgetWrongParams);
        Result.rcEquals(result, "0");

        result = mainPage.checkNewTabLink("https://www.google.com/#q=");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void getFirstDonutData_checkChartNumbers() {
        result = mainPage.getChartNumbers(widget);
        Result.outEquals(result, "[1, 11, 111]");
    }

    @Test(priority = 190)
    public void getFirstDonutData_sendNewDataCityOne() throws UnrecoverableKeyException,
            NoSuchAlgorithmException,
            KeyStoreException, JSONException, KeyManagementException {
        json = "{\"loc\":\"" + cityOneName + "\"," +
                "\"sales\":\"5\"," +
                "\"temp\":\"55\"," +
                "\"heads\":\"555\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void getFirstDonutData_checkChartNumbersUpdated() {
        result = mainPage.getChartNumbers(widget);
        Result.outEquals(result, "[5, 55, 555]");
    }

    @Test(priority = 210)
    public void getFirstDonutData_sendNewDataCityTwo() throws UnrecoverableKeyException,
            NoSuchAlgorithmException,
            KeyStoreException, JSONException, KeyManagementException {
        json = "{\"loc\":\"" + cityTwoName + "\"," +
                "\"site\":\"Big Ben\"," +
                "\"sales\":\"3\"," +
                "\"temp\":\"33\"," +
                "\"heads\":\"333\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void getFirstDonutData_checkChartNumbersNotUpdated() {
        result = mainPage.getChartNumbers(widget);
        Result.outEquals(result, "[5, 55, 555]");
    }

    @Test(priority = 230)
    public void getSecondDonutData_goToCityTwo() {
        result = mainPage.viewDashboardforHyperlinkfortemplate(instanceTwo);
        Result.rcEquals(result, "0");

        UiTestUtils.waitDocumentReadyState(driver, 15);
    }

    @Test(priority = 240)
    public void getSecondDonutData_checkUrl() {
        result = mainPage.clickWidget(urlsWidget);
        Result.rcEquals(result, "0");

        UiTestUtils.waitDocumentReadyState(driver, 15);

        result = mainPage.checkNewTabLink("https://www.google.co.in/Rome/55");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 250)
    public void getSecondDonutData_checkChartNumbers() {
        result = mainPage.getChartNumbers(widget);
        String[] expected = {"3", "33", "333"};
        Result.outContains(result, expected);
    }




    @Test(priority=270)
    public void logoutcheckfornonadmin()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 275)
    public void deleteTemplate() {
        client.templateWS.deleteTemplate(dashboard);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 280)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }




    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}
