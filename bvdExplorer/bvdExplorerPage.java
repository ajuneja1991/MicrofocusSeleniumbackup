package com.hp.opr.qa.framework.pageobjects.bvdExplorer;

import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;

public class bvdExplorerPage {
    final WebDriver driver;
    final WebDriverWait wait;
    @FindBy(
            how = How.ID,
            using = "userNameInput"
    )
    private WebElement input_UserName;
    @FindBy(
            how = How.ID,
            using = "userPasswordInput"
    )
    private WebElement input_Password;
    @FindBy(
            how = How.CSS,
            using = "#loginButton,#submit_login"
    )
    private WebElement button_Login;
    @FindBy(
            how = How.ID,
            using = "username"
    )
    private WebElement input_idm_username;
    @FindBy(
            how = How.ID,
            using = "password"
    )
    private WebElement input_idm_password;
    @FindBy(
            how = How.ID,
            using = "submit"
    )
    private WebElement button_idm_login;
    @FindBy(
            how = How.CSS,
            using = "div.alert"
    )
    private WebElement div_idm_loginerror;
    @FindBy(
            how = How.XPATH,
            using = "//span[text()='You Are Now Logged Out.']"
    )
    private WebElement div_idm_logout;
    @FindBy(
            how = How.CSS,
            using = "page-header-logo"
    )
    private WebElement header_logo;
    @FindBy(
            how = How.CSS,
            using = "mondrian-page"
    )
    private WebElement mondrian_page;
    @FindBy(
            how = How.CSS,
            using = ".context-filter-menu .time-selection-trigger"
    )
    private WebElement datePicker;
    @FindBy(
            how = How.CSS,
            using = ".date-picker-from-section input[aria-label='hour']"
    )
    private WebElement datePickerFromHour;
    @FindBy(
            how = How.CSS,
            using = ".date-picker-from-section input[aria-label='minute']"
    )
    private WebElement datePickerFromMunite;
    @FindBy(
            how = How.CSS,
            using = ".date-picker-to-section input[aria-label='hour']"
    )
    private WebElement datePickerToHour;
    @FindBy(
            how = How.CSS,
            using = ".date-picker-to-section input[aria-label='minute']"
    )
    private WebElement datePickerToMunite;

