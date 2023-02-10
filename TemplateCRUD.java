package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.*;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.hp.opr.qa.framework.utils.UiTestUtils;
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
 * Created by Eva Sokolyanskaya on 05/07/2016.
 * BVD - CRUD Templates
 */
public class TemplateCRUD  {
    private Map<String, String> result;
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private TemplatePage templatePage;
    private String sourceDashboard = "sample_dashboard_test";
    private String dashboard ;
    private String dashboardNew;
    private String dashboardPath ;
    private WebDriver driver;
    private String widget = "group792";
    private String dataChannel ;
    private String key;
    private String dims = "browser,OS";
    private String tags = "template";
    private BvdWsClient client;
    private String json;
    private String linkToDashboard;
    private String autoCreatedInstanceName;
    private String newInstance = "someInstance";
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;

    private String userName = "Testertemplatecrud";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testtemplatecrud";
    private String desc = "For templatecrud";
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
        dashboard = sourceDashboard + "_BVD_Template_" + BROWSER;
        dashboardPath = "C:\\tmp\\" + dashboard + ".svg";
        dataChannel = "template " + BROWSER + " Unix";
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
        json = "{\"browser\":\"" + BROWSER + "\"," +
                "\"OS\":\"Unix\"," +
                "\"omiSystem\":\"" + URL + "\"," +
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

    @Test(priority = 42)
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
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

