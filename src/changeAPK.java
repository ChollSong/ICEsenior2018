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
    	
    	for(int j = x-1 ;j < y ;j++){
    	
				File currentFile = filesInFolder.get(j);
				//alter this
				currentAPK = "/Volumes/Senior/APK/1-100/" + currentFile.getName();
			
    		System.out.println(currentAPK);
    		try {
    			 DesiredCapabilities capabilities = new DesiredCapabilities();
                 capabilities.setCapability("appium-version", "1.7.2");
                 capabilities.setCapability("platformName", "Android");
                 capabilities.setCapability("platformVersion", "6.0");
                 capabilities.setCapability("deviceName", "YFBDU15519002831");
                 capabilities.setCapability("unicodeKeyboard", "true");                
                 capabilities.setCapability("app", currentAPK);
                 capabilities.setCapability("fullReset", "false");
                 wd = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
                 wd.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    		}catch(Exception e){
    			Utilities.log(currentFile+"APK ERROR");
    			System.out.println("error in loading file");
    		}
    		
    		
             
            
            Utilities.log(currentAPK+" start ");
            //enter test here

            //initialize the term
            start();
            
            String firstPageName = getName();
            //int firstPageClickable = getClickableNum();
            //replaced with utilities function instead
            int firstPageClickable = Utilities.clickableCount(firstPageName);
            
            System.out.println("firstPage name is: "+firstPageName);
            System.out.println(firstPageClickable+" possible ways to go");
            //need code to determine if the code is from somewhere else
            //create a search Tree
            SearchTree sTree = new SearchTree(firstPageName, firstPageClickable, Utilities.getPackageName(firstPageName));
            System.out.println("Official package name from first page is: "+sTree.packageName);
            
//Template if you want to use timeout
            

            while(!sTree.isFrontierEmpty()) {
            	System.out.println("Processing");
            	Node n = sTree.popFrontier();
    			sTree.addExplored(n.stateName);
            	for(int i=0; i< n.clickableNum; i++) {
            		String currentName = "ErrorState";
            		int clickableNum = 0;
            		if(goIndex(i)) {
            			
            		}
            		currentName = getName();
            		clickableNum = Utilities.clickableCount(currentName);
            		//limit to 15
            		if(clickableNum>15) {
            			clickableNum = 15;
            		}
            		//for error
            		try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		if(!sTree.isExploredNode(currentName)&&!currentName.equals("ErrorState")) {
            			//for handling cases where exit app
            			
            			if(!Utilities.getPackageName(currentName).equals(sTree.packageName)) {
            				currentName = "OUTSIDE APP";
            				//for seeing what kind of package name it has
            				System.out.println(currentName);
            				clickableNum = 0;
            			}
            			Node newNode = new Node(currentName,clickableNum,n ,i);
            			
            			System.out.println(n.clickableNum+" ways that can go");
            			sTree.pushFrontier(newNode);
            		}else {
            			System.out.println("at explored");
            			System.out.println(currentName);
            		}
            		goNode(sTree, n);
            	}
            }
            
            System.out.println("test is over");
            //When the test end 
            Utilities.log(currentFile.getName()+" end ");
     
            wd.removeApp(currentFile.getName().substring(0, currentFile.getName().length()-4));
            wd.quit();
    	}
    	    	
    }
    
    public static void goNode(SearchTree st, Node n) {
    	//go back to first page
    	int count = 0;
    	while(!Utilities.isSimilar(getName(), st.getRoot().stateName)) {
    	//while(!getName().equals(st.getRoot().stateName)) {
    		if(count>n.depth+1) {
    			System.out.println("reseting");
    			wd.resetApp();
            	try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	start();
            	break;
    		}
    		System.out.println("going back");
    		goBack();
    		count++;
    		try {
    			//can be set for longer
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	
    	/*wd.resetApp();
    	try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	traverseNode(n);
    	
    
    }
    
    private static void traverseNode(Node n) {
    	if(n.route==-1){
    		System.out.println("At root");
    		return;
    	}
    	try {
			Thread.sleep(30);
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
                Thread.sleep(30);
    
            }
                }catch(Exception e)
                {
                    System.out.println("ERROR:back");
                }
    }

    public static boolean goIndex(int i){
        try{
            {
            	System.out.println("go to "+i);
                WebElement el =  wd.findElement(MobileBy.AndroidUIAutomator("new UiSelector().clickable(true).instance("+i+")"));   
                el.click();
                return true;
    
            }
                }catch(Exception e)
                {
                    System.out.println("ERROR:index at: "+ i);
                    return false;
                }
    }
    
    //if it error return -1
    //old function no longer in use
    public static int getClickableNum(){
        int count = -1;
        System.out.println("getting clickable Num");
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
              //  name = Utilities.parseNameRegex(name);
              //  int hashed = name.hashCode();
                //answer = Integer.toString(hashed);
                //return answer;
                
                
                return name;
    
            }
                }catch(Exception e)
                {
                    System.out.println("ERROR:name");
                }
        return answer;
    }
    //resets the app
    public static void start() {
    	//might need to change to something else if decide to hash current name
        long times = 5000;
        //times = times*60000;
        long startTime = System.currentTimeMillis();
        long endTime = startTime+times;
        //for stalling until start
        //might need to set timeout so bounch out of app
        while (System.currentTimeMillis()<endTime && Utilities.clickableCount(getName())<1) {
			 try {
				 System.out.println("Count of clickables"+Utilities.clickableCount(getName()));
			Thread.sleep(300);
			 } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 }
		 	}
        
        if(Utilities.isLoadPage(getName())) {
        try {
 
        	WebElement el =  wd.findElement(MobileBy.AndroidUIAutomator("new UiSelector().clickable(true).textMatches(\""+
        	Utilities.foundString(getName())
        			+"\")"));
        	if(el!= null) {
        		el.click();
        	}
			Thread.sleep(30);
			 } catch (InterruptedException e) {
			// TODO Auto-generated catch block
	
			 }
        }
    }
    
}