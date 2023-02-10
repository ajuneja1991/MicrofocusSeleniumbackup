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

public class CalculationRule
{
  
  private LoginPage loginPage;
  private MainPage mainPage;
  private ManageDashboardsPage manageDashboardsPage;
  private EditDashboardPage editDashboardPage;
  private WebDriver driver;
  private Map<String, String> result;
  private String widgetSum = "group44";
  private String widgetMax = "group55";
  private String widgetAverage = "group88";
  private String widgetSumValue = "shape54";
  private String widgetMaxValue = "shape87";
  private String widgetAveragValue = "shape96";
  private String widgetGroup = "group143";
  private String widgetGroupBarChart = "group121";
  private String widgetGroupBarChartValue = "shape101";
  private String sSourceDashboard = "CalculationDashboard";
  private String sDashboard ;
  private String sDashboardPath;
  private String dims = "status";
  private String tags = "CalculationRuleWG";
  private String tags2 = "CalculationRuleSimple";
  private BvdWsClient client;
  private String json;
  //private String directLogin = EXT_BVD_URL + "/login/" + BVD_USR + "/" + BVD_PWD;
  private List<String> datasValues = Arrays.asList("value1", "value2", "value3", "value4");
  private List<String> dataValuesWg = Arrays.asList("value_1", "value_2", "value_3", "value_4");
  private List<String> dataValuesAverage = Arrays.asList("value1", "value2", "value3");
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
  private String OS ;
  private String userName = "TesterCalculationRule";
  private String userLogin;
  private String userPwd = "Test@123";
  private String roleName = "testCalculationRule";
  private String desc = "For tester";
  private String category = "All";
  private String accessType = "full-control";
  String roleId;
  private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");
  
  private void initPages() {
    loginPage = PageFactory.initElements(driver, LoginPage.class);
    mainPage = PageFactory.initElements(driver, MainPage.class);
    manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
    editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
  }
  
  private String get_key()
  {
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
    sDashboard = sSourceDashboard + "-" + BROWSER;
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


  @Test(priority = 30)
  public void copyTestfiles() {
    result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 40)
  public void getApiKeyWS() {
    result = client.userWS.getApiKey();
    Result.rcEquals(result, "0");
  }


  @Test(priority = 50)
  public void sendDataWg() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{ \n" +
          "\"items\" : [{  \n" +
          "\"id\": 1,\n" +
          "\"value_1\":20,\n" +
          "\"value_2\":30,\n" +
          "\"value_3\":40,\n" +
          "\"value_4\":50,\n" +
          "\"status\":\"red\"\n" +
          "},{\n" +
          "\"id\": \"2\",\n" +
          "\"value_1\":60,\n" +
          "\"value_2\":70,\n" +
          "\"value_3\":80,\n" +
          "\"value_4\":90,\n" +
          "\"status\":\"green\"\n" +
          "}]\n" +
          "}";

    result = client.receiverWS.bvdSendDataTagsOnly(get_key(), tags, json);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 55)
  public void sendData() throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

    json = "{\n" +
          "\"status\": \"SUCCESS\",\n" +
          "\"value1\": \"20\",  \n" +
          "\"value2\": \"10\",  \n" +
          "\"value3\": \"60\",  \n" +
          "\"value4\": \"40\"  \n" +
          "}";

    result = client.receiverWS.bvdSendData(get_key(), dims, tags2, json);
    Result.rcEquals(result, "0");

  }

  @Test(priority = 60)
  public void openManageDashboards() {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 70)
  public void uploadDashboard() {
    result = manageDashboardsPage.uploadDashboard(sDashboardPath);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 80)
  public void selectWidgetSum() {
    result = editDashboardPage.selectWidget(widgetSum);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 90)
  public void setDataChannelSum() {
    result = editDashboardPage.setDataChannel(tags2);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 100)
  public void setSumDataField() {
    result = editDashboardPage.setDataFieldMultiSelect(datasValues);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 110)
  public void selectWidgetSumValue() {
    result = editDashboardPage.selectWidget(widgetSumValue);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 120)
  public void setDataChannelSumValue() {
    result = editDashboardPage.setDataChannel(tags2);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 130)
  public void setSumCalculationRule() {
    result = editDashboardPage.setCalculationRule("value1 + value2 + value3 + value4", 50);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 140)
  public void CheckSumCalculationRule() {
    result = editDashboardPage.checkCalculationRule(widgetSumValue);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 150)
  public void setDataSumFieldCalcProperty() {
    result = editDashboardPage.setDataField("calculatedProperty");
    Result.rcEquals(result, "0");
  }


