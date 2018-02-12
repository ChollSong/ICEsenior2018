import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.RemoteWebElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.lang.Double;
import java.util.*;



public class testDoctorme

{

    public static AppiumDriver wd;
    public static Random random = new Random();
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static Long times;
   




    public static void main(String[] args) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium-version", "1.7.2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "6.0.1");
        capabilities.setCapability("deviceName", "Galaxy J7");
        capabilities.setCapability("unicodeKeyboard", "true");
        capabilities.setCapability("app", "C:/Users/Chollsong212/Documents/SeniorProject/Senior/Senior/apk/ApiDemos-debug.apk");
        wd = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        wd.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);


        System.out.println("Start");
        System.out.print("Input times : ");

        Scanner scanner = new Scanner(System.in);
        long times = scanner.nextLong();
        times = times*60000;
        long startTime = System.currentTimeMillis();
        long endTime = startTime+times;

        //profile();

        while (System.currentTimeMillis()<endTime)
        	try{
        {

            int randomcase = random.nextInt(13)+1;
            System.out.println("sss"+randomcase);
            switch (randomcase)
            {
                case 1:
                    System.out.println("case 1 button");
                        //if(wd.findElements(By.className("android.widget.Button")).size()>0)

                        if(wd.findElements(By.className("android.widget.Button")).isEmpty())
                            {
                                //System.out.println("f 1" + wd.findElements(By.className("android.widget.Button")).size());
                                System.out.println(">>>>>>Not found button");
                                break;
                            }
                         else
                            {
                                System.out.println("found button");
                                button();
                                extraTap();
                                break;
                            }

                case 2:
                    System.out.println("case 2 imagebutton");
                        if(wd.findElements(By.className("android.widget.ImageButton")).isEmpty())
                            {
                                System.out.println(">>>>>>>Not found imagebutton");
                                break;
                            }
                        else
                            {
                                System.out.println("found imagebutton");
                                imageButton();
                                extraTap();
                                break;
                            }

                case 3:
                    System.out.println("case 3 radio button ");
                        if(wd.findElements(By.className("android.widget.RadioButton")).isEmpty())
                        {
                            System.out.println(">>>>>>Not found radiobutton");
                            break;
                        }
                        else
                        {
                            System.out.println("found radiobutton");
                            radioButton();
                            extraTap();
                            break;

                        }

                case 4:
                    System.out.println("case 4 actionbar");
                         if(wd.findElements(By.className("android.app.ActionBar$Tab")).isEmpty())
                         {
                             System.out.println(">>>>>>>Not found actionbar");
                             break;
                         }
                         else
                         {
                            System.out.println("found actionbar");
                             actionBarTab();
                             extraTap();
                            break;
                         }

                case 5:
                    System.out.println("case 5 textview");
                        if(wd.findElements(By.className("android.widget.TextView")).isEmpty())
                        {
                            System.out.println(">>>>>>Not found textview");
                            break;
                        }
                        else
                        {
                            System.out.println("found textview");
                            textView();
                            mobileTap();
                            break;
                        }

                case 6:
                    System.out.println("case 6 linearlayout");
                    if(wd.findElements(By.className("android.widget.LinearLayout")).isEmpty())
                        {
                            System.out.println(">>>>>Not found linearlayout");
                            break;
                        }
                        else
                        {
                            System.out.println("found linearlayout");
                            linearLayout();
                            extraTap();
                            break;
                        }

                case 7:
                    System.out.println("case 7 edittext");
                        if(wd.findElements(By.className("android.widget.EditText")).isEmpty())
                        {
                            System.out.println(">>>>>>Not found edittext");
                            break;
                        }
                        else
                        {
                            System.out.println("found edittext");
                            editText();
                            extraTap();
                            break;
                        }
                case 8:
                    System.out.println("case 8 seekbar");

                    if(wd.findElements(By.className("android.widget.SeekBar")).isEmpty())
                    {
                        System.out.println(">>>>>>Not found seekbar");
                        break;
                    }
                    else
                    {
                        System.out.println("found seekbar");
                        mobileTap();
                        extraTap();
                        break;
                    }
                case 9:
                	System.out.println("case 9 imageview");
                	if(wd.findElements(By.className("android.widget.ImageView")).isEmpty())
                    {
                        System.out.println(">>>>>>Not found imageview");
                        break;
                    }
                    else
                    {
                        System.out.println("found imageview");
                        imageView();  
                        break;
                    }
                case 10:
                	System.out.println("case 10 checktextview");
                	if(wd.findElements(By.className("android.widget.CheckedTextView")).isEmpty())
                    {
                        System.out.println(">>>>>>Not found checkedtextview");
                        break;
                    }
                    else
                    {
                        System.out.println("found checkedtextview");
                        checkedTextview();
                        break;
                    }
                case 11:
                	System.out.println("case 11 spinner");
                	if(wd.findElements(By.className("android.widget.Spinner")).isEmpty())
                    {
                        System.out.println(">>>>>>Not found spinner");
                        break;
                    }
                    else
                    {
                        System.out.println("found spinner");
                        spinner();
                        break;
                    }
                case 12:
                	System.out.println("case 12 relativelayout");
                	if(wd.findElements(By.className("android.widget.RelativeLayout")).isEmpty())
                    {
                        System.out.println(">>>>>>Not found relativelayout");
                        break;
                    }
                    else
                    {
                        System.out.println("found relativelayout");
                        relativeLayout();
                        break;
                    }
                case 13:
                	System.out.println("case 13 viewView");
                	if(wd.findElements(By.className("android.view.View")).isEmpty())
                    {
                        System.out.println(">>>>>>Not found viewView");
                        break;
                    }
                    else
                    {
                        System.out.println("found viewView");
                        view();
                        break;
                    }
                    
                    
                    
            }



        }
        	}catch(Exception e)
        	{
        		System.out.println("got some error here!!!");
        	}
        System.out.println("finish");



    }


  


    public static void button()
    {
        List<WebElement> classname = wd.findElements(By.className("android.widget.Button")) ;
        int index = random.nextInt(classname.size());
        classname.get(index).click();
        System.out.println("buttonindex"+index);
        System.out.println("button was clicked");
   

    }



    public static void imageButton()
    {
        List<WebElement> classname = wd.findElements(By.className("android.widget.ImageButton")) ;
        int index = random.nextInt(classname.size());
        classname.get(index).click();
        System.out.println("imagebuttonindex"+index);
        System.out.println("Imagebutton was clicked");

    }

    public static void radioButton()
    {
        List<WebElement> classname = wd.findElements(By.className("android.widget.RadioButton")) ;
        int index = random.nextInt(classname.size());
        classname.get(index).click();
        System.out.println("radiobuttonindex"+index);
        System.out.println("Radiobutton was clicked");

    }



    public static void actionBarTab()
    {
        List<WebElement> classname= wd.findElements(By.className("android.app.ActionBar$Tab")) ;
        int index = random.nextInt(classname.size());
        classname.get(index).click();
        System.out.println("actionbarindex"+index);
        System.out.println("actionBar was clicked");

    }

    public static void textView()
    {
        List<WebElement> classname= wd.findElements(By.className("android.widget.TextView"));
        int index = random.nextInt(classname.size());
        classname.get(index).click();
        System.out.println("textviewindex"+index);
        System.out.println("Textview was clicked");

    }

    public static void linearLayout()
    {
        List<WebElement> classname= wd.findElements(By.className("android.widget.LinearLayout")) ;
        int index = random.nextInt(classname.size());
        classname.get(index).click();
        System.out.println("linearlayoutindex"+index);
        System.out.println("linearLayout was clicked");

    }


    public static void editText()
    {
        List<WebElement> classname= wd.findElements(By.className("android.widget.EditText")) ;
        if (classname.size()>1)
            {
                for (int i = 0; i < classname.size(); i++)
                {
                    classname.get(i).sendKeys("MsecureTest"+randomString(4));
                    System.out.println("edittextindex"+i);
                    System.out.println("editText was sent key");

                }

                if(!wd.findElements(By.className("android.widget.Button")).isEmpty())
                {
                    button();
                }
                else if (!wd.findElements(By.className("android.widget.ImageButton")).isEmpty())
                {
                    imageButton();
                }
            }
        else
            {
                classname.get(0).sendKeys("MsecureTest"+randomString(4));
                System.out.println("editText was sent key index 0");
            }

    }

