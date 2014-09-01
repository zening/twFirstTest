package com.thoughtweb.webdriver;


import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static junit.framework.Assert.assertTrue;

public class FirstTest

{
    public String baseUrl = "http://10.35.249.109:8080/";
    public String servletContext = "twservlet";
    public String kdName = "jMeter";
    public String userName = "Leo Liu";
    public String password = "leol";

    @Test
    public void blandKD()
    {

        WebDriver driver = new FirefoxDriver();
        driver.get(baseUrl + "/" + servletContext);
        assertTrue(driver.getTitle().startsWith("ThoughtWeb Login"));

        driver.findElement(By.id("j_username")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

        driver.findElement(By.cssSelector("div.portalLinks a[href=\"/"+servletContext+"/app/admin/\"]")).click();
        driver.findElement(By.cssSelector("select#AvailableKDs option[value=\""+kdName+"\"]")).click();

        //connect kd to Pooled box
        driver.findElement(By.cssSelector("div.div2 .submitButton")).click();

        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        //click on the kd to build schema
        driver.findElement(By.cssSelector("select#PooledKDs option[value=\""+kdName+"\"]")).click();


        driver.findElement(By.cssSelector("input[value=\"Build Schema\"]")).click();

        String parentHandle = driver.getWindowHandle();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        //on popup build schema, then back to main window.
        driver.findElement(By.id("buildSchemaBtn")).click();
        driver.close();
        driver.switchTo().window(parentHandle);


        assertTrue(driver.getTitle().startsWith("ThoughtWeb Management Console"));
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("select#PooledKDs option[value=\"" + kdName + "\"]")));

        myDynamicElement.click();
        driver.findElement(By.cssSelector("select#PooledKDs option[value=\""+kdName+"\"]")).click();
        driver.findElement(By.cssSelector("input[value=\"Import KD\"]")).click();


        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        driver.findElement(By.id("newKdBtn")).click();
        driver.close();
        driver.switchTo().window(parentHandle);

        try {
            Thread.sleep(2000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        //start kd
        assertTrue(driver.getTitle().startsWith("ThoughtWeb Management Console"));

        ////driver.findElement(By.cssSelector("input[value=\"Start Pooled KD\"]")).click();

        try {
            Thread.sleep(2000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        driver.findElement(By.cssSelector("div.adminConsole a#LogoutButton")).click();

//        WebElement signOutBtn = (new WebDriverWait(driver, 10))
//                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#header a#LogoutButton")));

        //signOutBtn.click();
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        driver.get(baseUrl + "/" + servletContext);

        driver.findElement(By.cssSelector("div#header a#LogoutButton")).click();

        driver.quit();

        //StartKD();

        //LoginKD();

    }

    @Test
    public void StartKD(){

        System.setProperty("webdriver.chrome.driver", "C://seleniumIDE//chrome/chromedriver.exe");


        ChromeOptions options = new ChromeOptions();
        //options.addArguments("disable-web-security");
        //options.addArguments("start-maximized");
        options.addArguments("test-type");

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        ChromeDriver driver = new ChromeDriver(options);

        userName = "appman";
        password = "J@Jwuth2f@p0wJf";
        driver.get(baseUrl + "/" + servletContext);
        assertTrue(driver.getTitle().startsWith("ThoughtWeb Login"));
        driver.findElement(By.id("j_username")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        driver.findElement(By.cssSelector("div.portalLinks a[href=\"/"+servletContext+"/app/admin/\"]")).click();

        driver.findElement(By.cssSelector("select#PooledKDs option[value=\"" + kdName + "\"]")).click();
        driver.findElement(By.cssSelector("input[value=\"Start Pooled KD\"]")).click();

        driver.quit();

    }

    @Test
    public void LoginKD(){
        System.setProperty("webdriver.chrome.driver", "C://seleniumIDE//chrome/chromedriver.exe");


        ChromeOptions options = new ChromeOptions();
        //options.addArguments("disable-web-security");
        //options.addArguments("start-maximized");
        options.addArguments("test-type");

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        ChromeDriver driver = new ChromeDriver(options);

        driver.get(baseUrl + "/" + servletContext);
        userName = "appman";
        password = "J@Jwuth2f@p0wJf";
        driver.get(baseUrl + "/" + servletContext);
        assertTrue(driver.getTitle().startsWith("ThoughtWeb Login"));
        driver.findElement(By.id("j_username")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

        driver.findElement(By.cssSelector("div.portalLinks a[href=\"/"+servletContext+"/app/"+kdName+"/\"]")).click();

        driver.findElement(By.cssSelector("td.nav img[name='Build']")).click();


    }
}
