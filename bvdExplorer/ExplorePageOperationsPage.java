package com.hp.opr.qa.framework.pageobjects.bvdExplorer;

import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.runner.Result;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import com.hp.opr.qa.framework.pageobjects.bvd.ExploreCommonPage;

import java.util.List;
import java.util.Map;


public class ExplorePageOperationsPage  extends ExploreMainPage{

    final WebDriver driver;
    final WebDriverWait wait;
    
     @FindBy(how = How.CSS, using = "button.edit-icon")
    private WebElement button_EditDashboarIcon;
    
    //edit page toolbar
    
  //*[@id="split-button-toggle"]
    
   // @FindBy(how = How.XPATH, using = "//*[@id='split-button-toggle']")
    @FindBy(how = How.XPATH, using = "//b[text()='OPEN']")
    private WebElement button_Open;

    @FindBy(how = How.XPATH, using = "//b[text()='SAVE']")
    private WebElement button_Save;

    @FindBy(how = How.XPATH, using = "//b[text()='SAVE AS']")
    private WebElement button_SaveAs;

    //@FindBy(how = How.XPATH, using = "//button/*[@class='qtm-font-icon qtm-icon-revert']")
     
    @FindBy(how = How.XPATH, using = "//button[@popoverclass='popover-arrow revert-popover']")
    private WebElement button_Revert;

    @FindBy(how = How.XPATH, using = "//button/*[@class='hpe-icon hpe-close']")
    private WebElement button_Close;
    
    @FindBy(how = How.CSS, using = "div.view-container")
    private WebElement mondrianPage; //middle part
    
    @FindBy(how = How.XPATH, using = "//button[contains(@class,'spannerIcon')]/i")
    private List<WebElement> buttons_Spanner;
    
    @FindBy(how = How.XPATH, using = "//button[contains(@class,'spannerIcon')]/i")
    private WebElement button_First_Spanner;

    @FindBy(how = How.XPATH, using = "//ux-tabset/descendant::span[@class='ux-icon ux-icon-drag']")
    private List<WebElement> icons_DragAndDrop;
    
    @FindBy(how = How.XPATH, using = "//a/span[text()='Remove Chart']")
    private WebElement a_removeChart;

    @FindBy(how = How.XPATH, using = "//a/span[text()='Duplicate Chart']")
    private WebElement a_duplicateChart;
    
      
    public ExplorePageOperationsPage(WebDriver driver)
    {
    	super(driver);
          
    	this.driver = driver;
    	this.wait = new WebDriverWait(driver, 40);
    }
    
         public Map<String,String> clickEditPageIcon(){
	   Map<String, String> result;
	   try{
		   waitAndClick(button_EditDashboarIcon);
		   wait.until(ExpectedConditions.visibilityOf(button_Open));
		   
		   result = ResultStore.success("Successfully clicked on Edit Dashbaord Icon");
		   
	   }catch(Exception exc){
		   exc.printStackTrace();
		   result = ResultStore.failure("Failed to click on Edit dashbaord Icon,error: " + Misc.getStacktraceAsString(exc));
		   
	   }
	   return result;
   }
            
         
         public Map<String,String> checkEditPageToolbarElements(){
      	   Map<String, String> result;
      	 
      	   try{
      		 wait.until(ExpectedConditions.visibilityOf(button_Open));
      		result = ResultStore.success("Button Open is visible");
      		System.out.println("Button Open is visible");
      		
      		wait.until(ExpectedConditions.visibilityOf(button_Save));
      		result = ResultStore.success("Button Save is visible");
      		System.out.println("Button Save is visible");
      		
      		wait.until(ExpectedConditions.visibilityOf(button_SaveAs));
      		result = ResultStore.success("Button SaveAs is visible");
      		System.out.println("Button Saveas is visible");
      		
      		wait.until(ExpectedConditions.visibilityOf(button_Revert));
      		result = ResultStore.success("Button Revert is visible");
      		System.out.println("Button Revert is visible");
      		
      		wait.until(ExpectedConditions.visibilityOf(button_Close));
      		result = ResultStore.success("Button Close is visible");
      		System.out.println("Button close is visible");
      		   
      		   
      	   }catch(Exception exc){
      		   exc.printStackTrace();
      		   result = ResultStore.failure("checkEditPageToolbarElements,error: " + Misc.getStacktraceAsString(exc));
      		   
      	   }
      	   return result;
         }
         
         public Map<String,String> duplicateWidget(){
      	   Map<String, String> result;
      	   int widget_count_before_duplicate,widget_count_after_duplicate;
      	   try{
      		 widget_count_before_duplicate= mondrianPageWidgets.size();
      		   
      		   System.out.println("----Before duplicate "+widget_count_before_duplicate);
      		  result = ResultStore.success("Checked mondrian page widget count before duplicate chart");
      		  
      		    waitAndClick(button_First_Spanner);     		   
      		   result = ResultStore.success("Clicked on first spanner successfully");
      		   
      		   waitAndClick(a_duplicateChart);
      		   delay(500);
      		 
      		  result = ResultStore.success("Clicked on first spanner duplciate chart option successfully");
      		  
      		widget_count_after_duplicate= mondrianPageWidgets.size();
      		  
      		System.out.println("----after duplicate "+widget_count_after_duplicate);
      		 result = ResultStore.success("Checked mondrian page widget count after duplicate chart");
      		 
      		 if(widget_count_after_duplicate==widget_count_before_duplicate+1) {
      			result = ResultStore.success("Duplicate chart functionality working fine");
      		 }else{
      			result = ResultStore.failure("Duplicate chart functionality not working");
      		 }
      		   
      		   
      	   }catch(Exception exc){
      		   exc.printStackTrace();
      		   result = ResultStore.failure("duplicateWidget,error: " + Misc.getStacktraceAsString(exc));
      		   
      	   }
      	   return result;
         }
         
