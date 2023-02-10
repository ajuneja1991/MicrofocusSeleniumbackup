package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.common.omi.OmiCmd;
import com.hp.opr.qa.framework.common.utils.Rmi2HttpClient;
import com.hp.opr.qa.framework.omirestws.OmiRestClient;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.SystemSettingsPage;
import com.hp.opr.qa.framework.runner.Configuration;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.rmi.RemoteException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

public class CreateConnectedServerTypeBVD {
  private Map<String, String> result;
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
  private Rmi2HttpClient dps;
  private LoginPage loginPage;
  private MainPage mainPage;
  private SystemSettingsPage systemSettingsPage;
  private WebDriver driver;
  private String name = "bvd";
  private String dns;
  private String connectedServerId;
  private String connectedServerActive = "true";
  private String port;
  private String ssl;
  private String apiKey;
  private String OMI_USER;
  private String OMI_PWD;

  private OmiRestClient client;
  private String xml;
  private String ruleName = "EventFwdRule";
  private String ruleDescription = "bla";
  private String ruleActive = "true";
  private String ruleId;
  private String dashboardId = "aee87b33-5aac-43eb-b992-78eec1167121";

    private void initPages() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        systemSettingsPage = PageFactory.initElements(driver, SystemSettingsPage.class);
    }

    @BeforeClass
    public void setup() {
        //super.setup(true);
        //driver = getWebDriver();
        //driver.get(EXT_BVD_URL);
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        OMI_USER =cfg.getString("application.obm.user.adminusr");
        OMI_PWD=cfg.getString("application.obm.user.adminpwd");
        dps=new Rmi2HttpClient(cfg.getString("environment.obm.rmihost.dps"));
        client = new OmiRestClient(OMI_USER, OMI_PWD);
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        initPages();
    }

    @Test(priority = 10, enabled = true)
    public void testLogin() {
        result = loginPage.login(BVD_USR, BVD_PWD);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 20, enabled = true)
    public void openSystemSettings() {
        result = mainPage.openSystemSettings();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30, enabled = true)
    public void getAPIKey() {
        result = systemSettingsPage.getAPIKey();
        apiKey = result.get("out");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40, enabled = true)
    public void logout() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50, enabled = true)
    public void createConnectedServer() throws RemoteException {
        getConfig();
        String args[] = {"-add", "-type", "BVD",
                "-label", name,
                "-name", name,
                "-dns", dns,
                "-active", connectedServerActive,
                "-integrationport", port,
                "-issl", ssl,
                "-username", BVD_USR,
                "-password", BVD_PWD,
                "-wsrooturl", "api/submit/" + apiKey};
        result = dps.proxy(OmiCmd.class).ConnectedServer(args);
        //result = DPS_OMI_CMD.ConnectedServer(args);
        Result.rcEquals(result, "0");

        connectedServerId = result.get("out").substring(result.get("out").indexOf("[") + 1, result.get("out").indexOf("]"));
    }

    @Test(priority = 60)
    public void checkConnectedServer() throws RemoteException {
        String args[] = {"-list", "-username", OMI_USER, "-password", OMI_PWD};
        result = dps.proxy(OmiCmd.class).ConnectedServer(args);
        //result = DPS_OMI_CMD.ConnectedServer(args);
        Result.outContains(result, name);
    }

    @Test(priority = 70)
    public void createForwardingRule() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        xml = "<bvd_forwarding_rule_configuration xmlns = \"http://www.hp.com/2009/software/opr/data_model\" xmlns:xs = \"http://www.w3.org/2001/XMLSchema\">" +
                "<label>" + ruleName + "</label>" +
                "<description>" + ruleDescription + "</description>" +
                "<active>" + ruleActive + "</active>" +
                "<data_list/>" +
                "<event_dashboard_forwarding_data type=\"urn:x-hp:2009:software:data_model:type:event_dashboard_forwarding_data\" version=\"1.0\">"+
                "<dashboard_id>" + dashboardId + "</dashboard_id>" +
                "</event_dashboard_forwarding_data>" +
                "<target_list>" +
                "<connected_server self = \"" + URL + "/opr-web/admin/rest/connectedServer/" + connectedServerId +"\" relationships_included = \"true\">" +
                "<id>" + connectedServerId + "</id>" +
                "<version>1</version>" +
                "<name>" + name + "</name>" +
                "<label>" + name + "</label>" +
                "<active>" + connectedServerActive + "</active>" +
                "<server_type>bvd</server_type>" +
                "<configuration_item type = \"urn:x-hp:2009:software:data_model:type:configuration_item\" version = \"1.2\">" +
                "<id>0deeabb5b3124e28b8fe5be9d2457665</id>" +
                "<type>business_value_dashboard_system</type>" +
                "</configuration_item>" +
                "<dns_name>dns</dns_name>" +
                "<root_url_path>api/submit/" + apiKey + "</root_url_path>" +
                "<artifact_origin>custom</artifact_origin>" +
                "</connected_server>" +
                "</target_list>" +
                "<artifact_origin>custom</artifact_origin>" +
                "</bvd_forwarding_rule>";


        result = client.bvdForwardingRule.create(xml);
        Result.rcEquals(result, "0");

        result = client.bvdForwardingRule.getID(ruleName);
        Result.rcEquals(result, "0");
        ruleId = result.get("out");
    }

    @Test(priority = 80)
    public void updateForwardingRule() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        xml = "<bvd_forwarding_rule_configuration xmlns = \"http://www.hp.com/2009/software/opr/data_model\" xmlns:xs = \"http://www.w3.org/2001/XMLSchema\">" +
                "<label>" + ruleName + "</label>" +
                "<description>" + ruleDescription + "</description>" +
                "<active>" + ruleActive + "</active>" +
                "<data_list/>" +
                "<event_dashboard_forwarding_data type=\"urn:x-hp:2009:software:data_model:type:event_dashboard_forwarding_data\" version=\"1.0\">"+
                "<dashboard_id>" + dashboardId + "</dashboard_id>" +
                "</event_dashboard_forwarding_data>" +
                "<target_list>" +
                "<connected_server self = \"" + URL + "/opr-web/admin/rest/connectedServer/" + connectedServerId +"\" relationships_included = \"true\">" +
                "<id>" + connectedServerId + "</id>" +
                "<version>1</version>" +
                "<name>" + name + "</name>" +
                "<label>" + name + "</label>" +
                "<active>" + connectedServerActive + "</active>" +
                "<server_type>bvd</server_type>" +
                "<configuration_item type = \"urn:x-hp:2009:software:data_model:type:configuration_item\" version = \"1.2\">" +
                "<id>0deeabb5b3124e28b8fe5be9d2457665</id>" +
                "<type>business_value_dashboard_system</type>" +
                "</configuration_item>" +
                "<dns_name>dns</dns_name>" +
                "<root_url_path>api/submit/" + apiKey + "</root_url_path>" +
                "<artifact_origin>custom</artifact_origin>" +
                "</connected_server>" +
                "</target_list>" +
                "<artifact_origin>custom</artifact_origin>" +
                "</bvd_forwarding_rule>";


        result = client.bvdForwardingRule.update(ruleId, xml);
        Result.rcEquals(result, "0");

        result = client.bvdForwardingRule.getID(ruleName);
        Result.rcEquals(result, "0");
        ruleId = result.get("out");
    }

    @AfterClass
    public void cleanup() throws RemoteException {
        client.bvdForwardingRule.deleteById(ruleId);
        String args[] = {"-username", OMI_USER, "-password", OMI_PWD, "-delete", connectedServerId};
        result = dps.proxy(OmiCmd.class).ConnectedServer(args);
        //result = dp.ConnectedServer(args);
        driver.quit();
    }

    private void getConfig() {
        String configFile = "config.properties";
        if (System.getProperty("configFile") != null) {
            configFile = System.getProperty("configFile");
        } else if (System.getenv("configFile") != null) {
            configFile = System.getenv("configFile");
        }
        Configuration config = new Configuration(configFile);

        dns = config.getConfig("BVD_WEBSERVER_NAT_HOSTNAME");
        port = config.getConfig("BVD_RECEIVER_NAT_PORT");
        if (config.getConfig("BVD_RECEIVER_SSL_ENABLED").equals("true"))
        ssl = "yes";
        else
            ssl = "no";
    }
}
