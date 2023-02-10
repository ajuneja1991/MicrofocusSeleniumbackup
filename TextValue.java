package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.*;
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

public class TextValue  {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private DataCollectorPage dataCollectorPage;
    private WebDriver driver;
    private Map<String, String> result;
    private String sWidget = "shape452";
    private String localdatesWidget="shape453";
    private String localvaluesWidget="shape454";
    private String sDataChannel ;
    private String sDataField = "status";
    private String localdatesDataField="date";
    private String localvaluesDataField="data2";
    private String sText = "20";
    private String sText_title="20";
    private String sTextLocaldates="1/23/2017, 11:00:00 PM";
    private String sTextLocaldates_title="1/23/2017, 11:00:00 PM";
    private String sTextLocalvalues_title="60,000,000";
    private String sTextLocalvalues="60,000,000";
    private String sNumberFormat = "'0o'";
    private String sTextFormatted = "20th";
    private String sColoringRule = "#FF0000:status==20;";
    private String sColor = "#FF0000";
    private String sLinkToUrl = "https://www.google.de/";
    private String sVisibilityRule = "status!=20";
    private String sVisibility = "hidden";
    private String sSourceDashboard = "BaseDashboard";
    private String dashboardToLink = "Welcome";
    private String DefectDataChannel = "TextValueDefect";
    private String sDashboard ;
    private String sDashboardPath ;
    private String key;
    private String dims = "browser";
    private String tags = "TextValue";
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;
    private BvdWsClient client;
    private String json;
    private String userName = "TesterTextvalue";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testtextvalue";
    private String desc = "For tester";
    private String category = "All";
    private String accessType = "full-control";
    String queryType="DataQuery";
    String roleId;
    private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");

    private void initPages() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
        dataCollectorPage = PageFactory.initElements(driver, DataCollectorPage.class);
    }

    private String defaultWidgetJson()
    {
        return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"unchanged\"," +
                "\"tags\":[],\"dims\":[],\"queryText\":\"SELECT * FROM public" +
                ".datetimeparam_table_test where id = 23\",\"availableColumns\":[\"id\",\"timestamp\",\"data1\"," +
                "\"data2\"],\"sampleQueryResult\":{\"id\":23,\"timestamp\":\"2017-01-23T23:00:00.342Z\",\"data1\":8," +
                "\"data2\":60000000}},\"name\":\"TextValue\"}}";
    }

    private String TextValueWidgetDefectJson()
    {
        return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"unchanged\"," +
                "\"tags\":[],\"dims\":[],\"queryText\":\"select '120005' as value_column " +
                "from dual\",\"availableColumns\":[" +
                "\"value_column\"],\"sampleQueryResult\":{\"value_column\":120005" +
                "}},\"name\":\"TextValueDefect\"}}";
    }

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        sDataChannel = "TextValue " + BROWSER;
        sDashboard = sSourceDashboard + "_BVDTextValue_" + BROWSER;
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

    @Test(priority = 3)
    public void deleteAllCreatedQueriesIfExist() throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException
    {
        client.collectorWS.deleteAllExistQueries();
        result = client.collectorWS.bvdDataCollectorCreateQuery(defaultWidgetJson());
        Result.rcEquals(result, "0");

        result = client.collectorWS.bvdDataCollectorCreateQuery(TextValueWidgetDefectJson());
        Result.rcEquals(result, "0");
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
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg",  sDashboardPath);
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
                "\"viewName\":\"OprSample\"," +
                "\"ciId\":\"c51a039f38fe4b1db16d23181ce869c2\"," +
                "\"ciName\":\"Gold ESS\"," +
                "\"ciType\":\"BusinessService\"," +
                "\"kpiName\":\"System Performance\"," +
                "\"kpiId\":1002," +
                "\"status\":20}";

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
        result = editDashboardPage.setDataField(sDataField);
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

    @Test(priority = 125)
    public void checkTextOfWidget_title() {
        result = mainPage.getTextOfTextValueWidget_title(sWidget);
        Result.outEquals(result, sText_title);
    }

    @Test(priority = 130)
    public void checkTextOfWidget() {
        result = mainPage.getTextOfTextValueWidget(sWidget);
        Result.outEquals(result, sText);
    }


    @Test(priority = 132)
    public void clickQuickEdit() {
        result = mainPage.clickQuickEditbutton();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 135)
    public void selectWidgetlocaldate() {
        result = editDashboardPage.selectWidget(localdatesWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 140)
    public void setDataChannellocaldate() {
        result = editDashboardPage.setDataChannel("TextValue");
        Result.rcEquals(result, "0");
    }


    @Test(priority = 145)
    public void setDataFieldlocaldate() {
        result = editDashboardPage.setDataField("timestamp");
        Result.rcEquals(result, "0");
    }


    @Test(priority = 150)
    public void selectWidgetlocalvalue() {
        result = editDashboardPage.selectWidget(localvaluesWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 155)
    public void setDataChannellocalvalue() {
        result = editDashboardPage.setDataChannel("TextValue");
        Result.rcEquals(result, "0");
    }


    @Test(priority = 160)
    public void setDataFieldlocalvalue() {
        result = editDashboardPage.setDataField("data2");
        Result.rcEquals(result, "0");
    }


    @Test(priority = 180)
    public void saveUploadDashboardlocaldate() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 185)
    public void viewDashboardlocaldate() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void checkTextOfWidgetlocaldate() {
        result = mainPage.getTextOfTextValueWidget(localdatesWidget);
        Result.outEquals(result, sTextLocaldates);
    }

    @Test(priority = 193)
    public void checkTextOfWidgetlocaldate_title() {
        result = mainPage.getTextOfTextValueWidget_title(localdatesWidget);
        Result.outEquals(result, sTextLocaldates_title);
    }

    @Test(priority = 195)
    public void checkTextOfWidgetlocalvalue() {
        result = mainPage.getTextOfTextValueWidget(localvaluesWidget);
        Result.outEquals(result, sTextLocalvalues);
    }

    @Test(priority = 197)
    public void checkTextOfWidgetlocalvalue_title() {
        result = mainPage.getTextOfTextValueWidget_title(localvaluesWidget);
        Result.outEquals(result, sTextLocalvalues_title);
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

    @Test(priority = 221)
    public void setNumberFormat() {
        result = editDashboardPage.setNumberFormat(sNumberFormat);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 222)
    public void selectWidgetToSetChannelforDefect() {
        result = editDashboardPage.selectWidget(localvaluesWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 225)
    public void setColumnForDefect() {
        result = editDashboardPage.clearandSetDataChannel(DefectDataChannel);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 227)
    public void setDatFieldForDefect() {
        result = editDashboardPage.setDataField("value_column");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 240)
    public void saveDashboardNumberFormat() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 250)
    public void viewDashboardNumberFormat() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 260)
    public void checkNumberFormatOfWidget() {
        result = mainPage.getTextOfTextValueWidget(sWidget);
        Result.outEquals(result, sTextFormatted);
    }

    @Test(priority = 265)
    public void checkDataForDefect() {
        result = mainPage.getTextOfTextValueWidget(localvaluesWidget);
        Result.outEquals(result, "120005");
    }

    @Test(priority = 270)
    public void openManageDashboardsToSetColoringRule() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 280)
    public void openEditDashboardToSetColoringRule() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 290)
    public void selectWidgetToSetColoringRule() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 300)
    public void setColoringRule() {
        result = editDashboardPage.setColoringRule(sColoringRule);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 310)
    public void saveDashboardColoringRule() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 320)
    public void viewDashboardColoringRule() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 330)
    public void checkColorOfWidget() {
        result = mainPage.getColorOfTextValueWidget(sWidget);
        Result.outEquals(result, sColor);
    }

    @Test(priority = 340)
    public void openManageDashboardsToSetHyperlinkToAnotherDashboard() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 350)
    public void openEditDashboardToSetHyperlinkToAnotherDashboard() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 360)
    public void selectWidgetToSetHyperlinkToAnotherDashboard() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 370)
    public void setHyperlinkToAnotherDashboard() {
        result = editDashboardPage.setHyperlinkToAnotherDashboard(dashboardToLink);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 380)
    public void saveDashboardHyperlinkToAnotherDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 390)
    public void viewDashboardHyperlinkToAnotherDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 400)
    public void checkHyperlinkToAnotherDashboard() {
        result = mainPage.clickWidgetText(sWidget);
        Result.rcEquals(result, "0");

        result = mainPage.getDashboard();
        Result.rcEquals(result, "0");
        Result.outContains(result, dashboardToLink);
    }

