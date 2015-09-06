package com;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
 
public class FilterTester {
	private WebDriver driver;
    private String baseUrl;
	
	@Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://rozetka.com.ua/notebooks/c80004/filter/";
    }
 
	@Test
    public void testManufacturerFilter()throws Exception {
        driver.get(baseUrl);
        FilterPage rozetkaFilter = PageFactory.initElements(driver, FilterPage.class);
        FilterPage filterResults = rozetkaFilter.filterManufacturer("Apple");
        filterResults.verifyManufacturerAndNumberOfModels("Apple");
        filterResults = filterResults.filterManufacturer("Asus");
        filterResults = filterResults.clearOneChosenFilter("Asus");
        filterResults.verifyManufacturerAndNumberOfModels("Apple");
        filterResults = filterResults.clearAllFilters();
    }
	
	@Test
    public void testResolutionFilter()throws Exception {
        driver.get(baseUrl);
        FilterPage rozetkaFilter = PageFactory.initElements(driver, FilterPage.class);
        FilterPage filterResults = rozetkaFilter.filterResolution("1440x900");
        filterResults.verifyResolutionAndNumberOfModels("1440x900");
        filterResults = filterResults.clearAllFilters();
    }
	
	@Test
    public void testScreenFilter()throws Exception {
        driver.get(baseUrl);
        FilterPage rozetkaFilter = PageFactory.initElements(driver, FilterPage.class);
        FilterPage filterResults = rozetkaFilter.filterScreen("18\"-20\"");
        filterResults.verifyScreenAndNumberOfModels("18\"-20\"");
        filterResults = filterResults.clearAllFilters();
    }
	
	@Test
    public void testFilter()throws Exception {
        driver.get(baseUrl);
        FilterPage rozetkaFilter = PageFactory.initElements(driver, FilterPage.class);
        FilterPage filterResults = rozetkaFilter.filterManufacturer("Apple");
        filterResults.verifyManufacturerAndNumberOfModels("Apple");
        filterResults = rozetkaFilter.filterScreen("14\"-15.6\"");
        filterResults.verifyScreenAndNumberOfModels("14\"-15.6\"");
        filterResults = filterResults.clearAllFilters();
    }
	
	@After
    public void tearDown() throws Exception {
        driver.quit();
	}
}