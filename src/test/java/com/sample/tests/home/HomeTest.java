package com.sample.tests.home;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sample.tests.basetest.BaseTest;
import com.sample.views.home.HomeView;

public class HomeTest extends BaseTest {

	HomeView homeview;

	@Test
	public void login() {

	}

	@Override
	@BeforeMethod
	public void setup() {
		homeview = new HomeView(driver);

	}

	@Override
	@AfterMethod
	public void stopServer() {

	}
}
