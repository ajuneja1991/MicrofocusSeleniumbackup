package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
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
import java.util.Map;

public class QuickEditFunctionality {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String sDataChannel ;
    private String sDashboard ;
    private String sDashboardPath ;
    private String sSourceDashboard = "BaseDashboard";
    private BvdWsClient client;
    private Map<String, String> result;
    private String userName = "TesterforquickEdit";
    private String userLogin;
    private String userNameview = "TesterforquickEditview";
    private String userPwd = "Test@123";
    private String roleName = "testquickEdit";
    private String roleNameview = "testquickEditview";
    private String desc = "For tester";
    private String category = "All";
    private String accessType = "full-control";
    private String accessTypeview = "view";
    String roleId1;
    String roleId;
    private String json;
    private String key;
    private String dims = "browser";
    private String tags = "QuickEditFunctionality";
    private String sWidget = "shape452";
    private String sDataField = "numberOfNormal";
    private String schangedDataField="numberOfUnknown";
    private String schangedDataFieldrelogin="numberOfMajor";
    private String sDefaultDashboard="Welcome";
    private String defaultdashboard="Welcome";

    private String sText = "10";
    private String sTextafterquickEdit1 = "5";
    private String sTextafterquickEditrelogin="20";

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
        sDataChannel = "QuickEditFunctionality " + BROWSER;
        sDashboard = sSourceDashboard + "_QuickEditFunctionality_" + BROWSER;
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

    @Test(priority = 5)
    public void quickEditButton_DashboardName()
    {
        result = mainPage.quickEdit_validateDashboardName(defaultdashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 10)
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
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }

    @Test(priority = 45)
    public void createRoleView() {
        result = client.userWS.createRole(roleNameview, desc, category,accessTypeview);
        Result.rcEquals(result, "0");
        roleId1 = result.get("out");
    }

    @Test(priority = 50)
    public void getApiKeyWS() {
        result = client.userWS.getApiKey();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60)
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

    @Test(priority=62)
    public void logoutfromadmin()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }



    @Test(priority = 65)
    public void Loginnonadmin()
    {
        driver.get(URL);
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 66)
    public void login_checkfornonadmin()
    {
        result = loginPage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 70)
    public void openManageDashboards() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void uploadDashboard() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90)
    public void selectWidget() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void setDataChannel() {
        result = editDashboardPage.setDataChannel(sDataChannel);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 110)
    public void setDataField() {
        result = editDashboardPage.setDataField(sDataField);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void apply() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void viewDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 135)
    public void validateDashboardName() {
        result = mainPage.quickEdit_validateDashboardName(sDashboard);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 140)
    public void checkTextOfWidget() {
        result = mainPage.getTextOfTextValueWidget(sWidget);
        Result.outEquals(result, sText);
    }


    @Test(priority = 150)
    public void editDashboardViewDashboardscreen() {
        result = mainPage.clickQuickEditbutton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 160)
    public void changeDatafield() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");

        result = editDashboardPage.clearandSetDataChannel(sDataChannel);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataField(schangedDataField);
        Result.rcEquals(result, "0");


    }

    @Test(priority = 170)
    public void applyforDatafield() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void viewDashboardforquickEditFunc() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void checkTextOfWidgetafterquickEdit() {
        result = mainPage.getTextOfTextValueWidget(sWidget);
        Result.outEquals(result, sTextafterquickEdit1);
    }

    @Test(priority = 200)
    public void viewDefaultDashboard() {
        result =mainPage.viewDefaultDashboard();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 210)
    public void setDefaultDashboard() {
        TestUtils.sleepAndLog(2000);
        result = mainPage.setDefaultDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void viewNewDefaultDashboard() {
        TestUtils.sleepAndLog(2000);
        result = mainPage.viewDefaultDashboard();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 230)
    public void validateeditedDefaultDashboard() {
        result = mainPage.getTextOfTextValueWidget(sWidget);
        Result.outEquals(result, sTextafterquickEdit1);
    }


    @Test(priority=240)
    public void logoutcheck()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }



    @Test(priority = 250)
    public void testLoginnonadmin()
    {
        driver.get(URL);
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 260)
    public void validatereloginDefaultDashboard() {
        result = mainPage.getTextOfTextValueWidget(sWidget);
        Result.outEquals(result, sTextafterquickEdit1);
    }


    @Test(priority = 270)
    public void editDashboardViewDashboardrelogin() {
        result = mainPage.clickQuickEditbutton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 280)
    public void changeDatafieldrelogin() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");

        result = editDashboardPage.clearandSetDataChannel(sDataChannel);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataField(schangedDataFieldrelogin);
        Result.rcEquals(result, "0");


    }

    @Test(priority = 290)
    public void applyforDatafieldrelogin() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 300)
    public void viewDashboardforquickEditFuncrelogin() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 310)
    public void checkTextOfWidgetafterquickEditrelogin() {
        result = mainPage.getTextOfTextValueWidget(sWidget);
        Result.outEquals(result, sTextafterquickEditrelogin);
    }

    @Test(priority = 320)
    public void revertDefaultDashboard() {
        TestUtils.sleepAndLog(2000);
        result = mainPage.setDefaultDashboard(sDefaultDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 330)
    public void viewRevertedDefaultDashboard() {
        TestUtils.sleepAndLog(2000);
        result = mainPage.viewDefaultDashboard();
        Result.rcEquals(result, "0");
    }

    @Test(priority=340)
    public void logoutchecklast()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 350)
    public void testLoginnonadminview()
    {
        driver.get(URL);
        result = loginPage.login(userNameview, userPwd);
        Result.rcEquals(result, "0");
    }



    @Test(priority = 360)
    public void nonadminviewEditDashboard()
    {
        result=mainPage.absenceofQuickEditbutton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 370)
    public void validateDashboardNameNonadmin() {
        result = mainPage.quickEdit_validateDashboardName(sDashboard);
        Result.rcEquals(result, "0");
    }




    @Test(priority = 900)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");

        result = client.userWS.deleteRole(roleId1);
        Result.rcEquals(result,"0");
    }


    @Test(priority = 1000)
    public void deleteDashboard() {
        result = client.dashboardWS.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }


    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}
