package com.hp.opr.qa.framework.pageobjects.bvd;

import com.mf.opr.qa.framework.utils.TestUtils;
import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.runner.Result;
import com.hp.opr.qa.framework.utils.UiTestUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * Created by Eva Sokolyanskaya on 05/07/2016.
 * BVD Template Page Objects
 */
public class TemplatePage {
    final WebDriver driver;
    private final WebDriverWait wait;
    private String css;
    private String xpath;
    private String id;

    // BVD NEW INSTANCE BUTTON
    @FindBy(how = How.CSS, using = "button[title='New']")
    private static WebElement button_new_instance;

    // BVD NEW INSTANCE INDICATOR
    @FindBy(how = How.CSS, using = "#title")
    private static WebElement instance_title;

    @FindBy(how = How.CSS, using = "input[placeholder='Type here to create a menu category']")
    static WebElement input_MenuCategory;

    // BVD NEW INSTANCE ICON
    String icon_new_instance_css = "[ng-if='instance.updated']";
    @FindBy(how = How.CSS, using = "[ng-if='instance.updated']")
    private static WebElement icon_new_instance;

    // BVD OK DELETE TEMPLATE BUTTON
    @FindBy(how = How.ID, using = "okTemplate")
    private static WebElement button_ok_delete;

    // BVD DELETE INSTANCE BUTTON
    @FindBy(how = How.ID, using = "delete-")
    private static WebElement button_delete_instance;

    // BVD UNDO DELETE INSTANCE BUTTON
    @FindBy(how = How.ID, using = "hide-")
    private static WebElement button_undo_delete_instance;

    // BVD TEMPLATE TITLE
    @FindBy(how = How.ID, using = "templateTitle")
    private static WebElement templateTitle;

    // BVD SAVE TEMPLATE BUTTON
    @FindBy(how = How.ID, using = "config_cancel")
    private static WebElement button_cancel_template;

    // BVD CANCEL TEMPLATE BUTTON
    @FindBy(how = How.ID, using = "config_save")
    private static WebElement button_save_template;

    // BVD APPLY TEMPLATE BUTTON
    @FindBy(how = How.ID, using = "config_apply")
    private static WebElement button_apply_template;

    // BVD CATEGORY
    @FindBy(how = How.CSS, using = "input[placeholder='Type here to create a permission category']")
    static WebElement input_Category;

    // BVD ADD CATEGORY
    @FindBy(how = How.CSS, using = "#select2-drop .select2-results .btn-primary")
    private static WebElement button_AddCategory;

    // BVD TEMPLATE FILTER INSTANCES FIELD
    @FindBy(how = How.ID, using = "filterInstances")
    private static WebElement field_filter_instances;

    // BVD TEMPLATE FILTER SEARCH BUTTON
//  String button_search_filter_css = ".hpe-icon.hpe-icon-search.hpe-icon-blurry-fix.ng-scope";
    @FindBy(how = How.CSS, using = ".qtm-font-icon.qtm-icon-search")
    private static WebElement button_search_filter;

    // BVD TEMPLATE BUTTON YES FOR CONVERT TEMPLATE
    @FindBy(how = How.ID, using = "templateConvert")
    private static WebElement button_yes_template_convert;

    @FindBy(how = How.CSS, using = "button[title='Toggle show in menu']")
    private static WebElement button_ToggleMenu;

    @FindBy(how = How.CSS, using = ".hpe-icon-status-new-badge")
    private static WebElement toggleBage;


