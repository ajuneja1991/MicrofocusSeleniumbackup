package com.hp.opr.qa.tests.sys.ui.bvd.usermgmt;

import com.hp.autopassj.common.util.FileUtils;
import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.hp.opr.qa.framework.pageobjects.usermgmt.*;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class UserViewPermissions {
  private LoginPage loginPage;
  private MainPage mainPage;
  private ManageDashboardsPage manageDashboardsPage;
  private EditDashboardPage editDashboardPage;
  private UsersGroupsAndRolesHomePage usersGroupsAndRolesHomePage;
  private UsersGroupsAndRolesSideNav usersGroupsAndRolesSideNav;
  private UserEditor userEditor;
  private RoleEditor roleEditor;
  private RoleList roleList;
  private WebDriver driver;
  private Map<String, String> result;
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
  private String sUserName = "";
  private String sUserLogin = "";
  private String sUserEmail = "";
  private String sUserPwd = "T3s!er1?";
  private Boolean bUserSuperAdmin = false;
  private String sDashboardPath;
  private String sSourceDashboard = "sample_dashboard_test";
  private String sDashboard = "";
  private String sCategory = "UserViewPermissions";


    private void initPages() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
        usersGroupsAndRolesHomePage = PageFactory.initElements(driver, UsersGroupsAndRolesHomePage.class);
        usersGroupsAndRolesSideNav = PageFactory.initElements(driver, UsersGroupsAndRolesSideNav.class);
        userEditor = PageFactory.initElements(driver, UserEditor.class);
        roleEditor = PageFactory.initElements(driver, RoleEditor.class);
        roleList = PageFactory.initElements(driver, RoleList.class);
    }

    @BeforeClass
    public void setup() {
        //super.setup();
        //driver.get(EXT_BVD_URL);
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        sUserName = "bvd tester " + BROWSER + " User View Permissions";
        sUserLogin = "bvd_tester_" + BROWSER + "_UserViewPermissions";
        sUserEmail = "bvd_tester_" + BROWSER + "_UserViewPermissions@hpe.com";
        sDashboard = sSourceDashboard + "_BVDUserViewPermissions_" + BROWSER;
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        initPages();
    }

    @Test(priority = 0)
    public void copyTestFiles() throws IOException
    {
        if (sSourceDashboard != null)
        {
            // copy and rename test file
            URL url = this.getClass().getResource(sSourceDashboard + ".svg");
            File fDashboardSourceFile;
            File fDashboardFile;
            fDashboardSourceFile = new File(url.toString().replace("file:/", "").replace("/", "\\"));
            fDashboardFile = new File("C:\\tmp\\" + sDashboard + ".svg");
            FileUtils.copy(fDashboardSourceFile, fDashboardFile);
            // windows
            /*if (OS.contains("win"))
            {
                fDashboardSourceFile = new File(url.toString().replace("file:/", "").replace("/", "\\"));
                fDashboardFile = new File("C:\\tmp\\" + sDashboard + ".svg");
                FileUtils.copy(fDashboardSourceFile, fDashboardFile);
            }
            else
            {
                // linux
                fDashboardSourceFile = new File(url.toString().replace("file:", ""));
                fDashboardFile = new File("/tmp/" + sDashboard + ".svg");
                FileUtils.copy(fDashboardSourceFile, fDashboardFile);
            }*/
            sDashboardPath = fDashboardFile.toString();
        }
    }

    @Test(priority = 10)
    public void login() {
        result = loginPage.login(BVD_USR, BVD_PWD);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 20)
    public void openManageDashboards() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30)
    public void uploadDashboard() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40)
    public void assignNewCategory() {
        result = editDashboardPage.assignNewCategory(sCategory);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50)
    public void saveDashboard() {
        // save
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60)
    public void openUserManagement() {
        result = mainPage.openUserManagementforTest();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70)
    public void openCreateNewUser() {
        result = usersGroupsAndRolesHomePage.openCreateNewUser();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80, enabled = true)
    public void createNewUser() {
        result = userEditor.createUser(sUserName, sUserLogin, sUserPwd, sUserEmail, bUserSuperAdmin);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90, enabled = true)
    public void openManageRolesSideNavToCreateGroup() {
        result = usersGroupsAndRolesSideNav.openManageRolesSideNav();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100, enabled = true)
    public void openCreateNewRole() {
        result = roleList.openCreateNewRole();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110, enabled = true)
    public void createNewRole() {
        result =roleEditor.createRole(sDashboard, sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120, enabled = true)
    public void openEditRole() {
        result = roleList.openEditRole();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130, enabled = true)
    public void setPermissions() {
        //result = RoleEditor.setDashboardsCategoriesViewPermissions(sCategory, bViewPerm);
        Result.rcEquals(result, "0");
    }
/*
    @Test(priority = 140, enabled = true)
    public void testAssignRoleToUser() {
        result = RoleEditorPage.assign();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150, enabled = true)
    public void testVerifyRoleMembership() {
        result = UserList.getAssignedRoles(sUserName);
        Result.outEquals(result, sCategory);
    }

    @Test(priority = 160, enabled = true)
    public void testDeleteUser() {
        result = UserList.deleteUser(sUserName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170, enabled = true)
    public void testOpenManageUserToDeleteRole() {
        result = UsersGroupsAndRolesHomePage.openManageRolesSideNav();
        Result.rcEquals(result, "0");
    }
*/
    @Test(priority = 180, enabled = true)
    public void deleteRole() {
        result = roleList.deleteSelectedRole();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void logout() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}
