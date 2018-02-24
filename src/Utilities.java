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
		if(0.05> levenDis/maxLength) {
			System.out.println("Leven Distance is: "+levenDis/maxLength);
			similarity = true;
			System.out.println("similar");
		}else {
			System.out.println("not similar");
		}
		
		return similarity;
	}
	
	
	
	
	
public static void main(String[] args) {
		
		String s = "<hierarchy rotation=\"0\">\r\n" + 
				"    <android.widget.FrameLayout index=\"0\" text=\"\" class=\"android.widget.FrameLayout\" package=\"com.top.android.boost\" content-desc=\"\" checkable=\"false\" checked=\"false\" clickable=\"false\" enabled=\"true\" focusable=\"false\" focused=\"false\" scrollable=\"false\" long-clickable=\"false\" password=\"false\" selected=\"false\" bounds=\"[0,0][1080,1812]\" resource-id=\"\" instance=\"0\">\r\n " + 
				"        <android.widget.LinearLayout index=\"0\" text=\"\" class=\"android.widget.LinearLayout\" package=\"com.top.android.boost\" content-desc=\"\" checkable=\"false\" checked=\"false\" clickable=\"false\" enabled=\"true\" focusable=\"false\" focused=\"false\" scrollable=\"false\" long-clickable=\"false\" password=\"false\" selected=\"false\" bounds=\"[0,0][1080,1812]\" resource-id=\"\" instance=\"0\">\r\n " + 
				"            <android.widget.FrameLayout index=\"0\" text=\"\" class=\"android.widget.FrameLayout\" package=\"com.top.android.boost\" content-desc=\"\" checkable=\"false\" checked=\"false\" clickable=\"false\" enabled=\"true\" focusable=\"false\" focused=\"false\" scrollable=\"false\" long-clickable=\"false\" password=\"false\" selected=\"false\" bounds=\"[0,72][1080,1812]\" resource-id=\"android:id/content\" instance=\"1\">\r\n " + 
				"                <android.widget.RelativeLayout index=\"0\" text=\"\" class=\"android.widget.RelativeLayout\" package=\"com.top.android.boost\" content-desc=\"\" checkable=\"false\" checked=\"false\" clickable=\"false\" enabled=\"true\" focusable=\"false\" focused=\"false\" scrollable=\"false\" long-clickable=\"false\" password=\"false\" selected=\"false\" bounds=\"[0,72][1080,1812]\" resource-id=\"\" instance=\"0\">\r\n " + 
				"                    <android.widget.LinearLayout index=\"0\" text=\"\" class=\"android.widget.LinearLayout\" package=\"com.top.android.boost\" content-desc=\"\" checkable=\"false\" checked=\"false\" clickable=\"true\" enabled=\"true\" focusable=\"false\" focused=\"false\" scrollable=\"false\" long-clickable=\"false\" password=\"false\" selected=\"false\" bounds=\"[0,72][1080,216]\" resource-id=\"com.top.android.boost:id/cn\" instance=\"1\">\r\n " + 
				"                        <android.widget.ImageView index=\"0\" text=\"\" class=\"android.widget.ImageView\" package=\"com.top.android.boost\" content-desc=\"\" checkable=\"false\" checked=\"false\" clickable=\"false\" enabled=\"true\" focusable=\"false\" focused=\"false\" scrollable=\"false\" long-clickable=\"false\" password=\"false\" selected=\"false\" bounds=\"[30,120][78,168]\" resource-id=\"com.top.android.boost:id/co\" instance=\"0\"/>\r\n " + 
				"                        <android.widget.TextView index=\"1\" text=\"Set up\" class=\"android.widget.TextView\" package=\"com.top.android.boost\" content-desc=\"\" checkable=\"false\" checked=\"false\" clickable=\"false\" enabled=\"true\" focusable=\"false\" focused=\"false\" scrollable=\"false\" long-clickable=\"false\" password=\"false\" selected=\"false\" bounds=\"[108,107][261,180]\" resource-id=\"\" instance=\"0\"/>\r\n " + 
				"                    </android.widget.LinearLayout>\r\n" + 
				"\r\n" + 
				" <android.view.View index=\"1\" text=\"\" class=\"android.view.View\" package=\"com.top.android.boost\" content-desc=\"\" checkable=\"false\" checked=\"false\" clickable=\"false\" enabled=\"true\" focusable=\"false\" focused=\"false\" scrollable=\"false\" long-clickable=\"false\" password=\"false\" selected=\"false\" bounds=\"[48,365][1080,366]\" resource-id=\"com.top.android.boost:id/gu\" instance=\"0\"/>";
		
		
		System.out.println(isSimilar("<android.widget.Image", "<android.widget.Line"));
		
		
		
	}
}
