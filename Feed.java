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
import java.util.List;
import java.util.Map;

public class Feed   {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private Map<String, String> result;
    private String sWidget = "shape2";
    private String key;
    private String dims = "browser";
    private String tags = "Feed";
    private BvdWsClient client;
    private String json;
    private String sDataChannel;
    private List<String> lFeeds ;
    private String sFeedMaxItems = "2";
    private List<String> lFeedsMaxFeedItem = Arrays.asList("Tests show UK Quran manuscript is among" +
            " world's oldest - 3rd", "Tests show UK Quran manuscript is among world's oldest - 2nd");
    private Boolean bTransparentBackground = false;
    private String sSourceDashboard = "BaseDashboard";
    private String sDashboard;
    private String sDashboardPath;
    private String userName = "Testerforfeed";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testfeed";
    private String desc = "For tester";
    private String category = "All";
    private String accessType = "full-control";
    String roleId;
    //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;

    private void initPages() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
    }

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        sDataChannel = tags + " " + BROWSER;
        lFeeds = Arrays.asList("Tests show UK Quran manuscript is among world's " +
                "oldest - 3rd", "Tests show UK Quran manuscript is among world's oldest - 2nd", "Tests " +
                "show UK Quran manuscript is among world's oldest");
        sDashboard = sSourceDashboard + "_BVDFeed_" + BROWSER;
        sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";

        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        initPages();
        try {
            //client = new BvdRestClient(PROXY_HOST, PROXY_PORT);
            client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
        } catch (Exception e) {
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

    @Test(priority = 2)
    public void copyTesFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30)
    public void getApiKeyWS() {
        result = client.userWS.getApiKey();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40)
    public void sendData() throws JSONException, UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        key = result.get("out");
        json = "{\"time\":1437633749317," +
                "\"browser\":\"" + BROWSER + "\"," +
                "\"title\":\"Tests show UK Quran manuscript is among world's oldest\"," +
                "\"link\":\"http://rss.cnn.com/~r/rss/cnn_latest/~3/HgufPus_pOs/index.html\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50)
    public void openManageDashboards() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60)
    public void uploadDashboard() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70)
    public void selectWidget() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void setDataChannel() {
        result = editDashboardPage.setDataChannel(sDataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90)
    public void saveDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void sendDataFeeds() throws JSONException, UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        json = "{\"time\":1437633749317," +
                "\"browser\":\"" + BROWSER + "\"," +
                "\"title\":\"Tests show UK Quran manuscript is among world's oldest - 2nd\"," +
                "\"link\":\"http://rss.cnn.com/~r/rss/cnn_latest/~3/HgufPus_pOs/index.html\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");

        json = "{\"time\":1437633749317," +
                "\"browser\":\"" + BROWSER + "\"," +
                "\"title\":\"Tests show UK Quran manuscript is among world's oldest - 3rd\"," +
                "\"link\":\"http://rss.cnn.com/~r/rss/cnn_latest/~3/HgufPus_pOs/index.html\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void viewDashboard() {
        result = mainPage.viewDashboardNew(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void checkFeed() {
        result = mainPage.getFeeds(sWidget);
        Result.outContains(result, lFeeds.toString().replace("[", "").replace("]", ""));

        result = mainPage.getTransparentBackground(sWidget);
        Result.outEquals(result, "0");
    }

    @Test(priority = 130)
    public void openManageDashboardsToSetMaxFeedItems() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 140)
    public void openEditDashboardToSetMaxFeedItems() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150)
    public void selectWidgetToSetMaxFeedItems() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 160)
    public void setMaxFeedItems() {
        result = editDashboardPage.setMaxFeedItems(sFeedMaxItems);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void saveDashboardMaxFeedItems() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void viewDashboardMaxFeedItem() {
        result = mainPage.viewDashboardNew(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void checkFeedsMaxFeedItem() {
        result = mainPage.getFeeds(sWidget);
        Result.outEquals(result, lFeedsMaxFeedItem.toString());

        result = mainPage.getTransparentBackground(sWidget);
        Result.outContains(result, "0");
    }

    @Test(priority = 200)
    public void openManageDashboardsToSetTransparentBackgroundToFalse() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 210)
    public void openEditDashboardToSetTransparentBackgroundToFalse() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void selectWidgetToSetTransparentBackgroundToFalse() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 230)
    public void setTransparentBackgroundToFalse() {
        result = editDashboardPage.setTransparentBackground(bTransparentBackground);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 240)
    public void saveSetTransparentBackgroundToFalse() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 250)
    public void viewSetTransparentBackgroundToFalse() {
        result = mainPage.viewDashboardNew(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 260)
    public void checkTransparentBackgroundToFalse() {
        result = mainPage.getTransparentBackground(sWidget);
        Result.outContains(result, "1");

        result = mainPage.getFeeds(sWidget);
        Result.outEquals(result, lFeedsMaxFeedItem.toString());
    }

    @Test(priority = 270)
    public void openManageDashboardsToDelete() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 280)
    public void deleteDashboard() {
        result = manageDashboardsPage.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 300)
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }

    @Test(priority = 310)
    public void logOutFromAdmin() {
        result = mainPage.logout();
    }


    @Test(priority = 320)
    public void loginAsNewUser() {
        driver.get(URL);
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 330)
    public void checkLogin()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 340,enabled = false)
    public void sendDataForNonAdmin() throws JSONException, UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        key = result.get("out");
        json = "{\"time\":1437633749317," +
                "\"browser\":\"" + BROWSER + "\"," +
                "\"title\":\"Tests show UK Quran manuscript is among world's oldest\"," +
                "\"link\":\"http://rss.cnn.com/~r/rss/cnn_latest/~3/HgufPus_pOs/index.html\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 350)
    public void openManageDashboardsForNonAdmin() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 360)
    public void uploadDashboardForNonAdmin() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 370)
    public void selectWidgetForNonAdmin() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 380)
    public void setDataChannelForNonAdmin() {
        result = editDashboardPage.setDataChannel(sDataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 390)
    public void saveDashboardForNonAdmin() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 400, enabled = false)
    public void sendDataFeedsForNonAdmin() throws JSONException, UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        json = "{\"time\":1437633749317," +
                "\"browser\":\"" + BROWSER + "\"," +
                "\"title\":\"Tests show UK Quran manuscript is among world's oldest - 2nd\"," +
                "\"link\":\"http://rss.cnn.com/~r/rss/cnn_latest/~3/HgufPus_pOs/index.html\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");

        json = "{\"time\":1437633749317," +
                "\"browser\":\"" + BROWSER + "\"," +
                "\"title\":\"Tests show UK Quran manuscript is among world's oldest - 3rd\"," +
                "\"link\":\"http://rss.cnn.com/~r/rss/cnn_latest/~3/HgufPus_pOs/index.html\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 410)
    public void viewDashboardForNonAdmin() {
        result = mainPage.viewDashboardNew(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 420)
    public void checkFeedForNonAdmin() {
        result = mainPage.getFeeds(sWidget);
        Result.outContains(result, lFeeds.toString().replace("[", "").replace("]", ""));

        result = mainPage.getTransparentBackground(sWidget);
        Result.outEquals(result, "0");
    }

    @Test(priority = 430)
    public void openManageDashboardsToSetMaxFeedItemsForNonAdmin() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 440)
    public void openEditDashboardToSetMaxFeedItemsForNonAdmin() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 450)
    public void selectWidgetToSetMaxFeedItemsForNonAdmin() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 460)
    public void setMaxFeedItemsForNonAdmin() {
        result = editDashboardPage.setMaxFeedItems(sFeedMaxItems);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 470)
    public void saveDashboardMaxFeedItemsForNonAdmin() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 480)
    public void viewDashboardMaxFeedItemForNonAdmin() {
        result = mainPage.viewDashboardNew(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 490)
    public void checkFeedsMaxFeedItemForNonAdmin() {
        result = mainPage.getFeeds(sWidget);
        Result.outEquals(result, lFeedsMaxFeedItem.toString());

        result = mainPage.getTransparentBackground(sWidget);
        Result.outContains(result, "0");
    }

    @Test(priority = 500)
    public void openManageDashboardsToDeleteForNonAdmin() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 510)
    public void deleteDashboardForNonAdmin() {
        result = manageDashboardsPage.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 520)
    public void logOutFromUser()
    {
        result = mainPage.logout();
    }

    @Test(priority = 530)
    public void loginIntoAdmin() {
        result = loginPage.login(BVD_USR, BVD_PWD);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 540)
    public void loginCheck()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 550)
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
