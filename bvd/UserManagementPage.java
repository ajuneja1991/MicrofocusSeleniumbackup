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

import java.util.Map;


public class UserManagementPage {

    final WebDriver driver;
    final WebDriverWait wait;

    // BVD CREATE NEW USER
    @FindBy(how = How.ID, using = "new_user")
    static WebElement button_NewUser;

    @FindBy(how = How.ID, using = "linkToCreateUserPage")
    static WebElement button_NewUserCreate;

    @FindBy(how = How.ID, using = "cancelEditorButton")
    static WebElement button_cancelUserCreate;

    // BVD CONFIRM DELETE USER
    @FindBy(how = How.ID, using = "OK")
    static WebElement button_ConfirmDeleteUser;


    public UserManagementPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
    }


    // OPEN CREATE NEW USER
    public Map<String, String> openCreateNewUser()
    {
        Map<String, String> result;

        try {
            wait.until(ExpectedConditions.elementToBeClickable(button_NewUser));
            button_NewUser.click();
            // synchronize
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cancel")));
            result = ResultStore.success("Dashboard successfully uploaded");
        }
        catch (Exception exc)
        {
            result = ResultStore.failure("Failed to upload Dashboards. error: " + Misc.getStacktraceAsString(exc));
        }

        return result;
    }

    // DELETE USER
    public Map<String, String> deleteUser(String sEmail)
    {
        Map<String, String> result;
        WebElement div_User = driver.findElement(By.id(sEmail));
        WebElement button_DeleteUser = driver.findElement(By.xpath("//div[@id='" + sEmail +"']//button[@tooltip='Delete']"));//("delete-" + sEmail));


        try {
            wait.until(ExpectedConditions.elementToBeClickable(div_User));
            div_User.click();
            wait.until(ExpectedConditions.elementToBeClickable(button_DeleteUser));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", button_DeleteUser);

            wait.until(ExpectedConditions.elementToBeClickable(button_ConfirmDeleteUser));
            button_ConfirmDeleteUser.click();

            // synchronize
            wait.until(ExpectedConditions.visibilityOf(button_NewUser));
            result = ResultStore.success("User successfully deleted");
        }
        catch (Exception exc)
        {
            result = ResultStore.failure("Failed to delete User. error: " + Misc.getStacktraceAsString(exc));
        }

        return result;
    }

    // OPEN EDIT NEW USER
    public Map<String, String> openEditUser(String sEmail)
    {
        Map<String, String> result;
        WebElement div_User = driver.findElement(By.id(sEmail));
        WebElement button_EditUser = div_User.findElement(By.xpath("//div[@id='" + sEmail +"']//button[@tooltip='Edit']"));//("edit-" + sEmail));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(div_User));
            div_User.click();
            wait.until(ExpectedConditions.elementToBeClickable(button_EditUser));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", button_EditUser);
            // synchronize
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cancel")));
            result = ResultStore.success("Dashboard successfully uploaded");
        }
        catch (Exception exc)
        {
            result = ResultStore.failure("Failed to upload Dashboards. error: " + Misc.getStacktraceAsString(exc));
        }

        return result;
    }
    public Map<String, String> checkUserManagementInFrame()
    {
        Map<String, String> result;

        try {
            //UiTestUtils.waitUntilPageIsLoaded(driver, wait);
            TestUtils.sleep(10);
            driver.switchTo().defaultContent();
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("bvd"));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentFrame"));
            TestUtils.sleepAndLog(2000);

            wait.until(ExpectedConditions.elementToBeClickable(button_NewUserCreate));
            button_NewUserCreate.click();
            // synchronize
            driver.switchTo().defaultContent();
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("bvd"));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentFrame"));
            TestUtils.sleepAndLog(2000);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cancelEditorButton")));
            if(button_cancelUserCreate.isDisplayed()){
                result = ResultStore.success("Create User Under Frame is working Fine");
            }
            /*Actions builder = new Actions(driver);
            builder.moveToElement(button_cancelUserCreate).click(button_cancelUserCreate).build().perform();*/

            result = ResultStore.success("User Page in Frame successfull");
        }
        catch (Exception exc)
        {
            result = ResultStore.failure("Failed to check UserPage in Frame. error: " + Misc.getStacktraceAsString(exc));
        }

        return result;
    }

}
