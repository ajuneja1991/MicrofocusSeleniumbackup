package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ScalingofDashboard
{

    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private Map<String, String> result;

    private String sSourceDashboard = "BaseDashboard";
    private String sDashboard ;
    private String sDashboardPath;
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;

    private String singleBar1 = "group133";
    private String singleBar2 = "group137";
    private String singleBar3 = "group141";
    private String singleBar4 = "group145";
    private String dashboardToLink = "Welcome";
    private String tagsSimple = "SingleBarChart";
    private String tagsWg = "SingleBarChartWg";
    private BvdWsClient client;
    private String json;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String userName = "TestScaling";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testScaling";
    private String desc = "For tester";
    private String category = "All";
    private String accessType = "full-control";
    private String sFitToPageScalingOption = "Fit-to-Page";
    private String sFitToWindowScalingOption = "Fit-to-Window";
    String roleId;
    private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");
    private void initPages() {
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

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        sDashboard = sSourceDashboard + "_BVDBarChart_" + BROWSER;
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
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 30)
    public void sendDataWg() throws JSONException, UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, KeyManagementException
    {
        json = "{ \n" +
                "\"items\" : [{  \n" +
                "\"id\": 1,\n" +
                "\"value_1\":120,\n" +
                "\"value_2\":30,\n" +
                "\"value_3\":40,\n" +
                "\"value_4\":50,\n" +
                "\"status\":\"red\"\n" +
                "},{\n" +
                "\"id\": \"2\",\n" +
                "\"value_1\":60,\n" +
                "\"value_2\":70,\n" +
                "\"value_3\":80,\n" +
                "\"value_4\":90,\n" +
                "\"status\":\"green\"\n" +
                "}]\n" +
                "}";

        result = client.receiverWS.bvdSendDataTagsOnly(get_key(), tagsWg, json);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 40)
    public void sendData() throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        json = "{\n" +
                "\"status\": \"SUCCESS\",\n" +
                "\"value1\": \"200\",  \n" +
                "\"value2\": \"200\",  \n" +
                "\"value3\": \"30\",  \n" +
                "\"value4\": \"40\"  \n" +
                "}";

        result = client.receiverWS.bvdSendDataTagsOnly(get_key(), tagsSimple, json);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 50)
    public void uploadDashboard() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 70)
    public void setupBarChart1() {

        result = editDashboardPage.selectWidget(singleBar1);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataChannel(tagsSimple);
        Result.rcEquals(result, "0");

        result = editDashboardPage.typeDataFieldNew("value1");
        Result.rcEquals(result, "0");

        result = editDashboardPage.setMaxValue("1000");
        Result.rcEquals(result, "0");

        result = editDashboardPage.setChartColors("#2C9FE4");
        Result.rcEquals(result, "0");

        result = editDashboardPage.setNumberFormat("'0o'");
        Result.rcEquals(result, "0");

    }

    @Test(priority = 80)
    public void setupBarChart2() {

        result = editDashboardPage.selectWidget(singleBar2);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataChannel(tagsWg);
        Result.rcEquals(result, "0");

        result = editDashboardPage.typeDataFieldNew("items/0/value_1");
        Result.rcEquals(result, "0");

        result = editDashboardPage.setShowChartNumbers(false);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setChartAutoScale(true);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setHyperlinkToAnotherDashboard(dashboardToLink);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 90)
    public void setupBarChart3() {

        result = editDashboardPage.selectWidget(singleBar3);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataChannel(tagsSimple);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setCalculationRule("value1 + value2", 50);
        Result.rcEquals(result, "0");

        result = editDashboardPage.typeDataFieldNew("calculatedProperty");
        Result.rcEquals(result, "0");

        result = editDashboardPage.setHyperlinkToUrl("https://www.google.de/");
        Result.rcEquals(result, "0");

    }

    @Test(priority = 100)
    public void setupBarChart4() {

        result = editDashboardPage.selectWidget(singleBar4);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataChannel(tagsSimple);
        Result.rcEquals(result, "0");

        result = editDashboardPage.typeDataFieldNew("value3");
        Result.rcEquals(result, "0");

        result = editDashboardPage.setVisibilityRule("value3<=50");
        Result.rcEquals(result, "0");

        result = editDashboardPage.save();
        Result.rcEquals(result, "0");

    }

    @Test(priority = 120)
    public void viewDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void openEditToVerifyDefaultScalingOption() {
        result = mainPage.clickQuickEditbutton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 140)
    public void verifyDefaultScalingOption() {
        result = editDashboardPage.verifyDefaultScalingOption(sFitToWindowScalingOption);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150)
    public void cancelEditDashboardPage() {
        result = editDashboardPage.cancelAfterQuickEdit(sDashboard);
        Result.rcEquals(result, "0");
    }

    /*@Test(priority = 160)
    public void viewDashboardToAssignFitToPageScalingOption() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }*/

    @Test(priority = 170)
    public void assignFitToPageScalingOption() {
        result = mainPage.clickQuickEditbutton();
        Result.rcEquals(result, "0");
        result = editDashboardPage.assignScalingOption(sFitToPageScalingOption);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void saveFitToPageScalingOptionDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
        result = editDashboardPage.verifyScrollBar(sFitToPageScalingOption);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void viewDashboardToAssignFitToWindowScalingOption() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void assignFitToWindowScalingOption() {
        result = mainPage.clickQuickEditbutton();
        Result.rcEquals(result, "0");
        result = editDashboardPage.assignScalingOption(sFitToWindowScalingOption);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 210)
    public void saveFitToWindowScalingOptionDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void testLogout() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 230)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 240)
    public void deleteDashboard() {
        result = client.dashboardWS.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @AfterClass
    public void cleanup()
    {
        driver.quit();
    }


}

