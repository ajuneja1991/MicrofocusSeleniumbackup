package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.pageobjects.bvd.*;
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

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MultiSeriesBarChart   {
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
    private String tags = "MultiSeriesBarChart";
    private BvdWsClient client;
    private String json;
    String queryType="DataQuery";

    private String datapointLocator ="svg path[d$='141.6862 Z']";
    private String sDataChannel ;
    private String HyperLinkforURL="g#group271 svg path[d*='M 63.4702 141.6862 L 116.29759999999999 141.6862 L 116.29759999999999 75.59020000000001 L 63.4702 75.59020000000001 L 63.4702 141.6862 Z']";
    private List<String> sDataFields = Arrays.asList("Data1", "Data2");
    private List<String> FieldsCalculationRule =Arrays.asList("calculatedProperty");
    private List<String> xyaxisTextFields = Arrays.asList("0","20","40","60","80","100","5:30 AM","2/28/2019","12:00 AM","3/1/2019","12:00 AM","3/2/2019","12:00 AM","3/3/2019","5:30 AM","3/4/2019");
    private String sLinkToDashboard="Welcome";
    private String sLinkToUrl = "https://www.google.de";
    private String sVisibilityRule = "Data1<10";
    private String sVisibility = "hidden";
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
    private String userName = "TesterMultiSeriesBar";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testmultibar";
    private String desc = "For MultiSeriesBar";
    private String category = "All";
    private String accessType = "full-control";

    String roleId;

    private List<String> datapointtext=Arrays.asList("Data1: 45 03/01/2019",
            "Data1: 92 03/02/2019",
            "Data1: 56 03/03/2019",
            "Data2: 46 03/01/2019",
            "Data2: 23 03/02/2019",
            "Data2: 67 03/03/2019");

    private List<String> dataPointcalculatedPropertytext=Arrays.asList("calculatedProperty: 91 03/01/2019",
            "calculatedProperty: 115 03/02/2019",
            "calculatedProperty: 123 03/03/2019");


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
        sSourceDashboard = "MultiSeriesBarChart";
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

    }

    @Test(priority = 40)
    public void getApiKeyWS() {
        result = client.userWS.getApiKey();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 42)
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

        result = dataCollectorPage.typeTextIntoQuery("select * from MultiSerieschart order by timestamp");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResultforMultiSeries("DATA1", "DATA2","TIMESTAMP","ID");
        Result.rcEquals(result, "0");

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


    @Test(priority = 95)
    public void apply() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void setDataField() {
        result = editDashboardPage.setDataFieldMultiSelect(sDataFields);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 101)
    public void applyagain() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }
    @Test(priority = 102)
    public void setTimeSpan() {
        result = editDashboardPage.setTimeSpanInMinutes("0");
        Result.rcEquals(result, "0");
    }
    @Test(priority = 105)
    public void saveUploadDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority=106)
    public void logoutcheck()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 107)
    public void testLoginnonadmin()
    {
        driver.get(URL);
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 108)
    public void login_checkfornonadmin()
    {
        result = loginPage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void viewDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120,enabled=false)
    public void checkLineChartTexts() {
        result = mainPage.getXandYaxistext(sWidget,xyaxisTextFields);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 125)
    public void checkData() {
        result = mainPage.barCount(sWidget,6);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 127)
    public void checkData_Title() {
        result = mainPage.getdataPointstitlewithoutGMT(sWidget,datapointtext,"2019",true);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void openManageDashboardsToSetShowLegend() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 140)
    public void openEditDashboardoToSetShowLegend() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150)
    public void selectWidgetToSetShowLegend() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 160)
    public void setShowLegend() {

        result = editDashboardPage.setShowLegend(true);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void applyforLegend() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }
    @Test(priority = 190)
    public void viewDashboardforlegend() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 200)
    public void validateshowLegend() {
        result = mainPage.barCount(sWidget,6);
        Result.rcEquals(result, "0");

        result = mainPage.checktextexist(sWidget,sDataFields);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 205)
    public void validateshowLegend_Title() {
        result = mainPage.getdataPointstitlewithoutGMT(sWidget,datapointtext,"2019",true);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 210)
    public void openManageDashboardsToSetCalculationRule() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void openEditDashboardoToSetCalculationRule() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 230)
    public void selectWidgetToSetCalculationRule() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 260)
    public void setSumCalculationRule() {
        result = editDashboardPage.setCalculationRule("Data1 + Data2", 50);
        Result.rcEquals(result, "0");

        result = editDashboardPage.checkCalculationRule(sWidget);
        Result.rcEquals(result, "0");

        result = editDashboardPage.clearDataFieldselectCalprop();
        Result.rcEquals(result, "0");

        if (BROWSER.equalsIgnoreCase("chrome")) {

            result = editDashboardPage.SetDataFieldIgnoreExisting(FieldsCalculationRule);
            Result.rcEquals(result, "0");
        }else{
            result = editDashboardPage.setDataFieldMultiSelectgecko(FieldsCalculationRule);
            Result.rcEquals(result, "0");

        }


    }

    @Test(priority = 300)
    public void applyforCalculationRule() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 310)
    public void viewDashboardforCalculationRule() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 320)
    public void checkDataforCalculationRule() {
        result = mainPage.barCount(sWidget,3);
        Result.rcEquals(result, "0");

        result = mainPage.checktextexist(sWidget,FieldsCalculationRule);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 325)
    public void DataforCalculationRuleTitle() {
        result = mainPage.getdataPointstitlewithoutGMT(sWidget,dataPointcalculatedPropertytext,"2019",true);
        Result.rcEquals(result, "0");
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
        result = editDashboardPage.setTimeSpanInMinutes("0");
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

    @Test(priority = 470,enabled=false)
    public void checkHyperlinkToAnotherDashboard() {
        result=mainPage.clickOnHyperLink(HyperLinkforURL);
        Result.rcEquals(result, "0");

        result = mainPage.getDashboard();
        Result.rcEquals(result, "0");
        Result.outContains(result, sLinkToDashboard);
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

    @Test(priority = 540,enabled=false)
    public void checkHyperlinkToUrl() {
        result = mainPage.clickOnHyperLink(HyperLinkforURL);
        Result.rcEquals(result, "0");

        result = mainPage.checkNewTabLink("https://www.google.de/");
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

    //Test this modify this
    @Test(priority = 620)
    public void checkVisibilityOfWidget() {
        result = mainPage.getVisibilityOfWidget(sWidget);
        Result.outEquals(result, sVisibility);
    }

    @Test(priority=630)
    public void logoutcheckfornonadmin()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 660)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 680)
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


