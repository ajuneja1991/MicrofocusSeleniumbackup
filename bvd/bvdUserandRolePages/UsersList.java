package com.hp.opr.qa.framework.pageobjects.bvd.bvdUserandRolePages;


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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UsersList {
    final WebDriver driver;
    final WebDriverWait wait;
    @FindBy(
            how = How.ID,
            using = "btnCreateUser"
    )
    static WebElement a_newUser;
    @FindBy(
            how = How.ID,
            using = "filterUsers"
    )
    static WebElement input_filterUser;
    @FindBy(
            how = How.ID,
            using = "generalSection"
    )
    WebElement sec_details;
    @FindBy(
            how = How.ID,
            using = "btnDeleteUsers"
    )
    static WebElement a_deleteUsers;
    @FindBy(
            how = How.ID,
            using = "btnEditUser"
    )
    static WebElement a_editUser;
    @FindBy(
            how = How.ID,
            using = "btnDialogSubmit"
    )
    static WebElement button_btnDialogSubmit;
    @FindBy(
            how = How.CLASS_NAME,
            using = "oas-list-user-name"
    )
    public List<WebElement> allUsers;
    By btnSubmit = By.id("btnDialogSubmit");
    By btnCreate = By.id("btnCreateUser");
    By btnDelete = By.id("btnDeleteUsers");

    public UsersList(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 25L);
    }

    private void switchToTargetFrame() {
        this.wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentFrame"));
    }

    public Map<String, String> openCreateNewUser() {
        Map result;
        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(a_newUser));
            this.wait.until(ExpectedConditions.elementToBeClickable(a_newUser));
            a_newUser.click();
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("frameworkLoadingSpinner")));
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("saveGroupButton")));
            result = ResultStore.success("New user successfully opened");
        } catch (Exception var3) {
            result = ResultStore.failure("Failed to open new user. error: " + Misc.getStacktraceAsString(var3));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    private void filterByUserName(String name) {
        input_filterUser.clear();
        input_filterUser.sendKeys(new CharSequence[]{name});
    }

    public Map<String, String> filterUser(String name) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_filterUser));
            this.filterByUserName(name);
            List<WebElement> filteredUsers = this.driver.findElements(By.className("oas-list-user-name"));
            Iterator var5 = filteredUsers.iterator();

            while(var5.hasNext()) {
                WebElement user = (WebElement)var5.next();
                if (user.getText().contains(name)) {
                    user.click();
                    this.wait.until(ExpectedConditions.visibilityOf(this.sec_details));
                    WebElement userName = this.driver.findElement(By.className("oas-view-user-name"));
                    if (!userName.getAttribute("title").equals(name)) {
                        throw new Exception("The name of the user does not match user details.");
                    }
                }
            }

            result.put("out", "User found in the list");
            result.put("err", "");
            result.put("rc", "0");
        } catch (Exception var7) {
            result.put("out", "");
            result.put("err", "Failed to find user. error: " + Misc.getStacktraceAsString(var7));
            result.put("rc", "1");
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> isOfUserType(String name, String userType) {
        Map<String, String> result = ResultStore.getResultObj();
        this.wait.until(ExpectedConditions.visibilityOf(input_filterUser));

        try {
            this.switchToTargetFrame();
            this.filterByUserName(name);
            List<WebElement> filteredUsers = this.driver.findElements(By.className("oas-list-user-name"));
            Iterator var7 = filteredUsers.iterator();

            label59:
            while(true) {
                List badges;
                WebElement userName;
                do {
                    do {
                        WebElement user;
                        do {
                            if (!var7.hasNext()) {
                                result.put("out", "User contains the correct badges");
                                result.put("err", "");
                                result.put("rc", "0");
                                break label59;
                            }

                            user = (WebElement)var7.next();
                        } while(!user.getText().contains(name));

                        user.click();
                        this.wait.until(ExpectedConditions.visibilityOf(this.sec_details));
                        badges = this.driver.findElements(By.className("user-badge"));
                        userName = this.driver.findElement(By.className("oas-view-user-name"));
                    } while(!userName.getAttribute("title").equals(name));
                } while(badges.isEmpty());

                Iterator var10 = badges.iterator();

                while(var10.hasNext()) {
                    WebElement badge = (WebElement)var10.next();
                    if (userType.equals("none")) {
                        if (!badge.getAttribute("class").contains("ng-hide")) {
                            input_filterUser.sendKeys(new CharSequence[]{"none"});
                        }
                    } else if (!badge.getAttribute("class").contains("ng-hide")) {
                        if (userType.equals("super")) {
                            if (badge.getAttribute("ng-show").contains("dministrator")) {
                                break;
                            }

                            input_filterUser.sendKeys(new CharSequence[]{"superHiden"});
                        } else if (userType.equals("ldap")) {
                            if (badge.getAttribute("ng-show").contains("ldap")) {
                                break;
                            }

                            input_filterUser.sendKeys(new CharSequence[]{"ldapHiden"});
                        }
                    }
                }
            }
        } catch (Exception var11) {
            result.put("out", "");
            result.put("err", "Failed to find user. error: " + Misc.getStacktraceAsString(var11));
            result.put("rc", "1");
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> openEditSelectedUser() {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.elementToBeClickable(a_editUser));
            a_editUser.click();
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("frameworkLoadingBlur")));
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("frameworkLoadingSpinner")));
            result.put("out", "Edit user successfully opened");
            result.put("err", "");
            result.put("rc", "0");
        } catch (Exception var3) {
            result.put("out", "");
            result.put("err", "Failed to open edit user. error: " + Misc.getStacktraceAsString(var3));
            result.put("rc", "1");
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> deleteSelectedUser(String login) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.elementToBeClickable(a_deleteUsers));
            a_deleteUsers.click();
            this.wait.until(ExpectedConditions.elementToBeClickable(button_btnDialogSubmit));
            button_btnDialogSubmit.click();
            result.put("out", "User successfully deleted from the list");
            result.put("err", "");
            result.put("rc", "0");
        } catch (Exception var4) {
            result.put("out", "");
            result.put("err", "Failed to delete user. error: " + Misc.getStacktraceAsString(var4));
            result.put("rc", "1");
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> deleteUser(String sUserName) {
        Map result;
        try {
            this.switchToTargetFrame();
            By user = By.cssSelector("span[text='" + sUserName + "']");
            this.wait.until(ExpectedConditions.elementToBeClickable(user));
            UiTestUtils.clickTill(this.driver, user);
            TestUtils.sleepAndLog(1000L);
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("section#generalSection span[title='" + sUserName + "']")));
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section#generalSection span[title='" + sUserName + "']")));
            this.wait.until(ExpectedConditions.elementToBeClickable(this.btnDelete));
            UiTestUtils.clickTill(this.driver, this.btnDelete);
            this.wait.until(ExpectedConditions.elementToBeClickable(this.btnSubmit));
            UiTestUtils.clickTill(this.driver, this.btnSubmit);
            this.wait.until(ExpectedConditions.elementToBeClickable(this.btnCreate));
            TestUtils.sleepAndLog(1500L);
            result = ResultStore.success("User successfully deleted");
        } catch (Exception var4) {
            result = ResultStore.failure("Failed to delete user. error: " + Misc.getExceptionInfo(var4));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> deactivateUser(String sUserName) {
        Map result;
        WebElement a_deactivateUser;
        try {
            this.switchToTargetFrame();
            TestUtils.sleepAndLog(2500L);
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
            a_deactivateUser = this.driver.findElement(By.cssSelector("span[text='" + sUserName + "']"));
            this.wait.until(ExpectedConditions.elementToBeClickable(a_deactivateUser));
            a_deactivateUser.click();
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section#generalSection span[title='" + sUserName + "']")));
            Actions actions = new Actions(this.driver);
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class*='selected'] a[ng-click*='userMgmtCtrl.toggleUserState']")));
            a_deactivateUser = this.driver.findElement(By.cssSelector("div[class*='selected'] a[ng-click*='userMgmtCtrl.toggleUserState']"));
            actions.moveToElement(a_deactivateUser).moveToElement(a_deactivateUser).build().perform();
            this.wait.until(ExpectedConditions.elementToBeClickable(a_deactivateUser));
            a_deactivateUser.click();
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section#generalSection span[title='" + sUserName + "'] " + "~ span[ng-show*='userMgmtCtrl.uiUser.inactive'] " + "span[ng-bind*='opr.userMgmt.users.properties.disabled']")));
            result = ResultStore.success("User successfully deactivated");
        } catch (Exception var7) {
            try {
                this.wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[text='" + sUserName + "']")));
                UiTestUtils.enableTestAutomationMode(this.driver);
                a_deactivateUser = this.driver.findElement(By.cssSelector("div[class*='selected'] a[ng-click*='userMgmtCtrl.toggleUserState']"));
                this.wait.until(ExpectedConditions.elementToBeClickable(a_deactivateUser));
                a_deactivateUser.click();
                this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section#generalSection span[title='" + sUserName + "'] " + "~ span[ng-show*='userMgmtCtrl.uiUser.inactive'] " + "span[ng-bind*='opr.userMgmt.users.properties.disabled']")));
                result = ResultStore.success("User successfully deactivated");
            } catch (Exception var6) {
                result = ResultStore.failure("Failed to deactivate User. error: " + Misc.getExceptionInfo(var6));
            }
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> activateUser(String sUserName) {
        Map result;
        WebElement a_activateUser;
        try {
            this.switchToTargetFrame();
            TestUtils.sleepAndLog(2500L);
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
            a_activateUser = this.driver.findElement(By.cssSelector("span[text='" + sUserName + "']"));
            this.wait.until(ExpectedConditions.elementToBeClickable(a_activateUser));
            a_activateUser.click();
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section#generalSection span[title='" + sUserName + "']")));
            Actions actions = new Actions(this.driver);
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class*='selected'] a[ng-click*='userMgmtCtrl.toggleUserState']")));
            a_activateUser = this.driver.findElement(By.cssSelector("div[class*='selected'] a[ng-click*='userMgmtCtrl.toggleUserState']"));
            actions.moveToElement(a_activateUser).moveToElement(a_activateUser).build().perform();
            this.wait.until(ExpectedConditions.elementToBeClickable(a_activateUser));
            a_activateUser.click();
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section#generalSection span[title='" + sUserName + "'] " + "~ span[ng-show*='userMgmtCtrl.uiUser.inactive'] " + "span[ng-bind*='opr.userMgmt.users.properties.disabled']")));
            result = ResultStore.success("User successfully activated");
        } catch (Exception var7) {
            try {
                this.wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[text='" + sUserName + "']")));
                UiTestUtils.enableTestAutomationMode(this.driver);
                a_activateUser = this.driver.findElement(By.cssSelector("div[class*='selected'] a[ng-click*='userMgmtCtrl.toggleUserState']"));
                this.wait.until(ExpectedConditions.elementToBeClickable(a_activateUser));
                a_activateUser.click();
                this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section#generalSection span[title='" + sUserName + "'] " + "~ span[ng-show*='userMgmtCtrl.uiUser.inactive'] " + "span[ng-bind*='opr.userMgmt.users.properties.disabled']")));
                result = ResultStore.success("User successfully activated");
            } catch (Exception var6) {
                result = ResultStore.failure("Failed to activate User. error: " + Misc.getExceptionInfo(var6));
            }
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> getGroupMemberships(String sUserName) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[text='" + sUserName + "']")));
            this.driver.findElement(By.cssSelector("span[text='" + sUserName + "']")).click();
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section#generalSection span[title='" + sUserName + "']")));
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("section#groupsSection div[control='userGroups.groupsField']")));
            List<WebElement> groups = this.driver.findElements(By.cssSelector("section#groupsSection div[control='userGroups.groupsField'] ul li.ui-select-match-item"));
            List<String> sGroups = new ArrayList();
            Iterator var6 = groups.iterator();

            while(var6.hasNext()) {
                WebElement group = (WebElement)var6.next();
                sGroups.add(group.getText());
            }

            result.put("out", sGroups.toString());
            result.put("err", "");
            result.put("rc", "0");
        } catch (Exception var7) {
            result.put("out", "");
            result.put("err", "Failed to get groups. error: " + Misc.getStacktraceAsString(var7));
            result.put("rc", "1");
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> getAssignedRoles(String sUserName) {
        Map result;
        try {
            this.switchToTargetFrame();
            String css = "span[text='" + sUserName + "']";
            ((WebElement)this.wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(css)))).click();
            css = "#rolesSection [title='Assigned roles']";
            /*this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
            String xpath = ".//*[@id='rolesSection']//label[@title='Assigned roles']/../*[@id='controls']//li/div";
            //section#groupsSection div[control='userGroups.groupsField'] ul li.ui-select-match-item
            this.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
            List<WebElement> roles = this.driver.findElements(By.xpath(xpath));*/
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section#generalSection span[title='" + sUserName + "']")));
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("section#rolesSection div[control='userRoles.rolesField']")));
            //List<WebElement> roles= this.driver.findElements(By.cssSelector("section#rolesSection div[control='userRoles.rolesField'] ul li.ui-select-match-item"));
            List<WebElement> roles= this.driver.findElements(By.cssSelector("section#rolesSection div[control='userRoles.rolesField'] ul li span"));
            String toResult = "";

            WebElement role;
            for(Iterator var8 = roles.iterator(); var8.hasNext(); toResult = toResult.concat(role.getText() + " ")) {
                role = (WebElement)var8.next();
            }

            result = ResultStore.success(toResult);
        } catch (Exception var9) {
            result = ResultStore.failure("Failed to get roles. " + Misc.getStacktraceAsString(var9));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> getInheritedRoles(String sUserName) {
        Map result;
        try {
            this.switchToTargetFrame();
            String css = "span[text='" + sUserName + "']";
            ((WebElement)this.wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(css)))).click();
            css = "#rolesSection [title='Assigned roles']";
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
            //String xpath = ".//*[@id='rolesSection']//label[@title='Inherited roles']/../*[@id='controls']//li/div";
            //String xpath="#rolesSection label[title='Inherited roles']~#controls li.ui-select-match-item";
            String xpath="#rolesSection label[title='Inherited roles']~#controls li span";
            this.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(xpath)));

            List<WebElement> roles = this.driver.findElements(By.cssSelector(xpath));
            String toResult = "";

            WebElement role;
            for(Iterator var8 = roles.iterator(); var8.hasNext(); toResult = toResult.concat(role.getText() + " ")) {
                role = (WebElement)var8.next();
            }

            result = ResultStore.success(toResult);
        } catch (Exception var9) {
            result = ResultStore.failure("Failed to get roles. " + Misc.getStacktraceAsString(var9));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> openEditUser(String sUserName) {
        Map result;
        WebElement a_editUser;
        try {
            TestUtils.sleepAndLog(4000L);
            this.switchToTargetFrame();
            TestUtils.sleepAndLog(4000L);
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
            a_editUser = this.driver.findElement(By.cssSelector("span[text='" + sUserName + "']"));
            this.wait.until(ExpectedConditions.elementToBeClickable(a_editUser));
            a_editUser.click();
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section#generalSection span[title='" + sUserName + "']")));
            Actions actions = new Actions(this.driver);
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class*='selected'] a:not([ng-click])")));
            a_editUser = this.driver.findElement(By.cssSelector("div[class*='selected'] a:not([ng-click])"));
            actions.moveToElement(a_editUser).moveToElement(a_editUser).build().perform();
            this.wait.until(ExpectedConditions.elementToBeClickable(a_editUser));
            a_editUser.click();
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1[title^='Edit User']")));
            result = ResultStore.success("Edit user successfully opened");
        } catch (Exception var7) {
            try {
                this.wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[text='" + sUserName + "']")));
                UiTestUtils.enableTestAutomationMode(this.driver);
                a_editUser = this.driver.findElement(By.cssSelector("div[class*='selected'] a:not([ng-click])"));
                this.wait.until(ExpectedConditions.elementToBeClickable(a_editUser));
                a_editUser.click();
                this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1[title^='Edit User']")));
                result = ResultStore.success("Edit Dashboard successfully opened");
            } catch (Exception var6) {
                result = ResultStore.failure("Failed to open edit Dashboard. error: " + Misc.getExceptionInfo(var6));
            }
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> isSuperAdmin(String sUserName) {
        Map result;
        try {
            this.driver.switchTo().frame("contentFrame");
            String xpath = ".//div[@title='" + sUserName + " (" + sUserName.toLowerCase() + ")" + "']/span[@class='badges']/span[@ng-show='row.super_administrator']";
            WebElement key = this.driver.findElement(By.xpath(xpath));
            if (!key.getAttribute("class").contains("ng-hide")) {
                result = ResultStore.success("SuperAdmin");
            } else {
                result = ResultStore.success("NonSuperAdmin");
            }

            this.driver.switchTo().defaultContent();
        } catch (Exception var5) {
            result = ResultStore.failure("Failed to check SuperAdmin. error: " + Misc.getExceptionInfo(var5));
        }

        return result;
    }

    public Map<String, String> isLdapUser(String sUserName) {
        Map result;
        try {
            UiTestUtils.waitUntilPageIsLoaded(this.driver, this.wait);
            this.driver.switchTo().frame("contentFrame");
            String css = "div[title='" + sUserName + " (" + sUserName.toLowerCase() + ")'] span[ng-show='row.ldap_user']";
            WebElement key = this.driver.findElement(By.cssSelector(css));
            if (!key.getAttribute("class").contains("ng-hide")) {
                result = ResultStore.success("LdapUser");
            } else {
                result = ResultStore.success("NonLdapUser");
            }

            this.driver.switchTo().defaultContent();
        } catch (Exception var5) {
            result = ResultStore.failure("Failed to check LdapUser. error: " + Misc.getExceptionInfo(var5));
        }

        return result;
    }
}

