import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
	
	
	
	
	
}

