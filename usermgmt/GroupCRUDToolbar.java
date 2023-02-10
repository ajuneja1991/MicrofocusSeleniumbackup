package com.hp.opr.qa.tests.sys.ui.bvd.usermgmt;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.bvdUserandRolePages.GroupEditor;
import com.hp.opr.qa.framework.pageobjects.bvd.bvdUserandRolePages.GroupList;
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

public class GroupCRUDToolbar {
    private LoginPage loginPage;
    private MainPage mainPage;
    private UsersGroupsAndRolesHomePage usersGroupsAndRolesHomePage;
    private GroupEditor groupEditor;
    private GroupList groupList;
    private WebDriver driver;
    private Map<String, String> result;
    private BvdWsClient client;

    private String sGroupName ;
    private String sGroupDescription = "This group does not have users, parents, children or roles.";
    private String sNewGroupName ;
    private String sNewGroupDescription = "This is the modified group that still does not have users, parents, children or roles.";
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
        groupEditor = PageFactory.initElements(driver, GroupEditor.class);
        groupList = PageFactory.initElements(driver, GroupList.class);
    }

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        sGroupName = BROWSER + "_Group_Filter_CRUD";
        sNewGroupName = BROWSER + "_New_Group_Filter_CRUD";
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
    public void openUsersGroupsAndRoles() {
        result = mainPage.openUserManagementforTest();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30)
    public void openCreateNewGroup() {
        result = usersGroupsAndRolesHomePage.openCreateNewGroup();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40)
    public void createNewGroup() {
        result = groupEditor.createGroup(sGroupName, sGroupDescription);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50)
    public void filterGroupUI() {
        result = groupList.filterGroup(sGroupName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60)
    public void filterGroupWS() {
        result = client.userWS.getGroups();
        Result.rcEquals(result, "0");
        Result.outContains(result, sGroupName);
    }

    @Test(priority = 70)
    public void openEditGroup() {
        result = groupList.openEditGroup();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void editGroup() {
        result = groupEditor.editGroup(sNewGroupName, sNewGroupDescription);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 85)
    public void checkNewGroupWS() {
        result = client.userWS.getGroups();
        Result.rcEquals(result, "0");
        Result.outContains(result, sNewGroupName);
    }

    @Test(priority = 90)
    public void filterToDeleteGroup() {
        result = groupList.filterGroup(sNewGroupName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void deleteSelectedGroup() {
        result = groupList.deleteSelectedGroup();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void onFailDeleteGroupWS() {
        result = client.userWS.getGroups();
        Result.rcEquals(result, "0");

        if (!result.get("out").contains(sNewGroupName)) {
            result = client.userWS.deleteGroupByName(sNewGroupName);
            Result.rcEquals(result, "0");
        }

        if (!result.get("out").contains(sGroupName)) {
            result = client.userWS.deleteGroupByName(sGroupName);
            Result.rcEquals(result, "0");
        }
    }

    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}