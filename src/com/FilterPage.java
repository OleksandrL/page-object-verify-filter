package com;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class FilterPage extends Page{
 
    public FilterPage(WebDriver driver) {
        super(driver);
    }
    
    @FindBy (xpath="//*[@name='filter_parameters_block'][2]/descendant::span")
    private List<WebElement> manufacturers; //List of manufacturer
    
    @FindBy (xpath="//*[@name='filter_parameters_block'][3]/descendant::span")
    private  List<WebElement> screens; //List of screen sizes
    
    @FindBy (xpath="//*[@name='filter_parameters_block'][4]/descendant::span")
    private  List<WebElement> resolutions; //List of screen resolutions
    
    @FindBy (xpath="//*[@name='filter_parameters_block'][18]/descendant::span")
    private  List<WebElement> color; //List of colors
    
    @FindBy(className="filter-active-i-link")
    private List <WebElement> activeFilters; //List of active filters
    
    @FindBy(css=".g-i-tile-i-title>a")
    private List<WebElement> listOfModelTitles;  //List of titles of filtered models
    
    @FindBy(name="more_goods")
    private WebElement navigation; //Button to show all models on the one page
    
    @FindBy(xpath="//*[@class='filter-active-l clearfix']/descendant::p")
    private WebElement filterResult;
    
/*-------------------------------------------------------------------------------*/    
    
    public FilterPage filterManufacturer(String filterStatement){
    	boolean found = false;
    	for(WebElement f:manufacturers){
    		if(f.getText().startsWith(filterStatement)){
    			System.out.println(f.getText());
    			f.click();
    			found = true;
    			break;
    		}
    	}
    	if (found == false){
    		throw new IllegalArgumentException("There is no such manufacturer in the list.");
    	}
        return PageFactory.initElements(driver, FilterPage.class);
    }
    
    public FilterPage verifyManufacturerAndNumberOfModels(String titleStatement){
    	int count = 0;
    	verifyActiveFilter(titleStatement,"manufacturers");
    	
    	showAllFoundModels();
    	
    	for (WebElement title: listOfModelTitles){
			if (listOfModelTitles.size()==0){
				throw new RuntimeException("There are no models in the list.");
			}
    		System.out.println(title.getText());
    		Assert.assertTrue("The model #"+(count+1)+" has other manufacturer.", 
    				title.getText().contains(titleStatement));
    		count++;
    		}
    	System.out.println(filterResult.getText());
    	verifyNumberOfModels(count);
    	System.out.println(count);
    	return this;
    }
    
/*----------------------------------------------------------------------------*/
    
    public FilterPage filterResolution(String filterStatement){
    	boolean found = false;
    	for(WebElement f:resolutions){
    		if(f.getText().startsWith(filterStatement)){
    			System.out.println(f.getText());
    			f.click();
    			found = true;
    			break;
    		}
    	}
    	if (found == false){
    		throw new IllegalArgumentException("There is no such resolution in the list.");
    	}
        return PageFactory.initElements(driver, FilterPage.class);
    }
    
    public FilterPage verifyResolutionAndNumberOfModels(String titleStatement){
    	int count = 0;
    	verifyActiveFilter(titleStatement,"resolutions");
    	
    	showAllFoundModels();
    	
    	String titleStatement2 = "";
    	if (titleStatement.equals("Full HD")){
    		titleStatement = "1920x1080";
    		}
    	if (titleStatement.equals("Больше Full HD")){
    		titleStatement2 = titleStatement;
    		titleStatement = "1920x1080";
    	}
    		Pattern resFirstPattern = Pattern.compile("(\\d{4})(?=\\s?[xх]\\s?\\d{3,4})", Pattern.UNICODE_CASE);
    		Matcher resFirstMatcher = resFirstPattern.matcher(titleStatement);
    		Pattern resSecondPattern = Pattern.compile("(?<=\\d{4}\\s?[xх]\\s?)(\\d{3,4})", Pattern.UNICODE_CASE);
    		Matcher resSecondMatcher = resSecondPattern.matcher(titleStatement);
    		resFirstMatcher.find(); 
    		resSecondMatcher.find();
    		int resFirst = Integer.parseInt(resFirstMatcher.group());
    		int resSecond = Integer.parseInt(resSecondMatcher.group());
    		System.out.println(resFirst+"x"+resSecond);
    		for (WebElement title: listOfModelTitles){
    			if (listOfModelTitles.size()==0){
    				throw new RuntimeException("There are no models in the list.");
    			}
    			moveToElement(title);
    			System.out.println(title.getText());
    			WebElement info = getInfoAboutModel(count);
    			resFirstMatcher.reset(info.getText());
    			resSecondMatcher.reset(info.getText());
    			resFirstMatcher.find(); 
    	    	resSecondMatcher.find();
    	    	int resFirstTitle = Integer.parseInt(resFirstMatcher.group());
    	    	int resSecondTitle = Integer.parseInt(resSecondMatcher.group());
    	    	System.out.println(resFirstTitle+"x"+resSecondTitle);
    	    	if(titleStatement2.equals("Больше Full HD")){
    	    		Assert.assertTrue("The model #"+(count+1)+" has other resolution.", 
        					((resFirst < resFirstTitle) && (resSecond < resSecondTitle)));
    	    	}else{
    			Assert.assertTrue("The model #"+(count+1)+" has other resolution.", 
    					((resFirst == resFirstTitle) && (resSecond == resSecondTitle)));
    	    	}
    			count++;
    	}
    		System.out.println(filterResult.getText());
        	verifyNumberOfModels(count);
    	System.out.println(count);
    	return this;
    }
    
/*----------------------------------------------------------------------------*/

    public FilterPage filterColor(String filterStatement){
    	boolean found = false;
    	for(WebElement f:color){
    		if(f.getText().startsWith(filterStatement)){
    			System.out.println(f.getText());
    			f.click();
    			found = true;
    			break;
    		}
    	}
    	if (found == false){
    		throw new IllegalArgumentException("There is no such color in the list.");
    	}
        return PageFactory.initElements(driver, FilterPage.class);
    }

/*---------------------------------------------------------------------------*/  

    public FilterPage filterScreen(String filterStatement){
    	boolean found = false;
    	for(WebElement f:screens){
    		System.out.println(f.getText());
    		if(f.getText().startsWith(filterStatement)){
    			System.out.println(f.getText());
    			f.click();
    			found = true;
    			break;
    		}
    	}
    	if (found == false){
    		throw new IllegalArgumentException("There is no such screen size in the list.");
    	}
        return PageFactory.initElements(driver, FilterPage.class);
    }
    
    public FilterPage verifyScreenAndNumberOfModels(String titleStatement){
    	int count = 0;
    	verifyActiveFilter(titleStatement,"screen sizes");
    	showAllFoundModels();
    		Pattern sizePattern = Pattern.compile("(\\d{1,2}\\.?\\d?)(?=\\\"|''|”)", Pattern.UNICODE_CASE);
    		Matcher sizeMatcher = sizePattern.matcher(titleStatement);
    		List<Double> margins = new LinkedList<Double>();
    		while(sizeMatcher.find()){
    			margins.add(Double.parseDouble(sizeMatcher.group()));
    		}
    		for (WebElement title: listOfModelTitles){
    			if (listOfModelTitles.size()==0){
    				throw new RuntimeException("There are no models in the list.");
    			}
    			moveToElement(title);
    			System.out.println(title.getText());
    			WebElement info = getInfoAboutModel(count);
    			sizeMatcher.reset(info.getText());
    			sizeMatcher.find();
    			double scr = Double.parseDouble(sizeMatcher.group());
    			System.out.println(scr+"\"");
    			if (margins.size()>1){
    				Assert.assertTrue("The model #"+(count+1)+" has other resolution.", ((scr>=margins.get(0))&&(scr<=margins.get(1)+0.1)));
    			}else{
    				Assert.assertTrue("The model #"+(count+1)+" has other resolution.", ((scr>=margins.get(0))&&(scr<=margins.get(0)+0.5)));
    			}
    			count++;
    			}
    		System.out.println(filterResult.getText());
        	verifyNumberOfModels(count);
    	System.out.println(count);
    	return this;
    }
    
/*---------------------------------------------------------------------------*/    
    public FilterPage clearOneChosenFilter(String filterTitle){
    	for (WebElement f: activeFilters){
    		if(f.getText().equals(filterTitle)){
    			f.click();
    			break;
    		}
    	}
    	return PageFactory.initElements(driver, FilterPage.class);
    }
    
    public FilterPage clearAllFilters(){
    	activeFilters.get(activeFilters.size()-1).click();
    	return PageFactory.initElements(driver, FilterPage.class);
    }
    
    private ResultsPage clickOnElement(WebElement element){
    	Actions builder = new Actions(driver);
		Action mouseoverAndClick = builder.moveToElement(element).click(element).build();
		mouseoverAndClick.perform();
    	//((JavascriptExecutor)driver).executeScript("arguments[0].click()", element);
		return PageFactory.initElements(driver, ResultsPage.class);	
    }
    
 /*---------------------------------------------------------------------------*/   

	private void showAllFoundModels() {
		try{
    		while(navigation.isDisplayed()){
    			navigation.click();
    	}
    	}catch (Exception e){}
	}
	

	private void verifyActiveFilter(String titleStatement, String filterType) {
		boolean found = false;
    	for(WebElement f: activeFilters){
    		if(f.getText().startsWith(titleStatement)){
    			found = true;
    			break;
    		}
    	}
    	Assert.assertTrue("Different "+filterType+" in test and in the 'Filter active' block of the page.",
    			found);
	}
    
	private void verifyNumberOfModels(int count) {
		if (!filterResult.getText().contains(" "+count+" ")){
    		throw new RuntimeException("The number of models in 'Filter active' block and in the list of models are not equal.");
    	}
	}
	
	private WebElement getInfoAboutModel(int count) {
		WebElement info = driver.findElement(By.xpath("//*[@class='g-i-tile g-i-tile-catalog']["+(count+1)+"]"));
		return info;
	}
    
    private void moveToElement(WebElement element){
    	Actions builder = new Actions(driver);
		Action mouseOver = builder.moveToElement(element).build();
		mouseOver.perform();
    }
}