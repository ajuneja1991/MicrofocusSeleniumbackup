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

public class ImageSelectionRule  {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private Map<String, String> result;
    private String sWidget = "group922";
    private String sDataChannel ;
    private String sStatusField = "statusImage";
    private String sRemoveStatusField = "";
    private String sStatusImageDataField = "yellow";
    private String sImageSelectionRule = "grey:status>10;green:status>=5;red";
    private String sStatusImageImageSelectionRuleStatus0 = "red";
    private String sStatusImageImageSelectionRuleStatus5 = "green";
    private String sStatusImageImageSelectionRuleStatus20 = "grey";
    private String sImageSelectionRuleDefaultFirst = "red;green:status==0";
    private String sStatusImageDefaultFirst = "red";
    private String sSourceDashboard = "sample_dashboard_test";
    private String sDashboard ;
    private String sDashboardPath ;
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String key;
    private String dims = "browser";
    private String tags = "ImageSelectionRule";
    private BvdWsClient client;
    private String json;
    private String userName = "TesterImageselection";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testImageselection";
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

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        sDataChannel = "ImageSelectionRule " + BROWSER;
        sDashboard = sSourceDashboard + "_BVDImageSelectionRule_" + BROWSER;
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
    @Test(priority =30)
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40)
    public void getApiKeyWS() {
        result = client.userWS.getApiKey();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50)
    public void sendData() throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        key = result.get("out");
        json = "{\"browser\":\"" + BROWSER + "\"," +
                "\"omiSystem\":\"tcpd123.deu.hp.com\"," +
                "\"status\":100, " +
                "\"statusImage\":\"yellow\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
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

    @Test(priority = 85)
    public void apply() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90)
    public void setDataChannel() {
        result = editDashboardPage.setDataChannel(sDataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void setStatusField() {
        result = editDashboardPage.setStatusField(sStatusField);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void save() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void viewDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void checkStatusImageOfWidget() {
        result = mainPage.getStatusImage(sWidget);
        Result.outEquals(result, sStatusImageDataField);
    }

    @Test(priority = 140)
    public void openManageDashboardsToSetImageSelectionRule() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150)
    public void openEditDashboardToSetImageSelectionRule() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 160)
    public void selectWidgetToSetImageSelectionRule() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 165)
    public void removeStatusField() {
        result = editDashboardPage.setStatusField(sRemoveStatusField);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void applyRemoveStatusField() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 175)
    public void setImageSelectionRule() {
        result = editDashboardPage.setImageSelectionRule(sImageSelectionRule);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void saveDashboardImageSelectionRule() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 185)
    public void sendDataStatus0() throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        json = "{\"browser\":\"" + BROWSER + "\"," +
                "\"omiSystem\":\"tcpd123.deu.hp.com\"," +
                "\"status\":0, " +
                "\"statusImage\":\"yellow\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 190)
    public void viewDashboardChangedImageSelectionRule() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void checkChangesStatusImageStatus0() {
        result = mainPage.getStatusImage(sWidget);
        Result.outEquals(result, sStatusImageImageSelectionRuleStatus0);
    }

    @Test(priority = 202)
    public void sendDataStatus5() throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        json = "{\"browser\":\"" + BROWSER + "\"," +
                "\"omiSystem\":\"tcpd123.deu.hp.com\"," +
                "\"status\":5, " +
                "\"statusImage\":\"yellow\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 204)
    public void checkChangesStatusImageStatus5() {
        TestUtils.sleepAndLog(1000);
        result = mainPage.getStatusImage(sWidget);
        Result.outEquals(result, sStatusImageImageSelectionRuleStatus5);
    }

    @Test(priority = 206)
    public void sendDataStatus20() throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        json = "{\"browser\":\"" + BROWSER + "\"," +
                "\"omiSystem\":\"tcpd123.deu.hp.com\"," +
                "\"status\":20, " +
                "\"statusImage\":\"yellow\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 208)
    public void checkChangesStatusImageStatus20() {
        TestUtils.sleepAndLog(1000);
        result = mainPage.getStatusImage(sWidget);
        Result.outEquals(result, sStatusImageImageSelectionRuleStatus20);
    }

    @Test(priority = 210)
    public void openManageDashboardsToChangeImageSelectionRuleOnceAgain() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void openEditDashboardToChangeImageSelectionRuleOnceAgain() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 230)
    public void selectWidgetToChangeImageSelectionRuleOnceAgain() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 240)
    public void changeImageSelectionRuleOnceAgain() {
        result = editDashboardPage.setImageSelectionRule(sImageSelectionRuleDefaultFirst);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 250)
    public void saveChangedDashboardImageSelectionRuleOnceAgain() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 260)
    public void viewDashboardChangedImageSelectionRuleOnceAgain() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 270)
    public void checkChangedStatusImageOfWidgetOnceAgain() {
        result = mainPage.getStatusImage(sWidget);
        Result.outEquals(result, sStatusImageDefaultFirst);
    }

    @Test(priority = 430)
    public void openManageDashboardsToDelete() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 440)
    public void deleteDashboard() {
        result = client.dashboardWS.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 450)
    public void testLogout() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 460)
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
