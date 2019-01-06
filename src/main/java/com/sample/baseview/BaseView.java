package com.sample.baseview;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class BaseView {

	public static Logger log;
	public AppiumDriver<MobileElement> driver;

	public BaseView(AppiumDriver<MobileElement> driver) {
		this.driver = driver;
		log = Logger.getLogger("devpinoyLogger");
		log.info("Configuring log4j properties");
		PropertyConfigurator.configure(
				"C:\\Users\\Shaik.Hajara\\eclipse-workspace\\Glooko-master\\src\\main\\resources\\logs\\log4j.properties");
	}

	public void clickUsingJsExecutor(MobileElement ele) {
		final JavascriptExecutor js = driver;
		js.executeScript("arguments[0].click();", ele);

	}

	/**
	 * Method to get 'X' Coordinate of Element.
	 *
	 * @param ele- Used to get X Coordinate of element.
	 * @return- X Coordinate.
	 */
	public int findXCoordinate(MobileElement ele) {
		final int X = ele.getLocation().getX();
		System.out.println(X);
		return X;
	}

	/**
	 * Method to get Y Coordinate of Element.
	 *
	 * @param ele- Used to get Y-Coordinate of element.
	 * @return -Y Coordinate.
	 */
	public int findYCoordinate(MobileElement ele) {
		final int Y = ele.getLocation().getY();
		return Y;
	}

	public int getHeightOfScreen(MobileElement ele) {
		final int height = ele.getSize().getHeight();
		return height;
	}

	public int getMiddleYCoordinate(MobileElement ele) {
		final int middleY = (int) (findYCoordinate(ele) + getHeightOfScreen(ele) * 1.5);
		return middleY;
	}

	/**
	 * Method to get text from drop down list
	 *
	 * @param ele                is list mobile element
	 * @param startElementInList is element from where to scroll
	 */
	public void getTextFromDropdownList(List<MobileElement> ele, MobileElement startElementInList) {

		final int medicationListSize = ele.size();
		ele.forEach(names -> log.info(names.getText()));

		final String lastElement = ele.get(medicationListSize - 1).getText();
		log.info("last element in list is " + ele.get(medicationListSize - 1).getText());
		final String lastElementWithoutPrefix = lastElement.substring(0, lastElement.length() - 1);
		log.info(lastElementWithoutPrefix);
		if (!lastElementWithoutPrefix.equalsIgnoreCase("Afrezza")) {
			scrollUsingElements(ele.get(12), startElementInList);
			new BaseView(driver).getTextFromDropdownList(ele, startElementInList);
		} else {
			log.info("Cannot scroll further");
			driver.navigate().back();
			driver.navigate().back();
		}
	}

	public int getWidthOfScreen(MobileElement ele) {
		final int width = ele.getSize().getWidth();
		return width;
	}

	public void longPress(MobileElement ele, int timeOutInSec) {
		final TouchAction action = new TouchAction(driver);
		action.longPress(LongPressOptions.longPressOptions()
				.withPosition(PointOption.point(findXCoordinate(ele), findYCoordinate(ele)))).release().perform();

	}

	public void longPressAndMove(int X, int Y, MobileElement endElement, int timeOutInSeconds) {
		final TouchAction action = new TouchAction(driver);
		action.longPress(PointOption.point(X, Y))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(timeOutInSeconds)))
				.moveTo(PointOption.point(findXCoordinate(endElement), findYCoordinate(endElement))).release()
				.perform();

	}

	/**
	 * Method to scroll using X,Y coordinates.
	 *
	 * @param startX   - Used to find startX Coordinate of device.
	 * @param          startY- Used to find startY Coordinate of device.
	 * @param          endX- Used to find endX Coordinate of device.
	 * @param endY     -Used to find endY Coordinate of device.
	 * @param duration -Waiting time to scroll from start to end position.
	 */
	public void scrollUsingCoordinates(int duration) {
		final TouchAction action = new TouchAction(driver);
		final Dimension dimension = driver.manage().window().getSize();
		final int startX = dimension.getWidth() / 3;
		System.out.println(startX);
		final int startY = (int) (dimension.getHeight() * 0.8);
		System.out.println(startY);
		final int endX = (int) (dimension.getWidth() * 0.50);
		System.out.println(endX);
		final int endY = (int) (dimension.getHeight() * 0.50);
		System.out.println(endY);
		action.longPress(LongPressOptions.longPressOptions().withPosition(PointOption.point(startX, startY)))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(duration))).moveTo(PointOption.point(endX, endY))
				.release().perform();
		// action.longPress(startX,
		// startY).waitAction(Duration.ofSeconds(duration)).moveTo(endX, endY).release()
		// .perform();
	}

	/**
	 * Method to scroll from start element to end element.-Applicable for
	 * java-client version<6.0
	 *
	 * @param startElement-This is first parameter in scrollUsingElements method.
	 * @param endElement-This is second parameter in scrollUsingElements method.
	 */

	public void scrollUsingElements(MobileElement startElement, MobileElement endElement) {
		final TouchAction action = new TouchAction(driver);
		action.press(PointOption.point(findXCoordinate(startElement), findYCoordinate(startElement)))
				.moveTo(PointOption.point(findXCoordinate(endElement), findYCoordinate(endElement))).release()
				.perform();
		// action.press(startElement).waitAction(Duration.ofSeconds(10)).moveTo(endElement).release().perform();
	}

	/**
	 * Method to find endXCoordinate of Mobile Element.
	 *
	 * @param ele -used to find endX coordinate.
	 * @return -endX coordinate.
	 */
	public int secondXCoordinate(MobileElement ele) {
		final int secondX = ele.getLocation().getX() + ele.getSize().getWidth();
		return secondX;
	}

	/**
	 * Method to find endY Coordinate of Mobile Element.
	 *
	 * @param ele- used to find endY Coordinate.
	 * @return-end Y coordinate.
	 */
	public int secondYCoordinate(MobileElement ele) {
		final int secondY = ele.getLocation().getY() + ele.getSize().getHeight();
		return secondY;
	}

	public void swipeDirection(String direction, int durationInSeconds) {
		int startX;
		int startY;
		int endX;
		int endY;

		final Dimension dimension = driver.manage().window().getSize();
		final TouchAction action = new TouchAction(driver);
		switch (direction) {

		case "UP":
			startX = dimension.getWidth() / 2;
			startY = (int) (dimension.getHeight() * 0.80);
			endX = dimension.getWidth() / 2;
			endY = (int) (dimension.getHeight() * 0.20);
			action.longPress(LongPressOptions.longPressOptions().withPosition(PointOption.point(startX, startY)))

					.moveTo(PointOption.point(endX, endY)).release().perform();
			break;
		case "DOWN":
			startX = dimension.getWidth() / 2;
			startY = (int) (dimension.getHeight() * 0.2);
			endX = dimension.getWidth() / 2;
			endY = (int) (dimension.getHeight() * 0.8);
			action.longPress(LongPressOptions.longPressOptions().withPosition(PointOption.point(startX, startY)))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(durationInSeconds)))
					.moveTo(PointOption.point(endX, endY)).release().perform();

			break;
		case "LEFT":
			startX = (int) (dimension.getWidth() * 0.8);
			log.info(startX + " while swiping left side");
			startY = dimension.getHeight() / 2;
			log.info(startY + " while swiping left side");
			endX = (int) (dimension.getWidth() * 0.2);
			log.info(endX + " while swiping left side");
			endY = dimension.getHeight() / 2;
			log.info(endY + " while swiping LEFT side");
			action.longPress(LongPressOptions.longPressOptions().withPosition(PointOption.point(startX, startY)))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(durationInSeconds)))
					.moveTo(PointOption.point(endX, endY)).release().perform();
			break;

		case "RIGHT":
			startX = (int) (dimension.getWidth() * 0.2);
			log.info(startX + " while swiping right side");
			startY = dimension.getHeight() / 2;
			log.info(startY + " while swiping right side");
			endX = (int) (dimension.getWidth() * 0.8);
			log.info(endX + " while swiping right side");
			endY = dimension.getHeight() / 2;
			log.info(endY + " while swiping right side");
			action.longPress(LongPressOptions.longPressOptions().withPosition(PointOption.point(startX, startY)))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(durationInSeconds)))
					.moveTo(PointOption.point(endX, endY)).release().perform();
			break;

		case "FromLeftEdgeSide":
			startX = dimension.getWidth() / 45;
			log.info(startX + " while swiping right side");
			startY = dimension.getHeight() / 4;
			log.info(startY + " while swiping right side");
			endX = (int) (dimension.getWidth() * 0.6);
			log.info(endX + " while swiping right side");
			endY = (int) (dimension.getHeight() * 0.9);
			log.info(endY + " while swiping right side");
			action.longPress(LongPressOptions.longPressOptions().withPosition(PointOption.point(startX, startY)))
					.moveTo(PointOption.point(endX, endY)).release().perform();
			break;
		}

	}

	/**
	 * Method to swipe down Notification bar of Mobile device.Used for java
	 * client>6.0
	 */
	public void swipeNotificationBar() {
		log.info("Swipe down notification bar");
		final TouchAction action = new TouchAction(driver);
		action.longPress(LongPressOptions.longPressOptions().withPosition(PointOption.point(481, 37)))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(20))).moveTo(PointOption.point(507, 2233))
				.release().perform();

		// action.longPress(481, 37, Duration.ofSeconds(20)).moveTo(507,
		// 2233).release().perform();
	}

	/**
	 * Method to swipe from Top to Bottom.Used for latest version of java client>6.0
	 *
	 * @param durationInSeconds- Time to move from start to end coordinates.
	 */
	public void swipeTopToBottom(int durationInSeconds) {
		final TouchAction action = new TouchAction(driver);
		final Dimension size = driver.manage().window().getSize();
		final int startX = size.getWidth() / 3;
		System.out.println(startX);
		final int endX = (int) (size.getWidth() * 0.50);
		System.out.println(endX);
		final int endY = (int) (size.getHeight() * 0.50);
		System.out.println(endY);
		action.longPress(LongPressOptions.longPressOptions().withPosition(PointOption.point(startX, 2)))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(durationInSeconds)))
				.moveTo(PointOption.point(endX, endY)).release().perform();

		// action.longPress(startX,
		// 2).waitAction(Duration.ofSeconds(durationInSeconds)).moveTo(endX,
		// endY).release()
		// .perform();
	}

	public void takeScreenshotAtEndOfTest() throws IOException {
		final File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		final String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}

	public void tap(int X, int Y) {
		final TouchAction action = new TouchAction(driver);
		action.tap(PointOption.point(X, Y)).release().perform();
	}

	/**
	 * Method to wait for visibility of Mobile Element located by 'By'.
	 *
	 * @param by -This is first parameter of waitForElement method.
	 * @param    timeOutInSeconds- This parameter is used to wait for mentioned
	 *           time.
	 */
	public void waitForElement(By by, int timeOutInSeconds) {
		final WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	/**
	 * Method to wait for Visibility of Mobile element.
	 *
	 * @param ele- This is first paramater.Here we need to pass mobile element.
	 * @param timeOutInSeconds- This parameter is used to wait for mentioned time.
	 */
	public void waitForElement(MobileElement ele, int timeOutInSeconds) {
		log.info("Waiting for element" + " " + ele.toString());
		final WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(ele));
	}

}
