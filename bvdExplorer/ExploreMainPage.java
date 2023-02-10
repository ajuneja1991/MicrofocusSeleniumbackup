package com.hp.opr.qa.framework.pageobjects.bvdExplorer;

import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.utils.UiTestUtils;
import com.mf.opr.qa.framework.utils.TestUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.util.List;
import java.util.Map;

public class ExploreMainPage extends ExploreCommonPage{

    final WebDriver driver;
    final WebDriverWait wait;

    @FindBy(how = How.NAME, using = "username")
    static WebElement username;

    @FindBy(how = How.NAME, using = "password")
    static WebElement password;

    @FindBy(how = How.ID, using = "submit_login")
    static WebElement submit;

    @FindBy(how=How.TAG_NAME, using="ux-select-list-item")
    static List<WebElement> noOfHost;

    @FindBy(how = How.ID, using = "submit")
    private WebElement button_idm_login;

    @FindBy(how = How.ID, using = "username")
    private WebElement input_idm_username;

    @FindBy(how = How.CSS, using = "#logout-link")
    static WebElement relogin;

    @FindBy(how = How.XPATH, using = "//div[@class='context-filter-content']/span")
    static List<WebElement> breadcrums;

    @FindBy(how = How.CSS, using = "button > i")
    static WebElement shareButton;

