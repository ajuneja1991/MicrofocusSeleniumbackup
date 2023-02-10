package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.pageobjects.bvd.*;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.*;

public class NonQueryVariableParameter {
    private Config cfg;
    private String BVD_USR;
    private String BVD_PWD;
    private String URL;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String queryType="DataQuery";
    String querytypeParam="ParameterQuery";
    private String sWidget="group3";
    private LoginPage loginpage;
    private MainPage mainpage;
    private DataCollectorPage datacollectorpage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private String dataqueryName;
    private Map<String,String> result;
    private String sSourceDashboard;
    private String sDashboard;
    private String sDashboardPath;
    private String sDataChannel;
    private BvdWsClient client;
    private List<String> ChartNumberdefault = Collections.singletonList("20");
    private List<String> ChartHeightdefault = Collections.singletonList("18.61");
    private List<String> ChartNumbernonQueryparam = Collections.singletonList("20");
    private List<String> ChartHeightnonQueryparam=Collections.singletonList("18.61");
    private List<String> sDataFields = Arrays.asList("Data1");
    private String userName = "Testernonqueryvariable";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testnonqueryvariable";
    private String desc = "For Nonqueryvariable";
    private String category = "All";
    private String accessType = "full-control";
    String roleId;
    private String errorMessage = "Values must not contain a semicolon ';'";
    private String value = "val;ue";
    private String errorMsgDefault = "Default value must not contain a semicolon ';'";

    private void initpages() {
        loginpage= PageFactory.initElements(driver,LoginPage.class);
        mainpage=PageFactory.initElements(driver,MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
        datacollectorpage=PageFactory.initElements(driver, DataCollectorPage.class);

    }
    private Map<String, String> valuelist() {
        Map<String,String> myMap = new HashMap<String,String>();
        myMap.put("Day", "1");
        myMap.put("Week", "7");
        myMap.put("Month","30");
        myMap.put("Year","365");
        return myMap;
    }
    private Map<String, String> semicolonValue(){
        Map<String,String> myMap = new HashMap<String,String>();
        myMap.put("Decade","Tent;h");
        return myMap;
    }
    @BeforeClass
    public void setup(){
        try{
            cfg=new TestConfig().getConfig();
            BVD_USR=cfg.getString("application.bvd.user.adminusr");
            BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
            URL=cfg.getString("application.bvd.url.external");
            BROWSER=cfg.getString("environment.browser.name");
            sSourceDashboard="BaseDashboard";
            sDataChannel = "NonQueryVariableParameter " + BROWSER;
            dataqueryName="NonQueryVariableParameter_" + BROWSER;
            sDashboard=sSourceDashboard +"_NonQueryVariableParameter_"+ BROWSER;
            sDashboardPath="C:\\tmp\\" +sDashboard+ ".svg";
            driver=UiTestWebDriver.getWebDriver();
            driver.get(URL);
            client=new BvdWsClient(BVD_USR,BVD_PWD,PROXY_HOST,PROXY_PORT);
            initpages();
        }catch(Exception e){
            Reporter.log(Misc.getExceptionInfo(e));
        }

    }

    @Test(priority=0)
    public void idm_login(){
        result=loginpage.login(BVD_USR,BVD_PWD);
        Result.rcEquals(result,"0");
    }

    @Test(priority=1)
    public void idm_logincheck(){
        result=loginpage.idm_LoggedIn_Check();
        Result.rcEquals(result,"0");
    }

    @Test(priority=5)
    public void copyTestFiles (){
        result= TestUtils.downloadResource(this.getClass(),sSourceDashboard+".svg",sDashboardPath);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 10)
    public void getDashboardnames(){
        result=mainpage.getDashboardNames();
        Result.rcEquals(result,"0");
    }

    @Test(priority=20)
    public void getAPIKey(){
        result=client.userWS.getApiKey();
        Result.rcEquals(result,"0");
    }

    @Test(priority = 22)
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }

