package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WebPage {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private Map<String, String> result;
    private String sWidget = "shape1";
    private String sSourceDashboard = "BaseDashboard";
    private String sDashboard ;
    private String sDashboardPath ;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String userName = "TesterWebpage";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testwebpage";
    private String desc = "For tester";
    private String category = "All";
    private String accessType = "full-control";
    String roleId;
    private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");
    private BvdWsClient client;
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;

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
        sDashboard = sSourceDashboard + "_BVDWebPage_" + BROWSER;
        sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        //super.setup(true);
        //driver = getWebDriver();
        //driver.get(directLogin);
        //driver.get(EXT_BVD_URL);
        initPages();
        try
        {
            //client = new BvdRestClient(PROXY_HOST, PROXY_PORT);
            client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
        } catch (Exception e)
        {
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

    @Test(priority = 20)
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg",  sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50)
    public void testOpenManageDashboards() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60)
    public void uploadDashboard() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70)
    public void selectWidget() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");

        if (!"ie".equals(BROWSER.toLowerCase()) && !"gecko".equals(BROWSER.toLowerCase())) {
            result = editDashboardPage.getJsError();
            Result.rcEquals(result, "0");
        }
    }

    @Test(priority = 80)
    public void setWebPage() {
        result = editDashboardPage.setWebPage(URL);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90)
    public void saveDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void viewDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    /*@Test(priority = 120)
    public void checkWebPage() {
        result = mainPage.getWebPage(sWidget);
        Result.outContains(result, EXT_BVD_URL);
    }*/

    @Test(priority = 270)
    public void openManageDashboardsToDelete() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 280)
    public void deleteDashboard() {
        result = manageDashboardsPage.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 285)
    public void testLogout() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 290)
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
