package com.hp.opr.qa.framework.pageobjects.bvd;


import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.hp.opr.qa.framework.utils.UiTestUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.*;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class EditDashboardPage
{

  final WebDriver driver;
  final WebDriverWait wait;

  // BVD APPLY
  @FindBy(how = How.ID, using = "config_apply")
  static WebElement button_Apply;

  @FindBy(how = How.ID, using = "ol li.highlighted span.ux-filter-match")
  static WebElement datafieldhiglight;

  @FindBy(how = How.CSS, using = "input#hyperlink-dashboard[type='radio']")
  static WebElement hyperlinktoanotherdashboradradiobutton;

  @FindBy(how = How.CSS, using = "input#hyperlink-none[type='radio']")
  static WebElement hyperlinkdefaultradiobutton;

  @FindBy(how = How.CSS, using = "opr-dropdown#hyperlink_select input")
  static WebElement hyperlinkinputtexttotype;

  @FindBy(how = How.CSS, using = "#opr_field +multiple-select select")
  static WebElement selectdatafields;

  @FindBy(how = How.CSS, using ="ul.select2-choices li.select2-search-choice div")
  static WebElement categoryassignedDashboard;

  @FindBy(how = How.CSS, using ="#displayName-input")
  static WebElement scalingOption;

  @FindBy(how = How.CSS, using ="div.checkbox label")
  static WebElement passparamtodashboardCheckbox;

  // BVD SAVE
  @FindBy(how = How.ID, using = "config_save")
  static WebElement button_Save;


  @FindBy(how = How.CSS, using = "button.channelResultListElement-clear")
  private WebElement clearDataChannel;

  @FindBy(how = How.CSS, using = "div li.select2-search-choice a")
  private WebElement selectdatafield;
  // BVD CANCEL
  @FindBy(how = How.ID, using = "config_cancel")
  static WebElement button_Cancel;

  // BVD DATA CHANNEL
  @FindBy(how = How.CSS, using = ".searchCriteria")
  static WebElement input_DataChannel;

  @FindBy(how = How.CSS, using = ".searchCriteria")
  static WebElement input_Dashboard;

  @FindBy(how = How.CSS, using = "#s2id_autogen9")
  static WebElement input_searchField;

  @FindBy(how = How.ID, using = "bvd_time")
  static WebElement input_timespan;

  //BVD SELECTED DATA CHANNEL WRAPPER
  @FindBy(how = How.CSS, using = ".searchChannels.channelInputWrapper")
  static WebElement wrapper_DataChannel;

  @FindBy(how = How.CSS, using = "div#select2-drop ul li.select2-no-results")
  static WebElement addnewcategory_textbar;

  // BVD STATUS FIELD
  @FindBy(how = How.ID, using = "opr_status")
  static WebElement select_StatusField;

  // BVD DATA FIELD
  @FindBy(how = How.ID, using = "opr_field")
  static WebElement select_DataField;

  // BVD DATA FIELD MULTI SELECT
  @FindBy(how = How.CSS, using = "multiple-select#dataFields select")
  static WebElement select_DataFieldMultiSelect;



  @FindBy(how = How.CSS, using = "multiple-select#searchFields select")
  static WebElement select_searchFieldMultiSelect;

  // BVD DATA DEFAULT VALUE
  @FindBy(how = How.CSS, using = "select#opr_switch_default")
  static WebElement select_DefaultValue;

  // BVD MAX VALUE
  @FindBy(how = How.ID, using = "maxValue")
  static WebElement input_MaxValue;

  // BVD MIN VALUE
  @FindBy(how = How.ID, using = "minValue")
  static WebElement input_MinValue;

  // BVD UPDATE TEXT WITH VALUE
  @FindBy(how = How.ID, using = "opr_text_value")
  static WebElement input_UpdateTextWithValue;

  // BVD UPDATE COLOR OF TEXT
  @FindBy(how = How.ID, using = "opr_text_color")
  static WebElement input_UpdateColorOfText;

  // BVD CHART AUTO SCALE
  @FindBy(how = How.ID, using = "opr_chart_auto_scale")
  static WebElement input_ChartAutoScale;

  // BVD REVERSE DISPLAY ORDER
  @FindBy(how = How.ID, using = "opr_chart_reverse")
  static WebElement input_ReverseDisplayOrder;

  // BVD MOUSE OVER
  @FindBy(how = How.ID, using = "opr_mouse_over")
  static WebElement input_MouseOver;

  // BVD CHART COLORS
  @FindBy(how = How.ID, using = "opr_chart_colors")
  static WebElement input_ChartColors;

  // BVD CHART PERIOD IN MINUTES
  @FindBy(how = How.ID, using = "opr_chart_period")
  static WebElement input_ChartPeriodInMinutes;

  // BVD COLORING RULE
  @FindBy(how = How.ID, using = "opr_coloring_rule")
  static WebElement input_ColoringRule;

  // BVD TEXT FORMAT
  @FindBy(how = How.ID, using = "opr_text_format")
  static WebElement input_TextFormat;

  // BVD VISIBILITY RULE
  @FindBy(how = How.ID, using = "opr_visibility_rule")
  static WebElement input_VisibilityRule;

  // BVD HYPERLINK TO ANOTHER DASHBOARD
  @FindBy(how = How.ID, using = "hyperlink-dashboard")
  static WebElement input_HyperlinkDashboard;

  // BVD HYPERLINK SELECT DASHBOARD
  @FindBy(how = How.ID, using = "hyperlink_select")
  static WebElement select_HyperlinkDashboard;

  // BVD HYPERLINK TO URL
  @FindBy(how = How.ID, using = "hyperlink-url")
  static WebElement input_HyperlinkUrl;

  // BVD HYPERLINK
  @FindBy(how = How.ID, using = "hyperlink")
  static WebElement input_Hyperlink;

  // BVD HYPERLINK
  @FindBy(how = How.CSS, using = "input#hyperlink")
  static WebElement input_Hyperlink_text;


  // BVD MAX FEED ITEM
  @FindBy(how = How.ID, using = "opr_feed_max_items")
  static WebElement input_MaxFeedItem;

  @FindBy(how = How.ID, using = "opr_transparent_background")
  static WebElement input_TransparentBackground;

  @FindBy(how = How.ID, using = "opr_chart_numbers")
  static WebElement input_ShowChartNumbers;

  @FindBy(how = How.ID, using = "bvd_legend")
  static WebElement input_ShowLegend;


  @FindBy(how = How.ID, using = "bvd_stack")
  static WebElement input_StackValues;

  // BVD MAX FEED ITEM
  @FindBy(how = How.ID, using = "opr_channel")
  static WebElement input_WebPage;

  // BVD MAX FEED ITEM
  @FindBy(how = How.ID, using = "opr_coloring_rule")
  static WebElement input_ImageSelectionRule;

  // BVD DISCARD CHANGES
  @FindBy(how = How.ID, using = "ok")
  static WebElement button_DiscardChanges;

  // BVD TITLE
  @FindBy(how = How.ID, using = "title")
  static WebElement input_Title;

  // BVD CATEGORY
  @FindBy(how = How.CSS, using = "input[placeholder='Type here to create a permission category']")
  static WebElement input_Category;

  @FindBy(how = How.CSS, using = "input[placeholder='Type here to create a menu category']")
  static WebElement input_MenuCategory;

  // BVD ADD CATEGORY
  @FindBy(how = How.CSS, using = "div#select2-drop button")
  static WebElement button_AddCategory;

  // BVD CURRENT WIDGET ID
  @FindBy(how = How.ID, using = "current_widget_id")
  static WebElement label_widgetID;

  // BVD REPLACE DASHBOARD
  @FindBy(how = How.ID, using = "dashboard-upload-button")
  static WebElement span_replaceDashboard;

  // BVD DELETE CHANNEL
  @FindBy(how = How.CLASS_NAME, using = "channelResultListElement-clear")
  static WebElement icon_deleteChannel;

  // BVD BROWSE
  @FindBy(how = How.ID, using = "dashboardFile")
  //@CacheLookup
  static WebElement input_BrowseDashboardFile;

  // BVD UPLOAD DASHBOARD
  @FindBy(how = How.ID, using = "ok")
  static WebElement button_UploadDashboard;

  // BVD BACKGROUND COLOR
  @FindBy(how = How.ID, using = "bgColor")
  static WebElement input_BackgroundColor;

  @FindBy(how = How.ID, using = "startAngle")
  static WebElement input_StartAngle;

  @FindBy(how = How.ID, using = "donutSize")
  static WebElement input_DonutSize;

  @FindBy(how = How.ID, using = "percentage")
  static WebElement input_DonutHoleSize;

  @FindBy(how = How.ID, using = "channelElement1")
  static WebElement channel_dim;

  @FindBy(how = How.ID, using = "ok")
  static WebElement convert_Template_OK;

  @FindBy(how = How.ID, using = "saveTemplate")
  static WebElement save_Template_Yes;

  @FindBy(how = How.ID, using = "saveTemplateNoInstances")
  static WebElement button_replace_cvg;

  @FindBy(how = How.ID, using = "saveSVGTemplateWithInstances")
  static WebElement button_replace_template;

  @FindBy(how = How.CSS, using = ".propertiesOverflowFix")
  static WebElement dashboardPropertiesContainer;

  @FindBy(how = How.ID, using = "opr_calculation_rule")
  static WebElement calculationRuleField;

  @FindBy(how = How.XPATH, using = "//input[@name='opr_text_overflow']")
  static WebElement textOverFlowRadio;

  @FindBy(how = How.ID, using = "bvd_range")
  static WebElement rangeField;

  @FindBy(how = How.XPATH, using = "//button[@id='bvd_category-input']")
  static WebElement categoryButton;

  @FindBy(how=How.XPATH,using="//a[contains(@title,'Last value')]")
  static List<WebElement> categoryValue;

  @FindBy(how = How.CSS, using = "#displayName-input")
  static WebElement input_Scaling;

  @FindBy(how = How.XPATH, using = "//input[@name='opr_text_overflow']")
  static WebElement input_textoverflowOption;

  /*@FindBy(how = How.CSS, using = "multiple-select#dataFields")
  static WebElement select_DataField_search;*/

  @FindBy(how = How.CSS, using = "input[placeholder='Select a data field']")
  static WebElement select_DataField_search;

  @FindBy(how = How.CSS, using = "input[placeholder='Select a data field']")
  static WebElement select_datafield_input;

  public EditDashboardPage(WebDriver driver)
  {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, 15);
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

  private Map<String, String> waitingModaldisappearance()
  {
    try
    {
      wait.until(ExpectedConditions.not(presenceOfAllElementsLocatedBy(By.className("" +
              "modal-backdrop"))));
      wait.until(ExpectedConditions.not(presenceOfAllElementsLocatedBy(By
              .cssSelector("[modal-window='modal-window']"))));
      return ResultStore.success("No Modal Window");
    } catch (Exception e)
    {
      return ResultStore.failure("Modal window exists");
    }

  }

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

  // APPLY
  public Map<String, String> apply()
  {
    Map<String, String> result;

    WebDriverWait localWait = new WebDriverWait(driver, 15);

    try
    {
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));

      waitingModaldisappearance();

      WebElement applyButton = driver.findElement(By.id("config_apply"));
      localWait.until(ExpectedConditions.elementToBeClickable(applyButton));
      applyButton.click();

      //synchronize
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
//
//            //To investigate behavior of Apply button:
////            localWait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(By
//// .id("config_apply"))));
//            //TODO: remove after investigation
//            String disabled;
//            int count = 10;
//            do {
//                WebElement applyButton = driver.findElement(By.id("config_apply"));
//                disabled = applyButton.getAttribute("disabled");
//                System.out.println("disabled= " + disabled);
//                count--;
//                TestUtils.sleepAndLog(100);
//            } while (count > 0 && disabled != null && disabled.isEmpty());
//
////            String css = "button[id='config_apply'][disabled='disabled']";
////            localWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));
      TestUtils.sleepAndLog(500);
      result = ResultStore.success("Successfully applied Changes");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to apply Changes. error: " + Misc.getExceptionInfo(exc));
    }

    return result;
  }

  public Map<String, String> navigateToDashboardProperties()
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));

      waitingModaldisappearance();

      WebElement backtodashboardProplink = driver.findElement(By.cssSelector("a#propertiesBackButton"));
      wait.until(ExpectedConditions.elementToBeClickable(backtodashboardProplink));
      backtodashboardProplink.click();

      result = ResultStore.success("Clicked on Back to Dashboard Properties Link");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to click on Dashboard Properties. error: " + Misc.getExceptionInfo(exc));
    }

    return result;
  }


  public Map<String, String> applyClick()
  {
    try
    {

      waitingModaldisappearance();

      clickElementAndReturnResultDynamicWait(button_Apply, "Apply Clicked", "Apply not Clicked");
      return ResultStore.success("Saved");
    } catch (Exception exc)
    {

      return ResultStore.failure("Failed to save Changes. error: " + Misc.getStacktraceAsString
              (exc));

    }
  }


  public Map<String, String> savewithExtraTime()
  {
    Map<String, String> result;

    try
    {
      String xpath = ".//*[@id='propertiesBackButton']/span";
      if (driver.findElement(By.xpath(xpath)).isDisplayed())
      {
        System.out.println("Workaround step");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
      }

      waitingModaldisappearance();


      wait.until(ExpectedConditions.elementToBeClickable(button_Save));
      button_Save.click();
      TestUtils.sleep(20);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dashboardUploadButton")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      //wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
      // ("div[data-progress-text='100%']")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));

      result = ResultStore.success("Successfully saved Changes");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to save Changes. error: " + Misc.getStacktraceAsString
              (exc));

    }

    return result;
  }


  // SAVE
  public Map<String, String> save()
  {
    Map<String, String> result;

    try
    {
      String xpath = ".//*[@id='propertiesBackButton']/span";
      if (driver.findElement(By.xpath(xpath)).isDisplayed())
      {
        System.out.println("Workaround step");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
      }

      waitingModaldisappearance();


      wait.until(ExpectedConditions.elementToBeClickable(button_Save));
      button_Save.click();

      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dashboardUploadButton")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      //wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
      // ("div[data-progress-text='100%']")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));

      result = ResultStore.success("Successfully saved Changes");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to save Changes. error: " + Misc.getStacktraceAsString
              (exc));

    }

    return result;
  }

  public Map<String, String> saveClick()
  {
    try
    {
      waitingModaldisappearance();
      clickElementAndReturnResultDynamicWait(button_Save, "Save Clicked", "Save not Clicked");
      return ResultStore.success("Saved");
    } catch (Exception exc)
    {

      return ResultStore.failure("Failed to save Changes. error: " + Misc.getStacktraceAsString
              (exc));

    }
  }

  public Map<String, String> editDashboardTemplateManager(String dashboardName)
  {
    try
    {
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      WebElement editButton = driver.findElement(By.cssSelector("#edit-" + dashboardName));
      waitingModaldisappearance();
      clickElementAndReturnResultDynamicWait(editButton, "", "Edit button not clicked");
      return ResultStore.success("Edit button clicked");
    } catch (Exception e)
    {
      return ResultStore.failure("Edit button not clicked " + e.getMessage());
    }
  }

  public Map<String, String> cancelAfterQuickEdit(String sDashboardName)
  {
    Map<String, String> result;

    try
    {
      WebDriverWait localWait = new WebDriverWait(driver, 30);

      waitingModaldisappearance();

      localWait.until(ExpectedConditions.elementToBeClickable(By.id("config_cancel")));
      button_Cancel.click();
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+sDashboardName+"')]")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button#nav_default_dashboard")));

      result = ResultStore.success("Successfully canceled editing");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to cancel editing. error: " + Misc
              .getStacktraceAsString(exc));

    }

    return result;
  }

  // CANCEL
  public Map<String, String> cancel()
  {
    Map<String, String> result;

    try
    {
      WebDriverWait localWait = new WebDriverWait(driver, 30);

      waitingModaldisappearance();

      localWait.until(ExpectedConditions.elementToBeClickable(By.id("config_cancel")));
      button_Cancel.click();
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dashboardUploadButton")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));

      result = ResultStore.success("Successfully canceled editing");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to cancel editing. error: " + Misc
              .getStacktraceAsString(exc));

    }

    return result;
  }


  // OK
  public Map<String, String> okTemplate()
  {
    Map<String, String> result;

    try
    {
      waitingModaldisappearance();
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
              ("#config_apply[disabled='disabled']")));
      UiTestUtils.tryToClick(driver, By.id("config_ok"), 3000, 5);
