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
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AreaChart  {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private Map<String, String> result;
    private String sWidget = "group57";
    private String sDataChannel ;
    private String sDataField = "numberOfNormal";
    private String[] sAreaChrome = {"53.6758412277", "59.53054809"};
    private String[] sAreaIE = {"26.6566", "29.511"};
    private String[] sAreaFirefox = {"26.657269445733583", "29.51171875"};
    private String sNumberFormat = "'0o'";
    private String[] sChartNumbersNumberFormat = {"0th", "100th"};
    private Boolean sDontShowChartColors = false;
    private List<String> lNoChartNumbers = Collections.singletonList("");
    private String sChartColors = "#2AD2C9";
    private List<String> lChartColors = Collections.singletonList("#2AD2C9");
    private String sLinkToDashboard;
    private String sLinkToUrl = "https://www.google.de";
    private String sVisibilityRule = "numberOfUnknown!=5";
    private String sVisibility = "hidden";
    private String sSourceDashboard = "BaseDashboard";
    private String sDashboard ;
    private String sDashboardPath ;
    private String key;
    private String dims = "browser";
    private String tags = "AreaChart";
    private BvdWsClient client;
    private String json;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String userName = "Testerforareachart";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testareachart";
    private String desc = "For tester";
    private String category = "All";
    private String accessType = "view";
    String roleId;
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;

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
        sDataChannel = "AreaChart " + BROWSER;
        sDashboard = sSourceDashboard + "_AreaChart_" + BROWSER;
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

    @Test(priority = 45,enabled = false)
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }

    @Test(priority = 50)
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
        result = mainPage.viewDashboardNew(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void checkAreaChart() {
        result = mainPage.getAreaLineChart(sWidget);

        switch (BROWSER) {
            case "ie":
                Result.outContains(result, sAreaIE);
                break;
            case "chrome":
                Result.outContains(result, sAreaChrome);
                break;
            default:
                Result.outContains(result, sAreaFirefox);

                break;
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
        result = mainPage.viewDashboardNew(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 260)
    public void checkChartNumbersNumberFormat() {
        result = mainPage.getChartNumbers(sWidget);
        Result.outContains(result, sChartNumbersNumberFormat);
    }

    @Test(priority = 270)
    public void openManageDashboardsToDontShowChartNumbers() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 280)
    public void openEditDashboardToDontShowChartNumbers() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 290)
    public void selectWidgetToDontShowChartNumbers() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 300)
    public void dontShowChartNumbers() {
        result = editDashboardPage.setShowChartNumbers(sDontShowChartColors);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 310)
    public void saveDontShowChartNumbers() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 320)
    public void viewDashboardDontShowChartNumbers() {
        result = mainPage.viewDashboardNew(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 330)
    public void checkChartNumbersDontShowChartNumbers() {
        result = mainPage.getChartNumbers(sWidget);
        Result.outEquals(result, lNoChartNumbers.toString());
    }

    @Test(priority = 340)
    public void openManageDashboardsToSetChartColors() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 350)
    public void openEditDashboardToSetChartColor() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 360)
    public void selectWidgetToSetChartColor() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 370)
    public void setChartColor() {
        result = editDashboardPage.setChartColors(sChartColors);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 380)
    public void saveDashboardSetChartColor() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 390)
    public void viewDashboardSetChartColor() {
        result = mainPage.viewDashboardNew(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 400)
    public void checkChartColors() {
        result = mainPage.getColorsMultiAreaChartWidget(sWidget);
        Result.outEquals(result, lChartColors.toString());
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
        result = mainPage.viewDashboardNew(sDashboard);
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
        result = mainPage.viewDashboardNew(sDashboard);
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
            result.put("out", "Hyperlink to Url " + sLinkToUrl + " is working");
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
        result = mainPage.viewDashboardNew(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 620)
    public void checkVisibilityOfWidget() {
        result = mainPage.getVisibilityOfWidget(sWidget);
        Result.outEquals(result, sVisibility);
    }

    @Test(priority = 625)
    public void createRoleFoArea() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }

    @Test(priority = 630)
    public void logOutFromAdmin() {
        result = mainPage.logout();
    }


    @Test(priority = 640)
    public void loginAsNewUser() {
        driver.get(URL);
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 650)
    public void checkLogin()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 660)
    public void viewDashboardForNonAdminUser() {
        result = mainPage.viewDashboardNew(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 670)
    public void checkVisibilityOfWidgetForNonAdmin() {
        result = mainPage.getVisibilityOfWidget(sWidget);
        Result.outEquals(result, sVisibility);
    }

    @Test(priority = 680)
    public void logOutFromUser() {
        result = mainPage.logout();
    }

    @Test(priority = 710)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }


    @Test(priority = 730)
    public void deleteDashboard() {
        result = client.dashboardWS.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }


    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}
