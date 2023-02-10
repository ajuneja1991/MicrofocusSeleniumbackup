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

public class SparklineChart  {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private Map<String, String> result;
    private String sWidget = "group77";

    private String key;
    private String dims = "browser";
    private String tags = "SparkLineChart";
    private BvdWsClient client;
    private String json;
    private String sDataChannel ;
    private String sDataField = "numberOfCritical";
    private String[] sSparklineChrome = {"53.444"};
    private String[] sSparklineIE = {"35.3064"};
    private String[] sSparklineFirefox = {"35.307264397905755"};
    private String sNumberFormat = "'0o'";
    private String[] sMouseOverNumberFormat = {"25th"};
    private String sLinkToDashboard;
    private String sLinkToUrl = "https://www.google.de";
    private String sVisibilityRule = "numberOfUnknown!=5";
    private String sVisibility = "hidden";
    private String sSourceDashboard = "BaseDashboard";
    private String sDashboard ;
    private String sDashboardPath ;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String userName = "Testersparkline";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testsparkline";
    private String desc = "For tester";
    private String category = "All";
    private String accessType = "full-control";
    String roleId;
    private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;

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
        sDataChannel = tags.replace(",", " ") + " " + BROWSER;
        sDashboard = sSourceDashboard + "_SparkLineChart_" + BROWSER;
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

