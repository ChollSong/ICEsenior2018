import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDeviceActionShortcuts;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;

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
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Double;
import java.util.*;

public class clickbutton {
    
            public static AppiumDriver  wd;
            public static Long times;


            public static void main(String[] args) throws MalformedURLException {
                
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("appium-version", "1.7.2");
                capabilities.setCapability("platformName", "Android");
                capabilities.setCapability("platformVersion", "6.0.1");
                capabilities.setCapability("deviceName", "Galaxy J7");
                capabilities.setCapability("unicodeKeyboard", "true");                
                capabilities.setCapability("app", "C:/Users/Chollsong212/Documents/SeniorProject/Senior/Senior/apk/ApiDemos-debug.apk");
                capabilities.setCapability("fullReset", "true");
                wd = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
                wd.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                
                String firstPageName = getName();
                System.out.println("firstPage name is: "+firstPageName);
                System.out.println(getClickableNum()+" possible ways to go");
                //create a search Tree
                SearchTree sTree = new SearchTree(firstPageName, getClickableNum());
                
// Template if you want to use timeout
//              System.out.println("Start");
//              System.out.print("Input times : ");

//              Scanner scanner = new Scanner(System.in);
//              long times = scanner.nextLong();
//              times = times*60000;
//              long startTime = System.currentTimeMillis();
//              long endTime = startTime+times;
//
//             
//
//              while (System.currentTimeMillis()<endTime)
                //check if frontier is empty
                
                while(!sTree.isFrontierEmpty()) {
                	Node n = sTree.popFrontier();
        			sTree.addExplored(n.stateName);
                	for(int i=0; i< n.clickableNum; i++) {
                		goIndex(i);
                		//for error
                		try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                		if(!sTree.isExploredNode(getName())) {
                			Node newNode = new Node(getName(),getClickableNum(),n ,i);
                			System.out.println("frontier name is: "+newNode.stateName);
                			sTree.pushFrontier(newNode);
                		}
                		goNode(sTree, n);
                	}
                }
                
                System.out.println("test is over");
           
            }
            
            public static void goNode(SearchTree st, Node n) {
            	//go back to first page
            	
            	/*
            	while(!getName().equals(st.getRoot().stateName)) {
            		System.out.println("going back");
            		goBack();
            		try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}*/
            	wd.resetApp();
            	try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	traverseNode(n);
            	
            
            }
            
            private static void traverseNode(Node n) {
            	if(n.route==-1){
            		return;
            	}
            	try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	traverseNode(n.parent);
            	goIndex(n.route);
            }
            
          

            public static void goBack(){
                try{
                    {
                        
                        ((AndroidDriver) wd).pressKeyCode(AndroidKeyCode.BACK);
                        Thread.sleep(100);
            
                    }
                        }catch(Exception e)
                        {
                            System.out.println("ERROR:back");
                        }
            }

            public static void goIndex(int i){
                try{
                    {
                    	System.out.println("go to "+i);
                        WebElement el =  wd.findElement(MobileBy.AndroidUIAutomator("new UiSelector().clickable(true).instance("+i+")"));   
                        el.click();
            
                    }
                        }catch(Exception e)
                        {
                            System.out.println("ERROR:index");
                        }
            }
            
            //if it error return -1
            public static int getClickableNum(){
                int count = -1;
                try{
                    {
                        List<MobileElement> el = (List<MobileElement>) wd.findElements(MobileBy.AndroidUIAutomator("new UiSelector().clickable(true)"));
                        count = el.size();
                        System.out.println("Total clickable "+count);
                        return count;
                    }
                        }catch(Exception e)
                        {
                            System.out.println("ERROR:getNUM");
                        }
                return count;
            }
            
            //if it error return ERROR:name
            public static String getName(){
                String answer = "ERROR:name";
                
                try{
                    {
                        
                        String name = wd.getPageSource();
                        name = parseNameRegex(name);
                        int hashed = name.hashCode();
                        answer = Integer.toString(hashed);
                        return answer;
            
                    }
                        }catch(Exception e)
                        {
                            System.out.println("ERROR:name");
                        }
                return answer;
            }
            
            private static String parseNameRegex(String s) {
            	String name = "";
                String pattern = "<android\\.[a-zA-Z0-9]*\\.[a-zA-Z0-9]*\\s";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(s);
                while (m.find()) {
                	 //  System.out.println(m.group());
                	   name = name + m.group();
                }
                if(name.equals("")) {
                	System.out.println("ERROR:regex parse");
                	return "ERROR";
                }else {
                	return name;
                }
            	
            }
            
}
                    
