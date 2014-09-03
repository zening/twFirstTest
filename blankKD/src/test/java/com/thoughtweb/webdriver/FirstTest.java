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
        driver.findElement(By.cssSelector("body > center > map > area:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.Center.rowOdd > td.KsName > a:nth-child(1) > img")).click();

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

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
        driver.findElement(By.cssSelector("body > center > map > area:nth-child(3)")).click();
        driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b1.rowOdd > td.KsName > a:nth-child(1) > img")).click();

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

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

        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(8) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(3)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(9) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(3)")).click();
        driver.findElement(By.cssSelector("#fmWizard > center > table:nth-child(1) > tbody > tr:nth-child(10) > td:nth-child(2) > center > input[type=\"RADIO\"]:nth-child(3)")).click();

        driver.findElement(By.cssSelector("#fmWizard > table:nth-child(2) > tbody > tr > td:nth-child(2) > input[type=\"button\"]:nth-child(6)")).click();

        driver.switchTo().window(parentHandle); // back to the main window
        driver.findElement(By.cssSelector("td.nav img[name='Build']")).click();
        driver.switchTo().frame("Content");

        driver.close();

    }

    @Test(priority = 40, enabled = true)
    public void checkSIPRA_value(){
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
        // b1   114.48
        // b2   114.11
        // b3   100
        String b1_i = driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b1.rowOdd > td.IValue > a > img")).getAttribute("title");
        assertEquals("114.48", b1_i);

        String b2_i = driver.findElement(By.cssSelector("#ksHeaderTable > tbody > tr.b2.rowEven > td.IValue > a > img")).getAttribute("title");
        assertEquals("114.11", b2_i);

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



    }
}
