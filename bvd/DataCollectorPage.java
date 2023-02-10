package com.hp.opr.qa.framework.pageobjects.bvd;


import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.hp.opr.qa.framework.utils.UiTestUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataCollectorPage
{

  final WebDriver driver;
  final WebDriverWait wait;


  public DataCollectorPage(WebDriver driver)
  {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, 15);
  }

  /************************
   /* HOME PAGE
   /************************/

  // Button Create New Query Top of List
  //@FindBy(how = How.CSS, using = ".opr-toolbar #btn-container-0 button")
  @FindBy(how = How.CSS, using = ".dropdown #dropdownNew1")
  public WebElement buttonNew;


  @FindBy(how = How.ID, using = "ssl-input")
  static WebElement sslCheckBox;

  @FindBy(how = How.CSS, using = "button[title='More']")
  public WebElement moreButton;

  public static int queryResultsElementsSize=0;
  // Button DB Settings Top of List
  @FindBy(how = How.CSS, using = ".opr-toolbar #btn-container-5 button")
  static WebElement buttonDbSettings;

  // Button Edit Query Top of List
  //@FindBy(how = How.CSS, using = ".opr-toolbar #btn-container-1 button")
  /*@FindBy(how = How.CSS, using = "button[type='button'][title='Edit']")
  static WebElement buttonEdit;*/
  @FindBy(how = How.CSS, using = "[title='Edit'][class='btn btn-secondary ng-scope']")
  static WebElement buttonEdit;

  @FindBy(how=How.XPATH,using="//opr-extended-input-container[starts-with(@id,'oprDropdown_')]")
  static List<WebElement> values;
  // Button Param Edit Query Top of List
  //@FindBy(how = How.CSS, using = "button[type='button'][title='Edit'][class='btn btn-secondary ng-scope']")
  //static WebElement buttonParamEdit;

  // Button Duplicate Top of List
  //@FindBy(how = How.CSS, using = ".opr-toolbar #btn-container-2 button")
  @FindBy(how = How.CSS, using = "button[type='button'][title='Duplicate']")
  static WebElement buttonDuplicate;

  @FindBy(how = How.XPATH, using = "//span[text()='Add']")
  static WebElement addValuelistbutton;

  @FindBy(how = How.XPATH, using = "//button[@title='Add']")
  static WebElement addValuelistbuttonafteraddfirst;

  @FindBy(how = How.CSS, using = "[name='labelField']")
  static WebElement valuelistlabelfield;

  @FindBy(how = How.CSS, using = "[name='valueField']")
  static WebElement valuelistvaluefield;

  @FindBy(how = How.CSS, using = "button[title='Apply']")
  static WebElement valuelistApplyfield;

  @FindBy(how = How.CSS, using = "button[title='Apply'][class*=btn-success]:not([class*='ng-hide']")
  static WebElement valuelistApplyfieldafterfirstapply;



