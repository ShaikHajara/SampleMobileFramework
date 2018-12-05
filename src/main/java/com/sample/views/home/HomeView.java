package com.sample.views.home;

import org.openqa.selenium.support.PageFactory;

import com.sample.baseview.BaseView;
import com.sample.pageobjects.HomePageObject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomeView extends BaseView {

	HomePageObject homePageObj = new HomePageObject();;

	public HomeView(AppiumDriver<MobileElement> driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), homePageObj);
	}

}
