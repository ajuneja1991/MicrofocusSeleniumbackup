package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.Driver;
import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.SystemSettingsPage;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

public class RequestNewAPIKey  {
  private LoginPage loginPage;
  private MainPage mainPage;
  private SystemSettingsPage systemSettingsPage;
  private WebDriver driver;
  private Map<String, String> result;
  private String oldKey;
  private String newKey;
  private BvdWsClient client;
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;

  WebDriver driver2;

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
        client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
    }
  @Test(priority = 0)
  public void idmlogin() {
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");
  }
  @Test(priority = 1)
  public void login_check() {
    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
  }

  
    @Test(priority = 20)
    public void openSystemSettings() {
        result = mainPage.openApiKey();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30)
    public void getAPIKey() {
        result = systemSettingsPage.getAPIKey();
        oldKey = result.get("out");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40)
    public void requestNewAPIKey() {
        result = systemSettingsPage.requestNewAPIKey();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50)
    public void getNewAPIKey() {
        result = systemSettingsPage.getAPIKey();
        newKey = result.get("out");
        Result.outContainsNot(result, oldKey);
    }

  @Test(priority = 60)
  public void anotherBrowser_Open() {

    driver2 = UiTestWebDriver.getWebDriver();
    driver2.get(URL);

    loginPage = PageFactory.initElements(driver2, LoginPage.class);
    result = loginPage.login(BVD_USR, BVD_PWD);
    Result.rcEquals(result, "0");

    result = loginPage.idm_LoggedIn_Check();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 70)
  public void anotherBrowser_checkApiKey() {
    mainPage = PageFactory.initElements(driver2, MainPage.class);

    result = mainPage.openApiKey();
    Result.rcEquals(result, "0");

    systemSettingsPage = PageFactory.initElements(driver2, SystemSettingsPage.class);

    result = systemSettingsPage.getAPIKey();
    Result.rcEquals(result, "0");
    Result.outEquals(result, newKey);
  }

  @Test(priority = 80)
  public void anotherBrowser_close() {
    driver2.quit();
  }

    @Test(priority = 90)
    public void sendData_NegativeCase() throws JSONException, UnrecoverableKeyException,
          NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String key = oldKey;
        String dims = "viewName,ciName,kpiName";
        String tags = "omi,kpi";
        String json = "{\"omiSystem\":\"tcpd123.deu.hp.com\",\"viewName\":\"OprSample\",\"ciId\":\"c51a039f38fe4b1db16d23181ce869c2\",\"ciName\":\"Gold ESS\",\"ciType\":\"BusinessService\",\"kpiName\":\"System Performance\",\"kpiId\":1002,\"status\":20}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "403");
        Result.errContains(result, "The API key " + oldKey + " does not exist.");
    }

  @Test(priority = 95)
  public void sendData() throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    String key = newKey;
    String dims = "viewName,ciName,kpiName";
    String tags = "omi,kpi";
    String json = "{\"omiSystem\":\"tcpd123.deu.hp.com\",\"viewName\":\"OprSample\",\"ciId\":\"c51a039f38fe4b1db16d23181ce869c2\",\"ciName\":\"Gold ESS\",\"ciType\":\"BusinessService\",\"kpiName\":\"System Performance\",\"kpiId\":1002,\"status\":20}";

    result = client.receiverWS.bvdSendData(key, dims, tags, json);
    Result.rcEquals(result, "0");
  }

    @AfterClass
    public void cleanup() {
        driver.quit();
    }


  private WebDriver getFirefoxDriver() {

    WebDriver m_webDriver = null;
    FirefoxProfile profile = new FirefoxProfile();
    FirefoxOptions options;
    profile.setPreference("intl.accept_languages", cfg.getString("environment.browser.locale"));
    if (System.getProperty("os.name").toLowerCase().contains("win")) {
      profile.setPreference("browser.download.dir", "C:\\tmp");
    } else {
      profile.setPreference("browser.download.dir", "/tmp");
    }

    profile.setPreference("browser.download.folderList", 2);
    profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "image/svg+xml,application/download");
    profile.setPreference("plugin.state.flash", 2);
    String trgDir = "/tmp/selenium-driver";
    String tool = "geckodriver.exe";
    if (System.getProperty("os.name").toLowerCase().contains("inux")) {
      tool = "geckodriver";
    }

    try {
      if (Files.notExists(Paths.get(trgDir), new LinkOption[0])) {
        Files.createDirectories(Paths.get(trgDir));
      }

      Files.deleteIfExists(Paths.get(trgDir + "/" + tool));
      java.io.File file = new File(trgDir + "/" + tool);
      InputStream res = Driver.class.getResourceAsStream(tool);
      Files.copy(res, file.getAbsoluteFile().toPath(), new CopyOption[0]);
      if (System.getProperty("os.name").toLowerCase().contains("inux")) {
        file.setExecutable(true);
        file.setReadable(true);
      }
    } catch (IOException var9) {
      var9.printStackTrace();
    }

    System.setProperty("webdriver.gecko.driver", trgDir + "/" + tool);
    options = new FirefoxOptions();
    options.setCapability("marionette", true);
    options.setProfile(profile);
    m_webDriver = new FirefoxDriver(options);
    m_webDriver.manage().window().maximize();
    return  m_webDriver;
  }
}