    public TemplatePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);
    }

    private Map<String, String> clickElementAndReturnResultDynamicWait(WebElement element,
                                                                       String successMessage,
                                                                       String failureMessage) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            Reporter.log(successMessage);
            return ResultStore.success(successMessage);
        } catch (Exception e) {
            Reporter.log(failureMessage + e);
            return ResultStore.failure(failureMessage);
        }
    }

    // DELETE TEMPLATE
    public Map<String, String> deleteTemplate(String sDashboardName) {
        Map<String, String> result;

        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("delete-" + sDashboardName)));
            driver.findElement(By.id("delete-" + sDashboardName)).click();

            wait.until((ExpectedConditions.visibilityOf(button_ok_delete)));
            wait.until((ExpectedConditions.elementToBeClickable(button_ok_delete)));
            button_ok_delete.click();
            TestUtils.sleepAndLog(1000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mainView")));

            result = ResultStore.success("Template is successfully deleted");

        } catch (Exception exc) {

            result = ResultStore.failure("Failed to delete template. error: " + Misc.getStacktraceAsString
                    (exc));

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
    // EDIT TEMPLATE
    public Map<String, String> goToEditTemplate(String sDashboardName) {
        Map<String, String> result;

        try {
            WebDriverWait localWait = new WebDriverWait(driver, 30);
            TestUtils.sleepAndLog(1500);

            id = "edit-" + sDashboardName;
            localWait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
            TestUtils.sleepAndLog(1000);
            driver.findElement(By.id(id)).click();
            TestUtils.sleepAndLog(1000);

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
                    ("div[data-progress='99']")));
            localWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dashboard_file_name")));

            result = ResultStore.success("Edit Template successfully opened");

        } catch (Exception exc) {

            result = ResultStore.failure("Failed to open edit Template. error: " + Misc
                    .getStacktraceAsString
                            (exc));

        }
        return result;
    }

    // GET TEMPLATE TITLE
    public Map<String, String> getTemplateTitle() {
        Map<String, String> result;

        try {
            wait.until(ExpectedConditions.attributeToBeNotEmpty(templateTitle, "textContent"));

            String title = templateTitle.getAttribute("textContent");

            result = ResultStore.success(title);

        } catch (Exception exc) {
            result = ResultStore.failure("Failed to get Template Title. error: " + Misc
                    .getStacktraceAsString(exc));

        }
        return result;
    }

    // GET AUTOCREATED INSTANCE NAME
    public Map<String, String> getAutoCreatedInstanceName(String templateName) {
        Map<String, String> result;

        try {
            String instanceName = "Automatically created dashboard instance (" + templateName + " " +
                    "template)";
            result = ResultStore.success(instanceName);

        } catch (Exception exc) {
            result = ResultStore.failure("AutoCreated Instance Name is not available. error: " + Misc
                    .getStacktraceAsString(exc));

        }
        return result;
    }

    // SELECT TEMPLATE INSTANCE
    public Map<String, String> selectInstance(String instanceName) {
        Map<String, String> result;

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
            UiTestUtils.waitPageIsLoaded(driver, 15);
            TestUtils.sleepAndLog(1500);

            css = "span[title='" + instanceName + "'] .ng-isolate-scope";
            WebElement instance = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                    .cssSelector(css)));
//      WebElement instance = driver.findElement(By.cssSelector(css));

            Actions actions = new Actions(driver);
            actions.moveToElement(instance).build().perform();
            actions.click(instance).build().perform();

//      WebDriverWait localWait = new WebDriverWait(driver, 30);
//      localWait.until(ExpectedConditions.elementToBeClickable(instance));
//
//      instance.click();
//      TestUtils.sleepAndLog(1500);
//      instance.click();

            result = ResultStore.success("Instance selected");

        } catch (Exception exc) {
            result = ResultStore.failure("Instance can't be selected. error: " + Misc
                    .getStacktraceAsString(exc));

        }
        return result;
    }

    // GET TEMPLATE VARIABLES
    public Map<String, String> getVariables() {
        Map<String, String> result;
        try {

            //xpath = "//option[@class='defaultSelectOption']";
            css = "simple-dropdown #browser-input";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(css)));

            List<WebElement> variableFields = driver.findElements(By.cssSelector(css));

            String toResult = "";

            for (WebElement element : variableFields) {
                toResult = toResult.concat(element.getText()).concat(";");
            }

            result = ResultStore.success(toResult);

        } catch (Exception exc) {
            result = ResultStore.failure("Variables are not available. error: " + Misc
                    .getStacktraceAsString(exc));

        }
        return result;

