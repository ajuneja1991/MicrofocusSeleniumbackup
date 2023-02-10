package com.hp.opr.qa.tests.sys.ui.bvd.usermgmt;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.UserEditorBVD;
import com.hp.opr.qa.framework.pageobjects.bvd.bvdUserandRolePages.UsersList;
import com.hp.opr.qa.framework.pageobjects.usermgmt.GroupEditor;
import com.hp.opr.qa.framework.pageobjects.usermgmt.GroupList;
import com.hp.opr.qa.framework.pageobjects.usermgmt.UsersGroupsAndRolesHomePage;
import com.hp.opr.qa.framework.pageobjects.usermgmt.UsersGroupsAndRolesSideNav;
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

public class AssignUserToGroup {
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
    private List<String> lGroup ;
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
        sUserName = "bvd tester " + BROWSER + " Assign User To Group";
        sUserLogin = "bvd_tester_" + BROWSER + "_AssignUserToGroup";
        sUserEmail = "bvd_tester_" + BROWSER + "_AssignUserToGroup@hpe.com";
        sGroupName = BROWSER + "_Group_AssignUserToGroup";
        lGroup = Collections.singletonList(sGroupName);
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
    public void openManageGroupsSideNavToCreateGroup() {
        result = usersGroupsAndRolesSideNav.openManageGroupsSideNav();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60, enabled = true)
    public void openCreateNewGroup() {
        result = groupList.openCreateNewGroup();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70, enabled = true)
    public void createNewGroup() {
        result = groupEditor.createGroup(sGroupName, sGroupDescription);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80, enabled = true)
    public void openEditGroup() {
        result = groupList.openEditGroup(sGroupName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90, enabled = true)
    public void assignUserToGroup() {
        result = groupEditor.assignUserToGroup(sUserName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100, enabled = true)
    public void openManageUserCheckGroupMembership() {
        result = usersGroupsAndRolesSideNav.openManageUsersSideNav();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110, enabled = true)
    public void verifyGroupMembership() {
        result = userList.getGroupMemberships(sUserName);
        Result.outEquals(result, lGroup.toString());
    }

    @Test(priority = 120, enabled = true)
    public void deleteUser() {
        result = client.userWS.deleteUserByName(sUserName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130, enabled = true)
    public void openManageUserToDeleteGroup() {
        result = usersGroupsAndRolesSideNav.openManageGroupsSideNav();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 140, enabled = true)
    public void deleteGroup() {
        result = client.userWS.deleteGroupByName(sGroupName);
        Result.rcEquals(result, "0");
    }

    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}
