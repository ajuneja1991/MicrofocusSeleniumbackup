package com.hp.opr.qa.tests.sys.ui.bvd.usermgmt;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.hp.opr.qa.framework.pageobjects.bvd.UserEditorBVD;
import com.hp.opr.qa.framework.pageobjects.usermgmt.UsersGroupsAndRolesHomePage;
import com.hp.opr.qa.framework.pageobjects.bvd.bvdUserandRolePages.UsersList;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

/**
 * Created by Eva Sokolyanskaya on 02/12/2016.
 */
public class UserPermissions  {
    private Map<String, String> result;
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private UsersGroupsAndRolesHomePage userManagementPage;
    private UsersList userList;
    private UserEditorBVD userEditor;

    private String sourceDashboard ;
    private String dashboardAlfa ;
    private String dashboardBeta ;
    private String dashboardGamma;
    private String dashboardAlfaPath ;
    private String dashboardBetaPath ;
    private String dashboardGammaPath;
    private WebDriver driver;
    private BvdWsClient client;

    private String allFull = "AllFull";
    private String allView = "AllView";
    private String betaFull = "AssignedBetaFull";
    private String betaView = "AssignedBetaView";
    private String anyFull = "AnyAssignedFull";
    private String anyView = "AnyAssignedView";
    private String notFull = "NotAssignedFull";
    private String notView = "NotAssignedView";

    String userNameAllFull;
    String userNameAllView;
    String userNameBetaFull;
    String userNameBetaView;
    String userNameAnyFull ;
    String userNameAnyView ;
    String userNameNotFull ;
    String userNameNotView ;
    String userNameSA ;

    String categoryBeta = "Beta";
    String categoryGamma = "Gamma";
    String roleGamma = "roleGamma";
    String roleGammaId;
    String userPwd = "User!234";
    private List<String> allViewtitles = Arrays.asList("Dashboards", "Personal user settings", "Administration", "Help");
    private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;