    public bvdExplorerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30L);
    }

    public Map<String, String> login(String sUserName, String sPassword) {
        Map result;
        try {
            this.wait.until(ExpectedConditions.visibilityOf(this.button_idm_login));
            this.input_idm_username.clear();
            this.input_idm_username.sendKeys(new CharSequence[]{sUserName});
            this.input_idm_password.clear();
            this.input_idm_password.sendKeys(new CharSequence[]{sPassword});
            ((WebElement)this.wait.until(ExpectedConditions.elementToBeClickable(this.button_idm_login))).click();
            this.wait.until(ExpectedConditions.visibilityOf(this.mondrian_page));
            result = ResultStore.success("successfully logged in");
        } catch (Exception var5) {
            result = ResultStore.failure("cannot login. error: " + Misc.getStacktraceAsString(var5));
        }

        return result;
    }

    public Map<String, String> clickContext(String context) {
        try {
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("mondrian-page mondrian-test")));
            WebElement contextElem = this.driver.findElement(By.id(context));
            this.wait.until(ExpectedConditions.elementToBeClickable(contextElem));
            contextElem.click();
            return ResultStore.success("Context clicked successfully");
        } catch (Exception var3) {
            return ResultStore.failure("Context not clicked" + Misc.getExceptionInfo(var3));
        }
    }

    public Map<String, String> contextIsSelected(String context) {
        try {
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("mondrian-page mondrian-test")));
            List<WebElement> contextElem = this.driver.findElements(By.cssSelector("#" + context + " " + ".isRegistered"));
            return !contextElem.isEmpty() && contextElem.size() == 1 ? ResultStore.success("Context clicked successfully") : ResultStore.failure("Context not clicked");
        } catch (Exception var3) {
            return ResultStore.failure("Context not clicked" + Misc.getExceptionInfo(var3));
        }
    }

    public Map<String, String> contextTabsCount(int contextCount) {
        try {
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("mondrian-page mondrian-test")));
            List<WebElement> contextTabs = this.driver.findElements(By.cssSelector(".context-filter-content .tag-flat"));
            return contextTabs.size() == contextCount ? ResultStore.success("Context tabs are equal to " + contextCount) : ResultStore.failure("Context tabs found " + contextTabs.size() + " expected " + contextCount);
        } catch (Exception var3) {
            return ResultStore.failure("Context not clicked" + Misc.getExceptionInfo(var3));
        }
    }

    public Map<String, String> closeContextTab(int contextIndex) {
        try {
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("mondrian-page mondrian-test")));
            List<WebElement> contextTabs = this.driver.findElements(By.cssSelector(".context-filter-content .tag-flat .close"));
            ((WebElement)contextTabs.get(contextIndex)).click();
            return ResultStore.success("Context tab close clicked successfully");
        } catch (Exception var3) {
            return ResultStore.failure("Context tab close not clicked" + Misc.getExceptionInfo(var3));
        }
    }

    public Map<String, String> openDatePicker() {
        try {
            this.datePicker.click();
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("mondrian .context-filter-menu-open")));
            return ResultStore.success("Date picker opened successfully");
        } catch (Exception var2) {
            return ResultStore.failure("Date picker doesn't open " + Misc.getExceptionInfo(var2));
        }
    }

    public Map<String, String> closeDatePicker() {
        try {
            this.datePicker.click();
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("mondrian .context-filter-menu-open")));
            return ResultStore.success("Date picker opened successfully");
        } catch (Exception var2) {
            return ResultStore.failure("Date picker doesn't open " + Misc.getExceptionInfo(var2));
        }
    }

    public Map<String, String> setDateFrom(String day, String hour, String minute) {
        try {
            WebElement monthHeaderFrom = this.driver.findElement(By.cssSelector(".date-picker-from-section  ux-date-time-picker-header .header-title"));
            String monthFrom = monthHeaderFrom.getText().substring(0, 3).toLowerCase();
            monthFrom = monthFrom.substring(0, 1).toUpperCase() + monthFrom.substring(1);
            WebElement dateFromButton = this.driver.findElement(By.cssSelector(".date-picker-from-section button[aria-label='" + monthFrom + " " + day + ", 2018']"));
            TestUtils.sleep(1);
            dateFromButton.click();
            this.datePickerFromHour.click();
            this.datePickerFromHour.clear();
            this.datePickerFromHour.sendKeys(new CharSequence[]{hour});
            this.datePickerFromMunite.click();
            this.datePickerFromMunite.clear();
            this.datePickerFromMunite.sendKeys(new CharSequence[]{minute});
            return ResultStore.success("Date picker opened successfully");
        } catch (Exception var7) {
            return ResultStore.failure("Date picker doesn't open " + Misc.getExceptionInfo(var7));
        }
    }

    public Map<String, String> setDateTo(String day, String hour, String minute) {
        try {
            WebElement monthHeaderFrom = this.driver.findElement(By.cssSelector(".date-picker-to-section  ux-date-time-picker-header .header-title"));
            String monthFrom = monthHeaderFrom.getText().substring(0, 3).toLowerCase();
            monthFrom = monthFrom.substring(0, 1).toUpperCase() + monthFrom.substring(1);
            WebElement dateFromButton = this.driver.findElement(By.cssSelector(".date-picker-to-section button[aria-label='" + monthFrom + " " + day + ", 2018']"));
            TestUtils.sleep(1);
            dateFromButton.click();
            this.datePickerToHour.click();
            this.datePickerToHour.clear();
            this.datePickerToHour.sendKeys(new CharSequence[]{hour});
            this.datePickerToMunite.click();
            this.datePickerToMunite.clear();
            this.datePickerToMunite.sendKeys(new CharSequence[]{minute});
            return ResultStore.success("Date picker opened successfully");
        } catch (Exception var7) {
            return ResultStore.failure("Date picker doesn't open " + Misc.getExceptionInfo(var7));
        }
    }

    public Map<String, String> checkDateFromTo(String fromDate, String toDate) {
        try {
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("mondrian-page mondrian-test")));
            List<WebElement> datesList = this.driver.findElements(By.cssSelector(".mondrian-test-container .col-sm-8 label"));
            String dateFromStr = ((WebElement)datesList.get(0)).getText();
            String dateToStr = ((WebElement)datesList.get(1)).getText();
            return dateFromStr.contains(fromDate) && dateToStr.contains(toDate) ? ResultStore.success("Picked date shown correctly") : ResultStore.failure("Picked date is wrong displayed");
        } catch (Exception var6) {
            return ResultStore.failure("Picked date is wrong " + Misc.getExceptionInfo(var6));
        }
    }
}

