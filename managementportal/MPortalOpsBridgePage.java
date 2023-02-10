package com.hp.opr.qa.framework.pageobjects.managementportal;

import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.utils.UiTestUtils;
import com.mf.opr.qa.framework.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

import static com.hp.opr.qa.framework.utils.UiTestUtils.waitUntilBusyAnimationHidden;

public class MPortalOpsBridgePage {

    final WebDriver driver;
    final WebDriverWait wait;
    final WebDriverWait shortwait;

    int timeout = 15;
    int shorttimeout = 1;

    @FindBy(how = How.XPATH, using = "//span[.='Users']")
    private WebElement span_userbutton;

    @FindBy(how = How.LINK_TEXT, using = "User List")
    private WebElement user_Listlink;


    @FindBy(how = How.CSS, using = "tbody tr td:nth-child(1)")
    private WebElement RBACuserSelectfromSearch;

    @FindBy(how = How.CSS, using = "button[tooltip='Search']")
    private WebElement searchbutton;

    @FindBy(how = How.CSS, using = "input[placeholder='Search ..']")
    private WebElement serachtypetext;

    @FindBy(how = How.CSS, using = "span.hpe-trash")
    private WebElement deletebutton;

    @FindBy(how = How.CSS, using = ".button-warning")
    private WebElement removebutton;

    @FindBy(how = How.LINK_TEXT, using = "Group List")
    private WebElement group_Listlink;

    @FindBy(how = How.LINK_TEXT, using = "Group Settings")
    private WebElement groupsettings_link;

    @FindBy(how = How.XPATH, using = "//span[.='Groups']")
    private WebElement span_groupbutton;

    @FindBy(how = How.CSS, using = "input[type='text'][name='name']")
    private WebElement namefieldforuser;

    @FindBy(how = How.CSS, using = "input[type='text'][name='name']")
    private WebElement namefieldforgroup;

    @FindBy(how = How.CSS, using = "input[type='text'][name='displayName']")
    private WebElement displaynamefieldforuser;

    @FindBy(how = How.CSS, using = "input[type='text'][name='displayName']")
    private WebElement displaynamefieldforgroup;

    @FindBy(how = How.CSS, using = "input#ux-select-1-input")
    private WebElement assrolesforgroupinput;

    @FindBy(how = How.CSS, using = "#ux-select-2-input")
    private WebElement groupruletype;

    @FindBy(how = How.CSS, using = "input[type='password'][name='password'][maxlength='50']")
    private WebElement passwordfieldforuser;

    @FindBy(how = How.CSS, using = "input[name='displayName']")
    private WebElement dispnameforgrouprule;

    @FindBy(how = How.XPATH, using = "//span[@class='hpe-icon hpe-add']")
    private WebElement createUseraddbtn;

    @FindBy(how = How.XPATH, using = "//span[.='Roles']")
    private WebElement span_rolesbutton;

    @FindBy(how = How.CSS, using = "div.tab-ebox div button[type='Submit']")
    private WebElement savebuttoncreateUser;

    @FindBy(how = How.CSS, using = "div.tab-ebox div button[type='Submit']")
    private WebElement savebuttoncreategroup;

    @FindBy(how = How.CSS, using = "input#ux-select-4-input")
    private WebElement assousergrouprole;



