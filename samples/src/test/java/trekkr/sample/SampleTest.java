package trekkr.sample;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.lokirobotics.trekkr.ProvidesWebDriver;
import com.lokirobotics.trekkr.ScreenshotManager;
import com.lokirobotics.trekkr.TakeScreenshot;

/**
 * A simple test demonstrating the use of the trekkr api.
 * @author Christoph Fichtmueller
 */
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
