package com.hp.opr.qa.framework.pageobjects.bvd.bvdUserandRolePages;

import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class RoleEditor {
    final WebDriver driver;
    final WebDriverWait wait;
    String permissionsList = "<PermissionList> <view>      <name>Amazon Cloud</name>    <operation>View</operation>    </view>    <view>    <name>All My Windows Servers</name>    <operation>Change</operation>    </view>    <view>    <name>All My Unix Servers</name>    <operation>Change</operation>    <operation>Delete</operation>    </view>    <view>    <name>J2EE Topology</name>    <operation>View</operation>    <operation>Change</operation>    <operation>Delete</operation>    </view>    </PermissionList>";
    public Map<String, List<String>> viewPermissions = this.setupViews();
    @FindBy(
            how = How.NAME,
            using = "roleName"
    )
    static WebElement input_roleName;
    @FindBy(
            how = How.NAME,
            using = "roleDescription"
    )
    static WebElement textarea_roleDescription;
    @FindBy(
            how=How.CSS,
            using="#groupsSection #controls li span"

    )static WebElement groupsassignedlist;
    @FindBy(
            how = How.ID,
            using = "saveRoleButton"
    )
    static WebElement input_saveRole;
    @FindBy(
            how = How.ID,
            using = "cancelEditorButton"
    )
    static WebElement input_cancelRole;
    @FindBy(
            how = How.CLASS_NAME,
            using = "opr-form-item-line"
    )
    static WebElement a_roleError;
    @FindBy(
            how = How.CLASS_NAME,
            using = "ui-grid-filter-input"
    )
    static WebElement txt_search;
    @FindBy(
            how = How.CSS,
            using = "input[value ='Create Role']"
    )
    static WebElement input_createRole;
    @FindBy(
            how = How.ID,
            using = "toggleCollapse_omi-workspaces"
    )
    static WebElement tgl_workspacesCategory;
    @FindBy(
            how = How.ID,
            using = "toggleCollapse_omi-event-processing"
    )
    static WebElement tgl_eventProcessingCategory;
    @FindBy(
            how = How.ID,
            using = "toggleCollapse_omi-monitoring"
    )
    static WebElement tgl_monitoringCategory;
    @FindBy(
            how = How.ID,
            using = "toggleCollapse_omi-operations-console"
    )
    static WebElement tgl_operationsConsoleCategory;
    @FindBy(
            how = How.ID,
            using = "toggleCollapse_omi-service-health"
    )
    static WebElement tgl_serviceHealthCategory;
    @FindBy(
            how = How.ID,
            using = "toggleCollapse_omi-setup"
    )
    static WebElement tgl_setupMaintenanceCategory;
    @FindBy(
            how = How.ID,
            using = "toggleCollapse_omi-users"
    )
    static WebElement tgl_usersCategory;
    @FindBy(
            how = How.ID,
            using = "toggleCollapse_rtsm"
    )
    static WebElement tgl_rtsmCategory;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-predefined-page"
    )
    static WebElement tgl_predefinedPages;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-user-component"
    )
    static WebElement tgl_userComponents;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-user-page"
    )
    static WebElement tgl_userPages;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-automation"
    )
    static WebElement tgl_automation;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-correlation"
    )
    static WebElement tgl_correlation;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-assignment"
    )
    static WebElement tgl_assignments;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-autoassignment-mgr"
    )
    static WebElement tgl_autoAssignment;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-depljob-mgr"
    )
    static WebElement tgl_deploymentJob;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-ma-special-operations"
    )
    static WebElement tgl_maSpecial;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-mt-aspect-template"
    )
    static WebElement tgl_mtAspectsPolicyTempl;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-browser-options"
    )
    static WebElement tgl_browserOptions;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-custom-action"
    )
    static WebElement tgl_customActionsExec;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-customaction-script-mgr"
    )
    static WebElement tgl_customActionsAdmin;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-event"
    )
    static WebElement tgl_events;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-event-browser"
    )
    static WebElement tgl_eventBrowser;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-event-dashboard"
    )
    static WebElement tgl_monitoringDashboards;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-event-dashboard-designer"
    )
    static WebElement tgl_dashboardDesigner;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-ext-intf-script-mgr"
    )
    static WebElement tgl_externalInstructions;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-perfgraph-mgr"
    )
    static WebElement tgl_perfGraphMappings;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-roi-dashboard"
    )
    static WebElement tgl_roiDashboard;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-run-book-administration"
    )
    static WebElement tgl_runBookMappings;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-run-book-execution"
    )
    static WebElement tgl_runBookExec;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-tool"
    )
    static WebElement tgl_toolExec;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-tools-mgr"
    )
    static WebElement tgl_toolAdmin;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-view-mapping-mgr"
    )
    static WebElement tgl_viewMappings;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_pmi-designgraph-ui"
    )
    static WebElement tgl_designGraphs;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-alerts"
    )
    static WebElement tgl_alerts;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-ci-context-menu"
    )
    static WebElement tgl_ciContextMenus;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-ci-status-alert"
    )
    static WebElement tgl_ciStatusAlerts;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-ci-status-calculation"
    )
    static WebElement tgl_ciStatusCalc;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-custom-image"
    )
    static WebElement tgl_customImage;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-downtime-management"
    )
    static WebElement tgl_downtimeManagement;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-kpi-in-view"
    )
    static WebElement tgl_kpiInView;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-repositories"
    )
    static WebElement tgl_repositories;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-audit-log"
    )
    static WebElement tgl_auditLog;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-certificate-request-mgr"
    )
    static WebElement tgl_certificateRequests;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-content-mgr"
    )
    static WebElement tgl_contentPacks;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-infrastructure-setting"
    )
    static WebElement tgl_infraSettings;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-license-management"
    )
    static WebElement tgl_licenseMgmt;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-node-editor"
    )
    static WebElement tgl_monitoredNodes;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-servers-mgr"
    )
    static WebElement tgl_connectedServers;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-authentication-strategy"
    )
    static WebElement tgl_authManagement;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-recipients"
    )
    static WebElement tgl_recipients;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_rtsm-view"
    )
    static WebElement tgl_rtsmView;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-automatic-runbook-launch-mgr"
    )
    static WebElement tgl_autoAutomaticRunBookExec;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-downtime-behavior"
    )
    static WebElement tgl_autoDowntimeBehavior;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-epi-script-mgr"
    )
    static WebElement tgl_autoEventProcessingCustom;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-eti-mapping-mgr"
    )
    static WebElement tgl_autoIndicatorMappings;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-event-automation-script-mgr"
    )
    static WebElement tgl_autoEventAutomationConfig;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-event-fwd-mgr"
    )
    static WebElement tgl_autoEventForwarding;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-event-to-group"
    )
    static WebElement tgl_autoUserGroupAssignment;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-notification-mgr"
    )
    static WebElement tgl_autoNotifications;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-notification-template-mgr"
    )
    static WebElement tgl_autoNotificationTemplates;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-submit-event"
    )
    static WebElement tgl_autoEventSubmission;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-time-based-event-automation-mgr"
    )
    static WebElement tgl_autoTbea;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-correlation-mgr"
    )
    static WebElement tgl_corrTbec;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-event-storm-suppression-mgr"
    )
    static WebElement tgl_corrEventStormSup;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-event-suppression-mgr"
    )
    static WebElement tgl_corrEventStorm;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-stream-based-correlation-mgr"
    )
    static WebElement tgl_corrSbec;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-config-folder-assignment-related"
    )
    static WebElement tgl_assigMgmtTemplatesAspects;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-template-group-assignment-related"
    )
    static WebElement tgl_assigPolicyTemplates;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-health-indicator"
    )
    static WebElement tgl_reposHealthIndicators;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-recipient"
    )
    static WebElement tgl_recipRecipients;
    @FindBy(
            how = How.ID,
            using = "resourceColumnItem_omi-recipient-template"
    )
    static WebElement tgl_recipTemplates;
    @FindBy(
            how = How.ID,
            using = "permissionPopoverButton_rtsm"
    )
    static WebElement btn_rtsmCategoryFilter;
    @FindBy(
            how = How.ID,
            using = "permissionPopoverButton_rtsm-view"
    )
    static WebElement btn_rtsmViewFilter;
    @FindBy(
            how = How.CLASS_NAME,
            using = "oas-all-permissions"
    )
    static WebElement txt_allFilter;
    @FindBy(
            how = How.CLASS_NAME,
            using = "oas-view-permissions"
    )
    static WebElement txt_viewFilter;
    @FindBy(
            how = How.CLASS_NAME,
            using = "oas-no-permissions"
    )
    static WebElement txt_noneFilter;

    public RoleEditor(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10L);
    }

    protected void switchToTargetFrame() {
        this.wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentFrame"));
        this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("frameworkLoadingBlur")));
        this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("frameworkLoadingSpinner")));
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("permissionsSection")));
        this.wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("a[id^='resourceColumnItem']")));
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveRoleButton")));
        TestUtils.sleepAndLog(500L);
    }

    protected void setRoleName(String name) {
        input_roleName.clear();
        input_roleName.sendKeys(new CharSequence[]{name});
    }

    protected void setRoleDescription(String text) {
        textarea_roleDescription.clear();
        textarea_roleDescription.sendKeys(new CharSequence[]{text});
    }

    private Map<String, String> expandParentResource(WebElement category, WebElement parentResource) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.elementToBeClickable(category));
            category.click();
            this.wait.until(ExpectedConditions.visibilityOf(parentResource));
            parentResource.click();
            result.put("out", "successfully expanded the resource " + parentResource.getText());
            result.put("err", "");
            result.put("rc", "0");
        } catch (Exception var5) {
            result.put("out", "");
            result.put("err", "cannot open resource '" + parentResource.getText() + "'\n" + Misc.getStacktraceAsString(var5));
            result.put("rc", "1");
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> createRoleOMi(String name, String description) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_roleName));
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("permissionsSection")));
            this.setRoleName(name);
            this.setRoleDescription(description);
            this.wait.until(ExpectedConditions.visibilityOf(input_saveRole));
            input_saveRole.click();
            result.put("out", "Role successfully created");
            result.put("err", "");
            result.put("rc", "0");
        } catch (Exception var5) {
            result.put("out", "");
            result.put("err", "Failed to create role. error: " + Misc.getStacktraceAsString(var5));
            result.put("rc", "1");
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> createRole(String name, String description) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_createRole));
            this.setRoleName(name);
            this.setRoleDescription(description);
            input_createRole.click();
            result.put("out", "Role successfully created");
            result.put("err", "");
            result.put("rc", "0");
        } catch (Exception var5) {
            result.put("out", "");
            result.put("err", "Failed to create role. error: " + Misc.getStacktraceAsString(var5));
            result.put("rc", "1");
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> createRoleWithRTSMView(String name, String msg) {
        Map<String, String> result = ResultStore.getResultObj();
        WebElement a_createRole2 = (WebElement)this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@value ='" + msg + "']")));

        try {
            this.switchToTargetFrame();
            this.setRoleName(name);
            this.setRoleDescription("This is a Role with RTSM View Permissions");
            this.expandParentResource(tgl_rtsmCategory, tgl_rtsmView);
            this.selectViewPermissions();
            a_createRole2.click();
            result.put("out", "Role successfully created");
            result.put("err", "");
            result.put("rc", "0");
        } catch (Exception var6) {
            result.put("out", "");
            result.put("err", "Failed to create role. error: " + Misc.getStacktraceAsString(var6));
            result.put("rc", "1");
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> createDuplicateRole(String name) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_createRole));
            this.setRoleName(name);
            this.wait.until(ExpectedConditions.visibilityOf(a_roleError));
            result.put("out", "Duplication error correctly displayed");
            result.put("err", "");
            result.put("rc", "0");
        } catch (Exception var4) {
            result.put("out", "");
            result.put("err", "Failed to display useful error: " + Misc.getStacktraceAsString(var4));
            result.put("rc", "1");
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, List<String>> setupViews() {
        Map<String, List<String>> map = new HashMap();
        List<String> values1 = new ArrayList();
        values1.add("View");
        List<String> values2 = new ArrayList();
        values2.add("Change");
        List<String> values3 = new ArrayList();
        values3.add("Delete");
        List<String> values4 = new ArrayList();
        values4.add("Change");
        values4.add("Delete");
        map.put("Amazon Cloud", values1);
        map.put("All My Windows Servers", values2);
        map.put("All My Unix Servers", values3);
        map.put("J2EE Topology", values4);
        return map;
    }

    public Map<String, String> selectOperation(String viewName, String permission) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            WebElement viewRowContent = this.driver.findElement(By.xpath("//div[contains(@class, 'ui-grid-cell') and .//text()='" + viewName + "']"));
            WebElement viewRow = viewRowContent.findElement(By.xpath(".."));
            List<WebElement> allCheckboxes = viewRow.findElements(By.xpath(".//*"));
            Iterator var8 = allCheckboxes.iterator();

            while(var8.hasNext()) {
                WebElement checkBox = (WebElement)var8.next();
                if (checkBox.getAttribute("title").contains(permission)) {
                    checkBox.click();
                    break;
                }
            }

            result.put("out", "View found and permission assigned");
            result.put("err", "");
            result.put("rc", "0");
        } catch (Exception var9) {
            result.put("out", "");
            result.put("err", "Failed to find view or assign permission. error: " + var9.getMessage());
            result.put("rc", "1");
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> selectViewPermissions() {
        Map<String, String> result = ResultStore.getResultObj();
        Map viewPermissions = this.setupViews();

        try {
            this.switchToTargetFrame();
            Iterator var4 = viewPermissions.entrySet().iterator();

            while(var4.hasNext()) {
                Map.Entry<String, List<String>> entry = (Map.Entry)var4.next();
                String view = (String)entry.getKey();
                this.findView(view);
                List<String> values = (List)entry.getValue();
                Iterator var8 = values.iterator();

                while(var8.hasNext()) {
                    String operation = (String)var8.next();
                    this.selectOperation(view, operation);
                }
            }

            result.put("out", "View found and permission assigned");
            result.put("err", "");
            result.put("rc", "0");
        } catch (Exception var9) {
            result.put("out", "");
            result.put("err", "Failed to find view or assign permission. error: " + var9.getMessage());
            result.put("rc", "1");
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> editRole(String newName, String newDescription) {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_roleName));
            this.setRoleName(newName);
            this.setRoleDescription(newDescription);
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("permissionsSection")));
            this.wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("a[id^='resourceColumnItem']")));
            this.wait.until(ExpectedConditions.visibilityOf(input_saveRole));
            input_saveRole.click();
            result.put("out", "User successfully edited role from the list");
            result.put("err", "");
            result.put("rc", "0");
        } catch (Exception var5) {
            result.put("out", "");
            result.put("err", "Failed to edit role. error: " + var5.getMessage());
            result.put("rc", "1");
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    private void findView(String viewName) {
        this.wait.until(ExpectedConditions.visibilityOf(txt_search));
        txt_search.clear();
        txt_search.sendKeys(new CharSequence[]{viewName});
    }

    public Map<String, String> cancelCreateRole() {
        Map result = ResultStore.getResultObj();

        try {
            this.switchToTargetFrame();
            this.wait.until(ExpectedConditions.visibilityOf(input_cancelRole));
            input_cancelRole.click();
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#roleManagement")));
            result.put("out", "Cancelation of role creation was successful");
            result.put("err", "");
            result.put("rc", "0");
        } catch (Exception var3) {
            result.put("out", "");
            result.put("err", "Failed to cancel role creation: " + var3.getMessage());
            result.put("rc", "1");
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> assignUserToRole(String userName) {
        Map result;
        try {
            this.switchToTargetFrame();
            String css = "opr-user-section input";
            ((WebElement)this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)))).sendKeys(new CharSequence[]{userName + Keys.ENTER});
            String xpath = ".//*[@title='" + userName + "']";
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            this.wait.until(ExpectedConditions.visibilityOf(input_saveRole));
            input_saveRole.click();
            result = ResultStore.success("User " + userName + " successfully assigned to role");
        } catch (Exception var5) {
            result = ResultStore.failure("User " + userName + " was not assigned to role. " + Misc.getStacktraceAsString(var5));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }

    public Map<String, String> assignGroupToRole(String groupName) {
        Map result;
        try {
            this.switchToTargetFrame();
            String css = "#groupsSection input";
            ((WebElement)this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)))).sendKeys(new CharSequence[]{groupName + Keys.ENTER});
            String xpath = "#groupsSection #controls li span";
            //String xpath = ".//*[@id='groupsSection']//div[text()='" + groupName + "']";
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(xpath)));
            this.wait.until(ExpectedConditions.textToBePresentInElement(groupsassignedlist,groupName));
            this.wait.until(ExpectedConditions.visibilityOf(input_saveRole));
            input_saveRole.click();
            result = ResultStore.success("Group " + groupName + " successfully assigned to role");
        } catch (Exception var5) {
            result = ResultStore.failure("Group " + groupName + " was not assigned to role. " + Misc.getStacktraceAsString(var5));
        }

        this.driver.switchTo().defaultContent();
        return result;
    }
}

