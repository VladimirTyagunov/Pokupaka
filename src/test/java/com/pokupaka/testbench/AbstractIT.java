package com.pokupaka.testbench;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.pokupaka.testbench.elements.ui.LoginViewElement;
import com.pokupaka.ui.utils.PokupakaAppConst;
import com.vaadin.testbench.IPAddress;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchDriverProxy;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.parallel.ParallelTest;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.LoggerFactory;

public abstract class AbstractIT<E extends TestBenchElement> extends ParallelTest {
	public String APP_URL = "http://localhost:8080/";

	static {
		// Prevent debug logging from Apache HTTP client
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.INFO);
		// Let notifications persist longer during tests
		PokupakaAppConst.NOTIFICATION_DURATION = 10000;
	}

	@Rule
	public ScreenshotOnFailureRule screenshotOnFailure = new ScreenshotOnFailureRule(this, true);

	@Override
	public void setup() throws Exception {
		System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
		super.setup();
		if (getRunLocallyBrowser() == null) {
			APP_URL = "http://" + IPAddress.findSiteLocalAddress() + ":8080/";
		}
	}

	@Override
	public TestBenchDriverProxy getDriver() {
		return (TestBenchDriverProxy) super.getDriver();
	}

	@Override
	public void setDesiredCapabilities(DesiredCapabilities desiredCapabilities) {
		// Disable interactivity check in Firefox https://github.com/mozilla/geckodriver/#mozwebdriverclick
		if (desiredCapabilities.getBrowserName().equals(BrowserType.FIREFOX)) {
			desiredCapabilities.setCapability("moz:webdriverClick", false);
		}

		super.setDesiredCapabilities(desiredCapabilities);
	}

	protected LoginViewElement openLoginView() {
		return openLoginView(getDriver(), APP_URL);
	}

	protected LoginViewElement openLoginView(WebDriver driver, String url) {
		driver.get(url);
		return $(LoginViewElement.class).waitForFirst();
	}

	protected abstract E openView();

}
