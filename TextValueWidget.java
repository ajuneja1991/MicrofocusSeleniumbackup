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

public class TextValueWidget{
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
    private String sSourceDashboard = "TextValueWidget";
    private String sDashboard = sSourceDashboard + "_" + BROWSER;
    private String sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
    private EditDashboardPage editDashboardPage;
    private String widget1 = "shape1";
    private String widget2 = "shape2";
    private String dataChannel = "TextWidget";
    private List<String> sTextFields1 = Arrays.asList("Model Name is:", "OS is:");
    private List<String> sTextFields1_title = Arrays.asList("Model Name is:", "OS is:");
    private List<String> sTextFields2 = Arrays.asList("Model Name is: A", "OS is: Windows");
    private List<String> sTextFields2_title = Arrays.asList("Model Name is: A", "OS is: Windows");
    private List<String> sTextFields3 = Arrays.asList("Model Name is: A", "OS is: Windows");
    private List<String> sTextFields3_title = Arrays.asList("Model Name is: A", "OS is: Windows");
    private List<String> sTextFields4 = Arrays.asList("Model Name is: B", "OS is: Ub/antu");
    private List<String> oslist = Arrays.asList("Windows", "Linux", "Ubantu", "Ub/antu", "Ub\\antu");
    private List<String> sTextFields5 = Arrays.asList("Model Name is: C", "OS is: Ub\\antu");
    private String id1 = "slideOutPanel";


    private String hoverText = "OS is: Windows";
    private String id = "background";

    private String userName = "Tester";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "test";
    private String desc = "For tester";
    private String category = "All";
    private String accessType = "full-control";
    String roleId;
    private String errorMsg = "Invalid values: the parameter query returns values with a semicolon";
    private String value = "val;ue";
    private String errorMsgDefault = "Default value must not contain a semicolon ';'";
    private String value_dataQuery = "";
    private String label_dataQuery = "";

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
        channelTags.add("TextWidget");
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

