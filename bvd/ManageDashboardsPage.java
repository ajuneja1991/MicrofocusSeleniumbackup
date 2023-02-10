package com.hp.opr.qa.framework.pageobjects.bvd;


import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.hp.opr.qa.framework.utils.UiTestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import com.hp.opr.qa.framework.utils.UiTestUtils;

public class ManageDashboardsPage
{

  final WebDriver driver;
  final WebDriverWait wait;

  // BVD UPLOAD DASHBOARD
  @FindBy(how = How.ID, using = "dashboardUploadButton")
  static WebElement div_UploadDashboard;

  // BVD BROWSE
  @FindBy(how = How.ID, using = "dashboardFile")
  //@CacheLookup
  static WebElement input_BrowseDashboardFile;

  // BVD UPLOAD DASHBOARD
  @FindBy(how = How.ID, using = "upload")
  static WebElement button_UploadDashboard;

  // BVD REPLACE DASHBOARD
  @FindBy(how = How.ID, using = "replace")
  static WebElement button_ReplaceDashboard;

  // BVD CANCEL UPLOAD DASHBOARD
  @FindBy(how = How.ID, using = "cancel")
  static WebElement button_CancelUploadDashboard;

  // BVD CONFIRM DELETE DASHBOARD
  @FindBy(how = How.ID, using = "ok")
  static WebElement button_ConfirmDeleteDashboard;

  static final By buttonConfirmDelete = By.id("ok");
  Map<String, String> result;

