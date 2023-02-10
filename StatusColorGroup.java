package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.utils.ResultStore;
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

public class StatusColorGroup {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private Map<String, String> result;
    private String sWidget = "group178";
    private String sDataChannel;
    private String sColoringRuleDataField = "statusColor";
    private String sColoringRule = "#FF0000:status==20;";
    private String sLinkToDashboard;
    private String sLinkToUrl = "https://www.google.de";
    private String sVisibilityRule = "status!=20";
    private String sSourceDashboard = "BaseDashboard";
    private String sDashboard ;
    private String sDashboardPath ;
    private String sColor = "#33CC33";
    private String sColorColoringRule = "#FF0000";
    private String sVisibility = "hidden";
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
    private String tags = "StatusColorGroup";
    private BvdWsClient client;
    private String json;
    private String userName = "Testerstatuscolorgroup";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "teststatuscolorgroup";
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
        sDataChannel = "StatusColorGroup " + BROWSER;
        sDashboard = sSourceDashboard + "_BVDStatusColorGroup_" + BROWSER;
        sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        //super.setup(true);
        //driver = getWebDriver();
        //driver.get(directLogin);
        //driver.get(EXT_BVD_URL);

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

    @Test(priority = 17)
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg",  sDashboardPath);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 20)
    public void getDashboardNames() {
        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");

        sLinkToDashboard = "Welcome";
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
                "\"viewName\":\"OprSample\"," +
                "\"ciId\":\"c51a039f38fe4b1db16d23181ce869c2\"," +
                "\"ciName\":\"Gold ESS\"," +
                "\"ciType\":\"BusinessService\"," +
                "\"kpiName\":\"System Performance\"," +
                "\"kpiId\":1002," +
                "\"status\":20, " +
                "\"statusColor\":\"#33CC33\"}";

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

    @Test(priority = 90)
    public void setDataChannel() {
        result = editDashboardPage.setDataChannel(sDataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 91)
    public void apply() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void setColoringRule() {
        result = editDashboardPage.setColoringRule(sColoringRuleDataField);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void saveDashboardColoringRule() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void viewDashboardColoringRule() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void checkColorOfWidget() {
        result = mainPage.getColorOfStatusColorGroupWidget(sWidget);
        Result.outEquals(result, sColor);
    }

    @Test(priority = 140)
    public void openManageDashboardsToChangeColoringRule() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150)
    public void openEditDashboardToChangeColoringRule() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 160)
    public void selectWidgetToChangeColoringRule() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void changeColoringRule() {
        result = editDashboardPage.setColoringRule(sColoringRule);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void saveChangedDashboardColoringRule() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void viewDashboardChangedColoringRule() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void checkChangesColorOfWidget() {
        result = mainPage.getColorOfStatusColorGroupWidget(sWidget);
        Result.outEquals(result, sColorColoringRule);
    }

    @Test(priority = 210)
    public void openManageDashboardsToSetHyperlinkToAnotherDashboard() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void openEditDashboardToSetHyperlinkToAnotherDashboard() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 230)
    public void selectWidgetToSetHyperlinkToAnotherDashboard() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 240)
    public void setHyperlinkToAnotherDashboard() {
        result = editDashboardPage.setHyperlinkToAnotherDashboard(sLinkToDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 250)
    public void saveDashboardHyperlinkToAnotherDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 260)
    public void viewDashboardHyperlinkToAnotherDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 270, enabled = false)
    public void checkHyperlinkToAnotherDashboard() {
        result = mainPage.getHyperlink(sWidget);
        Result.outContains(result, sLinkToDashboard);

        result = mainPage.clickWidget(sWidget);
        Result.rcEquals(result, "0");

        result = mainPage.getDashboard();
        Result.outEquals(result, sLinkToDashboard);
    }

    @Test(priority = 280)
    public void openManageDashboardsToSetHyperlinkToUrl() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 290)
    public void openEditDashboardToSetHyperlinkToUrl() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 300)
    public void selectWidgetToSetHyperlinkToUrl() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 310)
    public void setHyperlinkToUrl() {
        result = editDashboardPage.setHyperlinkToUrl(sLinkToUrl);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 320)
    public void saveDashboardHyperlinkToUrl() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 330)
    public void viewDashboardHyperlinkToUrl() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 340, enabled = false)
    public void checkHyperlinkToUrl() {
        result = mainPage.getHyperlink(sWidget);
        Result.outEquals(result, sLinkToUrl);

        result = mainPage.clickWidget(sWidget);
        Result.outEquals(result, sLinkToUrl);
    }

    @Test(priority = 350, enabled = false)
    public void back() {
        driver.navigate().back();
        TestUtils.sleepAndLog(2000);
        // string compare
        if (driver.getCurrentUrl().contains(URL)) {
            result = ResultStore.success("Backward navigation was successful");
        } else {
            result = ResultStore.failure("Backward navigation was not working, instead " + result.get("out") + " is shown");
        }
        Result.rcEquals(result, "0");
    }

    @Test(priority = 360)
    public void openManageDashboardsToSetVisibilityRule() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 370)
    public void openEditDashboardToSetVisibilityRule() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 380)
    public void selectWidgetToSetVisibilityRule() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 390)
    public void setVisibilityRule() {
        result = editDashboardPage.setVisibilityRule(sVisibilityRule);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 400)
    public void saveDashboardVisibilityRule() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 410)
    public void viewDashboardVisibilityRule() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 420)
    public void checkVisibilityOfWidget() {
        result = mainPage.getVisibilityOfWidget(sWidget);
        Result.outEquals(result, sVisibility);
    }




    @Test(priority = 430)
    public void testLogout() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 435)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 440)
    public void deleteDashboard() {
        result = client.dashboardWS.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }


    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}
