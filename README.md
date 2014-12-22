trekkr
======

trekkr is a small library intended to be used in selenium test cases.

If you ever had to take screenshots in your test cases trekkr is the right tool for you.
Via a simple API you can create named screenshots. They will be automatically be
saved and grouped by test-class and test-case.

##Disclaimer
This project isn't production ready yet. The API may change at any given time. Do not use this library in production code until the 1.0.0 version is released. When the first version is released you will be able to include the dependency via the maven central repository. Until then you will have to build the library by yourself.

##Prerequisites
In order to use the trekkr library add the following dependency to your pom file

````xml
<dependency>
  <groupId>com.lokirobotics</groupId>
  <artifactId>trekkr</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <scope>test</scope>
</dependency
````

##Usage

As the goal of the trekkr library is the easy creation of screenshots it doesn't take much to do so.

````java

// imports omitted

public class SampleTest implements ProvidesWebDriver {
	
	@Rule
	public ScreenshotManager screenshotManager = new ScreenshotManager(this);
	
	private WebDriver webDriver;
	
	@Before
	public void setUp() {
		this.webDriver = new FirefoxDriver();
	}
	
	@After
	public void tearDown() {
		this.webDriver.close();
	}
	
	@Test
	public void takeMultipleScreenshots() {
		getWebDriver().get("http://en.wikipedia.org");
		screenshotManager.takeScreenshot("Wikipedia-start-page");
		final WebElement searchBox = getWebDriver().findElement(By.id("searchInput"));
		searchBox.sendKeys("pi\n");
		screenshotManager.takeScreenshot("should-be-the-pi-article");
	}
	
	@Test
	@TakeScreenshot("selenium-article")
	public void takeSingleScreenshot() {
		getWebDriver().get("http://en.wikipedia.org/wiki/Selenium_(software)");
	}

	/*
	 * (non-Javadoc)
	 * @see com.lokirobotics.trekkr.ProvidesWebDriver#getWebDriver()
	 */
	@Override
	public WebDriver getWebDriver() {
		return this.webDriver;
	}

}
````

The two ways to create screenshots are the following

You can either use the _ScreenshotManager_ rule to directly take a screenshot.
````java
//somewhere in your test case
screenShotManager.takeScreenshot("my-screenshot-name");
````

Or use the _TakeScreenshot_ annotation to automatically take a screenshot at the end of the test.
````java
@Test
@TakeScreenshot("my-special-screenshot")
public void someTest() {
	// do something
}
````

Essentially there are three things you have to do:

1. provide a _WebDriver_ instance through the _ProvidesWebDriver_ interface
2. set up a _ScreenshotManager_ rule
3. either tell the _ScreenshotManager_ to take a screenshot or annotate a test case with the _TakeScreenshot_ annotation

That's pretty much it.
After you ran the tests you will find a folder named *trekkr-screenshots* containing the screenshots grouped by test-class and test-cases.
Running the sample above would produce the following:

* trekkr.sample.SampleTest
  * takeMultipleScreenshots
    * 0_Wikipedia-start-page.png
    * 1_should-be-the-pi-article.png
  * takeSingleScreenshot
    * 0_selenium-article.png