//            wait.until(ExpectedConditions.elementToBeClickable(button_Cancel)).click();

      // synchronize
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));

      result = ResultStore.success("Successfully canceled editing");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to cancel editing. error: " + Misc
              .getStacktraceAsString(exc));

    }

    return result;
  }

  // CANCEL
  public Map<String, String> cancelTemplate()
  {
    Map<String, String> result;

    try
    {
      waitingModaldisappearance();
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
              ("#config_apply[disabled='disabled']")));
      UiTestUtils.tryToClick(driver, By.id("config_cancel"), 3000, 5);
//            wait.until(ExpectedConditions.elementToBeClickable(button_Cancel)).click();

      // synchronize
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));

      result = ResultStore.success("Successfully canceled editing");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to cancel editing. error: " + Misc
              .getStacktraceAsString(exc));

    }

    return result;
  }

  // CANCEL and discard changes
  public Map<String, String> cancelAndDiscardChanges()
  {
    Map<String, String> result;

    try
    {
      waitingModaldisappearance();
      wait.until(ExpectedConditions.elementToBeClickable(button_Cancel));
      button_Cancel.click();
      wait.until(ExpectedConditions.elementToBeClickable(button_DiscardChanges));
      button_DiscardChanges.click();
      // synchronize
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dashboardUploadButton")));

      result = ResultStore.success("Successfully canceled editing");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to cancel editing. error: " + Misc
              .getStacktraceAsString(exc));

    }

    return result;
  }

  // SELECT WIDGET
  public Map<String, String> selectWidget(String sId)
  {
    Map<String, String> result;
    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("g " +
              "[highlight_widget_id='" + sId + "']")));
      WebElement widget = driver.findElement(By.cssSelector("g [highlight_widget_id='" + sId +
              "'] rect"));
      wait.until(ExpectedConditions.elementToBeClickable(widget));
      widget.click();

      // synchronize
      TestUtils.sleepAndLog(1000);
      ExpectedConditions.elementToBeSelected(driver.findElement(By.cssSelector("div.opr-dropdown-selection[title^='" + sId + "']")));
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("propertiesBackButton")));

      result = ResultStore.success("Successfully selected Widget");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to select Widget. error: " + Misc
              .getStacktraceAsString(exc));

    }

    return result;
  }


  public Map<String, String> modifyDataChannelSetNewChannel(String sDataChannel)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(clearDataChannel));
      Actions actclear=new Actions(driver);
      actclear.moveToElement(clearDataChannel).click(clearDataChannel).build().perform();
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.searchChannels input[type='text']")));
      wait.until(ExpectedConditions.elementToBeClickable(input_DataChannel));
      input_DataChannel.clear();
      input_DataChannel.sendKeys(sDataChannel + " " + Keys.ENTER);
      TestUtils.sleepAndLog(3000);

      if(driver.findElements(By.cssSelector(".channelInputResults  ul li")).size() != 0){
        try {
          WebElement searchHighlight_DataChannel = driver.findElement(By.cssSelector
                  ("li [filter-text='" + sDataChannel + "']"));
          Actions act1=new Actions(driver);
          act1.moveToElement(searchHighlight_DataChannel).click(searchHighlight_DataChannel).build().perform();

        } catch (org.openqa.selenium.StaleElementReferenceException ex) {
          WebElement searchHighlight_DataChannel = driver.findElement(By.cssSelector
                  ("li [filter-text='" + sDataChannel + "']"));
          Actions act1=new Actions(driver);
          act1.moveToElement(searchHighlight_DataChannel).click(searchHighlight_DataChannel).build().perform();
        }
      } else {
        input_DataChannel.sendKeys(sDataChannel + " " + Keys.TAB);
        wait.until(ExpectedConditions.elementToBeClickable(button_Save));
        button_Save.click();
        throw new Exception("no data channel");
      }

      // synchronize
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector
              (".channelInputResults")));
      wait.until(ExpectedConditions.visibilityOf(button_Save));
