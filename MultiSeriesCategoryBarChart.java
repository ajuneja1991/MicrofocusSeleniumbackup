package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.utils.ResultStore;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MultiSeriesCategoryBarChart  {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private DataCollectorPage dataCollectorPage;
    private WebDriver driver;
    private Map<String, String> result;
    private String sWidget = "group205";
    private String key;
    private String dims = "browser";
    private String tags = "MultiSeriesCategoryBarChart";
    private BvdWsClient client;
    private String json;
    String queryType="DataQuery";
    private String datapointLocator ="svg path[d$='141.6862 Z']";
    private String sDataChannel ;
    private String HyperLinkforURL="g#group271 svg path[d*='M 63.4702 141.6862 L 116.29759999999999 141.6862 L 116.29759999999999 75.59020000000001 L 63.4702 75.59020000000001 L 63.4702 141.6862 Z']";
    private List<String> sDataFields = Arrays.asList("Data1", "Data2");
    private List<String> FieldsCalculationRule =Arrays.asList("calculatedProperty");
    private List<String> xyaxisTextFields = Arrays.asList("0","100","200","300","400","hostA","hostB","hostC");
    private String sLinkToDashboard="Welcome";
    private String sLinkToUrl = "https://www.google.de";
    private String sVisibilityRule = "Data1<10";
    private String sVisibility = "hidden";
    private String sSourceDashboard = "MultiSeriesCategoryBarChart";
    private String sDashboard ;
    private String sDashboardPath;
    private String categoryColumn = "category";
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String userName = "TesterMultiCategBar";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testcategbar";
    private String desc = "For testerMultiCategBar";
    private String category = "All";
    private String accessType = "full-control";
    String roleId;

    private List<String> datapointtext=Arrays.asList("Data1: 100 hostA",
            "Data1: 200 hostB",
            "Data1: 400 hostC",
            "Data2: 70 hostA",
            "Data2: 170 hostB",
            "Data2: 320 hostC");

    private List<String> dataPointcalculatedProperty=Arrays.asList("calculatedProperty: 170 hostA",
            "calculatedProperty: 370 hostB",
            "calculatedProperty: 720 hostC");

    private List<String> dataPointcalculatedPropertyDiffCateg=Arrays.asList("calculatedProperty: 170 host1",
            "calculatedProperty: 370 host2",
            "calculatedProperty: 720 host3");


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

        result = dataCollectorPage.typeTextIntoQuery("select * from CategoryChartTable");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResultforMultiSeries("hostname", "Data1","Data2","id");
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


    @Test(priority = 100)
    public void apply() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void setDataField() {
        result = editDashboardPage.setDataFieldMultiSelect(sDataFields);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 111)
    public void applyagain() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void selectCategoryField() {
        result = editDashboardPage.setCategory(categoryColumn);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 140)
    public void saveUploadDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }



    @Test(priority=141)
    public void logoutcheck()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 142)
    public void testLoginnonadmin()
    {
        driver.get(URL);
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 143)
    public void login_checkfornonadmin()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }
    @Test(priority = 150)
    public void viewDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 160)
    public void checkBarChartTexts() {
        result = mainPage.getXandYaxistext(sWidget,xyaxisTextFields);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void checkData() {
        result = mainPage.barCount(sWidget,6);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 175)
    public void checkData_Title() {
        result = mainPage.getdataPointstitle(sWidget,datapointtext,"GMT",10,false);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void openManageDashboardsToSetShowLegend() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void openEditDashboardoToSetShowLegend() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void selectWidgetToSetShowLegend() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 210)
    public void setShowLegend() {

        result = editDashboardPage.setShowLegend(true);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 220)
    public void applyforLegend() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }
    @Test(priority = 230)
    public void viewDashboardforlegend() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 235)
    public void validateshowLegend_Title() {
        result = mainPage.getdataPointstitle(sWidget,datapointtext,"GMT",10,false);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 240)
    public void validateshowLegend() {
        result = mainPage.barCount(sWidget,8);
        Result.rcEquals(result, "0");

        result = mainPage.checktextexist(sWidget,sDataFields);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 250)
    public void openManageDashboardsToSetCalculationRule() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 260)
    public void openEditDashboardoToSetCalculationRule() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 270)
    public void selectWidgetToSetCalculationRule() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 280)
    public void setSumCalculationRule() {
        result = editDashboardPage.setCalculationRule("Data1 + Data2", 50);
        Result.rcEquals(result, "0");

        result = editDashboardPage.checkCalculationRule(sWidget);
        Result.rcEquals(result, "0");

        result = editDashboardPage.clearDataFieldselectCalprop();
        Result.rcEquals(result, "0");

        if (BROWSER.equalsIgnoreCase("chrome")) {

            result = editDashboardPage.setDataFieldMultiSelectold(FieldsCalculationRule);
            Result.rcEquals(result, "0");
        }else{
            result = editDashboardPage.setDataFieldMultiSelectgecko(FieldsCalculationRule);
            Result.rcEquals(result, "0");
        }
    }


    @Test(priority = 290)
    public void applyforCalculationRule() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 300)
    public void viewDashboardforCalculationRule() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 305)
    public void DataforCalculationRuleTitle() {
        result = mainPage.getdataPointstitle(sWidget,dataPointcalculatedProperty,"GMT",10,false);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 310)
    public void checkDataforCalculationRule() {
        result = mainPage.barCount(sWidget,4);
        Result.rcEquals(result, "0");
        result = mainPage.checktextexist(sWidget,FieldsCalculationRule);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 320)
    public void openManageDashboardsToSetHyperlinkToAnotherDashboard() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 330)
    public void openEditDashboardToSetHyperlinkToAnotherDashboard() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 340)
    public void selectWidgetToSetHyperlinkToAnotherDashboard() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 341)
    public void selectDiffCategoryField() {
        result = editDashboardPage.setCategory("hostname");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 350)
    public void setHyperlinkToAnotherDashboard() {
        result = editDashboardPage.setHyperlinkToAnotherDashboard(sLinkToDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 360)
    public void saveDashboardHyperlinkToAnotherDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 370)
    public void viewDashboardHyperlinkToAnotherDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 372)
    public void DataforDiffCategory() {
        result = mainPage.getdataPointstitle(sWidget,dataPointcalculatedPropertyDiffCateg,"GMT",10,false);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 375)
    public void checkDataforDiffCategory() {
        result = mainPage.barCount(sWidget,4);
        Result.rcEquals(result, "0");
        result = mainPage.checktextexist(sWidget,FieldsCalculationRule);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 380,enabled=false)
    public void checkHyperlinkToAnotherDashboard() {
        result=mainPage.clickOnHyperLink(HyperLinkforURL);
        Result.rcEquals(result, "0");

        result = mainPage.getDashboard();
        Result.rcEquals(result, "0");
        Result.outContains(result, sLinkToDashboard);
    }

    @Test(priority = 390)
    public void openManageDashboardsToSetHyperlinkToUrl() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 400)
    public void openEditDashboardToSetHyperlinkToUrl() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 410)
    public void selectWidgetToSetHyperlinkToUrl() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 420)
    public void setHyperlinkToUrl() {
        result = editDashboardPage.setHyperlinkToUrl(sLinkToUrl);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 430)
    public void saveDashboardHyperlinkToUrl() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 440)
    public void viewDashboardHyperlinkToUrl() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 450,enabled=false)
    public void checkHyperlinkToUrl() {
        result = mainPage.clickOnHyperLink(HyperLinkforURL);
        Result.rcEquals(result, "0");

        result = mainPage.checkNewTabLink("https://www.google.de/");
        Result.rcEquals(result, "0");

    }

    @Test(priority = 460)
    public void openManageDashboardsToSetVisibilityRule() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 470)
    public void openEditDashboardToSetVisibilityRule() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 480)
    public void selectWidgetToSetVisibilityRule() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 490)
    public void setVisibilityRule() {
        result = editDashboardPage.setVisibilityRule(sVisibilityRule);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 500)
    public void saveDashboardVisibilityRule() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 510)
    public void viewDashboardVisibilityRule() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    //Test this modify this
    @Test(priority = 520)
    public void checkVisibilityOfWidget() {
        result = mainPage.getVisibilityOfWidget(sWidget);
        Result.outEquals(result, sVisibility);
    }

    @Test(priority=530)
    public void logoutcheckfornonadmin()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 560)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }


    @Test(priority = 580)
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
