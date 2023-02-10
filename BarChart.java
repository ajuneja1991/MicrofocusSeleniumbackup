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
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BarChart {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private Map<String, String> result;

    private String sWidget = "group3";
    private String sDataChannel;
    private List<String> lDataField = Collections.singletonList("numberOfCritical");
    private List<String> lDataFields = Arrays.asList("numberOfCritical", "numberOfMajor",
          "numberOfMinor", "numberOfNormal", "numberOfUnknown", "cpuLoad", "ramUsage");
    private List<String> lChartNumber = Collections.singletonList("20");
    private List<String> lChartNumberFormatted = Collections.singletonList("20th");
    private List<String> lChartHeight = Collections.singletonList("18.61"); // rounded value
    private List<String> lChartHeightMaxValue = Collections.singletonList("93.07");
    private List<String> lChartColorDefault = Collections.singletonList("#00BC74");
    private List<String> lChartColor = Collections.singletonList("#FF0000");
    private List<String> lChartColors = Arrays.asList("#FF0000, #FF9933, #C8C800, #33CC33, #B2B2B2, #7030A0, #0078B9");
    private List<String> lChartNumbers = Arrays.asList("20th", "15th", "10th", "75th", "0th",
          "77th", "89th");
    private List<String> lChartHeights = Arrays.asList("20.92", "15.69", "10.46", "78.43", "0.00",
          "80.52", "93.07");
    private String sVisibility = "hidden";
    private String sMaxValue = "10";
    private String sChartColors = "#FF0000;#FF9933;#C8C800;#33CC33;#B2B2B2";
    private String sNumberFormat = "'0o'";
    private String sLinkToDashboard ;
    private String sLinkToUrl = "https://www.google.de/";
    private String sVisibilityRule = "numberOfCritical!=20";
    private String sSourceDashboard = "BaseDashboard";
    private String sDashboard ;
    private String sDashboardPath ;
    private String sDashboardPath2 ;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;

    private String key;
    private String dims = "browser";
    private String tags = "BarChart";
    private BvdWsClient client;
    private String json;
    private String userName = "Testerbarchart";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testbarchart";
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
        sDataChannel = "BarChart " + BROWSER;
        sLinkToDashboard = "Dashboard_for_link_BVDBarChart_" + BROWSER;
        sDashboard = sSourceDashboard + "_BVDBarChart_" + BROWSER;
        sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
        sDashboardPath2 = "C:\\tmp\\" + sLinkToDashboard + ".svg";
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

    @Test(priority = 20)
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30)
    public void uploadDashboardForLink() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg",
                sDashboardPath2);
        Result.rcEquals(result, "0");

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.uploadDashboard(sDashboardPath2);
        Result.rcEquals(result, "0");

        result = editDashboardPage.save();
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
                "\"mdb\":\"OMi Health Status\"," +
                "\"label\":\"Overall\"," +
                "\"type\":\"PIE\"," +
                "\"filterName\":\"OMi Health Status Overall\"," +
                "\"viewFilterName\":\"OMi Deployment with HP Operations Agents\"," +
                "\"filterStatus\":\"FILTER_OK\"," +
                "\"mostCritical\":3," +
                "\"numberOfCritical\":20," +
                "\"numberOfMajor\":15," +
                "\"numberOfMinor\":10," +
                "\"numberOfNormal\":75," +
                "\"numberOfUnknown\":0," +
                "\"cpuLoad\":77," +
                "\"ramUsage\":89," +
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

    @Test(priority = 91)
    public void apply() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void setDataField() {
        result = editDashboardPage.setDataFieldMultiSelect(lDataField);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void saveUploadDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void viewDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void checkChartNumber() {
        result = mainPage.getChartNumbers(sWidget);
        Result.outEquals(result, lChartNumber.toString());
    }

    @Test(priority = 140)
    public void checkChartHeight() {
        result = mainPage.getChartHeightsOfBarChartWidget(sWidget);
        Result.outEquals(result, lChartHeight.toString());
    }

    @Test(priority = 150)
    public void checkDefaultChartColors() {
        result = mainPage.getColorsOfBarChartWidget(sWidget);
        Result.outEquals(result, lChartColorDefault.toString());
    }

    @Test(priority = 160)
    public void openManageDashboardsToSetMaxValue() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void openEditDashboardToSetMaxValue() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void selectWidgetToSetMaxValue() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void setMaxValue() {
        result = editDashboardPage.setMaxValue(sMaxValue);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void saveDashboardMaxValue() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 210)
    public void viewDashboardMaxValue() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void checkChartNumbersMaxValue() {
        result = mainPage.getChartNumbers(sWidget);
        Result.outEquals(result, lChartNumber.toString());
    }

    @Test(priority = 230)
    public void checkChartHeightsMaxValue() {
        result = mainPage.getChartHeightsOfBarChartWidget(sWidget);
        Result.outEquals(result, lChartHeightMaxValue.toString());
    }

    @Test(priority = 240)
    public void openManageDashboardsToSetChartAutoScale() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 250)
    public void openEditDashboardToSetChartAutoScale() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 260)
    public void selectWidgetToSetChartAutoScale() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 270)
    public void setChartAutoScale() {
        result = editDashboardPage.setChartAutoScale(true);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 280)
    public void saveDashboardChartAutoScale() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 290)
    public void viewDashboardChartAutoScale() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 300)
    public void checkChartNumbersChartAutoScale() {
        result = mainPage.getChartNumbers(sWidget);
        Result.outEquals(result, lChartNumber.toString());
    }

    @Test(priority = 310)
    public void checkChartHeightsChartAutoScale() {
        result = mainPage.getChartHeightsOfBarChartWidget(sWidget);
        Result.outEquals(result, lChartHeightMaxValue.toString());
    }

    @Test(priority = 320)
    public void openManageDashboardsToSetNumberFormat() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 330)
    public void openEditDashboardToSetNumberFormat() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 340)
    public void selectWidgetToSetNumberFormat() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 350)
    public void setNumberFormat() {
        result = editDashboardPage.setNumberFormat(sNumberFormat);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 360)
    public void saveDashboardNumberFormat() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 370)
    public void viewDashboardNumberFormat() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 380)
    public void checkNumberFormatOfChartNumbers() {
        result = mainPage.getChartNumbers(sWidget);
        Result.outEquals(result, lChartNumberFormatted.toString());
    }

    @Test(priority = 390)
    public void openManageDashboardsToSetChartColors() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 400)
    public void openEditDashboardToSetChartColors() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 410)
    public void selectWidgetToSetChartColors() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 420)
    public void setChartColors() {
        result = editDashboardPage.setChartColors(sChartColors);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 430)
    public void saveDashboardChartColors() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 440)
    public void viewDashboardChartColors() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 450)
    public void checkChartColors() {
        result = mainPage.getColorsOfBarChartWidget(sWidget);
        Result.outEquals(result, lChartColor.toString());
    }

    @Test(priority = 460)
    public void openManageDashboardsToSetDataFields() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 470)
    public void openEditDashboardToSetDataFields() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 480)
    public void selectWidgetToSetDataFields() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 490)
    public void setDataFields() {
        if (BROWSER.equalsIgnoreCase("chrome")) {

            result = editDashboardPage.SetDataFieldIgnoreExisting(lDataFields);
            Result.rcEquals(result, "0");
        }else{
            result= editDashboardPage.clearDataFieldselectCalprop();
            Result.rcEquals(result, "0");

            result = editDashboardPage.setDataFieldMultiSelectgecko(lDataFields);
            Result.rcEquals(result, "0");

        }
    }

    @Test(priority = 500)
    public void saveDashboardDataFields() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 510)
    public void viewDashboardDataFields() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 520)
    public void checkChartColorsDataFields() {
        result = mainPage.getColorsOfBarChartWidget(sWidget);
        Result.outEquals(result, lChartColors.toString());
    }

    @Test(priority = 530)
    public void checkChartNumbersDataFields() {
        result = mainPage.getChartNumbers(sWidget);
        Result.outEquals(result, lChartNumbers.toString());
    }

    @Test(priority = 540)
    public void checkChartHeightDataFields() {
        result = mainPage.getChartHeightsOfBarChartWidget(sWidget);
        Result.outEquals(result, lChartHeights.toString());
    }


    @Test(priority = 550)
    public void openManageDashboardsToSetHyperlinkToAnotherDashboard() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 560)
    public void openEditDashboardToSetHyperlinkToAnotherDashboard() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 570)
    public void selectWidgetToSetHyperlinkToAnotherDashboard() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 580)
    public void setHyperlinkToAnotherDashboard() {
        result = editDashboardPage.setHyperlinkToAnotherDashboard(sLinkToDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 590)
    public void saveDashboardHyperlinkToAnotherDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 600)
    public void viewDashboardHyperlinkToAnotherDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 610)
    public void checkHyperlinkToAnotherDashboard() {
        result = mainPage.clickWidget(sWidget);
        Result.rcEquals(result, "0");

        result = mainPage.getDashboard();
        Result.rcEquals(result, "0");
        Result.outContains(result, sLinkToDashboard);
    }

    @Test(priority = 620)
    public void openManageDashboardsToSetHyperlinkToUrl() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 630)
    public void openEditDashboardToSetHyperlinkToUrl() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 640)
    public void selectWidgetToSetHyperlinkToUrl() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 650)
    public void setHyperlinkToUrl() {
        result = editDashboardPage.setHyperlinkToUrl(sLinkToUrl);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 660)
    public void saveDashboardHyperlinkToUrl() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 670)
    public void viewDashboardHyperlinkToUrl() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 680)
    public void checkHyperlinkToUrl() {

        result = mainPage.clickWidget(sWidget);
        Result.rcEquals(result, "0");

        result = mainPage.checkNewTabLink("https://www.google.de/");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 700)
    public void openManageDashboardsToSetVisibilityRule() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 710)
    public void openEditDashboardToSetVisibilityRule() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 720)
    public void selectWidgetToSetVisibilityRule() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 730)
    public void setVisibilityRule() {
        result = editDashboardPage.setVisibilityRule(sVisibilityRule);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 740)
    public void saveDashboardVisibilityRule() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 750)
    public void viewDashboardVisibilityRule() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 760)
    public void checkVisibilityOfWidget() {
        result = mainPage.getVisibilityOfWidget(sWidget);
        Result.outEquals(result, sVisibility);
    }


    @Test(priority = 765)
    public void testLogout() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 770)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 780)
    public void deleteDashboard() {
        result = client.dashboardWS.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");

        result = client.dashboardWS.deleteDashboard(sLinkToDashboard);
        Result.rcEquals(result, "0");
    }


    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}
