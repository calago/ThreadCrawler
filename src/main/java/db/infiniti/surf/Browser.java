package db.infiniti.surf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.UnreachableBrowserException;

public class Browser {

	// FirefoxBrowser driver = new FirefoxBrowser();
	// FirefoxDriver driver = new FirefoxDriver(new FirefoxBinary(), profile,
	// new TimeSpan(0, 0, 0, timeoutSeconds));
	FirefoxDriver driver;
	String sourceText = "";
	String driverURL = "";
	List<WebElement> qResS = null;
	int timeOutThread = 4000; // milisecond
	int timeOutPageScript = 3; // seconds

	public long loadTime = 0;
	public long getSourceTime = 0;
	public long getTextTime = 0;
	public long totalStopTime = 0;
	public long totalDriverTime = 0;

	public void setDriver() {
		org.openqa.selenium.firefox.FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("dom.max_chrome_script_run_time", 0);
		profile.setPreference("dom.max_script_run_time", 0);
		profile.setPreference("webdriver.load.strategy", "fast");
		profile.setPreference("network.http.connection-timeout", 2);
		profile.setPreference("network.http.connection-retry-timeout", 1);
	//gives error-illegalArgument	profile.setPreference("dom.disable_open_during_load", true);

		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.showWhenStarting",
				false);
		profile.setPreference(
				"browser.download.dir",
				"/media/DATA/dropbox/Dropbox/ubuntu workspace/maven.1374667260682/CrawlingTestProj/crawledData/popupsaved");

		profile.setPreference(
				"browser.helperApps.neverAsk.saveToDisk",
				"application/octet-stream,,application/octet,application/pdf,application/x-pdf,application/vnd.ms-excel,"
						+ "application/x-xpinstall,application/x-zip,application/x-zip-compressed,application/zip,"
						+ "application/msword,text/plain,text/csv,"
						+ "application/vnd.fdf, application/x-msdos-program, application/x-unknown-application-octet-stream, "
						+ "application/vnd.ms-powerpoint, application/excel, "
						+ "application/vnd.ms-publisher, application/x-unknown-message-rfc822, "
						+ "application/msword, application/x-mspublisher, application/x-tar, "
						+ "application/x-gzip,application/x-stuffit, application/vnd.ms-works, application/powerpoint, "
						+ "application/rtf, application/postscript, application/x-gtar, video/quicktime, video/x-msvideo, "
						+ "video/mpeg, audio/x-wav, audio/x-midi, audio/x-aiff");

		driver = new FirefoxDriver(profile);
		// driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.manage().timeouts()
				.setScriptTimeout(timeOutPageScript, TimeUnit.SECONDS);
		driver.manage().timeouts()
				.pageLoadTimeout(timeOutPageScript, TimeUnit.SECONDS);
		/*
		 * String adblockfile =
		 * "/Users/username/Downloads/adblock_plus-2.4-tb+fx+an+sm.xpi";
		 * profile.add_extension(adblockfile);
		 * ffprofile.set_preference("extensions.adblockplus.currentVersion",
		 * "2.4")
		 */
		// driver = new FirefoxDriver();

		/*
		 * Thread t = new Thread(new Runnable() { public void run() {
		 * driver.get(Thread.currentThread().getName()); } }, url); t.start();
		 * try { t.join(YOUR_TIMEOUT_HERE_IN_MS); } catch (InterruptedException
		 * e) { // ignore } if (t.isAlive()) { // Thread still alive, we need to
		 * abort logger.warning("Timeout on loading page " + url);
		 * t.interrupt(); }
		 */