    @Test(priority = 30)
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40)
    public void getDashboardNames() {
        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");

        sLinkToDashboard = "Welcome";
    }


    @Test(priority = 45)
    public void sendData() throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        key = result.get("out");
        json = "{\"browser\":\"" + BROWSER + "\"," +
                "\"omiSystem\":\"tcpd123.deu.hp.com\"," +
                "\"mdb\":\"OMi Health Status\"," +
                "\"label\":\"Overall\"," +
                "\"type\":\"PIE\"," +
                "\"filterName\":\"OMi Health Status Overall\"," +
                "\"viewFilterName\":\"OMi Deployment with HP Operations Agents\"," +
                "\"filterStatus\":\"FILTER_OK\"," +
                "\"mostCritical\":3," +
                "\"numberOfCritical\":25," +
                "\"numberOfMajor\":20," +
                "\"numberOfMinor\":15," +
                "\"numberOfNormal\":10," +
                "\"numberOfUnknown\":5," +
                "\"statusColor\":\"#C8C800\"}";

        result = client.receiverWS.bvdSendData(get_key(), dims, tags, json);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 55)
    public void sendData2() throws JSONException, UnrecoverableKeyException,
          NoSuchAlgorithmException, KeyStoreException, KeyManagementException
    {
        key = result.get("out");
        json = "{\"browser\":\"" + BROWSER + "\"," +
              "\"omiSystem\":\"tcpd123.deu.hp.com\"," +
              "\"mdb\":\"OMi Health Status\"," +
              "\"label\":\"Overall\"," +
              "\"type\":\"PIE\"," +
              "\"filterName\":\"OMi Health Status Overall\"," +
              "\"viewFilterName\":\"OMi Deployment with HP Operations Agents\"," +
              "\"filterStatus\":\"FILTER_OK\"," +
              "\"mostCritical\":3," +
              "\"numberOfCritical\":25," +
              "\"numberOfMajor\":20," +
              "\"numberOfMinor\":15," +
              "\"numberOfNormal\":10," +
              "\"numberOfUnknown\":5," +
              "\"statusColor\":\"#C8C800\"}";
        
        result = client.receiverWS.bvdSendData(get_key(), dims, tags, json);
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


    @Test(priority = 95)
    public void apply() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void setDataField() {
        result = editDashboardPage.selectDataFieldFromDropdown(sDataField);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 105)
    public void saveUploadDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }
    
    
    
    @Test(priority = 110)
    public void viewDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void checkSparklineChart() {
        result = mainPage.getAreaLineChart(sWidget);

        if (BROWSER.equals("ie")) {
            Result.outContains(result, sSparklineIE);
        } else if (BROWSER.equals("chrome")) {
            Result.outContains(result, sSparklineChrome);
        } else {
            Result.outContains(result, sSparklineFirefox);

        }
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void openManageDashboardsToSetNumberFormat() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 210)
    public void openEditDashboardToSetNumberFormat() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void selectWidgetToSetNumberFormat() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 230)
    public void setNumberFormat() {
        result = editDashboardPage.setNumberFormat(sNumberFormat);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 240)
    public void saveSetNumberFormat() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 250)
    public void viewDashboardSetNumberFormat() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 260)
    public void checkMouseOverNumberFormat() {
        result = mainPage.mouseOver(sWidget);
        Result.rcEquals(result, "0");

        result = mainPage.getMouseOver(sWidget);
        Result.outContains(result, sMouseOverNumberFormat);
    }

    @Test(priority = 410)
    public void openManageDashboardsToSetHyperlinkToAnotherDashboard() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 420)
    public void openEditDashboardToSetHyperlinkToAnotherDashboard() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 430)
    public void selectWidgetToSetHyperlinkToAnotherDashboard() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 440)
    public void setHyperlinkToAnotherDashboard() {
        result = editDashboardPage.setHyperlinkToAnotherDashboard(sLinkToDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 450)
    public void saveDashboardHyperlinkToAnotherDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 460)
    public void viewDashboardHyperlinkToAnotherDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 470, enabled = false)
    public void checkHyperlinkToAnotherDashboard() {
        result = mainPage.getHyperlink(sWidget);
        Result.rcEquals(result, "0");

        // string compare
        if (result.get("out").contains(sLinkToDashboard)) {
            result = ResultStore.success(sWidget + " hast hyperlink to dashoard " + sLinkToDashboard);
        } else {
            result = ResultStore.failure(sWidget + " is does not have hyperlink to another dashboard, but " + result.get("out"));
        }
        Result.rcEquals(result, "0");

        result = mainPage.clickWidget(sWidget);
        Result.rcEquals(result, "0");

        result = mainPage.getDashboard();
        Result.outEquals(result, sLinkToDashboard);
    }

    @Test(priority = 480)
    public void openManageDashboardsToSetHyperlinkToUrl() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 490)
    public void openEditDashboardToSetHyperlinkToUrl() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 500)
    public void selectWidgetToSetHyperlinkToUrl() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 510)
    public void setHyperlinkToUrl() {
        result = editDashboardPage.setHyperlinkToUrl(sLinkToUrl);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 520)
    public void saveDashboardHyperlinkToUrl() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 530)
    public void viewDashboardHyperlinkToUrl() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 540, enabled = false)
    public void checkHyperlinkToUrl() {
        result = mainPage.getHyperlink(sWidget);
        Result.outEquals(result, sLinkToUrl);

        result = mainPage.clickWidget(sWidget);
        Result.rcEquals(result, "0");

        // string compare
        if (driver.getCurrentUrl().equals(sLinkToUrl)) {
            result = ResultStore.success("Hyperlink to Url " + sLinkToUrl + " is working");
        } else {
            result = ResultStore.failure("Hyperlink to Url " + sLinkToUrl + " is not working, instead " + result.get("out") + " is shown");
        }
        Result.rcEquals(result, "0");
    }

    @Test(priority = 550, enabled = false)
    public void back() {
        // open manage Dashboard to edit Dashboard
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

    @Test(priority = 560)
    public void openManageDashboardsToSetVisibilityRule() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 570)
    public void openEditDashboardToSetVisibilityRule() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 580)
    public void selectWidgetToSetVisibilityRule() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 590)
    public void setVisibilityRule() {
        result = editDashboardPage.setVisibilityRule(sVisibilityRule);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 600)
    public void saveDashboardVisibilityRule() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 610)
    public void viewDashboardVisibilityRule() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 620)
    public void checkVisibilityOfWidget() {
        result = mainPage.getVisibilityOfWidget(sWidget);
        Result.outEquals(result, sVisibility);
    }

    @Test(priority = 630)
    public void openManageDashboardsToDelete() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 635)
    public void testLogout() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 637)
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
    public void cleanup() {
        driver.quit();
    }
}