/* previous code.
   // Nevertheless the name "getVariables" it was returned only one value, marked as
   defaultSelectOption

    try {
      xpath = "//option[@class='defaultSelectOption']";
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
      wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

      List<WebElement> variableFields = driver.findElements(By.xpath(xpath));

      String toResult = "";

      for (WebElement element: variableFields) {
        toResult = toResult.concat(element.getAttribute("textContent")).concat(";");
      }

      result = ResultStore.success(toResult);

    } catch (Exception exc) {
      result = ResultStore.failure("Variables are not available. error: " + Misc
            .getStacktraceAsString(exc));

    }
    return result;
    */
    }

    // GET TEMPLATE BACKGROUND
    public Map<String, String> getBackgroundColor() {
        Map<String, String> result;

        String sBackgroundColorHex;

        try {
            css = ".template-image-item";
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));
            WebElement body_Template = driver.findElement(By.cssSelector(css));
            String sBackgroundColor = body_Template.getCssValue("background-color");
            if (sBackgroundColor.contains("rgba")) {
                sBackgroundColor = sBackgroundColor.replace("rgba(", "").replaceAll("\\).*", "");
                String[] rgba = sBackgroundColor.split(", ");
                sBackgroundColorHex = String.format("#%02X%02X%02X", Integer.parseInt(rgba[0]), Integer
                        .parseInt(rgba[1]), Integer.parseInt(rgba[2]));
            } else {
                sBackgroundColor = sBackgroundColor.replace("rgb(", "").replaceAll("\\).*", "");
                String[] rgb = sBackgroundColor.split(", ");
                sBackgroundColorHex = String.format("#%02X%02X%02X", Integer.parseInt(rgb[0]), Integer
                        .parseInt(rgb[1]), Integer.parseInt(rgb[2]));
            }
            result = ResultStore.success(sBackgroundColorHex);
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to get Background Color. error: " + Misc
                    .getStacktraceAsString(exc));
        }
        return result;
    }

    // REMOVE INSTANCE
    public Map<String, String> removeInstance() {
        Map<String, String> result;

        try {

            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".list-group-item" +
                    ".instanceItemContent")));
            WebElement instance = driver.findElement(By.cssSelector(".list-group-item" +
                    ".instanceItemContent"));

            Actions actions = new Actions(driver);
            actions.moveToElement(instance).build().perform();
            TestUtils.sleepAndLog(1500);

            wait.until(ExpectedConditions.visibilityOf(button_delete_instance));
            button_delete_instance.click();

            result = ResultStore.success("Instance deleted successfully");
        } catch (Exception exc) {
            result = ResultStore.failure("Problem in deleting instance. Error: " + Misc
                    .getStacktraceAsString(exc));
        }
        return result;
    }

    // APPLY CONVERT TEMPLATE TO DASHBOARD
    public Map<String, String> applyConvertTemplateToDashboard() {
        Map<String, String> result;

        try {
            wait.until(ExpectedConditions.elementToBeClickable(button_save_template));
            button_save_template.click();

            wait.until((ExpectedConditions.elementToBeClickable(button_yes_template_convert)));
            button_yes_template_convert.click();

            result = ResultStore.success("Template is successfully converted to dashboard");

        } catch (Exception exc) {

            result = ResultStore.failure("Failed to convert template to dashboard. error: " + Misc
                    .getStacktraceAsString
                            (exc));

        }
        return result;
    }

    // TEMPLATE PRESS CANCEL
    public Map<String, String> pressCancel() {
        Map<String, String> result;

        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfElementLocated(By
                    .className("loadingBackground"))));
            wait.until(ExpectedConditions.elementToBeClickable(button_cancel_template));
            button_cancel_template.click();

            wait.until((ExpectedConditions.visibilityOfElementLocated(By.id("mainView"))));

            result = ResultStore.success("We are on Manage Dashboard page");

        } catch (Exception exc) {

            result = ResultStore.failure("Return to Manage Dashboard page was not successful. error: " +
                    Misc.getStacktraceAsString(exc));

        }
        return result;
    }

    // TEMPLATE CREATE NEW INSTANCE
    public Map<String, String> createInstance() {
        Map<String, String> result;

        try {
            wait.until(ExpectedConditions.elementToBeClickable(button_new_instance));
            button_new_instance.click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
            wait.until((ExpectedConditions.visibilityOfElementLocated(By.cssSelector
                    (icon_new_instance_css))));

            result = ResultStore.success("New Instance is created");

        } catch (Exception exc) {

            result = ResultStore.failure("New Instance is NOT created. error: " +
                    Misc.getStacktraceAsString(exc));

        }
        return result;
    }

    // TEMPLATE CREATE NEW INSTANCE WITH NAME
    public Map<String, String> createInstance(String newInstanceName) {
        Map<String, String> result;

        try {
            TestUtils.sleepAndLog(2000);
            wait.until(ExpectedConditions.visibilityOf(button_new_instance));
            wait.until(ExpectedConditions.elementToBeClickable(button_new_instance));
            button_new_instance.click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
            wait.until((ExpectedConditions.visibilityOfElementLocated(By.cssSelector
                    (icon_new_instance_css))));
            button_apply_template.click();

            result = selectInstance("New Instance");
            Result.rcEquals(result, "0");

            setInstancesTitle(newInstanceName);
//      button_apply_template.click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));

            result = ResultStore.success("New Instance " + newInstanceName + " is created");

        } catch (Exception exc) {

            result = ResultStore.failure("New Instance is NOT created. error: " +
                    Misc.getStacktraceAsString(exc));

        }
        return result;
    }

    // GET TEMPLATE INSTANCE LIST
    public Map<String, String> getInstances() {
        Map<String, String> result;

        try {
            css = "*[id='instanceList'] > div";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));

            List<WebElement> instances = driver.findElements(By.cssSelector(css));

            String toResult = "";

            for (WebElement element : instances) {
                toResult = toResult.concat(element.getText()).concat(";");
            }

            result = ResultStore.success(toResult);

        } catch (Exception exc) {
            result = ResultStore.failure("Instances are not available. error: " + Misc
                    .getStacktraceAsString(exc));

        }
        return result;
    }

    // GET NUMBER OF NEW INSTANCES
    public Map<String, String> getNewInstancesNumber() {
        Map<String, String> result;

        try {
            wait.until(ExpectedConditions.visibilityOf(icon_new_instance));

            List<WebElement> newInstances = driver.findElements(By.cssSelector(icon_new_instance_css));

            result = ResultStore.success(Integer.toString(newInstances.size()));

        } catch (Exception exc) {
            result = ResultStore.failure("No new instance found. error: " + Misc
                    .getStacktraceAsString(exc));

        }
        return result;
    }

    // SET INSTANCE TITLE
    public Map<String, String> setInstancesTitle(String newTitle) {
        Map<String, String> result;

        try {
            wait.until(ExpectedConditions.elementToBeClickable(instance_title));
            instance_title.clear();
            instance_title.sendKeys(Keys.chord(Keys.CONTROL, "a") + newTitle);
            // synchronize
            wait.until(ExpectedConditions.elementToBeClickable(button_save_template));
            button_apply_template.click();
            wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(By.id
                    ("config_apply"))));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));

            result = ResultStore.success("Successfully changed Title to " + newTitle);

        } catch (Exception exc) {

            result = ResultStore.failure("Failed to change Title to " + newTitle + ". error: " + Misc
                    .getStacktraceAsString(exc));

        }

        return result;
    }

    // SET VALUE FOR VARIABLE
    public Map<String, String> setVariableValue(String variable, String value) {
        Map<String, String> result;
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id(variable)));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(variable)));
            WebElement var = driver.findElement(By.id(variable));
            wait.until(ExpectedConditions.elementToBeClickable(var));

            By variableBy = By.xpath("//simple-dropdown[@label-id='" + variable + "']");
            wait.until(ExpectedConditions.elementToBeClickable(variableBy));
            WebElement variableUI = driver.findElement(variableBy);
            variableUI.click();
            TestUtils.sleepAndLog(1000);

            By menuItemBy = By.linkText(value);
            WebElement menuItem = variableUI.findElement(menuItemBy);
            menuItem.click();
            TestUtils.sleepAndLog(1500);
            wait.until(ExpectedConditions.elementToBeClickable(button_apply_template));
            button_apply_template.click();

            result = ResultStore.success("Successfully set " + variable + " = " + value);

        } catch (Exception exc) {

            result = ResultStore.failure("Failed to set " + variable + ". error: " + Misc
                    .getStacktraceAsString(exc));

        }
        return result;
    /* previous
    try {
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id(variable)));
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(variable)));
      WebElement var = driver.findElement(By.id(variable));
      wait.until(ExpectedConditions.elementToBeClickable(var));

      Select varSelection = new Select(var);
      varSelection.selectByVisibleText(value);

      // synchronize
      wait.until(ExpectedConditions.elementToBeSelected(By.cssSelector("#" + variable +
            " option[label='" + value + "']")));
      TestUtils.sleepAndLog(1500);
      wait.until(ExpectedConditions.elementToBeClickable(button_apply_template));
      button_apply_template.click();

      result = ResultStore.success("Successfully set " + variable + " = " + value);

    } catch (Exception exc) {

      result = ResultStore.failure("Failed to set " + variable + ". error: " + Misc
            .getStacktraceAsString(exc));

    }

    return result;
    */
    }

    // GET VALUE OF VARIABLE
    public Map<String, String> getVariableValue(String variable) {
        Map<String, String> result;

        try {
            css = "button#" + variable + "-input";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
            WebElement box = driver.findElement(By.cssSelector(css));
            String value = box.getText();

      /* previous
      css = "#" + variable;
      WebElement box = driver.findElement(By.cssSelector(css));

      Select selectedValue = new Select(box);
      String value = selectedValue.getFirstSelectedOption().getText();
      */
            result = ResultStore.success(value);

        } catch (Exception exc) {

            result = ResultStore.failure("Failed to get " + variable + ". error: " + Misc
                    .getStacktraceAsString(exc));

        }

        return result;
    }

    // IS DASHBOARD ICON PRESENTED
    public Map<String, String> isDashboardIconPresented(String instanceName) {
        Map<String, String> result;

        try {
            css = "span[title='" + instanceName + "'] .ng-isolate-scope";
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));

            css = "span[title='" + instanceName + "'] + hpe-svg-icon";
            try {
                WebElement icon = driver.findElement(By.cssSelector(css));
                icon.isDisplayed();
                result = ResultStore.success("true");
            } catch (NoSuchElementException elem_exception) {
                result = ResultStore.success("false");
            }

        } catch (Exception exc) {
            result = ResultStore.failure("Can't find icon. error: " + Misc
                    .getStacktraceAsString(exc));

        }
        return result;
    }

    public Map<String, String> clickToggleShowInMenu(String instanceName) {
        try {
            String verticalInstanceCss = ".verticalCenterPadding [text='" + instanceName + "']";
            WebElement instance = driver.findElement(By.cssSelector(verticalInstanceCss));
            Actions action = new Actions(driver);
            action.moveToElement(instance).perform();

            wait.until(ExpectedConditions.elementToBeClickable(button_ToggleMenu));

            clickElementAndReturnResultDynamicWait(button_ToggleMenu, "Toggle button clicked",
                    "Toggle button not clicked");
            TestUtils.sleepAndLog(1500);
            //if (driver.findElements(By.cssSelector(".hpe-icon-status-new-badge")).size() != 0) {
            //div.verticalCenterPadding .qtm-icon-status-new-badge
            if (driver.findElements(By.cssSelector(".qtm-icon-status-new-badge")).size() != 0) {
                return ResultStore.success("Toggle show in menu is clicked, instance tagged");
            }

            return ResultStore.failure("Toggle show in menu is clicked, BUT instance is NOT tagged");
        } catch (Exception exc) {
            return ResultStore.failure("Toggle show in menu is not clicked" + Misc
                    .getStacktraceAsString(exc));
        }
    }

    // CLICK HIDE FROM MENU TOGGLE
    public Map<String, String> clickHideFromMenu(String instanceName) {
        Map<String, String> result;

        try {

            css = "span[title='" + instanceName + "']";
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));
//      WebElement instance = driver.findElement(By.cssSelector(css));
            Actions action = new Actions(driver);
