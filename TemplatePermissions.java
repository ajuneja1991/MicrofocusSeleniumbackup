package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.*;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.*;

/**
 * Created by Eva Sokolyanskaya on 18/08/2016.
 * For manual test "BVD - Instances - Permissions"
 */
public class TemplatePermissions {
    private Map<String, String> result;
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private TemplatePage templatePage;
    private String sourceDashboardAlfa = "sample_dashboard_Template_Alfa";
    private String sourceDashboardBeta = "sample_dashboard_Template_Beta";
    private String dashboardAlfa = sourceDashboardAlfa;
    private String dashboardBeta = sourceDashboardBeta;
    private String dashboardAlfaPath = "C:\\tmp\\" + sourceDashboardAlfa + ".svg";
    private String dashboardBetaPath = "C:\\tmp\\" + sourceDashboardBeta + ".svg";
    private WebDriver driver;
    private String widget = "group848";
    private String key;
    private String dims = "loc";
    private String tags = "template";
    private String dataChannel = tags + " " + "Odessa";
    private BvdWsClient client;
    private String json;
    private List<String> dashboardsUnderCategAlfa= Arrays.asList("");
    private List<String> dashboardsUnderCategBeta= Arrays.asList("");

    private String allFull = "AllFull";
    private String allView = "AllView";
    private String betaFull = "AssignedBetaFull";
    private String betaView = "AssignedBetaView";
    private String anyFull = "AnyAssignedFull";
    private String anyView = "AnyAssignedView";
    private String notFull = "NotAssignedFull";
    private String notView = "NotAssignedView";

    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;

    String userNameAllFull ;
    String userNameAllView ;
    String userNameBetaFull ;
    String userNameBetaView ;
    String userNameAnyFull ;
    String userNameAnyView ;
    String userNameNotFull ;
    String userNameNotView ;

    String categoryAlfa = "Alfa";
    String categoryBeta = "Beta";
    String menucategoryAlfa = "Alfamenu";
    String menucategoryBeta = "Betamenu";
    String userPwd = "User!234";