    @Test(priority=101)
    public void logoutcheck()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }



    @Test(priority = 102)
    public void testLoginnonadmin()
    {
        driver.get(URL);
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 105)
    public void login_checkfornonadmin()
    {
        result = loginPage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");
    }



    @Test(priority = 110)
    public void createTemplate_openManageDashboards() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void createTemplate_checkTemplateIsCreated() {
        result = manageDashboardsPage.checkDashboardIsTemplate(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void createTemplate_getAutoCreatedInstanceName() {
        result = templatePage.getAutoCreatedInstanceName(dashboard);
        Result.rcEquals(result, "0");

        autoCreatedInstanceName = result.get("out");
    }

    @Test(priority = 140)
    public void createTemplate_checkAutoCreatedInstance() {
        result = mainPage.getDashboardNames();
        Result.outContains(result, autoCreatedInstanceName);
    }

    @Test(priority = 150)
    public void checkTemplate_insertAutoTest() {
        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 160)
    public void checkTemplate_openTemplatePage() {
        result = manageDashboardsPage.openTemplatePage(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void checkVariables_selectAutoCreatedInstance() {
        result = templatePage.selectInstance(autoCreatedInstanceName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void checkVariables_getVariablesFromTemplate() {
        result = templatePage.getVariables();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void checkVariables() {
        String expected = "Value from template";
        String[] actual = result.get("out").split(";");
        Result.assertEquals(actual.length, 1);

        for (String str : actual) {
            Result.assertEquals(str, expected);
        }
    }

    @Test(priority = 210)
    public void checkDownloadLink() {
        String expected = (mainPage.getBvdUrlForDownload().get("out") + "/ui/dashboard-download/"
                + dashboard).toLowerCase();
        result = templatePage.getDownloadLink(dashboard);
        Result.assertEquals(result.get("out").toLowerCase(), expected);
    }

    @Test(priority = 220)
    public void changeTitle_goToEditTemplate() {
        result = templatePage.goToEditTemplate(dashboard);
        Result.rcEquals(result, "0");

        UiTestUtils.waitPageIsLoaded(driver, 15);
    }

    @Test(priority = 230)
    public void changeTitle_changeTemplateTitle() {
        dashboardNew = dashboard + "_new";
        result = editDashboardPage.changeTitle(dashboardNew);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 240)
    public void addDimension_selectWidget() {
        result = editDashboardPage.selectWidget(widget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 250)
    public void addDimension_setAdditionalDimension() {
        result = editDashboardPage.clickOnDimension("2");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 270)
    public void applyChanges() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");

        result = editDashboardPage.cancelTemplate();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 280)
    public void checkTemplateTitleIsChanged() {
        result = templatePage.getTemplateTitle();
        Result.assertEquals(result.get("out"), dashboardNew);
    }

    @Test(priority = 290)
    public void getNewAutoCreatedInstanceName() {
        result = templatePage.getAutoCreatedInstanceName(dashboard);
        Result.rcEquals(result, "0");

        autoCreatedInstanceName = result.get("out");
    }

    @Test(priority = 300)
    public void checkAddedVariables_selectAutoCreatedInstance() {
        result = templatePage.selectInstance(autoCreatedInstanceName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 310)
    public void checkAddedVariables_getAdditionalVariablesFromTemplate() {
        result = templatePage.getVariables();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 320)
    public void checkAddedVariables_checkAdditionalVariablesFromTemplate() {
        String expected = "Value from template";
        String[] actual = result.get("out").split(";");
        for (String str : actual) {
            Result.assertEquals(str, expected);
        }
    }

    @Test(priority = 330)
    public void removeAdditionalDimension_goToEditTemplatePage() {
        result = templatePage.goToEditTemplate(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 340)
    public void removeAdditionalDimension_selectWidget() {
        result = editDashboardPage.selectWidget(widget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 350)
    public void removeAdditionalDimension() {
        result = editDashboardPage.clickOnDimension("2");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 360)
    public void removeAdditionalDimension_returnToTemplatePage() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");

        result = editDashboardPage.cancelTemplate();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 370)
    public void removeAdditionalDimension_selectAutoCreatedInstance() {
        result = templatePage.selectInstance(autoCreatedInstanceName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 380)
    public void removeAdditionalDimension_getListOfVariables() {
        result = templatePage.getVariables();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 390)
    public void removeAdditionalDimension_checkListOfVariables() {
        String[] actual = result.get("out").split(";");
        Result.assertEquals(actual.length, 1);

        String expected = "Value from template";
        Result.assertEquals(actual[0], expected);
    }

    @Test(priority = 400)
    public void removeLastDimension_goToEditTemplatePage() {
        result = templatePage.goToEditTemplate(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 410)
    public void removeLastDimension_selectWidget() {
        result = editDashboardPage.selectWidget(widget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 420)
    public void removeLastDimension_removeDimension() {
        result = editDashboardPage.clickOnDimension("1");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 430)
    public void removeLastDimension_confirmConvertToDashboard() {
        result = editDashboardPage.saveNewTemplate();
        Result.rcEquals(result, "0");
    }

    //TODO: check if it works correctly
    @Test(priority = 440)
    public void removeLastDimension_checkTemplateBecameDashboard() {
        result = manageDashboardsPage.checkDashboardIsNotTemplate(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 450)
    public void createTemplateToCheckHyperlink_openEditDashboard() {
        result = manageDashboardsPage.openEditDashboard(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 460)
    public void createTemplateToCheckHyperlink_selectWidget() {
        result = editDashboardPage.selectWidget(widget);
        Result.rcEquals(result, "0");
    }



    @Test(priority = 470)
    public void createTemplateToCheckHyperlink_setDataChannel() {
        result = editDashboardPage.modifyDataChannelSetNewChannel(dataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 480)
    public void createTemplateToCheckHyperlink_convertToTemplate() {
        result = editDashboardPage.convertToTemplate();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 490)
    public void createTemplateToCheckHyperlink_openManageDashboardsToCheckTemplate() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 500)
    public void createTemplateToCheckHyperlink_checkTemplateIsCreated() {
        result = manageDashboardsPage.checkDashboardIsTemplate(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 510)
    public void createTemplateToCheckHyperlink_openTemplatePage() {
        result = manageDashboardsPage.openTemplatePage(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 520)
    public void createTemplateToCheckHyperlink_createNewInstance() {
        result = templatePage.createInstance(newInstance);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 530)
    public void checkHyperlink_goToEditTemplate() {
        result = templatePage.goToEditTemplate(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 540)
    public void checkHyperlink_selectWidget() {
        result = editDashboardPage.selectWidget(widget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 545)
    public void setDataField() {
        result = editDashboardPage.setDataField("numberOfCritical");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 550)
    public void checkHyperlink_setHyperlinkToAnotherDashboard() {
        linkToDashboard = "Welcome";
        result = editDashboardPage.setHyperlinkToAnotherDashboard(linkToDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 560)
    public void checkHyperlink_saveDashboardHyperlinkToAnotherDashboard() {
        result = editDashboardPage.saveEditedTemplate();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 570)
    public void createTemplate_getNewAutoCreatedInstance() {
        result = templatePage.getAutoCreatedInstanceName(dashboardNew);
        Result.rcEquals(result, "0");

        autoCreatedInstanceName = result.get("out");
    }

    @Test(priority = 580, enabled = true)
    public void checkHyperlink_viewDashboardHyperlinkToAnotherDashboard() {
        result = mainPage.viewDashboardforHyperlinkfortemplate(newInstance);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 610, enabled = true)
    public void checkHyperlink_clickOnWidget() {
        result = mainPage.clickHyperlink(widget,"[y*='49']");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 620, enabled = true)
    public void checkHyperlink_checkAnotherDashboardOpened() {
        result = mainPage.getDashboard();
        Result.rcEquals(result, "0");
        Result.outContains(result, linkToDashboard);
    }

    @Test(priority = 630)
    public void removeInstance_openTemplatePage() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.openTemplatePage(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 640)
    public void removeInstance_insertTestAutomation() {
        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 650)
    public void removeInstance_clickOnDeleteButton1() {
        result = templatePage.clickDeleteInstance(autoCreatedInstanceName);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 660)
    public void removeInstance_clickOnDeleteButton2() {
        result = templatePage.clickDeleteInstance(newInstance);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 670)
    public void removeInstance_applyConvertTemplateToDashboard() {
        result = templatePage.applyConvertTemplateToDashboard();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 680)
    public void removeInstance_checkTemplateBecameDashboard() {
        result = manageDashboardsPage.checkDashboardIsNotTemplate(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 690)
    public void createTemplateToDelete_openEditDashboard() {
        result = manageDashboardsPage.openEditDashboard(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 700)
    public void createTemplateToDelete_selectWidget() {
        result = editDashboardPage.selectWidget(widget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 710)
    public void createTemplateToDelete_setDataChannel() {
        result = editDashboardPage.modifyDataChannelSetNewChannel(dataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 720)
    public void createTemplateToDelete_convertToTemplate() {
        result = editDashboardPage.convertToTemplate();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 730)
    public void createTemplateToDelete_openManageDashboardsToCheckTemplate() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 740)
    public void createTemplateToDelete_checkTemplateIsCreated() {
        result = manageDashboardsPage.checkDashboardIsTemplate(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 750)
    public void createTemplateToDelete_openTemplatePage() {
        result = manageDashboardsPage.openTemplatePage(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 996, enabled = true)
    public void deleteTemplate() {
        result = templatePage.deleteTemplate(dashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 997, enabled = true)
    public void onFailDeleteTemplateUsingWs() {
        if (!result.get("rc").equalsIgnoreCase("0")) {
            result = client.templateWS.deleteTemplate(dashboard);
            Result.rcEquals(result, "0");
        }
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
        driver.quit();
    }
}