//  // Button Activate Top of List
//  @FindBy(how = How.CSS, using = ".opr-toolbar #btn-container-4 button")
//  static WebElement buttonActivate;
//
//
//  // Button Pause Top of List
//  @FindBy(how = How.CSS, using = ".opr-toolbar #btn-container-5 button")
//  static WebElement buttonPause;
//
//  // Button Execute Top of List
//  @FindBy(how = How.CSS, using = ".opr-toolbar #btn-container-6 button")
//  static WebElement buttonExecute;

  // Button Delete Query Top of List
  //@FindBy(how = How.CSS, using = ".opr-toolbar [title='Delete']")
  @FindBy(how = How.CSS, using = "button[type='button'][title='Delete']")
  static WebElement buttonDelete;

  // Button Search button Top of List
  //@FindBy(how = How.CSS, using = ".opr-toolbar opr-reveal-panel button")
  @FindBy(how = How.CSS, using = "button[title='Search']")
  static WebElement buttonSearch;

  // Search Input Field
  @FindBy(how = How.CSS, using = ".opr-toolbar opr-reveal-panel .filter-input")
  static WebElement querySearchField;

  @FindBy(how = How.CSS, using = "[label='Search']")
  static WebElement querySearchCleanButton;

  @FindBy(how = How.CSS, using ="button i[title='Clear']")
  static WebElement   querySearchClearButton;


  // Edit Button On Query In List
  @FindBy(how = How.CSS, using = "opr-list-item a.active .list-buttons .hpe-icon-edit")
  static WebElement editButtonOnQuery;

  // Activate Button On Query In List
  @FindBy(how = How.CSS, using = "opr-list-item a.active .list-buttons .hpe-icon-play")
  static WebElement activateButtonOnQuery;

  // Deactivate Button On Query In List
  @FindBy(how = How.CSS, using = "opr-list-item a.active .list-buttons .hpe-icon-pause")
  static WebElement deactivateButtonOnQuery;

  // Delete Button On Query In List
  @FindBy(how = How.CSS, using = "opr-list-item a.active .list-buttons [title='Delete']")
  static WebElement deleteButtonOnQuery;

  @FindBy(how=How.LINK_TEXT,using="Create Data Query")
  static WebElement clickDataQuery;

  @FindBy(how = How.LINK_TEXT, using = "Create Parameter Query")
  static WebElement clickParameterQuery;
  /************************
   /* EDITOR Page
   /************************/

  // Query Name Field
  @FindBy(how = How.ID, using = "name-input_name")
  static WebElement queryNameField;

  // Query Description Field
  @FindBy(how = How.CSS, using = "textarea[name='description']")
  static WebElement queryDescriptionField;

  // Query Parameter Variable Field
  @FindBy(how = How.ID, using = "variable-input_name")
  static WebElement queryParameterVarField;
  // Query Parameter Description Field
  @FindBy(how = How.ID, using = "description-input")
  static WebElement queryParameterDescField;

  // Query Column Value Field
  /*@FindBy(how = How.XPATH, using = "//label[contains(text(),'Value Column')]/parent::div/following-sibling::div")
  static WebElement queryColumnValue;*/

  //@FindBy(how = How.CSS, using = "div.opr-dropdown-selection[title='Value column'] div")
  @FindBy(how = How.CSS, using = "div opr-dropdown[placeholder='Value column'] div button.opr-dropdown-selection-button>div")
  static WebElement queryColumnValue;

  /*@FindBy(how = How.XPATH, using = "//label[contains(text(),'Label Column')]/parent::div/following-sibling::div")
  static WebElement queryLabelValue;*/

  //@FindBy(how = How.CSS, using = "div.opr-dropdown-selection[title='Label column'] div")
  @FindBy(how = How.CSS, using = "div opr-dropdown[placeholder='Label column'] div button.opr-dropdown-selection-button>div")
  static WebElement queryLabelValue;

  @FindBy(how=How.CSS,using="div[class='list-title ng-binding ng-scope']")
  static List<WebElement> queryColumnValueData;
  @FindBy(how = How.CSS, using = "div[class='truncate-text ng-binding']:first-child")
  static WebElement queryColumnValueRead;

  // Query Param Display Name Field
  @FindBy(how = How.ID, using = "name-input_displayname")
  static WebElement queryDisplayNameField;

  // Query Tags Field
  @FindBy(how = How.CSS, using = "opr-tag-bar[name='tagsSelector'] input.opr-tag-bar-input")
  static WebElement queryTagsField;

  // Query Default Widget Radio Field
  @FindBy(how = How.ID, using = "queryformat-default")
  static WebElement defaultWidgetRadio;

  // Radio Button for Database query
  @FindBy(how = How.XPATH, using = "//div[contains(text(),'Database query')]")
  static WebElement dataDatabaseQuery;
  // Radio Button for Value list
  @FindBy(how = How.XPATH, using = "//div[contains(text(),'Value list')]")
  static WebElement dataValuelist;

  @FindBy(how = How.XPATH, using = "//div[contains(text(),'Date')]")
  static WebElement dateValuelist;

  // Query Default WidgetGroup Radio Field
  @FindBy(how = How.ID, using = "queryformat-widgetgroup")
  static WebElement widgetGroupRadio;

  // Parameter Default Radio Field
  @FindBy(how = How.ID, using = "radio1")
  static WebElement defaultNoneRadio;
  // Parameter Default Value Field
  @FindBy(how = How.ID, using = "radio2")
  static WebElement defaultValueRadio;
  // Parameter Default Value Field
  //@FindBy(how = How.XPATH, using = "//ul/li[2]/input")
  //static WebElement defaultValueText;


  @FindBy(how = How.CSS, using = "div.radio input[type='text']")
  static WebElement defaultValueText;

  // Query Retention Value Field
  @FindBy(how = How.CSS, using = "[name='retentionValueSelector']")
  static WebElement retentionValueField;

  // Query Retention Period Field
  @FindBy(how = How.CSS, using = ".opr-search2-input-cell input")
  static WebElement retentionPeriodField;

  // Query SQL Field
  @FindBy(how = How.CSS, using = "#query-text .ace_content")
  static WebElement querySqlField;

  // Button Execute Query
  @FindBy(how = How.ID, using = "buttonExecuteQuery")
  static WebElement buttonExecuteQuery;

  // Query Result Title
  @FindBy(how = How.CSS, using = "h3.queryResultTitle")
  static WebElement queryResultTitle;


  // Button Save Query
  @FindBy(how = How.ID, using = "buttonConfirm")
  static WebElement buttonSaveQuery;

  @FindBy(how = How.ID, using = "button#buttonConfirm[disabled='disabled']")
  static WebElement buttonSaveQueryDisabled;

  // Button Save Query
  @FindBy(how = How.ID, using = "buttonCancel")
  static WebElement buttonCancelQuery;

  // Activate Query After Save checkBox
  @FindBy(how = How.CSS, using = "input#active + span")
  static WebElement activateAfterSaveCheckBox;

  // Confirmation deletion on Query in list
  @FindBy(how = How.CSS, using = ".delete-mode-yes")
  static WebElement buttonYesQueryDeletion;


  /************************
   /* Query Info Window
   /************************/


  // Query Info Window Query Name
  @FindBy(how = How.CSS, using = ".col-sm-8 h2")
  static WebElement infoQueryName;

  // Query Info Window Query Description
  @FindBy(how = How.CSS, using = ".col-sm-8 h3")
  static WebElement infoQueryDescription;

  // Query Info Window Query Status
  @FindBy(how = How.CSS, using = ".col-sm-8 label")
  static WebElement infoQueryStatus;


  /************************
   /* DB Connection
   /************************/

  @FindBy(how = How.ID, using = "buttonDBSetting")
  static WebElement buttonDBSetting;

  @FindBy(how = How.CSS, using = "input[name='hostName']")
  static WebElement hostNameField;

  @FindBy(how = How.CSS, using = "input[name='port']")
  static WebElement portField;

  @FindBy(how = How.CSS, using = "#ssl-input + span")
  static WebElement sslField;

  @FindBy(how = How.CSS, using = "input[name='dbName']")
  static WebElement dbNameField;

  @FindBy(how = How.CSS, using = "input[name='dbUser']")
  static WebElement dbUserField;

  @FindBy(how = How.CSS, using = "input[name='dbPassword']")
  static WebElement dbPasswordField;

  @FindBy(how = How.CSS, using = "input[name='dbPasswordConfirm']")
  static WebElement dbPasswordConfirmField;

  @FindBy(how = How.ID, using = "buttonTestConnection")
  static WebElement buttonTestConnection;

  @FindBy(how = How.CSS, using = ".alert.testConnectionAlert")
  static WebElement testConnectionAlert;

  @FindBy(how = How.CSS, using = ".alert.testConnectionAlert.alert-success")
  static WebElement testConnectionAlertsucess;

  @FindBy(how = How.ID, using = "buttonSaveDBSetting")
  static WebElement buttonSaveDBSetting;

  @FindBy(how = How.CSS, using = "footer-section .btn-secondary")
  static WebElement buttonCancelDBSetting;

  @FindBy(how = How.ID, using = "btnDialogSubmit")
  static WebElement buttonConfirmQueryDeletion;

  @FindBy(how = How.CSS, using = ".toolbar-panel-content .cg-busy-backdrop-animation.ng-hide")
  static WebElement spinnerBusy;

  @FindBy(how = How.CSS, using = ".opr-toolbar [title='Download']")
  static WebElement downloadQueryTopToolbar;

  //@FindBy(how = How.CSS, using = "opr-context-actions .opr-context-actions-launcher")
  @FindBy(how = How.CSS, using = ".dropdown #dropdownNew3")
  static WebElement toolBarDropDownMenuButton;

  //@FindBy(how = How.CSS, using = "[title='Export Selected']")
  @FindBy(how = How.PARTIAL_LINK_TEXT, using = "Export Selected")
  static WebElement exportSelectedQuery;

  //@FindBy(how = How.CSS, using = "[title='Export']")
  @FindBy(how = How.PARTIAL_LINK_TEXT, using = "Export")
  static WebElement exportNotSelectedQuery;

  @FindBy(how = How.PARTIAL_LINK_TEXT, using = "DB connection settings")
  static WebElement dbconnectionsettingdropdown;


  //@FindBy(how = How.CSS, using = "[title='Import']")
  @FindBy(how = How.PARTIAL_LINK_TEXT, using = "Import")
  static WebElement importQuery;

  // For Specific Query List click
  @FindBy(how=How.CSS,using="div[class='list-title ng-isolate-scope']")
  static List<WebElement> QueryNameValidation;

  //@FindBy(how = How.CSS, using = "p[class='info ng-binding']")
  @FindBy(how = How.CSS, using = "p.info.ng-binding")
  static WebElement valueErrorInfo;

  @FindBy(how = How.CSS, using = "button[id='buttonConfirm']")
  static WebElement saveDataQueryButton;

  @FindBy(how=How.CSS,using="div.alert-inverse")
  static WebElement rbacdataquererror;

  @FindBy(how=How.XPATH,using="(//button[@title='Delete'])[5]")
  static WebElement semicolonValueDelete;

  @FindBy(how=How.XPATH,using="(//div[@class='opr-grid-row opr-grid-data-row qtm-tr ng-scope'])[5]")
  static WebElement semicolonValueRow;

  @FindBy(how=How.NAME,using="calendar")
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


  @FindBy(how=How.CSS,using="div.date-string textarea")
  static WebElement parametertextarea;


  static int count=0;

  private Map<String, String> clickElementAndReturnResultDynamicWait(WebElement element,
                                                                     String successMessage,
                                                                     String failureMessage)
  {
    try
    {
      wait.until(ExpectedConditions.visibilityOf(element));
      wait.until(ExpectedConditions.elementToBeClickable(element));
      element.click();
      return ResultStore.success(successMessage);
    } catch (Exception e)
    {
      return ResultStore.failure(failureMessage);
    }
  }

  private Map<String, String> clickElementDynamicWait(WebElement element)
  {
    try
    {
      wait.until(ExpectedConditions.visibilityOf(element));
      wait.until(ExpectedConditions.elementToBeClickable(element));
      element.click();
      return ResultStore.success("Button clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("Button not clicked" + Misc.getStacktraceAsString
              (exc));
    }
  }


  private Map<String, String> clickParentElementDynamicWait(WebElement element)
  {
    try
    {
      wait.until(ExpectedConditions.visibilityOf(element));
      wait.until(ExpectedConditions.elementToBeClickable(element));
      WebElement parent = element.findElement(By.xpath(".."));
      parent.click();
      return ResultStore.success("Button clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("Button not clicked" + Misc.getStacktraceAsString
              (exc));
    }
  }


  public WebElement waitVisibility(WebElement elem) throws NoSuchElementException, TimeoutException
  {
    return wait.until(ExpectedConditions.visibilityOf(elem));
  }

  public void waitVisibilityLocatedById(String elem) throws NoSuchElementException, TimeoutException
  {
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elem)));
  }

  public void waitVisibilityLocatedByCss(String elem) throws NoSuchElementException,
          TimeoutException
  {
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(elem)));
  }

  public void waitClearAndSendKeysToElement(WebElement element, String keys)
  {

    waitVisibility(element);
    element.clear();
    element.sendKeys(keys);

  }

  public void waitSendKeysToElementEnter(WebElement element, String keys)
  {

    waitVisibility(element);
    element.sendKeys(keys);
    element.sendKeys(Keys.ENTER);

  }

  /************************
   /* New Query Creation
   /************************/


  public Map<String, String> clickNewQuery(String text)
  {
    try
    {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      UiTestUtils.switchToContentFrame(driver, wait);
      TestUtils.sleepAndLog(2000);
      clickElementDynamicWait(buttonNew);
      if (text.equals("DataQuery")) {
        wait.until(ExpectedConditions.elementToBeClickable(clickDataQuery));
        clickDataQuery.click();
        waitVisibility(queryNameField);

      } else if (text.equals("ParameterQuery")){
        wait.until(ExpectedConditions.elementToBeClickable(clickParameterQuery));
        clickParameterQuery.click();
        waitVisibility(queryDisplayNameField);
      }
      return ResultStore.success("New Data Query Button Clicked");
    }  catch (Exception exc)
    {

      return ResultStore.failure("New Query Button not Clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> typeTextIntoQueryName(String text, String query)
  {
    try{
      if (query.equals("DataQuery")) {
        waitClearAndSendKeysToElement(queryNameField, text);
      }else if (query.equals("ParameterQuery")) {
        waitClearAndSendKeysToElement(queryDisplayNameField, text);
      }
      return ResultStore.success("Query Title typed Successfully");
    } catch (Exception exc)
    {
      return ResultStore.failure("Query Title not typed. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> typeTextIntoQueryDescription(String text,String query)
  {
    try
    {
      if (query.equals("DataQuery")) {
        waitClearAndSendKeysToElement(queryDescriptionField, text);
      }
      else if (query.equals("ParameterQuery")){
        waitClearAndSendKeysToElement(queryParameterDescField, text);
      }
      return ResultStore.success("Query Description typed Successfully");
    } catch (Exception exc)
    {
      return ResultStore.failure("Query Description not typed. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> typeTextIntoQueryParamVariable(String text) {
    try {
      waitClearAndSendKeysToElement(queryParameterVarField, text);
      return ResultStore.success("Query Param Variable typed Successfully");
    } catch (Exception exc) {
      return ResultStore.failure("Query Param Variable not typed. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }


  public Map<String, String> createTag(String text)
  {
    try
    {
      waitSendKeysToElementEnter(queryTagsField, text);
      return ResultStore.success("Query tag typed Successfully");
    } catch (Exception exc)
    {
      return ResultStore.failure("Query tag not typed. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> switchFormat(String text)
  {
    try
    {
      if (text.equals("Default"))
      {

        defaultWidgetRadio.click();
      }
      else if (text.equals("Use in widget group"))
      {
        widgetGroupRadio.click();
      }

      return ResultStore.success("format selected Successfully");
    } catch (Exception exc)
    {
      return ResultStore.failure("Format not selected. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> typeTextIntoQuery(String text)
  {
    try
    {

      new Actions(driver).sendKeys(Keys.TAB).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys
              (Keys.DELETE).sendKeys(text).build().perform();

      return ResultStore.success("Query typed Successfully");
    } catch (Exception exc)
    {
      return ResultStore.failure("Query  not typed. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String,String> checkSemicolonInOSList(String text, String expectedText, String queryType, String value, String label)
  {
    try{
      new Actions(driver).sendKeys(Keys.TAB).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys
              (Keys.DELETE).sendKeys(text).build().perform();
      clickElementDynamicWait(buttonExecuteQuery);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".queryResultData .table")));
      waitVisibility(queryResultTitle);
      wait.until(ExpectedConditions.visibilityOf(valueErrorInfo));
      Assert.assertTrue(valueErrorInfo.getText().equals(expectedText));

      if(queryType.equals("Data Query")){
        Assert.assertFalse(saveDataQueryButton.isEnabled());
        switchFormat("Default");
      }
      else
      {
        switchDefault("None","");
        Assert.assertFalse(saveDataQueryButton.isEnabled());
        switchData("Database query");
        switchTab();
        switchTab();
      }
      return ResultStore.success("Semicolon value defect in os list validated successfully ");
    }
    catch(Exception exc)
    {
      return ResultStore.failure("Semicolon value defect in os list not validated successfully  " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }


  public Map<String,String> checkRemovingSemicolonInOSList(String text, String queryType, String value, String label)
  {
    try{

      new Actions(driver).sendKeys(Keys.TAB).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys
              (Keys.DELETE).sendKeys(text).build().perform();
      clickElementDynamicWait(buttonExecuteQuery);
      TestUtils.sleepAndLog(5000);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".queryResultData .table")));
      JavascriptExecutor scroll = (JavascriptExecutor)driver;
      scroll.executeScript("arguments[0].scrollIntoView();", queryResultTitle);
      waitVisibility(queryResultTitle);
      if(driver.findElements(By.cssSelector("p.info.ng-binding")).size() != 0)
      {
        Assert.assertFalse(valueErrorInfo.getText().equals("Invalid values: the parameter query returns values with a semicolon"));

      }
      if(queryType.equals("Data Query")){
        Assert.assertTrue(saveDataQueryButton.isEnabled());
        switchFormat("Default");
      }
      else
      {
        selectValueColumn(value);
        selectLabelColumn(label);
        switchDefault("None","");
        Assert.assertTrue(saveDataQueryButton.isEnabled());
        switchData("Database query");
        switchTab();
        switchTab();
      }

      return ResultStore.success("Semicolon value after removing using query  validated successfully ");
    }
    catch(Exception exc)
    {
      return ResultStore.failure("Semicolon value after removing using query not  validated successfully   " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String,String> checksemicolonValue(Map<String,String> map1,String expectedMessage) {

    try{
      createNonqueryValueList(map1);
      wait.until(ExpectedConditions.visibilityOf(valueErrorInfo));
      Assert.assertTrue(valueErrorInfo.getText().equals(expectedMessage));
      Assert.assertFalse(buttonSaveQuery.isEnabled());

      Actions move = new Actions(driver);
      move.moveToElement(semicolonValueRow).click();
      Action moveToDelete = move.moveToElement(semicolonValueDelete).build();
      moveToDelete.perform();
      wait.until(ExpectedConditions.visibilityOf(semicolonValueDelete));
      semicolonValueDelete.click();
      count--;
      Assert.assertTrue(driver.findElements(By.cssSelector("p.info.ng-binding")).size() == 0);
      Assert.assertTrue(buttonSaveQuery.isEnabled());
      return ResultStore.success("Value validated successfully");
    }
    catch(Exception exc)
    {
      return ResultStore.failure("value not validated successfully: "+
              Misc.getStacktraceAsString(exc));
    }
  }

  public Map<String,String> checkSemicolonDefaultValue (String value,String expectedMessage) {

    try{
      defaultValueRadio.click();
      defaultValueText.clear();
      defaultValueText.click();
      defaultValueText.sendKeys(value);
      wait.until(ExpectedConditions.visibilityOf(valueErrorInfo));
      Assert.assertTrue(valueErrorInfo.getText().equals(expectedMessage));
      Assert.assertFalse(buttonSaveQuery.isEnabled());
      defaultValueText.clear();
      Assert.assertTrue(driver.findElements(By.cssSelector("p.info.ng-binding")).size()!= 0);
      Assert.assertTrue(driver.findElements(By.cssSelector("button#buttonConfirm[disabled='disabled']")).size()!=0);
      return ResultStore.success("Default Value validated successfully");

    }
    catch(Exception exc)
    {
      return ResultStore.failure("value not validated successfully: "+
              Misc.getStacktraceAsString(exc));
    }
  }

  public Map<String, String> typeTextIntoQueryafterClearing(String text)
  {
    try
    {
      waitClearAndSendKeysToElement(querySqlField,text);
      return ResultStore.success("Query typed Successfully");
    } catch (Exception exc)
    {
      return ResultStore.failure("Query  not typed. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }




  public Map<String, String> clickExecuteQuery()
  {
    try
    {
      clickElementDynamicWait(buttonExecuteQuery);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".queryResultData .table")));
      waitVisibility(queryResultTitle);
      return ResultStore.success("Execute query Clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("Execute query not clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> checkQueryResult(String tableName, String tableData)
  {
    try
    {
      waitVisibility(queryResultTitle);
      TestUtils.sleepAndLog(3000);
      List<WebElement> queryResultsElements = driver.findElements(By.cssSelector(".col-sm-12 .queryResultData table tr .ng-binding"));
      if (queryResultsElements.get(0).getText().equalsIgnoreCase(tableName)
              && queryResultsElements.get(1)
              .getText().equalsIgnoreCase(tableData))
      {
        return ResultStore.success("Query Data Correct");
      }

      return ResultStore.failure("Query Data Wrong");
    } catch (Exception exc)
    {
      return ResultStore.failure("Execute query not clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }
  /*public Map<String, String> checkQueryResultforRBAC(String column1, String column2,String column3,String column4)
  {
    try
    {
      waitVisibility(queryResultTitle);
      TestUtils.sleepAndLog(3000);
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".col-sm-12 .queryResultData table tr .ng-binding")));
      List<WebElement> queryResultsElements = driver.findElements(By.cssSelector(".col-sm-12 .queryResultData table tr .ng-binding"));
      if (queryResultsElements.get(0).getText().equalsIgnoreCase(column1)
              && queryResultsElements.get(1)
              .getText().equalsIgnoreCase(column2)&&(queryResultsElements.get(2).getText().equalsIgnoreCase(column3) || queryResultsElements.get(2).getText().equalsIgnoreCase(column4))
              && (queryResultsElements.get(3).getText().equalsIgnoreCase(column4) || queryResultsElements.get(3).getText().equalsIgnoreCase(column3) ))
      {
        return ResultStore.success("Query Data Correct");
      }

      return ResultStore.failure("Query Data Wrong");
    } catch (Exception exc)
    {
      return ResultStore.failure("Execute query not clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }*/

  public Map<String, String> checkQueryResultforMultiSeries(String column1, String column2,String column3,String column4)
  {
    try
    {
      waitVisibility(queryResultTitle);
      JavascriptExecutor scroll = (JavascriptExecutor)driver;
      scroll.executeScript("arguments[0].scrollIntoView();", queryResultTitle);
      TestUtils.sleepAndLog(3000);
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".col-sm-12 .queryResultData table tr .ng-binding")));
      List<WebElement> queryResultsElements = driver.findElements(By.cssSelector(".col-sm-12 .queryResultData table tr .ng-binding"));
      if (queryResultsElements.get(0).getText().equalsIgnoreCase(column1)
              && queryResultsElements.get(1)
              .getText().equalsIgnoreCase(column2)&&queryResultsElements.get(2).getText().equalsIgnoreCase(column3)
              && queryResultsElements.get(3).getText().equalsIgnoreCase(column4))
      {
        return ResultStore.success("Query Data Correct");
      }

      return ResultStore.failure("Query Data Wrong");
    } catch (Exception exc)
    {
      return ResultStore.failure("Execute query not clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }
  public Map<String, String> checkQueryResultforRBAC()
  {
    try
    {

      waitVisibility(queryResultTitle);
      TestUtils.sleepAndLog(3000);
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".col-sm-12 .queryResultData table tr .ng-binding")));
      List<WebElement> queryResultsElements = driver.findElements(By.cssSelector(".col-sm-12 .queryResultData table tr .ng-binding"));
      queryResultsElementsSize=queryResultsElements.size();
      String column1="";
      String column2="";
      String column3="";
      String column4="";
      if(queryResultsElements.size()>2) {
        column1="?column?";
        column2="groupname";
        column3="bvd_admin_group";
        column4="Administrators";
        if (queryResultsElements.get(0).getText().equalsIgnoreCase(column1)
                && queryResultsElements.get(1)
                .getText().equalsIgnoreCase(column2) && (queryResultsElements.get(2).getText().equalsIgnoreCase(column3) || queryResultsElements.get(2).getText().equalsIgnoreCase(column4))
                && (queryResultsElements.get(3).getText().equalsIgnoreCase(column4) || queryResultsElements.get(3).getText().equalsIgnoreCase(column3))) {
          return ResultStore.success("Query Data Correct");
        }
      }else{
        column1="groupname";
        column2="Administrators";
        if (queryResultsElements.get(0).getText().equalsIgnoreCase(column1)
                && queryResultsElements.get(1)
                .getText().equalsIgnoreCase(column2)) {
          return ResultStore.success("Query Data Correct");
        }
      }

      return ResultStore.failure("Query Data Wrong");
    } catch (Exception exc)
    {
      return ResultStore.failure("Execute query not clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }


  public Map<String, String> clickSaveQuery()
  {
    try
    {
      clickElementDynamicWait(buttonSaveQuery);
//      waitVisibilityLocatedByCss(".opr-tree-view-control-area");
      return ResultStore.success("Save query Clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("Save query not clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> clickCancelQuery()
  {
    try
    {
      clickElementDynamicWait(buttonCancelQuery);
      waitVisibilityLocatedByCss(".opr-tree-view-control-area");
      return ResultStore.success("Cancel query Clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("Cancel query not clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }


  /************************
   /* Queries List
   /************************/


  public Map<String, String> clickQueryInList(String queryTitle)
  {
    try
    {
      TestUtils.sleep(5);
      waitVisibilityLocatedByCss("div.opr-tree-view-control-area");
      clickElementAndReturnResultDynamicWait(driver.findElement(By.cssSelector(String.format
              ("[text^='%s']", queryTitle))), "Query Clicked", "Query not Clicked");
      String activeQueryName = driver.findElement(By.cssSelector("opr-list-item .active " +
              ".list-title")).getText();

      return ResultStore.success("Query in list Clicked. Selected " + activeQueryName);
    } catch (Exception exc)
    {

      return ResultStore.failure("Query in list NOT Clicked. error: " + Misc.getStacktraceAsString
              (exc));
    }
  }

  public Map<String, String> clickQueryInListImpExp(String queryTitle)
  {
    try
    {
      TestUtils.sleep(3);
      waitVisibilityLocatedByCss(".opr-tree-view-control-area");
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".opr-context-actions-list[role='menu']")));
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[opr-tooltip*='" +
              queryTitle + "']"))).click();
      String activeQueryName = driver.findElement(By.cssSelector("opr-list-item .active " +
              ".list-title")).getText();

      return ResultStore.success("Query in list Clicked. Selected " + activeQueryName);
    } catch (Exception exc)
    {

      return ResultStore.failure("Query in list NOT Clicked. error: " + Misc.getStacktraceAsString
              (exc));
    }
  }
  public Map<String, String> checkQueryInListfirsttime(String queryTitle)
  {
    try
    {

      TestUtils.sleep(3);
      driver.switchTo().frame("contentFrame");
      waitVisibilityLocatedByCss("div.opr-tree-view-control-area");
      List<WebElement> queryExists = driver.findElements(By.cssSelector(String.format("[text='%s']",
              queryTitle)));
      if (queryExists.size()>0)
      {
        return ResultStore.success("Query Exists");
      }
      else
      {
        return ResultStore.failure("Query does not exist");
      }
    } catch (Exception exc)
    {

      return ResultStore.failure("Checking Query in list Failed. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }
  public Map<String, String> checkQueryInList(String queryTitle)
  {
    try
    {

      TestUtils.sleep(3);
      waitVisibilityLocatedByCss("div.opr-tree-view-control-area");
      List<WebElement> queryExists = driver.findElements(By.cssSelector(String.format("[text='%s']",
              queryTitle)));
      if (queryExists.size()>0)
      {
        return ResultStore.success("Query Exists");
      }
      else
      {
        return ResultStore.failure("Query does not exist");
      }
    } catch (Exception exc)
    {

      return ResultStore.failure("Checking Query in list Failed. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> selectQueryUsingActions(String queryTitle,String queryDescription)
  {
    try
    {

      TestUtils.sleep(3);
      waitVisibilityLocatedByCss("div.opr-tree-view-control-area");
      WebElement ele=driver.findElement(By.xpath("//div[contains(text(),'"+queryDescription+"')]"));
      Actions act1=new Actions(driver);
      act1.moveToElement(ele).click(ele).build().perform();
        return ResultStore.success("Query selected");
    } catch (Exception exc)
    {

      return ResultStore.failure("Checking Query in list Failed. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> multipleQuerySelect(String queryTitle, String queryTitle2)
  {
    try
    {
      TestUtils.sleep(2);
      waitVisibilityLocatedByCss(".opr-tree-view-control-area");
      WebElement query1 = driver.findElement(By.cssSelector(String.format("[text='%s']",
              queryTitle)));
      WebElement query2 = driver.findElement(By.cssSelector(String.format("[text='%s']",
              queryTitle2)));
      TestUtils.sleep(2);
      Actions actions = new Actions(driver);
      actions.click(query1)
              .keyDown(Keys.CONTROL)
              .click(query2)
              .keyUp(Keys.CONTROL)
              .build()
              .perform();
      TestUtils.sleep(2);

      return ResultStore.success("Query in list Clicked");
    } catch (Exception exc)
    {

      return ResultStore.failure("Query in list NOT Clicked. error: " + Misc.getStacktraceAsString
              (exc));
    }
  }


  /************************
   /* Query Read
   /************************/


  public Map<String, String> clickEditQuery(String text)
  {
    try
    {
      clickElementDynamicWait(buttonEdit);
      if (text.equals("DataQuery")) {
        //clickElementDynamicWait(buttonEdit);
        waitVisibility(queryNameField);
      } else if (text.equals("ParameterQuery")){
        //clickElementDynamicWait(buttonEdit);
        waitVisibility(queryDisplayNameField);
      }

      return ResultStore.success("Edit query Clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("Edit query not clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }


  public Map<String, String> readQueryName(String text, String type) {
    try {
      //Thread.sleep(2000);
      TestUtils.sleep(2);
      JavascriptExecutor js = (JavascriptExecutor) driver;
      String fieldData = "";
      if (type.equals("DataQuery")) {
        fieldData = js.executeScript("return arguments[0].value", queryNameField).toString();
      }
      else if (type.equals("ParameterQuery")){
        fieldData = js.executeScript("return arguments[0].value", queryDisplayNameField).toString();
      }
      if (fieldData.contains(text)) {
        return ResultStore.success("Query Name equals to " + text);
      } else {
        return ResultStore.failure("Query Name not equals. Got:" + fieldData);
      }
    } catch (Exception exc) {
      return ResultStore.failure(" error: " + Misc.getStacktraceAsString(exc));
    }
  }



  public Map<String, String> readQueryNameforqueryOptions(String text, String type) {
    try {
      //Thread.sleep(2000);
      TestUtils.sleep(2);
      JavascriptExecutor js = (JavascriptExecutor) driver;
      String fieldData = "";

      List<WebElement> getTextforQuery=driver.findElements(By.cssSelector("div.form-group h2"));
      TestUtils.sleep(1);
      fieldData=getTextforQuery.get(0).getText();
      if (fieldData.equalsIgnoreCase(text)) {
        return ResultStore.success("Query Name equals to " + text);
      } else {
        return ResultStore.failure("Query Name not equals. Got:" + fieldData);
      }
    } catch (Exception exc) {
      return ResultStore.failure(" error: " + Misc.getStacktraceAsString(exc));
    }
  }


  public Map<String, String> readVariableName(String text) {
    try {
      Thread.sleep(2000);
      JavascriptExecutor js = (JavascriptExecutor) driver;

      String fieldData = js.executeScript("return arguments[0].value", queryParameterVarField).toString();
      if (fieldData.contains(text)) {
        return ResultStore.success("Variable Name equals to " + text);
      } else {
        return ResultStore.failure("Variable Name not equals. Got:" + fieldData);
      }
    } catch (Exception exc) {
      return ResultStore.failure(" error: " + Misc.getStacktraceAsString(exc));
    }
  }



  public Map<String, String> readQueryDescription(String text)
  {
    try
    {

      JavascriptExecutor js = (JavascriptExecutor) driver;
      String fieldData = js.executeScript("return arguments[0].value", queryDescriptionField)
              .toString();
      if (fieldData.contains(text))
      {
        return ResultStore.success("Query Description equals to " + text);
      }
      else
      {
        return ResultStore.failure("Query Description not equals. Got:" + fieldData);
      }
    } catch (Exception exc)
    {
      return ResultStore.failure("error: " + Misc.getStacktraceAsString(exc));
    }

  }


  public Map<String, String> readTags(List<String> tagsArray)
  {
    try
    {
      List<String> tagsQuery = new ArrayList<>();

      List<WebElement> elems = driver.findElements(By.cssSelector(".opr-tag-bar span"));
      for (WebElement elem : elems)
      {
        tagsQuery.add(elem.getText());
      }
      if (tagsArray.equals(tagsQuery))
      {
        return ResultStore.success("Tags exists");
      }
      else
      {
        return ResultStore.failure("Tags check Fail");
      }

    } catch (Exception exc)
    {
      return ResultStore.failure("error: " + Misc.getStacktraceAsString(exc));
    }

  }

  public Map<String, String> readChannel(List<String> tagsArray)
  {
    try
    {
      List<String> tagsQuery = new ArrayList<>();
      List<WebElement> elems = driver.findElements(By.cssSelector(".col-sm-6 .itemArrayContainer " +
              "span"));
      for (WebElement elem : elems)
      {

        tagsQuery.add(elem.getText());
      }
      if (tagsArray.equals(tagsQuery))
      //if (tagsArray.contains(tagsQuery))
      {
        return ResultStore.success("Tags exists");
      }
      else
      {
        return ResultStore.failure("Tags check Fail");
      }

    } catch (Exception exc)
    {
      return ResultStore.failure("error: " + Misc.getStacktraceAsString(exc));
    }

  }

  public Map<String, String> readChannelforDelete(List<String> tagsArray)
  {
    try
    {
      List<String> tagsQuery = new ArrayList<>();
      /*List<WebElement> elems = driver.findElements(By.cssSelector(".col-sm-6 .itemArrayContainer " +
              "span"));*/
      List<WebElement> elems = driver.findElements(By.cssSelector(".itemArrayContainer " +
              "span"));
      for (WebElement elem : elems)
      {

        tagsQuery.add(elem.getText());
      }
      //if (tagsArray.equals(tagsQuery))
      if (tagsQuery.containsAll(tagsArray))

      {
        return ResultStore.success("Tags exists");
      }
      else
      {
        return ResultStore.failure("Tags check Fail");
      }

    } catch (Exception exc)
    {
      return ResultStore.failure("error: " + Misc.getStacktraceAsString(exc));
    }

  }



  public Map<String, String> readFormat(String text)
  {
    try
    {
      TestUtils.sleepAndLog(1000);
      String format = driver.findElement(By.cssSelector(".queryFormatContainer " +
              "input[type='radio']:checked +span")).getText();
      if (format.contains(text))
      {
        return ResultStore.success("Radio set to " + text);
      }
      else
      {
        return ResultStore.failure("Wrong radio button set. Got:" + format);
      }
    } catch (Exception exc)
    {
      return ResultStore.failure("error: " + Misc.getStacktraceAsString(exc));
    }

  }


  public Map<String, String> readSQLField(String text)
  {
    try
    {
      TestUtils.sleepAndLog(1000);
      String fieldData = querySqlField.getText().replaceAll("\\s", "");
      System.out.println("@@@@@@@@@@ " + fieldData);

      if (fieldData.contains(text.replaceAll("\\s", "")))
      {
        return ResultStore.success("SQL field contains correct data " + text);
      }
      else
      {
        return ResultStore.failure("SQL field contains wrong data. Got:" + fieldData);
      }
    } catch (Exception exc)
    {
      return ResultStore.failure("error: " + Misc.getStacktraceAsString(exc));
    }

  }


  /************************
   /* Query Update
   /************************/


  public Map<String, String> deleteTag(String text)
  {
    try
    {

      WebElement tag = driver.findElement(By.cssSelector(".opr-tag-bar span[title='" + text + "']" +
              " + " +
              "button"));

      clickElementDynamicWait(tag);

      return ResultStore.success("Tag successfully deleted ");

    } catch (Exception exc)
    {
      return ResultStore.failure("error: " + Misc.getStacktraceAsString(exc));
    }

  }


  /************************
   /* Query Delete
   /************************/

  public Map<String, String> clickDeleteQueryInList()
  {
    try
    {
      clickElementDynamicWait(deleteButtonOnQuery);
      clickElementDynamicWait(buttonYesQueryDeletion);
      return ResultStore.success("Delete query Clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("Delete query not clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> clickDeleteQueryTopOfList()
  {
    try
    {
      TestUtils.sleep(2);
      clickElementDynamicWait(buttonDelete);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal " +
              ".modal-content")));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("btnDialogSubmit")));
      wait.until(ExpectedConditions.elementToBeClickable(buttonConfirmQueryDeletion));
      buttonConfirmQueryDeletion.click();
      wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By
              .cssSelector(".modal .modal-content"))));
      return ResultStore.success("Delete query Clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("Delete query not clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }


  /************************
   /* DB Create config
   /************************/


  public Map<String, String> clickDBconfiguraton()
  {
    try
    {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      UiTestUtils.switchToContentFrame(driver, wait);
      TestUtils.sleepAndLog(2000);
      clickElementDynamicWait(buttonDBSetting);
      waitVisibility(dbUserField);
      return ResultStore.success("New DB settings creation Clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("New DB settings creation not Clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> viewDBSettings()
  {
    try
    {
      wait.until(ExpectedConditions.visibilityOf(toolBarDropDownMenuButton));
      clickElementAndReturnResultDynamicWait(toolBarDropDownMenuButton, "Toolbar clicked",
              "Toolbar not clicked");
      clickElementAndReturnResultDynamicWait(dbconnectionsettingdropdown, "Db connection setting clicked", "DB connection settings not" +
              " clicked");
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("opr-panel.opr-slide-panel-content")));
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2[title='SET UP DB CONNECTION']")));
      return ResultStore.success("Set Up DB Connection Screen displayed");
    } catch (Exception exc)
    {
      return ResultStore.failure("New DB settings creation not Clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }



  public Map<String, String> fillDBSettingsFields(List<String> fields)
  {
    try
    {
      clickDBssl();
      waitClearAndSendKeysToElement(hostNameField, fields.get(0));
      waitClearAndSendKeysToElement(portField, fields.get(1));
      waitClearAndSendKeysToElement(dbNameField, fields.get(2));
      waitClearAndSendKeysToElement(dbUserField, fields.get(3));
      waitClearAndSendKeysToElement(dbPasswordField, fields.get(4));
      waitClearAndSendKeysToElement(dbPasswordConfirmField, fields.get(4));

      return ResultStore.success("New DB fields are filled");
    } catch (Exception exc)
    {
      return ResultStore.failure("New DB fields not filled. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> testDBConnection()
  {
    try
    {
      clickElementAndReturnResultDynamicWait(buttonTestConnection, "Test Connection Clicked",
              "Test Connection not clicked");
      buttonTestConnection.click();
      wait.until(ExpectedConditions.textToBePresentInElement(testConnectionAlertsucess, "Test " +
              "connection succeeded"));
      System.out.println("@@@@@@@@@@@ " + testConnectionAlertsucess.getText());
      if (testConnectionAlertsucess.getText().equals("Test connection succeeded"))
      {
        return ResultStore.success("DB TEST connection PASSED ");
      }
      return ResultStore.failure("DB TEST connection FAILED ");
    } catch (Exception exc)
    {
      return ResultStore.failure("Test DB Connection FAILED. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> saveDBSettings()
  {
    try
    {
      clickElementAndReturnResultDynamicWait(buttonSaveDBSetting, "Save Db settings Clicked",
              "Save Db settings not clicked");
      return ResultStore.success("Save Db settings Clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("Save Db settings FAILED. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }


  public Map<String, String> readDBSettingsFields(List<String> fields)
  {
    try
    {

      JavascriptExecutor js = (JavascriptExecutor) driver;
      String hostname = js.executeScript("return arguments[0].value", hostNameField).toString();
      String port = js.executeScript("return arguments[0].value", portField).toString();
      String dbName = js.executeScript("return arguments[0].value", dbNameField).toString();
      String dbUser = js.executeScript("return arguments[0].value", dbUserField).toString();
      String useTLS = js.executeScript("return arguments[0].checked", sslCheckBox).toString();

      System.out.println(useTLS);
      if (hostname.equals(fields.get(0)) &&
              port.equals(fields.get(1)) &&
              dbName.equals(fields.get(2)) &&
              dbUser.equals(fields.get(3))&&
              useTLS.equals(fields.get(4)))
      {
        return ResultStore.success("New DB fields are filled");
      }
      else
      {
        return ResultStore.failure("New DB fields not filled");
      }
    } catch (Exception exc)
    {
      return ResultStore.failure("New DB fields not filled. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }

  }

  public Map<String, String> enableTLSCheckBox()
  {
    Map<String, String> result;
    try
    {
      if (sslCheckBox.getAttribute("class").contains("ng-empty"))
      {
        TestUtils.sleep(5);
        sslCheckBox.click();
      }

      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert-error")));
      WebElement erroralert=driver.findElement(By.cssSelector("div.alert-error"));
      Assert.assertEquals(erroralert.getText(),"This field is required");
      Assert.assertFalse(buttonTestConnection.isEnabled());
      Assert.assertFalse(buttonSaveDBSetting.isEnabled());
      TestUtils.sleep(2);

      result = ResultStore.success("Successfully set TLS checkbox");
    } catch (Exception exc)

    {
      result = ResultStore.failure("Failed to enable TLS checkbox. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }



  public Map<String, String> clickDBcancel()
  {
    try
    {
      clickElementDynamicWait(buttonCancelDBSetting);
      return ResultStore.success("DB cancel Clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("DB cancel not Clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> clickDBssl()
  {
    try
    {

      clickElementDynamicWait(sslField);
      return ResultStore.success("ssl field Clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("ssl field not Clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> checkSSLselected()
  {
    try
    {
      if (driver.findElement(By.id("ssl-input")).isSelected())
      {
        return ResultStore.success("ssl Checked");
      }
      else
      {
        return ResultStore.failure("ssl not checked");
      }

    } catch (Exception exc)
    {
      return ResultStore.failure("error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }


  /************************
   /* Toolbar buttons functionality
   /************************/

  public Map<String, String> clickDBsettingsToolbar()
  {
    try
    {
      clickElementDynamicWait(buttonDbSettings);
      waitVisibility(dbUserField);
      clickElementDynamicWait(buttonCancelDBSetting);
      return ResultStore.success("DB setting Toolbar Clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("DB setting Toolbar Clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> duplicateQuery()
  {
    try
    {
      clickElementDynamicWait(buttonDuplicate);
      TestUtils.sleep(2);
      clickElementDynamicWait(buttonSaveQuery);
      TestUtils.sleep(2);
      return ResultStore.success("Duplicate Toolbar Clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("Duplicate toolbar not Clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }


  public Map<String, String> clickEditQueryInList()
  {
    try
    {
      clickParentElementDynamicWait(editButtonOnQuery);
      return ResultStore.success("Edit Button Clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("Edit button not Clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

//  public Map<String, String> clickDeactivateTopOfList()
//  {
//    try
//    {
//      clickElementDynamicWait(buttonPause);
//      waitVisibilityLocatedByCss(".opr-toolbar #btn-container-4 button .hpe-icon-play");
//      waitVisibilityLocatedByCss("opr-list-item a.active .list-badges .hpe-icon-pause");
//      WebElement infoPauseIcon = driver.findElement(By.cssSelector(".details-panel " +
//            ".hpe-icon-pause"));
//      WebElement parent = infoPauseIcon.findElement(By.xpath(".."));
//      waitVisibility(infoPauseIcon);
//      if (parent.getText().equals("Inactive"))
//      {
//        return ResultStore.success("Query Deactivated");
//      }
//      else
//      {
//        return ResultStore.failure("Query not Deactivated");
//      }
//    } catch (Exception exc)
//    {
//      return ResultStore.failure("error: " + Misc
//            .getStacktraceAsString
//                  (exc));
//    }
//  }
//
//
//  public Map<String, String> clickActivateTopOfList()
//  {
//    try
//    {
//      clickElementDynamicWait(buttonActivate);
//      waitVisibilityLocatedByCss(".opr-toolbar #btn-container-5 button .hpe-icon-pause");
//      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("opr-list-item " +
//            "a.active .list-badges .hpe-icon-pause")));
//      WebElement infoPauseIcon = driver.findElement(By.cssSelector(".details-panel " +
//            ".hpe-icon-play"));
//      WebElement parent = infoPauseIcon.findElement(By.xpath(".."));
//      waitVisibility(infoPauseIcon);
//      if (parent.getText().equals("Active"))
//      {
//        return ResultStore.success("Query Deactivated");
//      }
//      else
//      {
//        return ResultStore.failure("Query not Deactivated");
//      }
//    } catch (Exception exc)
//    {
//      return ResultStore.failure("error: " + Misc
//            .getStacktraceAsString
//                  (exc));
//    }
//  }


  public Map<String, String> clickDeactivateOnQuery()
  {
    try
    {
      clickParentElementDynamicWait(deactivateButtonOnQuery);
      waitVisibilityLocatedByCss(".opr-toolbar #btn-container-4 button .hpe-icon-play");
      waitVisibilityLocatedByCss("opr-list-item a.active .list-badges .hpe-icon-pause");
      WebElement infoPauseIcon = driver.findElement(By.cssSelector(".details-panel " +
              ".hpe-icon-pause"));
      WebElement parent = infoPauseIcon.findElement(By.xpath(".."));
      waitVisibility(infoPauseIcon);
      if (parent.getText().equals("Inactive"))
      {
        return ResultStore.success("Query Deactivated");
      }
      else
      {
        return ResultStore.failure("Query not Deactivated");
      }
    } catch (Exception exc)
    {
      return ResultStore.failure("error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> checkDeactivated()
  {
    try
    {
      waitVisibilityLocatedByCss(".opr-toolbar #btn-container-4 button .hpe-icon-play");
      waitVisibilityLocatedByCss("opr-list-item a.active .list-badges .hpe-icon-pause");
      WebElement infoPauseIcon = driver.findElement(By.cssSelector(".details-panel " +
              ".hpe-icon-pause"));
      WebElement parent = infoPauseIcon.findElement(By.xpath(".."));
      waitVisibility(infoPauseIcon);
      if (parent.getText().equals("Inactive"))
      {
        return ResultStore.success("Query Deactivated");
      }
      else
      {
        return ResultStore.failure("Query not Deactivated");
      }
    } catch (Exception exc)
    {
      return ResultStore.failure("error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> clickActivateOnQuery()
  {
    try
    {
      clickParentElementDynamicWait(activateButtonOnQuery);
      waitVisibilityLocatedByCss(".opr-toolbar #btn-container-5 button .hpe-icon-pause");
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("opr-list-item " +
              "a.active .list-badges .hpe-icon-pause")));
      WebElement infoPauseIcon = driver.findElement(By.cssSelector(".details-panel " +
              ".hpe-icon-play"));
      WebElement parent = infoPauseIcon.findElement(By.xpath(".."));
      waitVisibility(infoPauseIcon);
      if (parent.getText().equals("Active"))
      {
        return ResultStore.success("Query Deactivated");
      }
      else
      {
        return ResultStore.failure("Query not Deactivated");
      }
    } catch (Exception exc)
    {
      return ResultStore.failure("error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> clickActiveAfterSave()
  {
    try
    {
      clickElementDynamicWait(activateAfterSaveCheckBox);
      return ResultStore.success("activateAfterSaveCheckBox Clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("activateAfterSaveCheckBox not Clicked. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> searchQuery(String text)
  {
    try
    {
      TestUtils.sleepAndLog(2000);
      clickElementDynamicWait(buttonSearch);
      TestUtils.sleepAndLog(1000);
      new Actions(driver).sendKeys(text).build().perform();
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("" +
              ".cg-busy.cg-busy-animation:not(.ng-hide)")));
      List<WebElement> queries = driver.findElements(By.cssSelector(".list-body .list-title"));

      if (queries.size() == 1 && queries.get(0).getAttribute("text").equals(text))
      {
        return ResultStore.success("Search success, Query found");
      }
      return ResultStore.failure("Search failed, Query NOT found or found more than ONE query");
    } catch (Exception exc)
    {
      return ResultStore.failure("error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> clearSearchField()
  {
    try
    {
      TestUtils.sleepAndLog(2000);
      //clickElementDynamicWait(querySearchCleanButton);
      clickElementDynamicWait(querySearchClearButton);

      return ResultStore.success("Search clean success");
    } catch (Exception exc)
    {
      return ResultStore.failure("error: " + Misc.getStacktraceAsString(exc));
    }
  }


  public Map<String, String> readQueryInfo(List<String> tagsExpected, List<String>
          channelsExpected, String
                                                   formExpected)
  {
    try
    {
      List<WebElement> rowDetails = driver.findElements(By.cssSelector(".form-group .rowDetail " +
              "span"));
      String formActual = driver.findElement(By.cssSelector(".form-group .rowDetail .col-sm-8 " +
              "label")).getText();
      List<String> actualTagsChannels = new ArrayList<>();

      for (WebElement row : rowDetails)
      {

        actualTagsChannels.add(row.getText());

      }
      if (actualTagsChannels.containsAll(tagsExpected) && actualTagsChannels.containsAll
              (channelsExpected) && formActual.equals(formExpected))
      {
        return ResultStore.success("Tags, Channels and format exist in info query panel");
      }


      return ResultStore.failure("Search failed, Query NOT found");
    } catch (Exception exc)
    {
      return ResultStore.failure("error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }


  public boolean isDBSet() {

    UiTestUtils.waitUntilPageIsLoaded(driver, wait);
    UiTestUtils.switchToContentFrame(driver, wait);
    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
            ("opr-app-title[text='Predefined Queries']")));
    TestUtils.sleepAndLog(5000);
    try {

      TestUtils.sleepAndLog(5000);
      System.out.println("Waiting for New Query button appearance");
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.btn-primary[title='New']")));
      return true;
    } catch (Exception exc) {
      System.out.println("No New Query button");
      return false;

    }

  }

  public Map<String, String> exportSelectedQuery()
  {
    try
    {
      String activeQueryName = driver.findElement(By.cssSelector("opr-list-item .active " +
              ".list-title")).getText();
      TestUtils.sleep(1);
      System.out.println("Selected for export : " + activeQueryName);
      TestUtils.sleep(1);
      clickElementAndReturnResultDynamicWait(toolBarDropDownMenuButton, "Toolbar clicked",
              "Toolbar not clicked");
      clickElementAndReturnResultDynamicWait(exportSelectedQuery, "Export clicked", "Export not" +
              " clicked");

      return ResultStore.success("Export clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("Export failed. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> exportAllQueries()
  {
    try
    {
      clickElementAndReturnResultDynamicWait(toolBarDropDownMenuButton, "Toolbar clicked",
              "Toolbar not clicked");
      clickElementAndReturnResultDynamicWait(exportNotSelectedQuery, "Export clicked", "Export not" +
              " clicked");
      return ResultStore.success("Export clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("Export failed. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> importQuery(String fileName)
  {
    try
    {
      TestUtils.sleepAndLog(2000);
      clickElementAndReturnResultDynamicWait(toolBarDropDownMenuButton, "Toolbar clicked",
              "Toolbar not clicked");
      clickElementAndReturnResultDynamicWait(importQuery, "Import clicked", "Import not" +
              " clicked");

      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".modal-header h3")));
      WebElement element = driver.findElement(By.id("importQueriesFileInput"));
      element.sendKeys(fileName);
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".modal-footer button" +
              ".btn-primary "))).click();

      wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".modal-header h3"), "IMPORT SUCCESS"));
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".modal-footer button" +
              ".btn-primary "))).click();
      return ResultStore.success("Import clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("Import failed. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> importQuerySkip(String fileName)
  {
    try
    {
      TestUtils.sleepAndLog(2000);
      clickElementAndReturnResultDynamicWait(toolBarDropDownMenuButton, "Toolbar clicked",
              "Toolbar not clicked");
      clickElementAndReturnResultDynamicWait(importQuery, "Import clicked", "Import not" +
              " clicked");

      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".modal-header h3")));
      WebElement element = driver.findElement(By.id("importQueriesFileInput"));
      element.sendKeys(fileName);
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".modal-footer button" +
              ".btn-primary "))).click();
      TestUtils.sleepAndLog(2000);
      WebElement header = driver.findElement(By.cssSelector(".modal-header h3"));
      header.getText().equals("Stored Query Import Conflicts");
      TestUtils.sleep(1);
      WebElement skipEle = driver.findElement(By.cssSelector("[ng-click*='skip']"));
      wait.until(ExpectedConditions.elementToBeClickable(skipEle)).click();
      TestUtils.sleep(1);
      WebElement headernew = driver.findElement(By.cssSelector(".modal-header h3"));
      headernew.getText().equals("Import Success");
      TestUtils.sleep(1);
      WebElement okButton = driver.findElement(By.cssSelector(".modal-footer button" +
              ".btn-primary"));
      wait.until(ExpectedConditions.elementToBeClickable(okButton)).click();

      return ResultStore.success("Skip clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("Import failed. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }


  public Map<String, String> importQueryOverwrite(String fileName)
  {
    try
    {
      TestUtils.sleepAndLog(2000);
      clickElementAndReturnResultDynamicWait(toolBarDropDownMenuButton, "Toolbar clicked",
              "Toolbar not clicked");
      clickElementAndReturnResultDynamicWait(importQuery, "Import clicked", "Import not" +
              " clicked");

      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".modal-header h3")));
      WebElement element = driver.findElement(By.id("importQueriesFileInput"));
      element.sendKeys(fileName);
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".modal-footer button" +
              ".btn-primary "))).click();
      TestUtils.sleepAndLog(2000);
      driver.findElement(By.cssSelector(".modal-header h3")).getText().equals("Stored Query Import Conflicts");
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[ng-click*='overwrite']"))).click();
      driver.findElement(By.cssSelector(".modal-header h3")).getText().equals("Import Success");
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".modal-footer button" +
              ".btn-primary "))).click();
      return ResultStore.success("Overwrite clicked");
    } catch (Exception exc)
    {
      return ResultStore.failure("Import failed. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> importWrongData(String fileName)
  {
    try
    {
      TestUtils.sleepAndLog(2000);
      clickElementAndReturnResultDynamicWait(toolBarDropDownMenuButton, "Toolbar clicked",
              "Toolbar not clicked");
      clickElementAndReturnResultDynamicWait(importQuery, "Import clicked", "Import not" +
              " clicked");

      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".modal-header h3")));
      WebElement element = driver.findElement(By.id("importQueriesFileInput"));
      element.sendKeys(fileName);
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".modal-footer button" +
              ".btn-primary "))).click();

      /*wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".modal-header" +
              " h3"), "Import failed"));*/
      wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".modal-header" +
              " h3"), "IMPORT FAILED"));
      wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".errorInfo p"), "The selected file does not contain predefined queries exported from BVD!"));
      TestUtils.sleep(2);
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".modal-footer button"))).click();

      return ResultStore.success("Import successfully canceled");
    } catch (Exception exc)
    {
      return ResultStore.failure("Import cancel  failed. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }
  public Map<String, String> checkDataCollectorInFrame(){
    try {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      TestUtils.sleep(10);
      driver.switchTo().defaultContent();
      wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("bvd"));
      wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentFrame"));
      TestUtils.sleepAndLog(2000);

       /*wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(
          "//h2"), "Data Collectors"));*/
      clickElementDynamicWait(buttonNew);
      wait.until(ExpectedConditions.elementToBeClickable(clickDataQuery));
      clickDataQuery.click();
      driver.switchTo().defaultContent();
      wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("bvd"));
      wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentFrame"));
      wait.until(ExpectedConditions.visibilityOf(queryNameField));
      return ResultStore.success("Datacollectors in Frame");
    }catch (Exception exc)
    {
      return ResultStore.failure("Datacollectors not in frame" + Misc
              .getStacktraceAsString
                      (exc));
    }

  }

  public Map<String, String> selectValueColumn(String text) {
    try {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      //UiTestUtils.switchToContentFrame(driver, wait);
      TestUtils.sleepAndLog(2000);

      WebElement element = driver.findElement(By.cssSelector("div opr-dropdown[placeholder='Value column'] div button.opr-dropdown-selection-button>div"));
      JavascriptExecutor scroll = (JavascriptExecutor)driver;
      scroll.executeScript("arguments[0].scrollIntoView();", element);
      wait.until(ExpectedConditions.elementToBeClickable(queryColumnValue));
      queryColumnValue.click();

      TestUtils.sleepAndLog(2000);
      WebElement columnselect=driver.findElement(By.cssSelector("opr-grouped-list-item[id$='"+
              text+"'] div.list-title"));
      wait.until(ExpectedConditions.elementToBeClickable(columnselect));
      columnselect.click();

      return ResultStore.success("Param Value Column selected");
    }
    catch (Exception exc) {

      return ResultStore.failure("Param Value Column not selected. error: " + Misc.getStacktraceAsString(exc));
    }
  }

  public Map<String, String> selectLabelColumn(String text) {
    try {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      UiTestUtils.switchToContentFrame(driver, wait);
      TestUtils.sleepAndLog(2000);

      WebElement element = driver.findElement(By.cssSelector("div opr-dropdown[placeholder='Label column'] div button.opr-dropdown-selection-button>div"));
      JavascriptExecutor scroll = (JavascriptExecutor)driver;
      scroll.executeScript("arguments[0].scrollIntoView();", element);
      wait.until(ExpectedConditions.elementToBeClickable(queryLabelValue));
      queryLabelValue.click();

      TestUtils.sleepAndLog(2000);
      WebElement columnlabelselect=driver.findElement(By.cssSelector("opr-grouped-list-item[id$='"+
              text+"'] div.list-title"));
      wait.until(ExpectedConditions.elementToBeClickable(columnlabelselect));
      columnlabelselect.click();
      //return ResultStore.success("Param Value Column selected");

      return ResultStore.success("Param Label Column selected");
    } catch (Exception exc) {

      return ResultStore.failure("Param Label Column not selected. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> provideDefaultValueForDate(List<String> fromdatelist,List<String> todatelist) {
    try {
        defaultValueRadio.click();
        TestUtils.sleep(3);
        Assert.assertTrue(driver.findElement(By.cssSelector("div.alert p")).getText().equalsIgnoreCase("Default value field cannot be empty"));

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

      return ResultStore.success("Default selected Successfully");
    } catch (Exception exc) {
      return ResultStore.failure("Default not selected. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }


  public Map<String, String> switchDefault(String text,String dataValue) {
    try {
      if (text.equals("None")) {
        JavascriptExecutor scroll = (JavascriptExecutor)driver;
        scroll.executeScript("arguments[0].scrollIntoView();", defaultNoneRadio);
        defaultNoneRadio.click();
      } else if (text.equals("Value")) {
        JavascriptExecutor scroll = (JavascriptExecutor)driver;
        scroll.executeScript("arguments[0].scrollIntoView();", defaultValueRadio);
        defaultValueRadio.click();
        defaultValueText.sendKeys(dataValue);
      }

      return ResultStore.success("Default selected Successfully");
    } catch (Exception exc) {
      return ResultStore.failure("Default not selected. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> readDefault(String text) {
    try {
      TestUtils.sleepAndLog(1000);
      WebElement element= driver.findElement(By.cssSelector("input[name='optionsRadios']:checked+span"));
      JavascriptExecutor scroll = (JavascriptExecutor)driver;
      scroll.executeScript("arguments[0].scrollIntoView();", element);
      String format = driver.findElement(By.cssSelector("input[name='optionsRadios']:checked+span")).getText();
      if (format.contains(text)) {
        return ResultStore.success("Radio set to " + text);
      } else {
        return ResultStore.failure("Wrong radio button set. Got:" + format);
      }
    } catch (Exception exc) {
      return ResultStore.failure("error: " + Misc.getStacktraceAsString(exc));
    }

  }

  public Map<String, String> readValues(String text) {
    try {
      TestUtils.sleep(2);
      for (WebElement value : values) {
        if (value.getText().contains(text)){
          return ResultStore.success("Value Column data equals to " + text);
        } else {
          return ResultStore.failure("Value Column data Name not equals. Got:" + value.getText());
        }

      }
      return  ResultStore.success("Value read successfully");
    } catch (Exception exc) {
      return ResultStore.failure(" error: " + Misc.getStacktraceAsString(exc));
    }
  }

  public Map<String, String> switchData(String text)
  {
    try
    {
      if (text.equals("Database query"))
      {
        JavascriptExecutor scroll = (JavascriptExecutor)driver;
        scroll.executeScript("arguments[0].scrollIntoView();", dataDatabaseQuery);
        dataDatabaseQuery.click();
      }
      else if (text.equals("Value list"))
      {
        dataValuelist.click();
      }else if(text.equals("Date")){
        dateValuelist.click();
      }

      return ResultStore.success("Data selected Successfully");
    } catch (Exception exc)
    {
      return ResultStore.failure("Data not selected. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String,String> createNonqueryValueList(Map<String,String> map1){

    try{

      for (Map.Entry<String,String> entry: map1.entrySet()){
        if (count<1) {
          addValuelistbutton.click();
          wait.until(ExpectedConditions.visibilityOf(valuelistlabelfield));
          valuelistlabelfield.sendKeys(entry.getKey());
          valuelistlabelfield.sendKeys(Keys.TAB);
          valuelistvaluefield.sendKeys(entry.getValue());
          TestUtils.sleep(1);
          wait.until(ExpectedConditions.visibilityOf(valuelistApplyfield));
          valuelistApplyfield.click();
        }else{
          addValuelistbuttonafteraddfirst.click();
          wait.until(ExpectedConditions.visibilityOf(valuelistlabelfield));
          valuelistlabelfield.sendKeys(entry.getKey());
          valuelistlabelfield.sendKeys(Keys.TAB);
          valuelistvaluefield.sendKeys(entry.getValue());
          TestUtils.sleep(1);
          wait.until(ExpectedConditions.visibilityOf(valuelistApplyfieldafterfirstapply));
          valuelistApplyfieldafterfirstapply.click();
        }
        count=count+1;
      }

      return ResultStore.success("No query Value is created successfully");
    } catch (Exception exc)
    {
      return ResultStore.failure("Tab not switched. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }

  public Map<String, String> switchTab()
  {
    try
    {
      Actions actions = new Actions(driver);
      actions.sendKeys(Keys.TAB).build().perform();

      return ResultStore.success("Tab switched Successfully");
    } catch (Exception exc)
    {
      return ResultStore.failure("Tab not switched. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }
  }


  public Map<String, String> readerror(String text) {
    try {
      //Thread.sleep(2000);
      TestUtils.sleep(2);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.alert-inverse")));
      String errortext=rbacdataquererror.getText();
      System.out.println(errortext);
      Assert.assertEquals(errortext,text);
      //wait.until(ExpectedConditions.textToBePresentInElement(rbacdataquererror,text));
      return ResultStore.success("Expected Error is displayed :" + text);

    } catch (Exception exc) {
      return ResultStore.failure(" error: " + Misc.getStacktraceAsString(exc));
    }
  }

}
  
  
  
  

