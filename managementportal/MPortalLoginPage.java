package com.hp.opr.qa.framework.pageobjects.managementportal;

import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.utils.TestUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

public class MPortalLoginPage {

    final WebDriver driver;
    final WebDriverWait wait;
    final WebDriverWait shortwait;
    @FindBy(how = How.CSS, using = "#logout-link")
    static WebElement relogin;

    @FindBy(how = How.ID, using = "password")
    private WebElement input_idm_password;

    @FindBy(how = How.ID, using = "submit")
    private WebElement button_idm_login;

    @FindBy(how = How.ID, using = "username")
    private WebElement input_idm_username;

    @FindBy(how = How.XPATH, using = "//span[.='APPLICATION']")
    private WebElement span_application;

    int timeout = 15;
    int shorttimeout = 1;

    public MPortalLoginPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, timeout);
        this.shortwait = new WebDriverWait(driver, shorttimeout);
        this.driver = driver;
    }

    @FindBy(how = How.XPATH, using = "//span[.='Users']")
    private WebElement span_userbutton;

    public Map<String, String> login(String sUserName, String sPassword)
    {
        Map<String, String> result;
            try
            {
                wait.until(ExpectedConditions.visibilityOf(button_idm_login));
                TestUtils.sleepAndLog(1000);
                input_idm_username.clear();
                input_idm_username.sendKeys(sUserName);
                input_idm_password.clear();
                input_idm_password.sendKeys(sPassword);
                wait.until(ExpectedConditions.elementToBeClickable(button_idm_login)).click();
                TestUtils.sleep(10);
                // synchronize - wait until Home Button is there
                wait.until(ExpectedConditions.visibilityOf(span_application));
                result = ResultStore.success("successfully logged in");
            }
            catch (Exception exc)
            {
                try
                {
                    Alert confirmationAlert = driver.switchTo().alert();
                    String alertText = confirmationAlert.getText();
                    confirmationAlert.dismiss();

                    result = ResultStore.success("Alert was present: " + alertText);
                }
                catch (Exception exc2)
                {

                    result = ResultStore.failure("cannot login. error: " + Misc.getStacktraceAsString(exc));
                }
            }return result;
            }
}
