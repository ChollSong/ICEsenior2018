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
import java.time.LocalDateTime;


public class HTTPMarker {
	
	
	
	static String apkPath = new File("httpMark.apk").getAbsolutePath();
   public static void markHTML() throws Exception {
	   AppiumDriver driver;
		 DesiredCapabilities capabilities = new DesiredCapabilities();
     capabilities.setCapability("appium-version", "1.7.2");
     capabilities.setCapability("platformName", "Android");
     capabilities.setCapability("platformVersion", "6.0.1");
     capabilities.setCapability("deviceName", "Galaxy J7");
     capabilities.setCapability("unicodeKeyboard", "true");                
     capabilities.setCapability("app", apkPath);
     capabilities.setCapability("autoGrantPermissions", "true");
     capabilities.setCapability("fullReset", "false");
     driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
     driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	   
       Thread.sleep(500);
       
       WebElement el =  driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().clickable(true).textMatches(\"Button\")"));   
       el.click();
       
       Thread.sleep(500);
      
       driver.removeApp("com.example.chollsong212.httpmarker");
   }
   

   public static void main(String[] args) throws Exception
   {
	   
   }
}