//            wait.until(ExpectedConditions.elementToBeClickable(button_Save));

      result = ResultStore.success("Successfully set Data Channel to " + sDataChannel);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on Quick button. error: " + Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  // SELECT WIDGET FROM DROP-DOWN LIST
  public Map<String, String> selectWidgetFromList(String widgetId)
  {
    Map<String, String> result;
    try
    {
      String id = "current_selected_widget_id";
      WebElement list = driver.findElement(By.id(id));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
      if (!list.isDisplayed())
      {
        id = "current_selected_widget_id_top";
        list = driver.findElement(By.id(id));
      }
      list.click();
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("opr-dropdown-filter-input")))
              .sendKeys(widgetId + " ");
      WebElement wID = driver.findElement(By.cssSelector("div[search-highlight][text^='" + widgetId + " ']"));
      wait.until(ExpectedConditions.elementToBeClickable(wID));
      ((JavascriptExecutor)driver).executeScript("arguments[0].click();", wID);
      result = ResultStore.success("Successfully selected Widget");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to select Widget. error: " + Misc.getStacktraceAsString(exc));
    }

    return result;
  }

  // SET DATA CHANNEL
  public Map<String, String> setDataChannel(String sDataChannel)
  {
    Map<String, String> result;

    try {
      wait.until(ExpectedConditions.elementToBeClickable(input_DataChannel));
      ((JavascriptExecutor)driver).executeScript("arguments[0].click();", input_DataChannel);
      input_DataChannel.clear();
      input_DataChannel.sendKeys(sDataChannel + " " + Keys.ENTER);
      TestUtils.sleepAndLog(3000);

      //Workaround for JS :element is not attached to the page document
      if(driver.findElements(By.cssSelector(".channelInputResults  ul li")).size() != 0){
        try {
          WebElement searchHighlight_DataChannel = driver.findElement(By.cssSelector
                  ("li [filter-text='" + sDataChannel + "']"));
          JavascriptExecutor executor = (JavascriptExecutor)driver;
          executor.executeScript("arguments[0].click();", searchHighlight_DataChannel);

        }
        catch (org.openqa.selenium.StaleElementReferenceException ex) {
          WebElement searchHighlight_DataChannel = driver.findElement(By.cssSelector
                  ("li [filter-text='" + sDataChannel + "']"));
          //searchHighlight_DataChannel.click();
          JavascriptExecutor executor = (JavascriptExecutor)driver;
          executor.executeScript("arguments[0].click();", searchHighlight_DataChannel);

        }
      } else {
        input_DataChannel.sendKeys(sDataChannel + " " + Keys.TAB);
        wait.until(ExpectedConditions.elementToBeClickable(button_Save));
        button_Save.click();
        throw new Exception("no data channel");
      }

      // synchronize
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector
              (".channelInputResults")));
      wait.until(ExpectedConditions.visibilityOf(button_Save));
//            wait.until(ExpectedConditions.elementToBeClickable(button_Save));

      result = ResultStore.success("Successfully set Data Channel to " + sDataChannel);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to set Data Channel " + sDataChannel + ". error: " +
              Misc.getStacktraceAsString(exc));

    }

    return result;
  }
  public Map<String, String> setsearchFields(List<String> sSearchFields)
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleepAndLog(1000);
      //wait.until(ExpectedConditions.elementToBeClickable(input_searchField));
      //input_searchField.click();
      wait.until(ExpectedConditions.elementToBeClickable(select_searchFieldMultiSelect));
      Select dataField = new Select(select_searchFieldMultiSelect);

      TestUtils.sleepAndLog(1000);

      for (String sSearchField : sSearchFields)
      {
        dataField.selectByVisibleText(sSearchField);
        wait.until(ExpectedConditions.elementToBeSelected(By.cssSelector
                ("multiple-select#searchFields option[value='" + sSearchField + "']")));
      }
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

      result = ResultStore.success("Successfully selected Data Field to " + sSearchFields.toString());

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to selected Data Field " + sSearchFields.toString() + "." +
              " error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }


  public Map<String, String> clearandSetDataChannel(String sDataChannel)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_DataChannel));
      wait.until(ExpectedConditions.elementToBeClickable(clearDataChannel));
      Actions act2=new Actions(driver);
      TestUtils.sleepAndLog(1000);
      act2.moveToElement(clearDataChannel).click(clearDataChannel).build().perform();
      TestUtils.sleepAndLog(1000);
      //clearDataChannel.click();
      input_DataChannel.clear();
      input_DataChannel.sendKeys(sDataChannel + Keys.ENTER);
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".channelResultList" +
              ".ng-scope")));
      WebElement searchHighlight_DataChannel = driver.findElement(By.cssSelector
              (".channelResultList.ng-scope"));

      //searchHighlight_DataChannel.click();
      Actions act1=new Actions(driver);
      act1.moveToElement(searchHighlight_DataChannel).click(searchHighlight_DataChannel).build().perform();
      // synchronize
//            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id
// ("channelInputResults")));
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

      result = ResultStore.success("Successfully set Data Channel to " + sDataChannel);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to set Data Channel " + sDataChannel + ". error: " +
              Misc.getStacktraceAsString(exc));

    }

    return result;
  }
  // SELECT DATA CHANNEL
  public Map<String, String> selectDataChannel(String sDataChannel)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_DataChannel));
      input_DataChannel.clear();
      input_DataChannel.sendKeys(sDataChannel + Keys.ENTER);
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".channelResultList" +
              ".ng-scope")));
      WebElement searchHighlight_DataChannel = driver.findElement(By.cssSelector
              (".channelResultList.ng-scope"));
      searchHighlight_DataChannel.click();

      // synchronize
