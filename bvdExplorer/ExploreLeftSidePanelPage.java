package com.hp.opr.qa.framework.pageobjects.bvdExplorer;

import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.utils.UiTestUtils;
import com.mf.opr.qa.framework.utils.TestUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.util.Map;


public class ExploreLeftSidePanelPage {

    final WebDriver driver;
    final WebDriverWait wait;

    //Left Side Panel
    @FindBy(how = How.CLASS_NAME, using = "ux-side-menu-content")
    static WebElement leftSidePanel;

    //Search Button
    @FindBy(how = How.CLASS_NAME, using = "sideNavSearchItem")
    static WebElement searchBarButton;

    //Search Text Box
    @FindBy(how = How.ID, using = "sideNavigationSearchInput")
    static WebElement serachTextBox;

    @FindBy(how = How.CSS, using = "ng-star-inserted")
    static WebElement opeartionsButton;

    @FindBy(how = How.CSS, using = "li[role='none']")
    static WebElement administrationButton;

    @FindBy(how = How.CSS, using = "button[class='ux-side-menu-minimize-button']")
    static WebElement leftSideMinimizeButton;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),\"Angular info page\")]")
    static WebElement angularInfoPageFromLeftSidePanel;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),\"Network\")]")
    static WebElement networkPageFromLeftSidePanel;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),\"Flip chart page\")]")
    static WebElement flipChartPageFromLeftSidePanel;




    public ExploreLeftSidePanelPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);
    }

    public Map<String, String> mouseMovement()
    {
        Map<String, String> result;

        try
        {
            TestUtils.sleepAndLog(1000);
            wait.until(ExpectedConditions.visibilityOf(leftSidePanel));
            Actions actions = new Actions(driver);
            actions.moveToElement(leftSidePanel).perform();
            wait.until(ExpectedConditions.elementToBeClickable(leftSideMinimizeButton));

            result = ResultStore.success("Successfully moved mouse");

        } catch (Exception exc)
        {
            result = ResultStore.failure("Failed to move Mouse: " + Misc
                    .getStacktraceAsString(exc));
        }
        return result;
    }

    public Map<String, String> checkForLeftSidePanel()
    {
        Map<String, String> result;

        try
        {
            TestUtils.sleepAndLog(1000);
            wait.until(ExpectedConditions.visibilityOf(leftSidePanel));

            result = ResultStore.success("Left side panel is present");

        } catch (Exception exc)
        {
            result = ResultStore.failure("Left side panel is not present" + Misc
                    .getStacktraceAsString(exc));
        }
        return result;
    }

    public Map<String, String> clickOnSearchButton()
    {
        Map<String, String> result;

        try
        {
            TestUtils.sleepAndLog(1000);
            wait.until(ExpectedConditions.elementToBeClickable(searchBarButton));
            searchBarButton.click();

            result = ResultStore.success("Successfully clicked on search button");

        } catch (Exception exc)
        {
            result = ResultStore.failure("Failed to click on search button: " + Misc
                    .getStacktraceAsString(exc));
        }
        return result;
    }

}
