package com.hp.opr.qa.framework.pageobjects.bvdExplorer;

import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.utils.TestUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class ExploreLoginPage {

    final WebDriver driver;
    final WebDriverWait wait;

    @FindBy(how = How.ID, using = "username")
    private WebElement input_UserName;

    @FindBy(how = How.ID, using = "password")
    private WebElement input_Password;

    @FindBy(how = How.ID, using = "submit")
    private WebElement button_Login;

    @FindBy(how = How.CSS, using = "span.mondrianBreadcrumbItem")
    private WebElement startpage_title;

    /*@FindBy(how = How.ID, using = "login_error")
    static WebElement loginError;*/

    @FindBy(how = How.CSS, using = "div.alert")
    private WebElement loginError;


    @FindBy(how = How.ID, using = "host_list")
    static WebElement  contextHost;

    public ExploreLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);


    }


    public Map<String, String> loginBvdExlpore(String sUserName, String sPassword) {
        Map<String, String> result;



        try {
            wait.until(ExpectedConditions.visibilityOf(input_UserName));
            input_UserName.sendKeys(sUserName);
            input_Password.sendKeys(sPassword);
            button_Login.click();
            wait.until(ExpectedConditions.visibilityOf(startpage_title));
           result = ResultStore.success(" successfully Logged Into BVD Explore");
        } catch (Exception exc) {



            result = ResultStore.failure("Failed to Login. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }


    public Map<String, String> loginIntoBvdExlpore(String user, String passwd){
        Map<String, String> result;

        try {

            WebDriverWait localWait = new WebDriverWait(driver, 50);
            localWait.until(ExpectedConditions.visibilityOf(button_Login));
            localWait.until(ExpectedConditions.elementToBeClickable(input_UserName));
            input_UserName.clear();
            input_Password.clear();
            input_UserName.sendKeys(user);
            input_Password.sendKeys(passwd);
            button_Login.click();
            localWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
            //localWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[data-progress='99']")));
            localWait.until(ExpectedConditions.visibilityOf(contextHost));
            result = ResultStore.success(" successfully Logged In");
        }
        catch(Exception exc){

            result = ResultStore.failure("Failed to Login. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    public Map<String, String> explore_LoggedIn_Check()
    {
        try
        {
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span.mondrianBreadcrumbItem")));
            return ResultStore.success("successfully logged in");
        }
        catch (Exception exc)
        {
            return ResultStore.failure("Error message: StackTrace " + Misc.getStacktraceAsString(exc));
        }
    }

    public Map<String, String> loginWithWrongCredentials(String sUserName, String sPassword)
    {
        Map<String, String> result;

            try
            {
                //wait.until(ExpectedConditions.visibilityOf(button_Login));
                wait.until(ExpectedConditions.elementToBeClickable(input_UserName));
                input_UserName.sendKeys(sUserName);
                wait.until(ExpectedConditions.textToBePresentInElementValue(input_UserName, sUserName));
                wait.until(ExpectedConditions.elementToBeClickable(input_Password));
                input_Password.sendKeys(sPassword);
                wait.until(ExpectedConditions.textToBePresentInElementValue(input_Password, sPassword));
                button_Login.click();
                wait.until(ExpectedConditions.visibilityOf(loginError));
                Assert.assertEquals(loginError.getText(),"Invalid user name/password or user account is locked or expired");
                result = ResultStore.success("expected login error shown");

            }
            catch (Exception exc)
            {

                result = ResultStore.failure("expected login error missing. error: " + Misc.getStacktraceAsString(exc));

            }

        return result;
    }
}
