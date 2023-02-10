package com.hp.opr.qa.framework.pageobjects.bvd;


import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.utils.TestUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;


public class SystemSettingsPage {

    final WebDriver driver;
    final WebDriverWait wait;

    // BVD UPLOAD CUSTOM CSS
    @FindBy(how = How.ID, using = "upload")
    static WebElement button_UploadChangedStyle;

    // BVD UPLOAD DASHBOARD
    @FindBy(how = How.ID, using = "error")
    static WebElement div_Error;

    @FindBy(how = How.ID, using = "success")
    static WebElement div_Success;

    // BVD CUSTOM CSS
    @FindBy(how = How.ID, using = "textareaField")
//    @FindBy(how = How.ID, using = "custom_css")
    static WebElement textarea_CustomCSS;
    
    @FindBy(how = How.CSS, using = "#customCssField .ace_text-input")
    static WebElement cssInputField;
    
    
    
    // BVD CURRENT API KEY
    @FindBy(how = How.ID, using = "api_key")
    static WebElement div_APIKey;

    // BVD REQUEST NEW API KEY
    @FindBy(how = How.ID, using = "new_api_key")
    static WebElement button_NewAPIKey;
    
    @FindBy(how = How.CSS, using = "slide-system-settings [title='Close']")
    static WebElement closeSystemSettingspanel;
    
    @FindBy(how = How.CSS, using = ".slidePanelSystemSetting #buttonSaveSetting")
    static WebElement saveSystemSettings;
    
    @FindBy(how = How.CSS, using = ".slidePanelSystemSetting #buttonCancelSetting")
    static WebElement cancelSystemSettings;
    
    @FindBy(how = How.CSS, using = ".slidePanelSystemSetting #success")
    static WebElement successDiv;
    
    
    
