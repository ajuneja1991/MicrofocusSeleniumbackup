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

public class GroupList {
    final WebDriver driver;
    final WebDriverWait wait;
    String id;
    @FindBy(
            how = How.ID,
            using = "btnCreateGroup"
    )
    static WebElement a_newGroup;
    @FindBy(
            how = How.ID,
            using = "filterGroups"
    )
    static WebElement input_filterGroup;
    @FindBy(
            how = How.ID,
            using = "generalSection"
    )
    WebElement sec_details;
    @FindBy(
            how = How.ID,
            using = "btnDeleteGroups"
    )
    static WebElement a_deleteGroups;
    @FindBy(
            how = How.ID,
            using = "btnDialogSubmit"
    )
    static WebElement a_btnDialogSubmit;
    @FindBy(
            how = How.ID,
            using = "btnEditGroup"
    )
    static WebElement a_editGroup;
    @FindBy(
            how = How.CLASS_NAME,
            using = "oas-list-group-name"
    )
    public List<WebElement> allGroups;

    public GroupList(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 25L);
    }

    private void switchToTargetFrame() {
        this.wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentFrame"));
    }

    public Map<String, String> openCreateNewGroup() {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.id = "btnCreateGroup";
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(this.id)));
            a_newGroup.click();
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1[title='Create Group']")));
            result = ResultStore.success("Create new group successfully opened from list");
        } catch (Exception var3) {
            result.put("out", "");
            result = ResultStore.failure("Failed to open create new group from list. error: " + Misc.getStacktraceAsString(var3));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    private void filterByGroupName(String name) {
        input_filterGroup.clear();
        TestUtils.sleepAndLog(2000L);
        input_filterGroup.sendKeys(new CharSequence[]{name});
    }

    public Map<String, String> openEditGroup() {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.elementToBeClickable(a_editGroup));
            a_editGroup.click();
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("frameworkLoadingBlur")));
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("frameworkLoadingSpinner")));
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveGroupButton")));
            result = ResultStore.success("Edit group successfully opened");
        } catch (Exception var3) {
            result = ResultStore.failure("Failed to enter edit mode for group. error: " + Misc.getStacktraceAsString(var3));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> openEditGroup(String sGroupName) {
        Map result;
        try {
            this.switchToTargetFrame();
            TestUtils.sleepAndLog(4000L);
            this.wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[text='" + sGroupName + "']")));
            TestUtils.sleepAndLog(4000L);
            UiTestUtils.clickTill(this.driver, By.cssSelector("span[text='" + sGroupName + "']"));
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("section#generalSection span[title='" + sGroupName + "']")));
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section#generalSection span[title='" + sGroupName + "']")));
            TestUtils.sleepAndLog(1000L);
            this.wait.until(ExpectedConditions.elementToBeClickable(By.id("btnEditGroup")));
            UiTestUtils.clickTill(this.driver, By.id("btnEditGroup"));
            TestUtils.sleepAndLog(1000L);
            this.wait.until(ExpectedConditions.elementToBeClickable(By.id("saveGroupButton")));
            result = ResultStore.success("Edit group successfully opened");
        } catch (Exception var4) {
            result = ResultStore.failure("Failed to enter edit mode for group. error: " + Misc.getExceptionInfo(var4));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> filterGroup(String name) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.id = "filterGroups";
            TestUtils.sleepAndLog(1000L);
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(this.id)));
            this.filterByGroupName(name);
            TestUtils.sleepAndLog(1000L);
            List<WebElement> filteredGroups = this.driver.findElements(By.className("oas-list-group-name"));
            Iterator var5 = filteredGroups.iterator();

            while(var5.hasNext()) {
                WebElement group = (WebElement)var5.next();
                if (group.getText().contains(name)) {
                    group.click();
                    String css = "div#rightContent div[class*='cg-busy-animation']";
                    this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
                    this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(css)));
                    this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("load-spinner")));
                    WebElement groupName = this.driver.findElement(By.xpath(".//*[contains(@class,'oas-view-group-name')]"));
                    if (!groupName.getAttribute("title").equals(name)) {
                        throw new Exception("The name of the group does not match group details." + groupName.getAttribute("title"));
                    }
                }
            }

            result = ResultStore.success("Group found in the list");
        } catch (Exception var8) {
            result = ResultStore.failure("Failed to find group. error: " + Misc.getStacktraceAsString(var8));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> isOfGroupType(String name, String groupType) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_filterGroup));
            this.filterByGroupName(name);
            List<WebElement> filteredGroups = this.driver.findElements(By.className("oas-list-group-name"));
            Iterator var7 = filteredGroups.iterator();

            label50:
            while(true) {
                List badges;
                WebElement groupName;
                do {
                    do {
                        WebElement group;
                        do {
                            if (!var7.hasNext()) {
                                result = ResultStore.success("Group found in the list");
                                break label50;
                            }

                            group = (WebElement)var7.next();
                        } while(!group.getText().contains(name));

                        group.click();
                        this.wait.until(ExpectedConditions.visibilityOf(this.sec_details));
                        badges = this.driver.findElements(By.className("user-badge"));
                        groupName = this.driver.findElement(By.className("oas-view-group-name"));
                    } while(!groupName.getAttribute("title").equals(name));
                } while(badges.isEmpty());

                Iterator var10 = badges.iterator();

                while(var10.hasNext()) {
                    WebElement badge = (WebElement)var10.next();
                    if (groupType.equals("none")) {
                        if (!badge.getAttribute("class").contains("ng-hide")) {
                            input_filterGroup.sendKeys(new CharSequence[]{"noneError"});
                        }
                    } else if (!badge.getAttribute("class").contains("ng-hide")) {
                        if (badge.getAttribute("ng-show").contains("ssignment")) {
                            break;
                        }

                        input_filterGroup.sendKeys(new CharSequence[]{"assignHiden"});
                    }
                }
            }
        } catch (Exception var11) {
            result = ResultStore.failure("Failed to find group. error: " + Misc.getStacktraceAsString(var11));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> deleteSelectedGroup() {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.elementToBeClickable(a_deleteGroups));
            a_deleteGroups.click();
            this.wait.until(ExpectedConditions.visibilityOf(a_btnDialogSubmit));
            a_btnDialogSubmit.click();
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#groupManagement")));
            result = ResultStore.success("Group successfully deleted from the list");
        } catch (Exception var3) {
            result = ResultStore.failure("Failed to delete group. error: " + Misc.getStacktraceAsString(var3));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> deleteGroup(String sGroupName) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            WebElement span_group = this.driver.findElement(By.cssSelector("span[text='" + sGroupName + "']"));
            this.wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[text='" + sGroupName + "']")));
            span_group.click();
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section#generalSection span[title='" + sGroupName + "']")));
            WebElement a_deleteGroup = this.driver.findElement(By.cssSelector("div[class*='selected'] a[title='Delete Group']"));
            Actions actions = new Actions(this.driver);
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class*='selected'] a[title='Delete Group']")));
            actions.moveToElement(span_group).moveToElement(a_deleteGroup).build().perform();
            this.wait.until(ExpectedConditions.elementToBeClickable(a_deleteGroup));
            a_deleteGroup.click();
            this.wait.until(ExpectedConditions.visibilityOf(a_btnDialogSubmit));
            a_btnDialogSubmit.click();
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectItemPrompt")));
            result = ResultStore.success("Group successfully deleted");
        } catch (Exception var6) {
            result = ResultStore.failure("Failed to delete group. error: " + Misc.getStacktraceAsString(var6));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> getGroupMembers(String sGroupName) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[text='" + sGroupName + "']")));
            this.driver.findElement(By.cssSelector("span[text='" + sGroupName + "']")).click();
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section#generalSection span[title='" + sGroupName + "']")));
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("section#usersSection div#sectionContent")));
            List<WebElement> groupMembers = this.driver.findElements(By.cssSelector("section#usersSection div#sectionContent div.slick-row div[class*='slick-cell l0']"));
            List<String> sGroupMembers = new ArrayList();
            Iterator var6 = groupMembers.iterator();

            while(var6.hasNext()) {
                WebElement member = (WebElement)var6.next();
                sGroupMembers.add(member.getText());
            }

            result = ResultStore.success(sGroupMembers.toString());
        } catch (Exception var7) {
            result = ResultStore.failure("Failed to get group members. error: " + Misc.getStacktraceAsString(var7));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> getAssignedRoles(String sGroupName) {
        Map result;
        try {
            this.switchToTargetFrame();
            String css = "span[text='" + sGroupName + "']";
            this.wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(css)));
            this.driver.findElement(By.cssSelector("span[text='" + sGroupName + "']")).click();
            css = "section#generalSection span[title='" + sGroupName + "']";
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
            css = "section#rolesSection div#sectionContent";
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));
            //css = "section#rolesSection div#sectionContent div ul li div";
            css="section#rolesSection div#sectionContent div ul li span";
            List<WebElement> roles = this.driver.findElements(By.cssSelector(css));
            List<String> toResult = new ArrayList();
            Iterator var7 = roles.iterator();

            while(var7.hasNext()) {
                WebElement member = (WebElement)var7.next();
                toResult.add(member.getText());
            }

            result = ResultStore.success(toResult.toString());
        } catch (Exception var8) {
            result = ResultStore.failure("Failed to get group members. error: " + var8.getMessage() + Misc.getStacktraceAsString(var8));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }
}

