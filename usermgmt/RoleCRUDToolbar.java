package com.hp.opr.qa.tests.sys.ui.bvd.usermgmt;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.bvdUserandRolePages.RoleEditor;
import com.hp.opr.qa.framework.pageobjects.usermgmt.RoleList;
import com.hp.opr.qa.framework.pageobjects.usermgmt.UsersGroupsAndRolesHomePage;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class RoleCRUDToolbar  {
    private LoginPage loginPage;
    private MainPage mainPage;
    private UsersGroupsAndRolesHomePage usersGroupsAndRolesHomePage;
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
    private String sRoleName ;
    private String sRoleDescription = "This role does not have users nor groups assigned.";
    private String sNewRoleName = "NewRolcito";
    private String sNewRoleDescription = "This is the modified role, does not have users, groups or permissions.";
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;

    private void initPages() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        usersGroupsAndRolesHomePage = PageFactory.initElements(driver, UsersGroupsAndRolesHomePage.class);
        roleEditor = PageFactory.initElements(driver, RoleEditor.class);
        roleList = PageFactory.initElements(driver, RoleList.class);
    }

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        sRoleName = BROWSER + "RoleCRUD";
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        initPages();
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

    @Test(priority = 20, enabled = true)
    public void openUsersGroupsAndRoles() {
        mainPage.openUserManagementforTest();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30, enabled = true)
    public void openCreateNewRole() {
        result = usersGroupsAndRolesHomePage.openCreateNewRole();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40, enabled = true)
    public void createNewRole() {
        result = roleEditor.createRole(sRoleName, sRoleDescription);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50, enabled = true)
    public void filterRole() {
        result = roleList.filterRole(sRoleName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60, enabled = true)
    public void openCreateDuplicateRole() {
        result = roleList.openCreateNewRole();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70, enabled = true)
    public void createDuplicateRole() {
        result = roleEditor.createDuplicateRole(sRoleName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80, enabled = true)
    public void cancelCreateRole() {
        result = roleEditor.cancelCreateRole();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90, enabled = true)
    public void openEditRole() {
        result = roleList.openEditRole();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100, enabled = true)
    public void editRole() {
        result = roleEditor.editRole(sNewRoleName, sNewRoleDescription);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110, enabled = true)
    public void filterToDeleteRole() {
        result = roleList.filterRole(sNewRoleName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120, enabled = true)
    public void deleteSelectedRole() {
        result = roleList.deleteSelectedRole();
        Result.rcEquals(result, "0");
    }

    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}