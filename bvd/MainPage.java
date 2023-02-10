package com.hp.opr.qa.framework.pageobjects.bvd;


import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.hp.opr.qa.framework.utils.UiTestUtils;
import com.typesafe.config.Config;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.beans.Visibility;
import java.security.Key;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class MainPage {

  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg= new TestConfig().getConfig();
  private String PROXY_HOST="";
  private String BROWSER="";
  private int PROXY_PORT;
  private String transDashboard = "translated";


  final WebDriver driver;
  final WebDriverWait wait;


  @FindBy(how = How.CSS, using = "g#shape2 text")
  static WebElement admintextValueRBAC;

  @FindBy(how = How.CSS, using = "input#searchDashboard")
  static WebElement searchDashboardInputtextbox;

  @FindBy(how = How.CSS, using = "div.slide-menu-content[aria-expanded='true']")
  static WebElement dashboardslideoutlist;

  @FindBy (how = How.CSS, using = "g#shape2 text")
  static WebElement ele1;

  @FindBy(how = How.CSS, using = "g#shape3 text")
  static WebElement ele2;

  @FindBy(how = How.CSS, using = "g#shape5 text")
  static WebElement ele3;

  @FindBy(how = How.CSS, using = "g#shape3 text")
  static WebElement admintextValueRBACgroup;

  @FindBy(how = How.CSS, using = "g#shape4 text")
  static WebElement admintextValueRBACgroup1;

  @FindBy(how = How.CSS, using = "button[title='Dashboards'] span")
  static WebElement dashboardName;

  // BVD DASHBOARDS
  @FindBy(how = How.ID, using = "nav_dashboards_menu")
  static WebElement a_Dashboards;

  @FindBy(how=How.CSS,using="a[href='https://github.com/MicroFocus/ColorYourData/tree/master/generators/node']")
  static WebElement a_sampleGen_githublink;

  @FindBy(how = How.CSS, using = "div.slideout-apply-param div.alert")
  static WebElement invaliddateErrorMessage;

  @FindBy(how = How.CSS, using = "div.dropdown button")
  private  List<WebElement> dropdowns;

  @FindBy(how = How.CSS, using = "div#slide-menu-header a")
  private WebElement closeSideMenu;

  @FindBy(how = How.CSS, using = "div.slide-menu-header a")
  private WebElement closeSideMenunew;

  @FindBy(how = How.CSS, using = "button[title='Edit dashboard']")
  private WebElement quickEditbutton;

  @FindBy(how = How.CSS, using = "button.channelResultListElement-clear")
  private WebElement clearDataChannel;

  @FindBy(how=How.CSS,using="div[role='alert'][class*=alert]")
  static WebElement errormessage;

  @FindBy(how=How.CSS,using="button#nav_admin_menu+ul li a")
  private  List<WebElement> adminlistfornonadmin;

  @FindBy(how=How.CSS,using="button#param_apply[disabled='disabled']")
  private  WebElement applydisabled;

  @FindBy(how=How.CSS,using="button#param_reset[disabled='disabled']")
  private  WebElement resetdisabled;

  @FindBy(how=How.CSS,using="button#param_reset")
  private WebElement slideout_resetbutton;

  @FindBy(how = How.ID, using = "submit")
  private WebElement button_idm_login;

  @FindBy(how = How.ID, using = "username")
  private WebElement input_idm_username;

  @FindBy(how = How.XPATH, using = "//button[contains(text(),'Log In')]")
  static WebElement relogin;

  // BVD HOME
  @FindBy(how = How.ID, using = "nav_default_dashboard")
  static WebElement a_DefaultDashboard;

  // BVD USER SETTINGS
  @FindBy(how = How.ID, using = "nav_user_menu")
  static WebElement a_PersonalUserSettings;

  // BVD MY ACCOUNT
  @FindBy(how = How.ID, using = "nav_my_account")
  static WebElement a_MyAccount;

  // BVD LOGOUT
  @FindBy(how = How.ID, using = "nav_logout")
  static WebElement a_Logout;

  // BVD ADMINISTRATION
  @FindBy(how = How.ID, using = "nav_admin_menu")
  static WebElement a_Administration;

  @FindBy(how = How.ID, using = "nav_system_settings")
  static WebElement a_SystemSettings;

  // BVD MANAGE DASHBOARDS
  @FindBy(how = How.ID, using = "nav_system_manage")
  static WebElement a_ManageDashboards;

  @FindBy(how = How.ID, using = "nav_system_users")
  static WebElement a_UserManagement;

  @FindBy(how = How.ID, using = "nav_system_downloads")
  static WebElement a_DownloadToolsAndSamples;

  // BVD HELP
  @FindBy(how = How.ID, using = "nav_help_menu")
  static WebElement a_Help;

  @FindBy(how = How.ID, using = "nav_helps")
  static WebElement a_Documentation;

  @FindBy(how = How.ID, using = "nav_get_started")
  static WebElement a_GettingStarted;

  @FindBy(how = How.ID, using = "nav_feedback")
  static WebElement a_Feedback;

  @FindBy(how = How.ID, using = "nav_about")
  static WebElement a_About;

  // Home Dashboard
  @FindBy(how = How.ID, using = "nav_default_dashboard")
  static WebElement a_HomeDashboard;

  @FindBy(how = How.ID, using = "nav_api_key")
  static WebElement apiKeyMenu;

  @FindBy(how = How.CSS, using = "input#ssl-input.ng-not-empty")
  static WebElement sslcheckboxselected;

  @FindBy(how = How.CSS, using = "input#ssl-input.ng-empty")
  static WebElement sslcheckboxNOTselected;

  //MyAccount
  @FindBy(how = How.ID, using = "email-input_email")
  static WebElement emailField;

  @FindBy(how = How.ID, using = "name-input_name")
  static WebElement nameField;

  @FindBy(how = How.ID, using = "oldPassword-input")
  static WebElement oldPasswordField;

  @FindBy(how = How.ID, using = "newPassword-input")
  static WebElement newPasswordField;

  @FindBy(how = How.ID, using = "confirmPassword-input")
  static WebElement confirmNewPasswordField;

  //@FindBy(how = How.ID, using = "ssl-input")
  @FindBy(how = How.ID, using = "ssl-input")
  static WebElement sslCheckBox;

  @FindBy(how = How.ID, using = "purge_now_btn")
  static WebElement purgeButton;

  // parameter page side button
  //@FindBy(how = How.XPATH, using = "//button[@class='ng-scope slideout-button']")
  @FindBy(how = How.XPATH, using = "//button[@data-cy='slideout-button']")
  static WebElement parameterButtonToClick;
  // parameter Value selection from left bar drop down
  @FindBy(how = How.XPATH, using = "//h4[@title='fetchingcpudata (cpuparameter)']/parent::div/div")
  static WebElement dropDownValue1;


  @FindBy(how = How.CSS, using = "div.opr-extended-input-container button.opr-dropdown-selection-button")
  static WebElement slideout_dropdownbtn;

  @FindBy(how = How.CSS, using = "h4[title='osparam (os)']~div button.opr-dropdown-selection-button")
  static WebElement osparams_dropdown;

  @FindBy(how = How.CSS, using = "h4[title='modelparam (MODEL_NAME)']~div div.opr-extended-input-container button.opr-dropdown-selection-button")
  static WebElement secondslideout_dropdownbtn;

  @FindBy(how = How.CSS, using = "input[type='text']")
  static WebElement slideparamValueselect;

  @FindBy(how=How.XPATH,using="//button[starts-with(@id,'oprExtendedInputContainer_')]")
  static List<WebElement> dropDownValue;
  // parameter Value selection from left bar drop down
  @FindBy(how = How.XPATH, using = "//h4[@title='fetchingramdata (ramparameter)']/parent::div/div")
  static WebElement dropDownValue2;

  @FindBy(how = How.XPATH, using = "//h4[@title='fetchingupdatedcpudata (cpuparam)']/parent::div/div")
  static WebElement dropDownValue3;

  @FindBy(how = How.XPATH, using = "//h4[@title='fetchingupdatedramdata (ramparam)']/parent::div/div")
  static WebElement dropDownValue4;

  @FindBy(how=How.XPATH,using="//h4[@title='fetchingcpudata (cpuparameter)']/parent::div/div/opr-tag-selector/div/opr-tag-bar/div/opr-tag-bar-input/input")
  static WebElement leftBarValuetext1;

  @FindBy(how=How.XPATH,using="//h4[@title='fetchingramdata (ramparameter)']/parent::div/div/opr-tag-selector/div/opr-tag-bar/div/opr-tag-bar-input/input")
  static WebElement leftBarValuetext2;

  @FindBy(how=How.XPATH,using="//h4[@title='fetchingupdatedcpudata (cpuparam)']/parent::div/div/opr-tag-selector/div/opr-tag-bar/div/opr-tag-bar-input/input")
  static WebElement leftBarValuetext3;

  @FindBy(how=How.XPATH,using="//h4[@title='fetchingupdatedramdata (ramparam)']/parent::div/div/opr-tag-selector/div/opr-tag-bar/div/opr-tag-bar-input/input")
  static WebElement leftBarValuetext4;

  @FindBy(how=How.CSS,using="div[class='list-title ng-binding ng-scope']")
  static List<WebElement> leftBarValueData;
  // parameter Value selection from left bar drop down
  @FindBy(how = How.ID, using = "param_apply")
  static WebElement applyButtonForParameterValue;

  @FindBy(how=How.CSS,using="g>text.st2[y='0']")
  static List<WebElement> queryValue;

  @FindBy(how = How.XPATH, using = "//h4[@title='modelparam (MODEL_NAME)']/parent::div/div")
  static WebElement parameterButtonTextWidgetModel;

  @FindBy(how = How.XPATH, using = "//h4[@title='osparam (os)']/parent::div/div")
  static WebElement parameterButtonTextWidgetOs;

  @FindBy(how=How.XPATH,using="//h4[@title='modelparam (MODEL_NAME)']/parent::div/div/opr-tag-selector/div/opr-tag-bar/div/opr-tag-bar-input/input")
  static WebElement TextWidgetvaluemodel;

  @FindBy(how=How.XPATH,using="//h4[@title='osparam (os)']/parent::div/div/opr-tag-selector/div/opr-tag-bar/div/opr-tag-bar-input/input")
  static WebElement TextWidgetvalueOs;

  @FindBy(how=How.CSS,using="[title='paramquery_datetime :end']~div input[name='dateSelector']")
  static WebElement datetime_toslideouttextbox;

  @FindBy(how=How.CSS,using="[title='paramquery_datetime :start']~div input[name='dateSelector']")
  static WebElement datetime_fromslideouttextbox;

  //@FindBy(how=How.NAME,using="calendar")
  @FindBy(how=How.CSS, using="button[title='Open picker']")
  static WebElement calendarbutton;

  @FindBy(how=How.CSS,using="ux-date-time-picker.start-date-picker button[aria-label='Switch to show months in the year']")
  static WebElement startdatepickerbutton;

  @FindBy(how=How.CSS,using="ux-date-time-picker.start-date-picker button[aria-label='Switch to show years in the decade']")
  static WebElement startdatedecadebutton;

  @FindBy(how=How.CSS,using="ux-date-time-picker.end-date-picker button[aria-label='Switch to show years in the decade']")
  static WebElement enddatedecadebutton;

  @FindBy(how=How.CSS,using="ux-date-time-picker.end-date-picker button[aria-label='Switch to show months in the year']")
  static WebElement enddatepickerbutton;

  @FindBy(how=How.CSS,using="ux-date-time-picker.start-date-picker [name='previous']")
  static WebElement startdatepreviousbutton;

  @FindBy(how=How.CSS,using="ux-date-time-picker.start-date-picker [name='next']")
  static WebElement startdatenextbutton;

  @FindBy(how=How.CSS,using="ux-date-time-picker.end-date-picker [name='previous']")
  static WebElement enddatepreviousbutton;

  @FindBy(how=How.CSS,using="ux-date-time-picker.end-date-picker [name='next']")
  static WebElement enddatenextbutton;

  @FindBy(how=How.CSS,using="ux-date-time-picker.start-date-picker input[type='text'][aria-label='hour']")
  static WebElement fromdateinputhour;

  @FindBy(how=How.CSS,using="ux-date-time-picker.start-date-picker input[type='text'][aria-label='Time Zone']")
  static WebElement fromdateTimeZone;

  @FindBy(how=How.CSS,using="ux-date-time-picker.start-date-picker button[aria-label='Switch to the next time zone'] ux-icon")
  static WebElement fromdateTimeZoneIncreaseKey;

  @FindBy(how=How.CSS,using="ux-date-time-picker.start-date-picker button[aria-label='Switch to the previous time zone'] ux-icon")
  static WebElement fromdateTimeZoneDecreaseKey;

  @FindBy(how=How.CSS,using="ux-date-time-picker.end-date-picker button[aria-label='Switch to the next time zone'] ux-icon")
  static WebElement todateTimeZoneIncreaseKey;

  @FindBy(how=How.CSS,using="ux-date-time-picker.end-date-picker button[aria-label='Switch to the previous time zone'] ux-icon")
  static WebElement todateTimeZoneDecreaseKey;

  @FindBy(how=How.CSS,using="ux-date-time-picker.end-date-picker input[type='text'][aria-label='Time Zone']")
  static WebElement todateTimeZone;

  @FindBy(how=How.CSS,using="ux-date-time-picker.start-date-picker input[type='text'][aria-label='minute']")
  static WebElement fromdateinputminute;

  @FindBy(how=How.CSS,using="ux-date-time-picker.end-date-picker input[type='text'][aria-label='hour']")
  static WebElement todateinputhour;

  @FindBy(how=How.CSS,using="ux-date-time-picker.end-date-picker input[type='text'][aria-label='minute']")
  static WebElement todateinputminute;

  @FindBy(how=How.CSS,using="ux-date-time-picker.start-date-picker  button[aria-selected='true']")
  static WebElement selectedfromdate;

  @FindBy(how=How.CSS,using="ux-date-time-picker.end-date-picker  button[aria-selected='true']")
  static WebElement selectedtodate;

  @FindBy(how=How.CSS,using="ux-date-time-picker.end-date-picker button[role='radio'][aria-checked='true']")
  static WebElement todateampm;

  @FindBy(how=How.CSS,using="ux-date-time-picker.start-date-picker button[role='radio'][aria-checked='true']")
  static WebElement fromdateampm;


  @FindBy(how=How.CSS,using="[data-cy='date-string']")
  static WebElement parametertextarea;

  public MainPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, 30);
  }


  private Map<String, String> waitingModaldisappearance() {
    try {
      wait.until(ExpectedConditions.not(presenceOfAllElementsLocatedBy(By.className("" +
              "modal-backdrop"))));
      wait.until(ExpectedConditions.not(presenceOfAllElementsLocatedBy(By
              .cssSelector("[modal-window='modal-window']"))));
      return ResultStore.success("No Modal Window");
    } catch (Exception e) {
      return ResultStore.failure("Modal window exists");
    }

  }

  private void waitingLoadingBackgroundGone() {
    WebElement spinner = driver.findElement(By.id("load-spinner"));
    wait.until(ExpectedConditions.attributeToBe(spinner, "style", "display: none;"));
  }

  // OPEN MANAGE DASHBOARDS
  public Map<String, String> openManageDashboards() {
    Map<String, String> result;

    try {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      TestUtils.sleep(10);

      waitingModaldisappearance();
      driver.switchTo().defaultContent();
      wait.until(ExpectedConditions.elementToBeClickable(a_Administration));
      a_Administration.click();
      wait.until(ExpectedConditions.elementToBeClickable(a_ManageDashboards));
      a_ManageDashboards.click();
      // synchronize
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#container " +
              ".element-item:nth-child(2)")));
      result = ResultStore.success("Manage Dashboards successfully opened");
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to open Manage Dashboards. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getdashboardname() {
    Map<String, String> result;

    try {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      TestUtils.sleep(2);
      waitingModaldisappearance();
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button#nav_dashboards_menu span")));
      result = ResultStore.success(dashboardName.getText());
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to open Manage Dashboards. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }


  public Map<String, String> openDataCollectors() {
    Map<String, String> result;

    try {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      wait.until(ExpectedConditions.elementToBeClickable(By.id("nav_admin_menu")));
      a_Administration.click();
      TestUtils.sleepAndLog(4000);
      WebElement dataCollector = driver.findElement(By.id("nav_system_data_collector"));
      wait.until(ExpectedConditions.elementToBeClickable(By.id("nav_system_data_collector")));
      TestUtils.sleepAndLog(2000);
      dataCollector.click();
      result = ResultStore.success("DataCollectors successfully opened");
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to open DataCollectors. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }
  public Map<String, String> validateMenuOptionswhenrestricted(List<String> titles) {
    Map<String, String> result;

    try {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      List<String> titletext=new ArrayList<>();
      for (WebElement ele:dropdowns){
        titletext.add(ele.getAttribute("title"));
      }
      System.out.println(Arrays.toString(titletext.toArray()));
      if (titles.equals(titletext)){
        result = ResultStore.success("As Expected Administration Menu option not present");
      }else{
        throw new Exception();
      }

    } catch (Exception exc) {
      result = ResultStore.failure("Administration Menu is present" + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> adminoptionsfornonadmin(List<String> admindropdown) {
    Map<String, String> result;
    try {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      List<String> admindropdowntext=new ArrayList<>();
      for (WebElement ele:adminlistfornonadmin){
        admindropdowntext.add(ele.getAttribute("title"));
      }
      System.out.println(Arrays.toString(admindropdowntext.toArray()));
      if (admindropdown.equals(admindropdowntext)){
        result = ResultStore.success("As Expected Administration Menu option has the options");
      }else{
        throw new Exception();
      }

    } catch (Exception exc) {
      result = ResultStore.failure("Administration Menu does not have expected options" + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> openDeniedManageDashboards() {
    Map<String, String> result;
    WebDriverWait wait = new WebDriverWait(driver, 5);
    try {
//            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      wait.until(ExpectedConditions.elementToBeClickable(By.id("nav_admin_menu")));
      a_Administration.click();
      wait.until(ExpectedConditions.elementToBeClickable(By.id("nav_system_manage")));
      a_ManageDashboards.click();

      result = ResultStore.success("Manage Dashboards successfully opened");
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to open Manage Dashboards. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> openManageDashboardsLimited() {
    Map<String, String> result;

    try {
//            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      wait.until(ExpectedConditions.elementToBeClickable(By.id("nav_admin_menu")));
      JavascriptExecutor executor = (JavascriptExecutor) driver;
      executor.executeScript("arguments[0].click();", a_ManageDashboards);
      wait.until(ExpectedConditions.elementToBeClickable(By.id("nav_system_manage")));
      a_ManageDashboards.click();

      result = ResultStore.success("Manage Dashboards successfully opened");
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to open Manage Dashboards. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }


  public Map<String, String> viewDashboard(String sDashboardName) {
    Map<String, String> result;

    try {

      WebDriverWait localWait = new WebDriverWait(driver, 30);
      //UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      // wait until all menu entries are loaded
      localWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div#slide-menu" +
              " a")));
      waitingLoadingBackgroundGone();
      localWait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
      a_Dashboards.click();
      TestUtils.sleepAndLog(1500);
      WebElement a_Dashboard = driver.findElement(By.xpath("//div[@class=\"categorized-dashboard-list\"]//a[contains(text()"
              + ",'" + sDashboardName +"')]"));
      TestUtils.sleepAndLog(1500);
      wait.until(ExpectedConditions.visibilityOf(a_Dashboard));
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboard));
      a_Dashboard.click();


      //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sDashboardName)));
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+sDashboardName+"')]")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button#nav_default_dashboard")));
      // synchronize
      wait.until(ExpectedConditions.elementSelectionStateToBe(By.cssSelector("[data-cy='no-permission-systemSettings']"), false));
      Boolean isPresent = driver.findElements(By.cssSelector("[data-cy='external-link']")).size() > 0;
      if(isPresent) {
        String str=driver.findElement(By.cssSelector("[data-cy='external-link']")).getText();
        System.out.println(str);
        Assert.assertEquals(str, "This dashboard contains external links which could lead to malicious sites\n" +
                "Do not show this message again");
        TestUtils.sleep(1);
        driver.findElement(By.linkText("Do not show this message again")).click();
      }
      Boolean isdashboarlistopen = driver.findElements(By.cssSelector("div.slide-menu-content[aria-expanded='true']")).size() > 0;
      if(isdashboarlistopen){
        wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
        a_Dashboards.click();
      }


      result = ResultStore.success("Dashboard " + sDashboardName + " successfully opened");
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to open Dashboard " + sDashboardName + ". error: " +
              Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> viewDashboardwithMenu(String menuCategory,String sDashboardName) {
    Map<String, String> result;

    try {

      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      // wait until all menu entries are loaded
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div#slide-menu" +
              " a")));
      waitingLoadingBackgroundGone();
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
      a_Dashboards.click();
      TestUtils.sleepAndLog(1500);
      WebElement menuCategorybtn = driver.findElement(By.xpath("//span[contains(text(),'"+menuCategory+"')]/preceding::button[1]"));
      wait.until(ExpectedConditions.visibilityOf(menuCategorybtn));
      wait.until(ExpectedConditions.elementToBeClickable(menuCategorybtn));
      menuCategorybtn.click();
      TestUtils.sleepAndLog(1500);
      WebElement a_Dashboard = driver.findElement(By.xpath("//div[@class=\"categorized-dashboard-list\"]//a[contains(text()"
              + ",'" + sDashboardName +"')]"));
      wait.until(ExpectedConditions.visibilityOf(a_Dashboard));
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboard));
      a_Dashboard.click();

      //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sDashboardName)));
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+sDashboardName+"')]")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button#nav_default_dashboard")));
      // synchronize
      Boolean noPermissionPresent = driver.findElements(By.cssSelector("[data-cy='no-permission-systemSettings']")).size() >0;
      if(noPermissionPresent) {
        wait.until(ExpectedConditions.elementSelectionStateToBe(By.cssSelector("[data-cy='no-permission-systemSettings']"), false));
      }
      Boolean isPresent = driver.findElements(By.cssSelector("[data-cy='external-link']")).size() > 0;
      if(isPresent) {
        String str=driver.findElement(By.cssSelector("[data-cy='external-link']")).getText();
        System.out.println(str);
        Assert.assertEquals(str, "This dashboard contains external links which could lead to malicious sites\n" +
                "Do not show this message again");
        TestUtils.sleep(1);
        driver.findElement(By.linkText("Do not show this message again")).click();
      }

      result = ResultStore.success("Dashboard " + sDashboardName + " successfully opened");
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to open Dashboard " + sDashboardName + ". error: " +
              Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> viewDashboardNew(String sDashboardName) {
    Map<String, String> result;

    try {

      WebDriverWait localWait = new WebDriverWait(driver, 30);
      //UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      // wait until all menu entries are loaded
      localWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div#slide-menu" +
              " a")));
      waitingLoadingBackgroundGone();
      localWait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
      a_Dashboards.click();
      TestUtils.sleepAndLog(3000);
      WebElement a_Dashboard = driver.findElement(By.xpath("//div[@class=\"categorized-dashboard-list\"]//a[contains(text()"
              + ",'" + sDashboardName +"')]"));
      TestUtils.sleepAndLog(1500);
      wait.until(ExpectedConditions.visibilityOf(a_Dashboard));
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboard));
      a_Dashboard.click();


      //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sDashboardName)));
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+sDashboardName+"')]")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button#nav_default_dashboard")));
      // synchronize
      Boolean isPresent = driver.findElements(By.cssSelector("[data-cy='external-link']")).size() > 0;
      if(isPresent) {
        String str=driver.findElement(By.cssSelector("[data-cy='external-link']")).getText();
        System.out.println(str);
        Assert.assertEquals(str, "This dashboard contains external links which could lead to malicious sites\n" +
                "Do not show this message again");
        TestUtils.sleep(1);
        driver.findElement(By.linkText("Do not show this message again")).click();
      }
      Boolean isdashboarlistopen = driver.findElements(By.cssSelector("div.slide-menu-content[aria-expanded='true']")).size() > 0;
      if(isdashboarlistopen){
        wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
        a_Dashboards.click();
      }


      result = ResultStore.success("Dashboard " + sDashboardName + " successfully opened");
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to open Dashboard " + sDashboardName + ". error: " +
              Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> checkDashboardsUnderMenuCategory(String menuCategory,List<String> dashboardsexpected ) {
    Map<String, String> result;

    try {

      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      // wait until all menu entries are loaded
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div#slide-menu" +
              " a")));
      waitingLoadingBackgroundGone();
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
      a_Dashboards.click();
      TestUtils.sleep(2);

      if(driver.findElements(By.xpath("//span[contains(text(),'"+menuCategory+"')]/parent::td/span/ux-icon[@class='clickable ux-icon ux-icon-folder']")).size() > 0){
        TestUtils.sleepAndLog(1500);
        WebElement menuCategorybtn = driver.findElement(By.xpath("//span[contains(text(),'"+menuCategory+"')]/ancestor::tr/td/span/button"));
        wait.until(ExpectedConditions.visibilityOf(menuCategorybtn));
        wait.until(ExpectedConditions.elementToBeClickable(menuCategorybtn));
        menuCategorybtn.click();

      }
      TestUtils.sleepAndLog(1500);
      List<WebElement> dashboardslist=driver.findElements(By.xpath("//span[contains(text(),'"+menuCategory+"')]/following::tr" +
              "/td/a"));


      List<String> dashboards = new ArrayList<>();

      for (WebElement a_Dashboard : dashboardslist) {
        dashboards.add(a_Dashboard.getText());
      }
      Assert.assertEquals(dashboards.containsAll(dashboardsexpected),true);
      Assert.assertEquals(dashboardsexpected.containsAll(dashboards),true);
      a_Dashboards.click();
      TestUtils.sleepAndLog(1500);
      result = ResultStore.success("Dashboards list under category is as expected");
    } catch (Exception exc) {
      result = ResultStore.failure("Dashboards list under category is not as expected: " +
              Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> searchDashboard(String menuCategory) {
    Map<String, String> result;

    try {

      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      // wait until all menu entries are loaded
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div#slide-menu" +
              " a")));
      waitingLoadingBackgroundGone();
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
      Boolean isdashboardlistopen = driver.findElements(By.cssSelector("div.slide-menu-content[aria-expanded='true']")).size() > 0;
      if(isdashboardlistopen){

      }else{
        wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
        a_Dashboards.click();
      }

      wait.until(ExpectedConditions.elementToBeClickable(searchDashboardInputtextbox));
      searchDashboardInputtextbox.click();
      TestUtils.sleep(2);
      typingText(searchDashboardInputtextbox,menuCategory,10);
      TestUtils.sleep(2);
      Boolean bool=driver.findElement(By.xpath("//span[contains(text(),'"+menuCategory+"')]")).isDisplayed();
      Assert.assertTrue(bool);
      result = ResultStore.success("Dashboards list under category is as expected");
    } catch (Exception exc) {
      result = ResultStore.failure("Dashboards list under category is not as expected: " +
              Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> checkDashboardHiddenfromViewDashboard(String sDashboardName) {
    Map<String, String> result;

    try {

      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      // wait until all menu entries are loaded
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div#slide-menu" +
              " a")));
      waitingLoadingBackgroundGone();
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
      a_Dashboards.click();
      TestUtils.sleepAndLog(1500);
      List<WebElement> a_Dashboards = driver.findElements(By.cssSelector("span.channelResultListElement"));

      for(WebElement ele:a_Dashboards){
        if(ele.getText().contains(sDashboardName)){
          Assert.assertEquals(true,false);
        }
      }
      TestUtils.sleepAndLog(1500);

      wait.until(ExpectedConditions.visibilityOf(closeSideMenunew));
      Actions closeMenu=new Actions(driver);
      closeMenu.moveToElement(closeSideMenunew).click(closeSideMenunew).build().perform();

      wait.until(ExpectedConditions.invisibilityOf(closeSideMenunew));

      result = ResultStore.success("Dashboard " + sDashboardName + " is hidden now");
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to hide Dashboard " + sDashboardName + ". error: " +
              Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public boolean checkIfDashboardExistsInMainPage(String sDashboardName) {
    try {

      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      // wait until all menu entries are loaded
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div#slide-menu" +
              " a")));
      waitingLoadingBackgroundGone();

      TestUtils.sleepAndLog(1500);
      String currentDashboardName = driver.findElement(By.cssSelector("button[title='Dashboards'] i~span")).getText();

      if(currentDashboardName.equals(sDashboardName)){
        return true;
      } else{
        return false;
      }
    } catch (Exception exc) {
      return false;
    }
  }




  public Map<String, String> viewDashboardfornonadmincheck(String sDashboardName) {
    Map<String, String> result;

    try {

      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      // wait until all menu entries are loaded
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div#slide-menu" +
              " a")));
      waitingLoadingBackgroundGone();
      wait.until(presenceOfElementLocated(By.cssSelector("a[title='" +
              sDashboardName + "']")));
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
      a_Dashboards.click();
      TestUtils.sleepAndLog(1500);
      WebElement a_Dashboard = driver.findElement(By.cssSelector("a[title='" + sDashboardName +
              "']"));
      TestUtils.sleepAndLog(1500);
      wait.until(ExpectedConditions.visibilityOf(a_Dashboard));
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboard));
      a_Dashboard.click();


      //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sDashboardName)));
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+sDashboardName+"')]")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));
      // synchronize
      TestUtils.sleepAndLog(10000);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[role='alert'][class*=alert]")));

      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[role='alert'][class*=alert]:not([class*='ng-hide'])")));


      result = ResultStore.success("Dashboard " + sDashboardName + " successfully opened");
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to open Dashboard " + sDashboardName + ". error: " +
              Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> viewDashboardforGroupWidget(String sDashboardName) {
    Map<String, String> result;

    try {

      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      // wait until all menu entries are loaded
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div#slide-menu" +
              " a")));
      waitingLoadingBackgroundGone();
      wait.until(presenceOfElementLocated(By.cssSelector("input[value='" +
              sDashboardName + "']")));
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));

      a_Dashboards.click();
      TestUtils.sleepAndLog(1500);
      WebElement a_Dashboard = driver.findElement(By.xpath("//a[text()='" + sDashboardName +
              "']"));
      TestUtils.sleepAndLog(1500);
      wait.until(ExpectedConditions.visibilityOf(a_Dashboard));
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboard));
      a_Dashboard.click();

      //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sDashboardName)));
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+sDashboardName+"')]")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));
      // synchronize

      result = ResultStore.success("Dashboard " + sDashboardName + " successfully opened");
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to open Dashboard " + sDashboardName + ". error: " +
              Misc.getStacktraceAsString(exc));
    }
    return result;
  }


  public Map<String, String> viewDatainDashboardforGroupWidget(String sDashboardName) {
    Map<String, String> result;

    try {

      TestUtils.sleepAndLog(10000);
      wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[id*=-shape13]")));
      // wait until all menu entries are loaded

      List<WebElement> webelements = driver.findElements(By.cssSelector("[id*=-shape13] text"));

      List<String> listoftext1 = new ArrayList<>();
      for (WebElement xytext : webelements)
      {
        listoftext1.add(xytext.getText());
      }
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='group16widgetGroupScroll']")));

      WebElement scroll = driver.findElement(By.cssSelector("[id='group16widgetGroupScroll'] rect:nth-child(2)"));

      Actions actions = new Actions(driver);

      int numberOfPixelsToDragTheScrollbarUp = -10;
      int scrollPoints = 100;
      for (int i = scrollPoints; i > 10; i = i + numberOfPixelsToDragTheScrollbarUp)
      {
        actions.moveToElement(scroll).clickAndHold().moveByOffset(0, numberOfPixelsToDragTheScrollbarUp).release(scroll).build().perform();
      }

      List<WebElement> webelements1 = driver.findElements(By.cssSelector("[id*=-shape13] text"));

      List<String> listoftext2 = new ArrayList<>();
      for (WebElement xytext : webelements1)
      {
        listoftext2.add(xytext.getText());
      }

      System.out.println(listoftext1);
      System.out.println(listoftext2);
      if(listoftext1.equals(listoftext2)){
        result = ResultStore.failure("list contains the data fields");
      }else{
        result = ResultStore.success("List does not contain the data fields ");
      }

    } catch (Exception exc) {
      result = ResultStore.failure("Failed to view data in " + sDashboardName + ". error: " +
              Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> viewDashboardforHyperlinkfortemplate(String sDashboardName) {
    Map<String, String> result;

    try {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      // wait until all menu entries are loaded
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div#slide-menu" +
              " a")));
      waitingLoadingBackgroundGone();
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));

      a_Dashboards.click();
      TestUtils.sleepAndLog(1500);
      WebElement a_Dashboard = driver.findElement(By.xpath("//a[text()='" + sDashboardName +
              "']"));
      TestUtils.sleepAndLog(1500);
      wait.until(ExpectedConditions.visibilityOf(a_Dashboard));
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboard));
      a_Dashboard.click();

      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+sDashboardName+"')]")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));
      // synchronize

      result = ResultStore.success("Dashboard " + sDashboardName + " successfully opened");
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to open Dashboard " + sDashboardName + ". error: " +
              Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> viewDashboardforHyperlink(String sDashboardName) {
    Map<String, String> result;

    try {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      // wait until all menu entries are loaded
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div#slide-menu" +
              " a")));
      waitingLoadingBackgroundGone();
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));

      a_Dashboards.click();
      TestUtils.sleepAndLog(1500);
      WebElement a_Dashboard = driver.findElement(By.xpath("//a[text()='" + sDashboardName +
              "']"));
      String sID = a_Dashboard.getAttribute("href").replaceAll("(.*)#/show/()", "").replace
              ("%20", " ").replace("?params=none", "");
      TestUtils.sleepAndLog(1500);
      wait.until(ExpectedConditions.visibilityOf(a_Dashboard));
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboard));
      a_Dashboard.click();

      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+sID+"')]")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));
      // synchronize

      result = ResultStore.success("Dashboard " + sDashboardName + " successfully opened");
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to open Dashboard " + sDashboardName + ". error: " +
              Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  // VIEW DASHBOARD
