package com.hp.opr.qa.framework.pageobjects.bvd;


import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.Map;


public class MyAccountPage
{

  final WebDriver driver;
  final WebDriverWait wait;


  // BVD SUCCESSFULLY CHANGED MESSAGE
  @FindBy(how = How.CSS, using = "slide-my-account #success")
  static WebElement div_success;


  //MyAccount


  @FindBy(how = How.ID, using = "email-input_email")
  static WebElement emailField;

  @FindBy(how = How.ID, using = "name-input")
  static WebElement nameField;

  @FindBy(how = How.ID, using = "oldPassword-input")
  static WebElement oldPasswordField;

  @FindBy(how = How.ID, using = "newPassword-input")
  static WebElement newPasswordField;

  @FindBy(how = How.ID, using = "confirmPassword-input")
  static WebElement confirmNewPasswordField;

  @FindBy(how = How.CSS, using = "slide-my-account #buttonSaveSetting")
  static WebElement button_ApplyChanges;

  @FindBy(how = How.CSS, using = "slide-my-account [ng-click='$ctrl.close()']")
  static WebElement closeMyAccountpanel;

  @FindBy(how = How.CSS, using = ".form-group .input-error")
  static WebElement enteroldpwderrortoEnterNewPwd;


  @FindBy(how = How.CSS, using = ".form-group .alert")
  static WebElement newpwdrulesValidation;


  @FindBy(how = How.CSS, using = ".form-group .alert")
  static  WebElement newpwdemptyAlert;

  @FindBy(how = How.ID, using = "buttonCancelSetting")
  static  WebElement cancelbutton;

