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

public class ExploreCommonPage {
	
	protected WebDriver driver;
	  protected WebDriverWait wait;
	  protected WebDriverWait short_wait;
	  protected WebDriverWait long_wait;
	 protected final int timeout = 40;
	  protected final int short_timeout = 15;
	  protected final int long_timeout = 90;

	  protected ExploreCommonPage()  { }
	  
	  protected ExploreCommonPage(WebDriver driver)
	  {
	    this.driver = driver;
	    this.wait = new WebDriverWait(driver, timeout);
	    this.short_wait = new WebDriverWait(driver, short_timeout);
	    this.long_wait = new WebDriverWait(driver, long_timeout);
	  }

	  protected void waitAndClick(WebElement elem)
	  	{
		  
	  	wait.until(ExpectedConditions.visibilityOf(elem));
	  	wait.until(ExpectedConditions.elementToBeClickable(elem));
	  	elem.click();
	  }
	  
	  protected void actionWaitAndClick(WebElement element){
			 				
					Actions ob = new Actions(driver);
					/*short_wait.until(ExpectedConditions.visibilityOf(element));
					short_wait.until(ExpectedConditions.elementToBeClickable(element));*/
		           ob.click(element).build().perform();
		}

	  
	  protected void delay(int MilliSec) {
          try {
                 Thread.sleep(MilliSec);
          } catch (Exception e) {
                 e.printStackTrace();
                 
          }

   }
	  
	  protected boolean isListExists(List<WebElement> elementList){
			boolean flag=false;
			try{
				if(elementList.size()!=0){
					flag=true;
				}
				else{
					flag=false;
					
				    }
			}catch(Exception e){
				e.printStackTrace();
								
			}
			return flag;
						
		}
		
	  
	  protected void dragAndDrop(WebElement source,WebElement target){

		  
		  Actions ob = new Actions(driver);
		  short_wait.until(ExpectedConditions.visibilityOf(source));
		  short_wait.until(ExpectedConditions.visibilityOf(target));
		  //ob.dragAndDrop(source, target).perform();
		  
		  ob.clickAndHold(source).moveToElement(target).release(target).build().perform();
			
		 /* Action dragAndDropAction = ob.clickAndHold(source)
			   .moveToElement(target)
			   .release(target)
			   .build();
			dragAndDropAction.perform();*/
	  
	 	}
	  protected void resize(WebElement ele,int xOffset,int yOffset){
		  
	       Actions ob =new Actions(driver); 
			  ob.clickAndHold(ele).moveByOffset(xOffset,yOffset).release().build().perform();

	  
	  }
	  
	  
	  protected void moveVerticalScrollbar(WebElement ele,int yPixels){
		  Actions ob = new Actions(driver);
		  	  
		  	ob.moveToElement(ele).clickAndHold().moveByOffset(0,yPixels).release().perform();
	    }
	  
	  protected void moveHorizontalScrollbar(WebElement ele,int xPixels){
		  Actions ob = new Actions(driver);
		  	  
		  	ob.moveToElement(ele).clickAndHold().moveByOffset(xPixels,0).release().perform();
	    }
	  
	  }