         public Map<String,String> removeWidget(){
        	   Map<String, String> result;
        	   int widget_count_before_remove,widget_count_after_remove;
        	   try{
        		         		   
        		   widget_count_before_remove= mondrianPageWidgets.size();
          		   System.out.println("----Before remove chart"+widget_count_before_remove);
          		   
          		  result = ResultStore.success("Checked mondrian page widget count before remove chart");
          		  
        		    waitAndClick(buttons_Spanner.get(1));     		   
        		   result = ResultStore.success("Clicked on second spanner successfully");
        		   
        		   waitAndClick(a_removeChart);
        		   
        		   delay(500);
        		   
        		   widget_count_after_remove = mondrianPageWidgets.size();
        		   
        		   System.out.println("----after remove chart"+widget_count_after_remove);
        		   
        		   if(widget_count_after_remove==widget_count_before_remove-1) {
             			result = ResultStore.success("Remove chart functionality working fine");
             		 }else{
             			result = ResultStore.failure("Remove chart functionality not working");
             		 }
        		   result = ResultStore.success("Clicked on second spanner remvoe chart option successfully");
        		   
        	   }catch(Exception exc){
        		   exc.printStackTrace();
        		   result = ResultStore.failure("RemoveWidget,error: " + Misc.getStacktraceAsString(exc));
        		   
        	   }
        	   return result;
           }
   
   
       public Map<String,String> dragAndDropChart(){
	   Map<String, String> result;
	   int widget_count_before_drag,widget_count_after_drag;
	   try{
		   delay(5000);
		    wait.until(ExpectedConditions.visibilityOf(icons_DragAndDrop.get(0)));
		  	wait.until(ExpectedConditions.elementToBeClickable(icons_DragAndDrop.get(0)));
		  	
		  	widget_count_before_drag= mondrianPageWidgets.size();
    		   System.out.println("----Before drag and drop "+widget_count_before_drag);
    		   
		      dragAndDrop(icons_DragAndDrop.get(0), mondrianPage);
		      
		      widget_count_after_drag= mondrianPageWidgets.size();
   		   System.out.println("----after drag and drop "+widget_count_before_drag);
   		   
   		 if(widget_count_after_drag==widget_count_before_drag+1) {
   			result = ResultStore.success("Drag and drop functionality working fine");
   		 }else{
   			result = ResultStore.failure("Drag and drop functionality not working");
   		 }
   		      
		   
		   result = ResultStore.success("Drap and drop successful");
		   
	   }catch(Exception exc){
		   result = ResultStore.failure("Drap and drop not successful " + Misc.getStacktraceAsString(exc));
		   exc.printStackTrace();
	   }
	   return result;
   }
       
       public Map<String,String> checkWidgetResize(){
    	   Map<String, String> result;
    	   try{
    		   
    		    resize(mondrianPageWidgets.get(1),100,1000);
    		       
    		    
    		   result = ResultStore.success("resize");
    		   
    	   }catch(Exception exc){
    		   exc.printStackTrace();
    		   result = ResultStore.failure("resize " + Misc.getStacktraceAsString(exc));
    		   
    	   }
    	   return result;
       }
       
       
       
       public Map<String,String> checkRevert(){
    	   Map<String, String> result;
    	   int widget_count_before_revert,widget_count_after_revert;
    	   try{
    		       		   
    		   duplicateWidget();
    		   delay(500);
    		   widget_count_before_revert = mondrianPageWidgets.size();
    		   System.out.println("------before revert "+widget_count_before_revert);
    		   
    		   waitAndClick(button_Revert);
    		   delay(500);
    		   
    		   widget_count_after_revert = mondrianPageWidgets.size();
    		    
    		    System.out.println("------rever "+widget_count_after_revert);
    		    if(widget_count_after_revert==widget_count_before_revert-1){
    		    	result = ResultStore.success("Revert is succesful");
    		    }else{
    		    	result = ResultStore.success("Revert operation is not succesful");
    		    }
    		       		   
    		   
    	   }catch(Exception exc){
    		   exc.printStackTrace();
    		   result = ResultStore.failure("Revert is successful " + Misc.getStacktraceAsString(exc));
    		   
    	   }
    	   return result;
       }
       
       public Map<String,String> saveDashboard(){
    	   Map<String, String> result;
    	   try{
    		  //actionWaitAndClick(button_Save); //after drag and drop operation the toolbar list is going up since side its scrolling
    		   waitAndClick(button_Save);
    		   
    		   result = ResultStore.success("click save dashbaord");
    		   
    	   }catch(Exception exc){
    		   exc.printStackTrace();
    		   result = ResultStore.failure("click save dashbaord,error " + Misc.getStacktraceAsString(exc));
    		   
    	   }
    	   return result;
       }
    
    
}



