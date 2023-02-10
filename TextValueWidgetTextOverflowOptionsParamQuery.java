package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.*;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TextValueWidgetTextOverflowOptionsParamQuery{
    private Config cfg;
    private String BVD_URL;
    private String BVD_USR;
    private String BVD_PWD;
    private String BROWSER;
    private String PROXY_HOST;
    private int PROXY_PORT;
    private LoginPage loginPage;
    private MainPage mainPage;
    ManageDashboardsPage manageDashboardsPage;
    private DataCollectorPage dataCollectorPage;
    private WebDriver driver;
    private Map<String, String> result;
    BvdWsClient client;
    private List<String> tags = new ArrayList<>();
    private List<String> channelTags = new ArrayList<>();
    private List<String> dBFields = new ArrayList<>();
    String queryType="DataQuery";
    String querytypeParam="ParameterQuery";
    private String sSourceDashboard = "dashboard_parameter_query";
    private String sDashboard = sSourceDashboard + "_" + BROWSER;
    private String sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
    private EditDashboardPage editDashboardPage;
    private String widget1 = "shape1";
    private String widget2 = "shape2";
    private String widget3 = "shape3";
    private String dataChannel = "TextWidgetOverflowOptions";
    private List<String> sTextFields2 = Arrays.asList("sample sample sample sample sample samplesamplesamplesamplesample");
    private List<String> sTextFields3 = Arrays.asList("sample sample sample sample ...");
    private List<String> sTextFields4 = Arrays.asList("sample sample sample sample sample samplesamplesamplesamplesample");
    private List<String> sTextFields2_title = Arrays.asList("sample sample sample sample sample samplesamplesamplesamplesample");
    private List<String> sTextFields5 = Arrays.asList("###$$--->>");
    private List<String> sTextFields5_title = Arrays.asList("###$$--->>");


    private String hoverText = "sample sample sample sample sample samplesamplesamplesamplesample";
    private String id = "background";

    private String userName = "Tester";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "test";
    private String desc = "For tester";
    private String category = "All";
    private String accessType = "full-control";
    String roleId;

    private void initPages()
    {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
        dataCollectorPage = PageFactory.initElements(driver, DataCollectorPage.class);
    }

    @BeforeClass
    public void setup()
    {
        cfg = new TestConfig().getConfig();
        BVD_URL = cfg.getString("application.bvd.url.external");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD = cfg.getString("application.bvd.user.adminpwd");
        BROWSER = cfg.getString("environment.browser.name");
        PROXY_HOST = cfg.getString("environment.proxy.host");
        PROXY_PORT = cfg.getInt("environment.proxy.port");
        userLogin = "tester-" + BROWSER + "@microfocus.com";
        driver = UiTestWebDriver.getWebDriver();
        driver.get(BVD_URL);
        tags.add("AutomationTag1");
        channelTags.add("TextWidgetOverflowOptions");
        channelTags.add("AutomationTag1");
        dBFields.add("vdb.mambo.net");
        dBFields.add("5433");
        dBFields.add("opsadb");
        dBFields.add("dbadmin");
        dBFields.add("installed");
        initPages();
        try
        {
            client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
        } catch (Exception e)
        {
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
        System.out.println("dashboard = "+sSourceDashboard);
        System.out.println("dashboard path  = "+sDashboardPath);
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 3)
    public void deleteAllCreatedQueriesIfExist() throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException
    {
        client.collectorWS.deleteAllExistQueries();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 5)
    public void openDataCollectorApplication()
    {
        result = mainPage.openDataCollectors();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 10)
    public void newQueryCreation()
    {
        result = dataCollectorPage.clickNewQuery(queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryName("TextWidgetOverflowOptions",queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryDescription("Text Value Widgets overflow options",queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.createTag(tags.get(0));
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchFormat("Default");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQuery("select * from samplewidget where ${nodename=${bn}}");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickQueryInList("TextWidgetOverflowOptions");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readQueryInfo(tags, channelTags, "Default");
        Result.rcEquals(result, "0");

    }

    @Test(priority = 30)
    public void newParameterQueryCreation()
    {
        result = dataCollectorPage.clickNewQuery(querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryName("bn", querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryParamVariable("bn");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryDescription("Parameter query for text overflow options",querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchData("Database query");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchTab();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchTab();
        Result.rcEquals(result,"0");

        result = dataCollectorPage.typeTextIntoQuery("select * from samplewidget");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.selectValueColumn("nodename");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.selectLabelColumn("nodename");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchDefault("Value","abc");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");

    }

    @Test(priority = 50)
    public void openDashboardApplication()
    {

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60)
    public void uploadDashboard() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70)
    public void clickOnFirstTextWidget() {
        result = editDashboardPage.selectWidget(widget1);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void setDataChannelForFirstTextWidget() {
        result = editDashboardPage.setDataChannel(dataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90)
    public void setDataFieldForFirstTextWidget() {
        result = editDashboardPage.setDataField("nodename");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 91)
    public void verifyDefaultTextOverflowOptionforWidget1() {
        result = editDashboardPage.verifyDefaultTextOverflowOption();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 92)
    public void setWrapandTruncatetextforWidget1() {
        result = editDashboardPage.assignTextOverflowOptionForWidget();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 93)
    public void clickOnSecondTextWidget() {
        result = editDashboardPage.selectWidget(widget2);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 94)
    public void setDataChannelForSecondTextWidget() {
        result = editDashboardPage.setDataChannel(dataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 95)
    public void setDataFieldForSecondTextWidget() {
        result = editDashboardPage.setDataField("nodename");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 96)
    public void verifyDefaultTextOverflowOptionforWidget2() {
        result = editDashboardPage.verifyDefaultTextOverflowOption();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 92)
    public void setWrapandTruncatetextforWidget2() {
        result = editDashboardPage.assignTextOverflowOptionForWidget();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 98)
    public void clickOnThirdTextWidget() {
        result = editDashboardPage.selectWidget(widget3);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 99)
    public void setDataChannelForThirdTextWidget() {
        result = editDashboardPage.setDataChannel(dataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void setDataFieldForThirdTextWidget() {
        result = editDashboardPage.setDataField("nodename");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150)
    public void saveUploadedDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 160)
    public void viewDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void clickOnLeftBarButton() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void clickOnLeftBarParameterValue() {
        result = mainPage.verifySelectedValueOnLeftBar("bn","abc");
        Result.rcEquals(result, "0");
        result = mainPage.ParameterValueForTextWidget("bn","sample sample sample sample sample samplesamplesamplesamplesample");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 210)
    public void clickOnLeftBarParameterApplyValue() {
        result = mainPage.leftBarParameterApplyValue();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 225)
    public void verifyingTextAfterParameterWidget1() {
        result = mainPage.verifyingTextForWidget(id,sTextFields2,widget1);;
        Result.rcEquals(result, "0");
    }

    @Test(priority = 230)
    public void verifyingTextAfterParameter_titleWidget1() {
        result = mainPage.verifyingText_titleWidget(id,widget1,sTextFields2_title,"title");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 240)
    public void mouseHoverforTextVerifyingWidget1() {
        result = mainPage.mouseHoverforWidget(widget1, "52");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 250)
    public void verifyingHoverTextWidget1() {
        result = mainPage.verifyingHoverTextForWidget(widget1, hoverText);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 265)
    public void verifyingTextAfterParameterWidget2() {
        result = mainPage.verifyingTextForWidget(id,sTextFields3,widget2);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 270)
    public void verifyingTextAfterParameter_titleWidget2() {
        result = mainPage.verifyingText_titleWidget(id,widget2,sTextFields2_title,"title");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 280)
    public void mouseHoverforTextVerifyingWidget2() {
        result = mainPage.mouseHoverforWidget(widget2,"75");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 290)
    public void verifyingHoverTextWidget2() {
        result = mainPage.verifyingHoverTextForWidget(widget2,hoverText);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 300)
    public void verifyingTextAfterParameterWidget3() {
        result = mainPage.verifyingTextForWidget(id,sTextFields4,widget3);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 310)
    public void verifyingTextAfterParameter_titleWidget3() {
        result = mainPage.verifyingText_titleWidget(id,widget3,sTextFields2_title,"title");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 320)
    public void mouseHoverforTextVerifyingWidget3() {
        result = mainPage.mouseHoverforWidget(widget3,"52");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 330)
    public void verifyingHoverTextWidget3() {
        result = mainPage.verifyingHoverTextForWidget(widget3,hoverText);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 335)
    public void verifySelectedValueOnLeftBar() {
        result = mainPage.verifySelectedValueOnLeftBar("bn","sample sample sample sample sample samplesamplesamplesamplesample");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 340)
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }

    @Test(priority = 350)
    public void logOutFromAdmin() {
        result = mainPage.logout();
    }


    @Test(priority = 360)
    public void loginAsNewUser() {
        driver.get(BVD_URL);
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 370)
    public void checkLogin()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    /*@Test(priority = 380)
    public void viewDashboardForNonAdminUser() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
        TestUtils.sleep(2);
    }*/

    @Test(priority = 390)
    public void clickOnLeftBarButtonForNonAdmin() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 400)
    public void clickOnLeftBarParameterValueForNonAdmin() {
        result = mainPage.ParameterValueForTextWidget("bn","###$$$--->>");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 410)
    public void clickOnLeftBarParameterApplyValueForNonAdmin() {
        result = mainPage.leftBarParameterApplyValue();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 420)
    public void verifyingTextForNonAdminSpecialVariable() {
        result = mainPage.verifyingTextForWidget(id,sTextFields5,widget1);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 435)
    public void verifyingTextForNonAdmin_titleSpecialVariable() {
        result = mainPage.verifyingText_titleWidget(id,widget1,sTextFields5_title,"title");
        Result.rcEquals(result, "0");
    }
    @Test(priority = 440)
    public void logOutFromUser() {
        result = mainPage.logout();
    }

    @Test(priority = 450)
    public void loginIntoAdmin() {
        result = loginPage.login(BVD_USR, BVD_PWD);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 460)
    public void loginCheck()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 470)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 480)
    public void openDataCollectorForQueryDelete()
    {
        TestUtils.sleep(2);
        result = mainPage.openDataCollectors();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 490)
    public void queryDelete()
    {
        Actions actions = new Actions(driver);
        try {
            Robot robot = new Robot();

            robot.mouseMove(400,350);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        actions.click().build().perform();

        TestUtils.sleep(3);

        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@name='iframer']")));

        TestUtils.sleep(2);

        result = dataCollectorPage.clickQueryInList("TextWidgetOverflowOptions");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickDeleteQueryInList();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickQueryInList("TextWidgetOverflowOptions");
        Result.rcEquals(result, "1");

    }


    @Test(priority = 500)
    public void queryDeleteParam()
    {
        result = dataCollectorPage.clickQueryInList("bn");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickDeleteQueryInList();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickQueryInList("bn");
        Result.rcEquals(result, "1");
    }


    @Test(priority = 520)
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