//            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id
// ("channelInputResults")));
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

      result = ResultStore.success("Successfully set Data Channel to " + sDataChannel);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to set Data Channel " + sDataChannel + ". error: " +
              Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  // SET DATA CHANNEL
  public Map<String, String> setDataField(String sDataField)
  {
    Map<String, String> result;
    try
    {
      TestUtils.sleepAndLog(1000);
      wait.until(ExpectedConditions.elementToBeClickable(select_DataField_search)).click();
      TestUtils.sleepAndLog(1000);
      Actions ac = new Actions(driver);
      ac.sendKeys(sDataField).perform();
      TestUtils.sleep(1);
      ac.sendKeys(Keys.ENTER).perform();
      TestUtils.sleep(1);


      wait.until(ExpectedConditions.elementToBeClickable(button_Save));
      result = ResultStore.success("Successfully selected Data Field to " + sDataField);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to selected Data Field " + sDataField + ". " +
              "error: " + Misc.getStacktraceAsString(exc));
    }


    return result;
  }

  public Map<String, String> selectDataFieldFromDropdown(String sDataField)
  {
    Map<String, String> result;
    try
    {
      TestUtils.sleepAndLog(1000);
      WebElement dataFielddropdown = driver.findElement(By.cssSelector("#opr_field-input"));
      dataFielddropdown.click();
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(sDataField)));
      driver.findElement(By.linkText(sDataField)).click();

      TestUtils.sleepAndLog(1000);
      result = ResultStore.success("Successfully entered Data Field to " + sDataField);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to selected Data Field " + sDataField + ". " +
              "error: " + Misc.getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> selectstatusFromDropdown(String sStatusField)
  {
    Map<String, String> result;
    try
    {
      TestUtils.sleepAndLog(1000);
      WebElement dataFielddropdown = driver.findElement(By.cssSelector("#opr_status-input"));
      dataFielddropdown.click();
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(sStatusField)));
      driver.findElement(By.linkText(sStatusField)).click();
      TestUtils.sleepAndLog(1000);
      result = ResultStore.success("Successfully entered Data Field to " + sStatusField);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to selected Data Field " + sStatusField + ". " +
              "error: " + Misc.getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> typeDataFieldNew(String sDataField)
  {
    Map<String, String> result;
    try
    {
      TestUtils.sleepAndLog(1000);
      WebElement dataField = driver.findElement(By.cssSelector("#dataFields input"));
      dataField.click();
      dataField.sendKeys(sDataField);
      TestUtils.sleepAndLog(1000);
      dataField.sendKeys(Keys.ENTER);
      TestUtils.sleepAndLog(1000);
      result = ResultStore.success("Successfully entered Data Field to " + sDataField);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to selected Data Field " + sDataField + ". " +
              "error: " + Misc.getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> clearDataFieldselectCalprop()
  {
    Map<String, String> result;
    try
    {
      TestUtils.sleepAndLog(1000);
      WebElement dataField = driver.findElement(By.cssSelector("#dataFields input"));
      dataField.click();
      Actions act2=new Actions(driver);
      act2.sendKeys(Keys.BACK_SPACE).build().perform();
      act2.sendKeys(Keys.BACK_SPACE).build().perform();
      act2.sendKeys(Keys.BACK_SPACE).build().perform();
      act2.sendKeys(Keys.BACK_SPACE).build().perform();
      act2.sendKeys(Keys.BACK_SPACE).build().perform();

      result = ResultStore.success("Successfully cleared Data Field");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to clear Data Field " + ". " +
              "error: " + Misc.getStacktraceAsString(exc));
    }

    return result;
  }



  // SET DATA CHANNEL MULTI SELECT
  public Map<String, String> setDataFieldMultiSelect(List<String> sDataFields)
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleepAndLog(2000);
      wait.until(ExpectedConditions.elementToBeClickable(select_datafield_input)).click();
      TestUtils.sleepAndLog(1000);

      for (String sDataField : sDataFields)
      {
        Actions ac = new Actions(driver);
        ac.sendKeys(sDataField).perform();
        TestUtils.sleepAndLog(1000);
        ac.sendKeys(Keys.ENTER).perform();
        TestUtils.sleepAndLog(1000);
      }

      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

// div[ng-show='showWidgetForm'] div[class='form-group config-section'] label")).click();

      result = ResultStore.success("Successfully selected Data Field to " + sDataFields.toString());

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to selected Data Field " + sDataFields.toString() + "." +
              " error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> setDataFieldMultiSelectold(List<String> sDataFields)
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleepAndLog(1000);
      wait.until(ExpectedConditions.elementToBeClickable(select_datafield_input)).click();
      TestUtils.sleepAndLog(1000);

      for (String sDataField : sDataFields)
      {
        Actions ac = new Actions(driver);
        ac.sendKeys(sDataField).perform();
        TestUtils.sleep(1);
        ac.sendKeys(Keys.ENTER).perform();
        TestUtils.sleep(1);
      }

      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

// div[ng-show='showWidgetForm'] div[class='form-group config-section'] label")).click();

      result = ResultStore.success("Successfully selected Data Field to " + sDataFields.toString());

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to selected Data Field " + sDataFields.toString() + "." +
              " error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }


  public Map<String, String> SetDataFieldIgnoreExisting(List<String> sDataFields)
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleepAndLog(1000);
      wait.until(ExpectedConditions.elementToBeClickable(select_datafield_input)).click();
      TestUtils.sleepAndLog(1000);



      for (String sDataField : sDataFields)
      {
        if(driver.findElements(By.xpath("//li[@class='ux-tag']/span[contains(text(),'"+sDataField+"')]")).size()>0){
          System.out.println( sDataField +"already exists");
        }else{
          Actions ac = new Actions(driver);
          ac.sendKeys(sDataField).perform();
          TestUtils.sleep(1);
          ac.sendKeys(Keys.ENTER).perform();
          TestUtils.sleep(1);
        }
      }

      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

// div[ng-show='showWidgetForm'] div[class='form-group config-section'] label")).click();

      result = ResultStore.success("Successfully selected Data Field to " + sDataFields.toString());

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to selected Data Field " + sDataFields.toString() + "." +
              " error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> setDataFieldMultiSelectgecko(List<String> sDataFields) {
    Map<String, String> result;

    try {
      TestUtils.sleepAndLog(1000);
      WebElement dataField = driver.findElement(By.cssSelector("#dataFields input"));
      for (String sDataField : sDataFields) {
        dataField.sendKeys(sDataField);
        dataField.sendKeys(Keys.ENTER);
        TestUtils.sleep(1);
      }

      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

// div[ng-show='showWidgetForm'] div[class='form-group config-section'] label")).click();

      result = ResultStore.success("Successfully selected Data Field to " + sDataFields.toString());

    } catch (Exception exc) {

      result = ResultStore.failure("Failed to selected Data Field " + sDataFields.toString() + "." +
              " error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> setDataFieldMultiSelectTypeText(List<String> sDataFields)
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleepAndLog(2000);
      wait.until(ExpectedConditions.elementToBeClickable(select_datafield_input));
      TestUtils.sleepAndLog(1000);

      select_datafield_input.click();

      for (String sDataField : sDataFields)
      {
        typingText(select_datafield_input, sDataField, 50);
        TestUtils.sleep(1);
        select_datafield_input.sendKeys(Keys.ENTER);
        TestUtils.sleep(1);
      }
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));
      result = ResultStore.success("Successfully selected Data Field to " + sDataFields.toString());

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to selected Data Field " + sDataFields.toString() + "." +
              " error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> getDataChannels()
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_DataChannel));
      input_DataChannel.clear();
      input_DataChannel.sendKeys(Keys.ENTER);
      // synchronization
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector
              (".channelInputResults")));
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("" +
              ".channelInputResults " +
              "ul li")));

      WebElement div_channelInputResult = driver.findElement(By.cssSelector("" +
              ".channelInputResults"));
      List<WebElement> channels = div_channelInputResult.findElements(By.cssSelector
              //("search-highlight"));
                      ("bvd-search-highlight"));
      List<String> dataChannels = new ArrayList<>();

      for (WebElement channel : channels)
      {
        dataChannels.add(channel.getAttribute("text").replace("<>", " "));
      }

      result = ResultStore.success(dataChannels.toString());

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to get Data Channels . error: " + Misc
              .getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> getSelectedDataChannels()
  {
    Map<String, String> result;
    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(wrapper_DataChannel));
      // synchronization
      List<WebElement> selectedChannels = wrapper_DataChannel.findElements(By.cssSelector(".channelResultListElement.ng-scope"));
      wait.until(ExpectedConditions.visibilityOfAllElements(selectedChannels));

      List<String> selectedDataChannels = new ArrayList<>();


      for (WebElement channel : selectedChannels)
      {
        selectedDataChannels.add(channel.getText());
      }

      result = ResultStore.success("The next channels are selected: \n"+selectedDataChannels);
      result.put("OUT", selectedDataChannels.toString());

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to get Data Channels . error: " + Misc
              .getStacktraceAsString(exc));

    }
    return result;
  }

  // change title
  public Map<String, String> changeTitle(String sNewTitle)
  {
    Map<String, String> result;

    try
    {
//            wait.until(ExpectedConditions.elementToBeClickable(input_Title));
//            input_Title.clear();
//            input_Title.sendKeys(sNewTitle);

      String xpath = ".//input[@id='title']";
      wait.until(presenceOfElementLocated(By.xpath(xpath)));
      driver.findElement(By.xpath(xpath)).sendKeys(Keys.chord(Keys.CONTROL, "a") + sNewTitle);

      if (!input_Title.getText().equalsIgnoreCase(sNewTitle))
      {
        driver.findElement(By.xpath(xpath)).sendKeys(Keys.chord(Keys.CONTROL, "a") + sNewTitle);
        System.out.println("Kostyl detected");
      }
      wait.until(ExpectedConditions.textToBePresentInElementValue(input_Title, sNewTitle));
      // synchronize
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

      result = ResultStore.success("Successfully changed Title to " + sNewTitle);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to change Title to " + sNewTitle + ". error: " + Misc
              .getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> setNumberFormat(String sNumberFormat)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_TextFormat));
      input_TextFormat.clear();
      input_TextFormat.sendKeys(sNumberFormat);

      // synchronize
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

      result = ResultStore.success("Successfully set Number Format to " + sNumberFormat);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to set Number Format to " + sNumberFormat + ". error: " +
              "" + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> setColoringRule(String sColoringRule)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_ColoringRule)).click();
      //input_ColoringRule.clear();
      //input_ColoringRule.sendKeys(Keys.chord(Keys.CONTROL, "a") + sColoringRule);
      Actions ac = new Actions(driver);
      ac.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys
              (Keys.DELETE).sendKeys(sColoringRule).build().perform();


      // synchronize
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

      result = ResultStore.success("Successfully set Coloring Rule to " + sColoringRule);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to set Coloring Rule to " + sColoringRule + ". error: " +
              "" + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> getColoringRule()
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOf(input_ColoringRule));

      String textResult = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0]" +
              ".value;", input_ColoringRule);
      result = ResultStore.success(textResult);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to get Coloring Rule to . error: " +
              "" + Misc.getStacktraceAsString(exc));
    }

    return result;
  }


  public Map<String, String> setImageSelectionRule(String sImageSelectionRule)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_ImageSelectionRule));
      input_ImageSelectionRule.sendKeys(Keys.chord(Keys.CONTROL, "a") + sImageSelectionRule);

      // synchronize
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));
      result = ResultStore.success("Successfully set Image Selection Rule to " +
              sImageSelectionRule);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to set Image Selection Rule to " + sImageSelectionRule
              + ". error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> setVisibilityRule(String sVisibilityRule)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_VisibilityRule));
      input_VisibilityRule.clear();
      input_VisibilityRule.sendKeys(sVisibilityRule);

      // synchronize
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));
      result = ResultStore.success("Successfully set Visibility Rule to " + sVisibilityRule);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to set Visibility Rule to " + sVisibilityRule + ". " +
              "error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> getVisibilityRule()
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOf(input_VisibilityRule));

      String textResult = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0]" +
              ".value;", input_VisibilityRule);
      result = ResultStore.success(textResult);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to get Visibility Rule to . error: " +
              "" + Misc.getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> pageScrollView(By ele)
  {
    Map<String, String> result;

    try
    {

      WebElement element = driver.findElement(ele);
      JavascriptExecutor scroll = (JavascriptExecutor)driver;
      scroll.executeScript("arguments[0].scrollIntoView();", element);


      result = ResultStore.success("Successfully scrolled Element to View" );

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to scroll: " + Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> setHyperlinkToAnotherDashboard(String sDashboard)
  {
    Map<String, String> result;
    try
    {
      TestUtils.sleepAndLog(2000);
      By hyperlinkRadio = By.cssSelector("input#hyperlink-dashboard[type='radio']");
      wait.until(ExpectedConditions.presenceOfElementLocated(hyperlinkRadio));
      pageScrollView(hyperlinkRadio);
      driver.findElement(hyperlinkRadio).click();
      TestUtils.sleepAndLog(500);

      By hyperlinkInputBy = By.cssSelector("opr-dropdown#hyperlink_select button.opr-dropdown-selection-button");
      WebElement dataField = driver.findElement(hyperlinkInputBy);
      pageScrollView(hyperlinkInputBy);
      Actions act = new Actions(driver);
      //act.moveToElement(dataField, 30, 10).build().perform();
      //act.click().build().perform();
      act.moveToElement(dataField).build().perform();
      act.click(dataField).build().perform();

      TestUtils.sleepAndLog(1000);
      hyperlinkinputtexttotype.clear();
      hyperlinkinputtexttotype.sendKeys(sDashboard + " " + Keys.ENTER);
      TestUtils.sleepAndLog(3000);
      if(driver.findElements(By.cssSelector("div.list-group")).size() != 0){
        try {
          WebElement searchHighlight_Dashboard = driver.findElement(By.cssSelector
                  ("div[search-highlight][text='" + sDashboard + "']"));
          searchHighlight_Dashboard.click();
        } catch (org.openqa.selenium.StaleElementReferenceException ex) {
          WebElement searchHighlight_Dashboard = driver.findElement(By.cssSelector
                  ("div[search-highlight][text='" + sDashboard + "']"));
          searchHighlight_Dashboard.click();

        }
      } else {
        input_Dashboard.sendKeys(sDashboard + " " + Keys.TAB+ " " +Keys.ENTER);
        wait.until(ExpectedConditions.elementToBeClickable(button_Save));
        button_Save.click();
        throw new Exception("no data channel");
      }
      result = ResultStore.success("Successfully selected Data Field to " + sDashboard);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to selected Data Field " + sDashboard + ". " +
              "error: " + Misc.getStacktraceAsString(exc));
    }

    return result;
  }



  public Map<String, String> setHyperlinkToAnotherDashboard_passparams(String sDashboard,boolean boolvalue)

  {
    Map<String, String> result;
    try
    {
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input#hyperlink-none[type='radio']")));
      if (!(hyperlinkdefaultradiobutton.getAttribute("class").contains("ng-not-empty")))
      {
        Assert.assertTrue(false,"default Hyperlink none radio button is not selected");
      }

      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input#hyperlink-dashboard[type='radio']")));
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", hyperlinktoanotherdashboradradiobutton);
      hyperlinktoanotherdashboradradiobutton.click();


      By hyperlinkInputBy = By.cssSelector("opr-dropdown#hyperlink_select button.opr-dropdown-selection-button");
      WebElement dataField = driver.findElement(hyperlinkInputBy);
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dataField);
      TestUtils.sleepAndLog(1000);
      Actions act = new Actions(driver);
      act.moveToElement(dataField).build().perform();
      act.click(dataField).build().perform();
      TestUtils.sleepAndLog(1000);


      hyperlinkinputtexttotype.clear();
      hyperlinkinputtexttotype.sendKeys(sDashboard + " " + Keys.ENTER);
      TestUtils.sleepAndLog(3000);
      if(driver.findElements(By.cssSelector("div.list-group")).size() != 0){
        try {
          WebElement searchHighlight_Dashboard = driver.findElement(By.cssSelector
                  ("div[search-highlight][text='" + sDashboard + "']"));
          searchHighlight_Dashboard.click();
        } catch (org.openqa.selenium.StaleElementReferenceException ex) {
          WebElement searchHighlight_Dashboard = driver.findElement(By.cssSelector
                  ("div[search-highlight][text='" + sDashboard + "']"));
          searchHighlight_Dashboard.click();
        }
      } else {
        input_Dashboard.sendKeys(sDashboard + " " + Keys.TAB+ " " +Keys.ENTER);
        wait.until(ExpectedConditions.elementToBeClickable(button_Save));
        passparamtodashboardCheckbox.click();
        button_Save.click();
        throw new Exception("no data channel");
      }

      if(boolvalue){
        TestUtils.sleep(2);
        passparamtodashboardCheckbox.click();
      }

      result = ResultStore.success("Successfully selected Dashboard: " + sDashboard);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to selected Data Field " + sDashboard + ". " +
              "error: " + Misc.getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> setHyperlinkToAnotherDashboardTemplateCRUD(String sDashboard)
  {
    Map<String, String> result;
    try
    {
      Capabilities capa = ((RemoteWebDriver) driver).getCapabilities();
      String browserName = capa.getBrowserName().toLowerCase();

      System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@BROWSERNAME = " + browserName);

      TestUtils.sleepAndLog(2000);
      //By anotherDashboardBy = By.id("hyperlink-dashboard");
      By anotherDashboardBy = By.xpath("//label[input[@id='hyperlink-dashboard']]");
      wait.until(ExpectedConditions.elementToBeClickable(anotherDashboardBy));
      driver.findElement(anotherDashboardBy).click();
      TestUtils.sleepAndLog(500);

      By hyperlinkInputBy = By.id("-input");
      WebElement dataField = driver.findElement(hyperlinkInputBy);
      if (browserName.equals("chrome"))
      {
        System.out.println("###### chrome workaround " + browserName);
        Actions act = new Actions(driver);
        act.moveToElement(dataField, 50, 10).build().perform();
        act.click().build().perform();
        TestUtils.sleepAndLog(1000);
      }


      By menuListBy = By.id("hyperlink_select");
      wait.until(ExpectedConditions.visibilityOfElementLocated(menuListBy));
      WebElement menuList = driver.findElement(menuListBy);

      By menuItemBy = By.linkText(sDashboard);
      wait.until(ExpectedConditions.elementToBeClickable(menuItemBy));
      menuList.findElement(menuItemBy).click();

      result = ResultStore.success("Successfully selected Data Field to " + sDashboard);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to selected Data Field " + sDashboard + ". " +
              "error: " + Misc.getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> setHyperlinkToUrl(String sUrl)
  {
    Map<String, String> result;

    try
    {
      wait.until(presenceOfElementLocated(By.id("hyperlink-url")));
      input_HyperlinkUrl.findElement(By.xpath("..")).click();
      Actions ac = new Actions(driver);
      ac.sendKeys(Keys.TAB);
      wait.until(ExpectedConditions.elementToBeClickable(input_Hyperlink));
      wait.until(ExpectedConditions.visibilityOf(input_Hyperlink));
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", input_Hyperlink_text);

      ac.moveToElement(input_Hyperlink).click(input_Hyperlink).build().perform();
      TestUtils.sleepAndLog(1000);
      ac.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys
              (Keys.DELETE).sendKeys(sUrl).build().perform();

      // synchronize
//            driver.findElement(By.cssSelector("div[class='properties']
// div[ng-show='showWidgetForm'] div[class='form-group config-section'] label")).click();
      wait.until(ExpectedConditions.textToBePresentInElementValue(input_Hyperlink, sUrl));
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

      result = ResultStore.success("Successfully set Hyperlink to Url " + sUrl);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to set Hyperlink to Url " + sUrl + ". error: " + Misc
              .getStacktraceAsString(exc));

    }

    return result;
  }


  public Map<String, String> setMaxValue(String sMaxValue)
  {
    Map<String, String> result;

    try
    {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      TestUtils.sleep(1);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("maxValue")));
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("maxValue")));
      wait.until(ExpectedConditions.elementToBeClickable(input_MaxValue)).click();
      //input_MaxValue.clear();
      Actions ac = new Actions(driver);
      ac.moveToElement(input_MaxValue).click().keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys
              (Keys.DELETE).sendKeys(sMaxValue).build().perform();
//            input_MaxValue.sendKeys(Keys.chord(Keys.CONTROL, "a") + sMaxValue);
      //input_MaxValue.sendKeys(sMaxValue);

      // synchronize
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

      result = ResultStore.success("Successfully set Max Value to " + sMaxValue);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to set Max Value to " + sMaxValue + ". error: " + Misc
              .getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> setChartAutoScale(boolean bEnabled)
  {
    Map<String, String> result;

    try
    {
      wait.until(presenceOfElementLocated(By.id("opr_chart_auto_scale")));
      if (input_ChartAutoScale.isSelected() && bEnabled)
      {

        result = ResultStore.failure("Checkbox is already selected");

      }
      else if (!(input_ChartAutoScale.isSelected()) && !(bEnabled))
      {

        result = ResultStore.failure("Checkbox is already deselected");

      }
      else
      {
        // click on parent label
        input_ChartAutoScale.findElement(By.xpath("..")).click();
        //synchronize
        wait.until(ExpectedConditions.elementSelectionStateToBe(input_ChartAutoScale, bEnabled));
        wait.until(ExpectedConditions.elementToBeClickable(button_Save));

        result = ResultStore.success("Successfully set Checkbox to " + bEnabled);

      }
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click checkbox. error: " + Misc
              .getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> setChartColors(String sChartColors)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_ChartColors));
      input_ChartColors.clear();
      input_ChartColors.sendKeys(sChartColors);

      // synchronize
      TestUtils.sleepAndLog(1500);
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

      result = ResultStore.success("Successfully set Chart Colors to " + sChartColors);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to set Chart Colors to " + sChartColors + ". error: "
              + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> reverseDisplayOrder(boolean bEnabled)
  {
    Map<String, String> result;

    try
    {
      wait.until(presenceOfElementLocated(By.id("opr_chart_reverse")));
      if (input_ReverseDisplayOrder.isSelected() && bEnabled)
      {

        result = ResultStore.failure("Checkbox is already selected");

      }
      else if (!(input_ReverseDisplayOrder.isSelected()) && !(bEnabled))
      {

        result = ResultStore.failure("Checkbox is already deselected");

      }
      else
      {
        // click on parent label
        input_ReverseDisplayOrder.findElement(By.xpath("..")).click();
        //synchronize
        wait.until(ExpectedConditions.elementSelectionStateToBe(input_ReverseDisplayOrder,
                bEnabled));
        wait.until(ExpectedConditions.elementToBeClickable(button_Save));

        result = ResultStore.success("Successfully set Checkbox to " + bEnabled);

      }
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click checkbox. error: " + Misc
              .getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> setMaxFeedItems(String sMaxFeedItems)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_MaxFeedItem)).click();
      //input_MaxFeedItem.clear();
      //input_MaxFeedItem.sendKeys(Keys.chord(Keys.CONTROL, "a") + sMaxFeedItems);

      new Actions(driver).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys
              (Keys.DELETE).sendKeys(sMaxFeedItems).build().perform();

      // synchronize
      wait.until(ExpectedConditions.textToBePresentInElementValue(input_MaxFeedItem,
              sMaxFeedItems));
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

      result = ResultStore.success("Successfully set Max Feed Items to " + sMaxFeedItems);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to set Max Feed Items to " + sMaxFeedItems + ". error:" +
              " " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> setTransparentBackground(Boolean bTransparentBackground)
  {
    Map<String, String> result;

    try
    {
      wait.until(presenceOfElementLocated(By.id("opr_transparent_background")));
      if (input_TransparentBackground.isSelected() && bTransparentBackground)
      {

        result = ResultStore.failure("Checkbox is already selected");

      }
      else if (!(input_TransparentBackground.isSelected()) && !(bTransparentBackground))
      {

        result = ResultStore.failure("Checkbox is already deselected");

      }
      else
      {
        // click on parent label
        wait.until(ExpectedConditions.elementToBeClickable(input_TransparentBackground
                .findElement(By.xpath(".."))));
        input_TransparentBackground.findElement(By.xpath("..")).click();
        //synchronize
        wait.until(ExpectedConditions.elementSelectionStateToBe(input_TransparentBackground,
                bTransparentBackground));
        wait.until(ExpectedConditions.elementToBeClickable(button_Save));

        result = ResultStore.success("Successfully set Checkbox to " + bTransparentBackground);

      }
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click checkbox. error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> setWebPage(String sWebPage)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_WebPage));
      input_WebPage.clear();
      input_WebPage.sendKeys(sWebPage);

      // synchronize
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

      result = ResultStore.success("Successfully set Included Web Page to " + sWebPage);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to set Included Web Page to " + sWebPage + ". error: "
              + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> assignNewCategory(String sCategory)
  {
    Map<String, String> result;

    try
    {

      wait.until(ExpectedConditions.elementToBeClickable(input_Category));
      input_Category.clear();
      typingText(input_Category,sCategory,10);
      input_Category.sendKeys(Keys.ENTER);
      TestUtils.sleep(2);
      String actualCategory=driver.findElement(By.xpath("//*[@placeholder='Type here to create a permission category']//preceding::li[1]/span")).getText();
      Assert.assertEquals(actualCategory,sCategory,"Category is not assigned ");
      // synchronize
      result = ResultStore.success("Successfully assigned dashboard to category " + sCategory);

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to assign dashboard to category " + sCategory + ". " +
              "error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> assignNewCategoryMultiple(String sCategory)
  {
    Map<String, String> result;

    try
    {

      wait.until(ExpectedConditions.elementToBeClickable(input_Category));
      input_Category.clear();
      typingText(input_Category,sCategory,10);
      input_Category.sendKeys(Keys.ENTER);
      TestUtils.sleep(2);
      List<WebElement> categories=driver.findElements(By.xpath("//*[@placeholder='Type here to create a permission category']//preceding::li[1]/span"));
      Set<String> catset=new HashSet<>();
      for (WebElement cat : categories)
      {
        catset.add(cat.getText());

      }
      Assert.assertTrue(catset.contains(sCategory),"Category is not assigned" );
      result = ResultStore.success("Successfully assigned dashboard to category " + sCategory);

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to assign dashboard to category " + sCategory + ". " +
              "error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> assignNewMenuCategory(String sMenuCategory)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_MenuCategory));
      input_MenuCategory.clear();
      input_MenuCategory.sendKeys(sMenuCategory + " " + Keys.ENTER);
      TestUtils.sleep(2);
      String actualMenuCategory=driver.findElement(By.xpath("//*[@placeholder='Type here to create a menu category']//preceding::li[1]/span")).getText();
      Assert.assertEquals(actualMenuCategory,sMenuCategory,"Menu Category is not assigned to Menu Category");
      result = ResultStore.success("Successfully assigned dashboard to menu category " + sMenuCategory);

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to assign dashboard to menu category " + sMenuCategory + ". " +
              "error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> assignMenuCategory(String sMenuCategory)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_MenuCategory));
      input_MenuCategory.click();
      TestUtils.sleep(2);
      WebDriverWait localWait = new WebDriverWait(driver, 30);
      WebElement spanformenucategory=driver.findElement(By.xpath("//ux-typeahead-options-list/ol/li/span[contains(text(),'"+sMenuCategory+"')]"));
      Actions act=new Actions(driver);
      act.moveToElement(spanformenucategory).click(spanformenucategory).build().perform();
      TestUtils.sleep(1);
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));
      String actualMenuCategory=driver.findElement(By.xpath("//*[@placeholder='Type here to create a menu category']//preceding::li[1]/span")).getText();
      Assert.assertEquals(actualMenuCategory,sMenuCategory,"Menu Category is not assigned to Menu Category");
      apply();

      result = ResultStore.success("Successfully assigned existing menu category to dashboard " + sMenuCategory);

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to assign existing menu category to dashboard  " + sMenuCategory + ". " +
              "error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> verifyDefaultScalingOption(String expected_scaling)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOf(input_Scaling));
      //wait.until(ExpectedConditions.elementToBeClickable(input_Scaling));
      String actual_scaling = input_Scaling.getText();
      System.out.println("actual value =="+actual_scaling);
      if (expected_scaling.equals(actual_scaling))
        result = ResultStore.success("Successfully verified default scaling option " + expected_scaling);
      else
        result =  ResultStore.failure("Default Scaling option is set to different value " + actual_scaling);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to verify default scaling option " + expected_scaling + ". " +
              "error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> assignScalingOption(String scaling_option)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(scalingOption));
      scalingOption.click();

      By menuListBy = By.cssSelector("ul.dropdown-menu[aria-labelledby='displayName-input']");
      wait.until(ExpectedConditions.visibilityOfElementLocated(menuListBy));
      WebElement menuList = driver.findElement(menuListBy);

      By menuItemBy = By.linkText(scaling_option);
      wait.until(ExpectedConditions.elementToBeClickable(menuItemBy));
      menuList.findElement(menuItemBy).click();
      result = ResultStore.success("Successfully assigned scaling option" + scaling_option);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to assign scaling option " + scaling_option + ". " +
              "error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> verifyScrollBar(String scaling_option) {
    Map<String, String> result;
    try {
      ((JavascriptExecutor) driver).executeScript("javascript:window.scrollBy(0,250)");

      result = ResultStore.success("Successfully scrolled Element to View" );

    } catch (Exception exc) {
      result = ResultStore.failure("Failed to scroll: " + scaling_option + ". " +
              "error: " + Misc.getStacktraceAsString(exc));

    }
    return result;
  }

  public Map<String, String> assignExistingCategory(String sCategory)
  {
    Map<String, String> result;

    try
    {
      wait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));
//            TestUtils.sleepAndLog(3000);
      wait.until(ExpectedConditions.elementToBeClickable(input_Category));
      input_Category.click();
      input_Category.clear();
      input_Category.sendKeys(sCategory);

      String xpath = ".//button[contains(@data-id,'" + sCategory + "')]/..";
      int num = driver.findElements(By.xpath(xpath)).size();
      if (num > 0)
      {
        wait.until(presenceOfElementLocated(By.xpath(xpath)));
        driver.findElement(By.xpath(xpath)).click();
      }
      else
      {
        wait.until(ExpectedConditions.visibilityOf(button_AddCategory));
        button_AddCategory.click();
      }

      // synchronize
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

      result = ResultStore.success("Successfully assigned dashboard to category " + sCategory);

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to assign dashboard to category " + sCategory + ". " +
              "error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> removeCategoryAssignment(String category)
  {
    Map<String, String> result;

    try
    {

      WebDriverWait localWait = new WebDriverWait(driver, 60);
      TestUtils.sleepAndLog(1500);
      localWait.until(presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));

      String xpath="//span[contains(text(),'"+category+"')]/ancestor::li/button/ux-icon[@name='close']";

      localWait.until(presenceOfElementLocated(By.xpath(xpath)));
      UiTestUtils.tryToClick(driver, By.xpath(xpath), 1500, 5);
      // synchronize
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));
      apply();

      result = ResultStore.success("Successfully removed category: " +
              category);

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to remove assignment of " + category +
              ". error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> removeMenuCategory(String menuCategory)
  {
    Map<String, String> result;

    try
    {

      WebDriverWait localWait = new WebDriverWait(driver, 30);
      TestUtils.sleepAndLog(1500);
      WebElement removeMenubtn=driver.findElement(By.xpath("//div[@id='menu-category']//li/span[contains(text(),'"+menuCategory+"')]/ancestor::li/button/ux-icon[@name='close']"));

      Actions act=new Actions(driver);
      act.moveToElement(removeMenubtn).click(removeMenubtn).build().perform();

      localWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li[class='ux-tag-input'] [placeholder='Type here to create a menu category']")));


      result = ResultStore.success("Successfully removed category: " +
              menuCategory);

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to remove assignment of " + menuCategory +
              ". error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> deleteCategory(String category)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_Category));
      input_Category.click();
      TestUtils.sleep(2);
      Actions clickOnCategoryDeleteIconAction = new Actions(driver);
      WebElement menucategorytext=driver.findElement(By.xpath("//li/span[contains(text(),'"+category+ "')]"));
      clickOnCategoryDeleteIconAction.moveToElement(menucategorytext);
      WebElement menucategtrashbtn = driver.findElement(By.xpath("//li/span[contains(text(),'"+category+"')]/following::ux-icon[1]"));
      clickOnCategoryDeleteIconAction.moveToElement(menucategtrashbtn);
      clickOnCategoryDeleteIconAction.click().build().perform();

      TestUtils.sleepAndLog(3000);
      String id = "deleteCategory";
      wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
      driver.findElement(By.id(id)).click();
      TestUtils.sleepAndLog(1500);

      result = ResultStore.success("Successfully deleted category: " +
              category);

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to delete category " + category +
              ". error: " + Misc.getStacktraceAsString(exc));
    }

    return result;
  }


  public Map<String, String> deleteCategorynew(String category)
  {
    Map<String, String> result;

    try
    {
      Actions clickOnCategoryDeleteIconAction = new Actions(driver);
      wait.until(ExpectedConditions.elementToBeClickable(input_MenuCategory));
      input_MenuCategory.click();
      clickOnCategoryDeleteIconAction.moveToElement(driver.findElement(By.cssSelector("ux-typeahead-options-list ol ux-icon[name='trash']")))
              .click().build().perform();

      String id = "deleteCategory";
      wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
      driver.findElement(By.id(id)).click();
      TestUtils.sleepAndLog(1500);

      result = ResultStore.success("Successfully deleted category: " +
              category);

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to delete category " + category +
              ". error: " + Misc.getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> deleteMenuCategory(String menucategory)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_MenuCategory));
      input_MenuCategory.click();
      Actions clickOnCategoryDeleteIconAction = new Actions(driver);
      WebElement menucategorytext=driver.findElement(By.xpath("//li/span[contains(text(),'"+menucategory+ "')]"));
      clickOnCategoryDeleteIconAction.moveToElement(menucategorytext);
      WebElement menucategtrashbtn = driver.findElement(By.xpath("//li/span[contains(text(),'"+menucategory+"')]/following::ux-icon[1]"));
      clickOnCategoryDeleteIconAction.moveToElement(menucategtrashbtn);
      clickOnCategoryDeleteIconAction.click().build().perform();

      String id = "deleteCategory";
      wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
      driver.findElement(By.id(id)).click();
      TestUtils.sleepAndLog(1500);

      result = ResultStore.success("Successfully deleted category: " +
              menucategory);

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to delete menu category " + menucategory +
              ". error: " + Misc.getStacktraceAsString(exc));
    }

    return result;
  }


  public Map<String, String> deleteCategoryerror(String menucategory)
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleep(2);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Error')]")));
      wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains(text(),'is used by "+
              "other dashboards and cannot be deleted.')]")))).isDisplayed();
      driver.findElement(By.id("ok")).click();
      wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//h3[contains(text(),'Error')]"))));
      result = ResultStore.success("Successfully deleted category: " +
              menucategory);

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to delete menu category " + menucategory +
              ". error: " + Misc.getStacktraceAsString(exc));
    }

    return result;
  }


  public Map<String, String> validateCategoriesassignedDashboard(Set<String> expectedResult)
  {
    Map<String, String> result;

    try
    {

      wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//*[@placeholder='Type here to create a permission category']//preceding::li/span"))));

      List<WebElement> categories=driver.findElements(By.xpath("//*[@placeholder='Type here to create a permission category']//preceding::li/span"));

      Set<String> catset=new HashSet<>();
      for (WebElement cat : categories)
      {
        catset.add(cat.getText());

      }
      Assert.assertEquals(catset,expectedResult,"All the categories are not present");

      // synchronize
      result = ResultStore.success("All the categories are successfully imported");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get instance categories " +
              "error: " + Misc.getStacktraceAsString(exc));

    }
    return result;
  }

  public Map<String, String> validateMenuCategoriesassigned(String expectedResult)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder='Type here to create a menu category']//preceding::li[1]/span")));
      String actualMenuCategory=driver.findElement(By.xpath("//*[@placeholder='Type here to create a menu category']//preceding::li[1]/span")).getText();
      Assert.assertEquals(actualMenuCategory,expectedResult,"Menu Category is not exported");
      result = ResultStore.success("Required Menu Category is exported ");
    } catch (Exception exc)
    {
      result = ResultStore.failure("the required Menu Category is not exported" +
              "error: " + Misc.getStacktraceAsString(exc));

    }
    return result;
  }
  public Map<String, String> getListOfCategories()
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_Category));
      input_Category.click();

      String className = "select2-result-label";
      List<WebElement> categories = driver.findElements(By.className(className));
      String toResult = "";
      for (WebElement cat : categories)
      {
        toResult = toResult.concat(cat.getText().concat(" "));
      }

      Actions action = new Actions(driver);
      action.moveToElement(button_Cancel).perform();
      action.click().perform();

      result = ResultStore.success(toResult);

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get instance categories " +
              "error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> setStatusField(String sStatusField)
  {
    Map<String, String> result;
    try
    {
      clickElementAndReturnResultDynamicWait(driver.findElement(By.id("opr_status-input")),
              "Status dropdown clicked", "Status dropdown not clicked");
      TestUtils.sleepAndLog(2000);
      clickElementAndReturnResultDynamicWait(driver.findElement(By.xpath("//a[text()='" +
              sStatusField + "']")), "Status dropdown field clicked", "Status dropdown field not " +
              "clicked");
      result = ResultStore.success("Successfully selected Status Field to " + sStatusField);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to selected Data Field " + sStatusField + ". " +
              "error: " + Misc.getStacktraceAsString(exc));
    }
    return result;

  }

  public Map<String, String> getStatusField()
  {
    Map<String, String> result;

    try
    {
      String xpath = ".//*[@id='opr_status']/option[@selected='selected']";
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
      WebElement field = driver.findElement(By.xpath(xpath));

      result = ResultStore.success(field.getAttribute("label"));

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get status field. error: " + Misc
              .getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> setDefaultValue(String sDefaultValue)
  {
    Map<String, String> result;
    try
    {
      String defaultValueId = "opr_switch_default-input";
      wait.until(ExpectedConditions.elementToBeClickable(By.id(defaultValueId)));
      WebElement defaultValue = driver.findElement(By.id(defaultValueId));
      defaultValue.click();
      TestUtils.sleepAndLog(1500);

      String menuItemXpath = "//simple-dropdown[@label-id='opr_switch_default']" +
              "//a[text()='" + sDefaultValue + "']";
      wait.until(ExpectedConditions.elementToBeClickable(By.xpath(menuItemXpath)));
      WebElement menuItem = driver.findElement(By.xpath(menuItemXpath));
      menuItem.click();

      // synchronize
      wait.until(ExpectedConditions.textToBePresentInElement(defaultValue, sDefaultValue));
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

      result = ResultStore.success("Successfully selected Default Value " + sDefaultValue);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to selected Default Value " + sDefaultValue + ". " +
              "error: " + Misc.getStacktraceAsString(exc));

    }

    return result;

  }

  public Map<String, String> setShowChartNumbers(Boolean bShowChartNumbers)
  {
    Map<String, String> result;

    try
    {
      wait.until(presenceOfElementLocated(By.id("opr_chart_numbers")));
      if (input_ShowChartNumbers.isSelected() && bShowChartNumbers)
      {
        result = ResultStore.failure("Checkbox is already selected");
      }
      else if (!(input_ShowChartNumbers.isSelected()) && !(bShowChartNumbers))
      {
        result = ResultStore.failure("Checkbox is already deselected");
      }
      else
      {
        // click on parent label
        input_ShowChartNumbers.findElement(By.xpath("..")).click();
        //synchronize
        wait.until(ExpectedConditions.elementSelectionStateToBe(input_ShowChartNumbers,
                bShowChartNumbers));
        wait.until(ExpectedConditions.elementToBeClickable(button_Save));
        result = ResultStore.success("Successfully set Checkbox to " + bShowChartNumbers);
      }
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click checkbox. error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> setShowLegend(Boolean bShowLegend)
  {
    Map<String, String> result;

    try
    {
      wait.until(presenceOfElementLocated(By.id("bvd_legend")));
      if (input_ShowLegend.isSelected() && bShowLegend)
      {
        result = ResultStore.failure("Checkbox is already selected");
      }
      else if (!(input_ShowLegend.isSelected()) && !(bShowLegend))
      {
        result = ResultStore.failure("Checkbox is already deselected");
      }
      else
      {
        // click on parent label
        input_ShowLegend.findElement(By.xpath("..")).click();
        //synchronize
        wait.until(ExpectedConditions.elementSelectionStateToBe(input_ShowLegend,
                bShowLegend));
        wait.until(ExpectedConditions.elementToBeClickable(button_Save));
        result = ResultStore.success("Successfully set Checkbox to " + bShowLegend);
      }
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click checkbox for showlegend. error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }
  public Map<String, String> setStackValues(Boolean bStackValues)
  {
    Map<String, String> result;

    try
    {
      wait.until(presenceOfElementLocated(By.id("bvd_stack")));
      if (input_StackValues.isSelected() && bStackValues)
      {
        result = ResultStore.failure("Checkbox is already selected");
      }
      else if (!(input_StackValues.isSelected()) && !(bStackValues))
      {
        result = ResultStore.failure("Checkbox is already deselected");
      }
      else
      {
        // click on parent label
        input_StackValues.findElement(By.xpath("..")).click();
        //synchronize
        wait.until(ExpectedConditions.elementSelectionStateToBe(input_StackValues,
                bStackValues));
        wait.until(ExpectedConditions.elementToBeClickable(button_Save));
        result = ResultStore.success("Successfully set Checkbox to " + bStackValues);
      }
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click checkbox for showlegend. error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }


  public Map<String, String> replaceDashboard(String sDashboardPath)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(span_replaceDashboard)).click();

      wait.until(ExpectedConditions.visibilityOf(input_BrowseDashboardFile));
      input_BrowseDashboardFile.sendKeys(sDashboardPath);
      wait.until(ExpectedConditions.elementToBeClickable(button_UploadDashboard));
      wait.until(ExpectedConditions.visibilityOf(button_UploadDashboard)).click();

      // synchronize
      waitingModaldisappearance();

      wait.until(ExpectedConditions.elementToBeClickable(button_Apply));
      result = ResultStore.success("Dashboard successfully uploaded");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to upload Dashboard. error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> replaceTemplateSvg()
  {
    Map<String, String> result;

    try
    {
      UiTestUtils.tryToClick(driver, By.id("config_apply"), 3000, 5);
      wait.until(ExpectedConditions.elementToBeClickable(button_replace_cvg)).click();
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".save-view " +
              "#config_apply[disabled='disabled']")));
      result = ResultStore.success("Template SVG successfully updated");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to upload Dashboard. error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> replaceTemplateAll(String sTemplatePath)
  {
    Map<String, String> result;

    try
    {
      result = replaceDashboard(sTemplatePath);
      Result.rcEquals(result, "0");

      waitingModaldisappearance();

      UiTestUtils.tryToClick(driver, By.id("config_apply"), 3000, 5);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id
              ("saveSVGTemplateWithInstances")));
      wait.until(ExpectedConditions.elementToBeClickable(button_replace_template));
      button_replace_template.click();
      Result.rcEquals(result, "0");

      result = ResultStore.success("Template SVG successfully updated");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to upload Dashboard. error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> setBackgroundColor(String sBackgroundColor)
  {
    Map<String, String> result;

    try
    {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      wait.until(ExpectedConditions.elementToBeClickable(input_BackgroundColor)).click();
      new Actions(driver).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys
              (Keys.DELETE).sendKeys(sBackgroundColor).build().perform();

      //input_BackgroundColor.sendKeys(Keys.chord(Keys.CONTROL, "a") + sBackgroundColor);
      // synchronize
      wait.until(ExpectedConditions.textToBePresentInElementValue(input_BackgroundColor,
              sBackgroundColor));
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));
      result = ResultStore.success("Successfully set Background Color to " + sBackgroundColor);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to set Background Color to " + sBackgroundColor + ". " +
              "error: " + Misc.getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> setStartAngle(String sStartAngle)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_StartAngle));
      input_StartAngle.clear();
      input_StartAngle.sendKeys(sStartAngle);
      // synchronize
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));
      result = ResultStore.success("Successfully set Start Angle to " + sStartAngle);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to set Start Angle to " + sStartAngle + ". error: " +
              Misc.getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> setDonutSize(String sDonutSize)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_DonutSize));
      input_DonutSize.clear();
      input_DonutSize.sendKeys(Keys.chord(Keys.CONTROL, "a") + sDonutSize);
      // synchronize
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));
      result = ResultStore.success("Successfully set Donut Size to " + sDonutSize);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to set Donut Size to " + sDonutSize + ". error: " +
              Misc.getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> setDonutHoleSize(String sDonutHoleSize)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_DonutHoleSize));
      input_DonutHoleSize.clear();
      input_DonutHoleSize.sendKeys(Keys.chord(Keys.CONTROL, "a") + sDonutHoleSize);
      // synchronize
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));
      result = ResultStore.success("Successfully set Donut Hole Size to " + sDonutHoleSize);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to set Donut Hole Size to " + sDonutHoleSize + ". " +
              "error: " + Misc.getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> getCategory()
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_Category));
      List<WebElement> list_AllCategories = driver.findElements(By.xpath
              ("//*[@placeholder='Type here to create a permission category']//preceding::li[1]/span"));
      List<String> categories = new ArrayList<>();

      for (WebElement div_Category : list_AllCategories)
      {
        categories.add(div_Category.getText());
      }
      result = ResultStore.success(categories.toString());
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Categories . error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> getMenuCategory()
  {
    Map<String, String> result;
    String actualMenuCategory="";
    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(input_MenuCategory));
      actualMenuCategory=driver.findElement(By.cssSelector("div [ng-reflect-placeholder='Menu categories'] li span")).getText();
      result = ResultStore.success(actualMenuCategory);

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get the menu category value" + actualMenuCategory + ". " +
              "error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> convertToTemplate()
  {
    Map<String, String> result;
    try
    {
      By channel1 = By.id("channelElement1");
      wait.until(ExpectedConditions.elementToBeClickable(channel1));
      UiTestUtils.clickTill(driver, channel1);
      TestUtils.sleepAndLog(1500);

//            clickOnDimension("1");

//            wait.until(ExpectedConditions.elementToBeClickable(By.id("ok")));
      UiTestUtils.clickTill(driver, By.id("ok"));

      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));

      waitingModaldisappearance();

      WebElement applyButton = driver.findElement(By.id("config_apply"));
      wait.until(ExpectedConditions.elementToBeClickable(applyButton));
      applyButton.click();

      //synchronize
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
//            apply();

      result = ResultStore.success("Template is created");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to convert to template . error: " + Misc
              .getExceptionInfo(exc));
    }

    return result;
  }

  public Map<String, String> clickOnDimension(String dimIndexInDataChannel)
  {
    Map<String, String> result;

    try
    {
      By channel = By.id("channelElement" + dimIndexInDataChannel);
      wait.until(ExpectedConditions.elementToBeClickable(channel));
      UiTestUtils.clickTill(driver, channel);
      TestUtils.sleepAndLog(1500);

//            String xpath = ".//span[@id='" + channel + "']";
//            wait.until(ExpectedConditions.presenceOfElementLocated());
      driver.findElement(By.id("propertiesBackButton")).click();

      result = ResultStore.success("Dimension is clicked");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on dimension. error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> saveNewTemplate()
  {
    Map<String, String> result;

    try
    {
      waitingModaldisappearance();
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));
      button_Save.click();
      wait.until(ExpectedConditions.visibilityOf(save_Template_Yes));
      wait.until(ExpectedConditions.elementToBeClickable(save_Template_Yes));
      save_Template_Yes.click();

      result = ResultStore.success("Template is saved");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Template is not saved . error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> saveEditedTemplate()
  {
    Map<String, String> result;

    try
    {
      waitingModaldisappearance();
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));
//            button_Save.click();
      save();
      wait.until(presenceOfElementLocated(By.id("instanceListWrapper")));

      result = ResultStore.success("Template is saved");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Template is not saved . error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> backToDashboardProperties()
  {
    Map<String, String> result;

    try
    {
      String id = "propertiesBackButton";
      wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
      driver.findElement(By.id(id)).click();

      id = "mainView";
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));

      result = ResultStore.success("We are on Dashboard Properties page");
    } catch (Exception exc)
    {
      result = ResultStore.failure("We are NOT on Dashboard Properties page . error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }


  // COLLECT BROWSER ERRORS
  public Map<String, String> getJsError()
  {
    Map<String, String> result;
    try
    {
      Set<String> logTypes = driver.manage().logs().getAvailableLogTypes();
      System.out.println("log types set: " + logTypes);

      Logs logs = driver.manage().logs();
      LogEntries logEntries = logs.get(LogType.BROWSER);

      for (LogEntry logEntry : logEntries)
      {
        System.out.println(logEntry.getMessage());
      }
      result = ResultStore.success("Successfully get Browser Errors");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to get Browser Errors. error: " + Misc
              .getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> deleteChannel()
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(icon_deleteChannel)).click();
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));

      result = ResultStore.success("Channel Successfully deleted");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Channel was not deleted: " + Misc.getStacktraceAsString
              (exc));

    }

    return result;
  }

  public Map<String, String> getChannelsVisibility()
  {
    Map<String, String> result;

    try
    {
      driver.findElement(By.cssSelector("div.searchChannels input.ng-empty"));
      result = ResultStore.success("Data Channel Field is Empty");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Data Channel Field is not Empty: " + Misc.getStacktraceAsString
              (exc));

    }

    return result;
  }

  public Map<String, String> getZoomLevel(String dashboardName)
  {
    try
    {
      WebElement dashboard = driver.findElement(By.cssSelector
              ("svg-img[dashboard-id='"+dashboardName+"'] svg>g[transform]"));
      wait.until(ExpectedConditions.visibilityOf(dashboard));
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      String s = dashboard.getAttribute("transform");
      System.out.println("###Transform = " + s);
      return ResultStore.success("Zoom level is " + s);
    } catch (Exception exc)
    {
      return ResultStore.failure("Failed to get zoom level" + Misc.getStacktraceAsString(exc));
    }
  }

  public Map<String, String> zoomIn(int pressCount)
  {
    try
    {
      for (int i = 0; i < pressCount; i++)
      {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector
                ("#zoom_in")))).click();
        TestUtils.sleepAndLog(2000);
      }
      return ResultStore.success("Zoomed in " + pressCount + " times");
    } catch (Exception e)
    {
      return ResultStore.failure("Failed to zoom in" + e.getMessage());
    }
  }

  public Map<String, String> zoomOut(int pressCount)
  {
    try
    {
      for (int i = 0; i < pressCount; i++)
      {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector
                ("#zoom_out")))).click();
        TestUtils.sleepAndLog(2000);
      }
      return ResultStore.success("Zoomed out " + pressCount + " times");
    } catch (Exception e)
    {
      return ResultStore.failure("Failed to zoom out" + e.getMessage());
    }
  }

  public Map<String, String> resetZoomLevel()
  {
    WebDriverWait localwait = new WebDriverWait(driver, 3);
    try
    {
      WebElement zoomButton = driver.findElement(By.cssSelector
              ("#zoom_reset"));
      localwait.until(ExpectedConditions.visibilityOf(zoomButton));
      zoomButton.click();
      TestUtils.sleepAndLog(2000);
      return ResultStore.success("Zoom returned to normal");
    } catch (Exception e)
    {
      return ResultStore.failure(e.getMessage());
    }
  }

  public Map<String, String> scrollToBottomDashboardProperties()
  {
    try
    {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      boolean scrollBarPresent = (boolean) js.executeScript(
              "var element = document.querySelector('.propertiesOverflowFix'); " +
                      "return element.scrollHeight > element.clientHeight;");

      System.out.println("ScrollBar is present = " + scrollBarPresent);

      if (scrollBarPresent)
      {

        UiTestUtils.scrollIntoView(driver, dashboardPropertiesContainer, "#validateUrl", true, 500);
        return ResultStore.success("ScrollBar is present");
      }

      return ResultStore.success("ScrollBar isn't present");
    } catch (Exception e)
    {
      return ResultStore.failure("Couldn't navigate to bottom of the list " + e.getMessage());
    }
  }

  public Map<String, String> setCalculationRule(String rule, int delay)
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleepAndLog(1000);
      wait.until(ExpectedConditions.visibilityOf(calculationRuleField));
      calculationRuleField.clear();
      typingText(calculationRuleField, rule, delay);
      result = ResultStore.success("Rule typed successfully");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Rule not entered: " + Misc.getStacktraceAsString
              (exc));

    }

    return result;
  }

  public Map<String, String> getCalculationRule()
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOf(calculationRuleField));

      String textResult = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0]" +
              ".value;", calculationRuleField);
      result = ResultStore.success(textResult);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to get Range Rule to . error: " +
              "" + Misc.getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> checkCalculationRule(String widget)
  {
    Map<String, String> result = null;

    try
    {
      if(driver.findElements(By.cssSelector("div.alert-error[ng-if=invalidCalculationRule]")).size() == 0
              && driver.findElements(By.cssSelector("[highlight_widget_id='" + widget + "'] g[type='invalid']")).size() == 0)
      {
        result = ResultStore.success("Rule syntax is passed");
      }
      else if(driver.findElements(By.cssSelector("div.alert-error[ng-if=invalidCalculationRule]")).size() > 0
              && driver.findElements(By.cssSelector("[highlight_widget_id='" + widget + "'] g[type='invalid']")).size() > 0)
      {
        result = ResultStore.failure("Rule syntax check failed");
      }
      else
      {
        result = ResultStore.failure("Rule syntax check failed. One or more conditions doesn't match." +
                " # of alerts: " + driver.findElements(By.cssSelector("div.alert-error[ng-if=invalidCalculationRule]")).size() +
                " # of invalid highlight widgets: " + driver.findElements(By.cssSelector("[highlight_widget_id='" + widget + "'] g[type='invalid']")).size());
      }
    } catch (Exception exc)
    {

      result = ResultStore.failure("Rule not entered: " + Misc.getStacktraceAsString
              (exc));

    }

    return result;
  }

  public Map<String, String> setCircleRange(String range)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(rangeField));
      rangeField.clear();
      rangeField.sendKeys(range);

      // synchronize
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

      result = ResultStore.success("Successfully set Range Rule to " + range);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to set Range Rule to " + range + ". error: " +
              "" + Misc.getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> getCircleRange()
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOf(rangeField));

      String textResult = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0]" +
              ".value;", rangeField);
      result = ResultStore.success(textResult);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to get Range Rule to . error: " +
              "" + Misc.getStacktraceAsString(exc));
    }

    return result;
  }

  // SET TimeSpanInMinutes
  public Map<String, String> setTimeSpanInMinutes(String timeValue)
  {
    Map<String, String> result;

    try
    {


      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      TestUtils.sleep(1);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("bvd_time")));
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bvd_time")));
      wait.until(ExpectedConditions.elementToBeClickable(input_timespan)).click();
      input_timespan.clear();
