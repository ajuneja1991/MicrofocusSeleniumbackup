package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.mf.opr.qa.framework.utils.TextUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.hp.opr.qa.framework.UiTestWebDriver;
import com.mf.opr.qa.framework.TestConfig;
import com.typesafe.config.Config;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DashboardZoom  {


    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private BvdWsClient client;
    private Map<String, String> result;
    private String sSourceDashboard = "sample_dashboard_test";
    private String sDashboard="";
    private String sDashboardPath="";
    private String script = "window_activator.vbs";
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String userName = "Testerforzoom@microfocus.com";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testerforzoom";
    private String desc = "For Tester zoom";
    private String category = "All";
    private String accessType = "full-control";
    String roleId;
    private void initPages() {

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
    }


    private static void robotMouseWheel() throws AWTException, IOException {
        Robot robot = new Robot();
        robot.mouseMove((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
        TestUtils.sleepAndLog(1000);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        TestUtils.sleepAndLog(1000);
        robot.mouseWheel(-2);
        TestUtils.sleepAndLog(1000);

    }

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        BROWSER=cfg.getString("application.bvd.browser.name");
        sDashboard = sSourceDashboard + "_BVDDashboardZoom_" + BROWSER;
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

    @Test(priority = 10)
    public void login_check() {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 20)
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
        Result.rcEquals(result, "0");
        result = TestUtils.downloadResource(this.getClass(), "window_activator.vbs", "c:\\tmp\\" +
                script);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 40)
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }

    @Test(priority = 60)
    public void testOpenManageDashboards() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150)
    public void logOutFromAdmin() {
        result = mainPage.logout();
    }

    @Test(priority = 160)
    public void loginAsNewUser() {
        driver.get(URL);
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void checkLogin()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void openManageDashboardsForNonAdmin() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }
    @Test(priority = 185)
    public void uploadDashboard() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 187)
    public void apply() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 189)
    public void openManageDashboardsForNonAdminagain() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }
    @Test(priority = 190)
    public void openEditDashboardForNonAdmin() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void checkZoomInEditorForNonAdmin() {
        result = editDashboardPage.zoomIn(1);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 210)
    public void checkZoomInLevelEditorForNonAdmin() {
        result = editDashboardPage.getZoomLevel(sDashboard);
        Result.rcEquals(result, "0");
        Result.assertTrue(result.get("out").contains("scale(1.3)"));

    }


    @Test(priority = 220)
    public void checkZoomOutEditorForNonAdmin() {
        result = editDashboardPage.zoomOut(1);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 230)
    public void checkZoomOutLevelEditorForNonAdmin() {
        result = editDashboardPage.getZoomLevel(sDashboard);
        Result.rcEquals(result, "0");
        if (BROWSER.equalsIgnoreCase("ie")) {
            Result.assertTrue(result.get("out").contains("translate(0) scale(1)"));
        } else {
            Result.assertTrue(result.get("out").contains("translate(0,0)scale(1)"));
        }
    }

    @Test(priority = 240)
    public void checkResetZoomEditorForNonAdmin() {
        result = editDashboardPage.resetZoomLevel();
        Result.rcEquals(result, "1");

        result = editDashboardPage.zoomIn(2);
        Result.rcEquals(result, "0");

        result = editDashboardPage.resetZoomLevel();
        Result.rcEquals(result, "0");
        TestUtils.sleepAndLog(2000);

    }

    @Test(priority = 250)
    public void checkResetZoomLevelEditorForNonAdmin() {
        result = editDashboardPage.getZoomLevel(sDashboard);
        Result.rcEquals(result, "0");
        if (BROWSER.equalsIgnoreCase("ie")) {
            Result.assertTrue(result.get("out").contains("translate(0) scale(1)"));
        } else {
            Result.assertTrue(result.get("out").contains("translate(0,0)scale(1)"));
        }

    }

    @Test(priority = 260)
    public void logOutFromUser() {
        result = mainPage.logout();
    }

    @Test(priority = 270)
    public void loginIntoAdmin() {
        result = loginPage.login(BVD_USR, BVD_PWD);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 280)
    public void loginCheck()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 290)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 300)
    public void deleteTemplateUsingWs() {
        result = client.dashboardWS.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}