//   check seek bar more than tap eveyone;;;;
//    public  static void seekBar()
//    {
//        List<WebElement> classname = wd.findElements(By.className("android.widget.SeekBar"));
//        if(classname.size()>1)
//        {
//            for (int i = 1; i <= classname.size(); i++)
//            {
//                mobileTap();
//            }
//
//        }
//        else
//        {
//            mobileTap();
//        }
//    }
    public static void imageView()
    {

    	List<WebElement> classname= wd.findElements(By.className("android.widget.ImageView")) ;
        int index = random.nextInt(classname.size());
        classname.get(index).click();
        System.out.println("imageiewindex"+index);
        System.out.println("imageview was clicked");
    }
    public static void checkedTextview()
    {
    	List<WebElement> classname= wd.findElements(By.className("android.widget.CheckedTextView")) ;
        int index = random.nextInt(classname.size());
        classname.get(index).click();
        System.out.println("checktextviewindex"+index);
        System.out.println("checktextview was clicked");
    }
    
    public static void spinner()
    {
    	List<WebElement> classname= wd.findElements(By.className("android.widget.Spinner")) ;
        int index = random.nextInt(classname.size());
        classname.get(index).click();
        System.out.println("Spinnerindex"+index);
        System.out.println("Spinner was clicked");
        if(!wd.findElements(By.className("android.widget.CheckedTextView")).isEmpty())
        {
        	System.out.println("found checkedtextview after click spinner");
        	checkedTextview();
        }
    }
    
    public static void relativeLayout()
    {
    	List<WebElement> classname= wd.findElements(By.className("android.widget.RelativeLayout")) ;
        int index = random.nextInt(classname.size());
        classname.get(index).click();
        System.out.println("relativelayoutindex"+index);
        System.out.println("relativelayout was clicked");
    }
    public static void view()

    {

    List<WebElement> classname= wd.findElements(By.className("android.view.View")) ;

        int index = random.nextInt(classname.size());

        classname.get(index).click();

        System.out.println("Viewindex"+index);

        System.out.println("View was clicked");

    }

    
    
    public static void mobileTap()
    {
        wd.executeScript("mobile: tap", new HashMap<String, Double>() {{
            put("tapCount", 1.0);
            put("touchCount", 1.0);
            put("duration", 0.5);
            put("x", 418.0);
            put("y", 303.0);
        }});
    }


    public static void extraTap()
    {
        if(!wd.findElements(By.className("android.widget.TextView")).isEmpty())
        {
            mobileTap();
        }
    }

    public static String randomString( int len )
    {
        StringBuilder sb = new StringBuilder(len);
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt(random.nextInt(AB.length())) );
        return sb.toString();
    }




}
