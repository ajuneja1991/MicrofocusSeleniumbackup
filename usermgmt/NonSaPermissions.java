package com.hp.opr.qa.tests.sys.ui.bvd.usermgmt;


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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Eva Sokolyanskaya on 08/12/2016.
 * Check "Edit dashboards" permissions of non Super-Admin user
 */
public class NonSaPermissions {
    private Map<String, String> result;
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private TemplatePage templatePage;

    private String sourceDashboard = "sample_dashboard_test";
    private String dashboardAlfa ;
    private String dashboardBeta ;
    private String templateGamma ;
    private String dashboardAlfaPath ;
    private String dashboardBetaPath ;
    private String templateGammaPath ;

    private String instanceName = "gamma_instance";
    private String autoCreatedInstanceName;
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;

    String categoryAlfa = "Alfa";
    String categoryBeta = "Beta";
    String categoryGamma = "Gamma";

    private WebDriver driver;
    private BvdWsClient client;
    private BvdWsClient userClient;
    private String widget = "group792";
    private String key;
    private String dims = "loc";
    private String tags = "nonSA";
    private String dataChannel = tags + " " + "Odessa";
    private String json;
    private List<String> dataFields = Arrays.asList("temp", "heads", "sales");

    String userName ;
    String userPwd = "User!234";
    String roleName = "BVD_Role_Full_Control";
    String roleId;
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
        //driver.get(EXT_BVD_URL + "/login/" + uname + "/" + pwd);
        //driver.get(EXT_BVD_URL);
        result = loginPage.login(BVD_USR, BVD_PWD);
        Result.rcEquals(result, "0");
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        dashboardAlfa = "A_" + sourceDashboard + "_BVD_Dashboard_Alfa_" + BROWSER;
        dashboardBeta = "A_" + sourceDashboard + "_BVD_Dashboard_Beta_" + BROWSER;
        templateGamma = "A_" + sourceDashboard + "_BVD_Template_Gamma_" + BROWSER;
        dashboardAlfaPath = "C:\\tmp\\" + dashboardAlfa + ".svg";
        dashboardBetaPath = "C:\\tmp\\" + dashboardBeta + ".svg";
        templateGammaPath = "C:\\tmp\\" + templateGamma + ".svg";
        userName = "non_SA_user";
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        initPages();
        client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
        userClient = new BvdWsClient(userName, userPwd, PROXY_HOST, PROXY_PORT);
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

        result = TestUtils.downloadResource(this.getClass(), sourceDashboard + ".svg", templateGammaPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 20)
    public void uploadDashboardByAdmin() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.uploadDashboard(dashboardAlfaPath);
        Result.rcEquals(result, "0");

        result = editDashboardPage.assignNewCategory(categoryAlfa);
        Result.rcEquals(result, "0");

