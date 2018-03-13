
public class Node{
		Node parent = null;
		//number of children
		int clickableNum = -1;
		//name
		String stateName;
		//route
		int route = -1;
		//depth
		int depth = 0;
		public Node(String name, int childNum, Node parent) {
			//declare structure of the node
			clickableNum = childNum;
			stateName = name;
			this.parent = parent;
			if(parent != null) {
				depth = parent.depth+1;
			}
			
			System.out.println(stateName);
		}
		
		
		public String getName() {
			return stateName;
		}
		
		public int getClickableNum(){
			return clickableNum;
		}
		
		public void setClickableNum(int x){
			clickableNum = x;
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