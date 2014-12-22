/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014 lokirobotics
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.lokirobotics.trekkr;

import java.io.File;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.lokirobotics.trekkr.core.ScreenshotDirectory;
import com.lokirobotics.trekkr.core.ScreenshotDirectoryGroup;
import com.lokirobotics.trekkr.core.TakesScreenshotFactory;

/**
 * Use the {@link ScreenshotManager} rule to create screenshots in your test cases.
 * <p>
 * The {@link ScreenshotManager} takes care of creating and saving the screenshot files.<br />
 * Needs a {@link ProvidesWebDriver} instance to be instantiated.
 * </p>
 * @author Christoph Fichtmueller
 */
public class ScreenshotManager implements MethodRule {
	
	private final String baseDirectoryPath;
	private final ProvidesWebDriver webDriverSource;
	
	private ScreenshotDirectory currentDirectory;
	
	/**
	 * Default constructor.<br />
	 * Sets the base directory to {@code test-screenshots}.
	 * @param webDriverSource the {@link WebDriver} source to use for taking screenshots
	 * <br />every screenshot request will be executed using the given {@link WebDriver}
	 */
	public ScreenshotManager(final ProvidesWebDriver webDriverSource) {
		this(webDriverSource, "trekkr-screenshots");
	}
	
	/**
	 * Use this constructor if you want to specify the output directory for the taken screenshots.
	 * @param webDriverSource the {@link WebDriver} source to use for taking screenshots
	 * <br />every screenshot request will be executed using the given {@link WebDriver}
	 * @param baseDirectoryPath the directory in which to store the screenshots
	 */
	public ScreenshotManager(final ProvidesWebDriver webDriverSource, final String baseDirectoryPath) {
		this.webDriverSource = webDriverSource;
		this.baseDirectoryPath = baseDirectoryPath;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.junit.rules.MethodRule#apply(org.junit.runners.model.Statement, org.junit.runners.model.FrameworkMethod, java.lang.Object)
	 */
	public Statement apply(final Statement base,
						   final FrameworkMethod method,
						   final Object target) {
		this.currentDirectory = ScreenshotDirectoryGroup
							    .get(this, baseDirectoryPath, target)
							    .createScreenshotDirectory(method);
		final TakeScreenshot ts = method.getAnnotation(TakeScreenshot.class);
		return ts == null ? base : new Statement() {

			/*
			 * (non-Javadoc)
			 * @see org.junit.runners.model.Statement#evaluate()
			 */
			@Override
			public void evaluate() throws Throwable {
				base.evaluate();
				takeScreenshot(ts.value());
			}
			
		};
	}
					
	/**
	 * Takes a screenshot and saves it using a specified name.
	 * @param name the name of the screenshot
	 */
	public void takeScreenshot(final String name) {
		final TakesScreenshot ts = TakesScreenshotFactory.create(webDriverSource.getWebDriver());
		final File screenshot = ts.getScreenshotAs(OutputType.FILE);
		currentDirectory.storeFile(screenshot, name);
	}
		
	/**
	 * Takes a screenshot and sets the name to {@code "screenshot"}.
	 * @see #takeScreenshot(String)
	 */
	public void takeScreenshot() {
		takeScreenshot("screenshot");
	}

}