  public MyAccountPage(WebDriver driver)
  {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, 15);
  }


  public void typingText(WebElement element, String value)
  {

    for (int i = 0; i < value.length(); i++)
    {
      char c = value.charAt(i);
      String s = new StringBuilder().append(c).toString();
      TestUtils.sleepAndLog(50);
      element.sendKeys(s);
    }
  }


  public Map<String, String> closeMyAccountPannel()
  {
    Map<String, String> result = ResultStore.getResultObj();

    try
    {
      wait.until(ExpectedConditions.visibilityOf(closeMyAccountpanel));
      closeMyAccountpanel.click();
      result = ResultStore.success("My Account panel successfully closed");
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to close My Account panel. error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }


  // UPLOAD DASHBOARD
  public Map<String, String> changePassword(String sOldPassword, String sNewPassword)
  {
    Map<String, String> result = ResultStore.getResultObj();

    try
    {
      wait.until(ExpectedConditions.visibilityOf(oldPasswordField));
      oldPasswordField.clear();
      typingText(oldPasswordField, sOldPassword);
//            oldPasswordField.sendKeys(sOldPassword);
      wait.until(ExpectedConditions.visibilityOf(newPasswordField));
      newPasswordField.clear();
      typingText(newPasswordField, sNewPassword);
//            newPasswordField.sendKeys(sNewPassword);
      wait.until(ExpectedConditions.visibilityOf(confirmNewPasswordField));
      confirmNewPasswordField.clear();
      typingText(confirmNewPasswordField, sNewPassword);
//            confirmNewPasswordField.sendKeys(sNewPassword);
      wait.until(ExpectedConditions.visibilityOf(button_ApplyChanges));
      wait.until(ExpectedConditions.elementToBeClickable(button_ApplyChanges));
      button_ApplyChanges.click();
      // synchronize
      wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By
              .cssSelector
                      ("opr-slide-panel-header hpe-padding-md hpe-padding-box"))));
      result = ResultStore.success("Password successfully changed");


    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to change the password. error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }


  public Map<String, String> changePasswordAndCheckWrongOld(String sOldPassword, String
          sNewPassword)
  {
    Map<String, String> result = ResultStore.getResultObj();

    try
    {
      wait.until(ExpectedConditions.visibilityOf(oldPasswordField));
      oldPasswordField.clear();
      typingText(oldPasswordField, sOldPassword);
//            oldPasswordField.sendKeys(sOldPassword);
      wait.until(ExpectedConditions.visibilityOf(newPasswordField));
      newPasswordField.clear();
      typingText(newPasswordField, sNewPassword);
//            newPasswordField.sendKeys(sNewPassword);
      wait.until(ExpectedConditions.visibilityOf(confirmNewPasswordField));
      confirmNewPasswordField.clear();
      typingText(confirmNewPasswordField, sNewPassword);
//            confirmNewPasswordField.sendKeys(sNewPassword);
      wait.until(ExpectedConditions.visibilityOf(button_ApplyChanges));
      wait.until(ExpectedConditions.elementToBeClickable(button_ApplyChanges));
      button_ApplyChanges.click();

      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.alert-error[ng-if*=wrongOldPassword]")));
      // synchronize

      result = ResultStore.success("Wrong Password successfully checked");


    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to check the wrong password. error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> changePasswordAndCheckWrongOldWithValidations(String sOldPassword, String
          sNewPassword)
  {
    Map<String, String> result = ResultStore.getResultObj();

    try
    {
      wait.until(ExpectedConditions.visibilityOf(oldPasswordField));
      oldPasswordField.clear();
      typingText(oldPasswordField, "abc");

      wait.until(ExpectedConditions.visibilityOfAllElements(enteroldpwderrortoEnterNewPwd));
      List<WebElement> errorsbeforeEnteringNewPwd=driver.findElements(By.cssSelector(".form-group .input-error"));
      Assert.assertEquals(errorsbeforeEnteringNewPwd.size(),2,"Errors before entering New Password not working Correctly");

//            oldPasswordField.sendKeys(sOldPassword);
      wait.until(ExpectedConditions.visibilityOf(newPasswordField));
      newPasswordField.clear();
      typingText(newPasswordField, "abcde");
      wait.until(ExpectedConditions.visibilityOf(newpwdrulesValidation));
      String errorMessage=newpwdrulesValidation.getText();
      String expectedError="Must be 8 characters, contain at least one lower-case, one upper-case, one special character, and one digit";
      boolean sboolean=false;
      if(errorMessage.contains(expectedError)){
        sboolean=true;
      }
      Assert.assertTrue(sboolean,"New Password rules Validation not successfull");
      newPasswordField.clear();

      wait.until(ExpectedConditions.visibilityOf(newpwdemptyAlert));
      String newPwdEmptyAlertactual=newpwdemptyAlert.getText();
      String newPwdEmptyAlertExpected="Please enter a value on this field";
      boolean sbooleanempty=false;
      if(newPwdEmptyAlertactual.contains(newPwdEmptyAlertExpected)){
        sbooleanempty=true;
      }
      Assert.assertTrue(sbooleanempty,"New Password empty alert Validation not successfull");

      wait.until(ExpectedConditions.visibilityOf(cancelbutton));
      wait.until(ExpectedConditions.elementToBeClickable(cancelbutton));
      cancelbutton.click();

      result = ResultStore.success("All Password Validations were successfull");


    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to check the wrong password. error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> changeEmail(String sNewEmail)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOf(emailField));
      emailField.clear();
      typingText(emailField, sNewEmail);
//            emailField.sendKeys(sNewEmail);
      wait.until(ExpectedConditions.visibilityOf(button_ApplyChanges));
      wait.until(ExpectedConditions.elementToBeClickable(button_ApplyChanges));
      button_ApplyChanges.click();
      // synchronize
      wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By
              .cssSelector
                      ("opr-slide-panel-header hpe-padding-md hpe-padding-box"))));

      result = ResultStore.success("Email successfully changed");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to change an email. error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> changeName(String sNewName)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOf(nameField));
      TestUtils.sleep(1);
      nameField.clear();
      typingText(nameField, sNewName);
//            nameField.sendKeys(sNewName);
      wait.until(ExpectedConditions.visibilityOf(button_ApplyChanges));
      button_ApplyChanges.click();
      // synchronize
      wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By
              .cssSelector
                      ("opr-slide-panel-header hpe-padding-md hpe-padding-box"))));

      result = ResultStore.success("Name successfully changed");

    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to change the name. error: " + Misc
              .getStacktraceAsString(exc));
    }

    return result;
  }

  public Map<String, String> getEmail(String mail)
  {
    Map<String, String> result;

    try
    {
      wait.until(ExpectedConditions.visibilityOf(emailField));
      wait.until(ExpectedConditions.attributeToBe(emailField, "value", mail));
      String sEmail = emailField.getAttribute("value");
      result = ResultStore.success(sEmail);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Email. error: " + Misc.getStacktraceAsString
              (exc));
    }

    return result;
  }

  public Map<String, String> getName(String name)
  {
    Map<String, String> result = ResultStore.getResultObj();

    try
    {
      wait.until(ExpectedConditions.visibilityOf(nameField));
      wait.until(ExpectedConditions.attributeToBe(nameField, "value", name));
      String sName = nameField.getAttribute("value");
      result = ResultStore.success(sName);
    } catch (Exception exc)
    {
      result = ResultStore.failure("Failed to get Name. error: " + Misc.getStacktraceAsString(exc));
    }

    return result;
  }
}
