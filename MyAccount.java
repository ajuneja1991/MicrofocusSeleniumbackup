package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MyAccountPage;
import com.hp.opr.qa.framework.pageobjects.bvd.UserEditorBVD;
import com.hp.opr.qa.framework.pageobjects.usermgmt.UserEditor;
import com.hp.opr.qa.framework.pageobjects.usermgmt.UsersGroupsAndRolesHomePage;
import com.hp.opr.qa.framework.pageobjects.usermgmt.UsersList;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

@Test(groups = "bvd")
public class MyAccount {
    private UsersGroupsAndRolesHomePage usersGroupsAndRolesHomePage;
    private UserEditorBVD userEditor;
    private UsersList userList;
    private LoginPage loginPage;
    private MainPage mainPage;
    private MyAccountPage myAccountPage;
    private WebDriver driver;
    private Map<String, String> result;
    private BvdWsClient client;
    private String sUserName = "tester";
    private String sUserLogin ;
    private String sUserEmail ;
    private String sUserPwd = "T3st3r!!";
    private Boolean bUserSuperAdmin = false;
    private String wronfPasswd = "somepasswd";
    private String sNewPwd = "Ch4nge!!";
    private String sNewEmail ;
    private String sNewName = "new tester";
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;

    private void initPages() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        userEditor = PageFactory.initElements(driver, UserEditorBVD.class);
        myAccountPage = PageFactory.initElements(driver, MyAccountPage.class);
        userList = PageFactory.initElements(driver, UsersList.class);
        usersGroupsAndRolesHomePage = PageFactory.initElements(driver, UsersGroupsAndRolesHomePage.class);
        client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
    }

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        sUserLogin = "tester-" + BROWSER + "@microfocus.com";
        sUserEmail = "tester-" + BROWSER + "@microfocus.com";
        sNewEmail = "new-tester-" + BROWSER + "@microfocus.com";
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        initPages();
    }


    @Test(priority = 50, enabled = true)
    public void createUser() {
        result = client.userWS.createUserBVD(sUserName, sUserLogin, sUserPwd, false);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60)
    public void loginAsNewUser() {
        driver.get(URL);
        result = loginPage.login(sUserLogin, sUserPwd);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70)
    public void openMyAccountforPasswordValidations() {
        result = mainPage.openMyAccount();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 71)
    public void changePasswordValidations() {
        result = myAccountPage.changePasswordAndCheckWrongOldWithValidations(wronfPasswd, sNewPwd);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 72)
    public void openMyAccount() {
        result = mainPage.openMyAccount();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 75)
    public void changePasswordWrongOld() {
        result = myAccountPage.changePasswordAndCheckWrongOld(wronfPasswd, sNewPwd);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void changePassword() {
        result = myAccountPage.changePassword(sUserPwd, sNewPwd);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90)
    public void logoutNewUser() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void loginNewPassword() {
        driver.get(URL);
        result = loginPage.login(sUserLogin, sNewPwd);
        Result.rcEquals(result, "0");
    }

    /*@Test(priority = 110)
    public void openMyAccountToChangeEmail() {
        result = mainPage.openMyAccount();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void checkOldEmail() {
        result = myAccountPage.getEmail(sUserEmail);
        Result.rcEquals(result, "0");
        Result.outEquals(result, sUserEmail);
    }

    @Test(priority = 130)
    public void changeEmail() {
        result = myAccountPage.changeEmail(sNewEmail);
        Result.rcEquals(result, "0");
      }

    @Test(priority = 140)
    public void checkEmail() {
        result = mainPage.openMyAccount();
        Result.rcEquals(result, "0");
        result = myAccountPage.getEmail(sNewEmail);
        Result.rcEquals(result, "0");
        Result.outEquals(result, sNewEmail);
    }*/

    @Test(priority = 150)
    public void changeName() {
        result = mainPage.openMyAccount();
        Result.rcEquals(result, "0");
        result = myAccountPage.changeName(sNewName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 160)
    public void checkPersonalUserSettingsDisplayName() {
        result = mainPage.getPersonalUserSettingsDisplayName(sNewName);
        Result.rcEquals(result, "0");
        Result.outEquals(result, sNewName);
    }

    @Test(priority = 170)
    public void openMyAccountToCheckName() {
        result = mainPage.openMyAccount();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void checkName() {
        result = myAccountPage.getName(sNewName);
        Result.rcEquals(result, "0");
        Result.outEquals(result, sNewName);
        result = myAccountPage.closeMyAccountPannel();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 250, enabled = true)
    public void deleteUser() {
        result = client.userWS.deleteUserByName(sNewName);
        Result.rcEquals(result, "0");
    }

    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}
