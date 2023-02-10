package com.hp.opr.qa.framework.pageobjects.bvd.bvdUserandRolePages;


import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.utils.UiTestUtils;
import com.mf.opr.qa.framework.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

public class GroupEditor {
    String id;
    @FindBy(
            how = How.NAME,
            using = "groupName"
    )
    static WebElement input_groupName;
    @FindBy(
            how = How.XPATH,
            using = "//*[@form='ldapForm']//input[@type='text']"
    )
    static WebElement input_mappedGroup;
    @FindBy(
            how = How.NAME,
            using = "groupDescription"
    )
    static WebElement textarea_groupDescription;
    @FindBy(
            how = How.CLASS_NAME,
            using = "checkbox inline"
    )
    static WebElement label_eventAssignment;
    @FindBy(
            how = How.CSS,
            using = "input.btn.btn-primary.btn-large"
    )
    static WebElement input_createGroup;
    @FindBy(
            how = How.XPATH,
            using = "//div[contains(@id,'select2-result-label')]"
    )
    static WebElement mappedLDAPGroup;
    @FindBy(
            how = How.ID,
            using = "saveGroupButton"
    )
    static WebElement input_saveGroup;
    @FindBy(
            how = How.ID,
            using = "cancelEditorButton"
    )
    static WebElement input_cancel;
    @FindBy(
            how = How.CLASS_NAME,
            using = "opr-form-item-line"
    )
    static WebElement a_groupError;
    @FindBy(
            how = How.ID,
            using = "btnCreateGroup"
    )
    static WebElement btn_newGroup;
    @FindBy(
            how = How.CSS,
            using = "div#controls input[ng-model*='eventAssignment']"
    )
    static WebElement input_evtAssignment;
    @FindBy(
            how = How.CLASS_NAME,
            using = "help-block"
    )
    static WebElement a_nameValidation;
    @FindBy(
            how = How.CSS,
            using = "opr-item-selector-2[selected-items='containedUsers'] input"
    )
    static WebElement input_addUser;
    @FindBy(
            how = How.CSS,
            using = "opr-list-selector[list='users'] a"
    )
    static WebElement a_browseUsers;
    @FindBy(
            how = How.XPATH,
            using = ".//*[@id='rolesSection']//label[@title='Assigned roles']/..//input"
    )
    static WebElement input_addRole;
    final WebDriver driver;
    final WebDriverWait wait;