    //@FindBy(how = How.XPATH, using = "//div/button[@_ngcontent-hgx-c1]")
    @FindBy(how = How.CSS, using = "body > app-root > mondrian > ux-page-header > div > div > div.page-header-icon-menus > button")
    static WebElement adminButton;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),\"Log out\")]")
    static WebElement logoutButton;

    @FindBy(how = How.XPATH, using = "//canvas")
    static WebElement canvasImage;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),\"Complex chart page\")]")
    static WebElement contextChartPage;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),\"Page without activation\")]")
    static WebElement pageWithoutActivation;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),\"Flip chart page\")]")
    static WebElement flipChartPage;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),\"Analytics\")]")
    static WebElement analyticsPage;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),\"First page - Mock\")]")
    static WebElement firstMockPage;
    
    
    //mondrian page means middle page(excluding left and right) 
    
    @FindBy(how = How.XPATH, using = "//mondrian-mashup/descendant::mondrian-simple-list")
    protected List<WebElement> mondrianPageWidgets;
        
    @FindBy(how = How.XPATH, using = "(//mondrian-mashup/descendant::mondrian-simple-list[@class='ng-star-inserted']/descendant::ux-select-list-item)[1]")
    private WebElement firstHost;
    
    


    public ExploreMainPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);
    }
    
    public Map<String, String> selectFirstHost(){
        Map<String, String> result;

        try
        {
        	waitAndClick(firstHost);
        	  return ResultStore.success("Selected first host ");
        }
        catch(Exception exc)
        {    exc.printStackTrace();
            return ResultStore.failure("Error message: StackTrace " + Misc.getStacktraceAsString(exc));
        }
    }


    public Map<String, String> hostCountingInBreadCrumbs(){
        Map<String, String> result;

        try
        {
            //List<WebElement> breadcrums;
            TestUtils.sleepAndLog(6000);

//        get all the host list
            //List<WebElement> list = driver.findElements(By.tagName("ux-select-list-item"));
            int counter=0;

            // click all the host
            for ( WebElement i: noOfHost) {
                counter++;
                i.click();
                TestUtils.sleepAndLog(500);
            }
            //lineChart();
            //list.get(0).click();
            //breadcrums = driver.findElements(By.xpath("//div[@class='context-filter-content']/span"));
            //System.out.println(counter+"--------"+breadcrums.size());

//        check host clicked and total host in
            //Assert.assertEquals(breadcrums.size(),counter);

//       remove selected host
            //for ( WebElement i: noOfHost.subList(1,noOfHost.size())) {
                //i.click();
                //TestUtils.sleepAndLog(500);
            //}
            //TestUtils.sleepAndLog(2000);

            return ResultStore.success("successfully counted the host");
        }
        catch(Exception exc)
        {
            return ResultStore.failure("Error message: StackTrace " + Misc.getStacktraceAsString(exc));
        }
    }

    public Map<String, String> removeSelectedHost(){
        Map<String, String> result;

        TestUtils.sleepAndLog(3000);
        try
        {
            for ( WebElement host: noOfHost.subList(1,noOfHost.size())) {
                host.click();
                TestUtils.sleepAndLog(500);
            }
            TestUtils.sleepAndLog(2000);

            return ResultStore.success("Successfully removed the host");
        }
        catch (Exception exc){

                return ResultStore.failure("Could not remove host list, Error Message: StackTrace " + Misc.getStacktraceAsString(exc));
        }
    }

    public Map<String, String> selectHost(List<String> hostList){
        Map<String, String> result;

        try {
            TestUtils.sleepAndLog(2000);
            int count = hostList.size();
            System.out.println(count);
            System.out.println(hostList.get(count-1));
            for (WebElement host : noOfHost) {
                for(int i=0; i<count;i++) {
                    if (host.getText().equals(hostList.get(i))) {
                        host.click();
                        TestUtils.sleepAndLog(500);
                    }
                }
            }
            return ResultStore.success("successfully selected the host");
        }
        catch(Exception exc)
        {
            return ResultStore.failure("Error message: StackTrace " + Misc.getStacktraceAsString(exc));
        }
    }

    public Map<String, String> clickOnShareButton(){
        Map<String, String> result;

        try {

            wait.until(ExpectedConditions.elementToBeClickable(shareButton));
            shareButton.click();
            result = ResultStore.success(" Share Button clicked successfully");
        }
        catch(Exception exc){

            result = ResultStore.failure("Failed to click on Share Button. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    public Map<String, String> checkDataPoints(List<String> dataPointLocate)
    {
        Map<String, String> result;

        try
        {
            WebElement elementTest=null;
            UiTestUtils.waitUntilPageIsLoaded(driver, wait);
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div > svg")));
            TestUtils.sleepAndLog(1500);
            for(String datapoint:dataPointLocate){
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("svg>" + datapoint)));
                elementTest=driver.findElement(By.cssSelector("svg>" + datapoint));
                if(!elementTest.isDisplayed()){
                    Assert.assertTrue(false,"Data point is not available");
                }

            }
            result = ResultStore.success("All the data points are available");
            //if (result.get("out").contains("null")) Reporter.log(driver.getPageSource());
        } catch (Exception exc)
        {

            result = ResultStore.failure("Failed to get data points. error: " + Misc.getExceptionInfo(exc));
        }
        return result;
    }

    public String copyFromClipboard()
    {
        String link ="";
            try
            {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Clipboard clipboard = toolkit.getSystemClipboard();
                link = (String) clipboard.getData(DataFlavor.stringFlavor);
            }catch(Exception exc)
            {
                exc.printStackTrace();
            }
            return link;
    }

    public Map<String, String> openNewWindow() {
        Map<String, String> result;

        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.open()");
            result = ResultStore.success("new window is open");
        } catch (Exception exc) {

            result = ResultStore.failure("Failed to get data points. error: " + Misc.getExceptionInfo(exc));
        }
        return result;
    }


    public Map<String, String> switchToNewWindow(int number) {
        Map<String, String> result;

        try {
            driver.switchTo().window(driver.getWindowHandles().toArray()[number].toString());
            result = ResultStore.success("switched to another window");
        } catch (Exception exc) {

            result = ResultStore.failure("Failed to switch to another window. error: " + Misc.getExceptionInfo(exc));
        }
        return result;
    }

    public Map<String, String> logout()
    {
        Map<String, String> result;

        try
        {
            wait.until(ExpectedConditions.elementToBeClickable(adminButton));
            TestUtils.sleep(2);
            adminButton.click();
            wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
            logoutButton.click();
            TestUtils.sleep(2);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#logout-link")));
            relogin.click();
            wait.until(ExpectedConditions.visibilityOf(button_idm_login));
            TestUtils.sleepAndLog(3000);
            input_idm_username.clear();
            result = ResultStore.success("Successfully logged out");
        } catch (Exception exc)
        {
            result = ResultStore.failure("Failed to log out. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    public Map<String, String> checkCanvas()
    {
        Map<String, String> result;

        try
        {


            Actions actionBuilder=new Actions(driver);
            Action drawOnCanvas=actionBuilder
                    .contextClick(canvasImage)
                    .moveToElement(canvasImage,8,8)
                    .clickAndHold(canvasImage)
                    .moveByOffset(120, 120)
                    .moveByOffset(60,70)
                    .moveByOffset(-140,-140)
                    .release(canvasImage)
                    .build();
            drawOnCanvas.perform();
            result = ResultStore.success("Successfully clicked on canvas image");
        } catch (Exception exc)
        {
            result = ResultStore.failure("Failed to click on canvas image. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    public Map<String, String> clickOnContextChartPage(){
        Map<String, String> result;

        try {

            wait.until(ExpectedConditions.elementToBeClickable(contextChartPage));
            contextChartPage.click();
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("mondrian-widget > div.panel.panel-default > echarts-chart")));
            result = ResultStore.success(" Context Chart Page clicked successfully");
        }
        catch(Exception exc){

            result = ResultStore.failure("Failed to click on Context Chart Page. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    public Map<String, String> clickOnPageWithoutActivation(){
        Map<String, String> result;

        try {

            wait.until(ExpectedConditions.elementToBeClickable(pageWithoutActivation));
            pageWithoutActivation.click();
            result = ResultStore.success("Page without activation Page clicked successfully");
        }
        catch(Exception exc){

            result = ResultStore.failure("Failed to click on Page without activation Page. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    public Map<String, String> clickOnFlipChartPage(){
        Map<String, String> result;

        try {

            wait.until(ExpectedConditions.elementToBeClickable(flipChartPage));
            flipChartPage.click();
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("mondrian-widget > div.panel.panel-default > echarts-chart")));
            result = ResultStore.success("Flip Chart Page clicked successfully");
        }
        catch(Exception exc){

            result = ResultStore.failure("Failed to click on Flip Chart Page. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    public Map<String, String> clickOnAnalyticsPage(){
        Map<String, String> result;

        try {

            wait.until(ExpectedConditions.elementToBeClickable(analyticsPage));
            analyticsPage.click();
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("mondrian-widget > div.panel.panel-default > echarts-chart")));
            result = ResultStore.success("Analytics Page clicked successfully");
        }
        catch(Exception exc){

            result = ResultStore.failure("Failed to click on Analytics Page. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    public Map<String, String> clickOnFirstMockPage(){
        Map<String, String> result;

        try {

            wait.until(ExpectedConditions.elementToBeClickable(firstMockPage));
            firstMockPage.click();
            result = ResultStore.success("First Mock Page clicked successfully");
        }
        catch(Exception exc){

            result = ResultStore.failure("Failed to click on First Mock Page. error: " + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

    public Map<String, String> gettingCurrentUrlAndAppending(String url,String appendValue)
    {
        try
        {
            TestUtils.sleepAndLog(3000);
            //String currentURL = driver.getCurrentUrl();
            String appendedURL = url + appendValue;
            driver.navigate().to(appendedURL);
            driver.navigate().refresh();
            TestUtils.sleepAndLog(3000);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("mondrian-widget > div.panel.panel-default > echarts-chart")));
            return ResultStore.success("Url appended successfully and redirected");
        } catch (Exception exc)
        {
            return ResultStore.failure("Url is not appended successfully or redirected" + Misc.getStacktraceAsString(exc));
        }
    }

    public Map<String, String> clickOnBackButton()
    {
        try
        {

            driver.navigate().back();
            TestUtils.sleepAndLog(3000);
            wait.until(ExpectedConditions.visibilityOf(submit));
            wait.until(ExpectedConditions.elementToBeClickable(username));
            return ResultStore.success("Clicked on back button successfully");
        } catch (Exception exc)
        {
            return ResultStore.failure("could not click on back button" + Misc.getStacktraceAsString(exc));
        }
    }
}
