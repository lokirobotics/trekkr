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
package com.lokirobotics.trekkr.core;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;

import com.lokirobotics.trekkr.core.ScreenshotDirectoryGroup;

/**
 * @author Christoph Fichtmueller
 */
public class ScreenshotDirectoryGroupTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	
	@Mock
	private Object owner1;
	@Mock
	private Object owner2;
	@Mock
	private Object testClass;
	
	private String baseDirectory;
	
	
	@Before
	public void setUp() {
		initMocks(this);
		baseDirectory = tempFolder.getRoot().getAbsolutePath();
	}
	
	@Test
	public void loadGroupTwiceForSameOwner_returnedGroupsShouldBeTheSame() {
		final ScreenshotDirectoryGroup group1 = ScreenshotDirectoryGroup
												.get(owner1, baseDirectory, testClass);
		
		final ScreenshotDirectoryGroup group2 = ScreenshotDirectoryGroup
												.get(owner1, baseDirectory, testClass);
		
		assertSame(group1, group2);
	}
	
	@Test
	public void loadGroupForDifferentOwners_returnedGroupsShouldBeDifferent() {
		final ScreenshotDirectoryGroup group1 = ScreenshotDirectoryGroup
												.get(owner1, baseDirectory, testClass);
		final ScreenshotDirectoryGroup group2 = ScreenshotDirectoryGroup
												.get(owner2, baseDirectory, testClass);
		
		assertNotSame(group1, group2);
	}
}
