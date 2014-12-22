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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.runners.model.FrameworkMethod;

/**
 * A place where multiple {@link ScreenshotDirectory ScreenshotDirectories} gather.<br />
 * Give each test class its own group.
 * @author Christoph Fichtmueller
 */
public class ScreenshotDirectoryGroup extends AbstractDirectory {
	
	private static Map<Object, ScreenshotDirectoryGroup> instances = new HashMap<Object, ScreenshotDirectoryGroup>();
	
	public static ScreenshotDirectoryGroup get(final Object owner,
			 								   final String baseDirectory,
			 								   final Object testClass) {
		if (!instances.containsKey(owner)) {
			final ScreenshotDirectoryGroup group = new ScreenshotDirectoryGroup(baseDirectory, testClass);
			instances.put(owner, group);
		}
		return instances.get(owner);
	}

	private ScreenshotDirectoryGroup(final String baseDirectory,
									final Object testClass) {
		super(baseDirectory
				+ ((baseDirectory == null) || baseDirectory.isEmpty() ? "" : File.separator)
				+ testClass.getClass().getName()
			);
		createMeIfIDoNotExistYet();
	}
	
	public ScreenshotDirectory createScreenshotDirectory(final FrameworkMethod testMethod) {
		return new ScreenshotDirectory(getPath(), testMethod);
	}
}
