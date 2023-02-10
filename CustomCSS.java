package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.SystemSettingsPage;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CustomCSS   {
    private LoginPage loginPage;
    private MainPage mainPage;
    private SystemSettingsPage systemSettingsPage;
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;
    private BvdWsClient client;
    private WebDriver driver;
    private Map<String, String> result;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String userName = "TesterCustomCSS";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testCustomCSS";
    private String desc = "For tester";
    private String category = "All";
    private String accessType = "full-control";
    String roleId;
    private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");
    private void initPages() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        systemSettingsPage = PageFactory.initElements(driver, SystemSettingsPage.class);
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
        try
        {
            //client = new BvdRestClient(PROXY_HOST, PROXY_PORT);
            client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
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


    @Test(priority = 10)
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }


    @Test(priority = 20)
    public void testOpenSystemSettings() {
        result = mainPage.openSystemSettings();
        Result.rcEquals(result, "0");
    }



    @Test(priority = 30)
    public void uploadCustomCSS() {


        String sCustomCSS = ".feedItem .ts {display: none;}" +
                ".feedItem .even {background-color: #1895E8;}" +
                ".feedItem a:hover {text-decoration: none;}" +
                ".feedItem section {height:30px; padding: 3px; margin: 0}" +
                ".feedItem {font-family: 'Indie Flower'}" +
                "body, .navbar-dashboard .navbar-brand, .label-hp, .navbar-default .navbar-nav > li > " +
                "a, .dropdown-menu > li > a {font-family: 'Indie Flower'; color: #9999FF;}" +
                ".btn-primary-hp, .uploadItem {background-color: #6600CC;}" +
                ".navbar-dashboard {background-color: #CCFFFF;}" +
                "element {background-color: #E9FFFF;}" +
                "#mainView .manageDashboardSize h2 {font-family: 'Indie Flower';}"+
                "@font-face {" +
                "font-family: 'Indie Flower';" +
                "  font-style: normal;" +
                "  font-weight: 400;" +
                "  src: local('Indie Flower'), local('IndieFlower'), url(https://fonts.gstatic" +
                ".com/s/indieflower/v7/10JVD_humAd5zP2yrFqw6nhCUOGz7vYGh680lGh-uXM.woff) format('woff')" +
                ";" +
                "}";

        result = systemSettingsPage.uploadChangedStyle(sCustomCSS);
        Result.rcEquals(result, "0");
    }



    @Test(priority = 33)
    public void logOutFromUser() {
        result = mainPage.logout();
    }

    @Test(priority = 35)
    public void loginIntoAdmin() {
        driver.get(URL);
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 37)
    public void loginCheck()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 45)
    public void openManageDashboards() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50)
    public void verifyCustomCSS() {
      TestUtils.sleep(2);
        String xpath = "//h2[text()='Dashboards']";

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        WebElement header = driver.findElement(By.xpath(xpath));

        // font compare
        if (StringUtils.containsIgnoreCase(header.getCssValue("font-family"), "indie flower")) {
            result = ResultStore.success("Custom font Indie Flower has been applied");
        } else {
            result = ResultStore.failure("Failed to apply custom font Indie Flower. Instead: " + header
                    .getCssValue("font-family"));
        }

        Result.rcEquals(result, "0");
    }

    @Test(priority = 53)
    public void logOutUser() {
        result = mainPage.logout();
    }

    @Test(priority = 55)
    public void logIntoAdmin() {
        result = loginPage.login(BVD_USR, BVD_PWD);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 57)
    public void loginCheckIDM()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60)
    public void openSystemSettingsToDeleteCustomCSS() {
        result = mainPage.openSystemSettings();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70)
    public void clearChangedStyleEmptyString() {
      result = systemSettingsPage.deleteChangedStyle();
      Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void openManageDashboardsToCheckDeletedCSS() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90)
    public void verifyDefaultCSS() {
        String xpath = "//h2[text()='Dashboards']";
        //temporary wait here.
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        WebElement header = driver.findElement(By.xpath(xpath));
        TestUtils.sleep(2);
        // font compare
        if (StringUtils.containsIgnoreCase(header.getCssValue("font-family"), "Metric, \"Helvetica Neue\", Helvetica, Arial, sans-serif")) {
            result = ResultStore.success("Default font has been applied");
        } else {
            result = ResultStore.failure("Failed to find default font. Instead: " + header
                    .getCssValue("font-family"));
        }

        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
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
