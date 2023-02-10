package com.hp.opr.qa.tests.sys.ui.bvd.usermgmt;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.UserEditorBVD;
import com.hp.opr.qa.framework.pageobjects.usermgmt.*;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AssignGroupToUser  {
    private LoginPage loginPage;
    private MainPage mainPage;
    private UsersGroupsAndRolesHomePage usersGroupsAndRolesHomePage;
    private UsersGroupsAndRolesSideNav usersGroupsAndRolesSideNav;
    private UserEditorBVD userEditor;
    private UsersList userList;
    private GroupEditor groupEditor;
    private GroupList groupList;
    private WebDriver driver;
    private Map<String, String> result;
    private BvdWsClient client;

    private String sUserName ;
    private String sUserLogin;
    private String sUserEmail;
    private String sUserPwd = "T3s!er1?";
    private Boolean bUserSuperAdmin = true;
    private String sGroupName ;
    private String sGroupDescription = "This group does not have users, parents, children or roles.";
    private List<String> lUser ;
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
        groupEditor = PageFactory.initElements(driver, GroupEditor.class);
        groupList = PageFactory.initElements(driver, GroupList.class);
        usersGroupsAndRolesSideNav = PageFactory.initElements(driver, UsersGroupsAndRolesSideNav.class);
    }

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        sUserName = "bvd tester " + BROWSER + " Assign Group To User";
        sUserLogin = "bvd_tester_" + BROWSER + "_AssignGroupToUser";
        sUserEmail = "bvd_tester_" + BROWSER + "_AssignGroupToUser@hpe.com";
        sGroupName = BROWSER + "_Group_AssignGroupToUser";
        lUser = Collections.singletonList(sUserName);
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

    @Test(priority = 20)
    public void openUserManagement() {
        result = mainPage.openUserManagementforTest();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30)
    public void openCreateNewGroup() {
        result = usersGroupsAndRolesHomePage.openCreateNewGroup();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40, enabled = true)
    public void createNewGroup() {
        result = groupEditor.createGroup(sGroupName, sGroupDescription);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50, enabled = true)
    public void openManageUsersSideNavToCreateUser() {
        result = usersGroupsAndRolesSideNav.openManageUsersSideNav();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60, enabled = true)
    public void openCreateNewUser() {
        result = userList.openCreateNewUser();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70, enabled = true)
    public void createNewUser() {
        result = userEditor.createUser(sUserName, sUserLogin, sUserPwd, bUserSuperAdmin);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80, enabled = true)
    public void openEditUser() {
        result = userList.openEditUser(sUserName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90, enabled = true)
    public void assignGroupToUser() {
        result = userEditor.assignGroupToUser(sGroupName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100, enabled = true)
    public void openManageGroupCheckGroupMembers() {
        result = usersGroupsAndRolesSideNav.openManageGroupsSideNav();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110, enabled = true)
    public void verifyGroupMembers() {
        result = groupList.getGroupMembers(sGroupName);
        Result.outEquals(result, lUser.toString());
    }

    @Test(priority = 120, enabled = true)
    public void deleteGroupUsingWS() {
        result = client.userWS.deleteGroupByName(sGroupName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 140, enabled = true)
    public void deleteUserUsingWS() {
        result = client.userWS.deleteUserByName(sUserName);
        Result.rcEquals(result, "0");
    }

    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}
