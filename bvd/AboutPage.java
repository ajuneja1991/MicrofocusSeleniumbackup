package com.hp.opr.qa.framework.pageobjects.bvd;

import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

/**
 * Created by Eva Sokolyanskaya on 06/06/2016.
 * BVD About Page Objects
 */
public class AboutPage
{
  final WebDriver driver;
  final WebDriverWait wait;
  String xpath;
  
  // License Type
  @FindBy(how = How.XPATH, using = "//li[contains(text(),'Type:')]")
  static WebElement licenseType;

  // Allowed number of dashboards
  @FindBy(how = How.XPATH, using = "//li[contains(text(),'Allowed number of dashboards:')]")
  static WebElement allowedNumberOfDashboards;

  // Number of uploaded dashboards
  @FindBy(how = How.XPATH, using = "//li[contains(text(),'Number of uploaded dashboards:')]")
  static WebElement numberOfUploadedDashboards;

  // Server Info
  @FindBy(how = How.XPATH, using = "//li[contains(@ng-show,'serverInfo.')]")
  static WebElement serverInfo;

  public AboutPage(WebDriver driver) {
    this.wait = new WebDriverWait(driver, 15);
    this.driver = driver;
  }
    
  public Map<String, String> getLicenseType(){
    Map<String, String> result;
    try
    {
      wait.until(ExpectedConditions.visibilityOf(licenseType));
      result = ResultStore.success(licenseType.getText());
    } catch (Exception exc){
      
      result = ResultStore.failure("License type is not available. error: " +
            Misc.getStacktraceAsString(exc));
      
    }
    return result;
  }

  public Map<String, String> getAllowedNumberOfDashboards(){
    Map<String, String> result;
    try
    {
      
      xpath = "//li[contains(text(),'Allowed number of dashboards:')]";
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
      WebElement element = driver.findElement(By.xpath(xpath));
      
      int number = Integer.parseInt(element.getText().replaceAll("\\D+", ""));
  
      result = ResultStore.success(Integer.toString(number));
    } catch (Exception exc){
  
      result = ResultStore.failure("Allowed number of dashboards is not available. error: " +
            Misc.getStacktraceAsString(exc));
  
    }
    return result;
  }
  
  public Map<String, String> getNumberOfUploadedDashboards(){
    Map<String, String> result;
    try
    {
      wait.until(ExpectedConditions.visibilityOf(numberOfUploadedDashboards));
      int number = Integer.parseInt(numberOfUploadedDashboards.getText().replaceAll("\\D+", ""));
      
      result = ResultStore.success(Integer.toString(number));
    } catch (Exception exc){
      
      result = ResultStore.failure("Number of uploaded dashboards ai not available. error: " +
            Misc.getStacktraceAsString(exc));
      
    }
    return result;
  }
  
  public Map<String, String> getExpiredLicenseData(){
    Map<String, String> result;
    try
    {
      
      xpath = ".//div[h4[contains(text(),'Expired License Information')]]/ul/li";
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
      WebElement element = driver.findElement(By.xpath(xpath));
            
      result = ResultStore.success(element.getText());
    } catch (Exception exc){
      
      result = ResultStore.failure("Allowed number of dashboards is not available. error: " +
            Misc.getStacktraceAsString(exc));
      
    }
    return result;
  }
  
  // GET NUMBER OF UPLOADED DASHBOARDS
  public Map<String, String> getNumberOfUploadedDashboards1() {
    Map<String, String> result;
    
    try {
      xpath = "//li[contains(text(),'Number of uploaded dashboards:')]";
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
      String text = driver.findElement(By.xpath(xpath)).getText();
      int number = Integer.parseInt(text.replaceAll("\\D+",""));
      
      result = ResultStore.success(Integer.toString(number));
      
    } catch (Exception exc) {
      result = ResultStore.failure("Number of uploaded dashboards is unavailable. error: " + Misc
            .getStacktraceAsString(exc));
      
    }
    return result;
  }

  public String getServerInfo(){
    return serverInfo.getText();
  }
  
  public Map<String, String> refresh() {
    Map<String, String> result;
    
    try {
      driver.navigate().refresh();
      
      result = ResultStore.success("Refreshed.");
    }
    catch (Exception exc)
    {
      result = ResultStore.failure("Not refreshed. error: " + Misc
            .getStacktraceAsString(exc));
    }
    return result;
  }
}
