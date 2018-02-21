import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchTree {
	
	Node root;
	ArrayList<String> exploredStateNames = new ArrayList<String>();
	Stack<Node> frontierStates = new Stack<Node>();
	
	public SearchTree(String name, int childNum) {
		root = new Node(name, childNum, null);
		System.out.println(root.stateName);
		frontierStates.add(root);
		
	}
	
	
	//for specifying if the node has already been found
	public boolean isExploredNode(String name) {
		if(exploredStateNames.contains(name)) {
			return true;
		}
		return false;
	}
	
	public void addExplored(String name) {
		this.exploredStateNames.add(name);
	}
	
	
	
	public boolean isFrontierEmpty() {
		return frontierStates.empty();
	}
	
	public Node popFrontier() {
		return frontierStates.pop();
	}
	
	public void pushFrontier(Node n) {
		this.frontierStates.push(n);
	}
	
	public Node getRoot() {
		return root;
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
	
	}
		

	
	
}