    public GroupEditor(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 25L);
    }

    private void switchToTargetFrame() {
        this.wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentFrame"));
    }

    public void openCreateNewGroupFromList() {
        this.wait.until(ExpectedConditions.visibilityOf(btn_newGroup));
        btn_newGroup.click();
    }

    private void setGroupName(String name) {
        this.wait.until(ExpectedConditions.elementToBeClickable(input_groupName));
        TestUtils.sleep(2);
        input_groupName.clear();
        UiTestUtils.waitDocumentReadyState(this.driver, 15);
        input_groupName.sendKeys(new CharSequence[]{Keys.chord(new CharSequence[]{Keys.CONTROL, "a"}) + name});
    }

    private void setGroupDescription(String description) {
        textarea_groupDescription.sendKeys(new CharSequence[]{Keys.chord(new CharSequence[]{Keys.CONTROL, "a"}) + description});
    }

    private void setMappedLDAPGroups(String mappedGroup) {
        this.wait.until(ExpectedConditions.elementToBeClickable(input_mappedGroup));
        input_mappedGroup.clear();
        input_mappedGroup.sendKeys(new CharSequence[]{mappedGroup});
        TestUtils.sleepAndLog(4000L);
        mappedLDAPGroup.click();
    }

    public Map<String, String> assignRoleToGroup(String roleName) {
        Map result;
        try {
            this.switchToTargetFrame();
            TestUtils.sleepAndLog(500L);
            this.wait.until(ExpectedConditions.visibilityOf(input_addRole));
            input_addRole.sendKeys(new CharSequence[]{roleName + Keys.ENTER});
            String xpath = ".//*[@id='rolesSection']//label[@title='Assigned roles']/..//li/div[contains(text(),'" + roleName + "')]";
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            input_saveGroup.click();
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#footerContainer > input.btn.btn-primary.btn-large")));
            result = ResultStore.success("Role assigned successfully");
        } catch (Exception var4) {
            result = ResultStore.failure("Assign role failed" + Misc.getStacktraceAsString(var4));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> createGroup(String name, String description) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_groupName));
            this.setGroupName(name);
            TestUtils.sleepAndLog(1000L);
            this.setGroupDescription(description);
            this.wait.until(ExpectedConditions.visibilityOf(input_saveGroup));
            input_saveGroup.click();
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("frameworkLoadingBlur")));
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("frameworkLoadingSpinner")));
            result = ResultStore.success("Group successfully created");
        } catch (Exception var5) {
            result = ResultStore.failure("Failed to create group. error: " + Misc.getStacktraceAsString(var5));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> editGroup(String newName, String newDescription) {
        Map result = ResultStore.getResultObj();

        try {
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
            UiTestUtils.waitUntilPageIsLoaded(this.driver, this.wait);
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_groupName));
            this.setGroupName(newName);
            TestUtils.sleepAndLog(1000L);
            this.setGroupDescription(newDescription);
            TestUtils.sleepAndLog(2500L);
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
            this.wait.until(ExpectedConditions.visibilityOf(input_saveGroup));
            input_saveGroup.click();
            result = ResultStore.success("Successfully edited group from the list");
        } catch (Exception var5) {
            result = ResultStore.failure("Failed to edit group. error: " + Misc.getStacktraceAsString(var5));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> createGroupWithLDAPMapping(String name, String mappedGroup, String description) {
        Map result;
        try {
            this.switchToTargetFrame();
            this.setGroupName(name);
            TestUtils.sleepAndLog(2000L);
            this.setGroupDescription(description);
            TestUtils.sleepAndLog(2000L);
            this.setMappedLDAPGroups(mappedGroup);
            TestUtils.sleepAndLog(2000L);
            input_saveGroup.click();
            result = ResultStore.success("Group with mapping successfully created");
        } catch (Exception var6) {
            result = ResultStore.failure("Failed to create group with mapping. error: " + Misc.getStacktraceAsString(var6));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> createDuplicateGroup(String name, String error) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_saveGroup));
            this.setGroupName(name);
            this.wait.until(ExpectedConditions.visibilityOf(a_groupError));
            if (a_nameValidation.getText().contains(error)) {
                result = ResultStore.success("Duplicate error correctly displayed");
            }

            result = ResultStore.success("Duplication error correctly displayed");
        } catch (Exception var5) {
            result = ResultStore.failure("Failed to display useful error: " + Misc.getStacktraceAsString(var5));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> cancelCreateGroup() {
        Map result = ResultStore.getResultObj();

        try {
            this.driver.switchTo().defaultContent();
            this.driver.switchTo().frame("contentFrame");
            this.wait.until(ExpectedConditions.visibilityOf(input_cancel));
            input_cancel.click();
            this.wait.until(ExpectedConditions.visibilityOf(btn_newGroup));
            result = ResultStore.success("Cancelation of group creation was successful");
        } catch (Exception var3) {
            result = ResultStore.failure("Failed to cancel group creation: " + Misc.getStacktraceAsString(var3));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> createLongNameGroup(String name, String error) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_saveGroup));
            this.setGroupName(name);
            this.wait.until(ExpectedConditions.visibilityOf(a_groupError));
            if (!a_nameValidation.getText().contains(error)) {
                throw new Exception();
            }

            result = ResultStore.success("Max-Length error correctly displayed");
        } catch (Exception var5) {
            result = ResultStore.failure("Failed to display useful error: " + Misc.getStacktraceAsString(var5));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> createEmptyNameGroup(String error) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            input_groupName.clear();
            this.wait.until(ExpectedConditions.visibilityOf(a_groupError));
            if (a_nameValidation.getText().contains(error)) {
                result = ResultStore.success("Required field error correctly displayed");
            }

            this.id = "cancelEditorButton";
            this.wait.until(ExpectedConditions.elementToBeClickable(By.id(this.id)));
            input_cancel.click();
        } catch (Exception var4) {
            result = ResultStore.failure("Failed to display useful error: " + Misc.getStacktraceAsString(var4));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> assignUserToGroup(String sUser) {
        Map result;
        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_addUser));
            input_addUser.sendKeys(new CharSequence[]{sUser + Keys.ENTER});
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section#usersSection div[title='" + sUser + "']")));
            TestUtils.sleepAndLog(1000L);
            this.wait.until(ExpectedConditions.elementToBeClickable(By.id("saveGroupButton")));
            input_saveGroup.click();
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("saveGroupButton")));
            result = ResultStore.success("Successfully added user to group");
        } catch (Exception var4) {
            result = ResultStore.failure("Failed to add user to group. error: " + Misc.getExceptionInfo(var4));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }
}