        result = dataCollectorPage.typeTextIntoQueryName("TextWidget",queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryDescription("Text Value Widgets",queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.createTag(tags.get(0));
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchFormat("Default");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkSemicolonInOSList("select * from TextWidget_Defect where ${Model = ${MODEL_NAME}} and ${OS = ${os}}",
                                                        errorMsg,"Data Query",value_dataQuery,label_dataQuery);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkRemovingSemicolonInOSList("select * from TextWidget_Defect where ${Model = ${MODEL_NAME}} and ${OS = ${os}}" +
                        "and OS NOT LIKE 'Li%'","Data Query",value_dataQuery,label_dataQuery);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQuery("select * from TextWidget where ${Model = ${MODEL_NAME}} and ${OS = ${os}}");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResultforMultiSeries("Process", "Model", "OS", "CPU");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickQueryInList("TextWidget");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.readQueryInfo(tags, channelTags, "Default");
        Result.rcEquals(result, "0");

    }

    @Test(priority = 30,dataProvider = "MultipleValue")
    public void newParameterQueryCreation(String queryName, String paramVariable, String Desc, String Value, String label, String def)
    {
        result = dataCollectorPage.clickNewQuery(querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryName(queryName, querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryParamVariable(paramVariable);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryDescription(Desc,querytypeParam);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchData("Database query");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchTab();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchTab();
        Result.rcEquals(result,"0");

        result = dataCollectorPage.checkSemicolonInOSList("select * from TextWidget_Defect",
                errorMsg,"Parameter Query",Value,label);
        Result.rcEquals(result,"0");

        result = dataCollectorPage.checkRemovingSemicolonInOSList("select * from TextWidget_Defect " +
                "where OS NOT LIKE 'Li%'", "Parameter Query",Value,label);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQuery("select * from TextWidget");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResultforMultiSeries("Process", "Model", "OS", "CPU");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.selectValueColumn(Value);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.selectLabelColumn(label);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkSemicolonDefaultValue(value, errorMsgDefault);
        Result.rcEquals(result,"0");

        result = dataCollectorPage.switchDefault("None","");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");

    }

    @DataProvider(name="MultipleValue")
    public Object[][] getDataFromDataprovider(){
        return new Object[][]
                {
                        { "modelparam", "MODEL_NAME", "Text Value Widgets", "Model", "Model", "None" },
                        { "osparam", "os", "Text Value Widgets", "OS", "OS", "None" }
                };

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
        result = editDashboardPage.setDataField("Model");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void clickOnSecondTextWidget() {
        result = editDashboardPage.selectWidget(widget2);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void setDataChannelForSecondTextWidget() {
        result = editDashboardPage.setDataChannel(dataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void setDataFieldSecondTextWidget() {
        result = editDashboardPage.setDataField("OS");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void saveUploadedDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 140)
    public void viewDashboard() {
        result = mainPage.viewDashboardNew(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150)
    public void verifyingText() {
        result = mainPage.verifyingTextForTextValue(id,sTextFields1);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 160)
    public void verifyingText_title() {
        result = mainPage.verifyingText_title(id,sTextFields1_title,"title");
        Result.rcEquals(result, "0");
    }


    @Test(priority = 170)
    public void clickOnLeftBarButton() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void clickOnLeftBarParameterValue() {
        result = mainPage.assignvaluetosingleParam("A","MODEL_NAME");
        result = mainPage.assignvaluetosingleParam("Windows","os");

        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void clickOnLeftBarParameterApplyValue() {
        result = mainPage.leftBarParameterApplyValue();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 195)
    public void verifyingTextAfterParameter() {
        result = mainPage.verifyingText(id,sTextFields2);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 196)
    public void verifyingTextAfterParameter_title() {
        result = mainPage.verifyingText_title(id,sTextFields2_title,"title");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 197)
    public void clickOnLeftArrowButton() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 198)
    public void verifyingTextInDropdown(){
        result = mainPage.verifyingTextPresentInDropdown(id1, "os",oslist);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 199)
    public void closeSlideOut(){
        result = mainPage.closeSlideout();
        Result.rcEquals(result, "0");

    }

    @Test(priority = 200)
    public void openSlideOut() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 203)
    public void setLeftBarParameterValue() {
        result = mainPage.assignvaluetosingleParamselectDropDownParameter("B","MODEL_NAME");
        result = mainPage.assignvaluetosingleParamselectDropDownParameter("Ub/antu","os");
        Result.rcEquals(result, "0");

    }

    @Test(priority = 205)
    public void verifyingSelectedText(){

        result = mainPage.verifyingSelectedText(id1,"os",oslist);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 206)
    public void clickOnLeftBarApplyValue() {
        result = mainPage.leftBarParameterApplyValue();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 207)
    public void verifyingTextAfterSettingParameter() {
        result = mainPage.verifyingText(id,sTextFields4);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 208)
    public void validateNegativeScenario() {

        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");

        result = mainPage.assignvaluetosingleParamselectDropDownParameter("C","MODEL_NAME");
        result = mainPage.assignvaluetosingleParamselectDropDownParameter("Ub\\antu","os");
        Result.rcEquals(result, "0");
        result = mainPage.leftBarParameterApplyValue();

        Result.rcEquals(result, "0");
        TestUtils.sleep(2);
        result = mainPage.verifyingText(id, sTextFields5);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 360)
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }

    @Test(priority = 370)
    public void logOutFromAdmin() {
        result = mainPage.logout();
    }


    @Test(priority = 380)
    public void loginAsNewUser() {
        driver.get(BVD_URL);
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 390)
    public void checkLogin()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 400)
    public void viewDashboardForNonAdminUser() {
        result = mainPage.viewDashboardNew(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 410)
    public void clickOnLeftBarButtonForNonAdmin() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 420)
    public void clickOnLeftBarParameterValueForNonAdmin() {
        result = mainPage.assignvaluetosingleParam("A","MODEL_NAME");
        result = mainPage.assignvaluetosingleParam("Windows","os");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 430)
    public void clickOnLeftBarParameterApplyValueForNonAdmin() {
        result = mainPage.leftBarParameterApplyValue();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 440)
    public void verifyingTextForNonAdmin() {
        result = mainPage.verifyingText(id,sTextFields3);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 445)
    public void verifyingTextForNonAdmin_title() {
        result = mainPage.verifyingText_title(id,sTextFields3_title,"title");
        Result.rcEquals(result, "0");
    }
    @Test(priority = 450)
    public void logOutFromUser() {
        result = mainPage.logout();
    }

    @Test(priority = 480)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
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
