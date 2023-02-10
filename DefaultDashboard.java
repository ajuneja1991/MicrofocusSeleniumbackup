package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.SystemInfo;
import com.hp.opr.qa.framework.common.utils.Rmi2HttpClient;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.mf.opr.qa.framework.TestConfig;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultDashboard   {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private Map<String, String> result;
    private String sSourceDashboard = "sample_dashboard_test";
    private String sDashboard="";
    private String sDashboardPath="";
    private String sDefaultDashboard;
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String userName = "Testerfordefaultdash";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testfordefaultdash";
    private String desc = "For tester";
    private String category = "All";
    private String accessType = "full-control";
    String roleId;
    private BvdWsClient client;
    private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");
    private static String OS="";
    private void initPages() {

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
    }

    @BeforeClass
    public void setup() throws RemoteException {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        sDashboard = sSourceDashboard + "_BVDDefaultDashboard_" + BROWSER;
        sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
        OS = System.getProperty("os.name").toLowerCase();
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        initPages();
        if(OS.contains("win")){
            sDashboardPath = new String("C:\\tmp\\" + sDashboard + ".svg");
        } else {
            sDashboardPath = new String("/tmp/" + sDashboard + ".svg");
        }
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


    @Test(priority = 17)
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 20)
    public void testOpenManageDashboards() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30)
    public void uploadDashboard() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40)
    public void saveUploadedDashboard() {
        TestUtils.sleepAndLog(2000);
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50)
    public void viewDefaultDashboard() {
        TestUtils.sleepAndLog(2000);
        result = mainPage.viewDefaultDashboard();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60)
    public void setDefaultDashboard() {
        TestUtils.sleepAndLog(2000);
        result = mainPage.setDefaultDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70)
    public void viewNewDefaultDashboard() {
        TestUtils.sleepAndLog(2000);
        result = mainPage.viewDefaultDashboard();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void getDashboardNames() {
        TestUtils.sleepAndLog(2000);
        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");

        sDefaultDashboard = "Welcome";
    }

    @Test(priority = 90)
    public void revertDefaultDashboard() {
        TestUtils.sleepAndLog(2000);
        result = mainPage.setDefaultDashboard(sDefaultDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void viewRevertedDefaultDashboard() {
        TestUtils.sleepAndLog(2000);
        result = mainPage.viewDefaultDashboard();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void openManageDashboardsToDelete() {
        TestUtils.sleepAndLog(2000);
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 135)
    public void deleteDashboard() {
        TestUtils.sleepAndLog(2000);
        result = manageDashboardsPage.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }



    @Test(priority = 137)
    public void logOutFromUser() {
        result = mainPage.logout();
    }

    @Test(priority = 139)
    public void loginIntoAdmin() {
        result = loginPage.login(BVD_USR, BVD_PWD);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 140)
    public void loginCheck()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 145)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }



    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}