  //MAX

  @Test(priority = 200)
  public void selectWidgetMax() {
    result = editDashboardPage.selectWidget(widgetMax);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 210)
  public void setDataChannelMax() {
    result = editDashboardPage.setDataChannel(tags2);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 220)
  public void setDataFieldMax() {
    result = editDashboardPage.setDataFieldMultiSelect(datasValues);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 230)
  public void selectWidgetMaxValue() {
    result = editDashboardPage.selectWidget(widgetMaxValue);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 240)
  public void setDataChannelMaxValue() {
    result = editDashboardPage.setDataChannel(tags2);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 250)
  public void setWrongCalculationRule() {
    result = editDashboardPage.setCalculationRule("value1 + value2w", 50);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 260)
  public void CheckCalculationRule() {
    result = editDashboardPage.checkCalculationRule(widgetMaxValue);
    Result.rcEquals(result, "1");
  }

  @Test(priority = 270)
  public void setMaxCalculationRule() {
    result = editDashboardPage.setCalculationRule("max(value1, value2, value3, value4)", 50);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 280)
  public void setDataMaxFieldCalcProperty() {
    result = editDashboardPage.setDataField("calculatedProperty");
    Result.rcEquals(result, "0");
  }


  //AVERAGE

  @Test(priority = 290)
  public void selectWidgetAverage() {
    result = editDashboardPage.selectWidget(widgetAverage);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 300)
  public void setDataChannelAverage() {
    result = editDashboardPage.setDataChannel(tags2);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 310)
  public void setDataFieldAverage() {
    result = editDashboardPage.setDataFieldMultiSelect(dataValuesAverage);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 320)
  public void selectWidgetAverageValue() {
    result = editDashboardPage.selectWidget(widgetAveragValue);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 330)
  public void setDataChannelAverageValue() {
    result = editDashboardPage.setDataChannel(tags2);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 340)
  public void setAverageCalculationRule() {
    result = editDashboardPage.setCalculationRule("(value1 + value2 + value3)/3", 600);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 350)
  public void CheckAverageCalculationRule() {
    result = editDashboardPage.checkCalculationRule(widgetAveragValue);
    Result.rcEquals(result, "0");
  }


  @Test(priority = 360)
  public void setDataFieldAverageCalcProperty() {
    result = editDashboardPage.setDataField("calculatedProperty");
    Result.rcEquals(result, "0");
  }

  //Widget Group

  @Test(priority = 370)
  public void selectWidgetGroup() {
    result = editDashboardPage.selectWidgetFromList(widgetGroup);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 380)
  public void setDataChannelWG() {
    result = editDashboardPage.setDataChannel(tags);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 410)
  public void selectWGValue() {
    result = editDashboardPage.selectWidgetFromList(widgetGroupBarChartValue);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 420)
  public void setWGCalculationRule() {
    result = editDashboardPage.setCalculationRule("min(value_1, value_2, value_3, value_4)", 50);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 430)
  public void CheckWGCalculationRule() {
    result = editDashboardPage.checkCalculationRule(widgetGroupBarChartValue);
    Result.rcEquals(result, "0");
  }

  @Test(priority = 440)
  public void setWGFieldCalcProperty() {
    result = editDashboardPage.setDataField("calculatedProperty");
    Result.rcEquals(result, "0");
  }

  @Test(priority = 450)
  public void saveDashboard() {
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }

  // Check DATA on dashboard
  
  @Test(priority = 460)
  public void viewDashboard() {
    result = mainPage.viewDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 470)
  public void getSumValue() {
    result = mainPage.getTextOfTextValueWidget(widgetSumValue);
    Result.outEquals(result, "130");
  }
  