		/*
		 * driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		 * driver.manage().timeouts().setScriptTimeout(4, TimeUnit.SECONDS);
		 * driver.manage().timeouts().pageLoadTimeout(4, TimeUnit.SECONDS);
		 */
	}

	public void pressSaveKey() {
		Actions actions = new Actions(driver);

		/*
		 * List<WebElement> el = this.runXPathQuery("*");
		 * el.get(0).sendKeys(Keys.chord(Keys.CONTROL, "s"));
		 * el.get(0).sendKeys(Keys.NULL);
		 */

		actions.sendKeys(Keys.chord(Keys.CONTROL, "s", Keys.CLEAR)).perform();// not
																				// released
		actions.sendKeys(Keys.chord(Keys.ENTER)).perform();

		/*
		 * Robot robot; try { robot = new Robot(); // press Ctrl+S the Robot's
		 * way robot.keyPress(KeyEvent.VK_CONTROL);
		 * robot.keyPress(KeyEvent.VK_S); robot.keyRelease(KeyEvent.VK_CONTROL);
		 * robot.keyRelease(KeyEvent.VK_S); // press Enter
		 * robot.keyPress(KeyEvent.VK_ENTER);
		 * robot.keyRelease(KeyEvent.VK_ENTER); } catch (AWTException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */

	}

	public void captureSc(String savePath) {

		/*
		 * WebDriver driver = new FirefoxDriver(); //
		 * driver.get("http://www.google.com/"); try { WebDriver augmentedDriver
		 * = new Augmenter().augment(driver); File source =
		 * ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
		 * path = "./target/screenshots/" + source.getName();
		 * FileUtils.copyFile(source, new File(path)); } catch(IOException e) {
		 * path = "Failed to capture screenshot: " + e.getMessage(); } return
		 * path;
		 */

		// WebDriver driver = new FirefoxDriver();
		// driver.get(url); //"http://www.google.com/"

		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		try {
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(savePath + "_sc.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	public void DownloadImage(WebElement Image, String loc) {
		//TODO 
		
		// WebElement Image= driver.findElement(by);
		File screen = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		int width = Image.getSize().getWidth();
		int height = Image.getSize().getHeight();
		BufferedImage img;
		BufferedImage dest;
		try {
			img = ImageIO.read(screen);
			dest = img.getSubimage(Image.getLocation().getX(), Image
					.getLocation().getY(), width, height);
			ImageIO.write(dest, "png", screen);
			File file = new File(loc);
			FileUtils.copyFile(screen, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}


	public void loadPage(String url) {
		driverURL = url;//;"http://www.quotenet.nl/Miljonairs/Klaus-de-Clercq-Zubli";
		long time = System.currentTimeMillis();
		totalStopTime = 0;
		Thread t = new Thread(new Runnable() {
			public void run() {
				String url = Thread.currentThread().getName();
				try {
					driver.get(url);
				} catch (UnreachableBrowserException e) {
					System.out.println("UnreachableBrowserException " + url);
					setDriver();
					getPageSource(url);
				} catch (TimeoutException tmoutEx) {
					// TODO check if it is stopped or we need to use
					// window.stop();
					System.out.println("in thread, time out + driver.get "
							+ url);
				} catch (Exception e) {
					System.out.println("in thread, time out + driver.get "
							+ url + ", " + e.toString());
				}
			}
		}, url);
		t.start();
		try {
			t.join(timeOutThread);
		} catch (InterruptedException e) { // ignore
		}

		this.loadTime = System.currentTimeMillis() - time;
		totalDriverTime = totalDriverTime + loadTime;

		if (t.isAlive()) { // Thread still alive, we need to abort
			long time2 = System.currentTimeMillis();
			try {
				// driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
				if (driver instanceof JavascriptExecutor) {
					((JavascriptExecutor) driver)
							.executeScript("return window.stop();");
					System.out.println("page load is stopped. " + url);

				}
			} catch (UnreachableBrowserException e) {
				System.out
						.println("UnreachableBrowserException in stop() + loadingPage => new driver is set. "
								+ url);
				setDriver();
			} catch (Exception see) {
				System.out.println("in stop() + loadingPage " + url + ", "
						+ see.getStackTrace());
			}
			totalStopTime = totalStopTime + System.currentTimeMillis() - time2;

		}

	}

	public String getPageSource(String url) {
		// driver.navigate().to(url);
		// String sourceText = "";
		driverURL = url;//"http://www.quotenet.nl/Miljonairs/Klaus-de-Clercq-Zubli";
		//url = "http://www.quotenet.nl/Miljonairs/Klaus-de-Clercq-Zubli"
		long time = System.currentTimeMillis();
		totalStopTime = 0;
		Thread t = new Thread(new Runnable() {
			public void run() {
				String url = Thread.currentThread().getName();
				try {
					driver.get(url);
				//	
				} catch (UnreachableBrowserException e) {
					System.out.println("UnreachableBrowserException " + url);
					setDriver();
					getPageSource(url);
				} catch (TimeoutException tmoutEx) {
					// TODO check if it is stopped or we need to use
					// window.stop();
					System.out.println("in thread, time out + driver.get "
							+ url);
				} catch (Exception e) {
					System.out.println("in thread, time out + driver.get "
							+ url + ", " + e.toString());
				}
			}
		}, url);
		t.start();
		try {
			t.join(timeOutThread);
		} catch (InterruptedException e) { // ignore
		}

		this.loadTime = System.currentTimeMillis() - time;
		totalDriverTime = totalDriverTime + loadTime;

		if (t.isAlive()) { // Thread still alive, we need to abort
			long time2 = System.currentTimeMillis();
			try {
				if (driver instanceof JavascriptExecutor) {

					/*WebDriverBackedSelenium backedSelenuium = 
				            new WebDriverBackedSelenium(driver,"about:blank");    
*/
/*					((JavascriptExecutor) driver)
							.executeScript("return window.stop();");*/
					driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
					System.out.println("page load is stopped. " + url);
				}
			} catch (UnreachableBrowserException e) {
				System.out
						.println("UnreachableBrowserException in stop() + loadingPage => new driver is set. "
								+ url);
				setDriver();
			} catch (UnhandledAlertException uae) {
		        Alert alert = driver.switchTo().alert();
		        alert.accept();
		    }
			catch (Exception see) {
				System.out.println("in stop() + loadingPage " + url + ", "
						+ see.getStackTrace());
			}
			totalStopTime = totalStopTime + System.currentTimeMillis() - time2;

		}

		time = System.currentTimeMillis();
		t = new Thread(new Runnable() {
			public void run() {
				String url = Thread.currentThread().getName();
				try {
					sourceText = driver.getPageSource();
				//	 sourceText = getPageSourceNew(url);

				} catch (TimeoutException tmoutEx) {
					System.out.println("timeout + getPageSource " + url);
				} catch (UnreachableBrowserException e) {
					System.out.println("UnreachableBrowserException in getPageSource() "
							+ url);
					setDriver();
					try {
						sourceText = driver.getPageSource();
						// sourceText = getPageSourceNew(url);
						// view-source:
						// sourceText = this.getPageSourceNew(url);
					} catch (TimeoutException tmoutEx) {
						System.out.println("time out + getPageSource + new setDriver "
								+ url);
						// ((JavascriptExecutor)
						// driver).executeScript("return window.stop();");
					} catch (Exception ee) {
						System.out.println("getPageSource(), second try " + url
								+ " " + ee.toString());
					}
				} catch (Exception e) {
					System.out.println("driver.getPageSource()  " + url + " "
							+ e.toString());
				}
			}

		}, url);
		t.start();

		try {
			t.join(timeOutThread);
		} catch (InterruptedException e) { // ignore
		}
		this.getSourceTime = System.currentTimeMillis() - time;
		totalDriverTime = totalDriverTime + getSourceTime;

		if (t.isAlive()) { // Thread still alive, we need to abort
			long time2 = System.currentTimeMillis();
			try {
				if (driver instanceof JavascriptExecutor) {
					((JavascriptExecutor) driver)
							.executeScript("return window.stop();");
					// driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
					System.out
							.println("getpageSource - page load is stopped in getPageSource. "
									+ url);
				}
			} catch (UnreachableBrowserException e) {
				System.out
						.println("UnreachableBrowserException + executeScript - getpageSource "
								+ url);
				setDriver();
			} catch (TimeoutException tmoutEx) {
				System.out
						.println("TimeoutException in stop() - getpageSource "
								+ url);
			} catch (WebDriverException wE) {
				System.out
						.println("WebDriverException in getpageSource stop() in getPageSource. "
								+ url + wE.toString());
			} catch (Exception see) {
				System.out.println("Exception getpageSource stop(). " + url
						+ see.toString());
			}
			totalStopTime = totalStopTime + System.currentTimeMillis() - time2;

		}

		/*
		 * try { driver.get(url); } catch (TimeoutException e) { // ok //
		 * this.driver.close(); try { ((JavascriptExecutor)
		 * driver).executeScript("window.stop();"); } catch (Exception se) { //
		 * TODO System.out.println("page load is stopped."); } } catch
		 * (Exception e) { try { this.driver.close(); } catch (Exception ex) {
		 * System.out.println(ex.toString()); } }
		 */
		/*
		 * try { sourceText = driver.getPageSource(); } catch
		 * (org.openqa.selenium.WebDriverException webDriverException) {
		 * System.out.println("webDriverException for: "); } catch (Exception
		 * se) { System.out.println("driver.getPageSource() Exception for: "); }
		 */

		return sourceText;

		/*
		 * WebDriverWait wait = new WebDriverWait(browser, new TimeSpan(time in
		 * seconds)); wait.until(Your condition) you could also change the
		 * implicit wait time
		 * 
		 * driver.Manage().Timeouts().ImplicitlyWait(TimeSpan.FromSeconds(10));
		 */

	}
	public String getPageSourceNew(String url) {
		// TODO Auto-generated method stub
		driver.get("view-source:" + url);
		String sourceText = getPageText();
		driver.get(url);
		return sourceText;
	}
	/*
	 * public void downloadImage() { String s =
	 * driver.findElement(By.cssSelector("#navbtm img")) .getAttribute("src");
	 * 
	 * try { URL url = new URL(s); System.out.println(url); BufferedImage
	 * bufImgOne = ImageIO.read(url); ImageIO.write(bufImgOne, "png", new
	 * File("test.png")); } catch (IOException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } }
	 */
	public void stopFXDriver() {
		try {
			this.driver.close();
		} catch (UnreachableBrowserException e) {
			System.out.println("driver.close() + UnreachableBrowserException");
		} catch (Exception ex) {
			System.out.println("driver.close()" + ex.toString());
		}
	}

	public String getPageText() {

		long time = System.currentTimeMillis();
		String text = "";
		qResS = runXPathQuery("//body");// [contains(.,'content')]
		if (qResS != null && !qResS.isEmpty()) {
			try {
				text = qResS.get(0).getText().toLowerCase();
				qResS.clear();
			} catch (Exception e) {
				System.out
						.println("Error in qResS.get(0).getText().toLowerCase(). "
								+ this.driverURL);
				text = "Error in getting the text.";
			}
		} else {
			System.out.println("No results in runXPathQuery for getting text. "
					+ this.driverURL);
		}
		this.getTextTime = System.currentTimeMillis() - time;
		totalDriverTime = totalDriverTime + getTextTime;
		System.out.println("Loading time: " + this.loadTime + " miliseconds.");
		System.out.println("Source code time: " + this.getSourceTime
				+ " miliseconds.");
		System.out.println("Text time: " + this.getTextTime + " miliseconds.");
		System.out.println("Total stop page time: " + totalStopTime
				+ "miliseconds.");
		System.out.println("Total driver processing time: " + totalDriverTime
				+ "miliseconds.");
		if (totalDriverTime > 20000) {
			System.out.println("investigate.");
		}

		totalDriverTime = 0;
		return text;
	}

	public List<WebElement> runXPathQuery(String xpathExpression) {
		Thread t = new Thread(new Runnable() {
			public void run() {

				try {
					qResS = driver.findElements(By.xpath(Thread.currentThread()
							.getName()));
				} catch (UnreachableBrowserException e) {
					System.out.println("qResS = driver.findElements + UnreachableBrowserException"
							+ driver.getCurrentUrl());
					setDriver();
					try {
						driver.get(driverURL);
					} catch (Exception ee) {
						System.out.println("qResS = driver.findElements "
								+ driver.getCurrentUrl() + ee.toString());
					}
				} catch (Exception e) {
					System.out.println("qResS = driver.findElements "
							+ driver.getCurrentUrl() + e.toString());
				}
			}
		}, xpathExpression);
		t.start();
		try {
			t.join(timeOutThread);
		} catch (InterruptedException e) { // ignore
		}
		if (t.isAlive()) { // Thread still alive, we need to abort
			long time2 = System.currentTimeMillis();
			try {
				if (driver instanceof JavascriptExecutor) {

					((JavascriptExecutor) driver)
							.executeScript("return window.stop();");
					System.out
							.println("findElements.By.xpath - page is stopped. "
									+ this.driverURL);
				}
			} catch (UnreachableBrowserException e) {
				System.out
						.println("findElements.By.xpath - page is stopped + UnreachableBrowserException "
								+ this.driverURL);
				setDriver();
			} catch (Exception see) {
				System.out
						.println(" findElements.By.xpath - page window.stop() + error in findElements.By.xpath. "
								+ this.driverURL);
			}
			totalStopTime = totalStopTime + System.currentTimeMillis() - time2;

		}

		/*
		 * for(int i=0; i<l.size(); i++) { WebElement wel = qResS.get(i); wel.
		 * new
		 * HtmlElementGeometry(wel.getLocation().x,wel.getLocation().y,wel.getSize
		 * ().getHeight(),wel.getSize().getWidth()); }
		 */
		return qResS;
	}
}
