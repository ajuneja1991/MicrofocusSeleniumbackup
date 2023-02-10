package com.hp.opr.qa.framework.pageobjects.managementportal;

import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.utils.UiTestUtils;
import com.mf.opr.qa.framework.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

public class MPortalMainPage {

    final WebDriver driver;
    final WebDriverWait wait;
    final WebDriverWait shortwait;

    int timeout = 15;
    int shorttimeout = 1;

    // BVD
    @FindBy(how = How.XPATH, using = "//span[.='IdM Administration']")
    private WebElement span_idmadministration;

    @FindBy(how = How.XPATH, using = "//span[@class='hpe-icon hpe-add']")
    private WebElement createUseraddbtn;

    @FindBy(how = How.XPATH, using = "//button/span[text()='admin']")
    private WebElement adminbtnforLogout;

    @FindBy(how = How.XPATH, using = "//button[text()='Logout']")
    private WebElement logoutbtn;

    @FindBy(how = How.XPATH, using = "//span[.='APPLICATION']")
    private WebElement span_application;

    @FindBy(how = How.XPATH, using = "//div[text()='Operations Bridge']")
    private WebElement div_OperationBridge;

    @FindBy(how = How.CSS, using = "#logout-link")
    static WebElement relogin;

    @FindBy(how = How.ID, using = "submit")
    private WebElement button_idm_login;

    @FindBy(how = How.ID, using = "username")
    private WebElement input_idm_username;

    public MPortalMainPage(WebDriver driver)
    {
        this.wait = new WebDriverWait(driver, timeout);
        this.shortwait = new WebDriverWait(driver, shorttimeout);
        this.driver = driver;
    }



    public Map<String, String> clickonOperationsBridge(){
        Map<String, String> result;
        try{
            TestUtils.sleep(10);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='APPLICATION']")));
            span_application.click();
            wait.until(ExpectedConditions.visibilityOf(span_idmadministration));
            span_idmadministration.click();
            UiTestUtils.waitUntilPageIsLoaded(driver, wait);
            (new WebDriverWait(driver, 5)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("idmAdmin")));
            Actions act1=new Actions(driver);
            act1.moveToElement(div_OperationBridge).click(div_OperationBridge).build().perform();
            result = ResultStore.success("successfully clicked onOperationBridge");
        }catch(Exception exc){
            result = ResultStore.failure("cannot login. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }
    public Map<String, String> logout() {
        Map<String, String> result;
        try {
            wait.until(ExpectedConditions.visibilityOf(adminbtnforLogout));
            adminbtnforLogout.click();
            wait.until(ExpectedConditions.visibilityOf(logoutbtn));
            logoutbtn.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#logout-link")));
            relogin.click();
            wait.until(ExpectedConditions.visibilityOf(button_idm_login));
            TestUtils.sleepAndLog(3000);
            input_idm_username.clear();
            result = ResultStore.success("Successfully logged out of management portal");


        }catch (Exception exc)
        {
            result = ResultStore.failure("Failed to log out. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

}
