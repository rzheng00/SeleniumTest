package web;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class FirstSample {
	private WebDriver driver;
	private String browser;
	
	@Parameters({"browser"})
    @BeforeClass    
    public void beforeClass(String browserType) {
		browser = browserType;
    	if (browserType.equals("Firefox")) {
	    	System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\geckodriver.exe");
	        driver = new FirefoxDriver();
    	} else if (browserType.equals("Chrome")) {
    		System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
    		driver = new ChromeDriver();
    	} else if (browserType.equals("Internet Explorer")) {
    		System.setProperty("webdriver.ie.driver","src\\test\\resources\\IEDriverServer32.exe");
    		DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
    		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
    		cap.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
    		cap.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
    		cap.setCapability("ignoreProtectedModeSettings", true);
    		cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
    		cap.setCapability("InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION", true);
    	    cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
//    	    cap.setCapability("ignoreZoomSetting", true);
//    	    cap.setCapability("ignoreProtectedModeSettings", true);
    		driver = new InternetExplorerDriver(cap);
    		
    	}
    	driver.manage().window().maximize();
        System.out.println("called beforeClass");
    }

    @AfterClass
    public void afterClass() {
    	System.out.println("called afterClass");
        //driver.quit();
    }
    
    @Test(priority=0)
    public void ssoLogin() throws Exception{
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("https://anduril-test-05.eastus.cloudapp.azure.com/");
        
        if (!browser.equals("Internet Explorer")) {
	        // SSO
	        WebElement username = driver.findElement(By.name("username"));
	        username.sendKeys("503068424");
	        
	        WebElement password = driver.findElement(By.name("password"));
	        password.sendKeys("Jingfx1979sd");
	        
	        WebElement login = driver.findElement(By.id("submitFrm"));
	        login.click();	        
	       
        } else {
        	WebElement more = driver.findElement(By.linkText("More information"));
 	        
 	        if (more !=null) more.click();
 	        
 	        WebElement goOn = driver.findElement(By.partialLinkText("Go on to"));
 	        
 	        if (goOn !=null) goOn.click();
        }
        	
        Thread.sleep(100);
        
        
        
        System.out.println("1st case");
    }
    
    @Test(priority=1)
    public void homePage() throws Exception{  
    	System.out.println("2nd case");

        String search_text = "UserName";
        WebElement username = driver.findElement(By.name("username"));

        String text = username.getAttribute("id");

        Assert.assertEquals(text, search_text, "Text not found!");
        Assert.assertTrue(driver.getTitle().contains("ShiftSelect"));
        System.out.println("2nd case ....done");
    }
    
    @Test(priority=2)
    public void searchWeather() throws Exception{
    	System.out.println("3rd case");
    	System.out.println("current URL:" + driver.getCurrentUrl());
    	String title = driver.getTitle();
    	Assert.assertTrue(title.contains("ShiftSelect"));
    	System.out.println("3rd case ...done");
    }
}