    String instanceAlfaName = "instanceAlfa";
    String instanceNoCategory = "instanceNoCategory";
    String instanceBetaName = "instanceBeta";

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
        templatePage = PageFactory.initElements(driver, TemplatePage.class);
    }

    private void reLogin(String uname, String pwd) {
        driver.quit();
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        initPages();
        result = loginPage.login(uname, pwd);
        Result.rcEquals(result, "0");
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    private void reLogincheckError(String uname, String pwd) {
        driver.quit();
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        initPages();
        result = loginPage.login(uname, pwd);
        Result.rcEquals(result, "0");
        result = loginPage.idm_LoggedIn_CheckforViewNotAssigned();
        Result.rcEquals(result, "0");
    }
    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        userNameAllFull = "bvd_tester_" + allFull;
        userNameAllView = "bvd_tester_" + allView;
        userNameBetaFull = "bvd_tester_" + betaFull;
        userNameBetaView = "bvd_tester_" + betaView;
        userNameAnyFull = "bvd_tester_" + anyFull;
        userNameAnyView = "bvd_tester_" + anyView;
        userNameNotFull = "bvd_tester_" + notFull;
        userNameNotView = "bvd_tester_" + notView;
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
    public void login_recheck()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }
    @Test(priority = 2)
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sourceDashboardAlfa + ".svg", dashboardAlfaPath);
        Result.rcEquals(result, "0");

        result = TestUtils.downloadResource(this.getClass(), sourceDashboardBeta + ".svg", dashboardBetaPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 20)
    public void getAPIKey() {
        result = client.userWS.getApiKey();
        Result.rcEquals(result, "0");

        key = result.get("out");
    }

    @Test(priority = 30)
    public void sendDataOne() throws UnrecoverableKeyException,
            NoSuchAlgorithmException,
            KeyStoreException, JSONException, KeyManagementException {
        json = "{\"loc\":\"Odessa\"," +
                "\"sales\":\"1\"," +
                "\"temp\":\"11\"," +
                "\"heads\":\"111\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 35)
    public void login_check() {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 40)
    public void prepareTemplates() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.uploadTemplate(dashboardAlfaPath);
        Result.rcEquals(result, "0");

        result = templatePage.selectInstance(instanceAlfaName);
        Result.rcEquals(result, "0");

        result = templatePage.assignNewCategory(categoryAlfa);
        Result.rcEquals(result, "0");

        result = templatePage.assignNewMenuCategory(menucategoryAlfa);
        Result.rcEquals(result, "0");

        result = editDashboardPage.okTemplate();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.uploadTemplate(dashboardBetaPath);
        Result.rcEquals(result, "0");

        result = editDashboardPage.editDashboardTemplateManager(dashboardBeta);
        Result.rcEquals(result, "0");

        result = templatePage.assignNewCategory(categoryBeta);
        Result.rcEquals(result, "0");

        result = editDashboardPage.assignNewMenuCategory(menucategoryBeta);
        Result.rcEquals(result, "0");

        TestUtils.sleepAndLog(2000);

        result = editDashboardPage.applyClick();
        Result.rcEquals(result, "0");


    }

    @Test(priority = 90, enabled = true)
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

    @Test(priority = 100, enabled = true)
    public void checkUser_AllFull() {
        reLogin(userNameAllFull, userPwd);

        dashboardsUnderCategAlfa = Arrays.asList("instanceAlfa");
        dashboardsUnderCategBeta = Arrays.asList("instanceBeta","Automatically created dashboard instance (sample_dashboard_Template_Beta template)");

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {templatePage.getAutoCreatedInstanceName(dashboardAlfa).get("out"), instanceNoCategory};
        Result.outContains(result, expected1);

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.getDashboardsListnew();
        Result.rcEquals(result, "0");
        String[] expected2 = {"sample_dashboard_template_beta" + " (template)", "sample_dashboard_template_alfa" + " (template)"};
        Result.outContains(result, expected2);

        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.openTemplatePage(dashboardAlfa);
        Result.rcEquals(result, "0");

        result = templatePage.goToEditTemplate(dashboardAlfa);
        Result.rcEquals(result, "0");

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = mainPage.checkDashboardsUnderMenuCategory(menucategoryAlfa,dashboardsUnderCategAlfa);
        Result.rcEquals(result, "0");

        result = mainPage.checkDashboardsUnderMenuCategory(menucategoryBeta,dashboardsUnderCategBeta);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 110, enabled = true)
    public void checkUser_AllView() {
        reLogin(userNameAllView, userPwd);

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");

        String[] expected1 = {templatePage.getAutoCreatedInstanceName(dashboardAlfa).get("out"), instanceNoCategory};
        Result.outContains(result, expected1);

        result = mainPage.openDeniedManageDashboards();
        Result.errContains(result, "Failed to open Manage Dashboards");
    }

    @Test(priority = 120, enabled = true)
    public void checkUser_BetaFull() {
        reLogin(userNameBetaFull, userPwd);

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {templatePage.getAutoCreatedInstanceName(dashboardBeta).get("out"),
                instanceBetaName};
        Result.outContains(result, expected1);

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.getDashboardsList();
        Result.rcEquals(result, "0");
        String expected2 = "sample_dashboard_template_beta" + " (template);";
        Result.assertEquals(result.get("out").split(";").length, 1);
        Result.outEquals(result, expected2);

        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.openTemplatePage(dashboardBeta);
        Result.rcEquals(result, "0");

        result = templatePage.goToEditTemplate(dashboardBeta);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130, enabled = true)
    public void checkUser_BetaView() {
        reLogin(userNameBetaView, userPwd);

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {templatePage.getAutoCreatedInstanceName(dashboardBeta).get("out"),
                instanceBetaName};
        Result.outContains(result, expected1);

        result = mainPage.openDeniedManageDashboards();
        Result.errContains(result, "Failed to open Manage Dashboards");
    }

    @Test(priority = 140, enabled = true)
    public void checkUser_AnyAssignedFull() {
        reLogin(userNameAnyFull, userPwd);

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {instanceAlfaName};
        Result.outContains(result, expected1);

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.getDashboardsList();
        Result.rcEquals(result, "0");
        String expected2 = "sample_dashboard_template_beta" + " (template);";
        Result.outContains(result, expected2);
        String notExpected = "sample_dashboard_template_alfa" + " (template);";
        Result.outContainsNot(result, notExpected);

        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.openTemplatePage(dashboardBeta);
        Result.rcEquals(result, "0");

        result = templatePage.goToEditTemplate(dashboardBeta);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150, enabled = true)
    public void checkUser_AnyAssignedView() {
        reLogin(userNameAnyView, userPwd);

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {instanceAlfaName};
        Result.outContains(result, expected1);

        result = mainPage.openDeniedManageDashboards();
        Result.errContains(result, "Failed to open Manage Dashboards");
    }

    @Test(priority = 160, enabled = true)
    public void checkUser_NotAssignedFull() {
        reLogin(userNameNotFull, userPwd);

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {instanceNoCategory,
                templatePage.getAutoCreatedInstanceName(dashboardAlfa).get("out")};
        Result.outContains(result, expected1);

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.getDashboardsList();
        Result.rcEquals(result, "0");
        String expected2 = "sample_dashboard_template_alfa" + " (template);";
        Result.outContains(result, expected2);
        String notExpected = "sample_dashboard_template_beta" + " (template);";
        Result.outContainsNot(result, notExpected);

        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.openTemplatePage(dashboardAlfa);
        Result.rcEquals(result, "0");

        result = templatePage.goToEditTemplate(dashboardAlfa);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170, enabled = true)
    public void checkUser_NotAssignedView() {
        reLogin(userNameNotView, userPwd);

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {instanceNoCategory,
                templatePage.getAutoCreatedInstanceName(dashboardAlfa).get("out")};
        Result.outContains(result, expected1);

        result = mainPage.setDefaultDashboard("Welcome");
        Result.rcEquals(result, "0");

        result = mainPage.openDeniedManageDashboards();
        Result.errContains(result, "Failed to open Manage Dashboards");
    }

    @Test(priority = 180)
    public void testReLoginToAdmin() {
        reLogin(BVD_USR, BVD_PWD);

    }

    @Test(priority = 190, enabled = true)
    public void changeCategoriesAssignments_assign() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.openTemplatePage(dashboardAlfa);
        Result.rcEquals(result, "0");

        result = templatePage.removeCategoryFromInstance(instanceAlfaName, categoryAlfa);
        Result.rcEquals(result, "0");

        result = templatePage.goToEditTemplate(dashboardAlfa);
        Result.rcEquals(result, "0");

        result = client.userWS.getCategoriesNames("permission");
        Result.rcEquals(result, "0");

        result = editDashboardPage.assignNewCategory(categoryBeta);
        Result.rcEquals(result, "0");

        result = editDashboardPage.saveEditedTemplate();
        Result.rcEquals(result, "0");

    }

    @Test(priority = 191, enabled = true)
    public void changeCategoriesAssignments_unAssign() {

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.openTemplatePage(dashboardBeta);
        Result.rcEquals(result, "0");

        result = templatePage.goToEditTemplate(dashboardBeta);
        Result.rcEquals(result, "0");

        result = client.dashboardWS.getAssignedCategories(dashboardBeta);
        Result.rcEquals(result, "0");

        result = editDashboardPage.removeCategoryAssignment(categoryBeta);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200, enabled = true)
    public void reCheckUser_AllFull() {
        reLogin(userNameAllFull, userPwd);

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {templatePage.getAutoCreatedInstanceName(dashboardAlfa).get("out"), instanceNoCategory};
        Result.outContains(result, expected1);

        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.getDashboardsListnew();
        Result.rcEquals(result, "0");
        String[] expected2 = {"sample_dashboard_template_beta" + " (template)", "sample_dashboard_template_alfa" + " (template)"};
        Result.outContains(result, expected2);

        result = manageDashboardsPage.openTemplatePage(dashboardAlfa);
        Result.rcEquals(result, "0");

        templatePage.insertTestAutomation();

        result = templatePage.goToEditTemplate(dashboardAlfa);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 210, enabled = true)
    public void reCheckUser_AllView() {
        reLogin(userNameAllView, userPwd);

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {templatePage.getAutoCreatedInstanceName(dashboardAlfa).get("out"), instanceNoCategory};
        Result.outContains(result, expected1);

        result = mainPage.openDeniedManageDashboards();
        Result.errContains(result, "Failed to open Manage Dashboards");
    }

    @Test(priority = 220, enabled = true)
    public void reCheckUser_BetaFull() {
        reLogincheckError(userNameBetaFull, userPwd);

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {templatePage.getAutoCreatedInstanceName(dashboardAlfa).get("out"), instanceNoCategory};
        Result.outContains(result, expected1);

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.getDashboardsList();
        Result.rcEquals(result, "0");
        String expected2 = "sample_dashboard_template_alfa" + " (template);";
        Result.assertEquals(result.get("out").split(";").length, 1);
        Result.outEquals(result, expected2);

        result = manageDashboardsPage.openTemplatePage(dashboardAlfa);
        Result.rcEquals(result, "0");

        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");

        result = templatePage.goToEditTemplate(dashboardAlfa);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 230, enabled = true)
    public void reCheckUser_BetaView() {
        reLogincheckError(userNameBetaView, userPwd);

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {templatePage.getAutoCreatedInstanceName(dashboardAlfa).get("out"), instanceNoCategory};
        Result.outContains(result, expected1);

        result = mainPage.openDeniedManageDashboards();
        Result.errContains(result, "Failed to open Manage Dashboards");
    }

    @Test(priority = 240, enabled = true)
    public void reCheckUser_AnyAssignedFull() {
        reLogin(userNameAnyFull, userPwd);

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {templatePage.getAutoCreatedInstanceName(dashboardAlfa).get("out"), instanceNoCategory,instanceAlfaName};
        Result.outContains(result, expected1);

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.getDashboardsListnew();
        Result.rcEquals(result, "0");
        String expected2 = "sample_dashboard_template_alfa" + " (template)";
        Result.outContains(result, expected2);
        String notExpected = "sample_dashboard_template_beta" + " (template)";
        Result.outContainsNot(result, notExpected);

        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.openTemplatePage(dashboardAlfa);
        Result.rcEquals(result, "0");

        templatePage.insertTestAutomation();

        result = templatePage.goToEditTemplate(dashboardAlfa);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 250, enabled = true)
    public void reCheckUser_AnyAssignedView() {
        reLogin(userNameAnyView, userPwd);

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {templatePage.getAutoCreatedInstanceName(dashboardAlfa).get("out"),
                instanceNoCategory,
                instanceAlfaName};
        Result.outContains(result, expected1);

        result = mainPage.checkDashboardsUnderMenuCategory(menucategoryAlfa,dashboardsUnderCategAlfa);
        Result.rcEquals(result, "0");

        result = mainPage.openDeniedManageDashboards();
        Result.errContains(result, "Failed to open Manage Dashboards");


    }

    @Test(priority = 260, enabled = true)
    public void reCheckUser_NotAssignedFull() {
        reLogincheckError(userNameNotFull, userPwd);

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1 = {"Welcome"};
        Result.outContains(result, expected1);

        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");

        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.getDashboardsList();
        Result.rcEquals(result, "0");
        String expected2 = "sample_dashboard_template_beta" + " (template)";
        Result.outContains(result, expected2);
        String notExpected = "sample_dashboard_template_alfa" + " (template)";
        Result.outContainsNot(result, notExpected);

        result = manageDashboardsPage.openTemplatePage(dashboardBeta);
        Result.rcEquals(result, "0");

        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");

        result = templatePage.goToEditTemplate(dashboardBeta);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 270, enabled = true)
    public void reCheckUser_NotAssignedView() {
        reLogin(userNameNotView, userPwd);

        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");
        String[] expected1={"Welcome"};
        Result.outContains(result, expected1);

        result = mainPage.checkDashboardsUnderMenuCategory(menucategoryBeta,dashboardsUnderCategBeta);
        Result.rcEquals(result, "0");

        result = mainPage.openDeniedManageDashboards();
        Result.errContains(result, "Failed to open Manage Dashboards");


    }

    @Test(priority = 994, enabled = true)
    public void deleteTemplates() {
        result = client.templateWS.deleteTemplate(dashboardAlfa);
        Result.rcEquals(result, "0");

        result = client.templateWS.deleteTemplate(dashboardBeta);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 996, enabled = true)
    public void deleteRoles() {
        for (Map.Entry<String, String> entry : rolesIds.entrySet()) {
            result = client.userWS.deleteRole(entry.getValue());
            Result.rcEquals(result, "0");
        }
    }

    @Test(priority = 997, enabled = true)
    public void deleteCategories() {
        String[] categories = {categoryAlfa, categoryBeta};
        result = client.userWS.deleteCategories(categories);
        Result.rcEquals(result, "0");
    }

    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}