  @Test(priority = 475)
  public void getMaxValue() {
    result = mainPage.getTextOfTextValueWidget(widgetMaxValue);
    Result.outEquals(result, "60");
  }
  
  @Test(priority = 480)
  public void getAverageValue() {
    result = mainPage.getTextOfTextValueWidget(widgetAveragValue);
    Result.outEquals(result, "30");
  }
  
  @Test(priority = 490)
  public void getWGValues() {
    result = mainPage.getTextValueOfTextWidgetsInsideWidgetGroup(widgetGroup);
    Result.outEquals(result, "20,60");
  }

  
  //Sending new DATA and checking values
  
  
  @Test(priority = 500)
  public void reSendDataWg() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    json = "{ \n" +
          "\"items\" : [{  \n" +
          "\"id\": 1,\n" +
          "\"value_1\":30,\n" +
          "\"value_2\":40,\n" +
          "\"value_3\":50,\n" +
          "\"value_4\":60,\n" +
          "\"status\":\"red\"\n" +
          "},{\n" +
          "\"id\": \"2\",\n" +
          "\"value_1\":70,\n" +
          "\"value_2\":80,\n" +
          "\"value_3\":90,\n" +
          "\"value_4\":100,\n" +
          "\"status\":\"green\"\n" +
          "}]\n" +
          "}";
    
    result = client.receiverWS.bvdSendDataTagsOnly(get_key(), tags, json);
    Result.rcEquals(result, "0");
    
  }
  
  @Test(priority = 510)
  public void reSendData() throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    
    json = "{\n" +
          "\"status\": \"SUCCESS\",\n" +
          "\"value1\": \"30\",  \n" +
          "\"value2\": \"40\",  \n" +
          "\"value3\": \"50\",  \n" +
          "\"value4\": \"60\"  \n" +
          "}";
    
    result = client.receiverWS.bvdSendData(get_key(), dims, tags2, json);
    Result.rcEquals(result, "0");
    
  }
  
  @Test(priority = 520)
  public void getChangedSumValue() {
    result = mainPage.getTextOfTextValueWidget(widgetSumValue);
    Result.outEquals(result, "180");
  }
  
  @Test(priority = 530)
  public void getChangedMaxValue() {
    result = mainPage.getTextOfTextValueWidget(widgetMaxValue);
    Result.outEquals(result, "60");
  }
  
  @Test(priority = 540)
  public void getChangedAverageValue() {
    result = mainPage.getTextOfTextValueWidget(widgetAveragValue);
    Result.outEquals(result, "40");
  }
  
  @Test(priority = 550)
  public void getChangedWGValues() {
    result = mainPage.getTextValueOfTextWidgetsInsideWidgetGroup(widgetGroup);
    Result.outEquals(result, "30,70");
  }
  
  
  // Change one of rule
  
  @Test(priority = 600)
  public void openManageDashboardsForRuleChange()
  {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
  }

  @Test(priority = 610)
  public void editDashboard()
  {
    result = manageDashboardsPage.openEditDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 620)
  public void changeCalcRule()
  {
    result = editDashboardPage.selectWidget(widgetSumValue);
    Result.rcEquals(result, "0");
  
    result = editDashboardPage.setCalculationRule("value4 - value1", 50);
    Result.rcEquals(result, "0");
  
    result = editDashboardPage.save();
    Result.rcEquals(result, "0");
  }
  
  //Check changed rule data
  
  @Test(priority = 630)
  public void viewDashboardChangedRule() {
    result = mainPage.viewDashboard(sDashboard);
    Result.rcEquals(result, "0");
  }
  
  @Test(priority = 640)
  public void getChangedRuleSumValue() {
    result = mainPage.getTextOfTextValueWidget(widgetSumValue);
    Result.outEquals(result, "30");
  }
  
  //Delete Dashboard
  
  @Test(priority = 650)
  public void deleteDashboard()
  {
    result = mainPage.openManageDashboards();
    Result.rcEquals(result, "0");
  
    result = manageDashboardsPage.deleteDashboard(sDashboard);
    Result.rcEquals(result, "0");
    
  }
  @Test(priority = 660)
  public void testLogout() {
    result = mainPage.logout();
    Result.rcEquals(result, "0");
  }


  @Test(priority = 670)
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
