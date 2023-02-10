package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.*;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
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

public class InstallDefaultVerticaTest {

    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private DataCollectorPage dataCollectorPage;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private WebDriver driver;
    private Map<String, String> result;
    BvdWsClient client;

    private List<String> verticaDBconnectionDetails;
    private void initPages()
    {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        dataCollectorPage = PageFactory.initElements(driver, DataCollectorPage.class);

    }

    @BeforeClass
    public void setup()
    {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        driver = UiTestWebDriver.getWebDriver();
        verticaDBconnectionDetails = Arrays.asList(cfg.getString("application.bvd.vertica.hostname"),
                cfg.getString("application.bvd.vertica.port"),cfg.getString("application.bvd.vertica.dbname"),
                cfg.getString("application.bvd.vertica.username"),cfg.getString("application.bvd.vertica.tlscheck"));
        driver.get(URL);
        initPages();
        try
        {
            client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test(priority = 10)
    public void idmlogin() {
        result = loginPage.login(BVD_USR, BVD_PWD);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 20)
    public void login_check()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 30)
    public void openDataCollectorApplication()
    {
        result = mainPage.openDataCollectors();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 40)
    public void testTLSConnection()
    {
        Assert.assertTrue(dataCollectorPage.isDBSet());

        result = dataCollectorPage.viewDBSettings();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.enableTLSCheckBox();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickDBcancel();
        Result.rcEquals(result, "0");

    }

    @Test(priority = 50)
    public void openDBSettingsforTest()
    {
        TestUtils.sleep(4);
        result = dataCollectorPage.viewDBSettings();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 60)
    public void validateDBConnection()
    {
        result = dataCollectorPage.readDBSettingsFields(verticaDBconnectionDetails);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.testDBConnection();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.saveDBSettings();
        Result.rcEquals(result, "0");

    }

    @AfterClass
    public void cleanup() {

        driver.quit();
    }
}



