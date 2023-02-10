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
public class ExploreComplexChartPage {

    final WebDriver driver;
    final WebDriverWait wait;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Hosts')]")
    static WebElement hostDrag;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),' Avg CPU Baseline Chart ')]")
    static WebElement avgCpuBaselineChartDrag;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),' AVG CPU LOAD ')]")
    static WebElement avgCpuLoadDrag;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),' MAX CPU LOAD ')]")
    static WebElement maxCpuLoadDrag;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),' MIN CPU LOAD ')]")
    static WebElement minCpuLoad;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),' Average CPU Utilization With Change Marker ')]")
    static WebElement avgCpuUtilizationDrag;

    public ExploreComplexChartPage(WebDriver driver){

        this.driver = driver;
        this.wait = new WebDriverWait(driver,30);
    }

    public Map<String, String> checkForHostDrag(){
        Map<String, String> result;

        try{

            wait.until(ExpectedConditions.visibilityOf(hostDrag));
            result = ResultStore.success("Host is present");
        }
        catch (Exception exc)
        {
            result = ResultStore.failure("Host is not present:" + Misc.getStacktraceAsString(exc));
        }
        return result;
    }

}
