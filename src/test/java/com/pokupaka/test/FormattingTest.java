package com.pokupaka.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.Locale;
import java.util.Locale.Category;

/**
 * Test superclass that runs tests in Turkish locale.
 */
public abstract class FormattingTest {

	private static Locale locale;

	@BeforeClass
	public static void setUpClass() {
		locale = Locale.getDefault(Category.FORMAT);
		Locale.setDefault(Category.FORMAT, new Locale("tr", "TR"));
	}

	@AfterClass
	public static void tearDownClass() {
		Locale.setDefault(Category.FORMAT, locale);
		locale = null;
	}

}