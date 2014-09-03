package com.thoughtweb.webdriver;

//import org.junit.Test;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class FirstTest

{
    public String baseUrl = "http://10.35.249.109:8080/";
    public String servletContext = "twservlet";
    public String kdName = "jMeter";
    public String userName = "appman";
    public String password = "J@Jwuth2f@p0wJf";

    @Test(priority = 0, enabled = true)
    public void blandKD() {

        WebDriver driver = new FirefoxDriver();
        driver.get(baseUrl + "/" + servletContext);
        assertTrue(driver.getTitle().startsWith("ThoughtWeb Login"));

        driver.findElement(By.id("j_username")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

        // start here
        driver.findElement(By.cssSelector("div.portalLinks a[href=\"/"+servletContext+"/app/admin/\"]")).click();

        // stop it first if running
        if(!driver.findElements(By.cssSelector("select#RunningKDs option[value=\""+kdName+"\"]")).isEmpty()){
            driver.findElement(By.cssSelector("select#RunningKDs option[value=\""+kdName+"\"]")).click();
            driver.findElement(By.cssSelector("input.submitButton[value=\"Stop KD\"]")).click();
        }

        try {
            Thread.sleep(5000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

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

        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        driver.get(baseUrl + "/" + servletContext);

        driver.findElement(By.cssSelector("div#header a#LogoutButton")).click();

        driver.quit();

    }

    @Test(priority = 10, enabled = true)
    public void startKD(){

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

    @Test(priority = 20, enabled = true)
    public void loginKD_createConcepts(){
        System.setProperty("webdriver.chrome.driver", "C://seleniumIDE//chrome/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        //options.addArguments("disable-web-security");
        //options.addArguments("start-maximized");
        options.addArguments("test-type");

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        WebDriver driver = new ChromeDriver(options);

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

        driver.switchTo().frame("Content");
        driver.findElement(By.cssSelector("body > a:nth-child(12)")).click();

        String parentHandle = driver.getWindowHandle();

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        driver.findElement(By.name("ConceptName")).sendKeys("A");
        driver.findElement(By.name("ConceptPlural")).sendKeys("A");
        driver.findElement(By.name("ConceptMapY")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        driver.findElement(By.name("ConceptMapY")).sendKeys("200");
        driver.findElement(By.cssSelector("body > form:nth-child(4) > table:nth-child(10) > tbody > tr:nth-child(2) > td > input[type=\"radio\"]:nth-child(5)")).click();
        driver.findElement(By.id("btnfmConceptProperties1")).click();
        driver.switchTo().window(parentHandle); // back to the main window

        driver.switchTo().frame("Content");
        driver.findElement(By.cssSelector("body > a:nth-child(12)")).click();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        driver.findElement(By.name("ConceptName")).sendKeys("B");
        driver.findElement(By.name("ConceptPlural")).sendKeys("B");
        driver.findElement(By.name("ConceptMapX")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        driver.findElement(By.name("ConceptMapX")).sendKeys("300");
        driver.findElement(By.name("ConceptMapY")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        driver.findElement(By.name("ConceptMapY")).sendKeys("200");
        driver.findElement(By.cssSelector("body > form:nth-child(4) > table:nth-child(10) > tbody > tr:nth-child(2) > td > input[type=\"radio\"]:nth-child(5)")).click();
        driver.findElement(By.id("btnfmConceptProperties1")).click();

        driver.switchTo().window(parentHandle); // back to the main window
        driver.switchTo().frame("Content");
        driver.findElement(By.cssSelector("body > a:nth-child(12)")).click();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        driver.findElement(By.name("ConceptName")).sendKeys("C");
        driver.findElement(By.name("ConceptPlural")).sendKeys("C");
        driver.findElement(By.name("ConceptMapX")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        driver.findElement(By.name("ConceptMapX")).sendKeys("300");
        driver.findElement(By.cssSelector("body > form:nth-child(4) > table:nth-child(10) > tbody > tr:nth-child(2) > td > input[type=\"radio\"]:nth-child(5)")).click();
        driver.findElement(By.id("btnfmConceptProperties1")).click();
        driver.switchTo().window(parentHandle); // back to the main window
        driver.switchTo().frame("Content");

        driver.findElement(By.xpath("/html/body/a[1]")).click();

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        driver.findElement(By.cssSelector("#ConceptNoFROM0 > option:nth-child(5)")).click();
        driver.findElement(By.cssSelector("#ConceptNoTO0 > option:nth-child(2)")).click();

        driver.findElement(By.cssSelector("#ConceptNoFROM1 > option:nth-child(5)")).click();
        driver.findElement(By.cssSelector("#ConceptNoTO1 > option:nth-child(4)")).click();

        driver.findElement(By.cssSelector("#ConceptNoFROM2 > option:nth-child(2)")).click();
        driver.findElement(By.cssSelector("#ConceptNoTO2 > option:nth-child(3)")).click();
        driver.findElement(By.cssSelector("#row_2 > td:nth-child(8) > input[type=\"radio\"]")).click();


        driver.findElement(By.cssSelector("#ConceptNoFROM3 > option:nth-child(4)")).click();
        driver.findElement(By.cssSelector("#ConceptNoTO3 > option:nth-child(3)")).click();
        driver.findElement(By.cssSelector("#row_3 > td:nth-child(9) > input[type=\"radio\"]")).click();

        driver.findElement(By.cssSelector("#btnfmConceptConnections1")).click();

        driver.quit();

    }

    @Test(priority = 30, enabled = true)
    public void createObjects(){
        System.setProperty("webdriver.chrome.driver", "C://seleniumIDE//chrome/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        //options.addArguments("disable-web-security");
        //options.addArguments("start-maximized");
        options.addArguments("test-type");

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        WebDriver driver = new ChromeDriver(options);

        driver.get(baseUrl + "/" + servletContext);
        assertTrue(driver.getTitle().startsWith("ThoughtWeb Login"));
        driver.findElement(By.id("j_username")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

        driver.findElement(By.cssSelector("div.portalLinks a[href=\"/"+servletContext+"/app/"+kdName+"/\"]")).click();

        driver.findElement(By.cssSelector("td.nav img[name='Build']")).click();

        driver.switchTo().frame("Content");
        driver.findElement(By.cssSelector("body > center > map > area:nth-child(2)")).click();
        driver.findElement(By.cssSelector("body > center > input[type=\"button\"]")).click();

        String parentHandle = driver.getWindowHandle();

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        driver.findElement(By.cssSelector("#ShortName")).sendKeys("a1");
        driver.findElement(By.cssSelector("#fmWizard > table:nth-child(3) > tbody > tr > td > b > a:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#ShortName")).sendKeys("a2");
        driver.findElement(By.cssSelector("#fmWizard > table:nth-child(3) > tbody > tr > td > b > a:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#ShortName")).sendKeys("a3");
        driver.findElement(By.cssSelector("#fmWizard > table:nth-child(2) > tbody > tr > td > input[type=\"button\"]:nth-child(6)")).click();

        driver.switchTo().window(parentHandle); // back to the main window
        driver.findElement(By.cssSelector("td.nav img[name='Build']")).click();
        driver.switchTo().frame("Content");
        driver.findElement(By.cssSelector("body > center > map > area:nth-child(3)")).click();
        driver.findElement(By.cssSelector("body > center > input[type=\"button\"]")).click();


        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        driver.findElement(By.cssSelector("#ShortName")).sendKeys("b1");
        driver.findElement(By.cssSelector("#fmWizard > table:nth-child(3) > tbody > tr > td > b > a:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#ShortName")).sendKeys("b2");
        driver.findElement(By.cssSelector("#fmWizard > table:nth-child(3) > tbody > tr > td > b > a:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#ShortName")).sendKeys("b3");
        driver.findElement(By.cssSelector("#fmWizard > table:nth-child(2) > tbody > tr > td > input[type=\"button\"]:nth-child(6)")).click();

        driver.switchTo().window(parentHandle); // back to the main window
        driver.findElement(By.cssSelector("td.nav img[name='Build']")).click();
        driver.switchTo().frame("Content");
        driver.findElement(By.cssSelector("body > center > map > area:nth-child(4)")).click();
        driver.findElement(By.cssSelector("body > center > input[type=\"button\"]")).click();


        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        driver.findElement(By.cssSelector("#ShortName")).sendKeys("c1");
        driver.findElement(By.cssSelector("#fmWizard > table:nth-child(3) > tbody > tr > td > b > a:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#ShortName")).sendKeys("c2");
        driver.findElement(By.cssSelector("#fmWizard > table:nth-child(3) > tbody > tr > td > b > a:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#ShortName")).sendKeys("c3");
        driver.findElement(By.cssSelector("#fmWizard > table:nth-child(2) > tbody > tr > td > input[type=\"button\"]:nth-child(6)")).click();

        driver.switchTo().window(parentHandle); // back to the main window
        driver.findElement(By.cssSelector("td.nav img[name='Build']")).click();
        driver.switchTo().frame("Content");

        // open A KS
        driver.findElement(By.cssSelector("body > center > map > area:nth-child(1)")).click();
        // click on a1 edit
        driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.Center.rowOdd > td.KsName > a:nth-child(1) > img")).click();

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        // Center object connections
        driver.findElement(By.cssSelector("#fmWizard > table:nth-child(3) > tbody > tr > td > b > a:nth-child(1)")).click();

        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(3) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(3)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(4) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(2)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(5) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(8) > td:nth-child(4) > center > input[type=\"RADIO\"]:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(9) > td:nth-child(4) > center > input[type=\"RADIO\"]:nth-child(2)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(10) > td:nth-child(4) > center > input[type=\"RADIO\"]:nth-child(3)")).click();

        driver.findElement(By.cssSelector("#fmWizard > table:nth-child(2) > tbody > tr > td:nth-child(2) > input[type=\"button\"]:nth-child(6)")).click();

        driver.switchTo().window(parentHandle); // back to the main window
        driver.findElement(By.cssSelector("td.nav img[name='Build']")).click();
        driver.switchTo().frame("Content");

        //open B KS
        driver.findElement(By.cssSelector("body > center > map > area:nth-child(3)")).click();
        driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b1.rowOdd > td.KsName > a:nth-child(1) > img")).click();

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        // b1 connections
        driver.findElement(By.cssSelector("#fmWizard > table:nth-child(3) > tbody > tr > td > b > a:nth-child(2)")).click();

        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(3) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(4) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(5) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(1)")).click();

        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(8) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(3)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(9) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(10) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(1)")).click();

        driver.findElement(By.cssSelector("#fmWizard > table:nth-child(3) > tbody > tr > td > b > a:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(3) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(2)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(4) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(5) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(1)")).click();

        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(8) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(2)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(9) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(10) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(1)")).click();

        driver.findElement(By.cssSelector("#fmWizard > table:nth-child(3) > tbody > tr > td > b > a:nth-child(2)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(3) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(3)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(4) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(5) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(1)")).click();

        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(8) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(9) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(10) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(1)")).click();

        driver.findElement(By.cssSelector("#fmWizard > table:nth-child(2) > tbody > tr > td:nth-child(2) > input[type=\"button\"]:nth-child(6)")).click();

        driver.switchTo().window(parentHandle); // back to the main window
        driver.findElement(By.cssSelector("td.nav img[name='Build']")).click();
        driver.switchTo().frame("Content");

        driver.close();

    }

    @Test(priority = 40, enabled = true)
    public void checkFirst_I_value(){
        System.setProperty("webdriver.chrome.driver", "C://seleniumIDE//chrome/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        //options.addArguments("disable-web-security");
        //options.addArguments("start-maximized");
        options.addArguments("test-type");

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        WebDriver driver = new ChromeDriver(options);

        driver.get(baseUrl + "/" + servletContext);
        assertTrue(driver.getTitle().startsWith("ThoughtWeb Login"));
        driver.findElement(By.id("j_username")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

        driver.findElement(By.cssSelector("div.portalLinks a[href=\"/"+servletContext+"/app/"+kdName+"/\"]")).click();


        // from the topic menu
        driver.findElement(By.cssSelector("#Topicsmenu > table > tbody > tr:nth-child(1) > td")).click();
        driver.findElement(By.cssSelector("#TopicsContents > a:nth-child(2) > div > img")).click();
        String parentHandle = driver.getWindowHandle();

        driver.switchTo().frame("Content");
        //check A i value
        // a1   70
        // a2   80
        // a3   100
        String a1_i = driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.a1.rowOdd > td.IValue > a > img")).getAttribute("title");
        assertEquals("70", a1_i);

        String a2_i = driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.a2.rowEven > td.IValue > a > img")).getAttribute("title");
        assertEquals("80", a2_i);

        String a3_i = driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.a3.rowOdd > td.IValue > a > img")).getAttribute("title");
        assertEquals("100", a3_i);


        driver.switchTo().window(parentHandle);
        //driver.findElement(By.cssSelector("#Topicsmenu > table > tbody > tr:nth-child(1) > td")).click();
        driver.findElement(By.cssSelector("#TopicsContents > a:nth-child(3) > div > img")).click();
        driver.switchTo().frame("Content");
        //check B i value
        // b1   96.56
        // b2   96.24
        // b3   100
        String b1_i = driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b1.rowOdd > td.IValue > a > img")).getAttribute("title");
        assertEquals("96.56", b1_i);

        String b2_i = driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b2.rowEven > td.IValue > a > img")).getAttribute("title");
        assertEquals("96.24", b2_i);

        String b3_i = driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b3.rowOdd > td.IValue > a > img")).getAttribute("title");
        assertEquals("100", b3_i);

        driver.switchTo().window(parentHandle);
        //driver.findElement(By.cssSelector("#Topicsmenu > table > tbody > tr:nth-child(1) > td")).click();
        driver.findElement(By.cssSelector("#TopicsContents > a:nth-child(4) > div > img")).click();
        driver.switchTo().frame("Content");
        //check C i value
        // c1   300
        // c2   200
        // c3   100
        String c1_i = driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.c1.rowOdd > td.IValue > a > img")).getAttribute("title");
        assertEquals("300", c1_i);

        String c2_i = driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.c2.rowEven > td.IValue > a > img")).getAttribute("title");
        assertEquals("200", c2_i);

        String c3_i = driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.c3.rowOdd > td.IValue > a > img")).getAttribute("title");
        assertEquals("100", c3_i);

        driver.quit();

    }

    @Test(priority = 50, enabled = true)
    public void setup_SIPRA() {
        System.setProperty("webdriver.chrome.driver", "C://seleniumIDE//chrome/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        //options.addArguments("disable-web-security");
        //options.addArguments("start-maximized");
        options.addArguments("test-type");

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        WebDriver driver = new ChromeDriver(options);

        driver.get(baseUrl + "/" + servletContext);
        assertTrue(driver.getTitle().startsWith("ThoughtWeb Login"));
        driver.findElement(By.id("j_username")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

        driver.findElement(By.cssSelector("div.portalLinks a[href=\"/" + servletContext + "/app/" + kdName + "/\"]")).click();

        String parentHandle = driver.getWindowHandle();

        // click the build icon
        driver.findElement(By.cssSelector("body > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td.nav > a:nth-child(15) > img")).click();

        driver.switchTo().frame("Content");
        driver.findElement(By.cssSelector("body > table > tbody > tr:nth-child(2) > td:nth-child(1) > a > img")).click();

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        driver.findElement(By.cssSelector("#PerfMs1")).clear();
        driver.findElement(By.cssSelector("#PerfMs1")).sendKeys("Performance1");

        driver.findElement(By.cssSelector("#PerfMs1Weight")).clear();
        driver.findElement(By.cssSelector("#PerfMs1Weight")).sendKeys("98");

        driver.findElement(By.cssSelector("#PerfMs1Base")).clear();
        driver.findElement(By.cssSelector("#PerfMs1Base")).sendKeys("31");

        driver.findElement(By.cssSelector("#PerfMs1Target")).clear();
        driver.findElement(By.cssSelector("#PerfMs1Target")).sendKeys("113");

        driver.findElement(By.cssSelector("body > form:nth-child(4) > table:nth-child(31) > tbody > tr:nth-child(2) > td:nth-child(6) > select > option:nth-child(1)")).click();


        driver.findElement(By.cssSelector("#PerfMs2")).clear();
        driver.findElement(By.cssSelector("#PerfMs2")).sendKeys("Performance2");

        driver.findElement(By.cssSelector("#PerfMs2Weight")).clear();
        driver.findElement(By.cssSelector("#PerfMs2Weight")).sendKeys("46");

        driver.findElement(By.cssSelector("#PerfMs2Base")).clear();
        driver.findElement(By.cssSelector("#PerfMs2Base")).sendKeys("130");

        driver.findElement(By.cssSelector("#PerfMs2Target")).clear();
        driver.findElement(By.cssSelector("#PerfMs2Target")).sendKeys("50");

        driver.findElement(By.cssSelector("body > form:nth-child(4) > table:nth-child(31) > tbody > tr:nth-child(3) > td:nth-child(6) > select > option:nth-child(2)")).click();

        driver.findElement(By.cssSelector("#btnfmConceptProperties240")).click();

        // back to main page
        driver.switchTo().window(parentHandle);

        driver.switchTo().frame("Content");
        driver.findElement(By.cssSelector("body > table > tbody > tr:nth-child(3) > td:nth-child(1) > a > img")).click();

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        assertEquals("A Performance", driver.findElement(By.cssSelector("#PerfMs1")).getAttribute("value") );
        assertEquals("20.0", driver.findElement(By.cssSelector("#PerfMs1Weight")).getAttribute("value") ) ;
        assertEquals("0.0", driver.findElement(By.cssSelector("#PerfMs1Base")).getAttribute("value")) ;
        assertEquals("100.0", driver.findElement(By.cssSelector("#PerfMs1Target")).getAttribute("value") ) ;

        driver.findElement(By.cssSelector("#PerfMs2")).clear();
        driver.findElement(By.cssSelector("#PerfMs2")).sendKeys("PerformancdB1");

        driver.findElement(By.cssSelector("#PerfMs2Weight")).clear();
        driver.findElement(By.cssSelector("#PerfMs2Weight")).sendKeys("80");

        driver.findElement(By.cssSelector("#PerfMs2Base")).clear();
        driver.findElement(By.cssSelector("#PerfMs2Base")).sendKeys("45");

        driver.findElement(By.cssSelector("#PerfMs2Target")).clear();
        driver.findElement(By.cssSelector("#PerfMs2Target")).sendKeys("77");

        driver.findElement(By.cssSelector("body > form:nth-child(4) > table:nth-child(31) > tbody > tr:nth-child(3) > td:nth-child(6) > select > option:nth-child(2)")).click();

        driver.findElement(By.cssSelector("#btnfmConceptProperties240")).click();


        // back to main page
        driver.switchTo().window(parentHandle);

        driver.switchTo().frame("Content");
        driver.findElement(By.cssSelector("body > table > tbody > tr:nth-child(4) > td:nth-child(1) > a > img")).click();

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        assertEquals("B Performance", driver.findElement(By.cssSelector("#PerfMs1")).getAttribute("value") ) ;
        assertEquals("20.0", driver.findElement(By.cssSelector("#PerfMs1Weight")).getAttribute("value") ) ;
        assertEquals("0.0", driver.findElement(By.cssSelector("#PerfMs1Base")).getAttribute("value") ) ;
        assertEquals("100.0", driver.findElement(By.cssSelector("#PerfMs1Target")).getAttribute("value") ) ;

        driver.findElement(By.cssSelector("#btnfmConceptProperties240")).click();

        // back to main page
        driver.switchTo().window(parentHandle);

        driver.findElement(By.cssSelector("td.nav img[name='Build']")).click();
        driver.switchTo().frame("Content");

        // A KS
        driver.findElement(By.cssSelector("body > center > map > area:nth-child(2)")).click();
        // click on a1 edit
        driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.a1.rowOdd > td.PValue > a > img")).click();

        // click on Performance1
        driver.findElement(By.cssSelector("body > center:nth-child(9) > table > tbody > tr > td:nth-child(1) > a:nth-child(2) > img")).click();

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        driver.findElement(By.cssSelector("body > form:nth-child(3) > table > tbody > tr:nth-child(2) > td:nth-child(2) > input[type=\"text\"]:nth-child(2)")).sendKeys("87");
        driver.findElement(By.cssSelector("#btnfmInput5")).click();
        driver.findElement(By.cssSelector("#btnfmInput0")).click();

        // back to main page
        driver.switchTo().window(parentHandle); // back to the main window

        driver.switchTo().frame("Content");
        //click on Performance2
        driver.findElement(By.cssSelector("body > center:nth-child(10) > table > tbody > tr > td:nth-child(1) > a:nth-child(2) > img")).click();

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }
        driver.findElement(By.cssSelector("body > form:nth-child(3) > table > tbody > tr:nth-child(2) > td:nth-child(2) > input[type=\"text\"]:nth-child(2)")).sendKeys("99");
        driver.findElement(By.cssSelector("#btnfmInput5")).click();
        driver.findElement(By.cssSelector("#btnfmInput0")).click();

        // back to main page
        driver.switchTo().window(parentHandle);

        driver.findElement(By.cssSelector("td.nav img[name='Build']")).click();
        driver.switchTo().frame("Content");

        // B KS
        driver.findElement(By.cssSelector("body > center > map > area:nth-child(3)")).click();
        // click on b1 edit
        driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b1.rowOdd > td.PValue > a > img")).click();
        // click on Performance1
        driver.findElement(By.cssSelector("body > center:nth-child(10) > table > tbody > tr > td:nth-child(1) > a:nth-child(2) > img")).click();

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }
        driver.findElement(By.cssSelector("body > form:nth-child(3) > table > tbody > tr:nth-child(2) > td:nth-child(2) > input[type=\"text\"]:nth-child(2)")).sendKeys("75");
        driver.findElement(By.cssSelector("#btnfmInput5")).click();
        driver.findElement(By.cssSelector("#btnfmInput0")).click();


        // back to main page
        driver.switchTo().window(parentHandle);
        driver.findElement(By.cssSelector("td.nav img[name='Build']")).click();

        driver.findElement(By.cssSelector("#Optionsmenu > table > tbody > tr:nth-child(1) > td")).click();
        driver.findElement(By.cssSelector("#administrationItemHead > td > b > i")).click();

        // run SIPRA
        driver.findElement(By.cssSelector("#MenuTable > tbody:nth-child(1) > tr:nth-child(5) > td:nth-child(1) > a:nth-child(1) > div:nth-child(1)")).click();

        try {
            Thread.sleep(2000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        driver.quit();

    }

    @Test(priority = 60, enabled = true)
    public void checkFinal_SIPRA_value(){
        System.setProperty("webdriver.chrome.driver", "C://seleniumIDE//chrome/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        //options.addArguments("disable-web-security");
        //options.addArguments("start-maximized");
        options.addArguments("test-type");

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        WebDriver driver = new ChromeDriver(options);

        driver.get(baseUrl + "/" + servletContext);
        assertTrue(driver.getTitle().startsWith("ThoughtWeb Login"));
        driver.findElement(By.id("j_username")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

        driver.findElement(By.cssSelector("div.portalLinks a[href=\"/"+servletContext+"/app/"+kdName+"/\"]")).click();


        // run SIPRA 2nd time
 //       driver.findElement(By.cssSelector("td.nav img[name='Build']")).click();
//        driver.findElement(By.cssSelector("#Optionsmenu > table > tbody > tr:nth-child(1) > td")).click();
//        driver.findElement(By.cssSelector("#administrationItemHead > td > b > i")).click();
//
//        driver.findElement(By.cssSelector("#MenuTable > tbody:nth-child(1) > tr:nth-child(5) > td:nth-child(1) > a:nth-child(1) > div:nth-child(1)")).click();
//
//        try {
//            Thread.sleep(2000);                 //1000 milliseconds is one second.
//        } catch(InterruptedException ex) {
//            Thread.currentThread().interrupt();
//        }


        // from the topic menu
        driver.findElement(By.cssSelector("#Topicsmenu > table > tbody > tr:nth-child(1) > td")).click();
        driver.findElement(By.cssSelector("#TopicsContents > a:nth-child(2) > div > img")).click();
        String parentHandle = driver.getWindowHandle();



        driver.switchTo().frame("Content");
        //check A
        //      S       I       P       R
        // a1   41.2    70      58.86   28.8
        // a2   0       80      0       0
        // a3   0       100     0       0
        assertEquals("41.2", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.a1.rowOdd > td.SValue > a > img")).getAttribute("title"));
        assertEquals("70", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.a1.rowOdd > td.IValue > a > img")).getAttribute("title"));
        assertEquals("58.86", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.a1.rowOdd > td.PValue > a > img")).getAttribute("title"));
        assertEquals("28.8", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.a1.rowOdd > td.RValue > a > img")).getAttribute("title"));

        assertEquals("0", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.a2.rowEven > td.SValue > a > img")).getAttribute("title"));
        assertEquals("80", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.a2.rowEven > td.IValue > a > img")).getAttribute("title"));
        assertEquals("0", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.a2.rowEven > td.PValue > a > img")).getAttribute("title"));
        assertEquals("0", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.a2.rowEven > td.RValue > a > img")).getAttribute("title"));

        assertEquals("0", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.a3.rowOdd > td.SValue > a > img")).getAttribute("title"));
        assertEquals("100", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.a3.rowOdd > td.IValue > a > img")).getAttribute("title"));
        assertEquals("0", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.a3.rowOdd > td.PValue > a > img")).getAttribute("title"));
        assertEquals("0", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.a3.rowOdd > td.RValue > a > img")).getAttribute("title"));

        // back to main page
        driver.switchTo().window(parentHandle);
        driver.findElement(By.cssSelector("td.nav img[name='Build']")).click();

        driver.findElement(By.cssSelector("#Optionsmenu > table > tbody > tr:nth-child(1) > td")).click();
        driver.findElement(By.cssSelector("#administrationItemHead > td > b > i")).click();



        driver.switchTo().window(parentHandle);
        //driver.findElement(By.cssSelector("#Topicsmenu > table > tbody > tr:nth-child(1) > td")).click();
        driver.findElement(By.cssSelector("#TopicsContents > a:nth-child(3) > div > img")).click();
        driver.switchTo().frame("Content");
        //check B
        //      S       I       P       R
        // b1   76.21   96.56   78.92   20.35
        // b2   16.18   96.24   16.82   80.06
        // b3   15.26   100     15.26   84.74
        assertEquals("76.21", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b1.rowOdd > td.SValue > a > img")).getAttribute("title"));
        assertEquals("96.56", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b1.rowOdd > td.IValue > a > img")).getAttribute("title"));
        assertEquals("78.92", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b1.rowOdd > td.PValue > a > img")).getAttribute("title"));
        assertEquals("20.35", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b1.rowOdd > td.RValue > a > img")).getAttribute("title"));

        assertEquals("16.18", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b2.rowEven > td.SValue > a > img")).getAttribute("title"));
        assertEquals("96.24", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b2.rowEven > td.IValue > a > img")).getAttribute("title"));
        assertEquals("16.82", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b2.rowEven > td.PValue > a > img")).getAttribute("title"));
        assertEquals("80.06", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b2.rowEven > td.RValue > a > img")).getAttribute("title"));

        assertEquals("15.26", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b3.rowOdd > td.SValue > a > img")).getAttribute("title"));
        assertEquals("100", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b3.rowOdd > td.IValue > a > img")).getAttribute("title"));
        assertEquals("15.26", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b3.rowOdd > td.PValue > a > img")).getAttribute("title"));
        assertEquals("84.74", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b3.rowOdd > td.RValue > a > img")).getAttribute("title"));

        // back to main page
        driver.switchTo().window(parentHandle);
        driver.findElement(By.cssSelector("td.nav img[name='Build']")).click();

        driver.findElement(By.cssSelector("#Optionsmenu > table > tbody > tr:nth-child(1) > td")).click();
        driver.findElement(By.cssSelector("#administrationItemHead > td > b > i")).click();

        driver.switchTo().window(parentHandle);
        //driver.findElement(By.cssSelector("#Topicsmenu > table > tbody > tr:nth-child(1) > td")).click();
        driver.findElement(By.cssSelector("#TopicsContents > a:nth-child(4) > div > img")).click();
        driver.switchTo().frame("Content");
        //check C
        //      S       I       P       R
        // c1   33.58   100     33.58   66.42
        // c2   24.67   66.67   37      42
        // c3   12.33   33.33   37      21
        assertEquals("33.58", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.c1.rowOdd > td.SValue > a > img")).getAttribute("title"));
        assertEquals("100", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.c1.rowOdd > td.IValue > a > img")).getAttribute("title"));
        assertEquals("33.58", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.c1.rowOdd > td.PValue > a > img")).getAttribute("title"));
        assertEquals("66.42", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.c1.rowOdd > td.RValue > a > img")).getAttribute("title"));

        assertEquals("24.67", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.c2.rowEven > td.SValue > a > img")).getAttribute("title"));
        assertEquals("66.67", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.c2.rowEven > td.IValue > a > img")).getAttribute("title"));
        assertEquals("37", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.c2.rowEven > td.PValue > a > img")).getAttribute("title"));
        assertEquals("42", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.c2.rowEven > td.RValue > a > img")).getAttribute("title"));

        assertEquals("12.33", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.c3.rowOdd > td.SValue > a > img")).getAttribute("title"));
        assertEquals("33.33", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.c3.rowOdd > td.IValue > a > img")).getAttribute("title"));
        assertEquals("37", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.c3.rowOdd > td.PValue > a > img")).getAttribute("title"));
        assertEquals("21", driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.c3.rowOdd > td.RValue > a > img")).getAttribute("title"));


        driver.quit();

    }


}