//      action.moveToElement(instance).perform();
//      wait.until(ExpectedConditions.elementToBeClickable(instance));
//      instance.click();
//      action.perform();

            xpath = ".//*[@id='show-" + instanceName + "']";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            WebElement toggle = driver.findElement(By.xpath(xpath));
            wait.until(ExpectedConditions.elementToBeClickable(toggle));

            action.moveToElement(toggle).perform();

            toggle.click();
            action.perform();


            result = ResultStore.success("Toggle successfully clicked.");

        } catch (Exception exc) {
            result = ResultStore.failure("Can't click the toggle. error: " + Misc
                    .getStacktraceAsString(exc));

        }
        return result;
    }

    // TEMPLATE PRESS APPLY BUTTON
    public Map<String, String> applyChanges() {
        Map<String, String> result;

        try {
            id = "config_apply";
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(id)));
            wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
            button_apply_template.click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
            wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable
                    (button_apply_template)));

            result = ResultStore.success("Changes applied");

        } catch (Exception exc) {

            result = ResultStore.failure("Changes NOT applied. error: " +
                    Misc.getStacktraceAsString(exc));

        }
        return result;
    }

    // SET INSTANCE FILTER
    public Map<String, String> setInstancesFilter(String filterSting) {
        Map<String, String> result;

        try {
            wait.until(ExpectedConditions.elementToBeClickable(field_filter_instances));
            field_filter_instances.clear();
            field_filter_instances.sendKeys(Keys.chord(Keys.CONTROL, "a") + filterSting);
            // synchronize

            UiTestUtils.waitPageIsLoaded(driver, 15);
            result = ResultStore.success("Instances filter was set.");

        } catch (Exception exc) {

            result = ResultStore.failure("Instances filter was NOT set. " + ". error: " + Misc
                    .getStacktraceAsString(exc));

        }

        return result;
    }

    // CLEAN INSTANCE FILTER
    public Map<String, String> cleanInstancesFilter() {
        Map<String, String> result;

        try {
            wait.until(ExpectedConditions.elementToBeClickable(field_filter_instances));
            field_filter_instances.clear();

            result = ResultStore.success("Instances filter was cleaned.");

        } catch (Exception exc) {
            result = ResultStore.failure("Instances filter was NOT cleaned. " + ". error: " + Misc
                    .getStacktraceAsString(exc));
        }

        return result;
    }

    // DELETE INSTANCE BY NAME
    public Map<String, String> clickDeleteInstance(String instanceName) {
        Map<String, String> result;

        try {

            id = "delete-" + instanceName;
            TestUtils.sleepAndLog(2500);

            String xpath = ".//button[@id='" + id + "']";
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();

            result = ResultStore.success("Delete instance button is clicked.");

        } catch (Exception exc) {
            result = ResultStore.failure("Delete instance button is NOT clicked. error: " + Misc
                    .getStacktraceAsString(exc));

        }
        return result;
    }

    // UNDO DELETE INSTANCE
    public Map<String, String> undoDeleteInstance(String instanceName) {
        Map<String, String> result;

        clickOnSomewhereElse();

        try {

            xpath = ".//*[@id='hide-" + instanceName + "']";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

            WebElement undoDeleteInstance = driver.findElement(By.xpath(xpath));
            wait.until(ExpectedConditions.elementToBeClickable(undoDeleteInstance));

            undoDeleteInstance.click();

            result = ResultStore.success("Undo delete instance button is clicked.");

        } catch (Exception exc) {
            result = ResultStore.failure("Undo delete instance button is NOT clicked. error: " + Misc
                    .getStacktraceAsString(exc));

        }
        return result;
    }

    // CLICK ON SOMEWHERE ELSE
    private Map<String, String> clickOnSomewhereElse() {
        Map<String, String> result;

        try {

            css = ".save-view";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
            WebElement someElement = driver.findElement(By.cssSelector(css));
            wait.until(ExpectedConditions.elementToBeClickable(someElement));
            someElement.click();

            result = ResultStore.success("Successfully clicked.");

        } catch (Exception exc) {
            result = ResultStore.failure("Unsuccessfully clicked. error: " + Misc
                    .getStacktraceAsString(exc));

        }
        return result;
    }

    // INSERT TEST-AUTOMATION
    public Map<String, String> insertTestAutomation() {
        Map<String, String> result;

        try {

            JavascriptExecutor js = (JavascriptExecutor) driver;
            xpath = ".//body";
            WebElement element = driver.findElement(By.xpath(xpath));
            String attName = "class";
            String attValue = element.getAttribute(attName) + " test-automation";

            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                    element, attName, attValue);

            attValue = element.getAttribute(attName);

            result = ResultStore.success("Test-Automation inserted successfully. New Class value: " +
                    attValue);

        } catch (Exception exc) {
            result = ResultStore.failure("Test-Automation was NOT inserted. error: " + Misc
                    .getStacktraceAsString(exc));

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
            TestUtils.sleepAndLog(1000);
            wait.until(ExpectedConditions.elementToBeClickable(button_apply_template));
            button_apply_template.click();
            result = ResultStore.success("Successfully assigned dashboard to menu category " + sMenuCategory);

        } catch (Exception exc)
        {
            result = ResultStore.failure("Failed to assign dashboard to menu category " + sMenuCategory + ". " +
                    "error: " + Misc.getStacktraceAsString(exc));

        }

        return result;
    }

    public Map<String, String> assignNewCategory(String sCategory) {
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

    /*public Map<String, String> getInstanceCategories() {
        Map<String, String> result;

        try {
            css = ".select2-search-choice div";
            List<WebElement> categories = driver.findElements(By.cssSelector(css));
            String toResult = "";
            for (WebElement cat : categories) {
                toResult = toResult.concat(cat.getText().concat(" "));
            }

            result = ResultStore.success(toResult);

        } catch (Exception exc) {
            result = ResultStore.failure("Failed to get instance categories " +
                    "error: " + Misc.getStacktraceAsString(exc));

        }

        return result;
    }*/

    public Map<String, String> getInstanceCategories()
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


    public Map<String, String> getListOfCategories() {
        Map<String, String> result;

        TestUtils.sleepAndLog(1500);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(input_Category));
            input_Category.click();

            String className = "select2-result-label";
            List<WebElement> categories = driver.findElements(By.className(className));
            String toResult = "";
            for (WebElement cat : categories) {
                toResult = toResult.concat(cat.getText().concat(" "));
            }

            id = "select2-drop-mask";
            WebElement element = driver.findElement(By.id(id));

            Actions action = new Actions(driver);
            action.moveToElement(element).perform();
            action.click().perform();

            result = ResultStore.success(toResult);

        } catch (Exception exc) {
            result = ResultStore.failure("Failed to get instance categories " +
                    "error: " + Misc.getStacktraceAsString(exc));

        }

        return result;
    }

    public Map<String, String> removeCategoryFromInstance(String instanceName, String category) {
        Map<String, String> result;

        try {
            result = selectInstance(instanceName);
            Result.rcEquals(result, "0");
            WebDriverWait localWait = new WebDriverWait(driver, 60);
            TestUtils.sleepAndLog(1500);
            localWait.until(presenceOfElementLocated(By.cssSelector
                    ("div[data-progress='99']")));

            String xpath="//span[contains(text(),'"+category+"')]/following-sibling::button";

            localWait.until(presenceOfElementLocated(By.xpath(xpath)));
            UiTestUtils.tryToClick(driver, By.xpath(xpath), 1500, 5);
            TestUtils.sleepAndLog(1000);
            // synchronize
            wait.until(ExpectedConditions.elementToBeClickable(button_apply_template));
            button_apply_template.click();
            UiTestUtils.waitUntilPageIsLoaded(driver, wait);

            result = ResultStore.success("Successfully remove category from instance");

        } catch (Exception exc) {
            result = ResultStore.failure("Failed to remove category from instance. " +
                    "error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    public Map<String, String> deleteCategoryFromInstance(String instanceName, String category) {
        Map<String, String> result;

        try {
            result = selectInstance(instanceName);
            Result.rcEquals(result, "0");

            /*clickElementAndReturnResultDynamicWait(input_Category, "input_Category clicked",
                    "input_Category not clicked");
            input_Category.clear();
            input_Category.sendKeys(category);

            WebElement categoryDeleteButton = driver.findElement(By.cssSelector("button[data-id='" +
                    category + "']"));


            wait.until(ExpectedConditions.elementToBeClickable(categoryDeleteButton));

            clickElementAndReturnResultDynamicWait(categoryDeleteButton, "categoryDeleteButton clicked",
                    "categoryDeleteButton not clicked");
            WebElement deleteCategoryApprove = driver.findElement(By.id("deleteCategory"));
            clickElementAndReturnResultDynamicWait(deleteCategoryApprove, "deleteCategoryApprove " +
                    "clicked", "deleteCategoryApprove not clicked");

//      wait.until(ExpectedConditions.elementToBeClickable(button_cancel_template));

            result = ResultStore.success("Successfully deleted category: " + category);

            TestUtils.sleepAndLog(3000);*/
            wait.until(ExpectedConditions.elementToBeClickable(input_Category));
            input_Category.click();
            Actions clickOnCategoryDeleteIconAction = new Actions(driver);
            WebElement menucategorytext=driver.findElement(By.xpath("//li/span[contains(text(),'"+category+ "')]"));
            clickOnCategoryDeleteIconAction.moveToElement(menucategorytext);
            WebElement menucategtrashbtn = driver.findElement(By.xpath("//li/span[contains(text(),'"+category+"')]/following::ux-icon[1]"));
            clickOnCategoryDeleteIconAction.moveToElement(menucategtrashbtn);
            clickOnCategoryDeleteIconAction.click().build().perform();

            String id = "deleteCategory";
            wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
            driver.findElement(By.id(id)).click();
            TestUtils.sleepAndLog(1500);
            result = ResultStore.success("Successfully deleted category: " + category);

        } catch (Exception exc) {
            result = ResultStore.failure("Failed to delete category. " +
                    "error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }


    public Map<String, String> getDownloadLink(String templateName) {
        Map<String, String> result;
        WebDriverWait localWait = new WebDriverWait(driver, 30);

        try {

            id = "export-" + templateName;
            localWait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
            WebElement downloadButton = driver.findElement(By.id(id));

            String downloadLink = downloadButton.getAttribute("href");

            result = ResultStore.success(downloadLink);

        } catch (Exception exc) {
            result = ResultStore.failure("Failed to get link for download. " +
                    "error: " + Misc.getStacktraceAsString(exc));

        }

        return result;
    }

//  public Map<String, String> downloadTemplate(String templateName) {
//    Map<String, String> result;
//
//    try {
//
//      id = "export-" + templateName;
//      wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
//      driver.findElement(By.id(id)).click();
//
//      // synchronize
//      wait.until(ExpectedConditions.elementToBeClickable(button_apply_template));
//      button_apply_template.click();
//      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
//
//      result = ResultStore.success("Successfully remove category from instance");
//
//    } catch (Exception exc) {
//      result = ResultStore.failure("Failed to remove category from instance. " +
//            "error: " + Misc.getStacktraceAsString(exc));
//
//    }
//
//    return result;
//  }

    // OPEN MANAGE DASHBOARDS
    public Map<String, String> openManageDashboards() {
        Map<String, String> result;

        try {
            UiTestUtils.waitUntilPageIsLoaded(driver, wait);
            id = "nav_admin_menu";
            wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
            UiTestUtils.clickTill(driver, By.id(id));

            id = "nav_system_manage";
            wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
            UiTestUtils.clickTill(driver, By.id(id));
            // synchronize
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id
// ("dashboardUploadButton")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dashboardUploadButton")));
            result = ResultStore.success("Manage Dashboards successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to open Manage Dashboards. error: " + Misc
                    .getStacktraceAsString(exc));
        }
        return result;
    }

    public Map<String, String> getPicture() {
        Map<String, String> result;

        try {
            String css = ".template-image-item";
            WebElement picture = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));
            wait.until(ExpectedConditions.attributeToBeNotEmpty(picture, "src"));

            String url = picture.getAttribute("src");

            driver.get(url);
            driver.navigate().back();
            result = ResultStore.success("Picture URL successfully opened");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to get picture. error: " + Misc.getStacktraceAsString
                    (exc));
        }

        return result;
    }
}