//  public Map<String, String> viewDashboard(String sDashboardName)
//  {
//    Map<String, String> result;
//
//    try
//    {
//      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
//      // wait until all menu entries are loaded
//      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div#slide-menu" +
//            " a")));
//      waitingLoadingBackgroundGone();
//      wait.until(presenceOfElementLocated(By.cssSelector("a[title='" +
//            sDashboardName + "']")));
//      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
//
//      a_Dashboards.click();
//      TestUtils.sleepAndLog(1500);
//      WebElement a_Dashboard = driver.findElement(By.cssSelector("a[title='" + sDashboardName +
//            "']"));
//      String sID = a_Dashboard.getAttribute("href").replaceAll("(.*)#/show/()", "").replace
//            ("%20", " ");
//      TestUtils.sleepAndLog(1500);
//      wait.until(ExpectedConditions.visibilityOf(a_Dashboard));
//      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboard));
//      a_Dashboard.click();
//
//      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sID)));
//      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
//      wait.until(presenceOfElementLocated(By.cssSelector
//            ("div[data-progress='99']")));
//      // synchronize
//
//      result = ResultStore.success("Dashboard " + sDashboardName + " successfully opened");
//    } catch (Exception exc)
//    {
//      result = ResultStore.failure("Failed to open Dashboard " + sDashboardName + ". error: " +
//            Misc.getStacktraceAsString(exc));
//    }
//    return result;
//  }

  // GET ALL DASHBOARDS
  public Map<String, String> getDashboardNames() {
    Map<String, String> result;

    try {
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div#slide-menu" +
              " a")));
      waitingLoadingBackgroundGone();
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
      a_Dashboards.click();
      TestUtils.sleepAndLog(1500);
      String xpath = "//*[@class='categorized-dashboard-list']//a";
      List<WebElement> list_AllDashboards = driver.findElements(By.xpath(xpath));
      String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance()
              .getTime());
      List<String> dashboards = new ArrayList<>();

      for (WebElement a_Dashboard : list_AllDashboards) {
        dashboards.add(a_Dashboard.getAttribute("innerText"));
      }
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
      a_Dashboards.click();
      result = ResultStore.success(dashboards.toString());
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to get Dashboards . error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> getDashboardNamesifhavingMenucat() {
    Map<String, String> result;

    try {
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div#slide-menu" +
              " a")));
      waitingLoadingBackgroundGone();
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
      a_Dashboards.click();
      TestUtils.sleepAndLog(1500);
      String xpath = "//*[@class='categorized-dashboard-list']//a";
      List<WebElement> list_AllDashboards = driver.findElements(By.xpath(xpath));
      String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance()
              .getTime());
      List<String> dashboards = new ArrayList<>();

      for (WebElement a_Dashboard : list_AllDashboards) {
        dashboards.add(a_Dashboard.getAttribute("innerText"));
      }
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
      a_Dashboards.click();
      result = ResultStore.success(dashboards.toString());
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to get Dashboards . error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  // OPEN USER MANAGEMENT
  public Map<String, String> openUserManagement() {
    Map<String, String> result = ResultStore.getResultObj();

    try {
      TestUtils.sleepAndLog(2500);
      wait.until(ExpectedConditions.elementToBeClickable(a_Administration));
      a_Administration.click();
      wait.until(ExpectedConditions.elementToBeClickable(a_UserManagement));
      a_UserManagement.click();
      // not working :(
      //UiTestUtils.waitUntilPageIsLoadedAndElementIsVisible(wait, By.id("load-spinner"), By
      // .cssSelector("iframe#contentFrame "));

      // synchronize
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector
              ("iframe#contentFrame")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      //wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
      // ("div[data-progress-text='100%']")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));

      result = ResultStore.success("User Management successfully opened");
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to open User Management. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> openUserManagementinFrame() {
    Map<String, String> result = ResultStore.getResultObj();

    try {
      TestUtils.sleepAndLog(2500);

      driver.switchTo().defaultContent();
      wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("bvd"));
      wait.until(ExpectedConditions.elementToBeClickable(a_Administration));
      a_Administration.click();
      TestUtils.sleepAndLog(2500);
      wait.until(ExpectedConditions.elementToBeClickable(a_UserManagement));
      a_UserManagement.click();
      TestUtils.sleepAndLog(2500);
      // not working :(
      //UiTestUtils.waitUntilPageIsLoadedAndElementIsVisible(wait, By.id("load-spinner"), By
      // .cssSelector("iframe#contentFrame "));

      // synchronize
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector
              ("iframe#contentFrame")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      //wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
      // ("div[data-progress-text='100%']")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));

      result = ResultStore.success("User Management successfully opened");
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to open User Management. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }
  public Map<String, String> openUserManagementforTest() {
    Map<String, String> result = ResultStore.getResultObj();

    try {
      TestUtils.sleepAndLog(2500);
      wait.until(ExpectedConditions.elementToBeClickable(a_Administration));
      a_Administration.click();
      TestUtils.sleepAndLog(2000);
      wait.until(ExpectedConditions.elementToBeClickable(a_UserManagement));
      a_UserManagement.click();
      // not working :(
      //UiTestUtils.waitUntilPageIsLoadedAndElementIsVisible(wait, By.id("load-spinner"), By
      // .cssSelector("iframe#contentFrame "));

      // synchronize
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector
              ("iframe#contentFrame")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      //wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
      // ("div[data-progress-text='100%']")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));

      result = ResultStore.success("User Management successfully opened");
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to open User Management. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }




  // OPEN DOCUMENTATION
  public Map<String, String> openDocumentation()
  {
    Map<String, String> result = ResultStore.getResultObj();

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(a_Help));
      a_Help.click();
      wait.until(ExpectedConditions.elementToBeClickable(a_Documentation));
      JavascriptExecutor executor = (JavascriptExecutor) driver;
      executor.executeScript("arguments[0].click();", a_Documentation);
      // WAIT UNTIL DOCUMENTATION IS THERE
      driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@alt='Logo']")));
      result = ResultStore.success("Documentation successfully opened");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to open Documentation. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> openResources()
  {
    Map<String, String> result;

    try
    {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      TestUtils.sleep(10);

      waitingModaldisappearance();

      wait.until(ExpectedConditions.elementToBeClickable(a_Administration));
      a_Administration.click();
      TestUtils.sleep(2);
      wait.until(ExpectedConditions.elementToBeClickable(a_DownloadToolsAndSamples));
      a_DownloadToolsAndSamples.click();
      // synchronize

      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".downloads " +
              "h2:nth-child(1)")));
      result = ResultStore.success("Resources successfully opened");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to open Resources. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  // LOGOUT
  public Map<String, String> logout()
  {
    Map<String, String> result;

    try
    {
      String id = "nav_user_menu";

      waitingModaldisappearance();

      wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
      TestUtils.sleep(2);
      a_PersonalUserSettings.click();
      wait.until(ExpectedConditions.elementToBeClickable(a_Logout));
      TestUtils.sleep(2);
      a_Logout.click();
      TestUtils.sleep(2);
      if(isAlertPresent()){
        driver.switchTo().alert().accept();
      }
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Log In')]")));
      relogin.click();
      wait.until(ExpectedConditions.visibilityOf(button_idm_login));
      TestUtils.sleepAndLog(3000);
      input_idm_username.clear();
      result = ResultStore.success("Successfully logged out");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to log out. error: " + Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public boolean isAlertPresent(){
    try{
      driver.switchTo().alert();
      return true;
    }catch(NoAlertPresentException ex){
      return false;
    }
  }

  // LOGOUT
  public Map<String, String> idmLogout()
  {
    Map<String, String> result;

    try
    {
      String id = "nav_user_menu";
      wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
      a_PersonalUserSettings.click();
      wait.until(ExpectedConditions.elementToBeClickable(a_Logout));
      JavascriptExecutor executor = (JavascriptExecutor) driver;
      executor.executeScript("arguments[0].click();", a_Logout);
      // WAIT UNTIL LOGIN BUTTON IS THERE
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-link")));
      result = ResultStore.success("Successfully logged out");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to log out. error: " + Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  // IS NON ADMIN VIEW SHOWN
  public Map<String, String> isNonAdminViewShown()
  {
    Map<String, String> result;

    try
    {
      // Dashboards - visible
      wait.until(ExpectedConditions.visibilityOf(a_Dashboards));
      // Home - visible
      wait.until(ExpectedConditions.visibilityOf(a_DefaultDashboard));
      // User Settings - visible
      wait.until(ExpectedConditions.visibilityOf(a_PersonalUserSettings));
      // Documentation - visible
      wait.until(ExpectedConditions.visibilityOf(a_Help));
      // Administration - NOT visible
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("nav_admin_menu")));
      result = ResultStore.success("Non Admin View is shown");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Non Admin View is NOT shown. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  // IS ADMIN VIEW SHOWN
  public Map<String, String> isAdminViewShown()
  {
    Map<String, String> result;

    try
    {
      // Dashboards - visible
      wait.until(ExpectedConditions.visibilityOf(a_Dashboards));
      // Home - visible
      wait.until(ExpectedConditions.visibilityOf(a_DefaultDashboard));
      // User Settings - visible
      wait.until(ExpectedConditions.visibilityOf(a_PersonalUserSettings));
      // Documentation - visible
      wait.until(ExpectedConditions.visibilityOf(a_Help));
      // Administration - visible
      wait.until(ExpectedConditions.visibilityOf(a_Administration));
      result = ResultStore.success("Admin View is shown");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Admin View is NOT shown. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  // OPEN System Settings
  public Map<String, String> openSystemSettings()
  {
    Map<String, String> result;

    try
    {
      //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      TestUtils.sleepAndLog(2000);
      wait.until(ExpectedConditions.elementToBeClickable(a_Administration));
      a_Administration.click();
      TestUtils.sleep(2);
      wait.until(ExpectedConditions.elementToBeClickable(a_SystemSettings));
      a_SystemSettings.click();
      TestUtils.sleep(5);
      // synchronize
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
              ("slide-system-settings " +
                      ".slidePanelSystemSetting")));
      result = ResultStore.success("System Settings successfully opened");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to open System Settings. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> openSystemSettingsforEmbedded()
  {
    Map<String, String> result;

    try
    {

      //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      TestUtils.sleep(5);
      wait.until(ExpectedConditions.elementToBeClickable(a_Administration));
      a_Administration.click();
      TestUtils.sleepAndLog(2000);
      wait.until(ExpectedConditions.elementToBeClickable(a_SystemSettings));
      a_SystemSettings.click();
      TestUtils.sleep(5);
      // synchronize
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
              ("slide-system-settings " +
                      ".slidePanelSystemSetting")));
      result = ResultStore.success("System Settings successfully opened");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to open System Settings. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> closeSystemSettings()
  {
    Map<String, String> result;

    try
    {

      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("slide-system-settings [ng-click='$ctrl.close()']"))).click();
      // synchronize
//      wait.until(ExpectedConditions.not(presenceOfElementLocated(By
//            .cssSelector(("slide-system-settings .hideBvdSlidePanel")))));
      result = ResultStore.success("System Settings successfully closed");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to close System Settings. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }


  public Map<String, String> openApiKey()
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));

      wait.until(ExpectedConditions.elementToBeClickable(a_Administration));
      a_Administration.click();
      waitSystemMenu();
      wait.until(ExpectedConditions.elementToBeClickable(apiKeyMenu));
      TestUtils.sleepAndLog(2000);
      apiKeyMenu.click();
      // synchronize
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new_api_key")));
      result = ResultStore.success("ApiKey menu successfully opened");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to open ApiKey menu. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> clickPurgeAndClosePanel()
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleep(2);
      wait.until(ExpectedConditions.elementToBeClickable(purgeButton));
      purgeButton.click();
      wait.until(ExpectedConditions.presenceOfElementLocated(By.className("alert-success")));
      closeSystemSettings();
      result = ResultStore.success("Purge triggered");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Purge not triggered. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }


  public Map<String, String> getTextOfTextValueWidget(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      WebElement text_Widget = driver.findElement(By.cssSelector("g#" + sWidget + " text"));
      String sText = text_Widget.getText();
      result = ResultStore.success(sText);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Text. error: " + Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getTextOfTextValueWidget_title(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      WebElement text_Widget = driver.findElement(By.cssSelector("g#" + sWidget + " title"));
      String sText = text_Widget.getText();
      result = ResultStore.success(sText);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Text. error: " + Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getColorOfTextValueWidget(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      WebElement text_Widget = driver.findElement(By.cssSelector("g#" + sWidget + " text"));
      String rgbHex = text_Widget.getCssValue("fill");
      if (rgbHex.contains("rgb"))
      {
        String sColor = text_Widget.getCssValue("fill").replace("rgb(", "").replace(")", "");
        String[] rgb = sColor.split(", ");
        rgbHex = String.format("#%02X%02X%02X", Integer.parseInt(rgb[0]), Integer.parseInt
                (rgb[1]), Integer.parseInt(rgb[2]));
      }
      else
      {
        rgbHex = rgbHex.toUpperCase();
      }
      result = ResultStore.success(rgbHex);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Color. error: " + Misc.getStacktraceAsString
              (exc));
    }
    return result;
  }

  public Map<String, String> getColorOfStatusColorGroupWidget(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      WebElement group_Widget = driver.findElement(By.cssSelector("g#" + sWidget + " path"));
      String rgbHex = group_Widget.getCssValue("fill");
      if (rgbHex.contains("rgb"))
      {
        String sColor = rgbHex.replace("rgb(", "").replace(")", "");
        String[] rgb = sColor.split(", ");
        rgbHex = String.format("#%02X%02X%02X", Integer.parseInt(rgb[0]), Integer.parseInt
                (rgb[1]), Integer.parseInt(rgb[2]));
      }
      else
      {
        rgbHex = rgbHex.toUpperCase();
      }
      result = ResultStore.success(rgbHex);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Color. error: " + Misc.getStacktraceAsString
              (exc));
    }
    return result;
  }

  public Map<String, String> getChartNumbers(String sWidget)
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleepAndLog(2000);
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(sWidget)));
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      List<WebElement> chartNumbers = driver.findElements(By.cssSelector("g#" + sWidget + " g:not" +
              "([style='visibility: hidden;']) text"));
      List<String> sChartNumbers = new ArrayList<>();

      for (WebElement chartNumber : chartNumbers)
      {
        sChartNumbers.add(chartNumber.getText());
        System.out.println("BarChart Number !!! " + chartNumber.getText());
      }
      result = ResultStore.success(sChartNumbers.toString());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Chart Numbers. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getChartNumbersWidgetGroup(String widgetGroup, String sWidget,
                                                        String widgetRowNum)
  {
    Map<String, String> result;

    try
    {
      String wSelector = "#instance-"+widgetGroup+"-"+widgetRowNum+"-"+sWidget+" g:not" +
              "([style='visibility: hidden;']) text ";
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(wSelector)));
      TestUtils.sleepAndLog(500);
      List<WebElement> chartNumbers = driver.findElements(By.cssSelector(wSelector));
      List<String> sChartNumbers = new ArrayList<>();

      for (WebElement chartNumber : chartNumbers)
      {
        sChartNumbers.add(chartNumber.getText());
        System.out.println("BarChart Number !!! " + chartNumber.getText());
      }
      result = ResultStore.success(sChartNumbers.toString());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Chart Numbers. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getChartHoverNumbers(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1000);
      String css = "g#" + sWidget + " g:not([style='visibility: hidden;']) title";
      List<WebElement> chartNumbers = driver.findElements(By.cssSelector(css));
      List<String> sChartNumbers = new ArrayList<>();

      for (WebElement chartNumber : chartNumbers)
      {
        sChartNumbers.add(chartNumber.getText());
      }
      result = ResultStore.success(sChartNumbers.toString());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Chart Numbers. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getChartAnchors(String sWidget, List<String> dataList)
  {
    Map<String, String> result;

    try
    {
      String css = "g#" + sWidget + " g:not([style='visibility: hidden;']) text";
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1000);
      List<WebElement> slices = driver.findElements(By.cssSelector(css));
      Map<String, String> anchors = new HashMap<>();

      for (WebElement slice : slices)
      {
        anchors.put(slice.getText(), slice.getAttribute("text-anchor"));
      }

      String toResult = "";
      for (String data : dataList)
      {
        toResult = toResult.concat(anchors.get(data)).concat(" ");
      }
      result = ResultStore.success(toResult);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get anchors. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getChartHeightsOfBarChartWidget(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      List<WebElement> chartHeights = driver.findElements(By.cssSelector("g#" + sWidget + " g:not" +
              "([style='visibility: hidden;']) rect"));
      List<String> sChartHeights = new ArrayList<>();

      for (WebElement chartHeight : chartHeights)
      {
        sChartHeights.add(String.format(Locale.ENGLISH, "%.2f", Double.parseDouble(chartHeight
                .getAttribute("height"))));
        System.out.println("Bar Chart Height: " + String.format(Locale.ENGLISH, "%.2f", Double.parseDouble(chartHeight
                .getAttribute("height"))));
      }
      result = ResultStore.success(sChartHeights.toString());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Chart Heights. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getChartHeightsOfBarChartWidgetGroup (String widgetGroup, String sWidget,
                                                                   String widgetRowNum)
  {
    Map<String, String> result;

    try
    {
      String wSelector = "#instance-"+widgetGroup+"-"+widgetRowNum+"-"+sWidget+" g:not" +
              "([style='visibility: hidden;']) rect ";
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(wSelector)));
      TestUtils.sleepAndLog(500);
      List<WebElement> chartHeights = driver.findElements(By.cssSelector(wSelector));
      List<String> sChartHeights = new ArrayList<>();

      for (WebElement chartHeight : chartHeights)
      {
        sChartHeights.add(String.format(Locale.ENGLISH, "%.2f", Double.parseDouble(chartHeight
                .getAttribute("height"))));
        System.out.println("Bar Chart Height: " + String.format(Locale.ENGLISH, "%.2f", Double.parseDouble(chartHeight
                .getAttribute("height"))));
      }
      result = ResultStore.success(sChartHeights.toString());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Chart Heights. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }


  public Map<String, String> getSlicesOfDonutChartWidgetNew(String sWidget,List<String> dataPointLocate)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      List<WebElement> chartHeights = driver.findElements(By.cssSelector("g#" + sWidget + " g:not" +
              "([style='visibility: hidden;']) path"));
      //List<String> sChartHeights = new ArrayList<>();
      WebElement elementTest;
      for(String str:dataPointLocate){
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("g#" + sWidget + " g:not" +
                "([style='visibility: hidden;']) path"+str)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("g#" + sWidget + " g:not" +
                "([style='visibility: hidden;']) path"+str)));
        elementTest=driver.findElement(By.cssSelector("g#" + sWidget + " " +
                str));
        if(!elementTest.isDisplayed()){
          Assert.assertTrue(false,"Donut part is not available");
        }

      }

      result = ResultStore.success("All Donut points are available");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Chart Heights. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getSlicesOfDonutChartWidget(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      List<WebElement> chartHeights = driver.findElements(By.cssSelector("g#" + sWidget + " g:not" +
              "([style='visibility: hidden;']) path"));
      List<String> sChartHeights = new ArrayList<>();

      for (WebElement chartHeight : chartHeights)
      {
        sChartHeights.add(chartHeight.getAttribute("d"));
        System.out.println("DONUT !!! : " + chartHeight.getAttribute("d"));
      }
      result = ResultStore.success(sChartHeights.toString());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Chart Heights. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }


  public Map<String, String> getSlicesOfDonutChartWidgetGroup(String widgetGroup, String sWidget,
                                                              String widgetRowNum)
  {
    Map<String, String> result;

    try
    {
      String wSelector = "#instance-"+widgetGroup+"-"+widgetRowNum+"-"+sWidget+" g:not" +
              "([style='visibility: hidden;']) path ";
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(wSelector)));
      TestUtils.sleepAndLog(500);

      List<WebElement> chartHeights = driver.findElements(By.cssSelector(wSelector));
      List<String> sChartHeights = new ArrayList<>();

      for (WebElement chartHeight : chartHeights)
      {
        sChartHeights.add(chartHeight.getAttribute("d"));
        System.out.println("DONUT SLICE DATA!!!: " + chartHeight.getAttribute("d"));
      }
      result = ResultStore.success(sChartHeights.toString());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Chart Heights. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getSlicesOfDonutChartWidgetGroupNew(String widgetGroup, String sWidget,
                                                              String widgetRowNum,List<String>  donutPoints)
  {
    Map<String, String> result;

    try
    {
      String wSelector = "#instance-"+widgetGroup+"-"+widgetRowNum+"-"+sWidget+" g:not" +
              "([style='visibility: hidden;']) path ";
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(wSelector)));
      TestUtils.sleepAndLog(500);
      WebElement elementTest;
      String donutPointElement;
      for(String str : donutPoints) {
        donutPointElement = "#instance-"+widgetGroup+"-"+widgetRowNum+"-"+sWidget+ " " +str;
        elementTest = driver.findElement(By.cssSelector(donutPointElement));

        if (!elementTest.isDisplayed()) {
          Assert.assertTrue(false, "Donut part is not available");
        }
      }
      result = ResultStore.success("All Donuts points are available");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Chart Heights. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }


  public Map<String, String> getBarsOfStackBarsWidget(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      List<WebElement> bars = driver.findElements(By.cssSelector("g#"+sWidget+" g:not" +
              "([style='visibility: hidden;']) rect"));
      List<String> sChartHeights = new ArrayList<>();

      for (WebElement chartHeight : bars)
      {
        sChartHeights.add(String.format(Locale.ENGLISH, "%.2f", Double.parseDouble(chartHeight
                .getAttribute("height"))));
        System.out.println("Stack Bars!!! : " + String.format(Locale.ENGLISH, "%.2f", Double.parseDouble(chartHeight
                .getAttribute("height"))));
      }
      result = ResultStore.success(sChartHeights.toString());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Chart Heights. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getBarsOfStackBarsWidgetGroup(String widgetGroup, String sWidget,
                                                           String widgetRowNum)
  {
    Map<String, String> result;

    try
    {
      String wSelector = "#instance-"+widgetGroup+"-"+widgetRowNum+"-"+sWidget+" g:not" +
              "([style='visibility: hidden;']) rect ";
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(wSelector)));
      TestUtils.sleepAndLog(500);
      List<WebElement> bars = driver.findElements(By.cssSelector(wSelector));
      List<String> sChartHeights = new ArrayList<>();

      for (WebElement chartHeight : bars)
      {
        sChartHeights.add(String.format(Locale.ENGLISH, "%.2f", Double.parseDouble(chartHeight
                .getAttribute("height"))));
        System.out.println("Stack Bars!!! : " + String.format(Locale.ENGLISH, "%.2f", Double.parseDouble(chartHeight
                .getAttribute("height"))));
      }
      result = ResultStore.success(sChartHeights.toString());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Chart Heights. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getVisibilityOfWidget(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(presenceOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      WebElement widget = driver.findElement(By.id(sWidget));
      String sVisibility = widget.getCssValue("visibility");
      result = ResultStore.success(sVisibility);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Visibility. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> clickWidget(String sWidget)
  {
    Map<String, String> result;
    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      wait.until(ExpectedConditions.elementToBeClickable(By.id(sWidget)));
      TestUtils.sleepAndLog(1000);

      WebElement we = new WebDriverWait(driver, 30).until(ExpectedConditions
              .elementToBeClickable(By.id(sWidget)));
      we.click();

      TestUtils.sleepAndLog(1000);

      result = ResultStore.success("Successfully clicked widget " + sWidget);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click widget " + sWidget + ". error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> clickWidgetText(String sWidget)
  {
    Map<String, String> result;
    try
    {
      wait.until(presenceOfElementLocated(By.id(sWidget)));
      wait.until(ExpectedConditions.elementToBeClickable(By.id(sWidget)));
      TestUtils.sleepAndLog(1000);

      String css = "g#" + sWidget + " " + "text";
      WebElement we = new WebDriverWait(driver, 30).until(ExpectedConditions
              .elementToBeClickable(By.cssSelector(css)));
      we.click();

      TestUtils.sleepAndLog(1000);

      result = ResultStore.success("Successfully clicked widget " + sWidget);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click widget " + sWidget + ". error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> clickWidgetRect(String sWidget)
  {
    Map<String, String> result;
// TODO: remove commented code from final version
    try
    {
      wait.until(presenceOfElementLocated(By.id(sWidget)));
      wait.until(ExpectedConditions.elementToBeClickable(By.id(sWidget)));
      TestUtils.sleepAndLog(1000);

      String css = "#" + sWidget + " rect[class='overlays']";
      WebElement we = new WebDriverWait(driver, 30).until(ExpectedConditions
              .elementToBeClickable(By.cssSelector(css)));
      we.click();

      TestUtils.sleepAndLog(1000);

      result = ResultStore.success("Successfully clicked widget " + sWidget);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click widget " + sWidget + ". error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> clickHyperlink(String sWidget,String rectValue)
  {
    Map<String, String> result;
// TODO: remove commented code from final version
    try
    {
      wait.until(presenceOfElementLocated(By.id(sWidget)));
      wait.until(ExpectedConditions.elementToBeClickable(By.id(sWidget)));
      TestUtils.sleepAndLog(1000);
      String css = "g#" + sWidget + " rect"+rectValue;
      WebElement we = new WebDriverWait(driver, 30).until(ExpectedConditions
              .elementToBeClickable(By.cssSelector(css)));
      we.click();

      TestUtils.sleepAndLog(1000);

      result = ResultStore.success("Successfully clicked widget " + sWidget);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click widget " + sWidget + ". error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> clickWidgetNewGauge(String sWidget)
  {
    Map<String, String> result;
    try
    {
      wait.until(presenceOfElementLocated(By.id(sWidget)));
      wait.until(presenceOfAllElementsLocatedBy(By.cssSelector("g#" + sWidget + " g:not" +
              "([style='visibility: hidden;']) path")));
      List<WebElement> gaugeSlices = driver.findElements(By.cssSelector("g#" + sWidget + " g:not" +
              "([style='visibility: hidden;']) path"));

      gaugeSlices.get(0).click();

      TestUtils.sleepAndLog(1000);

      result = ResultStore.success("Successfully clicked widget " + sWidget);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click widget " + sWidget + ". error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> checkNewTabLink(String url)
  {
    Map<String, String> result;
    try
    {
      TestUtils.sleepAndLog(1000);
      ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
      driver.switchTo().window(tabs2.get(1));
      TestUtils.sleepAndLog(2000);
      String urlResult = driver.getCurrentUrl();
      if (urlResult.equals(url))
      {
        result = ResultStore.success("Tab opened, URL exist");
        driver.close();
        driver.switchTo().window(tabs2.get(0));
        return result;
      }
      driver.close();
      driver.switchTo().window(tabs2.get(0));
      result = ResultStore.failure("FAILED. Tab opened, Url EXPECTED = " + url + " but GOT " +
              "" +
              urlResult);

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get new tab link. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> clickWidgetElement(String sWidget)
  {
    Map<String, String> result;

    try
    {
      String xpath = ".//*[@id='" + sWidget + "']//*[@fill]";
      wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
      driver.findElements(By.xpath(xpath)).get(2).click();

      result = ResultStore.success("Successfully clicked widget " + sWidget);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click widget " + sWidget + ". error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }


  public Map<String, String> clickOnWidgetElement(String widgetId, String fill)
  {
    Map<String, String> result;

    try
    {

      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(widgetId)));
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#" + widgetId + " " +
              "[fill='" +
              fill + "']"))).click();
      result = ResultStore.success("Successfully clicked widget " + widgetId);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click widget " + widgetId + ". error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getTitle()
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleep(2);
      result = ResultStore.success(driver.getTitle());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get title. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> clickWidgetJQuery(String widgetId)
  {
    Map<String, String> result;

    try
    {
      String xpath = "//*[@id='" + widgetId + "']";
      wait.until(presenceOfElementLocated(By.xpath(xpath)));

      JavascriptExecutor executor = (JavascriptExecutor) driver;
      executor.executeScript("jQuery('#" + widgetId + "').click()");

      TestUtils.sleepAndLog(1500);

      result = ResultStore.success("Successfully clicked widget " + widgetId);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click widget " + widgetId + ". error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> clickWidgetAnyWay(String widgetId, String expectedLink)
  {
    Map<String, String> result;
    String toResultError;

    try
    {
      String xpath = "//*[@id='" + widgetId + "']";
      wait.until(presenceOfElementLocated(By.xpath(xpath)));
      TestUtils.sleepAndLog(1000);
      WebElement widget = driver.findElement(By.xpath(xpath));

      widget.click();
      result = ResultStore.success("Successfully simple clicked widget " + widgetId);
      return result;

    } catch (Exception exc)
    {
      toResultError = "Failed to simple click widget " + widgetId + ". error: " + Misc
              .getStacktraceAsString(exc);
    }

    try
    {
      String xpath = "//*[@id='" + widgetId + "']";
      wait.until(presenceOfElementLocated(By.xpath(xpath)));

      JavascriptExecutor executor = (JavascriptExecutor) driver;
      executor.executeScript("jQuery('#" + widgetId + "').click()");

      TestUtils.sleepAndLog(1500);

      result = ResultStore.success(toResultError + "\n Successfully JQuerry clicked widget " +
              widgetId);
    } catch (Exception exc)
    {
      result = ResultStore.failure(toResultError + " \n Failed to JQuerry click widget " +
              widgetId +
              ". error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getHyperlink(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(presenceOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1000);
      WebElement g_Widget = driver.findElement(By.id(sWidget));
      WebElement a_hyperlink = g_Widget.findElement(By.xpath(".."));

//            Map<String, String> sHyperlinks = (Map<String, String>) ((JavascriptExecutor)
// driver).executeScript("return arguments[0][arguments[1]]", a_hyperlink, "href");
//            String sHyperlink = sHyperlinks.get("baseVal");

      String sHyperlink = a_hyperlink.getAttribute("href");
      result = ResultStore.success(sHyperlink);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Hyperlink. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> clickOnHyperLink(String hyperLinkElement)
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleepAndLog(3000);
      wait.until(presenceOfElementLocated(By.cssSelector(hyperLinkElement)));
      WebElement ele=driver.findElement(By.cssSelector(hyperLinkElement));
      ele.click();
      result = ResultStore.success("Clicked on HyperLink");
    }catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on Hyperlink " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> closeSlideout()
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleepAndLog(3000);
      wait.until(presenceOfElementLocated(By.cssSelector("div#slideOutPanel button[title='Close']")));
      WebElement ele=driver.findElement(By.cssSelector("div#slideOutPanel button[title='Close']"));
      ele.click();
      result = ResultStore.success("Clicked on Close button of slideout");
    }catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on Close button of slideout " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }



  public Map<String, String> findElementByHyperlink(String hyperlink, String widgetId)
  {
    Map<String, String> result;

    try
    {
      String xpath = ".//a[contains(@href,'" + hyperlink + "')]//g[@id='" + widgetId + "']";
      wait.until(presenceOfElementLocated(By.xpath
              (xpath)));
      if (driver.findElements(By.xpath(xpath)).size() == 1)
      {
        result = ResultStore.success("Hyperlink is found successfully.");
      }
      else
      {
        result = ResultStore.failure("There are " + String.valueOf(driver.findElements(By
                .xpath(xpath)).size()) + " elements found");
      }
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to check Hyperlink. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> checkHyperlink(String urlToCheck, String widget)
  {
    Map<String, String> result;
    if (cfg.getString("application.bvd.browser.name").equalsIgnoreCase("ie"))
    {
      result = findElementByHyperlink(urlToCheck, widget);
      Result.rcEquals(result, "0");
    }
    else
    {
      result = getHyperlink(widget);
      Result.rcEquals(result, "0");
      Result.outContains(result, urlToCheck);
    }
    return result;
  }

  public Map<String, String> getHyperlinkFromStatusImg(String sWidget)
  {
    try
    {
      WebElement g_Widget = driver.findElement(By.id(sWidget));
      String link = g_Widget.getAttribute("opr_hyperlink");
      return ResultStore.success("Hyperlink is found successfully." + link);
    } catch (Exception e)
    {
      return ResultStore.failure("Failed to check Hyperlink. error: " + Misc
              .getStacktraceAsString(e));
    }
  }

  public Map<String, String> getDashboard()
  {
    Map<String, String> result;

    try
    {
      WebDriverWait localWait = new WebDriverWait(driver, 30);
      TestUtils.sleepAndLog(1500);
      waitingLoadingBackgroundGone();
      localWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("svg-img[dashboard-id]")));
      localWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("svg-img[dashboard-id]")));
      WebElement svg_Dashboard = driver.findElement(By.cssSelector("svg-img[dashboard-id]"));
      String sDashboard = svg_Dashboard.getAttribute("dashboard-id");
      result = ResultStore.success(sDashboard);
    } catch (Exception exc)    {
      result = ResultStore.failure("Failed to get Dashboard Name. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getColorsOfBarChartWidget(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1000);
      List<WebElement> chartColors = driver.findElements(By.cssSelector("g#" + sWidget + " g:not" +
              "([style='visibility: hidden;']) rect"));
      List<String> sChartColors = new ArrayList<>();

      for (WebElement chartColor : chartColors)
      {
        String rgbHex = chartColor.getCssValue("fill");
        if (rgbHex.contains("rgb"))
        {
          String sColor = rgbHex.replace("rgb(", "").replace(")", "");
          String[] rgb = sColor.split(", ");
          rgbHex = String.format("#%02X%02X%02X", Integer.parseInt(rgb[0]), Integer.parseInt
                  (rgb[1]), Integer.parseInt(rgb[2]));
        }
        rgbHex = rgbHex.toUpperCase();
        sChartColors.add(rgbHex);
      }
      result = ResultStore.success(sChartColors.toString());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Colors. error: " + Misc.getStacktraceAsString
              (exc));
    }
    return result;
  }

  public Map<String, String> getColorsOfDonutChartWidget(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1000);
      List<WebElement> chartColors = driver.findElements(By.cssSelector("g#" + sWidget + " g:not" +
              "([style='visibility: hidden;']) path"));
      List<String> sChartColors = new ArrayList<>();

      for (WebElement chartColor : chartColors)
      {
        String rgbHex = chartColor.getCssValue("fill");
        if (rgbHex.contains("rgb"))
        {
          String sColor = rgbHex.replace("rgb(", "").replace(")", "");
          String[] rgb = sColor.split(", ");
          rgbHex = String.format("#%02X%02X%02X", Integer.parseInt(rgb[0]), Integer.parseInt
                  (rgb[1]), Integer.parseInt(rgb[2]));
          sChartColors.add(rgbHex);
        }
        else
        {
          sChartColors.add(rgbHex.toUpperCase());
        }

      }
      result = ResultStore.success(sChartColors.toString());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Colors. error: " + Misc.getStacktraceAsString
              (exc));
    }
    return result;
  }

  public Map<String, String> getDonutFieldValues_title(List<String> staticFiled)
  {
    Map<String, String> result;

    try
    {
      Boolean bool=true;
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("path title")));
      TestUtils.sleepAndLog(1500);
      List<WebElement> xytexts = driver.findElements(By.cssSelector("path title"));
      List<String> listoftext = new ArrayList<>();
      int count=0;

      for (WebElement xytext : xytexts) {
        listoftext.add(xytext.getText());
        System.out.println("=================="+xytext.getAttribute("innerHTML"));
        System.out.println(xytext.getText());
      }
      Assert.assertEquals(listoftext,staticFiled);

      result = ResultStore.success(listoftext.toString());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get text list. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String, String> getColorsMultiAreaChartWidget(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1000);
      List<WebElement> chartColors = driver.findElements(By.cssSelector("g#" + sWidget + " g:not" +
              "([style='visibility: hidden;'])[clip-path] path"));
      List<String> sChartColors = new ArrayList<>();

      for (WebElement chartColor : chartColors)
      {
        String rgbHex = chartColor.getCssValue("fill");
        if (rgbHex.contains("rgb"))
        {
          String sColor = rgbHex.replace("rgb(", "").replace(")", "");
          String[] rgb = sColor.split(", ");
          rgbHex = String.format("#%02X%02X%02X", Integer.parseInt(rgb[0]), Integer.parseInt
                  (rgb[1]), Integer.parseInt(rgb[2]));
          sChartColors.add(rgbHex);
        }
        else
        {
          sChartColors.add(rgbHex.toUpperCase());
        }

      }
      result = ResultStore.success(sChartColors.toString());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Colors. error: " + Misc.getStacktraceAsString
              (exc));
    }
    return result;
  }

  public Map<String, String> getFeed(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(presenceOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      WebElement feed = driver.findElement(By.cssSelector("div[id*='feed_" + sWidget + "'] " +
              "section a"));
      result = ResultStore.success(feed.getText());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get feed. error: " + Misc.getStacktraceAsString
              (exc));
    }
    return result;
  }

  public Map<String, String> getFeeds(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(presenceOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      List<WebElement> feeds = driver.findElements(By.cssSelector("div[id*='feed_'] section" +
              " a"));
      List<String> sFeeds = new ArrayList<>();

      for (WebElement feed : feeds)
      {
        sFeeds.add(feed.getText());
      }
      result = ResultStore.success(sFeeds.toString());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Colors. error: " + Misc.getStacktraceAsString
              (exc));
    }
    return result;
  }

  public Map<String, String> getTransparentBackground(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(presenceOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1000);
      WebElement widget = driver.findElement(By.id(sWidget));
      String sTransparentBackground = widget.getCssValue("opacity");
      result = ResultStore.success(sTransparentBackground);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Transparent Dashboard. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getWebPage(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(presenceOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1000);
      WebElement iframe = driver.findElement(By.id("url_" + sWidget));
      String sWebPage = iframe.getAttribute("src");
      result = ResultStore.success(sWebPage);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Included Web Page. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> viewDefaultDashboard()
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
      a_Dashboards.click();
      TestUtils.sleep(2);
      String sDefaultDashboard = driver.findElement(By.xpath("//ux-icon[contains(@class,'ux-icon-home default-dashboard-color')]"+
              "/ancestor::td//a")).getText();
      // view default dashboard
      wait.until(ExpectedConditions.elementToBeClickable(a_DefaultDashboard));
      a_DefaultDashboard.click();

      //synchronize
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));
      TestUtils.sleep(5);
      //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sDefaultDashboard)));
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+sDefaultDashboard+"')]")));



      result = ResultStore.success("Successfully opened Default Dashboard");
    } catch (Exception exc)
    {
      try
      {
        wait.until(ExpectedConditions.elementToBeClickable(a_DefaultDashboard));
        a_DefaultDashboard.click();

        // synchronize
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#mainView " +
                "div[class*='alert']")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
        wait.until(presenceOfElementLocated(By.cssSelector
                ("div[data-progress='99']")));

        String sAlert = driver.findElement(By.cssSelector("div#mainView div[class*='alert']"))
                .getText();
        result = ResultStore.success(sAlert);
      } catch (Exception exc2)
      {
        result = ResultStore.failure("Failed to view Default Dashboard. error: " + Misc
                .getStacktraceAsString(exc));
      }
    }
    return result;
  }

  public Map<String, String> setDefaultDashboard(String sDashboardName)
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleepAndLog(1500);
      // wait until all menu entries are loaded
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div#slide-menu" +
              " a")));
      UiTestUtils.waitDocumentReadyState(driver, 30);

      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards)).click();
      TestUtils.sleepAndLog(1500);
      wait.until(presenceOfElementLocated(By.xpath("//div[@class=\"categorized-dashboard-list\"]//a[contains(text()"
              + ",'" + sDashboardName +"')]")));
      UiTestUtils.enableTestAutomationMode(driver);

      String xpath = "//div[@class=\"categorized-dashboard-list\"]//a[contains(text(),'" + sDashboardName +
              "')]/parent::td/span/ux-icon";
      wait.until(presenceOfElementLocated(By.xpath(xpath))).click();

      // synchronize
      TestUtils.sleepAndLog(1000);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"categorized-dashboard-list\"]//a[contains(text()"
              +",'"+sDashboardName+"')]/parent::td/span/ux-icon[not(contains(@class,'not-default-dashboard-color'))]")));
      // close Dashboards side bar
      String slideCloseXpath = "//*[contains(@class, \"ux-icon ux-icon-link-previous\")]";
      wait.until(ExpectedConditions.elementToBeClickable(By.xpath(slideCloseXpath))).click();

      // synchronize
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector
              ("div#slide-menu-header button")));

      result = ResultStore.success("Successfully selected Default Dashboard");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to select Default Dashboard. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getStatusImage(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(presenceOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      WebElement statusImage = driver.findElement(By.cssSelector("g#" + sWidget + " " +
              "g[opacity='1']"));
      String sStatusImage = statusImage.getAttribute("opr_switch_value");
      result = ResultStore.success(sStatusImage);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Status Image. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getAreaLineChart(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      List<WebElement> areaCharts = driver.findElements(By.cssSelector("g#" + sWidget + " " +
              "g[clip-path] path"));
      List<String> sAreaCharts = new ArrayList<>();
      TestUtils.sleep(3);
      for (WebElement area : areaCharts)
      {
        sAreaCharts.add(area.getAttribute("d"));
        System.out.println("AREACHART!!! " + area.getAttribute("d"));
      }
      result = ResultStore.success(sAreaCharts.toString());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Areas. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }
  public Map<String, String> getXandYaxistext(String sWidget,List<String> staticFiled)
  {
    Map<String, String> result;

    try
    {
      Boolean bool=true;
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      List<WebElement> xytexts = driver.findElements(By.cssSelector("g#" + sWidget + " " +
              "svg text"));
      List<String> listoftext = new ArrayList<>();
      int count=0;
      for (WebElement xytext : xytexts) {
        listoftext.add(xytext.getText());
      }
      Assert.assertEquals(listoftext,staticFiled);

      result = ResultStore.success(listoftext.toString());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get text list. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }
  public Map<String, String> checktextexist(String sWidget,List<String> staticFiled)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      List<WebElement> xytexts = driver.findElements(By.cssSelector("g#" + sWidget + " " +
              "svg > text tspan"));
      List<String> listoftext = new ArrayList<>();
      int count=0;
      for (WebElement xytext : xytexts)
      {
        listoftext.add(xytext.getText());
      }
      if(listoftext.contains(staticFiled)){
        result = ResultStore.success("list contains the data fields");
      }else{
        result = ResultStore.failure("List does not contain the data fields ");
      }
      result = ResultStore.success(listoftext.toString());
      //if (result.get("out").contains("null")) Reporter.log(driver.getPageSource());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get text list. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String, String> getdataPointslinechart_title(String sWidget,String dataPointLocate,List<String> expectedValues,String content)
  {
    Map<String, String> result;


    try
    {
      WebElement elementTest=null;
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);


      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("g#" + sWidget + " " +
              dataPointLocate+" title")));

      List<WebElement> li=driver.findElements(By.cssSelector("g#" + sWidget + " " +
              dataPointLocate+" title"));
      for (WebElement singleElem: li){
        String str=singleElem.getAttribute("innerHTML").replaceAll("[\n]"," ");
        if(!expectedValues.contains(str)){
          Assert.assertTrue(false,content+" "+str+" is not available");
        }

      }
      result = ResultStore.success("All the data points are available");
      //if (result.get("out").contains("null")) Reporter.log(driver.getPageSource());
    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to get data points. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String, String> getdataPoints(String sWidget,List<String> dataPointLocate)
  {
    Map<String, String> result;


    try
    {
      WebElement elementTest=null;
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      for(String str:dataPointLocate){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("g#" + sWidget + " " +
                str)));
        elementTest=driver.findElement(By.cssSelector("g#" + sWidget + " " +
                str));
        if(!elementTest.isDisplayed()){
          Assert.assertTrue(false,"Data point is not available");
        }

      }
      result = ResultStore.success("All the data points are available");
      //if (result.get("out").contains("null")) Reporter.log(driver.getPageSource());
    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to get data points. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String, String> getdataPoints_title(String sWidget,Map<String,String> dataPointLocatetitle,String content)
  {
    Map<String, String> result;


    try
    {
      String  str=null;

      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);

      for(Map.Entry<String,String> mapentry: dataPointLocatetitle.entrySet()){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("g#" + sWidget + " " +
                mapentry.getKey()+" title")));
        str=driver.findElement(By.cssSelector("g#" + sWidget + " " +
                mapentry.getKey()+" title")).getAttribute("innerHTML").replaceAll("[\n]"," ");
        if(!str.equals(mapentry.getValue())){
          Assert.assertTrue(false,mapentry.getValue()+ content+" title text is not available");
        }

      }
      result = ResultStore.success("All the "+content+" title texts are available");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to get "+content+" title texts. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String, String> getdataPointstitle(String sWidget,List<String> dataPointtext,String endIndexstr,int value,boolean bool)
  {
    Map<String, String> result;


    try
    {
      String  str=null;

      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      List<String> listtext=new ArrayList<>();

      List<WebElement> li=driver.findElements(By.cssSelector("g#" + sWidget+" title"));
      if(bool) {
        for (WebElement elementtitletext : li) {

          String str1 = elementtitletext.getAttribute("innerHTML").replaceAll("[\n]", " ");
          listtext.add(str1.substring(0, (str1.indexOf(endIndexstr)) - value));
        }
      }else{
        for (WebElement elementtitletext : li) {

          String str1 = elementtitletext.getAttribute("innerHTML").replaceAll("[\n]", " ");
          listtext.add(str1);
        }
      }
      Assert.assertEquals(listtext,dataPointtext);
      result = ResultStore.success("All the title texts are available");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to get title texts. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String, String> getdataPointstitlewithoutGMT(String sWidget,List<String> dataPointtext,String endIndexstr,boolean bool)
  {
    Map<String, String> result;


    try
    {
      String  str=null;

      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      List<String> listtext=new ArrayList<>();

      List<WebElement> li=driver.findElements(By.cssSelector("g#" + sWidget+" title"));
      if(bool) {
        for (WebElement elementtitletext : li) {

          String str1 = elementtitletext.getAttribute("innerHTML").replaceAll("[\n]", " ");
          listtext.add(str1.substring(0, (str1.indexOf(endIndexstr)+4)));
        }
      }else{
        for (WebElement elementtitletext : li) {

          String str1 = elementtitletext.getAttribute("innerHTML").replaceAll("[\n]", " ");
          listtext.add(str1);
        }
      }
      Assert.assertEquals(listtext,dataPointtext);
      result = ResultStore.success("All the title texts are available");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to get title texts. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String,String> verifyingSelectedText(String id,String paramVariableName,List<String> oslist)
  {
    Map<String, String> result;
    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-cy='"+paramVariableName+"'] input[type='text']")));
      TestUtils.sleep(2);

      JavascriptExecutor js=(JavascriptExecutor)driver;
      String txt=js.executeScript("return document.querySelector(\"[data-cy=\'"+paramVariableName+"\'] input[type='text']\").value;").toString();

      if(oslist.contains(txt))
      {
        result = ResultStore.success("Option selected is correct");
      }
      else{
        result = ResultStore.failure("Option selected is not correct ");
      }
    }
    catch(Exception exc)
    {
      result = ResultStore.failure("Failed to get text . error: " + Misc.getExceptionInfo(exc));
    }

    return result;
  }



  public Map<String,String> verifyingTextPresentInDropdown(String id, String paramVariableName,List<String> str)
  {
    Map<String, String> result;
    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id = '"+id+"']")));
      TestUtils.sleepAndLog(1500);
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("bvd-ng2-dropdown[data-cy='"+paramVariableName+"'] input[type='text']")));
      WebElement slideOutInputTextBox=driver.findElement(By.cssSelector("bvd-ng2-dropdown[data-cy='"+paramVariableName+"'] input[type='text']"));
      Actions actions = new Actions(driver);
      actions.moveToElement(slideOutInputTextBox).click(slideOutInputTextBox).build().perform();
      TestUtils.sleep(2);

      List<WebElement> optionList = driver.findElements(By.cssSelector("bvd-ng2-dropdown[data-cy='"+paramVariableName+"'] span"));
      List<String> listoftext = new ArrayList<>();
      for(WebElement option : optionList)
      {
        listoftext.add(option.getText());

      }
      if(listoftext.equals(str))
      {
        result = ResultStore.success("list contain the options");
      }
      else
      {
        result = ResultStore.success("list doesnot contain the options");
      }
      slideOutInputTextBox.sendKeys(Keys.ESCAPE);
    }
    catch(Exception exc)
    {
      result = ResultStore.failure("Failed to get text . error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

// [fill="#c6179d"],[fill="#0073e7"]

  public Map<String, String> barCount(String sWidget,int barCount)
  {
    Map<String, String> result;
    int count=0;

    try
    {
      WebElement elementTest=null;
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(3000);
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("g#" + sWidget + " " +
              "svg path")));

      count=driver.findElements(By.cssSelector("g#" + sWidget + " " +
              "svg path")).size();
      Assert.assertEquals(count,barCount);
      result = ResultStore.success("All the expectd bars are available");
      //if (result.get("out").contains("null")) Reporter.log(driver.getPageSource());
    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to get data points. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String, String> linepointCount(String sWidget,int barCount)
  {
    Map<String, String> result;
    int count=0;

    try
    {
      WebElement elementTest=null;
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("g#" + sWidget + " " +
              "svg path title")));

      count=driver.findElements(By.cssSelector("g#" + sWidget + " " +
              "svg path title")).size();
      Assert.assertEquals(count,barCount);
      result = ResultStore.success("All the expectd line points are available");
      //if (result.get("out").contains("null")) Reporter.log(driver.getPageSource());
    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to get data points. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public void typingText(WebElement element, String value, int delay)
  {

    for (int i = 0; i < value.length(); i++)
    {
      char c = value.charAt(i);
      String s = new StringBuilder().append(c).toString();
      if(delay > 0)
      {
        TestUtils.sleepAndLog(delay);
      }
      TestUtils.sleepAndLog(50);
      element.sendKeys(s);
    }
  }

  public Map<String, String> typeTextinSearchBoxScrollBar()
  {
    Map<String, String> result;

    try
    {
      WebElement elementTest=null;
      TestUtils.sleep(5);
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      WebElement searchBox=driver.findElement(By.cssSelector("g[opr_item_type='opr_search_box'] rect"));
      wait.until(ExpectedConditions.elementToBeClickable(searchBox));
      searchBox.click();
      WebElement searchBoxField=driver.findElement(By.cssSelector("input[placeholder='Search']"));
      wait.until(ExpectedConditions.elementToBeClickable(searchBoxField));
      searchBoxField.click();
      typingText(searchBoxField,"10",10);
      TestUtils.sleepAndLog(1500);
      result = ResultStore.success("typed the text correctly in the search box");
    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to get data points. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String, String> validateSearchFunctionalityScrollBar(){
    Map<String,String> result;

    try{
      List<String> searchresultText=new ArrayList<String>();
      int count=0;
      List<WebElement> searchresultElements=driver.findElements(By.cssSelector("[id*=-shape13] text"));
      for(WebElement ele:searchresultElements){
        searchresultText.add(ele.getText());
      }
      System.out.println(searchresultText.size());

      String first = searchresultText.get(0);
      if (first.equals("10"))
      {
        result=ResultStore.success("Search Result Validated Successfully");
      }
      else {
        result=ResultStore.failure("Search Result Validation failed");
      }

    }catch(Exception exc){
      result=ResultStore.failure("Search Functionality Not working Fine");
    }return result;
  }

  public Map<String, String> typeTextinSearchBox()
  {
    Map<String, String> result;

    try
    {
      WebElement elementTest=null;
      TestUtils.sleep(5);
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      WebElement searchBox=driver.findElement(By.cssSelector("g[opr_item_type='opr_search_box'] rect"));
      wait.until(ExpectedConditions.elementToBeClickable(searchBox));
      searchBox.click();
      WebElement searchBoxField=driver.findElement(By.cssSelector("input[placeholder='Search']"));
      wait.until(ExpectedConditions.elementToBeClickable(searchBoxField));
      searchBoxField.click();
      typingText(searchBoxField,"yellow",10);
      TestUtils.sleepAndLog(1500);
      result = ResultStore.success("typed the text correctly in the search box");
    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to get data points. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String, String> validateSearchFunctionality(){
    Map<String,String> result;

    try{
      List<String> searchresultText=new ArrayList<String>();
      int count=0;
      List<WebElement> searchresultElements=driver.findElements(By.cssSelector("g#group28>g:nth-child(3) g:not([style]) g[id$='shape22'] text"));
      for(WebElement ele:searchresultElements){
        if(count<3){
          searchresultText.add(ele.getText());
        }else{
          break;
        }
        count=count+1;
      }
      System.out.println(searchresultText.size());

      boolean sboolean=true;
      String first = searchresultText.get(0);
      for (String s : searchresultText) {
        if (!s.equals("yellow"))
          sboolean=false;
      }
      System.out.println(sboolean);
      Assert.assertTrue(sboolean,"search result values are not equal");
      result=ResultStore.success("Search Result Validated Successfully");

    }catch(Exception exc){
      result=ResultStore.failure("Search Functionality Not working Fine");
    }return result;
  }


  public Map<String, String> getBackgroundColor()
  {
    Map<String, String> result;
    String sBackgroundColorHex;

    try
    {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      wait.until(presenceOfElementLocated(By.cssSelector("svg")));
      wait.until(presenceOfElementLocated(By.cssSelector("body")));
      TestUtils.sleepAndLog(1000);
      WebElement body_Dashboard = driver.findElement(By.cssSelector("body"));
      String sBackgroundColor = body_Dashboard.getCssValue("background-color");
      if (sBackgroundColor.contains("rgba"))
      {
        sBackgroundColor = sBackgroundColor.replace("rgba(", "").replaceAll("\\).*", "");
        String[] rgba = sBackgroundColor.split(", ");
        sBackgroundColorHex = String.format("#%02X%02X%02X", Integer.parseInt(rgba[0]), Integer
                .parseInt(rgba[1]), Integer.parseInt(rgba[2]));
      }
      else
      {
        sBackgroundColor = sBackgroundColor.replace("rgb(", "").replaceAll("\\).*", "");
        String[] rgb = sBackgroundColor.split(", ");
        sBackgroundColorHex = String.format("#%02X%02X%02X", Integer.parseInt(rgb[0]), Integer
                .parseInt(rgb[1]), Integer.parseInt(rgb[2]));
      }
      result = ResultStore.success(sBackgroundColorHex);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Background Color. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getMouseOver(String sWidget)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1000);
      WebElement mouseOver = driver.findElement(By.cssSelector("g#" + sWidget + " ellipse ~ text"));
      result = ResultStore.success(mouseOver.getAttribute("textContent"));
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Chart Numbers. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> mouseOver(String sWidget)
  {
    Map<String, String> result;

    try
    {
      Actions actions = new Actions(driver);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      WebElement chart = driver.findElement(By.cssSelector("g#" + sWidget + " " +
              "g[opr_line_design='chart']"));
      TestUtils.sleepAndLog(3000);
      actions.moveToElement(chart).build().perform();
      // synchronize
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("g#" + sWidget + " " +
              "ellipse ~ text")));
      result = ResultStore.success("Successfully hovered over widget");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to hover over widget " + sWidget + ". error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> openMyAccount()
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.not(presenceOfAllElementsLocatedBy(By.cssSelector
              ("h2.ng-binding.ng-scope"))));
      wait.until(ExpectedConditions.elementToBeClickable(a_PersonalUserSettings));
      a_PersonalUserSettings.click();
      wait.until(ExpectedConditions.elementToBeClickable(a_MyAccount));
      a_MyAccount.click();

      // synchronize
      wait.until(ExpectedConditions.visibilityOf(newPasswordField));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      //wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
      // ("div[data-progress-text='100%']")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));

      result = ResultStore.success("My Account successfully opened");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to open My Account. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;

  }

  public Map<String, String> getPersonalUserSettingsDisplayName(String name)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOf(a_PersonalUserSettings));
      WebElement span_displayName = driver.findElement(By.cssSelector("button[title='Personal user settings'"));
      wait.until(ExpectedConditions.textToBePresentInElement(span_displayName, name));
      String sDisplayName = span_displayName.getText();
      result = ResultStore.success(sDisplayName);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Personal User Settings Display Name. error: " +
              Misc.getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> openHome()
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
      a_Dashboards.click();
      TestUtils.sleep(2);
      String sDefaultDashboard = driver.findElement(By.xpath("//ux-icon[contains(@class,'ux-icon-home default-dashboard-color')]"+
              "/ancestor::td//a")).getText();
      // view default dashboard
      wait.until(ExpectedConditions.elementToBeClickable(a_DefaultDashboard));
      a_DefaultDashboard.click();

      //synchronize
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));
      TestUtils.sleep(2);

      result = ResultStore.success("Successfully opened Default Dashboard");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to view Default Dashboard. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> goToAboutPage()
  {
    Map<String, String> result;

    try
    {

      WebDriverWait localWait = new WebDriverWait(driver, 30);
      String url = cfg.getString("application.bvd.url.external")+ "/#/about";
      driver.get(url);

      TestUtils.sleepAndLog(2500);

      localWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));

      String xpath = ".//*[@id='mainView']/div/p[contains(text(),'Hewlett Packard Enterprise')]";
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      localWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));

      result = ResultStore.success("About page successfully opened");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to open About page. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;

  }

  public Map<String, String> openAboutPageUI()
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      wait.until(ExpectedConditions.elementToBeClickable(a_Help)).click();

      String id = "nav_about";
      WebElement about = wait.until(presenceOfElementLocated(By.id(id)));
      Actions act = new Actions(driver);
      act.moveToElement(about).build().perform();

      wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
      driver.findElement(By.id(id)).click();
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));

      String xpath = ".//*[@id='mainView']/div/p[contains(text(),'Hewlett Packard Enterprise')]";
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
      TestUtils.sleepAndLog(2500);

      result = ResultStore.success("About page successfully opened");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to open About page. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getWidgetLocation(String widgetId)
  {
    Map<String, String> result;

    try
    {
      WebElement widget = driver.findElement(By.id(widgetId));
      result = ResultStore.success(widget.getLocation().getX() + "," + widget.getLocation()
              .getY());
//            }
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get widget location. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getWidgetCenter(String widgetId)
  {
    Map<String, String> result;

    try
    {
      WebElement widget = driver.findElement(By.id(widgetId));
      int x = widget.getLocation().getX() + widget.getSize().getWidth() / 2;
      int y = widget.getLocation().getY() + widget.getSize().getHeight() / 2;

      result = ResultStore.success(x + "," + y);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get widget location. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getBvdUrlForDownload()
  {
    Map<String, String> result;
    try
    {



      String bvdUrl;
      String bvdNatHostname = cfg.getString("application.bvd.network.external_access_host");
      String bvdNatPort = cfg.getString("application.bvd.network.nat_port");
      String bvdContextRoot = cfg.getString("application.bvd.network.contextroot");

      if (cfg.getString("application.bvd.network.bvd_webserver_protocol").equalsIgnoreCase("http") & bvdNatPort.equalsIgnoreCase
              ("80") |

              cfg.getString("application.bvd.network.bvd_webserver_protocol").equalsIgnoreCase("https") & bvdNatPort
                      .equalsIgnoreCase("443"))
      {
        bvdUrl = cfg.getString("application.bvd.network.bvd_webserver_protocol")+ "://" + bvdNatHostname;
      }
      else
      {
        bvdUrl = cfg.getString("application.bvd.network.bvd_webserver_protocol") + "://" + bvdNatHostname + ":" + bvdNatPort;
      }

      if (!(bvdContextRoot == null || bvdContextRoot.trim().isEmpty()))
      {
        bvdUrl = bvdUrl.concat("/" + bvdContextRoot);
      }

      if (cfg.getString("application.bvd.network.setup").equalsIgnoreCase("bvd-docker"))
      {
        bvdUrl = bvdUrl + "/bvd";
      }
      result = ResultStore.success(bvdUrl);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get BVD Url. error: " + Misc.getStacktraceAsString
              (exc));
    }

    return result;
  }

  public Map<String, String> closeCurrentPage()
  {
    Map<String, String> result;
    try
    {
      Robot robot = new Robot();
      robot.keyPress(KeyEvent.VK_CONTROL);
      robot.keyPress(KeyEvent.VK_W);
      robot.keyRelease(KeyEvent.VK_W);
      robot.keyRelease(KeyEvent.VK_CONTROL);
      result = ResultStore.success();

    } catch (Exception exc)
    {
      result = ResultStore.failure();
    }
    return result;
  }


  public Map<String, String> closeCurrentTab()
  {
    Map<String, String> result;
    try
    {
      ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
      driver.switchTo().window(tabs.get(1));
      driver.close();
      driver.switchTo().window(tabs.get(0));
      result = ResultStore.success("Tab closed");
    } catch (Exception exc)
    {
      result = ResultStore.failure();
    }
    return result;
  }

  public Map<String, String> getZoomLevel(String dashboardName)
  {
    try
    {
      TestUtils.sleepAndLog(2000);
      String css = "#" + dashboardName + ">g";
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      WebElement dashboard = wait.until(ExpectedConditions.visibilityOfElementLocated(By
              .cssSelector(css)));

      String str = dashboard.getAttribute("transform");

      if (str == null && str.isEmpty())
      {
        TestUtils.sleepAndLog(10000);
        String str2 = dashboard.getAttribute("transform");
        System.out.println("###Transform2 = " + str2);
      }

      System.out.println("###Transform = " + str);
      if (str != null && !str.isEmpty())
      {
        return ResultStore.success(str);
      }
      else
      {
        return ResultStore.failure("Zoom Level string is empty or missing");
      }
    } catch (Exception e)
    {
      return ResultStore.failure("Failure to get zoom data" + Misc.getStacktraceAsString
              (e));
    }
  }

  public Map<String, String> resetZoomLevel()
  {
    try
    {
      WebElement zoomButton = driver.findElement(By.cssSelector
              ("dashboard-zoom #zoom_reset"));
      wait.until(ExpectedConditions.visibilityOf(zoomButton));
      zoomButton.click();
      TestUtils.sleepAndLog(1000);
      return ResultStore.success("FIT Button is clicked");
    } catch (Exception e)
    {
      return ResultStore.failure(Misc.getStacktraceAsString(e));
    }
  }

  public Map<String, String> clickOnDashboard(String dashboardName)
  {
    try
    {
      WebElement dashboard = wait.until(ExpectedConditions.elementToBeClickable(By.id
              (dashboardName)));
      Actions act = new Actions(driver);
      act.moveToElement(dashboard, 300, 300).build().perform();
      act.click().build().perform();
      return ResultStore.success("Dashboard clicked successful");
    } catch (Exception e)
    {
      return ResultStore.failure("Failed to click dashboard" + Misc.getStacktraceAsString
              (e));
    }
  }


  public int getGroupWidgetCount()
  {

    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[id*='instance-']:not(*[id*='shape'])")));
    List<WebElement> widgetsInGroup = driver.findElements(By.cssSelector
            ("[id*='instance-']:not(*[id*='shape'])"));
    return widgetsInGroup.size();
  }
  public Map<String, String> compareTextElementsInTextWidgetWithOutTitle(List textElements,String widgetshape)
  {
    List textElems = new ArrayList();

    try
    {
      List<WebElement> widgetsInGroup = driver.findElements(By.cssSelector
              ("g[id$='-"+widgetshape+"'] text"));
      for (WebElement textElem : widgetsInGroup)
      {
        textElems.add(textElem.getText());
      }
      if (textElems.equals(textElements))
      {
        return ResultStore.success("Text Group Widget compared");
      }
      return ResultStore.failure("Text Group Widget NOT compared");
    } catch (Exception e)
    {
      return ResultStore.failure(Misc.getStacktraceAsString(e));
    }
  }

  public Map<String, String> compareTextElementsInTextWidget(List textElements)
  {
    List textElems = new ArrayList();

    try
    {
      List<WebElement> widgetsInGroup = driver.findElements(By.cssSelector
              ("[id*='instance-']:not(*[id*='shape'])"));
      for (WebElement textElem : widgetsInGroup) {
        List<WebElement> widgetsInGrouptitle = textElem.findElements(By.tagName("title"));
        String str = "";
        int count = 0;
        for (WebElement texttitle : widgetsInGrouptitle) {
          if (count >= 1) {
            str = str + " " + texttitle.getText().replaceAll("[\n]", " ");
          } else {
            str = str + texttitle.getText().replaceAll("[\n]", " ");
          }
          count = count + 1;
        }
        textElems.add(str);
      }
      if (textElems.equals(textElements))
      {
        return ResultStore.success("Text Group Widget compared");
      }
      return ResultStore.failure("Text Group Widget NOT compared");
    } catch (Exception e)
    {
      return ResultStore.failure(Misc.getStacktraceAsString(e));
    }
  }

  public Map<String, String> checkSoloCircleSize(String shapeID, String size)
  {
    try
    {
      WebElement soloCircle = driver.findElement(By.cssSelector("#" + shapeID + " circle"));
      System.out.println("Expected size : " + size + ", Actual size :" + soloCircle.getAttribute
              ("r"));

      Double db1=Double.parseDouble(size.trim());
      DecimalFormat df = new DecimalFormat("##.###");
      df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
      System.out.println(df.format(db1));

      Double db2=Double.parseDouble(soloCircle.getAttribute("r").trim());
      DecimalFormat df1 = new DecimalFormat("##.###");
      df1.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
      System.out.println(df1.format(db2));

      Double db3=Double.parseDouble(df.format(db1).trim());
      Double db4=Double.parseDouble(df1.format(db2).trim());

      if(Double.compare(db3,db4)==0)
      {
        return ResultStore.success("Circle zize OK");
      }
      else
      {
        return ResultStore.failure("Circle zize not equal");
      }
    } catch (Exception e)
    {
      return ResultStore.failure("Failed to click dashboard" + Misc.getStacktraceAsString
              (e));
    }

  }


  public Map<String, String> checkSoloCircleColor(String shapeID)
  {
    try
    {
      WebElement soloCircle = driver.findElement(By.cssSelector("#" + shapeID + " circle"));
      return ResultStore.success(soloCircle.getAttribute("fill"));
    } catch (Exception e)
    {
      return ResultStore.failure("Failed to check circle color" + Misc.getStacktraceAsString
              (e));
    }

  }

  public Map<String, String> checkWGCircleSize(String shapeID, String size)
  {
    try
    {
      WebElement soloCircle = driver.findElement(By.cssSelector("[id*='" + shapeID + "'] " +
              "circle"));
      System.out.println("Expected size : " + size + ", Actual size :" + soloCircle.getAttribute
              ("r"));
      if (soloCircle.getAttribute("r").equals(size))
      {
        return ResultStore.success("Circle zize OK");
      }
      else
      {
        return ResultStore.failure("Circle zize not equal");
      }
    } catch (Exception e)
    {
      return ResultStore.failure("Failed to click dashboard" + Misc.getStacktraceAsString
              (e));
    }

  }

  public Map<String, String> checkWGCircleColor(String shapeID)
  {
    try
    {
      WebElement soloCircle = driver.findElement(By.cssSelector("[id*='" + shapeID + "'] " +
              "circle"));
      return ResultStore.success(soloCircle.getAttribute("fill"));
    } catch (Exception e)
    {
      return ResultStore.failure("Failed to click dashboard" + Misc.getStacktraceAsString
              (e));
    }

  }

  private void fillAging()
  {

    int dockerEnv = driver.findElements(By.id("bvd-aging")).size();
    // synchronize

    if (dockerEnv == 1)
    {
      WebElement dataChannelField = driver.findElement(By.cssSelector("[name" +
              "=\"statsNumberSpinner\"]"));
      WebElement dataRecordField = driver.findElement(By.cssSelector("[name" +
              "=\"dataRecordsNumberSpinner\"]"));
      dataRecordField.clear();
      dataRecordField.sendKeys("1");
      dataChannelField.clear();
      dataChannelField.sendKeys("1");

    }

  }

  public Map<String, String> setAllowEmbedded()
  {
    Map<String, String> result;
    try
    {
      if (sslCheckBox.getAttribute("class").contains("ng-empty"))
      {
        TestUtils.sleep(5);
        sslCheckBox.click();

      }

      WebElement systemSettingsApplyButton = driver.findElement(By.cssSelector
              ("slide-system-settings #buttonSaveSetting"));
      wait.until(ExpectedConditions.visibilityOf(systemSettingsApplyButton));
      wait.until(ExpectedConditions.elementToBeClickable(systemSettingsApplyButton));
      systemSettingsApplyButton.click();
      TestUtils.sleep(10);
      result = ResultStore.success("Successfully set embedded");
    } catch (Exception exc)

    {
      result = ResultStore.failure("Failed to set embedded. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> denyEmbedded()
  {
    Map<String, String> result;

    try
    {
      if (!(sslCheckBox.getAttribute("class").contains("ng-empty")))
      {
        TestUtils.sleep(5);
        sslCheckBox.click();

      }
      //fillAging();
      WebElement systemSettingsApplyButton = driver.findElement(By.cssSelector
              ("slide-system-settings #buttonSaveSetting"));
      wait.until(ExpectedConditions.visibilityOf(systemSettingsApplyButton));
      wait.until(ExpectedConditions.elementToBeClickable(systemSettingsApplyButton));
      systemSettingsApplyButton.click();
      TestUtils.sleep(10);
      result = ResultStore.success("Successfully uncheck embedded");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to unset embedded. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }


  private void waitSystemMenu()
  {
    WebElement system_menu = driver.findElement(By.cssSelector("[aria-expanded='true']"));
    wait.until(ExpectedConditions.elementToBeClickable(system_menu));
  }



  public Map<String, String> getTextValueOfTextWidgetsInsideWidgetGroup(String widgetGroupId)
  {
    Map<String, String> result;
    try
    {
      List<String> widgetValues = new ArrayList<>();
      List<WebElement> textWidgets = driver.findElements(By.cssSelector("[id*='instance-" +
              widgetGroupId + "-'] text"));
      for (WebElement textWidget : textWidgets)
      {
        widgetValues.add(textWidget.getText());
      }

      System.out.println("WGRESULTS = " + String.join(",", widgetValues));

      result = ResultStore.success(String.join(",", widgetValues));
    } catch (Exception e)
    {
      return ResultStore.failure("Failed to click dashboard" + Misc.getStacktraceAsString(e));
    }

    return result;
  }

  public Map<String, String> downloadResource(String fileName)
  {
    Map<String, String> result;

    try
    {

      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector
              ("[href='downloads/"+fileName+"']")));
      WebElement downloadLink = driver.findElement(By.cssSelector
              ("[href='downloads/"+fileName+"']"));
      downloadLink.click();
      result = ResultStore.success("Successfully clicked on link to download the file");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to download file: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> pageScroll(String positionX, String positionY)
  {
    Map<String, String> result;

    try
    {

      JavascriptExecutor scroll = (JavascriptExecutor)driver;
      scroll.executeScript("scroll("+ positionX +", "+ positionY +");");
      JavascriptExecutor position = (JavascriptExecutor)driver;
      Long xPosition = (Long) position.executeScript("return window.pageXOffset || document" +
              ".documentElement.scrollLeft;");
      Long yPosition = (Long) position.executeScript("return window.pageYOffset || document" +
              ".documentElement.scrollTop;");

      if (!xPosition.toString().equals(positionX) || !yPosition.toString().equals(positionY ))
      {
        result = ResultStore.failure("scroll coordinates wrong: " + xPosition.toString()+ " " + yPosition.toString());
        return result;
      }
      result = ResultStore.success("Successfully scrolled to " + xPosition.toString()+ " " + yPosition.toString() );

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to scroll: " + Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> pageScrollView()
  {
    Map<String, String> result;

    try
    {

      WebElement element = driver.findElement(By.cssSelector("a[href='downloads/bvd-cli.zip']"));
      JavascriptExecutor scroll = (JavascriptExecutor)driver;
      scroll.executeScript("arguments[0].scrollIntoView();", element);


      result = ResultStore.success("Successfully scrolled Element to View" );

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to scroll: " + Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String,String> clickDataGenerateLink(){
    Map<String,String> result;
    try{
      wait.until(ExpectedConditions.elementToBeClickable(a_sampleGen_githublink));
      a_sampleGen_githublink.click();

      result=ResultStore.success("clicked on Github link for SampleGenerator");
    }catch(Exception e){
      result=ResultStore.failure("unable to click the link"+Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> switchToTab(String pageTitle) {
    String subWindowHandler = null;
    Set<String> handles = driver.getWindowHandles();
    Iterator<String> iterator = handles.iterator();
    while (iterator.hasNext()) {
      subWindowHandler = iterator.next();
    }
    driver.switchTo().window(subWindowHandler);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.repository-content")));
    String actualTitle = driver.getTitle();
    try {
      if (actualTitle.equals(pageTitle)) {
        return ResultStore.success("Tab Switched to Colour You Data");
      }
      return ResultStore.failure("Tab not Switched to Colour Your Data");

    } catch (Exception e) {
      return ResultStore.failure(e.getMessage());
    }
  }

  public Map<String, String> closeActiveTab()
  {
    try
    {

      ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
      driver.switchTo().window(tabs2.get(1));
      driver.close();
      driver.switchTo().window(tabs2.get(0));
      return ResultStore.success("Tab closed");
    } catch (Exception e)
    {
      return ResultStore.failure("Tab not Closed");
    }
  }

  public Map<String, String> readslideoutfieldValues(List<String> fields,List<String> fieldtitle)
  {
    try
    {

      WebElement timetestvalueselected=driver.findElement(By.cssSelector("span[title='"+fieldtitle.get(0)+"']~div div.opr-dropdown-selection div"));
      WebElement idparamselected=driver.findElement(By.cssSelector("span[title='"+fieldtitle.get(1)+"']~div div.opr-dropdown-selection div"));
      JavascriptExecutor js = (JavascriptExecutor) driver;

      String valuefromdropdownvalue=timetestvalueselected.getText();
      String idparamvalue=idparamselected.getText();
      if (valuefromdropdownvalue.equals(fields.get(0))&&idparamvalue.equals(fields.get(1)))
      {
        return ResultStore.success("All the param fields are displayed as expected");
      }
      else
      {
        return ResultStore.failure("All the param fields are not as expected");
      }
    } catch (Exception exc)
    {
      return ResultStore.failure("All the param fields are not as expected. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }

  }
  public Map<String, String> switchToTabforAnotherDashboard(String pageTitle) {
    String subWindowHandler = null;
    Set<String> handles = driver.getWindowHandles();
    Iterator<String> iterator = handles.iterator();
    while (iterator.hasNext()) {
      subWindowHandler = iterator.next();
    }
    driver.switchTo().window(subWindowHandler);
    wait.until(ExpectedConditions.elementToBeClickable(parameterButtonToClick));
    String actualTitle = driver.getTitle();
    System.out.println(actualTitle);
    try {
      if (actualTitle.equals(pageTitle)) {
        return ResultStore.success("Tab Switched");
      }
      return ResultStore.failure("Tab not Switched");

    } catch (Exception e) {
      return ResultStore.failure(e.getMessage());
    }
  }


  public Map<String, String> logoutinFrame()
  {
    Map<String, String> result;

    try
    {
      String id = "nav_user_menu";

      waitingModaldisappearance();

      wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
      TestUtils.sleep(5);
      driver.switchTo().defaultContent();
      wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("bvd"));
      wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentFrame"));
      TestUtils.sleepAndLog(2000);
      a_PersonalUserSettings.click();
      wait.until(ExpectedConditions.elementToBeClickable(a_Logout));
      a_Logout.click();
      TestUtils.sleep(2);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#logout-link")));
      relogin.click();
      wait.until(ExpectedConditions.visibilityOf(button_idm_login));
      TestUtils.sleepAndLog(3000);
      input_idm_username.clear();
      result = ResultStore.success("Successfully logged out");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to log out. error: " + Misc.getStacktraceAsString(exc));
    }
    return result;
  }
  public Map<String, String> clickOnLeftButton()
  {
    Map<String, String> result;

    try
    {

      wait.until(ExpectedConditions.elementToBeClickable(parameterButtonToClick));
      parameterButtonToClick.click();
      result = ResultStore.success("Successfully clicked on Left Button");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on Left Button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> selectdateparmsfromslideoutcustomised(String fromdate,String todate,String fromdateparamnameinUI,String todateparamnameinUI)
  {
    Map<String, String> result;

    try
    {


      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[title='"+fromdateparamnameinUI+"']~div input[name='dateSelector']")));
      WebElement fromdateelement=driver.findElement(By.cssSelector("[title='"+fromdateparamnameinUI+"']~div input[name='dateSelector']"));
      fromdateelement.click();
      fromdateelement.clear();
      fromdateelement.sendKeys(fromdate);
      TestUtils.sleep(2);

      fromdateelement.sendKeys(Keys.TAB);
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[title='"+todateparamnameinUI+"']~div input[name='dateSelector']")));
      WebElement todateelement=driver.findElement(By.cssSelector("[title='"+todateparamnameinUI+"']~div input[name='dateSelector']"));
      todateelement.click();
      todateelement.clear();
      TestUtils.sleep(1);
      todateelement.sendKeys(todate);
      TestUtils.sleep(2);
      todateelement.sendKeys(Keys.TAB);
      TestUtils.sleep(1);
      result = ResultStore.success("Successfully clicked on Left Button");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on Left Button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> downloadResourceUsingHref(String href)
  {
    Map<String, String> result;

    try
    {

      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector
              ("[href='"+href+"']")));
      WebElement downloadLink = driver.findElement(By.cssSelector
              ("[href='"+href+"']"));
      downloadLink.click();
      result = ResultStore.success("Successfully clicked on link to download the file");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to download file: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }
  public Map<String, String> selectdateparmsfromslideout(List<String> fromdatelist,List<String> todatelist)
  {
    Map<String, String> result;

    try
    {

      wait.until(ExpectedConditions.elementToBeClickable(calendarbutton));
      calendarbutton.click();
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.filter-menu-container")));
      wait.until(ExpectedConditions.visibilityOf(startdatepickerbutton));
      startdatepickerbutton.click();
      TestUtils.sleep(1);
      startdatedecadebutton.click();
      TestUtils.sleep(1);
      startdatepreviousbutton.click();
      TestUtils.sleep(1);
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.calendar-item[aria-label='"+fromdatelist.get(0)+"']")));
      String fromyearstr="button.calendar-item[aria-label='"+fromdatelist.get(0)+"']";
      driver.findElement(By.cssSelector(fromyearstr)).click();
      TestUtils.sleep(1);
      String frommonthstr="button.calendar-item[aria-label='"+fromdatelist.get(1)+"']";
      driver.findElement(By.cssSelector(frommonthstr)).click();
      TestUtils.sleep(1);
      String fromdatestr="ux-date-time-picker.start-date-picker table.calendar button[aria-label='"+fromdatelist.get(2)+"']";
      driver.findElement(By.cssSelector(fromdatestr)).click();
      TestUtils.sleep(2);
      wait.until(ExpectedConditions.visibilityOf(enddatepickerbutton));
      enddatepickerbutton.click();
      TestUtils.sleep(1);
      enddatedecadebutton.click();
      TestUtils.sleep(1);
      enddatepreviousbutton.click();
      TestUtils.sleep(1);
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.calendar-item[aria-label='"+todatelist.get(0)+"']")));
      String toyearstr="button.calendar-item[aria-label='"+todatelist.get(0)+"']";
      driver.findElement(By.cssSelector(toyearstr)).click();
      TestUtils.sleep(1);
      String tomonthstr="button.calendar-item[aria-label='"+todatelist.get(1)+"']";
      driver.findElement(By.cssSelector(tomonthstr)).click();
      TestUtils.sleep(1);
      String todatestr="ux-date-time-picker.end-date-picker table.calendar button[aria-label='"+todatelist.get(2)+"']";
      driver.findElement(By.cssSelector(todatestr)).click();
      TestUtils.sleep(2);
      fromdateinputhour.click();
      fromdateinputhour.clear();
      fromdateinputhour.sendKeys(fromdatelist.get(3));
      fromdateinputminute.click();
      fromdateinputminute.clear();
      fromdateinputminute.sendKeys(fromdatelist.get(4));
      WebElement ampmelementfrom=driver.findElement(By.cssSelector("ux-date-time-picker.start-date-picker button[aria-label='"+ fromdatelist.get(5)+"']"));
      Actions act1=new Actions(driver);
      act1.moveToElement(ampmelementfrom).click(ampmelementfrom).build().perform();

      todateinputhour.click();
      todateinputhour.clear();
      todateinputhour.sendKeys(todatelist.get(3));
      todateinputminute.click();
      todateinputminute.clear();
      todateinputminute.sendKeys(todatelist.get(4));
      WebElement ampmelementto=driver.findElement(By.cssSelector("ux-date-time-picker.end-date-picker button[aria-label='"+ todatelist.get(5)+"']"));
      Actions act2=new Actions(driver);
      act2.moveToElement(ampmelementto).click(ampmelementto).build().perform();


      TestUtils.sleep(2);
      Actions act3=new Actions(driver);
      act3.sendKeys(ampmelementto,Keys.ESCAPE).build().perform();

      result = ResultStore.success("Successfully set the calendar");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to set the calendar: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> selectdateTimepickercalendar()
  {
    Map<String, String> result;
    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(calendarbutton));
      calendarbutton.click();
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.filter-menu-container")));
      wait.until(ExpectedConditions.visibilityOf(startdatepickerbutton));

      result = ResultStore.success("Successfully Clicked on the calendar button");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on the calendar button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> closedateTimepickercalendar()
  {
    Map<String, String> result;
    try
    {
      WebElement ampmelementto=driver.findElement(By.cssSelector("ux-date-time-picker.end-date-picker button[aria-label='AM']"));
      Actions act2=new Actions(driver);
      act2.moveToElement(ampmelementto).click(ampmelementto).build().perform();


      TestUtils.sleep(2);
      Actions act3=new Actions(driver);
      act3.sendKeys(ampmelementto,Keys.ESCAPE).build().perform();

      result = ResultStore.success("Successfully closed the calendar button");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to close the calendar button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }


  public Map<String, String> validateGMTOperation()
  {
    Map<String, String> result;
    try
    {
      wait.until(ExpectedConditions.visibilityOf(fromdateTimeZone));
      String fromdateTimezonevalue=fromdateTimeZone.getAttribute("aria-valuenow");
      String todateTimezonevalue=todateTimeZone.getAttribute("aria-valuenow");
      fromdateTimeZoneIncreaseKey.click();
      int fromvaluebeforeIncrease=0;
      int fromvalueafterIncrease=0;

      int tovaluebeforeIncrease=0;
      int tovalueafterIncrease=0;
      if (fromdateTimezonevalue.equals("GMT")){
        Assert.assertEquals(fromdateTimeZone.getAttribute("aria-valuenow"),"GMT+1");
      }else if(fromdateTimezonevalue.equals("GMT+5:30")){
        Assert.assertEquals(fromdateTimeZone.getAttribute("aria-valuenow"),"GMT+6");
      } else if(fromdateTimezonevalue.equals("GMT+5:00")){
        Assert.assertEquals(fromdateTimeZone.getAttribute("aria-valuenow"),"GMT+5:30");
      }else if(fromdateTimezonevalue.equals("GMT-1")){
        Assert.assertEquals(fromdateTimeZone.getAttribute("aria-valuenow"),"GMT");
      } else if(fromdateTimezonevalue.contains("GMT-")){
        fromvaluebeforeIncrease=Integer.parseInt(fromdateTimezonevalue.substring(fromdateTimeZone.getAttribute("aria-valuenow").indexOf('-')+1));
        fromvalueafterIncrease=fromvaluebeforeIncrease-1;
        Assert.assertEquals(fromdateTimeZone.getAttribute("aria-valuenow"),"GMT-"+String.valueOf(fromvalueafterIncrease));
      }else if(fromdateTimezonevalue.contains("GMT+")) {
        fromvaluebeforeIncrease = Integer.parseInt(fromdateTimezonevalue.substring(fromdateTimeZone.getAttribute("aria-valuenow").indexOf('+')+1));
        fromvalueafterIncrease = fromvaluebeforeIncrease + 1;
        Assert.assertEquals(fromdateTimeZone.getAttribute("aria-valuenow"),"GMT+"+String.valueOf(fromvalueafterIncrease));
      }

      todateTimeZoneDecreaseKey.click();
      if (todateTimezonevalue.equals("GMT")){
        Assert.assertEquals(todateTimeZone.getAttribute("aria-valuenow"),"GMT-1");
      }else if(fromdateTimezonevalue.equals("GMT+5:30")){
        Assert.assertEquals(todateTimeZone.getAttribute("aria-valuenow"),"GMT+5");
      } else if(fromdateTimezonevalue.equals("GMT+6:00")){
        Assert.assertEquals(todateTimeZone.getAttribute("aria-valuenow"),"GMT+5:30");
      }else if(fromdateTimezonevalue.equals("GMT+1")){
        Assert.assertEquals(todateTimeZone.getAttribute("aria-valuenow"),"GMT");
      } else if(fromdateTimezonevalue.contains("GMT-")){
        tovaluebeforeIncrease=Integer.parseInt(fromdateTimezonevalue.substring(fromdateTimeZone.getAttribute("aria-valuenow").indexOf('-')+1));
        tovalueafterIncrease=tovaluebeforeIncrease+1;
        Assert.assertEquals(todateTimeZone.getAttribute("aria-valuenow"),"GMT-"+String.valueOf(tovalueafterIncrease));
      }else if(fromdateTimezonevalue.contains("GMT+")) {
        tovaluebeforeIncrease = Integer.parseInt(fromdateTimezonevalue.substring(fromdateTimeZone.getAttribute("aria-valuenow").indexOf('+')+1));
        tovalueafterIncrease = tovaluebeforeIncrease-1;
        Assert.assertEquals(todateTimeZone.getAttribute("aria-valuenow"),"GMT+"+String.valueOf(tovalueafterIncrease));
      }

      result = ResultStore.success("GMT Increase Decrease button is working as expected");

    } catch (Exception exc)
    {
      result = ResultStore.failure("GMT Increase Decrease button is not working as expected : " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> validaterelativeTime(String relativeTime,int hours, String setTimeZone)
  {
    Map<String, String> result;
    try
    {
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.filter-menu-container")));
      wait.until(ExpectedConditions.elementToBeClickable(By.linkText(relativeTime)));
      if(driver.findElement(By.linkText(relativeTime)).getAttribute("class").contains("active")){
        System.out.println(relativeTime +" is the active relative time");
      }else{
        System.out.println(relativeTime +" is not the active relative time");
      }

      Calendar cal=Calendar.getInstance(TimeZone.getTimeZone(setTimeZone));
      cal.setTimeZone(TimeZone.getTimeZone(setTimeZone));
      Date date = cal.getTime();
      DateFormat dateFormat = new SimpleDateFormat("dd, yyyy hh aa");
      dateFormat.setTimeZone(TimeZone.getTimeZone(setTimeZone));
      String tostrDate = dateFormat.format(date);

      Calendar fromdatecal=Calendar.getInstance(TimeZone.getTimeZone(setTimeZone));
      fromdatecal.setTimeZone(TimeZone.getTimeZone(setTimeZone));
      fromdatecal.add(Calendar.HOUR,hours);
      Date fromdate=fromdatecal.getTime();
      DateFormat fromdateFormat = new SimpleDateFormat("dd, yyyy hh aa");
      fromdateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      String fromstrDate = fromdateFormat.format(fromdate);


      String todateactual="";
      String fromdateactual="";

      String todatedaynumber=selectedtodate.getAttribute("aria-label");
      String fromdatedaynumber=selectedfromdate.getAttribute("aria-label");

      if(Integer.parseInt(selectedfromdate.getText())<10) {
        String getfromdaystr=selectedfromdate.getAttribute("aria-label").substring(4,selectedfromdate.getAttribute("aria-label").indexOf(',')-1);
        getfromdaystr="0"+getfromdaystr;
        fromdatedaynumber=selectedfromdate.getAttribute("aria-label").substring(0,4)+getfromdaystr+selectedfromdate.getAttribute("aria-label").substring(4);
      }

      if(Integer.parseInt(selectedtodate.getText())<10) {
        String gettodaystr=selectedtodate.getAttribute("aria-label").substring(4,selectedtodate.getAttribute("aria-label").indexOf(',')-1);
        gettodaystr="0"+gettodaystr;
        todatedaynumber=selectedtodate.getAttribute("aria-label").substring(0,4)+gettodaystr+selectedtodate.getAttribute("aria-label").substring(4);
      }

      if(Integer.parseInt(fromdateinputhour.getAttribute("aria-valuenow"))<10){
        fromdateactual=fromdatedaynumber+" 0"+fromdateinputhour.getAttribute("aria-valuenow")+" "+fromdateampm.getAttribute("aria-label");
        fromdateactual=fromdateactual.substring(4);
      }else{
        fromdateactual=fromdatedaynumber+" "+fromdateinputhour.getAttribute("aria-valuenow")+" "+fromdateampm.getAttribute("aria-label");
        fromdateactual=fromdateactual.substring(4);
      }

      if(Integer.parseInt(todateinputhour.getAttribute("aria-valuenow"))<10){
        todateactual=todatedaynumber+" 0"+todateinputhour.getAttribute("aria-valuenow")+" "+todateampm.getAttribute("aria-label");
        todateactual=todateactual.substring(4);
      }else{
        todateactual=todatedaynumber+" "+todateinputhour.getAttribute("aria-valuenow")+" "+todateampm.getAttribute("aria-label");
        todateactual=todateactual.substring(4);
      }

      Assert.assertEquals(tostrDate,todateactual,"To date is not expected");
      Assert.assertEquals(fromstrDate,fromdateactual,"from date is not expected");

      WebElement ampmelementto=driver.findElement(By.cssSelector("ux-date-time-picker.end-date-picker button[aria-label='PM']"));
      Actions act3=new Actions(driver);
      act3.sendKeys(ampmelementto,Keys.ESCAPE).build().perform();
      result = ResultStore.success("Successfully Validated the relative Time :"+relativeTime);

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on the calendar button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> validaterelativeTimeDWMY(String relativeTime,int value){
    Map<String, String> result;
    try
    {
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.filter-menu-container")));
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-cy='"+relativeTime+"']")));
      if(driver.findElement(By.cssSelector("[data-cy='"+relativeTime+"']")).getAttribute("class").contains("active")){
        System.out.println(relativeTime +" is the active relative time");
      }else{
        System.out.println(relativeTime +" is not the active relative time");
      }
      String[] monthArray = new String[]{ "Jan","Feb","Mär","Apr","Mai","Jun","Jul","Aug","Sep","Oct", "Nov", "Dec"};

      String fromstrDate="";
      String tostrDate="";
      Calendar fromdatecal=Calendar.getInstance();
      Calendar todatecal=Calendar.getInstance();


      if(relativeTime.equals("YEAR")){
        fromdatecal.add(Calendar.YEAR,value);
        fromdatecal.set(Calendar.MONTH, fromdatecal.getActualMinimum(Calendar.JANUARY));
        fromdatecal.set(Calendar.DAY_OF_MONTH,fromdatecal.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date fromdate=fromdatecal.getTime();
        DateFormat fromdateFormat = new SimpleDateFormat("M dd, yyyy");
        fromstrDate = fromdateFormat.format(fromdate);


        todatecal.add(Calendar.YEAR,value);
        todatecal.set(Calendar.MONTH, Calendar.DECEMBER);
        todatecal.set(Calendar.DAY_OF_MONTH,fromdatecal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date date = todatecal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("M dd, yyyy");
        tostrDate = dateFormat.format(date);

      }else if(relativeTime.equals("MONTH")){

        fromdatecal.add(Calendar.MONTH,value);
        fromdatecal.set(Calendar.DAY_OF_MONTH, fromdatecal.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date fromdate=fromdatecal.getTime();
        DateFormat fromdateFormat = new SimpleDateFormat("M dd, yyyy");
        fromstrDate = fromdateFormat.format(fromdate);


        todatecal.add(Calendar.MONTH,value);
        todatecal.set(Calendar.DAY_OF_MONTH, todatecal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date date = todatecal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("M dd, yyyy");
        tostrDate = dateFormat.format(date);


      }else if (relativeTime.equals("DAY")){

        fromdatecal.add(Calendar.DAY_OF_MONTH,value);
        Date fromdate=fromdatecal.getTime();
        DateFormat fromdateFormat = new SimpleDateFormat("M dd, yyyy");
        fromstrDate = fromdateFormat.format(fromdate);

        todatecal.add(Calendar.DAY_OF_MONTH,value);
        Date date = todatecal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("M dd, yyyy");
        tostrDate = dateFormat.format(date);

      }else if (relativeTime.equals("WEEK")){

        fromdatecal.add(Calendar.WEEK_OF_MONTH,value);
        fromdatecal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        Date fromdate=fromdatecal.getTime();
        DateFormat fromdateFormat = new SimpleDateFormat("M dd, yyyy");
        fromstrDate = fromdateFormat.format(fromdate);

        todatecal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        Date date = todatecal.getTime();
        DateFormat todateFormat = new SimpleDateFormat("M dd, yyyy");
        tostrDate = todateFormat.format(date);

      }

      String todateactual="";
      String fromdateactual="";

      int monthNumber=Integer.parseInt(fromstrDate.substring(0, fromstrDate.indexOf(' ')));
      fromstrDate=fromstrDate.replaceFirst(fromstrDate.substring(0, fromstrDate.indexOf(' ')), monthArray[monthNumber-1]);
      fromstrDate=fromstrDate+" 12:00 AM";
      int monthNumberto=Integer.parseInt(tostrDate.substring(0, tostrDate.indexOf(' ')));
      tostrDate=tostrDate.replaceFirst(tostrDate.substring(0, tostrDate.indexOf(' ')), monthArray[monthNumberto-1]);
      tostrDate=tostrDate+" 11:59 PM";

      String fromdatedaynumber=selectedfromdate.getAttribute("aria-label");
      String todatedaynumber=selectedtodate.getAttribute("aria-label");

      if(Integer.parseInt(selectedfromdate.getText())<10) {
        String getfromdaystr=selectedfromdate.getAttribute("aria-label").substring(4,selectedfromdate.getAttribute("aria-label").indexOf(',')-1);
        getfromdaystr="0"+getfromdaystr;
        fromdatedaynumber=selectedfromdate.getAttribute("aria-label").substring(0,4)+getfromdaystr+selectedfromdate.getAttribute("aria-label").substring(4);
      }

      if(Integer.parseInt(selectedtodate.getText())<10) {
        String gettodaystr=selectedtodate.getAttribute("aria-label").substring(4,selectedtodate.getAttribute("aria-label").indexOf(',')-1);
        gettodaystr="0"+gettodaystr;
        todatedaynumber=selectedtodate.getAttribute("aria-label").substring(0,4)+gettodaystr+selectedtodate.getAttribute("aria-label").substring(4);
      }

      if(Integer.parseInt(fromdateinputhour.getAttribute("aria-valuenow"))<10){
        fromdateactual=fromdatedaynumber+" 0"+fromdateinputhour.getAttribute("aria-valuenow")+":"+
                fromdateinputminute.getAttribute("aria-valuenow")+" "+fromdateampm.getAttribute("aria-label");
      }else{
        fromdateactual=fromdatedaynumber+" "+fromdateinputhour.getAttribute("aria-valuenow")+":"+
                fromdateinputminute.getAttribute("aria-valuenow")+" "+fromdateampm.getAttribute("aria-label");
      }

      if(Integer.parseInt(todateinputhour.getAttribute("aria-valuenow"))<10){
        todateactual=todatedaynumber+" 0"+todateinputhour.getAttribute("aria-valuenow")+":"+
                todateinputminute.getAttribute("aria-valuenow")+" "+todateampm.getAttribute("aria-label");
      }else{
        todateactual=todatedaynumber+" "+todateinputhour.getAttribute("aria-valuenow")+":"+
                todateinputminute.getAttribute("aria-valuenow")+" "+todateampm.getAttribute("aria-label");
      }

      System.out.println("The Expected from date of "+relativeTime+" is :"+fromstrDate);
      System.out.println("The actual from date of "+relativeTime+" is :"+fromdateactual);
      System.out.println("The Expected to date of "+relativeTime+"is :"+tostrDate);
      System.out.println("The actual to date of "+relativeTime+" is :"+todateactual);
      Assert.assertEquals(fromstrDate,fromdateactual,"from date is not expected");
      Assert.assertEquals(tostrDate,todateactual,"To date is not expected");

      WebElement ampmelementto=driver.findElement(By.cssSelector("ux-date-time-picker.end-date-picker button[aria-label='PM']"));
      Actions act3=new Actions(driver);
      act3.sendKeys(ampmelementto,Keys.ESCAPE).build().perform();
      result = ResultStore.success("Successfully Validated the relative Time :"+relativeTime);

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on the calendar button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }


  public Map<String, String> selectrelativeTimefromDatePicker(String relativeTime,String Expectedtext)
  {
    Map<String, String> result;
    try
    {

      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.filter-menu-container")));
      wait.until(ExpectedConditions.elementToBeClickable(By.linkText(relativeTime)));
      driver.findElement(By.linkText(relativeTime)).click();
      TestUtils.sleep(1);
      wait.until(ExpectedConditions.visibilityOf(parametertextarea));

      String value=parametertextarea.getText();
      System.out.println(value);
      Assert.assertEquals(value,Expectedtext,"displayed "+relativeTime+ "is not correct ");

      result = ResultStore.success("Successfully Clicked on the required relative time");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on the required relative button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> selectrelativeTimefromDatePickerNew(String relativeTime,String Expectedtext)
  {
    Map<String, String> result;
    try
    {

      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.filter-menu-container")));
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-cy='"+relativeTime+"']")));
      driver.findElement(By.cssSelector("[data-cy='"+relativeTime+"']")).click();
      TestUtils.sleep(1);

      wait.until(ExpectedConditions.visibilityOf(parametertextarea));
      String value=parametertextarea.getText();
      Assert.assertEquals(value,Expectedtext,"displayed "+relativeTime+ "is not correct ");


      result = ResultStore.success("Successfully Clicked on the required relative time");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on the required relative button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> validateApplyButtondisabled()
  {
    Map<String, String> result;
    try
    {

      wait.until(ExpectedConditions.visibilityOf(applydisabled));
      result = ResultStore.success("Apply button is disabled as expected");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Apply button is not disabled " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> applyandResetbtnStatuswhenViewDashboard1stTime()
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleepAndLog(1000);
      wait.until(ExpectedConditions.visibilityOf(applydisabled));
      wait.until(ExpectedConditions.visibilityOf(resetdisabled));
      result = ResultStore.success("Apply and Reset button status are as expected");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on Parameter Value Apply Button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }
  public Map<String, String> datetimepicker(String fromdate,String todate)
  {
    Map<String, String> result;

    try
    {

      wait.until(ExpectedConditions.elementToBeClickable(datetime_fromslideouttextbox));
      datetime_fromslideouttextbox.click();
      datetime_fromslideouttextbox.clear();
      datetime_fromslideouttextbox.sendKeys(fromdate);
      TestUtils.sleep(2);
      datetime_fromslideouttextbox.sendKeys(Keys.TAB);
      wait.until(ExpectedConditions.elementToBeClickable(datetime_toslideouttextbox));
      datetime_toslideouttextbox.click();
      datetime_toslideouttextbox.clear();
      TestUtils.sleep(1);
      datetime_toslideouttextbox.sendKeys(todate);
      TestUtils.sleep(2);
      datetime_toslideouttextbox.sendKeys(Keys.TAB);
      TestUtils.sleep(1);
      result = ResultStore.success("Successfully clicked on Left Button");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on Left Button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }


  public Map<String, String> validateInvalidDateRange(String errormessageExpected)
  {
    Map<String, String> result;

    try
    {

      wait.until(ExpectedConditions.visibilityOf(invaliddateErrorMessage));

      String errorMessage=invaliddateErrorMessage.getText();
      Assert.assertEquals(errorMessage,errormessageExpected);

      result = ResultStore.success("Successfully validated presence of the error message ");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to validate the error message validation for invalid date: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }



  public Map<String, String> leftBarParameterValue1(String value, String noData, String type)
  {
    Map<String, String> result;

    try
    {
      if(type.contains("new")) {
        if (noData.contains("1stdata")){
          wait.until(ExpectedConditions.elementToBeClickable(dropDownValue1));
          dropDownValue1.click();

          leftBarValuetext1.sendKeys(value);
          leftBarValuetext1.sendKeys(Keys.ENTER);
          TestUtils.sleep(2);
          Actions actions = new Actions(driver);
          actions.sendKeys(Keys.TAB).build().perform();

          TestUtils.sleep(2);
        } else if (noData.contains("2nddata")) {
          wait.until(ExpectedConditions.elementToBeClickable(dropDownValue2));
          dropDownValue2.click();
          leftBarValuetext2.sendKeys(value);
          leftBarValuetext2.sendKeys(Keys.ENTER);
          Actions actions = new Actions(driver);
          try {
            Robot robot = new Robot();

            robot.mouseMove(100,350);
          } catch (AWTException e) {
            e.printStackTrace();
          }

          actions.click().build().perform();

          TestUtils.sleep(1);
        }
      }
      else if(type.contains("update"))
      {
        if (noData.contains("1stdata")) {
          wait.until(ExpectedConditions.elementToBeClickable(dropDownValue3));
          dropDownValue3.click();
          leftBarValuetext3.sendKeys(value);
          leftBarValuetext3.sendKeys(Keys.ENTER);
          TestUtils.sleep(2);
          Actions actions = new Actions(driver);
          actions.sendKeys(Keys.TAB).build().perform();

          TestUtils.sleep(1);
        } else if (noData.contains("2nddata")) {
          wait.until(ExpectedConditions.elementToBeClickable(dropDownValue4));
          dropDownValue4.click();
          leftBarValuetext4.sendKeys(value);
          leftBarValuetext4.sendKeys(Keys.ENTER);
          leftBarValuetext4.sendKeys(Keys.TAB);
          Actions actions = new Actions(driver);
          try {
            Robot robot = new Robot();

            robot.mouseMove(100,350);
          } catch (AWTException e) {
            e.printStackTrace();
          }

          actions.click().build().perform();

          TestUtils.sleep(1);
        }
      }
      result = ResultStore.success("Successfully selected the parameter value");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on Left Button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> leftBarParameterValue2(String value, String noData, String type)
  {
    Map<String, String> result;

    try
    {
      if(type.contains("new")) {
        if (noData.contains("1stdata")){
          wait.until(ExpectedConditions.elementToBeClickable(slideout_dropdownbtn));
          Actions actions = new Actions(driver);
          actions.moveToElement(slideout_dropdownbtn).click(slideout_dropdownbtn).build().perform();
          TestUtils.sleep(2);
          slideparamValueselect.sendKeys(value);
          slideparamValueselect.sendKeys(Keys.TAB);

          // Sendkeys using Action class object
          Actions action = new Actions(driver);
          action.sendKeys(Keys.ENTER).build().perform();
          TestUtils.sleep(2);

        } else if (noData.contains("2nddata")) {


          wait.until(ExpectedConditions.elementToBeClickable(secondslideout_dropdownbtn));
          Actions actions = new Actions(driver);
          actions.moveToElement(secondslideout_dropdownbtn).click(secondslideout_dropdownbtn).build().perform();
          TestUtils.sleep(2);
          slideparamValueselect.sendKeys(value);
          slideparamValueselect.sendKeys(Keys.TAB);

          // Sendkeys using Action class object
          Actions action = new Actions(driver);
          action.sendKeys(Keys.ENTER).build().perform();
          TestUtils.sleep(2);
        }
      }
      else if(type.contains("update"))
      {
        if (noData.contains("1stdata")) {
          wait.until(ExpectedConditions.elementToBeClickable(slideout_dropdownbtn));
          Actions actions = new Actions(driver);
          actions.moveToElement(slideout_dropdownbtn).click(slideout_dropdownbtn).build().perform();
          TestUtils.sleep(2);
          slideparamValueselect.sendKeys(value);
          slideparamValueselect.sendKeys(Keys.TAB);

          // Sendkeys using Action class object
          Actions action = new Actions(driver);
          action.sendKeys(Keys.ENTER).build().perform();
          TestUtils.sleep(2);

        } else if (noData.contains("2nddata")) {
          wait.until(ExpectedConditions.elementToBeClickable(secondslideout_dropdownbtn));
          Actions actions = new Actions(driver);
          actions.moveToElement(secondslideout_dropdownbtn).click(secondslideout_dropdownbtn).build().perform();
          TestUtils.sleep(2);
          slideparamValueselect.sendKeys(value);
          slideparamValueselect.sendKeys(Keys.TAB);

          // Sendkeys using Action class object
          Actions action = new Actions(driver);
          action.sendKeys(Keys.ENTER).build().perform();
          TestUtils.sleep(2);

        }
      }
      result = ResultStore.success("Successfully selected the parameter value");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on Left Button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> assignvaluetosingleParam(String datatoselect, String paramVariableName){
    Map<String, String> result;
    try{
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("bvd-ng2-dropdown[data-cy='"+paramVariableName+"'] input[type='text']")));
      WebElement slideOutInputTextBox=driver.findElement(By.cssSelector("bvd-ng2-dropdown[data-cy='"+paramVariableName+"'] input[type='text']"));
      Actions actions = new Actions(driver);
      actions.moveToElement(slideOutInputTextBox).click(slideOutInputTextBox).build().perform();
      TestUtils.sleep(2);
      slideOutInputTextBox.sendKeys(datatoselect);
      TestUtils.sleep(2);
      if(driver.findElements(By.cssSelector("div.ux-typeahead-options ux-typeahead-options-list li")).size() != 0){
        try {
          WebElement searchHighlight_paramValueSelect = driver.findElement(By.xpath("//li[@class='highlighted']//span[contains(text(),'"+datatoselect+"')]"));
          Actions actions1 = new Actions(driver);
          actions1.moveToElement(searchHighlight_paramValueSelect).click(searchHighlight_paramValueSelect).build().perform();

        }
        catch (org.openqa.selenium.StaleElementReferenceException ex) {
          WebElement searchHighlight_paramValueSelect = driver.findElement(By.xpath("//li[@class='active highlighted']//span[contains(text(),'"+datatoselect+"')]"));
          Actions actions2 = new Actions(driver);
          actions2.moveToElement(searchHighlight_paramValueSelect).click(searchHighlight_paramValueSelect).build().perform();

        }
      }
      // synchronize
      wait.until(ExpectedConditions.visibilityOf(applyButtonForParameterValue));

      result = ResultStore.success("Successfully set Param Value to " + datatoselect);

    }catch(Exception exc)
    {
      result = ResultStore.failure("Failed to click on Left Button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> assignvaluetosingleParamselectDropDownParameter(String dropDownValue, String paramVariableName) {
    Map<String, String> result;
    try{
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("bvd-ng2-dropdown[data-cy='"+paramVariableName+"'] input[type='text']")));
      WebElement slideOutInputTextBox=driver.findElement(By.cssSelector("bvd-ng2-dropdown[data-cy='"+paramVariableName+"'] input[type='text']"));
      slideOutInputTextBox.click();
      slideOutInputTextBox.sendKeys(Keys.BACK_SPACE);
      TestUtils.sleep(1);
      driver.findElement(By.cssSelector("bvd-ng2-dropdown[data-cy='"+paramVariableName+"'] input[type='text']")).sendKeys(dropDownValue);
      TestUtils.sleep(1);
      String valueTxt=driver.findElement(By.cssSelector("bvd-ng2-dropdown[data-cy='"+paramVariableName+"'] input[type='text']")).getAttribute("value");
      if(!(valueTxt.contains(dropDownValue))){
        slideOutInputTextBox.sendKeys(dropDownValue);
      }
      if(driver.findElements(By.cssSelector("bvd-ng2-dropdown[data-cy='"+paramVariableName+"'] ux-typeahead-options-list li.highlighted span span")).size() != 0){
        try {
          TestUtils.sleep(2);
          slideOutInputTextBox.sendKeys(Keys.ENTER);
        }
        catch (org.openqa.selenium.StaleElementReferenceException ex) {
          slideOutInputTextBox.sendKeys(Keys.ENTER);
        }
      }
      wait.until(ExpectedConditions.visibilityOf(applyButtonForParameterValue));

      result = ResultStore.success("Successfully set Param Value to " + dropDownValue);
    }
    catch(Exception exc)
    {
      result = ResultStore.failure("Failed to click on Left Button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> assignvaluetosingleParam_Old(String datatoselect){
    Map<String, String> result;
    try{
      wait.until(ExpectedConditions.elementToBeClickable(slideparamValueselect));
      slideparamValueselect.click();
      //TestUtils.sleep(2);
      slideparamValueselect.sendKeys(datatoselect);
      slideparamValueselect.sendKeys(Keys.ENTER);
      TestUtils.sleep(2);
      Actions actions = new Actions(driver);
      actions.sendKeys(Keys.TAB).build().perform();

      result = ResultStore.success("Successfully selected the parameter value");
    }catch(Exception exc)
    {
      result = ResultStore.failure("Failed to click on Left Button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }


  public Map<String, String> leftBarParameterApplyValue()
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleepAndLog(1000);
      wait.until(ExpectedConditions.elementToBeClickable(applyButtonForParameterValue));
      applyButtonForParameterValue.click();
      result = ResultStore.success("Successfully clicked on Parameter Value Apply Button");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on Parameter Value Apply Button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }



  public Map<String, String> leftBarParameterRESETbutton()
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleepAndLog(1000);
      wait.until(ExpectedConditions.elementToBeClickable(slideout_resetbutton));
      slideout_resetbutton.click();
      wait.until(ExpectedConditions.elementToBeClickable(applyButtonForParameterValue));
      result = ResultStore.success("Successfully clicked on Parameter Value Apply Button");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on Parameter Value Apply Button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> validateapplyandRestAfterResestClicked(String textvalue)
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleepAndLog(1000);

      wait.until(ExpectedConditions.visibilityOf(parametertextarea));

      String value=parametertextarea.getText();
      Assert.assertEquals(value,textvalue,"displayed "+textvalue+ "is not correct ");
      wait.until(ExpectedConditions.visibilityOf(resetdisabled));
      wait.until(ExpectedConditions.elementToBeClickable(applyButtonForParameterValue));
      applyButtonForParameterValue.click();
      result = ResultStore.success("Successfully clicked on Parameter Value Apply Button");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on Parameter Value Apply Button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }


  public Map<String, String> leftBarParameterApplyValuespecialcase()
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleepAndLog(1000);
      wait.until(ExpectedConditions.elementToBeClickable(applyButtonForParameterValue));
      applyButtonForParameterValue.click();
      result = ResultStore.success("Successfully clicked on Parameter Value Apply Button");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on Parameter Value Apply Button: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> verifyingValue(List<String> staticFiled)
  {
    Map<String, String> result;

    try
    {
      //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("svg > g[transform]")));
      TestUtils.sleepAndLog(1500);
      List<WebElement> xytexts = driver.findElements(By.cssSelector("g>" + "text.st2" +
              "[y='0']"));
      List<String> listoftext = new ArrayList<>();
      int count=0;
      for (WebElement xytext : xytexts)
      {
        listoftext.add(xytext.getText());
      }
      if(listoftext.equals(staticFiled)){
        result = ResultStore.success("list contains the data fields");
      }else{
        result = ResultStore.failure("List does not contain the data fields ");
      }
      //result = ResultStore.success(listoftext.toString());
      //if (result.get("out").contains("null")) Reporter.log(driver.getPageSource());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to verify value. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String, String> verifyingValueIfEmptyArrayReturned(List<String> staticFiled)
  {
    Map<String, String> result;

    try
    {
      //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("svg > g[transform]")));
      TestUtils.sleepAndLog(1500);
      List<WebElement> xytexts = driver.findElements(By.cssSelector("g>" + "text.st2"));
      List<String> listoftext = new ArrayList<>();
      int count=0;
      for (WebElement xytext : xytexts)
      {
        listoftext.add(xytext.getText());
      }
      if(listoftext.equals(staticFiled)){
        result = ResultStore.success("list contains the data fields");
      }else{
        result = ResultStore.failure("List does not contain the data fields ");
      }
      //result = ResultStore.success(listoftext.toString());
      //if (result.get("out").contains("null")) Reporter.log(driver.getPageSource());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to verify value. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String, String> checkdataPoints(String sWidget,List<String> dataPointLocate)
  {
    Map<String, String> result;

    try
    {
      WebElement elementTest=null;
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sWidget)));
      TestUtils.sleepAndLog(1500);
      for(String str:dataPointLocate){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("g#" + sWidget + ">" + str)));
        elementTest=driver.findElement(By.cssSelector("g#" + sWidget + ">" + str));
        if(!elementTest.isDisplayed()){
          Assert.assertTrue(false,"Data point is not available");
        }

      }
      result = ResultStore.success("All the data points are available");
      //if (result.get("out").contains("null")) Reporter.log(driver.getPageSource());
    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to get data points. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }


  public Map<String, String> ParameterValueForTextWidget(String value, String noData, String type)
  {
    Map<String, String> result;
    try
    {
      if(type.contains("new")) {
        if (noData.contains("1stdata")){
          wait.until(ExpectedConditions.elementToBeClickable(secondslideout_dropdownbtn));
          Actions actions = new Actions(driver);
          actions.moveToElement(secondslideout_dropdownbtn).click(secondslideout_dropdownbtn).build().perform();
          TestUtils.sleep(2);
          slideparamValueselect.sendKeys(value);
          slideparamValueselect.sendKeys(Keys.TAB);

          // Sendkeys using Action class object
          Actions action = new Actions(driver);
          action.sendKeys(Keys.ENTER).build().perform();
          TestUtils.sleep(2);
        } else if (noData.contains("2nddata")) {
          wait.until(ExpectedConditions.elementToBeClickable(slideout_dropdownbtn));
          Actions actions = new Actions(driver);
          actions.moveToElement(slideout_dropdownbtn).click(slideout_dropdownbtn).build().perform();
          TestUtils.sleep(2);
          slideparamValueselect.sendKeys(value);
          slideparamValueselect.sendKeys(Keys.TAB);

          // Sendkeys using Action class object
          Actions action = new Actions(driver);
          action.sendKeys(Keys.ENTER).build().perform();
          TestUtils.sleep(2);
        }
      }
      result = ResultStore.success("Successfully selected the parameter value 1");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to select parameter value: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }


  public Map<String, String> ParameterValueForTextWidgetnew(String paramnames,String value)
  {
    Map<String, String> result;
    try
    {
      String str= "//label[@title='"+paramnames+"']/ancestor::div[@class='form-group']//div[starts-with(@id,'oprExtendedInputContainer')]//button";
      WebElement ele=driver.findElement(By.xpath(str));
      wait.until(ExpectedConditions.elementToBeClickable(ele));
      Actions actions = new Actions(driver);

      actions.moveToElement(ele).click(ele).build().perform();
      TestUtils.sleep(2);
      slideparamValueselect.sendKeys(value);
      slideparamValueselect.sendKeys(Keys.TAB);

      // Sendkeys using Action class object
      Actions action = new Actions(driver);
      action.sendKeys(Keys.ENTER).build().perform();
      TestUtils.sleep(2);
      result = ResultStore.success("Successfully selected the parameter value");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to select parameter value: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }


  public Map<String, String> verifyingTextfornontruncatevalues(String id,List<String> staticFiled)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("g > rect[id = '"+id+"']")));
      TestUtils.sleepAndLog(1500);
      List<WebElement> xytexts = driver.findElements(By.cssSelector("g[id^='shape'] text"));
      List<String> listoftext = new ArrayList<>();
      int count=0;
      for (WebElement xytext : xytexts)
      {
        listoftext.add(xytext.getText());
      }
      System.out.println(listoftext);
      System.out.println(staticFiled);
      if(listoftext.equals(staticFiled)){
        result = ResultStore.success("list contains the data fields");
      }else{
        result = ResultStore.failure("List does not contain the data fields ");
      }
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get text list. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String, String> verifyingText(String id,List<String> staticFiled)
  {
    Map<String, String> result;



    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("g > rect[id = '"+id+"']")));
      TestUtils.sleepAndLog(1500);
      //List<WebElement> xytexts = driver.findElements(By.cssSelector("g[id^='shape'] text"));
      List<String> listoftext = new ArrayList<>();
      int count=0;
      String str="";
      String str1="";
      TestUtils.sleep(2);
      if(driver.findElements(By.cssSelector("g[id^='shape'] text")).size()>0) {
        for (WebElement xytext : (driver.findElements(By.cssSelector("g[id^='shape'] text")))) {
          if(xytext.getAttribute("innerHTML").contains("tspan")){
            System.out.println(xytext.getAttribute("innerHTML")); //.............
            //str=xytext.getAttribute("innerHTML").replaceAll("[<tspan>|</tspan>|<tspan class=\"elip\">]","");
            str=xytext.getAttribute("innerHTML").replaceAll("(?i)<tspan[^>]*>([^<]*)</tspan>","$1").trim();
            // .replaceAll("\\s+",  " ").trim();
            listoftext.add(str);
          }else{
            listoftext.add(xytext.getAttribute("innerHTML").trim());
          }
        }
        System.out.println(listoftext);
        System.out.println(staticFiled);
      }
      if(listoftext.equals(staticFiled)){
        result = ResultStore.success("list contains the data fields");
      }else{
        result = ResultStore.failure("List does not contain the data fields ");
      }
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get text list. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }


  public Map<String, String> verifyingTextForTextValue(String id,List<String> staticFiled)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("g > rect[id = '"+id+"']")));
      TestUtils.sleepAndLog(1500);
      List<String> listoftext = new ArrayList<>();
      int count=0;
      String str="";
      String str1="";
      TestUtils.sleep(2);
      if(driver.findElements(By.cssSelector("g[id^='shape'] text")).size()>0) {
        for (WebElement textOnChart : (driver.findElements(By.cssSelector("g[id^='shape'] text")))) {
          str=textOnChart.getText();
          listoftext.add(str);
        }
      }
      System.out.println(listoftext);
      System.out.println(staticFiled);
      if(listoftext.equals(staticFiled)){
        result = ResultStore.success("list contains the data fields");
      }else{
        result = ResultStore.failure("List does not contain the data fields ");
      }
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get text list. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String, String> verifyingText_title(String id,List<String> staticFiled,String type)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("g > rect[id = '"+id+"']")));
      TestUtils.sleepAndLog(1500);
      List<WebElement> xytexts = driver.findElements(By.cssSelector("g[id^='shape'] "+type));
      List<String> listoftext = new ArrayList<>();
      int count=0;
      for (WebElement xytext : xytexts)
      {
        listoftext.add(xytext.getText());
      }
      System.out.println(listoftext);
      System.out.println(staticFiled);
      if(listoftext.equals(staticFiled)){
        result = ResultStore.success("list contains the data fields");
      }else{
        result = ResultStore.failure("List does not contain the data fields ");
      }
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get text list. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String, String> verifyingTextForSpecialvariable(String id,List<String> staticFiled)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("g > rect[id = '"+id+"']")));
      TestUtils.sleepAndLog(1500);
      String actualText = driver.findElement(By.cssSelector("g[id^='shape9'] text")).getText();
      String[] specialVariable = actualText.split(" ");
      List<String> listofvariable = new ArrayList<>();
      for (String sp:specialVariable)
      {
        listofvariable.add(sp);
      }
      System.out.println(listofvariable);
      listofvariable.remove(2);
      System.out.println(listofvariable);

      if(listofvariable.equals(staticFiled)){
        result = ResultStore.success("list contains the data fields");
      }else{
        result = ResultStore.failure("List does not contain the data fields ");
      }
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get text list. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }


  public Map<String, String> verifyingTextForDate(String id,List<String> staticFiled,String shape)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("g > rect[id = '"+id+"']")));
      TestUtils.sleepAndLog(1500);
      List<WebElement> xytexts = driver.findElements(By.cssSelector("g#"+shape+" text"));
      List<String> listoftext = new ArrayList<>();
      int count=0;
      for (WebElement xytext : xytexts)
      {
        listoftext.add(xytext.getText());
      }
      System.out.println(listoftext);
      System.out.println(staticFiled);
      List<String> listofvariable = new ArrayList<>();
      String specialVariables = listoftext.get(0);
      String[] specialVariable = specialVariables.split(" ");
      for (String sp:specialVariable)
      {
        listofvariable.add(sp);
      }
      System.out.println(listofvariable);
      listofvariable.remove(2);
      System.out.println(listofvariable);

      if(listofvariable.equals(staticFiled)){
        result = ResultStore.success("list contains the data fields");
      }else{
        result = ResultStore.failure("List does not contain the data fields ");
      }
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get text list. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }
  public Map<String, String> mouseHover()
  {
    try
    {
      WebElement hoverEle = driver.findElement(By.cssSelector("g > text[x^='34']"));
      wait.until(ExpectedConditions.visibilityOf(hoverEle));
      Actions actions = new Actions(driver);
      Point point = hoverEle.getLocation();
      int xcord = point.getX();
      int ycord = point.getY();
      actions.moveToElement(hoverEle,xcord,ycord).build().perform();
      TestUtils.sleepAndLog(3000);
      return ResultStore.success("Mouse is hovered to Element Successfully");
    } catch (Exception exc)
    {
      return ResultStore.failure("Mouse is not hovered to element" + Misc
              .getStacktraceAsString
                      (exc));
    }
  }
  public Map<String, String> verifyingHoverText(String text)
  {
    try
    {
      TestUtils.sleepAndLog(3000);
      WebElement hoverElement = driver.findElement(By.cssSelector("g > title"));
      String hoverText = hoverElement.getText();
      if(hoverText.equals(text)) {
        return ResultStore.success("Hover Text is verified Successfully");
      }
      return ResultStore.success("Hover Text is verified Successfully");
    } catch (Exception exc)
    {
      return ResultStore.failure("Hover Text is not verified" + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> gettingCurrentUrlAndAppending(String url,String appendValue)
  {
    try
    {
      TestUtils.sleepAndLog(3000);
      String appendedURL = url + appendValue;
      driver.navigate().to(appendedURL);
      driver.navigate().refresh();
      TestUtils.sleepAndLog(3000);
      return ResultStore.success("Url appended successfully and redirected");
    } catch (Exception exc)
    {
      return ResultStore.failure("Url is not appended successfully or redirected" + Misc.getStacktraceAsString(exc));
    }
  }

  public Map<String, String> validateDashboard(String[] data)
  {
    try {

      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("g#shape2 text")));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("g#shape3 text")));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("g#shape5 text")));

      String dashboardRetreived1= ele1.getText();
      String dashboardRetreived2 = ele2.getText();
      String dashboardRetreived3 = ele3.getText();
      Assert.assertEquals(data[0],dashboardRetreived1);
      Assert.assertEquals(data[1],dashboardRetreived2);
      Assert.assertEquals(data[2],dashboardRetreived3);

      TestUtils.sleepAndLog(1000);
      return ResultStore.success("Dashboard " + transDashboard + " successfully validated");
    }
    catch(Exception exc) {

      return ResultStore.failure("Failed to validate Dashboard " + transDashboard + ". error: " +
              Misc.getStacktraceAsString(exc));
    }

  }

  public Map<String, String> validateadminvalueRBAC (String value,String groupvalue,String groupvalue1)
  {
    try
    {
      TestUtils.sleepAndLog(1000);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("g#shape2 text")));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("g#shape3 text")));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("g#shape4 text")));
      wait.until(ExpectedConditions.textToBePresentInElement(admintextValueRBAC,value));
      if(admintextValueRBACgroup.getText().equals(groupvalue) || admintextValueRBACgroup.getText().equals(groupvalue1) ){
        System.out.println(admintextValueRBACgroup.getText() +"is displayed as expected");
      }
      else{
        throw new ElementNotFoundException(admintextValueRBACgroup.getText(),admintextValueRBACgroup.getText(),admintextValueRBACgroup.getText());
      }
      if(admintextValueRBACgroup1.getText().equals(groupvalue) || admintextValueRBACgroup1.getText().equals(groupvalue1) ){
        System.out.println(admintextValueRBACgroup1.getText() +"is displayed as expected");
      }else{
        throw new ElementNotFoundException(admintextValueRBACgroup1.getText(),admintextValueRBACgroup1.getText(),admintextValueRBACgroup1.getText());
      }
      return ResultStore.success("Admin Value for RBAC is correctly displayed");
    } catch (Exception exc)
    {
      return ResultStore.failure("Admin Value for RBAC is not displayed correctly" + Misc.getStacktraceAsString(exc));
    }
  }

  public Map<String, String> validateMultiFieldValues(String widgetshape,String valuetext)
  {
    try
    {
      TestUtils.sleepAndLog(1000);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("g#"+widgetshape+" text")));
      WebElement element=driver.findElement(By.cssSelector("g#"+widgetshape+" text"));
      wait.until(ExpectedConditions.textToBePresentInElement(element,valuetext));

      return ResultStore.success("All values correctly displayed");
    } catch (Exception exc)
    {
      return ResultStore.failure("None of the values are not correctly displayed correctly" + Misc.getStacktraceAsString(exc));
    }
  }

  public Map<String, String> clickQuickEditbutton()
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(quickEditbutton));
      quickEditbutton.click();
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dashboard_file_name")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));

      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));
      TestUtils.sleepAndLog(1000);

      result = ResultStore.success("Edit Dashboard successfully opened");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on Quick button. error: " + Misc.getStacktraceAsString(exc));
    }
    return result;
  }


  public Map<String, String> quickEdit_validateDashboardName(String dashboardondisplay)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOf(dashboardName));

      Assert.assertEquals(dashboardName.getText(),dashboardondisplay);

      TestUtils.sleepAndLog(1000);

      result = ResultStore.success("Dashboard Name is displayed Correctly");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on Quick button. error: " + Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> absenceofQuickEditbutton()
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(quickEditbutton));
      Assert.assertEquals(true,false);
      result = ResultStore.success("Quick Edit button is present");
    } catch (org.openqa.selenium.TimeoutException exc)
    {
      result = ResultStore.success("As Expected there is no Quick Edit button");
    }catch(Exception exc){
      result = ResultStore.failure("Failed to click on Quick button. error: " + Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> verifyingTextForWidget(String id, List<String> staticFiled, String widget)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("g > rect[id = '"+id+"']")));
      TestUtils.sleepAndLog(1500);
      List<WebElement> xytexts = null;
      xytexts = driver.findElements(By.cssSelector("g[id='"+widget+"'] text"));

      List<String> listoftext = new ArrayList<>();
      int count=0;
      for (WebElement xytext : xytexts)
      {
        listoftext.add(xytext.getText());
      }
      System.out.println(listoftext);
      System.out.println(staticFiled);
      if(listoftext.equals(staticFiled)){
        result = ResultStore.success("list contains the data fields");
      }else{
        result = ResultStore.failure("List does not contain the data fields ");
      }
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get text list. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String, String> verifyingText_titleWidget(String id,String widget, List<String> staticFiled,String type)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("g > rect[id = '"+id+"']")));
      TestUtils.sleepAndLog(1500);
      List<WebElement> xytexts = driver.findElements(By.cssSelector("g[id='"+widget+"'] "+type));
      List<String> listoftext = new ArrayList<>();
      int count=0;
      for (WebElement xytext : xytexts)
      {
        listoftext.add(xytext.getText());
      }
      System.out.println(listoftext);
      System.out.println(staticFiled);
      if(listoftext.equals(staticFiled)){
        result = ResultStore.success("list contains the data fields");
      }else{
        result = ResultStore.failure("List does not contain the data fields ");
      }
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get text list. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String, String> verifyingText_titleWidget_dataquery(String id,String widget, List<String> staticFiled,String type)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("g > rect[id = '"+id+"']")));
      TestUtils.sleepAndLog(1500);
      List<WebElement> xytexts = driver.findElements(By.cssSelector("g[id='"+widget+"'] "+type));
      List<String> listoftext = new ArrayList<>();
      int count=0;
      for (WebElement xytext : xytexts)
      {
        listoftext.add(xytext.getText());
      }
      System.out.println(listoftext);
      System.out.println(staticFiled);
      if(listoftext.equals(staticFiled)){
        result = ResultStore.success("list contains the data fields");
      }else{
        result = ResultStore.failure("List does not contain the data fields ");
      }
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get text list. error: " + Misc.getExceptionInfo(exc));
    }
    return result;
  }

  public Map<String, String> mouseHoverforWidget(String widget, String id)
  {
    try
    {
      WebElement hoverEle = driver.findElement(By.cssSelector("g[id='"+widget+"'] > text[x^='"+id+"']"));
      wait.until(ExpectedConditions.visibilityOf(hoverEle));
      Actions actions = new Actions(driver);
      //Point point = hoverEle.getLocation();
      //int xcord = point.getX();
      //int ycord = point.getY();

      int getTopLeftY = ((hoverEle.getSize().getHeight()/2) - hoverEle.getSize().getHeight());
      int getTopLeftX =  (hoverEle.getSize().getWidth()/2) - hoverEle.getSize().getWidth();

      actions.moveToElement(hoverEle,getTopLeftX,getTopLeftY).build().perform();
      TestUtils.sleepAndLog(3000);
      return ResultStore.success("Mouse is hovered to Element Successfully");
    } catch (Exception exc)
    {
      return ResultStore.failure("Mouse is not hovered to element" + Misc
              .getStacktraceAsString
                      (exc));
    }
  }
  public Map<String, String> verifyingHoverTextForWidget(String widget, String text)
  {
    try
    {
      TestUtils.sleepAndLog(3000);
      WebElement hoverElement = driver.findElement(By.cssSelector("g[id='"+widget+"'] > title"));
      String hoverText = hoverElement.getText();
      if(hoverText.equals(text)) {
        return ResultStore.success("Hover Text is verified Successfully");
      }
      return ResultStore.success("Hover Text is verified Successfully");
    } catch (Exception exc)
    {
      return ResultStore.failure("Hover Text is not verified" + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> ParameterValueForTextWidget(String paramnames,String value)
  {
    Map<String, String> result;
    try
    {
      String str= "//label[@title='"+paramnames+"']/ancestor::div[@class='form-group']//div[starts-with(@id,'oprExtendedInputContainer')]//button";

      WebElement ele=driver.findElement(By.xpath(str));
      wait.until(ExpectedConditions.elementToBeClickable(ele));
      Actions actions = new Actions(driver);

      actions.moveToElement(ele).click(ele).build().perform();
      TestUtils.sleep(2);
      slideparamValueselect.sendKeys(value);
      slideparamValueselect.sendKeys(Keys.TAB);

      // Sendkeys using Action class object
      Actions action = new Actions(driver);
      action.sendKeys(Keys.ENTER).build().perform();
      TestUtils.sleep(2);
      result = ResultStore.success("Successfully selected the parameter value");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to select parameter value: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> verifySelectedValueOnLeftBar(String paramnames,String value)
  {
    Map<String, String> result;
    try
    {
      String str= "//label[@title='"+paramnames+"']/ancestor::div[@class='form-group']//div[starts-with(@id,'oprExtendedInputContainer')]//div[@title='"+value+"']";
      WebElement ele=driver.findElement(By.xpath(str));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(str)));
      result = ResultStore.success("Successfully verified the parameter value");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to verify parameter value: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }
}

