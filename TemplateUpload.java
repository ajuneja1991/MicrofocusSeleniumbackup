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
 * Created by Eva Sokolyanskaya on 04/11/2016.
 */
public class TemplateUpload  {
    private Map<String, String> result;
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private TemplatePage templatePage;
    //  private String uploadedTemplate = "uploaded_template";
    private String uploadedTemplate = "large_uploaded_template_assigned";
    private String template ;
    private String templatePath ;
    private WebDriver driver;
    private String widget = "group52";
    private String widget2 = "shape184";
    private String key;
    private String dims = "browser,OS";
    private String tags = "upload_template";
    private BvdWsClient client;
    private String json;
    private String sBackgroundColor = "#DEF016";
    private String instanceOne = "Instance One";
    private String instanceTwo = "Instance Two";
    private String instanceThree = "Instance Three";
    private String variable = "browser";
    private String dataChannel = "upload_template chrome Unix";
    private String dataField = "numberOfCritical";
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String userName = "Testertemplateupload";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testtemplateupload";
    private String desc = "For templateupload";
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
        template = uploadedTemplate + "_" + BROWSER;
        templatePath = "C:\\tmp\\" + template + ".svg";
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
        result = TestUtils.downloadResource(this.getClass(), uploadedTemplate + ".svg", templatePath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 20)
    public void getAPIKey() {
        result = client.userWS.getApiKey();
        Result.rcEquals(result, "0");

        key = result.get("out");
    }

    @Test(priority = 23)
    public void sendDataChrome() throws UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException, JSONException, KeyManagementException {
        json = "{\"browser\":\"chromevalue44\"," +
                "\"OS\":\"Unix\"," +
                "\"numberOfCritical\":10," +
                "\"numberOfMajor\":20," +
                "\"numberOfNormal\":30}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 25)
    public void sendDataFirefox() throws UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException, JSONException, KeyManagementException {
        json = "{\"browser\":\"firefoxvalue44\"," +
                "\"OS\":\"Unix\"," +
                "\"numberOfCritical\":15," +
                "\"numberOfMajor\":25," +
                "\"numberOfNormal\":35}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30)
    public void uploadTemplate() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.uploadTemplate(templatePath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 42)
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }


    @Test(priority = 55)
    public void checkTemplate_instances() {
        result = templatePage.getInstances();
        String[] expected = {instanceOne, instanceTwo};
        Result.rcEquals(result, "0");
        Result.outContains(result, expected);
    }

    @Test(priority = 60)
    public void checkTemplate_variableValues() {

        result = templatePage.selectInstance(instanceOne);
        Result.rcEquals(result, "0");

        result = templatePage.getVariableValue(variable);
        Result.rcEquals(result, "0");
        Result.assertEquals(result.get("out"), "chromevalue44");

        result = templatePage.selectInstance(instanceTwo);
        Result.rcEquals(result, "0");

        result = templatePage.getVariableValue(variable);
        Result.rcEquals(result, "0");
        Result.assertEquals(result.get("out"), "firefoxvalue44");
    }

    @Test(priority = 90)
    public void checkInstanceOne_Open() {
        result = mainPage.viewDashboardforHyperlinkfortemplate(instanceOne);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100, enabled = true)
    public void checkBackground() {
        result = mainPage.getBackgroundColor();
        Result.assertEquals(result.get("out"), sBackgroundColor);
    }

    @Test(priority = 110)
    public void checkInstanceOne_ChartNumbers() {
        result = mainPage.getChartNumbers(widget);
        Result.outEquals(result, "[10, 20, 30]");
    }

    @Test(priority = 120)
    public void checkInstanceTwo_Open() {
        result = mainPage.viewDashboardforHyperlinkfortemplate(instanceTwo);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void checkInstanceTwo_ChartNumbers() {
        result = mainPage.getChartNumbers(widget);
        Result.outEquals(result, "[15, 25, 35]");
    }

    @Test(priority = 140)
    public void editTemplate_open() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = templatePage.insertTestAutomation();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.openTemplatePage(template);
        Result.rcEquals(result, "0");

        result = templatePage.goToEditTemplate(template);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150)
    public void editTemplate_setNewWidget() {
        result = editDashboardPage.selectWidget(widget2);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataChannel(dataChannel);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataField(dataField);
        Result.rcEquals(result, "0");

        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }


    @Test(priority=161)
    public void logoutcheck()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }



    @Test(priority = 162)
    public void testLoginnonadmin()
    {
        driver.get(URL);
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 165)
    public void login_checkfornonadmin()
    {
        result = loginPage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");
    }



    @Test(priority = 170)
    public void editTemplate_checkChanges() {
        result = mainPage.viewDashboardforHyperlinkfortemplate(instanceOne);
        Result.rcEquals(result, "0");

        result = mainPage.getTextOfTextValueWidget(widget2);
        Result.rcEquals(result, "0");
        Result.outEquals(result, "10");
    }

    @Test(priority = 175)
    public void addInstanceToTemplate() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.openTemplatePage(template);
        Result.rcEquals(result, "0");

        result = templatePage.createInstance(instanceThree);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180, enabled = true)
    public void updateSvg() {
        result = templatePage.goToEditTemplate(template);
        Result.rcEquals(result, "0");
    
        result = editDashboardPage.replaceDashboard(templatePath);
        Result.rcEquals(result, "0");

        result = editDashboardPage.replaceTemplateSvg();
        Result.rcEquals(result, "0");

        result = editDashboardPage.cancelTemplate();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190, enabled = true)
    public void checkUpdatedSvg() {
        result = templatePage.getInstances();
        Result.rcEquals(result, "0");

        String[] expected = {instanceOne, instanceTwo, instanceThree};
        Result.outContains(result, expected);
    }

    @Test(priority = 250, enabled = true)
    public void updateTemplate() {
        result = templatePage.goToEditTemplate(template);
        Result.rcEquals(result, "0");

        result = editDashboardPage.replaceTemplateAll(templatePath);
        Result.rcEquals(result, "0");

        result = editDashboardPage.cancelTemplate();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 260, enabled = true)
    public void checkUpdatedTemplate() {
        result = templatePage.getInstances();
        Result.rcEquals(result, "0");

        String[] expected = {instanceOne, instanceTwo};
        Result.outContains(result, expected);
        Result.outContainsNot(result, instanceThree);
    }


    @Test(priority=270)
    public void logoutcheckfornonadmin()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 280)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 997, enabled = true)
    public void deleteTemplate() {
        client.templateWS.deleteTemplate(template);
        Result.rcEquals(result, "0");
    }

    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}

