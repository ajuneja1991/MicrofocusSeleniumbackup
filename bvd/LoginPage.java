package com.hp.opr.qa.framework.pageobjects.bvd;



import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.hp.opr.qa.framework.utils.UiTestUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Map;


public class LoginPage
{
  final WebDriver driver;
  final WebDriverWait wait;
  final WebDriverWait shortwait;

  int timeout = 20;
  int shorttimeout = 1;

  // BVD
  @FindBy(how = How.ID, using = "user")
  private WebElement input_UserName;

  @FindBy(how = How.ID, using = "nav_dashboards_menu")
  private WebElement a_Dashboards;

  @FindBy(how=How.CSS,using="div[role='alert'][class*=alert]")
  static WebElement errormessage;

  @FindBy(how = How.ID, using = "password")
  private WebElement input_Password;

  @FindBy(how = How.CSS, using = "#loginButton,#submit_login")
  private WebElement button_Login;

  @FindBy(how = How.ID, using = "login_error")
  private WebElement div_loginError;

  // IDM
  @FindBy(how = How.ID, using = "username")
  private WebElement input_idm_username;

  @FindBy(how = How.ID, using = "password")
  private WebElement input_idm_password;

  @FindBy(how = How.ID, using = "submit")
  private WebElement button_idm_login;

  @FindBy(how = How.CSS, using = "div span#error-help-block")
  private WebElement div_idm_loginerror;


  public LoginPage(WebDriver driver)
  {
    this.wait = new WebDriverWait(driver, timeout);
    this.shortwait = new WebDriverWait(driver, shorttimeout);
    this.driver = driver;
  }


  public Map<String, String> login(String sUserName, String sPassword)
  {
    Map<String, String> result;
    WebDriverWait localWait = new WebDriverWait(driver, 40);
    if (getLoginType().equals("omi"))
    {
      try
      {
        localWait.until(ExpectedConditions.visibilityOf(button_Login));
        localWait.until(ExpectedConditions.elementToBeClickable(By.id("user")));
        input_UserName.clear();
        input_UserName.click();
//        input_UserName.sendKeys(Keys.chord(Keys.CONTROL, "a") + sUserName);
        input_UserName.sendKeys(sUserName);
        wait.until(ExpectedConditions.textToBePresentInElementValue(input_UserName, sUserName));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
        input_Password.sendKeys(sPassword);
        wait.until(ExpectedConditions.textToBePresentInElementValue(input_Password, sPassword));
        button_Login.click();

        // synchronize - wait until Home Button is there
        localWait.until(ExpectedConditions.elementToBeClickable(By.id("nav_default_dashboard")));
        localWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
        localWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[data-progress='99']")));

        result = ResultStore.success("successfully logged in");

      }
      catch (Exception exc)
      {
        try
        {
          Alert confirmationAlert = driver.switchTo().alert();
          String alertText = confirmationAlert.getText();
          confirmationAlert.dismiss();

          result = ResultStore.failure("Alert was present: " + alertText);
        }
        catch (Exception exc2)
        {

          result = ResultStore.failure("cannot login. error: " + Misc.getStacktraceAsString(exc));
        }
      }
    }
    else
    {
      try
      {
        localWait.until(ExpectedConditions.visibilityOf(button_idm_login));
        TestUtils.sleepAndLog(1000);
        input_idm_username.clear();
        input_idm_username.sendKeys(sUserName);
        input_idm_password.clear();
        input_idm_password.sendKeys(sPassword);
        localWait.until(ExpectedConditions.elementToBeClickable(button_idm_login)).click();

        // synchronize - wait until Home Button is there
        TestUtils.sleepAndLog(1000);
        localWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
        localWait.until(ExpectedConditions.elementToBeClickable(By.id("nav_default_dashboard")));
        localWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[data-progress='99']")));
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
      }
    }
    return result;
  }

  public Map<String, String> goToReLogin()
  {
    Map<String, String> result;
    {
      try
      {
        String id = "logout-link";
        wait.until(ExpectedConditions.elementToBeClickable(By.id(id))).click();
        id = "submit";
        wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
        result = ResultStore.success("Successfully opened Login Page");

      }
      catch (Exception e)
      {
        result = ResultStore.failure("Cannot open Login Page. error: " + Misc.getStacktraceAsString
                (e));
      }
    }
    return result;
  }

  public Map<String, String> loginGetAlert(String sUserName, String sPassword)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOf(button_Login));
      wait.until(ExpectedConditions.elementToBeClickable(By.id("user")));
      input_UserName.clear();
      input_UserName.sendKeys(sUserName);
      wait.until(ExpectedConditions.textToBePresentInElementValue(input_UserName, sUserName));
      wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
      input_Password.sendKeys(sPassword);
      wait.until(ExpectedConditions.textToBePresentInElementValue(input_Password, sPassword));
      button_Login.click();

      // synchronize - wait until Home Button is there
      TestUtils.sleepAndLog(2500);
      MainPage.a_Administration.click();
      MainPage.a_ManageDashboards.click();
