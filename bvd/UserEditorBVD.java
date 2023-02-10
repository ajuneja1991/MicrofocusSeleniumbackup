package com.hp.opr.qa.framework.pageobjects.bvd;


import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.hp.opr.qa.framework.utils.UiTestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UserEditorBVD {
    final WebDriver driver;
    final WebDriverWait wait;
    @FindBy(
            how = How.NAME,
            using = "userName"
    )
    static WebElement input_userName;

    @FindBy(
            how=How.CSS,
            using="#rolesSection #controls div[name='userRolesField'] span[placeholder='Add role ...'] li span"
    )static WebElement assignedroletoUser;
    @FindBy(
            how = How.NAME,
            using = "userLogin"
    )
    static WebElement input_userLogin;
    @FindBy(
            how = How.NAME,
            using = "passwordin"
    )
    static WebElement input_newPassword;
    @FindBy(
            how = How.NAME,
            using = "confirmPassword"
    )
    static WebElement input_confirmPassword;
    /*@FindBy(
            how = How.CSS,
            using = "a[ng-bind*='opr.userMgmt.users.password.change']"
    )
    static WebElement a_changePassword;*/
    @FindBy(
            how = How.LINK_TEXT,
            using = "Change password"
    )
    static WebElement a_changePassword;
    /*@FindBy(
            how = How.NAME,
            using = "userEmail"
    )
    static WebElement input_email;*/
    @FindBy(
            how = How.ID,
            using = "saveUserButton"
    )
    static WebElement input_saveUser;
    @FindBy(
            how = How.ID,
            using = "cancelEditorButton"
    )
    static WebElement input_cancelUser;
    @FindBy(
            how = How.CLASS_NAME,
            using = "opr-form-item-line"
    )
    static WebElement a_userError;
    @FindBy(
            how = How.ID,
            using = "checkboxLdapUser"
    )
    static WebElement input_ldapFlag;
    /*@FindBy(
            how = How.NAME,
            using = "checkboxSuperAdmin"
    )
    static WebElement input_superAdmin;*/
    @FindBy(
            how = How.CSS,
            using = "input[name='checkboxSuperAdmin']~span"
    )
    static WebElement input_superAdmin;

    @FindBy(
            how = How.CSS,
            using = "input[name='checkboxSuperAdmin']"
    )
    static WebElement input_superAdminchecked;

    @FindBy(
            how = How.CSS,
            using = "section#groupsSection input"
    )
    static WebElement input_addGroup;
    @FindBy(
            how = How.XPATH,
            using = ".//*[@id='rolesSection']//label[@title='Assigned roles']/..//input"
    )
    static WebElement input_addRole;

    public UserEditorBVD(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30L);
    }

    public void typingText(WebElement element, String value, int sleepTime) {
        for(int i = 0; i < value.length(); ++i) {
            char c = value.charAt(i);
            String s = "" + c;
            TestUtils.sleepAndLog((long)sleepTime);
            element.sendKeys(new CharSequence[]{s});
        }

    }

    private void switchToTargetFrame() {
        this.wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentFrame"));
        this.wait.until(ExpectedConditions.presenceOfElementLocated(By.id("saveUserButton")));
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cancelEditorButton")));
    }

    private void setUserName(String name) {
        this.wait.until(ExpectedConditions.elementToBeClickable(By.name("userName")));
        input_userName.clear();
        input_userName.sendKeys(new CharSequence[]{Keys.chord(new CharSequence[]{Keys.CONTROL, "a"}) + name});
        this.wait.until(ExpectedConditions.textToBePresentInElementValue(input_userName, name));
    }

    private void setLogin(String login) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("userLogin")));
        input_userLogin.sendKeys(new CharSequence[]{Keys.chord(new CharSequence[]{Keys.CONTROL, "a"}) + login});
        this.wait.until(ExpectedConditions.textToBePresentInElementValue(input_userLogin, login));
    }

    private void setGroup(String group) {
        input_ldapFlag.sendKeys(new CharSequence[]{Keys.TAB, group, Keys.ENTER});
    }

    private void setPassword(String pwd) {
        wait.until(ExpectedConditions.visibilityOf(input_newPassword));
        input_newPassword.sendKeys(new CharSequence[]{Keys.chord(new CharSequence[]{Keys.CONTROL, "a"}) + pwd});
        input_confirmPassword.sendKeys(new CharSequence[]{Keys.chord(new CharSequence[]{Keys.CONTROL, "a"}) + pwd});
    }

    private void changePassword(String newPwd) {
        wait.until(ExpectedConditions.elementToBeClickable(a_changePassword));
        Actions chngpwd=new Actions(driver);
        chngpwd.moveToElement(a_changePassword).click(a_changePassword).build().perform();
        //a_changePassword.click();
        this.setPassword(newPwd);
    }

    /*private void setEmail(String email) {
        input_email.clear();
        input_email.sendKeys(new CharSequence[]{Keys.chord(new CharSequence[]{Keys.CONTROL, "a"}) + email});
    }*/

    private void setTimeZone(String timezone) {
        //input_email.sendKeys(new CharSequence[]{Keys.TAB});
        WebElement a_timeZone = this.driver.switchTo().activeElement();
        a_timeZone.sendKeys(new CharSequence[]{Keys.ENTER});
        WebElement txt_timeZone = this.driver.findElement(By.className("ui-select-search"));
        txt_timeZone.sendKeys(new CharSequence[]{timezone, Keys.ENTER});
    }

    private void setSuperAdmin(boolean bSuperAdmin) {
        if(bSuperAdmin==true){
            wait.until(ExpectedConditions.visibilityOf(input_superAdmin));
            input_superAdmin.click();
        }
        if(bSuperAdmin==true){
            wait.until(ExpectedConditions.attributeContains(input_superAdminchecked,"class","ng-not-empty"));
            this.wait.until(ExpectedConditions.elementToBeClickable(input_saveUser));
        }else{
            wait.until(ExpectedConditions.attributeContains(input_superAdminchecked,"class","ng-empty"));
            this.wait.until(ExpectedConditions.elementToBeClickable(input_saveUser));
        }

    }

    public Map<String, String> createUser(String name, String login, String pwd, String timeZone, Boolean superAdmin) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_userLogin));
            this.setUserName(name);
            this.setLogin(login);
            //this.setEmail(email);
            this.setTimeZone(timeZone);
            this.setPassword(pwd);
            this.setSuperAdmin(superAdmin);
            input_saveUser.click();
            result.put("out", "User successfully created");
            result.put("err", "");
            result.put("rc", "0");
        } catch (Exception var9) {
            result.put("out", "");
            result.put("err", "Failed to create user. error: " + Misc.getStacktraceAsString(var9));
            result.put("rc", "1");
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> createUser(String name, String login, String pwd, Boolean superAdmin) {
        Map result;
        try {
            UiTestUtils.waitPageIsLoaded(this.driver, 30);
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_userLogin));
            this.setUserName(name);
            this.setLogin(login);
            //this.setEmail(email);
            this.setPassword(pwd);
            this.setSuperAdmin(superAdmin);
            input_saveUser.click();
            result = ResultStore.success("User successfully created");
        } catch (Exception var8) {
            result = ResultStore.failure("Failed to create user. error: " + Misc.getStacktraceAsString(var8));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> editUser(String newName, String newTimeZone, String newPwd) {
        Map result;
        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_userLogin));
            this.setUserName(newName);
            //this.setEmail(newEmail);
            this.setTimeZone(newTimeZone);
            this.changePassword(newPwd);
            this.wait.until(ExpectedConditions.visibilityOf(input_saveUser));
            input_saveUser.click();
            result = ResultStore.success("User successfully edited user from the list");
        } catch (Exception var7) {
            result = ResultStore.failure("Failed to edit user. error: " + var7.getMessage());
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> editUser(String newName, String newPwd) {
        Map result;
        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_saveUser));
            this.setUserName(newName);
            //this.setEmail(newEmail);
            this.changePassword(newPwd);
            TestUtils.sleep(2);
            input_saveUser.click();
            result = ResultStore.success("User successfully edited user from the list");
        } catch (Exception var6) {
            result = ResultStore.failure("Failed to edit user. error: " + Misc.getStacktraceAsString(var6));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> createLDAPUser(String name, String login, String ldapGroupName) {
        Map result;
        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_saveUser));
            this.setUserName(name);
            this.setLogin(login);
            input_ldapFlag.click();
            this.setGroup(ldapGroupName);
            input_saveUser.click();
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
            result = ResultStore.success("User successfully created");
        } catch (Exception var6) {
            result = ResultStore.failure("Failed to create user. error: " + Misc.getStacktraceAsString(var6));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> createDuplicateUser(String newName, String newLogin, String newPwd) {
        Map result;
        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_userLogin));
            this.setUserName(newName);
            this.setLogin(newLogin);
            this.setPassword(newPwd);
            this.wait.until(ExpectedConditions.visibilityOf(a_userError));
            result = ResultStore.success("Duplication error correctly displayed");
        } catch (Exception var6) {
            result = ResultStore.failure("Failed to display useful error: " + Misc.getStacktraceAsString(var6));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> cancelCreateUser() {
        Map result;
        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_cancelUser));
            input_cancelUser.click();
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("frameworkLoadingSpinner")));
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("saveGroupButton")));
            result = ResultStore.success("Cancelation of user creation was successful");
        } catch (Exception var3) {
            result = ResultStore.failure("Failed to cancel user creation: " + Misc.getStacktraceAsString(var3));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> assignGroupToUser(String sGroupName) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            TestUtils.sleepAndLog(500L);
            this.wait.until(ExpectedConditions.visibilityOf(input_addGroup));
            input_addGroup.sendKeys(new CharSequence[]{sGroupName + Keys.ENTER});
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + sGroupName + "')]")));
            input_saveUser.click();
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#footerContainer > input.btn.btn-primary.btn-large")));
            result.put("out", "Successfully added user to group");
            result.put("err", "");
            result.put("rc", "0");
        } catch (Exception var4) {
            result.put("out", "");
            result.put("err", "Failed to add user to group. error: " + Misc.getStacktraceAsString(var4));
            result.put("rc", "1");
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> assignRoleToUser(String roleName) {
        Map result;
        try {
            this.switchToTargetFrame();
            TestUtils.sleepAndLog(500L);
            this.wait.until(ExpectedConditions.visibilityOf(input_addRole));
            input_addRole.sendKeys(new CharSequence[]{roleName + Keys.ENTER});
            //String xpath = ".//*[@id='rolesSection']//label[@title='Assigned roles']/..//li/div[contains(text(),'" + roleName + "')]";
            String xpath="#rolesSection #controls div[name='userRolesField'] span[placeholder='Add role ...'] li span";

            this.wait.until(ExpectedConditions.textToBePresentInElement(assignedroletoUser,roleName));
            //this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

            input_saveUser.click();
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#footerContainer > input.btn.btn-primary.btn-large")));
            result = ResultStore.success("Role assigned successfully");
        } catch (Exception var4) {
            result = ResultStore.failure("Assign role failed" + Misc.getStacktraceAsString(var4));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }


    public Map<String, String> getInheritedRoles() {
        Map result;
        try {
            this.switchToTargetFrame();
            //String xpath = ".//section[contains(@id,'rolesSection')]//div//label[contains(text(),'Inherited roles:')]/../div/div/ul/li/div";
            String xpath="#rolesSection label[title='Inherited roles']~#controls li span";
            List<WebElement> roles = this.driver.findElements(By.cssSelector(xpath));
            String toResult = "";

            WebElement el;
            for(Iterator var6 = roles.iterator(); var6.hasNext(); toResult = toResult.concat(el.getText() + " ")) {
                el = (WebElement)var6.next();
            }

            result = ResultStore.success(toResult);
        } catch (Exception var7) {
            result = ResultStore.failure("Failed to get inherited roles");
        }

        this.driver.switchTo().defaultContent();
        return result;
    }
}