//            input_MaxValue.sendKeys(Keys.chord(Keys.CONTROL, "a") + timeValue);
      input_timespan.sendKeys(timeValue);

      // synchronize
      wait.until(ExpectedConditions.elementToBeClickable(button_Save));

      result = ResultStore.success("Successfully set time Value to " + timeValue);

    } catch (Exception exc)
    {

      result = ResultStore.failure("Failed to set time span" + timeValue.toString() + "." +
              " error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }


  public Map<String, String> setCategory(String text) {
    try {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
      TestUtils.sleepAndLog(2000);

      wait.until(ExpectedConditions.elementToBeClickable(categoryButton));
      categoryButton.click();

      TestUtils.sleepAndLog(2000);
      for (WebElement data : categoryValue) {
        if (data.getText().contains(text)){
          data.click();
          return ResultStore.success("Category Field selected");
        }
        //else {
        //return ResultStore.failure("No Value selected. Got:" + data.getText());
        //}

      }
      return ResultStore.success("Category Field selected");
    } catch (Exception exc) {

      return ResultStore.failure("Category Field not selected. error: " + Misc.getStacktraceAsString(exc));
    }
  }

  public Map<String, String> setTextOverFlowRadio()
  {
    Map<String, String> result;

    try
    {
      TestUtils.sleepAndLog(1000);
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", textOverFlowRadio);
      //wait.until(ExpectedConditions.visibilityOf(textOverFlowRadio));
      textOverFlowRadio.click();
      result = ResultStore.success("Text Overflow Radio Button selected successfully");

    } catch (Exception exc)
    {

      result = ResultStore.failure("Text Overflow Radio Button not selected" + Misc.getStacktraceAsString
              (exc));

    }

    return result;
  }

  public Map<String, String> verifyDefaultTextOverflowOption()
  {
    Map<String, String> result;
    try
    {
      TestUtils.sleep(5);
      //wait.until(ExpectedConditions.visibilityOf(input_textoverflowOption));
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", input_textoverflowOption);
      if (input_textoverflowOption.getAttribute("class").contains("ng-empty"))
      {
        result = ResultStore.success("Successfully verified Text overflow option for widget is not enabled ");
      }
      else
        result = ResultStore.success("Successfully verified Text overflow option for widget is enabled ");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to verify default text overflow option. " +
              "error: " + Misc.getStacktraceAsString(exc));

    }
    return result;
  }

  public Map<String, String> assignTextOverflowOptionForWidget()
  {
    Map<String, String> result;

    try
    {
      //wait.until(ExpectedConditions.visibilityOf(input_textoverflowOption));
      if (input_textoverflowOption.getAttribute("class").contains("ng-empty"))
      {
        result = ResultStore.success("Successfully enabled Text overflow option for widget");
        TestUtils.sleep(5);
        input_textoverflowOption.click();

      }
      else {

        result = ResultStore.success("Successfully disabled Text overflow option for widget");
        TestUtils.sleep(5);
        input_textoverflowOption.click();
      }

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to enable or disable text overflow option. " +
              "error: " + Misc.getStacktraceAsString(exc));

    }
    return result;
  }

}