    public MPortalOpsBridgePage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, timeout);
        this.shortwait = new WebDriverWait(driver, shorttimeout);
        this.driver = driver;
    }

    public Map<String, String> createUser(String name, String password) {
        Map<String, String> result;
        try {
            waitUntilBusyAnimationHidden(driver,wait);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Users']")));
            span_userbutton.click();
            waitUntilBusyAnimationHidden(driver,wait);
            Actions act1=new Actions(driver);
            act1.moveToElement(createUseraddbtn).click(createUseraddbtn).build().perform();
            waitUntilBusyAnimationHidden(driver,wait);
            wait.until(ExpectedConditions.visibilityOf(namefieldforgroup));
            namefieldforgroup.click();
            namefieldforgroup.sendKeys(name);
            TestUtils.sleepAndLog(1000);
            namefieldforgroup.sendKeys(Keys.TAB);
            TestUtils.sleepAndLog(1000);
            displaynamefieldforuser.click();
            displaynamefieldforuser.sendKeys(name);
            TestUtils.sleepAndLog(1000);
            passwordfieldforuser.click();
            passwordfieldforuser.sendKeys(password);
            TestUtils.sleepAndLog(1000);
            savebuttoncreateUser.click();
            waitUntilBusyAnimationHidden(driver,wait);
            //wait.until(ExpectedConditions.visibilityOf(user_Listlink));
            result = ResultStore.success("successfully created OperationBridge User");
        } catch (Exception exc) {
            result = ResultStore.failure("cannot create OperationBridge User error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }


    private Map<String, String> clickElementDynamicWait(WebElement element)
    {
        try
        {
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            return ResultStore.success("Button clicked");
        } catch (Exception exc)
        {
            return ResultStore.failure("Button not clicked" + Misc.getStacktraceAsString
                    (exc));
        }
    }


    public Map<String, String> clickDelete()
    {
        try
        {
            clickElementDynamicWait(deletebutton);
            clickElementDynamicWait(removebutton);
            return ResultStore.success("Delete user Clicked");
        } catch (Exception exc)
        {
            return ResultStore.failure("Delete query not clicked. error: " + Misc
                    .getStacktraceAsString
                            (exc));
        }
    }

    public Map<String, String> selectUser() {
        Map<String, String> result;
        try {
            waitUntilBusyAnimationHidden(driver,wait);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Users']")));
            span_userbutton.click();

            waitUntilBusyAnimationHidden(driver,wait);

            result = ResultStore.success("successfully selected OperationBridge User");
        }catch (Exception exc) {
            result = ResultStore.failure("unable to select OperationBridge User error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    public Map<String, String> selectgroup() {
        Map<String, String> result;
        try {

            UiTestUtils.waitUntilPageIsLoaded(driver, wait);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Groups']")));
            waitUntilBusyAnimationHidden(driver,wait);
            Actions act1=new Actions(driver);
            act1.moveToElement(span_groupbutton).click(span_groupbutton).build().perform();
            waitUntilBusyAnimationHidden(driver,wait);

            result = ResultStore.success("successfully selected OperationBridge group");
        }catch (Exception exc) {
            result = ResultStore.failure("unable to select OperationBridge group error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }


    public Map<String, String> deleteUserorgroup(String username) {
        Map<String, String> result;
        try {

            waitUntilBusyAnimationHidden(driver,wait);
            WebElement searchBox=driver.findElement(By.cssSelector("button[tooltip='Search']"));
            wait.until(ExpectedConditions.elementToBeClickable(searchBox));
            searchBox.click();

            WebElement searchBoxField=driver.findElement(By.cssSelector("input[placeholder='Search ..']"));
            wait.until(ExpectedConditions.elementToBeClickable(searchBoxField));
            searchBoxField.click();

            typingText(searchBoxField,username,10);
            TestUtils.sleepAndLog(1500);
            waitUntilBusyAnimationHidden(driver,wait);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tbody tr td:nth-child(1)")));

            Actions act1=new Actions(driver);
            act1.moveToElement(RBACuserSelectfromSearch).click(RBACuserSelectfromSearch).build().perform();

            clickDelete();

            waitUntilBusyAnimationHidden(driver,wait);

            result = ResultStore.success("successfully deleted OperationBridge User");
        } catch (Exception exc) {
            result = ResultStore.failure("cannot delete OperationBridge User error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }


    public void typingText(WebElement element, String value, int sleepTime) {
        for (int i = 0; i < value.length(); ++i) {
            char c = value.charAt(i);
            String s = "" + c;
            TestUtils.sleepAndLog((long) sleepTime);
            element.sendKeys(new CharSequence[]{s});
        }

    }

    public Map<String, String> creategroup(String name, String password, String assrole, String grouprole, String createdusername) {
        Map<String, String> result;
        try {
            waitUntilBusyAnimationHidden(driver,wait);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Groups']")));
            span_groupbutton.click();
            UiTestUtils.waitUntilPageIsLoaded(driver, wait);
            Actions act1=new Actions(driver);
            act1.moveToElement(createUseraddbtn).click(createUseraddbtn).build().perform();
            waitUntilBusyAnimationHidden(driver,wait);
            wait.until(ExpectedConditions.visibilityOf(namefieldforgroup));
            namefieldforgroup.click();
            namefieldforgroup.sendKeys(name);
            TestUtils.sleepAndLog(1000);
            namefieldforgroup.sendKeys(Keys.TAB);
            TestUtils.sleepAndLog(1000);
            displaynamefieldforgroup.click();
            displaynamefieldforgroup.sendKeys(name);
            TestUtils.sleepAndLog(1000);
            displaynamefieldforgroup.sendKeys(Keys.TAB);
            TestUtils.sleepAndLog(1000);
            assrolesforgroupinput.click();
            typingText(assrolesforgroupinput, assrole, 1000);
            TestUtils.sleepAndLog(1000);
            assrolesforgroupinput.sendKeys(Keys.ENTER);
            TestUtils.sleepAndLog(1000);
            savebuttoncreategroup.click();
            waitUntilBusyAnimationHidden(driver,wait);
            wait.until(ExpectedConditions.visibilityOf(createUseraddbtn));

            createUseraddbtn.click();
            waitUntilBusyAnimationHidden(driver,wait);
            TestUtils.sleepAndLog(1000);
            dispnameforgrouprule.click();
            TestUtils.sleepAndLog(1000);
            dispnameforgrouprule.sendKeys(grouprole);
            typingText(groupruletype, grouprole, 1000);
            TestUtils.sleepAndLog(1000);
            groupruletype.sendKeys(Keys.ENTER);
            TestUtils.sleepAndLog(1000);
            assousergrouprole.click();
            typingText(assousergrouprole, createdusername, 1000);
            assousergrouprole.sendKeys(Keys.ENTER);
            TestUtils.sleepAndLog(1000);
            savebuttoncreateUser.click();
            waitUntilBusyAnimationHidden(driver,wait);
            wait.until(ExpectedConditions.visibilityOf(createUseraddbtn));
            savebuttoncreateUser.click();
            waitUntilBusyAnimationHidden(driver,wait);
            TestUtils.sleepAndLog(1000);
            wait.until(ExpectedConditions.visibilityOf(createUseraddbtn));
            driver.switchTo().defaultContent();
            result = ResultStore.success("successfully created OperationBridge group");
        } catch (Exception exc) {
            result = ResultStore.failure("cannot create OperationBridge group. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }
}
