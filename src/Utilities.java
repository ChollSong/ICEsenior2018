import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
	//getting front components of each part
	public static String parseNameRegex(String s) {
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
	//will have to modify clickableCount to consider NAF
	public static int clickableCount(String s) {
		int i =0;
		 String pattern = "<android.*clickable=\"true\".*>";
	     Pattern r = Pattern.compile(pattern);
	     Matcher m = r.matcher(s);
	     while (m.find()) {
	        //  System.out.println(m.group());
	        i++;
	     }
		return i;
		
	}
	
	public static boolean isFirstPage(String s) {
		 String pattern = "YES|Yes|Accept|ACCEPT|NEXT|Next|AGREE|Agree|START|Start";
	     Pattern r = Pattern.compile(pattern);
	     Matcher m = r.matcher(s);
		boolean status = false;
		status = clickableCount(s)<3 && m.find();
		return status;
	}
	
	public static String foundString(String s) {
		 String txt="";
		 String pattern = "YES|Yes|Accept|ACCEPT|NEXT|Next|AGREE|Agree|START|Start";
	     Pattern r = Pattern.compile(pattern);
	     Matcher m = r.matcher(s);
	     if(m.find()) {
	    	 txt=m.group();
	     }
	     return txt;
	}
	
	public static String getPackageName(String s) {
		String packageName ="";
		String pattern = "package=[\".a-zA-Z0-9-]*";
	    Pattern r = Pattern.compile(pattern);
	    Matcher m = r.matcher(s);
	    if(m.find()) {
	        packageName = packageName + m.group();
	    }
	    return packageName;
	}
	
	//LevenShteinDistance
	//will need to see if 80% of the total length of string
	public static int levenshteinDistance (CharSequence lhs, CharSequence rhs) {                          
	    int len0 = lhs.length() + 1;                                                     
	    int len1 = rhs.length() + 1;                                                     
	                                                                                    
	    // the array of distances                                                       
	    int[] cost = new int[len0];                                                     
	    int[] newcost = new int[len0];                                                  
	                                                                                    
	    // initial cost of skipping prefix in String s0                                 
	    for (int i = 0; i < len0; i++) cost[i] = i;                                     
	                                                                                    
	    // dynamically computing the array of distances                                  
	                                                                                    
	    // transformation cost for each letter in s1                                    
	    for (int j = 1; j < len1; j++) {                                                
	        // initial cost of skipping prefix in String s1                             
	        newcost[0] = j;                                                             
	                                                                                    
	        // transformation cost for each letter in s0                                
	        for(int i = 1; i < len0; i++) {                                             
	            // matching current letters in both strings                             
	            int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;             
	                                                                                    
	            // computing cost for each transformation                               
	            int cost_replace = cost[i - 1] + match;                                 
	            int cost_insert  = cost[i] + 1;                                         
	            int cost_delete  = newcost[i - 1] + 1;                                  
	                                                                                    
	            // keep minimum cost                                                    
	            newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
	        }                                                                           
	                                                                                    
	        // swap cost/newcost arrays                                                 
	        int[] swap = cost; cost = newcost; newcost = swap;                          
	    }                                                                               
	                                                                                    
	    // the distance is the cost for transforming all letters in both strings        
	    return cost[len0 - 1];                                                          
	}
	
	public static boolean isSimilar(String s1, String s2) {
		boolean similarity = false;
		float maxLength = Math.max(s1.length(), s2.length());
		float levenDis = levenshteinDistance(s1,s2);
		System.out.println("Leven Distance is: "+levenDis/maxLength);
		if(0.05> levenDis/maxLength) {
			
			similarity = true;
			System.out.println("similar");
		}else {
			System.out.println("not similar");
		}
		
		return similarity;
	}
	
	
	
	
	
	
	
	
public static void main(String[] args) {
		
		String s = "<android.widget.FrameLayout index=\"0\" text=\"\" class=\"android.widget.FrameLayout\" package=\"com.jvstudios.claptofindmyphone\" content-desc=\"\" checkable=\"false\" checked=\"false\" clickable=\"false\" enabled=\"true\" focusable=\"false\" focused=\"false\" scrollable=\"false\" long-clickable=\"false\" password=\"false\" selected=\"false\" instance=\"0\">\r\n" + 
				"        <android.widget.LinearLayout index=\"0\" text=\"\" class=\"android.widget.LinearLayout\" package=\"com.jvstudios.claptofindmyphone\" content-desc=\"\" checkable=\"false\" checked=\"false\" clickable=\"false\" enabled=\"true\" focusable=\"false\" focused=\"false\" scrollable=\"false\" long-clickable=\"false\" password=\"false\" selected=\"false\" instance=\"0\">\r\n" + 
				"            <android.widget.FrameLayout index=\"0\" text=\"\" class=\"android.widget.FrameLayout\" package=\"com.jvstudios.claptofindmyphone\" content-desc=\"\" checkable=\"false\" checked=\"false\" clickable=\"false\" enabled=\"true\" focusable=\"false\" focused=\"false\" scrollable=\"false\" long-clickable=\"false\" password=\"false\" selected=\"false\" instance=\"1\">\r\n" + 
				"                <android.widget.RelativeLayout index=\"0\" text=\"\" class=\"android.widget.RelativeLayout\" package=\"com.jvstudios.claptofindmyphone\" content-desc=\"\" checkable=\"false\" checked=\"false\" clickable=\"false\" enabled=\"true\" focusable=\"false\" focused=\"false\" scrollable=\"false\" long-clickable=\"false\" password=\"false\" selected=\"false\" instance=\"0\">\r\n" + 
				"                    <android.widget.TextView NAF=\"true\" index=\"0\" text=\"\" class=\"android.widget.TextView\" package=\"com.jvstudios.claptofindmyphone\" content-desc=\"\" checkable=\"false\" checked=\"false\" clickable=\"true\" enabled=\"true\" focusable=\"false\" focused=\"false\" scrollable=\"false\" long-clickable=\"false\" password=\"false\" selected=\"false\" instance=\"0\"/>\r\n" + 
				"                    <android.widget.TextView index=\"1\" text=\"Continue\" class=\"android.widget.TextView\" package=\"com.jvstudios.claptofindmyphone\" content-desc=\"\" checkable=\"false\" checked=\"false\" clickable=\"true\" enabled=\"true\" focusable=\"false\" focused=\"false\" scrollable=\"false\" long-clickable=\"false\" password=\"false\" selected=\"false\" instance=\"1\"/>\r\n" + 
				"                </android.widget.RelativeLayout>\r\n" + 
				"            </android.widget.FrameLayout>\r\n" + 
				"        </android.widget.LinearLayout>\r\n" + 
				"    </android.widget.FrameLayout>";
		
		
		System.out.println(getPackageName(s));
		
		
		
	}
}
