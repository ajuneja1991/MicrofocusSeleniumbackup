package com.hp.opr.qa.tests.sys.ui.bvd.usermgmt;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.hp.opr.qa.framework.pageobjects.usermgmt.RoleList;
import com.hp.opr.qa.framework.pageobjects.usermgmt.RoleEditor;
import com.hp.opr.qa.framework.pageobjects.usermgmt.UsersGroupsAndRolesHomePage;
import com.hp.opr.qa.framework.pageobjects.usermgmt.UsersGroupsAndRolesSideNav;
import com.hp.opr.qa.framework.pageobjects.bvd.bvdUserandRolePages.UsersList;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Created by Eva Sokolyanskaya on 02/02/2017.
 */
public class AssignUserToRole  {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private UsersGroupsAndRolesHomePage usersGroupsAndRolesHomePage;
    private UsersGroupsAndRolesSideNav usersGroupsAndRolesSideNav;
    private RoleEditor roleEditor;
    private UsersList userList;
    private RoleList roleList;
    private WebDriver driver;
    private Map<String, String> result;
    private BvdWsClient client;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String sourceDashboard = "sample_dashboard_test";
    private String dashboardName ;
    private String dashboardPath ;
    private String sUserLogin ;
    private String sUserPwd = "T3s!er1?";
    private Boolean bUserSuperAdmin = false;
    String roleName ;
    String roleDescription ;
    String categoryName = "AlfaCategory";
    String accessType = "view";
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;

    private void initPages() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
        usersGroupsAndRolesHomePage = PageFactory.initElements(driver, UsersGroupsAndRolesHomePage.class);
        roleEditor = PageFactory.initElements(driver, RoleEditor.class);
        userList = PageFactory.initElements(driver, UsersList.class);
        usersGroupsAndRolesSideNav = PageFactory.initElements(driver, UsersGroupsAndRolesSideNav.class);
        roleList = PageFactory.initElements(driver, RoleList.class);
    }

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        dashboardName = sourceDashboard + "_AssignUserToRole_" + BROWSER;
        dashboardPath = "C:\\tmp\\" + dashboardName + ".svg";
        sUserLogin = "bvd_tester__AssignUserToRole";
        roleName = "bvd_role_" + BROWSER + "_Assign_User_To_Role";
        roleDescription = "bvd role description " + BROWSER + " Assign User To Role";
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        initPages();
        client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
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
        result = TestUtils.downloadResource(this.getClass(), sourceDashboard + ".svg", dashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 20)
    public void uploadDashboardSetCategory() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.uploadDashboard(dashboardPath);
        Result.rcEquals(result, "0");

        result = editDashboardPage.assignNewCategory(categoryName);
        Result.rcEquals(result, "0");

        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30)
    public void createRole() {
        result = client.userWS.createRole(roleName, roleDescription, categoryName, accessType);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90, enabled = true)
    public void deleteRoleUsingWS() {
        result = client.userWS.getRoleId(roleName);
        Result.rcEquals(result, "0");

        result = client.userWS.deleteRole(result.get("out"));
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110, enabled = true)
    public void deleteDashboard() {
        result = client.dashboardWS.deleteDashboard(dashboardName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120, enabled = true)
    public void deleteCategory() {
        result = client.userWS.deleteCategory(categoryName);
        Result.rcEquals(result, "0");
    }

    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}
