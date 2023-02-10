package com.hp.opr.qa.tests.sys.ui.bvd.usermgmt;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.usermgmt.GroupEditor;
import com.hp.opr.qa.framework.pageobjects.usermgmt.GroupList;
import com.hp.opr.qa.framework.pageobjects.usermgmt.UsersGroupsAndRolesHomePage;
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


public class GroupNegative  {
    private LoginPage loginPage;
    private MainPage mainPage;
    private UsersGroupsAndRolesHomePage usersGroupsAndRolesHomePage;
    private GroupEditor groupEditor;
    private GroupList groupList;
    private WebDriver driver;
    private Map<String, String> result;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String sMAX_LENGTH = "You have exceeded the allowed ";
    private String sDUPLICATE = "A group with this name already exists";
    private String sREQUIRED = "This field is required";
    private String sLONGSTRING = "abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcde";
    private String sDUPLICATENAME = "duplicateGroup";
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;

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
        result = mainPage.openUserManagementforTest();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30, enabled = true)
    public void openCreateNewGroup() {
        result = usersGroupsAndRolesHomePage.openCreateNewGroup();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40)
    public void negCreateNewGroupMaxLength() {
        result = groupEditor.createLongNameGroup(sLONGSTRING, sMAX_LENGTH);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50, enabled = true)
    public void createNewGroup() {
        result = groupEditor.createGroup(sDUPLICATENAME, "This group does not have users, parents, children or roles.");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60, enabled = true)
    public void createDuplicateGroup() {
        result = groupList.openCreateNewGroup();
        Result.rcEquals(result, "0");

        result = groupEditor.createDuplicateGroup(sDUPLICATENAME, sDUPLICATE);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70, enabled = true)
    public void createGroupWithEmptyName() {
        TestUtils.sleep(4);
        result = groupEditor.createEmptyNameGroup(sREQUIRED);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80, enabled = true)
    public void deleteGroup() {
        TestUtils.sleep(4);
        result = groupList.filterGroup(sDUPLICATENAME);
        Result.rcEquals(result, "0");

        result = groupList.deleteSelectedGroup();
        Result.rcEquals(result, "0");
    }

    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}
