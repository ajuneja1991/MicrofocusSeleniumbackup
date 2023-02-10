package com.hp.opr.qa.tests.sys.ui.bvd;



import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DrilldownLink {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private Map<String, String> result;
    private String sWidget = "group792";
    private String sWidgetURL = "group936";
    private String textWidget = "shape917";
    private String textInTextWidget = "New-York";
    private String sDataChannel ;
    private String dataChannel = "dimension1_1 New-York channel_1";

    private String sHyperlinkDataField = "https://#{url}.wikipedia.org";
    private String googleHyperlinkData = "https://www.google.com.ua/?q=#{value10/subvalue1}";


    private String dimsList = "dimension1, value10/subvalue1, channel";
    private String valueData = "value10/subvalue1";

    private String sSourceDashboard = "sample_dashboard_test_url";
    private String sDashboard ;
    private String sDashboardPath ;
    private String dims = "browser";
    private String tags = "Hyperlink";
    private BvdWsClient client;
    private String json;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String userName = "TesterDrilldownlink";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testdrilldownlink";
    private String desc = "For tester";
    private String category = "All";
    private String accessType = "full-control";
    String roleId;
    private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;


    private void initPages() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
    }

    private String get_key() {
        result = client.userWS.getApiKey();
        return result.get("out");
    }

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        sDataChannel = "Hyperlink " + BROWSER;
        sDashboard = sSourceDashboard + "_BVDHyperlink_" + BROWSER;
        sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        initPages();
        try {
            client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 1)
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }
    @Test(priority = 5)
    public void idmlogin() {
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 10)
    public void login_check()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 15)
    public void checknonadminOptionsdropdown() {
        result = mainPage.adminoptionsfornonadmin(admindropdownoptions);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 25)
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30)
    public void sendDataEn() throws JSONException, UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        json = "{\"browser\":\"" + BROWSER + "\"," +
                "\"omiSystem\":\"tcpd123.deu.hp.com\"," +
                "\"data\":\"50\"," +
                "\"url\":\"en\"}";

        result = client.receiverWS.bvdSendData(get_key(), dims, tags, json);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 40)
    public void openManageDashboards() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50)
    public void uploadDashboard() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60)
    public void selectWidget() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70)
    public void setDataChannel() {
        result = editDashboardPage.setDataChannel(sDataChannel);
        Result.rcEquals(result, "0");

        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void setDataField() {
        List<String> lDataField = Collections.singletonList("data");
        result = editDashboardPage.setDataFieldMultiSelect(lDataField);
        Result.rcEquals(result, "0");

        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90)
    public void setVariableHyperlink() {
        result = editDashboardPage.setHyperlinkToUrl(sHyperlinkDataField);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void saveDashboardVariableHyperlink() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void viewDashboardVariableHyperlink() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 115)
    public void checkUploadedHyperlink() {
        result = mainPage.getHyperlinkFromStatusImg(sWidgetURL);
        Result.outContains(result, "abc%26cc");
    }

    @Test(priority = 120)
    public void checkVariableHyperlinkCom() {

        result = mainPage.clickWidget(sWidget);
        Result.rcEquals(result, "0");

        result = mainPage.checkNewTabLink("https://en.wikipedia.org/wiki/Main_Page");
        Result.rcEquals(result, "0");

    }

    @Test(priority = 130)
    public void sendDataDe() throws JSONException, UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        json = "{\"browser\":\"" + BROWSER + "\"," +
                "\"omiSystem\":\"tcpd123.deu.hp.com\"," +
                "\"data\":\"50\"," +
                "\"url\":\"de\"}";

        result = client.receiverWS.bvdSendData(get_key(), dims, tags, json);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 140)
    public void checkVariableHyperlinkDe() {

        result = mainPage.clickWidget(sWidget);
        Result.rcEquals(result, "0");

        result = mainPage.checkNewTabLink("https://de.wikipedia.org/wiki/Wikipedia:Hauptseite");
        Result.rcEquals(result, "0");

    }

    @Test(priority = 141)
    public void sendArrayDataSlashTest() throws JSONException, UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        json = "[{\n" +
                " \"dimension1\" : \"dimension1_1\",\n" +
                " \"dimension2\" : \"dimension2_not_used\",\n" +
                " \"channel\": \"channel_1\", \n" +
                " \"value1\" : 0,\n" +
                " \"value10\" :{\"subvalue1\" : \"New-York\"}\n" +
                "}\n]";

        result = client.receiverWS.bvdSendDataArrayQuery(get_key(), dimsList,
                "",
                json);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 150)
    public void openManageDashboardsForWidgetEditing() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 160)
    public void openDashboard() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void selectTextWidgetGoogle() {
        result = editDashboardPage.selectWidget(textWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void setDataChannelGoogle() {
        result = editDashboardPage.setDataChannel(dataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void setDataFieldGoogle() {
        result = editDashboardPage.setDataField(valueData);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void setGoogleHyperlink() {
        result = editDashboardPage.setHyperlinkToUrl(googleHyperlinkData);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 210)
    public void saveDashboardGoogle() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void viewDashboardGoogle() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 230)
    public void getTextWidgetDataGoogle() {
        result = mainPage.getTextOfTextValueWidget(textWidget);
        Result.outEquals(result, textInTextWidget);
    }

    @Test(priority = 240)
    public void checkVariableHyperlinkGoogle() {
        result = mainPage.clickWidget(textWidget);
        Result.rcEquals(result, "0");

        result = mainPage.checkNewTabLink("https://www.google.com.ua/?q=New-York");
        Result.rcEquals(result, "0");

    }

    @Test(priority = 250)
    public void openManageDashboardsToDelete() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 260)
    public void deleteDashboard() {
        result = manageDashboardsPage.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 270)
    public void testLogout() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 280)
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
