
public class Node{
		Node parent = null;
		//number of children
		int clickableNum = -1;
		//name
		String stateName;
		//route
		int route = -1;
		public Node(String name, int childNum, Node parent) {
			//declare structure of the node
			clickableNum = childNum;
			stateName = name;
			this.parent = parent;
			
			System.out.println(stateName);
		}
		
		
		public String getName() {
			return stateName;
		}
		
		public Node(String name, int childNum, Node parent, int route) {
			//declare structure of the node
			this.route = route;
			clickableNum = childNum;
			stateName = name;
			this.parent = parent;
			System.out.println(stateName);
		}
		
	}