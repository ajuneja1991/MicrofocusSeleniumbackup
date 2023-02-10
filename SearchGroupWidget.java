package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdJSONFileEdit.EditJSONFile;
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

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SearchGroupWidget {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private Map<String, String> result;
    private String sWidget = "group28";
    private String tags = "GroupWidget";
    private String sDataChannel = "with_widget_group9";
    private String sLinkToDashboard;
    private String sLinkToUrl = "https://www.google.de";
    private String sVisibilityRule = "numberOfUnknown!=5";
    private String sVisibility = "hidden";
    private String sSourceDashboard = "SearchGroupWidget";
    private String sDashboard ;
    private String sDashboardPath;
    private String key;
    private List<String> sDataSearchFields = Arrays.asList("id", "severity","performance");
    private String dims = "browser";
    private BvdWsClient client;
    private String json;
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;
    private String widget1 = "shape13";
    private String widget2 = "group14";
    private String widget3 = "shape22";
    private String widget4 = "group24";
    private String statusfieldCSS="#opr_status-input";
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String userName = "TesterSearchGroup";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testSearchGroup";
    private String desc = "For tester";
    private String category = "All";
    private String accessType = "full-control";
    String roleId;
    private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");

    private void initPages() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
    }


    private void runcolourDataGenerator() throws IOException
    {
        TestUtils.sleepAndLog(5000);
        Process p = Runtime.getRuntime().exec("cmd.exe /c cd \"C:\\testing\\Colouryourdata\\ColorYourData-master\\generators\\node\" & start cmd.exe /k \"node basicGenerator.js -f config.json\"");
    }

    private void terminatedataGenerator () throws IOException{
        Process p= Runtime.getRuntime().exec("taskkill /IM cmd.exe");
    }

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        sDashboard = sSourceDashboard + "_" + BROWSER;
        sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        initPages();
        try {
            //client = new BvdRestClient(PROXY_HOST, PROXY_PORT);
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

    @Test(priority = 18)
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 20)
    public void getDashboardNames() {
        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");


    }

    @Test(priority = 40)
    public void getApiKeyWS() {
        result = client.userWS.getApiKey();
        Result.rcEquals(result, "0");
        key = result.get("out");
    }

    @Test(priority = 50)
    public void sendData() throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException ,IOException, org.json.JSONException {

        EditJSONFile.editAPIKeyinJSON("C:\\testing\\Colouryourdata\\ColorYourData-master\\generators\\node\\config.json","apiKey",key);
        runcolourDataGenerator();
        TestUtils.sleep(10);
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
        result = editDashboardPage.selectstatusFromDropdown("severity");
        Result.rcEquals(result, "0");
    }

    @Test(priority=120)
    public void setWidget3() {

        result = editDashboardPage.selectWidget(widget3);
        Result.rcEquals(result, "0");
        result = editDashboardPage.setDataField("severity");
        Result.rcEquals(result, "0");
    }

    @Test(priority=130)
    public void setWidget4() {

        result = editDashboardPage.selectWidget(widget4);
        Result.rcEquals(result, "0");
        result = editDashboardPage.selectDataFieldFromDropdown("performance");
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


    @Test(priority = 160)
    public void typeTextinSearch() {
        result = mainPage.typeTextinSearchBox();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void SearchFunctionality() {
        result = mainPage.validateSearchFunctionality();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void testLogout() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 190)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }


    @Test(priority = 200)
    public void deleteDashboard() {
        result = client.dashboardWS.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void killcmd() throws IOException{
        Runtime.getRuntime().exec("c:\\Windows\\System32\\taskkill /IM  node.exe /F");
        Runtime.getRuntime().exec("c:\\Windows\\System32\\taskkill /F /IM cmd.exe");
    }

    @AfterClass
    public void cleanup() throws IOException
    {
        //terminatedataGenerator();

        driver.quit();
    }

}