//    @Test(priority = 345)
//    public void closeCurrentPage() {
//      result = mainPage.closeCurrentPage();
//      Result.rcEquals(result, "0");
//    }

    @Test(priority = 410)
    public void openManageDashboardsToSetHyperlinkToUrl() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 420)
    public void openEditDashboardToSetHyperlinkToUrl() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 430)
    public void selectWidgetToSetHyperlinkToUrl() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 440)
    public void setHyperlinkToUrl() {
        result = editDashboardPage.setHyperlinkToUrl(sLinkToUrl);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 450)
    public void saveDashboardHyperlinkToUrl() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 460)
    public void viewDashboardHyperlinkToUrl() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 470)
    public void checkHyperlinkToUrl() {
        result = mainPage.clickWidgetText(sWidget);
        Result.rcEquals(result, "0");

        result = mainPage.checkNewTabLink("https://www.google.de/");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 480)
    public void openManageDashboardsToSetVisibilityRule() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 490)
    public void openEditDashboardToSetVisibilityRule() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 500)
    public void selectWidgetToSetVisibilityRule() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 510)
    public void setVisibilityRule() {
        result = editDashboardPage.setVisibilityRule(sVisibilityRule);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 520)
    public void saveDashboardVisibilityRule() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 530)
    public void viewDashboardVisibilityRule() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 540)
    public void checkVisibilityOfWidget() {
        result = mainPage.getVisibilityOfWidget(sWidget);
        Result.outEquals(result, sVisibility);
    }

    @Test(priority = 550)
    public void testLogout() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 560)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 570)
    public void deleteDashboards() throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException{
        result = client.dashboardWS.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");

        client.collectorWS.deleteAllExistQueries();
    }

    @AfterClass
    public void cleanup() {

        driver.quit();
    }
}