        result = editDashboardPage.saveClick();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40, enabled = true)
    public void createRole() {
        result = client.userWS.createRole(roleName, roleName + "_description",
                "All", "full-control");
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }

    @Test(priority = 50)
    public void loginAsUser() {
        reLogin(userName, userPwd);
    }

    @Test(priority = 60)
    public void sendDataByUser() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException {
        result = client.userWS.getApiKey();
        Result.rcEquals(result, "0");
        key = result.get("out");

        json = "{\"loc\":\"Odessa\"," +
                "\"sales\":\"1\"," +
                "\"temp\":\"11\"," +
                "\"heads\":\"111\"}";

        result = userClient.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70)
    public void uploadDashboardByUser() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.uploadDashboard(dashboardBetaPath);
        Result.rcEquals(result, "0");

        result = editDashboardPage.assignNewCategory(categoryBeta);
        Result.rcEquals(result, "0");

        result = editDashboardPage.selectWidget(widget);
        Result.rcEquals(result, "0");

        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataChannel(dataChannel);
        Result.rcEquals(result, "0");

        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataFieldMultiSelect(dataFields);
        Result.rcEquals(result, "0");

        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void createTemplateByUser() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.uploadDashboard(templateGammaPath);
        Result.rcEquals(result, "0");

        result = editDashboardPage.assignExistingCategory(categoryAlfa);
        Result.rcEquals(result, "0");

        result = editDashboardPage.selectWidget(widget);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataChannel(dataChannel);
        Result.rcEquals(result, "0");

        result = editDashboardPage.convertToTemplate();
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataFieldMultiSelect(dataFields);
        Result.rcEquals(result, "0");

        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90)
    public void editDashboard() {
        result = manageDashboardsPage.openEditDashboard(dashboardAlfa);
        Result.rcEquals(result, "0");

        result = editDashboardPage.assignExistingCategory(categoryBeta);
        Result.rcEquals(result, "0");

        result = editDashboardPage.selectWidget(widget);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataChannel(dataChannel);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataFieldMultiSelect(dataFields);
        Result.rcEquals(result, "0");

        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void editTemplate() {
        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");
//
//                result = mainPage.openManageDashboards();
//        Result.rcEquals(result, "0");

        result = manageDashboardsPage.openTemplatePage(templateGamma);
        Result.rcEquals(result, "0");

        result = templatePage.createInstance(instanceName);
        Result.rcEquals(result, "0");


        result = templatePage.assignNewCategory(categoryGamma);
        Result.rcEquals(result, "0");

        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");

        result = templatePage.getAutoCreatedInstanceName(templateGamma);
        Result.rcEquals(result, "0");
        autoCreatedInstanceName = result.get("out");

        result = templatePage.selectInstance(autoCreatedInstanceName);
        Result.rcEquals(result, "0");

        result = templatePage.clickToggleShowInMenu(autoCreatedInstanceName);
        Result.rcEquals(result, "0");

        result = templatePage.applyChanges();
        Result.rcEquals(result, "0");

        result = templatePage.getDownloadLink(templateGamma);
        Result.rcEquals(result, "0");

        String expected = (mainPage.getBvdUrlForDownload().get("out") + "/ui/dashboard-download/" +
                templateGamma).toLowerCase();
        Result.assertEquals(result.get("out").toLowerCase(), expected);
    }

    @Test(priority = 110)
    public void manageDashboards() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.getDownloadLink(dashboardAlfa);
        Result.rcEquals(result, "0");

        String expected = (mainPage.getBvdUrlForDownload().get("out") + "/ui/dashboard-download/" +
                dashboardAlfa).toLowerCase();
        Result.assertEquals(result.get("out").toLowerCase(), expected);

        result = manageDashboardsPage.hideDashboardFromMenu(dashboardAlfa);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void viewDashboards() {
        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");

        String[] expected = {dashboardBeta, instanceName};
        Result.outContains(result, expected);

        Result.outContainsNot(result, dashboardAlfa);
        Result.outContainsNot(result, autoCreatedInstanceName);
    }

    @Test(priority = 990, enabled = true)
    public void checkDeletePermissions() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.deleteDashboard(dashboardAlfa);
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.deleteDashboard(dashboardBeta);
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.openTemplatePage(templateGamma);
        Result.rcEquals(result, "0");

        result = templatePage.deleteTemplate(templateGamma);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 994, enabled = true)
    public void deleteDashboards() {
        if (!result.get("rc").equalsIgnoreCase("0")) {
            result = client.dashboardWS.deleteDashboard(dashboardAlfa);
            Result.rcEquals(result, "0");

            result = client.dashboardWS.deleteDashboard(dashboardBeta);
            Result.rcEquals(result, "0");

            result = client.templateWS.deleteTemplate(templateGamma);
            Result.rcEquals(result, "0");
        }
    }

    @Test(priority = 996, enabled = true)
    public void deleteRoles() {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 997, enabled = true)
    public void deleteCategories() {
        String[] categories = {categoryAlfa, categoryBeta, categoryGamma};
        result = client.userWS.deleteCategories(categories);
        Result.rcEquals(result, "0");
    }

    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}
