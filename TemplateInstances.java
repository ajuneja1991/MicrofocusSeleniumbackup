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
import java.util.Map;

/**
 * Created by Eva Sokolyanskaya on 13/07/2016.
 */
public class TemplateInstances {
    private WebDriver driver;
    private Map<String, String> result;
    private BvdWsClient client;
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private TemplatePage templatePage;
    private String sourceDashboard = "sample_dashboard_test";
    private String dashboard ;
    private String dashboardPath ;
    private String key;
    private String dims = "browser,OS";
    private String tags = "instance";
    private String json;
    private String widget = "group848";
    private String dataChannel = "instance chrome_temp_inst Unix";
    private int newInstanceNumber;
    private String newInstance = "New Instance";
    private String newInstanceTitle = "Renamed_New_Instance";
    private String title1 = "Cannondale";
    private String title2 = "Merida";
    private String category1 = "myCategory";
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String userName = "Testertemplateinstance";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testtemplateinstance";
    private String desc = "For templateinstance";
    private String category = "All";
    private String accessType = "full-control";
    String roleId;


    private void initPages() {

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
        templatePage = PageFactory.initElements(driver, TemplatePage.class);
    }

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        dashboard = sourceDashboard + "_BVD_Instance_" + BROWSER;
        dashboardPath = "C:\\tmp\\" + dashboard + ".svg";
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        initPages();
        client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
    }

    @Test(priority = 12)
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }
    @Test(priority = 16)
    public void idmlogin() {
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 17)
    public void login_check()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 18)
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sourceDashboard + ".svg", dashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 20)
    public void getAPIKey() {
        result = client.userWS.getApiKey();
        Result.rcEquals(result, "0");

        key = result.get("out");
    }

    @Test(priority = 30)
    public void createTemplate_sendData() throws UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException, JSONException, KeyManagementException {
        json = "{\"browser\":\"chrome_temp_inst\"," +
                "\"OS\":\"Unix\"," +
                "\"omiSystem\":\"MAMBO\"," +
                "\"mdb\":\"OMi Health Status\"," +
                "\"label\":\"Overall\"," +
                "\"type\":\"PIE\"," +
                "\"filterName\":\"OMi Health Status Overall\"," +
                "\"viewFilterName\":\"OMi Deployment with HP Operations Agents\"," +
                "\"filterStatus\":\"FILTER_OK\"," +
                "\"mostCritical\":3," +
                "\"numberOfCritical\":25," +
                "\"numberOfMajor\":20," +
                "\"numberOfMinor\":15," +
                "\"numberOfNormal\":10," +
                "\"numberOfUnknown\":5," +
                "\"statusColor\":\"#C8C800\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40)
    public void createTemplate_OpenManageDashboards() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50)
    public void createTemplate_uploadDashboard() {
        result = manageDashboardsPage.uploadDashboard(dashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60)
    public void createTemplate_saveUploadedDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70)
    public void createTemplate_openEditDashboard() {
        result = manageDashboardsPage.openEditDashboard(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void createTemplate_selectWidget() {
        result = editDashboardPage.selectWidget(widget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90)
    public void createTemplate_setDataChannel() {
        result = editDashboardPage.setDataChannel(dataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void createTemplate_convertToTemplate() {
        result = editDashboardPage.convertToTemplate();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void createTemplate_openManageDashboards() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void createInstance_enableAutoTest() {
        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void createInstance_openTemplatePage() {
        result = manageDashboardsPage.openTemplatePage(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 140)
    public void createInstance() {
        result = templatePage.createInstance();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150)
    public void createInstance_checkNumberOfNewInstances() {
        result = templatePage.getNewInstancesNumber();
        Result.rcEquals(result, "0");

        newInstanceNumber = Integer.parseInt(result.get("out"));
        Result.assertEquals(newInstanceNumber, 1);
    }

    @Test(priority = 160)
    public void createInstance_checkNewInstanceName() {
        result = templatePage.getInstances();
        Result.outContains(result, newInstance);
    }

    @Test(priority = 170)
    public void changeTitle_selectInstance() {
        result = templatePage.selectInstance(newInstance);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void changeTitle_setNewTitle() {
        result = templatePage.setInstancesTitle(newInstanceTitle);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void changeTitle_checkNewInstanceTitle() {
        result = templatePage.getInstances();
        Result.outContains(result, newInstanceTitle);
    }

    @Test(priority = 200)
    public void changeTitle_checkDashboardTitle() {
        result = mainPage.getDashboardNames();
        Result.outContains(result, newInstanceTitle);
    }

    @Test(priority = 205)
    public void secondValue_sendData() throws UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException, JSONException, KeyManagementException {
        json = "{\"browser\":\"firefox_temp_inst\"," +
                "\"OS\":\"Unix\"," +
                "\"omiSystem\":\"MAMBO\"," +
                "\"mdb\":\"OMi Health Status\"," +
                "\"label\":\"Overall\"," +
                "\"type\":\"PIE\"," +
                "\"filterName\":\"OMi Health Status Overall\"," +
                "\"viewFilterName\":\"OMi Deployment with HP Operations Agents\"," +
                "\"filterStatus\":\"FILTER_OK\"," +
                "\"mostCritical\":3," +
                "\"numberOfCritical\":25," +
                "\"numberOfMajor\":20," +
                "\"numberOfMinor\":15," +
                "\"numberOfNormal\":10," +
                "\"numberOfUnknown\":5," +
                "\"statusColor\":\"#C8C800\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 210)
    public void changeTitle_openTemplatePage() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.openTemplatePage(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void checkValue_selectInstance() {
        result = templatePage.selectInstance(newInstanceTitle);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 230)
    public void checkValue_setFirstValue() {
        result = templatePage.setVariableValue("browser", "chrome_temp_inst");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 240)
    public void checkValue_getVariableValue() {
        result = templatePage.getVariableValue("browser");
        Result.assertEquals(result.get("out"), "chrome_temp_inst");
    }


    @Test(priority = 260)
    public void secondValue_selectInstance() {
        result = templatePage.selectInstance(newInstanceTitle);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 270)
    public void secondValue_setValue() {
        result = templatePage.setVariableValue("browser", "firefox_temp_inst");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 280)
    public void secondValue_getVariableValue() {
        result = templatePage.getVariableValue("browser");
        Result.assertEquals(result.get("out"), "firefox_temp_inst");
    }

    @Test(priority = 290)
    public void checkFilter_createInstance1() {
        result = templatePage.createInstance(title1);
        Result.outContains(result, title1);
    }

    @Test(priority = 300)
    public void checkFilter_createInstance2() {
        result = templatePage.createInstance(title2);
        Result.outContains(result, title2);
    }

    @Test(priority = 310)
    public void checkFilter_checkInstancesAreCreated() {
        String[] names = {title1, title2};
        result = mainPage.getDashboardNames();
        Result.outContains(result, names);
    }

    @Test(priority = 320)
    public void checkFilter_setFilter() {
        String filter = "Cannon";
        result = templatePage.setInstancesFilter(filter);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 330)
    public void checkFilter_getFilteredInstancesList() {
        result = templatePage.getInstances();
        Result.outContains(result, title1);
        Result.outContainsNot(result, title2);
    }

    @Test(priority = 340)
    public void checkFilter_cleanFilter() {
        result = templatePage.cleanInstancesFilter();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 350)
    public void checkFilter_checkFilterCleaned() {
        result = templatePage.getInstances();
        Result.outContains(result, title2);
    }

    @Test(priority = 360)
    public void hideFromMenu_insertAutoTest() {
        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 370)
    public void hideFromMenu_checkDashboardIconPresented() {
        result = templatePage.isDashboardIconPresented(newInstanceTitle);
        Result.rcEquals(result, "0");
        Result.assertEquals(result.get("out"), "true");
    }

    @Test(priority = 380)
    public void hideFromMenu_pressToggleButton() {
        result = templatePage.clickHideFromMenu(newInstanceTitle);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 390)
    public void hideFromMenu_apply() {
        result = templatePage.applyChanges();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 400)
    public void hideFromMenu_checkDashboardIconAbsent() {
        result = templatePage.isDashboardIconPresented(newInstanceTitle);
        Result.assertEquals(result.get("out"), "false");
    }

    @Test(priority = 410)
    public void hideFromMenu_checkDashboardHidden() {
        result = mainPage.getDashboardNames();
        Result.outContainsNot(result, newInstanceTitle);
    }

    @Test(priority = 420)
    public void checkCategory_addNewCategory() {
        result = templatePage.assignNewCategory(category1);
        Result.rcEquals(result, "0");

        result = templatePage.applyChanges();
        Result.rcEquals(result, "0");

        result = templatePage.getInstanceCategories();
        Result.outContains(result, category1);
    }

    @Test(priority = 430)
    public void checkCategory_removeCategory() {

        result = templatePage.removeCategoryFromInstance(newInstanceTitle, category1);
        Result.rcEquals(result, "0");

        result = templatePage.getInstanceCategories();
        Result.outContainsNot(result, category1);
    }

    @Test(priority = 440)
    public void checkCategory_deleteCategory() {
        result = templatePage.deleteCategoryFromInstance(newInstanceTitle, category1);
        Result.rcEquals(result, "0");

        result = client.userWS.getCategoriesNames("permission");
        Result.rcEquals(result, "0");
        Result.outContainsNot(result, category1);
    }

    @Test(priority = 450, enabled = true)
    public void onFailDeleteCategory() {
        if (result.get("out").contains(category1)) {
            result = client.userWS.deleteCategory(category1);
            Result.rcEquals(result, "0");
        }
    }

    @Test(priority = 460)
    public void deleteInstance_enableAutoTest() {
        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 470)
    public void deleteInstance_tryDelete() {
        result = templatePage.clickDeleteInstance(newInstanceTitle);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 480)
    public void deleteInstance_checkDeleteTried() {
        result = templatePage.getInstances();
        Result.outContains(result, "Deleted");
    }

    @Test(priority = 490)
    public void deleteInstance_insertTestAutomation() {
        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 500)
    public void deleteInstance_undoDeleteInstance() {
        result = templatePage.undoDeleteInstance(newInstanceTitle);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 510)
    public void deleteInstance_checkInstanceNotDeleted() {
        result = templatePage.getInstances();
        Result.outContains(result, newInstanceTitle);
    }

    @Test(priority = 520)
    public void deleteInstance_clickDelete() {
        result = templatePage.clickDeleteInstance(newInstanceTitle);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 530)
    public void deleteInstance_checkDeleteClicked() {
        result = templatePage.getInstances();
        Result.outContains(result, "Deleted");
    }

    @Test(priority = 540)
    public void deleteInstance_selectAnotherInstance() {
        result = templatePage.selectInstance(title1);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 550)
    public void deleteInstance_checkInstanceDeleted() {
        result = templatePage.getInstances();
        Result.outContainsNot(result, newInstanceTitle);
    }

    @Test(priority=1000)
    public void logoutcheckfornonadmin()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 1010)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }
    @AfterClass
    public void cleanup() {
        client.templateWS.deleteTemplate(dashboard);

        driver.quit();
    }
}
