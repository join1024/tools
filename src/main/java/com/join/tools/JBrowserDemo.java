package com.join.tools;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.Timezone;

/**
 * @author ：Join
 * @date ：Created in 2019/6/19 19:59
 * @modified By：
 */
public class JBrowserDemo {

	public static void main(String[] args) {

		// You can optionally pass a Settings object here,
		// constructed using Settings.Builder
		JBrowserDriver driver = new JBrowserDriver(Settings.builder().
				timezone(Timezone.AMERICA_NEWYORK).build());

		// This will block for the page load and any
		// associated AJAX requests
		System.out.println("=========== 1");
		//driver.get("http://www.qktsw.com/tingshu/51314.html");
		driver.get("http://www.qktsw.com/playbook/51314-1-2.html");

		// You can get status code unlike other Selenium drivers.
		// It blocks for AJAX requests and page loads after clicks
		// and keyboard events.
		System.out.println("=========== 2");
		System.out.println(driver.getStatusCode());

		// Returns the page source in its current state, including
		// any DOM updates that occurred after page load
		System.out.println("=========== 3");
		System.out.println(driver.getPageSource());


		// Close the browser. Allows this thread to terminate.
		driver.quit();
	}


}