     public SystemSettingsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
    }


    public Map<String, String> setSystemTimeZoneUI(String timeZoneValue)
    {
        Map<String, String> result;

        try
        {
            TestUtils.sleepAndLog(1000);
            if(driver.findElements(By.cssSelector("div[title='"+ timeZoneValue +"']")).size() == 0) {
                driver.findElement(By.cssSelector("div[id$='0_content'] button:first-child")).click();
                TestUtils.sleep(1);
                driver.findElement(By.cssSelector("input[placeholder='Search for a region']")).click();
                driver.findElement(By.cssSelector("input[placeholder='Search for a region']")).sendKeys(timeZoneValue);
                TestUtils.sleep(1);
                driver.findElement(By.cssSelector("input[placeholder='Search for a region']")).sendKeys(Keys.ENTER);

                WebElement searchHighlight_timeZone = driver.findElement(By.cssSelector("opr-grouped-list-item[id$='"+ timeZoneValue +"']"));
                Actions actions1 = new Actions(driver);
                actions1.moveToElement(searchHighlight_timeZone).click(searchHighlight_timeZone).build().perform();
                TestUtils.sleep(1);

                wait.until(ExpectedConditions.elementToBeClickable(saveSystemSettings));
                saveSystemSettings.click();
            }else{
                    wait.until(ExpectedConditions.elementToBeClickable(cancelSystemSettings));
                    cancelSystemSettings.click();
                }
            result = ResultStore.success("Timezone is Set as required");
        } catch (Exception exc)
        {
            result = ResultStore.failure("Failed to set timezone. error: " + Misc
                    .getStacktraceAsString(exc));
        }
        return result;
    }


    private Map<String, String> waitingModaldisappearance(){
        try {
            TestUtils.sleepAndLog(2000);
            wait.until(ExpectedConditions.not(presenceOfAllElementsLocatedBy(By.className("" +
                  "modal-backdrop"))));

            wait.until(ExpectedConditions.not(presenceOfAllElementsLocatedBy(By
                  .cssSelector("[modal-window='modal-window']"))));
            return ResultStore.success("No Modal Window");
        } catch (Exception e) {
            return ResultStore.failure("Modal window exists");
        }
        
    }
    
    
    
    // UPLOAD DASHBOARD
    public Map<String, String> uploadChangedStyle(String sCustomCSS) {
        Map<String, String> result;

        try {
//            WebElement cssFiledInput = driver.findElement(By.cssSelector("#customCssField " +
//                  "textarea.ace_text-input"));
//
//            TestUtils.sleep(2);
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            js.executeScript("arguments[0].editor.setValue(\""+ sCustomCSS +"\"), 0;",
//                  cssFiledInput);
            TestUtils.sleep(2);
           /* WebElement cssFiledInput = driver.findElement(By.cssSelector("#customCssField " +
                  "textarea.ace_text-input"));*/
            WebElement cssFiledInput = driver.findElement(By.cssSelector("div.ace_scroller"));
            JavascriptExecutor scroll = (JavascriptExecutor)driver;
            scroll.executeScript("arguments[0].scrollIntoView();", cssFiledInput);
            wait.until(ExpectedConditions.elementToBeClickable(cssFiledInput)).click();
            new Actions(driver).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys
                    (Keys.DELETE).sendKeys(sCustomCSS).build().perform();

            /*cssFiledInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            cssFiledInput.sendKeys(Keys.DELETE);
            cssFiledInput.sendKeys(sCustomCSS);*/
            
            //Detect env type 1 = docker , 0 = saas
            //int dockerEnv = driver.findElements(By.id("bvd-aging")).size();
            // synchronize
            
            /*if (dockerEnv == 1){
               WebElement dataChannelField = driver.findElement(By.cssSelector("[name" +
                     "=\"statsNumberSpinner\"]"));
                WebElement dataRecordField = driver.findElement(By.cssSelector("[name" +
                      "=\"dataRecordsNumberSpinner\"]"));
                dataRecordField.clear();
                dataRecordField.sendKeys("1");
                dataChannelField.clear();
                dataChannelField.sendKeys("1");
                
            }*/
            TestUtils.sleepAndLog(2000);
            wait.until(ExpectedConditions.elementToBeClickable(saveSystemSettings));
            saveSystemSettings.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("slide-system-settings .opr-slide-panel-header")));
            result = ResultStore.success("Custom CSS successfully uploaded");
            TestUtils.sleep(5);

//            if (successDiv.getText().contains("Successfully")) {
//                result = ResultStore.success("Custom CSS successfully uploaded");
//            } else {
//                result = ResultStore.failure("Failed to upload Custom CSS. error: " + successDiv.getText());
//            }
//            closeSystemSettingspanel.click();
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to upload Custom CSS. error: " + Misc.getStacktraceAsString(exc));
        }
        
        return result;
    }
    
    public Map<String, String> deleteChangedStyle() {
        Map<String, String> result;
        
        try {
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            js.executeScript("arguments[0].CodeMirror.setValue(\""+ sCustomCSS +"\");",
//                  cssInputField);
//            js.executeScript("arguments[0].CodeMirror.clearHistory()",
//                  cssInputField);
            TestUtils.sleep(1);
            /*WebElement cssFiledInput = driver.findElement(By.cssSelector("#customCssField " +
                  "textarea.ace_text-input"));*/
            WebElement cssFiledInput = driver.findElement(By.cssSelector("div.ace_scroller"));
            JavascriptExecutor scroll = (JavascriptExecutor)driver;
            scroll.executeScript("arguments[0].scrollIntoView();", cssFiledInput);
            wait.until(ExpectedConditions.elementToBeClickable(cssFiledInput)).click();
            new Actions(driver).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys
                    (Keys.DELETE).build().perform();


            //cssFiledInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            //cssFiledInput.sendKeys(Keys.DELETE);
            
            //Detect env type 1 = docker , 0 = saas
            //int dockerEnv = driver.findElements(By.id("bvd-aging")).size();
            // synchronize
            
            /*if (dockerEnv == 1){
                WebElement dataChannelField = driver.findElement(By.cssSelector("[name" +
                      "=\"statsNumberSpinner\"]"));
                WebElement dataRecordField = driver.findElement(By.cssSelector("[name" +
                      "=\"dataRecordsNumberSpinner\"]"));
                dataRecordField.clear();
                dataRecordField.sendKeys("1");
                dataChannelField.clear();
                dataChannelField.sendKeys("1");
                
            }*/
            wait.until(ExpectedConditions.elementToBeClickable(saveSystemSettings));
            saveSystemSettings.click();
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("slide-system-settings .opr-slide-panel-header")));
            result = ResultStore.success("Custom CSS successfully uploaded");
            TestUtils.sleep(5);
//            if (successDiv.getText().contains("Successfully")) {
//                result = ResultStore.success("Custom CSS successfully uploaded");
//            } else {
//                result = ResultStore.failure("Failed to upload Custom CSS. error: " + successDiv.getText());
//            }
//            closeSystemSettingspanel.click();
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to upload Custom CSS. error: " + Misc.getStacktraceAsString(exc));
        }
        
        return result;
    }
    
    
    // get API key
    public Map<String, String> getAPIKey() {
        Map<String, String> result;

        try {
            waitingModaldisappearance();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("slide-api-key .cg-busy.cg-busy-backdrop.cg-busy-backdrop-animation.ng-scope.ng-hide")));
            wait.until(ExpectedConditions.visibilityOf(div_APIKey));
            String key = div_APIKey.getText();
            result = ResultStore.success(key);

        } catch (Exception exc) {
            result = ResultStore.failure("Failed to get API Key");
        }

        return result;
    }

    // get API key
    public Map<String, String> requestNewAPIKey() {
        Map<String, String> result;

        try {
            String oldkey;
            String newkey;
            WebElement apiKey = driver.findElement(By.id("api_key"));
            oldkey = apiKey.getText();
            wait.until(ExpectedConditions.visibilityOf(button_NewAPIKey));
            button_NewAPIKey.click();
            WebElement window = driver.findElement(By.cssSelector(".modal-header"));
            wait.until(ExpectedConditions.visibilityOf(window));
            WebElement newApiKeyConfirm = driver.findElement(By.cssSelector(".modal-content " +
                  "#new_api_key"));
            wait.until(ExpectedConditions.elementToBeClickable(newApiKeyConfirm));
            newApiKeyConfirm.click();
    
            newkey = apiKey.getText();
            
            if(oldkey.equals(newkey)){
                result = ResultStore.success("New API key not requested");
            }
            result = ResultStore.success("New API key successfully requested");
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to request new API key. error:" + Misc.getStacktraceAsString(exc));
        }

        return result;
    }

    
    public Map<String, String> deleteCustomCSS() {
        Map<String, String> result;
        
        try {

            wait.until(ExpectedConditions.visibilityOf(textarea_CustomCSS));
            wait.until(ExpectedConditions.elementToBeClickable(textarea_CustomCSS));
            textarea_CustomCSS.clear();
            textarea_CustomCSS.click();
    
            //Detect env type 1 = docker , 0 = saas
            //int dockerEnv = driver.findElements(By.id("bvd-aging")).size();
            // synchronize
    
            /*if (dockerEnv == 1){
                WebElement dataChannelField = driver.findElement(By.cssSelector("[name" +
                      "=\"statsNumberSpinner\"]"));
                WebElement dataRecordField = driver.findElement(By.cssSelector("[name" +
                      "=\"dataRecordsNumberSpinner\"]"));
                dataRecordField.clear();
                dataRecordField.sendKeys("1");
                dataChannelField.clear();
                dataChannelField.sendKeys("1");
                
            }*/
            
            wait.until(ExpectedConditions.elementToBeClickable(saveSystemSettings));
            saveSystemSettings.click();
            wait.until(ExpectedConditions.visibilityOf(successDiv));
            
            if (successDiv.getText().contains("Successfully")) {
                result = ResultStore.success("Custom CSS successfully deleted");
            } else {
                result = ResultStore.failure("Failed to delete Custom CSS. error: " + successDiv
                      .getText());
            }
            closeSystemSettingspanel.click();
            driver.navigate().refresh();
        } catch (Exception exc) {
            result = ResultStore.failure("Failed to delete Custom CSS. error: " + Misc
                  .getStacktraceAsString(exc));
        }
        
        return result;
    }
}