//            wait.until(ExpectedConditions.elementToBeClickable(By.id("nav_default_dashboard")));

//            result = ResultStore.failure("Logged in without exception");
      throw new Exception("Logged in without exception");
    }
    catch (UnhandledAlertException alertException)
    {
//            WebDriverException
      String popupText = alertException.getAlertText();
      result = ResultStore.success(popupText);
    }
    catch (Exception exc)
    {
      try
      {
        Alert confirmationAlert = driver.switchTo().alert();
        String alertText = confirmationAlert.getText();
        confirmationAlert.dismiss();

        result = ResultStore.failure("Alert was present: " + alertText);
      }
      catch (Exception exc2)
      {

        result = ResultStore.failure("cannot login. error: " + Misc.getStacktraceAsString(exc));
      }

    }
    return result;
  }

  public Map<String, String> loginGetAlert1(String sUserName, String sPassword)
  {
    Map<String, String> result;
    if (getLoginType().equals("omi"))
    {

      try
      {
        wait.until(ExpectedConditions.visibilityOf(button_Login));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user")));
        input_UserName.clear();
        input_UserName.sendKeys(sUserName);
        wait.until(ExpectedConditions.textToBePresentInElementValue(input_UserName, sUserName));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
        input_Password.sendKeys(sPassword);
        wait.until(ExpectedConditions.textToBePresentInElementValue(input_Password, sPassword));
        button_Login.click();

        // synchronize - wait until Home Button is there
//            wait.until(ExpectedConditions.elementToBeClickable(By.id("nav_default_dashboard")));

//            result = ResultStore.failure("Logged in without exception");

        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();

//            String alertText = driver.switchTo().alert().getText();
//            driver.switchTo().alert().accept();
        result = ResultStore.success("Alert was present: " + alertText);
      }
      catch (Exception exc)
      {
        result = ResultStore.failure("cannot login. error: " + Misc.getStacktraceAsString(exc));
      }
    }
    else
    {
      try
      {
        wait.until(ExpectedConditions.elementToBeClickable(button_idm_login));
        TestUtils.sleepAndLog(1000);
        input_idm_username.clear();
        input_idm_username.sendKeys(sUserName);
        input_idm_password.clear();
        input_idm_password.sendKeys(sPassword);
        button_idm_login.click();

        // synchronize - wait until Home Button is there
//            wait.until(ExpectedConditions.elementToBeClickable(By.id("nav_default_dashboard")));

//            result = ResultStore.failure("Logged in without exception");

        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();

//            String alertText = driver.switchTo().alert().getText();
//            driver.switchTo().alert().accept();
        result = ResultStore.success("Alert was present: " + alertText);
      }
      catch (Exception exc)
      {
        result = ResultStore.failure("cannot login. error: " + Misc.getStacktraceAsString(exc));
      }
    }

    return result;
  }

  public Map<String, String> loginWithWrongCredentials(String sUserName, String sPassword)
  {

    Map<String, String> result;

    if (getLoginType().equals("omi"))
    {
      try
      {
        wait.until(ExpectedConditions.visibilityOf(button_Login));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user")));
        input_UserName.sendKeys(sUserName);
        wait.until(ExpectedConditions.textToBePresentInElementValue(input_UserName, sUserName));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
        input_Password.sendKeys(sPassword);
        wait.until(ExpectedConditions.textToBePresentInElementValue(input_Password, sPassword));
        button_Login.click();
        wait.until(ExpectedConditions.visibilityOf(div_loginError));

        result = ResultStore.success("expected login error shown");

      }
      catch (Exception exc)
      {

        result = ResultStore.failure("expected login error missing. error: " + Misc.getStacktraceAsString(exc));

      }
    }
    else
    {
      try
      {
        wait.until(ExpectedConditions.visibilityOf(button_idm_login));
        input_idm_username.clear();
        input_idm_username.sendKeys(sUserName);
        input_idm_password.clear();
        input_idm_password.sendKeys(sPassword);
        wait.until(ExpectedConditions.elementToBeClickable(button_idm_login)).click();
        TestUtils.sleepAndLog(500);
        wait.until(ExpectedConditions.visibilityOf(div_idm_loginerror));
        String getError=div_idm_loginerror.getText();
        Assert.assertEquals(getError,"Invalid user name/password or user account is locked or expired","expected login Error not shown");
        result = ResultStore.success("expected login error shown");
      }
      catch (Exception e)
      {
        result = ResultStore.failure("expected login error missing. error: " + Misc.getStacktraceAsString(e));
      }
    }
    return result;
  }

  public Map<String, String> directAccess(String URL, String sUserName, String sPassword)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOf(button_Login));
      driver.get(URL + "/" + sUserName + "/" + sPassword);

      // not working :(
      //UiTestUtils.waitUntilPageIsLoadedAndElementIsClickable(wait, By.id("load-spinner"), By.id("nav_default_dashboard"));

      // synchronize - wait until Home Button is there
      wait.until(ExpectedConditions.elementToBeClickable(By.id("nav_default_dashboard")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      result = ResultStore.success("successfully logged in");

    }
    catch (Exception exc)
    {
      try
      {
        Alert confirmationAlert = driver.switchTo().alert();
        String alertText = confirmationAlert.getText();
        confirmationAlert.dismiss();

        result = ResultStore.failure("Alert was present: " + alertText);
      }
      catch (Exception exc2)
      {

        result = ResultStore.failure("cannot login. error: " + Misc.getStacktraceAsString(exc));
      }

    }
    return result;
  }

  public Map<String, String> directAccessWithWrongCredentials(String URL, String sUserName, String sPassword)
  {

    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOf(button_Login));
      driver.get(URL + "/" + sUserName + "/" + sPassword);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login_error")));

      result = ResultStore.success("expected login error shown");

    }
    catch (Exception exc)
    {

      result = ResultStore.failure("expected login error missing. error: " + Misc.getStacktraceAsString(exc));

    }
    return result;
  }

  private String getLoginType()
  {
    int i = 0;
    String logintype = "omi";

    while (i < 40)
    {
      try
      {
        shortwait.until(ExpectedConditions.visibilityOf(button_Login));
        break;
      }
      catch (Exception e1)
      {
        try
        {
          shortwait.until(ExpectedConditions.visibilityOf(button_idm_login));
          logintype = "idm";
          break;
        }
        catch (Exception e2)
        {
          i++;
        }
      }
    }
    return logintype;
  }

  //public Map<String, String> bvd_Direct_Access_LoggedIn_Check()
  public Map<String, String> idm_LoggedIn_Check()
  {
    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(By.id("nav_default_dashboard")));
      //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[data-progress='99']")));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button#nav_default_dashboard")));
      //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[role='alert'][class*=alert]:not([class*='ng-hide'])")));

      Boolean isPresent = driver.findElements(By.cssSelector("div[role='alert'][class*=alert]:not([class*='ng-hide'])")).size() > 0;
      if(isPresent) {
        String str=driver.findElement(By.cssSelector("div[role='alert'][class*=alert]:not([class*='ng-hide'])")).getText();
        Assert.assertEquals(str, "Error: Connection \"default\" does not exist.");
      }
      return ResultStore.success("successfully logged in");
    }
    catch (Exception exc)
    {
      return ResultStore.failure("Error message: StackTrace " + Misc.getStacktraceAsString(exc));
    }
  }

  public Map<String, String> idm_LoggedIn_Checkfornonadmin()
  {
    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(By.id("nav_default_dashboard")));
      //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[data-progress='99']")));
      TestUtils.sleepAndLog(15000);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button#nav_default_dashboard")));
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[role='alert'][class*=alert]:not([class*='ng-hide'])")));

      return ResultStore.success("successfully logged in");
    }
    catch (Exception exc)
    {
      return ResultStore.failure("Error message: StackTrace " + Misc.getStacktraceAsString(exc));
    }
  }



  public Map<String, String> idm_LoggedIn_CheckforViewNotAssigned()
  {
    try
    {
      wait.until(ExpectedConditions.elementToBeClickable(By.id("nav_default_dashboard")));
      //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[data-progress='99']")));
      TestUtils.sleepAndLog(15000);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='alert'][class*=alert]:not([class*='ng-hide'])")));
      return ResultStore.success("successfully logged in");
    }
    catch (Exception exc)
    {
      return ResultStore.failure("Error message: StackTrace " + Misc.getStacktraceAsString(exc));
    }
  }
  public Map<String, String> checkFrame()
  {
    Map<String, String> result;
    try
    {
      UiTestUtils.waitPageIsLoaded(driver, 5);
      TestUtils.sleep(5);
      wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("bvd"));

      //wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user")));
      //input_UserName.sendKeys("username");
      wait.until(ExpectedConditions.elementToBeClickable(a_Dashboards));
      if(a_Dashboards.isDisplayed()){
        return ResultStore.success("successfully checked into main page frame");
      }else{
        return ResultStore.failure("unable to checkin to Main Page Frame");
      }
    }
    catch (Exception exc)
    {
      return ResultStore.failure("Frame check failed. Error message: StackTrace " + Misc
              .getStacktraceAsString(exc));
    }
  }

}