  public ManageDashboardsPage(WebDriver driver)
  {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, 30);
  }


  // UPLOAD DASHBOARD
  public Map<String, String> uploadDashboard(String sDashboardPath)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(div_UploadDashboard));
      div_UploadDashboard.click();
      wait.until(ExpectedConditions.visibilityOf(input_BrowseDashboardFile));
      wait.until(ExpectedConditions.elementToBeClickable(input_BrowseDashboardFile));
      input_BrowseDashboardFile.sendKeys(sDashboardPath);
      wait.until(ExpectedConditions.visibilityOf(button_UploadDashboard));
      wait.until(ExpectedConditions.elementToBeClickable(button_UploadDashboard));
      button_UploadDashboard.click();
      // synchronize
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dashboard_file_name")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      TestUtils.sleepAndLog(1000);
      result = ResultStore.success("Dashboard successfully uploaded");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to upload Dashboard. error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  // UPLOAD DASHBOARD
  public Map<String, String> uploadTemplate(String templatePath)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(div_UploadDashboard));
      div_UploadDashboard.click();
      wait.until(ExpectedConditions.visibilityOf(input_BrowseDashboardFile));
      input_BrowseDashboardFile.sendKeys(templatePath);
      wait.until(ExpectedConditions.visibilityOf(button_UploadDashboard));
      button_UploadDashboard.click();
      // synchronize
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("templateTitle")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      result = ResultStore.success("Template uploaded successfully");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to upload Template. error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> clickOnColorPickerAndSetColor()
  {
    Map<String, String> result;

    try
    {

      WebElement colorPicker = driver.findElement(By.cssSelector(".form-group " +
              "input[type='color']"));
      TestUtils.sleep(1);

      Actions act =new Actions(driver);
      act.moveToElement(colorPicker);
      act.click(colorPicker).build().perform();




//      ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', " +
//                  "'top: 40.px; left: 0px;')",
//            driver.findElement(By.cssSelector(".colorpicker-visible .colorpicker-hue i")));
//
//      driver.findElement(By.cssSelector(".colorpicker-visible .colorpicker-hue i")).click();

      TestUtils.sleep(1);

      ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', " +
                      "'top: 5px; left: 97px;')",
              driver.findElement(By.cssSelector(".colorpicker-visible .colorpicker-saturation i")));

      TestUtils.sleep(3);
      driver.findElement(By.cssSelector(".colorpicker-visible .colorpicker-saturation i")).click();
      TestUtils.sleep(1);
      String colorId = ((JavascriptExecutor) driver).executeScript("return arguments[0].value",  driver.findElement(By.id("bgColor"))).toString();

      TestUtils.sleep(1);
      result = ResultStore.success(colorId);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on Color picker. error: " + Misc
              .getStacktraceAsString
                      (exc));
    }

    return result;
  }

  // OPEN EDIT DASHBOARD
  public Map<String, String> openEditDashboard(String sDashboardName)
  {
    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sDashboardName + "-img")));
      WebElement img_Dashboard = driver.findElement(By.id(sDashboardName + "-img"));
      WebElement button_Edit = driver.findElement(By.id("edit-" + sDashboardName));
      WebElement svg_Edit = driver.findElement(By.cssSelector("button#edit-" + sDashboardName + "" +
              " hpe-svg-icon svg"));

      // try to hover and click
      Actions actions = new Actions(driver);
      wait.until(ExpectedConditions.visibilityOf(img_Dashboard));
      actions.moveToElement(img_Dashboard).moveToElement(svg_Edit).build().perform();
      wait.until(ExpectedConditions.elementToBeClickable(button_Edit));
      button_Edit.click();

      // not working :(
      //UiTestUtils.waitUntilPageIsLoadedAndElementIsVisible(wait, By.id("load-spinner"), By.id
      // ("dashboard_file_name"));

      // synchronize
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dashboard_file_name")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));

      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));
      TestUtils.sleepAndLog(1000);

      result = ResultStore.success("Edit Dashboard successfully opened");
    } catch (Exception exc)
    {
      try
      {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sDashboardName + "-img")));

        // make hover buttons visible
        UiTestUtils.enableTestAutomationMode(driver);
        WebElement button_Edit = driver.findElement(By.id("edit-" + sDashboardName));

        // click
        wait.until(ExpectedConditions.elementToBeClickable(button_Edit));
        button_Edit.click();

        // synchronize
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dashboard_file_name")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
                ("div[data-progress='99']")));

        result = ResultStore.success("Edit Dashboard successfully opened");
      } catch (Exception exc2)
      {
        result = ResultStore.failure("Failed to open edit Dashboard. error: " + Misc
                .getExceptionInfo(exc2));
      }
    }
    return result;
  }

  // DELETE DASHBOARD
  public Map<String, String> deleteDashboard(String sDashboardName)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sDashboardName + "-img")));
      WebElement img_Dashboard = driver.findElement(By.id(sDashboardName + "-img"));
      WebElement button_Delete = driver.findElement(By.id("delete-" + sDashboardName));
      WebElement svg_Delete = driver.findElement(By.cssSelector("button#delete-" + sDashboardName
              + " hpe-svg-icon svg"));

      // try to hover and click
      Actions actions = new Actions(driver);
      wait.until(ExpectedConditions.visibilityOf(img_Dashboard));
      actions.moveToElement(img_Dashboard).moveToElement(svg_Delete).build().perform();
      wait.until(ExpectedConditions.elementToBeClickable(button_Delete));
      button_Delete.click();

      // confirm
      wait.until(ExpectedConditions.elementToBeClickable(buttonConfirmDelete));
      button_ConfirmDeleteDashboard.click();

      // synchronize
      wait.until(ExpectedConditions.visibilityOf(div_UploadDashboard));
      wait.until(ExpectedConditions.stalenessOf(img_Dashboard));
      result = ResultStore.success("Dashboard successfully deleted");
    } catch (Exception exc)
    {
      try
      {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sDashboardName + "-img")));
        WebElement img_Dashboard = driver.findElement(By.id(sDashboardName + "-img"));

        // make hover buttons visible
        UiTestUtils.enableTestAutomationMode(driver);
        WebElement button_Delete = driver.findElement(By.id("delete-" + sDashboardName));

        // click
        wait.until(ExpectedConditions.elementToBeClickable(button_Delete));
        button_Delete.click();

        // confirm
        wait.until(ExpectedConditions.elementToBeClickable(buttonConfirmDelete));
        button_ConfirmDeleteDashboard.click();

        // synchronize
        wait.until(ExpectedConditions.visibilityOf(div_UploadDashboard));
        wait.until(ExpectedConditions.stalenessOf(img_Dashboard));

        result = ResultStore.success("Dashboard successfully deleted");
      } catch (Exception exc2)
      {
        result = ResultStore.failure("Failed to delete Dashboard. error: " + Misc
                .getStacktraceAsString(exc2));
      }
    }
    return result;
  }


  public Map<String, String> hideInMenuDashboard(String sDashboardName)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sDashboardName + "-img")));
      WebElement img_Dashboard = driver.findElement(By.id(sDashboardName + "-img"));
      WebElement button_hideinMenu = driver.findElement(By.id("hide-" + sDashboardName));
      WebElement svg_hideinMenu = driver.findElement(By.cssSelector("button#hide-" + sDashboardName
              + " hpe-svg-icon svg"));


      // try to hover and click
      Actions actions = new Actions(driver);
      wait.until(ExpectedConditions.visibilityOf(img_Dashboard));
      actions.moveToElement(img_Dashboard).moveToElement(svg_hideinMenu).build().perform();
      wait.until(ExpectedConditions.elementToBeClickable(button_hideinMenu));
      button_hideinMenu.click();

      TestUtils.sleep(2);

      WebElement button_showInMenu=driver.findElement(By.id("show-"+sDashboardName));
      // synchronize
      wait.until(ExpectedConditions.visibilityOf(button_showInMenu));
      result = ResultStore.success("Succesfully Clicked on Hide In Menu of Dashboard");
    } catch (Exception exc)
    {
      try
      {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sDashboardName + "-img")));
        WebElement img_Dashboard = driver.findElement(By.id(sDashboardName + "-img"));

        // make hover buttons visible
        UiTestUtils.enableTestAutomationMode(driver);
        WebElement button_Delete = driver.findElement(By.id("delete-" + sDashboardName));

        // click
        wait.until(ExpectedConditions.elementToBeClickable(button_Delete));
        button_Delete.click();

        // confirm
        wait.until(ExpectedConditions.elementToBeClickable(buttonConfirmDelete));
        button_ConfirmDeleteDashboard.click();

        // synchronize
        wait.until(ExpectedConditions.visibilityOf(div_UploadDashboard));
        wait.until(ExpectedConditions.stalenessOf(img_Dashboard));

        result = ResultStore.success("Dashboard successfully deleted");
      } catch (Exception exc2)
      {
        result = ResultStore.failure("Failed to delete Dashboard. error: " + Misc
                .getStacktraceAsString(exc2));
      }
    }
    return result;
  }

  public Map<String, String> showInMenuDashboard(String sDashboardName)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sDashboardName + "-img")));
      WebElement img_Dashboard = driver.findElement(By.id(sDashboardName + "-img"));
      WebElement button_hideinMenu = driver.findElement(By.id("show-" + sDashboardName));
      WebElement svg_hideinMenu = driver.findElement(By.cssSelector("button#show-" + sDashboardName
              + " hpe-svg-icon svg"));


      // try to hover and click
      Actions actions = new Actions(driver);
      wait.until(ExpectedConditions.visibilityOf(img_Dashboard));
      actions.moveToElement(img_Dashboard).moveToElement(svg_hideinMenu).build().perform();
      wait.until(ExpectedConditions.elementToBeClickable(button_hideinMenu));
      button_hideinMenu.click();

      TestUtils.sleep(2);

      WebElement button_hideInMenu=driver.findElement(By.id("hide-"+sDashboardName));
      // synchronize
      wait.until(ExpectedConditions.visibilityOf(button_hideInMenu));
      result = ResultStore.success("Succesfully Clicked on Show In Menu of Dashboard");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to click on Show In menu Dashboard. error: " + Misc
              .getStacktraceAsString(exc));

    }
    return result;
  }


  public Map<String, String> replaceDashboard(String sDashboardPath)
  {
    Map<String, String> result;

    try
    {
      // open uplooad
      wait.until(ExpectedConditions.elementToBeClickable(div_UploadDashboard));
      div_UploadDashboard.click();
      // select Dashboard
      wait.until(ExpectedConditions.visibilityOf(input_BrowseDashboardFile));
      input_BrowseDashboardFile.sendKeys(sDashboardPath);
      // upload
      wait.until(ExpectedConditions.visibilityOf(button_UploadDashboard));
      button_UploadDashboard.click();
      // replace
      wait.until(ExpectedConditions.visibilityOf(button_ReplaceDashboard));
      button_ReplaceDashboard.click();
      // synchronize
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dashboard_file_name")));
      result = ResultStore.success("Dashboard successfully replaced");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to replace Dashboard. error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> checkDashboardHiddenbtnactive(String sDashboardName) {
    Map<String, String> result;

    try {

      TestUtils.sleep(2);

      WebElement button_showInMenu=driver.findElement(By.id("show-"+sDashboardName));
      JavascriptExecutor scroll = (JavascriptExecutor)driver;
      scroll.executeScript("arguments[0].scrollIntoView();", button_showInMenu);

      result = ResultStore.success("Successfully scrolled Element to View and element is present" );

      result = ResultStore.success("Dashboard hide button is working after import");
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to hide Dashboard " + sDashboardName + ". error: " +
              Misc.getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> checkDashboardShowbtnactive(String sDashboardName) {
    Map<String, String> result;

    try {

      TestUtils.sleep(2);

      WebElement button_hideInMenu=driver.findElement(By.id("hide-"+sDashboardName));
      JavascriptExecutor scroll = (JavascriptExecutor)driver;
      scroll.executeScript("arguments[0].scrollIntoView();", button_hideInMenu);

      result = ResultStore.success("Successfully scrolled Element to View and hide in Menu element is present" );

      result = ResultStore.success("Dashboard hide button is working after import");
    } catch (Exception exc) {
      result = ResultStore.failure("Failed to hide Dashboard " + sDashboardName + ". error: " +
              Misc.getStacktraceAsString(exc));
    }
    return result;
  }



  public Map<String, String> downloadDashboard(String sDashboardName)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sDashboardName + "-img")));
      WebElement img_Dashboard = driver.findElement(By.id(sDashboardName + "-img"));
      WebElement a_Download = driver.findElement(By.id("export-" + sDashboardName));
      //WebElement hpeSvgIcon_Download = a_Download.findElement(By.cssSelector("hpe-svg-icon svg"));

      Actions actions = new Actions(driver);
      wait.until(ExpectedConditions.visibilityOf(img_Dashboard));
      actions.moveToElement(img_Dashboard).build().perform();
      wait.until(ExpectedConditions.visibilityOf(a_Download));
      //actions.moveToElement(hpeSvgIcon_Download).click().build().perform();
      a_Download.click();
      // synchronize
      wait.until(ExpectedConditions.visibilityOf(div_UploadDashboard));
      result = ResultStore.success("Dashboard successfully downloaded");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to download Dashboard. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getDownloadLink(String dashboardName)
  {
    Map<String, String> result;
    WebDriverWait localWait = new WebDriverWait(driver, 30);

    try
    {
      String id = "export-" + dashboardName;
      localWait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
      WebElement downloadButton = driver.findElement(By.id(id));

      String downloadLink = downloadButton.getAttribute("href");

      result = ResultStore.success(downloadLink);

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get link for download. " +
              "error: " + Misc.getStacktraceAsString(exc));

    }

    return result;
  }

  public Map<String, String> showDashboardInMenu(String sDashboardName)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sDashboardName + "-img")));
      WebElement img_Dashboard = driver.findElement(By.id(sDashboardName + "-img"));
      WebElement button_Show = driver.findElement(By.id("show-" + sDashboardName));
      WebElement svg_Delete = driver.findElement(By.cssSelector("button#show-" + sDashboardName +
              " hpe-svg-icon svg"));

      // try to hover and click
      Actions actions = new Actions(driver);
      wait.until(ExpectedConditions.visibilityOf(img_Dashboard));
      actions.moveToElement(img_Dashboard).moveToElement(svg_Delete).build().perform();
      wait.until(ExpectedConditions.elementToBeClickable(button_Show));
      button_Show.click();

      // synchronize
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showInMenu-" +
              sDashboardName)));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("hide-" + sDashboardName)));
      result = ResultStore.success("Successfully showed Dashboard in Menu");
    } catch (Exception exc)
    {
      try
      {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sDashboardName + "-img")));

        // make hover buttons visible
        UiTestUtils.enableTestAutomationMode(driver);
        WebElement button_Show = driver.findElement(By.id("show-" + sDashboardName));

        // click
        wait.until(ExpectedConditions.elementToBeClickable(button_Show));
        button_Show.click();

        // synchronize
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showInMenu-" +
                sDashboardName)));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("hide-" + sDashboardName)));

        result = ResultStore.success("Dashboard successfully deleted");
      } catch (Exception exc2)
      {
        result = ResultStore.failure("Failed to show Dashboard in Menu. error: " + Misc
                .getStacktraceAsString(exc2));
      }
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

  public Map<String, String> hideDashboardFromMenu(String sDashboardName)
  {
    Map<String, String> result;

    try
    {
      By ele = By.id(sDashboardName + "-img");
      pageScrollView(ele);
      wait.until(ExpectedConditions.visibilityOfElementLocated(ele));
      WebElement img_Dashboard = driver.findElement(By.id(sDashboardName + "-img"));
      By hideEle = By.id("hide-" + sDashboardName);
      pageScrollView(hideEle);
      WebElement button_Hide = driver.findElement(hideEle);
      //WebElement hpeSvgIcon_Hide = button_Hide.findElement(By.cssSelector("hpe-svg-icon svg"));


      Actions actions = new Actions(driver);
      wait.until(ExpectedConditions.visibilityOf(img_Dashboard));
      actions.moveToElement(img_Dashboard).build().perform();
      wait.until(ExpectedConditions.elementToBeClickable(button_Hide));
      //actions.moveToElement(hpeSvgIcon_Hide).click().build().perform();
      button_Hide.click();
      TestUtils.sleep(3);
      // synchronize
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("showInMenu-" +
              sDashboardName)));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("show-" + sDashboardName)));
      result = ResultStore.success("Successfully hided Dashboard from Menu");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to hide Dashboard in Menu. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> checkDashboardIsTemplate(String sDashboardName)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id(
              "template-" + sDashboardName)));

      result = ResultStore.success("This dashboard is Template");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Dashboard Template is not found. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> checkDashboardIsNotTemplate(String sDashboardName)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id(
              "edit-" + sDashboardName)));

      result = ResultStore.success("This dashboard is not Template");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Dashboard is not found. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> openTemplatePage(String sDashboardName)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));
      String id = "template-" + sDashboardName;
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
      WebElement button_editTemplate = driver.findElement(By.id(id));
      WebElement template = driver.findElement(By.id(sDashboardName + "-img"));

      Actions actions = new Actions(driver);
      wait.until(ExpectedConditions.visibilityOf(template));
      actions.moveToElement(template).build().perform();
      wait.until(ExpectedConditions.elementToBeClickable(button_editTemplate));
      button_editTemplate.click();

      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));
      id = "edit-" + sDashboardName;
      wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));

      result = ResultStore.success("Successfully transited to Template Page");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Template Page is not reached. Error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> checkTemplateIsDeleted(String sDashboardName)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(By.id(sDashboardName + "-img")));
      result = ResultStore.failure("Template was not deleted.");

    } catch (Exception exc)
    {
      result = ResultStore.success("Template is deleted successfully" + Misc.getStacktraceAsString
              (exc));

    }
    return result;
  }

  public Map<String, String> getPopupText()
  {
    Map<String, String> result;
    TestUtils.sleepAndLog(4000);
    wait.until(ExpectedConditions.alertIsPresent());
    try
    {
      String popupText = driver.switchTo().alert().getText();
      result = ResultStore.success(popupText);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Popup text is not available. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> acceptPopup()
  {
    Map<String, String> result;

    try
    {
      driver.switchTo().alert().accept();
      result = ResultStore.success("Popup accepted");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Accept popup is not successful. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getDashboardsList()
  {
    Map<String, String> result;

    try
    {
      String css = ".pull-left.config-dashboard-title.ng-binding.config-dashboard-title";
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));

      String toResult = "";
      List<WebElement> dashboards = driver.findElements(By.cssSelector(css));
      for (WebElement dashboard : dashboards)
      {
        toResult = toResult.concat(dashboard.getText().toLowerCase()).concat(";");
      }

      result = ResultStore.success(toResult);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Dashboards list unavailable. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  public Map<String, String> getDashboardsListnew()
  {
    Map<String, String> result;

    try
    {
      String css = ".pull-left.config-dashboard-title.ng-binding.config-dashboard-title";
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
              ("div[data-progress='99']")));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));

      List<WebElement> list_AllDashboards = driver.findElements(By.cssSelector(css));
      List<String> dashboards = new ArrayList<>();

      for (WebElement dashboard : list_AllDashboards)
      {
        dashboards.add(dashboard.getText().toLowerCase());
      }
      result = ResultStore.success(dashboards.toString());

    } catch (Exception exc)
    {
      result = ResultStore.failure("Dashboards list unavailable. error: " + Misc
              .getStacktraceAsString(exc));
    }
    return result;
  }

  // CHECK UPLOAD DASHBOARD IS FORBIDDEN
  public Map<String, String> checkUploadDashboardForbidden()
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#container " +
              ".element-item:nth-child(2)"))).click();
      TestUtils.sleep(4);
      String permissionText = driver.findElement(By.cssSelector(".modal-dialog h3:nth-child" +
              "(2)")).getText();
      System.out.println(permissionText);
      TestUtils.sleep(4);
      if (permissionText.equalsIgnoreCase("No Permission"))
      {
        driver.findElement(By.id("ok")).click();
        result = ResultStore.success("Dashboard upload is Forbidden");
      }
      else
      {
        result = ResultStore.failure("Failed confirm that Dashboard upload is Forbidden");
      }

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed confirm that Dashboard upload is Forbidden. " +
              "error: " + Misc.getStacktraceAsString(exc));
    }

    return result;
  }

  // CHECK UPLOAD DASHBOARD IS ALLOWED
  public Map<String, String> checkUploadDashboardAllowed()
  {
    Map<String, String> result;

    try
    {
      UiTestUtils.waitUntilPageIsLoaded(driver, wait);
//            JavascriptExecutor executor = (JavascriptExecutor) driver;
//            executor.executeScript("arguments[0].click();", div_UploadDashboard);
      wait.until(ExpectedConditions.elementToBeClickable(div_UploadDashboard)).click();

      UiTestUtils.waitUntilPageIsLoaded(driver, wait);

      String id = "cancel";
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
      driver.findElement(By.id(id)).click();

      wait.until(ExpectedConditions.visibilityOf(div_UploadDashboard));
      result = ResultStore.success("Dashboard upload is Allowed");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed confirm that Dashboard upload is Forbidden. " +
              "error: " + Misc.getStacktraceAsString(exc));
    }

    return result;
  }

}
