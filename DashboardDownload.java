package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.SystemInfo;
import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.common.utils.Rmi2HttpClient;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.apache.commons.io.IOUtils;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;
import java.util.Map;

public class DashboardDownload {
  private LoginPage loginPage;
  private MainPage mainPage;
  private ManageDashboardsPage manageDashboardsPage;
  private EditDashboardPage editDashboardPage;
  private WebDriver driver;
  private Map<String, String> result;
  private String sSourceDashboard = "sample_dashboard_test_791";
  private String sDashboard = "";
  private String sDashboardPath = "";
  private String sWidget = "group791";
  private String sDataChannel = "";
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
  private String key;
  private String dims = "browser";
  private String tags = "DashboardDownload";
  private BvdWsClient client;
  private String json;
    private static String OS="";



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
        OS = System.getProperty("os.name").toLowerCase();
        sDashboard = sSourceDashboard + "_" + BROWSER;
        sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
        sDataChannel = "DashboardDownload " + BROWSER;

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
    public void copyTestfiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg",  sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 10)
    public void testLogin() {
        result = loginPage.login(BVD_USR, BVD_PWD);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 20)
    public void testOpenManageDashboards() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30)
    public void uploadDashboard() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40)
    public void saveUploadedDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50)
    public void deleteDashboardFile() throws IOException {
        // dashboard file path
        File file;

        if(OS.contains("win")){
            file = new File("C:\\tmp\\" + sDashboard + ".svg");
        } else {
            file = new File("/tmp/" + sDashboard + ".svg");
        }

        // delete file
        try {
            file.delete();
            result = ResultStore.success("File successfully deleted");

        } catch (Exception exc) {
            result = ResultStore.failure("Failed to delete Dashboard " + file.toString() + ". error: " + Misc.getStacktraceAsString(exc));
        }

        Result.rcEquals(result, "0");
    }

    @Test(priority = 60)
    public void viewDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70)
    public void openManageDashboardsToDownload() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void downloadDashboard() {
        result = manageDashboardsPage.downloadDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90, enabled = false)
    public void checkDashboard() throws IOException {
        TestUtils.sleepAndLog(6000);
        URL filePathDownload;

        //downloaded dashboard
        if(OS.contains("win")){
            filePathDownload = new URL("file:/C:/tmp/" + sDashboard + ".svg");
        } else {
            filePathDownload = new URL("file:/tmp/" + sDashboard + ".svg");
        }
        String sFileDownloadedContent = new String(IOUtils.toByteArray(filePathDownload), "UTF-8");
        String sChangedDataChannel = "<g id=\"group791\" transform=\"translate(609.847,-351.616)\" opr_item_type=\"opr_update_visibility\" opr_channel=\"omi&#60;&#62;mdb&#60;&#62;OMi Health Status&#60;&#62;Overall&#60;&#62;PIE\" opr_dashboard_item=\"1\">";

        // string compare
        if (sFileDownloadedContent.contains(sChangedDataChannel)) {
            result = ResultStore.success("Download File contains uploaded Data Channel");
        } else {
            result = ResultStore.failure("Downloaded File doesn't contain uploaded Data Channel.");
        }
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void deleteDownloadedDashboardFile() throws IOException {
        // dashboard file path
        File file;

        if(OS.contains("win")){
            file = new File("C:\\tmp\\" + sDashboard + ".svg");
        } else {
            file = new File("/tmp/" + sDashboard + ".svg");
        }

        // delete file
        try {
            file.delete();
            result = ResultStore.success("File successfully deleted");

        } catch (Exception exc) {
            result = ResultStore.failure("Failed to delete Dashboard " + file.toString() + ". error: " + Misc.getStacktraceAsString(exc));
        }

        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void getApiKeyWS() {
        result = client.userWS.getApiKey();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void sendData() throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        key = result.get("out");
        json = "{\"browser\":\"" + BROWSER + "\"," +
                "\"omiSystem\":\"tcpd123.deu.hp.com\"," +
                "\"viewName\":\"OprSample\"," +
                "\"ciId\":\"c51a039f38fe4b1db16d23181ce869c2\"," +
                "\"ciName\":\"Gold ESS\"," +
                "\"ciType\":\"BusinessService\"," +
                "\"kpiName\":\"System Performance\"," +
                "\"kpiId\":1002,\"status\":20}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 140)
    public void openManageDashboardsToEdit() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150)
    public void openEditDashboard() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 160)
    public void selectWidget() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void setDataChannel() {
        result = editDashboardPage.setDataChannel(sDataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void saveChanges() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void downloadChangedDashboard() {
        result = manageDashboardsPage.downloadDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void checkDownloadedDashboard() throws IOException {

        TestUtils.sleepAndLog(6000);
        URL filePathDownload;

        //downloaded changed dashboard
        if(OS.contains("win")){
            filePathDownload = new URL("file:/C:/tmp/" + sDashboard + ".svg");
        } else {
            filePathDownload = new URL("file:/tmp/" + sDashboard + ".svg");
        }
        String sFileDownloadedContent = new String(IOUtils.toByteArray(filePathDownload), "UTF-8");
        String sChangedDataChannel = "<g id=\"group791\" transform=\"translate(609.847,-351.616)\" opr_item_type=\"opr_update_visibility\" opr_channel=\"DashboardDownload&lt;&gt;" + BROWSER + "\" opr_dashboard_item=\"1\">";

        // string compare
        if (sFileDownloadedContent.contains(sChangedDataChannel)) {
            result = ResultStore.success("Downloaded File contains changed Data Channel");
        } else {
            result = ResultStore.failure("Downloaded File doesn't contain changed Data Channel");
        }
        Result.rcEquals(result, "0");
    }

    @Test(priority = 210)
    public void deleteChangedDashboardFile() throws IOException {
        // dashboard file path
        File file;

        if(OS.contains("win")){
            file = new File("C:\\tmp\\" + sDashboard + ".svg");
        } else {
            file = new File("/tmp/" + sDashboard + ".svg");
        }

        // delete file
        try {
            file.delete();
            result = ResultStore.success("File successfully deleted");

        } catch (Exception exc) {
            result = ResultStore.failure("Failed to delete Dashboard " + file.toString() + ". error: " + Misc.getStacktraceAsString(exc));
        }

        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void deleteDashboard() {
        result = manageDashboardsPage.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }


    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}