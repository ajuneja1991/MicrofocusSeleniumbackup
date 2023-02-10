package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
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
import java.util.List;
import java.util.Map;

public class MultiDataFieldTxtWidget {

    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private DataCollectorPage dataCollectorPage;
    private WebDriver driver;
    private Map<String, String> result;
    private String sWidget = "shape143";
    private String key;
    private String dims = "browser";
    private String tags = "MultiDataFieldTxtWidget";
    private BvdWsClient client;
    private String json;
    String queryType="DataQuery";
    private String sDataChannel ;
    private List<String> sDataFieldslessvalue = Arrays.asList("CPU");
    private List<String> sDataFieldsvalue = Arrays.asList("CPU","OS");
    private List<String> sDataFieldsmorevalue = Arrays.asList("CPU","OS","Model");
    private List<String> sDataFieldsnovalue = Arrays.asList("");
    private List<String> FieldsCalculationRule =Arrays.asList("calculatedProperty");
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
    private String userName = "TesterMultiDataField";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testmultidatafield";
    private String desc = "For Multidatafield";
    private String category = "All";
    private String accessType = "full-control";
    String roleId;
    String lessvaluetext="CPU:50 OS:";
    String morevaluetext="CPU:50 OS:Ub\\antu";
    String valuetext="CPU:50 OS:Ub\\antu";
    String novaluetext="CPU: OS:";


    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;

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
        sSourceDashboard = "MultiDataFieldTxtWidget";
        sDashboard = sSourceDashboard + "_" + BROWSER;
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

        //sLinkToDashboard = result.get("out").replace("[", "").replace("]", "").split(",")[0];
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

        result = dataCollectorPage.typeTextIntoQuery("select * from TextWidget");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResultforMultiSeries("Process", "Model", "OS", "CPU");
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


    @Test(priority=110)
    public void logoutcheck()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }



    @Test(priority = 120)
    public void testLoginnonadmin()
    {
        driver.get(URL);
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 130)
    public void login_checkfornonadmin()
    {
        result = loginPage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 140)
    public void viewDashboardnovalue() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150)
    public void checkDatanovalue() {
        result = mainPage.validateMultiFieldValues(sWidget,novaluetext);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 160)
    public void openManageDashboardsToSetlessvalue() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void openEditDashboardoToSetlessvalue() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void selectWidgetforlessvalue() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 190)
    public void setdatafieldforlessvalue() {

        result = editDashboardPage.setDataFieldMultiSelect(sDataFieldslessvalue);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void applyforLegend() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 210)
    public void viewDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void checkDatalessvalue() {
        result = mainPage.validateMultiFieldValues(sWidget,lessvaluetext);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 230)
    public void openManageDashboardsSetDataField() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 240)
    public void openEditDashboardsSetDataField() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 250)
    public void selectWidgetforvalue() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 260)
    public void setdatafieldforvalue() {

        if (BROWSER.equalsIgnoreCase("chrome")) {

            result = editDashboardPage.SetDataFieldIgnoreExisting(sDataFieldsvalue);
            Result.rcEquals(result, "0");
        }else{
            result= editDashboardPage.clearDataFieldselectCalprop();
            Result.rcEquals(result, "0");

            result = editDashboardPage.setDataFieldMultiSelectgecko(sDataFieldsvalue);
            Result.rcEquals(result, "0");
        }
    }

    @Test(priority = 270)
    public void applyforValue() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }
    @Test(priority = 280)
    public void viewDashboardforvalues() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 290)
    public void checkDatavalue() {
        result = mainPage.validateMultiFieldValues(sWidget,valuetext);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 300)
    public void openManageDashboardsToSetmorevalue() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 310)
    public void openEditDashboardoToSetmorevalue() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 320)
    public void selectWidgetformorevalue() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 330)
    public void setdatafieldformorevalue() {

        if (BROWSER.equalsIgnoreCase("chrome")) {

            result = editDashboardPage.SetDataFieldIgnoreExisting(sDataFieldsmorevalue);
            Result.rcEquals(result, "0");
        }else{
            result= editDashboardPage.clearDataFieldselectCalprop();
            Result.rcEquals(result, "0");

            result = editDashboardPage.setDataFieldMultiSelectgecko(sDataFieldsmorevalue);
            Result.rcEquals(result, "0");
        }
    }

    @Test(priority = 340)
    public void applyformorevalue() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }
    @Test(priority = 350)
    public void viewDashboardformorevalues() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 360)
    public void checkDatamorevalue() {
        result = mainPage.validateMultiFieldValues(sWidget,morevaluetext);
        Result.rcEquals(result, "0");
    }



    @Test(priority=400)
    public void logoutcheckfornonadmin()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }



    @Test(priority = 625)
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
    public void cleanup() throws UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException, JSONException, KeyManagementException

    {
        client.collectorWS.deleteAllExistQueries();
        driver.quit();

    }
}


