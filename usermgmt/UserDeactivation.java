package com.hp.opr.qa.tests.sys.ui.bvd.usermgmt;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.UserEditorBVD;
import com.hp.opr.qa.framework.pageobjects.usermgmt.UsersGroupsAndRolesHomePage;
import com.hp.opr.qa.framework.pageobjects.usermgmt.UsersList;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class UserDeactivation {
    private LoginPage loginPage;
    private MainPage mainPage;
    private UsersGroupsAndRolesHomePage usersGroupsAndRolesHomePage;
    private UserEditorBVD userEditor;
    private UsersList userList;
    private WebDriver driver;
    private Map<String, String> result;
    private String sUserName ;
    private String sUserLogin ;
    private String sUserEmail ;
    private String sUserPwd = "T3s!er1?";
    private Boolean bUserSuperAdmin = true;
    private String sNewUserPwd = "T3s!er^^changed";
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;

    private void initPages() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        usersGroupsAndRolesHomePage = PageFactory.initElements(driver, UsersGroupsAndRolesHomePage.class);
        userEditor = PageFactory.initElements(driver, UserEditorBVD.class);
        userList = PageFactory.initElements(driver, UsersList.class);
    }

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        sUserName = "bvd tester " + BROWSER + " User Deactivation";
        sUserLogin = "bvd_tester_" + BROWSER + "_UserDeactivation";
        sUserEmail = "bvd_tester_" + BROWSER + "_UserDeactivation@hpe.com";
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

    @Test(priority = 20)
    public void openUserManagement() {
        result = mainPage.openUserManagementforTest();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30)
    public void openCreateNewUser() {
        result = usersGroupsAndRolesHomePage.openCreateNewUser();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40, enabled = true)
    public void createNewUser() {
        result = userEditor.createUser(sUserName, sUserLogin, sUserPwd, bUserSuperAdmin);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50, enabled = true)
    public void deactivateUser() {
        result = userList.deactivateUser(sUserName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60, enabled = true)
    public void logout() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70, enabled = true)
    public void loginDeactivatedUser() {
        result = loginPage.loginWithWrongCredentials(sUserName, sNewUserPwd);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80, enabled = true)
    public void loginToActivateUser() {
        result = loginPage.login(BVD_USR, BVD_PWD);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90, enabled = true)
    public void openUserManagementToActivateUser() {
        result = mainPage.openUserManagementforTest();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100, enabled = true)
    public void openManageUserToActivateUser() {
        TestUtils.sleep(4);
        result = usersGroupsAndRolesHomePage.openManageUsers();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110, enabled = true)
    public void activateUser() {
        result = userList.activateUser(sUserName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120, enabled = true)
    public void logoutAgain() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130, enabled = true)
    public void loginActivatedUser() {
        result = loginPage.login(BVD_USR, BVD_PWD);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 140, enabled = true)
    public void openUserManagementToDeleteUser() {
        result = mainPage.openUserManagementforTest();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150, enabled = true)
    public void openManageUserToDeleteUser() {
        result = usersGroupsAndRolesHomePage.openManageUsers();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 160, enabled = true)
    public void deleteUser() {
        userList.filterUser(sUserName);
        Result.rcEquals(result, "0");

        result = userList.deleteSelectedUser(sUserLogin);
        Result.rcEquals(result, "0");
    }

    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}