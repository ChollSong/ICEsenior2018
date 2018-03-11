import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDeviceActionShortcuts;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.File;
import java.io.IOException;
import java.lang.Double;
import java.util.*;



public class changeAPK {
	 public static AppiumDriver  wd;
     public static Long times;
     public static List<File> filesInFolder;
     public static String currentAPK;
     
    public static void main(String[] args) throws MalformedURLException {
        System.out.println("test APK from x to y");
        System.out.println("insert x");
    	Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        System.out.println("insert y");
    	Scanner scanner2 = new Scanner(System.in);
        int y = scanner2.nextInt();
        
        try {
			filesInFolder = Files.walk(Paths.get("/Volumes/Senior/APK/1-100"))
			        .filter(Files::isRegularFile)
			        .map(Path::toFile)
			        .collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	for(int i = x-1 ;i < y ;i++){
    	
				File currentFile = filesInFolder.get(i);
				currentAPK = "/Volumes/Senior/APK/1-100/" + currentFile.getName();
			
    		System.out.println(currentAPK);
    		
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("appium-version", "1.7.2");
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("platformVersion", "5.1.1");
            capabilities.setCapability("deviceName", "EP73249JM1");
            capabilities.setCapability("unicodeKeyboard", "true");                
            capabilities.setCapability("app", currentAPK);
          //  capabilities.setCapability("fullReset", "true");
            wd = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
            wd.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            
            //enter test here
            String test = wd.getPageSource();
            System.out.println(test);
            //When the test end 
            wd.quit();
    	}
    	    	
    }
}