    Map<String, String> rolesIds = new HashMap<>();
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
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
        userManagementPage = PageFactory.initElements(driver, UsersGroupsAndRolesHomePage.class);
        userList = PageFactory.initElements(driver, UsersList.class);
        userEditor = PageFactory.initElements(driver, UserEditorBVD.class);
    }

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        sourceDashboard = "sample_dashboard_test";
        dashboardAlfa = sourceDashboard + "_BVD_Template_Alfa_" + BROWSER;
        dashboardBeta = sourceDashboard + "_BVD_Template_Beta_" + BROWSER;
        dashboardGamma = sourceDashboard + "_BVD_Template_Gamma_" + BROWSER;
        dashboardAlfaPath = "C:\\tmp\\" + dashboardAlfa + ".svg";
        dashboardBetaPath = "C:\\tmp\\" + dashboardBeta + ".svg";
        dashboardGammaPath = "C:\\tmp\\" + dashboardGamma + ".svg";
        userNameAllFull = "bvd_tester_" + allFull;
        userNameAllView = "bvd_tester_" + allView;
        userNameBetaFull = "bvd_tester_" + betaFull;
        userNameBetaView = "bvd_tester_" + betaView;
        userNameAnyFull = "bvd_tester_" + anyFull;
        userNameAnyView = "bvd_tester_" + anyView;
        userNameNotFull = "bvd_tester_" + notFull;
        userNameNotView = "bvd_tester_" + notView;
        userNameSA = "bvd_tester_SA";
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
        result = TestUtils.downloadResource(this.getClass(), sourceDashboard + ".svg", dashboardAlfaPath);
        Result.rcEquals(result, "0");

        result = TestUtils.downloadResource(this.getClass(), sourceDashboard + ".svg", dashboardBetaPath);
        Result.rcEquals(result, "0");

        result = TestUtils.downloadResource(this.getClass(), sourceDashboard + ".svg", dashboardGammaPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 20)
    public void uploadDashboardsSetCategories() {
        uploadDashboardSetCategory(dashboardGammaPath, categoryGamma);
        uploadDashboardSetCategory(dashboardBetaPath, categoryBeta);

        result = manageDashboardsPage.uploadDashboard(dashboardAlfaPath);
        Result.rcEquals(result, "0");

        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40, enabled = true)
    public void createRoles() {

        List<Map<String, String>> roleList = new ArrayList<>();
        Map<String, String> role = new HashMap<>();
        role.put("name", allFull);
        role.put("description", allFull);
        role.put("categoryName", "All");
        role.put("accessType", "full-control");
        roleList.add(role);

        role = new HashMap<>();
        role.put("name", allView);
        role.put("description", allView);
        role.put("categoryName", "All");
        role.put("accessType", "view");
        roleList.add(role);

        role = new HashMap<>();
        role.put("name", betaFull);
        role.put("description", betaFull);
        role.put("categoryName", "Beta");
        role.put("accessType", "full-control");
        roleList.add(role);

        role = new HashMap<>();
        role.put("name", betaView);
        role.put("description", betaView);
        role.put("categoryName", "Beta");
        role.put("accessType", "view");
        roleList.add(role);

        role = new HashMap<>();
        role.put("name", anyFull);
        role.put("description", anyFull);
        role.put("categoryName", "assigned");
        role.put("accessType", "full-control");
        roleList.add(role);

        role = new HashMap<>();
        role.put("name", anyView);
        role.put("description", anyView);
        role.put("categoryName", "assigned");
        role.put("accessType", "view");
        roleList.add(role);

        role = new HashMap<>();
        role.put("name", notFull);
        role.put("description", notFull);
        role.put("categoryName", "not-assigned");
        role.put("accessType", "full-control");
        roleList.add(role);

        role = new HashMap<>();
        role.put("name", notView);
        role.put("description", notView);
        role.put("categoryName", "not-assigned");
        role.put("accessType", "view");
        roleList.add(role);


        for (Map<String, String> rl : roleList) {
            result = client.userWS.createRole(rl.get("name"), rl.get("description"),
                    rl.get("categoryName"), rl.get("accessType"));
            Result.rcEquals(result, "0");
            rolesIds.put(rl.get("name"), result.get("out"));
        }
    }

    @Test(priority = 50, enabled = true)
    public void createRole() {
        result = client.userWS.createRole(roleGamma, "roleGammaDescription", categoryGamma, "view");
        Result.rcEquals(result, "0");
        roleGammaId = result.get("out");

    }

    @Test(priority = 100, enabled = true)
    public void checkUser_AllFull() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
        driver.get(URL);
        result = loginPage.login(userNameAllFull, userPwd);
        Result.rcEquals(result, "0");

        result = loginPage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {dashboardAlfa, dashboardBeta, dashboardGamma};
        Result.outContains(result, expected1);

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        TestUtils.sleepAndLog(2000);

        result = mainPage.setDefaultDashboard(dashboardBeta);
        Result.rcEquals(result, "0");

        TestUtils.sleepAndLog(2000);

        result = manageDashboardsPage.getDashboardsList();
        Result.rcEquals(result, "0");
        String[] expected2 = {"sample_dashboard_test_bvd_template_alfa_chrome", "sample_dashboard_test_bvd_template_beta_chrome", "sample_dashboard_test_bvd_template_gamma_chrome"};
        Result.outContains(result, expected2);

        TestUtils.sleep(2);

        result = manageDashboardsPage.checkUploadDashboardAllowed();
        Result.rcEquals(result, "0");

        TestUtils.sleep(15);
    }

    @Test(priority = 110, enabled = true)
    public void checkUser_AllView() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");

        TestUtils.sleep(15);
        driver.get(URL);
        result = loginPage.login(userNameAllView, userPwd);
        Result.rcEquals(result, "0");

        result = loginPage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");

        result=mainPage.validateMenuOptionswhenrestricted(allViewtitles);
        Result.rcEquals(result, "0");

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {dashboardAlfa, dashboardBeta, dashboardGamma};
        Result.outContains(result, expected1);

        result = mainPage.setDefaultDashboard(dashboardAlfa);
        Result.rcEquals(result, "0");

        result = mainPage.openManageDashboards();
        Result.errContains(result, "Failed to open Manage Dashboards");
    }

    @Test(priority = 120, enabled = true)
    public void checkUser_BetaFull() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
        driver.get(URL);
        result = loginPage.login(userNameBetaFull, userPwd);
        Result.rcEquals(result, "0");

        result = loginPage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");

        result= mainPage.adminoptionsfornonadmin(admindropdownoptions);
        Result.rcEquals(result, "0");

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {dashboardBeta};
        Result.outContains(result, expected1);

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.getDashboardsList();
        Result.rcEquals(result, "0");
        String[] expected2 = {"sample_dashboard_test_bvd_template_beta_chrome"};
        Result.assertEquals(result.get("out").split(";").length, 1);
        Result.outContains(result, expected2);
        Result.outContainsNot(result, dashboardAlfa);

        result = manageDashboardsPage.checkUploadDashboardForbidden();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130, enabled = true)
    public void checkUser_BetaView() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
        driver.get(URL);
        result = loginPage.login(userNameBetaView, userPwd);
        Result.rcEquals(result, "0");

        result = loginPage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");

        result=mainPage.validateMenuOptionswhenrestricted(allViewtitles);
        Result.rcEquals(result, "0");

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        Result.outContains(result, dashboardBeta);
        Result.outContainsNot(result, dashboardAlfa);

        result = mainPage.setDefaultDashboard(dashboardBeta);
        Result.rcEquals(result, "0");

        result = mainPage.openManageDashboards();
        Result.errContains(result, "Failed to open Manage Dashboards");
    }

    @Test(priority = 140, enabled = true)
    public void checkUser_AnyAssignedFull() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
        driver.get(URL);
        result = loginPage.login(userNameAnyFull, userPwd);
        Result.rcEquals(result, "0");

        result = loginPage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");

        result= mainPage.adminoptionsfornonadmin(admindropdownoptions);
        Result.rcEquals(result, "0");

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {dashboardBeta, dashboardGamma};
        Result.outContains(result, expected1);
        Result.outContainsNot(result, dashboardAlfa);

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.getDashboardsList();
        Result.rcEquals(result, "0");
        String[] expected2 = {"sample_dashboard_test_bvd_template_beta_chrome", "sample_dashboard_test_bvd_template_gamma_chrome"};
        Result.outContains(result, expected2);
        Result.outContainsNot(result, dashboardAlfa);

        result = manageDashboardsPage.checkUploadDashboardForbidden();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150, enabled = true)
    public void checkUser_AnyAssignedView() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
        driver.get(URL);
        result = loginPage.login(userNameAnyView, userPwd);
        Result.rcEquals(result, "0");

        result = loginPage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");

        result=mainPage.validateMenuOptionswhenrestricted(allViewtitles);
        Result.rcEquals(result, "0");

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {dashboardBeta, dashboardGamma};
        Result.outContains(result, expected1);
        Result.outContainsNot(result, dashboardAlfa);

        result = mainPage.setDefaultDashboard(dashboardBeta);
        Result.rcEquals(result, "0");

        result = mainPage.openManageDashboards();
        Result.errContains(result, "Failed to open Manage Dashboards");
    }

    @Test(priority = 160, enabled = true)
    public void checkUser_NotAssignedFull() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
        driver.get(URL);
        result = loginPage.login(userNameNotFull, userPwd);
        Result.rcEquals(result, "0");

        result = loginPage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");

        result= mainPage.adminoptionsfornonadmin(admindropdownoptions);
        Result.rcEquals(result, "0");

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        Result.outContains(result, dashboardAlfa);

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.getDashboardsList();
        Result.rcEquals(result, "0");
        Result.outContains(result, "sample_dashboard_test_bvd_template_alfa_chrome");
        Result.outContainsNot(result, "sample_dashboard_test_bvd_template_beta_chrome");
        Result.outContainsNot(result, "sample_dashboard_test_bvd_template_gamma_chrome");

        result = manageDashboardsPage.checkUploadDashboardForbidden();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170, enabled = true)
    public void checkUser_NotAssignedView() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
        driver.get(URL);
        result = loginPage.login(userNameNotView, userPwd);
        Result.rcEquals(result, "0");

        result = loginPage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");

        result=mainPage.validateMenuOptionswhenrestricted(allViewtitles);
        Result.rcEquals(result, "0");

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        Result.outContains(result, dashboardAlfa);
        Result.outContainsNot(result, dashboardBeta);
        Result.outContainsNot(result, dashboardGamma);

        result = mainPage.setDefaultDashboard(dashboardAlfa);

        result = mainPage.openManageDashboards();
        Result.errContains(result, "Failed to open Manage Dashboards");
    }

    @Test(priority = 994, enabled = true)
    public void deleteDashboards() {
        result = client.dashboardWS.deleteDashboard(dashboardAlfa);
        Result.rcEquals(result, "0");

        result = client.dashboardWS.deleteDashboard(dashboardBeta);
        Result.rcEquals(result, "0");

        result = client.dashboardWS.deleteDashboard(dashboardGamma);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 996, enabled = true)
    public void deleteRoles() {
        for (Map.Entry<String, String> entry : rolesIds.entrySet()) {
            result = client.userWS.deleteRole(entry.getValue());
            Result.rcEquals(result, "0");
        }

        result = client.userWS.deleteRole(roleGammaId);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 998, enabled = true)
    public void deleteCategories() {
        String[] categories = {categoryBeta, categoryGamma};
        result = client.userWS.deleteCategories(categories);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 999, enabled = false)
    public void testLogout() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @AfterClass
    public void cleanup() {
        driver.quit();
    }

    private void uploadDashboardSetCategory(String dashboardPath, String categoryName) {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.uploadDashboard(dashboardPath);
        Result.rcEquals(result, "0");

        result = editDashboardPage.assignNewCategory(categoryName);
        Result.rcEquals(result, "0");

        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

}