    @Test(priority=30)
    public void createDataQuery(){

        result=mainpage.openDataCollectors();
        Result.rcEquals(result,"0");

        result = datacollectorpage.clickNewQuery("DataQuery");
        Result.rcEquals(result, "0");

        result=datacollectorpage.typeTextIntoQueryName(sDataChannel,queryType);
        Result.rcEquals(result,"0");

        result = datacollectorpage.typeTextIntoQueryDescription(sSourceDashboard+" "+BROWSER+" Description",queryType);
        Result.rcEquals(result, "0");

        result = datacollectorpage.switchFormat("Default");
        Result.rcEquals(result, "0");

        result = datacollectorpage.typeTextIntoQuery("select * from NonqueryWithDateTable where ${createdon <"+
                "(current_date - cast(${TimeTest} as INTEGER))} order by createdon desc");
        Result.rcEquals(result, "0");

        result = datacollectorpage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = datacollectorpage.checkQueryResultforMultiSeries("DATA1", "DATA2","ID","CREATEDON");
        Result.rcEquals(result, "0");

        result = datacollectorpage.clickSaveQuery();
        Result.rcEquals(result, "0");

    }
    @Test(priority=40)
    public void createParamQuery(){

        result = datacollectorpage.clickNewQuery(querytypeParam);
        Result.rcEquals(result, "0");

        result = datacollectorpage.typeTextIntoQueryName("TimeTest", querytypeParam);
        Result.rcEquals(result, "0");

        result = datacollectorpage.typeTextIntoQueryParamVariable("TimeTest");
        Result.rcEquals(result, "0");

        result = datacollectorpage.typeTextIntoQueryDescription("TimeTest",querytypeParam);
        Result.rcEquals(result, "0");

        result = datacollectorpage.switchData("Value list");
        Result.rcEquals(result, "0");

        result = datacollectorpage.createNonqueryValueList(valuelist());
        Result.rcEquals(result, "0");


        result = datacollectorpage.checksemicolonValue(semicolonValue(),errorMessage);
        Result.rcEquals(result,"0");

        result = datacollectorpage.checkSemicolonDefaultValue(value, errorMsgDefault);
        Result.rcEquals(result,"0");

        result = datacollectorpage.switchDefault("Value","9125");
        Result.rcEquals(result, "0");

        result = datacollectorpage.clickSaveQuery();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 60)
    public void openManageDashboards() {
        result = mainpage.openManageDashboards();
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
        result = editDashboardPage.setDataFieldMultiSelectTypeText(sDataFields);
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
        result = mainpage.logout();
        Result.rcEquals(result, "0");
    }



    @Test(priority = 107)
    public void testLoginnonadmin()
    {
        driver.get(URL);
        result = loginpage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 108)
    public void login_checkfornonadmin()
    {
        result = loginpage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 109)
    public void viewWelcomeDashboard() {
        result = mainpage.viewDashboard("Welcome");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void viewDashboard() {
        result = mainpage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 120)
    public void checkChartNumberforDefault() {
        result = mainpage.getChartNumbers(sWidget);
        Result.outEquals(result, ChartNumberdefault.toString());
    }

    @Test(priority = 130)
    public void checkChartHeightforDefault() {
        result = mainpage.getChartHeightsOfBarChartWidget(sWidget);
        Result.outEquals(result, ChartHeightdefault.toString());
    }

    @Test(priority = 140)
    public void clickOnLeftBarButtontoSelectNonQueryParam() {
        result = mainpage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150)
    public void clickOnLeftBarParameterValue() {
        result = mainpage.assignvaluetosingleParam("Day", "TimeTest");
        Result.rcEquals(result, "0");
    }
    @Test(priority = 160)
    public void updatingParameterValueApplyNonQueryParam() {
        result = mainpage.leftBarParameterApplyValue();
        Result.rcEquals(result, "0");
    }
    @Test(priority = 170)
    public void checkChartNumberfornonQueryParam() {
        result = mainpage.getChartNumbers(sWidget);
        Result.outEquals(result, ChartNumbernonQueryparam.toString());
    }

    @Test(priority = 180)
    public void checkChartHeightfornonQueryParam() {
        result = mainpage.getChartHeightsOfBarChartWidget(sWidget);
        Result.outEquals(result, ChartHeightnonQueryparam.toString());
    }


    @Test(priority=190)
    public void logoutcheckfornonadmin()
    {
        result = mainpage.logout();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 200)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }

    @AfterClass
    public void cleanup() throws UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException, JSONException, KeyManagementException
    {
        client.dashboardWS.deleteDashboard(sDashboard);
        client.collectorWS.deleteAllExistQueries();
        driver.quit();
    }

}
